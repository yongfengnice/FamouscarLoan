package com.famous.zhifu.loan.activity;

import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.famouscarloan.account.AccountActivity;
import com.famous.zhifu.loan.famouscarloan.account.SettingActivity;
import com.famous.zhifu.loan.famouscarloan.home.HomeActivity;
import com.famous.zhifu.loan.famouscarloan.investlist.InvestListActivity;
import com.famous.zhifu.loan.util.SharePrefUtil;
import com.famous.zhifu.loan.util.Tools;
import com.famous.zhifu.loan.view.dialog.ExitDialog;


public class MainActivity extends ActivityGroup implements ActivityInterface,
		OnClickListener {
	private View mView;
	// private RadioGroup mRadioGroup;
	// private RadioButton mRHome, mRInVest, mRNotice, mRAccount;
	private LocalActivityManager localManager;
	private FrameLayout milkContain;
	private LayoutParams layoutParams;
	private TextView tv_account, tv_borrow, tv_invest, tv_home;
	private ImageView img_account, img_borrow, img_invest, img_home;
	private View[] imgViews = { img_home, img_invest, img_borrow, img_account };
	private LinearLayout home, invest, borrow, account;
	private View[] views = { home, invest, borrow, account };
	private DisplayMetrics dm;
	private ExitDialog exitDialog;
	
	private TextView shadeView = null;
	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mView = getLayoutInflater().inflate(R.layout.activity_main, null);
		setContentView(mView);
		MyApp.getInstance().addActivity(this);
		MyApp.username = SharePrefUtil.getString(this,Consts.SharePref.USERNAME, "");
		MyApp.token = SharePrefUtil.getUserInfoString(this,Consts.SharePref.TOKEN, "");
		findView();
		initData();
		addAction();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if (Tools.isEmpty(MyApp.token)) {
			shadeView.setVisibility(View.VISIBLE);
		} else {
			shadeView.setVisibility(View.GONE);
		}
	}
	
	private void showGesture(){
		
	}
	
	/**
	 * 代码触发下面的TAB
	 * @param flag
	 */
	public void setClicKHandler(int flag){
		views[flag].performClick();
	}
	
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode==10000 && resultCode==11000) {
			views[2].performClick();
		}
	}

	@Override
	public void findView() {
		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		MyApp.screenWidth = dm.widthPixels;
		MyApp.screenHeight = dm.heightPixels;
		
		localManager = getLocalActivityManager();

		milkContain = (FrameLayout) mView.findViewById(R.id.milkContain);
		shadeView = (TextView) findViewById(R.id.main_shade);
		
		tv_home = (TextView) findViewById(R.id.tv_home);
		tv_invest = (TextView) findViewById(R.id.tv_invest);
		tv_borrow = (TextView) findViewById(R.id.tv_borrow);
		tv_account = (TextView) findViewById(R.id.tv_account);

		imgViews[0] = (ImageView) findViewById(R.id.img_home);
		imgViews[1] = (ImageView) findViewById(R.id.img_invest);
		imgViews[2] = (ImageView) findViewById(R.id.img_borrow);
		imgViews[3] = (ImageView) findViewById(R.id.img_account);

		views[0] = (LinearLayout) findViewById(R.id.home);
		views[1] = (LinearLayout) findViewById(R.id.invest);
		views[2] = (LinearLayout) findViewById(R.id.borrow);
		views[3] = (LinearLayout) findViewById(R.id.more);


		layoutParams = new LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT);

		Intent intent = new Intent(this, HomeActivity.class);
		loadView(intent, "home");
	}

	@Override
	public void initData() {

	}

	@Override
	public void addAction() {
		for (int i = 0, j = views.length; i < j; i++) {
			views[i].setOnClickListener(this);
		}
	}

	private void loadView(Intent intent, String tag) {
		try {
			milkContain.removeAllViews();
			milkContain.addView(
					localManager.startActivity(tag,
							intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
							.getDecorView(), layoutParams);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.home:
			imgViews[0].setBackgroundResource(R.drawable.home_select);
			imgViews[1].setBackgroundResource(R.drawable.invest_list_not_select);
			imgViews[2].setBackgroundResource(R.drawable.notice_not_select);
			imgViews[3].setBackgroundResource(R.drawable.my_account_not_select);

			tv_home.setTextColor(0xffB49060);
			tv_invest.setTextColor(0xFF8F8F8F);
			tv_borrow.setTextColor(0xFF8F8F8F);
			tv_account.setTextColor(0xFF8F8F8F);

			intent = new Intent(this, HomeActivity.class);
			loadView(intent, "home");
			break;
		case R.id.invest:
			imgViews[0].setBackgroundResource(R.drawable.home_none_select);
			imgViews[1].setBackgroundResource(R.drawable.invest_list_select);
			imgViews[2].setBackgroundResource(R.drawable.notice_not_select);
			imgViews[3].setBackgroundResource(R.drawable.my_account_not_select);
			
			tv_home.setTextColor(0xFF8F8F8F);
			tv_invest.setTextColor(0xffB49060);
			tv_borrow.setTextColor(0xFF8F8F8F);
			tv_account.setTextColor(0xFF8F8F8F);

			intent = new Intent(this, InvestListActivity.class);
			loadView(intent, "invest");
			break;
		case R.id.borrow:
			if (Tools.isEmpty(MyApp.token)) {
				startActivityForResult(new Intent(this,LoginActivity.class), 1003);
				
				return;
			} 
			
			imgViews[0].setBackgroundResource(R.drawable.home_none_select);
			imgViews[1].setBackgroundResource(R.drawable.invest_list_not_select);
			imgViews[2].setBackgroundResource(R.drawable.notice_select);
			imgViews[3].setBackgroundResource(R.drawable.my_account_not_select);

			tv_home.setTextColor(0xFF8F8F8F);
			tv_invest.setTextColor(0xFF8F8F8F);
			tv_borrow.setTextColor(0xffB49060);
			tv_account.setTextColor(0xFF8F8F8F);

			intent = new Intent(this, AccountActivity.class);
			
			loadView(intent, "account");
			break;
		case R.id.more:
			imgViews[0].setBackgroundResource(R.drawable.home_none_select);
			imgViews[1].setBackgroundResource(R.drawable.invest_list_not_select);
			imgViews[2].setBackgroundResource(R.drawable.notice_not_select);
			imgViews[3].setBackgroundResource(R.drawable.my_account_select);


			tv_home.setTextColor(0xFF8F8F8F);
			tv_invest.setTextColor(0xFF8F8F8F);
			tv_borrow.setTextColor(0xFF8F8F8F);
			tv_account.setTextColor(0xffB49060);

			intent = new Intent(this, SettingActivity.class);
			loadView(intent, "more");
			break;

		default:
			break;
		}
	}
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_UP
				&& event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (exitDialog == null) {
				exitDialog = new ExitDialog(this);
			}
			exitDialog.showDilog();
			return true;
		}
		return super.dispatchKeyEvent(event);
	}
	
	public void shadeHandler(View v){
		Intent intent = new Intent(this,LoginActivity.class);
		
		startActivityForResult(intent, 10000);
	}
	
}
