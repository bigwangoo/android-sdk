package com.txby.sample_kit.demo.ele;

import android.animation.ValueAnimator;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demo.apps.R;
import com.demo.apps.R2;
import com.demo.common.ui.activity.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wang.yd on 2017/7/18.
 */
public class EleSearchActivity extends BaseActivity {

    @BindView(R2.id.rl_title)
    RelativeLayout mRlSearchTitle;
    @BindView(R2.id.rl_search_content)
    RelativeLayout mRlSearchContent;
    @BindView(R2.id.tv_location)
    TextView mTvLocation;
    @BindView(R2.id.tvRecommend)
    TextView mTvRecommend;

    @BindView(R2.id.iv_back)
    ImageView mIvBack;
    @BindView(R2.id.tv_search)
    TextView mTvSearch;
    @BindView(R2.id.fl_content)
    TextView mFlResult;

    float frameBgHeight = 0;
    float searchBgHeight = 0;

    @Override
    public int getViewByXml() {
        return R.layout.apps_activity_ele_search;
    }

    @Override
    public void initView() {
        mRlSearchContent.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mRlSearchContent.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                        performEnterAnimation();
                    }
                });
    }

    @Override
    public void initData() {

    }

    @OnClick(R2.id.iv_back)
    public void onClick() {
        onBackPressed();
    }

    private void performEnterAnimation() {
        float originY = getIntent().getIntExtra("y", 0);

        int location[] = new int[2];
        mRlSearchContent.getLocationOnScreen(location);

        final float translateY = originY - (float) location[1];
        frameBgHeight = mRlSearchTitle.getHeight();

        //放到前一个页面的位置
        mRlSearchContent.setY(mRlSearchContent.getY() + translateY);
        searchBgHeight = mRlSearchContent.getY();
        final ValueAnimator translateVa = ValueAnimator.ofFloat(searchBgHeight, searchBgHeight - 100);
        translateVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mRlSearchContent.setY((Float) valueAnimator.getAnimatedValue());
                ViewGroup.LayoutParams linearParams = mRlSearchTitle.getLayoutParams();
                linearParams.height = (int) (frameBgHeight - (searchBgHeight - (Float) valueAnimator.getAnimatedValue()) * 2);
                mRlSearchTitle.setLayoutParams(linearParams);
            }
        });

        ValueAnimator scaleVa = ValueAnimator.ofFloat(1, 0.8f);
        scaleVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mRlSearchContent.setScaleX((Float) valueAnimator.getAnimatedValue());
            }
        });

        ValueAnimator alphaVa = ValueAnimator.ofFloat(0, 1f);
        alphaVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mRlSearchTitle.setAlpha((Float) valueAnimator.getAnimatedValue());
                mIvBack.setAlpha((Float) valueAnimator.getAnimatedValue());
                mTvSearch.setAlpha((Float) valueAnimator.getAnimatedValue());
            }
        });
        ValueAnimator alphaVa2 = ValueAnimator.ofFloat(1f, 0);
        alphaVa2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mTvLocation.setAlpha((Float) valueAnimator.getAnimatedValue());
                mTvRecommend.setAlpha((Float) valueAnimator.getAnimatedValue());
            }
        });
        alphaVa.setDuration(500);
        alphaVa2.setDuration(300);
        translateVa.setDuration(500);
        scaleVa.setDuration(500);

        alphaVa.start();
        alphaVa2.start();
        translateVa.start();
        scaleVa.start();
    }

    @Override
    public void onBackPressed() {
        performExitAnimation();
    }

    private void performExitAnimation() {
//        float originY = getIntent().getIntExtra("y", 0);
//
//        int location[] = new int[2];
//        mSearchBGTxt.getLocationOnScreen(location);
//
//        final float translateY = originY - (float) location[1];
//
//
//        final ValueAnimator translateVa = ValueAnimator.ofFloat(mSearchBGTxt.getY(), mSearchBGTxt.getY() + translateY);
//        translateVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                mSearchBGTxt.setY((Float) valueAnimator.getAnimatedValue());
//                mbackIv.setY(mSearchBGTxt.getY() + (mSearchBGTxt.getHeight() - mbackIv.getHeight()) / 2);
//                mHintTxt.setY(mSearchBGTxt.getY() + (mSearchBGTxt.getHeight() - mHintTxt.getHeight()) / 2);
//                mSearchTxt.setY(mSearchBGTxt.getY() + (mSearchBGTxt.getHeight() - mSearchTxt.getHeight()) / 2);
//                ViewGroup.LayoutParams linearParams = frameBg.getLayoutParams(); //取控件textView当前的布局参数
//                linearParams.height = (int) (frameBgHeight - (searchBgHeight - (Float) valueAnimator.getAnimatedValue()) * 2);
//                frameBg.setLayoutParams(linearParams);
//            }
//        });
//        translateVa.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animator) {
//                finish();
//                overridePendingTransition(0, 0);
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animator) {
//
//            }
//        });
//
//        ValueAnimator scaleVa = ValueAnimator.ofFloat(0.8f, 1f);
//        scaleVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                mSearchBGTxt.setScaleX((Float) valueAnimator.getAnimatedValue());
//            }
//        });
//
//        ValueAnimator alphaVa = ValueAnimator.ofFloat(1, 0f);
//        alphaVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                mContentFrame.setAlpha((Float) valueAnimator.getAnimatedValue());
//                mbackIv.setAlpha((Float) valueAnimator.getAnimatedValue());
//                mSearchTxt.setAlpha((Float) valueAnimator.getAnimatedValue());
//            }
//        });
//        ValueAnimator alphaVa2 = ValueAnimator.ofFloat(0, 1f);
//        alphaVa2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                locationTv.setAlpha((Float) valueAnimator.getAnimatedValue());
//                recommandTv.setAlpha((Float) valueAnimator.getAnimatedValue());
//            }
//        });
//
//        alphaVa.setDuration(500);
//        alphaVa2.setDuration(500);
//        translateVa.setDuration(500);
//        scaleVa.setDuration(500);
//        alphaVa.start();
//        alphaVa2.start();
//        translateVa.start();
//        scaleVa.start();
    }
}

