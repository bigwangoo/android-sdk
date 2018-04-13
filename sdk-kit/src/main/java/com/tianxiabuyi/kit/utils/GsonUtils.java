package com.demo.common.utils;

import com.google.gson.Gson;

/**
 * Created in 2017/4/25.
 *
 * @author wang.
 */
public class GsonUtils {

    private static Gson instance;

    public static Gson getGson() {
        if (instance == null) {
            synchronized (GsonUtils.class) {
                if (instance == null) {
                    instance = new Gson();
                }
            }
        }
        return instance;
    }

}
