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

public class GridAdapter extends BaseAdapter {

    private List<GridBean> gridDataList;
    private Context context;

    GridAdapter(List<GridBean> gridDataList, Context context) {
        this.gridDataList = gridDataList;
        this.context = context;
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
        View view;
        ViewHolder viewHolder;
        if (convertView == null ) {
            // 加载子布局
            view = LayoutInflater.from(context).inflate(R.layout.gridview_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = view.findViewById(R.id.gridImage);
            viewHolder.textView = view.findViewById(R.id.gridText);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.imageView.setImageResource(gridDataList.get(position).getIconId());
        viewHolder.textView.setText(gridDataList.get(position).getName());
        return view;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
