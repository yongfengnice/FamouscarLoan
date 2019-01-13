package com.famous.zhifu.loan.view.dialog;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.entity.BdzlItem;

/**
 * 自下而上弹出选择框
 * @author LYM
 *
 */
public class OperateTipPopup extends PopupWindow {
	private Activity activity = null;
	
	private View cacheView = null;
	
	/**
	 * 
	 * @param context	上下文对象
	 * @param arr		数据源
	 * @param view		触发弹出的父对象
	 * @param itemClickListener		ListView的监听事件
	 */
	public OperateTipPopup(Activity context){
		super(context);
		
		this.activity = context;
		
		cusInit();
	}
	
	private void cusInit(){
		LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		cacheView = inflater.inflate(R.layout.item_dialog_tip, null);
		
		RelativeLayout okLayout = (RelativeLayout) cacheView.findViewById(R.id.item_dialog_tip_confirm);
		
		okLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dismiss();
			}
		});
		
		setOnListener();
	}
	
	/** 设置监听事件，使得点击后消失 */
	private void setOnListener(){
		//设置SelectPicPopupWindow的View
		this.setContentView(cacheView);
		//设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.MATCH_PARENT);
		//设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.MATCH_PARENT);
		//设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		//设置SelectPicPopupWindow弹出窗体动画效果
		//this.setAnimationStyle(R.style.profile_bottom_anim);
		//实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		//设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
		//mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
		
		
		/*cacheView.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				
				int height = cacheView.findViewById(R.id.pop_alert_profile_lv).getTop();
				int y=(int) event.getY();
				
				if(event.getAction()==MotionEvent.ACTION_UP){
					dismiss();
				}	
				
				return true;
			}
		});*/
	}
	
}
