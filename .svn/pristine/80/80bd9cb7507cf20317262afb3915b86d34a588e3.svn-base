package com.famous.zhifu.loan.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.entity.ProblemInfo;

/**
 *  常见问题的Adapter
 */

public class ProblemAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<ProblemInfo> lists;
	private LayoutInflater inflater;

	public ProblemAdapter(Context context, ArrayList<ProblemInfo> lists) {
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
			convertView = inflater.inflate(R.layout.usually_problem_item, null);
			viewHolder.title = (TextView) convertView
					.findViewById(R.id.usually_problem_item_tv);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		ProblemInfo problemInfo = lists.get(position);
		viewHolder.title.setText(problemInfo.getTitle());

		return convertView;
	}

	static class ViewHolder {
		TextView title;
		TextView content;
	}

}
