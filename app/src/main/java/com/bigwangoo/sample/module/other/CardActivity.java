package com.bigwangoo.sample.module.other;


import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.bigwangoo.sample.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianxiabuyi.kit.R2;
import com.tianxiabuyi.kit.ui.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wang.yd on 2017/7/6.
 */
public class CardActivity extends BaseActivity {

    @BindView(R.id.llCardContent)
    LinearLayout llCardContent;
    @BindView(R.id.rvTxtContent)
    RecyclerView rvTxtContent;

    private CardAdapter mAdapter;
    private List<String> mData;

    @Override
    public int getViewByXml() {
        return R.layout.apps_activity_card;
    }

    @Override
    public void initView() {
        mData = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mData.add("第" + i + "条数据");
        }

        llCardContent = (LinearLayout) findViewById(R.id.llCardContent);
        rvTxtContent = (RecyclerView) findViewById(R.id.rvTxtContent);

        mAdapter = new CardAdapter(mData);
        rvTxtContent.setLayoutManager(new LinearLayoutManager(this));
        rvTxtContent.setAdapter(mAdapter);
    }

    @Override
    public void initData() {

    }

    private class CardAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        CardAdapter(@Nullable List<String> data) {
            super(R.layout.apps_item_txt, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.tvTxt, item);
        }
    }
}
