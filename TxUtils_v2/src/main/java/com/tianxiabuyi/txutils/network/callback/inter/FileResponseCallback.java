package com.tianxiabuyi.txutils.network.callback.inter;

import com.tianxiabuyi.txutils.network.exception.TxException;

import java.io.File;

/**
 * Created by xjh1994 on 2016/8/31.
 * 文件上传
 */
public interface FileResponseCallback {

    void onProgress(int progress, long total);

    void onSuccess(File response);

    void onError(TxException e);
}
