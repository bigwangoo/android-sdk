package com.tianxiabuyi.txutils.network.service;

import com.tianxiabuyi.txutils.config.TxConstants;
import com.tianxiabuyi.txutils.network.TxCall;
import com.tianxiabuyi.txutils.network.model.HttpResult;

import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 意见反馈
 * Created by xjh1994 on 16/11/15.
 */
public interface TxFeedbackService {

    /**
     * @param version      1.0
     * @param version_code 1
     * @param device       设备类型（iOS为0, Android为1, Wechat为2）
     * @param grade        5.0
     * @param suggestion   意见内容
     * @param uid          ""
     * @param phone        (可选)
     * @param name         (可选)
     * @param imgs         (可选)
     * @return TxCall
     */
    @POST(TxConstants.FEEDBACK_URL)
    TxCall<HttpResult> sendFeedback(@Query("version") String version,
                                    @Query("version_code") int version_code,
                                    @Query("device") int device,
                                    @Query("grade") float grade,
                                    @Query("suggestion") String suggestion,
                                    @Query("uid") String uid,
                                    @Query("name") String name,
                                    @Query("phone") String phone,
                                    @Query("imgs") String imgs);
}
