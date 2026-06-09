/*
 * Copyright (c) 2019-2026. zhengweiyi.cn all rights reserved
 * 郑维一版权所有，未经授权禁止使用，开源项目请遵守指定的开源协议
 */

package cn.zhengweiyi.weiyichild.bean;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * 安全接送记录数据访问对象（DAO）
 * 定义对 PICKUP_HISTORY 表的数据库操作
 */
@Dao
public interface PickupHistoryDao {

    /**
     * 查询全部接送记录
     *
     * @return 所有接送记录
     */
    @Query("SELECT * FROM PICKUP_HISTORY")
    List<PickupHistory> loadAll();

    /**
     * 批量插入接送记录（冲突时替换）
     *
     * @param pickupHistoryList 接送记录列表
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertInTx(List<PickupHistory> pickupHistoryList);
}
