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
 * 基于基本ListView的Adapter 例如setting界面
 */

public class LineXXXAdapter extends BaseAdapter {
	private Context context;
	private String[] strings;
	private int[] drawables;
	private String isOpen;

	public LineXXXAdapter(Context context, String[] strings, int[] drawables,
			String isOpen) {
		this.context = context;
		this.strings = strings;
		this.drawables = drawables;
		this.isOpen = isOpen;
	}
	
	public void setStatus(String isOpen){
		this.isOpen = isOpen;
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(context).inflate(R.layout.item_user_center, null);
		
		ImageView ivIcon = (ImageView) convertView.findViewById(R.id.account_iv_icon_left);
		TextView tvText = (TextView) convertView.findViewById(R.id.account_tv_text);

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
