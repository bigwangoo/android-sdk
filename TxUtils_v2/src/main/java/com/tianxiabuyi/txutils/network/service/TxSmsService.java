package com.tianxiabuyi.txutils.network.service;

import com.tianxiabuyi.txutils.network.TxCall;
import com.tianxiabuyi.txutils.network.model.HttpResult;

import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by xjh1994 on 17/4/11.
 */

public interface TxSmsService {

    /**
     * 发送验证码
     * @param phone
     * @param category 1用于注册验证身份 2用于找回密码 3用于短信验证快捷登录
     * @return
     */
    @POST("sms/create")
    TxCall<HttpResult> send(@Query("phone") String phone, @Query("category") String category);

    /**
     * 验证code
     *
     * @param phone
     * @param code
     * @param category 验证类型，5更换手机号前验证老手机号
     * @return
     */
    @POST("sms/validate")
    TxCall<HttpResult> validate(@Query("phone") String phone, @Query("code") String code, @Query("category") String category);

}
