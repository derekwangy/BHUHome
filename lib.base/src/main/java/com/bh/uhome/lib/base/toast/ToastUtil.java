package com.bh.uhome.lib.base.toast;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * 全局Toast类
 * @author 凌霄
 * @date 2017/7/25.
 * @time 17:19.
 * @description Describe
 */
public class ToastUtil {
    /**
     * Toast Long Msg
     * @param mContext
     * @param msg
     */
    public static void showLong(Context mContext,String msg){
        if (!TextUtils.isEmpty(msg) && mContext != null){
            Toast.makeText(mContext,msg,Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Toast Short Msg
     * @param mContext
     * @param msg
     */
    public static void showShort(Context mContext,String msg){
        if (!TextUtils.isEmpty(msg) && mContext != null){
            Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
        }
    }
}
