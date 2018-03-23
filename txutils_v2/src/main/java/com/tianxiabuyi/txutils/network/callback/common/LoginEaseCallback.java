package com.tianxiabuyi.txutils.network.callback.common;

import android.content.Context;

import com.tianxiabuyi.txutils.network.exception.TxException;
import com.tianxiabuyi.txutils.network.model.bean.LoginBean;

/**
 * Created by ASUS on 2016/12/30.
 * 用来处理环信登录成功后再保存用户信息和token的场景
 * LoginCallback只把 TxUser 传给了回调方法, 这里直接把 LoginResult 传出去
 */
public abstract class LoginEaseCallback {

    private Context mContext;

    public LoginEaseCallback() {

    }

    public LoginEaseCallback(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    public abstract void onSuccess(LoginBean result);

    public abstract void onError(TxException e);
}
