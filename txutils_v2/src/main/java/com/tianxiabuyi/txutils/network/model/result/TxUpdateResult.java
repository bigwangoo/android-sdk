package com.tianxiabuyi.txutils.network.model.result;

import com.tianxiabuyi.txutils.network.model.HttpResult;

/**
 * Created by xjh1994 on 2016/8/31.
 */

public class TxUpdateResult extends HttpResult {
    /**
     * desc : 产品优化
     * app_type : hospital
     * app_name : 家庭医生端
     * url : http://file.eeesys.com/hospital/1044/platform_doctor_v0.9_txby(1).apk
     * hospital : 1044
     * version_code : 2
     * version : 2.0
     */

    private AppBean app;

    public AppBean getApp() {
        return app;
    }

    public void setApp(AppBean app) {
        this.app = app;
    }

    public static class AppBean {
        private String desc;
        private String app_type;
        private String app_name;
        private String url;
        private int hospital;
        private int version_code;
        private String version;

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getApp_type() {
            return app_type;
        }

        public void setApp_type(String app_type) {
            this.app_type = app_type;
        }

        public String getApp_name() {
            return app_name;
        }

        public void setApp_name(String app_name) {
            this.app_name = app_name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getHospital() {
            return hospital;
        }

        public void setHospital(int hospital) {
            this.hospital = hospital;
        }

        public int getVersion_code() {
            return version_code;
        }

        public void setVersion_code(int version_code) {
            this.version_code = version_code;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }
}
