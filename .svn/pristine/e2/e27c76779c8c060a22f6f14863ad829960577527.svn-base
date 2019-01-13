package com.famous.zhifu.loan.adapter;

import java.util.ArrayList;

import net.tsz.afinal.FinalBitmap;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.entity.CarInfo;
import com.famous.zhifu.loan.parse.MapRelation;

public class InvestAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private ArrayList<CarInfo> arrays;
	private int screenHeight;
	private FinalBitmap fb;
	private String lastType = null;

	public InvestAdapter(Context context, ArrayList<CarInfo> arrays,
			int screenHeight, FinalBitmap fb) {
		this.context = context;
		this.arrays = arrays;
		this.screenHeight = screenHeight;
		this.fb = fb;
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
		CarInfo carInfo = arrays.get(position);
		ViewHolder viewHolder = null;
		if (convertView == null||lastType==null||(!carInfo.getType().equals(lastType))) {
			lastType = carInfo.getType();
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_item_home, null);
//			Log.i("sam", "车子类型："+ carInfo==null?"":carInfo.getType()==null?"":carInfo.getType());
//			if(carInfo==null?false:carInfo.getType()==null?false:new Integer(carInfo.getType())==2){
//				convertView = inflater.inflate(R.layout.item_item_home_red, null);
//			}
			
			viewHolder.tuijian = (ImageView) convertView.findViewById(R.id.item_item_home_tuijian);
			viewHolder.isComplete = (ImageView) convertView.findViewById(R.id.item_item_home_complete);
			
			viewHolder.title = (TextView) convertView.findViewById(R.id.item_item_label);
			viewHolder.loanmoney = (TextView) convertView.findViewById(R.id.item_item_home_jine);
			viewHolder.loanmonth = (TextView) convertView.findViewById(R.id.item_item_home_qixian);
			viewHolder.payType = (TextView) convertView.findViewById(R.id.item_item_home_type);
			viewHolder.yearrate = (TextView) convertView.findViewById(R.id.item_item_home_lilv);
			
			viewHolder.bar = (ProgressBar) convertView.findViewById(R.id.item_item_home_progressbar);
			viewHolder.jindu = (TextView) convertView.findViewById(R.id.item_item_home_jindu);
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		
		viewHolder.title.setText(carInfo.getTitle());
		viewHolder.loanmoney.setText(carInfo.getLoanmoney() + " 元");
		viewHolder.loanmonth.setText(carInfo.getLoanmonth() + "个月");
		viewHolder.payType.setText(MapRelation.REPAYMENT_MAP.get(carInfo.getRepayment()));
		viewHolder.yearrate.setText(carInfo.getYearrate() + " ％");
		
		if (carInfo.getRatio().endsWith("100")) {
			viewHolder.isComplete.setVisibility(View.VISIBLE);
		}else {
			viewHolder.isComplete.setVisibility(View.GONE);
		}
		
		int jindu = Integer.valueOf(carInfo.getRatio());
		
		if (jindu==100) {
			viewHolder.bar.setProgressDrawable(context.getResources().getDrawable(R.drawable.self_define_progress_complete));
		}else {
			viewHolder.bar.setProgressDrawable(context.getResources().getDrawable(R.drawable.self_define_progress_uncomplete));
		}
		
		viewHolder.bar.setProgress(jindu);
		viewHolder.jindu.setText("完成"+carInfo.getRatio()+"%");
		
		return convertView;
	}

	static class ViewHolder {
		ImageView tuijian;
		ImageView isComplete;
		
		TextView title;
		TextView loanmoney;
		TextView loanmonth;
		TextView payType;
		TextView yearrate;
		
		ProgressBar bar;
		TextView jindu;
	}
}
