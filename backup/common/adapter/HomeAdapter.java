package com.bigwangoo.sample.common.adapter;

import com.bigwangoo.sample.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;


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
