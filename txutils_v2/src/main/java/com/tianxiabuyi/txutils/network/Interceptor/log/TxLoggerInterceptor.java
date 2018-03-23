package com.tianxiabuyi.txutils.network.Interceptor.log;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * 网络请求打印日志
 */
public class TxLoggerInterceptor implements Interceptor {

    public static final String TAG = "TxUtils";

    private static final String KEY_JSON = "json";

    private String tag;
    private boolean showLog;

    public TxLoggerInterceptor(String tag) {
        this(tag, false);
    }

    public TxLoggerInterceptor(String tag, boolean showLog) {
        if (TextUtils.isEmpty(tag)) {
            tag = TAG;
        }
        this.tag = tag;
        this.showLog = showLog;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (showLog) {
            logForRequest(request);
        }
        Response response = null;
        try {
            response = chain.proceed(request);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        if (showLog) {
            return logForResponse(response);
        }
        return response;
    }

    /**
     * 打印响应
     */
    private Response logForResponse(Response response) throws IOException {
        Log.e(tag, "========response'log=======");
        Response.Builder builder = response.newBuilder();
        Response clone = builder.build();
        Log.e(tag, "mUrl : " + clone.request().url());
        Log.e(tag, "code : " + clone.code());
        Log.e(tag, "protocol : " + clone.protocol());
        if (!TextUtils.isEmpty(clone.message())) {
            Log.e(tag, "message : " + clone.message());
        }

        if (showLog) {
            ResponseBody body = clone.body();
            if (body != null) {
                MediaType mediaType = body.contentType();
                if (mediaType != null) {
                    Log.e(tag, "responseBody's contentType : " + mediaType.toString());
                    if (isText(mediaType)) {
                        String resp = body.string();
                        if (!clone.isSuccessful()) {
                            LogUtil.e(tag, "responseBody's content : " + resp);
                        } else {
                            if (resp.contains("errcode")) {
                                LogUtil.e(tag, "responseBody's content : " + resp);
                            } else {
                                LogUtil.e(tag, "responseBody's content : " + resp);
                            }
                        }
                        body = ResponseBody.create(mediaType, resp);
                        Log.e(tag, "========response'log=======end");

                        return response.newBuilder().body(body).build();
                    } else {
                        Log.e(tag, "responseBody's content : " + " maybe [file part] , too large too print , ignored!");
                    }
                }
            }
        }
        Log.e(tag, "========response'log=======end");

        return response;
    }

    /**
     * 打印请求
     */
    private void logForRequest(Request request) {
        try {
            Log.e(tag, "========request'log=======");
            Log.e(tag, "method : " + request.method());
            Log.e(tag, "mUrl : " + request.url().toString());
            Log.e(tag, "json : " + "json=" + request.url().queryParameter(KEY_JSON));
            Log.e(tag, "tag : " + request.tag());
            //
            Headers headers = request.headers();
            if (headers != null && headers.size() > 0) {
                Log.e(tag, "headers : " + headers.toString());
            }
            //
            RequestBody requestBody = request.body();
            if (requestBody != null) {
                MediaType mediaType = requestBody.contentType();
                if (mediaType != null) {
                    Log.e(tag, "requestBody's contentType : " + mediaType.toString());
                    if (isText(mediaType)) {
                        Log.e(tag, "requestBody's content : " + bodyToString(request));
                    } else {
                        Log.e(tag, "requestBody's content : " + " maybe [file part] , too large too print , ignored!");
                    }
                }
            }
            Log.e(tag, "========request'log=======end");
        } catch (Exception e) {
            // e.printStackTrace();
        }
    }

    /**
     * body是否是文本
     */
    private boolean isText(MediaType mediaType) {
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        if (mediaType.subtype() != null) {
            if (mediaType.subtype().equals("json") ||
                    mediaType.subtype().equals("xml") ||
                    mediaType.subtype().equals("html") ||
                    mediaType.subtype().equals("webviewhtml")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 请求body
     */
    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "something error when show requestBody.";
        }
    }

    /**
     * 打印日志的工具类
     *
     * @author donkor
     */
    public static class LogUtil {
        /**
         * 规定每段显示的长度
         */
        private static int maxLogLength = 2000;

        public static void e(String tag, String msg) {
            int strLength = msg.length();
            int start = 0;
            int end = maxLogLength;
            for (int i = 0; i < 100; i++) {
                //剩下的文本还是大于规定长度则继续重复截取并输出
                if (strLength > end) {
                    Log.e(tag + i, msg.substring(start, end));
                    start = end;
                    end = end + maxLogLength;
                } else {
                    Log.e(tag, msg.substring(start, strLength));
                    break;
                }
            }
        }
    }
}
