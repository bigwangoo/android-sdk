package com.txby.sample_kit.demo.other;

import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.widget.EditText;

import com.demo.apps.R;
import com.demo.apps.R2;
import com.demo.common.receiver.SmsContentObserver;
import com.demo.common.ui.activity.BaseActivity;

import butterknife.BindView;

/**
 * Created by wangyd on 2017/7/30.
 */

public class SmsActivity extends BaseActivity {
    @BindView(R2.id.tv_code)
    EditText mTvCode;

    private SmsContentObserver mSmsContentObserver;
    private Uri mUri;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    String code = msg.obj.toString();
                    mTvCode.setText(code);
                    break;
            }
        }
    };

    @Override
    public int getViewByXml() {
        return R.layout.apps_activity_other_sms;
    }

    @Override
    public void initView() {
        mUri = Uri.parse("content://sms");
        mSmsContentObserver = new SmsContentObserver(this, mHandler, 0);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        getContentResolver().registerContentObserver(mUri, false, mSmsContentObserver);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getContentResolver().unregisterContentObserver(mSmsContentObserver);
    }
}
