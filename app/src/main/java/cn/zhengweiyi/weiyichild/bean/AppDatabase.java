/*
 * Copyright (c) 2019-2026. zhengweiyi.cn all rights reserved
 * 郑维一版权所有，未经授权禁止使用，开源项目请遵守指定的开源协议
 */

package cn.zhengweiyi.weiyichild.bean;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

/**
 * Room 数据库类
 * 管理数据库的创建和版本控制
 *
 * 数据库名称沿用原 GreenDAO 的 "sport-db"，保持数据兼容
 * 版本号为 2（原 GreenDAO schemaVersion 为 1，升级 Room 后递增）
 */
@Database(entities = {Dietary.class, PickupHistory.class}, version = 2, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    /** 获取每日食谱 DAO */
    public abstract DietaryDao dietaryDao();

    /** 获取安全接送记录 DAO */
    public abstract PickupHistoryDao pickupHistoryDao();

    /**
     * 获取数据库单例
     *
     * @param context 上下文
     * @return AppDatabase 实例
     */
    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "sport-db")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
