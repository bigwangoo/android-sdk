package com.tianxiabuyi.txutils.widget.avatar;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.tianxiabuyi.txutils.R;
import com.tianxiabuyi.txutils.util.FileProvider7;

import java.io.File;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by xjh1994 on 16/12/2.
 * 图片选择activity
 */
public class TxPhotoHelperActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private int mType;
    private int mQuality;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = getIntent().getIntExtra(TxPhotoHelper.EXTRA_TYPE, 0);
        mQuality = getIntent().getIntExtra(TxPhotoHelper.EXTRA_QUALITY, 100);

        if (mType == TxPhotoHelper.TAKE_PHOTO) {
            cameraTask();
        } else if (mType == TxPhotoHelper.CHOOSE_PICTURE) {
            galleryTask();
        }
    }

    /**
     * 拍照
     */
    @AfterPermissionGranted(TxPhotoHelper.RC_CAMERA_PERM)
    public void cameraTask() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            TxPhotoHelper.takePhoto(this);
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.tx_rationale_camera),
                    TxPhotoHelper.RC_CAMERA_PERM, Manifest.permission.CAMERA);
        }
    }

    /**
     * 相册
     */
    @AfterPermissionGranted(TxPhotoHelper.RC_GALLERY_PERM)
    public void galleryTask() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            TxPhotoHelper.pickFromGallery(this);
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.tx_rationale_gallery),
                    TxPhotoHelper.RC_GALLERY_PERM, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            finish();
            return;
        }
        //拍照->裁剪
        if (requestCode == TxPhotoHelper.TAKE_PHOTO) {
            File tempFile = TxPhotoHelper.getTempFile();
            if (tempFile.exists()) {
                Uri uri = FileProvider7.getUriForFile(this, tempFile);
                TxPhotoHelper.cropPicture(this, uri);
                TxPhotoHelper.setTempFile(null);
            } else {
                finish();
            }
        }
        //相册->裁剪
        else if (requestCode == TxPhotoHelper.CHOOSE_PICTURE) {
            if (data != null && data.getData() != null) {
                // 获取图片的uri
                Uri uri = data.getData();
                TxPhotoHelper.cropPicture(this, uri);
            } else {
                finish();
            }
        }
        //裁剪->上传头像
        else if (requestCode == TxPhotoHelper.CROP_PICTURE) {
            if (data != null) {
                Bitmap bitmap = data.getParcelableExtra("data");
                if (bitmap != null) {
                    //结束Activity返回
                    TxPhotoHelper.uploadFile(this, bitmap, mQuality);
                } else {
                    finish();
                }
            } else {
                finish();
            }
        }

        if (requestCode == TxPhotoHelper.RC_SETTINGS_SCREEN) {
            Toast.makeText(this, R.string.tx_returned_from_app_settings_to_activity, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this)
                    .setRationale(getString(R.string.tx_rationale_ask_again))
                    .setTitle(getString(R.string.tx_title_settings_dialog))
                    .setPositiveButton(getString(R.string.tx_setting))
                    .setNegativeButton(getString(R.string.tx_cancel))
                    .setRequestCode(TxPhotoHelper.RC_SETTINGS_SCREEN)
                    .build()
                    .show();
        } else {
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
