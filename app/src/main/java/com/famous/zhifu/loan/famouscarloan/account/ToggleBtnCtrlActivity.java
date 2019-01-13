package com.famous.zhifu.loan.famouscarloan.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.activity.GestureEditActivity;
import com.famous.zhifu.loan.util.SharePrefUtil;

public class ToggleBtnCtrlActivity extends Activity {
	private ToggleButton tButton = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_toggle_ctrl);
		
		cusInit();
	}
	
	private void cusInit(){
		TextView mTvTitle = (TextView) findViewById(R.id.main_header_tv_text);
		mTvTitle.setText("手势密码设置");
		
		tButton = (ToggleButton) findViewById(R.id.activity_toggle_ctrl_tglSound);
		
		boolean temp = SharePrefUtil.getBoolean(this, "switch", false);
		tButton.setChecked(temp);
		
		tButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
				SharePrefUtil.saveBoolean(ToggleBtnCtrlActivity.this, "switch", isChecked);
				
			}
		});
	}

	/**
	 * 修改手势密码
	 * @param view
	 */
	public void modifyHandler(View view){
		Intent intent = new Intent(this, GestureEditActivity.class);
		
		startActivityForResult(intent, 10005);
	}
	
	public void backHandler(View v){
		finish();
	}
	
}
