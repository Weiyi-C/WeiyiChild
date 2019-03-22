/*
 * Copyright (c) 2019. zhengweiyi.cn all rights reserved
 * 郑维一版权所有，未经授权禁止使用，开源项目请遵守指定的开源协议
 */

package cn.zhengweiyi.weiyichild;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.net.URI;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.zhengweiyi.weiyichild.bean.Dietary;
import cn.zhengweiyi.weiyichild.bean.PickupHistory;
import cn.zhengweiyi.weiyichild.custom.DateFormatUtil;
import cn.zhengweiyi.weiyichild.greenDao.db.DaoMaster;
import cn.zhengweiyi.weiyichild.greenDao.db.DaoSession;
import cn.zhengweiyi.weiyichild.greenDao.db.DietaryDao;
import cn.zhengweiyi.weiyichild.greenDao.db.PickupHistoryDao;

public class MyApplication extends Application {

    public final String ROOT_HOST = "weiyihost";
    public final String API_SEND_CHILD = ":8080/sendChild?";

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private List<Dietary> dietaryList = new ArrayList<>();
    private List<PickupHistory> pickupHistoryList = new ArrayList<>();

    // 静态单例
    public static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        setDatabase();
    }

    public static MyApplication getInstance() {
        return instance;
    }

    /**
     * 设置 GreenDao
     */
    private void setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，应该 TODO 做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(this, "sport-db", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    /**
     * 初始化数据
     * TODO 实现服务器读取后修改该方法
     */
    public void initData() throws ParseException {
        DietaryDao dietaryDao = getDaoSession().getDietaryDao();
        if (dietaryDao.load(1L) == null) {  //判断数据库是否有数据
            // 初始化测试数据
            String stringDate = DateFormatUtil.DateToStr(new Date());
            Date dateNow = DateFormatUtil.StrToDate(stringDate);
            Dietary dietary0 = new Dietary(dateNow, 0, "早餐", "奶黄包、豆浆");
            Dietary dietary1 = new Dietary(dateNow, 1, "上午点心", "香蕉、哈密瓜");
            Dietary dietary2 = new Dietary(dateNow, 2, "午餐", "土豆鸡块、番茄炒蛋、清炒包菜、冬瓜虾皮汤");
            Dietary dietary3 = new Dietary(dateNow, 3, "下午点心", "蒸南瓜、红豆汤");
            Dietary dietary4 = new Dietary(dateNow, 4, "过敏儿童", "白菜粉丝汤");
            Dietary dietary5 = new Dietary(dateNow, 5, "病号餐", "青菜肉丝面");
            dietaryList.add(dietary0);
            dietaryList.add(dietary1);
            dietaryList.add(dietary2);
            dietaryList.add(dietary3);
            dietaryList.add(dietary4);
            dietaryList.add(dietary5);
            Log.d("临时数组", "dietaryList：" + String.valueOf(dietaryList));

            // 写入数据到数据库
            dietaryDao.saveInTx(dietaryList);
            Log.d("写入数据库", "dietaryDao[1].date：" + String.valueOf(dietaryDao.load(1L).getDate()));
            Log.d("写入数据库", "dietaryDao[2].date：" + String.valueOf(dietaryDao.load(2L).getDate()));
        }
    }

    /**
     * 写入接送记录测试数据
     */
    public void initTestDataPickup() {
        PickupHistoryDao pickupHistoryDao = getDaoSession().getPickupHistoryDao();
        Date dateNow = new Date();
        for (int i = 0; i < 3; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateNow);
            calendar.add(Calendar.DATE, -i);
            Date date = calendar.getTime();
            PickupHistory pickupHistoryPickup = new PickupHistory(date, PickupHistory.PICK_UP, "家长名字", "贾老师");
            calendar.add(Calendar.HOUR_OF_DAY, -6);
            date = calendar.getTime();
            PickupHistory pickupHistorySend = new PickupHistory(date, PickupHistory.SEND, "家长姓名", "甄老师");
            pickupHistoryList.add(pickupHistoryPickup);
            pickupHistoryList.add(pickupHistorySend);
            pickupHistoryDao.saveInTx(pickupHistoryList);
        }
    }
}
