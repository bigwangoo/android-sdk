package com.tianxiabuyi.txutils.manager;

import com.tianxiabuyi.txutils.TxUtils;
import com.tianxiabuyi.txutils.app.TxConstants;
import com.tianxiabuyi.txutils.network.model.TxUser;
import com.tianxiabuyi.txutils.util.GsonUtils;
import com.tianxiabuyi.txutils.util.SpUtils;

/**
 * @author xjh1994
 * @date 2016/8/18
 * @description 用户管理
 */
public class TxUserManager {

    private static TxUserManager mInstance;
    private static TxUser currentUser;
    private SpUtils spUtils;

    private TxUserManager() {
        spUtils = new SpUtils(TxUtils.getInstance().getContext());
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

    /**
     * 是否已登录
     */
    public boolean isLogin() {
        return getCurrentUser() != null;
    }

    /**
     * 获取当前用户
     */
    public TxUser getCurrentUser() {
        if (currentUser == null) {
            String json = (String) spUtils.get(TxConstants.KEY_USER, "");
            currentUser = GsonUtils.fromJson(json, TxUser.class);
        }
        return currentUser;
    }

    /**
     * 获取当前用户
     *
     * @param clazz 自定义用户
     */
    public <T extends TxUser> T getCurrentUser(Class<T> clazz) {
        String json = (String) spUtils.get(TxConstants.KEY_USER, "");
        T t = GsonUtils.fromJson(json, clazz);
        currentUser = t;
        return t;
    }

    /**
     * 设置当前用户
     */
    public void saveCurrentUser(TxUser user) {
        TxUserManager.currentUser = user;
        spUtils.put(TxConstants.KEY_USER, GsonUtils.toJson(user));
    }

    /**
     * 设置当前用户 json
     */
    public void saveCurrentUser(String userJson) {
        TxUserManager.currentUser = null;
        spUtils.put(TxConstants.KEY_USER, userJson);
    }
}
