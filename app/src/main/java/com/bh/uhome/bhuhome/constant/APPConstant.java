package com.bh.uhome.bhuhome.constant;

/**
 * @author 凌霄
 * @date 2017/8/24.
 * @time 17:27.
 * @description Describe
 */
public class APPConstant {
    //引导页标识
    public static boolean isFirstLaunch = false;

    //萤石云 key
    public static final String YS_APP_KEY = "0e4b3763549e44069c0b83fa27fdd681";
    public static final String YS_APP_SECRET = "6c7c9646a624c46dba0046597e1571e3";

    /**
     * 空字符
     */
    public static final String STRING_EMPTY = "";
    /**
     * 一个空白符号
     */
    public static final String STRING_ONE_BLANK = " ";
    /**
     * 手机号码长度
     */
    public static final int PHONE_NUMBER_LENGTH = 11;
    /**
     * 有效手机号码正则式
     */
    public static final String PHONE_REGX = "[1][34587]\\d{9}";
    /**
     * 最小密码长度
     */
    public static final int PASSWORD_MIN_LENGTH = 6;
    /**
     * 最大密码长度
     */
    public static final int PASSWORD_MAX_LENGTH = 16;
}
