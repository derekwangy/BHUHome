package com.bh.uhome.bhuhome.db.sharedprefer;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * sharepreference 存储
 * Created by 凌霄 on 17/6/27.
 */
public class ShareCache {

    private static ShareCache mLoginType = null;

    private ShareCache() {
    };

    public synchronized static ShareCache getInstance() {
        if (mLoginType == null) {
            mLoginType = new ShareCache();
        }
        return mLoginType;
    }

    //微脉数据缓存
    private SharedPreferences sharedPreferences = null;
    public final static String SAVE_WEIMAI_DB = "weimai_db";     //保存数据—db
    public final static String ENABLE_DOWNLOAD_KEY = "enable_download_key";     //保存是否下载 key


    /**
     * 保存登录类型
     * @param mActivity
     * @param type
     */
    public void saveSharefEnableDownload(Context mActivity,boolean type) {
        sharedPreferences = mActivity.getSharedPreferences(SAVE_WEIMAI_DB, Context.MODE_PRIVATE);
        // 对数据进行编辑,返回的是一个Editor对象
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(ENABLE_DOWNLOAD_KEY,type);
        editor.commit();
    }


    /**
     * 获取登录类型
     * @param mActivity
     * @return
     */
    public boolean getSharefEnableDownload(Context mActivity) {
        boolean  resultType = false;
        try{
            sharedPreferences = mActivity.getSharedPreferences(SAVE_WEIMAI_DB, Context.MODE_PRIVATE);
            // 对数据进行编辑,返回的是一个Editor对象
            resultType = sharedPreferences.getBoolean(ENABLE_DOWNLOAD_KEY,false);
            return resultType;
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultType;
    }



}
