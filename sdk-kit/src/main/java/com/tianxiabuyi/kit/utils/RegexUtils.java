package com.tianxiabuyi.kit.utils;

import android.text.TextUtils;
import android.widget.Toast;

import com.tianxiabuyi.txutils.TxUtils;

/**
 * 正则工具
 * Created by YaoDong.Wang on 2017/8/17.
 */
public class RegexUtils {

    /**
     * 用户名正则
     */
    public static final String REGEX_USER_NAME = "^[a-zA-Z][a-zA-Z0-9]{3,19}$";
    /**
     * 手机号码正则
     */
    public static final String REGEX_MOBILE_PHONE = "^1\\d{10}$";
    /**
     * 真实姓名正则
     */
    public static final String REGEX_NAME = "[\u4e00-\u9fa5]{2,10}";
    /**
     * 金额 > 0
     */
    public static final String REGEX_MONEY = "^0(.\\d{1,2})?|[1-9]\\d*(.\\d{1,2})?$";
    /**
     * 金额 大于等于0
     */
    public static final String REGEX_MONEY_ZERO = "^0|0(.\\d{1,2})?|[1-9]\\d*(.\\d{1,2})?$";


    public static boolean checkMoney(String money) {
        if (TextUtils.isEmpty(money)) {
            Toast.makeText(TxUtils.getInstance().getContext(), "金额不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!money.matches(REGEX_MONEY)) {
            Toast.makeText(TxUtils.getInstance().getContext(), "金额不正确", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
