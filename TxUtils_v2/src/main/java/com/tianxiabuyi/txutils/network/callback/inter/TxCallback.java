package com.tianxiabuyi.txutils.network.callback.inter;

import com.tianxiabuyi.txutils.network.exception.TxException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by xjh1994 on 2016/8/29.
 * 回调
 */
public interface TxCallback<T> {

    void onStart(Call<T> call);

    void onFinish();

    void onSuccess(T result);

    void onFailed(TxException e);

    void onError(TxException e);

    void onServerError(Response<T> response);

    void onRequestFailure(Call<T> call, Throwable t);

    void onReLogin();
}
