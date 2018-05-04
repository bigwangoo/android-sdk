package com.tianxiabuyi.kit.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.tianxiabuyi.kit.utils.DisplayUtils;


/**
 * 自定义圆形进度条
 * <p>
 * 下载进度使用
 * Created by wyd on 2017/5/2.
 */
public class ProgressArc extends View {

    public static final int STYLE_NONE = -1;

    public static final int STYLE_DOWNLOADING = 0;

    public static final int STYLE_WAITING = 1;
    // 开始进度（角度）
    private final static int START_PROGRESS = -90;
    //
    private final static float RATIO = 360;
    // 画笔
    private Paint mPaint;
    // 圆形区域
    private RectF mRectF;
    // 直径
    private int mArcDiameter;
    // 是否包含圆心
    private boolean mUseCenter;
    // 图片
    private Drawable mForegroundDrawable;
    // 资源id
    private int mForegroundDrawableResId;
    // 进度颜色
    private int mProgressColor;
    // 圆弧扫过范围
    private float mSweep = 0;
    // 进度
    private float mProgress;

    private int mStyle = STYLE_NONE;

    public ProgressArc(Context context) {
        super(context);

        // 初始画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(DisplayUtils.dip2px(1));

        mRectF = new RectF();
        mUseCenter = false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = 0;
        int height = 0;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = mForegroundDrawable == null ? 0 : mForegroundDrawable.getIntrinsicWidth();
            if (widthMode == MeasureSpec.AT_MOST) {
                width = Math.min(width, widthSize);
            }
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = mForegroundDrawable == null ? 0 : mForegroundDrawable.getIntrinsicHeight();
            if (heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(height, heightSize);
            }
        }
        mRectF.left = (width - mArcDiameter) * 0.5f;
        mRectF.top = (height - mArcDiameter) * 0.5f;
        mRectF.right = (width + mArcDiameter) * 0.5f;
        mRectF.bottom = (height + mArcDiameter) * 0.5f;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 绘制
        if (mForegroundDrawable != null) {
            mForegroundDrawable.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
            mForegroundDrawable.draw(canvas);
        }
        if (mStyle == STYLE_DOWNLOADING || mStyle == STYLE_WAITING) {
            mPaint.setColor(mProgressColor);
            mSweep = mProgress * RATIO;
            // 绘制
            canvas.drawArc(mRectF, START_PROGRESS, mSweep, mUseCenter, mPaint);
        }
    }

    /**
     * 设置style
     *
     * @param style
     */
    public void setStyle(int style) {
        this.mStyle = style;
        if (mStyle == STYLE_WAITING) {
            invalidateSafe();
        }
    }

    /**
     * 设置背景
     *
     * @param resId
     */
    public void setForegroundResource(int resId) {
        if (mForegroundDrawableResId == resId) {
            return;
        }
        mForegroundDrawableResId = resId;
        mForegroundDrawable = DisplayUtils.getDrawable(mForegroundDrawableResId);
        invalidateSafe();  //刷新界面
    }

    /**
     * 设置直径
     */
    public void setArcDiameter(int diameter) {
        mArcDiameter = diameter;
    }

    /**
     * 设置进度条的颜色
     */
    public void setProgressColor(int progressColor) {
        mProgressColor = progressColor;
        mPaint.setColor(progressColor);
    }

    /**
     * 设置进度
     *
     * @param progress
     * @param smooth
     */
    public void setProgress(float progress, boolean smooth) {
        mProgress = progress;
        invalidateSafe();
    }

    private void invalidateSafe() {
        if (true) {
            postInvalidate();
        } else {
            invalidate();
        }
    }
}
