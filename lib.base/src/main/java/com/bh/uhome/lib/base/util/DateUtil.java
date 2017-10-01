/**
 * Copyright (c) 2015 Choice, Inc.
 * All Rights Reserved.
 * Choice Proprietary and Confidential.
 */
package com.bh.uhome.lib.base.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 时间日期转换工具类
 *
 * @author yanshaokun@myweimai.com
 * @date 2016/1/12
 * @time 20:05
 * @description Describe the place where the class needs to pay attention.
 */
public class DateUtil {
    /**
     * 1s==1000ms
     */
    private final static int TIME_MILLISECONDS = 1000;
    /**
     * 时间中的分、秒最大值均为60
     */
    private final static int TIME_NUMBERS = 60;
    /**
     * 时间中的小时最大值
     */
    private final static int TIME_HOURSES = 24;
    /**
     * 格式化日期的标准字符串
     * <p/>
     * 例如 2016-01-01 00:00:00
     */
    public final static String FORMAT_MODE_SIMPLE_DATE = "yyyy-MM-dd HH:mm:ss";
    /**
     * 简单时间格式模板.
     * <p/>
     * 时间格式化模式.
     * 例如 2016:01:01 00:00:00
     */
    public static String FORMAT_MODE_SIMPLE_TIME = "yyyy:MM:dd HH:mm:ss";
    /**
     * Http 1.1 (GMT) 标准时间格式模板.
     * 时间格式化模式
     */
    public static String FORMAT_MODE_HTTP_GMT = "EEE, dd MMM yyyy HH:mm:ss 'GMT'";
    /**
     *
     */
    public static String FORMAT_MODE_HTTP_GMT2 = "yyyy-MM-DD'T'HH:mm:ss Z";
    /**
     * 获取时区信息
     */
    public static TimeZone getTimeZone() {
        return TimeZone.getDefault();
    }
    /**
     * 将日期字符串转换为Date对象
     *
     * @param date 日期字符串，必须为"yyyy-MM-dd HH:mm:ss"
     * @return 日期字符串的Date对象表达形式
     */
    public static Date parseDate(String date) {
        return parseDate(date, FORMAT_MODE_SIMPLE_DATE);
    }
    /**
     * 将日期字符串转换为Date对象
     *
     * @param date   日期字符串，必须为"yyyy-MM-dd HH:mm:ss"
     * @param format 格式化字符串
     * @return 日期字符串的Date对象表达形式
     */
    public static Date parseDate(String date, String format) {
        Date dt = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
        try {
            dt = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dt;
    }
    /**
     * 将Date对象转换为指定格式的字符串
     *
     * @param date Date对象
     * @return Date对象的字符串表达形式"yyyy-MM-dd HH:mm:ss"
     */
    public static String formatDate(Date date) {
        return formatDate(date, FORMAT_MODE_SIMPLE_DATE);
    }
    /**
     * 将Date对象转换为指定格式的字符串
     *
     * @param date   Date对象
     * @param format 格式化字符串
     * @return Date对象的字符串表达形式
     */
    public static String formatDate(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        try {
            return URLEncoder.encode(dateFormat.format(date), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 格式化日期
     *
     * @param unixTime unix时间戳
     * @return 日期字符串"yyyy-MM-dd HH:mm:ss"
     */
    public static String formatUnixTime(long unixTime) {
        return formatUnixTime(unixTime, FORMAT_MODE_SIMPLE_DATE);
    }
    /**
     * 格式化日期
     *
     * @param unixTime unix时间戳
     * @param format   格式化字符串
     * @return 日期字符串
     */
    public static String formatUnixTime(long unixTime, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
        return dateFormat.format(unixTime);
    }
    /**
     * 将GMT日期格式化为系统默认时区的日期字符串表达形式
     *
     * @param gmtUnixTime GTM时间戳
     * @return 日期字符串"yyyy-MM-dd HH:mm:ss"
     */
    public static String formatGMTUnixTime(long gmtUnixTime) {
        return formatGMTUnixTime(gmtUnixTime, FORMAT_MODE_SIMPLE_DATE);
    }
    /**
     * 将GMT日期格式化为系统默认时区的日期字符串表达形式
     *
     * @param gmtUnixTime GTM时间戳
     * @param format      格式化字符串
     * @return 日期字符串"yyyy-MM-dd HH:mm:ss"
     */
    public static String formatGMTUnixTime(long gmtUnixTime, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return dateFormat.format(gmtUnixTime + TimeZone.getDefault().getRawOffset());
    }
    /**
     * 获取时间戳的Date表示形式
     *
     * @param unixTime unix时间戳
     * @return Date对象
     */
    public static Date getDate(long unixTime) {
        return new Date(unixTime);
    }
    /**
     * 获取GMT时间戳的Date表示形式（转换为Date表示形式后，为系统默认时区下的时间）
     *
     * @param gmtUnixTime GMT Unix时间戳
     * @return Date对象
     */
    public static Date getGMTDate(long gmtUnixTime) {
        return new Date(gmtUnixTime + TimeZone.getDefault().getRawOffset());
    }
    /**
     * 将系统默认时区的Unix时间戳转换为GMT Unix时间戳
     *
     * @param unixTime unix时间戳
     * @return GMT Unix时间戳
     */
    public static long getGMTUnixTime(long unixTime) {
        return unixTime - TimeZone.getDefault().getRawOffset();
    }
    /**
     * 将GMT Unix时间戳转换为系统默认时区的Unix时间戳
     *
     * @param gmtUnixTime GMT Unix时间戳
     * @return 系统默认时区的Unix时间戳
     */
    public static long getCurrentTimeZoneUnixTime(long gmtUnixTime) {
        return gmtUnixTime + TimeZone.getDefault().getRawOffset();
    }
    /**
     * 获取当前时间的GMT Unix时间戳
     *
     * @return 当前的GMT Unix时间戳
     */
    public static long getGMTUnixTimeByCalendar() {
        Calendar calendar = Calendar.getInstance();
        // 获取当前时区下日期时间对应的时间戳
        long unixTime = calendar.getTimeInMillis();
        // 获取标准格林尼治时间下日期时间对应的时间戳
        long unixTimeGMT = unixTime - TimeZone.getDefault().getRawOffset();
        return unixTimeGMT;
    }
    /**
     * 获取当前时间的Unix时间戳
     *
     * @return 当前的Unix时间戳
     */
    public static long getUnixTimeByCalendar() {
        Calendar calendar = Calendar.getInstance();
        // 获取当前时区下日期时间对应的时间戳
        long unixTime = calendar.getTimeInMillis();
        return unixTime;
    }
    /**
     * 获取更改时区后的时间
     *
     * @param date    时间
     * @param oldZone 旧时区
     * @param newZone 新时区
     * @return 时间
     */
    public static Date changeTimeZone(Date date, TimeZone oldZone, TimeZone newZone) {
        Date dateTmp = null;
        if (date != null) {
            int timeOffset = oldZone.getRawOffset() - newZone.getRawOffset();
            dateTmp = new Date(date.getTime() - timeOffset);
        }
        return dateTmp;
    }
    /**
     * 将总秒数转换为时分秒表达形式
     *
     * @param seconds 任意秒数
     * @return %s小时%s分%s秒
     */
    public static String formatTime(long seconds) {
        long hh = seconds / TIME_NUMBERS / TIME_NUMBERS;
        long mm = (seconds - hh * TIME_NUMBERS * TIME_NUMBERS) > 0 ? (seconds - hh * TIME_NUMBERS * TIME_NUMBERS) / TIME_NUMBERS : 0;
        long ss = seconds < TIME_NUMBERS ? seconds : seconds % TIME_NUMBERS;
        return (hh == 0 ? "" : (hh < 10 ? "0" + hh : hh) + "小时")
                + (mm == 0 ? "" : (mm < 10 ? "0" + mm : mm) + "分")
                + (ss == 0 ? "" : (ss < 10 ? "0" + ss : ss) + "秒");
    }

    /**
     * 是否为同一周.
     * (同一周存在跨年,跨月的情况.)
     *
     * @return
     */
    @SuppressWarnings("WrongConstant")
    public static boolean isSameWeek(Date date1, Date date2) {
        // 同一周不同于同年或者同月的计算.
        // 因为同一周存在跨月,跨年
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
        if (subYear == 0) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        } else if (subYear == 1 && cal2.get(Calendar.MONTH) == 11) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        } else if (subYear == -1 && cal1.get(Calendar.MONTH) == 11) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        }
        return false;
    }
    /**
     * 给定的两个日期是否为同年同月.
     *
     * @return 给定的两个日期是否为同年同月.
     */
    public static boolean isSameMonth(Date date1, Date date2) {
        if (isSameYear(date1, date2)) {
            Calendar calendar1 = Calendar.getInstance();
            Calendar calendar2 = Calendar.getInstance();
            calendar1.setTime(date1);
            calendar2.setTime(date2);
            return calendar1.get(Calendar.MONTH) - calendar2.get(Calendar.MONTH) == 0;
        } else {
            return false;
        }
    }
    /**
     * 给定的两个日期是否为同一年.
     *
     * @return 给定的两个日期是否为同一年.
     */
    public static boolean isSameYear(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(date1);
        calendar2.setTime(date2);
        return calendar1.get(Calendar.YEAR) - calendar2.get(Calendar.YEAR) == 0;
    }
    /**
     * 获取第几周.
     * 根据指定日期计算指定日期为指定日期当月的第几周.
     *
     * @param date 指定日期
     * @return 指定日期为指定日期当月的第几周.
     */
    public static int getWeekNumberOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_MONTH);
    }
    /**
     * 获取第几天.
     * 根据指定日期计算指定日期为指定日期当周的第几天.
     *
     * @param date 指定日期.
     * @return 指定日期为指定日期当周的第几天.
     */
    public static int getDayNumberOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }
    /**
     * 获取星期几.
     * 根据指定日期计算指定日期为星期几.
     *
     * @param date 指定日期.
     * @return 指定日期为星期几.
     */
    public static String getWeekDayString(Date date) {
        final String dayNames[] = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return dayNames[calendar.get(Calendar.DAY_OF_WEEK) - 1];
    }
    /**
     * 获取微信的时差描述.
     *
     * @param date 计算日期.
     * @return 时差的大致表示形式.
     */
    public static String getWeChatDiffTimeDescriptions(@NonNull Date date, @Nullable Locale locale) {
        // 默认中国
        if (locale == null) {
            locale = Locale.CHINA;
        }
        // 计划进行计算的日期
        Calendar computingCalendar = Calendar.getInstance(locale);
        computingCalendar.setTime(date);
        // 今天
        Calendar today = Calendar.getInstance(locale);
        today.set(Calendar.YEAR, today.get(Calendar.YEAR));
        today.set(Calendar.MONTH, today.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, today.get(Calendar.DAY_OF_MONTH));
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        // 昨天
        Calendar yesterday = Calendar.getInstance(locale);
        yesterday.set(Calendar.YEAR, yesterday.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, yesterday.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH, yesterday.get(Calendar.DAY_OF_MONTH) - 1);
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);
        // 7天前
        Calendar sevenDaysAgo = Calendar.getInstance(locale);
        sevenDaysAgo.set(Calendar.YEAR, sevenDaysAgo.get(Calendar.YEAR));
        sevenDaysAgo.set(Calendar.MONTH, sevenDaysAgo.get(Calendar.MONTH));
        sevenDaysAgo.set(Calendar.DAY_OF_MONTH, sevenDaysAgo.get(Calendar.DAY_OF_MONTH) - 7);
        sevenDaysAgo.set(Calendar.HOUR_OF_DAY, 0);
        sevenDaysAgo.set(Calendar.MINUTE, 0);
        sevenDaysAgo.set(Calendar.SECOND, 0);
        // 今天
        // 判断条件 : 今天之后
        if (computingCalendar.after(today)) {
            return new SimpleDateFormat("HH:mm", Locale.CHINA).format(date);
        }
        // 昨天
        // 判断条件 : 今天之前,昨天以后.
        else if (computingCalendar.before(today) && computingCalendar.after(yesterday)) {
            return "昨天";
        }
        // 一周内
        // 昨天以前 && 7天前以后.
        else if (computingCalendar.before(yesterday) && computingCalendar.after(sevenDaysAgo)) {
            return getWeekDayString(date);
        } else {
            // return new SimpleDateFormat("YY/MM/dd", Locale.CHINA).format(date);
            // Modify By Ronny 2016-04-15 YY 为 Java 7 时启用的,在 Java 6 中,不兼容,会报错.
            // 这里修改为小写 yy 对 JAVA 7 以下做兼容.
            return new SimpleDateFormat("yy/MM/dd", Locale.CHINA).format(date);
        }
    }
    /**
     * 获取当前时间距离指定日期时差的大致表达形式
     *
     * @param date 日期
     * @return 时差的大致表达形式
     */
    public static String getDiffTime(long date) {
        String strTime = "很久很久以前";
        long time = Math.abs(new Date().getTime() - date);
        // 一分钟以内
        if (time < TIME_NUMBERS * TIME_MILLISECONDS) {
            strTime = "刚刚";
        } else {
            int min = (int) (time / TIME_MILLISECONDS / TIME_NUMBERS);
            if (min < TIME_NUMBERS) {
                if (min < 15) {
                    strTime = "一刻钟前";
                } else if (min < 30) {
                    strTime = "半小时前";
                } else {
                    strTime = "1小时前";
                }
            } else {
                int hh = min / TIME_NUMBERS;
                if (hh < TIME_HOURSES) {
                    strTime = hh + "小时前";
                } else {
                    int days = hh / TIME_HOURSES;
                    if (days <= 6) {
                        strTime = days + "天前";
                    } else {
                        int weeks = days / 7;
                        if (weeks < 3) {
                            strTime = weeks + "周前";
                        }
                    }
                }
            }
        }
        return strTime;
    }

}
