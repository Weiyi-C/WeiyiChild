/*
 * Copyright (c) 2019-2026. zhengweiyi.cn all rights reserved
 * 郑维一版权所有，未经授权禁止使用，开源项目请遵守指定的开源协议
 */

package cn.zhengweiyi.weiyichild.bean;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * 安全接送记录实体类
 * 对应数据库表 PICKUP_HISTORY
 */
@Entity(tableName = "PICKUP_HISTORY")
public class PickupHistory {

    /** 接孩子 */
    @Ignore
    public static final String PICK_UP = "PICKUP";

    /** 送孩子 */
    @Ignore
    public static final String SEND = "SEND";

    /** 主键，自增 */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;

    /** 日期（存储为时间戳） */
    @ColumnInfo(name = "DATE")
    private Date date;

    /** 类型：PICKUP（接）或 SEND（送） */
    @ColumnInfo(name = "TYPE")
    private String type;

    /** 家长姓名 */
    @ColumnInfo(name = "PARENT")
    private String parent;

    /** 老师姓名 */
    @ColumnInfo(name = "TEACHER")
    private String teacher;

    @Ignore
    public PickupHistory(Date date, String type, String parent, String teacher) {
        if (PICK_UP.equals(type) || SEND.equals(type)) {
            this.date = date;
            this.type = type;
            this.parent = parent;
            this.teacher = teacher;
        } else {
            Log.e("Database", "安全接送数据类型定义错误，无法写入数据");
        }
    }

    @Ignore
    public PickupHistory(String type, String parent, String teacher) {
        if (PICK_UP.equals(type) || SEND.equals(type)) {
            this.date = new Date();
            this.type = type;
            this.parent = parent;
            this.teacher = teacher;
        } else {
            Log.e("Database", "安全接送数据类型定义错误，无法写入数据");
        }
    }

    /** Room 使用的全参构造函数 */
    public PickupHistory(Long id, Date date, String type, String parent, String teacher) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.parent = parent;
        this.teacher = teacher;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParent() {
        return this.parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getTeacher() {
        return this.teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
