package com.famous.zhifu.loan.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.entity.ZjlsInfo;

public class ZjlsAdapter extends BaseAdapter {
	private ArrayList<ZjlsInfo> arrays = null;
	private Context context;
	private LayoutInflater inflater;

	public ZjlsAdapter(ArrayList<ZjlsInfo> arrays, Context context) {
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
			convertView = inflater.inflate(R.layout.item_account_zjjl, null);
			
			viewHolder.name = (TextView) convertView.findViewById(R.id.item_account_zjjl_type);
			viewHolder.time = (TextView) convertView.findViewById(R.id.item_account_zjjl_time);
			viewHolder.num = (TextView) convertView.findViewById(R.id.item_account_zjjl_jine);
			viewHolder.id = (TextView) convertView.findViewById(R.id.item_account_zjjl_state);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		ZjlsInfo info = arrays.get(position);
		
		viewHolder.name.setText(info.getName());
		viewHolder.id.setText(info.getState());
		
		String timeString = info.getDate();
		viewHolder.time.setText(timeString.substring(0, timeString.length()-3));
		viewHolder.num.setText(info.getAmount());

		// String codeid = info.getCodeid();
		// if (Tools.isEquals(codeid, "1")) {// 充值
		// viewHolder.name.setTextColor(color)
		// } else if (Tools.isEquals(codeid, "2")) {//提现
		//
		// }else if (Tools.isEquals(codeid, "3")) {//投标
		//
		// }

		return convertView;
	}

	static class ViewHolder {
		TextView name;
		TextView time;
		TextView num;
		TextView id;
	}

}
