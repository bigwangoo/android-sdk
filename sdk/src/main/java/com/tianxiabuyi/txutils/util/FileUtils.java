package com.tianxiabuyi.txutils.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import com.tianxiabuyi.txutils.TxUtils;
import com.tianxiabuyi.txutils.log.TxLog;

import java.io.File;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.LinkedList;

/**
 * 文件相关
 * tip:
 * Android相关存储路径
 * -------------------------------------------------------------------------------------------------
 * 一、独立文件夹
 * Environment.getExternalStorageDirectory() = /mnt/sdcard
 * Environment.getExternalStoragePublicDirectory(“test”) = /mnt/sdcard/test
 * Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES = /mnt/sdcard/Pictures
 * Environment.getRootDirectory() = /system
 * Environment.getDataDirectory() = /data
 * Environment.getDownloadCacheDirectory() = /cache
 * <p>
 * <p>
 * 二、专属文件夹(卸载后自动删除)
 * - 1) 存储在 internal storage
 * Context.getFilesDir() = /data/data/com.my.app/files
 * Context.getCacheDir() = /data/data/com.my.app/cache
 * Context.getDir(“test”, Context.MODE_PRIVATE) = /data/data/com.my.app/app_test
 * Context.getDatabasePath(“test”) = /data/data/com.my.app/databases/test
 * Context.getPackageCodePath() = /data/app/com.my.app-1.apk
 * Context.getPackageResourcePath() = /data/app/com.my.app-1.apk
 * <p>
 * - 2) 存储在 external storage:
 * Context.getExternalCacheDir() = /mnt/sdcard/Android/data/com.my.app/cache
 * Context.getExternalFilesDir(“test”) = /mnt/sdcard/Android/data/com.my.app/files/test
 * Context.getExternalFilesDir(null) = /mnt/sdcard/Android/data/com.my.app/files
 * -------------------------------------------------------------------------------------------------
 *
 * @author WangYaoDong
 * @date 2018/3/26 13:38
 */
public class FileUtils {

    /**
     * 项目文件统一处理，放在txby/app包名/分类
     */
    private static final Object ROOT_DIR = "txby";
    // 保存图片的路径
    public static final String ICON_DIR = "icon";
    // 缓存的文件
    public static final String CACHE_DIR = "cache";
    // 文件
    public static final String FILE_DIR = "file";
    // 下载文件的路径
    public static final String DOWNLOAD_DIR = "download";


    public static String getIconDir() {
        return getAppDir(ICON_DIR);
    }

    public static String getCacheDir() {
        return getAppDir(CACHE_DIR);
    }

    public static String getDownloadDir() {
        return getAppDir(DOWNLOAD_DIR);
    }

    public static String getAppDir(String name) {
        StringBuilder sb = new StringBuilder();
        if (isSDCardAvailable()) {
            sb.append(getExternalStoragePath());
        } else {
            sb.append(getCachePath());
        }
        if (!TextUtils.isEmpty(name)) {
            sb.append(name);
            sb.append(File.separator);
        }
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
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 缓存目录
     */
    public static File getExternalCacheDir() {
        String cachePath;
        if (isSDCardAvailable() && TxUtils.getInstance().getContext().getExternalCacheDir() != null) {
            cachePath = TxUtils.getInstance().getContext().getExternalCacheDir().getAbsolutePath();
        } else {
            cachePath = TxUtils.getInstance().getContext().getCacheDir().getAbsolutePath();
        }
        try {
            File file = new File(cachePath);
            if (!file.exists() || !file.isDirectory()) {
                boolean mkdirs = file.mkdirs();
            }
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 下载目录
     */
    public static File getExternalDownloadDir() {
        String cacheDir;
        if (isSDCardAvailable()) {
            cacheDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        } else {
            cacheDir = TxUtils.getInstance().getContext().getFilesDir().getAbsolutePath();
        }
        try {
            File file = new File(cacheDir);
            if (!file.exists() || !file.isDirectory()) {
                boolean mkdirs = file.mkdirs();
            }
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    //////////////////////////////// private

    /**
     * 返回 sd卡的路径
     */
    private static String getExternalStoragePath() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        sb.append(File.separator);
        sb.append(ROOT_DIR);
        sb.append(File.separator);
        return sb.toString();
    }

    /**
     * 返回 cache的路径
     */
    private static String getCachePath() {
        File file = TxUtils.getInstance().getContext().getCacheDir();
        return file.getAbsolutePath() + File.separator;
    }

    /**
     * 创建文件夹
     */
    private static boolean createDirs(String path) {
        // 写权限
        try {
            File file = new File(path);
            return !(!file.exists() || !file.isDirectory()) || file.mkdirs();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    //////////////////////////////// 缓存管理

    /**
     * 获取缓存大小
     *
     * @param context context
     * @return size
     */
    public static String getTotalCacheSize(Context context) {
        long cacheSize = 0;
        cacheSize = getFileSize(context.getCacheDir());
        if (isSDCardAvailable()) {
            cacheSize += getFileSize(context.getExternalCacheDir());
        }
        return formatFileSize(cacheSize);
    }

    /**
     * 清除所有缓存
     *
     * @param context context
     */
    public static void clearAllCache(Context context) {
        deleteDir(context.getCacheDir().getAbsolutePath());
        if (isSDCardAvailable()) {
            File cacheDir = context.getExternalCacheDir();
            if (cacheDir != null) {
                deleteDir(cacheDir.getAbsolutePath());
            }
        }
    }

    /**
     * 格式化文件大小
     */
    public static String formatFileSize(long file) {
        String fileSizeString = "";
        DecimalFormat df = new DecimalFormat("#.00");
        if (file < 1024) {
            if (file == 0) {
                fileSizeString = "0.0MB";
            } else {
                fileSizeString = df.format((double) file) + "B";
            }
        } else if (file < 1024 * 1024) {
            fileSizeString = df.format((double) file / 1024) + "KB";
        } else if (file < 1024 * 1024 * 1024) {
            fileSizeString = df.format((double) file / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) file / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 获取剩余空间
     */
    public static String getRestSize() {
        // TODO
        return "";
    }

    /**
     * 递归求取文件大小
     */
    public static long getFileSize(File path) {
        if (path == null) {
            return 0;
        }
        long size = 0;
        File[] files = path.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isDirectory()) {
                    size = size + getFileSize(file);
                } else {
                    size = size + file.length();
                }
            }
        } else {
            size = path.length();
        }
        return size;
    }

    /**
     * 递归求取目录文件个数
     */
    public static long getFileList(File path) {
        long size = 0;
        File[] files = path.listFiles();
        size = files.length;
        for (File file : files) {
            if (file.isDirectory()) {
                size = size + getFileList(file);
                size--;
            }
        }
        return size;
    }

    /**
     * 检索目录中的以head为头的文件;
     */
    public static LinkedList<File> getFile(File parent, String head) {
        LinkedList<File> listsFiles = new LinkedList<File>();
        if (parent.exists()) {
            String[] list = parent.list();
            if (list != null) {
                for (String name : list) {
                    if (name.startsWith(head)) {
                        listsFiles.add(new File(parent.getAbsoluteFile() + File.separator + name));
                    }
                }
            }
        }
        return listsFiles;
    }

    /**
     * 删除文件夹下文件
     * 注：删除cache目录后, 再次调用context.getExternalCacheDir()闪退 所以删除文件不包括目录
     * 如 CoolPad手机
     *
     * @param path path
     */
    public static void deleteDir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            String[] children = file.list();
            for (String name : children) {
                deleteDir(new File(file, name).getAbsolutePath());
            }
        } else {
            if (file.isFile()) {
                boolean delete = file.delete();
            }
        }
    }

    /**
     * 递归创建文件夹
     *
     * @param dirPath 目录
     * @return 创建失败返回 ""
     */
    public static String createDir(String dirPath) {
        try {
            File file = new File(dirPath);
            if (file.getParentFile().exists()) {
                TxLog.i("----- 创建文件夹" + file.getAbsolutePath());
                file.mkdir();
                return file.getAbsolutePath();
            } else {
                createDir(file.getParentFile().getAbsolutePath());
                TxLog.i("----- 创建文件夹" + file.getAbsolutePath());
                file.mkdir();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dirPath;
    }

    /**
     * 递归创建文件夹
     *
     * @param file
     * @return 创建失败返回 ""
     */
    public static String createFile(File file) {
        try {
            if (file.getParentFile().exists()) {
                TxLog.i("----- 创建文件" + file.getAbsolutePath());
                boolean newFile = file.createNewFile();
                return file.getAbsolutePath();
            } else {
                createDir(file.getParentFile().getAbsolutePath());
                boolean newFile = file.createNewFile();
                TxLog.i("----- 创建文件" + file.getAbsolutePath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 判断文件路径是否存在
     *
     * @param filePath path
     * @return true 存在,  false 不存在
     */
    public static boolean isExist(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * 获取Bitmap大小
     */
    public static long getBitmapSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return bitmap.getByteCount();
        }
        // Pre HC-MR1
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

    /**
     * 获取文件名
     */
    public static String getFileName(File file) {
        return file.getName().substring(file.getName().indexOf("."));
    }

    /**
     * 获取文件名
     */
    public static String getFileNameFromUrl(String url) {
        try {
            String[] urlArray = url.split("/");
            int length = urlArray.length;
            return urlArray[length - 1];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取字符串 MD5
     */
    public static String md5(String paramString) {
        String returnStr;
        try {
            MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
            if (paramString != null) {
                localMessageDigest.update(paramString.getBytes());
            }
            returnStr = byteToHexString(localMessageDigest.digest());
            return returnStr;
        } catch (Exception e) {
            return paramString;
        }
    }

    /**
     * 将指定 byte数组转换成16进制字符串
     */
    private static String byteToHexString(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            hexString.append(hex.toUpperCase());
        }
        return hexString.toString();
    }

    /**
     * 读取文件 MIME
     */
    public static String getMIMEType(File file) {
        String type = "*/*";
        String fName = file.getName();
        // 获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }
        // 获取文件的后缀名
        String end = fName.substring(dotIndex, fName.length()).toLowerCase();
        if (end.equals("")) {
            return type;
        }
        // 在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (String[] mimeTable : MIME_MAP_TABLE) {
            if (end.equals(mimeTable[0])) {
                type = mimeTable[1];
            }
        }
        return type;
    }

    // {后缀名，MIME类型}
    private static final String[][] MIME_MAP_TABLE = {
            {".doc", "application/msword"},
            {".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".xls", "application/vnd.ms-excel"},
            {".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {".pdf", "application/pdf"},
            {".ppt", "application/vnd.ms-powerpoint"},
            {".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            {".jpeg", "image/jpeg"},
            {".jpg", "image/jpeg"},
            {".zip", "application/x-zip-compressed"},
            {"", "*/*"}
    };

}
