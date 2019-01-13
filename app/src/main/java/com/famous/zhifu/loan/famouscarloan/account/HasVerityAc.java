package com.famous.zhifu.loan.famouscarloan.account;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.activity.Consts;
import com.famous.zhifu.loan.internet.ServiceClient;
import com.famous.zhifu.loan.util.NetUtil;

public class HasVerityAc extends Activity {

	private TextView mTvTitle;
	private LinearLayout ll_name, ll_phone, ll_email, ll_bank;
	private EditText editName, editIdcard, editPhone, editEmail, editBankName,
			name, editBankBranch, editBankCard;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.has_verify);
		intView();
	}

	public void intView() {
		String str = getIntent().getStringExtra("hasVerify");

		mTvTitle = (TextView) findViewById(R.id.main_header_tv_text);
		ll_name = (LinearLayout) findViewById(R.id.ll_name);
		ll_phone = (LinearLayout) findViewById(R.id.ll_phone);
		ll_email = (LinearLayout) findViewById(R.id.ll_email);
		ll_bank = (LinearLayout) findViewById(R.id.ll_bank);

		editName = (EditText) findViewById(R.id.editName);
		editIdcard = (EditText) findViewById(R.id.editIdcard);
		editPhone = (EditText) findViewById(R.id.editPhone);
		editEmail = (EditText) findViewById(R.id.editEmail);

		name = (EditText) findViewById(R.id.name);
		editBankName = (EditText) findViewById(R.id.editBankName);
		editBankBranch = (EditText) findViewById(R.id.editBankBranch);
		editBankCard = (EditText) findViewById(R.id.editBankCard);


		if (str.equals("realName")) {
			mTvTitle.setText("实名认证");
			ll_name.setVisibility(View.VISIBLE);
			getNameData();
		} else if (str.equals("phone")) {
			mTvTitle.setText("手机绑定");
			ll_phone.setVisibility(View.VISIBLE);
			getPhoneData();
		} else if (str.equals("bank")) {
			mTvTitle.setText("银行卡绑定");
			ll_bank.setVisibility(View.VISIBLE);
			getBankData();
		} else if (str.equals("email")) {
			mTvTitle.setText("邮箱绑定");
			ll_email.setVisibility(View.VISIBLE);
			getEmailData();
		}
	}

	public void getNameData() {
		if (NetUtil.isNetworkConnected(this)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("type", "realname");
			ServiceClient.call(Consts.NetWork.VERIFY, params, verifyback);
		} else {
			Toast.makeText(this, Consts.NONETWORK, 1).show();
		}
	}

	ServiceClient.Callback verifyback = new ServiceClient.Callback() {
		@Override
		public void callback(JSONObject data) {
			// System.out.println("已认证  " + data);
			try {
				if (data.getInt("errorcode") == 0) {
					JSONObject obj = data.getJSONObject("dataresult");
					editName.setText(obj.getString("realname"));
					editIdcard.setText(obj.getString("certinumber"));
				}
			} catch (JSONException e) {
				e.printStackTrace();
				try {
					String str = data.getString("errormsg");
					Toast.makeText(HasVerityAc.this, str, 1).show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					Toast.makeText(HasVerityAc.this, Consts.UNKNOWN_FAULT, 1)
							.show();
				}
			}
		}
	};

	public void getPhoneData() {
		if (NetUtil.isNetworkConnected(this)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("type", "mobile");
			ServiceClient.call(Consts.NetWork.VERIFY, params, verifyback1);
		} else {
			Toast.makeText(this, Consts.NONETWORK, 1).show();
		}
	}

	ServiceClient.Callback verifyback1 = new ServiceClient.Callback() {
		@Override
		public void callback(JSONObject data) {
			// System.out.println("已认证  " + data);
			try {
				if (data.getInt("errorcode") == 0) {
					JSONObject obj = data.getJSONObject("dataresult");
					editPhone.setText(obj.getString("mobile"));
				}
			} catch (JSONException e) {
				e.printStackTrace();
				try {
					String str = data.getString("errormsg");
					Toast.makeText(HasVerityAc.this, str, 1).show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					Toast.makeText(HasVerityAc.this, Consts.UNKNOWN_FAULT, 1)
							.show();
				}
			}
		}
	};

	public void getEmailData() {
		if (NetUtil.isNetworkConnected(this)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("type", "email");
			ServiceClient.call(Consts.NetWork.VERIFY, params, verifyback2);
		} else {
			Toast.makeText(this, Consts.NONETWORK, 1).show();
		}
	}

	ServiceClient.Callback verifyback2 = new ServiceClient.Callback() {
		@Override
		public void callback(JSONObject data) {
			// System.out.println("已认证  " + data);
			try {
				if (data.getInt("errorcode") == 0) {
					JSONObject obj = data.getJSONObject("dataresult");
					editEmail.setText(obj.getString("email"));
				}
			} catch (JSONException e) {
				e.printStackTrace();
				try {
					String str = data.getString("errormsg");
					Toast.makeText(HasVerityAc.this, str, 1).show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					Toast.makeText(HasVerityAc.this, Consts.UNKNOWN_FAULT, 1)
							.show();
				}
			}
		}
	};
	
	public void getBankData() {
		if (NetUtil.isNetworkConnected(this)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("type", "bank");
			ServiceClient.call(Consts.NetWork.VERIFY, params, verifyback3);
		} else {
			Toast.makeText(this, Consts.NONETWORK, 1).show();
		}
	}

	ServiceClient.Callback verifyback3 = new ServiceClient.Callback() {
		@Override
		public void callback(JSONObject data) {
			// System.out.println("银行卡认证  " + data);
			try {
				if (data.getInt("errorcode") == 0) {
					JSONObject obj = data.getJSONObject("dataresult");
					name.setText(obj.getString("bankusername"));
					editBankName.setText(obj.getString("bankname"));
					editBankBranch.setText(obj.getString("banksubbranch"));
					editBankCard.setText(obj.getString("banknum"));
				}
			} catch (JSONException e) {
				e.printStackTrace();
				try {
					String str = data.getString("errormsg");
					Toast.makeText(HasVerityAc.this, str, 1).show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					Toast.makeText(HasVerityAc.this, Consts.UNKNOWN_FAULT, 1)
							.show();
				}
			}
		}
	};
	
	public void backHandler(View v){
		finish();
	}

}
