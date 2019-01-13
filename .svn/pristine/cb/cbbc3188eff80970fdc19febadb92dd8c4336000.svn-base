package com.famous.zhifu.loan.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class GuideAdapter extends PagerAdapter {
	private List<View> mLists;
	
	public GuideAdapter(List<View> pLists)
	{
		this.mLists=pLists;
	}	
	@Override
	public int getCount() {
		return mLists.size();
	}
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0==arg1;
	}
	@Override
	public void destroyItem(View container, int position, Object object) {	
		((ViewPager)container).removeView(mLists.get(position));		
	}
	@Override
	public Object instantiateItem(View container, int position) {
		((ViewPager)container).addView(mLists.get(position));
		return mLists.get(position);
	}
}
