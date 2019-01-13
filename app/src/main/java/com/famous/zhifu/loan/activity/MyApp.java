package com.famous.zhifu.loan.activity;

import java.util.Stack;
import java.util.UUID;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.telephony.TelephonyManager;

public class MyApp extends Application {

	private SharedPreferences sharedPref;
	public static Context context;
	public static String _deviceId;
	public static int userId = 0;
	public static String token = "";
	public static String username = "";
	public static boolean isLogin = false;
	public static int screenWidth,screenHeight;
	
	/** 保存所有已创建的Activity */
	private static Stack<Activity> activityStack;
	private static MyApp instance;
	
	@Override
	public void onCreate() {
		super.onCreate();
		context = this.getApplicationContext();

		sharedPref = getSharedPreferences(Consts.MCD_FLAG, Context.MODE_PRIVATE);

		String deviceId = sharedPref.getString("deviceid", null);
		if (deviceId == null) {
			TelephonyManager telephonyManager = (TelephonyManager) this
					.getSystemService(Context.TELEPHONY_SERVICE);
			deviceId = telephonyManager.getDeviceId();
			if (deviceId == null) {
				deviceId = Settings.Secure.ANDROID_ID;
				if (deviceId == null || deviceId.equals("9774d56d682e549c")) {
					deviceId = UUID.randomUUID().toString();
				}
			}
			SharedPreferences.Editor speditor = sharedPref.edit();
			speditor.putString("deviceid", deviceId);
			speditor.commit();
		}
		_deviceId = deviceId;
		
	}
	
	/**
	 * 单一实例
	 */
	public static MyApp getInstance() {
		if (instance == null) {
			instance = new MyApp();
		}
		return instance;
	}
	
	/** 将Activity加入 */
	public void addActivity(Activity activity) {

		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}

	/** Activity被销毁时，从Activities中移除 */
	public void remove(Activity activity) {
		if (activity != null) {
			activityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	/**
	 * 移除当前所有已创建的Activiy， 并逐个销毁
	 */
	public void removeAll() {
		for (int i = 0, size = activityStack.size(); i < size; i++) {
			if (null != activityStack.get(i)) {
				activityStack.get(i).finish();
			}
		}
		activityStack.clear();
	}
	
}
