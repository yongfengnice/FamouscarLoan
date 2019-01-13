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
 * GridView Adapter
 */
public class LoanGridAdapter extends BaseAdapter {
    private Context context;
    private String[] strings;
    private int[] drawables;

    public LoanGridAdapter(Context context, String[] strings, int[] drawables) {
        this.context = context;
        this.strings = strings;
        this.drawables = drawables;
    }

    @Override
    public int getCount() {
        return strings.length;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.loan_money_confirm_item,null);
        ImageView ivIcon = (ImageView)convertView.findViewById(R.id.loan_money_confirm_item_icon);
        TextView tvText = (TextView)convertView.findViewById(R.id.loan_money_confirm_item_text);
        if(null!=strings&&strings.length>0){
            ivIcon.setImageResource(drawables[position]);
            tvText.setText(strings[position]);
        }
        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return strings[position];
    }
}
