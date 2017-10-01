package com.bh.uhome.bhuhome.util;

import android.app.Activity;

import com.bh.uhome.bhuhome.app.AppApplication;
import com.videogo.exception.BaseException;
import com.videogo.openapi.EZGlobalSDK;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.openapi.bean.EZAreaInfo;
import com.videogo.util.LogUtil;

import java.util.List;

/**
 * @author 凌霄
 * @date 2017/9/30.
 * @time 16:15.
 * @description Describe
 */
public class ActivityUtils {
    /**
     * 处理token过期的错误
     *
     * @throws
     */
    public static void handleSessionException(Activity activity) {
        goToLoginAgain(activity);
    }

    public static void goToLoginAgain(Activity activity) {
        if (EZGlobalSDK.class.isInstance(AppApplication.getOpenSDK())) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        List<EZAreaInfo> areaList = EZGlobalSDK.getInstance().getAreaList();
                        if (areaList != null) {
                            LogUtil.debugLog("application", "list count: " + areaList.size());

                            EZAreaInfo areaInfo = areaList.get(0);
                            EZGlobalSDK.getInstance().openLoginPage(areaInfo.getId());
                        }
                    } catch (BaseException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            EZOpenSDK.getInstance().openLoginPage();
        }
    }

}
