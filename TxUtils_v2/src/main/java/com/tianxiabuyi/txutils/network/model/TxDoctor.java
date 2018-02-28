package com.tianxiabuyi.txutils.network.model;

/**
 * Created by jjj on 2018/1/8.
 *
 * @description: 医生
 */
public class TxDoctor extends TxUser {

    /**
     * id_ : 714
     * username : 001
     * password : 14e1b600b1fd579f47433b88e8d85291
     * headUrl : http://www.szgjyy.com/upload/201704/28/201704281407153910.jpg
     * name : 朱峰
     * sex : 1
     * age : 25
     * mobileNO :
     * hosName : 苏州市广济医院
     * outpatientTimeStr : 周二上午、周三下午
     * healthJobTitle :
     * teachJobTitle :
     * adeptContent : 儿童孤独症的诊断和治疗指导，儿童多动症和抽动症的评估、诊断和治疗；智力发育障碍的智能、注意、记忆、行为评估和诊断分级；儿童青少年情绪障碍和精神分裂症的诊断和治疗；网络成瘾儿童青少年的心理评估和心理咨询；考前焦虑的心理咨询和指导等方面的临床工作。
     * updateTime : 2018-01-11 15:37:17
     */

    private String id_;
    private String username;
    private String password;
    private String name;
    private String headUrl;
    private String sex;
    private String age;
    private String mobileNO;
    private String hosName;
    private String adeptContent;
    private String outpatientTimeStr;
    private String healthJobTitle;
    private String teachJobTitle;
    private String updateTime;

    private int ischeckIn;
    private int totalPointNum;

    public String getId_() {
        return id_;
    }

    public void setId_(String id_) {
        this.id_ = id_;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMobileNO() {
        return mobileNO;
    }

    public void setMobileNO(String mobileNO) {
        this.mobileNO = mobileNO;
    }

    public String getHosName() {
        return hosName;
    }

    public void setHosName(String hosName) {
        this.hosName = hosName;
    }

    public String getAdeptContent() {
        return adeptContent;
    }

    public void setAdeptContent(String adeptContent) {
        this.adeptContent = adeptContent;
    }

    public String getOutpatientTimeStr() {
        return outpatientTimeStr;
    }

    public void setOutpatientTimeStr(String outpatientTimeStr) {
        this.outpatientTimeStr = outpatientTimeStr;
    }

    public String getHealthJobTitle() {
        return healthJobTitle;
    }

    public void setHealthJobTitle(String healthJobTitle) {
        this.healthJobTitle = healthJobTitle;
    }

    public String getTeachJobTitle() {
        return teachJobTitle;
    }

    public void setTeachJobTitle(String teachJobTitle) {
        this.teachJobTitle = teachJobTitle;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getIscheckIn() {
        return ischeckIn;
    }

    public void setIscheckIn(int ischeckIn) {
        this.ischeckIn = ischeckIn;
    }

    public int getTotalPointNum() {
        return totalPointNum;
    }

    public void setTotalPointNum(int totalPointNum) {
        this.totalPointNum = totalPointNum;
    }
}
