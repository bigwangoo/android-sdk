package com.bigwangoo.sample.common.utils;

import android.widget.Toast;

import com.tianxiabuyi.txutils.TxUtils;


/**
 * 吐司工具类
 * Created by wyd on 2017/4/27.
 */
public class ToastUtil {

    private static Toast toast;

    public static void show(String string) {
        if (toast == null) {
            toast = Toast.makeText(TxUtils.getInstance().getContext(), string, Toast.LENGTH_SHORT);
        } else {
            toast.setText(string);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    public static void showLong(String string) {
        if (toast == null) {
            toast = Toast.makeText(TxUtils.getInstance().getContext(), string, Toast.LENGTH_LONG);
        } else {
            toast.setText(string);
            toast.setDuration(Toast.LENGTH_LONG);
        }
        toast.show();
    }
}
