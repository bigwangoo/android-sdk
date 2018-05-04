package com.bigwangoo.sample.common.activity;

import android.support.v4.app.Fragment;


import com.bigwangoo.sample.R;
import com.bigwangoo.sample.common.fragment.SampleFragment1;
import com.bigwangoo.sample.common.fragment.SampleFragment2;
import com.bigwangoo.sample.common.fragment.SampleFragment3;
import com.tianxiabuyi.kit.ui.activity.BaseTabActivity;

import java.util.ArrayList;

public class MainActivity extends BaseTabActivity {

    private SampleFragment1 fragment1 = new SampleFragment1();
    private SampleFragment2 fragment2 = new SampleFragment2();
    private SampleFragment3 fragment3 = new SampleFragment3();
    private SampleFragment3 fragment4 = new SampleFragment3();

    @Override
    protected String[] getTitles() {
        return new String[]{"首页", "联系人", "发现", "个人中心"};
    }

    @Override
    protected int[] getIconUnSelectIds() {
        return new int[]{
                R.drawable.menu_home_unselected,
                R.drawable.menu_patient_unselected,
                R.drawable.menu_mine_unselected,
                R.drawable.menu_mine_unselected
        };
    }

    @Override
    protected int[] getIconSelectIds() {
        return new int[]{
                R.drawable.menu_home_selected,
                R.drawable.menu_patient_selected,
                R.drawable.menu_mine_selected,
                R.drawable.menu_mine_selected
        };
    }

    @Override
    protected ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        fragments.add(fragment4);
        return fragments;
    }

}


