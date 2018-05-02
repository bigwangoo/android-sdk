package com.txby.sample_kit.demo.ele.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.demo.apps.R;

import java.util.List;

/**
 * Created by YaoDong.Wang on 2017/7/26.
 */
public class RefreshAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public RefreshAdapter(@Nullable List<String> data) {
        super(R.layout.apps_item_refresh, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tvTxt, item);
    }
}