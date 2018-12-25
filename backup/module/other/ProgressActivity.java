package com.bigwangoo.sample.module.other;

import android.content.Context;
import android.content.Intent;

import com.bigwangoo.sample.R;
import com.bigwangoo.sample.kit.ui.activity.BaseActivity;

/**
 * Created by YaoDong.Wang on 2017/7/21.
 */
public class ProgressActivity extends BaseActivity {

    public static void newInstance(Context context) {
        context.startActivity(new Intent(context, ProgressActivity.class));
    }

    @Override
    public int getViewByXml() {
        return R.layout.apps_activity_other_progress;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }
}
