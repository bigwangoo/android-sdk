package com.tianxiabuyi.txutils.network;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;

/**
 * Created by xjh1994 on 2016/8/29.
 */

public class TxCallAdapter<T> implements CallAdapter<TxCall<?>, T> {

    private final Type responseType;

    public TxCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public Object adapt(Call call) {
        return new TxCall<>(call);
    }
}
