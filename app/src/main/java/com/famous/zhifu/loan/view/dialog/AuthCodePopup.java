package com.famous.zhifu.loan.view.dialog;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.activity.Consts;
import com.famous.zhifu.loan.activity.MyApp;
import com.famous.zhifu.loan.internet.ServiceClient;
import com.famous.zhifu.loan.util.Tools;

/**
 * 短信图片验证码
 * @author LYM
 *
 */
public class AuthCodePopup extends PopupWindow {
	private static final int MSG_GETCODE = 1001;
	
	private Activity activity = null;
	
	private View cacheView = null;
	
	private TextView authTxtView = null;
	private TextView changeView = null;		//换一张
	
	private WebView webView = null;
	
	//手机号
	private String phoneStr = "";
	//请求验证码的接口方法
	private String method = "";
	//验证码发送类型
	private String type = "";
	//请求验证码成功后使dialog消失并发送消息告诉主程序
	private Handler handler = null;
	
	
	/**
	 * 
	 * @param context
	 * @param handler
	 * @param phone
	 * @param method
	 * @param type
	 */
	public AuthCodePopup(Activity context,Handler handler,String phone,String method,String type){
		super(context);
		
		this.activity = context;
		this.handler = handler;
		this.phoneStr = phone;
		this.method = method;
		this.type = type;
		
		cusInit();
	}
	
	/** 程序派发重新获取的点击事件 */
	public void triggerClick(){
		authTxtView.setText("");
		changeView.performClick();
	}
	
	private void cusInit(){
		LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		cacheView = inflater.inflate(R.layout.authcode_popup, null);
		
		authTxtView = (TextView) cacheView.findViewById(R.id.authcode_popup_edt_auth);
		webView = (WebView) cacheView.findViewById(R.id.authcode_popup_img_webview);
		
		changeView = (TextView) cacheView.findViewById(R.id.authcode_popup_txt_change);
		changeView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (Tools.isEmpty(MyApp.token)) {	//未登录
					webView.loadUrl(Consts.SERVICE_URL+"/verifycode.html?d="+MyApp._deviceId);
				}else {	//已登录
					webView.loadUrl(Consts.SERVICE_URL+"/verifycode.html?d="+MyApp._deviceId+"&t="+MyApp.token);
				}
			}
		});
		
		ImageView closeView = (ImageView) cacheView.findViewById(R.id.auth_popup_close);
		closeView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dismiss();
			}
		});
		
		Button submitBtn = (Button) cacheView.findViewById(R.id.authcode_popup_btn);
		submitBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				toGetCode();	
			}
		});
		
		setOnListener();
	}
	
	// 获取验证码
	public void toGetCode() {
		String auth = authTxtView.getText().toString().trim();
		
		if (auth.length() == 0) {
			Toast.makeText(activity, "请输入图像验证码!",1).show();
			
			return;
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		params.put("vcode", authTxtView.getText().toString());
		
		if (!Tools.isEmpty(phoneStr)) {
			params.put("mobile", phoneStr);
		}
		
		boolean isToken = Tools.isEmpty(MyApp.token)? false:true;
		
		ServiceClient.call(method, params, verifyCallback,isToken);
	}

	ServiceClient.Callback verifyCallback = new ServiceClient.Callback() {
		@Override
		public void callback(JSONObject data) {
			// System.out.println(data);
			
			try {
				if (data.getInt("errorcode") == 0) {
					String result = data.getString("dataresult");
					Toast.makeText(activity, result, 1).show();

					handler.sendEmptyMessage(MSG_GETCODE);
					
					dismiss();
				}
			} catch (JSONException e) {
				try {
					String str = data.getString("errormsg");
					Toast.makeText(activity, str, 1).show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					
					Toast.makeText(activity, Consts.UNKNOWN_FAULT,1).show();
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
	}
	
}
