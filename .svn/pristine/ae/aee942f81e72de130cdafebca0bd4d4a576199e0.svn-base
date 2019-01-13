package com.famous.zhifu.loan.famouscarloan.home;

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
import android.widget.Toast;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.activity.ActivityInterface;
import com.famous.zhifu.loan.activity.Consts;
import com.famous.zhifu.loan.adapter.RankAdapter;
import com.famous.zhifu.loan.entity.RankInfo;
import com.famous.zhifu.loan.internet.ServiceClient;
import com.famous.zhifu.loan.util.NetUtil;
import com.famous.zhifu.loan.util.SharePrefUtil;
import com.famous.zhifu.loan.view.View_Loading;
import com.famous.zhifu.loan.view.xlistview.XListView;
import com.famous.zhifu.loan.view.xlistview.XListView.IXListViewListener;

public class RankActivity extends Activity implements ActivityInterface,
		OnClickListener, IXListViewListener {
	private View mView;
	private ImageView mIvLeft;
	private XListView xlistview;
	private RankAdapter adapter;
	private ArrayList<RankInfo> arrays = new ArrayList<RankInfo>();
	private boolean isclearList = false; // 是否清除集合(用于下拉刷新)
	private View_Loading loading;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		mView = getLayoutInflater().inflate(R.layout.rank, null);
		setContentView(mView);
		findView();
		initData();
		addAction();
		getRank();
	}

	@Override
	public void findView() {
		loading = (View_Loading) mView.findViewById(R.id.loading);
		
		mIvLeft = (ImageView) mView.findViewById(R.id.main_header_iv_left);
		xlistview = (XListView) mView.findViewById(R.id.xlistview);
		xlistview.setXListViewListener(this);
		xlistview.setPullLoadEnable(false);// 开启加载更多
		xlistview.setPullRefreshEnable(true);// 开启下拉刷新
		long lastRefTime = SharePrefUtil.getLastUpdateTime(
				getApplicationContext(), Consts.SharePref.RANK);
		xlistview.setLastRefreshTime(lastRefTime);

		adapter = new RankAdapter(arrays, this);
		xlistview.setAdapter(adapter);

	}

	@Override
	public void initData() {

	}

	@Override
	public void addAction() {
		mIvLeft.setOnClickListener(this);
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

	public void getRank() {
		if (NetUtil.isNetworkConnected(this)) {
			loading.setVisibility(View.VISIBLE);
			Map<String, Object> params = new HashMap<String, Object>();
			ServiceClient.call(Consts.NetWork.RANK, params, callback);
		} else {
			Toast.makeText(RankActivity.this, Consts.NONETWORK, 1).show();
			xlistview.stopLoadMore();
			xlistview.stopRefresh();
		}
	}

	ServiceClient.Callback callback = new ServiceClient.Callback() {
		@Override
		public void callback(JSONObject ret) {
			// System.out.println("排行榜 :" + ret);

			loading.setVisibility(View.GONE);
			xlistview.stopLoadMore();
			xlistview.stopRefresh();
			// 记录最后刷新时间
			long lastTime = System.currentTimeMillis();
			xlistview.setLastRefreshTime(lastTime);
			SharePrefUtil.setLastUpdateTime(getApplicationContext(),
					Consts.SharePref.RANK);
			// 如果是下拉刷新则清除集合
			if (isclearList) {
				arrays.clear();
				isclearList = false;
			}

			try {
				if (ret.getInt("errorcode") == 0) {
					JSONArray hallArray = ret.getJSONArray("dataresult");
					for (int i = 0, j = hallArray.length(); i < j; i++) {
						RankInfo item = new RankInfo();
						JSONObject jo = hallArray.getJSONObject(i);
						item.setUserName(jo.getString("username"));
						item.setNum(jo.getString("account"));
						arrays.add(item);
					}
					adapter.notifyDataSetChanged();
				}
			} catch (JSONException e) {
				try {
					String str = ret.getString("errormsg");
					Toast.makeText(RankActivity.this, str, 1).show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					Toast.makeText(RankActivity.this, Consts.UNKNOWN_FAULT, 1)
							.show();
				}
			}
		}
	};

	@Override
	public void onRefresh() {
		isclearList = true;
		getRank();
	}

	@Override
	public void onLoadMore() {

	}

}
