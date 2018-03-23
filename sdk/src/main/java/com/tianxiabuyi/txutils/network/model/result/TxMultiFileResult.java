package com.tianxiabuyi.txutils.network.model.result;

import com.tianxiabuyi.txutils.network.model.HttpResult;

import java.util.List;

/**
 * Created by xjh1994 on 2016/9/12.
 */

public class TxMultiFileResult extends HttpResult {
    /**
     * errcode : 0
     * errmsg : 接口调用成功
     * result : ["http://file.eeesys.com/attach/1473679343t"]
     */

    private List<String> result;

    public List<String> getResult() {
        return result;
    }

    public void setResult(List<String> result) {
        this.result = result;
    }
}
