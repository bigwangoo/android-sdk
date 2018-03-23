package com.tianxiabuyi.txutils;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.tianxiabuyi.txutils.network.Interceptor.TxEncryptInterceptor;
import com.tianxiabuyi.txutils.network.Interceptor.log.TxEncryptLoggerInterceptor;
import com.tianxiabuyi.txutils.network.Interceptor.log.TxLoggerInterceptor;
import com.tianxiabuyi.txutils.network.TxCallAdapterFactory;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.tianxiabuyi.txutils.config.TxConstants.TIMEOUT;

/**
 * @author xjh1994
 * @date 2016/8/29
 * @description 创建service
 */
public class TxServiceManager {

    private static HashMap<String, Object> mServiceMap = new HashMap<String, Object>();

    /**
     * 创建Retrofit Service
     */
    public static <T> T createService(Class<T> t) {
        return createService(t, "");
    }

    /**
     * 创建Retrofit Service，自定义接口地址url
     */
    @SuppressWarnings("unchecked")
    public static <T> T createService(Class<T> t, String url) {
        T service = (T) mServiceMap.get(t.getName());
        if (service == null) {
            OkHttpClient okHttpClient = TxUtils.getInstance().getOkHttpClient();
            Retrofit retrofit = getRetrofit(okHttpClient, url);
            service = retrofit.create(t);
            mServiceMap.put(t.getName(), service);
        }
        return service;
    }

    /**
     * 加密版
     * 创建Retrofit Service
     */
    public static <T> T createServiceEncrypt(Class<T> t) {
        return createServiceEncrypt(t, "");
    }

    /**
     * 加密版
     * 创建Retrofit Service，自定义接口地址url
     */
    public static <T> T createServiceEncrypt(Class<T> t, String url) {
        T service;

        TxConfiguration configuration = TxUtils.getInstance().getConfiguration();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new TxEncryptInterceptor(configuration))
                .addInterceptor(new TxEncryptLoggerInterceptor("tx_encrypt", configuration.mode))
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = getRetrofit(okHttpClient, url);
        service = retrofit.create(t);

        return service;
    }

    /**
     * 创建Retrofit Service
     * 自定义OkHttpClient
     */
    public static <T> T createService(Class<T> t, OkHttpClient client) {
        return createService(t, client, "");
    }

    /**
     * 创建Retrofit Service
     * 自定义OkHttpClient、接口地址url
     */
    public static <T> T createService(Class<T> t, OkHttpClient client, String url) {
        T service;

        if (client == null) {
            throw new NullPointerException("client cannot be null");
        }

        TxConfiguration configuration = TxUtils.getInstance().getConfiguration();
        OkHttpClient okHttpClient = client.newBuilder()
                .addInterceptor(new TxLoggerInterceptor("tc_common", configuration.isDebug()))
                .build();

        Retrofit retrofit = getRetrofit(okHttpClient, url);
        service = retrofit.create(t);

        return service;
    }

    @NonNull
    private static Retrofit getRetrofit(OkHttpClient okHttpClient, String url) {
        return new Retrofit.Builder()
                .baseUrl(TextUtils.isEmpty(url) ? TxUtils.getInstance().getConfiguration().getBaseUrl() : url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(TxCallAdapterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    /**
     * 取消请求
     * 在onDestroy时调用
     *
     * @param tag tag
     */
    public static void cancelTag(Object tag) {
        OkHttpClient mOkHttpClient = TxUtils.getInstance().getOkHttpClient();

        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }
}
