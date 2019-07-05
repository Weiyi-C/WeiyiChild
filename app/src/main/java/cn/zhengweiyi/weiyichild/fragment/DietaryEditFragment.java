/*
 * Copyright (c) 2019. zhengweiyi.cn all rights reserved
 * 郑维一版权所有，未经授权禁止使用，开源项目请遵守指定的开源协议
 */

package cn.zhengweiyi.weiyichild.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import cn.zhengweiyi.weiyichild.MyApplication;
import cn.zhengweiyi.weiyichild.R;
import cn.zhengweiyi.weiyichild.bean.Dietary;
import cn.zhengweiyi.weiyichild.custom.DateFormatUtil;
import cn.zhengweiyi.weiyichild.custom.DietaryRecyclerEditAdapter;
import cn.zhengweiyi.weiyichild.greenDao.DietaryLab;

/**
 * A simple {@link Fragment} subclass.
 */
public class DietaryEditFragment extends Fragment {

    private List<Dietary> dietaryList;
    private RecyclerView mRecyclerView;
    private MyApplication app;
    private DietaryLab dietaryLab;
    private DietaryRecyclerEditAdapter adapter;

    private String selectDate;

    public DietaryEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dietary_edit, container, false);
        // 获取Application
        app = (MyApplication) Objects.requireNonNull(getActivity()).getApplication();
        // 获取食谱
        dietaryLab = new DietaryLab(app.getDaoSession().getDietaryDao());
        dietaryList = dietaryLab.getDietaryByDate(DateFormatUtil.DateToStr(new Date()));
        // 显示食谱
        mRecyclerView = view.findViewById(R.id.dietary_edit_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        return view;
    }

    public boolean isScrollTop() {
        return mRecyclerView != null && mRecyclerView.computeVerticalScrollOffset() == 0;
    }

    public void changeDate(String date) {
        selectDate = date;
        if (dietaryList.size() > 0) {
            dietaryList.clear();
        }
        dietaryList = dietaryLab.getDietaryByDate(date);
        Log.d("DietaryRecycler", date + "的新食谱列表大小为" + dietaryList.size());
        adapter.refreshData(dietaryList);
    }

}
