package com.famous.zhifu.loan.famouscarloan.home;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.activity.ActivityInterface;
import com.famous.zhifu.loan.activity.BaseActivity;
import com.famous.zhifu.loan.activity.Consts;
import com.famous.zhifu.loan.internet.ServiceClient;
import com.famous.zhifu.loan.util.NetUtil;
import com.famous.zhifu.loan.util.Tools;
import com.famous.zhifu.loan.view.dialog.AuthCodePopup;

public class FindPasswordActivity extends BaseActivity implements
		ActivityInterface {
	/** 获取验证码Handler */
	private static final int MSG_GETCODE = 1001;
	/** 注册Handler */
	private static final int MSG_REG = 1002;

	private View mView;
	private Button mBtnNext;
	private TextView mTvTitle, getcode;
	private ImageView mIvRight;
	private EditText editPhone, editPhoneCode;
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
				FindPasswordActivity.this.finish();
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mView = getLayoutInflater().inflate(R.layout.find_password, null);
		setContentView(mView);
		findView();
		initData();
		addAction();
	}

	@Override
	public void findView() {
		mBtnNext = (Button) mView.findViewById(R.id.find_password_btn_next);
		mTvTitle = (TextView) mView.findViewById(R.id.main_header_tv_text);
		mIvRight = (ImageView) mView.findViewById(R.id.main_header_iv_right);
		mView.findViewById(R.id.main_header_iv_left).setOnClickListener(
				new MyClickListener());

		editPhone = (EditText) mView.findViewById(R.id.editPhone);
		editPhoneCode = (EditText) mView.findViewById(R.id.editPhoneCode);
		getcode = (TextView) mView.findViewById(R.id.getcode);
	}

	@Override
	public void initData() {
		mTvTitle.setText(getString(R.string.find_password));
		mIvRight.setVisibility(View.GONE);
	}

	@Override
	public void addAction() {
		mBtnNext.setOnClickListener(new MyClickListener());
		getcode.setOnClickListener(new MyClickListener());
	}

	private class MyClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.main_header_iv_left:
				finish();
				break;
			case R.id.getcode:
				phone = editPhone.getText().toString().trim();
				if (NetUtil.isNetworkConnected(FindPasswordActivity.this)) {
					if (phone.length() == 0) {
						Toast.makeText(FindPasswordActivity.this, "请输入手机号码", 1)
								.show();
						editPhone.setText("");
						editPhone.requestFocus();
					} else if (!Tools.isPhone(phone)) {
						Toast.makeText(FindPasswordActivity.this, "请正确输入手机号码",
								1).show();
						editPhone.requestFocus();
					} else {
						toShowDialog(phone);
//						toGetCode();
					}
				} else {
					Toast.makeText(FindPasswordActivity.this, Consts.NONETWORK,
							1).show();
				}
				break;
			case R.id.find_password_btn_next:// 下一步
				// startActivity(new Intent(FindPasswordActivity.this,
				// FindPasswordVerifyActivity.class));
				if (NetUtil.isNetworkConnected(FindPasswordActivity.this)) {
					toFindPwd();
				} else {
					Toast.makeText(FindPasswordActivity.this, Consts.NONETWORK,
							1).show();
				}
				break;
			}
		}
	}
	
	//弹出图像验证码
	private void toShowDialog(String phone){
		if (null==popup) {
			popup = new AuthCodePopup(this,handler,phone,Consts.NetWork.RECOVERPWD,"get_mobileverify");
		}
		
		popup.triggerClick();
		popup.showAtLocation(mView, Gravity.CENTER, 0, 0);
	}

	// 获取验证码
	public void toGetCode() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", "get_mobileverify");
		params.put("mobile", phone);
		ServiceClient.call(Consts.NetWork.RECOVERPWD, params, verifyCallback);
	}

	ServiceClient.Callback verifyCallback = new ServiceClient.Callback() {
		@Override
		public void callback(JSONObject data) {
			// System.out.println(data);
			try {
				if (data.getInt("errorcode") == 0) {
					String result = data.getString("dataresult");
					Toast.makeText(FindPasswordActivity.this, result, 1).show();

					handler.sendEmptyMessage(MSG_GETCODE);
				}
			} catch (JSONException e) {
				try {
					String str = data.getString("errormsg");
					Toast.makeText(FindPasswordActivity.this, str, 1).show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					Toast.makeText(FindPasswordActivity.this,
							Consts.UNKNOWN_FAULT, 1).show();
				}
			}
		}
	};

	public void toFindPwd() {
		String code = editPhoneCode.getText().toString().trim();
		if (code.length() == 0) {
			Toast.makeText(this, "请输入语音验证码", 1).show();
		} else {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("type", "recover");
			params.put("mobile", phone);
			params.put("verify_code", code);
			ServiceClient.call(Consts.NetWork.RECOVERPWD, params, callback);
		}
	}

	ServiceClient.Callback callback = new ServiceClient.Callback() {
		@Override
		public void callback(JSONObject ret) {
			// System.out.println("找回密码   :" + ret);
			try {
				if (ret.getInt("errorcode") == 0) {
					ret.get("dataresult");
					if (!TextUtils.isEmpty(ret.get("dataresult").toString())) {
						Toast.makeText(FindPasswordActivity.this,
								ret.getString("dataresult"), 1).show();
						FindPasswordActivity.this.finish();
					}
				}
			} catch (JSONException e) {
				try {
					String str = ret.getString("errormsg");
					Toast.makeText(FindPasswordActivity.this, str, 1).show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					Toast.makeText(FindPasswordActivity.this,
							Consts.UNKNOWN_FAULT, 1).show();
				}
			}
		}

	};
}
