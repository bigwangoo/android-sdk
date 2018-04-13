package com.demo.common.network;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

/**
 * Created by wangyd on 2017/8/13.
 */
public class TxCallAdapterFactory extends CallAdapter.Factory {

    public static TxCallAdapterFactory create() {
        return new TxCallAdapterFactory();
    }

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        return null;
    }


}
