package com.tianxiabuyi.txutils.network.Interceptor;

import android.content.Context;
import android.text.TextUtils;

import com.tianxiabuyi.txutils.TxConfiguration;
import com.tianxiabuyi.txutils.TxUserManager;
import com.tianxiabuyi.txutils.TxUtils;
import com.tianxiabuyi.txutils.network.model.HttpResult;
import com.tianxiabuyi.txutils.util.GsonUtils;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by xjh1994 on 2016/7/15.
 * 拦截器
 */
public class TxInterceptor implements Interceptor {

    /**
     * android 传1
     */
    protected String type = "1";
    /**
     * 刷新token地址
     */
    protected String tokenRefreshUrl;

    private Context context;

    private TxConfiguration configuration;

    public TxInterceptor(TxConfiguration configuration) {
        this.context = configuration.context;
        this.configuration = configuration;
        setDefaultParams();
    }

    /**
     * 设置默认参数
     */
    private void setDefaultParams() {
        tokenRefreshUrl = configuration.tokenRefreshUrl;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request newRequest = getNewRequest(chain.request());
        final Response originalResponse = chain.proceed(newRequest);
        final ResponseBody originalResponseBody = originalResponse.body();
        String originalResponseString = originalResponseBody.string();

        // token过期刷新处理
        HttpResult tempResult = GsonUtils.fromJson(originalResponseString, HttpResult.class);
        if (tempResult != null && tempResult.isTokenExpiredNew()) {
        }

        return originalResponse.newBuilder()
                .body(ResponseBody.create(originalResponseBody.contentType(), originalResponseString))
                .build();
    }

    /**
     * 获取新的Request
     */
    private Request getNewRequest(Request originalRequest) {
        HttpUrl httpUrl = originalRequest.url();
        List<String> encodedPathSegments = httpUrl.encodedPathSegments();
        boolean addType = true, addToken = true;
        HttpUrl.Builder newBuilder = httpUrl.newBuilder();
        for (int i = 0; i < encodedPathSegments.size(); i++) {
            String pathSegment = encodedPathSegments.get(i);
            if (TextUtils.equals(pathSegment, "removeType")) {
                newBuilder.setPathSegment(i, "");
                addType = false;
            }
            if (TextUtils.equals(pathSegment, "removeToken")) {
                newBuilder.setPathSegment(i, "");
                addToken = false;
            }
        }

        if (addType) {
            newBuilder.addPathSegment("1");
        }
        if (addToken) {
            String token = TxUserManager.getInstance().getToken();
            newBuilder.addPathSegment(token);
        }

        return originalRequest.newBuilder().url(newBuilder.build()).build();
    }


}
