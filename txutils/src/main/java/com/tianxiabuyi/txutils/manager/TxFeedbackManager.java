package com.tianxiabuyi.txutils.manager;

import android.content.Context;

import com.tianxiabuyi.txutils.TxUtils;
import com.tianxiabuyi.txutils.network.TxCall;
import com.tianxiabuyi.txutils.network.callback.ResponseCallback;
import com.tianxiabuyi.txutils.network.model.HttpResult;
import com.tianxiabuyi.txutils.network.service.TxFeedbackService;
import com.tianxiabuyi.txutils.util.AppUtils;

import okhttp3.OkHttpClient;

/**
 * 意见反馈
 *
 * @author xjh1994
 * @date 16/11/15
 */
public class TxFeedbackManager {

    /**
     * 发送意见反馈
     *
     * @param context
     * @param suggestion
     * @param grade
     * @param uid
     * @param callback
     */
    public static TxCall sendFeedback(Context context, String suggestion, float grade, String uid, ResponseCallback<HttpResult> callback) {
        // 需要使用默认配置
        OkHttpClient httpClient = TxUtils.getInstance().getDefaultOkHttp();
        TxFeedbackService service = TxServiceManager.createService(TxFeedbackService.class, httpClient);

        String version = AppUtils.getVersionName(context);
        int versionCode = AppUtils.getVersionCode(context);

        TxCall<HttpResult> txCall = service.sendFeedback(2, version, versionCode, suggestion, grade, uid);
        txCall.enqueue(callback);
        return txCall;
    }

}
