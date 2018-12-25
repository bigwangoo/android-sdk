package com.bigwangoo.sample.module.db;

import android.support.annotation.Nullable;

import com.bigwangoo.sample.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author wangyd
 * @date 2018/11/28
 * @description description
 */
public class DbAdapter extends BaseQuickAdapter<DbTest, BaseViewHolder> {
    public DbAdapter(@Nullable List<DbTest> data) {
        super(R.layout.item_db_test, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DbTest item) {
        helper.setText(R.id.tv_content, item.toString());
    }
}
