package com.tianxiabuyi.txutils.network.Interceptor.mock;

import android.text.TextUtils;
import android.util.Log;

import com.tianxiabuyi.txutils.network.util.MockDataUtils;

import org.xutils.common.util.LogUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Create on 2018/1/8 22:13
 *
 * @author Wang YaoDong.
 * @version 1.0.0
 * @description: 模拟数据
 */
public class TxMockInterceptor implements Interceptor {

    public static final String TAG = TxMockInterceptor.class.getSimpleName();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response;
        String path = chain.request().url().uri().getPath();
        LogUtil.d("intercept: path=" + path);

        response = interceptRequestWhenDebug(chain, path);
        if (null == response) {
            response = chain.proceed(chain.request());
        }
        return response;
    }

    /**
     * 拦截指定地址
     */
    private Response interceptRequestWhenDebug(Chain chain, String path) {
        Response response = null;
        Request request = chain.request();

//        if (path.contains("getCommunityNewsList")) {
//            response = getMockResponse(request, "mock/getCommunityNewsList.json");
//
//        } else if (path.contains("getCommunityNewsDetail")) {
//            response = getMockResponse(request, "mock/getCommunityNewsList.json");
//
//        } else if (path.contains("getLike")) {
//            response = getMockResponse(request, "mock/getCommunityNewsList.json");
//        }

        return response;
    }

    /**
     * 请求响应
     */
    private Response getMockResponse(Request request, String jsonPath) {
        Response response;
        String dataJson = MockDataUtils.getMockDataFromJsonFile(jsonPath);
        if (TextUtils.isEmpty(dataJson)) {
            Log.e(TAG, "response : " + "dataJson is empty!");

            response = new Response.Builder()
                    .code(500)
                    .protocol(Protocol.HTTP_1_0)
                    .request(request)
                    .build();
        } else {
            Log.e(TAG, "response : " + dataJson);

            response = new Response.Builder()
                    .code(200)
                    .message(dataJson)
                    .request(request)
                    .protocol(Protocol.HTTP_1_0)
                    .addHeader("Content-Type", "application/json")
                    .body(ResponseBody.create(MediaType.parse("application/json"), dataJson))
                    .build();
        }
        return response;
    }
}
