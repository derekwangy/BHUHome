package com.bh.uhome.bhuhome.app;


import android.content.Context;
import android.support.multidex.MultiDex;

import com.bh.uhome.bhuhome.constant.APPConstant;
import com.bh.uhome.lib.base.app.RxRetrofitApp;
import com.ezvizuikit.open.EZUIKit;

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

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        //初始化EZUIKit
        EZUIKit.initWithAppKey(this, APPConstant.YS_APP_KEY);

    }

    public void onCreate() {
        super.onCreate();
        appContext = this.getApplicationContext();
    }
}
