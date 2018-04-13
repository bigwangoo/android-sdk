package com.demo.common.network.manager;

import com.demo.common.network.TxCall;
import com.demo.common.network.service.TxServiceManager;
import com.demo.common.network.service.TxUserService;

/**
 * Created by wangyd on 2017/8/13.
 */
public class TxUserManager {

    static TxUserService service = null;

    private static TxUserService getInstanceService() {
        if (service == null) {
            synchronized (TxUserService.class) {
                if (service == null) {
                    service = TxServiceManager.createService(TxUserService.class);
                }
            }
        }
        return service;
    }

    public static TxCall login(String username, String password) {
        TxCall call = getInstanceService().login(username, password);
        return call;
    }
}
