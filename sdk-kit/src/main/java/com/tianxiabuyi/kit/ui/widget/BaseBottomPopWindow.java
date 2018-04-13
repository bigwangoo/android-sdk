package com.demo.common.ui.widget;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.demo.common.R;

/**
 * 底部弹出框
 * Created by WangYD on 2017/6/15.
 */
public abstract class BaseBottomPopWindow<T> extends PopupWindow {

    protected Context mContext;
    private View maskView;
    private WindowManager mWM;

    public BaseBottomPopWindow(Context context, T data) {
        super(context);
        mContext = context;
        mWM = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        initLayoutType();
        setContentView(initContentView(data));
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setOutsideTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(context.getResources().getDrawable(android.R.color.transparent));
        setAnimationStyle(R.style.Animations_BottomPush);
    }

    /*
     * 解决华为手机在home建进入后台后，在进入应用，蒙层出现在popupWindow上层的bug。
     * android4.0及以上版本都有这个hide方法，根据jvm原理，可以直接调用，选择android6.0版本进行编译即可。
     */
    @TargetApi(23)
    private void initLayoutType() {
        setWindowLayoutType(WindowManager.LayoutParams.TYPE_APPLICATION_SUB_PANEL);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        addMaskView(parent.getWindowToken());
        super.showAtLocation(parent, gravity, x, y);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        addMaskView(anchor.getWindowToken());
        super.showAsDropDown(anchor, xoff, yoff);
    }

    @Override
    public void dismiss() {
        removeMaskView();
        super.dismiss();
    }

    private void addMaskView(IBinder token) {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.format = PixelFormat.TRANSLUCENT;
        lp.type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL;
        lp.token = token;
        lp.windowAnimations = android.R.style.Animation_Toast;
        maskView = new View(mContext);
        maskView.setBackgroundColor(0x7f000000);
        maskView.setFitsSystemWindows(false);
        // 华为手机在home建进入后台后，在进入应用，蒙层出现在popupWindow上层，导致界面卡死，
        // 这里新增加按bug返回。
        // initLayoutType方法已经解决该问题，但是还是留着这个按back返回功能，防止其他手机出现华为手机类似问题。
        maskView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    removeMaskView();
                    return true;
                }
                return false;
            }
        });
        mWM.addView(maskView, lp);
    }

    private void removeMaskView() {
        if (maskView != null) {
            mWM.removeViewImmediate(maskView);
            maskView = null;
        }
    }

    /**
     * 显示在界面的底部
     */
    public void show(Activity activity) {
        showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    abstract View initContentView(T t);

}