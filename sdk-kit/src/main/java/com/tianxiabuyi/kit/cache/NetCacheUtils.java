package com.tianxiabuyi.kit.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by YaoDong.Wang on 2017/7/27.
 */
public class NetCacheUtils {

    private LocalCacheUtils mLocalCacheUtils;

    private MemoryCacheUtils mMemoryCacheUtils;

    public NetCacheUtils(LocalCacheUtils localCacheUtils, MemoryCacheUtils memoryCacheUtils) {
        mLocalCacheUtils = localCacheUtils;
        mMemoryCacheUtils = memoryCacheUtils;
    }

    public void getBitmapFromNet(ImageView imageView, String url) {
        new BitmapTask().execute(imageView, url);
    }

    /**
     * 三个泛型意义:
     * 第一个泛型: doInBackground里的参数类型
     * 第二个泛型: onProgressUpdate里的参数类型
     * 第三个泛型: onPostExecute里的参数类型及doInBackground的返回类型
     */
    private class BitmapTask extends AsyncTask<Object, Integer, Bitmap> {

        private ImageView imageView;
        private String url;

        // 1.预加载, 运行在主线程
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        // 2.正在加载, 运行在子线程(核心方法), 可以直接异步请求
        @Override
        protected Bitmap doInBackground(Object... params) {
            imageView = (ImageView) params[0];
            url = (String) params[1];
            //imageView.setTag(url);
            Bitmap bitmap = download(url);
            //调用此方法实现进度更新(会回调onProgressUpdate)
            //publishProgress(values);
            return bitmap;
        }

        // 3.更新进度的方法, 运行在主线程
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        // 4.加载结束, 运行在主线程(核心方法), 可以直接更新UI
        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                //设置图片(此处校验, 判断是否是正确的图片)
                String url = (String) imageView.getTag();
                if (url.equals(this.url)) { //判断图片绑定的url是否就是当前bitmap的url,
                    imageView.setImageBitmap(result);
                    mLocalCacheUtils.setLocalCache(url, result);  //写本地缓存
                    mMemoryCacheUtils.setMemoryCache(url, result); //写内存缓存
                }
            }
            super.onPostExecute(result);
        }
    }

    /**
     * 下载图片
     *
     * @param url
     * @return
     */
    private Bitmap download(String url) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);// 连接超时
            conn.setReadTimeout(5000);// 读取超时
            conn.connect();
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                // 生成bitmap对象
                InputStream inputStream = conn.getInputStream();
                return BitmapFactory.decodeStream(inputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }

}
