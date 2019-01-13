package com.famous.zhifu.loan.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.entity.NoticeInfo;

/**
 *  新闻资讯Adapter
 */
public class NewsAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private ArrayList<NoticeInfo> arrays;

	public NewsAdapter(Context context, ArrayList<NoticeInfo> arrays) {
		this.context = context;
		this.arrays = arrays;
		this.inflater = LayoutInflater.from(context);
		inflater = LayoutInflater.from(context);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public Object getItem(int position) {
		return arrays.get(position);
	}

	@Override
	public int getCount() {
		return arrays.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.news_item, null);
			viewHolder.title = (TextView) convertView.findViewById(R.id.title);
			viewHolder.time = (TextView) convertView.findViewById(R.id.time);
			viewHolder.desc = (TextView) convertView.findViewById(R.id.item_news_desc);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		NoticeInfo noticeInfo = arrays.get(position);
		viewHolder.title.setText(noticeInfo.getTitle());
		
		String timeString = noticeInfo.getAdd_time();
		viewHolder.time.setText(timeString.substring(5, 10));
		viewHolder.desc.setText(noticeInfo.getDescription());

		return convertView;
	}

	static class ViewHolder {
		TextView title;
		TextView time;
		TextView desc;
	}
}
