package com.tianxiabuyi.txutils.network.callback;

import android.text.TextUtils;

import com.tianxiabuyi.txutils.log.TxLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import okhttp3.Response;

/**
 * Created by xjh1994 on 16/11/4.
 */
public abstract class TxFileCallBack extends Callback<File> {
    /**
     * 目标文件存储的文件夹路径
     */
    private String destFileDir;
    /**
     * 目标文件存储的文件名
     */
    private String destFileName;

    public TxFileCallBack(String destFileDir, String destFileName) {
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
    }

    @Override
    public File parseNetworkResponse(Response response, int id) throws Exception {
        return saveFile(response, id);
    }


    public File saveFile(Response response, final int id) throws IOException {
        if (TextUtils.isEmpty(destFileName)) {
            destFileName = getResponseFileName(response);
            if (TextUtils.isEmpty(destFileName)) {
                String header = response.header("Content-Type");
                destFileName = System.currentTimeMillis() + getFileType(header);   //TODO Content-Length
            }
        }

        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        try {
            is = response.body().byteStream();
            final long total = response.body().contentLength();

            long sum = 0;

            File dir = new File(destFileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, destFileName);
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                sum += len;
                fos.write(buf, 0, len);
                final long finalSum = sum;
                OkHttpUtils.getInstance().getDelivery().execute(new Runnable() {
                    @Override
                    public void run() {

                        inProgress(finalSum * 1.0f / total, total, id);
                    }
                });
            }
            fos.flush();

            return file;

        } finally {
            try {
                response.body().close();
                if (is != null) is.close();
            } catch (IOException e) {
            }
            try {
                if (fos != null) fos.close();
            } catch (IOException e) {
            }

        }
    }

    private static String getResponseFileName(Response response) {
        String disposition = response.header("Content-Disposition");
        if (!TextUtils.isEmpty(disposition)) {
            int startIndex = disposition.indexOf("filename=");
            if (startIndex > 0) {
                startIndex += 9; // "filename=".length()
                int endIndex = disposition.indexOf(";", startIndex);
                if (endIndex < 0) {
                    endIndex = disposition.length();
                }
                if (endIndex > startIndex) {
                    try {
                        String contentType = response.header("Content-Type");
                        String encode = contentType.substring(contentType.lastIndexOf("charset"), contentType.length());
                        encode = encode.substring(encode.lastIndexOf("=") + 1, encode.length());
                        TxLog.e(encode);
                        String name = URLDecoder.decode(disposition.substring(startIndex, endIndex), encode);
                        if (name.startsWith("\"") && name.endsWith("\"")) {
                            name = name.substring(1, name.length() - 1);
                        }
                        return name;
                    } catch (UnsupportedEncodingException ex) {
                        TxLog.e(ex.getMessage(), ex);
                    }
                }
            }
        }
        return "";
    }

    private String getFileType(String header) {
        String type = "";
        for (int i = 0; i < MIME_MapTable.length; i++) { //MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？
            if (header.equals(MIME_MapTable[i][1]))
                type = MIME_MapTable[i][0];
        }
        TxLog.e(header + " ==>" + type);
        return type;
    }

    private final String[][] MIME_MapTable = {
            //{后缀名，MIME类型}
            {".doc", "application/msword"},
            {".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".xls", "application/vnd.ms-excel"},
            {".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {".pdf", "application/pdf"},
            {".ppt", "application/vnd.ms-powerpoint"},
            {".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            {".jpeg", "image/jpeg"},
            {".jpg", "image/jpeg"},
            {".zip", "application/x-zip-compressed"},
            {".apk", "application/vnd.android.package-archive"},
            {"", "*/*"}
    };
}
