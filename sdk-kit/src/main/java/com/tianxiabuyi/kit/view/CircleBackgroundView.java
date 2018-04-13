package com.demo.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.tianxiabuyi.txbysdk.R;

/**
 * 带动画的进度圆环
 * sample
 * <p>
 * <com.demo.common.view.ProgressRingView
 * android:layout_width="320dp"
 * android:layout_height="320dp"
 * app:pr_bg_end_color="#00ffffff"
 * app:pr_bg_mid_color="#33ffffff"
 * app:pr_bg_start_color="#00ffffff"
 * app:pr_progress="80"
 * app:pr_progress_end_color="#D9B262"
 * app:pr_progress_start_color="#00ffffff"
 * app:pr_progress_width="8dp" />
 * <p>
 * Created by YaoDong.Wang on 2017/7/21.
 */
public class CircleBackgroundView extends View {

    private int progressStartColor;
    private int progressEndColor;
    private int bgStartColor;
    private int bgMidColor;
    private int bgEndColor;
    private int progress;
    private float progressWidth;
    private int startAngle;
    private int sweepAngle;
    private boolean showAnim;

    private int mMeasureHeight;
    private int mMeasureWidth;

    private Paint bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    private Paint progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);

    private RectF pRectF;

    private float unitAngle;

    private int curProgress = 0;

//    public CircleBackgroundView(Context context) {
//        this(context, null);
//    }
//
//    public CircleBackgroundView(Context context, @Nullable AttributeSet attrs) {
//        this(context, attrs, 0);
//    }

    public CircleBackgroundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ProgressRing);
        progress = a.getInt(R.styleable.ProgressRing_pr_progress, 0);
        progressWidth = a.getDimension(R.styleable.ProgressRing_pr_progress_width, 8f);
        progressStartColor = a.getColor(R.styleable.ProgressRing_pr_progress_start_color, Color.YELLOW);
        progressEndColor = a.getColor(R.styleable.ProgressRing_pr_progress_end_color, progressStartColor);
        bgStartColor = a.getColor(R.styleable.ProgressRing_pr_bg_start_color, Color.LTGRAY);
        bgMidColor = a.getColor(R.styleable.ProgressRing_pr_bg_mid_color, bgStartColor);
        bgEndColor = a.getColor(R.styleable.ProgressRing_pr_bg_end_color, bgStartColor);
        startAngle = a.getInt(R.styleable.ProgressRing_pr_start_angle, 150);
        sweepAngle = a.getInt(R.styleable.ProgressRing_pr_sweep_angle, 240);
        showAnim = a.getBoolean(R.styleable.ProgressRing_pr_show_anim, true);
        a.recycle();

        unitAngle = (float) (sweepAngle / 100.0);

        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setStrokeCap(Paint.Cap.ROUND);
        bgPaint.setStrokeWidth(progressWidth);

        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
        progressPaint.setStrokeWidth(progressWidth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mMeasureWidth = getMeasuredWidth();
        mMeasureHeight = getMeasuredHeight();
        if (pRectF == null) {
            float halfProgressWidth = progressWidth / 2;
            pRectF = new RectF(halfProgressWidth + getPaddingLeft(), halfProgressWidth + getPaddingTop(),
                    mMeasureWidth - halfProgressWidth - getPaddingRight(),
                    mMeasureHeight - halfProgressWidth - getPaddingBottom());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!showAnim) {
            curProgress = progress;
        }
        drawBg(canvas);
        drawProgress(canvas);
        if (curProgress < progress) {
            curProgress++;
            postInvalidate();
        }
    }

    // 只需要画进度之外的背景即可
    private void drawBg(Canvas canvas) {
        float halfSweep = sweepAngle / 2;
        for (int i = sweepAngle, st = (int) (curProgress * unitAngle); i > st; --i) {
            if (i - halfSweep > 0) {
                bgPaint.setColor(getGradient((i - halfSweep) / halfSweep, bgMidColor, bgEndColor));
            } else {
                bgPaint.setColor(getGradient((halfSweep - i) / halfSweep, bgMidColor, bgStartColor));
            }
            canvas.drawArc(pRectF, startAngle + i, 1, false, bgPaint);
        }
    }

    private void drawProgress(Canvas canvas) {
        for (int i = 0, end = (int) (curProgress * unitAngle); i <= end; i++) {
            progressPaint.setColor(getGradient(i / (float) end, progressStartColor, progressEndColor));
            canvas.drawArc(pRectF, startAngle + i, 1, false, progressPaint);
        }
    }

    public void setProgress(@IntRange(from = 0, to = 100) int progress) {
        this.progress = progress;
        invalidate();
    }

    public int getProgress() {
        return progress;
    }

    //渐变
    public int getGradient(float fraction, int startColor, int endColor) {
        if (fraction > 1) fraction = 1;
        int alphaStart = Color.alpha(startColor);
        int redStart = Color.red(startColor);
        int blueStart = Color.blue(startColor);
        int greenStart = Color.green(startColor);
        int alphaEnd = Color.alpha(endColor);
        int redEnd = Color.red(endColor);
        int blueEnd = Color.blue(endColor);
        int greenEnd = Color.green(endColor);
        int alphaDifference = alphaEnd - alphaStart;
        int redDifference = redEnd - redStart;
        int blueDifference = blueEnd - blueStart;
        int greenDifference = greenEnd - greenStart;
        int alphaCurrent = (int) (alphaStart + fraction * alphaDifference);
        int redCurrent = (int) (redStart + fraction * redDifference);
        int blueCurrent = (int) (blueStart + fraction * blueDifference);
        int greenCurrent = (int) (greenStart + fraction * greenDifference);
        return Color.argb(alphaCurrent, redCurrent, greenCurrent, blueCurrent);
    }
}

