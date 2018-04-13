package com.demo.sample.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.demo.sample.R;

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
