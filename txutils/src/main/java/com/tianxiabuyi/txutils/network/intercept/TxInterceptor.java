package com.tianxiabuyi.txutils.network.intercept;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author xjh1994
 * @date 2016/7/15
 * @description 拦截器
 */
public class TxInterceptor implements Interceptor {

    public TxInterceptor() {
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        // doNothing
        Request originalRequest = chain.request();
        return chain.proceed(originalRequest);
    }
}