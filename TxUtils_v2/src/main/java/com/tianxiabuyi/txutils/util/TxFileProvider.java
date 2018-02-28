package com.tianxiabuyi.txutils.util;

import android.support.v4.content.FileProvider;

/**
 * @author WangYaoDong
 * @date 2018/2/27 10:48
 * @description 继承FileProvider类来解决合并冲突的问题, 如果第三方库也配置了同样的FileProvider
 */
public class TxFileProvider extends FileProvider {
    public TxFileProvider() {
    }
}
