package com.famous.zhifu.loan.famouscarloan.account;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.activity.ActivityInterface;
import com.famous.zhifu.loan.entity.ProblemInfo;

public class UsuallyProblemDetailActivity extends Activity implements ActivityInterface {
	private View mView;
	private TextView mTvTitle, title, content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		mView = getLayoutInflater().inflate(R.layout.activity_usually_problem_detail, null);
		setContentView(mView);
		findView();
		initData();
		addAction();
	}

	@Override
	public void findView() {
		ProblemInfo problem = (ProblemInfo) getIntent().getSerializableExtra(
				"problem");

		mTvTitle = (TextView) mView.findViewById(R.id.main_header_tv_text);

		title = (TextView) findViewById(R.id.title);
		content = (TextView) findViewById(R.id.content);

		if (problem != null) {
			title.setText(problem.getTitle());
			content.setText("      " + problem.getContent());
		}
	}

	@Override
	public void initData() {
		mTvTitle.setText(getResources().getString(R.string.usually_problem));
	}

	@Override
	public void addAction() {
		
	}
	
	public void backHandler(View v){
		finish();
	}
}
