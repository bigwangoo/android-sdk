package com.demo.common.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 本地缓存
 * Created by YaoDong.Wang on 2017/7/27.
 */
public class LocalCacheUtils {

    private static final String LOCAL_CACHE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/cache";

    /**
     * 写本地缓存
     *
     * @param url
     * @param bitmap
     */
    public void setLocalCache(String url, Bitmap bitmap) {
        File dir = new File(LOCAL_CACHE_PATH);
        if (!dir.exists() || !dir.isDirectory()) {
            boolean mkdirs = dir.mkdirs();
        }

        try {
            String fileName = MD5Encoder.encode(url);
            File cacheFile = new File(dir, fileName);
            // 参1:图片格式;参2:压缩比例0-100; 参3:输出流
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(cacheFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读本地缓存
     *
     * @param url
     * @return
     */
    public Bitmap getLocalCache(String url) {
        try {
            File cacheFile = new File(LOCAL_CACHE_PATH, MD5Encoder.encode(url));
            if (cacheFile.exists()) {
                return BitmapFactory.decodeStream(new FileInputStream(cacheFile));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
