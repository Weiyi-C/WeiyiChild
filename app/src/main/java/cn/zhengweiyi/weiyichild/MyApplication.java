/*
 * Copyright (c) 2019. zhengweiyi.cn all rights reserved
 * 郑维一版权所有，未经授权禁止使用，开源项目请遵守指定的开源协议
 */

package cn.zhengweiyi.weiyichild;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.util.DisplayMetrics;
import android.util.Log;

import com.bravin.btoast.BToast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    public static final int CLICK_MESSAGE = 1;

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
        setBToast();
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
     * 配置 BToast
     */
    private void setBToast() {
        BToast.Config.getInstance()
//                .setAnimate()               // 是否开启动画 默认为 false;
                .setAnimationDuration(200)     // 动画时长 默认为 800毫秒
                .setAnimationGravity(BToast.ANIMATION_GRAVITY_BOTTOM)      // 动画从哪个边进入 默认为 BToast.ANIMATION_GRAVITY_TOP
                .setDuration(BToast.DURATION_SHORT)              // 持续时间 BToast.DURATION_SHORT or BToast.DURATION_LONG
//                .setTextColor()             // 文字颜色， 默认为 white
//                .setErrorColor()            // 错误类型背景颜色 默认为 red
                .setInfoColor(0xEE2665D3)             // 通知类型背景颜色 默认为 blue
                .setSuccessColor(0xFF00AA74)          // 成功类型背景颜色 默认为 green
//                .setWarningColor()          // 警告类型背景颜色 默认为 orange
//                .setLayoutGravity()         // 指定Toast在target的位置 默认在target的底部 BToast.LAYOUT_GRAVITY_BOTTOM
//                .setLongDurationMillis()    // LONG类型对应的时长，默认为4500毫秒
//                .setRadius()                // 设置四个角的弧度，默认是高度的一半，可以设置一个非负值
//                .setRelativeGravity()       // 指定toast的内容相对于toast自身在内部的位置, 默认为 BToast.RELATIVE_GRAVITY_CENTER
//                .setSameLength()            // 是否和target同宽（高） 如果toast在target的上方或下方，sameLength代表同宽，在target左右则代表同高
//                .setShortDurationMillis()   // SHORT类型对应的时长，默认为3000毫秒
//                .setShowIcon()              // 是否显示icon 默认显示
                .setTextSize(12)              // 文字大小，单位是sp
                .apply(this);               // must call
    }

    public void setAppLanguage() {
        Resources resources = getResources();
        final SharedPreferences languageSettings = getSharedPreferences("language_list", Context.MODE_PRIVATE);
        int languageId = languageSettings.getInt("language_list", -1);
        // 获取设置语言
        Locale mLocale;
        switch (languageId) {
            case -1:
                mLocale = Locale.getDefault();
                break;
            case 0:
                mLocale = Locale.CHINESE;
                break;
            case 1:
                mLocale = Locale.ENGLISH;
                break;
            default:
                mLocale = Locale.getDefault();
                break;
        }
        // 判断当前语言，如与设置不同则修改当前语言以匹配设置语言
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        if (mLocale == configuration.locale) {
            configuration.setLocale(mLocale);
            resources.updateConfiguration(configuration, displayMetrics);
        }
    }

    /**
     * 初始化数据
     * TODO 实现服务器读取后修改该方法
     */
    public void initData() {
        DietaryDao dietaryDao = getDaoSession().getDietaryDao();
        if (dietaryDao.load(1L) == null) {  //判断数据库是否有数据

            // 写入数据到数据库
            dietaryDao.saveInTx(dietaryList);
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

    /**
     * 写入每日食谱测试数据
     */
    public boolean initTestDataDietary(String date) {
        boolean re = false;
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
                    BToast.info(this)
                            .text("星期天没有食谱，周末孩子都不上幼儿园的哦！")
                            .show();
                    re = false;
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
                    re = true;
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
                    re = true;
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
                    re = true;
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
                    re = true;
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
                    re = true;
                    break;
                case 7:
                    BToast.info(this)
                            .text("星期六没有食谱，周末孩子都不上幼儿园的哦！")
                            .show();
                    re = false;
                    break;
                default:
                    BToast.error(this)
                            .text("星期八的菜谱？大概是程序出错了！")
                            .show();
                    re = false;
                    break;
            }
            Log.d("DietaryDAO", "插入测试数据后数据库大小为" + dietaryLab.getAllDietary().size());
        }
        return re;
    }
}
