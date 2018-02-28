package com.tianxiabuyi.txutils.config;

/**
 * @author xjh1994
 * @date 2016/7/15
 * @description 医院信息配置
 */
public class TxConstants {

    //////////////////////// old

    /**
     * URL
     */
    public static final String BASE_URL = "http://api.tianxiabuyi.com:18080/v3";
    public static final String UPDATE_URL = "http://api.tianxiabuyi.com:18080/v3/app/version";
    public static final String FEEDBACK_URL = "http://api.tianxiabuyi.com:18080/v3/app/feedback";
    public static final String UPLOAD_FILE_URL = "http://cloud.eeesys.com/pu/upload.php";
    /**
     * KEY
     */
    public static final String KEY_APP_TYPE = "app_type";
    public static final String KEY_HOSPITAL = "hospital";
    public static final String KEY_TOKEN = "token";


    //////////////////////// new

    /**
     * URL
     */
    public static final String FILE_UPLOAD_URL = "http://api.eeesys.com:60000/file/uploadFile";
    public static final String FILES_UPLOAD_URL = "http://api.eeesys.com:60000/file/uploadFiles";


    //////////////////////// common

    /**
     * VALUE
     */
    public static final String DB_NAME = "txby_db";
    public static final long TIMEOUT = 20L;
    public static final long FILE_TIMEOUT = 5 * 60L;
    /**
     * API
     */
    public static final String TOKEN_REFRESH_URL = "token/refresh";
    public static final String TOKEN_REVOKE_URL = "token/revoke";
    public static final String USER_REGISTER_URL = "user/create";
    public static final String USER_LOGIN_URL = "user/login";
    public static final String USER_PASSWORD_URL = "user/password";
    public static final String USER_AVATAR_URL = "user/avatar";
    public static final String USER_MODIFY_URL = "user/modify";
    public static final String EASE_REGISTER_URL = "access_token/register";
    /**
     * EXTRA
     */
    public static final String EXTRA_TOOLBAR_COLOR = "extra_toolbar_color";
    public static final String EXTRA_LOGO = "extra_logo";
    public static final String EXTRA_APP_NAME = "extra_app_name";
    public static final String EXTRA_VERSION_NAME = "extra_version_name";
    public static final String EXTRA_TOKEN_EXPIRES = "extra_token_expires";
    public static final String EXTRA_RONG_OFFLINE = "extra_rong_offline";
    /**
     * CODE
     */
    public static final int REQUEST_CODE_PICK_IMAGE = 100;
    public static final int REQUEST_CODE_GET_IMAGE_URL = 200;

}
