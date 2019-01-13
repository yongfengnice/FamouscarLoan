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
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.activity.ActivityInterface;
import com.famous.zhifu.loan.activity.Consts;
import com.famous.zhifu.loan.internet.ServiceClient;
import com.famous.zhifu.loan.util.NetUtil;
import com.famous.zhifu.loan.view.View_Loading;

public class MediaDetailActivity extends Activity implements ActivityInterface{
	private View mView;
	
	private TextView mTvTitle;
	private String linkurl, code,islink;
	
	private WebView content;
	
	private View_Loading loading;

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
		
		mView = getLayoutInflater().inflate(R.layout.webview_cb,null);
		
		setContentView(mView);
		
		findView();
		initData();
		addAction();
	}

	@Override
	public void findView() {
		mTvTitle = (TextView) mView.findViewById(R.id.main_header_tv_text);

		content = (WebView) mView.findViewById(R.id.webview);
		loading = (View_Loading) findViewById(R.id.loading);
		
		WebSettings settings = content.getSettings(); 
		
		settings.setSupportZoom(true);
		settings.setBuiltInZoomControls(false);
		/*settings.setUseWideViewPort(true); 
	    settings.setLoadWithOverviewMode(true); */
		
		content.setWebViewClient(vieClient);
	}
	
	WebViewClient vieClient = new WebViewClient() {

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			
			loading.setVisibility(View.GONE);
		}

		@Override
		public void onReceivedError(WebView view, int errorCode,String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			loading.setVisibility(View.VISIBLE);
			
			content.loadUrl(url);
			return true;
		}

	};

	@Override
	public void initData() {
		mTvTitle.setText("媒体报道");

		linkurl = getIntent().getStringExtra("linkurl");
		code = getIntent().getStringExtra("code");
		
		islink = getIntent().getStringExtra("islink");
	}

	@Override
	public void addAction() {
		if (islink.equals("1")) {
			Message msg = new Message();
			
			msg.obj = linkurl;
			msg.what = 1001;
			
			handler.sendMessage(msg);
		}else {
			doloadtask();
		}
	}

	// 网站公告
	private void doloadtask() {
		if (NetUtil.isNetworkConnected(this)) {
			loading.setVisibility(View.VISIBLE);
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("code", code + "");
			params.put("cate", "media");
			
			ServiceClient.call(Consts.NetWork.NOTICE, params, mediaback);
		} else {
			Toast.makeText(this, Consts.NONETWORK, 1).show();
		}
	}

	ServiceClient.Callback mediaback = new ServiceClient.Callback() {

		@Override
		public void callback(JSONObject data) {
			loading.setVisibility(View.GONE);
			
			// System.out.println("meida   :" + data);

			try {
				if (data.getInt("errorcode") == 0) {
					JSONObject obj = data.getJSONObject("dataresult");
					
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
					Toast.makeText(MediaDetailActivity.this, str, 1).show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					
					Toast.makeText(MediaDetailActivity.this,Consts.UNKNOWN_FAULT, 1).show();
				}
			}
		}
	};
	
	public void backHandler(View v) {
		finish();
	}

}
