package com.tianxiabuyi.txutils.widget.avatar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.tianxiabuyi.txutils.R;
import com.tianxiabuyi.txutils.TxFileManager;
import com.tianxiabuyi.txutils.network.callback.ResponseCallback;
import com.tianxiabuyi.txutils.network.exception.TxException;
import com.tianxiabuyi.txutils.network.model.result.TxFileResult;
import com.tianxiabuyi.txutils.util.FileProvider7;
import com.tianxiabuyi.txutils.util.ToastUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;


/**
 * Created by xjh1994 on 16/12/2.
 * 图片选择 上传, 返回URL
 */
public class TxPhotoHelper {

    // action 默认: 选择+上传头像
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PICTURE = 2;
    public static final int CROP_PICTURE = 3;

    // requestCode
    public static final int RC_CAMERA_PERM = 123;
    public static final int RC_GALLERY_PERM = 124;
    public static final int RC_SETTINGS_SCREEN = 125;

    public static final String PHOTO_FILE_NAME = "temp.jpg";

    private static final String EXTRA_PREFIX = "TxPhotoHelper";

    public static final String EXTRA_ASPECT_RATIO_X = EXTRA_PREFIX + ".AspectRatioX";
    public static final String EXTRA_ASPECT_RATIO_Y = EXTRA_PREFIX + ".AspectRatioY";

    public static final String EXTRA_MAX_SIZE_X = EXTRA_PREFIX + ".MaxSizeX";
    public static final String EXTRA_MAX_SIZE_Y = EXTRA_PREFIX + ".MaxSizeY";

    public static final String EXTRA_TYPE = "type";
    public static final String EXTRA_QUALITY = "quality";
    public static final String EXTRA_RESULT = "result";
    public static final String EXTRA_RESULT_ERROR = "result_error";

    public static final int RESULT_ERROR = 99;

    private static File tempFile;

    private Bundle mOptionsBundle;

    private PopupWindow popupWindow;

    public static TxPhotoHelper newInstance() {
        return new TxPhotoHelper();
    }

    private TxPhotoHelper() {
        mOptionsBundle = new Bundle();
    }

    public TxPhotoHelper withAspectRatio(float x, float y) {
        mOptionsBundle.putFloat(EXTRA_ASPECT_RATIO_X, x);
        mOptionsBundle.putFloat(EXTRA_ASPECT_RATIO_Y, y);
        return this;
    }

    public TxPhotoHelper useSourceImageAspectRatio() {
        mOptionsBundle.putFloat(EXTRA_ASPECT_RATIO_X, 0);
        mOptionsBundle.putFloat(EXTRA_ASPECT_RATIO_Y, 0);
        return this;
    }

    /**
     * Set maximum size for result cropped image.
     *
     * @param width  max cropped image width
     * @param height max cropped image height
     */
    public TxPhotoHelper withMaxResultSize(@IntRange(from = 100) int width, @IntRange(from = 100) int height) {
        mOptionsBundle.putInt(EXTRA_MAX_SIZE_X, width);
        mOptionsBundle.putInt(EXTRA_MAX_SIZE_Y, height);
        return this;
    }

    /**
     * 图片质量 默认100不压缩
     */
    public TxPhotoHelper quality(int quality) {
        mOptionsBundle.putInt(EXTRA_QUALITY, quality);
        return this;
    }

    public void start(@NonNull Activity activity) {
        initPopupWindow(activity);
    }

    /**
     * 初始化PopupWindow
     */
    private void initPopupWindow(final Activity activity) {
        View view = LayoutInflater.from(activity).inflate(R.layout.tx_pop_upload_avatar, null);

        TextView tvTakePhoto = (TextView) view.findViewById(R.id.tvTakePhoto);
        TextView tvPickFromGallery = (TextView) view.findViewById(R.id.tvPickFromGallery);
        TextView tvCancel = (TextView) view.findViewById(R.id.tvCancel);
        tvTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                activity.startActivityForResult(getIntent(activity, TAKE_PHOTO), TAKE_PHOTO);
            }
        });
        tvPickFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                activity.startActivityForResult(getIntent(activity, CHOOSE_PICTURE), CHOOSE_PICTURE);
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        popupWindow.setAnimationStyle(R.style.TxAnimBottom);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                return false;
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setAlpha(activity, 1f);
            }

        });
        setAlpha(activity, 0.7f);
        popupWindow.showAtLocation(activity.findViewById(android.R.id.content),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 设置背景透明度
     */
    private void setAlpha(Activity activity, float alpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = alpha;
        activity.getWindow().setAttributes(lp);
    }

    @NonNull
    private Intent getIntent(Activity activity, int type) {
        return new Intent(activity, TxPhotoHelperActivity.class)
                .putExtras(mOptionsBundle)
                .putExtra(EXTRA_TYPE, type);
    }

    public static void setTempFile(File tempFile) {
        TxPhotoHelper.tempFile = tempFile;
    }

    public static File getTempFile() {
        return tempFile;
    }


    /**
     * 拍照
     */
    public static void takePhoto(Activity activity) {
        takePhoto(activity, TAKE_PHOTO);
    }

    public static void takePhoto(Activity activity, int requestCode) {
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_FILE_NAME);
            if (tempFile.exists()) {
                tempFile.delete();
            }
            Uri uri = FileProvider7.getUriForFile(activity, tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            activity.startActivityForResult(intent, requestCode);
        } else {
            ToastUtils.show("SD卡不可用");
        }
    }

    /**
     * 相册
     */
    public static void pickFromGallery(Activity activity) {
        pickFromGallery(activity, CHOOSE_PICTURE);
    }

    public static void pickFromGallery(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 裁剪图片
     */
    public static void cropPicture(Activity activity, Uri uri) {
        cropPicture(activity, uri, CROP_PICTURE);
    }

    public static void cropPicture(Activity activity, Uri uri, int requestCode) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        // 处理黑边
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        // 图片格式
        intent.putExtra("outputFormat", "JPEG");
        // 取消人脸识别
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 上传文件
     */
    public static void uploadFile(final Activity activity, Bitmap bitmap, int quality) {
        Uri uri = compressBitmap(activity, bitmap, quality);
        TxFileManager.uploadFile(uri.getPath(), new ResponseCallback<TxFileResult>() {
            @Override
            public void onSuccess(TxFileResult result) {
                Intent intent = new Intent();
                intent.putExtra(EXTRA_RESULT, result);
                activity.setResult(Activity.RESULT_OK, intent);
                activity.finish();
            }

            @Override
            public void onError(TxException e) {
                Intent intent = new Intent();
                intent.putExtra(EXTRA_RESULT_ERROR, e.getDetailMessage());
                activity.setResult(RESULT_ERROR);
                activity.finish();
            }
        });
    }

    /**
     * 压缩Bitmap到Uri
     */
    private static Uri compressBitmap(Activity activity, Bitmap bitmap, int quality) {
        File compressedFile = new File(getAlbumDir(activity), UUID.randomUUID().toString() + ".jpg");
        try {
            FileOutputStream fos = new FileOutputStream(compressedFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Uri.fromFile(compressedFile);
    }

    /**
     * 获取存储路径
     */
    private static File getAlbumDir(Context context) {
        String packageName = context.getPackageName();
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                packageName.substring(packageName.lastIndexOf(".") + 1, packageName.length()));
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    /**
     * 判断sd卡是否可用
     */
    private static boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
}