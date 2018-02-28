package com.tianxiabuyi.txutils.network.model.bean;

import com.tianxiabuyi.txutils.network.model.BaseBean;

/**
 * Created by xjh1994 on 17/2/24.
 * 图像
 */
public class ImageBean extends BaseBean {
    /**
     * width : 640
     * height : 652
     * url : http://image.eeesys.com/big/2017/20170224/201702241505462313.jpg
     */

    private int width;
    private int height;
    private String url;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
