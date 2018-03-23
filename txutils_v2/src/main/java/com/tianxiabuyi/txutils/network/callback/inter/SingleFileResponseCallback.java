package com.tianxiabuyi.txutils.network.callback.inter;

import com.tianxiabuyi.txutils.network.exception.TxException;
import com.tianxiabuyi.txutils.network.model.result.TxFileResult;

/**
 * Created by xjh1994 on 2016/8/31.
 * 单文件上传
 */
public interface SingleFileResponseCallback {

    void onProgress(int progress, long total);

    void onSuccess(TxFileResult response);

    void onError(TxException e);
}
