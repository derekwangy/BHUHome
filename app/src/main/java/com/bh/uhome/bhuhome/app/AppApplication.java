package com.bh.uhome.bhuhome.app;


import android.content.Context;
import android.support.multidex.MultiDex;

import com.bh.uhome.bhuhome.constant.APPConstant;
import com.bh.uhome.lib.base.app.RxRetrofitApp;
import com.videogo.openapi.EZOpenSDK;

/**
 * @author 凌霄
 * @date 2017/8/29.
 * @time 19:29.
 * @description Describe
 */
public class AppApplication extends RxRetrofitApp {
    private static Context appContext;
    //萤石token
    public static String YS_TOKEN = "";

    public static Context getInstance() {
        return appContext;
    }

    public static EZOpenSDK getOpenSDK() {
        return EZOpenSDK.getInstance();
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public void onCreate() {
        super.onCreate();
        appContext = this.getApplicationContext();
        RxRetrofitApp.init(this);
        //初始化EZUIKit
        initSDK();
    }

    private void initSDK() {
        {
            /**
             * sdk日志开关，正式发布需要去掉
             */
            EZOpenSDK.showSDKLog(true);

            /**
             * 设置是否支持P2P取流,详见api
             */
            EZOpenSDK.enableP2P(false);

            /**
             * APP_KEY请替换成自己申请的
             */
            EZOpenSDK.initLib(this, APPConstant.YS_APP_KEY, "");
        }
    }
}
