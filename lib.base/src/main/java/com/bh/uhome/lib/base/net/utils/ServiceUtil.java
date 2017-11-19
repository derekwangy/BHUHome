package com.bh.uhome.lib.base.net.utils;


import com.bh.uhome.lib.base.log.LogUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

/**
 * 解析工具类
 * @author 凌霄
 * @date 2017/11/3.
 * @time 15:13.
 * @description Describe
 */
public class ServiceUtil {
    private final static String TAG = "ServiceUtils";

    /**
     * 是否是json格式
     * @param jsonString
     * @return
     */
    public static boolean isJsonString(String jsonString) {
        //判断返回结果是否正确的json字符串格式
        boolean result = false;
        try {
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(jsonString);
            result = je.isJsonObject();
        }catch (JsonSyntaxException e) {
            e.printStackTrace();
            LogUtil.e(TAG, "parse Json Error");
        }
        return result;
    }


}
