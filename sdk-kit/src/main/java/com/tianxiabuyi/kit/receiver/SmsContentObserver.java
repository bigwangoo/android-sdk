package com.tianxiabuyi.kit.receiver;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 监听短信数据库
 * Created by wangyd on 2017/7/30.
 */

public class SmsContentObserver extends ContentObserver {

    private Context mContext;
    private Handler mHandler;
    private int mCode;
    private Cursor mCursor;

    public SmsContentObserver(Context context, Handler handler, int code) {
        super(handler);
        this.mContext = context;
        this.mHandler = handler;
        this.mCode = code;
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
//        //读取收件箱中指定号码的短信
//        cursor = managedQuery(Uri.parse("content://sms/inbox"),
//                new String[]{"_id", "address", "read", "body"},
//                " address=? and read=?", new String[]{"1065811201", "0"}, "_id desc");
        String code = "";
        Uri inBoxUri = Uri.parse("content://sms/inbox");
        // 根据号码查询
        Cursor c = mContext.getContentResolver().query(inBoxUri,
                null, null, null, "date desc");
        if (c != null) {
            if (c.moveToFirst()) {
                String address = c.getString(c.getColumnIndex("address"));
                String body = c.getString(c.getColumnIndex("body"));
                // Log.i("TAG", "发件人为: " + address + "\n" + "短信内容: " + body);
                Pattern pattern = Pattern.compile("\\d{6}");
                Matcher matcher = pattern.matcher(body);
                if (matcher.find()) {
                    code = matcher.group(0);
                    mHandler.obtainMessage(mCode, code).sendToTarget();
                }
            }
            c.close();
        }
    }

}
