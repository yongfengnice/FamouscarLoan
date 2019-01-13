package com.famous.zhifu.loan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.famous.zhifu.loan.R;

/**
 * 基于基本ListView的Adapter    例如setting界面
 */

public class LineAdapter extends BaseAdapter {
    private Context context;
    private String[] strings;
    private int[] drawables;
    private String[] stringOthers;


    public LineAdapter(Context context, String[] strings, int[] drawables, String[] stringOthers) {
        this.context = context;
        this.strings = strings;
        this.drawables = drawables;
        this.stringOthers = stringOthers;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.account_item, null);
        ImageView ivIcon = (ImageView) convertView.findViewById(R.id.account_iv_icon_left);
        TextView tvText = (TextView) convertView.findViewById(R.id.account_tv_text);
//        TextView tvTextTWo = (TextView) convertView.findViewById(R.id.account_tv_text_two);
        if(null!=stringOthers&&stringOthers.length>0){
          /*  tvTextTWo.setVisibility(View.VISIBLE);
            tvTextTWo.setText(stringOthers[position]);*/
        }else {
//            tvTextTWo.setVisibility(View.GONE);
        }
        
        if (null != strings && strings.length > 0) {
            ivIcon.setImageResource(drawables[position]);
            tvText.setText(strings[position]);
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return strings.length;
    }

    @Override
    public Object getItem(int position) {
        return strings[position];
    }
}
