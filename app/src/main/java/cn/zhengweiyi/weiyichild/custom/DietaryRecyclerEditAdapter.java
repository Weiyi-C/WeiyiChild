/*
 * Copyright (c) 2019-2026. zhengweiyi.cn all rights reserved
 * 郑维一版权所有，未经授权禁止使用，开源项目请遵守指定的开源协议
 */

package cn.zhengweiyi.weiyichild.custom;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.zhengweiyi.weiyichild.R;
import cn.zhengweiyi.weiyichild.bean.Dietary;

public class DietaryRecyclerEditAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int EMPTY_BUTTON_VIEW = 0;
    private static final int EDIT_ITEM_VIEW = 1;

    private final Context mContext;
    private List<Dietary> mDietaryList;
    private OnEmptyViewButtonClickListener emptyListener;
    private OnItemDeleteListener deleteListener;

    public DietaryRecyclerEditAdapter(List<Dietary> dietaryList, Context context) {
        mDietaryList = new ArrayList<>(dietaryList);
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == EMPTY_BUTTON_VIEW) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.recycler_item_null_button, viewGroup, false);
            return new EmptyButtonViewHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.recycler_item_dietary_add, viewGroup, false);
            return new EditItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof EditItemViewHolder) {
            EditItemViewHolder holder = (EditItemViewHolder) viewHolder;
            Dietary dietary = mDietaryList.get(position);

            // 移除旧的 TextWatcher，防止回收复用时触发
            if (holder.nameWatcher != null) {
                holder.editName.removeTextChangedListener(holder.nameWatcher);
            }
            if (holder.foodsWatcher != null) {
                holder.editFoods.removeTextChangedListener(holder.foodsWatcher);
            }

            holder.editName.setText(dietary.getName());
            holder.editFoods.setText(dietary.getFoods());

            // 餐名 TextWatcher
            holder.nameWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    int pos = holder.getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION && pos < mDietaryList.size()) {
                        mDietaryList.get(pos).setName(s.toString());
                    }
                }
            };
            // 食物 TextWatcher
            holder.foodsWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    int pos = holder.getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION && pos < mDietaryList.size()) {
                        mDietaryList.get(pos).setFoods(s.toString());
                    }
                }
            };

            holder.editName.addTextChangedListener(holder.nameWatcher);
            holder.editFoods.addTextChangedListener(holder.foodsWatcher);

            // 删除按钮
            holder.btnDelete.setOnClickListener(v -> {
                int pos = holder.getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION && pos < mDietaryList.size()) {
                    removeItem(pos);
                }
            });
        } else if (viewHolder instanceof EmptyButtonViewHolder) {
            EmptyButtonViewHolder holder = (EmptyButtonViewHolder) viewHolder;
            holder.nullIcon.setImageResource(R.drawable.ic_alert);
            holder.nullTextHint.setText(mContext.getResources().getString(R.string.recycler_null));
            holder.nullButton.setText(mContext.getResources().getString(R.string.dietary_add_item));
            holder.nullButton.setOnClickListener(v -> {
                if (emptyListener != null) {
                    emptyListener.onEmptyViewButtonClick();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDietaryList.isEmpty() ? 1 : mDietaryList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mDietaryList.isEmpty() ? EMPTY_BUTTON_VIEW : EDIT_ITEM_VIEW;
    }

    /**
     * 设置食谱数据（来自 LiveData）
     */
    public void setDietaryList(List<Dietary> dietaryList) {
        mDietaryList = new ArrayList<>(dietaryList);
        notifyDataSetChanged();
    }

    /**
     * 添加一个新的空白餐次
     */
    public void addNewItem() {
        boolean wasEmpty = mDietaryList.isEmpty();
        Dietary dietary = new Dietary(new Date(), mDietaryList.size(), "", "");
        mDietaryList.add(dietary);
        if (wasEmpty) {
            notifyDataSetChanged();
        } else {
            notifyItemInserted(mDietaryList.size() - 1);
        }
    }

    /**
     * 删除指定位置的餐次
     */
    public void removeItem(int position) {
        if (position >= 0 && position < mDietaryList.size()) {
            Dietary removed = mDietaryList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, mDietaryList.size() - position);
            if (deleteListener != null) {
                deleteListener.onItemDeleted(removed, position);
            }
        }
    }

    /**
     * 获取当前编辑中的全部食谱数据
     */
    public List<Dietary> getAllItems() {
        return new ArrayList<>(mDietaryList);
    }

    /**
     * 空视图按钮点击回调
     */
    public interface OnEmptyViewButtonClickListener {
        void onEmptyViewButtonClick();
    }

    public void setOnEmptyViewButtonClickListener(OnEmptyViewButtonClickListener listener) {
        this.emptyListener = listener;
    }

    /**
     * 单条删除回调（用于已持久化数据的数据库删除）
     */
    public interface OnItemDeleteListener {
        void onItemDeleted(Dietary dietary, int position);
    }

    public void setOnItemDeleteListener(OnItemDeleteListener listener) {
        this.deleteListener = listener;
    }

    /**
     * 可编辑食谱项 ViewHolder
     */
    static class EditItemViewHolder extends RecyclerView.ViewHolder {

        EditText editName;
        EditText editFoods;
        ImageButton btnDelete;
        TextWatcher nameWatcher;
        TextWatcher foodsWatcher;

        EditItemViewHolder(@NonNull View itemView) {
            super(itemView);
            editName = itemView.findViewById(R.id.dietary_edit_name);
            editFoods = itemView.findViewById(R.id.dietary_edit_content);
            btnDelete = itemView.findViewById(R.id.dietary_edit_delete);
        }
    }

    /**
     * 空数据提示 ViewHolder（带按钮）
     */
    static class EmptyButtonViewHolder extends RecyclerView.ViewHolder {

        ImageView nullIcon;
        TextView nullTextHint;
        Button nullButton;

        EmptyButtonViewHolder(@NonNull View itemView) {
            super(itemView);
            nullIcon = itemView.findViewById(R.id.recyclerNullIcon);
            nullTextHint = itemView.findViewById(R.id.recyclerNullTextHint);
            nullButton = itemView.findViewById(R.id.recyclerNullButton);
        }
    }
}
