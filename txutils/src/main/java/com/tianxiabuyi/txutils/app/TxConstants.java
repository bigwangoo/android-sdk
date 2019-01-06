package com.tianxiabuyi.txutils.app;

/**
 * @author xjh1994
 * @date 2016/7/15
 * @description 常量
 */
public class TxConstants {

    // BASE URL
    public static final String BASE_URL = "http://demoapi.eeesys.com:18088/v2/";
    public static final String UPDATE_URL = "http://api.eeesys.com:18088/v2/app/update";
    public static final String UPLOAD_FILE_URL = "http://cloud.eeesys.com/pu/upload.php";
    // VALUE
    public static final long READ_TIMEOUT = 2 * 60L;
    public static final long FILE_TIMEOUT = 5 * 60L;
    // API
    public static final String TOKEN_REFRESH_URL = "token/refresh";
    public static final String TOKEN_REVOKE_URL = "token/revoke";
    public static final String USER_CREATE_URL = "user/create";
    public static final String USER_LOGIN_URL = "user/login";
    public static final String USER_PASSWORD_URL = "user/password";
    public static final String USER_AVATAR_URL = "user/avatar";

    // EXTRA
    public static final String EXTRA_TOOLBAR_COLOR = "extra_toolbar_color";
    public static final String EXTRA_LOGO = "extra_logo";
    public static final String EXTRA_APP_NAME = "extra_app_name";
    public static final String EXTRA_VERSION_NAME = "extra_version_name";
    public static final String EXTRA_RECYCLER_ACTIVITY_TYPE = "extra_recycler_activity_type";
    public static final String EXTRA_TOKEN_EXPIRES = "extra_token_expires";

    // KEY
    public static final String KEY_TOKEN = "key_token";
    public static final String KEY_USER = "key_user";
    public static final String KEY_USER_ID = "key_user_id";
    public static final String KEY_USERNAME = "key_username";
}
