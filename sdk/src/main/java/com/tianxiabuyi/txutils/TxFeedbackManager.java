package com.tianxiabuyi.txutils;

import android.content.Context;

import com.tianxiabuyi.txutils.network.TxCall;
import com.tianxiabuyi.txutils.network.callback.ResponseCallback;
import com.tianxiabuyi.txutils.network.model.HttpResult;
import com.tianxiabuyi.txutils.network.service.TxFeedbackService;
import com.tianxiabuyi.txutils.util.AppUtils;

/**
 * @author xjh1994
 * @date 16/11/15
 * @description 意见反馈
 */
public class TxFeedbackManager {

    private static TxFeedbackService service = TxServiceManager.createServiceEncrypt(TxFeedbackService.class);

    /**
     * 发送意见反馈
     */
    public static TxCall sendFeedback(Context context, String suggestion, String uid,
                                      String name, String phone, String imgs,
                                      ResponseCallback<HttpResult> callback) {
        return sendFeedback(context, 5.0f, suggestion, uid, name, phone, imgs, callback);
    }

    /**
     * 无评分 匿名
     */
    public static TxCall sendFeedback(Context context, String suggestion,
                                      ResponseCallback<HttpResult> callback) {
        return sendFeedback(context, 5.0f, suggestion, "0", "", "", "", callback);
    }

    /**
     * 无评分 非匿名
     */
    public static TxCall sendFeedback(Context context, String suggestion, String uid,
                                      ResponseCallback<HttpResult> callback) {
        return sendFeedback(context, 5.0f, suggestion, uid, "", "", "", callback);
    }

    /**
     * 评分 匿名提交
     */
    public static TxCall sendFeedback(Context context, float grade, String suggestion,
                                      ResponseCallback<HttpResult> callback) {
        return sendFeedback(context, grade, suggestion, "0", "", "", "", callback);
    }

    /**
     * 评分 非匿名
     */
    public static TxCall sendFeedback(Context context, float grade, String suggestion, String uid,
                                      ResponseCallback<HttpResult> callback) {
        return sendFeedback(context, grade, suggestion, uid, "", "", "", callback);
    }

    /**
     * 发送意见反馈
     */
    public static TxCall sendFeedback(Context context, float grade, String suggestion, String uid,
                                      String name, String phone, String imgs, ResponseCallback<HttpResult> callback) {
        String version = AppUtils.getVersionName(context);
        int version_code = AppUtils.getVersionCode(context);

        TxCall<HttpResult> call = service.sendFeedback(version, version_code, 1,
                grade, suggestion, uid, name, phone, imgs);
        call.enqueue(callback);
        return call;
    }
}
