package com.demo.sample.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;

import com.demo.common.ui.activity.BaseActivity;
import com.demo.sample.R;

/**
 * 启动页面
 * Created by wyd on 2017/5/3.
 */
public class SplashActivity extends BaseActivity {

    private Handler handler = new Handler();

    private Runnable time;

    private int delayMillis = 1000;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        // 全屏
        if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getViewByXml() {
        return R.layout.activity_flash;
    }

    @Override
    public void initView() {
        time = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                SplashActivity.this.finish();
            }
        };
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (time != null) {
            handler.postDelayed(time, delayMillis);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (time != null) {
            handler.removeCallbacks(time);
        }
    }

}