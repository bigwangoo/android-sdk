package com.demo.common.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.tianxiabuyi.txbysdk.TxSDK;

/**
 * UI操作相关工具类
 * <p>
 * Created by wyd on 2017/4/27.
 */
public class UIUtils {


    /**
     * 获取全局context
     *
     * @return
     */
    public static Context getContext() {
        return TxSDK.getContext();
    }

    /**
     * 获取resources
     *
     * @return
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 获取字符串
     *
     * @param id
     * @return
     */
    public static String getString(int id) {
        return getResources().getString(id);
    }

    /**
     * 获取到字符数组
     */
    public static String[] getStringArray(int tabNames) {
        return getResources().getStringArray(tabNames);
    }

    /**
     * 获取drawable
     *
     * @param id
     * @return
     */
    public static Drawable getDrawable(int id) {
        return getResources().getDrawable(id);
    }

    /**
     * 获取颜色id
     *
     * @param colorId
     * @return
     */
    public static int getColor(int colorId) {
        return getResources().getColor(colorId);
    }

    /**
     * 获取dimens
     *
     * @param homePictureHeight
     * @return
     */
    public static int getDimens(int homePictureHeight) {
        return (int) getResources().getDimension(homePictureHeight);
    }

    /**
     * 获取视图inflate
     *
     * @param id
     * @return
     */
    public static View inflate(int id) {
        return View.inflate(getContext(), id, null);
    }

    /**
     * dip转换px
     */
    public static int dip2px(int dip) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * px转换dip
     */
    public static int px2dip(int px) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

}

