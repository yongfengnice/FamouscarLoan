package com.famous.zhifu.loan.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.util.SharePrefUtil;
import com.famous.zhifu.loan.util.Tools;

public class SplashActivity extends Activity {

	private Handler mHandler = new Handler();
	private int count = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = View.inflate(SplashActivity.this, R.layout.spalsh, null);
		setContentView(view);
		Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
		view.startAnimation(animation);
		animation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation arg0) {
			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				mHandler.postDelayed(new Runnable() {
					@Override
					public void run() {
						goHome();
					}

				}, 100);
			}
		});

	}

	private void goHome() {
		if (SharePrefUtil.getInt(SplashActivity.this, "count", 0) == 0) {
			SharePrefUtil.saveInt(SplashActivity.this, "count", ++count);
			
			startActivity(new Intent(SplashActivity.this, GuideActivity.class));
			
			finish();
		} else {
			jumoToNext();
		}

	}
	
	private void jumoToNext(){
		if (SharePrefUtil.getBoolean(this, "switch", false) && !Tools.isEmpty(SharePrefUtil.getString(this, "Gesture", null))) {
			startActivity(new Intent(SplashActivity.this, GestureVerifyActivity.class));
		}else {
			startActivity(new Intent(SplashActivity.this, MainActivity.class));
		}
		
		finish();
	}

}
