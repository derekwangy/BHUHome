package com.bh.uhome.lib.base.net.http.parse;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

/**
 * json解析
 * @author 凌霄
 * @date 2017/11/3.
 * @time 14:52.
 * @description Describe
 */
public class JsonHandler {

    /**
     * json转对象
     *
     * @param json json字符串
     * @param obj  obj对象
     * @return
     */
    public static <T> T jsonToObject(String json, Class<T> obj) {
        T resultObject = null;
        try{
            resultObject = new Gson().fromJson(json, obj);
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultObject;
    }

    /**
     * json转List
     *
     * @param json json字符串
     * @param type List的数据类型
     * @return cls List 元素对象类型
     */
    public static <T> List<T> jsonToList(String json, Type type) {
        List<T> list = null;
        try{
            list = new Gson().fromJson(json, type);
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
