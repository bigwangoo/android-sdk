package com.tianxiabuyi.txutils.network.callback.common;

import android.content.Context;

import com.tianxiabuyi.txutils.network.exception.TxException;

/**
 * Created by xjh1994 on 2016/8/30.
 * 更新
 */
public abstract class UpdateCallback<T> {

    private Context mContext;

    public UpdateCallback() {

    }

    public UpdateCallback(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    public abstract void onSuccess(T result);

    public abstract void onError(TxException e);
}
