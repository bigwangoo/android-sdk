package com.bigwangoo.sample.module.ele;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.RelativeLayout;


import com.bigwangoo.sample.R;
import com.bigwangoo.sample.module.ele.view.BounceLoadingView;
import com.tianxiabuyi.kit.R2;
import com.tianxiabuyi.kit.ui.activity.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ele home
 * Created by wang.yd on 2017/7/18.
 */
public class EleHomeActivity extends BaseActivity {

    @BindView(R.id.rl_search)
    RelativeLayout rlSearch;
    @BindView(R.id.blv_loading)
    BounceLoadingView loadingView;

    public static void newInstance(Context context) {
        context.startActivity(new Intent(context, EleHomeActivity.class));
    }

    @Override
    public int getViewByXml() {
        return R.layout.apps_activity_ele_home;
    }

    @Override
    public void initView() {
        //StatusBarUtil.setTranslucent(this, 0);

        loadingView.addBitmap(R.drawable.loading_v1);
        loadingView.addBitmap(R.drawable.loading_v2);
        loadingView.addBitmap(R.drawable.loading_v3);
        loadingView.addBitmap(R.drawable.loading_v4);
        loadingView.addBitmap(R.drawable.loading_v5);
        loadingView.addBitmap(R.drawable.loading_v6);
        loadingView.setShadowColor(Color.LTGRAY);
        loadingView.setDuration(5500);
        loadingView.start();
    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.rl_search)
    public void onClick() {
        Intent intent = new Intent(this,EleSearchActivity.class);
        int location[] = new int[2];
        rlSearch.getLocationOnScreen(location);
        intent.putExtra("x", location[0]);
        intent.putExtra("y", location[1]);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

}
