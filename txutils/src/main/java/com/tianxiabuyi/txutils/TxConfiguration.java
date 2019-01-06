package com.tianxiabuyi.txutils;

import android.content.Context;
import android.text.TextUtils;

import com.tianxiabuyi.txutils.app.TxConstants;
import com.tianxiabuyi.txutils.imageloader.BaseImageLoaderProvider;

import okhttp3.OkHttpClient;

/**
 * @author xjh1994
 * @date 2016/8/17
 * @description 全局配置
 */
public class TxConfiguration {

    public static final boolean DEBUG = true;
    public static final boolean RELEASE = false;

    private final boolean mode;
    private final Context context;
    private final String baseUrl;                               // API地址 （必传）
    private final Class loginClass;                             // 登录界面
    private final BaseImageLoaderProvider imageLoaderProvider;  // 图片加载
    private final OkHttpClient.Builder okHttpBuilder;           // 自定义okHttpBuilder
    private final boolean isCacheOn;                            // 开启缓存              默认关闭
    private final boolean isCacheFromHeader;                    // 缓存方式 header       默认开启
    private final boolean isCacheNetworkOff;                    // 无网络时是否使用缓存  默认关闭
    private final int cacheTime;                                // 缓存时间，单位是秒

    public TxConfiguration(final Builder builder) {
        this.mode = builder.mode;
        this.context = builder.context;
        this.baseUrl = builder.baseUrl;
        this.loginClass = builder.loginClass;
        this.imageLoaderProvider = builder.imageLoaderProvider;
        this.okHttpBuilder = builder.okHttpBuilder;
        this.isCacheOn = builder.isCacheOn;
        this.isCacheFromHeader = builder.isCacheFromHeader;
        this.isCacheNetworkOff = builder.isCacheNetworkOff;
        this.cacheTime = builder.cacheTime;
    }

    public boolean isDebug() {
        return mode;
    }

    public Context getContext() {
        return context;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public Class getLoginClass() {
        return loginClass;
    }

    public BaseImageLoaderProvider getImageLoaderProvider() {
        return imageLoaderProvider;
    }

    public OkHttpClient.Builder getOkHttpBuilder() {
        return okHttpBuilder;
    }

    public boolean isCacheOn() {
        return isCacheOn;
    }

    public boolean isCacheFromHeader() {
        return isCacheFromHeader;
    }

    public boolean isCacheNetworkOff() {
        return isCacheNetworkOff;
    }

    public int getCacheTime() {
        return cacheTime;
    }

    /**
     * builder
     */
    public static class Builder {
        private boolean mode = RELEASE;
        private Context context;
        private String baseUrl;
        private Class loginClass;
        private BaseImageLoaderProvider imageLoaderProvider;
        private OkHttpClient.Builder okHttpBuilder;
        private boolean isCacheOn = false;
        private boolean isCacheFromHeader = true;
        private boolean isCacheNetworkOff = false;
        private int cacheTime = 0;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder mode(boolean mode) {
            this.mode = mode;
            return this;
        }

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder loginClass(Class loginClass) {
            this.loginClass = loginClass;
            return this;
        }

        public Builder imageLoader(BaseImageLoaderProvider imageLoaderProvider) {
            this.imageLoaderProvider = imageLoaderProvider;
            return this;
        }

        public Builder okHttpBuilder(OkHttpClient.Builder okHttpBuilder) {
            this.okHttpBuilder = okHttpBuilder;
            return this;
        }

        public Builder isCacheOn(boolean isCacheOn) {
            this.isCacheOn = isCacheOn;
            return this;
        }

        public Builder isCacheFromHeader(boolean isCacheFromHeader) {
            this.isCacheFromHeader = isCacheFromHeader;
            return this;
        }

        public Builder isCacheNetworkOff(boolean isCacheNetworkOff) {
            this.isCacheNetworkOff = isCacheNetworkOff;
            return this;
        }

        public Builder cacheTime(int cacheTime) {
            this.cacheTime = cacheTime;
            return this;
        }

        public TxConfiguration build() {
            initEmptyFieldsWithDefaultValues();
            return new TxConfiguration(this);
        }

        private void initEmptyFieldsWithDefaultValues() {
            if (TextUtils.isEmpty(baseUrl)) {
                baseUrl = TxConstants.BASE_URL;
            }

//            if (TextUtils.isEmpty(baseUrl)) {
//                throw new NullPointerException("baseUrl cannot be null");
//            }
        }
    }
}
