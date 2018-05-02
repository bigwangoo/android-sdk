package com.tianxiabuyi.kit.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.tianxiabuyi.kit.ui.IBaseActivity;
import com.tianxiabuyi.txutils.network.TxCall;
import com.tianxiabuyi.txutils.util.ActivityUtils;
import com.tianxiabuyi.txutils.util.TxStatusBarUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * activity 基类
 * <p>
 * Created by wyd on 2017/4/27.
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseActivity {

    private InputMethodManager inputMethodManager;

    private List<TxCall> mCalls = new ArrayList<>();

    private boolean isEventBusEnabled = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        beforeOnCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        if (!isTaskRoot()) {
            Intent intent = getIntent();
            String action = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                finish();
                return;
            }
        }
        afterOnCreate(savedInstanceState);
        // activity 管理
        ActivityUtils.getInstance().addActivity(this);
        // 布局
        if (getViewByXml() != 0) {
            setContentView(getViewByXml());
        }

        ButterKnife.bind(this);

        init();
    }

    protected void init() {
        initView();
        initData();
    }

    @Override
    public abstract int getViewByXml();

    @Override
    public abstract void initView();

    @Override
    public abstract void initData();

    /**
     * 开启EventBus, onStart()之前调用
     */
    public void setEventBusEnabled() {
        isEventBusEnabled = true;
    }

    /**
     * 基类ActivityOnCreate之前
     */
    protected void beforeOnCreate(Bundle savedInstanceState) {
        // 主题
        setAppTheme();
        // 设置status 黑色字体
        TxStatusBarUtil.setStatusBarLightMode(this);
    }

    /**
     * 基类Activity OnCreate之后，setContentView之前
     */
    protected void afterOnCreate(Bundle savedInstanceState) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isEventBusEnabled) {
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
        if (mCalls != null) {
            for (int i = 0; i < mCalls.size(); i++) {
                TxCall call = mCalls.get(i);
                if (call != null && !call.isCanceled()) {
                    call.cancel();
                }
            }
        }
        mCalls = null;
        inputMethodManager = null;
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        ActivityUtils.getInstance().finishActivity(this);
    }

    /**
     * 添加网络请求, onDestroy取消
     */
    public void addCallList(TxCall call) {
        if (mCalls != null) {
            mCalls.add(call);
        }
    }

    protected void hideSoftKeyboard() {
        if (inputMethodManager == null) {
            inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null && inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    public String getStringExtra(String key) {
        return getIntent().getStringExtra(key);
    }

    public Serializable getObjectExtra(String key) {
        return getIntent().getSerializableExtra(key);
    }

    private void setAppTheme() {
//        switch (BuildConfig.THEME) {
//            case 1:
//                setTheme(R.style.theme_1);
//                break;
//            case 2:
//                setTheme(R.style.theme_2);
//                break;
//            default:
//                break;
//        }
    }
}
