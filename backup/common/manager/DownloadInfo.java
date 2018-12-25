package com.bigwangoo.sample.common.manager;


import com.bigwangoo.sample.common.utils.FileUtils;

/**
 * description
 *
 * @author WangYaoDong
 * @date 2018/3/26 13:27
 */
public class DownloadInfo {

    // 唯一标示
    private long id;
    // APP的名字
    private String appName;
    // APP的size
    private long appSize = 0;
    // 下载进度
    private long currentSize = 0;
    // 下载的地址
    private String url;
    // 保存的路径
    private String path;
    //下载的状态
    private int downloadState = 0;

    public static DownloadInfo clone(AppInfo info) {
        DownloadInfo downloadInfo = new DownloadInfo();
        downloadInfo.id = info.getId();
        downloadInfo.appName = info.getPackageName();
        downloadInfo.appSize = info.getSize();
        downloadInfo.currentSize = 0;
        downloadInfo.url = info.getDownloadUrl();
        downloadInfo.path = FileUtils.getDownloadDir() + downloadInfo.appName + ".apk";
        downloadInfo.downloadState = DownloadManager.STATE_NONE;
        return downloadInfo;
    }

    public float getProgress() {
        if (getAppSize() == 0) {
            return 0;
        }
        return (getCurrentSize() + 0.0f) / getAppSize();
    }

    /////////////////////////////

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public long getAppSize() {
        return appSize;
    }

    public void setAppSize(long appSize) {
        this.appSize = appSize;
    }

    public long getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(long currentSize) {
        this.currentSize = currentSize;
    }

    public int getDownloadState() {
        return downloadState;
    }

    public void setDownloadState(int downloadState) {
        this.downloadState = downloadState;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
