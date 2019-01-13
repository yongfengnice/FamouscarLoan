package com.famous.zhifu.loan.famouscarloan.account;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.activity.ActivityInterface;
import com.famous.zhifu.loan.activity.Consts;
import com.famous.zhifu.loan.activity.LoginActivity;
import com.famous.zhifu.loan.activity.MainActivity;
import com.famous.zhifu.loan.activity.MyApp;
import com.famous.zhifu.loan.adapter.LineXXXAdapter;
import com.famous.zhifu.loan.famouscarloan.notice.NoticeActivity;
import com.famous.zhifu.loan.internet.ServiceClient;
import com.famous.zhifu.loan.util.NetUtil;
import com.famous.zhifu.loan.util.SharePrefUtil;
import com.famous.zhifu.loan.util.Tools;
import com.famous.zhifu.loan.view.View_Loading;
import com.famous.zhifu.loan.view.dialog.OperateTipPopup;
import com.famous.zhifu.loan.view.mygridview.ExpandableHeightGridView;

public class AccountActivity extends Activity implements ActivityInterface {
	private View mView;
	private TextView mTvTitle, accountView, tv2, tv3, userName, lastLogin;
	private ExpandableHeightGridView mLv;
	private int[] drawables;
	private String[] strings;
	private ImageView imgMobile, imgPerson, imgEmail, imgCard;
	private RelativeLayout mRllyVerify;
	private View_Loading loading;
	private String bank_bind;// 银行卡是否验证
	private String isOpen;// 是否开启自动投标
	private String id;// 启自动投标ID
	private LineXXXAdapter lineXXXAdapter;
	private String availableAmount;
	private String cashoutAmount;

	private MainActivity parentActivity;

	private OperateTipPopup tipPopup = null;
	
	private JSONObject member_info = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mView = getLayoutInflater().inflate(R.layout.activity_account, null);
		setContentView(mView);
		MyApp.getInstance().addActivity(this);
		findView();
		initData();
		addAction();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (Tools.isEmpty(MyApp.token)) {
			parentActivity.setClicKHandler(0);
			
//			startActivityForResult(new Intent(AccountActivity.this,LoginActivity.class), 1003);
		} else {
			userName.setText(MyApp.username);
			
			toGetInfo();
		}
	}

	@Override
	public void findView() {
		loading = (View_Loading) findViewById(R.id.loading);
		mLv = (ExpandableHeightGridView) mView.findViewById(R.id.account_gv);
		mTvTitle = (TextView) mView.findViewById(R.id.main_header_tv_text);
		mRllyVerify = (RelativeLayout) mView.findViewById(R.id.account_verify_rlly);

		findViewById(R.id.main_header_notice_right).setVisibility(View.VISIBLE);

		userName = (TextView) findViewById(R.id.account_tv_name);
		lastLogin = (TextView) findViewById(R.id.lastLogin);
		accountView = (TextView) findViewById(R.id.account_balance);

		tv2 = (TextView) findViewById(R.id.tv2);
		tv3 = (TextView) findViewById(R.id.tv3);

		imgMobile = (ImageView) findViewById(R.id.imgMobile);
		imgPerson = (ImageView) findViewById(R.id.imgPerson);
		imgEmail = (ImageView) findViewById(R.id.imgEmail);
		imgCard = (ImageView) findViewById(R.id.imgCard);
	}

	@Override
	public void initData() {
		parentActivity = (MainActivity) getParent();

		tipPopup = new OperateTipPopup(this);

		findViewById(R.id.main_header_iv_left).setVisibility(View.GONE);

		mTvTitle.setText(getResources().getString(R.string.my_account));
		/*
		 * drawables = new int[] {
		 * 
		 * R.drawable.p_center_certification, R.drawable.p_center_chongzhi,
		 * R.drawable.p_center_tixian, R.drawable.p_center_financial_records,
		 * R.drawable.p_center_investment_records,
		 * R.drawable.p_center_automatic_bid, R.drawable.p_center_more}; strings
		 * = new String[] {
		 * getResources().getString(R.string.account_certification),
		 * getResources().getString(R.string.account_chongzhi),
		 * getResources().getString(R.string.account_tixian),
		 * getResources().getString(R.string.account_fund),
		 * getResources().getString(R.string.account_investment_records),
		 * getResources().getString(R.string.account_auto_bid),
		 * getResources().getString(R.string.account_more),};
		 * 
		 * lineXXXAdapter = new LineXXXAdapter(getApplication(),
		 * strings,drawables, isOpen);
		 * 
		 * mLv.setAdapter(lineXXXAdapter);
		 */

	}

	@Override
	public void addAction() {
		mRllyVerify.setOnClickListener(new MyClickListener());

	}

	private class MyClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			/*
			 * case R.id.tologin:// 登陆 startActivityForResult(new
			 * Intent(AccountActivity.this,LoginActivity.class), 1003); break;
			 */
			case R.id.main_header_iv_right:// 跳转到设置界面~
				startActivity(new Intent(AccountActivity.this,SettingActivity.class));
				break;
			case R.id.account_verify_rlly:// 跳转到用户认证界面~~
				startActivityForResult(new Intent(AccountActivity.this,AccountVerifyActivity.class), 1003);
				break;
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == 1003) {
			toGetInfo();
		} else if (requestCode == 1004 && resultCode == 1009) {
			id = data.getStringExtra("id");
			String isOpen = data.getStringExtra("isOpen");
			/*lineXXXAdapter.setStatus(isOpen);
			lineXXXAdapter.notifyDataSetChanged();*/
		}else if(requestCode == 1003 && resultCode == 1010){
			parentActivity.setClicKHandler(0);
		}
		
		
	}

	public void toGetInfo() {
		if (!Tools.isEmpty(MyApp.token)) {
			if (NetUtil.isNetworkConnected(this)) {
				loading.setVisibility(View.VISIBLE);
				ServiceClient.call(Consts.NetWork.INVESTINFO, null, callback);
			} else {
				Toast.makeText(this, Consts.NONETWORK, 1).show();
			}
		} else {
			// System.out.println(" no  token");
		}
	}

	ServiceClient.Callback callback = new ServiceClient.Callback() {
		@Override
		public void callback(JSONObject data) {
			// System.out.println("账户信息     " + data);
			loading.setVisibility(View.GONE);
			try {
				if (data.getInt("errorcode") == 0) {
					if (!TextUtils.isEmpty(data.get("dataresult").toString())) {
						JSONObject json = data.getJSONObject("dataresult");// cashoutAmount

						cashoutAmount = json.getString("cashoutAmount");
						availableAmount = json.getString("availableAmount");

						JSONObject obj1 = json.getJSONObject("allot_info");
						accountView.setText(availableAmount);
						tv2.setText(cashoutAmount);
						tv3.setText(json.getString("member_amount"));

						member_info = json.getJSONObject("member_info");
						lastLogin.setText("上次登录时间: "+ member_info.getString("lastlogintime"));

						if (Tools.isEquals("1", member_info.getString("mobile_bind"))) {
							imgMobile.setBackgroundResource(R.drawable.verify_mobile);
						} else {
							imgMobile.setBackgroundResource(R.drawable.verify_mobile_gray);
						}

						if (Tools.isEquals("1", member_info.getString("name_bind"))) {
							imgPerson
									.setBackgroundResource(R.drawable.verify_person);

						} else {
							imgPerson
									.setBackgroundResource(R.drawable.verify_person_gray);
						}

						bank_bind = member_info.getString("bank_bind");
						if (Tools.isEquals("1", bank_bind)) {
							imgCard.setBackgroundResource(R.drawable.verify_card);
						} else {
							imgCard.setBackgroundResource(R.drawable.verify_card_gray);
						}

						if (Tools.isEquals("1", member_info.getString("email_bind"))) {
							imgEmail.setBackgroundResource(R.drawable.verify_mail);
						} else {
							imgEmail.setBackgroundResource(R.drawable.verify_mail_gray);
						}

						if (!Tools.isEquals("[]", json.get("autotender_info")
								.toString())) {
							JSONObject obj3 = json
									.getJSONObject("autotender_info");

							id = obj3.getString("id");
							isOpen = obj3.getString("status");
							/*lineXXXAdapter.setStatus(isOpen);
							lineXXXAdapter.notifyDataSetChanged();*/
						}

					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
				try {
					String str = data.getString("errormsg");
					Toast.makeText(AccountActivity.this, str, 1).show();

					if (data.getString("errorcode").equals(Consts.NOT_LOGIN)) {
						MyApp.token = null;
						SharePrefUtil.clearUserInfo(AccountActivity.this);

						startActivityForResult(new Intent(AccountActivity.this,LoginActivity.class), 1003);
					}
				} catch (JSONException ee) {
					ee.printStackTrace();
				}
			}
		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();

		MyApp.getInstance().remove(this);
	}

	public void jumpHandler(View view) {
		String tagString = view.getTag().toString();

		if (TextUtils.isEmpty(tagString)) {
			return;
		} else {
			int position = Integer.valueOf(tagString);

			switch (position) {
			case 20:
				Intent balanceIntent = new Intent(AccountActivity.this,
						AccountBalanceActivity.class);
				balanceIntent.putExtra("availableAmount", availableAmount);
				balanceIntent.putExtra("cashoutAmount", cashoutAmount);

				startActivity(balanceIntent);

				break;

			case 1: // 认证管理

				if (!Tools.isEmpty(MyApp.token)) {
					startActivity(new Intent(AccountActivity.this,
							AccountVerifyActivity.class));
				} else {
					Toast.makeText(AccountActivity.this, "您尚未登录账户", 1).show();
					startActivityForResult(new Intent(AccountActivity.this,
							LoginActivity.class), 1003);
				}

				break;

			case 2: // 账户充值
//				tipPopup.showAtLocation(mView, Gravity.CENTER, 0, 0);]
				if (!Tools.isEmpty(MyApp.token)) {
					try {
						if (Tools.isEquals(member_info.getString("bank_bind"),"1") && Tools.isEquals(member_info.getString("name_bind"),"1")) {
							startActivity(new Intent(this,PayActivity.class));
						}else {
							if (!Tools.isEquals(member_info.getString("bank_bind"),"1")) {	//银行卡未绑定
								Toast.makeText(AccountActivity.this, "请先进行银行卡认证", 1).show();
							}else {
								Toast.makeText(AccountActivity.this, "请先进行实名认证", 1).show();
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
				} else {
					Toast.makeText(this, "您尚未登录账户", 1).show();
					
					startActivityForResult(new Intent(this,LoginActivity.class), 1003);
				}

				break;

			case 3: // 账户体现
				if (!Tools.isEmpty(MyApp.token)) {
					startActivity(new Intent(AccountActivity.this,WithdrawActivity.class));
				} else {
					Toast.makeText(AccountActivity.this, "您尚未登录账户", 1).show();
					startActivityForResult(new Intent(AccountActivity.this,LoginActivity.class), 1003);
				}

				break;

			case 4: // 资金记录
				if (!Tools.isEmpty(MyApp.token)) {
					startActivity(new Intent(AccountActivity.this, ZjlsAc.class));
				} else {
					Toast.makeText(AccountActivity.this, "您尚未登录账户", 1).show();
					startActivityForResult(new Intent(AccountActivity.this,
							LoginActivity.class), 1003);
				}

				break;

			case 5: // 我的投资
				if (!Tools.isEmpty(MyApp.token)) {
					startActivity(new Intent(AccountActivity.this, TzjlAc.class));
				} else {
					Toast.makeText(AccountActivity.this, "您尚未登录账户", 1).show();
					startActivityForResult(new Intent(AccountActivity.this,LoginActivity.class), 1003);
				}
				break;

			case 6: // 自动投标
				if (!Tools.isEmpty(MyApp.token)) {
					if (Tools.isEquals("1", bank_bind)) {
						Intent intent = new Intent(AccountActivity.this,ZdtbAc.class);
						intent.putExtra("id", id);
						startActivityForResult(intent, 1004);
					} else {
						Toast.makeText(AccountActivity.this, "请您先进入银行卡绑定", 1).show();
					}
				} else {
					Toast.makeText(AccountActivity.this, "您尚未登录账户", 1).show();

					startActivityForResult(new Intent(AccountActivity.this,LoginActivity.class), 1003);
				}
				break;

			case 7: // 更多信息
				// Log.i("-----", "123");

				if (!Tools.isEmpty(MyApp.token)) {
					parentActivity.setClicKHandler(3);
					
//					startActivity(new Intent(AccountActivity.this,SettingActivity.class));
				} else {
					Toast.makeText(AccountActivity.this, "您尚未登录账户", 1).show();
					startActivityForResult(new Intent(AccountActivity.this,LoginActivity.class), 1003);
				}

				break;

			case 12: // 回款预告
				if (!Tools.isEmpty(MyApp.token)) {
					startActivity(new Intent(AccountActivity.this, HkygAc.class));
				} else {
					Toast.makeText(AccountActivity.this, "您尚未登录账户", 1).show();
					startActivityForResult(new Intent(AccountActivity.this,LoginActivity.class), 1003);
				}
				break;

			default:
				break;
			}
		}
	}

	/**
	 * 跳转到通知
	 */
	public void noticeHandler(View v) {
		startActivity(new Intent(this, NoticeActivity.class));
	}
}
