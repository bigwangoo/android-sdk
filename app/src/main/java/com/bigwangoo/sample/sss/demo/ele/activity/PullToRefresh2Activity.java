package com.txby.sample_kit.demo.ele.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.demo.apps.R;
import com.demo.apps.R2;
import com.txby.sample_kit.demo.ele.adapter.RefreshAdapter;
import com.demo.common.ui.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by YaoDong.Wang on 2017/7/26.
 */
public class PullToRefresh2Activity extends BaseActivity {

    @BindView(R2.id.rv)
    RecyclerView mRecyclerView;

    private List<String> mData = new ArrayList<>();

    @Override
    public int getViewByXml() {
        return R.layout.apps_activity_refresh2;
    }

    @Override
    public void initView() {
        for (int i = 0; i < 100; i++) {
            mData.add("item" + i);
        }
    }

    @Override
    public void initData() {
        RefreshAdapter mAdapter = new RefreshAdapter(mData);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }
}
