/**
 * 
 */
package com.famous.zhifu.loan.util;

import java.util.List;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;
import android.view.WindowManager;

public class AppUtil {
    
	/**
     * ��ȡ��Ļ�ֱ���
     * @param context
     * @return
     */
    public static int[] getScreenDispaly(Context context) {
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		int width = windowManager.getDefaultDisplay().getWidth();// �ֻ���Ļ�Ŀ��
		int height = windowManager.getDefaultDisplay().getHeight();// �ֻ���Ļ�ĸ߶�
		int result[] = { width, height };
		return result;
	}
    
}
