package com.bigwangoo.sample;

import android.content.Context;
import android.net.Uri;
import android.support.multidex.MultiDexApplication;

import com.bigwangoo.sample.common.activity.LoginActivity;
import com.github.mzule.activityrouter.router.RouterCallback;
import com.github.mzule.activityrouter.router.RouterCallbackProvider;
import com.github.mzule.activityrouter.router.SimpleRouterCallback;
import com.tianxiabuyi.kit.utils.ToastUtil;
import com.tianxiabuyi.txutils.TxConfiguration;
import com.tianxiabuyi.txutils.TxUtils;
import com.tianxiabuyi.txutils.imageloader.glide.GlideImageLoaderProvider;

/**
 * @author WangYaoDong
 * @date 2017/4/25
 */
public class CusApplication extends MultiDexApplication implements RouterCallbackProvider {

    @Override
    public void onCreate() {
        super.onCreate();
        initTxUtils();
    }

    public void initTxUtils() {
        TxUtils.getInstance().init(new TxConfiguration.Builder(this)
                .mode(Constant.MODE_DEBUG)
                .baseUrl(Constant.BASE_URL)
                .appType(Constant.APP_TYPE)
                .hospitalId(Constant.HOSPITAL)
                .loginClass(LoginActivity.class)
                .imageLoader(new GlideImageLoaderProvider())
                .colorPrimary(R.color.colorPrimary)
                .isCacheOn(true)
                .build());
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public RouterCallback provideRouterCallback() {
        return new SimpleRouterCallback() {
            @Override
            public boolean beforeOpen(Context context, Uri uri) {
                return super.beforeOpen(context, uri);
            }

            @Override
            public void afterOpen(Context context, Uri uri) {
            }

            @Override
            public void error(Context context, Uri uri, Throwable e) {
                super.error(context, uri, e);
            }

            @Override
            public void notFound(Context context, Uri uri) {
                ToastUtil.show("模块正在建设中...");
            }
        };
    }

}
