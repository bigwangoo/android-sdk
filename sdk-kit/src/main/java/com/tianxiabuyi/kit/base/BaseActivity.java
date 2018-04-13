package com.demo.common.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.tianxiabuyi.txutils.network.TxCall;
import com.tianxiabuyi.txutils.util.ActivityUtils;
import com.tianxiabuyi.txutils.util.TxStatusBarUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * description
 *
 * @author WangYaoDong
 * @date 2018/3/23 15:40
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseActivity {

    protected InputMethodManager inputMethodManager;

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
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        afterOnCreate(savedInstanceState);
        ActivityUtils.getInstance().addActivity(this);

        if (getViewByXml() != 0) {
            setContentView(getViewByXml());
        }

        ButterKnife.bind(this);

        init();

        if (isEventBusEnabled) {
            EventBus.getDefault().register(this);
        }
    }

    protected void init() {
        initView();
        initData();
    }

    protected abstract int getViewByXml();

    public abstract void initView();

    @Override
    public abstract void initData();


    /**
     * 开启EventBus
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
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 添加网络请求, onDestroy取消
     *
     * @param call TxCall
     */
    public void addCallList(TxCall call) {
        if (mCalls != null) {
            mCalls.add(call);
        }
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
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        inputMethodManager = null;
        ActivityUtils.getInstance().finishActivity(this);
    }


    protected void hideSoftKeyboard() {
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
        /*switch (BuildConfig.THEME) {
            case 1:
                setTheme(R.style.theme_1);
                break;
            case 2:
                setTheme(R.style.theme_2);
                break;
            default:
                break;
        }*/
    }
}
