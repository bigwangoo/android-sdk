package com.tianxiabuyi.txutils.network.callback;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.tianxiabuyi.txutils.TxUtils;
import com.tianxiabuyi.txutils.config.TxConstants;
import com.tianxiabuyi.txutils.network.exception.TxException;
import com.tianxiabuyi.txutils.util.ActivityUtils;
import com.tianxiabuyi.txutils.util.NetUtils;
import com.tianxiabuyi.txutils.util.ToastUtils;

import org.json.JSONException;
import org.xutils.common.util.LogUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by xjh1994 on 2016/8/18.
 * 请求响应处理
 */
public abstract class ResponseCallback<T> extends BaseResponseCallback<T> {

    private static final String TAG = ResponseCallback.class.getSimpleName();

    private boolean mIsShowToast = true;

    public ResponseCallback() {
    }

    /**
     * 显示 toast
     */
    public ResponseCallback(boolean isShowToast) {
        mIsShowToast = isShowToast;
    }

    /**
     * 显示 dialog
     */
    public ResponseCallback(Context context) {
        super(context);
    }

    /**
     * 显示 dialog + toast
     */
    public ResponseCallback(Context context, boolean isShowToast) {
        super(context);
        mIsShowToast = isShowToast;
    }

    /**
     * 显示 自定义dialog + toast
     */
    public ResponseCallback(ProgressDialog dialog, boolean isShowToast) {
        super(dialog);
        mIsShowToast = isShowToast;
    }

    // onSuccess();
    // onError();

    @Override
    public void onFailed(TxException e) {
        LogUtil.e(TAG + "-------------" + e.getMessage());

        String message;
        if (e.getResultCode() == 500 || e.getResultCode() == 504) {
            message = "服务器出错，请稍后再试";
            onError(new TxException(message));
        } else if (e.getResultCode() == TxException.TOKEN_ERR_T
                || e.getResultCode() == TxException.ERROR_TOKEN_EXPIRED
                || e.getResultCode() == TxException.ERROR_TOKEN_FAILURE) {
            message = "登录过期，请重新登录";
            onReLogin();
        } else {
            message = e.getDetailMessage();
            onError(e);
        }

        if (mIsShowToast) {
            ToastUtils.show(message);
        }
    }

    @Override
    public void onServerError(Response<T> response) {
        String message;
        if (response.code() == 404) {
            message = "接口异常";
        } else if (response.code() == 504) {
            message = "请检查网络连接";
        } else {
            message = response.message();
        }
        onFailed(new TxException(message));
    }

    @Override
    public void onRequestFailure(Call<T> call, Throwable t) {
        LogUtil.e(TAG + "-------------" + t.toString());

        String message;
        if (call.isCanceled()) {
            // 取消请求
//            onError(new TxException("请求取消"));
            return;
        } else if (!NetUtils.isConnected(TxUtils.getInstance().getContext())) {
            // 网络问题
            message = "请检查网络连接";
        } else if (t instanceof ConnectException || t instanceof UnknownHostException) {
            // Connect
            message = "服务器连接失败，请稍后重试";
        } else if (t instanceof SocketTimeoutException) {
            // 超时
            message = "网络连接超时，请稍后重试";
        } else if (t instanceof HttpException) {
            // HTTP
            message = "网络连接错误";
        } else if (t instanceof JsonIOException || t instanceof JsonParseException
                || t instanceof ParseException || t instanceof JSONException) {
            // 数据解析异常
            message = "数据解析异常，请稍后再试";
        } else {
            // 其他
            message = "服务出错啦，请稍后再试";
        }

        if (mIsShowToast) {
            ToastUtils.show(message);
        }
        onError(new TxException(message));
    }

    @Override
    public void onReLogin() {
        Class loginClass = TxUtils.getInstance().getConfiguration().getLoginClass();
        if (loginClass != null) {
            Context context = TxUtils.getInstance().getContext();
            Intent intent = new Intent(context, loginClass);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(TxConstants.EXTRA_TOKEN_EXPIRES, true);
            context.startActivity(intent);
            if (ActivityUtils.getInstance().size() > 1) {
                ActivityUtils.getInstance().finishActivity();
            }
        }
    }

    public void setIsShowToast(boolean isShowToast) {
        this.mIsShowToast = isShowToast;
    }
}
