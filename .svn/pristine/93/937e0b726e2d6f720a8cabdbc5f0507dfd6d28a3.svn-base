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
import com.famous.zhifu.loan.adapter.RecordAdapter;
import com.famous.zhifu.loan.entity.RecordInfo;
import com.famous.zhifu.loan.internet.ServiceClient;
import com.famous.zhifu.loan.util.NetUtil;
import com.famous.zhifu.loan.util.SharePrefUtil;
import com.famous.zhifu.loan.view.View_Loading;
import com.famous.zhifu.loan.view.xlistview.XListView;
import com.famous.zhifu.loan.view.xlistview.XListView.IXListViewListener;

public class RecordDetailAc extends Activity implements OnClickListener,
		IXListViewListener {
	private TextView title;
	private ImageView ivLeft, ivRight;
	private String sn;
	private XListView xlistview;
	private RecordAdapter adapter;
	private boolean isclearList = false; // 是否清除集合(用于下拉刷新)
	private int page = 1, p_num = 15;
	private ArrayList<RecordInfo> lists = new ArrayList<RecordInfo>();
	private View_Loading loading;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record_detail);
		initView();
		loading.setVisibility(View.VISIBLE);
		getCarDetail();
	}

	public void initView() {
		sn = getIntent().getStringExtra("sn");

		title = (TextView) findViewById(R.id.main_header_tv_text);
		title.setText("投资记录");
		ivLeft = (ImageView) findViewById(R.id.main_header_iv_left);
		ivLeft.setOnClickListener(this);
		ivRight = (ImageView) findViewById(R.id.main_header_iv_right);
		ivRight.setVisibility(View.GONE);
		loading = (View_Loading) findViewById(R.id.loading);
		
		xlistview = (XListView) findViewById(R.id.xlistview);
		xlistview.setXListViewListener(this);
		xlistview.setPullLoadEnable(false);// 开启加载更多
		xlistview.setPullRefreshEnable(true);// 开启下拉刷新
		long lastRefTime = SharePrefUtil.getLastUpdateTime(
				getApplicationContext(), Consts.SharePref.RECORD);
		xlistview.setLastRefreshTime(lastRefTime);

		adapter = new RecordAdapter(lists, this);
		xlistview.setAdapter(adapter);
	}

	private void getCarDetail() {
		if (NetUtil.isNetworkConnected(this)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("sn", sn);
			params.put("p", page+"");
			params.put("p_num", p_num+"");
			params.put("page_type", "tenderinfo");
			ServiceClient
					.call(Consts.NetWork.LOANDETAIL, params, qian_detail);
		} else {
			xlistview.stopLoadMore();
			xlistview.stopRefresh();
			Toast.makeText(this, Consts.NONETWORK, 1).show();
		}
	}

	ServiceClient.Callback qian_detail = new ServiceClient.Callback() {

		@Override
		public void callback(JSONObject data) {
			// System.out.println("投资记录   :" + data);

			loading.setVisibility(View.GONE);
			xlistview.setPullLoadEnable(true);
			xlistview.stopLoadMore();
			xlistview.stopRefresh();
			// 记录最后刷新时间
			long lastTime = System.currentTimeMillis();
			xlistview.setLastRefreshTime(lastTime);
			SharePrefUtil.setLastUpdateTime(getApplicationContext(),
					Consts.SharePref.RECORD);
			// 如果是下拉刷新则清除集合
			if (isclearList) {
				lists.clear();
				isclearList = false;
			}

			try {
				if (data.getInt("errorcode") == 0) {
					JSONObject jsonObject = data.getJSONObject("dataresult");
					JSONObject json = jsonObject.getJSONObject("pageinfo");
					int totalPage = Integer.parseInt(json
							.getString("p_total"));

					JSONArray array = jsonObject.getJSONArray("tender");
					for (int i = 0, j = array.length(); i < j; i++) {
						RecordInfo info = new RecordInfo();
						JSONObject obj = array.getJSONObject(i);
						info.setIntegral(obj.getString("integral"));
						info.setIs_auto(obj.getString("is_auto"));
						info.setTimeadd(obj.getString("timeadd"));
						info.setUsername(obj.getString("username"));
						info.setType(obj.getString("type"));
						info.setMoney(obj.getString("money"));
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
			} catch (Exception e) {
				e.printStackTrace();
				try {
					String str = data.getString("errormsg");
					Toast.makeText(RecordDetailAc.this, str, 1).show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					Toast.makeText(RecordDetailAc.this, Consts.UNKNOWN_FAULT, 1)
							.show();
				}
			}
		}
	};

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

	@Override
	public void onRefresh() {
		loading.setVisibility(View.VISIBLE);
		isclearList = true;
		page = 1;
		getCarDetail();
	}

	@Override
	public void onLoadMore() {
		page++;
		getCarDetail();
	}
}
