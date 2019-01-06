package com.tianxiabuyi.txutils.imageloader;

import android.content.Context;
import android.widget.ImageView;

import com.tianxiabuyi.txutils.R;
import com.tianxiabuyi.txutils.imageloader.glide.GlideImageLoaderProvider;


/**
 * @author xjh1994
 * @date 2016/5/30
 * @description 统一图片加载入口
 */
public class TxImageLoader {

    // 占位图
    public static final int PLACEHOLDER = R.drawable.tx_loading;

    // 加载类型
    public static final int PIC_LARGE = 0;
    public static final int PIC_MEDIUM = 1;
    public static final int PIC_SMALL = 2;

    // 加载策略
    public static final int LOAD_STRATEGY_NORMAL = 0;
    public static final int LOAD_STRATEGY_ONLY_WIFI = 1;

    // 圆角半径
    public static final int ROUND_CORNER_RADIUS = 20;

    private static TxImageLoader mInstance;
    private BaseImageLoaderProvider mProvider;

    private TxImageLoader() {
        mProvider = new GlideImageLoaderProvider();
    }

    public static TxImageLoader getInstance() {
        if (mInstance == null) {
            synchronized (TxImageLoader.class) {
                if (mInstance == null) {
                    mInstance = new TxImageLoader();
                }
            }
        }
        return mInstance;
    }

    public synchronized void init(BaseImageLoaderProvider imageLoaderProvider) {
        this.mProvider = imageLoaderProvider;
    }

    /**
     * 默认
     */
    public void loadImage(Context context, CommonImageLoader img) {
        mProvider.loadImage(context, img);
    }

    public void loadImage(Context context, String url, ImageView imageView) {
        CommonImageLoader imageLoader = new CommonImageLoader.Builder()
                .url(url)
                .imgView(imageView)
                .build();
        mProvider.loadImage(context, imageLoader);
    }

    /**
     * 自定义 provider
     */
    public void loadImage(Context context, CommonImageLoader img, BaseImageLoaderProvider provider) {
        provider.loadImage(context, img);
    }

    public void loadImage(Context context, String url, ImageView imageView, BaseImageLoaderProvider provider) {
        CommonImageLoader imageLoader = new CommonImageLoader.Builder()
                .url(url)
                .imgView(imageView)
                .build();
        provider.loadImage(context, imageLoader);
    }

    /**
     * 圆形
     */
    public void loadImageCircle(Context context, String url, ImageView imageView) {
        CommonImageLoader imageLoader = new CommonImageLoader.Builder()
                .url(url)
                .imgView(imageView)
                .asCircle()
                .build();
        mProvider.loadImage(context, imageLoader);
    }

    public void loadImageCircle(Context context, String url, ImageView imageView, int placeHolder) {
        CommonImageLoader imageLoader = new CommonImageLoader.Builder()
                .url(url)
                .placeHolder(placeHolder)
                .imgView(imageView)
                .asCircle()
                .build();
        mProvider.loadImage(context, imageLoader);
    }

    /**
     * 圆角
     */
    public void loadImageRound(Context context, String url, ImageView imageView, int roundRadius) {
        CommonImageLoader imageLoader = new CommonImageLoader.Builder()
                .url(url)
                .imgView(imageView)
                .asRound(roundRadius)
                .build();
        mProvider.loadImage(context, imageLoader);
    }

    public void loadImageRound(Context context, String url, ImageView imageView, int placeHolder, int roundRadius) {
        CommonImageLoader imageLoader = new CommonImageLoader.Builder()
                .url(url)
                .placeHolder(placeHolder)
                .imgView(imageView)
                .asRound(roundRadius)
                .build();
        mProvider.loadImage(context, imageLoader);
    }
}
