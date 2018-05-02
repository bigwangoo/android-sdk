package com.bigwangoo.sample.module;

import android.content.Context;
import android.net.Uri;
import android.support.multidex.MultiDexApplication;

import com.github.mzule.activityrouter.router.RouterCallback;
import com.github.mzule.activityrouter.router.RouterCallbackProvider;
import com.github.mzule.activityrouter.router.SimpleRouterCallback;


import java.util.logging.Logger;


/**
 * @author WangYaoDong
 * @date 2017/4/25
 */
public class CustomApplication extends MultiDexApplication implements RouterCallbackProvider {

    @Override
    public void onCreate() {
        x.Ext.init(this);

        XGPushManager.setNotifactionCallback(new XGPushNotifactionCallback() {
            @Override
            public void handleNotify(XGNotifaction xGNotifaction) {
                Logger.i("test", "处理信鸽通知：" + xGNotifaction);
                // 获取标签、内容、自定义内容
                String title = xGNotifaction.getTitle();
                String content = xGNotifaction.getContent();
                String customContent = xGNotifaction.getCustomContent();
                // 其它的处理
                // 如果还要弹出通知，可直接调用以下代码或自己创建Notifaction，
                // 否则，本通知将不会弹出在通知栏中。
                xGNotifaction.doNotify();
            }
        });


        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public RouterCallback provideRouterCallback() {
        return new SimpleRouterCallback() {
            @Override
            public boolean beforeOpen(Context context, Uri uri) {
                return super.beforeOpen(context, uri);
            }

            @Override
            public void afterOpen(Context context, Uri uri) {
            }

            @Override
            public void error(Context context, Uri uri, Throwable e) {
                super.error(context, uri, e);
            }

            @Override
            public void notFound(Context context, Uri uri) {
                com.demo.common.utils.ToastUtil.show("模块正在建设中...");
            }
        };
    }

}
