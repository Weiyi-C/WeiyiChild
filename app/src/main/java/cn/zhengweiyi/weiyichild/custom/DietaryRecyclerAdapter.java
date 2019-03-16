/*
 * Copyright (c) 2019. zhengweiyi.cn all rights reserved
 * 郑维一版权所有，未经授权禁止使用，开源项目请遵守指定的开源协议
 */

package cn.zhengweiyi.weiyichild.custom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.zhengweiyi.weiyichild.R;
import cn.zhengweiyi.weiyichild.bean.Dietary;

public class DietaryRecyclerAdapter extends RecyclerView.Adapter<DietaryRecyclerAdapter.ViewHolder> {

    private List<Dietary> mDietaryList;

    static class ViewHolder extends RecyclerView.ViewHolder {

        View dietaryView;
        TextView dietaryName;
        TextView dietaryContent;
        Context context;

        public ViewHolder(@NonNull View itemView) {
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_item_dietary, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final Dietary dietary = mDietaryList.get(position);
        viewHolder.dietaryName.setText(dietary.getName());
        viewHolder.dietaryContent.setText(dietary.getFoods());
    }

    @Override
    public int getItemCount() {
        return mDietaryList.size();
    }
}
