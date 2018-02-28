package com.tianxiabuyi.txutils.network.Interceptor;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.tianxiabuyi.txutils.TxConfiguration;
import com.tianxiabuyi.txutils.TxServiceManager;
import com.tianxiabuyi.txutils.TxUserManager;
import com.tianxiabuyi.txutils.config.TxConstants;
import com.tianxiabuyi.txutils.network.model.HttpResult;
import com.tianxiabuyi.txutils.network.model.result.TokenResult;
import com.tianxiabuyi.txutils.network.service.TxUserService;
import com.tianxiabuyi.txutils.util.EncryptUtils;
import com.tianxiabuyi.txutils.util.GsonUtils;
import com.tianxiabuyi.txutils.util.SPUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by xjh1994 on 2016/7/15.
 * 加密解密、刷新token拦截器
 */
public class TxEncryptInterceptor implements Interceptor {

    //已有token在刷新时，请求重试的次数
    private static final int RETRY_TIMES = 10;
    private static boolean isRefreshingToken = false;
    private static final String KEY_JSON = "json";

    private Context context;
    private TxConfiguration configuration;

    private String app_type;
    private String hospital;
    private String token;
    private String tokenRefreshUrl;   //刷新token地址

    public TxEncryptInterceptor(TxConfiguration configuration) {
        this.context = configuration.context;
        this.configuration = configuration;
        setDefaultParams();
    }

    /**
     * 设置默认参数
     */
    private void setDefaultParams() {
        app_type = configuration.appType;
        hospital = configuration.hospitalId;
        token = TxUserManager.getInstance().getToken();
        tokenRefreshUrl = configuration.tokenRefreshUrl;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        setDefaultParams();

        //传入默认参数
        Request originalRequest = chain.request();
        HttpUrl httpUrl = originalRequest.url();
        //存放原url中的参数
        Map<String, String> params = new HashMap<>();
        Set<String> parameterNames = httpUrl.queryParameterNames();
        HttpUrl.Builder builder = httpUrl.newBuilder();
        if (parameterNames.size() > 0) {
            for (String name : parameterNames) {
                params.put(name, httpUrl.queryParameter(name));
                builder.removeAllQueryParameters(name);
            }
        }
        if (TextUtils.isEmpty(params.get(TxConstants.KEY_APP_TYPE))) {
            params.put(TxConstants.KEY_APP_TYPE, app_type);
        }
        if (TextUtils.isEmpty(params.get(TxConstants.KEY_HOSPITAL))) {
            params.put(TxConstants.KEY_HOSPITAL, hospital);
        }
        if (TextUtils.isEmpty(params.get(TxConstants.KEY_TOKEN))) {
            params.put(TxConstants.KEY_TOKEN, token);
        }
        String json = GsonUtils.toJson(params);
        String encryptJson = EncryptUtils.encryptStr(json);

        //加密请求数据
        HttpUrl newHttpUrl = builder.setQueryParameter(KEY_JSON, encryptJson).build();
        Request newRequest = originalRequest.newBuilder().url(newHttpUrl).build();

        //发送加密后的请求
        final Response originalResponse = chain.proceed(newRequest);

        final ResponseBody originalResponseBody = originalResponse.body();
        if (!originalResponse.isSuccessful()) {
            Response newResponse = originalResponse.newBuilder()
                    .body(ResponseBody.create(originalResponseBody.contentType(), originalResponseBody.string()))
                    .build();
            return newResponse;
        }

        //解密返回数据
        String originalResponseString = originalResponseBody.string();
        String decryptedStr = "";
        try {
            decryptedStr = EncryptUtils.decryptStr(originalResponseString);
        } catch (IllegalStateException | NullPointerException e) {
            e.printStackTrace();
        }

        //token过期处理
        HttpResult tempResult = GsonUtils.fromJson(decryptedStr, HttpResult.class);
        if (tempResult != null && tempResult.isTokenExpired()) {
            if (!isRefreshingToken) {
                //刷新token
                isRefreshingToken = true;
                TxUserService txUserService = TxServiceManager.createService(TxUserService.class);
                retrofit2.Response<TokenResult> tokenResponse = txUserService.refreshToken(token).execute();
                if (tokenResponse.isSuccessful()) {
                    String token = tokenResponse.body().getToken();
                    if (!TextUtils.isEmpty(token)) {
                        saveToken(token);
                        isRefreshingToken = false;
                        return getTokenResponse(chain, originalRequest, httpUrl, json, token);
                    }
                }
            } else {
                Response newResponse = retry(json, httpUrl, chain, newRequest);
                if (newResponse != null) {
                    return newResponse;
                } else {
                    throw new IOException("getting refreshed token failed");
                }
            }
        }

        //解密返回数据
        Response newResponse = originalResponse.newBuilder()
                .body(ResponseBody.create(originalResponseBody.contentType(), decryptedStr))
                .build();

        return newResponse;
    }

    private int retryCount = 0;

    /**
     * 重试
     */
    @Nullable
    private Response retry(String json, HttpUrl httpUrl, Chain chain, Request newRequest) throws IOException {
        Map<String, String> paramsMap = new LinkedHashMap<>();
        paramsMap = GsonUtils.fromJson(json, paramsMap.getClass());
        paramsMap.put(TxConstants.KEY_TOKEN, (String) SPUtils.get(context, TxConstants.KEY_TOKEN, ""));
        HttpUrl.Builder builder = httpUrl.newBuilder();
        String newJson = GsonUtils.toJson(paramsMap);
        String newEncryptJson = EncryptUtils.encryptStr(newJson);

        //加密请求数据
        HttpUrl newHttpUrl = builder.setQueryParameter(KEY_JSON, newEncryptJson).build();
        Request tempNewRequest = newRequest.newBuilder().url(newHttpUrl).build();
        Response tempOriginalResponse = chain.proceed(tempNewRequest);
        retryCount++;

        final ResponseBody tempOriginalResponseBody = tempOriginalResponse.body();
        if (!tempOriginalResponse.isSuccessful()) {
            Response newResponse = tempOriginalResponse.newBuilder()
                    .body(ResponseBody.create(tempOriginalResponseBody.contentType(), tempOriginalResponseBody.string()))
                    .build();
            return newResponse;
        }

        //解密返回数据
        String tempOriginalResponseString = tempOriginalResponseBody.string();
        String tempDecryptedStr = "";
        try {
            tempDecryptedStr = EncryptUtils.decryptStr(tempOriginalResponseString);
        } catch (IllegalStateException | NullPointerException e) {
            e.printStackTrace();
        }

        //token过期处理
        HttpResult tempResult = GsonUtils.fromJson(tempDecryptedStr, HttpResult.class);
        if (tempResult != null && tempResult.isTokenExpired()) {
            if (retryCount <= RETRY_TIMES) {
                return retry(newJson, httpUrl, chain, tempNewRequest);
            }
        }

        return null;
    }

    /**
     * 用新Token重新发送请求
     */
    private Response getTokenResponse(Chain chain,
                                      Request originalRequest,
                                      HttpUrl httpUrl,
                                      String json,
                                      String token) throws IOException {
        Map<String, String> params = new LinkedHashMap<>();
        params = GsonUtils.fromJson(json, params.getClass());
        params.put(TxConstants.KEY_TOKEN, token);
        String newJson = GsonUtils.toJson(params);
        String newEncryptJson = EncryptUtils.encryptStr(newJson);

        HttpUrl newTokenHttpUrl = httpUrl.newBuilder().setQueryParameter(KEY_JSON, newEncryptJson).build();
        Request newTokenRequest = originalRequest.newBuilder().url(newTokenHttpUrl).build();

        final Response originalTokenResponse = chain.proceed(newTokenRequest);

        if (originalTokenResponse.isSuccessful()) {
            final ResponseBody originalTokenResponseBody = originalTokenResponse.body();
            String decryptedTokenStr = EncryptUtils.decryptStr(originalTokenResponseBody.string());

            return originalTokenResponse.newBuilder()
                    .body(ResponseBody.create(originalTokenResponseBody.contentType(), decryptedTokenStr))
                    .build();
        }

        return originalTokenResponse;
    }

    private void saveToken(String token) {
        TxUserManager.getInstance().setToken(context, token);
    }
}