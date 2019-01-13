package com.famous.zhifu.loan.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.famous.zhifu.loan.R;

/**
 * 用于等待的 progressBar
 * 
 */
public class View_Loading extends RelativeLayout {

	private ProgressBar pb;

	public View_Loading(Context context, AttributeSet attrs) {
		super(context, attrs);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.view_loading, null);
//		pb = (ProgressBar) v.findViewById(R.id.pb);
//		ImageView iv1 = (ImageView) v.findViewById(R.id.iv1);
		// iv1.getBackground().setAlpha(240);// 0~255透明度值
		this.addView(v, -1, -1);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return true;
	}
}
