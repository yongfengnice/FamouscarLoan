package com.famous.zhifu.loan.famouscarloan.account;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.activity.ActivityInterface;
import com.famous.zhifu.loan.activity.Consts;
import com.famous.zhifu.loan.internet.ServiceClient;
import com.famous.zhifu.loan.util.NetUtil;
import com.famous.zhifu.loan.util.Tools;

public class EmailVerifyActivity extends Activity implements ActivityInterface,
		OnClickListener {
	/** 获取验证码Handler */
	private static final int MSG_GETCODE = 1001;
	private View mView;
	private TextView mTvTitle, getcode;
	private Button submit;
	private EditText editEmail, editCode;
	private String email;

	/** 记录倒计时的一分钟 */
	private int mimute = 60;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == MSG_GETCODE) {
				mimute = mimute - 1;
				getcode.setText(mimute + "秒后重新获取");
				if (mimute == 0) {
					getcode.setText("获取验证码");
					mimute = 60;
				} else {
					handler.postDelayed(new Runnable() {
						@Override
						public void run() {
							handler.sendEmptyMessage(MSG_GETCODE);
						}
					}, 1000);
				}
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		mView = getLayoutInflater().inflate(R.layout.activity_email_verify,
				null);
		setContentView(mView);
		findView();
		initData();
		addAction();
	}

	@Override
	public void findView() {
		mTvTitle = (TextView) mView.findViewById(R.id.main_header_tv_text);

		submit = (Button) mView.findViewById(R.id.submit);
		editEmail = (EditText) mView.findViewById(R.id.editEmail);
		editCode = (EditText) mView.findViewById(R.id.editCode);
		getcode = (TextView) mView.findViewById(R.id.getcode);
	}

	@Override
	public void initData() {
		mTvTitle.setText(getResources().getString(R.string.verify_email));
	}

	@Override
	public void addAction() {
		submit.setOnClickListener(this);
		getcode.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_header_iv_left:
			finish();
			break;
		case R.id.submit:
			submitEmailData();
			break;
		case R.id.getcode:
			email = editEmail.getText().toString().trim();
			if (Tools.isEmpty(email)) {
				Toast.makeText(this, "请输入您的邮箱地址", 1).show();
			} else if (!Tools.isEmail(email)) {
				Toast.makeText(this, "请输入正确的邮箱地址", 1).show();
			} else {
				sendCode(email);
			}
			break;

		default:
			break;
		}
	}

	public void sendCode(String email) {
		if (NetUtil.isNetworkConnected(this)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("type", "email");
			params.put("act", "get_email_verify");
			params.put("email", email);
			ServiceClient.call(Consts.NetWork.BINDSUBMIT, params, codeback);
		} else {
			Toast.makeText(this, Consts.NONETWORK, 1).show();
		}
	}

	ServiceClient.Callback codeback = new ServiceClient.Callback() {
		@Override
		public void callback(JSONObject data) {
			try {
				if (data.getInt("errorcode") == 0) {
					String result = data.getString("dataresult");
					Toast.makeText(EmailVerifyActivity.this, result, 1).show();
					handler.sendEmptyMessage(MSG_GETCODE);
				}
			} catch (JSONException e) {
				e.printStackTrace();
				try {
					String str = data.getString("errormsg");
					Toast.makeText(EmailVerifyActivity.this, str, 1).show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					Toast.makeText(EmailVerifyActivity.this,
							Consts.UNKNOWN_FAULT, 1).show();
				}
			}
		}
	};

	public void submitEmailData() {
		String code = editCode.getText().toString().trim();
		if (Tools.isEmpty(code)) {
			Toast.makeText(EmailVerifyActivity.this, "请输入验证码", 1).show();
		} else {
			if (NetUtil.isNetworkConnected(this)) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("type", "email");
				params.put("act", "submit_email");
				params.put("verify_emailcode", code);
				params.put("email", email);
				ServiceClient.call(Consts.NetWork.BINDSUBMIT, params,
						verifyback);
			} else {
				Toast.makeText(this, Consts.NONETWORK, 1).show();
			}
		}
	}

	ServiceClient.Callback verifyback = new ServiceClient.Callback() {
		@Override
		public void callback(JSONObject data) {
			// System.out.println("邮箱验证  :" + data);
			try {
				if (data.getInt("errorcode") == 0) {
					String result = data.getString("dataresult");
					Toast.makeText(EmailVerifyActivity.this, result, 1).show();
					setResult(1002);
					EmailVerifyActivity.this.finish();
				}
			} catch (JSONException e) {
				e.printStackTrace();
				try {
					String str = data.getString("errormsg");
					Toast.makeText(EmailVerifyActivity.this, str, 1).show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					Toast.makeText(EmailVerifyActivity.this,
							Consts.UNKNOWN_FAULT, 1).show();
				}
			}
		}
	};
	
	public void backHandler(View v){
		finish();
	}

}
