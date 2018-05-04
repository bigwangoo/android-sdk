package com.bigwangoo.sample.common.adapter;

import android.support.annotation.Nullable;

import com.bigwangoo.sample.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;


import java.util.List;


/**
 * Created by wangyd on 2017/7/29.
 */

public class DiscoverAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public DiscoverAdapter(@Nullable List<String> data) {
        super(R.layout.item_discover, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_item, item);
    }
}
