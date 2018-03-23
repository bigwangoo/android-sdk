package com.tianxiabuyi.txutils.network.model.result;

import com.tianxiabuyi.txutils.network.model.HttpResult;

/**
 * Created by xjh1994 on 2016/8/18.
 * token
 */
public class TokenResult extends HttpResult {
    /**
     * token : 9c1353c064d7144bf46f1a006b946676
     * expiresIn : 3600
     */

    private String token;
    private int expiresIn;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}
