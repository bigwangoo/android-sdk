package com.tianxiabuyi.txutils.imageloader.glide;

import android.content.Context;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.tianxiabuyi.txutils.imageloader.BaseImageLoaderProvider;
import com.tianxiabuyi.txutils.imageloader.CommonImageLoader;
import com.tianxiabuyi.txutils.imageloader.TxImageLoader;
import com.tianxiabuyi.txutils.util.NetUtils;

/**
 * @author xjh1994
 * @date 2016/9/1
 */
public class GlideImageLoaderProvider extends BaseImageLoaderProvider {

    @Override
    public void loadImage(Context context, CommonImageLoader img) {
        if (TxImageLoader.LOAD_STRATEGY_ONLY_WIFI == img.getStrategy()
                && !NetUtils.isWifi(context)) {
            //只在WiFi下加载
            img.getImgView().setImageResource(img.getPlaceHolder());
            return;
        }

        // 加载
        DrawableRequestBuilder requestBuilder = Glide
                .with(context)
                .load(img.getUrl())
                .placeholder(img.getPlaceHolder());
        if (img.isCircle()) {
            // 圆形
            requestBuilder.bitmapTransform(new CropCircleTransformation(context));
        }
        if (img.isRound()) {
            // 圆角
            requestBuilder.bitmapTransform(new RoundedCornersTransformation(context, img.getRoundRadius(), 0));
        }
        requestBuilder.into(img.getImgView());
    }
}
