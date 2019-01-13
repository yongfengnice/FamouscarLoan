package com.famous.zhifu.loan.famouscarloan.investlist;

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
import com.famous.zhifu.loan.adapter.RepayAdapter;
import com.famous.zhifu.loan.entity.RepayInfo;
import com.famous.zhifu.loan.internet.ServiceClient;
import com.famous.zhifu.loan.util.NetUtil;
import com.famous.zhifu.loan.util.SharePrefUtil;
import com.famous.zhifu.loan.util.Tools;
import com.famous.zhifu.loan.view.View_Loading;
import com.famous.zhifu.loan.view.xlistview.XListView;
import com.famous.zhifu.loan.view.xlistview.XListView.IXListViewListener;

public class RepayAc extends Activity implements OnClickListener,
		IXListViewListener {
	private String sn;
	private XListView xlistview;
	private RepayAdapter adapter;
	private boolean isclearList = false; // 是否清除集合(用于下拉刷新)
	private ArrayList<RepayInfo> lists = new ArrayList<RepayInfo>();
	private View_Loading loading;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.repay);
		initView();
		getDatas();
	}

	public void initView() {
		sn = getIntent().getStringExtra("sn");

		TextView title = (TextView) findViewById(R.id.main_header_tv_text);
		title.setText("还款方案");
		ImageView ivLeft = (ImageView) findViewById(R.id.main_header_iv_left);
		ivLeft.setOnClickListener(this);
		ImageView ivRight = (ImageView) findViewById(R.id.main_header_iv_right);
		ivRight.setVisibility(View.GONE);
		loading = (View_Loading) findViewById(R.id.loading);
		
		xlistview = (XListView) findViewById(R.id.xlistview);
		xlistview.setXListViewListener(this);
		xlistview.setPullLoadEnable(false);// 开启加载更多
		xlistview.setPullRefreshEnable(true);// 开启下拉刷新
		long lastRefTime = SharePrefUtil.getLastUpdateTime(
				getApplicationContext(), Consts.SharePref.REPAY);
		xlistview.setLastRefreshTime(lastRefTime);
		
		adapter = new RepayAdapter(lists, this);
		xlistview.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_header_iv_left:
			finish();
			break;

		default:
			break;
		}
	}

	private void getDatas() {
		if (NetUtil.isNetworkConnected(this)) {
			loading.setVisibility(View.VISIBLE);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("sn", sn);
			params.put("page_type", "issueinfo");
			ServiceClient.call(Consts.NetWork.LOANDETAIL, params, qian_index);
		} else {
			Toast.makeText(this, Consts.NONETWORK, 1).show();
			xlistview.stopRefresh();
		}
	}

	ServiceClient.Callback qian_index = new ServiceClient.Callback() {

		@Override
		public void callback(JSONObject data) {
			// System.out.println("还款方案   :" + data);
			loading.setVisibility(View.GONE);
			xlistview.stopRefresh();
			// 记录最后刷新时间
			long lastTime = System.currentTimeMillis();
			xlistview.setLastRefreshTime(lastTime);
			SharePrefUtil.setLastUpdateTime(getApplicationContext(),
					Consts.SharePref.REPAY);
			// 如果是下拉刷新则清除集合
			if (isclearList) {
				lists.clear();
				isclearList = false;
			}

			try {
				if (data.getInt("errorcode") == 0
						&& !Tools.isEquals(data.get("dataresult").toString(),
								"[]")) {
					JSONObject json = data.getJSONObject("dataresult");
					JSONArray array = json.getJSONArray("issue");
					for (int i = 0, j = array.length(); i < j; i++) {
						RepayInfo info = new RepayInfo();
						JSONObject obj = array.getJSONObject(i);
						info.setDate(obj.getString("endtime"));
						info.setNum(obj.getString("money"));
						info.setResiduemoney(obj.getString("residuemoney"));
						info.setStatus(obj.getString("isrepayment"));
						lists.add(info);
					}
					 adapter.notifyDataSetChanged();
				} 
			} catch (JSONException e) {
				e.printStackTrace();
				try {
					String str = data.getString("errormsg");
					Toast.makeText(RepayAc.this, str, 1).show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					Toast.makeText(RepayAc.this, Consts.UNKNOWN_FAULT, 1)
							.show();
				}
			}
		}
	};

	@Override
	public void onRefresh() {
		isclearList = true;
		getDatas();
	}

	@Override
	public void onLoadMore() {

	}
}
