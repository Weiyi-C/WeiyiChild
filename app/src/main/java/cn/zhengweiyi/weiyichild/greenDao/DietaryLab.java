/*
 * Copyright (c) 2019-2026. zhengweiyi.cn all rights reserved
 * 郑维一版权所有，未经授权禁止使用，开源项目请遵守指定的开源协议
 */

package cn.zhengweiyi.weiyichild.greenDao;

import java.util.List;

import cn.zhengweiyi.weiyichild.bean.Dietary;
import cn.zhengweiyi.weiyichild.bean.DietaryDao;
import cn.zhengweiyi.weiyichild.custom.DateFormatUtil;

/**
 * 每日菜谱数据库操作类
 * 封装 DietaryDao 的常用查询方法
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
     * @param str 日期字符串（yyyy-MM-dd 格式）
     * @return 返回指定日期的食谱
     */
    public List<Dietary> getDietaryByDate(String str) {
        return dietaryDao.loadByDate(DateFormatUtil.StrToDate(str));
    }

    /**
     * 根据 ID 获取食谱列表
     *
     * @param id 食谱主键
     * @return 包含该食谱的列表（不存在则为空列表）
     */
    public List<Dietary> getDietaryById(Long id) {
        List<Dietary> list = new java.util.ArrayList<>();
        Dietary dietary = dietaryDao.loadById(id);
        if (dietary != null) {
            list.add(dietary);
        }
        return list;
    }

    /**
     * 判断指定 ID 的食谱是否存在
     *
     * @param id 食谱主键
     * @return 存在返回 true，否则返回 false
     */
    public boolean existsById(Long id) {
        return dietaryDao.loadById(id) != null;
    }
}
