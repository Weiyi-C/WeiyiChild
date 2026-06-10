/*
 * Copyright (c) 2019-2026. zhengweiyi.cn all rights reserved
 * 郑维一版权所有，未经授权禁止使用，开源项目请遵守指定的开源协议
 */

package cn.zhengweiyi.weiyichild;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import cn.zhengweiyi.weiyichild.custom.LocaleHelper;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.wrap(newBase));
    }
}
