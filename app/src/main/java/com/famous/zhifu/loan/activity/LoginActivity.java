package com.famous.zhifu.loan.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.famouscarloan.home.FindPasswordActivity;
import com.famous.zhifu.loan.internet.ServiceClient;
import com.famous.zhifu.loan.util.NetUtil;
import com.famous.zhifu.loan.util.SharePrefUtil;
import com.famous.zhifu.loan.view.View_Loading;

public class LoginActivity extends Activity implements ActivityInterface {
	private View mView;
	private TextView mTvTitle, mTvLogin, mIvRight;
	private ImageView remenber_img;
	private EditText ed_username, ed_password;
	private Button loginBtn;
	private LinearLayout remenber;
	/** 是否记住密码 */
	private boolean isRemenber = false;
	private View_Loading loading;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		mView = getLayoutInflater().inflate(R.layout.activity_login, null);
		setContentView(mView);
		findView();
		initData();
		addAction();
	}

	@Override
	public void findView() {
		mTvTitle = (TextView) mView.findViewById(R.id.main_header_tv_text);
		mTvLogin = (TextView) mView.findViewById(R.id.login_tv_find_password);
		mIvRight = (TextView) mView.findViewById(R.id.main_header_iv_right);
		mView.findViewById(R.id.main_header_iv_left).setOnClickListener(new MyOnClickListener());

		remenber = (LinearLayout) mView.findViewById(R.id.remenberUserName);
		remenber_img = (ImageView) findViewById(R.id.remenber_img);
		ed_username = (EditText) mView.findViewById(R.id.ed_username);
		ed_password = (EditText) mView.findViewById(R.id.ed_password);
		loginBtn = (Button) mView.findViewById(R.id.loginBtn);
		loading = (View_Loading) findViewById(R.id.loading);
	}

	@Override
	public void initData() {
		mTvTitle.setText(getString(R.string.user_login));
	}

	@Override
	public void addAction() {
		mTvLogin.setOnClickListener(new MyOnClickListener());
		mIvRight.setOnClickListener(new MyOnClickListener());
		loginBtn.setOnClickListener(new MyOnClickListener());
		remenber.setOnClickListener(new MyOnClickListener());
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 记住密码
		boolean remenber = SharePrefUtil.getBoolean(this,
				Consts.SharePref.REMENBER, false);
		isRemenber = remenber;
		if (isRemenber) {
			ed_username.setText(SharePrefUtil.getString(this,
					Consts.SharePref.USERNAME, ""));
			remenber_img.setBackgroundResource(R.drawable.chick_sure);
		}

	}

	private class MyOnClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.main_header_iv_left:
				
				finish();
				break;
			case R.id.login_tv_find_password:// 找回密码
				startActivity(new Intent(LoginActivity.this,FindPasswordActivity.class));
				break;
			case R.id.main_header_iv_right:// 注册~
				startActivityForResult(new Intent(LoginActivity.this,
						RegsiterActivity.class), 1001);
				break;
			case R.id.loginBtn:// 登陆
				requestLogin();
				break;
			case R.id.remenberUserName:// 登陆
				if (isRemenber) {
					remenber_img.setBackgroundResource(R.drawable.chick);
					isRemenber = false;
				} else {
					remenber_img.setBackgroundResource(R.drawable.chick_sure);
					isRemenber = true;
				}
				break;
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1001 && resultCode == 888) {
			finish();
		}
	}

	private boolean checkINput() {
		if (ed_username.getText().toString().trim().equals("")) {
			Toast.makeText(this, "请输入登录账号", 1).show();
			ed_username.setText("");
			ed_username.requestFocus();
			return false;
		} else if (ed_password.getText().toString().trim().equals("")) {
			Toast.makeText(this, "请输入密码", 1).show();
			ed_password.setText("");
			ed_password.requestFocus();
			return false;
		} else {
			return true;
		}
	}

	public void requestLogin() {
		if (NetUtil.isNetworkConnected(this)) {
			if (checkINput()) {
				loading.setVisibility(View.VISIBLE);
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("username", ed_username.getText().toString().trim());
				params.put("password", ed_password.getText().toString().trim());
				ServiceClient.call(Consts.NetWork.LOGIN, params, callback);
			}
		} else {
			Toast.makeText(this, Consts.NONETWORK, 1).show();
		}
	}

	ServiceClient.Callback callback = new ServiceClient.Callback() {
		@Override
		public void callback(JSONObject data) {
			// System.out.println("登陆  " + data);
			loading.setVisibility(View.GONE);
			try {
				if (data.getInt("errorcode") == 0) {
					JSONObject obj = (JSONObject) data.get("dataresult");
					if (!TextUtils.isEmpty(obj.toString())) {

						MyApp.isLogin = true;
						MyApp.username = obj.getString("username");
						MyApp.token = obj.getString("_token_");

						SharePrefUtil.saveBoolean(LoginActivity.this,Consts.SharePref.REMENBER, isRemenber);
						SharePrefUtil.saveString(LoginActivity.this,Consts.SharePref.USERNAME, MyApp.username);
						SharePrefUtil.saveUserInfoString(LoginActivity.this,Consts.SharePref.TOKEN, MyApp.token);

						Toast.makeText(LoginActivity.this, "登录成功", 1).show();
						
						setResult(11000);
						
						LoginActivity.this.finish();
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
				try {
					String str = data.getString("errormsg");
					Toast.makeText(LoginActivity.this, str, 1).show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					Toast.makeText(LoginActivity.this, Consts.UNKNOWN_FAULT, 1)
							.show();
				}
			}
		}
	};

}
