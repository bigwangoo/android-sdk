package com.bigwangoo.sample.module.db;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;

/**
 * @author wangyd
 * @date 2018/11/28
 * @description description
 */
public class DbUtils {

    /**
     * 数据库名
     */
    private static final String DB_NAME = "test.db";
    /**
     * 数据库版本
     */
    private static final int DB_VERSION = 2;

    private static DbManager dbManager;

    /**
     * 获取 DbManager
     */
    public static synchronized DbManager getDb() {
        if (dbManager == null) {
            dbManager = x.getDb(new DbManager.DaoConfig()
                    .setDbName(DB_NAME)
                    .setDbVersion(DB_VERSION)
                    .setDbOpenListener(new DbManager.DbOpenListener() {
                        @Override
                        public void onDbOpened(DbManager db) {
                            db.getDatabase().enableWriteAheadLogging();
                        }
                    })
                    .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                        @Override
                        public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                            // 数据库升级操作
                        }
                    }));
        }

        return dbManager;
    }

    public static boolean insert(DbTest test) {
        try {
            return getDb().saveBindingId(test);
        } catch (DbException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static List<DbTest> loadAll() {
        try {
            return getDb().selector(DbTest.class).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }

        return null;
    }
}
