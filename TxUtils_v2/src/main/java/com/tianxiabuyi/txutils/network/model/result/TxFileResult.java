package com.tianxiabuyi.txutils.network.model.result;

import com.tianxiabuyi.txutils.network.model.HttpResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xjh1994 on 2016/8/19.
 * 上传文件返回
 */
public class TxFileResult extends HttpResult {

    private static final String BASE_IMG_URL = "http://api.eeesys.com:60000/file/show/";

    ///////////////////////// old
    /*单图片*/
    private String img;

    ///////////////////////// new
    /*单文件*/
    private String fileUrl;
    /*多文件*/
    private List<String> fileUrls;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getFileUrl() {
        return BASE_IMG_URL + fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public List<String> getFileUrls() {
        List<String> newFileUrls = new ArrayList<>();
        int size = fileUrls == null ? 0 : fileUrls.size();
        for (int i = 0; i < size; i++) {
            newFileUrls.add(BASE_IMG_URL + fileUrls.get(i));
        }
        return newFileUrls;
    }

    public void setFileUrls(List<String> fileUrls) {
        this.fileUrls = fileUrls;
    }

}
