package com.famous.zhifu.loan.famouscarloan.account;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.activity.ActivityInterface;
import com.famous.zhifu.loan.activity.Consts;
import com.famous.zhifu.loan.activity.GestureEditActivity;
import com.famous.zhifu.loan.activity.MyApp;
import com.famous.zhifu.loan.adapter.LineAdapter;
import com.famous.zhifu.loan.adapter.VerifyAdapter;
import com.famous.zhifu.loan.internet.ServiceClient;
import com.famous.zhifu.loan.util.NetUtil;
import com.famous.zhifu.loan.util.SharePrefUtil;
import com.famous.zhifu.loan.util.Tools;
import com.famous.zhifu.loan.view.View_Loading;

public class AccountVerifyActivity extends Activity implements
		ActivityInterface {
	private View mView;
	private TextView mTvTitle;
	private ListView mLvSettingOne, mLvSettingTwo;
	private String realNameFlag = "0", phoneFlag = "0", bankFlag = "0",
			emailFlag = "0";
	private VerifyAdapter adapter;
	private String[] flag = { realNameFlag, phoneFlag, bankFlag, emailFlag };
	private View_Loading loading;
	
	private Button exit;
	
	private ToggleButton mToggleButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		mView = getLayoutInflater().inflate(
				R.layout.activity_account_verify_setting, null);
		setContentView(mView);
		findView();
		initData();
		addAction();
		getData();
	}

	@Override
	public void findView() {
		mTvTitle = (TextView) mView.findViewById(R.id.main_header_tv_text);
		mLvSettingOne = (ListView) mView
				.findViewById(R.id.account_setting_verify_lv_one);
		mLvSettingTwo = (ListView) mView
				.findViewById(R.id.account_setting_verify_lv_two);
		loading = (View_Loading) findViewById(R.id.loading);
		
		mToggleButton = (ToggleButton) mView.findViewById(R.id.account_setting_verify_lv_tglSound);
		exit = (Button) mView.findViewById(R.id.exit);
	}

	@Override
	public void initData() {
		mTvTitle.setText("账户设置");
		String[] stringsOne = new String[] {getResources().getString(
				R.string.verify_head),getResources().getString(
				R.string.verify_password) };
		String[] stringsTwo = new String[] {
				getResources().getString(R.string.verify_name),
				getResources().getString(R.string.verify_mobile),
				getResources().getString(R.string.verify_card),
				getResources().getString(R.string.verify_email) };
		String[] stringsTwoShow = new String[] { "未认证", "已认证", "未认证", "未认证" };
		int[] drawablesOne = new int[] { R.drawable.p_center_password,R.drawable.p_center_password };
		int[] drawablesTwo = new int[] { R.drawable.p_center_name,
				R.drawable.p_center_mobile, R.drawable.p_center_card,
				R.drawable.p_center_email };
		
		mLvSettingOne.setAdapter(new LineAdapter(getApplication(), stringsOne,
				drawablesOne, new String[] {}));
		mLvSettingOne.setDividerHeight(18);
		adapter = new VerifyAdapter(getApplication(), stringsTwo, drawablesTwo,
				stringsTwoShow);
		adapter.setArray(flag);
		mLvSettingTwo.setAdapter(adapter);
	}
	
	public void exit() {
		if (NetUtil.isNetworkConnected(this)) {
			ServiceClient.call(Consts.NetWork.EXIT, null, callback);
		} else {
			Toast.makeText(this, Consts.NONETWORK, 1).show();
		}
	}

	@Override
	public void addAction() {
		exit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				exit();
			}
		});
		
		mToggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				SharePrefUtil.saveBoolean(AccountVerifyActivity.this, "switch", isChecked);
			}
		});
		
		mLvSettingTwo
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						switch (position) {
						case 0:// 实名认证~~
							if (Tools.isEquals("1", realNameFlag)) {
								Intent intent = new Intent(AccountVerifyActivity.this,HasVerityAc.class);
								intent.putExtra("hasVerify", "realName");
								startActivity(intent);
							} else {
								startActivityForResult(new Intent(AccountVerifyActivity.this,NameVerifyActivity.class), 1001);
							}
							
							break;
							
						case 1:// 手机验证~~
							if (Tools.isEquals("1", phoneFlag)) {
								Intent intent = new Intent(AccountVerifyActivity.this,HasVerityAc.class);
								intent.putExtra("hasVerify", "phone");
								
								startActivity(intent);
							} else {
								startActivityForResult(new Intent(AccountVerifyActivity.this,PhoneConfirmActivity.class), 1001);
							}
							
							break;
						case 2:// 银行卡认证~~
							if (Tools.isEquals("1", realNameFlag)) {
								if (Tools.isEquals("1", bankFlag)) {
									Intent intent = new Intent(AccountVerifyActivity.this,HasVerityAc.class);
									intent.putExtra("hasVerify", "bank");
									startActivity(intent);
								} else {
									startActivityForResult(new Intent(AccountVerifyActivity.this,CardVerifyActivity.class), 1001);
								}
							} else {
								Toast.makeText(AccountVerifyActivity.this,"请先进行实名认证", 1).show();

								startActivityForResult(new Intent(AccountVerifyActivity.this,NameVerifyActivity.class), 1001);
							}
							
							break;
						case 3:// 邮箱认证~~
							if (Tools.isEquals("1", emailFlag)) {
								Intent intent = new Intent(
										AccountVerifyActivity.this,
										HasVerityAc.class);
								intent.putExtra("hasVerify", "email");
								startActivity(intent);
							} else {
								startActivityForResult(new Intent(
										AccountVerifyActivity.this,
										EmailVerifyActivity.class), 1001);
							}
							break;
						// case 4:// 紧急联系人~~
						// startActivity(new Intent(
						// AccountVerifyActivity.this,
						// ContactVerifyActivity.class));
						// break;
						// case 5:// 收货地址
						// startActivity(new Intent(
						// AccountVerifyActivity.this,
						// AddressVerifyActivity.class));
						// break;

						}
					}
				});

		mLvSettingOne
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						switch (position) {
						case 0:
							// 修改头像~~
							startActivity(new Intent(AccountVerifyActivity.this,ChangeHeadVerifyActivity.class));
							break;
						case 1:
							// 修改密码~~
							startActivity(new Intent(AccountVerifyActivity.this,ChangePasswordVerifyActivity.class));
							break;
						}
					}
				});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1001 && resultCode == 1002) {
			getData();
		}
	}

	public void getData() {
		if (NetUtil.isNetworkConnected(this)) {
			loading.setVisibility(View.VISIBLE);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("type", "all");
			ServiceClient.call(Consts.NetWork.VERIFY, params, verifyback);
		} else {
			Toast.makeText(this, Consts.NONETWORK, 1).show();
		}
	}

	ServiceClient.Callback verifyback = new ServiceClient.Callback() {
		@Override
		public void callback(JSONObject data) {
			// System.out.println("验证结果  " + data);
			loading.setVisibility(View.GONE);
			try {
				if (data.getInt("errorcode") == 0) {
					JSONObject obj = data.getJSONObject("dataresult");
					realNameFlag = obj.getString("relanamebindsta");
					phoneFlag = obj.getString("mobilebindsta");
					bankFlag = obj.getString("bankbindsta");
					emailFlag = obj.getString("emailbindsta");

					flag[0] = realNameFlag;
					flag[1] = phoneFlag;
					flag[2] = bankFlag;
					flag[3] = emailFlag;
					adapter.setArray(flag);
					adapter.notifyDataSetChanged();
				}
			} catch (JSONException e) {
				e.printStackTrace();
				try {
					String str = data.getString("errormsg");
					Toast.makeText(AccountVerifyActivity.this, str, 1).show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					Toast.makeText(AccountVerifyActivity.this,
							Consts.UNKNOWN_FAULT, 1).show();
				}
			}
		}
	};
	
	ServiceClient.Callback callback = new ServiceClient.Callback() {
		@Override
		public void callback(JSONObject data) {
			// System.out.println("退出登录  " + data);
			try {
				if (data.getInt("errorcode") == 0) {
					if (!TextUtils.isEmpty(data.get("dataresult").toString())) {
						Toast.makeText(AccountVerifyActivity.this,
								data.get("dataresult") + "", 1).show();

						SharePrefUtil.clearUserInfo(AccountVerifyActivity.this);
						MyApp.username = "";
						MyApp.token = "";
						finish();
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
				try {
					String str = data.getString("errormsg");
					Toast.makeText(AccountVerifyActivity.this, str, 1).show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					Toast.makeText(AccountVerifyActivity.this, Consts.UNKNOWN_FAULT,1).show();
				}
			}
		}
	};
	

	public void tbHandler(View v){
		String temp = SharePrefUtil.getString(this, "Gesture", null);
		
		Intent intent = null;
		
		if (Tools.isEmpty(temp)) {	//如果为空则说明之前没有设置过,跳到设置页面
			intent = new Intent(this,GestureEditActivity.class);
			intent.putExtra("TAG", this.getClass().getSimpleName());
			
		}else {
			intent = new Intent(this,ToggleBtnCtrlActivity.class);
		}
		
		startActivity(intent);
		
		temp = null;
		
	}
	
	public void backHandler(View v){
		finish();
	}

}
