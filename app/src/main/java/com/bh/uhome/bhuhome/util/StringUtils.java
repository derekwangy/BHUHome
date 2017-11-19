package com.bh.uhome.bhuhome.util;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作工具类.
 *
 * @author maka on 17-7-26.
 */
public class StringUtils {

    /**
     * 替换不需要的字符
     *
     * @param checkString   源字符
     * @param reg           匹配正则
     * @param replaceString 替换字符
     * @return str
     */
    public static String replaceChar(@NonNull String checkString, @NonNull String reg, @NonNull String replaceString) {
        String resultString = "";
        if (!TextUtils.isEmpty(checkString)) {
            Pattern p = Pattern.compile(reg);
            Matcher m = p.matcher(checkString);
            resultString = m.replaceAll(replaceString);
        }
        return resultString;
    }

    /**
     * 将空格转换成 "".
     *
     * @param checkString checkString
     * @return 转换后字符串.
     */
    public static String replaceBlank(String checkString) {
        return replaceChar(checkString, "\\s*|\t|\r|\n", "");
    }

    /**
     * 是否满足正则(全部匹配).
     *
     * @param checkString 需要校验的字符串.
     * @param regular     需要满足的正则.
     * @return true / false.
     */
    public static boolean isMatcherRegular(String checkString, String regular) {
        if (isNull(regular)) {
            return false;
        }
        Pattern pattern = Pattern.compile(regular);
        Matcher isNum = pattern.matcher(checkString);
        return isNum.matches();
    }

    /**
     * 是否满足正则（部分匹配）.
     *
     * @param checkString 需要校验的字符串.
     * @param regular     需要满足的正则.
     * @return true / false.
     */
    public static boolean isFindRegular(String checkString, String regular) {
        if (isNull(regular)) {
            return false;
        }
        Pattern pattern = Pattern.compile(regular);
        Matcher isNum = pattern.matcher(checkString);
        return isNum.find();
    }

    /**
     * 是否全部是数字.
     * reg --> [0-9]*
     *
     * @param checkString 需要验证的字符串.
     * @return 结果.
     */
    public static boolean isNumber(String checkString) {
        return isMatcherRegular(checkString, "[0-9]*");
    }

    /**
     * 是否是全部是字符.
     * reg --> [0-9]*
     *
     * @param checkString 需要验证的字符串.
     * @return 结果.
     */
    public static boolean isChar(String checkString) {
        return isMatcherRegular(checkString, "[a-zA-Z#]*");
    }

    /**
     * 对数据进行非空校验并转换.
     *
     * @param needCheckedString 需要校验的字符串数据.
     * @return 返回.
     */
    public static String nullStringFormat(String needCheckedString) {
        return nullStringFormat(needCheckedString, "");
    }

    /**
     * 对数据进行非空校验并转换.
     *
     * @param needCheckedString 需要校验的字符串.
     * @param defaultString     为空情况 下的默认值.
     * @return 转换后数据.
     */
    public static String nullStringFormat(String needCheckedString, String defaultString) {
        if (!TextUtils.isEmpty(needCheckedString)) {
            return needCheckedString;
        } else {
            if (!TextUtils.isEmpty(defaultString)) {
                return defaultString;
            } else {
                return "";
            }
        }
    }

    /**
     * 对数据的校验.
     *
     * @return true if str is null or zero length
     */
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

    /**
     * 对数据的校验.
     *
     * @return true if str is null or zero length
     */
    public static boolean isNull(Object... objects) {
        if (null == objects){
            return true;
        }
        for (Object object : objects) {
            if (object instanceof String) {
                if (TextUtils.isEmpty(((String) object))) {
                    return true;
                }
            } else {
                if (null == object) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 对包含有Unicode的字符串进行转义.
     *
     * @param string 需要校验的字符串.
     * @return 返回结果.
     */
    public static String unicode2CN(String string) {
        // 数据校验.
        if (isNull(string)) {
            return "";
        }
        try {
            StringBuilder sb = new StringBuilder();
            while (string.length() > 0) {
                // 规则：以 “u” 开头，且之后的5个字符满足汉字的正则表达式，才是 Unicode，需要转码.
                boolean isUnicode = string.startsWith("u") && string.length() >= 5
                        && isMatcherRegular(string.substring(1, 5), "[\\\\u4E00-\\\\u9FBF]+");
                if (isUnicode) { // 从当前位置往后5个字符是 unicode .
                    int x = Integer.parseInt(string.substring(1, 5), 16);
                    sb.append((char) x);
                    string = string.substring(5);
                } else { // 不是.
                    sb.append(string.charAt(0));
                    string = string.substring(1);
                }
            }
            return sb.toString();
        } catch (Exception ignored) {
        }
        return string;
    }

}
