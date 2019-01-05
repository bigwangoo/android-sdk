package com.bigwangoo.sample;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.TelephonyManager;

import com.zhy.http.okhttp.request.OkHttpRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @author wangyd
 * @date 2018/12/26
 * @description description
 */
public class ConstantTest {


    public void test(Context context) {

        TelephonyManager systemService = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);


        try {
            Method method = Class.forName("android.os.ServiceManager")
                    .getMethod("getService", String.class);

            // 获取远程TELEPHONY_SERVICE的IBinder对象的代理
            IBinder binder = (IBinder) method.invoke(null, new Object[]{Context.TELEPHONY_SERVICE});
            // 将IBinder对象的代理转换为ITelephony对象
//            ITelephony telephony = ITelephony.Stub.asInterface(binder);
//            // 挂断电话
//            telephony.endCall();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        context.startActivity(new Intent(context, ConstantTest.class));


        OkHttpClient okHttpClient = new OkHttpClient();
        Request request;
    }

    public void proxy() {

        Object proxy = Proxy.newProxyInstance(Constant.class.getClassLoader(), new Class[]{Constant.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        method.invoke(this, args);
                        return null;
                    }
                });

//        proxy.xxx();
    }
}