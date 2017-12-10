package com.bh.uhome.bhuhome.db.sharedprefer;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * sharepreference 存储
 * Created by 凌霄 on 17/6/27.
 */
public class ShareCache {
    private static ShareCache instance = null;
    //微脉数据缓存
    private static SharedPreferences sharedPreferences = null;
    public final static String SAVE_WEIMAI_DB = "weimai_db";     //保存数据—db
    public final static String ENABLE_DOWNLOAD_KEY = "enable_download_key";     //保存是否下载 key

    //引导页
    public final static String IS_LAUNCH = "isLaunch";

    //shoujihao
    public final static String USER_NAME = "userName";

    private ShareCache() {
    };

    public synchronized static ShareCache getInstance(Context mActivity) {
        if (instance == null) {
            synchronized (ShareCache.class){
                if (instance == null) {
                    instance = new ShareCache();
                }
            }
        }
        if (sharedPreferences == null){
            sharedPreferences = mActivity.getSharedPreferences(SAVE_WEIMAI_DB, Context.MODE_PRIVATE);
        }
        return instance;
    }




    /**
     * 保存登录类型
     * @param type
     */
    public void saveSharefEnableDownload(boolean type) {

        // 对数据进行编辑,返回的是一个Editor对象
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(ENABLE_DOWNLOAD_KEY,type);
        editor.commit();
    }


    /**
     * 获取登录类型
     * @return
     */
    public boolean getSharefEnableDownload() {
        boolean  resultType = false;
        try{
            // 对数据进行编辑,返回的是一个Editor对象
            resultType = sharedPreferences.getBoolean(ENABLE_DOWNLOAD_KEY,false);
            return resultType;
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultType;
    }


    /**
     * 保存launch
     * @param isLaunch
     */
    public void saveIsLaunch(boolean isLaunch) {
        // 对数据进行编辑,返回的是一个Editor对象
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_LAUNCH,isLaunch);
        editor.commit();
    }

    /**
     * 获取launch
     * @return
     */
    public boolean getIsLaunch() {
        boolean  result = false;
        try{
            // 对数据进行编辑,返回的是一个Editor对象
            result = sharedPreferences.getBoolean(IS_LAUNCH,false);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * yonghuming
     * @param phone
     */
    public void saveUserName(String phone) {
        // 对数据进行编辑,返回的是一个Editor对象
        sharedPreferences.edit().putString(USER_NAME,phone).commit();
    }

    public String getUserName() {

        return sharedPreferences.getString(USER_NAME,"");
    }


}
