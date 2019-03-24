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
import cn.zhengweiyi.weiyichild.bean.PickupHistory;

public class PickupHistoryRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int EMPTY_VIEW = 0;
    private static final int EMPTY_BUTTON_VIEW = 1;
    private static final int PICK_UP_VIEW = 2;
    private static final int SEND_VIEW = 3;
    private Context mContext;

    // private int mEmptyType = 0;         // 控制空布局的显隐

    private List<PickupHistory> mPickupHistoryList;
    private OnEmptyViewButtonClickListener listener;

    public PickupHistoryRecyclerAdapter(List<PickupHistory> pickupHistoryList, Context context) {
        mPickupHistoryList = pickupHistoryList;
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        if (viewType == EMPTY_BUTTON_VIEW) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(
                    R.layout.recycler_item_null_button, viewGroup, false);
            return new EmptyButtonViewHolder(view);
        } else if (viewType == PICK_UP_VIEW) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(
                    R.layout.recycler_item_pickup_history_pickup, viewGroup, false);
            return new PickupViewHolder(view);
        } else if (viewType == SEND_VIEW) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(
                    R.layout.recycler_item_pickup_history_send, viewGroup, false);
            return new SendViewHolder(view);
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(
                    R.layout.recycler_item_null, viewGroup, false);
            return new EmptyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof PickupViewHolder) {
            PickupViewHolder holder = (PickupViewHolder) viewHolder;
            String text = mContext.getResources().getString(R.string.pickup_pick_up_time);
            String time = DateFormatUtil.DatetimeToStr(mPickupHistoryList.get(position).getDate());
            holder.pickupTime.setText(text + " " + time);
            holder.pickupTeacher.setText(mPickupHistoryList.get(position).getTeacher());
        } else if (viewHolder instanceof SendViewHolder) {
            SendViewHolder holder = (SendViewHolder) viewHolder;
            String text = mContext.getResources().getString(R.string.pickup_send_time);
            String time = DateFormatUtil.DatetimeToStr(mPickupHistoryList.get(position).getDate());
            holder.pickupTime.setText(text + " " + time);
            holder.pickupTeacher.setText(mPickupHistoryList.get(position).getTeacher());
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
        if (mPickupHistoryList.size() == 0) {
            Log.d("RecyclerView", "mPickupHistoryList 长度为 0，显示空数据提示");
            return 1;
        } else {
            Log.d("RecyclerView", "mPickupHistoryList 长度为 " + mPickupHistoryList.size());
            return mPickupHistoryList.size();
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
        if (mPickupHistoryList.size() == 0) {
            Log.d("PickupHistory",
                    "历史记录长度为零，返回空数据（带按钮）类型码：" + EMPTY_BUTTON_VIEW);
            return EMPTY_BUTTON_VIEW;
        } else if (mPickupHistoryList.get(position).getType().equals(PickupHistory.PICK_UP)) {
            Log.d("PickupHistory",
                    "历史记录类型为：接孩子，返回类型码：" + PICK_UP_VIEW);
            return PICK_UP_VIEW;
        } else if (mPickupHistoryList.get(position).getType().equals(PickupHistory.SEND)) {
            Log.d("PickupHistory",
                    "历史记录类型为：送孩子，返回类型码：" + SEND_VIEW);
            return SEND_VIEW;
        } else {
            Log.d("PickupHistory",
                    "历史记录长度不为零且无法判断历史记录类型，返回空数据类型码：" + EMPTY_VIEW);
            return EMPTY_VIEW;
            // return super.getItemViewType(position);
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
     * 接孩子记录ViewHolder
     */
    class PickupViewHolder extends RecyclerView.ViewHolder {

        View pickupView;
        TextView pickupTime;
        TextView pickupTeacher;

        PickupViewHolder(@NonNull View itemView) {
            super(itemView);
            pickupView = itemView;
            pickupTime = pickupView.findViewById(R.id.textPickupTime);
            pickupTeacher = pickupView.findViewById(R.id.textPickupTeacher);
        }
    }

    /**
     * 送孩子记录ViewHolder
     */
    class SendViewHolder extends RecyclerView.ViewHolder {

        View pickupView;
        TextView pickupTime;
        TextView pickupTeacher;

        SendViewHolder(@NonNull View itemView) {
            super(itemView);
            pickupView = itemView;
            pickupTime = pickupView.findViewById(R.id.textPickupTime);
            pickupTeacher = pickupView.findViewById(R.id.textPickupTeacher);
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

    public void refreshData(List<PickupHistory> pickupHistoryList) {
        mPickupHistoryList.clear();
        mPickupHistoryList = pickupHistoryList;
        notifyDataSetChanged();
        BToast.success(mContext)
                .text(R.string.page_updated)
                .show();
    }
}
