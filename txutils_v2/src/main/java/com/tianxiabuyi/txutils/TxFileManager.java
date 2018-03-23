package com.tianxiabuyi.txutils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.tianxiabuyi.txutils.config.TxConstants;
import com.tianxiabuyi.txutils.log.TxLog;
import com.tianxiabuyi.txutils.network.callback.ResponseCallback;
import com.tianxiabuyi.txutils.network.callback.TxFileCallBack;
import com.tianxiabuyi.txutils.network.callback.inter.FileResponseCallback;
import com.tianxiabuyi.txutils.network.callback.inter.MultiFileResponseCallback;
import com.tianxiabuyi.txutils.network.callback.inter.SingleFileResponseCallback;
import com.tianxiabuyi.txutils.network.exception.TxException;
import com.tianxiabuyi.txutils.network.model.result.TxFileResult;
import com.tianxiabuyi.txutils.network.model.result.TxMultiFileResult;
import com.tianxiabuyi.txutils.network.service.TxUploadFileService;
import com.tianxiabuyi.txutils.util.FileUtils;
import com.tianxiabuyi.txutils.util.GsonUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

/**
 * 文件管理（图片上传、下载）
 *
 * @author xjh1994
 * @date 2016/8/19
 */
public class TxFileManager {

    private static final String TAG = TxFileManager.class.getSimpleName();

    private static final String TOKEN_FILE_UPLOAD = "14e1b600b1fd579f47433b88e8d85291";

    /////////////////////////////// 新

    /**
     * 上传文件
     */
    public static void uploadFile(String path, ResponseCallback<TxFileResult> callback) {
        File file = new File(path);
        RequestBody requestBody = RequestBody.create(MultipartBody.FORM, file);
        // name需要与后台对应 "file"
        MultipartBody.Part part = MultipartBody.Part.createFormData("file",
                getRandomFileName(path), requestBody);

        TxUploadFileService service = TxServiceManager.createService(TxUploadFileService.class);
        service.uploadFile(part, TOKEN_FILE_UPLOAD).enqueue(callback);
    }

    /**
     * 上传多文件
     */
    public static void uploadFiles(List<String> paths, ResponseCallback<TxFileResult> callback) {
        if (paths == null || paths.size() < 1) {
            return;
        }

        MultipartBody.Part[] parts = new MultipartBody.Part[paths.size()];
        for (int i = 0; i < paths.size(); i++) {
            String path = paths.get(i);
            File file = new File(path);
            RequestBody requestBody = RequestBody.create(MultipartBody.FORM, file);
            // name需要与后台对应 "files"
            parts[i] = MultipartBody.Part.createFormData("files",
                    getRandomFileName(path), requestBody);
        }

        TxUploadFileService service = TxServiceManager.createService(TxUploadFileService.class);
        service.uploadFiles(parts, TOKEN_FILE_UPLOAD).enqueue(callback);
    }

    /////////////////////////////// 旧

    /**
     * 上传多图
     *
     * @param context  Context
     * @param paths    图片集合
     * @param callback callback
     * @return RequestCall
     */
    public static RequestCall uploadMulti(Context context,
                                          List<String> paths,
                                          final MultiFileResponseCallback callback) {
        Map<String, String> params = new HashMap<>();
        params.put("act", "multi");

        PostFormBuilder postFormBuilder = OkHttpUtils.post();

        int size = paths.size();
        for (int i = 0; i < size; i++) {
            File file = new File(paths.get(i));
            if (!file.exists()) {
                TxLog.d(context.getString(R.string.tx_file_not_exist));
                return null;
            }
            postFormBuilder.addFile("file[]", file.getName(), file);
        }
        RequestCall fileRequestCall = postFormBuilder
                .url(TxConstants.UPLOAD_FILE_URL)
                .params(params)
                .build();
        fileRequestCall.execute(new StringCallback() {

            @Override
            public void inProgress(float progress, long total, int id) {
                callback.onProgress((int) (100 * progress), total);
            }

            @Override
            public void onResponse(String response, int id) {
                TxMultiFileResult result = GsonUtils.fromJson(response, TxMultiFileResult.class);
                if (result != null && result.isSuccess()) {
                    callback.onSuccess(result);
                } else {
                    callback.onError(new TxException(response));
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                callback.onError(new TxException(e.getMessage()));
            }
        });
        return fileRequestCall;
    }

    /**
     * 通过Uri上传文件
     *
     * @param uri      Uri
     * @param callback callback
     */
    public static void upload(Uri uri, ResponseCallback<TxFileResult> callback) {
        String path = uri.getPath();
        File file = new File(path);
        if (!file.exists()) {
            path = getImageLoad(uri);
        }

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(TxConstants.FILE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TxConstants.FILE_TIMEOUT, TimeUnit.SECONDS)
                .build();

        TxUploadFileService service = TxServiceManager.createService(TxUploadFileService.class, client);

        String fileName = getRandomFileName(path);

        file = new File(path);
        RequestBody fileRequestBody = RequestBody.create(MediaType.parse("image/*"), file);

        MultipartBody.Part body = MultipartBody.Part.createFormData("file", fileName, fileRequestBody);

        service.uploadImage(fileName, "file", body).enqueue(callback);
    }

    /**
     * 单个文件上传
     * 带进度
     *
     * @param uri      Uri
     * @param callback callback
     */
    public static void upload(Uri uri, final SingleFileResponseCallback callback) {
        String path = uri.getPath();
        File file = new File(path);
        if (!file.exists()) {
            path = getImageLoad(uri);
        }
        file = new File(path);
        String fileName = getRandomFileName(path);

        OkHttpUtils.post()
                .addFile("file", fileName, file)
                .url(TxConstants.UPLOAD_FILE_URL)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        callback.onProgress((int) (100 * progress), total);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        callback.onError(new TxException(e.getMessage()));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        TxFileResult result = GsonUtils.fromJson(response, TxFileResult.class);
                        if (result != null && result.isSuccess()) {
                            callback.onSuccess(result);
                        } else {
                            callback.onError(new TxException(response));
                        }
                    }
                });
    }

    /**
     * 下载文件
     * 只需url
     */
    public static void download(String fileUrl, final FileResponseCallback callback) {
        download(fileUrl, FileUtils.getExternalDownloadDir().getAbsolutePath(), "", callback);
    }

    /**
     * 下载文件
     * 需要url和保存路径
     */
    public static void download(String fileUrl, String filePath, final FileResponseCallback callback) {
        download(fileUrl, filePath, "", callback);
    }

    /**
     * 下载文件
     *
     * @param fileUrl  文件服务器地址
     * @param filePath 下载后保存的路径
     * @param fileName 保存的文件名
     */
    public static void download(String fileUrl, String filePath, String fileName, final FileResponseCallback callback) {
        OkHttpUtils
                .get()
                .url(fileUrl)
                .tag(fileUrl)
                .build()
                .execute(new TxFileCallBack(filePath, fileName) {

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        callback.onProgress((int) (100 * progress), total);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        callback.onError(new TxException(e.getMessage()));
                    }

                    @Override
                    public void onResponse(File response, int id) {
                        callback.onSuccess(response);
                    }
                });
    }


    public static String getImageLoad(Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        String imagePath = null;
        Cursor cursor = TxUtils.getInstance().getContext()
                .getContentResolver()
                .query(uri, proj, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            if (cursor.getCount() > 0 && cursor.moveToFirst()) {
                imagePath = cursor.getString(column_index);
            }
        }
        return imagePath;
    }

    /**
     * 根据当前日期拼接文件名
     */
    public static String getRandomFileName(String path) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
        return TextUtils.concat(sdf.format(new Date()), path).toString();
    }

    private static String guessMimeType(String path) {
        if (path.toLowerCase().endsWith(".jpg"))
            return "image/jpeg";
        if (path.toLowerCase().endsWith(".png"))
            return "image/png";
        if (path.toLowerCase().endsWith(".gif"))
            return "image/gif";
        return "application/octet-stream";  //任意的二进制数据
    }
}
