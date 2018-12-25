package com.bigwangoo.sample.common.fragment;

import android.widget.Button;
import android.widget.TextView;


import com.bigwangoo.sample.R;
import com.bigwangoo.sample.common.utils.CacheManagerUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wang.yd on 2017/7/12.
 */
public class SampleFragment3 extends BaseFragment {

    @BindView(R.id.tvCacheSize)
    TextView tvCacheSize;
    @BindView(R.id.btnCacheClean)
    Button btnCacheClean;

    @Override
    protected int getViewByXml() {
        return R.layout.fragment_sample_03;
    }

    @Override
    protected void intView() {
        String totalCacheSize = CacheManagerUtils.getTotalCacheSize(getActivity());
        tvCacheSize.setText(totalCacheSize);
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.btnCacheClean)
    public void onClick() {
        CacheManagerUtils.clearAllCache(getActivity());

        String totalCacheSize = CacheManagerUtils.getTotalCacheSize(getActivity());
        tvCacheSize.setText(totalCacheSize);
    }
}
