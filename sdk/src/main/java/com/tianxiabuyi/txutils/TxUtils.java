package com.tianxiabuyi.txutils;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.tianxiabuyi.txutils.config.TxConstants;
import com.tianxiabuyi.txutils.imageloader.TxImageLoader;
import com.tianxiabuyi.txutils.imageloader.universal.UniversalImageLoaderTool;
import com.tianxiabuyi.txutils.log.LogLevel;
import com.tianxiabuyi.txutils.log.TxLog;
import com.tianxiabuyi.txutils.network.Interceptor.TxInterceptor;
import com.tianxiabuyi.txutils.network.Interceptor.log.TxLoggerInterceptor;
import com.tianxiabuyi.txutils.network.Interceptor.mock.TxMockInterceptor;
import com.tianxiabuyi.txutils.network.util.Platform;
import com.tianxiabuyi.txutils.util.AppUtils;
import com.tianxiabuyi.txutils.util.NetUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author xjh1994
 * @date 2016/7/15
 */
public class TxUtils {

    public static final String TAG = TxUtils.class.getSimpleName();

    private TxConfiguration mConfiguration;

    private Platform mPlatform;

    private OkHttpClient mOkHttpClient;

    private volatile static TxUtils mInstance;

    public TxUtils() {
        mPlatform = Platform.get();
    }

    public static TxUtils getInstance() {
        if (mInstance == null) {
            synchronized (TxUtils.class) {
                if (mInstance == null) {
                    mInstance = new TxUtils();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化配置
     */
    public synchronized void init(TxConfiguration configuration) {
        this.mConfiguration = configuration;

        //TxLog
        if (configuration.mode) {
            TxLog.init(TAG).logLevel(LogLevel.FULL);
        } else {
            TxLog.init(TAG).logLevel(LogLevel.NONE);
        }

        //logger
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(0)
                .methodOffset(7)
                .tag(TAG)
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));

        //okHttp
        if (configuration.okhttpBuilder == null) {
            configuration.okhttpBuilder = new OkHttpClient.Builder();
            // 缓存
            if (configuration.isCacheOn()) {
                addCacheInterceptor(configuration.okhttpBuilder);
            }
            configuration.okhttpBuilder
                    .addInterceptor(new TxMockInterceptor())
                    .addInterceptor(new TxInterceptor(configuration))
                    .addInterceptor(new TxLoggerInterceptor(TAG, configuration.mode))
                    .connectTimeout(TxConstants.TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(TxConstants.TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(TxConstants.TIMEOUT, TimeUnit.SECONDS);
        }
        this.mOkHttpClient = configuration.okhttpBuilder.build();

        //ImageLoader
        if (configuration.imageLoaderProvider == null) {
            UniversalImageLoaderTool.initImageLoader(configuration.context);
        } else {
            TxImageLoader.getInstance().init(configuration.imageLoaderProvider);
        }

        //xUtils db
        x.Ext.init((Application) configuration.context);
        x.Ext.setDebug(configuration.mode);

        //OkHttpUtils
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(TxConstants.FILE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TxConstants.FILE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new LoggerInterceptor(TAG))
                .build();
        OkHttpUtils.initClient(client);
    }

    /**
     * 缓存, 需要申请权限
     */
    private void addCacheInterceptor(OkHttpClient.Builder okHttpBuilder) {
        String cacheDir;
        if (Environment.getExternalStorageDirectory() != null) {
            cacheDir = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + File.separator + mConfiguration.cachePath +
                    File.separator + mConfiguration.context.getPackageName();
        } else {
            cacheDir = mConfiguration.context.getApplicationContext().getCacheDir().getAbsolutePath();
        }

        File cacheFile = new File(cacheDir, "");
        if (!cacheFile.exists()) {
            boolean b = cacheFile.mkdir();
        }
        TxLog.e(cacheFile.getAbsolutePath());

        // 50M 缓存大小
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetUtils.isConnected(mConfiguration.context)) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (NetUtils.isConnected(getContext())) {
                    Response.Builder builder = response.newBuilder();
                    if (mConfiguration.isCacheFromHeader) {
                        //读接口上的@Headers里的配置
                        builder.header("Cache-Control", "public, max-age=" + request.cacheControl().toString());
                    } else {
                        //读缓存时间配置
                        builder.header("Cache-Control", "public, max-age=" + mConfiguration.cacheTime);
                    }
                    return builder.removeHeader("Pragma").build();
                } else {
                    //无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    return response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
            }
        };

        //设置缓存
        okHttpBuilder.addNetworkInterceptor(cacheInterceptor);
        okHttpBuilder.addInterceptor(cacheInterceptor);
        okHttpBuilder.cache(cache);

        //错误重连
        okHttpBuilder.retryOnConnectionFailure(true);
    }


    public Context getContext() {
        return mConfiguration.context;
    }

    public TxConfiguration getConfiguration() {
        return mConfiguration;
    }

    public Platform getPlatform() {
        return mPlatform;
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    /**
     * xUtils数据库
     */
    public DbManager getDb() {
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbName(TxConstants.DB_NAME)
                .setDbVersion(AppUtils.getVersionCode(getContext()))
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        // 开启WAL, 对写入加速提升巨大
                        db.getDatabase().enableWriteAheadLogging();
                    }
                })
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        // 数据库更新, 默认删除数据库
                        try {
                            db.dropDb();
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }
                });
        return x.getDb(daoConfig);
    }
}
