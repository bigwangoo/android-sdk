package com.tianxiabuyi.txutils.network.callback;

import android.app.ProgressDialog;
import android.content.Context;

import com.tianxiabuyi.txutils.network.model.HttpResult;

import java.util.List;

/**
 * Created by xjh1994 on 16/12/7.
 * 集合形式请求响应处理
 */
public abstract class ListResponseCallback<T> extends ResponseCallback<HttpResult<T>> {

    public ListResponseCallback() {
    }

    public ListResponseCallback(boolean isShowToast) {
        super(isShowToast);
    }

    public ListResponseCallback(Context context) {
        super(context);
    }

    public ListResponseCallback(Context context, boolean isShowToast) {
        super(context, isShowToast);
    }

    public ListResponseCallback(ProgressDialog dialog, boolean isShowToast) {
        super(dialog, isShowToast);
    }

    @Override
    public void onSuccess(HttpResult<T> result) {
        T data = result.getData();
        if (data instanceof List) {
            if (((List) data).size() > 0) {
                onResponse(data);
            } else {
                onEmpty();
            }
        } else {
            onResponse(data);
        }
    }

    public abstract void onResponse(T data);

    public abstract void onEmpty();
}
