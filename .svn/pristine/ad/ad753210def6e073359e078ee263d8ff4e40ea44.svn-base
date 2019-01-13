package com.famous.zhifu.loan.famouscarloan.home;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.activity.ActivityInterface;
import com.famous.zhifu.loan.activity.BaseActivity;

public class FindPasswordVerifyActivity extends BaseActivity implements ActivityInterface {
    private View mView;
    private Button mBtnNext;
    private TextView mTvTitle;
    private ImageView mIvRight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = getLayoutInflater().inflate(R.layout.find_password_verify,null);
        setContentView(mView);
        findView();
        initData();
        addAction();
    }

    @Override
    public void findView() {
        mBtnNext = (Button)mView.findViewById(R.id.find_password_btn_complete);
        mTvTitle = (TextView)mView.findViewById(R.id.main_header_tv_text);
        mIvRight = (ImageView)mView.findViewById(R.id.main_header_iv_right);
    }

    @Override
    public void initData() {
        mTvTitle.setText(getString(R.string.find_password));
        mIvRight.setVisibility(View.GONE);
    }

    @Override
    public void addAction() {
        mBtnNext.setOnClickListener(new MyClickListener());
    }

    private class MyClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.find_password_btn_complete://下一步
                    break;
            }
        }
    }
}
