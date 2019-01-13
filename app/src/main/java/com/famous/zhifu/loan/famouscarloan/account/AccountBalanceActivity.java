package com.famous.zhifu.loan.famouscarloan.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.activity.ActivityInterface;
import com.famous.zhifu.loan.view.dialog.PromptDialog;

public class AccountBalanceActivity extends Activity implements
		ActivityInterface {
	private View mView;
	private TextView mTvTitle, mTvRecharge, mTvWithDeposit;
	private PromptDialog dialog;
	private String availableAmount;
	private String cashoutAmount;
	private TextView balance,doingNum;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		mView = getLayoutInflater().inflate(R.layout.activity_account_balance,null);
		setContentView(mView);
		findView();
		initData();
		addAction();
	}

	@Override
	public void findView() {
		Intent intent = getIntent();
		availableAmount = intent.getStringExtra("availableAmount");
		cashoutAmount = intent.getStringExtra("cashoutAmount");
		
		mTvTitle = (TextView) mView.findViewById(R.id.main_header_tv_text);
		mTvRecharge = (TextView) mView
				.findViewById(R.id.account_balance_tv_recharge);
		mTvWithDeposit = (TextView) mView
				.findViewById(R.id.account_balance_tv_withdraw_deposit);
		
		balance = (TextView) mView.findViewById(R.id.balance);
		doingNum = (TextView) mView.findViewById(R.id.doingNum);
	}

	@Override
	public void initData() {
		mTvTitle.setText(getString(R.string.account_balance));
		
		balance.setText(availableAmount);
		doingNum.setText(cashoutAmount);
	}

	@Override
	public void addAction() {
		mTvRecharge.setOnClickListener(new MyOnClickListener());
		mTvWithDeposit.setOnClickListener(new MyOnClickListener());
	}

	private class MyOnClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.main_header_iv_left:// 账户充值:
				finish();
				break;
			case R.id.account_balance_tv_recharge:// 账户充值:
				if (dialog == null) {
					dialog = new PromptDialog(AccountBalanceActivity.this);
				}
				dialog.showDilog();
				break;
			case R.id.account_balance_tv_withdraw_deposit:// 账户提现
				startActivity(new Intent(AccountBalanceActivity.this,
						WithdrawActivity.class));
				break;
			}
		}
	}
	
	public void backHandler(View v){
		finish();
	}
}
