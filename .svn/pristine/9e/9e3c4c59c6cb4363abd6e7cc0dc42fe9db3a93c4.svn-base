package com.famous.zhifu.loan.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.entity.TzjlInfo;
import com.famous.zhifu.loan.util.Tools;

public class TzjlAdapter extends BaseAdapter {
	private ArrayList<TzjlInfo> arrays = null;
	private Context context;
	private LayoutInflater inflater;
	private Resources resources;

	public TzjlAdapter(ArrayList<TzjlInfo> arrays, Context context) {
		this.arrays = arrays;
		this.context = context;
		inflater = LayoutInflater.from(context);
		resources = context.getResources();
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
			
			viewHolder.date = (TextView) convertView.findViewById(R.id.item_account_zjjl_time);
			viewHolder.num = (TextView) convertView.findViewById(R.id.item_account_zjjl_jine);
			viewHolder.interest = (TextView) convertView.findViewById(R.id.item_account_zjjl_type);
			viewHolder.type = (TextView) convertView.findViewById(R.id.item_account_zjjl_state);
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		TzjlInfo info = arrays.get(position);
		
		String timeString = info.getTimeadd();
		viewHolder.date.setText(timeString.substring(0, timeString.length()-3));
		viewHolder.num.setText(info.getMoney());
		viewHolder.interest.setText(info.getEnddate());
		
		String typeStr = "";
		
		if (Tools.isEquals(info.getRepaymentStatus(), "逾期")) {
			viewHolder.type.setTextColor(resources.getColorStateList(R.color.textcolor_red));
		}else if(Tools.isEquals(info.getRepaymentStatus(), "已还款")){
			viewHolder.type.setTextColor(resources.getColorStateList(R.color.textcolor_black));
		}else {
			viewHolder.type.setTextColor(resources.getColorStateList(R.color.textcolor_green));
		}
		
		viewHolder.type.setText(info.getRepaymentStatus());
		/*viewHolder.title.setText(info.getTitle());
		viewHolder.issue.setText(info.getLoanmonth() + "期");
		viewHolder.nIssue.setText("已还" + info.getPaycount() + "期");*/
		return convertView;
	}

	static class ViewHolder {
		TextView date;		//时间
		TextView num;		//投资金额
		TextView interest;	//年化利率
		TextView type;		//还款方式
	}

}
