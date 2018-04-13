package com.demo.common.ui.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Author :  suzeyu
 * Time   :  2016-12-26  上午1:41
 * ClassDescription : 对多点触控场景时, {@link android.support.v4.view.ViewPager#onInterceptTouchEvent(MotionEvent)}中
 * pointerIndex = -1. 发生IllegalArgumentException: pointerIndex out of range 处理
 */
public class ViewPagerFixMulti extends ViewPager {

    public ViewPagerFixMulti(Context context) {
        super(context);
    }

    public ViewPagerFixMulti(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        try {
            return super.onInterceptTouchEvent(motionEvent);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return false;
    }

}
