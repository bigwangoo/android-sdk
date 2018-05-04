package com.tianxiabuyi.kit.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.tianxiabuyi.kit.R;

import java.util.ArrayList;

/**
 * 通用tab界面
 * <p>
 * Created by wyd on 2017/4/28.
 */
public abstract class BaseTabActivity extends BaseActivity {

    protected CommonTabLayout mCommonTabLayout;

    protected ViewPager mViewPager;

    @Override
    public int getViewByXml() {
        return R.layout.activity_base_tab;
    }

    @Override
    public void initView() {
        // 数据
        String[] mTitles = getTitles();
        int[] iconUnSelectIds = getIconUnSelectIds();
        int[] iconSelectIds = getIconSelectIds();
        ArrayList<Fragment> mFragments = getFragments();

        ArrayList<CustomTabEntity> tabEntities = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            tabEntities.add(new TabEntity(mTitles[i], iconSelectIds[i], iconUnSelectIds[i]));
        }
        mCommonTabLayout = (CommonTabLayout) findViewById(R.id.tl_base);
        mCommonTabLayout.setTabData(tabEntities);
        mCommonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mViewPager = (ViewPager) findViewById(R.id.vp_base);
        mViewPager.setAdapter(new TabPagerAdapter(getSupportFragmentManager(), mFragments));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCommonTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setCurrentItem(0);
    }

    @Override
    public void initData() {

    }

    protected abstract String[] getTitles();

    protected abstract int[] getIconSelectIds();

    protected abstract int[] getIconUnSelectIds();

    protected abstract ArrayList<Fragment> getFragments();


    /**
     * adapter
     */
    private class TabPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragments;

        public TabPagerAdapter(FragmentManager fm, ArrayList<Fragment> mFragments) {
            super(fm);
            fragments = mFragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    /**
     * 导航条
     */
    private class TabEntity implements CustomTabEntity {

        public String title;
        public int selectedIcon;
        public int unSelectedIcon;

        public TabEntity(String title, int selectedIcon, int unSelectedIcon) {
            this.title = title;
            this.selectedIcon = selectedIcon;
            this.unSelectedIcon = unSelectedIcon;
        }

        @Override
        public String getTabTitle() {
            return title;
        }

        @Override
        public int getTabSelectedIcon() {
            return selectedIcon;
        }

        @Override
        public int getTabUnselectedIcon() {
            return unSelectedIcon;
        }
    }

}
