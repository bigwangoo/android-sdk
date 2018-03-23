package com.tianxiabuyi.txutils.network.exception;

import android.text.TextUtils;

import com.tianxiabuyi.txutils.network.model.HttpResult;

/**
 * Created by xjh1994 on 2016/8/18.
 * 通用异常
 */
public class TxException extends Exception {

    // 环信用户已存在
    public static final int EASE_USER_EXIST = 400;
    // 缺少参数
    public static final int ERROR_MISS_PARAM = 20001;
    // token过期
    public static final int TOKEN_ERR_O = 20002;
    // token未认证
    public static final int TOKEN_ERR_T = 20004;
    // 用户名或密码错误
    public static final int ERROR_USERNAME_PASS = 30022;
    // 用户已存在
    public static final int ERROR_USER_EXIST = 30029;

    /////////////////////////// 新后台

    // not found
    public static final int ERROR_NOT_FOUND = 1;
    // 参数验证失败,参数不正确
    public static final int ERROR_PARAM = 4000;
    // 访问验证失败,格式不正确
    public static final int ERROR_FORM = 4001;
    // token过期
    public static final int ERROR_TOKEN_EXPIRED = 4002;
    // token失效 其他设备登录
    public static final int ERROR_TOKEN_FAILURE = 4003;
    // 暂无数据
    public static final int ERROR_EMPTY_DATA = 4500;
    // 无权限
    public static final int ERROR_PERMISSION_REFUSE = 4501;
    // 系统开小差，请稍后重试
    public static final int ERROR_SERVER_INTERNAL = 5000;

    private int resultCode;
    private String detailMessage = "";

    public TxException(HttpResult result) {
        this(getTxExceptionMessage(result.getErrcode(), result.getErrmsg()));
        this.resultCode = result.getErrcode();
    }

    public TxException(int resultCode) {
        this(getTxExceptionMessage(resultCode, ""));
        this.resultCode = resultCode;
    }

    public TxException(String detailMessage) {
        super(detailMessage);
        this.detailMessage = detailMessage;
    }

    public TxException(int resultCode, String detailMessage) {
        this.resultCode = resultCode;
        this.detailMessage = detailMessage;
    }

    public int getResultCode() {
        return resultCode;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户
     *
     * @param code errcode
     * @param msg  errmsg
     * @return String
     */
    private static String getTxExceptionMessage(int code, String msg) {
        String message;
        switch (code) {
            case ERROR_MISS_PARAM:
                message = "请求缺少参数";
                break;
            case TOKEN_ERR_O:
                message = "登录已过期，请重新登录";
                break;
            case TOKEN_ERR_T:
                message = "登录已过期，请重新登录";
                break;
            case ERROR_USERNAME_PASS:
                message = "用户名或密码错误";
                break;
            case ERROR_USER_EXIST:
                message = "用户名已存在";
                break;
            case EASE_USER_EXIST:
                message = "环信用户已存在";
                break;
            default:
                message = TextUtils.isEmpty(msg) ? "未知错误" : msg;
        }
        return message;
    }

}
