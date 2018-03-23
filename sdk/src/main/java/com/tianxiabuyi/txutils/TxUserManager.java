package com.tianxiabuyi.txutils;

import android.content.Context;
import android.text.TextUtils;

import com.tianxiabuyi.txutils.config.TxConstants;
import com.tianxiabuyi.txutils.util.SPUtils;

/**
 * 用户管理
 *
 * @author xjh1994
 * @date 2016/8/18
 */
public class TxUserManager<T> {

    public static final String TAG = TxUserManager.class.getSimpleName();

    private String token;

    private static volatile TxUserManager mInstance;

    private TxUserManager() {

    }

    public static TxUserManager getInstance() {
        if (mInstance == null) {
            synchronized (TxUserManager.class) {
                if (mInstance == null) {
                    mInstance = new TxUserManager();
                }
            }
        }
        return mInstance;
    }


    /////////////////////////////////////// token

    /**
     * 保存token
     */
    public void setToken(Context context, String token) {
        this.token = token;
        SPUtils.put(context, TxConstants.KEY_TOKEN, token);
    }

    /**
     * 获取token
     */
    public String getToken() {
        if (TextUtils.isEmpty(token)) {
            token = (String) SPUtils.get(TxUtils.getInstance().getContext(), TxConstants.KEY_TOKEN, "");
        }
        return token;
    }
}
