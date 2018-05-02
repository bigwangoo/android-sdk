package com.tianxiabuyi.kit.cache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * 内存缓存
 * <p>
 * LruCache least recently used 最近最少使用算法 可以将最近最少使用的对象回收掉, 从而保证内存不会超出范围
 * <p>
 * Created by YaoDong.Wang on 2017/7/27.
 */
public class MemoryCacheUtils {

    private static final String TAG = MemoryCacheUtils.class.getSimpleName();

    private LruCache<String, Bitmap> mMemoryCache;

    public MemoryCacheUtils() {
        long maxMemory = Runtime.getRuntime().maxMemory();// 获取分配给app的内存大小
        // Log.d(TAG, "maxMemory:" + maxMemory);
        mMemoryCache = new LruCache<String, Bitmap>((int) (maxMemory / 8)) {
            // 返回每个对象的大小
            @Override
            protected int sizeOf(String key, Bitmap value) {
                // int byteCount = value.getByteCount();
                // 计算图片大小:每行字节数*高度
                return value.getRowBytes() * value.getHeight();
            }
        };
    }

    /**
     * 写缓存
     */
    public void setMemoryCache(String url, Bitmap bitmap) {
        mMemoryCache.put(url, bitmap);
    }

    /**
     * 读缓存
     */
    public Bitmap getMemoryCache(String url) {
        return mMemoryCache.get(url);
    }

}
