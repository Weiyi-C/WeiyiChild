/*
 * Copyright (c) 2019-2026. zhengweiyi.cn all rights reserved
 * 郑维一版权所有，未经授权禁止使用，开源项目请遵守指定的开源协议
 */

package cn.zhengweiyi.weiyichild.fragment;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;

import cn.zhengweiyi.weiyichild.R;
import cn.zhengweiyi.weiyichild.custom.DateFormatUtil;
import cn.zhengweiyi.weiyichild.custom.DietaryRecyclerAdapter;
import cn.zhengweiyi.weiyichild.viewmodel.DietaryViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class DietaryFragment extends Fragment implements DietaryRecyclerAdapter.OnEmptyViewButtonClickListener {

    private RecyclerView mRecyclerView;
    private DietaryViewModel viewModel;
    private DietaryRecyclerAdapter adapter;

    private String selectDate;

    public DietaryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dietary, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(DietaryViewModel.class);

        mRecyclerView = view.findViewById(R.id.dietary_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new DietaryRecyclerAdapter(new ArrayList<>(), getContext());
        adapter.setOnEmptyViewButtonClickListener(emptyViewButtonClickListener);
        mRecyclerView.setAdapter(adapter);

        // 观察 LiveData
        viewModel.getDietaryLiveData().observe(getViewLifecycleOwner(), dietaryList -> {
            Log.d("DietaryRecycler", "食谱列表大小为" + dietaryList.size());
            adapter.refreshData(dietaryList);
        });

        // 加载今日数据
        selectDate = DateFormatUtil.DateToStr(new Date());
        viewModel.loadByDate(selectDate);

        return view;
    }

    public boolean isScrollTop() {
        return mRecyclerView != null && mRecyclerView.computeVerticalScrollOffset() == 0;
    }

    public void changeDate(String date) {
        selectDate = date;
        viewModel.loadByDate(date);
    }

    public DietaryRecyclerAdapter.OnEmptyViewButtonClickListener emptyViewButtonClickListener
            = new DietaryRecyclerAdapter.OnEmptyViewButtonClickListener() {
        @Override
        public void onEmptyViewButtonClick() {
            Log.i("DietaryRecycler", "点击插入测试数据按钮");
            if (selectDate == null) {
                selectDate = DateFormatUtil.DateToStr(new Date());
            }
            Log.i("DietaryRecycler", "当前日期为" + selectDate);
            viewModel.initTestDataDietary(selectDate);
        }
    };

    @Override
    public void onEmptyViewButtonClick() {

    }
}
