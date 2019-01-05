package com.bigwangoo.sample.module.greendao.activity;

import com.bigwangoo.sample.R;
import com.bigwangoo.sample.greendao.UserDao;
import com.bigwangoo.sample.module.greendao.model.User;
import com.bigwangoo.sample.module.greendao.utils.DbHelper;
import com.tianxiabuyi.txutils.base.activity.BaseTxActivity;

import org.greenrobot.greendao.rx.RxQuery;

/**
 * 演示 数据库Rx
 * <p>
 * GitHub：https://github.com/greenrobot/greenDAO
 * 更新：https://github.com/yuweiguocn/GreenDaoUpgradeHelper
 * 加密：https://github.com/sqlcipher/android-database-sqlcipher
 *
 * @author wangyd
 * @date 2018/12/29
 */
public class GreenDaoActivity extends BaseTxActivity {

    @Override
    public int getViewByXml() {
        return R.layout.activity_db_greendao;
    }

    @Override
    public void initView() {
        RxQuery<User> rxQuery = DbHelper.getInstance().getDaoSession()
                .getUserDao()
                .queryBuilder()
                .orderAsc(UserDao.Properties.Id)
                .rx();


    }

    @Override
    public void initData() {

    }
}
