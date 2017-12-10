package com.bh.uhome.lib.base.net.db;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * @author 凌霄
 * @date 2017/11/1.
 * @time 15:35.
 * @description Describe
 */
public class ShareToken {
    private static ShareToken instance = null;
    private static SharedPreferences sharedPreferences = null;
    private static Context mContext = null;
    private static final String DB_TOKEN = "db_token";
    private static final String TOKEN = "token";

    private ShareToken(){}

    public static ShareToken getInstance(Context context){
        mContext = context;
        if (instance == null){
            synchronized (ShareToken.class){
                if (instance == null){
                    instance = new ShareToken();
                }
            }
        }
        if (sharedPreferences == null){
            sharedPreferences = mContext.getSharedPreferences(DB_TOKEN, MODE_PRIVATE);
        }
        return instance;
    }

    /**
     * 存储token
     * @param data
     */
    public  void saveToken(String data){

        sharedPreferences.edit().putString(TOKEN, data).commit();
    }

    /**
     * 获取token
     * @return
     */
    public String getToken(){

        return sharedPreferences.getString(TOKEN, "");
    }
}
