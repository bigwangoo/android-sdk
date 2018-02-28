package com.tianxiabuyi.txutils.imageloader;

import android.widget.ImageView;

/**
 * Created by xjh1994 on 2016/5/30.
 *
 * @description: ImageLoader
 */
public class CommonImageLoader {

    private int type;               //类型 (大图，中图，小图) TODO
    private int strategy;           //加载策略，是否在wifi下才加载
    private ImageView imgView;      //ImageView的实例
    private String url;             //需要解析的url
    private int placeHolder;        //当没有成功加载的时候显示的图片
    private boolean circle;         //圆
    private boolean round;          //圆角
    private int roundRadius;        //圆角半径
    private Size size;
    private boolean bitmap;

    public CommonImageLoader(Builder builder) {
        this.type = builder.type;
        this.strategy = builder.strategy;
        this.imgView = builder.imgView;
        this.url = builder.url;
        this.placeHolder = builder.placeHolder;
        this.circle = builder.circle;
        this.round = builder.round;
        this.roundRadius = builder.roundRadius;
        this.size = builder.size;
        this.bitmap = builder.bitmap;
    }

    public int getType() {
        return type;
    }

    public ImageView getImgView() {
        return imgView;
    }

    public String getUrl() {
        return url;
    }

    public int getPlaceHolder() {
        return placeHolder;
    }

    public int getStrategy() {
        return strategy;
    }

    public boolean isCircle() {
        return circle;
    }

    public boolean isRound() {
        return round;
    }

    public int getRoundRadius() {
        return roundRadius;
    }

    public Size getSize() {
        return size;
    }

    public boolean isBitmap() {
        return bitmap;
    }

    public static class Builder {
        private int type;
        private ImageView imgView;
        private String url;
        private int placeHolder;
        private int strategy;
        private boolean circle;
        private boolean round;
        private int roundRadius;
        private Size size;
        private boolean bitmap;

        public Builder() {
            this.type = TxImageLoader.PIC_SMALL;
            this.strategy = TxImageLoader.LOAD_STRATEGY_NORMAL;
            this.url = "";
            this.placeHolder = TxImageLoader.PLACEHOLDER;
            this.imgView = null;
            this.circle = false;
            this.round = false;
            this.roundRadius = TxImageLoader.ROUND_CORNER_RADIUS;
            this.size = null;
            this.bitmap = false;
        }

        public Builder type(int type) {
            this.type = type;
            return this;
        }

        public Builder imgView(ImageView imgView) {
            this.imgView = imgView;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder placeHolder(int placeHolder) {
            this.placeHolder = placeHolder;
            return this;
        }

        public Builder strategy(int strategy) {
            this.strategy = strategy;
            return this;
        }

        public Builder asCircle() {
            this.circle = true;
            return this;
        }

        public Builder asRound(int roundRadius) {
            this.round = true;
            this.roundRadius = roundRadius;
            return this;
        }

        public Builder size(Size size) {
            this.size = size;
            return this;
        }

        public Builder bitmap(boolean bitmap) {
            this.bitmap = bitmap;
            return this;
        }

        public CommonImageLoader build() {
            return new CommonImageLoader(this);
        }

    }

    public static class Size {

        public Size(int width, int height) {
            this.width = width;
            this.height = height;
        }

        int width;
        int height;

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
    }
}
