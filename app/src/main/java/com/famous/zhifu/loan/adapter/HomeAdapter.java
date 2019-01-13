package com.famous.zhifu.loan.adapter;

import java.util.ArrayList;

import android.R.raw;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.adapter.TzjlAdapter.ViewHolder;
import com.famous.zhifu.loan.entity.CarInfo;
import com.famous.zhifu.loan.entity.HomeInfo;
import com.famous.zhifu.loan.famouscarloan.investlist.InvestDetailActivity;
import com.famous.zhifu.loan.famouscarloan.investlist.InvestListActivity;
import com.famous.zhifu.loan.parse.MapRelation;
import com.famous.zhifu.loan.util.Tools;
import com.famous.zhifu.loan.view.circleprogress.CircleProgressBar;

public class HomeAdapter extends BaseAdapter implements OnClickListener {
	private Context context;
	private ArrayList<HomeInfo> homelists;
	private LayoutInflater inflater;
	private IHomeClickListener listener;

	private LayoutInflater childInflater;

	public HomeAdapter(Context context, ArrayList<HomeInfo> homelists) {
		this.context = context;
		this.homelists = homelists;
		inflater = LayoutInflater.from(context);

		childInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return homelists.size();
	}

	@Override
	public Object getItem(int position) {
		return homelists.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.home_item, null);

			viewHolder.radioGroup = (RadioGroup) convertView
					.findViewById(R.id.home_radio_button_group);
			viewHolder.layout = (LinearLayout) convertView
					.findViewById(R.id.home_tab_liLayout);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		HomeInfo homeInfo = homelists.get(position);
		final ArrayList<CarInfo> carlists = homeInfo.getCarlists();

		if (carlists != null) {
			fileData(viewHolder, carlists);
		}

		viewHolder.radioGroup
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup radioGroup, int id) {
						if (id == R.id.home_tab_car_btn) { // 车贷
							fileData(viewHolder, carlists);
						} else if (id == R.id.home_tab_xue_btn) { // 学贷
							fillNull(viewHolder);
						} else { // 房贷
							fillNull(viewHolder);
						}
					}
				});

		return convertView;
	}

	private void fileData(ViewHolder holder, ArrayList<CarInfo> carlists) {
		if (holder.layout.getChildCount() > 0) {
			holder.layout.removeAllViews();
		}

		for (int i = 0, j = carlists.size(); i < j; i++) {
			CarInfo info = carlists.get(i);
			View view = childInflater.inflate(R.layout.item_item_home, null);
//			if (new Integer(info.getType()) == 2) {
//				view = childInflater.inflate(R.layout.item_item_home_red, null);
//			}
			setOnClickHandler(view, info);

			TextView labelView = (TextView) view
					.findViewById(R.id.item_item_label);
			labelView.setText(info.getTitle());

			TextView jineView = (TextView) view
					.findViewById(R.id.item_item_home_jine);
			jineView.setText(info.getLoanmoney());

			TextView qixianView = (TextView) view
					.findViewById(R.id.item_item_home_qixian);
			qixianView.setText(info.getLoanmonth() + "个月");

			TextView typeView = (TextView) view
					.findViewById(R.id.item_item_home_type);
			typeView.setText(MapRelation.REPAYMENT_MAP.get(info.getRepayment()));

			TextView lilvView = (TextView) view
					.findViewById(R.id.item_item_home_lilv);
			lilvView.setText(info.getYearrate() + "%");

			ProgressBar bar = (ProgressBar) view
					.findViewById(R.id.item_item_home_progressbar);

			int jindu = Integer.valueOf(info.getRatio());

			if (jindu == 100) {
				bar.setProgressDrawable(context.getResources().getDrawable(
						R.drawable.self_define_progress_complete));
			} else {
				bar.setProgressDrawable(context.getResources().getDrawable(
						R.drawable.self_define_progress_uncomplete));
			}

			bar.setProgress(jindu);

			TextView jinduTextView = (TextView) view
					.findViewById(R.id.item_item_home_jindu);
			jinduTextView.setText("完成" + info.getRatio() + "%");

			holder.layout.addView(view);
		}

	}

	private void setOnClickHandler(View view, final CarInfo info) {
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, InvestDetailActivity.class);
				intent.putExtra("sn", info.getLoansn());

				context.startActivity(intent);
			}
		});

	}

	private void fillNull(ViewHolder holder) {
		if (holder.layout.getChildCount() > 0) {
			holder.layout.removeAllViews();
		}

		View view = childInflater.inflate(R.layout.item_home_null, null);

		holder.layout.addView(view);
	}

	public interface IHomeClickListener {
		/** 首页点击后的回调接口 */
		public void homeCallback(int flag);
	}

	public void setListener(IHomeClickListener listener) {
		this.listener = listener;
	}

	@Override
	public void onClick(View v) {

	}

	static class ViewHolder {
		RadioGroup radioGroup;
		LinearLayout layout;
	}

}
