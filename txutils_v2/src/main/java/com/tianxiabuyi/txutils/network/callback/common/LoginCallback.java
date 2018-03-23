package com.tianxiabuyi.txutils.network.callback.common;

import android.content.Context;

import com.tianxiabuyi.txutils.network.exception.TxException;
import com.tianxiabuyi.txutils.network.model.TxUser;

/**
 * Created by Administrator on 2016/8/18.
 * 登录
 */
public abstract class LoginCallback<T extends TxUser> {

    private Context mContext;

    public LoginCallback() {

    }

    public LoginCallback(Context context) {
        mContext = context;
    }

    public abstract void onSuccess(T user);

    public abstract void onError(TxException e);

    public Context getContext() {
        return mContext;
    }
}
