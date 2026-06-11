/*
 * Copyright (c) 2019-2026. zhengweiyi.cn all rights reserved
 * 郑维一版权所有，未经授权禁止使用，开源项目请遵守指定的开源协议
 */

package cn.zhengweiyi.weiyichild.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bravin.btoast.BToast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.zhengweiyi.weiyichild.R;
import cn.zhengweiyi.weiyichild.bean.Dietary;
import cn.zhengweiyi.weiyichild.custom.DateFormatUtil;
import cn.zhengweiyi.weiyichild.custom.DietaryRecyclerEditAdapter;
import cn.zhengweiyi.weiyichild.viewmodel.DietaryViewModel;

public class DietaryEditFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private DietaryRecyclerEditAdapter adapter;
    private DietaryViewModel viewModel;

    private String selectDate;

    public DietaryEditFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dietary_edit, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(DietaryViewModel.class);

        mRecyclerView = view.findViewById(R.id.dietary_edit_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new DietaryRecyclerEditAdapter(new ArrayList<>(), getContext());
        adapter.setOnEmptyViewButtonClickListener(() -> {
            if (selectDate == null) {
                selectDate = DateFormatUtil.DateToStr(new Date());
            }
            viewModel.initTestDataDietary(selectDate);
        });
        mRecyclerView.setAdapter(adapter);

        // 观察 LiveData，同步数据到编辑列表
        viewModel.getDietaryLiveData().observe(getViewLifecycleOwner(), dietaryList -> {
            Log.d("DietaryEdit", "收到食谱数据更新，数量：" + dietaryList.size());
            adapter.setDietaryList(dietaryList);
        });

        // 添加餐次按钮
        Button btnAdd = view.findViewById(R.id.add_dietary_item_button);
        btnAdd.setOnClickListener(v -> {
            adapter.addNewItem();
            mRecyclerView.scrollToPosition(adapter.getItemCount() - 1);
        });

        // 保存食谱按钮
        Button btnSave = view.findViewById(R.id.add_dietary_button);
        btnSave.setOnClickListener(v -> {
            List<Dietary> items = adapter.getAllItems();
            // 过滤掉餐名和食物都为空的项
            List<Dietary> validItems = new ArrayList<>();
            for (Dietary d : items) {
                if (d.getName() != null && !d.getName().trim().isEmpty()
                        || d.getFoods() != null && !d.getFoods().trim().isEmpty()) {
                    validItems.add(d);
                }
            }
            if (selectDate == null) {
                selectDate = DateFormatUtil.DateToStr(new Date());
            }
            viewModel.saveAllForDate(selectDate, validItems);
            BToast.success(requireContext())
                    .text(R.string.page_updated)
                    .show();
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
}
