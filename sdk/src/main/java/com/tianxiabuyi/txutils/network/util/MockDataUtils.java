package com.tianxiabuyi.txutils.network.util;

import android.content.Context;
import android.content.res.AssetManager;

import com.tianxiabuyi.txutils.TxUtils;

import org.xutils.common.util.IOUtil;

import java.io.IOException;
import java.io.InputStream;

/**
 * Create on 2018/1/8 22:11
 *
 * @author Wang YaoDong.
 * @version 1.0.0
 * @description: 模拟网络请求数据
 */
public class MockDataUtils {

    /**
     * 从Json文件获取接口Mock数据
     *
     * @param path
     */
    public static String getMockDataFromJsonFile(String path) {
        Context context = TxUtils.getInstance().getContext();
        try {
            InputStream inputStream = context.getAssets().open(path, AssetManager.ACCESS_BUFFER);
            return IOUtil.readStr(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
