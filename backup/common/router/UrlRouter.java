package com.bigwangoo.sample.common.router;

import com.github.mzule.activityrouter.router.Routers;
import com.tianxiabuyi.txutils.TxUtils;

/**
 * Router
 * Created by wyd on 2017/5/31.
 */
public class UrlRouter {
    public static final String PREFIX = "demo://";

    public static final String MODULE_MAIN = "main";
    public static final String MODULE_APPS = "apps";
    public static final String MODULE_LOGIN = "login";
    public static final String MODULE_PERSON = "person";
    public static final String MODULE_SETTING = "setting";
    public static final String MODULE_CHAT = "chat";
    public static final String MODULE_FESTIVAL = "festival";
    public static final String MODULE_NOTIFY = "notify";

    public static final String ACTIVITY_APPS = "apps";
    public static final String ACTIVITY_LOGIN = "login";
    public static final String ACTIVITY_PERSON = "person";
    public static final String ACTIVITY_SETTING = "setting";
    public static final String ACTIVITY_CHAT = "chat";
    public static final String ACTIVITY_FESTIVAL = "festival";
    public static final String ACTIVITY_NOTIFY = "notify";

    public static final String METHOD_CLEAR = "clear";
    public static final String METHOD_SHOW = "show";

    /*apps*/
    public static void openApps() {
        open(ACTIVITY_APPS);
    }

    /*login*/
    public static void openLogin() {
        open(ACTIVITY_LOGIN);
    }

    /*person*/
    public static void openPerson() {
        open(ACTIVITY_PERSON);
    }

    /*chat*/
    public static void openChat() {
        open(ACTIVITY_CHAT);
    }

    /*festival*/
    public static void openFestival() {
        open(ACTIVITY_FESTIVAL);
    }

    /*notify*/
    public static void openNotify() {
        open(ACTIVITY_NOTIFY);
    }


    /*******************************************************************************************/

    /*clear*/
    public static void openMethodClear() {
        open(METHOD_CLEAR);
    }

    /*show*/
    public static void openMethodShow() {
        open(METHOD_SHOW);
    }


    /*******************************************************************************************/

    public static void open(String path) {
        Routers.open(TxUtils.getInstance().getContext(), PREFIX + path);
    }

}