/*
 * Copyright (c) 2019. zhengweiyi.cn all rights reserved
 * 郑维一版权所有，未经授权禁止使用，开源项目请遵守指定的开源协议
 */

package cn.zhengweiyi.weiyichild.greenDao;

import java.util.List;

import cn.zhengweiyi.weiyichild.bean.PickupHistory;
import cn.zhengweiyi.weiyichild.greenDao.db.PickupHistoryDao;

/**
 * 安全接送历史记录数据库操作类
 */
public class PickupHistoryLab {

    private PickupHistoryDao pickupHistoryDao;

    public PickupHistoryLab(PickupHistoryDao pickupHistoryDao) {
        this.pickupHistoryDao = pickupHistoryDao;
    }

    /**
     * 读取全部历史记录
     *
     * @return 返回全部数据
     */
    public List<PickupHistory> getAllPickupHistory() {
        return pickupHistoryDao.loadAll();
    }
}
