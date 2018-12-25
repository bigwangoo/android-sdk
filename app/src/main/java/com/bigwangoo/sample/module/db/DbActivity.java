package com.bigwangoo.sample.module.db;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.bigwangoo.sample.R;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wangyd
 * @date 2018/11/28
 * @description description
 */
public class DbActivity extends AppCompatActivity implements BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.rv_test)
    RecyclerView rvTest;

    private DbAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_test);
        ButterKnife.bind(this);

        rvTest.setLayoutManager(new LinearLayoutManager(this));
        rvTest.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new DbAdapter(new ArrayList<DbTest>());
        mAdapter.setOnItemClickListener(this);
        rvTest.setAdapter(mAdapter);

        loadAll();
    }

    public void insert(View v) {
        insert();
    }

    public void loadAll(View v) {
        loadAll();
    }

    private void insert() {
//        Random random = new Random();
//        DbTest dbTest = new DbTest("test", random.nextInt());

        DbTest dbTest = new DbTest("test", "10");

        boolean insert = DbUtils.insert(dbTest);
        if (insert) {
            loadAll();
        }
    }

    private void loadAll() {
        List<DbTest> dbTests = DbUtils.loadAll();
        if (dbTests != null) {
            mAdapter.setNewData(dbTests);
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        DbTest dbTest = mAdapter.getData().get(position);
//        int age = dbTest.getAge();
        String age = dbTest.getAge();

        Log.e("TAG", "onItemClick: " + age);
    }
}
