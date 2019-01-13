package com.famous.zhifu.loan.internet;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.text.TextUtils;

/**
 * MD5 加密
 */
public class Md5Util {

	/**
	 * 对字符串加密
	 * 
	 * @param str
	 *            String
	 * @return 加密后的字符串
	 */
	public static String getMD5Str(String str) {
		MessageDigest messageDigest = null;
		try {
			if (!TextUtils.isEmpty(str)) {
				messageDigest = MessageDigest.getInstance("MD5");
				messageDigest.reset();
				messageDigest.update(str.getBytes("UTF-8"));
			}
		} catch (NoSuchAlgorithmException e) {
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			} else {
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
			}
		}
		return md5StrBuff.toString();
	}

	/**
	 * 加密对象
	 * 
	 * @param objects
	 *            对象数组
	 * @return 加密后的串
	 */
	public static String getMD5String(Object... objects) {
		StringBuilder sb = new StringBuilder();
		for (Object obj : objects) {
			if (obj != null) {
				sb.append(obj.toString());
			}
		}
		return getMD5Str(sb.toString());
	}

}
