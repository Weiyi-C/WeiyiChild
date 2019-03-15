/*
 * Copyright (c) 2019. zhengweiyi.cn all rights reserved
 * 郑维一版权所有，未经授权禁止使用，开源项目请遵守指定的开源协议
 */

package cn.zhengweiyi.weiyichild.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;

import java.util.Date;

@Entity (nameInDb = "dietary_list")
public class Dietary {
    @Id(autoincrement = true)
    private Long id;        // id
    private Date date;      // 食谱对应日期
    private int sequence;   // 序号
    private String name;    // 餐名，如“早餐”
    private String foods;   // 具体食物名称

    @Keep
    public Dietary(Date date, int sequence, String name,
                   String foods) {
        this.date = date;
        this.sequence = sequence;
        this.name = name;
        this.foods = foods;
    }

    @Generated(hash = 1630206678)
    public Dietary(Long id, Date date, int sequence, String name, String foods) {
        this.id = id;
        this.date = date;
        this.sequence = sequence;
        this.name = name;
        this.foods = foods;
    }

    @Generated(hash = 2076241465)
    public Dietary() {
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
