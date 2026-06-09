/*
 * Copyright (c) 2019-2026. zhengweiyi.cn all rights reserved
 * 郑维一版权所有，未经授权禁止使用，开源项目请遵守指定的开源协议
 */

package cn.zhengweiyi.weiyichild.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.bravin.btoast.BToast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.zhengweiyi.weiyichild.bean.AppDatabase;
import cn.zhengweiyi.weiyichild.bean.Dietary;
import cn.zhengweiyi.weiyichild.bean.DietaryDao;
import cn.zhengweiyi.weiyichild.custom.DateFormatUtil;

public class DietaryViewModel extends AndroidViewModel {

    private final DietaryDao dietaryDao;
    private final MutableLiveData<String> dateLiveData = new MutableLiveData<>();
    private final LiveData<List<Dietary>> dietaryLiveData;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public DietaryViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(application);
        dietaryDao = db.dietaryDao();
        dietaryLiveData = Transformations.switchMap(dateLiveData,
                date -> dietaryDao.loadByDateLive(DateFormatUtil.StrToDate(date)));
    }

    public LiveData<List<Dietary>> getDietaryLiveData() {
        return dietaryLiveData;
    }

    public void loadByDate(String date) {
        dateLiveData.setValue(date);
    }

    public void initTestDataDietary(String date) {
        executor.execute(() -> {
            List<Dietary> existing = dietaryDao.loadByDate(DateFormatUtil.StrToDate(date));
            if (!existing.isEmpty()) {
                return;
            }

            Log.i("DietaryDAO", "插入日期" + date + "的测试数据");
            Calendar calendar = Calendar.getInstance();
            Date mDate = DateFormatUtil.StrToDate(date);
            calendar.setTime(mDate);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            List<Dietary> list = new ArrayList<>();

            switch (dayOfWeek) {
                case 1:
                    BToast.info(getApplication())
                            .text("星期天没有食谱，周末孩子都不上幼儿园的哦！")
                            .show();
                    return;
                case 2:
                    list.add(new Dietary(mDate, 0, "早餐", "奶黄包、豆浆"));
                    list.add(new Dietary(mDate, 1, "上午点心", "香蕉、哈密瓜"));
                    list.add(new Dietary(mDate, 2, "午餐", "土豆鸡块、番茄炒蛋、清炒包菜、冬瓜虾皮汤"));
                    list.add(new Dietary(mDate, 3, "下午点心", "蒸南瓜、红豆汤"));
                    list.add(new Dietary(mDate, 4, "过敏儿童", "白菜粉丝汤"));
                    list.add(new Dietary(mDate, 5, "病号餐", "青菜肉丝面"));
                    break;
                case 3:
                    list.add(new Dietary(mDate, 0, "早餐", "水煮蛋、青菜瘦肉粥"));
                    list.add(new Dietary(mDate, 1, "上午点心", "苹果、火龙果"));
                    list.add(new Dietary(mDate, 2, "午餐", "糖醋里脊、茭白肉片、清炒秋葵、玉米排骨汤"));
                    list.add(new Dietary(mDate, 3, "下午点心", "芹菜猪肉蒸饺、柠檬水"));
                    list.add(new Dietary(mDate, 5, "病号餐", "南瓜粥"));
                    break;
                case 4:
                    list.add(new Dietary(mDate, 0, "早餐", "肉丝面"));
                    list.add(new Dietary(mDate, 1, "上午点心", "柚子、香蕉"));
                    list.add(new Dietary(mDate, 2, "午餐", "银鱼蒸蛋、红烧萝卜炖肉、清炒西葫芦、紫菜肉丝汤"));
                    list.add(new Dietary(mDate, 3, "下午点心", "三鲜小圆子"));
                    list.add(new Dietary(mDate, 4, "过敏儿童", "小肉圆、青菜胡萝卜肉丝汤"));
                    list.add(new Dietary(mDate, 5, "病号餐", "荞麦面"));
                    break;
                case 5:
                    list.add(new Dietary(mDate, 0, "早餐", "鲜肉小馄饨"));
                    list.add(new Dietary(mDate, 1, "上午点心", "圣女果、苹果"));
                    list.add(new Dietary(mDate, 2, "午餐", "红烧大虾、木耳山药肉片、土豆丝、贡丸鲜菇汤"));
                    list.add(new Dietary(mDate, 3, "下午点心", "肉末粉丝"));
                    list.add(new Dietary(mDate, 4, "过敏儿童", "红烧鸡腿"));
                    list.add(new Dietary(mDate, 5, "病号餐", "玉米粥"));
                    break;
                case 6:
                    list.add(new Dietary(mDate, 0, "早餐", "酸奶、小米糕"));
                    list.add(new Dietary(mDate, 1, "上午点心", "葡萄、苹果梨"));
                    list.add(new Dietary(mDate, 2, "午餐", "意大利炒饭、笑脸饼、小香肠、水果拼盘、萝卜排骨汤"));
                    list.add(new Dietary(mDate, 3, "下午点心", "葱花鸡蛋饼、绿豆汤"));
                    list.add(new Dietary(mDate, 5, "病号餐", "肉末粉丝汤"));
                    break;
                case 7:
                    BToast.info(getApplication())
                            .text("星期六没有食谱，周末孩子都不上幼儿园的哦！")
                            .show();
                    return;
                default:
                    BToast.error(getApplication())
                            .text("星期八的菜谱？大概是程序出错了！")
                            .show();
                    return;
            }

            dietaryDao.insertInTx(list);
            Log.d("DietaryDAO", "插入测试数据后数据库大小为" + dietaryDao.loadAll().size());
            loadByDate(date);
        });
    }
}
