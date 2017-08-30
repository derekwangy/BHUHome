package com.bh.uhome.bhuhome.util;

import android.app.Activity;
import android.text.TextUtils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by Administrator on 2015/11/17.
 */
public class MallUtils {

    //把获得的价格转换成对应的价格 (分->元)
    public static String getPrice(Object obj) {
        if (obj instanceof Integer) {
            int num = (int) obj;
            if(num == 0){
                return  "";
            }
            String zhengshu = num / 100 + "";
            String xiaoshu = num % 100 + "";
            if (num % 100 < 10) {
                xiaoshu = "0" + xiaoshu;
            }
            return zhengshu + "." + xiaoshu;
        } else if (obj instanceof Long) {

            long count = (long) obj;
            if(count == 0){
                return  "";
            }
            String zhengshu = count / 100 + "";
            String xiaoshu = count % 100 + "";
            if (count % 100 < 10) {
                xiaoshu = "0" + xiaoshu;
            }

            String str = zhengshu + "." + xiaoshu;
            return str;
        } else if (obj instanceof String) {
            String str = (String) obj;
            if(TextUtils.isEmpty(str) || "0".equals(str)){
                return "";
            }
            int length = str.length();
            if (length == 1) {
                return "0.0" + str ;
            } else if (length == 2) {
                return "0." + str;
            }
            return str.substring(0, length - 2) + "." + str.substring(length - 2);
        }

        return "";
    }


}
