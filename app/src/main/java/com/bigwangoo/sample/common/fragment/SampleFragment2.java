package com.bigwangoo.sample.common.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bigwangoo.sample.R;
import com.bigwangoo.sample.common.adapter.DiscoverAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tianxiabuyi.kit.router.UrlRouter;
import com.tianxiabuyi.kit.ui.Fragment.BaseFragment;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wang.yd on 2017/7/12.
 */
public class SampleFragment2 extends BaseFragment implements BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.rv)
    RecyclerView mRv;
    private List<String> mData = new ArrayList<>();

    @Override
    protected int getViewByXml() {
        return R.layout.fragment_sample_02;
    }

    @Override
    protected void intView() {
        setData();

        DiscoverAdapter adapter = new DiscoverAdapter(mData);
        adapter.setOnItemClickListener(this);
        mRv.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        mRv.setAdapter(adapter);
    }

    private void setData() {
        String[] data = {"微信", "ele", "音乐", "其他"};
        for (int i = 0; i < data.length; i++) {
            mData.add(data[i]);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        switch (position) {
            case 0:
                UrlRouter.openApps();
                break;
        }
    }
}
