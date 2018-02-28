package com.wangyd.module;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.tianxiabuyi.txutils.TxConfiguration;
import com.tianxiabuyi.txutils.TxUtils;
import com.tianxiabuyi.txutils.imageloader.glide.GlideImageLoaderProvider;
import com.wangyd.module.activity.LoginActivity;

/**
 * Create on 2018/2/8 15:16
 *
 * @author WangYaoDong
 */
public class CustomApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        initTxUtils();
    }

    public void initTxUtils() {
        TxConfiguration configuration = new TxConfiguration.Builder(this)
                .mode(Constant.MODE_DEBUG)
                .baseUrl(Constant.BASE_URL)
                .appType(Constant.APP_TYPE)
                .hospitalId(Constant.HOSPITAL)
                .loginClass(LoginActivity.class)
                .imageLoader(new GlideImageLoaderProvider())
                .colorPrimary(R.color.colorPrimary)
                .isCacheOn(true).build();
        TxUtils.getInstance().init(configuration);
    }
}
