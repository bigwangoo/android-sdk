package com.demo.sample.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.demo.common.common.router.UrlRouter;
import com.demo.common.ui.Fragment.BaseFragment;
import com.demo.sample.R;
import com.demo.sample.adapter.HomeAdapter;
import com.demo.sample.model.MessageEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
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

    private List<String> mData = new ArrayList<>();

    @Override
    protected int getViewByXml() {
        return R.layout.fragment_sample_01;
    }

    @Override
    protected void intView() {
        setMockData();

        mAdapter = new HomeAdapter(mData);
        mAdapter.setOnItemClickListener(this);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(mAdapter);
    }

    private void setMockData() {
        mData.clear();
        mData.add("apps");
        mData.add("photo");
        mData.add("card");
        mData.add("通知");
        mData.add("CustomView");
    }

    @Override
    protected void initData() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(MessageEvent messageEvent) {
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        switch (position) {
            case 0:
                UrlRouter.openApps();
                break;
            case 1:
//                startActivity(new Intent(getActivity(), CardActivity.class));
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

}