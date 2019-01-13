package com.famous.zhifu.loan.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.entity.DetailTzjlInfo;

/**
 *  常见问题的Adapter
 */

public class DetailTzjlAdapter extends BaseAdapter {
	private Context context;
	private List<DetailTzjlInfo> lists;
	
	private LayoutInflater inflater;

	public DetailTzjlAdapter(Context context, List<DetailTzjlInfo> lists) {
		this.context = context;
		this.lists = lists;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return lists.size();
	}

	@Override
	public Object getItem(int position) {
		return lists.get(position);
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
			convertView = inflater.inflate(R.layout.item_item_invest_detail_record, null);
			
			viewHolder.renView = (TextView) convertView.findViewById(R.id.item_item_invest_record_ren);
			viewHolder.imgBorder = (LinearLayout) convertView.findViewById(R.id.item_item_invest_record_level);
			viewHolder.jineView = (TextView) convertView.findViewById(R.id.item_item_invest_record_jine);
			viewHolder.timeView = (TextView) convertView.findViewById(R.id.item_item_invest_record_time);
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		DetailTzjlInfo info = lists.get(position);
		
		viewHolder.renView.setText(info.getUsername());
		viewHolder.jineView.setText(info.getMoney());
		viewHolder.timeView.setText(info.getTimeadd());
		
		setLevel(Integer.valueOf(info.getIntegral()),viewHolder.imgBorder);

		return convertView;
	}
	
	private void setLevel(int num,LinearLayout liLayout){
		boolean flag = false;	//金银的标志位
		
		int temp = 0;
		
		if (num>=10000) {
			flag = true;
			
			temp = temp/10000 >5 ? 5:temp/10000;
		}else {
			flag = true;
			
			if (num>8000) {
				temp = 5;
			}else if (num>6000) {
				temp = 4;
			}else if (num>4000) {
				temp = 3;
			}else if (num>2000) {
				temp = 2;
			}else {
				temp = 2;
			}
		}
		
		addTubiao(flag,temp,liLayout);
		
	}
	
	private void addTubiao(boolean flag,int num,LinearLayout liLayout){
		if (flag) {	//金色
			for (int i = 0; i < num; i++) {
				ImageView imageView = new ImageView(context);
				
				imageView.setPadding(0, 0, 5, 0);
				imageView.setImageResource(R.drawable.tou_zi_jinse);
				
				liLayout.addView(imageView);
			}
		}else {
			for (int i = 0; i < num; i++) {
				ImageView imageView = new ImageView(context);
				
				imageView.setPadding(0, 0, 5, 0);
				imageView.setImageResource(R.drawable.tou_zi_jinse);
				
				liLayout.addView(imageView);
			}
		}
	}

	static class ViewHolder {
		TextView renView;
		LinearLayout imgBorder;
		TextView jineView;
		TextView timeView;
	}

}
