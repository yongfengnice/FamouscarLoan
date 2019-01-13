package com.famous.zhifu.loan.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.view.View_Loading;

public class WebViewActivity extends Activity {
	private WebView contailView;
	private WebThread webThread;
	private String titlename, url;
	private TextView title;
	private View_Loading loading;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.webview_cb);
		intentdate();
		initMainViews();
		initDatas();
	}

	private void intentdate() {
		Intent intent = getIntent();
		url = intent.getStringExtra("apipath");
		// System.out.println("apipath  :" + url);
		
		titlename = intent.getStringExtra("titlename");
	}

	private void initDatas() {
		webThread = new WebThread();
		webThread.execute(url);
	}

	private void initMainViews() {
		loading = (View_Loading) findViewById(R.id.loading);
		contailView = (WebView) this.findViewById(R.id.webview);
		title = (TextView) this.findViewById(R.id.main_header_tv_text);
		title.setText("智富360");
		contailView.getSettings().setSupportZoom(true);
		contailView.setWebViewClient(vieClient);

	}

	WebViewClient vieClient = new WebViewClient() {

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			loading.setVisibility(View.GONE);
		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			loading.setVisibility(View.VISIBLE);
			contailView.loadUrl(url);
			return true;
		}

	};

	class WebThread extends AsyncTask<String, Void, Void> {
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			loading.setVisibility(View.VISIBLE);
		}
		
		@Override
		protected Void doInBackground(String... params) {
			String url = params[0];
			Message message = handler.obtainMessage(1, url);
			handler.sendMessage(message);
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			loading.setVisibility(View.GONE);
		}
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			if (what == 1) {
				String url = (String) msg.obj;
				contailView.loadUrl(url);
			}
		};
	};

	public void backHandler(View v) {
		finish();
	}

}
