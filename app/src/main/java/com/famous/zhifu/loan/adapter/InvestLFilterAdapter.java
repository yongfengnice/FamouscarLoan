package com.famous.zhifu.loan.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.entity.BdzlItem;
import com.famous.zhifu.loan.holder.InvestViewHolder;
import com.famous.zhifu.loan.view.dialog.SelectFilterPopup;

public class InvestLFilterAdapter extends BaseAdapter{
	
	private Activity activity;
	private CompoundButton cbButton = null;
	private String key;
	private List<BdzlItem> arrays;
	private LayoutInflater inflater;
	private SelectFilterPopup popup = null; 
	
	
	public InvestLFilterAdapter(Activity activity,CompoundButton cbButton,String key,List<BdzlItem> list,SelectFilterPopup popup){
		this.activity = activity;
		
		this.cbButton = cbButton;
		this.key = key;
		this.arrays = list;
		this.popup = popup;
		
		inflater = LayoutInflater.from(activity);
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
		InvestViewHolder viewHolder = null;
		if (convertView == null) {
			
			viewHolder = new InvestViewHolder();
			
			convertView = inflater.inflate(R.layout.item_invest_filter, null);
			
			viewHolder.setCbButton(cbButton);
			viewHolder.setKey(key);
			viewHolder.setPop(popup);
			
			viewHolder.setLabel((TextView) convertView.findViewById(R.id.item_invest_filter_label)) ;
			viewHolder.setGouView((ImageView) convertView.findViewById(R.id.item_invest_filter_gou));
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (InvestViewHolder) convertView.getTag();
		}

		BdzlItem info = arrays.get(position);
		
		if (info.getIspress()) {
			viewHolder.getGouView().setVisibility(View.VISIBLE);
			
			viewHolder.getLabel().setTextColor(activity.getResources().getColor(R.color.color_coffee));
		}else {
			viewHolder.getGouView().setVisibility(View.GONE);
			
			viewHolder.getLabel().setTextColor(activity.getResources().getColor(R.color.textcolor_light_black));
		}
		
		viewHolder.setPosition(position);
		viewHolder.setName(info.getName());
		viewHolder.getLabel().setText(info.getName());
		
		return convertView;
	}
	
}
