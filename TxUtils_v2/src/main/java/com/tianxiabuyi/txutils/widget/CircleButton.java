package com.tianxiabuyi.txutils.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.TextView;

import com.tianxiabuyi.txutils.R;

/**
 * CircleButton
 */
public class CircleButton extends TextView {
    // default white color
    private final int defaultColor = Color.rgb(255, 255, 255);
    // default 5 dp radius
    private final int defaultRadius = 5;

    private int drawColor = defaultColor;
    private int radius = defaultRadius;
    private Context context;

    public CircleButton(Context context) {
        super(context);
        initParmars(context, null);
    }


    public CircleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initParmars(context, attrs);
    }

    public CircleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParmars(context, attrs);
    }

    private void initParmars(Context context, AttributeSet attrs) {
        this.context = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleButton);
        radius = array.getDimensionPixelSize(R.styleable.CircleButton_radius, dip2px(context,defaultRadius));
        drawColor = array.getColor(R.styleable.CircleButton_back_color, defaultColor);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(drawColor);
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0, 0, this.getWidth(), this.getHeight());
        canvas.drawRoundRect(rectF, radius, radius, paint);
        super.onDraw(canvas);
    }
}
