package com.tianxiabuyi.txutils.ui.base.list;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianxiabuyi.txutils.R;
import com.tianxiabuyi.txutils.ui.base.activity.BaseTxTitleActivity;

/**
 * 通用列表界面 加载更多
 *
 * @author xjh1994
 * @date 2016/8/26
 */
public abstract class BaseTxListLoadMoreActivity<T> extends BaseTxTitleActivity
        implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.RequestLoadMoreListener {

    protected RecyclerView mRv;
    protected BaseQuickAdapter<T, BaseViewHolder> mAdapter;

    @Override
    public int getViewByXml() {
        return R.layout.tx_activity_base_list;
    }

    @Override
    public void initView() {
        mRv = (RecyclerView) findViewById(R.id.rv);
        setLayoutManager();
        mAdapter = getBaseAdapter();
        mAdapter.setOnLoadMoreListener(this, mRv);
        mRv.setAdapter(mAdapter);
    }

    @Override
    public void initData() {

    }

    protected void setLayoutManager() {
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.addItemDecoration(new TxDividerItemDecoration(this, TxDividerItemDecoration.VERTICAL_LIST));
    }

    protected abstract BaseQuickAdapter<T, BaseViewHolder> getBaseAdapter();
}

