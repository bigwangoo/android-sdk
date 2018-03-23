package com.tianxiabuyi.txutils.network.service;

import com.tianxiabuyi.txutils.config.TxConstants;
import com.tianxiabuyi.txutils.network.TxCall;
import com.tianxiabuyi.txutils.network.model.HttpResult;
import com.tianxiabuyi.txutils.network.model.bean.LoginBean;
import com.tianxiabuyi.txutils.network.model.result.TokenResult;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by xjh1994 on 2016/8/18.
 */
public interface TxUserService {


    @POST(TxConstants.TOKEN_REFRESH_URL)
    Call<TokenResult> refreshToken(@Query(TxConstants.KEY_TOKEN) String token);

    @POST(TxConstants.USER_LOGIN_URL)
    TxCall<HttpResult<LoginBean>> login(@Query("user_name") String username,
                                        @Query("password") String password);

    @POST(TxConstants.USER_LOGIN_URL)
    TxCall<HttpResult<LoginBean>> login(@Query("user_name") String username,
                                        @Query("password") String password,
                                        @Query("type") String type);



    @POST(TxConstants.USER_LOGIN_URL)
    TxCall<HttpResult<LoginBean>> login(@QueryMap Map<String, String> params);


    @POST(TxConstants.USER_MODIFY_URL)
    TxCall<HttpResult> modify(@Query("card_type") String card_type,
                              @Query("card_number") String card_number,
                              @Query("name") String name,
                              @Query("phone") String phone);

    @POST(TxConstants.USER_MODIFY_URL)
    TxCall<HttpResult> modify(@Query("card_type") String card_type,
                              @Query("card_number") String card_number,
                              @Query("name") String name,
                              @Query("phone") String phone,
                              @Query("gender") String gender);

    @POST("user/nickname")
    TxCall<HttpResult> nickname(@Query("nick_name") String nickname);


}
