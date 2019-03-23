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
import cn.zhengweiyi.weiyichild.custom.DietaryRecyclerAdapter;
import cn.zhengweiyi.weiyichild.greenDao.DietaryLab;

/**
 * A simple {@link Fragment} subclass.
 */
public class DietaryFragment extends Fragment implements DietaryRecyclerAdapter.OnEmptyViewButtonClickListener {

    private List<Dietary> dietaryList;
    private RecyclerView mRecyclerView;
    private MyApplication app;
    private DietaryLab dietaryLab;
    private DietaryRecyclerAdapter adapter;

    private String selectDate;

    public DietaryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dietary, container, false);
        // 获取 Application
        app = (MyApplication) Objects.requireNonNull(getActivity()).getApplication();
        // 读取食谱
        dietaryLab = new DietaryLab(app.getDaoSession().getDietaryDao());
        dietaryList = dietaryLab.getDietaryByDate(DateFormatUtil.DateToStr(new Date()));
        // 显示食谱
        mRecyclerView = view.findViewById(R.id.dietary_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new DietaryRecyclerAdapter(dietaryList, getContext());
        adapter.setOnEmptyViewButtonClickListener(emptyViewButtonClickListener);
        mRecyclerView.setAdapter(adapter);
        // Inflate the layout for this fragment
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
        Log.d("DietaryRecycler", date + "的食谱列表大小为" + dietaryList.size());
        adapter.refreshData(dietaryList);
    }

    public DietaryRecyclerAdapter.OnEmptyViewButtonClickListener emptyViewButtonClickListener
            = new DietaryRecyclerAdapter.OnEmptyViewButtonClickListener() {
        @Override
        public void onEmptyViewButtonClick() {
            Log.i("DietaryRecycler", "点击“插入测试数据按钮”");
            if (selectDate == null) {
                selectDate = DateFormatUtil.DateToStr(new Date());
            }
            Log.i("DietaryRecycler", "当前日期为" + selectDate);
            app.initTestDataDietary(selectDate);
            dietaryList.clear();
            dietaryList = dietaryLab.getDietaryByDate(selectDate);
            adapter.refreshData(dietaryList);
        }
    };

    @Override
    public void onEmptyViewButtonClick() {

    }
}
