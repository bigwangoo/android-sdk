package com.bigwangoo.sample.module.ele.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.ArrayList;

/**
 * @author deadline
 * @time 2016/9/26
 */
public class BounceLoadingView extends View {

    private static final String TAG = BounceLoadingView.class.getSimpleName();
    //默认阴影颜色
    private static final int DEFAULT_SHADOW_COLOR = Color.LTGRAY;
    //默认下落时长
    private static final int DEFAULT_DURATION = 700;

    //阴影颜色
    private int mShadowColor = DEFAULT_SHADOW_COLOR;
    //弹跳时长
    private int mDuration = DEFAULT_DURATION;
    //图片画笔
    private Paint mBitmapPaint;
    //阴影画笔
    private Paint mShadowPaint;
    //建议图片大小相同
    private ArrayList<Bitmap> mBitmapList;
    private Bitmap mCurrentBitmap;
    private int mCurrentIndex;
    //插值[0-1][1-0]
    private float mPercent;

    private Matrix matrix;
    private RectF mShadowRectF;
    private ValueAnimator animator;

    public BounceLoadingView(Context context) {
        this(context, null);
    }

    public BounceLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BounceLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BounceLoadingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void setup() {
        mPercent = 0f;
        mCurrentIndex = 0;
        matrix = new Matrix();
        mShadowRectF = new RectF();
        mBitmapList = new ArrayList<>();
        //图片
        mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 阴影
        mShadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mShadowPaint.setStyle(Paint.Style.FILL);
        mShadowPaint.setColor(mShadowColor);
    }

    public void addBitmap(int resId) {
        try {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resId);
            addBitmap(bitmap);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            mBitmapList.add(bitmap);
        }
    }

    public void addBitmaps(ArrayList<Bitmap> bitmaps) {
        if (bitmaps != null) {
            mBitmapList.addAll(bitmaps);
        }
    }

    /**
     * 设置每次跳起落下的时长
     *
     * @param duration
     */
    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    /**
     * 设置阴影的颜色
     *
     * @param shadowColor
     */
    public void setShadowColor(int shadowColor) {
        this.mShadowColor = shadowColor;
        if (mShadowPaint != null) {
            mShadowPaint.setColor(mShadowColor);
            postInvalidate();
        }
    }

    /**
     * 开启动画
     */
    public void start() {
        stop();

        if (animator == null) {
            animator = ValueAnimator.ofFloat(0f, 1f, 0f);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    //重置index
                    mCurrentIndex = 0;
                    mCurrentBitmap = mBitmapList.get(mCurrentIndex);
                }
            });
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float value = (float) valueAnimator.getAnimatedValue();
                    if (value == 1) {
                        Log.e(TAG, "onAnimationUpdate: " + value);
                        if (mBitmapList != null && mBitmapList.size() > 0) {
                            mCurrentIndex++;
                            if (mCurrentIndex >= mBitmapList.size()) {
                                mCurrentIndex = 0;
                            }
                            mCurrentBitmap = mBitmapList.get(mCurrentIndex);
                        }
                    }
                    if (value != mPercent) {
                        mPercent = value;
                        postInvalidate();
                    }
                }
            });
        }
        animator.setDuration(mDuration);
        animator.start();
    }

    /**
     * 结束
     */
    public void stop() {
        if (animator != null) {
            animator.cancel();
            animator = null;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, 2 * width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float mHalfShadowWidth = Math.max(16, getWidth() / 2f * mPercent);
        float mHalfShadowHeight = Math.max(8, getHeight() / 8f * mPercent);

        // mPercent=1 阴影最大
        mShadowRectF.set(getWidth() / 2f - mHalfShadowWidth, getHeight() * 7 / 8f - mHalfShadowHeight,
                getWidth() / 2f + mHalfShadowWidth, getHeight() * 7 / 8f + mHalfShadowHeight);
        canvas.drawOval(mShadowRectF, mShadowPaint);

        if (mCurrentBitmap != null) {
            canvas.save();
            float mTopMargin = (getHeight() * 0.9f - mCurrentBitmap.getHeight()) * mPercent;
            canvas.translate(getWidth() / 2f - mCurrentBitmap.getWidth() / 2f, mTopMargin);
            canvas.drawBitmap(mCurrentBitmap, matrix, mBitmapPaint);
            canvas.restore();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

}
