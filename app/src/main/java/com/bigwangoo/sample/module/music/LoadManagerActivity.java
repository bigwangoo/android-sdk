package com.txby.sample_kit.demo.music;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;

import com.demo.apps.R;
import com.demo.apps.R2;
import com.txby.sample_kit.demo.music.adapter.MusicListAdapter;
import com.txby.sample_kit.demo.music.module.SongBean;
import com.demo.common.ui.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by YaoDong.Wang on 2017/7/27.
 */
public class LoadManagerActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R2.id.rv)
    RecyclerView rv;

    private List<SongBean> mData = new ArrayList<>();
    private MusicListAdapter mAdapter;

    public static void newInstance(Context context) {
        context.startActivity(new Intent(context, LoadManagerActivity.class));
    }

    @Override

    public int getViewByXml() {
        return R.layout.apps_activity_music_home;
    }

    @Override
    public void initView() {
        setData();

//        new SimpleCursorAdapter(this, R.layout.apps_item_music_txt, null,
//                new String[]{MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ARTIST},
//                new int[]{R.id.tv_song, R.id.tv_singer}, 0);
        mAdapter = new MusicListAdapter(mData);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(mAdapter);

        registerForContextMenu(rv);

        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(0, null, this);
    }

    private void setData() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.apps_menu_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader = new CursorLoader(this,
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                mData.add(new SongBean(title, artist));
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

}