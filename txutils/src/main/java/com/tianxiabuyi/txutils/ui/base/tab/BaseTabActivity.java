package com.tianxiabuyi.txutils.ui.base.tab;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.tianxiabuyi.txutils.R;
import com.tianxiabuyi.txutils.ui.adapter.MyPagerAdapter;
import com.tianxiabuyi.txutils.ui.base.activity.BaseTxActivity;

import java.util.ArrayList;

/**
 * 通用Tab界面
 *
 * @author xjh1994
 * @date 2016/7/6
 */
public abstract class BaseTabActivity extends BaseTxActivity {

    protected ViewPager mViewPager;
    protected CommonTabLayout mTabLayout;

    @Override
    public int getViewByXml() {
        return R.layout.tx_activity_base_tab;
    }

    @Override
    public void initView() {
        ArrayList<Fragment> mFragments = getFragments();
        String[] mTitles = getTitles();
        int[] iconSelectIds = getIconSelectIds();
        int[] iconUnSelectIds = getIconUnselectIds();
        int currentItem = getCurrentItem();

        ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], iconSelectIds[i], iconUnSelectIds[i]));
        }

        mViewPager = (ViewPager) findViewById(R.id.vp);
        mViewPager.setOffscreenPageLimit(mFragments.size() - 1);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), mTitles, mFragments));

        mTabLayout = (CommonTabLayout) findViewById(R.id.tl);
        mTabLayout.setTabData(mTabEntities);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setCurrentItem(currentItem);
    }

    @Override
    public void initData() {

    }

    private int getCurrentItem() {
        return 0;
    }

    protected abstract String[] getTitles();

    protected abstract int[] getIconSelectIds();

    protected abstract int[] getIconUnselectIds();

    protected abstract ArrayList<Fragment> getFragments();
}
