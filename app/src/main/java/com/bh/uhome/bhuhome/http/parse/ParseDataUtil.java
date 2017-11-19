package com.bh.uhome.bhuhome.http.parse;

import android.app.Activity;
import android.text.TextUtils;


import com.bh.uhome.bhuhome.util.ToastUtil;
import com.bh.uhome.lib.base.app.RxRetrofitApp;
import com.bh.uhome.lib.base.net.http.parse.JsonHandler;
import com.bh.uhome.lib.base.net.utils.ServiceUtil;

import org.json.JSONException;
import org.json.JSONObject;


/**
 *  数据解析工具
 * @author 凌霄
 * @date 2017/11/3.
 * @time 14:25.
 * @description Describe
 */
public class ParseDataUtil {
    private static final String TAG = "ParseDataUtil";

    /**
     * 新网络库统一解析数据入口，兼容新老数据模式
     * @param json
     * @param cls
     * @param activity
     * @param <T>
     * @return
     */
    public static <T> T paseJsonData(String json, Class<T> cls, Activity activity){
        T resultObject = null;
        if (TextUtils.isEmpty(json)){
            return null;
        }
        if (!ServiceUtil.isJsonString(json)) {
            //数据格式错误
            ToastUtil.showLong(activity,"非json数据！");
        }else {
            try {
                JSONObject jsonObj = new JSONObject(json);
                if (jsonObj.has("CodeID")){ //老格式
                    resultObject = parseOldData(jsonObj,json,cls,activity);
                }else { //新格式
                    resultObject = parseNewData(jsonObj,json,cls,activity);
                }
            } catch (JSONException e) {
                ToastUtil.showLong(activity,"数据解析失败！");
                e.printStackTrace();
            }

        }
        return resultObject;
    }

    /**
     * 新数据格式
     *{
     "code": 0,
     "data": {},
     "message": "成功"
     }
     * @param jsonObj
     * @return
     */
    private static <T> T parseNewData(JSONObject jsonObj, String json, Class<T> cls, Activity activity) {
        T resultObject = null;
        int code = jsonObj.optInt("code");
        String message ;
        if(1 == code){ //此处约定是1为成功
            resultObject = JsonHandler.jsonToObject(json, cls);
        }else {
            message = jsonObj.optString("message");
            disparchErrorCode(code,message,activity);
        }
        return resultObject;
    }

    /**
     * 老数据格式
     * {
     "Code":"UNKNOWN",
     "CodeID":90000,
     "Content":"未知错误",
     "Message":"小脉开小差去了，请您稍后重试"
     }
     * @param jsonObj
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    private static <T> T parseOldData(JSONObject jsonObj, String json, Class<T> cls, Activity activity) {
        T resultObject = null;
        int code = jsonObj.optInt("CodeID");
        String message ;
        if(1 == code){
            resultObject = JsonHandler.jsonToObject(json, cls);
        }else {
            message = jsonObj.optString("Message");
            disparchErrorCode(code,message,activity);
        }
        return resultObject;
    }

    /**
     * 公共错误处理
     * @param code
     * @param message
     */
    private static void disparchErrorCode(int code, String message, Activity activity) {
        switch (code){
            case 10500://账号或密码错误
                ToastUtil.showLong(activity,RxRetrofitApp.isDebug()?code+":"+message:message);
                break;
            case 10501://账号或密码错误
                ToastUtil.showLong(activity,RxRetrofitApp.isDebug()?code+":"+message:message);
                break;
            case -1: //所有异常处理
                ToastUtil.showLong(activity, RxRetrofitApp.isDebug()?code+":"+message:message);
                break;
            case 0: //所有异常处理
                ToastUtil.showLong(activity, RxRetrofitApp.isDebug()?code+":"+message:message);
                break;
            case 90000://未知错误
                ToastUtil.showLong(activity,RxRetrofitApp.isDebug()?code+":"+message:message);
                break;
            case 40200://账号未注册
                ToastUtil.showLong(activity,RxRetrofitApp.isDebug()?code+":"+message:message);
                break;
            case 40400: //Token失效 强制退出登录
//                loginoutData(activity);
                break;


            default:
                ToastUtil.showLong(activity, RxRetrofitApp.isDebug()?code+":"+message:message);
        }
    }



}
