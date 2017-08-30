package com.bh.uhome.lib.base.log;

import android.text.TextUtils;
import android.util.Log;

import java.util.logging.Logger;

/**
 * 全局日志类
 * 1，设置开关 release 关闭，debug 打开
 * 2，设置日志类型 error，warn，verbose等
 * @author 凌霄
 * @date 2017/7/25.
 * @time 17:14.
 * @description Describe
 */
public class LogUtil extends Logger {
    private static  boolean isDebug = true;
    protected LogUtil(String name, String resourceBundleName) {
        super(name, resourceBundleName);
    }

    /**
     * 打印常用信息日志
     * @param TAG
     * @param msg
     */
    public static void i(String TAG,String msg){
        if (isDebug && !TextUtils.isEmpty(msg)){
            Log.i(TAG,msg);
        }
    }

    /**
     * 打印错误信息
     * @param TAG
     * @param msg
     */
    public static void e(String TAG,String msg){
        if (isDebug && !TextUtils.isEmpty(msg)){
            Log.e(TAG,msg);
        }
    }

    /**
     * 打印 debug 日志
     * @param TAG
     * @param msg
     */
    public static void d(String TAG,String msg){
        if (isDebug && !TextUtils.isEmpty(msg)){
            Log.d(TAG,msg);
        }
    }
}
