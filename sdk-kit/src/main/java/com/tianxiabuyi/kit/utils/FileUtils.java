package com.tianxiabuyi.kit.utils;

import android.os.Environment;

import com.tianxiabuyi.txutils.TxUtils;

import java.io.File;

/**
 * 文件工具类
 * <p>
 * Created by wyd on 2017/4/27.
 */
public class FileUtils {
    /**
     * sd卡下文件夹
     */
    public static final String ROOT_DIR = "module";
    /**
     * 保存图片的路径
     */
    public static final String ICON_DIR = "icon";
    /**
     * 缓存的文件
     */
    public static final String CACHE_DIR = "cache";
    /**
     * 下载文件的路径
     */
    public static final String DOWNLOAD_DIR = "download";

    /**
     * 获取图片的缓存的路径
     */
    public static String getIconDir() {
        return getDir(ICON_DIR);
    }

    /**
     * 获取缓存路径
     */
    public static String getCacheDir() {
        return getDir(CACHE_DIR);
    }

    /**
     * 获取下载路径
     */
    public static String getDownloadDir() {
        return getDir(DOWNLOAD_DIR);
    }

    /**
     * 获取指定name目录
     */
    public static String getDir(String name) {
        StringBuilder sb = new StringBuilder();
        if (isSDCardAvailable()) {
            sb.append(getExternalStoragePath());
            // 得到的路径：/mnt/sdcard/xxxx/name/
        } else {
            sb.append(getCachePath());
            // 得到的路径：/data/data/com.xxx.xx/cache/cache
        }
        sb.append(name);
        sb.append(File.separator);
        String path = sb.toString();
        if (createDirs(path)) {
            return path;
        } else {
            return null;
        }
    }

    /**
     * 判断sd卡是否挂载
     */
    public static boolean isSDCardAvailable() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 返回sd卡的market路径
     */
    public static String getExternalStoragePath() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        sb.append(File.separator);
        sb.append(ROOT_DIR);
        sb.append(File.separator);
        return sb.toString();
    }

    /**
     * 返回cache路径  //另一个路径是： getFileDir
     */
    private static String getCachePath() {
        File file = TxUtils.getInstance().getContext().getCacheDir();
        return file.getAbsolutePath() + File.separator;
    }

    /**
     * 创建文件夹
     */
    private static boolean createDirs(String path) {
        File file = new File(path);
        if (!file.exists() || !file.isDirectory()) {
            return file.mkdirs();
        }
        return true;
    }

}
