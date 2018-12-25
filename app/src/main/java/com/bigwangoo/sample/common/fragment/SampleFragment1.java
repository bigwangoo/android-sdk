package com.bigwangoo.sample.common.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bigwangoo.sample.R;
import com.bigwangoo.sample.common.adapter.HomeAdapter;
import com.bigwangoo.sample.common.model.MessageEvent;
import com.bigwangoo.sample.module.db.DbActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tianxiabuyi.kit.router.UrlRouter;
import com.tianxiabuyi.kit.ui.Fragment.BaseFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wang.yd on 2017/7/12.
 */
public class SampleFragment1 extends BaseFragment implements BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.toolBar)
    Toolbar toobar;
    @BindView(R.id.rv)
    RecyclerView rv;

    private HomeAdapter mAdapter;

    @Override
    protected int getViewByXml() {
        return R.layout.fragment_sample_01;
    }

    @Override
    protected void intView() {
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new HomeAdapter(new ArrayList<String>());
        mAdapter.setOnItemClickListener(this);
        rv.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        setMockData();
    }

    private void setMockData() {
        List<String> mData = new ArrayList<>();
        mData.add("apps");
        mData.add("photo");
        mData.add("card");
        mData.add("通知");
        mData.add("CustomView");
        mAdapter.setNewData(mData);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        switch (position) {
            case 0:
                UrlRouter.openApps();
                break;
            case 1:
                startActivity(new Intent(getActivity(), DbActivity.class));
                break;
            case 2:

                UrlRouter.openNotify();
                break;
            case 3:
//                startActivity(new Intent(getActivity(), PhotoViewActivity.class));
                //                startActivity(new Intent(getActivity(), CustomViewActivity.class));
                break;
            case 4:

                break;
            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(MessageEvent messageEvent) {
    }
}