package com.famous.zhifu.loan.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

public class CustomedViewPager extends ViewPager {
	private ListView listView =null;

	public void setListView(ListView listView) {
		this.listView=null;
		this.listView = listView;
	}

	public CustomedViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomedViewPager(Context context) {
		super(context);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
//		listView.requestDisallowInterceptTouchEvent(true);

		return super.onInterceptTouchEvent(arg0);
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		 super.onTouchEvent(arg0);
		 return true;
	}
    /****/
    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if(v instanceof ViewPager) {
            int currentItem = ((ViewPager) v).getCurrentItem();
            if((currentItem==0)){//禁止里面的控件滑动
                return true;
            }
            return false;//允许这个界面可以滑动
        }
        return super.canScroll(v, checkV, dx, x, y);
    }
}
