package com.bigwangoo.sample.module.greendao.utils;

import android.content.Context;

import com.bigwangoo.sample.greendao.DaoMaster;
import com.bigwangoo.sample.greendao.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * 数据库创建演示
 *
 * @author wangyd
 * @date 2018/12/29
 */
public class DbHelper {

    // 数据库是否加密的标识
    private static final boolean ENCRYPTED = true;
    private static final String DB_NAME = ENCRYPTED ? "notes-db-encrypted" : "notes-db";
    private static final String DB_PWD = "super-secret";
    private DaoSession mDaoSession;

    private DbHelper() {
    }

    private static class SingletonHolder {
        private static final DbHelper INSTANCE = new DbHelper();
    }

    public static DbHelper getInstance() {
        return DbHelper.SingletonHolder.INSTANCE;
    }

    /**
     * 初始化数据库
     */
    public void initDao(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME);
        // 获取数据库读写的权限，加密调用helper.getEncryptedWritableDb("super-secret")，参数为设置的密码
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb(DB_PWD) : helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}
