package com.tianxiabuyi.txutils.network.model.bean;

import com.tianxiabuyi.txutils.network.model.BaseBean;

import java.util.List;

/**
 * Created by ASUS on 2016/11/30.
 * 额外
 */
public class JsonBean extends BaseBean {

    /**
     * id : XMTc2NzM3MDY2MA\u003d\u003d
     * link : http://v.youku.com/v_show/id_XMTc2NzM3MDY2MA\u003d\u003d.html
     * thumb : http://r4.ykimg.com/054204085808594A6A0A4B04476D3D37
     * attachs : [{"title":"科教处、医疗设备处招聘公告","href":"http://www.sdfey.com/FileDownLoadAction_downLoadMyFile.action?uploadId\\u003dBBC32619-718E-48BE-8E40-A09ADAE8672F"}]
     */

    private String id;
    private String link;
    private String thumb;
    private List<AttachsBean> attachs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public List<AttachsBean> getAttachs() {
        return attachs;
    }

    public void setAttachs(List<AttachsBean> attachs) {
        this.attachs = attachs;
    }

    public static class AttachsBean {
        /**
         * title : 科教处、医疗设备处招聘公告
         * href : http://www.sdfey.com/FileDownLoadAction_downLoadMyFile.action?uploadId\u003dBBC32619-718E-48BE-8E40-A09ADAE8672F
         */

        private String title;
        private String href;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }
    }
}
