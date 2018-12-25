package com.bigwangoo.sample.module.ele.activity;

import android.content.Context;
import android.content.Intent;

import com.bigwangoo.sample.R;
import com.bigwangoo.sample.kit.ui.activity.BaseActivity;


/**
 * Created by wangyd on 2017/8/5.
 */

public class LoadingActivity extends BaseActivity {

    public static void newInstance(Context context) {
        context.startActivity(new Intent(context, LoadingActivity.class));
    }

    @Override
    public int getViewByXml() {
        return R.layout.apps_activity_ele_loading;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }
}
