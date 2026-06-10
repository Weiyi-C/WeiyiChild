/*
 * Copyright (c) 2019-2026. zhengweiyi.cn all rights reserved
 * 郑维一版权所有，未经授权禁止使用，开源项目请遵守指定的开源协议
 */

package cn.zhengweiyi.weiyichild.bean;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.Date;
import java.util.List;

/**
 * 每日食谱数据访问对象（DAO）
 * 定义对 dietary_list 表的数据库操作
 */
@Dao
public interface DietaryDao {

    /**
     * 根据主键查询食谱
     *
     * @param id 主键
     * @return 对应的食谱记录，不存在则返回 null
     */
    @Query("SELECT * FROM dietary_list WHERE _id = :id")
    Dietary loadById(long id);

    /**
     * 根据日期查询食谱，按序号升序排列
     *
     * @param date 日期（时间戳）
     * @return 指定日期的食谱列表
     */
    @Query("SELECT * FROM dietary_list WHERE DATE = :date ORDER BY SEQUENCE ASC")
    List<Dietary> loadByDate(Date date);

    /**
     * 根据日期查询食谱（LiveData 版本，数据变化时自动通知）
     *
     * @param date 日期（时间戳）
     * @return 指定日期的食谱列表 LiveData
     */
    @Query("SELECT * FROM dietary_list WHERE DATE = :date ORDER BY SEQUENCE ASC")
    LiveData<List<Dietary>> loadByDateLive(Date date);

    /**
     * 查询全部食谱
     *
     * @return 所有食谱记录
     */
    @Query("SELECT * FROM dietary_list")
    List<Dietary> loadAll();

    /**
     * 批量插入食谱（冲突时替换）
     *
     * @param dietaryList 食谱列表
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertInTx(List<Dietary> dietaryList);
}
