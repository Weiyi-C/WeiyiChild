/*
 * Copyright (c) 2019. zhengweiyi.cn all rights reserved
 * 郑维一版权所有，未经授权禁止使用，开源项目请遵守指定的开源协议
 */

package cn.zhengweiyi.weiyichild.custom;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtil {

    /**
     * 日期转字符串
     *
     * @param date 传入 Date 日期
     * @return 返回 yyyy-MM-dd 格式日期字符串
     */
    public static String DateToStr(Date date) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * 日期时间转字符串
     *
     * @param date 传入 Date 日期
     * @return 返回 yyyy-MM-dd HH:mm:ss 格式日期字符串
     */
    public static String DatetimeToStr(Date date) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    /**
     * 时间转字符串（时分秒）
     *
     * @param date 传入 Date 日期
     * @return 返回 HH:mm:ss 格式日期字符串
     */
    public static String TimeFullToStr(Date date) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(date);
    }

    /**
     * 时间转字符串（时分）
     *
     * @param date 传入 Date 日期
     * @return 返回 HH:mm 格式日期字符串
     */
    public static String TimeToStr(Date date) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }

    /**
     * 字符串转日期
     *
     * @param string 传入 yyyy-MM-dd 格式日期字符串
     * @return 返回 Date 日期
     */
    public static Date StrToDate(String string) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 字符串转日期
     *
     * @param string 传入 yyyy-MM-dd HH:mm:ss 格式日期字符串
     * @return 返回 Date 日期
     */
    public static Date StrToDatetime(String string) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
