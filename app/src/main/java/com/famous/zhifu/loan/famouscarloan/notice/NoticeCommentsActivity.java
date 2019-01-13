package com.famous.zhifu.loan.famouscarloan.notice;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.activity.ActivityInterface;

public class NoticeCommentsActivity extends Activity implements ActivityInterface{
    private View mView;
    private TextView mTvTitle;
    private ImageView mIvRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        mView = getLayoutInflater().inflate(R.layout.activity_usually_problem_detail,null);
        setContentView(mView);
        findView();
        initData();
        addAction();
    }

    @Override
    public void findView() {
        mTvTitle = (TextView)mView.findViewById(R.id.main_header_tv_text);
        mIvRight = (ImageView)mView.findViewById(R.id.main_header_iv_right);
    }

    @Override
    public void initData() {
        mTvTitle.setText(getResources().getString(R.string.usually_problem));
        mIvRight.setImageResource(R.drawable.edit);
    }

    @Override
    public void addAction() {
    }
}
