package com.demo.common.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by YaoDong.Wang on 2017/7/27.
 */
public class PersonProvider extends ContentProvider {

    private SQLiteDatabase db;
    //创建uri匹配器
    UriMatcher um = new UriMatcher(UriMatcher.NO_MATCH);

    { //添加匹配规则 arg0:主机名 arg1:路径 arg2:匹配码
        um.addURI("com.demo.people", "person", 1); //content://com.demo.people/person
        um.addURI("com.demo.people", "handsome", 2); //content://com.demo.people/handsome
        um.addURI("com.demo.people", "person/#", 3); //content://com.demo.people/person/10
    }

    @Override
    public boolean onCreate() {
//        MyOpenHelper oh = new MyOpenHelper(getContext());
//        db = oh.getWritableDatabase();
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor = null;
        if (um.match(uri) == 1) {
            cursor = db.query("person", projection, selection, selectionArgs, null, null, sortOrder, null);
        } else if (um.match(uri) == 2) {
            cursor = db.query("handsome", projection, selection, selectionArgs, null, null, sortOrder, null);
        } else if (um.match(uri) == 3) {
            long id = ContentUris.parseId(uri);  //取出uri末尾携带的数字
            cursor = db.query("person", projection, "_id = ?", new String[]{"" + id}, null, null, sortOrder, null);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        if (um.match(uri) == 1) {
            return "vnd.android.cursor.dir/person";
        } else if (um.match(uri) == 2) {
            return "vnd.android.cursor.dir/handsome";
        } else if (um.match(uri) == 3) {
            return "vnd.android.cursor.item/person";
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        if (um.match(uri) == 1) {
            db.insert("person", null, values);
            //数据库改变了,内容提供者发出通知
            //arg0:通知发到哪个uri上，注册在这个uri上的内容观察者都可以收到通知
            if (getContext() != null)
                getContext().getContentResolver().notifyChange(uri, null);
        } else if (um.match(uri) == 2) {
            db.insert("handsome", null, values);
            if (getContext() != null)
                getContext().getContentResolver().notifyChange(uri, null);
        } else {
            throw new IllegalArgumentException("uri传错啦傻逼");
        }
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int i;
        if (um.match(uri) == 1) {
            i = db.delete("person", selection, selectionArgs);
        } else if (um.match(uri) == 2) {
            i = db.delete("handsome", selection, selectionArgs);
        } else {
            throw new IllegalArgumentException("uri又传错啦傻逼");
        }
        return i;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        return db.update("person", values, selection, selectionArgs);
    }
}