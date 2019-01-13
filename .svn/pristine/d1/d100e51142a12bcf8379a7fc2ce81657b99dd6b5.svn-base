package com.famous.zhifu.loan.famouscarloan.home;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
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

public class LoanMoneyActivity extends Activity implements ActivityInterface {
	/** 获取验证码Handler */
	private static final int MSG_GETCODE = 1001;
	private View mView;
	private TextView mTvTitle, getcode;
	private Button mBtnSubmit;
	private EditText editName, editPhone, editPhoneCode, editCar, editNum;
	
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
		mView = getLayoutInflater().inflate(R.layout.activity_loan_money, null);
		setContentView(mView);
		findView();
		initData();
		addAction();
	}

	@Override
	public void findView() {
		mTvTitle = (TextView) mView.findViewById(R.id.main_header_tv_text);

		mBtnSubmit = (Button) mView.findViewById(R.id.loan_btn_confirm_submit);
		editName = (EditText) mView.findViewById(R.id.editName);
		editPhone = (EditText) mView.findViewById(R.id.editPhone);
		editPhoneCode = (EditText) mView.findViewById(R.id.editPhoneCode);
		editCar = (EditText) mView.findViewById(R.id.editCar);
		editNum = (EditText) mView.findViewById(R.id.editNum);
		getcode = (TextView) mView.findViewById(R.id.getcode);
	}

	@Override
	public void initData() {
		mTvTitle.setText(getResources().getString(R.string.right_away_invest));

	}

	@Override
	public void addAction() {
		mBtnSubmit.setOnClickListener(new MyClickListener());
		getcode.setOnClickListener(new MyClickListener());
	}

	private class MyClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.main_header_iv_left:
				LoanMoneyActivity.this.finish();
				break;
			case R.id.getcode:
				if (NetUtil.isNetworkConnected(LoanMoneyActivity.this)) {
					toGetCode();
				} else {
					Toast.makeText(LoanMoneyActivity.this, Consts.NONETWORK, 1).show();
				}
				break;
			case R.id.loan_btn_confirm_submit:// 提交申请~
				if (NetUtil.isNetworkConnected(LoanMoneyActivity.this)) {
					toSubmit();
				} else {
					Toast.makeText(LoanMoneyActivity.this, Consts.NONETWORK, 1)
							.show();
				}
				break;
			}
		}
	}
	
	//弹出图像验证码
	private void toShowDialog(String phone){
		if (null==popup) {
			popup = new AuthCodePopup(this,handler,phone,Consts.NetWork.BORROW,"get_mobileverify");
		}
		
		popup.triggerClick();
		popup.showAtLocation(mView, Gravity.CENTER, 0, 0);
	}

	// 获取验证码
	public void toGetCode() {
		String phone = editPhone.getText().toString().trim();
		if (Tools.isEmpty(phone)) {
			Toast.makeText(this, "请输入您的手机号", 1).show();
		} else if (!Tools.isPhone(phone)) {
			Toast.makeText(this, "请输入正确手机号码", 1).show();
		} else {
			toShowDialog(phone);
			/*Map<String, Object> params = new HashMap<String, Object>();
			params.put("type", "get_mobileverify");
			params.put("mobile", phone);
			ServiceClient.call(Consts.NetWork.BORROW, params, verifyCallback);*/
		}
	}

	ServiceClient.Callback verifyCallback = new ServiceClient.Callback() {
		@Override
		public void callback(JSONObject data) {
			// System.out.println(data);
			try {
				if (data.getInt("errorcode") == 0) {
					String result = data.getString("dataresult");
					Toast.makeText(LoanMoneyActivity.this, result, 1).show();

					handler.sendEmptyMessage(MSG_GETCODE);
				}
			} catch (JSONException e) {
				e.printStackTrace();
				try {
					String str = data.getString("errormsg");
					Toast.makeText(LoanMoneyActivity.this, str, 1).show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					Toast.makeText(LoanMoneyActivity.this,
							Consts.UNKNOWN_FAULT, 1).show();
				}
			}
		}
	};

	public void toSubmit() {
		String name = editName.getText().toString().trim();
		String phone = editPhone.getText().toString().trim();
		String code = editPhoneCode.getText().toString().trim();
		String car = editCar.getText().toString().trim();
		String num = editNum.getText().toString().trim();

		if (Tools.isEmpty(name)) {
			Toast.makeText(this, "请输入您的真实姓名", 1).show();
		} else if (Tools.isEmpty(phone)) {
			Toast.makeText(this, "请输入您的手机号", 1).show();
		} else if (Tools.isEmpty(code)) {
			Toast.makeText(this, "请输入您的验证码", 1).show();
		} else if (Tools.isEmpty(car)) {
			Toast.makeText(this, "请输入您的车辆型号", 1).show();
		} else if (Tools.isEmpty(num)) {
			Toast.makeText(this, "请输入您的借款金额", 1).show();
		} else {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("type", "quick_loan");
			params.put("mobile", phone);
			params.put("username", name);
			params.put("amount", num);
			params.put("verify_code", code);
			params.put("certinumber", car);
			ServiceClient.call(Consts.NetWork.BORROW, params, callback);
		}
	}

	ServiceClient.Callback callback = new ServiceClient.Callback() {
		@Override
		public void callback(JSONObject data) {
			// System.out.println("申请借款  ：" + data);
			try {
				if (data.getInt("errorcode") == 0) {
					String result = data.getString("dataresult");
					Toast.makeText(LoanMoneyActivity.this, result, 1).show();

					LoanMoneyActivity.this.finish();
					startActivity(new Intent(LoanMoneyActivity.this,LoanMoneyConfirmActivity.class));
				}
			} catch (JSONException e) {
				try {
					String str = data.getString("errormsg");
					Toast.makeText(LoanMoneyActivity.this, str, 1).show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					Toast.makeText(LoanMoneyActivity.this,
							Consts.UNKNOWN_FAULT, 1).show();
				}
			}
		}
	};

	public void backHandler(View v) {
		finish();
	}

}
