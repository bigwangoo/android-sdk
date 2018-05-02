package com.bigwangoo.sample.common.manager;

import java.util.List;

/**
 * description
 *
 * @author WangYaoDong
 * @date 2018/3/26 13:33
 */
public class AppInfo {

    //id
    private long id;
    //名字
    private String name;
    //包名
    private String packageName;
    //大小
    private long size;
    //app的版本
    private String version;
    //app的发布日期
    private String date;
    //
    private String des;
    //
    private String author;
    //图片下载地址
    private String iconUrl;
    //评分
    private float stars;
    //
    private String downloadUrl;
    //app的下载数量
    private String downloadNum;
    //截图下载地址
    private List<String> screen;
    //安全信息图片地址
    private List<String> safeUrl;
    //安全信息钩钩图片地址
    private List<String> safeDesUrl;
    //安全信息图片勾勾后面描述信息
    private List<String> safeDes;
    //安全信息的文字颜色
    private List<Integer> safeDesColor;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public float getStars() {
        return stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getDownloadNum() {
        return downloadNum;
    }

    public void setDownloadNum(String downloadNum) {
        this.downloadNum = downloadNum;
    }

    public List<String> getScreen() {
        return screen;
    }

    public void setScreen(List<String> screen) {
        this.screen = screen;
    }

    public List<String> getSafeUrl() {
        return safeUrl;
    }

    public void setSafeUrl(List<String> safeUrl) {
        this.safeUrl = safeUrl;
    }

    public List<String> getSafeDesUrl() {
        return safeDesUrl;
    }

    public void setSafeDesUrl(List<String> safeDesUrl) {
        this.safeDesUrl = safeDesUrl;
    }

    public List<String> getSafeDes() {
        return safeDes;
    }

    public void setSafeDes(List<String> safeDes) {
        this.safeDes = safeDes;
    }

    public List<Integer> getSafeDesColor() {
        return safeDesColor;
    }

    public void setSafeDesColor(List<Integer> safeDesColor) {
        this.safeDesColor = safeDesColor;
    }
}
