package com.famous.zhifu.loan.famouscarloan.investlist;

import java.math.BigDecimal;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.activity.Consts;
import com.famous.zhifu.loan.activity.LoginActivity;
import com.famous.zhifu.loan.activity.MyApp;
import com.famous.zhifu.loan.entity.Interest;
import com.famous.zhifu.loan.intef.ILinster;
import com.famous.zhifu.loan.internet.ServiceClient;
import com.famous.zhifu.loan.util.NetUtil;
import com.famous.zhifu.loan.util.SharePrefUtil;
import com.famous.zhifu.loan.util.Tools;

public class InvestNumAc extends Activity implements OnClickListener {
	private TextView title;
	private ImageView userImg;
	private TextView yueView,ketoujineView, doSubmit,yearrateView,qixianView, tv_shouyi;
	private Float needloanmoney = 0f;
	private EditText need_amountEdi;

	private Intent in;
	private String sn;
	private String mintendermoney;
	private String yearrate;
	private String loanmonth;
	private String repayment;
	private Float ketoujine;
	private float shouyi;
	private float tendermoney; // 最小输入值
	private ILinster linster;
	
	private String availableAmount;

	protected Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				if (!need_amountEdi.getText().toString().trim().equals("")) {
					needloanmoney = Float.parseFloat(need_amountEdi.getText()
							.toString().trim());
					if (needloanmoney.compareTo(ketoujine) > 0) {
						need_amountEdi.setText(ketoujine.toString().substring(
								0, ketoujine.toString().indexOf(".")));
						need_amountEdi.setSelection(need_amountEdi.getText()
								.toString().trim().length());
					}
					shouyi = 0;
					getShouyi();
				} else {
					need_amountEdi.setText("100");
					need_amountEdi.setSelection(need_amountEdi.getText()
							.toString().trim().length());
				}

			}
		};
	};

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				linster.linster(in);
				finish();
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.to_invest);
		initdate();
		linster = InvestDetailActivity.act;
		initView();
		
		toGetInfo();
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		if (TextUtils.isEmpty(MyApp.token)) {
			userImg.setVisibility(View.VISIBLE);
		} else {
			userImg.setVisibility(View.GONE);
		}
	}

	private void initdate() {
		in = getIntent();
		sn = in.getExtras().getString("sn");
		mintendermoney = in.getExtras().getString("mintendermoney");
		yearrate = in.getExtras().getString("yearrate");
		loanmonth = in.getExtras().getString("loanmonth");
		repayment = in.getExtras().getString("repayment");
		ketoujine = in.getExtras().getFloat("ketoujine");
	}

	public void initView() {
		title = (TextView) findViewById(R.id.main_header_tv_text);
		title.setText("投资金额");

		userImg = (ImageView) findViewById(R.id.main_header_iv_right);

		yueView = (TextView) findViewById(R.id.to_invest_yue);
		ketoujineView = (TextView) findViewById(R.id.to_invest_jine);
		
		yearrateView = (TextView) findViewById(R.id.to_invest_lilv);
		qixianView = (TextView) findViewById(R.id.to_invest_qixian);
		tv_shouyi = (TextView) findViewById(R.id.tv_shouyi);
		
		yearrateView.setText(yearrate+"%");
		qixianView.setText(loanmonth+"个月");
		
		doSubmit = (TextView) findViewById(R.id.doSubmit);
		doSubmit.setOnClickListener(this);

		need_amountEdi = (EditText) findViewById(R.id.need_amountEdi);

		tendermoney = Float.parseFloat(mintendermoney);
		String resultmintendermoney = String.format("%.2f", tendermoney);

//		need_amountEdi.setHint("每次投标至少" + resultmintendermoney + "元");

		String resultketoujine = String.format("%.2f", ketoujine);
		
		ketoujineView.setText(resultketoujine);

		need_amountEdi.addTextChangedListener(new TextWatcher() {

			private boolean running = false;

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,int arg3) {
				saveTwoPoint(arg0);

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable arg0) {
				if (!running) {
					running = true;
					handler.sendEmptyMessage(0);
					running = false;
				}

			}
		});
	}
	
	public void toGetInfo() {
		if (!Tools.isEmpty(MyApp.token)) {
			if (NetUtil.isNetworkConnected(this)) {
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
			try {
				if (data.getInt("errorcode") == 0) {
					if (!TextUtils.isEmpty(data.get("dataresult").toString())) {
						JSONObject json = data.getJSONObject("dataresult");// cashoutAmount
						
						availableAmount = json.getString("availableAmount");
						
						yueView.setText(availableAmount);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
				try {
					String str = data.getString("errormsg");
					Toast.makeText(InvestNumAc.this, str, 1).show();

					if (data.getString("errorcode").equals(Consts.NOT_LOGIN)) {
						MyApp.token = null;
						SharePrefUtil.clearUserInfo(InvestNumAc.this);

						startActivityForResult(new Intent(InvestNumAc.this,LoginActivity.class), 1003);
					}
				} catch (JSONException ee) {
					ee.printStackTrace();
				}
			}
		}
	};

	private void doSubmitInsert() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sn", sn);
		params.put("money", need_amountEdi.getText().toString().trim());
		ServiceClient.call(Consts.NetWork.LOANTENDER, params,submitMoneyCallback);
	}

	ServiceClient.Callback submitMoneyCallback = new ServiceClient.Callback() {
		@Override
		public void callback(JSONObject data) {
			// System.out.println("投资   " + data);
			try {
				if (data.get("dataresult") != null
						&& !data.get("dataresult").equals("")) {
					String str = data.getString("dataresult");
					Toast.makeText(InvestNumAc.this, str, 1).show();
					Message msg = new Message();
					msg.what = 1;
					msg.obj = in;
					mHandler.sendMessage(msg);
				} else {
					Toast.makeText(InvestNumAc.this,data.getString("errormsg"), 1).show();
					if (data.getString("errorcode").equals(Consts.NOT_LOGIN)) {
						MyApp.token = null;
						SharePrefUtil.clearUserInfo(InvestNumAc.this);
						startActivity(new Intent(InvestNumAc.this,LoginActivity.class));
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
				try {
					String str = data.getString("errormsg");
					Toast.makeText(InvestNumAc.this, str, 1).show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					Toast.makeText(InvestNumAc.this, "投资失败", 1).show();
				}
			}
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_header_iv_left:
			finish();
			break;
		case R.id.doSubmit:
			if (TextUtils.isEmpty(MyApp.token)) {
				startActivity(new Intent(this, LoginActivity.class));
			} else if (TextUtils.isEmpty(need_amountEdi.getText().toString().trim())) {
				Toast.makeText(this, "请输入投资金额", Toast.LENGTH_SHORT).show();
			} else if (Float.parseFloat(need_amountEdi.getText().toString().trim()) <= 0) {
//				Toast.makeText(this, "每次投标至少"+mintendermoney+"元", Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "投标金额不能小于等于0", Toast.LENGTH_SHORT).show();
			} else {
				doSubmitInsert();
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 使用户只能输入小数点后两位
	 */
	public void saveTwoPoint(CharSequence s) {
		if (s.toString().contains(".")) {
			if (s.length() - 1 - s.toString().indexOf(".") > 2) {
				s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
				need_amountEdi.setText(s);
				need_amountEdi.setSelection(s.length());
			}
		}
	}

	protected void getShouyi() {
		try {
			float monthtate = Float.parseFloat(yearrate) / 12 / 100;
			int intmonth = Integer.parseInt(loanmonth);
			if (repayment.equals("付息还本")) {
				shouyi = needloanmoney * monthtate * intmonth;
				String result = String.format("%.2f", shouyi);
				tv_shouyi.setText(result + "元");
			} else if (repayment.equals("等额本息")) {
				Interest in = new Interest();
				in.setCapital(new BigDecimal(0));
				in.setMonInt(new BigDecimal(0));
				in.setSurplus(new BigDecimal(needloanmoney));
				double b = Math.pow(1 + monthtate, intmonth);
				// 每月还款金额
				double myhkje = needloanmoney * monthtate * b / (b - 1);
				cope(in, new BigDecimal(monthtate), new BigDecimal(myhkje),
						intmonth);
				String result = String.format("%.2f", shouyi);
				
				tv_shouyi.setText(result + "元");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 利息 = 上期剩余本金 X 月利率，剩余本金 = 上期剩余本金 - 本期收取本金（第0期剩余本金即为原始出借金额）。
	public void cope(Interest last, BigDecimal monthIntRate, BigDecimal monPay,int month) {
		BigDecimal capital = last.getSurplus().multiply(monthIntRate);
		BigDecimal monInt = monPay.subtract(capital);
		BigDecimal surplus = last.getSurplus().subtract(monInt);
		last.setCapital(capital);
		last.setMonInt(monInt);
		shouyi = shouyi + capital.floatValue();
		last.setSurplus(surplus);
		--month;
		
		if (month > 0) {
			cope(last, monthIntRate, monPay, month);
		}
	}

	public void userHandler(View v) {
		startActivity(new Intent(this, LoginActivity.class));
	}

	public void backHandler(View v) {
		finish();
	}
}
