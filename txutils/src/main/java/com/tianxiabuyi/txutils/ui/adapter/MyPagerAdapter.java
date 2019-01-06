package com.tianxiabuyi.txutils.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * description
 *
 * @author wangyd
 * @date 2019/1/6
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> mFragments;
    private String[] mTitles;

    public MyPagerAdapter(FragmentManager fm, String[] mTitles, ArrayList<Fragment> mFragments) {
        super(fm);
        this.mTitles = mTitles;
        this.mFragments = mFragments;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}