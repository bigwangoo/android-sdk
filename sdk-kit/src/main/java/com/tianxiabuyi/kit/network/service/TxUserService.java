package com.demo.common.network.service;

import com.demo.common.network.TxCall;

import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by wangyd on 2017/8/13.
 */
public interface TxUserService {

    @POST("login")
    TxCall login(@Query("username") String username, @Query("password") String password);
}
