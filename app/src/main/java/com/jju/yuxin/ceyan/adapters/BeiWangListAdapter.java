package com.jju.yuxin.ceyan.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jju.yuxin.ceyan.R;
import com.jju.yuxin.ceyan.domain.BeiWang;

import java.util.List;

/**
 * =============================================================================
 * Copyright (c) 2016 yuxin All rights reserved.
 * Packname com.jju.yuxin.disizhou
 * Created by yuxin.
 * Created time 2016/9/26 0026 上午 10:43.
 * Version   1.0;
 * Describe :  备忘录列表的适配器
 * History:
 * ==============================================================================
 */

public class BeiWangListAdapter extends BaseAdapter {
    private List<BeiWang> beiWangs;
    private Context context;
    private LayoutInflater layoutInflater;
    private TextView tvitemid;
    private TextView tvitemtitle;
    private TextView tvitemcontent;
    private TextView tvitemdate;

    public BeiWangListAdapter(List<BeiWang> beiWangs, Context context) {
        this.beiWangs=beiWangs;
        this.context=context;
        layoutInflater=LayoutInflater.from(context);

    }

    public void remove(int position) {
         beiWangs.remove(position);
    }

    @Override
    public int getCount() {
        return beiWangs.size();
    }

    @Override
    public Object getItem(int position) {
        return beiWangs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.layout_item, null);
            viewHolder.tv_id = (TextView) convertView.findViewById(R.id.tv_item_id);
            viewHolder.tv_title= (TextView) convertView.findViewById(R.id.tv_item_title);
            viewHolder.tv_context= (TextView) convertView.findViewById(R.id.tv_item_content);
            viewHolder.tv_data= (TextView) convertView.findViewById(R.id.tv_item_date);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_id.setText(beiWangs.get(position).getId()+"");
        viewHolder.tv_title.setText(beiWangs.get(position).getTitle()+"");
        viewHolder.tv_context.setText(beiWangs.get(position).getContent()+"");
        viewHolder.tv_data.setText(beiWangs.get(position).getDate()+"");
        return convertView;
    }



    private class ViewHolder{
        TextView tv_id;
        TextView tv_title;
        TextView tv_context;
        TextView tv_data;
    }
}
