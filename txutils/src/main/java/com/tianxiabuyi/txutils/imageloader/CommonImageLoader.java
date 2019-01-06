package com.tianxiabuyi.txutils.imageloader;

import android.widget.ImageView;

/**
 * @author xjh1994
 * @date 2016/5/30
 */
public class CommonImageLoader {

    private int type;           //类型 TODO(大图，中图，小图)
    private String url;         //需要解析的url
    private ImageView imgView;  //ImageView的实例
    private int placeHolder;    //当没有成功加载的时候显示的图片
    private int strategy;       //加载策略，是否在wifi下才加载
    private boolean circle;     //圆形
    private boolean round;      //圆角
    private int roundRadius;    //圆角半径

    private CommonImageLoader(Builder builder) {
        this.type = builder.type;
        this.url = builder.url;
        this.placeHolder = builder.placeHolder;
        this.imgView = builder.imgView;
        this.strategy = builder.strategy;
        this.circle = builder.circle;
        this.round = builder.round;
        this.roundRadius = builder.roundRadius;
    }

    public int getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public ImageView getImgView() {
        return imgView;
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

    public static class Builder {
        private int type;
        private String url;
        private ImageView imgView;
        private int placeHolder;
        private int strategy;
        private boolean circle;
        private boolean round;
        private int roundRadius;

        public Builder() {
            this.type = TxImageLoader.PIC_SMALL;
            this.url = "";
            this.imgView = null;
            this.placeHolder = TxImageLoader.PLACEHOLDER;
            this.strategy = TxImageLoader.LOAD_STRATEGY_NORMAL;
            this.circle = false;
            this.round = false;
            this.roundRadius = TxImageLoader.ROUND_CORNER_RADIUS;
        }

        public Builder type(int type) {
            this.type = type;
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

        public Builder imgView(ImageView imgView) {
            this.imgView = imgView;
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

        public CommonImageLoader build() {
            return new CommonImageLoader(this);
        }

    }
}
