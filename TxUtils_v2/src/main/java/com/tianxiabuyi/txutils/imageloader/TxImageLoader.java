package com.tianxiabuyi.txutils.imageloader;

import android.content.Context;
import android.widget.ImageView;

import com.tianxiabuyi.txutils.R;
import com.tianxiabuyi.txutils.imageloader.universal.UniversalImageLoaderProvider;

/**
 * Created by xjh1994 on 2016/5/30.
 *
 * @description: 统一图片加载入口 TxImageLoader
 */
public class TxImageLoader {

    public static final int PIC_LARGE = 0;
    public static final int PIC_MEDIUM = 1;
    public static final int PIC_SMALL = 2;
    // 策略
    public static final int LOAD_STRATEGY_NORMAL = 0;
    public static final int LOAD_STRATEGY_ONLY_WIFI = 1;
    // 圆角
    public static final int ROUND_CORNER_RADIUS = 20;
    //占位图
    public static final int PLACEHOLDER = R.drawable.tx_ic_loading;

    private volatile static TxImageLoader mInstance;
    private BaseImageLoaderProvider mProvider;

    private TxImageLoader() {
        mProvider = new UniversalImageLoaderProvider();
    }

    public static TxImageLoader getInstance() {
        if (mInstance == null) {
            synchronized (TxImageLoader.class) {
                if (mInstance == null) {
                    mInstance = new TxImageLoader();
                    return mInstance;
                }
            }
        }
        return mInstance;
    }

    public synchronized void init(BaseImageLoaderProvider imageLoaderProvider) {
        this.mProvider = imageLoaderProvider;
    }

    ////////////////////// 自定义

    public void loadImage(Context context, CommonImageLoader img) {
        mProvider.loadImage(context, img);
    }

    ////////////////////// 常用

    public void loadImage(Context context, String url, ImageView imageView) {
        CommonImageLoader imageLoader = new CommonImageLoader.Builder()
                .url(url)
                .imgView(imageView)
                .build();
        mProvider.loadImage(context, imageLoader);
    }

    public void loadImage(Context context, String url, ImageView imageView, int placeHolder) {
        CommonImageLoader imageLoader = new CommonImageLoader.Builder()
                .url(url)
                .imgView(imageView)
                .placeHolder(placeHolder)
                .build();
        mProvider.loadImage(context, imageLoader);
    }

    public void loadCircleImage(Context context, String url, ImageView imageView) {
        CommonImageLoader imageLoader = new CommonImageLoader.Builder()
                .url(url)
                .imgView(imageView)
                .asCircle()
                .build();
        mProvider.loadImage(context, imageLoader);
    }

    public void loadCircleImage(Context context, String url, ImageView imageView, int placeHolder) {
        CommonImageLoader imageLoader = new CommonImageLoader.Builder()
                .url(url)
                .imgView(imageView)
                .placeHolder(placeHolder)
                .asCircle()
                .build();
        mProvider.loadImage(context, imageLoader);
    }

    public void loadRoundImage(Context context, String url, ImageView imageView, int roundRadius) {
        CommonImageLoader imageLoader = new CommonImageLoader.Builder()
                .url(url)
                .imgView(imageView)
                .asRound(roundRadius)
                .build();
        mProvider.loadImage(context, imageLoader);
    }

    public void loadRoundImage(Context context, String url, ImageView imageView, int roundRadius, int placeHolder) {
        CommonImageLoader imageLoader = new CommonImageLoader.Builder()
                .url(url)
                .imgView(imageView)
                .placeHolder(placeHolder)
                .asRound(roundRadius)
                .build();
        mProvider.loadImage(context, imageLoader);
    }
}
