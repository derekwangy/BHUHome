package com.bh.uhome.bhuhome.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.bh.uhome.lib.base.log.LogUtil;

/**
 * @author 凌霄
 * @date 2017/6/29.
 * @time 11:14.
 * @description Describe
 */
public class CommonUtil {

    public static boolean isNull(String... strings) {
            if (null == strings) {
                return true;
            }
            for (String string : strings) {
                if (TextUtils.isEmpty(string)) {
                    return true;
                }
            }
        return false;
    }

    private static long exitTime = 0;

    public static void exit(Context mContext) {
        try {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(mContext, "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
//                SysActivityManage.getInstance().exit();
                System.exit(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取版本编号，更新版本使用
     *
     * @param mContext
     * @return
     */
    public static int getAppVersionCode(Context mContext) {
        int versionCodeLocal = 0;
        try {
            PackageManager pm = mContext.getPackageManager();//context为当前Activity上下文
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), 0);
            versionCodeLocal = pi.versionCode;//版本升级用
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCodeLocal;
    }

    /**
     * 获取本地软件版本号名称
     */
    public static String getLocalVersionName(Context ctx) {
        String localVersion = "";
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionName;
            LogUtil.d("TAG", "本软件的版本号。。" + localVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }
}
