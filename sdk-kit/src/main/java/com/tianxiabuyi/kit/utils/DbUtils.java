package com.tianxiabuyi.kit.utils;

import android.util.Log;

import org.xutils.DbManager;
import org.xutils.db.table.TableEntity;
import org.xutils.x;

/**
 * Created by WangYD on 2017/6/20.
 */
public class DbUtils {

    private static final String DATABASE_NAME = "x.db";
    private static DbManager.DaoConfig daoConfig;

    /**
     * db manager
     */
    public static DbManager getDb() {
        if (daoConfig == null) {
            daoConfig = new DbManager.DaoConfig()
                    .setDbName(DATABASE_NAME)
                    .setDbVersion(1)
                    .setDbOpenListener(new DbManager.DbOpenListener() {
                        //设置数据库打开的监听
                        @Override
                        public void onDbOpened(DbManager db) {
                            //开启数据库支持多线程操作，提升性能，对写入加速提升巨大
                            db.getDatabase().enableWriteAheadLogging();
                        }
                    })
                    .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                        @Override
                        public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        }
                    })
                    .setTableCreateListener(new DbManager.TableCreateListener() {
                        //设置表创建的监听
                        @Override
                        public void onTableCreated(DbManager db, TableEntity<?> table) {
                            Log.i("JAVA", "onTableCreated：" + table.getName());
                        }
                    });
        }

        return x.getDb(daoConfig);
    }

}
