/*
 * Copyright (c) 2019-2026. zhengweiyi.cn all rights reserved
 * 郑维一版权所有，未经授权禁止使用，开源项目请遵守指定的开源协议
 */

package cn.zhengweiyi.weiyichild.custom;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.LocaleList;

import java.util.Locale;

/**
 * 语言切换工具类，通过 ContextWrapper 在 attachBaseContext 阶段设置 Locale
 */
public class LocaleHelper extends ContextWrapper {

    private static final String PREF_NAME = "language_list";
    private static final String PREF_KEY = "language_list";

    public LocaleHelper(Context base) {
        super(base);
    }

    /**
     * 根据保存的语言偏好包装 Context
     */
    public static ContextWrapper wrap(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        int languageId = prefs.getInt(PREF_KEY, -1);
        Locale locale = getLocaleForLanguage(languageId);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        Locale currentLocale = config.getLocales().get(0);

        if (!locale.equals(currentLocale)) {
            Locale.setDefault(locale);
            config.setLocale(locale);
            config.setLocales(new LocaleList(locale));
            Context updatedContext = context.createConfigurationContext(config);
            return new LocaleHelper(updatedContext);
        }

        return new LocaleHelper(context);
    }

    /**
     * 根据语言ID获取对应的 Locale
     */
    public static Locale getLocaleForLanguage(int languageId) {
        switch (languageId) {
            case 0:
                return Locale.CHINESE;
            case 1:
                return Locale.ENGLISH;
            default:
                return Locale.getDefault();
        }
    }
}
