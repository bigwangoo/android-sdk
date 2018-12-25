package com.bigwangoo.sample.common.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * 图片工具类
 * Created by wang.yd on 2017/7/6.
 */
public class BitmapUtils {
    public static final String TAG = "BitmapUtils";
    private static boolean DEBUG = false;

    /**
     * 图片质量压缩
     *
     * @param src   x
     * @param maxKb x
     * @return Bitmap
     */
    public static Bitmap compressBitmap(Bitmap src, int maxKb) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        src.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        if (DEBUG)
            Log.i(TAG, "原始大小: " + baos.toByteArray().length);

        int options = 100;
        while (baos.toByteArray().length / 1024 > maxKb) {
            if (DEBUG)
                Log.i(TAG, "压缩一次");

            baos.reset();
            options -= 10;
            src.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        if (DEBUG)
            Log.i(TAG, "压缩后大小: " + baos.toByteArray().length);

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        return BitmapFactory.decodeStream(bais, null, null);
    }

    /**
     * 图片尺寸压缩
     *
     * @param res       x
     * @param resId     x
     * @param reqWidth  所需图片压缩尺寸最小宽度
     * @param reqHeight 所需图片压缩尺寸最小高度
     * @return Bitmap
     */
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * 图片尺寸压缩
     *
     * @param filepath  x
     * @param reqWidth  所需图片压缩尺寸最小宽度
     * @param reqHeight 所需图片压缩尺寸最小高度
     * @return Bitmap
     */
    public static Bitmap decodeSampledBitmapFromFile(String filepath, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filepath, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filepath, options);
    }

    /**
     * 图片尺寸压缩
     *
     * @param bitmap    x
     * @param reqWidth  所需图片压缩尺寸最小宽度
     * @param reqHeight 所需图片压缩尺寸最小高度
     * @return Bitmap
     */
    public static Bitmap decodeSampledBitmapFromBitmap(Bitmap bitmap, int reqWidth, int reqHeight) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, baos);
        byte[] data = baos.toByteArray();

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, 0, data.length, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(data, 0, data.length, options);
    }

    /**
     * 计算压缩比例值
     * .
     * 原版2>4>8...倍压缩
     * 当前2>3>4...倍压缩
     *
     * @param options   解析图片的配置信息
     * @param reqWidth  所需图片压缩尺寸最小宽度O
     * @param reqHeight 所需图片压缩尺寸最小高度
     * @return inSampleSize
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int picWidth = options.outWidth;
        final int picHeight = options.outHeight;
        if (DEBUG)
            Log.i(TAG, "原尺寸: " + picWidth + "*" + picHeight);

        int targetWidth = picWidth;
        int targetHeight = picHeight;
        int inSampleSize = 1;

        if (targetWidth > reqWidth || targetHeight > reqHeight) {
            while (targetWidth >= reqWidth && targetHeight >= reqHeight) {
                if (DEBUG)
                    Log.i(TAG, "压缩: " + inSampleSize + "倍");

                inSampleSize += 1;
                targetWidth = picWidth / inSampleSize;
                targetHeight = picHeight / inSampleSize;
            }
        }
        if (DEBUG) {
            Log.i(TAG, "最终压缩比例: " + inSampleSize + "倍");
            Log.i(TAG, "新尺寸: " + targetWidth + "*" + targetHeight);
        }
        return inSampleSize;
    }

    /**
     * 图片拼接
     *
     * @param width
     * @param height
     * @param src
     * @return
     */
    public static Bitmap createRepeatBitmap(int width, int height, Bitmap src) {
        int count = height / src.getHeight() + 1;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        for (int i = 0; i < count; i++) {
            canvas.drawBitmap(src, 0, i * src.getHeight(), null);
        }
        return bitmap;
    }

}