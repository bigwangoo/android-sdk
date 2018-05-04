package com.tianxiabuyi.kit.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.tianxiabuyi.kit.R;
import com.tianxiabuyi.txutils.util.DisplayUtils;


/**
 * CardView
 * Created by wang.yd on 2017/7/6.
 */
public class TicketView extends LinearLayout {

    private Paint mPaint;
    private Paint mDashPaint;
    private int mItemHeight;                // item 高度
    private int mRadius;                    // 圆点半径
    private int mCircleLeftPadding;         // 圆点左边padding
    private int mCircleRightPadding;        // 圆点右边边padding
    private int mCircleColor;               // 圆点颜色
    private boolean isShowDivider;          // 底部分割
    private Path mPath;                     // 虚线
    private float mDashPaintWidth;          // 虚线高度
    private float mDashWidth;               // 虚线宽度
    private float mDashSpaceWidth;          // 虚线间隔宽度

    public TicketView(Context context) {
        this(context, null);
    }

    public TicketView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TicketView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TicketView);
        mItemHeight = a.getDimensionPixelSize(R.styleable.TicketView_itemHeight, DisplayUtils.dp2px(getContext(), 40));
        mRadius = a.getDimensionPixelSize(R.styleable.TicketView_circleRadius, DisplayUtils.dp2px(getContext(), 6));
        mCircleLeftPadding = a.getDimensionPixelSize(R.styleable.TicketView_circlePaddingLeft, DisplayUtils.dp2px(getContext(), 10));
        mCircleRightPadding = a.getDimensionPixelSize(R.styleable.TicketView_circlePaddingRight, DisplayUtils.dp2px(getContext(), 10));
        mCircleColor = a.getColor(R.styleable.TicketView_circleColor, 0xFFF1F0EE);
        isShowDivider = a.getBoolean(R.styleable.TicketView_showDivider, false);

        if (isShowDivider) {
            mDashPaintWidth = a.getFloat(R.styleable.TicketView_dashHeight, DisplayUtils.dp2px(getContext(), 1));
            mDashWidth = a.getFloat(R.styleable.TicketView_dashWidth, DisplayUtils.dp2px(getContext(), 6));
            mDashSpaceWidth = a.getFloat(R.styleable.TicketView_dashSpaceWidth, DisplayUtils.dp2px(getContext(), 6));
        }
        a.recycle();

        init();
    }

    private void init() {
        Drawable background = getBackground();
        if (background != null) {
            if (background instanceof ColorDrawable) {
                ColorDrawable colorDrawable = (ColorDrawable) background;
                int color = colorDrawable.getColor();
                setBackgroundColor(color);
            } else {
                Bitmap bitmap = ((BitmapDrawable) background).getBitmap();
                setBackground(new BitmapDrawable(getResources(), bitmap));
            }
        } else {
            setBackgroundColor(Color.WHITE);
        }

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mCircleColor);

        if (isShowDivider) {
            //设置虚线的间隔和点的长度
            PathEffect mEffects = new DashPathEffect(new float[]{
                    mDashWidth, mDashSpaceWidth, mDashWidth, mDashSpaceWidth}, 0);
            mPath = new Path();
            mDashPaint = new Paint();
            mDashPaint.setAntiAlias(true);
            mDashPaint.setColor(mCircleColor);
            mDashPaint.setStyle(Paint.Style.STROKE);
            mDashPaint.setStrokeWidth(mDashPaintWidth);
            mDashPaint.setPathEffect(mEffects);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mItemHeight > 0) {
            int mCount = (int) (getMeasuredHeight() / mItemHeight + 0.5f);

            for (int i = 0; i < mCount; i++) {
                if (i == mCount - 1 && isShowDivider) {
                    int offSet = DisplayUtils.dp2px(getContext(), 1);
                    canvas.drawCircle(offSet, getCircleY(i), getRadius(), mPaint);
                    canvas.drawCircle(getMeasuredWidth() - offSet, getCircleY(i), getRadius(), mPaint);
                    //虚线 起始坐标
                    mPath.moveTo(getRadius() + 10, getCircleY(i));
                    mPath.lineTo(getMeasuredWidth() - getRadius() - 10, getCircleY(i));
                    canvas.drawPath(mPath, mDashPaint);
                    break;
                }
                canvas.drawCircle(getCircleLeftX(), getCircleY(i), getRadius(), mPaint);
                canvas.drawCircle(getCircleRightX(), getCircleY(i), getRadius(), mPaint);
            }
        }
    }

    /**
     * 左圆心 X轴坐标
     *
     * @return x
     */
    private int getCircleLeftX() {
        return getPaddingLeft() + mCircleLeftPadding + mRadius;
    }

    /**
     * 右圆心 X轴坐标
     *
     * @return x
     */
    private int getCircleRightX() {
        return getMeasuredWidth() - getPaddingRight() - mCircleRightPadding - mRadius;
    }

    /**
     * 圆心 Y轴坐标
     *
     * @param count x
     * @return x
     */
    private int getCircleY(int count) {
        return getPaddingTop() + mItemHeight / 2 + mItemHeight * count;
    }

    // public
    public int getItemHeight() {
        return mItemHeight;
    }

    public void setItemHeight(int itemHeight) {
        this.mItemHeight = itemHeight;
        postInvalidate();
    }

    public int getRadius() {
        return mRadius;
    }

    public void setRadius(int radius) {
        this.mRadius = radius;
        postInvalidate();
    }

    public int getCircleColor() {
        return mCircleColor;
    }

    public void setCircleColor(int circleColor) {
        this.mCircleColor = circleColor;
        mPaint.setColor(circleColor);
        if (isShowDivider) {
            mDashPaint.setColor(mCircleColor);
        }
        postInvalidate();
    }

}

