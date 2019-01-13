package com.famous.zhifu.loan.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.entity.RecordInfo;

public class RecordAdapter extends BaseAdapter {
	private ArrayList<RecordInfo> arrays = null;
	private Context context;
	private LayoutInflater inflater;

	public RecordAdapter(ArrayList<RecordInfo> arrays, Context context) {
		this.arrays = arrays;
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return arrays.size();
	}

	@Override
	public Object getItem(int position) {
		return arrays.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.record_item, null);
			viewHolder.userName = (TextView) convertView.findViewById(R.id.userName);
			viewHolder.num = (TextView) convertView.findViewById(R.id.num);
			viewHolder.date = (TextView) convertView.findViewById(R.id.date);
			viewHolder.type = (TextView) convertView.findViewById(R.id.type);
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		RecordInfo info = arrays.get(position);
		viewHolder.userName.setText(info.getUsername());
		viewHolder.num.setText(info.getMoney());
		viewHolder.date.setText(info.getTimeadd());
		if (info.getType().equals("1")) {
			viewHolder.type.setText("网页");
		} else if (info.getType().equals("2")) {
			viewHolder.type.setText("自动投标");
		} else if (info.getType().equals("3")) {
			viewHolder.type.setText("android手机");
		} else if (info.getType().equals("4")) {
			viewHolder.type.setText("苹果");
		} else if (info.getType().equals("5")) {
			viewHolder.type.setText("移动浏览器");
		} else if (info.getType().equals("6")) {
			viewHolder.type.setText("微信");
		} else if (info.getType().equals("7")) {
			viewHolder.type.setText("短信");
		}
		return convertView;
	}

	static class ViewHolder {
		TextView userName;
		TextView num;
		TextView date;
		TextView type;
	}

}
