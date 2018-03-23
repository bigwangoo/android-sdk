package com.tianxiabuyi.txutils.network.model.bean;

import com.tianxiabuyi.txutils.network.model.BaseBean;
import com.tianxiabuyi.txutils.network.model.TxUser;

import java.util.List;

/**
 * Created by xjh1994 on 2016/7/18.
 * 登录
 */
public class LoginBean extends BaseBean {

    /**
     * uid : 1873
     * user_name : test
     * title :
     * skill :
     * name : 王浩然
     * avatar : http://image.eeesys.com/default/user_m.png
     * specialty :
     * ......
     */

    private List<AccountBean> account;

    public List<AccountBean> getAccount() {
        return account;
    }

    public void setAccount(List<AccountBean> account) {
        this.account = account;
    }

    private TxUser user;

    public TxUser getUser() {
        return user;
    }

    public void setUser(TxUser user) {
        this.user = user;
    }

    /**
     * token : 6af25391ed920e5cf3a4366004ebeb37
     * expires_in : 3600
     */

    private AuthBean auth;

    public AuthBean getAuth() {
        return auth;
    }

    public void setAuth(AuthBean auth) {
        this.auth = auth;
    }

    public static class AuthBean {
        private String token;
        private int expires_in;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getExpires_in() {
            return expires_in;
        }

        public void setExpires_in(int expires_in) {
            this.expires_in = expires_in;
        }
    }
}
