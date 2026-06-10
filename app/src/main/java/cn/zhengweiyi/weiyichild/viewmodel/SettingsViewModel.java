/*
 * Copyright (c) 2019-2026. zhengweiyi.cn all rights reserved
 * 郑维一版权所有，未经授权禁止使用，开源项目请遵守指定的开源协议
 */

package cn.zhengweiyi.weiyichild.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class SettingsViewModel extends AndroidViewModel {

    private static final String PREF_NAME = "language_list";
    private static final String PREF_KEY = "language_list";

    private final SharedPreferences prefs;
    private final MutableLiveData<Integer> languageLiveData;

    public SettingsViewModel(@NonNull Application application) {
        super(application);
        prefs = application.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        languageLiveData = new MutableLiveData<>(prefs.getInt(PREF_KEY, -1));
    }

    public LiveData<Integer> getLanguageLiveData() {
        return languageLiveData;
    }

    public int getCurrentLanguage() {
        return prefs.getInt(PREF_KEY, -1);
    }

    /**
     * 保存语言设置并通知观察者
     *
     * @param languageId 语言ID：-1=跟随系统, 0=简体中文, 1=English
     */
    public void setLanguage(int languageId) {
        prefs.edit().putInt(PREF_KEY, languageId).apply();
        languageLiveData.setValue(languageId);
    }
}
