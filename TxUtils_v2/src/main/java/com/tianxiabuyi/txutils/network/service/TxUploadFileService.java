package com.tianxiabuyi.txutils.network.service;

import com.tianxiabuyi.txutils.config.TxConstants;
import com.tianxiabuyi.txutils.network.TxCall;
import com.tianxiabuyi.txutils.network.model.result.TxFileResult;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * Created by xjh1994 on 2016/8/19.
 * 文件上传
 */
public interface TxUploadFileService {

    //////////////////////////// 旧
    @Multipart
    @POST(TxConstants.UPLOAD_FILE_URL)
    TxCall<TxFileResult> uploadImage(@Query("name") String name,
                                     @Query("type") String type,
                                     @Part MultipartBody.Part fileRequestBody);

    @Multipart
    @POST(TxConstants.UPLOAD_FILE_URL)
    TxCall<ResponseBody> uploadMultiImage(@Part("act") String act,
                                          @PartMap Map<String, RequestBody> params);

    //////////////////////////// 新
    @Multipart
    @POST(TxConstants.FILE_UPLOAD_URL + "/removeType/removeToken")
    TxCall<TxFileResult> uploadFile(@Part MultipartBody.Part file, @Query("token") String token);

    @Multipart
    @POST(TxConstants.FILES_UPLOAD_URL + "/removeType/removeToken")
    TxCall<TxFileResult> uploadFiles(@Part MultipartBody.Part[] files, @Query("token") String token);
}
