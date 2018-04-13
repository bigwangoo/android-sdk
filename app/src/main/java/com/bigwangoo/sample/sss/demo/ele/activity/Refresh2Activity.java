package com.txby.sample_kit.demo.ele.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.demo.apps.R;
import com.demo.apps.R2;
import com.txby.sample_kit.demo.ele.adapter.SwipeRefreshAdapter;
import com.txby.sample_kit.demo.ele.llistener.EndlessRecyclerOnScrollListener;
import com.demo.common.ui.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;

/**
 * 第二种
 * 下拉 swiperefresh layout
 * 上拉 adapter
 * <p>
 * Created by wangyd on 2017/7/27.
 */

public class Refresh2Activity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R2.id.srl)
    SwipeRefreshLayout srl;
    @BindView(R2.id.rv)
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
        Observable.timer(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .map(new Function<Long, Object>() {
                    @Override
                    public Object apply(Long aLong) throws Exception {
                        fetchingNewData();

                        srl.setRefreshing(false);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(Refresh2Activity.this, "Refresh Finished!", Toast.LENGTH_SHORT).show();
                        return null;
                    }
                }).subscribe();
    }

    private void fetchingNewData() {
        mList.add(0, "下拉刷新出来的数据");
    }

    /**
     * 上拉加载
     */
    private void simulateLoadMoreData() {
        Observable.timer(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .map(new Function<Long, Object>() {
                    @Override
                    public Object apply(Long aLong) throws Exception {
                        loadMoreData();

                        adapter.notifyDataSetChanged();
                        Toast.makeText(Refresh2Activity.this, "Load Finished!", Toast.LENGTH_SHORT).show();
                        return null;
                    }
                }).subscribe();
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
