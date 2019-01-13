package com.famous.zhifu.loan.famouscarloan.investlist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.activity.Consts;
import com.famous.zhifu.loan.entity.BdzlItem;
import com.famous.zhifu.loan.internet.ServiceClient;

public class SearchLcxm extends Activity implements OnClickListener {
	private TextView tv_quxiao, tv_qieding;
	private ListView listView_bdfl;
	private ListView listView_nsyl;
	private ListView listView_jkqx;
	private List<BdzlItem> ptypeArray = new ArrayList<BdzlItem>();
	private List<BdzlItem> annualearningArray = new ArrayList<BdzlItem>();
	private List<BdzlItem> loanperiodArray = new ArrayList<BdzlItem>();
	private MyAdapter adapter;
	private MyAdapter1 adapter1;
	private MyAdapter2 adapter2;

	private int loanstatus; // 标的种类状态
	private int yearrate; // 收益率的状态
	private int loanmonth; // 借款期限的状态

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchlcxm);
		initdate();
		initview();
		dosometthing();
	}

	private void initdate() {
		Intent in = getIntent();
		loanstatus = in.getExtras().getInt("loanstatus");
		yearrate = in.getExtras().getInt("yearrate");
		loanmonth = in.getExtras().getInt("loanmonth");
	}

	private void dosometthing() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", "condition");
		ServiceClient.call(Consts.NetWork.DDBLB, params, callback);
	}

	ServiceClient.Callback callback = new ServiceClient.Callback() {

		@Override
		public void callback(JSONObject data) {
			try {
				if (data.getInt("errorcode") == 0) {
					
					// Log.i("条件", data.toString());
					
					if (!TextUtils.isEmpty(data.get("dataresult").toString())) {
						JSONObject json = data.getJSONObject("dataresult");
						JSONObject datajson = json.getJSONObject("data");
						JSONArray arrayloanmonth = datajson.getJSONArray("loanmonth");
						JSONArray arrayyearrate = datajson.getJSONArray("yearrate");
						JSONArray arrayloanstatus = datajson.getJSONArray("loanstatus");
						
						for (int i = 0; i < arrayloanstatus.length(); i++) {
							BdzlItem item = new BdzlItem();
							if (i == loanstatus) {
								item.setIspress(true);
							} else {
								item.setIspress(false);
							}
							item.setName(arrayloanstatus.getString(i) + "");
							
							ptypeArray.add(item);
						}

						adapter.notifyDataSetChanged();
						
						for (int i = 0; i < arrayyearrate.length(); i++) {

							BdzlItem item = new BdzlItem();
							if (i == yearrate) {
								item.setIspress(true);
							} else {
								item.setIspress(false);
							}
							item.setName(arrayyearrate.getString(i) + "");
							annualearningArray.add(item);
						}
						adapter1.notifyDataSetChanged();
						for (int i = 0; i < arrayloanmonth.length(); i++) {
							BdzlItem item = new BdzlItem();
							if (i == loanmonth) {
								item.setIspress(true);
							} else {
								item.setIspress(false);
							}
							item.setName(arrayloanmonth.getString(i) + "");
							loanperiodArray.add(item);
						}
						adapter2.notifyDataSetChanged();
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	};

	private void initview() {
		tv_quxiao = (TextView) findViewById(R.id.tv_quxiao);
		tv_quxiao.setOnClickListener(this);
		tv_qieding = (TextView) findViewById(R.id.tv_qieding);
		tv_qieding.setOnClickListener(this);

		adapter = new MyAdapter();
		listView_bdfl = (ListView) findViewById(R.id.listView_bdfl);
		listView_bdfl.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				loanstatus = position;
				for (int i = 0, j = ptypeArray.size(); i < j; i++) {
					if (i == position) {
						ptypeArray.get(i).setIspress(true);
					} else {
						ptypeArray.get(i).setIspress(false);
					}
				}
				adapter.notifyDataSetChanged();
			}
		});
		listView_bdfl.setDividerHeight(0);
		listView_bdfl.setAdapter(adapter);

		adapter1 = new MyAdapter1();
		listView_nsyl = (ListView) findViewById(R.id.listView_nsyl);
		listView_nsyl.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				yearrate = position;
				for (int i = 0, j = annualearningArray.size(); i < j; i++) {
					if (i == position) {
						annualearningArray.get(i).setIspress(true);
					} else {
						annualearningArray.get(i).setIspress(false);
					}
				}
				adapter1.notifyDataSetChanged();
			}
		});
		listView_nsyl.setAdapter(adapter1);
		listView_nsyl.setDividerHeight(0);

		adapter2 = new MyAdapter2();
		listView_jkqx = (ListView) findViewById(R.id.listView_jkqx);
		listView_jkqx.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				loanmonth = position;
				for (int i = 0, j = loanperiodArray.size(); i < j; i++) {
					if (i == position) {
						loanperiodArray.get(i).setIspress(true);
					} else {
						loanperiodArray.get(i).setIspress(false);
					}
				}
				adapter2.notifyDataSetChanged();
			}
		});

		listView_jkqx.setDividerHeight(0);
		listView_jkqx.setAdapter(adapter2);
	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return ptypeArray.size();
		}

		@Override
		public Object getItem(int position) {
			return ptypeArray.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public boolean isEnabled(int position) {
			return super.isEnabled(position);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.addexam_list_item, null);
				viewHolder.tv_fenlei = (TextView) convertView
						.findViewById(R.id.tv_fenlei);
				viewHolder.iv_fenlei = (ImageView) convertView
						.findViewById(R.id.iv_fenlei);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			BdzlItem item = ptypeArray.get(position);
			if (item.getIspress()) {
				viewHolder.iv_fenlei.setVisibility(View.VISIBLE);
			} else {
				viewHolder.iv_fenlei.setVisibility(View.GONE);
			}
			viewHolder.tv_fenlei.setText(item.getName());
			return convertView;
		}

	}

	class MyAdapter1 extends BaseAdapter {

		@Override
		public int getCount() {
			return annualearningArray.size();
		}

		@Override
		public Object getItem(int position) {
			return annualearningArray.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public boolean isEnabled(int position) {
			return super.isEnabled(position);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if (convertView == null) {
				viewHolder = new ViewHolder();

				convertView = LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.addexam_list_item, null);
				viewHolder.tv_fenlei = (TextView) convertView
						.findViewById(R.id.tv_fenlei);
				viewHolder.iv_fenlei = (ImageView) convertView
						.findViewById(R.id.iv_fenlei);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			BdzlItem item = annualearningArray.get(position);
			if (item.getIspress()) {
				viewHolder.iv_fenlei.setVisibility(View.VISIBLE);
			} else {
				viewHolder.iv_fenlei.setVisibility(View.GONE);
			}

			viewHolder.tv_fenlei.setText(item.getName());
			return convertView;
		}

	}

	class MyAdapter2 extends BaseAdapter {

		@Override
		public int getCount() {
			return loanperiodArray.size();
		}

		@Override
		public Object getItem(int position) {
			return loanperiodArray.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public boolean isEnabled(int position) {
			return super.isEnabled(position);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.addexam_list_item, null);
				viewHolder.tv_fenlei = (TextView) convertView
						.findViewById(R.id.tv_fenlei);
				viewHolder.iv_fenlei = (ImageView) convertView
						.findViewById(R.id.iv_fenlei);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			BdzlItem item = loanperiodArray.get(position);
			if (item.getIspress()) {
				viewHolder.iv_fenlei.setVisibility(View.VISIBLE);
			} else {
				viewHolder.iv_fenlei.setVisibility(View.GONE);
			}
			viewHolder.tv_fenlei.setText(item.getName());
			return convertView;
		}

	}

	static class ViewHolder {
		TextView tv_fenlei;
		ImageView iv_fenlei;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_quxiao:
			finish();
			break;
		case R.id.tv_qieding:
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putInt("loanstatus", loanstatus);
			bundle.putInt("yearrate", yearrate);
			bundle.putInt("loanmonth", loanmonth);
			intent.putExtras(bundle);
			setResult(888, intent);
			finish();
			break;
		default:
			break;
		}

	}
}
