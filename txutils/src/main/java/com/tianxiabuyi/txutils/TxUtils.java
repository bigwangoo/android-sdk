package com.tianxiabuyi.txutils;

import android.app.Application;
import android.content.Context;

import com.tianxiabuyi.txutils.app.TxConstants;
import com.tianxiabuyi.txutils.db.x;
import com.tianxiabuyi.txutils.imageloader.TxImageLoader;
import com.tianxiabuyi.txutils.network.intercept.TxCacheInterceptor;
import com.tianxiabuyi.txutils.network.intercept.TxInterceptor;
import com.tianxiabuyi.txutils.network.intercept.TxLoggerInterceptor;
import com.tianxiabuyi.txutils.network.util.Platform;
import com.tianxiabuyi.txutils.network.util.TxLog;
import com.tianxiabuyi.txutils.util.FileUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * @author xjh1994
 * @date 2016/7/15
 */
public class TxUtils {

    public static final String TAG = TxUtils.class.getSimpleName();

    private volatile static TxUtils mInstance;
    private TxConfiguration mConfiguration;
    private Platform mPlatform;
    private OkHttpClient mOkHttpClient;

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
     * 初始化
     */
    public synchronized void init(TxConfiguration config) {
        this.mConfiguration = config;

        // 网络框架
        OkHttpClient.Builder builder = config.getOkHttpBuilder();
        if (builder == null) {
            builder = getDefaultOkHttpBuilder(config);
        }
        if (config.isCacheOn()) {
            // 添加缓存(需要读写权限)
            File cacheFile = FileUtils.getExternalCacheDir(FileUtils.CACHE);
            if (cacheFile == null) {
                return;
            }
            TxLog.e(cacheFile.getAbsolutePath());
            //缓存大小50M
            Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
            TxCacheInterceptor cacheInterceptor = new TxCacheInterceptor(mConfiguration);
            builder.addNetworkInterceptor(cacheInterceptor);
            builder.addInterceptor(cacheInterceptor);
            builder.cache(cache);
            builder.retryOnConnectionFailure(true);
        }
        this.mOkHttpClient = builder.build();

        // OkHttpUtils
        OkHttpUtils.initClient(new OkHttpClient.Builder()
                .connectTimeout(TxConstants.FILE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TxConstants.FILE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new LoggerInterceptor(TAG))
                .build());

        // 图片加载
        if (config.getImageLoaderProvider() != null) {
            TxImageLoader.getInstance().init(config.getImageLoaderProvider());
        }

        // 数据库（xUtils db）
        x.Ext.init((Application) config.getContext());
        x.Ext.setDebug(config.isDebug());
    }

    /**
     * 调试
     */
    public boolean isDebug() {
        return mConfiguration.isDebug();
    }

    public Context getContext() {
        return mConfiguration.getContext();
    }

    public Platform getPlatform() {
        return mPlatform;
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public TxConfiguration getConfiguration() {
        return mConfiguration;
    }

    /**
     * 特定接口需要使用默认配置
     */
    public OkHttpClient getDefaultOkHttp() {
        return getDefaultOkHttpBuilder(mConfiguration).build();
    }

    /**
     * 特定接口需要使用默认配置
     */
    public OkHttpClient.Builder getDefaultOkHttpBuilder(TxConfiguration configuration) {
        return new OkHttpClient.Builder()
                .addInterceptor(new TxInterceptor(configuration))
                .addInterceptor(new TxLoggerInterceptor(TAG, configuration.isDebug()))
                .connectTimeout(TxConstants.READ_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TxConstants.READ_TIMEOUT, TimeUnit.SECONDS);
    }
}
