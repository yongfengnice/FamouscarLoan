package com.famous.zhifu.loan.util;


import android.content.Context;
import android.content.SharedPreferences;

import com.famous.zhifu.loan.activity.Consts;

/**
 * SharePreferences操作工具类
 */
public class SharePrefUtil {
	private static SharedPreferences sp;
	private static SharedPreferences sp_userInfo;

	/**
	 * 保存布尔值
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void saveBoolean(Context context, String key, boolean value) {
		if (sp == null)
			sp = context.getSharedPreferences(Consts.SP_NAME, 0);
		sp.edit().putBoolean(key, value).commit();
	}

	public static void saveUserInfoBoolean(Context context, String key,
			boolean value) {
		if (sp_userInfo == null)
			sp_userInfo = context.getSharedPreferences(Consts.USERINFO, 0);
		sp.edit().putBoolean(key, value).commit();
	}

	/**
	 * 保存字符串
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void saveString(Context context, String key, String value) {
		if (sp == null)
			sp = context.getSharedPreferences(Consts.SP_NAME, 0);
		sp.edit().putString(key, value).commit();

	}

	public static void saveUserInfoString(Context context, String key,
			String value) {
		if (sp_userInfo == null)
			sp_userInfo = context.getSharedPreferences(Consts.USERINFO, 0);
		sp_userInfo.edit().putString(key, value).commit();

	}

	/**
	 * 清除SP
	 * 
	 * @param context
	 */
	public static void clear(Context context) {
		if (sp == null)
			sp = context.getSharedPreferences(Consts.SP_NAME, 0);
		sp.edit().clear().commit();
	}

	public static void clearUserInfo(Context context) {
		if (sp_userInfo == null)
			sp_userInfo = context.getSharedPreferences(Consts.USERINFO, 0);
		sp_userInfo.edit().clear().commit();
	}

	/**
	 * 保存long型
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void saveLong(Context context, String key, long value) {
		if (sp == null)
			sp = context.getSharedPreferences(Consts.SP_NAME, 0);
		sp.edit().putLong(key, value).commit();
	}

	public static void saveUserInfoLong(Context context, String key, long value) {
		if (sp_userInfo == null)
			sp_userInfo = context.getSharedPreferences(Consts.USERINFO, 0);
		sp_userInfo.edit().putLong(key, value).commit();
	}

	/**
	 * 保存int型
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void saveInt(Context context, String key, int value) {
		if (sp == null)
			sp = context.getSharedPreferences(Consts.SP_NAME, 0);
		sp.edit().putInt(key, value).commit();
	}

	public static void saveUserInfoInt(Context context, String key, int value) {
		if (sp_userInfo == null)
			sp_userInfo = context.getSharedPreferences(Consts.USERINFO, 0);
		sp_userInfo.edit().putInt(key, value).commit();
	}

	/**
	 * 保存float型
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void saveFloat(Context context, String key, float value) {
		if (sp == null)
			sp = context.getSharedPreferences(Consts.SP_NAME, 0);
		sp.edit().putFloat(key, value).commit();
	}

	public static void saveUserInfoFloat(Context context, String key,
			float value) {
		if (sp_userInfo == null)
			sp_userInfo = context.getSharedPreferences(Consts.USERINFO, 0);
		sp_userInfo.edit().putFloat(key, value).commit();
	}

	/**
	 * 获取字符值
	 * 
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static String getString(Context context, String key, String defValue) {
		if (sp == null)
			sp = context.getSharedPreferences(Consts.SP_NAME, 0);
		return sp.getString(key, defValue);
	}

	public static String getUserInfoString(Context context, String key,
			String defValue) {
		if (sp_userInfo == null)
			sp_userInfo = context.getSharedPreferences(Consts.USERINFO, 0);
		return sp_userInfo.getString(key, defValue);
	}

	/**
	 * 获取int值
	 * 
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static int getInt(Context context, String key, int defValue) {
		if (sp == null)
			sp = context.getSharedPreferences(Consts.SP_NAME, 0);
		return sp.getInt(key, defValue);
	}

	public static int getUserInfoInt(Context context, String key, int defValue) {
		if (sp_userInfo == null)
			sp_userInfo = context.getSharedPreferences(Consts.USERINFO, 0);
		return sp_userInfo.getInt(key, defValue);
	}

	/**
	 * 获取long值
	 * 
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static long getLong(Context context, String key, long defValue) {
		if (sp == null)
			sp = context.getSharedPreferences(Consts.SP_NAME, 0);
		return sp.getLong(key, defValue);
	}

	public static long getUserInfoLong(Context context, String key,
			long defValue) {
		if (sp_userInfo == null)
			sp_userInfo = context.getSharedPreferences(Consts.USERINFO, 0);
		return sp_userInfo.getLong(key, defValue);
	}

	/**
	 * 获取float值
	 * 
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static float getFloat(Context context, String key, float defValue) {
		if (sp == null)
			sp = context.getSharedPreferences(Consts.SP_NAME, 0);
		return sp.getFloat(key, defValue);
	}

	public static float getUserInfoFloat(Context context, String key,
			float defValue) {
		if (sp_userInfo == null)
			sp_userInfo = context.getSharedPreferences(Consts.USERINFO, 0);
		return sp_userInfo.getFloat(key, defValue);
	}

	/**
	 * 获取布尔值
	 * 
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static boolean getBoolean(Context context, String key,
			boolean defValue) {
		if (sp == null)
			sp = context.getSharedPreferences(Consts.SP_NAME, 0);
		return sp.getBoolean(key, defValue);
	}

	public static boolean getUserInfoBoolean(Context context, String key,
			boolean defValue) {
		if (sp_userInfo == null)
			sp_userInfo = context.getSharedPreferences(Consts.USERINFO, 0);
		return sp_userInfo.getBoolean(key, defValue);
	}

	/**
	 * 设置XlistView 的最后刷新时间
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static Long getLastUpdateTime(Context context, String key) {
		SharedPreferences sf = context.getSharedPreferences(Consts.SP_NAME,
				Context.MODE_PRIVATE);
		long timeString = sf.getLong(key, System.currentTimeMillis());
		return timeString;
	}

	public static void setLastUpdateTime(Context context, String key) {
		SharedPreferences sf = context.getSharedPreferences(Consts.SP_NAME,
				Context.MODE_PRIVATE);
		sf.edit().putLong(key, System.currentTimeMillis()).commit();
	}

	public static void setUser(Context context, String token, String username,
			String password) {
		SharedPreferences sf = context.getSharedPreferences(Consts.SP_NAME,
				Context.MODE_PRIVATE);

		SharedPreferences.Editor speditor = sf.edit();
		speditor.putString("token", token);
		speditor.putString("username", username);
		speditor.putString("password", password);
		speditor.commit();

	}

}
