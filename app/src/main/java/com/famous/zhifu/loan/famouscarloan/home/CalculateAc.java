package com.famous.zhifu.loan.famouscarloan.home;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.activity.ActivityInterface;
import com.famous.zhifu.loan.activity.Consts;
import com.famous.zhifu.loan.internet.ServiceClient;
import com.famous.zhifu.loan.util.DialogUtil;
import com.famous.zhifu.loan.util.NetUtil;
import com.famous.zhifu.loan.util.Tools;
import com.famous.zhifu.loan.view.View_Loading;
import com.famous.zhifu.loan.view.wheelview.ArrayWheelAdapter;
import com.famous.zhifu.loan.view.wheelview.WheelView;

public class CalculateAc extends FragmentActivity implements ActivityInterface,
		OnClickListener {
	private View mView;
	private EditText editNum, editTime, editRate;
	private TextView calculator, reset, totalNum, rateNum, mTvTitle, cancel,
			confirm, editRepay;
	private View dialogView;
	private LinearLayout lll;
	private WheelView wheelview;
	String[] strArray = { "等额本息", "付息还本" };
	String[] strFlag = { "1", "2" };
	private String type;
	private double ratemoney, total;
	private View_Loading loading;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		mView = getLayoutInflater().inflate(R.layout.calculate, null);
		setContentView(mView);
		findView();
		initData();
		addAction();

		infaterView();
		initPopAge();
	}

	@Override
	public void findView() {
		mTvTitle = (TextView) mView.findViewById(R.id.main_header_tv_text);

		editNum = (EditText) mView.findViewById(R.id.editNum);
		editTime = (EditText) mView.findViewById(R.id.editTime);
		editRate = (EditText) mView.findViewById(R.id.editRate);
		editRepay = (TextView) mView.findViewById(R.id.editRepay);

		calculator = (TextView) mView.findViewById(R.id.calculator);
		reset = (TextView) mView.findViewById(R.id.reset);

		totalNum = (TextView) mView.findViewById(R.id.totalNum);
		rateNum = (TextView) mView.findViewById(R.id.rateNum);

		loading = (View_Loading) findViewById(R.id.loading);
	}

	@Override
	public void initData() {
		mTvTitle.setText("计算器");
	}

	@Override
	public void addAction() {
		calculator.setOnClickListener(this);
		reset.setOnClickListener(this);
		editRepay.setOnClickListener(this);
	}

	private void initPopAge() {
		wheelview = (WheelView) dialogView.findViewById(R.id.wheelview);
		wheelview.setLabel("");// 添加文字
		wheelview.TEXT_SIZE = 28;
	}

	private void infaterView() {
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
		case R.id.main_header_iv_left:
			finish();
			break;
		case R.id.calculator:
			calculator();
			break;
		case R.id.reset:
			editNum.setText("");
			editTime.setText("");
			editRate.setText("");
			editRepay.setText("");
			totalNum.setText("0.00");
			rateNum.setText("0.00");
			break;
		case R.id.editRepay:
			Tools.hideSoftInput(CalculateAc.this);
			
			wheelview.setCyclic(false);
			wheelview.setCurrentItem(0);
			wheelview.setAdapter(new ArrayWheelAdapter<String>(strArray));
			DialogUtil.showFragment(dialogView);
			break;
		case R.id.cancel:
			DialogUtil.removeDialog(v.getContext());
			break;
		case R.id.lll:
			DialogUtil.removeDialog(v.getContext());
			break;
		case R.id.confirm:
			int position = wheelview.getCurrentItem();
			editRepay.setText(strArray[position]);
			type = strFlag[position];
			DialogUtil.removeDialog(v.getContext());
			break;
		default:
			break;
		}
	}

	public void calculator() {
		String num = editNum.getText().toString().trim();
		String time = editTime.getText().toString().trim();
		String rate = editRate.getText().toString().trim();
		String repay = editRepay.getText().toString().trim();

		if (Tools.isEmpty(num)) {
			Toast.makeText(this, "存款金额不能空", 1).show();
		} else if (Tools.isEmpty(time)) {
			Toast.makeText(this, "存款期限不能空", 1).show();
		} else if (Tools.isEmpty(rate)) {
			Toast.makeText(this, "年利率不能空", 1).show();
		} else if (Tools.isEmpty(repay)) {
			Toast.makeText(this, "请选择还款方式", 1).show();
		} else {
			if (NetUtil.isNetworkConnected(this)) {
				loading.setVisibility(View.VISIBLE);
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("payment", type);
				params.put("money", num);
				params.put("month", time);
				params.put("yearrate", rate);
				ServiceClient.call(Consts.NetWork.TYDK, params, verifyback);
			} else {
				Toast.makeText(this, Consts.NONETWORK, 1).show();
			}
		}
	}

	ServiceClient.Callback verifyback = new ServiceClient.Callback() {
		@Override
		public void callback(JSONObject ret) {
			// System.out.println("计算器  " + ret);
			loading.setVisibility(View.GONE);
			try {
				if (ret.getInt("errorcode") == 0) {
					JSONObject obj = (JSONObject) ret.get("dataresult");
					ratemoney = obj.getDouble("ratemoney");
					total = obj.getDouble("total");

					totalNum.setText(Tools.sishewurubaoliuliangwei(total)+ "");
					rateNum.setText(Tools.sishewurubaoliuliangwei(ratemoney) + "");
				}
			} catch (JSONException e) {
				try {
					String str = ret.getString("errormsg");
					Toast.makeText(CalculateAc.this, str, 1).show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					Toast.makeText(CalculateAc.this, Consts.UNKNOWN_FAULT, 1)
							.show();
				}
			}
		}
	};
	
	public void backHandler(View v){
		finish();
	}

}
