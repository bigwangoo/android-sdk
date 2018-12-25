package com.bigwangoo.sample.common.utils;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * 代码创建sharp selector
 * <p>
 * Created by wyd on 2017/4/27.
 */
public class DrawableUtils {
    /**
     * 代码 创建图形
     *
     * @param color
     * @return
     */
    public static GradientDrawable createShape(int color) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(DisplayUtils.dip2px(5));//设置4个角的弧度
        drawable.setColor(color);// 设置颜色
        return drawable;
    }

    /**
     * 代码创建 状态选择器
     *
     * @param pressedDrawable
     * @param normalDrawable
     * @return
     */
    public static StateListDrawable createSelectorDrawable(Drawable pressedDrawable, Drawable normalDrawable) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);// 按下显示的图片
        stateListDrawable.addState(new int[]{}, normalDrawable);// 抬起显示的图片
        return stateListDrawable;
    }

}
