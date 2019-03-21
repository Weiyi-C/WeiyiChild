/*
 * Copyright (c) 2019. zhengweiyi.cn all rights reserved
 * 郑维一版权所有，未经授权禁止使用，开源项目请遵守指定的开源协议
 */

package cn.zhengweiyi.weiyichild.greenDao;

import java.util.Date;
import java.util.List;

import cn.zhengweiyi.weiyichild.bean.Dietary;
import cn.zhengweiyi.weiyichild.custom.DateFormatUtil;
import cn.zhengweiyi.weiyichild.greenDao.db.DietaryDao;

/**
 * 每日菜谱数据库操作类
 */
public class DietaryLab {
    private DietaryDao dietaryDao;

    public DietaryLab(DietaryDao dietaryDao) {
        this.dietaryDao = dietaryDao;
    }

    /**
     * 获取全部食谱
     *
     * @return 返回全部食谱
     */
    public List<Dietary> getAllDietary() {
        return dietaryDao.loadAll();
    }

    /**
     * 根据日期获取食谱
     *
     * @param str 日期
     * @return 返回指定日期的食谱
     */
    public List<Dietary> getDietaryByDate(String str) {
        Date date = DateFormatUtil.StrToDate(str);
        return dietaryDao.queryBuilder().where(DietaryDao.Properties.Date.eq(date)).orderAsc().list();
    }

    public List<Dietary> getDietaryById(Long id) {
        return dietaryDao.queryBuilder().where(DietaryDao.Properties.Id.eq(id)).list();
    }

    public Object getDietaryObjectById(Long id) {
        return dietaryDao.queryBuilder().where(DietaryDao.Properties.Id.eq(id));
    }
}
