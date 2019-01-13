package com.famous.zhifu.loan.famouscarloan.account;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.activity.ActivityInterface;
import com.famous.zhifu.loan.activity.Consts;
import com.famous.zhifu.loan.activity.LoginActivity;
import com.famous.zhifu.loan.activity.MyApp;
import com.famous.zhifu.loan.internet.ServiceClient;
import com.famous.zhifu.loan.util.NetUtil;
import com.famous.zhifu.loan.util.SharePrefUtil;
import com.famous.zhifu.loan.util.Tools;
import com.famous.zhifu.loan.view.dialog.AuthCodePopup;

public class WithdrawActivity extends Activity implements ActivityInterface,OnClickListener {
	protected static final int MSGCODE = 1001;
	private View mView;
	private TextView mTvTitle, getCheckNum, bank_name_tv, card_num_tv,
			available_num, handling_charge, tv_nointent,submit;
	private ImageView checkfree;
	private EditText editPhone, take_cash_num, et_code;

	private int flag = 0;
	private double saveFee;
	private double fee;// 提现手续费
	private double cut_amount; // 计算管理费要减去的值
	private double point; // 提现管理费费率
	private double totalfee; // 提现总费用
	private double avai_amount;// 可提现金额
	private double amount;// 提现金额
	private double decimalguanlifei; // 管理费
	
	private AuthCodePopup popup = null;

	private int mimute = 60; // 记录一分钟
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == MSGCODE) {
				mimute = mimute - 1;
				getCheckNum.setClickable(false);
				getCheckNum.setText("重新获取验证码(" + mimute + ")");

				if (mimute == 0) {
					getCheckNum.setClickable(true);
					
					getCheckNum.setText("重新获取验证码");
					mimute = 60;
				} else {
					mHandler.postDelayed(new Runnable() {
						@Override
						public void run() {
							mHandler.sendEmptyMessage(MSGCODE);
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
		mView = getLayoutInflater().inflate(R.layout.account_withdraw_deposit,
				null);
		setContentView(mView);
		findView();
		initData();
		addAction();
		getDatas();
	}

	@Override
	public void findView() {
		mTvTitle = (TextView) mView.findViewById(R.id.main_header_tv_text);

		handling_charge = (TextView) findViewById(R.id.handling_charge);
		tv_nointent = (TextView) findViewById(R.id.tv_nointent);
		getCheckNum = (TextView) findViewById(R.id.getcode);
		getCheckNum.setOnClickListener(this);
		bank_name_tv = (TextView) findViewById(R.id.bank_name_tv);
//		card_num_tv = (TextView) findViewById(R.id.card_num_tv);
		available_num = (TextView) findViewById(R.id.available_num);
		editPhone = (EditText) findViewById(R.id.editPhone);
		checkfree = (ImageView) findViewById(R.id.checkfree);
		take_cash_num = (EditText) findViewById(R.id.take_cash_num);
		et_code = (EditText) findViewById(R.id.et_code);
		submit = (TextView) findViewById(R.id.submit);
		/*LinearLayout no_intent = (LinearLayout) findViewById(R.id.no_intent);
		no_intent.setOnClickListener(this);*/

		EdListener();
	}

	public void EdListener() {
		take_cash_num.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,int count) {
				saveTwoPoint(s);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,int after) {
				
			}

			@Override
			public void afterTextChanged(Editable s) {
				calculateAbout();
			}
		});

		take_cash_num.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				String cashStr = take_cash_num.getText().toString().trim();
				if (!TextUtils.isEmpty(cashStr)) {
					float cashInt = Float.parseFloat(cashStr);
					if (cashInt < 2) {
						take_cash_num.setText("");
					}
				}
			}
		});
	}

	@Override
	public void initData() {
		mTvTitle.setText(getResources().getString(
				R.string.account_withdraw_deposit));
	}

	@Override
	public void addAction() {
		submit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_header_iv_left:
			finish();
			break;
		case R.id.getcode:
			toShowDialog(null);
//			doGetCheckNum();
			break;
		/*case R.id.no_intent:
			changeBg();
			break;*/
		case R.id.submit:
			submit();
			break;
		default:
			break;
		}
	}

	public void changeBg() {
		if (flag == 0) {
			checkfree.setBackgroundResource(R.drawable.chick_sure);
			flag = 1;
		} else {
			checkfree.setBackgroundResource(R.drawable.chick);
			flag = 0;
		}
	}

	public void getDatas() {
		if (NetUtil.isNetworkConnected(this)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("type", "get_member_info");
			ServiceClient.call(Consts.NetWork.WITHDRAW, params, callback);
		} else {
			Toast.makeText(this, Consts.NONETWORK, 1).show();
		}
	}

	// 获取基本信息
	ServiceClient.Callback callback = new ServiceClient.Callback() {
		@Override
		public void callback(JSONObject data) {
			// System.out.println("提现基本信息     " + data);
			try {
				if (data.getInt("errorcode") == 0) {
					JSONObject json = data.getJSONObject("dataresult");
					String str = json.getString("avai_amount").toString();
					avai_amount = Double.parseDouble(str);
					available_num.setText(str);

				/*	String name = json.getString("name");
					if (!Tools.isEquals("null", name)) {
						name_tv.setText(name);
					}*/

					String bankname = json.getString("bankname");
					if (!Tools.isEmpty(bankname)) {
						bank_name_tv.setText(bankname);
					}

//					card_num_tv.setText(json.getString("banknum"));
					editPhone.setText(json.getString("mobile"));

					fee = Double.parseDouble(json.getString("fee"));
					// 暂时保存手续费数值
					saveFee = fee;
					cut_amount = Double.parseDouble(json
							.getString("cut_amount"));
					point = Double.parseDouble(json.getString("point"));

					if (cut_amount < fee) {
						cut_amount = 0;
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
				try {
					String str = data.getString("errormsg");
					Toast.makeText(WithdrawActivity.this, str, 1).show();
					if (data.getString("errorcode").equals(Consts.NOT_LOGIN)) {
						MyApp.token = null;
						SharePrefUtil.clearUserInfo(WithdrawActivity.this);
						startActivityForResult(new Intent(
								WithdrawActivity.this, LoginActivity.class),
								1003);
					}
				} catch (JSONException ee) {
					ee.printStackTrace();
				}
			}
		}
	};
	
	//弹出图像验证码
	private void toShowDialog(String phone){
		if (null==popup) {
			popup = new AuthCodePopup(this,mHandler,phone,Consts.NetWork.WITHDRAW,"get_smscode");
		}
		
		popup.triggerClick();
		popup.showAtLocation(mView, Gravity.CENTER, 0, 0);
	}

	// 获取验证码
	public void doGetCheckNum() {
		if (NetUtil.isNetworkConnected(this)) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("type", "get_smscode");
			ServiceClient.call(Consts.NetWork.WITHDRAW, param, checkNumCallback);
		} else {
			Toast.makeText(this, Consts.NONETWORK, 1).show();
		}
	}

	ServiceClient.Callback checkNumCallback = new ServiceClient.Callback() {
		@Override
		public void callback(JSONObject ret) {
			try {
				getCheckNum.setEnabled(true);
				if (ret.getInt("errorcode") == 0) {
					if (!TextUtils.isEmpty(ret.get("dataresult").toString())) {
						String result = ret.getString("dataresult");
						Toast.makeText(WithdrawActivity.this, result, 1).show();
						// 发送一次验证码后 不能再点击
						mHandler.sendEmptyMessage(MSGCODE);
					}
				}
			} catch (JSONException e) {
				try {
					String str = ret.getString("errormsg");
					Toast.makeText(WithdrawActivity.this, str, 1).show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					Toast.makeText(WithdrawActivity.this, Consts.UNKNOWN_FAULT,
							1).show();
				}
			}
		}
	};

	// 申请提现
	public void submit() {
		String cash = take_cash_num.getText().toString().trim();
		String code = et_code.getText().toString().trim();

		if (TextUtils.isEmpty(cash)) {
			Toast.makeText(this, "请输入提现金额", 1).show();
		} else if (TextUtils.isEmpty(code)) {
			Toast.makeText(this, "请输入验证码", 1).show();
		} else if (new Integer(code)<100) {
			Toast.makeText(this, "金额不能小于100", 1).show();
		} else {
			if (NetUtil.isNetworkConnected(this)) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("type", "cashout_submit");
				param.put("amount", cash);
				param.put("smscode", code);
				param.put("checkfee", flag+"");
				param.put("fee", totalfee+"");
				ServiceClient.call(Consts.NetWork.WITHDRAW, param,
						submitCallback);
			} else {
				Toast.makeText(this, Consts.NONETWORK, 1).show();
			}
		}
	}

	ServiceClient.Callback submitCallback = new ServiceClient.Callback() {
		@Override
		public void callback(JSONObject result) {
			if (result != null) {
				try {
					if (result.getInt("errorcode") == 0) {
						if (!TextUtils.isEmpty(result.get("dataresult")
								.toString())) {
							String results = result.getString("dataresult");
							Toast.makeText(WithdrawActivity.this, results, 1)
									.show();
							WithdrawActivity.this.finish();
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
					try {
						String str = result.getString("errormsg");
						Toast.makeText(WithdrawActivity.this, str, 1).show();
					} catch (JSONException ee) {
						ee.printStackTrace();
						Toast.makeText(WithdrawActivity.this,
								Consts.UNKNOWN_FAULT, 1).show();
					}
				}
			} else {
				Toast.makeText(WithdrawActivity.this, "提现申请失败", 1).show();
			}
		}
	};

	/**
	 * 使用户只能输入小数点后两位
	 */
	public void saveTwoPoint(CharSequence s) {
		if (s.toString().contains(".")) {
			if (s.length() - 1 - s.toString().indexOf(".") > 2) {
				s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
				take_cash_num.setText(s);
				take_cash_num.setSelection(s.length());
			}
		}
		if (s.toString().trim().substring(0).equals(".")) {
			s = "0" + s;
			take_cash_num.setText(s);
			take_cash_num.setSelection(2);
		}

		if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
			if (!s.toString().substring(1, 2).equals(".")) {
				take_cash_num.setText(s.subSequence(0, 1));
				take_cash_num.setSelection(1);
				return;
			}
		}
	}

	/**
	 * 相关计算
	 */
	public void calculateAbout() {
		String cash = take_cash_num.getText().toString();

		if (TextUtils.isEmpty(cash)) {
			cash = "0";
			amount = Double.parseDouble(cash);
		} else {
			amount = Double.parseDouble(cash);
		}

		// 最大可取金额
		double decimalmaxamount = calculateMax();
		// System.out.println(" 最大可取金额 :   " + decimalmaxamount);
		if (decimalmaxamount > 0) {
			if (decimalmaxamount < amount) {
				// 计算管理费 当可取最大金额大于 cut_amount 时才收取费用
				if (decimalmaxamount > cut_amount) {
					double guanlifei = (decimalmaxamount - cut_amount) * point;
					decimalguanlifei = sishewurubaoliuliangwei(guanlifei);
				} else {
					decimalguanlifei = 0;
				}

				// 收取费用情况
				handling_charge.setText("费用" + (decimalguanlifei + fee)
						+ "元（管理费" + decimalguanlifei + "元" + " + 手续费" + fee
						+ "元）");
				long longmaxamount;
				if (decimalmaxamount % 1.0 == 0) {
					longmaxamount = (long) decimalmaxamount;
					take_cash_num.setText(longmaxamount + "");
				} else {
					take_cash_num.setText(decimalmaxamount + "");
				}
			} else {
				// 计算管理费 输入的提现金额大于 cut_amount 时才收取费用
				if (amount > cut_amount) {
					double guanlifei = (amount - cut_amount) * point;
					// 是整数就放置整数
					long longguanlifei;
					if (guanlifei>0 && guanlifei % 1.0 == 0) {
						longguanlifei = (long) guanlifei;
						
						take_cash_num.setText(longguanlifei + "");
					} else {
						decimalguanlifei = sishewurubaoliuliangwei(guanlifei);
					}
				} else {
					decimalguanlifei = 0;
				}
				double a = sishewurubaoliuliangwei(decimalguanlifei + fee);

				// 收取费用情况
				handling_charge.setText("费用" + getFormat(a) + "元（管理费"
						+ getFormat(decimalguanlifei) + "元 " + " + 手续费"
						+ getFormat(fee) + "元）");
			}
		} else {
			Toast.makeText(getApplicationContext(), "余额不足，不能提现", 1).show();
		}
		// 总费用
		totalfee = decimalguanlifei + fee;

		tv_nointent.setText("我确定无套现意图,申请免收管理费" + decimalguanlifei + "元");

	}

	public String getFormat(double a) {
		DecimalFormat format = new DecimalFormat("###############0.00 ");
		return format.format(a);
	}

	/**
	 * 计算最做可取金额
	 */
	public double calculateMax() {

		// 余额-cut_amount > 手续费 --> 计算maxAmount 否则 maxAmount 为 avai_amount - fee
		double maxAmount = 0;
		if (avai_amount - cut_amount > fee) {
			// 计算可提取的最大金额
			maxAmount = (avai_amount - fee + cut_amount * point)
					/ (float) (1 + point);
		} else {
			maxAmount = avai_amount - fee;
		}

		// 四舍五入保留两位小数后的可提取的最大金额
		double decimalmaxamount = sishewurubaoliuliangwei(maxAmount);

		return decimalmaxamount;
	}

	private double sishewurubaoliuliangwei(double maxAmount) {
		DecimalFormat fnum = new DecimalFormat("##0.00");
		double decimalmaxamount = Double.parseDouble(fnum.format(maxAmount));
		return decimalmaxamount;
	}
	
	public void backHandler(View v){
		finish();
	}
}
