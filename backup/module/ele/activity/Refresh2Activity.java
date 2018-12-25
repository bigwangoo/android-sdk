package com.bigwangoo.sample.module.ele.activity;


import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bigwangoo.sample.R;
import com.bigwangoo.sample.module.ele.adapter.SwipeRefreshAdapter;
import com.bigwangoo.sample.module.ele.llistener.EndlessRecyclerOnScrollListener;
import com.bigwangoo.sample.kit.ui.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 第二种
 * 下拉 swiperefresh layout
 * 上拉 adapter
 * <p>
 * Created by wangyd on 2017/7/27.
 */

public class Refresh2Activity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    @BindView(R.id.rv)
    RecyclerView rv;
    private List<String> mList = new ArrayList<>();
    private SwipeRefreshAdapter adapter;

    public static void newInstance(Context context) {
        context.startActivity(new Intent(context, Refresh2Activity.class));
    }

    @Override
    public int getViewByXml() {
        return R.layout.apps_activity_ele_refresh;
    }

    @Override
    public void initView() {
        setData();

        adapter = new SwipeRefreshAdapter(mList);
        srl.setOnRefreshListener(this);
        srl.setColorSchemeResources(
                R.color.blue,
                R.color.gray,
                R.color.red,
                R.color.colorPrimary
        );
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(adapter);
        rv.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                simulateLoadMoreData();
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void onRefresh() {
        doRefresh();
    }

    /**
     * 下拉刷新
     */
    private void doRefresh() {
//        Observable.timer(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
//                .map(new Function<Long, Object>() {
//                    @Override
//                    public Object apply(Long aLong) throws Exception {
//                        fetchingNewData();
//
//                        srl.setRefreshing(false);
//                        adapter.notifyDataSetChanged();
//                        Toast.makeText(Refresh2Activity.this, "Refresh Finished!", Toast.LENGTH_SHORT).show();
//                        return null;
//                    }
//                }).subscribe();
    }

    private void fetchingNewData() {
        mList.add(0, "下拉刷新出来的数据");
    }

    /**
     * 上拉加载
     */
    private void simulateLoadMoreData() {
//        Observable.timer(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
//                .map(new Function<Long, Object>() {
//                    @Override
//                    public Object apply(Long aLong) throws Exception {
//                        loadMoreData();
//
//                        adapter.notifyDataSetChanged();
//                        Toast.makeText(Refresh2Activity.this, "Load Finished!", Toast.LENGTH_SHORT).show();
//                        return null;
//                    }
//                }).subscribe();
    }

    private void loadMoreData() {
        List<String> moreList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            moreList.add("加载更多的数据");
        }
        mList.addAll(moreList);
    }

    private void setData() {
        mList.clear();
        for (int i = 0; i < 20; i++) {
            mList.add("第" + i + "个");
        }
    }
}
