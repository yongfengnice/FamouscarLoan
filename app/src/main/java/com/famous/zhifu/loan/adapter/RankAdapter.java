package com.famous.zhifu.loan.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.entity.RankInfo;

public class RankAdapter extends BaseAdapter {
	private ArrayList<RankInfo> arrays = null;
	private Context context;
	private LayoutInflater inflater;

	public RankAdapter(ArrayList<RankInfo> arrays, Context context) {
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
			convertView = inflater.inflate(R.layout.phb_item, null);
			viewHolder.tv_integral = (TextView) convertView
					.findViewById(R.id.tv_integral);
			viewHolder.tv_username = (TextView) convertView
					.findViewById(R.id.tv_username);

			viewHolder.bt_phb = (Button) convertView.findViewById(R.id.bt_phb);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		RankInfo info = arrays.get(position);

		viewHolder.bt_phb.setText(position + 1 + "");
		viewHolder.tv_username.setText(info.getUserName());
		viewHolder.tv_integral.setText(info.getNum()+"元");
		return convertView;
	}

	static class ViewHolder {
		TextView tv_integral;
		TextView tv_username;
		Button bt_phb;
	}

}
