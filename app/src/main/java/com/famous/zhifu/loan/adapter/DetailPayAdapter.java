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
import com.famous.zhifu.loan.entity.DetailPayTypeInfo;

/**
 *  常见问题的Adapter
 */

public class DetailPayAdapter extends BaseAdapter {
	private Context context;
	private List<DetailPayTypeInfo> lists;
	
	private LayoutInflater inflater;

	public DetailPayAdapter(Context context, List<DetailPayTypeInfo> lists) {
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
			convertView = inflater.inflate(R.layout.item_item_invest_detail_repay, null);
			
			viewHolder.numView = (TextView) convertView.findViewById(R.id.item_item_invest_detail_repay_num);
			viewHolder.benxiView = (TextView) convertView.findViewById(R.id.item_item_invest_detail_repay_benxi);
			viewHolder.benjinView = (TextView) convertView.findViewById(R.id.item_item_invest_detail_repay_benjin);
			viewHolder.timeView = (TextView) convertView.findViewById(R.id.item_item_invest_detail_repay_time);
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		DetailPayTypeInfo info = lists.get(position);
		
		viewHolder.numView.setText(position+"");
		viewHolder.benxiView.setText(info.getMoney());
		viewHolder.benjinView.setText(info.getResiduemoney());
		viewHolder.timeView.setText(info.getEndtime());

		return convertView;
	}
	

	static class ViewHolder {
		TextView numView;
		TextView benxiView;
		TextView benjinView;
		TextView timeView;
	}

}
