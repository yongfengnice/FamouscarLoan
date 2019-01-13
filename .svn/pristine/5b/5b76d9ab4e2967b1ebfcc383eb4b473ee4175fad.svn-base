package com.famous.zhifu.loan.view.abviewpager;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class AbViewPagerAdapter extends PagerAdapter {

	/** The m context. */
	private Context mContext;

	/** The m list views. */
	private ArrayList<View> mListViews = null;

	/** The m views. */
	private HashMap<Integer, View> mViews = null;

	public AbViewPagerAdapter(Context context, ArrayList<View> mListViews) {
		this.mContext = context;
		this.mListViews = mListViews;
		this.mViews = new HashMap<Integer, View>();
	}

	/**
	 * 描述：获取数量.
	 */
	@Override
	public int getCount() {
		// 此时修改为了实现viewPager无限滑动
		return mListViews.size();
	}

	/**
	 * 描述：Object是否对应这个View.
	 */
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	/**
	 * 描述：显示View.
	 */
	@Override
	public Object instantiateItem(View container, int position) {
		// 这里直接给position也可以， position取膜集合长度 是为了适应无限滑动
		// View v = mListViews.get(position % mListViews.size());
		View v = mListViews.get(position);
		((ViewPager) container).addView(v);
		return v;
	}

	/**
	 * 描述：移除View.
	 */
	@Override
	public void destroyItem(View container, int position, Object object) {
		// 修改前
		((ViewPager) container).removeView((View) object);

		// 修改后 适应无线滑动
		// ((ViewPager) container).removeView(mListViews.get(position
		// % mListViews.size()));

	}

	/**
	 * 描述：很重要，否则不能notifyDataSetChanged.
	 */

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

}
