package com.famous.zhifu.loan.famouscarloan.account;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.activity.ActivityInterface;

public class BankVerifyActivity extends Activity implements ActivityInterface,
		OnClickListener {
	private View mView;
	private TextView mTvTitle;
	private ImageView mIvRight, mIvLeft;
	private Button submit;
	private EditText editName, editIdcard;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		mView = getLayoutInflater()
				.inflate(R.layout.activity_name_verify, null);
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

		submit = (Button) mView.findViewById(R.id.submit);
		editName = (EditText) mView.findViewById(R.id.editName);
		editIdcard = (EditText) mView.findViewById(R.id.editIdcard);
	}

	@Override
	public void initData() {
		mTvTitle.setText(getResources().getString(R.string.verify_name));
		mIvRight.setVisibility(View.GONE);
	}

	@Override
	public void addAction() {
		mIvLeft.setOnClickListener(this);
		submit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_header_iv_left:
			finish();
			break;
		case R.id.submit:

			break;

		default:
			break;
		}
	}
}
