package com.demo.common.network.service;

import android.support.annotation.NonNull;

import com.tianxiabuyi.txbysdk.TxSDK;
import com.demo.common.network.TxCallAdapterFactory;

import java.util.HashMap;
import java.util.Objects;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wangyd on 2017/8/13.
 */
public class TxServiceManager {

    private static HashMap<String, Objects> mServiceMap = new HashMap<>();

    public static <T> T createService(Class<T> t) {
        return createService(t, "");
    }

    public static <T> T createService(Class<T> t, String url) {
        T service = (T) mServiceMap.get(t.getName());
        if (service == null) {
            OkHttpClient okHttpClient = TxSDK.getOkHttpClient();
            Retrofit retrofit = getRetrofit(okHttpClient, url);
            service = retrofit.create(t);
        }
        return service;
    }

    @NonNull
    private static Retrofit getRetrofit(OkHttpClient okHttpClient, String url) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(TxCallAdapterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }
}
