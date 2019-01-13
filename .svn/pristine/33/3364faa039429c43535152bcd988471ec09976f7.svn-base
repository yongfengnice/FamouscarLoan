package com.famous.zhifu.loan.view.dialog;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.activity.Consts;
import com.famous.zhifu.loan.internet.ServiceClient;
import com.famous.zhifu.loan.util.NetUtil;
import com.famous.zhifu.loan.util.Tools;

/**
 * 自下而上弹出选择框
 * @author LYM
 *
 */
public class DetailJisuanPopup extends PopupWindow {
	private Activity activity = null;
	
	private View cacheView = null;
	
	private TextView jineView = null;
	private TextView timeView = null;
	
	private TextView shouyiView = null;
	
	private String type = "";
	private String rate = "";
	
	/**
	 * 
	 * @param context	上下文对象
	 * @param arr		数据源
	 * @param view		触发弹出的父对象
	 * @param itemClickListener		ListView的监听事件
	 */
	public DetailJisuanPopup(Activity context,String type,String rate){
		super(context);
		
		this.activity = context;
		this.type = type;
		this.rate = rate;
		
		cusInit();
	}
	
	private void cusInit(){
		LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		cacheView = inflater.inflate(R.layout.detail_popup_jisuan, null);
		
		jineView = (TextView) cacheView.findViewById(R.id.detail_popup_jiusuan_jines);
		timeView = (TextView) cacheView.findViewById(R.id.detail_popup_jiusuan_time);
		shouyiView = (TextView) cacheView.findViewById(R.id.detail_popup_jiusuan_shouyi);
		
		ImageView closeView = (ImageView) cacheView.findViewById(R.id.detail_popup_jiusuan_close);
		closeView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dismiss();
			}
		});
		
		Button jisuanBtn = (Button) cacheView.findViewById(R.id.detail_popup_jiusuan_btn);
		jisuanBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				calculator();
			}
		});
		
		setOnListener();
	}
	
	private void calculator() {
		String jine = jineView.getText().toString().trim();
		String time = timeView.getText().toString().trim();

		if (Tools.isEmpty(jine)) {
			Toast.makeText(activity, "金额不能空", 1).show();
		} else if (Tools.isEmpty(time)) {
			Toast.makeText(activity, "期限不能空", 1).show();
		} else {
			if (NetUtil.isNetworkConnected(activity)) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("payment", type);
				params.put("money", jine);
				params.put("month", time);
				params.put("yearrate", rate);
				ServiceClient.call(Consts.NetWork.TYDK, params, verifyback);
			} else {
				Toast.makeText(activity, Consts.NONETWORK, 1).show();
			}
		}
	}

	ServiceClient.Callback verifyback = new ServiceClient.Callback() {
		@Override
		public void callback(JSONObject ret) {
			// System.out.println("计算器  " + ret);
			
			try {
				if (ret.getInt("errorcode") == 0) {
					JSONObject obj = (JSONObject) ret.get("dataresult");
					double ratemoney = obj.getDouble("ratemoney");

					shouyiView.setText(Tools.sishewurubaoliuliangwei(ratemoney) + "");
				}
			} catch (JSONException e) {
				try {
					String str = ret.getString("errormsg");
					Toast.makeText(activity, str, 1).show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					Toast.makeText(activity, Consts.UNKNOWN_FAULT, 1).show();
				}
			}
		}
	};
	
	
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
				
//				int height = cacheView.findViewById(R.id.pop_alert_profile_lv).getTop();
				int y=(int) event.getY();
				
				if(event.getAction()==MotionEvent.ACTION_UP){
					dismiss();
				}	
				
				return true;
			}
		});*/
	}
	
}
