package com.famous.zhifu.loan.famouscarloan.account;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.activity.Consts;
import com.famous.zhifu.loan.internet.ServiceClient;
import com.famous.zhifu.loan.util.DialogUtil;
import com.famous.zhifu.loan.util.NetUtil;
import com.famous.zhifu.loan.util.Tools;
import com.famous.zhifu.loan.view.View_Loading;
import com.famous.zhifu.loan.view.wheelview.ArrayWheelAdapter;
import com.famous.zhifu.loan.view.wheelview.WheelView;

public class ZdtbAc extends FragmentActivity implements OnClickListener {
	private View_Loading loading;
	private View dialogView;
	private LinearLayout lll;
	private WheelView wheelview;
	private TextView mTvTitle,repay_way, min_deadline, max_deadline, rate_choice,
			cancel, confirm;
	private TextView submit,reset;
	// 利率期限下限 默认为 -1不限
	private String tender_val, rateLine = "-1", minLineNum = "1", maxLineNum = "1";
	// 是否开启自动投标
	private String isOpen = "1";
	private String id;// 自动投标的ID
	private EditText editNum;
	
	private RadioGroup radioGroup;
	
	private JSONObject cacheObj = null;
	/**
	 * 还款方式: -1 不限 1、等额本息 2、付息还本 3、本息到付
	 */
	private String[] repayType = { "-1", "1", "2", "3" };
	private String repayFlag = "-1"; // 默认为不限

	private int choiceFlag;
	String[] acquireArray = { "1", "2", "3", "4", "5", "6", "7", "8", "9","10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20" };
	String[] strArray = { "不限", "等额本息", "付息还本", "本息到付" };
	String[] monthArray = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
			"11", "12" };
	String[] rateArray = { "不限", "5", "6", "7", "8", "9", "10", "11", "12",
			"13", "14", "15", "16", "17", "18", "19", "20" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zdtb);

		infaterView();
		initView();
		initPopAge();

		if (!Tools.isEmpty(id)) {
			getDatas();
		}
	}

	public void initView() {
		loading = (View_Loading) findViewById(R.id.loading);
		
		mTvTitle = (TextView) findViewById(R.id.main_header_tv_text);
		mTvTitle.setText(getResources().getString(R.string.account_auto_bid));

		repay_way = (TextView) findViewById(R.id.repay_way);
		repay_way.setOnClickListener(this);
		min_deadline = (TextView) findViewById(R.id.min_deadline);
		min_deadline.setOnClickListener(this);
		max_deadline = (TextView) findViewById(R.id.max_deadline);
		max_deadline.setOnClickListener(this);
		rate_choice = (TextView) findViewById(R.id.rate_choice);
		rate_choice.setOnClickListener(this);

		editNum = (EditText) findViewById(R.id.editNum);

		submit = (TextView) findViewById(R.id.submit);
		submit.setOnClickListener(this);
		
		reset = (TextView) findViewById(R.id.reset);
		reset.setOnClickListener(this);
		
		
		radioGroup = (RadioGroup) findViewById(R.id.zdtb_radiogrp);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				switch (arg1) {
				case R.id.zdtb_radiobtn_shi:
					isOpen = "1";
					break;
					
				case R.id.zdtb_radiobtn_fou:
					isOpen = "0";
					break;

				default:
					break;
				}
			}
		});
	}

	private void initPopAge() {
		wheelview = (WheelView) dialogView.findViewById(R.id.wheelview);
		wheelview.setLabel("");// 添加文字
		wheelview.TEXT_SIZE = 28;
	}

	private void infaterView() {
		id = getIntent().getStringExtra("id");

		LayoutInflater inflater = LayoutInflater.from(this);
		dialogView = inflater.inflate(R.layout.dialog_fragment, null);

		lll = (LinearLayout) dialogView.findViewById(R.id.lll);
		lll.setOnClickListener(this);

		cancel = (TextView) dialogView.findViewById(R.id.cancel);
		cancel.setOnClickListener(this);
		confirm = (TextView) dialogView.findViewById(R.id.confirm);
		confirm.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.lll:
			DialogUtil.removeDialog(v.getContext());
			break;
		case R.id.repay_way:
			choiceFlag = 1;
			wheelview.setCyclic(false);// 可循环滚动
			wheelview.setCurrentItem(0);
			wheelview.setAdapter(new ArrayWheelAdapter<String>(strArray));
			DialogUtil.showFragment(dialogView);
			break;
		case R.id.min_deadline:
			choiceFlag = 2;
			wheelview.setCyclic(true);// 可循环滚动
			wheelview.setCurrentItem(0);
			wheelview.setAdapter(new ArrayWheelAdapter<String>(monthArray));
			DialogUtil.showFragment(dialogView);
			break;
		case R.id.max_deadline:
			choiceFlag = 3;
			wheelview.setCyclic(true);// 可循环滚动
			wheelview.setCurrentItem(0);
			wheelview.setAdapter(new ArrayWheelAdapter<String>(monthArray));
			DialogUtil.showFragment(dialogView);
			break;
		case R.id.rate_choice:
			choiceFlag = 4;
			wheelview.setCyclic(true);// 可循环滚动
			wheelview.setCurrentItem(0);
			wheelview.setAdapter(new ArrayWheelAdapter<String>(rateArray));
			DialogUtil.showFragment(dialogView);
			break;
		case R.id.cancel:
			DialogUtil.removeDialog(v.getContext());
			
			break;
		case R.id.submit:
			String num = editNum.getText().toString().trim();
			String min = min_deadline.getText().toString().trim();
			String max = max_deadline.getText().toString().trim();

			if (Tools.isEmpty(num)) {
				Toast.makeText(this, "请输入投标金额", 1).show();
			} else if (Integer.parseInt(min) > Integer.parseInt(max)) {
				Toast.makeText(this, "借款最小期限不能大于最大期限", 1).show();
			} else {
				submitDatas(num);
			}
			break;
			
		case R.id.reset:
			try {
				if (null==cacheObj) {
					return;
				}
				
				fillDefault(cacheObj);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			break;
		case R.id.confirm:
			int position = wheelview.getCurrentItem();
			switch (choiceFlag) {
			case 1:
				repay_way.setText(strArray[position]);
				repayFlag = repayType[position];
				break;
			case 2:
				min_deadline.setText(monthArray[position]);
				minLineNum = monthArray[position];
				break;
			case 3:
				max_deadline.setText(monthArray[position]);
				maxLineNum = monthArray[position];
				break;
			case 4:
				String a = rateArray[position];
				rate_choice.setText(a);
				if (Tools.isEquals("不限", a)) {
					rateLine = "-1";
				} else {
					rateLine = a;
				}
				break;
			default:
				break;
			}
			DialogUtil.removeDialog(v.getContext());
		}
	}

	public void getData(String type) {
		if (NetUtil.isNetworkConnected(this)) {
			loading.setVisibility(View.VISIBLE);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("time", type);
			ServiceClient.call(Consts.NetWork.ZJLS, params, verifyback);
		} else {
			Toast.makeText(this, Consts.NONETWORK, 1).show();
		}
	}

	ServiceClient.Callback verifyback = new ServiceClient.Callback() {
		@Override
		public void callback(JSONObject data) {
			// System.out.println("资金流水    " + data);

			loading.setVisibility(View.GONE);
			try {
				if (data.getInt("errorcode") == 0) {
					JSONObject json = data.getJSONObject("dataresult");
				}
			} catch (JSONException e) {
				e.printStackTrace();
				try {
					String str = data.getString("errormsg");
					Toast.makeText(ZdtbAc.this, str, 1).show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					Toast.makeText(ZdtbAc.this, Consts.UNKNOWN_FAULT, 1).show();
				}
			}
		}
	};

	// 获取自动投标信息
	private void getDatas() {
		if (NetUtil.isNetworkConnected(this)) {
			loading.setVisibility(View.VISIBLE);
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("type", "autotender");
			ServiceClient.call(Consts.NetWork.ZDTB, param, callback);
		} else {
			Toast.makeText(this, Consts.NONETWORK, 1).show();
		}
	}

	ServiceClient.Callback callback = new ServiceClient.Callback() {
		@Override
		public void callback(JSONObject data) {
			// System.out.println("获取自动投标信息  " + data);
			loading.setVisibility(View.GONE);
			
			try {
				if (data.getInt("errorcode") == 0) {
					JSONObject json = data.getJSONObject("dataresult");
					JSONArray array = json.getJSONArray("data");
					JSONObject obj = array.getJSONObject(0);
					
					cacheObj = obj;

					id = obj.getString("id");
					
					fillDefault(obj);
				}
			} catch (JSONException e) {
				e.printStackTrace();
				try {
					String msg = data.getString("errormsg");
					Toast.makeText(ZdtbAc.this, msg, 1).show();
					ZdtbAc.this.finish();
				} catch (JSONException ee) {
					ee.printStackTrace();
					Toast.makeText(ZdtbAc.this, Consts.UNKNOWN_FAULT, 1).show();
					ZdtbAc.this.finish();
				}
			}
		}
	};
	
	//填充默认值
	private void fillDefault(JSONObject obj) throws JSONException{
		tender_val = obj.getString("tender_val");
		editNum.setText(tender_val);
		
		minLineNum = obj.getString("min_loanmonth");
		min_deadline.setText(minLineNum);
		maxLineNum = obj.getString("max_loanmonth");
		max_deadline.setText(maxLineNum);

		rateLine = obj.getString("minyearrate");
		
		if (Tools.isEquals(rateLine, "-1")) {
			rate_choice.setText("不限");
		} else {
			rate_choice.setText(rateLine);
		}

		repayFlag = obj.getString("repayment");
		if (Tools.isEquals(repayFlag, "-1")) {
			repay_way.setText("不限");
		} else if (Tools.isEquals(repayFlag, "1")) {
			repay_way.setText("等额本息");
		} else if (Tools.isEquals(repayFlag, "2")) {
			repay_way.setText("付息还本");
		} else if (Tools.isEquals(repayFlag, "3")) {
			repay_way.setText("本息到付");
		}

		isOpen = obj.getString("status");
		
		if (Tools.isEquals(isOpen, "1")) {
			radioGroup.check(R.id.zdtb_radiobtn_shi);
		} else {
			radioGroup.check(R.id.zdtb_radiobtn_fou);
		}
		
	}

	// 编辑自动投标
	private void submitDatas(String num) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("type", "autotender_add");
		param.put("id", id);
		param.put("tender_val", num);
		param.put("tender_type", "1");
		param.put("status", isOpen);
		param.put("repayment", repayFlag);
		param.put("min_loanmonth", minLineNum);
		param.put("max_loanmonth", maxLineNum);
		param.put("minyearrate", rateLine);
		
		ServiceClient.call(Consts.NetWork.ZDTB, param, updateCallback);
	}

	ServiceClient.Callback updateCallback = new ServiceClient.Callback() {
		@Override
		public void callback(JSONObject data) {
			// System.out.println("编辑自动投标  " + data);
			try {
				if (data.getInt("errorcode") == 0) {
					JSONObject obj = (JSONObject) data.get("dataresult");
					if (!TextUtils.isEmpty(obj.toString())) {
						String msg = obj.getString("msg");
						Toast.makeText(ZdtbAc.this, msg, 1).show();

						Intent i = new Intent();
						i.putExtra("isOpen", isOpen);
						i.putExtra("id", id);
						setResult(1009, i);
						ZdtbAc.this.finish();
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
				Toast.makeText(ZdtbAc.this, "操作失败", 1).show();
				ZdtbAc.this.finish();
			}
		}
	};
	
	public void backHandler(View v) {
		finish();
	}

}
