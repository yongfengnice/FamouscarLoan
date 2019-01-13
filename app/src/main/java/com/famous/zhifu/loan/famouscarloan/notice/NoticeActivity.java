package com.famous.zhifu.loan.famouscarloan.notice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.activity.ActivityInterface;
import com.famous.zhifu.loan.activity.Consts;
import com.famous.zhifu.loan.activity.LoginActivity;
import com.famous.zhifu.loan.activity.MyApp;
import com.famous.zhifu.loan.activity.WebViewActivity;
import com.famous.zhifu.loan.adapter.MediaAdapter;
import com.famous.zhifu.loan.adapter.NewsAdapter;
import com.famous.zhifu.loan.entity.MediaInfo;
import com.famous.zhifu.loan.entity.NoticeInfo;
import com.famous.zhifu.loan.internet.ServiceClient;
import com.famous.zhifu.loan.util.NetUtil;
import com.famous.zhifu.loan.util.SharePrefUtil;
import com.famous.zhifu.loan.util.Tools;
import com.famous.zhifu.loan.view.View_Loading;
import com.famous.zhifu.loan.view.xlistview.XListView;
import com.famous.zhifu.loan.view.xlistview.XListView.IXListViewListener;

public class NoticeActivity extends Activity implements ActivityInterface,IXListViewListener {
	private View mView;
	private ImageView userImg;
	private XListView xlistview_notice, xlistview_media;
	private TextView noticeOne, noticeTwo;
	private NewsAdapter adapterNo;
	private MediaAdapter adapterMe;
	private ArrayList<NoticeInfo> arrays = new ArrayList<NoticeInfo>();
	private ArrayList<MediaInfo> arrays_media = new ArrayList<MediaInfo>();
	private boolean isclearList = false; // 是否清除集合(用于下拉刷新)
	private boolean isclearLists = false; // 是否清除集合(用于下拉刷新)
	private int page = 1, p_num = 10;
	private View_Loading loading;
	
	private TextView mTvTitle = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mView = getLayoutInflater().inflate(R.layout.activity_notice, null);
		setContentView(mView);
		MyApp.getInstance().addActivity(this);
		findView();
		initData();
		addAction();
		
		loading.setVisibility(View.VISIBLE);
		doloadtask();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if (TextUtils.isEmpty(MyApp.token)) {
			userImg.setVisibility(View.VISIBLE);
		} else {
			userImg.setVisibility(View.GONE);
		}
	}

	@Override
	public void findView() {
		mTvTitle = (TextView) mView.findViewById(R.id.main_header_tv_text);
		mView.findViewById(R.id.main_header_iv_right).setVisibility(View.VISIBLE);
		
		userImg = (ImageView)mView.findViewById(R.id.main_header_iv_right);
		
		noticeOne = (TextView) mView.findViewById(R.id.noticeOne);
		noticeTwo = (TextView) mView.findViewById(R.id.noticeTwo);
		loading = (View_Loading) findViewById(R.id.loading);
		
		xlistview_notice = (XListView) mView.findViewById(R.id.xlistview_notice);
		xlistview_notice.setXListViewListener(this);
		xlistview_notice.setPullLoadEnable(false);// 开启加载更多
		xlistview_notice.setPullRefreshEnable(true);// 开启下拉刷新
		long lastRefTime1 = SharePrefUtil.getLastUpdateTime(
				getApplicationContext(), Consts.SharePref.NOTICE);
		xlistview_notice.setLastRefreshTime(lastRefTime1);
		adapterNo = new NewsAdapter(this, arrays);
		xlistview_notice.setAdapter(adapterNo);
		xlistview_notice.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				NoticeInfo info = arrays.get(position - 1);
				Intent intent = new Intent(NoticeActivity.this,NoticeDetailActivity.class);
				intent.putExtra("sn", info.getNews_sn());
				intent.putExtra("views", info.getViews());
				startActivity(intent);
			}
		});
		

		xlistview_media = (XListView) mView.findViewById(R.id.xlistview_media);
		xlistview_media.setXListViewListener(this);
		xlistview_media.setPullLoadEnable(false);// 开启加载更多
		xlistview_media.setPullRefreshEnable(true);// 开启下拉刷新
		long lastRefTime2 = SharePrefUtil.getLastUpdateTime(
				getApplicationContext(), Consts.SharePref.MEDIA);
		xlistview_media.setLastRefreshTime(lastRefTime2);
		adapterMe = new MediaAdapter(this, arrays_media);
		xlistview_media.setAdapter(adapterMe);
		xlistview_media.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				MediaInfo info = arrays_media.get(position - 1);
				Intent intent = new Intent(NoticeActivity.this,MediaDetailActivity.class);
				
				intent.putExtra("linkurl", info.getUrlStr());
				intent.putExtra("code", info.getNews_sn());
				intent.putExtra("islink", info.getIslink());
				
				startActivity(intent);
			}
		});
	}

	@Override
	public void initData() {
		mTvTitle.setText(getResources().getString(R.string.more_news));
	}

	@Override
	public void addAction() {
		noticeOne.setOnClickListener(new MyClickListener());
		noticeTwo.setOnClickListener(new MyClickListener());
	}

	private String flag = "1";

	private class MyClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.noticeOne:
				flag = "1";
				xlistview_notice.setVisibility(View.VISIBLE);
				xlistview_media.setVisibility(View.GONE);
				xlistview_notice.toRefreshing();

				noticeTwo.setBackgroundResource(R.drawable.shape_red_right_line_red_bg);
				noticeOne.setBackgroundResource(R.drawable.shape_red_left_line_red_bg_select);

				break;
			case R.id.noticeTwo:
				flag = "2";
				xlistview_notice.setVisibility(View.GONE);
				xlistview_media.setVisibility(View.VISIBLE);
				xlistview_media.toRefreshing();

				noticeTwo.setBackgroundResource(R.drawable.shape_red_right_line_red_bg_select);
				noticeOne.setBackgroundResource(R.drawable.shape_red_right_line_red_bg);

				break;

			default:
				break;
			}
		}
	}

	@Override
	public void onRefresh() {
		loading.setVisibility(View.VISIBLE);
		if (Tools.isEquals(flag, "1")) {
			isclearList = true;
			page = 1;
			doloadtask();
		} else if (Tools.isEquals(flag, "2")) {
			isclearLists = true;
			getMediaData();
		}

	}

	@Override
	public void onLoadMore() {
		page++;
		doloadtask();
	}

	// 网站公告
	private void doloadtask() {
		if (NetUtil.isNetworkConnected(this)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("p", page + "");
			params.put("page_num", p_num + "");
			params.put("cate","notice");
			ServiceClient.call(Consts.NetWork.NOTICE, params, noticeback);
		} else {
			Toast.makeText(this, Consts.NONETWORK, 1).show();
		}
	}

	ServiceClient.Callback noticeback = new ServiceClient.Callback() {

		@Override
		public void callback(JSONObject data) {
			// System.out.println("notice   :" + data);

			loading.setVisibility(View.GONE);
			xlistview_notice.setPullLoadEnable(true);
			xlistview_notice.stopLoadMore();
			xlistview_notice.stopRefresh();
			// 记录最后刷新时间
			long lastTime = System.currentTimeMillis();
			xlistview_notice.setLastRefreshTime(lastTime);
			SharePrefUtil.setLastUpdateTime(getApplicationContext(),Consts.SharePref.NOTICE);
			// 如果是下拉刷新则清除集合
			if (isclearList) {
				arrays.clear();
				isclearList = false;
			}

			try {
				if (data.getInt("errorcode") == 0
						&& !Tools.isEquals(data.get("dataresult").toString(),"[]")) {

					JSONObject json = data.getJSONObject("dataresult");
					int totalPage = Integer.parseInt(json.getString("page_total"));
					JSONArray array = json.getJSONArray("data");
					for (int i = 0, j = array.length(); i < j; i++) {
						NoticeInfo info = new NoticeInfo();
						JSONObject obj = array.getJSONObject(i);
//						info.setNews_id(obj.getString("news_id"));
						info.setNews_sn(obj.getString("code"));
						info.setTitle(obj.getString("title"));
						info.setViews("[新闻公告]");
						info.setDescription(obj.getString("summary"));
						info.setAdd_time(obj.getString("timeadd"));
						arrays.add(info);
					}
					adapterNo.notifyDataSetChanged();
					if (page == totalPage) {
						if (page == 1) {
							xlistview_notice.setPullLoadEnable(false);
							xlistview_notice.setNoLoadFooterView("", null);
						} else {
							xlistview_notice.setPullLoadEnable(false);
							xlistview_notice.setNoLoadFooterView("没有更多的数据。",
									null);
						}
					} else if (totalPage == 0) {
						xlistview_notice.setPullLoadEnable(false);
					} else {
						xlistview_notice.setPullLoadEnable(true);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
				try {
					String str = data.getString("errormsg");
					Toast.makeText(NoticeActivity.this, str, 1).show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					Toast.makeText(NoticeActivity.this, Consts.UNKNOWN_FAULT, 1).show();
				}
			}

		}
	};

	// 媒体报道
	private void getMediaData() {
		if (NetUtil.isNetworkConnected(this)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("cate","media");
			ServiceClient.call(Consts.NetWork.NOTICE, params, mediaBack);
		} else {
			Toast.makeText(this, Consts.NONETWORK, 1).show();
		}
	}

	ServiceClient.Callback mediaBack = new ServiceClient.Callback() {

		@Override
		public void callback(JSONObject data) {
			// System.out.println("media   :" + data);

			loading.setVisibility(View.GONE);
			xlistview_media.stopLoadMore();
			xlistview_media.stopRefresh();
			// 记录最后刷新时间
			long lastTime = System.currentTimeMillis();
			xlistview_media.setLastRefreshTime(lastTime);
			SharePrefUtil.setLastUpdateTime(getApplicationContext(),Consts.SharePref.MEDIA);
			// 如果是下拉刷新则清除集合
			if (isclearLists) {
				arrays_media.clear();
				isclearLists = false;
			}

			try {
				if (data.getInt("errorcode") == 0&& !Tools.isEquals(data.get("dataresult").toString(),"[]")) {

					JSONObject json = data.getJSONObject("dataresult");
					JSONArray array = json.getJSONArray("data");
					for (int i = 0, j = array.length(); i < j; i++) {
						MediaInfo info = new MediaInfo();
						JSONObject obj = array.getJSONObject(i);
						
						info.setNews_sn(obj.getString("code"));
						info.setTitle(obj.getString("title"));
						info.setViews("[媒体报到]");
						info.setUrlStr(obj.getString("linkurl"));
						info.setDescription(obj.getString("summary"));
						info.setAdd_time(obj.getString("timeadd"));
						info.setIslink(obj.getString("islink"));
						
						arrays_media.add(info);
					}
					adapterMe.notifyDataSetChanged();

				}
			} catch (JSONException e) {
				e.printStackTrace();
				try {
					String str = data.getString("errormsg");
					Toast.makeText(NoticeActivity.this, str, 1).show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					Toast.makeText(NoticeActivity.this, Consts.UNKNOWN_FAULT, 1)
							.show();
				}
			}
		}
	};
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		MyApp.getInstance().remove(this);
	}
	
	
	public void rightHandler(View v){
		
	}
	
	public void userHandler(View v) {
		startActivity(new Intent(this,LoginActivity.class));
	}
	
	public void backHandler(View v) {
		finish();
	}
}
