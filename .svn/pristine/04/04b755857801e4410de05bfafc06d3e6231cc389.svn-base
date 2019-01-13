package com.famous.zhifu.loan.famouscarloan.home;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.activity.ActivityInterface;
import com.famous.zhifu.loan.activity.Consts;
import com.famous.zhifu.loan.internet.ServiceClient;
import com.famous.zhifu.loan.util.NetUtil;
import com.famous.zhifu.loan.util.Tools;
import com.famous.zhifu.loan.view.dialog.AuthCodePopup;

public class JoinUsActivity extends Activity implements ActivityInterface,
		OnClickListener {
	/** 获取验证码Handler */
	private static final int MSG_GETCODE = 1001;
	private View mView;
	private TextView mTvTitle, getcode;
	private EditText editName, editPhone, editPhoneCode;
	private Button submit;
	
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
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		mView = getLayoutInflater().inflate(R.layout.join_us, null);
		setContentView(mView);
		findView();
		initData();
		addAction();
	}

	@Override
	public void findView() {
		mTvTitle = (TextView) mView.findViewById(R.id.main_header_tv_text);

		editName = (EditText) mView.findViewById(R.id.editName);
		editPhone = (EditText) mView.findViewById(R.id.editPhone);
		editPhoneCode = (EditText) mView.findViewById(R.id.editPhoneCode);
		getcode = (TextView) mView.findViewById(R.id.getcode);
		submit = (Button) mView.findViewById(R.id.submit);
	}

	@Override
	public void initData() {
		mTvTitle.setText(getString(R.string.join_us));
	}

	@Override
	public void addAction() {
		submit.setOnClickListener(this);
		getcode.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.getcode:
			if (NetUtil.isNetworkConnected(this)) {
				sendCode();
			} else {
				Toast.makeText(this, Consts.NONETWORK, 1).show();
			}
			break;
		case R.id.submit:
			if (NetUtil.isNetworkConnected(this)) {
				toSubmit();
			} else {
				Toast.makeText(this, Consts.NONETWORK, 1).show();
			}
			break;

		default:
			break;
		}
	}
	
	//弹出图像验证码
	private void toShowDialog(String phone){
		if (null==popup) {
			popup = new AuthCodePopup(this,handler,phone,Consts.NetWork.JOIN,"get_smscode");
		}
		
		popup.triggerClick();
		popup.showAtLocation(mView, Gravity.CENTER, 0, 0);
	}

	public void sendCode() {
		String phone = editPhone.getText().toString().trim();
		if (Tools.isEmpty(phone)) {
			Toast.makeText(this, "请输入您的手机号码", 1).show();
		} else if(!Tools.isPhone(phone)){
			Toast.makeText(this, "请输入正确的手机号码", 1).show();
		}else {
			toShowDialog(phone);
			
			/*Map<String, Object> params = new HashMap<String, Object>();
			params.put("type", "get_smscode");
			params.put("mobile", phone);
			ServiceClient.call(Consts.NetWork.JOIN, params, codeback);*/
		}
	}

	ServiceClient.Callback codeback = new ServiceClient.Callback() {
		@Override
		public void callback(JSONObject ret) {
			// System.out.println("ret :"+ret);
			try {
				if (ret.getInt("errorcode") == 0) {
					String result = ret.getString("dataresult");
					Toast.makeText(JoinUsActivity.this, result, 1).show();

					handler.sendEmptyMessage(MSG_GETCODE);
				}
			} catch (JSONException e) {
				try {
					String str = ret.getString("errormsg");
					Toast.makeText(JoinUsActivity.this, str, 1).show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					Toast.makeText(JoinUsActivity.this, Consts.UNKNOWN_FAULT, 1)
							.show();
				}
			}
		}
	};

	// 注册
	public void toSubmit() {
		String name = editName.getText().toString().trim();
		String phone = editPhone.getText().toString().trim();
		String code = editPhoneCode.getText().toString().trim();

		if (Tools.isEmpty(name)) {
			Toast.makeText(this, "请输入您的姓名", 1).show();
		} else if (Tools.isEmpty(phone)) {
			Toast.makeText(this, "请输入您的手机号码", 1).show();
		} else if (Tools.isEmpty(code)) {
			Toast.makeText(this, "请输入您的验证码", 1).show();
		} else {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("type", "join_submit");
			params.put("name", name);
			params.put("mobile", phone);
			params.put("smscode", code);
			ServiceClient.call(Consts.NetWork.JOIN, params, callback);
		}
	}

	ServiceClient.Callback callback = new ServiceClient.Callback() {
		@Override
		public void callback(JSONObject ret) {
			// System.out.println("加入我们   :" + ret);
			try {
				if (ret.getInt("errorcode") == 0) {
					String str = ret.get("dataresult").toString();
					Toast.makeText(JoinUsActivity.this, str, 1).show();
					
					JoinUsActivity.this.finish();
				}
			} catch (JSONException e) {
				try {
					String str = ret.getString("errormsg");
					Toast.makeText(JoinUsActivity.this, str, 1).show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					Toast.makeText(JoinUsActivity.this, Consts.UNKNOWN_FAULT, 1)
							.show();
				}
			}
		}
	};
	
	public void backHandler(View v) {
		finish();
	}

}
