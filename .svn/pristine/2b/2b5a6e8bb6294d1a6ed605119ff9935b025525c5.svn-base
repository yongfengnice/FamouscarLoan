package com.famous.zhifu.loan.util;

import java.io.File;

import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.famous.zhifu.loan.intef.GetUpdateDownloadID;


/**
 * App 更新工具类
 * 
 */
public class UpdateUtil {
	private final static String TAG = "shure";
	private final static int INSTALL_ERROR = 3;
	private Context context;
	private DownloadManager downloadManager;
	private String downloadUrl = "";
	private String fileName;
	private GetUpdateDownloadID getUpdateDownloadID;
	private long mDownloadId;

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case INSTALL_ERROR:
				Toast.makeText(context, "安装新版本失败", 2).show();
			}
		}

	};

	public UpdateUtil(Context context, String url) {
		this.context = context;
		this.downloadUrl = url;

		downloadManager = (DownloadManager) context
				.getSystemService(context.DOWNLOAD_SERVICE);
		// http://static.cai188.com/v2/cai188.apk
		fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/") + 1);
	}

	// 开始下载
	public void startDownLoad() {
		Request request = new Request(Uri.parse(downloadUrl));

		request.setAllowedNetworkTypes(
				DownloadManager.Request.NETWORK_MOBILE
						| DownloadManager.Request.NETWORK_WIFI)
				.setAllowedOverRoaming(false) // 缺省是true
				.setTitle("更新") // 用于信息查看
				.setDescription("下载apk") // 用于信息查看
				.setDestinationInExternalPublicDir(
						Environment.DIRECTORY_DOWNLOADS, fileName);
		mDownloadId = downloadManager.enqueue(request); // 加入下载队列
		getUpdateDownloadID.getDownLoadID(mDownloadId);
		startQuery(mDownloadId);
	}

	public void startQuery(long downloadId) {
		if (downloadId != 0) {
			runnable.DownID = downloadId;
			handler.postDelayed(runnable, step);
		}
	};

	public void lookDownload() {
		context.startActivity(new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS));
	}

	public void stopQuery() {
		handler.removeCallbacks(runnable);
	}

	int step = 1000;
	QueryRunnable runnable = new QueryRunnable();

	class QueryRunnable implements Runnable {
		public long DownID;

		@Override
		public void run() {
			queryState(DownID);
			handler.postDelayed(runnable, step);
		}
	};

	private void queryState(long downID) {
		// 关键：通过ID向下载管理查询下载情况，返回一个cursor
		Cursor c = downloadManager.query(new DownloadManager.Query()
				.setFilterById(downID));
		if (c == null) {
			Toast.makeText(context, "没有找到有效下载", Toast.LENGTH_LONG).show();
		} else { // 以下是从游标中进行信息提取
			if (!c.moveToFirst()) {
				c.close();
				return;
			}

			c.close();
		}
	}

	// 获取当前APP 版本号
	public static int getVersionCode(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo packageInfo = manager.getPackageInfo(
					context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void setDownloadId(GetUpdateDownloadID getUpdateDownloadID) {
		this.getUpdateDownloadID = getUpdateDownloadID;
	}

	public void installApk(String fileName) {
		if (TextUtils.isEmpty(fileName)) {
			Message msg = new Message();
			msg.what = INSTALL_ERROR;
			handler.sendMessage(msg);
			return;
		}

		if (fileName.indexOf("?") > -1) {
			fileName = fileName.split("\\?")[0];
		}

		File file = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
				fileName);
		if (file.toString().endsWith(".apk")) {
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.addCategory(Intent.CATEGORY_DEFAULT);
			intent.setDataAndType(Uri.fromFile(file),
					"application/vnd.android.package-archive");
			context.startActivity(intent);
		} else {
			Message msg = new Message();
			msg.what = INSTALL_ERROR;
			handler.sendMessage(msg);
		}
	}
}
