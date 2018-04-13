package com.demo.common.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * 常用关闭流操作
 * <p>
 * Created by wyd on 2017/4/27.
 */
public class IOUtils {
    /**
     * 关闭流
     */
    public static boolean close(Closeable io) {
        if (io != null) {
            try {
                io.close();
            } catch (IOException e) {
                // LogUtils.e(e);
            }
        }
        return true;
    }
}