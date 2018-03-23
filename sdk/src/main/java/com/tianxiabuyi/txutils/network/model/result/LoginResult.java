package com.tianxiabuyi.txutils.network.model.result;

import com.tianxiabuyi.txutils.network.model.HttpResult;

/**
 * Create on 2018/1/8 20:30
 *
 * @author Wang YaoDong.
 * @version 1.0.0
 * @description:
 */
public class LoginResult<T> extends HttpResult<T> {

    private String token;
    private long expires;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpires() {
        return expires;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }
}
