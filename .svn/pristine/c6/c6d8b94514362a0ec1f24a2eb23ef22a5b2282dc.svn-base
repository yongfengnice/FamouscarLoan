package com.famous.zhifu.loan.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.entity.HkygInfo;

public class HkygAdapter extends BaseAdapter {
	private ArrayList<HkygInfo> arrays = null;
	private Context context;
	private LayoutInflater inflater;

	public HkygAdapter(ArrayList<HkygInfo> arrays, Context context) {
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
			convertView = inflater.inflate(R.layout.hkyg_list_item, null);
			viewHolder.title = (TextView) convertView.findViewById(R.id.title);
			viewHolder.date = (TextView) convertView.findViewById(R.id.date);
			viewHolder.principal = (TextView) convertView
					.findViewById(R.id.current_principal);
			viewHolder.totalIncome = (TextView) convertView
					.findViewById(R.id.total_income);
			viewHolder.interest = (TextView) convertView
					.findViewById(R.id.current_interest);
			viewHolder.issue = (TextView) convertView
					.findViewById(R.id.total_issue);
			viewHolder.nIssue = (TextView) convertView.findViewById(R.id.nqhk);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		HkygInfo tenderInfo = arrays.get(position);

		viewHolder.title.setText(tenderInfo.getTitle());
		viewHolder.date.setText(tenderInfo.getRepaymenttime());
		viewHolder.principal.setText("￥ " + tenderInfo.getTendermoney());
		viewHolder.interest.setText("￥ " + tenderInfo.getRatemoney());
		viewHolder.issue.setText("共" + tenderInfo.getLoanmonth() + "期");
		viewHolder.nIssue.setText("第" + tenderInfo.getAllot_periods() + "期还款");
		viewHolder.totalIncome.setText("￥ " + tenderInfo.getMoney());
		return convertView;
	}

	static class ViewHolder {
		TextView title;
		TextView date;
		TextView principal;
		TextView totalIncome;
		TextView interest;
		TextView issue;
		TextView nIssue;
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}

}
