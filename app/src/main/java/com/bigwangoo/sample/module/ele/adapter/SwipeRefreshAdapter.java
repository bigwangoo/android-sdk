package com.bigwangoo.sample.module.ele.adapter;


import android.support.annotation.Nullable;

import com.bigwangoo.sample.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by wangyd on 2017/7/27.
 */

public class SwipeRefreshAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public SwipeRefreshAdapter(@Nullable List<String> data) {
        super(R.layout.apps_item_ele_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv, item);
    }
}
