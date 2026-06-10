/*
 * Copyright (c) 2019-2026. zhengweiyi.cn all rights reserved
 * 郑维一版权所有，未经授权禁止使用，开源项目请遵守指定的开源协议
 */

package cn.zhengweiyi.weiyichild;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import cn.zhengweiyi.weiyichild.custom.StatusBarUtil;
import cn.zhengweiyi.weiyichild.fragment.GeneralPreferenceFragment;
import cn.zhengweiyi.weiyichild.viewmodel.SettingsViewModel;

public class SettingsActivity extends AppCompatActivity {

    private SettingsViewModel viewModel;
    private int lastLanguageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        StatusBarUtil.setStatusBarMode(this, true, R.color.colorPrimaryDark);

        viewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        viewModel.applyLanguage();
        lastLanguageId = viewModel.getCurrentLanguage();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings_container, new GeneralPreferenceFragment())
                    .commit();
        }

        viewModel.getLanguageLiveData().observe(this, languageId -> {
            if (languageId != lastLanguageId) {
                lastLanguageId = languageId;
                viewModel.applyLanguage();
                recreate();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
