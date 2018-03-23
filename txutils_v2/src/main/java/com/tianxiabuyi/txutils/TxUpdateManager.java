package com.tianxiabuyi.txutils;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.tianxiabuyi.txutils.network.callback.ResponseCallback;
import com.tianxiabuyi.txutils.network.callback.inter.FileResponseCallback;
import com.tianxiabuyi.txutils.network.exception.TxException;
import com.tianxiabuyi.txutils.network.model.HttpResult;
import com.tianxiabuyi.txutils.network.model.bean.VersionBean;
import com.tianxiabuyi.txutils.network.service.TxUpdateService;
import com.tianxiabuyi.txutils.permissions.PermissionsResultAction;
import com.tianxiabuyi.txutils.permissions.TxPermissionsManager;
import com.tianxiabuyi.txutils.util.AppUtils;
import com.tianxiabuyi.txutils.util.FileProvider7;
import com.tianxiabuyi.txutils.util.FileUtils;
import com.tianxiabuyi.txutils.util.NetUtils;
import com.tianxiabuyi.txutils.util.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;

/**
 * @author xjh1994
 * @date 2016/8/31
 * @description 软件更新管理
 */
public class TxUpdateManager {

    private static boolean isOnlyWifi = false;

    /**
     * 软件更新
     *
     * @param activity Activity
     */
    public static void update(final Activity activity) {
        TxUpdateManager.update(activity, false);
    }

    /**
     * 软件更新
     * 手动检查 带提示
     *
     * @param activity Activity
     * @param toast    isShow
     */
    public static void update(final Activity activity, final boolean toast) {
        if (isOnlyWifi && !NetUtils.isWifi(activity)) {
            //非WiFi下不更新
            return;
        }

        TxUpdateService service = TxServiceManager.createServiceEncrypt(TxUpdateService.class);
        service.update().enqueue(new ResponseCallback<HttpResult<VersionBean>>(toast) {
            @Override
            public void onSuccess(HttpResult<VersionBean> result) {
                compareVersion(activity, result.getData(), toast);
            }

            @Override
            public void onError(TxException e) {

            }
        });
    }

    /**
     * 软件更新
     * 自定义更新结果
     *
     * @param context  Context
     * @param listener TxUpdateListener
     */
    public static void checkUpdate(final Context context, final TxUpdateListener listener) {
        TxUpdateService service = TxServiceManager.createServiceEncrypt(TxUpdateService.class);
        service.update().enqueue(new ResponseCallback<HttpResult<VersionBean>>() {
            @Override
            public void onSuccess(HttpResult<VersionBean> result) {
                boolean hasUpdate = false;
                VersionBean updateResult = result.getData();
                if (updateResult != null) {
                    if (AppUtils.getVersionCode(context) < updateResult.getVersion_code()) {
                        hasUpdate = true;
                    }
                }
                listener.onUpdate(hasUpdate, result.getData());
            }

            @Override
            public void onError(TxException e) {
                listener.onError(e);
            }
        });
    }

    /**
     * 比较当前版本
     *
     * @param activity Activity
     * @param info     VersionBean
     * @param toast    isShow
     */
    private static void compareVersion(final Activity activity,
                                       final VersionBean info,
                                       final boolean toast) {
        if (info != null) {
            if (AppUtils.getVersionCode(activity) < info.getVersion_code()) {
                new AlertDialog.Builder(activity)
                        .setTitle("检测到新版本 " + info.getVersion())
                        .setMessage(TextUtils.concat("更新内容：\n", info.getDescription()))
                        .setCancelable(false)
                        .setNegativeButton(R.string.tx_cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton(R.string.tx_update, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                                TxPermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(activity,
                                        perms, new PermissionsResultAction() {
                                            @Override
                                            public void onGranted() {
                                                //下载apk
                                                String appName = AppUtils.getAppName(activity) + info.getTime();
                                                downloadApk(activity, appName, info.getVersion(), info.getUrl());
                                            }

                                            @Override
                                            public void onDenied(String permission) {
                                                if (toast) {
                                                    ToastUtils.show("授权失败");
                                                }
                                            }
                                        });
                            }
                        }).create().show();
            } else {
                if (toast) {
                    ToastUtils.show(activity.getString(R.string.tx_already_newest_version));
                }
            }
        }
    }

    /**
     * 下载 APK文件
     *
     * @param context Context
     * @param appName name
     * @param version version
     * @param url     downloadUrl
     */
    private static void downloadApk(final Activity context, String appName, String version, final String url) {
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setIndeterminate(false);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMax(100);
        dialog.setTitle(context.getString(R.string.tx_software_update));
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                OkHttpUtils.getInstance().cancelTag(url);
            }
        });
        dialog.show();

        File file = new File(FileUtils.getExternalDownloadDir(), context.getPackageName());
        if (!file.exists()) {
            boolean mkdir = file.mkdir();
        }
        String path = file.getAbsolutePath();
        // download
        TxFileManager.download(url, path, appName + "_" + version + ".apk", new FileResponseCallback() {
            @Override
            public void onProgress(int progress, long total) {
                dialog.setProgress(progress);
            }

            @Override
            public void onSuccess(File response) {
                dialog.dismiss();
                installApk(context, response);
            }

            @Override
            public void onError(TxException e) {
                dialog.dismiss();
                ToastUtils.show(context.getString(R.string.tx_cancel_update));
            }
        });
    }

    /**
     * 安装 APK文件
     */
    private static void installApk(Context context, File apkFile) {
        // 通过Intent安装APK文件
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        FileProvider7.setIntentDataAndType(context, intent,
                "application/vnd.android.package-archive", apkFile, true);
        context.startActivity(intent);
    }

    /**
     * 设置只在WiFi下更新
     */
    public static void setUpdateOnlyWifi(boolean isOnlyWifi) {
        TxUpdateManager.isOnlyWifi = isOnlyWifi;
    }

    public interface TxUpdateListener {
        void onUpdate(boolean hasUpdate, VersionBean updateResult);

        void onError(TxException e);
    }
}

