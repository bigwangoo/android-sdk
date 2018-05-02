package com.wangyd.module.utils;

import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;

/**
 * Create on 2018/1/2 16:58
 *
 * @author WangYaoDong
 */
public class MotionEventUtils {

    private void simulateClick(View view, float x, float y) {
        long downTime = SystemClock.uptimeMillis();
        final MotionEvent downEvent = MotionEvent.obtain(downTime, downTime, MotionEvent.ACTION_DOWN, x, y, 0);
        downTime += 1000;
        final MotionEvent upEvent = MotionEvent.obtain(downTime, downTime, MotionEvent.ACTION_UP, x, y, 0);
        view.onTouchEvent(downEvent);
        view.onTouchEvent(upEvent);
        downEvent.recycle();
        upEvent.recycle();
    }

    public void setMouseClick(View view, int x, int y) {
        MotionEvent evenDown = MotionEvent.obtain(System.currentTimeMillis(),
                System.currentTimeMillis() + 100, MotionEvent.ACTION_DOWN, x, y, 0);
        view.dispatchTouchEvent(evenDown);
        MotionEvent eventUp = MotionEvent.obtain(System.currentTimeMillis(),
                System.currentTimeMillis() + 100, MotionEvent.ACTION_UP, x, y, 0);
        view.dispatchTouchEvent(eventUp);
        evenDown.recycle();
        eventUp.recycle();
    }
}
