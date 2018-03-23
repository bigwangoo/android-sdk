package com.tianxiabuyi.txutils.network;

import android.os.Handler;
import android.os.Looper;

import com.tianxiabuyi.txutils.TxUtils;
import com.tianxiabuyi.txutils.network.callback.BaseResponseCallback;
import com.tianxiabuyi.txutils.network.util.Platform;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by xjh1994 on 2016/8/29.
 */

public class TxCall<T> {

    private static final int ON_START = 0;
    private static final int ON_FINISH = 1;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    private final Call<T> call;

    public TxCall(Call<T> call) {
        this.call = call;
    }

    public void enqueue(final BaseResponseCallback<T> callback) {
        //线程切换
        final Platform platform = TxUtils.getInstance().getPlatform();

        platform.execute(new Runnable() {
            @Override
            public void run() {
                callback.onStart(call);
            }
        });

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(final Call<T> call, final Response<T> response) {
                platform.execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onResponse(call, response);
                        callback.onFinish();
                    }
                });
            }

            @Override
            public void onFailure(final Call<T> call, final Throwable t) {
                platform.execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure(call, t);
                        callback.onFinish();
                    }
                });
            }
        });
    }

    public T execute() throws IOException {
        return call.execute().body();
    }

    public void cancel() {
        call.cancel();
    }

    public boolean isCanceled() {
        return call.isCanceled();
    }
}
