package com.famous.zhifu.loan.adapter;

import java.util.ArrayList;

import net.tsz.afinal.FinalBitmap;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.entity.TemporaryInfo;

public class GirdAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private ArrayList<TemporaryInfo> arrays;
	private FinalBitmap fb;

	public GirdAdapter(Context context, ArrayList<TemporaryInfo> arrays,
			FinalBitmap fb) {
		this.context = context;
		this.arrays = arrays;
		this.fb = fb;
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
			convertView = inflater.inflate(R.layout.picture_item, null);
			viewHolder.name = (TextView) convertView.findViewById(R.id.name);
			viewHolder.img = (ImageView) convertView.findViewById(R.id.img);
			convertView.setTag(viewHolder);
		}

		TemporaryInfo info = arrays.get(position);
		viewHolder.name.setText(info.getName());
		fb.display(viewHolder.img, info.getSmallUrl());
		return convertView;
	}

	static class ViewHolder {
		TextView name;
		ImageView img;
	}

}
