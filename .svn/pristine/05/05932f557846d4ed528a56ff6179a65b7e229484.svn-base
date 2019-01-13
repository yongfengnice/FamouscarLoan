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
import com.famous.zhifu.loan.activity.ActivityInterface;
import com.famous.zhifu.loan.activity.Consts;
import com.famous.zhifu.loan.adapter.HkygAdapter;
import com.famous.zhifu.loan.entity.HkygInfo;
import com.famous.zhifu.loan.internet.ServiceClient;
import com.famous.zhifu.loan.util.NetUtil;
import com.famous.zhifu.loan.util.SharePrefUtil;
import com.famous.zhifu.loan.view.View_Loading;
import com.famous.zhifu.loan.view.xlistview.XListView;
import com.famous.zhifu.loan.view.xlistview.XListView.IXListViewListener;

public class HkygAc extends Activity implements ActivityInterface,
		IXListViewListener {
	private View mView;
	private TextView mTvTitle, mTvOne, mTvTwo;
	private ImageView mIvRight, mIvLeft;
	private int page = 1, p_num = 10;
	private XListView xlistview;
	private ArrayList<HkygInfo> lists = new ArrayList<HkygInfo>();
	private HkygAdapter adapter;
	private boolean isclearList = false; // 是否清除集合(用于下拉刷新)
	private View_Loading loading;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		mView = getLayoutInflater().inflate(R.layout.hkyg, null);
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
		mTvOne = (TextView) mView.findViewById(R.id.account_tv_text);
		mTvTwo = (TextView) mView.findViewById(R.id.account_tv_text_two);
		mIvRight = (ImageView) mView.findViewById(R.id.main_header_iv_right);
		mIvLeft = (ImageView) mView.findViewById(R.id.main_header_iv_left);

		loading = (View_Loading) findViewById(R.id.loading);
		
		xlistview = (XListView) findViewById(R.id.xlistview);
		xlistview.setPullLoadEnable(false);// 开启加载更多
		xlistview.setPullRefreshEnable(true);// 开启下拉刷新
		xlistview.setDividerHeight(0);
		xlistview.setXListViewListener(this);

		long lastRefTime = SharePrefUtil.getLastUpdateTime(
				getApplicationContext(), Consts.SharePref.HKYG);
		xlistview.setLastRefreshTime(lastRefTime);

		adapter = new HkygAdapter(lists, this);
		xlistview.setAdapter(adapter);
	}

	@Override
	public void initData() {
		mTvTitle.setText("回款预告");
		mIvRight.setVisibility(View.GONE);
	}

	@Override
	public void addAction() {
		mIvLeft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

	public void getData() {
		if (NetUtil.isNetworkConnected(this)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("p", page + "");
			params.put("p_num ", p_num + "");
			ServiceClient.call(Consts.NetWork.HKYG, params, verifyback);
		} else {
			Toast.makeText(this, Consts.NONETWORK, 1).show();
		}
	}

	ServiceClient.Callback verifyback = new ServiceClient.Callback() {
		@Override
		public void callback(JSONObject data) {
			// System.out.println("回款预告  " + data);

			loading.setVisibility(View.GONE);
			xlistview.setPullLoadEnable(true);
			xlistview.stopLoadMore();
			xlistview.stopRefresh();
			// 记录最后刷新时间
			long lastTime = System.currentTimeMillis();
			xlistview.setLastRefreshTime(lastTime);
			SharePrefUtil.setLastUpdateTime(getApplicationContext(),
					Consts.SharePref.HKYG);
			// 如果是下拉刷新则清除集合
			if (isclearList) {
				lists.clear();
				isclearList = false;
			}

			try {
				if (data.getInt("errorcode") == 0) {
					JSONObject json = data.getJSONObject("dataresult");
					JSONObject pagObj = json.getJSONObject("page_info");
					int totalPage = Integer.parseInt(pagObj
							.getString("p_total"));

					String total_allotmoney = json
							.getString("total_allotmoney");
					mTvOne.setText("至" + json.getString("last_end_time")
							+ ",共回款" + total_allotmoney + "元");
					mTvTwo.setText("其中本金" + json.getString("total_tendermoney")
							+ "元,利息" + json.getString("total_ratemoney") + "元");

					JSONArray arrays = json.getJSONArray("allotinfo");
					for (int i = 0, j = arrays.length(); i < j; i++) {
						HkygInfo info = new HkygInfo();
						JSONObject obj = arrays.getJSONObject(i);
						info.setLoansn(obj.getString("loansn"));
						info.setLoanmonth(obj.getString("loanmonth"));
						info.setMoney(obj.getString("money"));
						info.setTendermoney(obj.getString("tendermoney"));
						info.setRatemoney(obj.getString("ratemoney"));
						info.setTitle(obj.getString("title"));
						info.setAllot_periods(obj.getString("allot_periods"));
						info.setRepaymenttime(obj.getString("repaymenttime"));

						// JSONArray array = obj.getJSONArray("allot");
						// for (int n = 0, m = array.length(); n < m; n++) {
						// HkygDetailInfo dInfo = new HkygDetailInfo();
						// JSONObject obj1 = array.getJSONObject(n);
						// }
						lists.add(info);
					}
					adapter.notifyDataSetChanged();

					if (page == totalPage) {
						if (page == 1) {
							xlistview.setPullLoadEnable(false);
							xlistview.setNoLoadFooterView("", null);
						} else {
							xlistview.setPullLoadEnable(false);
							xlistview.setNoLoadFooterView("没有更多的数据。", null);
						}
					} else if (totalPage == 0) {
						xlistview.setPullLoadEnable(false);
					} else {
						xlistview.setPullLoadEnable(true);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
				try {
					String str = data.getString("errormsg");
					Toast.makeText(HkygAc.this, str, 1).show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					Toast.makeText(HkygAc.this, Consts.UNKNOWN_FAULT, 1).show();
				}
			}
		}
	};

	@Override
	public void onRefresh() {
		loading.setVisibility(View.VISIBLE);
		page = 1;
		isclearList = true;
		getData();
	}

	@Override
	public void onLoadMore() {
		page++;
		getData();
	}

}
