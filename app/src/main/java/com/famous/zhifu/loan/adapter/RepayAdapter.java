package com.famous.zhifu.loan.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.entity.RepayInfo;
import com.famous.zhifu.loan.util.Tools;

public class RepayAdapter extends BaseAdapter {
	private ArrayList<RepayInfo> arrays = null;
	private Context context;
	private LayoutInflater inflater;

	public RepayAdapter(ArrayList<RepayInfo> arrays, Context context) {
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
			convertView = inflater.inflate(R.layout.repay_item, null);
			viewHolder.qi = (TextView) convertView.findViewById(R.id.qi);
			viewHolder.num = (TextView) convertView.findViewById(R.id.num);
			viewHolder.date = (TextView) convertView.findViewById(R.id.date);
			viewHolder.status = (TextView) convertView
					.findViewById(R.id.status);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		RepayInfo info = arrays.get(position);
		viewHolder.qi.setText(position + 1 + "期");
		viewHolder.num.setText(info.getNum() + "元");
		viewHolder.date.setText("还款截止日:" + info.getDate());
		String status = info.getStatus();
		if (Tools.isEquals(status, "0")) {
			viewHolder.status.setText("待还款");
		} else if (Tools.isEquals(status, "1")) {
			viewHolder.status.setText("已还款");
		} else if (Tools.isEquals(status, "2")) {
			viewHolder.status.setText("还款中");
		}
		return convertView;
	}

	static class ViewHolder {
		TextView qi;
		TextView num;
		TextView date;
		TextView status;
	}

}
