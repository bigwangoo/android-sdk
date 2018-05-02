package com.tianxiabuyi.kit.ui.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 * Created by WangYD on 2017/6/16.
 */
public abstract class BaseFragment extends Fragment {

    protected View mView;
    protected boolean isEventBusEnable = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mView = inflater.inflate(getViewByXml(), container, false);
    }

    protected abstract int getViewByXml();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, mView);
        intView();
        initData();
    }

    protected abstract void intView();

    protected abstract void initData();

    @Override
    public void onStart() {
        super.onStart();
        if (isEventBusEnable) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public void setEventBusEnable(boolean enable) {
        this.isEventBusEnable = enable;
    }

}
