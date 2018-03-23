package com.tianxiabuyi.txutils.network.model;

/**
 * Created by jjj on 2018/1/8.
 *
 * @description: 患者信息
 */
public class TxPatient extends TxUser {
    /**
     * id : 14
     * loginName : wang001
     * nickName : 1啥啥啥ss很神奇
     * displayName : 1啥啥啥ss很神奇
     * headUrl : http://api.eeesys.com:60000/file/show/IMAGE/2018117/191cdcdfc5e54ce0964c32abd5c163ff.jpg
     * sex : 0
     * age : 13
     * mobileNO : 18344684235
     * firstContact : 测试
     * firstContactTel : 18344684233
     * totalPointNum : 9
     * ischeckIn : 0
     */

    private String createTime;
    private int id_;
    private String id;
    private String loginName;
    private String passWord;
    private String nickName;
    private String displayName;
    private String headUrl;
    private String sex;
    private String age;
    private String mobileNO;
    private String firstContact;
    private String firstContactTel;
    private int totalPointNum;
    private int ischeckIn;
    private int status;
    private String updateTime;

    // 最早时间
    private String earlyTime;
    // sickId : 14 医生端 患者id
    private int sickId;
    //
    private boolean isChecked;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getId_() {
        return id_;
    }

    public void setId_(int id_) {
        this.id_ = id_;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

    public String getFirstContact() {
        return firstContact;
    }

    public void setFirstContact(String firstContact) {
        this.firstContact = firstContact;
    }

    public String getFirstContactTel() {
        return firstContactTel;
    }

    public void setFirstContactTel(String firstContactTel) {
        this.firstContactTel = firstContactTel;
    }

    public int getTotalPointNum() {
        return totalPointNum;
    }

    public void setTotalPointNum(int totalPointNum) {
        this.totalPointNum = totalPointNum;
    }

    public int getIscheckIn() {
        return ischeckIn;
    }

    public void setIscheckIn(int ischeckIn) {
        this.ischeckIn = ischeckIn;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getEarlyTime() {
        return earlyTime;
    }

    public void setEarlyTime(String earlyTime) {
        this.earlyTime = earlyTime;
    }

    public int getSickId() {
        return sickId;
    }

    public void setSickId(int sickId) {
        this.sickId = sickId;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
