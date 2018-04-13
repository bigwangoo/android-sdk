package com.demo.common.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.math.BigDecimal;

/**
 * Created by wang.yd on 2017/7/11.
 */
public class CacheManagerUtils {

    /**
     * 获取缓存大小
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static String getTotalCacheSize(Context context) {
        long cacheSize = getFolderSize(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(context.getExternalCacheDir());
        }
        return getFormatSize(cacheSize);
    }

    /**
     * 清楚缓存
     *
     * @param context
     */
    public static void clearAllCache(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
    }

    /**
     * 获取文件夹大小
     *
     * @param dir
     * @return
     * @throws Exception
     */
    private static long getFolderSize(File dir) {
        if (dir == null) {
            return 0;
        }
        long size = 0;
        try {
            File[] fileList = dir.listFiles();
            for (File file : fileList) {
                // 如果下面还有文件
                if (file.isDirectory()) {
                    size = size + getFolderSize(file);
                } else {
                    size = size + file.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 删除文件夹
     *
     * @param dir
     * @return
     */
    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String path : children) {
                boolean success = deleteDir(new File(dir, path));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    /**
     * 格式化单位
     *
     * @param size
     */
    private static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }






//
//    /**
//     * 递归求取目录文件个数
//     *
//     * @param f
//     * @return
//     */
//    public static long getlist(File f) {
//        long size = 0;
//        File flist[] = f.listFiles();
//        size = flist.length;
//        for (int i = 0; i < flist.length; i++) {
//            if (flist[i].isDirectory()) {
//                size = size + getlist(flist[i]);
//                size--;
//            }
//        }
//        return size;
//    }
//    /**
//     * 递归创建文件夹
//     *
//     * @param dirPath path
//     * @return 创建失败返回 ""
//     */
//    public static boolean createDir(String dirPath) {
//        try {
//            File file = new File(dirPath);
//            if (file.getParentFile().exists()) {
//                file.mkdir();
//                return file.getAbsolutePath();
//            } else {
//                createDir(file.getParentFile().getAbsolutePath());
//                file.mkdir();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return dirPath;
//    }
//
//    /**
//     * 递归创建文件
//     *
//     * @param file file
//     * @return fail return ""
//     */
//    public static boolean createFile(File file) {
//        try {
//            if (file.getParentFile().exists()) {
//                file.createNewFile();
//            } else {
//                createDir(file.getParentFile().getAbsolutePath());
//                file.createNewFile();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }

}