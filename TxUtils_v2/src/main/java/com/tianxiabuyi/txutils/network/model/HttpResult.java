package com.tianxiabuyi.txutils.network.model;

import com.tianxiabuyi.txutils.network.exception.TxException;

/**
 * Created by xjh1994 on 2016/7/27.
 * 数据响应格式
 * 非标准格式可以继承此类实现
 */
public class HttpResult<T> extends BaseBean {

    /* 返回成功 */
    private static final int SUCCESS = 0;

    private int errcode = -1;
    private String errmsg;
    private T data;

    public HttpResult() {

    }

    public HttpResult(String result) {

    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /////////////////////

    public boolean isSuccess() {
        return errcode == SUCCESS;
    }

    public boolean isTokenExpired() {
        return errcode == TxException.TOKEN_ERR_O;
    }

    public boolean isTokenExpiredNew() {
        return errcode == TxException.ERROR_TOKEN_EXPIRED
                || errcode == TxException.ERROR_TOKEN_FAILURE;
    }
}
