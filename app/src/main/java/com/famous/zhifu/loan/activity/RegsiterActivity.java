package com.famous.zhifu.loan.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.internet.ServiceClient;
import com.famous.zhifu.loan.util.NetUtil;
import com.famous.zhifu.loan.util.SharePrefUtil;
import com.famous.zhifu.loan.util.Tools;
import com.famous.zhifu.loan.view.dialog.AuthCodePopup;

public class RegsiterActivity extends Activity implements ActivityInterface {
	/** 获取验证码Handler */
	private static final int MSG_GETCODE = 1001;
	/** 注册Handler */
	private static final int MSG_REG = 1002;

	private LinearLayout protocol;
	private View mView;
	private TextView mTvTitle, getcode;
	private Button registerBtn;
	private EditText editUsername, editPassword, editAgainPwd, editPhone,editPhoneCode,EditInvitecode ;
	private String phone = "";
	
	private AuthCodePopup popup = null;

	/** 记录倒计时的一分钟 */
	private int mimute = 60;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == MSG_GETCODE) {
				mimute = mimute - 1;
				getcode.setClickable(false);
				getcode.setText(mimute + "秒后重新获取");
				
				if (mimute == 0) {
					getcode.setText("获取验证码");
					mimute = 60;
					
					getcode.setClickable(true);
				} else {
					handler.postDelayed(new Runnable() {
						@Override
						public void run() {
							handler.sendEmptyMessage(MSG_GETCODE);
						}
					}, 1000);
				}
			} else if (msg.what == MSG_REG) {
				Toast.makeText(RegsiterActivity.this, "注册成功", 1).show();
				setResult(888);
				RegsiterActivity.this.finish();
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		mView = getLayoutInflater().inflate(R.layout.activity_resigter, null);
		setContentView(mView);
		findView();
		initData();
		addAction();
	}

	@Override
	public void findView() {
		registerBtn = (Button) mView.findViewById(R.id.registerBtn);
		getcode = (TextView) mView.findViewById(R.id.getcode);
		mTvTitle = (TextView) mView.findViewById(R.id.main_header_tv_text);

		editUsername = (EditText) mView.findViewById(R.id.editUsername);
		editPassword = (EditText) mView.findViewById(R.id.editPassword);
		editAgainPwd = (EditText) mView.findViewById(R.id.editAgainPwd);
		editPhone = (EditText) mView.findViewById(R.id.editPhone);
		editPhoneCode = (EditText) mView.findViewById(R.id.editPhoneCode);
		EditInvitecode = (EditText) mView.findViewById(R.id.invitecode);

		protocol = (LinearLayout) mView.findViewById(R.id.protocol);
	}

	@Override
	public void initData() {
		mTvTitle.setText(getString(R.string.user_resigter));
	}

	@Override
	public void addAction() {
		getcode.setOnClickListener(new MyOnClickListener());
		registerBtn.setOnClickListener(new MyOnClickListener());
		protocol.setOnClickListener(new MyOnClickListener());
	}

	private class MyOnClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			
			case R.id.getcode:
				phone = editPhone.getText().toString().trim();
				Log.i("", phone);
				
				if (NetUtil.isNetworkConnected(RegsiterActivity.this)) {
					if (phone.length() == 0) {
						Toast.makeText(RegsiterActivity.this, "请输入手机号码", 1)
								.show();
						editPhone.setText("");
						editPhone.requestFocus();
					} else if (!Tools.isPhone(phone)) {
						Toast.makeText(RegsiterActivity.this, "请正确输入手机号码", 1)
								.show();
						editPhone.requestFocus();
					} else {
						toShowDialog(phone);
//						toGetCode();
					}
				} else {
					Toast.makeText(RegsiterActivity.this, Consts.NONETWORK, 1)
							.show();
				}
				break;
				
			case R.id.registerBtn:
				if (NetUtil.isNetworkConnected(RegsiterActivity.this)) {
					toReg();
				} else {
					Toast.makeText(RegsiterActivity.this, Consts.NONETWORK, 1).show();
				}
				break;
			case R.id.protocol:
				String url = "http://service.zhifu360.com/protocol";
				Intent intent = new Intent(RegsiterActivity.this,WebViewActivity.class);
				intent.putExtra("apipath", url);
				startActivity(intent);
				break;
			}
		}
	}
	
	//弹出图像验证码
	private void toShowDialog(String phone){
		if (null==popup) {
			popup = new AuthCodePopup(this,handler,phone,Consts.NetWork.SENDCODE,"get_mobileverify");
		}
		
		popup.triggerClick();
		popup.showAtLocation(mView, Gravity.CENTER, 0, 0);
	}

	// 获取验证码
	public void toGetCode() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", "get_mobileverify");
		params.put("mobile", phone);
		ServiceClient.call(Consts.NetWork.SENDCODE, params, verifyCallback);
	}

	ServiceClient.Callback verifyCallback = new ServiceClient.Callback() {
		@Override
		public void callback(JSONObject data) {
			// System.out.println(data);
			try {
				if (data.getInt("errorcode") == 0) {
					String result = data.getString("dataresult");
					Toast.makeText(RegsiterActivity.this, result, 1).show();

					handler.sendEmptyMessage(MSG_GETCODE);
				}
			} catch (JSONException e) {
				try {
					String str = data.getString("errormsg");
					Toast.makeText(RegsiterActivity.this, str, 1).show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					Toast.makeText(RegsiterActivity.this, Consts.UNKNOWN_FAULT,1).show();
				}
			}
		}
	};

	// 注册
	public void toReg() {
		String userName = editUsername.getText().toString().trim();
		String password = editPassword.getText().toString().trim();
		String code = editPhoneCode.getText().toString().trim();
		String againPwd = editAgainPwd.getText().toString().trim();

		if (!userName.matches("^[\u4e00-\u9fa5]{0,}$")
				&& (userName.length() < 6 || userName.length() > 16)) {
			Toast.makeText(this, "请输入6-16位字符的账号名称", 1).show();
		} else if (password.length() == 0) {
			Toast.makeText(this, "请输入账号密码", 1).show();
		} else if (password.length() < 6 || password.length() > 16) {
			Toast.makeText(this, "请输入6-16位字符的密码", 1).show();
		} else if (!Tools.isEquals(againPwd, password)) {
			Toast.makeText(this, "两次密码不一致", 1).show();
		} else if (phone.length() == 0) {
			Toast.makeText(this, "请收入手机号码", 1).show();
		} else if (code.length() == 0) {
			Toast.makeText(this, "请输入语音验证码", 1).show();
		} else {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("type", "reg");
			params.put("mobile", phone);
			params.put("username", userName);
			params.put("password", password);
			params.put("re_password", againPwd);
			params.put("verify_code", code);
			params.put("invitecode", EditInvitecode.getText().toString());

			ServiceClient.call(Consts.NetWork.SENDCODE, params, regCallback);
		}
	}

	ServiceClient.Callback regCallback = new ServiceClient.Callback() {
		@Override
		public void callback(JSONObject ret) {
			// System.out.println(ret);
			try {
				if (ret.getInt("errorcode") == 0) {
					if (!TextUtils.isEmpty(ret.get("dataresult").toString())) {
						JSONObject json = ret.getJSONObject("dataresult");

						MyApp.token = json.getString("_token_");
						MyApp.username = json.getString("username");

						SharePrefUtil.saveString(RegsiterActivity.this,
								Consts.SharePref.USERNAME, MyApp.username);
						SharePrefUtil.saveUserInfoString(RegsiterActivity.this,
								Consts.SharePref.TOKEN, MyApp.token);

						handler.sendEmptyMessage(MSG_REG);
					}
				}
			} catch (JSONException e) {
				try {
					String str = ret.getString("errormsg");
					Toast.makeText(RegsiterActivity.this, str, 1).show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					Toast.makeText(RegsiterActivity.this, Consts.UNKNOWN_FAULT,
							1).show();
				}
			}
		}
	};
	
	
	public void backHandler(View view){
		finish();
	}
}
