/*
 * Copyright (c) 2019. zhengweiyi.cn all rights reserved
 * 郑维一版权所有，未经授权禁止使用，开源项目请遵守指定的开源协议
 */

package cn.zhengweiyi.weiyichild.custom;

import android.content.Context;
import android.content.pm.PackageManager;

public class VersionCodeUtil {

    /**
     * 获取当前本地apk的版本
     *
     * @param context 上下文
     * @return 版本code
     */
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            //获取软件版本号，对应 build.gradle 下 versionCode
            versionCode = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取当前本地apk的版本名称
     *
     * @param context 上下文
     * @return 版本name
     */
    public static String getVerName(Context context) {
        String verName = null;
        try {
            verName = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }
}
