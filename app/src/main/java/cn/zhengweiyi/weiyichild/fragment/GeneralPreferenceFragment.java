/*
 * Copyright (c) 2019-2026. zhengweiyi.cn all rights reserved
 * 郑维一版权所有，未经授权禁止使用，开源项目请遵守指定的开源协议
 */

package cn.zhengweiyi.weiyichild.fragment;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;

import cn.zhengweiyi.weiyichild.R;
import cn.zhengweiyi.weiyichild.viewmodel.SettingsViewModel;

public class GeneralPreferenceFragment extends PreferenceFragmentCompat {

    private SettingsViewModel viewModel;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.pref_general, rootKey);
        viewModel = new ViewModelProvider(requireActivity()).get(SettingsViewModel.class);

        ListPreference languagePref = findPreference("language_list");
        if (languagePref != null) {
            languagePref.setOnPreferenceChangeListener((preference, newValue) -> {
                int languageId = Integer.parseInt((String) newValue);
                viewModel.setLanguage(languageId);
                return true;
            });
        }
    }
}
