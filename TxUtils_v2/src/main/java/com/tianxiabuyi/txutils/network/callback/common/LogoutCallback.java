package com.tianxiabuyi.txutils.network.callback.common;

import android.content.Context;

/**
 * Created by xjh1994 on 2016/9/4.
 * 登出
 */
public abstract class LogoutCallback {

    private Context mContext;

    public LogoutCallback() {

    }

    public LogoutCallback(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    public abstract void onSuccess();
}
