package com.famous.zhifu.loan.famouscarloan.investlist;

import net.tsz.afinal.FinalBitmap;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.famous.zhifu.loan.R;

public class BigImageAc extends Activity {
	private ImageView bigImg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bigimage);

		TextView title = (TextView) findViewById(R.id.main_header_tv_text);
		bigImg = (ImageView) findViewById(R.id.bigImg);

		FinalBitmap fb = FinalBitmap.create(this);
		String imgUrl = getIntent().getStringExtra("url");

		// System.out.println("imgUrl  :" + imgUrl);
		String name = getIntent().getStringExtra("name");
		title.setText(name);
		fb.display(bigImg, imgUrl);

	}

	public void backHandler(View v) {
		finish();
	}
}
