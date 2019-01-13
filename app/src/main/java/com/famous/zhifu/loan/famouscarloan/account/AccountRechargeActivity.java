package com.famous.zhifu.loan.famouscarloan.account;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.activity.ActivityInterface;

public class AccountRechargeActivity extends Activity implements ActivityInterface{
    private View mView;
    private TextView mTvTitle;
    private ImageView mIvRight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        mView = getLayoutInflater().inflate(R.layout.account_recharge,null);
        setContentView(mView);
        findView();
        initData();
        addAction();
    }

    @Override
    public void findView() {
        mTvTitle = (TextView)mView.findViewById(R.id.main_header_tv_text);
        mIvRight = (ImageView)mView.findViewById(R.id.main_header_iv_right);
        mView.findViewById(R.id.main_header_iv_left).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
    }

    @Override
    public void initData() {
        mTvTitle.setText(getResources().getString(R.string.account_recharge));
        mIvRight.setVisibility(View.GONE);
    }

    @Override
    public void addAction() {

    }
}
