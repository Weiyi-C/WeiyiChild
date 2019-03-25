/*
 * Copyright (c) 2019. zhengweiyi.cn all rights reserved
 * 郑维一版权所有，未经授权禁止使用，开源项目请遵守指定的开源协议
 */

package cn.zhengweiyi.weiyichild;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import cn.zhengweiyi.weiyichild.custom.StatusBarUtil;
import cn.zhengweiyi.weiyichild.custom.VersionCodeUtil;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        StatusBarUtil.setStatusBarMode(this, true, R.color.colorPrimaryDark);

        TextView textVersion = findViewById(R.id.app_version);
        String version = getResources().getString(R.string.app_version) + " " + VersionCodeUtil.getVerName(this);
        textVersion.setText(version);
    }
}
