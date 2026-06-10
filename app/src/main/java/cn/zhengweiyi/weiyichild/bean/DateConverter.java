/*
 * Copyright (c) 2019-2026. zhengweiyi.cn all rights reserved
 * 郑维一版权所有，未经授权禁止使用，开源项目请遵守指定的开源协议
 */

package cn.zhengweiyi.weiyichild.bean;

import androidx.room.TypeConverter;

import java.util.Date;

/**
 * Room 类型转换器
 * 将 java.util.Date 与数据库中的 Long（时间戳）互相转换
 */
public class DateConverter {

    /** Date → Long（写入数据库时调用） */
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    /** Long → Date（从数据库读取时调用） */
    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
