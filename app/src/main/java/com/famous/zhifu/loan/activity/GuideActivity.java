package com.famous.zhifu.loan.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.adapter.GuideAdapter;

public class GuideActivity extends BaseActivity {

	private List<View> mLists = new ArrayList<View>();

	private ViewPager mViewPager = null;
	private GuideAdapter adapter = null;

	private LayoutInflater mLayoutInflater;
	private LinearLayout mLinearLayout;

	private int current = 0;
	private ImageView[] points = new ImageView[3];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_guide);
		
		init();
	}

	private void init() {
		// 实例化
		mLayoutInflater = LayoutInflater.from(this);

		mLinearLayout = (LinearLayout) this.findViewById(R.id.activity_guide_liear);

		mViewPager = (ViewPager) this.findViewById(R.id.activity_guide_viewpager);

		View view1 = mLayoutInflater.inflate(R.layout.guide_item_01, null);
		View view2 = mLayoutInflater.inflate(R.layout.guide_item_02, null);
		View view3 = mLayoutInflater.inflate(R.layout.guide_item_03, null);

		mLists.add(view1);
		mLists.add(view2);
		mLists.add(view3);

		mViewPager.setAdapter(adapter = new GuideAdapter(mLists));
		mViewPager.setOnPageChangeListener(new MyOnPagerChangerListener());

		initPoints();
	}

	// 初始化下面的小圆点
	private void initPoints() {
		for (int i = 0; i < 3; i++) {
			points[i] = (ImageView) mLinearLayout.getChildAt(i);
			points[i].setImageResource(R.drawable.guide_point);
		}
		current = 0;// 默认在第一页

		points[current].setImageResource(R.drawable.guide_point_cur); // 此刻处于第一页，把第一页的小圆圈设置为unenabled
	}

	class MyOnPagerChangerListener implements OnPageChangeListener {
		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int arg0) {
			points[arg0].setImageResource(R.drawable.guide_point_cur);
			points[current].setImageResource(R.drawable.guide_point);
			current = arg0;
		}

	}
	
	public void jumpToMain(View view){
		startActivity(new Intent(this, MainActivity.class));
//		startActivity(new Intent(this, GestureEditActivity.class));
		
		finish();
	}

}
