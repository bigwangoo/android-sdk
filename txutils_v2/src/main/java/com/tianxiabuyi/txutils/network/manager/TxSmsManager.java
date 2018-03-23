package com.tianxiabuyi.txutils.network.manager;


import com.tianxiabuyi.txutils.TxServiceManager;
import com.tianxiabuyi.txutils.network.TxCall;
import com.tianxiabuyi.txutils.network.callback.ResponseCallback;
import com.tianxiabuyi.txutils.network.model.HttpResult;
import com.tianxiabuyi.txutils.network.service.TxSmsService;

/**
 * Created by xjh1994 on 17/4/11.
 */

public class TxSmsManager {

    public static final String REGISTER = "1";          // 1用于注册验证身份
    public static final String FORGET_PASSWORD = "2";   // 2用于找回密码
    public static final String LOGIN = "3";             // 3用于短信验证快捷登录
    public static final String BIND = "4";              // 4用于绑定手机号
    public static final String VALIDATE_OLD = "5";      // 5更换手机号前验证老手机号

    static TxSmsService service = TxServiceManager.createService(TxSmsService.class);

    /**
     * 发送注册验证码
     *
     * @param phone
     * @param callback
     * @return
     */
    public static TxCall send(String phone, String category, ResponseCallback<HttpResult> callback) {
        TxCall<HttpResult> call = service.send(phone, category);
        call.enqueue(callback);
        return call;
    }

    /**
     * 验证code
     *
     * @param phone
     * @param code
     * @param category
     * @param callback
     * @return
     */
    public static TxCall validate(String phone, String code, String category, ResponseCallback<HttpResult> callback) {
        TxCall<HttpResult> call = service.validate(phone, code, category);
        call.enqueue(callback);
        return call;
    }
}
