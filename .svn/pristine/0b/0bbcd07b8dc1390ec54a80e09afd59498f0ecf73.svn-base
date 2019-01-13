package com.famous.zhifu.loan.famouscarloan.account;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.activity.ActivityInterface;

public class PhoneConfirmActivity extends Activity implements ActivityInterface {
	private View mView;
	private TextView mTvTitle;
	private ImageView mIvRight, mIvLeft;
	private Button contact;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		mView = getLayoutInflater().inflate(R.layout.phone_verify, null);
		setContentView(mView);
		findView();
		initData();
		addAction();
	}

	@Override
	public void findView() {
		mTvTitle = (TextView) mView.findViewById(R.id.main_header_tv_text);
		mIvRight = (ImageView) mView.findViewById(R.id.main_header_iv_right);
		mIvLeft = (ImageView) mView.findViewById(R.id.main_header_iv_left);

		contact = (Button) mView.findViewById(R.id.contact);
	}

	@Override
	public void initData() {
		mTvTitle.setText(getResources().getString(R.string.verify_mobile));
		mIvRight.setVisibility(View.GONE);
	}

	@Override
	public void addAction() {
		mIvLeft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		contact.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentkfrx = new Intent(Intent.ACTION_DIAL, Uri
						.parse("tel:400-600-6789"));
				startActivity(intentkfrx);
			}
		});
	}
}
