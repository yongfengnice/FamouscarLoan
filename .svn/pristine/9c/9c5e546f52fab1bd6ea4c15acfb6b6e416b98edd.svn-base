package com.famous.zhifu.loan.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class AbFileUtil {
	/**
	 * 描述：获取src中的图片资源.
	 *
	 * @param src 图片的src路径，如（“image/arrow.png”）
	 * @return Bitmap 图片
	 */
	public static Bitmap getBitmapFormSrc(String src){
		Bitmap bit = null;
		try {
			bit = BitmapFactory.decodeStream(AbFileUtil.class.getResourceAsStream(src));
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	//AbLogUtil.d(AbFileUtil.class, "获取图片异常："+e.getMessage());
		}
		return bit;
	}
}
