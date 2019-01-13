package com.famous.zhifu.loan.famouscarloan.account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.activity.Consts;
import com.famous.zhifu.loan.adapter.ZjlsAdapter;
import com.famous.zhifu.loan.entity.ZjlsInfo;
import com.famous.zhifu.loan.internet.ServiceClient;
import com.famous.zhifu.loan.util.NetUtil;
import com.famous.zhifu.loan.util.SharePrefUtil;
import com.famous.zhifu.loan.view.View_Loading;
import com.famous.zhifu.loan.view.xlistview.XListView;
import com.famous.zhifu.loan.view.xlistview.XListView.IXListViewListener;

public class ZjlsAc extends Activity implements OnClickListener,
		IXListViewListener {
	private View_Loading loading;
	private TextView conditionOne, conditionTwo, conditionThree, conditionFour;
	private XListView xlistview;
	private boolean isclearList = false; // 是否清除集合(用于下拉刷新)
	private int page = 1, p_num = 15;
	private ArrayList<ZjlsInfo> lists = new ArrayList<ZjlsInfo>();
	private ZjlsAdapter adapter;
	private String type = "week";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zjls);
		initView();
		loading.setVisibility(View.VISIBLE);
		getData(type);
	}

	public void initView() {
		TextView mTvTitle = (TextView) findViewById(R.id.main_header_tv_text);
		mTvTitle.setText("资金记录");
		
		conditionOne = (TextView) findViewById(R.id.conditionOne);
		conditionOne.setOnClickListener(this);
		conditionTwo = (TextView) findViewById(R.id.conditionTwo);
		conditionTwo.setOnClickListener(this);
		conditionThree = (TextView) findViewById(R.id.conditionThree);
		conditionThree.setOnClickListener(this);
		conditionFour = (TextView) findViewById(R.id.conditionFour);
		conditionFour.setOnClickListener(this);

		loading = (View_Loading) findViewById(R.id.loading);
		
		View home_headview = getLayoutInflater().from(this).inflate(R.layout.item_account_zjjl_header, null);
		
		xlistview = (XListView) findViewById(R.id.xlistview);
		xlistview.setXListViewListener(this);
		xlistview.setPullLoadEnable(false);// 开启加载更多
		xlistview.setPullRefreshEnable(true);// 开启下拉刷新
		
		long lastRefTime = SharePrefUtil.getLastUpdateTime(getApplicationContext(), Consts.SharePref.ZJLS);
		xlistview.setLastRefreshTime(lastRefTime);
		
		xlistview.addHeaderView(home_headview);
		
		adapter = new ZjlsAdapter(lists, this);
		xlistview.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_header_iv_left:
			finish();
			break;
		case R.id.conditionOne:
			conditionOne.setBackgroundResource(R.drawable.shape_zjls_left_black_select);
			conditionTwo.setBackgroundResource(R.drawable.test1);
			conditionThree.setBackgroundResource(R.drawable.test1);
			conditionFour.setBackgroundResource(R.drawable.test1);

			conditionOne.setTextColor(0xffB49060);
			conditionTwo.setTextColor(0xff363636);
			conditionThree.setTextColor(0xff363636);
			conditionFour.setTextColor(0xff363636);

			type = "week";
			xlistview.toRefreshing();
			break;
		case R.id.conditionTwo:
			conditionOne
					.setBackgroundResource(R.drawable.test1);
			conditionTwo
					.setBackgroundResource(R.drawable.shape_zjls_left_black_select);
			conditionThree.setBackgroundResource(R.drawable.test1);
			conditionFour
					.setBackgroundResource(R.drawable.test1);

			conditionOne.setTextColor(0xff363636);
			conditionTwo.setTextColor(0xffB49060);
			conditionThree.setTextColor(0xff363636);
			conditionFour.setTextColor(0xff363636);

			type = "month";
			xlistview.toRefreshing();
			break;
		case R.id.conditionThree:
			conditionOne
					.setBackgroundResource(R.drawable.test1);
			conditionTwo.setBackgroundResource(R.drawable.test1);
			conditionThree
					.setBackgroundResource(R.drawable.shape_zjls_left_black_select);
			conditionFour
					.setBackgroundResource(R.drawable.test1);

			conditionOne.setTextColor(0xff363636);
			conditionTwo.setTextColor(0xff363636);
			conditionThree.setTextColor(0xffB49060);
			conditionFour.setTextColor(0xff363636);

			type = "more";
			xlistview.toRefreshing();
			break;
		case R.id.conditionFour:
			conditionOne
					.setBackgroundResource(R.drawable.test1);
			conditionTwo.setBackgroundResource(R.drawable.test1);
			conditionThree.setBackgroundResource(R.drawable.test1);
			conditionFour
					.setBackgroundResource(R.drawable.shape_zjls_left_black_select);

			conditionOne.setTextColor(0xff363636);
			conditionTwo.setTextColor(0xff363636);
			conditionThree.setTextColor(0xff363636);
			conditionFour.setTextColor(0xffB49060);

			type = "all";
			xlistview.toRefreshing();
			break;
		default:
			break;
		}
	}

	public void getData(String type) {
		if (NetUtil.isNetworkConnected(this)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("page", page + "");
			params.put("number ", p_num + "");
			params.put("time", type);
			ServiceClient.call(Consts.NetWork.ZJLS, params, verifyback);
		} else {
			xlistview.stopLoadMore();
			xlistview.stopRefresh();
			Toast.makeText(this, Consts.NONETWORK, 1).show();
		}
	}

	ServiceClient.Callback verifyback = new ServiceClient.Callback() {
		@Override
		public void callback(JSONObject data) {
			// System.out.println("资金流水    " + data);
			
			loading.setVisibility(View.GONE);
			xlistview.setPullLoadEnable(false);// 开启加载更多
			xlistview.stopLoadMore();
			xlistview.stopRefresh();

			long currTime = System.currentTimeMillis();
			xlistview.setLastRefreshTime(currTime);
			SharePrefUtil.setLastUpdateTime(getApplicationContext(),
					Consts.SharePref.ZJLS);
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
						ZjlsInfo info = new ZjlsInfo();
						JSONObject obj = array.getJSONObject(i);
						info.setId(obj.getString("id"));
						info.setAmount(obj.getString("amount"));
						info.setCodeid(obj.getString("codeid"));
						info.setSn(obj.getString("sn"));
						info.setCode(obj.getString("code"));
						info.setDate(obj.getString("date"));
						info.setName(obj.getString("name"));
						info.setState(obj.getString("statusName"));
						info.setStateId(obj.getString("status"));
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
					Toast.makeText(ZjlsAc.this, str, 1).show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					Toast.makeText(ZjlsAc.this, Consts.UNKNOWN_FAULT, 1).show();
				}
			}
		}
	};

	@Override
	public void onRefresh() {
		loading.setVisibility(View.VISIBLE);
		page = 1;
		isclearList = true;
		getData(type);
	}

	@Override
	public void onLoadMore() {
		page++;
		getData(type);
	}
	
	public void backHandler(View v) {
		finish();
	}
}
