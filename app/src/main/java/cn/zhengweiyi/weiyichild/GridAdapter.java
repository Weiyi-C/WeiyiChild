/*
 * Copyright (c) 2019. zhengweiyi.cn all rights reserved
 * 郑维一版权所有，未经授权禁止使用，开源项目请遵守指定的开源协议
 */

package cn.zhengweiyi.weiyichild;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.zhengweiyi.weiyichild.bean.GridBean;

public class GridAdapter extends BaseAdapter {

    private List<GridBean> gridDataList;
    private Context mContext;

    public GridAdapter(List<GridBean> gridDataList, Context context) {
        this.gridDataList = gridDataList;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return gridDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return gridDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            // 加载子布局
            convertView = LayoutInflater.from(mContext).inflate(R.layout.gridview_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = convertView.findViewById(R.id.gridImage);
            viewHolder.textView = convertView.findViewById(R.id.gridText);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.imageView.setImageResource(gridDataList.get(position).getIconId());
        viewHolder.imageView.setContentDescription(gridDataList.get(position).getName());
        viewHolder.textView.setText(gridDataList.get(position).getName());
        return convertView;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
