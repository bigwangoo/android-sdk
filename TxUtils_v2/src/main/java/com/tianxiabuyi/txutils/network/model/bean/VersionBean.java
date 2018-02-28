package com.tianxiabuyi.txutils.network.model.bean;

import com.tianxiabuyi.txutils.network.model.BaseBean;

/**
 * @author xjh1994
 * @date 2016/12/13
 * @description app更新
 */
public class VersionBean extends BaseBean {

    /**
     * time : 2015-12-28
     * url : http://192.168.1.1
     * description : 版本更新描述
     * version : 1.1.1
     * version_code : 2
     */

    private String time;
    private String url;
    private String description;
    private String version;
    private int version_code;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getVersion_code() {
        return version_code;
    }

    public void setVersion_code(int version_code) {
        this.version_code = version_code;
    }
}
