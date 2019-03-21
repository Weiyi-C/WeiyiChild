/*
 * Copyright (c) 2019. zhengweiyi.cn all rights reserved
 * 郑维一版权所有，未经授权禁止使用，开源项目请遵守指定的开源协议
 */

package cn.zhengweiyi.weiyichild.bean;

import android.util.Log;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Transient;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class PickupHistory {

    @Transient
    public static final String PICK_UP = "PICKUP";
    @Transient
    public static final String SEND = "SEND";

    @Id(autoincrement = true)
    private Long id;
    private Date date;
    private String type;
    private String parent;
    private String teacher;

    @Keep
    public PickupHistory(Date date, String type, String parent, String teacher) {
        switch (type) {
            case PICK_UP:
                this.date = date;
                this.type = type;
                this.parent = parent;
                this.teacher = teacher;
                break;
            case SEND:
                this.date = date;
                this.type = type;
                this.parent = parent;
                this.teacher = teacher;
                break;
            default:
                Log.e("Database", "安全接送数据类型定义错误，无法写入数据");
                break;
        }
    }

    @Keep
    public PickupHistory(String type, String parent, String teacher) {
        switch (type) {
            case PICK_UP:
                this.date = new Date();
                this.type = type;
                this.parent = parent;
                this.teacher = teacher;
                break;
            case SEND:
                this.date = new Date();
                this.type = type;
                this.parent = parent;
                this.teacher = teacher;
                break;
            default:
                Log.e("Database", "安全接送数据类型定义错误，无法写入数据");
                break;
        }
    }

    @Generated(hash = 185550921)
    public PickupHistory(Long id, Date date, String type, String parent,
            String teacher) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.parent = parent;
        this.teacher = teacher;
    }

    @Generated(hash = 1751476635)
    public PickupHistory() {
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
