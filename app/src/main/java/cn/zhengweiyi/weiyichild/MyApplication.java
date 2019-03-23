/*
 * Copyright (c) 2019. zhengweiyi.cn all rights reserved
 * 郑维一版权所有，未经授权禁止使用，开源项目请遵守指定的开源协议
 */

package cn.zhengweiyi.weiyichild;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.net.URI;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.zhengweiyi.weiyichild.bean.Dietary;
import cn.zhengweiyi.weiyichild.bean.PickupHistory;
import cn.zhengweiyi.weiyichild.custom.DateFormatUtil;
import cn.zhengweiyi.weiyichild.greenDao.DietaryLab;
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

    public void initTestDataDietary(String date) {
        Log.i("DietaryDAO", "插入日期" + date + "的测试数据");
        DietaryDao dietaryDao = getDaoSession().getDietaryDao();
        DietaryLab dietaryLab = new DietaryLab(dietaryDao);
        if (dietaryLab.getDietaryByDate(date).size() == 0) {
            Calendar calendar = Calendar.getInstance();
            Date mDate = DateFormatUtil.StrToDate(date);
            calendar.setTime(mDate);
            int i = calendar.get(Calendar.DAY_OF_WEEK);
            switch (i) {
                case 1:
                    Toast.makeText(this, "星期天没有食谱，周末孩子都不上幼儿园的哦！", Toast.LENGTH_LONG).show();
                    break;
                case 2:
                    Dietary dietary10 = new Dietary(mDate, 0, "早餐", "奶黄包、豆浆");
                    Dietary dietary11 = new Dietary(mDate, 1, "上午点心", "香蕉、哈密瓜");
                    Dietary dietary12 = new Dietary(mDate, 2, "午餐", "土豆鸡块、番茄炒蛋、清炒包菜、冬瓜虾皮汤");
                    Dietary dietary13 = new Dietary(mDate, 3, "下午点心", "蒸南瓜、红豆汤");
                    Dietary dietary14 = new Dietary(mDate, 4, "过敏儿童", "白菜粉丝汤");
                    Dietary dietary15 = new Dietary(mDate, 5, "病号餐", "青菜肉丝面");
                    dietaryList.add(dietary10);
                    dietaryList.add(dietary11);
                    dietaryList.add(dietary12);
                    dietaryList.add(dietary13);
                    dietaryList.add(dietary14);
                    dietaryList.add(dietary15);
                    dietaryDao.saveInTx(dietaryList);
                    break;
                case 3:
                    Dietary dietary20 = new Dietary(mDate, 0, "早餐", "水煮蛋、青菜瘦肉粥");
                    Dietary dietary21 = new Dietary(mDate, 1, "上午点心", "苹果、火龙果");
                    Dietary dietary22 = new Dietary(mDate, 2, "午餐", "糖醋里脊、茭白肉片、清炒秋葵、玉米排骨汤");
                    Dietary dietary23 = new Dietary(mDate, 3, "下午点心", "芹菜猪肉蒸饺、柠檬水");
                    Dietary dietary25 = new Dietary(mDate, 5, "病号餐", "南瓜粥");
                    dietaryList.add(dietary20);
                    dietaryList.add(dietary21);
                    dietaryList.add(dietary22);
                    dietaryList.add(dietary23);
                    dietaryList.add(dietary25);
                    dietaryDao.saveInTx(dietaryList);
                    break;
                case 4:
                    Dietary dietary30 = new Dietary(mDate, 0, "早餐", "肉丝面");
                    Dietary dietary31 = new Dietary(mDate, 1, "上午点心", "柚子、香蕉");
                    Dietary dietary32 = new Dietary(mDate, 2, "午餐", "银鱼蒸蛋、红烧萝卜炖肉、清炒西葫芦、紫菜肉丝汤");
                    Dietary dietary33 = new Dietary(mDate, 3, "下午点心", "三鲜小圆子");
                    Dietary dietary34 = new Dietary(mDate, 4, "过敏儿童", "小肉圆、青菜胡萝卜肉丝汤");
                    Dietary dietary35 = new Dietary(mDate, 5, "病号餐", "荞麦面");
                    dietaryList.add(dietary30);
                    dietaryList.add(dietary31);
                    dietaryList.add(dietary32);
                    dietaryList.add(dietary33);
                    dietaryList.add(dietary34);
                    dietaryList.add(dietary35);
                    dietaryDao.saveInTx(dietaryList);
                    break;
                case 5:
                    Dietary dietary40 = new Dietary(mDate, 0, "早餐", "鲜肉小馄饨");
                    Dietary dietary41 = new Dietary(mDate, 1, "上午点心", "圣女果、苹果");
                    Dietary dietary42 = new Dietary(mDate, 2, "午餐", "红烧大虾、木耳山药肉片、土豆丝、贡丸鲜菇汤");
                    Dietary dietary43 = new Dietary(mDate, 3, "下午点心", "肉末粉丝");
                    Dietary dietary44 = new Dietary(mDate, 4, "过敏儿童", "红烧鸡腿");
                    Dietary dietary45 = new Dietary(mDate, 5, "病号餐", "玉米粥");
                    dietaryList.add(dietary40);
                    dietaryList.add(dietary41);
                    dietaryList.add(dietary42);
                    dietaryList.add(dietary43);
                    dietaryList.add(dietary44);
                    dietaryList.add(dietary45);
                    dietaryDao.saveInTx(dietaryList);
                    break;
                case 6:
                    Dietary dietary50 = new Dietary(mDate, 0, "早餐", "酸奶、小米糕");
                    Dietary dietary51 = new Dietary(mDate, 1, "上午点心", "葡萄、苹果梨");
                    Dietary dietary52 = new Dietary(mDate, 2, "午餐", "意大利炒饭、笑脸饼、小香肠、水果拼盘、萝卜排骨汤");
                    Dietary dietary53 = new Dietary(mDate, 3, "下午点心", "葱花鸡蛋饼、绿豆汤");
                    Dietary dietary55 = new Dietary(mDate, 5, "病号餐", "肉末粉丝汤");
                    dietaryList.add(dietary50);
                    dietaryList.add(dietary51);
                    dietaryList.add(dietary52);
                    dietaryList.add(dietary53);
                    dietaryList.add(dietary55);
                    dietaryDao.saveInTx(dietaryList);
                    break;
                case 7:
                    Toast.makeText(this, "星期六没有食谱，周末孩子都不上幼儿园的哦！", Toast.LENGTH_LONG).show();
                    break;
                default:
                    Toast.makeText(this, "星期八的菜谱？大概是程序出错了！", Toast.LENGTH_LONG).show();
                    break;
            }

            // 写入数据到数据库

            Log.d("DietaryDAO", "插入测试数据后数据库大小为" + dietaryLab.getAllDietary().size());
        }
    }
}
