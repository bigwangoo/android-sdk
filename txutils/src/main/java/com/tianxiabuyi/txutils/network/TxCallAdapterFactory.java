package com.tianxiabuyi.txutils.network;

import android.support.annotation.NonNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

/**
 * @author xjh1994
 * @date 2016/8/29
 */
public class TxCallAdapterFactory extends CallAdapter.Factory {

    public static TxCallAdapterFactory create() {
        return new TxCallAdapterFactory();
    }

    @Override
    public CallAdapter<?, ?> get(@NonNull Type returnType, @NonNull Annotation[] annotations, @NonNull Retrofit retrofit) {
        Class<?> rawType = getRawType(returnType);

        if (rawType == TxCall.class && returnType instanceof ParameterizedType) {
            Type callReturnType = getParameterUpperBound(0, (ParameterizedType) returnType);
            return new TxCallAdapter(callReturnType);
        }
        return null;
    }
}
