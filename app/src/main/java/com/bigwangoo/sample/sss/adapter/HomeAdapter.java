package com.demo.sample.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.demo.sample.R;

import java.util.List;

/**
 * Created by YaoDong.Wang on 2017/7/27.
 */
public class HomeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public HomeAdapter(List<String> data) {
        super(R.layout.item_home, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_txt, item);
    }
}
