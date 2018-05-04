package com.bigwangoo.sample.module.other;

import android.os.AsyncTask;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigwangoo.sample.R;
import com.tianxiabuyi.kit.R2;
import com.tianxiabuyi.kit.ui.activity.BaseActivity;
import com.tianxiabuyi.kit.ui.view.ProgressArc;
import com.tianxiabuyi.kit.utils.DisplayUtils;

/**
 * 自定义View 界面
 * <p>
 * Created by wyd on 2017/4/28.
 */
public class CustomViewActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 下载相关
     */
    private RelativeLayout action_layout;
    private FrameLayout progress_layout;
    private ProgressArc progress_downloading;
    private TextView progress_txt;

    @Override
    public int getViewByXml() {
        return R.layout.apps_activtiy_view;
    }

    @Override
    public void initView() {
        // 下载action
        action_layout = (RelativeLayout) findViewById(R.id.item_action);
        action_layout.setOnClickListener(this);
        // 自定义进度
        progress_layout = (FrameLayout) findViewById(R.id.action_progress);
        progress_downloading = new ProgressArc(this);
        progress_downloading.setArcDiameter(DisplayUtils.dip2px(26));
        progress_downloading.setProgressColor(DisplayUtils.getColor(R.color.colorAccent));
        progress_downloading.setProgress(0, true);
        progress_downloading.setForegroundResource(R.drawable.ele_ic_search_gray);
        int size = DisplayUtils.dip2px(27);
        progress_layout.addView(progress_downloading, new FrameLayout.LayoutParams(size, size));
        progress_txt = (TextView) findViewById(R.id.action_txt);

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_action:
                new DownloadTask().execute();
                break;
        }
    }


    class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

    }

    private void refreshProgressState(int state, float progress) {

        switch (state) {
            case ProgressArc.STYLE_NONE:
                break;
            case ProgressArc.STYLE_DOWNLOADING:
                break;
            case ProgressArc.STYLE_WAITING:
                break;
            default:
                break;
        }

    }
}
