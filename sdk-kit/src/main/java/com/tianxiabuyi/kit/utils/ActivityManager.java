package com.tianxiabuyi.kit.view.glowpad;

import android.app.Activity;

import java.util.Stack;

/**
 * Author:caiyoufei
 * Date:2016/11/16
 * Time:19:54
 */
public class ActivityManager {
    //Activity堆栈管理
    public static Stack<Activity> activityStack = new Stack<>();

    /**
     * add Activity 添加Activity到栈
     */
    public static void addActivity(Activity activity) {
        activityStack.push(activity);
    }

    /**
     * 结束指定的Activity
     */
    public static void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public static void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                finishActivity(cls);
                break;
            }
        }
    }

    /**
     * 结束除主页外的Activity
     */
    public static void finishAllActivityWithoutMain(Activity activity) {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i) && activityStack.get(i) != activity) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 结束所有Activity
     */
    public static void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }
}
