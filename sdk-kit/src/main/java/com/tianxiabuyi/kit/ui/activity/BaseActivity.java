package com.demo.common.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.demo.common.common.app.ActivityUtils;
import com.demo.common.ui.activity.impl.BaseActivityImpl;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 * activity 基类
 * <p>
 * Created by wyd on 2017/4/27.
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseActivityImpl {

    protected boolean isEventBusEnable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // activity 管理
        ActivityUtils.getInstance().addActivity(this);
        // 布局
        if (getViewByXml() > 0) {
            setContentView(getViewByXml());
        }
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isEventBusEnable) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityUtils.getInstance().finishActivity(this);

        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    /*
     * onStart() 之前调用
     */
    protected void setEventBusEnable() {
        isEventBusEnable = true;
    }

}

