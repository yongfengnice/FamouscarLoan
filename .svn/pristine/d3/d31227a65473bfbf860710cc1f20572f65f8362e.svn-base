package com.famous.zhifu.loan.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.entity.HeInfo;
import com.famous.zhifu.loan.util.Tools;

public class HeAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private ArrayList<HeInfo> arrays;

	public HeAdapter(Context context, ArrayList<HeInfo> arrays) {
		this.context = context;
		this.arrays = arrays;
		inflater = inflater.from(context);
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
		ViewHolder viewHolder;
		if (convertView != null) {
			viewHolder = (ViewHolder) convertView.getTag();
		} else {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.he_item, null);
			viewHolder.time = (TextView) convertView.findViewById(R.id.time);
			viewHolder.num = (TextView) convertView.findViewById(R.id.num);
			viewHolder.status = (TextView) convertView
					.findViewById(R.id.status);
			convertView.setTag(viewHolder);
		}

		HeInfo info = arrays.get(position);
		viewHolder.time.setText(info.getLasttime());
		viewHolder.num.setText(info.getMoney());
		String status = info.getStatus();
		if (Tools.isEquals(status, "0")) {
			viewHolder.status.setText("未还款");
		} else if (Tools.isEquals(status, "1")) {
			viewHolder.status.setText("已还款");
		}

		return convertView;
	}

	static class ViewHolder {
		TextView time;
		TextView num;
		TextView status;
	}

}
