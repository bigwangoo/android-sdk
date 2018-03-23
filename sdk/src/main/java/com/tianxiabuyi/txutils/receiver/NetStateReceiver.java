package com.tianxiabuyi.txutils.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tianxiabuyi.txutils.log.TxLog;
import com.tianxiabuyi.txutils.util.NetUtils;

/**
 * Created by xjh1994 on 2016/9/1.
 * 网络状态监听
 */
public class NetStateReceiver extends BroadcastReceiver {

    private final static String ANDROID_NET_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ANDROID_NET_CHANGE_ACTION.equalsIgnoreCase(intent.getAction())) {
            if (NetUtils.isConnected(context)) {
                //Wifi情况下
                if (NetUtils.isWifi(context)) {
                    TxLog.e("使用WiFi");
                    onWifi();
                }
                //数据流量的情况下
                else {
                    TxLog.e("使用数据流量");
                    onData();
                }
            } else {
                TxLog.e("网络连接断开");
                onDisconnected();
            }
        }
    }

    protected void onDisconnected() {

    }

    protected void onData() {

    }

    protected void onWifi() {

    }
}
