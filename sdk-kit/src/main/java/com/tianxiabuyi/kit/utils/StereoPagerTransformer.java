package com.demo.common.utils;

import android.animation.TimeInterpolator;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

/**
 * 翻转动画
 * Created by wang.yd on 2017/7/7.
 */
public class StereoPagerTransformer implements ViewPager.PageTransformer {
    private static final String TAG = StereoPagerTransformer.class.getSimpleName();
    private static final float MAX_ROTATE_Y = 90;
    private final float pageWidth;

    public StereoPagerTransformer(float pageWidth) {
        this.pageWidth = pageWidth;
    }

    @Override
    public void transformPage(View page, float position) {
        page.setPivotY(page.getHeight() / 2);

        if (position < -1) { // [-Infinity,-1)
            page.setPivotX(0);
            page.setRotationY(90);
        } else if (position <= 0) { // [-1,0]
            page.setPivotX(pageWidth);
            page.setRotationY(-sInterpolator.getInterpolation(-position));
            Log.e(TAG, "------------|" + position);
        } else if (position <= 1) { // (0,1]
            page.setPivotX(0);
            page.setRotationY(sInterpolator.getInterpolation(position));
//            Log.e(TAG, "------------" + position);
        } else { // (1,+Infinity]
            page.setPivotX(0);
            page.setRotationY(90);
        }
    }

    private static final TimeInterpolator sInterpolator = new TimeInterpolator() {
        @Override
        public float getInterpolation(float input) {
            if (input < 0.7) {
                return input * (float) Math.pow(0.7, 3.0) * MAX_ROTATE_Y;
            } else {
                return (float) Math.pow(input, 4.0) * MAX_ROTATE_Y;
            }
        }
    };
}
