package com.bigwangoo.sample.module.wx;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.bigwangoo.sample.R;
import com.bigwangoo.sample.kit.ui.activity.BaseActivity;
import com.bigwangoo.sample.kit.ui.activity.DragPhotoActivity;

import java.util.ArrayList;

/**
 * 仿微信 朋友圈图片浏览
 * Created by wyd on 2017/5/4.
 */
public class PhotoViewActivity extends BaseActivity {


    @Override
    public int getViewByXml() {
        return R.layout.apps_activity_weixin;
    }

    @Override
    public void initView() {

        TextView tv = (TextView) findViewById(R.id.tv_1);
    }

    @Override
    public void initData() {

    }

    public void onClick(View view) {
        ArrayList<String> list = new ArrayList<>();
        list.add("http://img.my.csdn.net/uploads/201508/05/1438760758_3497.jpg");
        list.add("http://img.my.csdn.net/uploads/201508/05/1438760758_6667.jpg");
        list.add("http://img.my.csdn.net/uploads/201508/05/1438760755_6715.jpeg");

        startDragPhotoActivity(PhotoViewActivity.this, (ImageView) view, list);
    }

    /**
     * 图片点击打开效果
     *
     * @param context
     * @param view
     */
    private void startDragPhotoActivity(Context context, ImageView view, ArrayList<String> list) {
        // 获取图片在屏幕上位置
        int[] location = new int[2];
        view.getLocationOnScreen(location);

        Intent intent = new Intent(context, DragPhotoActivity.class);
        intent.putExtra(DragPhotoActivity.LEFT, location[0]);
        intent.putExtra(DragPhotoActivity.TOP, location[1]);
        intent.putExtra(DragPhotoActivity.RIGHT, view.getWidth());
        intent.putExtra(DragPhotoActivity.BOTTOM, view.getHeight());
        intent.putStringArrayListExtra(DragPhotoActivity.LIST, list);

        startActivity(intent);
        // 去除过渡动画
        overridePendingTransition(0, 0);
    }
}
