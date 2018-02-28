package com.tianxiabuyi.txutils.network.model.bean;

import com.tianxiabuyi.txutils.db.annotation.Column;
import com.tianxiabuyi.txutils.db.annotation.Table;

/**
 * Created by Administrator on 2017/5/2.
 * 第三方登录用户信息
 */
@Table(name = "AccountBean")
public class AccountBean {

    /**
     * union_id : 532D928BD48B579B74BA4813DC49D899
     * pid : 30614
     * source : qq
     */
    @Column(name = "union_id", isId = true, autoGen = false)
    private String union_id;
    @Column(name = "pid")
    private int pid;
    @Column(name = "source")
    private String source;
    @Column(name = "token")
    private String token;

    public AccountBean() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUnion_id() {
        return union_id;
    }

    public void setUnion_id(String union_id) {
        this.union_id = union_id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
