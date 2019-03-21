/*
 * Copyright (c) 2019. zhengweiyi.cn all rights reserved
 * 郑维一版权所有，未经授权禁止使用，开源项目请遵守指定的开源协议
 */

package cn.zhengweiyi.weiyichild.custom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.zhengweiyi.weiyichild.R;
import cn.zhengweiyi.weiyichild.bean.Dietary;

public class DietaryRecyclerAdapter extends RecyclerView.Adapter<DietaryRecyclerAdapter.DietaryViewHolder> {

    private List<Dietary> mDietaryList;

    static class DietaryViewHolder extends RecyclerView.ViewHolder {

        View dietaryView;
        TextView dietaryName;
        TextView dietaryContent;
        Context context;

        public DietaryViewHolder(@NonNull View itemView) {
            super(itemView);
            dietaryView = itemView;
            dietaryName = itemView.findViewById(R.id.dietary_text_name);
            dietaryContent = itemView.findViewById(R.id.dietary_text_content);
            context = itemView.getContext();
        }
    }

    public DietaryRecyclerAdapter(List<Dietary> dietaryList) {
        mDietaryList = dietaryList;
    }

    @NonNull
    @Override
    public DietaryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Log.d("RecyclerView", "onCreateViewHolder方法运行");
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_item_dietary, viewGroup, false);
        return new DietaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DietaryViewHolder viewHolder, int position) {
        Log.d("RecyclerView", "onBindViewHolder方法运行");
        final Dietary dietary = mDietaryList.get(position);
        viewHolder.dietaryName.setText(dietary.getName());
        viewHolder.dietaryContent.setText(dietary.getFoods());
    }

    @Override
    public int getItemCount() {
        return mDietaryList.size();
    }
}
