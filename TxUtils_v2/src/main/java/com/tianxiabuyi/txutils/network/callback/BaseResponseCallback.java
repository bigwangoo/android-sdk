package com.tianxiabuyi.txutils.network.callback;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.tianxiabuyi.txutils.R;
import com.tianxiabuyi.txutils.TxUtils;
import com.tianxiabuyi.txutils.network.callback.inter.TxCallback;
import com.tianxiabuyi.txutils.network.exception.TxException;
import com.tianxiabuyi.txutils.network.model.HttpResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by xjh1994 on 2016/8/18.
 * 统一回调
 */
public abstract class BaseResponseCallback<T> implements Callback<T>, TxCallback<T> {

    protected Context mContext;

    protected ProgressDialog mDialog;

    protected BaseResponseCallback() {

    }

    /**
     * 显示dialog
     */
    protected BaseResponseCallback(Context context) {
        if (context != null) {
            mContext = context;
            mDialog = new ProgressDialog(context);
        }
    }

    /**
     * 自定义dialog
     */
    protected BaseResponseCallback(ProgressDialog dialog) {
        mDialog = dialog;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response == null) {
            onFailed(new TxException(TxUtils.getInstance().getContext().getString(R.string.tx_error_reading_cache)));
            return;
        }

        if (response.isSuccessful()) {
            T result = response.body();
            if (result instanceof HttpResult) {
                // 继承HttpResult
                HttpResult httpResult = (HttpResult) result;
                if (httpResult.isSuccess()) {
                    onSuccess(result);
                } else {
                    onFailed(new TxException(httpResult));
                }
            } else {
                // 老接口、其他自定义数据
                onSuccess(result);
            }
        } else {
            onServerError(response);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onRequestFailure(call, t);
    }

    ////////////////////////

    @Override
    public void onStart(final Call<T> call) {
        if (mDialog != null) {
            initDialog(call);
            mDialog.show();
        }
    }

    @Override
    public void onFinish() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    private void initDialog(final Call<T> call) {
        mDialog.setMessage(TxUtils.getInstance().getContext().getString(R.string.tx_loading));
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                call.cancel();
            }
        });
    }
}
