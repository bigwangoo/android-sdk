package com.tianxiabuyi.kit.ui.widget;

import android.content.Context;
import android.view.View;

import com.tianxiabuyi.kit.R;


/**
 * Created by WangYD on 2017/6/28.
 */
public class AddressPickerPopWindow extends BaseBottomPopWindow<String> {

    public AddressPickerPopWindow(Context context, String data) {
        super(context, data);
    }

    @Override
    View initContentView(String s) {
        View.inflate(mContext, R.layout.layout_pop_address, null);
        return null;
    }
}
