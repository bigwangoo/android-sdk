package com.tianxiabuyi.txutils.network.callback.inter;

import com.tianxiabuyi.txutils.network.exception.TxException;
import com.tianxiabuyi.txutils.network.model.result.TxMultiFileResult;

/**
 * Created by xjh1994 on 2016/9/13.
 * 多文件上传
 */
public interface MultiFileResponseCallback {

    void onProgress(int progress, long total);

    void onSuccess(TxMultiFileResult files);

    void onError(TxException e);
}
