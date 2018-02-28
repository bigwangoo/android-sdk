package com.tianxiabuyi.txutils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.tianxiabuyi.txutils.config.TxConstants;
import com.tianxiabuyi.txutils.imageloader.BaseImageLoaderProvider;

import java.io.File;

import okhttp3.OkHttpClient;

/**
 * Created by xjh1994 on 2016/8/17.
 * 全局配置
 */
public class TxConfiguration {

    private static final String DEFAULT_CACHE_PATH = "TxCache";

    public static final boolean DEBUG = true;
    public static final boolean RELEASE = false;

    public final boolean mode;

    public final Context context;
    public final Class loginClass;
    public final Class personClass;

    public final String baseUrl;
    public final String appType;
    public final String hospitalId;
    public final String token;
    public final String tokenRefreshUrl;

    public final int colorPrimary;
    public final BaseImageLoaderProvider imageLoaderProvider;

    public OkHttpClient.Builder okhttpBuilder;
    public final boolean isCacheOn;
    public final boolean isCacheFromHeader;
    /**
     * 缓存时间，单位是秒
     */
    public final int cacheTime;

    public final String cachePath;

    public TxConfiguration(final Builder builder) {
        this.mode = builder.mode;
        this.context = builder.context;
        this.baseUrl = builder.baseUrl;
        this.appType = builder.appType;
        this.hospitalId = builder.hospitalId;
        this.token = builder.token;
        this.colorPrimary = builder.colorPrimary;
        this.okhttpBuilder = builder.okhttpBuilder;
        this.tokenRefreshUrl = builder.tokenRefreshUrl;
        this.loginClass = builder.loginClass;
        this.personClass = builder.personClass;
        this.imageLoaderProvider = builder.imageLoaderProvider;
        this.isCacheOn = builder.isCacheOn;
        this.cacheTime = builder.cacheTime;
        this.isCacheFromHeader = builder.isCacheFromHeader;
        this.cachePath = builder.cachePath;
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

    public String getAppType() {
        return appType;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public String getToken() {
        return token;
    }

    public String getTokenRefreshUrl() {
        return tokenRefreshUrl;
    }

    public int getColorPrimary() {
        return colorPrimary;
    }

    public OkHttpClient.Builder getOkhttpBuilder() {
        return okhttpBuilder;
    }

    public Class getLoginClass() {
        return loginClass;
    }

    public Class getPersonClass() {
        return personClass;
    }

    public boolean isCacheOn() {
        return isCacheOn;
    }

    public String getCachePath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + cachePath + File.separator;
    }

    public static class Builder {

        private boolean mode = RELEASE;

        private String baseUrl;
        private String appType;
        private String hospitalId;
        private String token;
        private String tokenRefreshUrl;

        private int colorPrimary = -1;
        private BaseImageLoaderProvider imageLoaderProvider;

        private OkHttpClient.Builder okhttpBuilder;

        private Context context;
        private Class loginClass;
        private Class personClass;

        private boolean isCacheOn = false;
        private boolean isCacheFromHeader = true;
        private int cacheTime = 0;

        private String cachePath = DEFAULT_CACHE_PATH;

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

        public Builder appType(String appType) {
            this.appType = appType;
            return this;
        }

        public Builder hospitalId(String hospitalId) {
            this.hospitalId = hospitalId;
            return this;
        }

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder tokenRefreshUrl(String tokenRefreshUrl) {
            this.tokenRefreshUrl = tokenRefreshUrl;
            return this;
        }

        public Builder colorPrimary(int colorPrimary) {
            this.colorPrimary = colorPrimary;
            return this;
        }

        public Builder imageLoader(BaseImageLoaderProvider imageLoaderProvider) {
            this.imageLoaderProvider = imageLoaderProvider;
            return this;
        }

        public Builder okhttpBuilder(OkHttpClient.Builder okhttpBuilder) {
            this.okhttpBuilder = okhttpBuilder;
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

        public Builder cacheTime(int cacheTime) {
            this.cacheTime = cacheTime;
            return this;
        }

        public Builder loginClass(Class loginClass) {
            this.loginClass = loginClass;
            return this;
        }

        public Builder personClass(Class personClass) {
            this.personClass = personClass;
            return this;
        }

        public Builder cachePath(String cachePath) {
            this.cachePath = cachePath;
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
            if (TextUtils.isEmpty(tokenRefreshUrl)) {
                tokenRefreshUrl = TxConstants.TOKEN_REFRESH_URL;
            }
            if (colorPrimary == -1) {
                throw new NullPointerException("colorPrimary cannot be null. " +
                        "Did you forget to set colorPrimary in TxConfiguration?");
            }
        }
    }
}
