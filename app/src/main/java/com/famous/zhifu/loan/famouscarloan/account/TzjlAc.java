package com.famous.zhifu.loan.famouscarloan.account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.activity.ActivityInterface;
import com.famous.zhifu.loan.activity.Consts;
import com.famous.zhifu.loan.adapter.TzjlAdapter;
import com.famous.zhifu.loan.entity.TzjlInfo;
import com.famous.zhifu.loan.internet.ServiceClient;
import com.famous.zhifu.loan.util.NetUtil;
import com.famous.zhifu.loan.util.SharePrefUtil;
import com.famous.zhifu.loan.view.View_Loading;
import com.famous.zhifu.loan.view.xlistview.XListView;
import com.famous.zhifu.loan.view.xlistview.XListView.IXListViewListener;

public class TzjlAc extends Activity implements ActivityInterface,
		IXListViewListener {
	private View mView;
	private TextView mTvTitle;
	private int page = 1, p_num = 10;
	private XListView xlistview;
	private TzjlAdapter adapter;
	private boolean isclearList = false; // 是否清除集合(用于下拉刷新)
	private ArrayList<TzjlInfo> lists = new ArrayList<TzjlInfo>();
	private View_Loading loading;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		mView = getLayoutInflater().inflate(R.layout.tzjl, null);
		setContentView(mView);
		findView();
		initData();
		addAction();
		
		loading.setVisibility(View.VISIBLE);
		
		getData();
	}

	@Override
	public void findView() {
		mTvTitle = (TextView) mView.findViewById(R.id.main_header_tv_text);
		
		loading = (View_Loading) findViewById(R.id.loading);
		
		View home_headview = getLayoutInflater().from(this).inflate(R.layout.item_account_zjjl_header, null);
		
		((TextView)home_headview.findViewById(R.id.item_account_header_time)).setText("投资时间");
		((TextView)home_headview.findViewById(R.id.item_account_header_jine)).setText("金额(元)");
		((TextView)home_headview.findViewById(R.id.item_account_header_type)).setText("回款日期");
		((TextView)home_headview.findViewById(R.id.item_account_header_state)).setText("回款状态");
		
		xlistview = (XListView) findViewById(R.id.xlistview);
		xlistview.setPullLoadEnable(false);// 开启加载更多
		xlistview.setPullRefreshEnable(true);// 开启下拉刷新
		xlistview.setXListViewListener(this);
		

		long lastRefTime = SharePrefUtil.getLastUpdateTime(getApplicationContext(), Consts.SharePref.TZJL);
		xlistview.setLastRefreshTime(lastRefTime);
		
		xlistview.addHeaderView(home_headview);

		adapter = new TzjlAdapter(lists, this);
		xlistview.setAdapter(adapter);
	}

	@Override
	public void initData() {
		mTvTitle.setText("投资记录");
	}

	@Override
	public void addAction() {
		xlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (null != lists && lists.size() > 0) {
					int temp = position - 2;
					
					if (temp<0 || temp>=lists.size()) {
						return;
					}
					
					TzjlInfo tzjlInfo = lists.get(temp);
					Intent intent = new Intent(TzjlAc.this, TzjlDetailAc.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("tzjlInfo", tzjlInfo);
					intent.putExtras(bundle);
					startActivity(intent);
				}
			}
		});
	}

	public void getData() {
		if (NetUtil.isNetworkConnected(this)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("p", page + "");
			params.put("p_num ", p_num + "");
			ServiceClient.call(Consts.NetWork.MYINVEST, params, verifyback);
		} else {
			xlistview.stopLoadMore();
			xlistview.stopRefresh();
			Toast.makeText(this, Consts.NONETWORK, 1).show();
		}
	}

	ServiceClient.Callback verifyback = new ServiceClient.Callback() {
		@Override
		public void callback(JSONObject data) {
			// System.out.println("投资记录   " + data);
			
			loading.setVisibility(View.GONE);
			
			xlistview.setPullLoadEnable(false);// 开启加载更多
			xlistview.stopLoadMore();
			xlistview.stopRefresh();

			long currTime = System.currentTimeMillis();
			xlistview.setLastRefreshTime(currTime);
			SharePrefUtil.setLastUpdateTime(getApplicationContext(),
					Consts.SharePref.TZJL);
			// 如果是下拉刷新则清除集合
			if (isclearList) {
				lists.clear();
				isclearList = false;
			}

			try {
				if (data.getInt("errorcode") == 0) {
					JSONObject json = data.getJSONObject("dataresult");
					int total = Integer.parseInt(json.getString("total"));
					JSONArray array = json.getJSONArray("list");
					for (int i = 0, j = array.length(); i < j; i++) {
						TzjlInfo info = new TzjlInfo();
						JSONObject obj = array.getJSONObject(i);
						info.setId(obj.getString("id"));
						info.setEnddate(obj.getString("enddate"));
						info.setRepaymentStatus(obj.getString("repaymentStatus"));
						info.setLoanmonth(obj.getString("loanmonth"));
						info.setTitle(obj.getString("title"));
						info.setLoansn(obj.getString("loansn"));
						info.setTimeadd(obj.getString("timeadd"));
						info.setYearrate(obj.getString("yearrate"));
						info.setRepayment(obj.getString("repayment"));
						info.setTendersn(obj.getString("tendersn"));
						info.setMoney(obj.getString("money"));
						lists.add(info);
					}
					adapter.notifyDataSetChanged();

					if (page == total) {
						if (page == 1) {
							xlistview.setPullLoadEnable(false);
							xlistview.setNoLoadFooterView("", null);
						} else {
							xlistview.setPullLoadEnable(false);
							xlistview.setNoLoadFooterView("没有更多的数据。", null);
						}

					} else if (total == 0) {
						xlistview.setPullLoadEnable(false);
						xlistview.setNoLoadFooterView("没有数据", null);
					} else {
						xlistview.setPullLoadEnable(true);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
				try {
					String str = data.getString("errormsg");
					Toast.makeText(TzjlAc.this, str, 1).show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					Toast.makeText(TzjlAc.this, Consts.UNKNOWN_FAULT, 1).show();
				}
			}
		}
	};

	@Override
	public void onRefresh() {
		loading.setVisibility(View.VISIBLE);
		isclearList = true;
		page = 1;
		getData();
	}

	@Override
	public void onLoadMore() {
		page++;
		getData();
	}
	
	public void backHandler(View v) {
		finish();
	}

}
