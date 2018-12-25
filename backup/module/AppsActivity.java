package com.bigwangoo.sample.module;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bigwangoo.sample.R;
import com.bigwangoo.sample.module.ele.EleHomeActivity;
import com.bigwangoo.sample.module.ele.activity.LoadingActivity;
import com.bigwangoo.sample.module.ele.activity.RefreshActivity;
import com.bigwangoo.sample.module.music.LoadManagerActivity;
import com.bigwangoo.sample.module.other.ProgressActivity;
import com.bigwangoo.sample.module.wx.RedPacketActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.mzule.activityrouter.annotation.Router;
import com.bigwangoo.sample.common.router.UrlRouter;
import com.bigwangoo.sample.kit.ui.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wang.yd on 2017/7/17.
 * apps
 */
@Router(UrlRouter.ACTIVITY_APPS)
public class AppsActivity extends BaseActivity implements BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.rv)
    RecyclerView rv;
    private ArrayList<String> mData;

    @Override
    public int getViewByXml() {
        return R.layout.apps_activity_apps;
    }

    @Override
    public void initView() {
        // mock
        setMockData();

        AppAdapter adapter = new AppAdapter(mData);
        adapter.setOnItemChildClickListener(this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
    }

    private void setMockData() {
        mData = new ArrayList<>();
        mData.add("饿了么");
        mData.add("列表");
        mData.add("刷新");
        mData.add("加载中");
        mData.add("view");
        mData.add("微信红包");
    }

    @Override
    public void initData() {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (position == 0) {
         EleHomeActivity.newInstance(AppsActivity.this);
        } else if (position == 1) {
             LoadManagerActivity.newInstance(AppsActivity.this);
        } else if (position == 2) {
             RefreshActivity.newInstance(AppsActivity.this);
        } else if (position == 3) {
             LoadingActivity.newInstance(AppsActivity.this);
        } else if (position == 4) {
             ProgressActivity.newInstance(AppsActivity.this);
        } else if (position == 5) {
        RedPacketActivity.newInstance(AppsActivity.this);
        }
    }

    /*adapter*/
    private class AppAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        AppAdapter(@Nullable List<String> data) {
            super(R.layout.apps_item_apps, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.tvClick, item).addOnClickListener(R.id.tvClick);
        }
    }

}