package com.tianxiabuyi.txutils.network.service;

import com.tianxiabuyi.txutils.config.TxConstants;
import com.tianxiabuyi.txutils.network.TxCall;
import com.tianxiabuyi.txutils.network.model.HttpResult;
import com.tianxiabuyi.txutils.network.model.bean.VersionBean;

import retrofit2.http.POST;

/**
 * 软件更新
 * Created by xjh1994 on 2016/8/31.
 */
public interface TxUpdateService {

    @POST(TxConstants.UPDATE_URL)
    TxCall<HttpResult<VersionBean>> update();
}
