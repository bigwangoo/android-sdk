package com.wangyd.module.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wangyd.module.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author WangYaoDong
 * @date 2018/2/27 13:07
 * @description home
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tvStyle)
    public void onViewClicked() {
        Intent intent = new Intent(this, StyleActivity.class);
        startActivity(intent);
    }
}
