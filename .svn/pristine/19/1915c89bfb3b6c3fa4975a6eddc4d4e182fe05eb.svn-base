package com.famous.zhifu.loan.famouscarloan.account;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.famous.zhifu.loan.entity.City;
import com.famous.zhifu.loan.internet.ServiceClient;
import com.famous.zhifu.loan.util.NetUtil;
import com.famous.zhifu.loan.util.Tools;

public class CardVerifyActivity extends Activity implements ActivityInterface,
		OnClickListener {
	private View mView;
	private TextView mTvTitle, tvBank, tvBankArea;
	private Button submit;
	private EditText editBranch, editBankCard, editName;
	private City city;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		mView = getLayoutInflater()
				.inflate(R.layout.activity_card_verify, null);
		setContentView(mView);
		findView();
		initData();
		addAction();
		getNameData();
	}

	@Override
	public void findView() {
		mTvTitle = (TextView) mView.findViewById(R.id.main_header_tv_text);

		submit = (Button) mView.findViewById(R.id.submit);
		tvBank = (TextView) mView.findViewById(R.id.tvBank);
		tvBankArea = (TextView) mView.findViewById(R.id.editBankArea);
		editName = (EditText) mView.findViewById(R.id.editName);
		editBranch = (EditText) mView.findViewById(R.id.editBranch);
		editBankCard = (EditText) mView.findViewById(R.id.editBankCard);
	}

	@Override
	public void initData() {
		city = new City();
		mTvTitle.setText(getResources().getString(R.string.verify_card));
	}

	@Override
	public void addAction() {
		submit.setOnClickListener(this);
		tvBank.setOnClickListener(this);
		tvBankArea.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_header_iv_left:
			finish();
			break;
		case R.id.submit:
			submitBankData();
			break;
		case R.id.tvBank:
			startActivityForResult(new Intent(this, BankSelectActivity.class),
					1001);
			break;
		case R.id.editBankArea:
			Intent intent = new Intent(this, CitySelectAc.class);
			intent.putExtra("city", city);
			startActivityForResult(intent, 1001);
			break;

		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1001 && resultCode == 1002) {
			String name = data.getStringExtra("name");
			tvBank.setText(name);
		} else if (requestCode == 1001 && resultCode == 1009) {
			city = data.getParcelableExtra("city");
			tvBankArea.setText(city.getProvince() + " " + city.getCity());
		}
	}

	public void getNameData() {
		if (NetUtil.isNetworkConnected(this)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("type", "realname");
			ServiceClient.call(Consts.NetWork.VERIFY, params, callback);
		} else {
			Toast.makeText(this, Consts.NONETWORK, 1).show();
		}
	}

	ServiceClient.Callback callback = new ServiceClient.Callback() {
		@Override
		public void callback(JSONObject data) {
			// System.out.println("已认证  " + data);
			try {
				if (data.getInt("errorcode") == 0) {
					JSONObject obj = data.getJSONObject("dataresult");
					editName.setText(obj.getString("realname"));
				}
			} catch (JSONException e) {
				e.printStackTrace();
				try {
					String str = data.getString("errormsg");
					Toast.makeText(CardVerifyActivity.this, str, 1).show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					Toast.makeText(CardVerifyActivity.this,
							Consts.UNKNOWN_FAULT, 1).show();
				}
			}
		}
	};

	public void submitBankData() {
		String name = editName.getText().toString().trim();
		String branchBank = editBranch.getText().toString().trim();
		String bank = tvBank.getText().toString().trim();
		String cardNum = editBankCard.getText().toString().trim();
		String area = tvBankArea.getText().toString().trim();

		if (Tools.isEmpty(name)) {
			Toast.makeText(CardVerifyActivity.this, "请输入开户名", 1).show();
		} else if (Tools.isEmpty(bank)) {
			Toast.makeText(CardVerifyActivity.this, "请输入开户银行", 1).show();
		} else if (Tools.isEmpty(branchBank)) {
			Toast.makeText(CardVerifyActivity.this, "请输入开户支行", 1).show();
		} else if (Tools.isEmpty(cardNum)) {
			Toast.makeText(CardVerifyActivity.this, "请输入开户卡号", 1).show();
		} else if(!Tools.isBankCard(cardNum)){
			Toast.makeText(CardVerifyActivity.this, "请输入检查开户卡号是否正确", 1).show();
		}else if (Tools.isEmpty(area)) {
			Toast.makeText(CardVerifyActivity.this, "请输入开户地区", 1).show();
		} else {
			if (NetUtil.isNetworkConnected(this)) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("type", "bank");
				params.put("act", "bank_bindsub");
				params.put("bankname", bank);
				params.put("bankcity", area);
				params.put("banksubbranch", branchBank);
				params.put("banknum", cardNum);
				params.put("re_banknum", cardNum);
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
			// System.out.println("银行卡验证  :" + data);
			try {
				if (data.getInt("errorcode") == 0) {
					String result = data.getString("dataresult");
					Toast.makeText(CardVerifyActivity.this, result, 1).show();
					setResult(1002);
					CardVerifyActivity.this.finish();
				}
			} catch (JSONException e) {
				e.printStackTrace();
				try {
					String str = data.getString("errormsg");
					Toast.makeText(CardVerifyActivity.this, str, 1).show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					Toast.makeText(CardVerifyActivity.this,
							Consts.UNKNOWN_FAULT, 1).show();
				}
			}
		}
	};
	
	public void backHandler(View v){
		finish();
	}
}
