package com.demo.common.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

import com.demo.common.R;

/**
 * 带清除功能的输入框
 * <p>
 * Created by wyd on 2017/4/28.
 */
public class CleanableEditText extends AppCompatEditText {
    /**
     * 默认大小
     */
    private int DEFAULT_SIZE = 15;
    /**
     * 图片大小
     */
    private int clear_image_size;

    private Drawable mRightDrawable;

    private boolean isFocus;

    public CleanableEditText(Context context) {
        super(context);
        init(context, null);
    }

    public CleanableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CleanableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CleanableEditText);
        clear_image_size = typedArray.getDimensionPixelSize(R.styleable.CleanableEditText_clear_size,
                dip2px(context, DEFAULT_SIZE));
        typedArray.recycle();

        Drawable[] drawables = this.getCompoundDrawables();
        mRightDrawable = drawables[2];
        if (mRightDrawable == null) {
            mRightDrawable = context.getResources().getDrawable(R.drawable.tx_edt_clearimage);
        }
        mRightDrawable.setBounds(0, 0, clear_image_size, clear_image_size);

        this.setOnFocusChangeListener(new FocusChangeListenerImpl());
        this.addTextChangedListener(new TextWatchImpl());
        // 默认不显示
        setCleanDrawableVisible(false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                int min = getWidth() - getTotalPaddingRight();
                int max = getWidth() - getPaddingRight();
                if (event.getX() > min && event.getX() < max) {
                    setText("");
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 焦点监听
     */
    private class FocusChangeListenerImpl implements OnFocusChangeListener {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            isFocus = hasFocus;
            if (isFocus) {
                boolean isShow = getText().toString().length() >= 1;
                setCleanDrawableVisible(isShow);
            } else {
                setCleanDrawableVisible(false);
            }
        }
    }

    /**
     * 输入框监听
     */
    private class TextWatchImpl implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            boolean isShow = getText().toString().length() >= 1;
            if (hasFocus()) {
                setCleanDrawableVisible(isShow);
            }
        }
    }

    /**
     * 设置图片是否显示
     *
     * @param isShow
     */
    private void setCleanDrawableVisible(boolean isShow) {
        Drawable rightDrawable;
        if (isShow) {
            rightDrawable = mRightDrawable;
        } else {
            rightDrawable = null;
        }
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1],
                rightDrawable, getCompoundDrawables()[3]);
    }

    /**
     * dp2px
     */
    private int dip2px(Context context, float dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }

    /**
     * 设置shake动画
     */
    public void setShakeAnimation() {
        this.setAnimation(shakeAnimation(5));
    }

    public Animation shakeAnimation(int CycleTimes) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 10);
        translateAnimation.setInterpolator(new CycleInterpolator(CycleTimes));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }

}
