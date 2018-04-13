package com.txby.sample_kit.demo.ele.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.demo.common.utils.DisplayUtils;


/**
 * 饿了么 最新loading的动画
 * Created by wangyd on 2017/8/5.
 */

public class LoadingView_Clock extends View {

    private Paint mClockPaint;
    private Paint mCirclePaint;

    private int mViewMinWidth;
    private int hourWidth;
    private int minuteWidth;
    private int c1R;
    private int c2R;
    private int c3R;

    private int hourDegree = 0;
    private int minuteDegree = 0;
    private int c1Degree = 0;
    private int c2Degree = 0;
    private int c3Degree = 0;
    private int cStartRange = -90;
    private int cSweepRange = 180;

    private int mCurrentNum = 0; //画圆顺序

    public LoadingView_Clock(Context context) {
        this(context, null);
    }

    public LoadingView_Clock(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView_Clock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mViewMinWidth = DisplayUtils.dp2px(getContext(), 60);

        hourWidth = DisplayUtils.dp2px(getContext(), 6);
        minuteWidth = DisplayUtils.dp2px(getContext(), 8);
        c1R = DisplayUtils.dp2px(getContext(), 12);
        c2R = DisplayUtils.dp2px(getContext(), 18);
        c3R = DisplayUtils.dp2px(getContext(), 24);

        int color = 0xFF028EE0;

        mClockPaint = new Paint();
        mClockPaint.setAntiAlias(true);
        mClockPaint.setStyle(Paint.Style.STROKE);
        mClockPaint.setStrokeWidth(DisplayUtils.dp2px(getContext(), 2));
        mClockPaint.setColor(color);
//        mClockPaint.setPathEffect(new CornerPathEffect(4));

        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(DisplayUtils.dp2px(getContext(), 2));
        mCirclePaint.setColor(color);
//        mCirclePaint.setPathEffect(new CornerPathEffect(4));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = setMeasure(widthMeasureSpec);
        int height = setMeasure(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int setMeasure(int measureSpec) {
        int measureResult = mViewMinWidth;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            measureResult = size;
        } else {
            if (mode == MeasureSpec.AT_MOST) {
                measureResult = Math.min(measureResult, size);
            }
        }
        return measureResult;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int cx = getWidth() / 2;
        int cy = getHeight() / 2;

        canvas.drawPoint(cx, cy, mClockPaint);
        // 时针
        drawHour(canvas, cx, cy);
        // 分针
        drawMinute(canvas, cx, cy);
        // 圆1
        drawCircle1(canvas, cx, cy);
        // 圆2
        drawCircle2(canvas, cx, cy);
        // 圆3
        drawCircle3(canvas, cx, cy);

        postInvalidateDelayed(30);
    }

    private void drawHour(Canvas canvas, int cx, int cy) {
        hourDegree += 6;
        if (hourDegree >= 360) {
            hourDegree = 0;
        }
        canvas.rotate(hourDegree, cx, cy);
        canvas.drawLine(cx, cy, cx, cy - hourWidth, mClockPaint);
        canvas.rotate(-hourDegree, cx, cy);
    }

    private void drawMinute(Canvas canvas, int cx, int cy) {
        minuteDegree += 18;
        if (minuteDegree >= 360) {
            minuteDegree = 0;
        }
        canvas.rotate(minuteDegree, cx, cy);
        canvas.drawLine(cx, cy, cx, cy - minuteWidth, mClockPaint);
        canvas.rotate(-minuteDegree, cx, cy);
    }

    private void drawCircle1(Canvas canvas, int cx, int cy) {
        if (mCurrentNum < 5) {
            if (mCurrentNum == 0) {
                c1Degree += 30;
                if (c1Degree >= 360) {
                    mCurrentNum = 1;
                }
                int alpha = 255 * c1Degree / 360;
                mCirclePaint.setAlpha(alpha);
            } else {
                c1Degree = 0;
                mCirclePaint.setAlpha(255);
            }
            canvas.rotate(c1Degree, cx, cy);
            RectF f1 = new RectF(cx - c1R, cy - c1R, cx + c1R, cy + c1R);
            canvas.drawArc(f1, cStartRange, cSweepRange, false, mCirclePaint);
            canvas.rotate(-c1Degree, cx, cy);
        }
        if (mCurrentNum == 5) {
            c1Degree += 30;
            int alpha = 255 * (360 - c1Degree) / 360;
            mCirclePaint.setAlpha(alpha);
            if (c1Degree >= 360) {
                mCurrentNum = 0;
                c1Degree = 0;
            }
            canvas.rotate(c1Degree, cx, cy);
            RectF f1 = new RectF(cx - c1R, cy - c1R, cx + c1R, cy + c1R);
            canvas.drawArc(f1, cStartRange, cSweepRange, false, mCirclePaint);
            canvas.rotate(-c1Degree, cx, cy);
        }
    }

    private void drawCircle2(Canvas canvas, int cx, int cy) {
        if (mCurrentNum > 0 && mCurrentNum < 4) {
            if (mCurrentNum == 1) {
                c2Degree += 35;
                if (c2Degree >= 360) {
                    mCurrentNum = 2;
                }
                int alpha = 255 * c2Degree / 360;
                mCirclePaint.setAlpha(alpha);
            } else {
                c2Degree = 0;
                mCirclePaint.setAlpha(255);
            }
            canvas.rotate(c2Degree, cx, cy);
            RectF f2 = new RectF(cx - c2R, cy - c2R, cx + c2R, cy + c2R);
            canvas.drawArc(f2, cStartRange, cSweepRange, false, mCirclePaint);
            canvas.rotate(-c2Degree, cx, cy);
        }
        if (mCurrentNum == 4) {
            c2Degree += 35;
            int alpha = 255 * (360 - c2Degree) / 360;
            mCirclePaint.setAlpha(alpha);
            if (c2Degree >= 360) {
                mCurrentNum = 5;
                c2Degree = 0;
            }
            canvas.rotate(c2Degree, cx, cy);
            RectF f2 = new RectF(cx - c2R, cy - c2R, cx + c2R, cy + c2R);
            canvas.drawArc(f2, cStartRange, cSweepRange, false, mCirclePaint);
            canvas.rotate(-c2Degree, cx, cy);
        }
    }

    private void drawCircle3(Canvas canvas, int cx, int cy) {
        if (mCurrentNum == 2) {
            c3Degree += 40;
            int alpha = 255 * c3Degree / 360;
            if (alpha > 150) {
                alpha = 150;
            }
            if (c3Degree >= 360) {
                mCurrentNum = 3;
                c3Degree = 0;
            }
            mCirclePaint.setAlpha(alpha);
            canvas.rotate(c3Degree, cx, cy);
            RectF f3 = new RectF(cx - c3R, cy - c3R, cx + c3R, cy + c3R);
            canvas.drawArc(f3, cStartRange, cSweepRange, false, mCirclePaint);
            canvas.rotate(-c3Degree, cx, cy);
        }
        if (mCurrentNum == 3) {
            c3Degree += 40;
            int alpha = 255 * (360 - c3Degree) / 360;
            if (alpha > 150) {
                alpha = 150;
            }
            if (c3Degree >= 360) {
                mCurrentNum = 4;
                c3Degree = 0;
            }
            mCirclePaint.setAlpha(alpha);
            canvas.rotate(c3Degree, cx, cy);
            RectF f3 = new RectF(cx - c3R, cy - c3R, cx + c3R, cy + c3R);
            canvas.drawArc(f3, cStartRange, cSweepRange, false, mCirclePaint);
            canvas.rotate(-c3Degree, cx, cy);
        }
    }

}
