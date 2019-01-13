package com.famous.zhifu.loan.famouscarloan.notice;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.activity.ActivityInterface;
import com.famous.zhifu.loan.activity.Consts;
import com.famous.zhifu.loan.internet.ServiceClient;
import com.famous.zhifu.loan.util.NetUtil;

public class NoticeDetailActivity extends Activity implements
		ActivityInterface, OnClickListener {
	private View mView;
	private TextView mTvTitle;
	private String newssn, news;
	private TextView title, views, time;
	private WebView content;

	// // 解析图片
	// ImageGetter imgGetter = new Html.ImageGetter() {
	// @Override
	// public Drawable getDrawable(String source) {
	// // System.out.println("source  " + source);
	//
	// Drawable drawable = null;
	// drawable = Drawable.createFromPath(source); // Or fetch it from the
	// // URL
	// // Important
	// drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
	// drawable.getIntrinsicHeight());
	// return drawable;
	// }
	// };

	// String url;
	// ImageGetter imgGetter;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1001) {
				String url = (String) msg.obj;
				
				content.loadDataWithBaseURL("about:blank", url, "text/html","utf-8", "");
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		mView = getLayoutInflater().inflate(R.layout.activity_notice_detail,
				null);
		setContentView(mView);
		findView();
		initData();
		addAction();
		doloadtask();
	}

	@Override
	public void findView() {
		mTvTitle = (TextView) mView.findViewById(R.id.main_header_tv_text);

		title = (TextView) mView.findViewById(R.id.title);
		views = (TextView) mView.findViewById(R.id.views);
		time = (TextView) mView.findViewById(R.id.time);
		content = (WebView) mView.findViewById(R.id.content);

	}

	@Override
	public void initData() {
		mTvTitle.setText("新闻公告");

		newssn = getIntent().getStringExtra("sn");
		news = getIntent().getStringExtra("views");
	}

	@Override
	public void addAction() {
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

	// 网站公告
	private void doloadtask() {
		if (NetUtil.isNetworkConnected(this)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("code", newssn + "");
			params.put("cate", "notice");
			ServiceClient.call(Consts.NetWork.NOTICE, params, noticeback);
		} else {
			Toast.makeText(this, Consts.NONETWORK, 1).show();
		}
	}

	ServiceClient.Callback noticeback = new ServiceClient.Callback() {

		@Override
		public void callback(JSONObject data) {
			// System.out.println("notice   :" + data);

			try {
				if (data.getInt("errorcode") == 0) {
					JSONObject obj = data.getJSONObject("dataresult");
					title.setText(obj.getString("title"));
					time.setText(obj.getString("timeadd"));
					views.setText(news);
					String url = obj.getString("content");
					Message msg = new Message();
					msg.obj = url;
					msg.what = 1001;
					handler.sendMessage(msg);
				}
			} catch (JSONException e) {
				e.printStackTrace();
				try {
					String str = data.getString("errormsg");
					Toast.makeText(NoticeDetailActivity.this, str, 1).show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					Toast.makeText(NoticeDetailActivity.this,
							Consts.UNKNOWN_FAULT, 1).show();
				}
			}
		}
	};
	
	public void backHandler(View v) {
		finish();
	}

}
