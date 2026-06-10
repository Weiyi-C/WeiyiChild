/*
 * Copyright (c) 2019-2026. zhengweiyi.cn all rights reserved
 * 郑维一版权所有，未经授权禁止使用，开源项目请遵守指定的开源协议
 */

package cn.zhengweiyi.weiyichild.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * 每日食谱实体类
 * 对应数据库表 dietary_list
 */
@Entity(tableName = "dietary_list")
public class Dietary {

    /** 主键，自增 */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;

    /** 食谱对应日期（存储为时间戳） */
    @ColumnInfo(name = "DATE")
    private Date date;

    /** 序号 */
    @ColumnInfo(name = "SEQUENCE")
    private int sequence;

    /** 餐名，如"早餐" */
    @ColumnInfo(name = "NAME")
    private String name;

    /** 具体食物名称 */
    @ColumnInfo(name = "FOODS")
    private String foods;

    public Dietary(Date date, int sequence, String name, String foods) {
        this.date = date;
        this.sequence = sequence;
        this.name = name;
        this.foods = foods;
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

    public int getSequence() {
        return this.sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFoods() {
        return this.foods;
    }

    public void setFoods(String foods) {
        this.foods = foods;
    }
}
