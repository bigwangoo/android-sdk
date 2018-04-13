package com.tianxiabuyi.txutils;

import com.tianxiabuyi.txutils.network.model.bean.AccountBean;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.List;

/**
 * @author xjh1994
 * @date 16/12/12
 * @description 第三方账号
 */
public class TxAccountService {

    private static DbManager db = TxUtils.getDb();

    /**
     * 获取本地所有第三方账号
     */
    public static List<AccountBean> getAccounts() {
        try {
            return db.findAll(AccountBean.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void save(List<AccountBean> accounts) {
        try {
            db.delete(AccountBean.class);
            db.save(accounts);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAll() {
        try {
            List<AccountBean> all = db.findAll(AccountBean.class);
            if (all != null) {
                db.delete(all);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查找第三方账号
     */
    public static AccountBean getAccount(String source) {
        try {
            return db.selector(AccountBean.class)
                    .where("source", "=", source)
                    .findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void save(AccountBean accountBean) {
        try {
            db.saveOrUpdate(accountBean);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public static void remove(String union_id) {
        try {
            db.deleteById(AccountBean.class, union_id);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}
