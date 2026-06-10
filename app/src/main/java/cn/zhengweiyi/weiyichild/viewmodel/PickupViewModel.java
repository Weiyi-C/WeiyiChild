/*
 * Copyright (c) 2019-2026. zhengweiyi.cn all rights reserved
 * 郑维一版权所有，未经授权禁止使用，开源项目请遵守指定的开源协议
 */

package cn.zhengweiyi.weiyichild.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.zhengweiyi.weiyichild.bean.AppDatabase;
import cn.zhengweiyi.weiyichild.bean.PickupHistory;
import cn.zhengweiyi.weiyichild.bean.PickupHistoryDao;

public class PickupViewModel extends AndroidViewModel {

    private final PickupHistoryDao pickupHistoryDao;
    private final MutableLiveData<List<PickupHistory>> pickupHistoryLiveData = new MutableLiveData<>();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public PickupViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(application);
        pickupHistoryDao = db.pickupHistoryDao();
    }

    public LiveData<List<PickupHistory>> getPickupHistoryLiveData() {
        return pickupHistoryLiveData;
    }

    public void loadAll() {
        executor.execute(() -> {
            List<PickupHistory> list = pickupHistoryDao.loadAll();
            pickupHistoryLiveData.postValue(list);
        });
    }

    public void initTestDataPickup() {
        executor.execute(() -> {
            List<PickupHistory> list = new ArrayList<>();
            Date dateNow = new Date();
            for (int i = 0; i < 3; i++) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateNow);
                calendar.add(Calendar.DATE, -i);
                Date date = calendar.getTime();
                PickupHistory pickup = new PickupHistory(date, PickupHistory.PICK_UP, "家长名字", "贾老师");
                calendar.add(Calendar.HOUR_OF_DAY, -6);
                date = calendar.getTime();
                PickupHistory send = new PickupHistory(date, PickupHistory.SEND, "家长姓名", "甄老师");
                list.add(pickup);
                list.add(send);
            }
            pickupHistoryDao.insertInTx(list);
            loadAll();
        });
    }
}
