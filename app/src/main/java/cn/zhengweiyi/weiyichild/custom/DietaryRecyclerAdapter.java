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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bravin.btoast.BToast;

import java.util.List;

import cn.zhengweiyi.weiyichild.R;
import cn.zhengweiyi.weiyichild.bean.Dietary;

public class DietaryRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int EMPTY_VIEW = 0;
    private static final int EMPTY_BUTTON_VIEW = 0;
    private static final int DIETARY_VIEW = 1;

    private Context mContext;
    private List<Dietary> mDietaryList;
    private OnEmptyViewButtonClickListener listener;

    public DietaryRecyclerAdapter(List<Dietary> dietaryList, Context context) {
        mDietaryList = dietaryList;
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Log.i("DietaryRecycler", "开始创建ViewHolder");
        View view;
        if (viewType == EMPTY_BUTTON_VIEW) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(
                    R.layout.recycler_item_null_button, viewGroup, false);
            Log.i("DietaryRecycler", "创建了一个EmptyButtonViewHolder");
            return new EmptyButtonViewHolder(view);
        } else if (viewType == DIETARY_VIEW) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(
                    R.layout.recycler_item_dietary, viewGroup, false);
            Log.i("DietaryRecycler", "创建了一个DietaryViewHolder");
            return new DietaryViewHolder(view);
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(
                    R.layout.recycler_item_null, viewGroup, false);
            Log.i("DietaryRecycler", "创建了一个EmptyViewHolder");
            return new EmptyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof DietaryViewHolder) {
            Log.i("DietaryRecycler", "根据视图创建第 " + position + " 个 item");
            DietaryViewHolder holder = (DietaryViewHolder) viewHolder;
            Dietary dietary = mDietaryList.get(position);
            holder.dietaryName.setText(dietary.getName());
            holder.dietaryContent.setText(dietary.getFoods());
        } else if (viewHolder instanceof EmptyButtonViewHolder) {
            EmptyButtonViewHolder holder = (EmptyButtonViewHolder) viewHolder;
            String hintText = mContext.getResources().getString(R.string.recycler_null);
            String buttonText = mContext.getResources().getString(R.string.button_insert_test_data);
            holder.nullIcon.setImageResource(R.drawable.ic_alert);
            holder.nullTextHint.setText(hintText);
            holder.nullButton.setText(buttonText);
            holder.nullButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onEmptyViewButtonClick();
                    }
                }
            });
        } else {
            EmptyViewHolder holder = (EmptyViewHolder) viewHolder;
            String hintText = mContext.getResources().getString(R.string.recycler_null_error);
            holder.nullTextHint.setText(hintText);
            Log.i("RecyclerView", "未获取到安全接送历史记录，返回空数据提示");
        }
    }

    @Override
    public int getItemCount() {
        if (mDietaryList.size() == 0) {
            Log.i("DietaryRecycler", "没有食谱，返回1个空数据提示");
            return 1;
        } else {
            Log.i("DietaryRecycler", "食谱数量为" + mDietaryList.size());
            return mDietaryList.size();
        }
    }

    /**
     * 判断Item类型
     *
     * @param position Item位置
     * @return 返回View标识
     */
    @Override
    public int getItemViewType(int position) {
        if (mDietaryList.size() == 0) {
            Log.i("DietaryRecycler", "第" + position + "项为空视图");
            return EMPTY_BUTTON_VIEW;
        } else {
            Log.i("DietaryRecycler", "第" + position + "项位正常视图类型");
            return DIETARY_VIEW;
        }
    }

    /**
     * 每日食谱正常布局ViewHolder
     */
    class DietaryViewHolder extends RecyclerView.ViewHolder {

        View dietaryView;
        TextView dietaryName;
        TextView dietaryContent;
        //Context context;

        DietaryViewHolder(@NonNull View itemView) {
            super(itemView);
            dietaryView = itemView;
            dietaryName = itemView.findViewById(R.id.dietary_text_name);
            dietaryContent = itemView.findViewById(R.id.dietary_text_content);
            //context = itemView.getContext();
        }
    }

    /**
     * 空数据提示ViewHolder
     */
    class EmptyViewHolder extends RecyclerView.ViewHolder {

        View emptyView;
        ImageView nullIcon;
        TextView nullTextHint;

        EmptyViewHolder(@NonNull View itemView) {
            super(itemView);
            emptyView = itemView;
            nullIcon = emptyView.findViewById(R.id.recyclerNullIcon);
            nullTextHint = emptyView.findViewById(R.id.recyclerNullTextHint);
        }
    }

    /**
     * 空数据提示ViewHolder(带Button)
     */
    class EmptyButtonViewHolder extends RecyclerView.ViewHolder {

        View emptyButtonView;
        ImageView nullIcon;
        TextView nullTextHint;
        Button nullButton;

        EmptyButtonViewHolder(@NonNull View itemView) {
            super(itemView);
            emptyButtonView = itemView;
            nullIcon = emptyButtonView.findViewById(R.id.recyclerNullIcon);
            nullTextHint = emptyButtonView.findViewById(R.id.recyclerNullTextHint);
            nullButton = emptyButtonView.findViewById(R.id.recyclerNullButton);
        }
    }

    /**
     * 空视图按钮点击接口
     */
    public interface OnEmptyViewButtonClickListener {
        void onEmptyViewButtonClick();
    }

    /**
     * 设置空布局按钮的点击监听回调接口
     *
     * @param listener 回调接口
     */
    public void setOnEmptyViewButtonClickListener(OnEmptyViewButtonClickListener listener) {
        this.listener = listener;
    }

    // 数据更新
    public void refreshData(List<Dietary> dietaryList) {
        mDietaryList.clear();
        mDietaryList = dietaryList;
        Log.i("DietaryRecycler", "通知数据更新");
        notifyDataSetChanged();
        BToast.success(mContext)
                .text(R.string.page_updated)
                .show();
    }
}
