package com.famous.zhifu.loan.service;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.widget.Toast;

import com.famous.zhifu.loan.activity.Consts;

public class SisterReceiver extends BroadcastReceiver {
	public static final String ACTION_DOWNLOAD_FAILED = "com.xvsheng.qdd.download.failed";
	public static final String ACTION_DOWNLOAD_SUCCESSED = "com.xvsheng.qdd.download.successed";
	public Context context;

	@Override
	public void onReceive(Context context, Intent intent) {
		this.context = context;
		String action = intent.getAction();
		if (ACTION_DOWNLOAD_SUCCESSED.equals(action)) {// 下载成功时安装apk
			int notifyId = intent.getIntExtra("notifyId", 0);
			String fileName = intent.getStringExtra("fileName");
			UpdateUtils.installApk(context, fileName);
			NotificationManager notifyM = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);
			notifyM.cancel(notifyId);
		} else if (ACTION_DOWNLOAD_FAILED.equals(action)) {
			if (isNetworkAvailable(context)) {
				String url = intent.getStringExtra("url");
				String apkPath = Environment.getExternalStorageDirectory()
						.getPath() + "/elves";
				new UpdateUtils(context).downloadApk(apkPath, url);
			} else {
				Toast.makeText(context, Consts.NONETWORK, Toast.LENGTH_SHORT)
						.show();
			}

		}
	}

	/**
	 * 
	 * @param context
	 * @return 返回网络是否可用
	 */
	public static boolean isNetworkAvailable(Context context) {
		if (context != null) {

			try {
				ConnectivityManager mConnectivityManager = (ConnectivityManager) context
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo mNetworkInfo = mConnectivityManager
						.getActiveNetworkInfo();
				if (mNetworkInfo != null) {
					return mNetworkInfo.isAvailable();
				}
			} catch (Exception e) {
				return false;
			}

		}
		return false;
	}

}
