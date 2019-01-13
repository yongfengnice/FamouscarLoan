package com.famous.zhifu.loan.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Process;
import android.text.TextUtils;
import android.widget.Toast;

import com.famous.zhifu.loan.R;

public class DownloadServer extends Service {

	String apkName;
	String apkPath;
	String url;
	ApkDownloadTask downloadTask;
	// 存放下载队列
	ArrayList<String> urlList = new ArrayList<String>();

	public static final int TASK_HAS_ALREADY_STARTED = 2;
	public static final int DOWNLOAD_SUCCESS = 3;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		if (intent != null) {
			apkName = intent.getStringExtra("apkName");
			apkPath = intent.getStringExtra("apkPath");
			url = intent.getStringExtra("url");
			if (urlList.contains(url)) {
				handler.sendEmptyMessage(TASK_HAS_ALREADY_STARTED);
			} else {
				urlList.add(url);
				downloadApk();
			}
		}
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			int what = msg.what;
			if (what == TASK_HAS_ALREADY_STARTED) {
				Toast.makeText(DownloadServer.this, "已经在下载队列中",
						Toast.LENGTH_LONG).show();
			} else if (what == DOWNLOAD_SUCCESS) {
				Bundle args = (Bundle) msg.obj;
				int notifyId = args.getInt("notifyId");
				String fileName = args.getString("fileName");
				Intent intent = new Intent(
						SisterReceiver.ACTION_DOWNLOAD_SUCCESSED);
				intent.putExtra("fileName", fileName);
				intent.putExtra("notifyId", notifyId);
				DownloadServer.this.sendBroadcast(intent);
			}
		}
	};

	public void downloadApk() {
		int notifyId = (int) System.currentTimeMillis();
		new ApkDownloadTask(apkName, apkPath, url, notifyId).start();
	}

	class ApkDownloadTask extends Thread {
		String apkName;
		String apkPath;
		String url;
		int notifyId;
		ElvesNotification notification;

		public ApkDownloadTask(String name, String path, String url,
				int notifyId) {
			if (!name.endsWith(".apk")) {
				this.apkName = System.currentTimeMillis() + name + ".apk";
			} else {
				this.apkName = name;
			}
			this.apkPath = path;
			this.url = url;
			this.notifyId = notifyId;
			notification = new ElvesNotification(DownloadServer.this);
		}

		@Override
		public void run() {
			Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
			boolean downloadSuccess = false;
			if (TextUtils.isEmpty(apkName)) {
				apkName = "temp.apk";
			}
			File fi = new File(apkPath);
			if (!fi.exists()) {
				fi.mkdirs();
			}
			String fileName = apkPath + "/" + apkName;
			URL urls;
			HttpURLConnection conn = null;
			FileOutputStream output = null;
			InputStream istream = null;
			try {
				urls = new URL(url);
				notification.notificationing(notifyId, 100, 1, apkName);
				conn = (HttpURLConnection) urls.openConnection();
				conn.setConnectTimeout(30 * 1000);
				conn.setRequestMethod("GET");
				conn.connect();
				istream = conn.getInputStream();
				int totalSize = conn.getContentLength();
				int currentSize = 0;
				output = new FileOutputStream(new File(apkPath, apkName));
				int M = totalSize / 1024 / 1024;
				int byteBuffer = 1024;
				if (M < 5) {
					byteBuffer = 2 * byteBuffer;
				} else if (M < 15) {
					byteBuffer = 6 * byteBuffer;
				} else if (M < 35) {
					byteBuffer = 14 * byteBuffer;
				} else if (M < 55) {
					byteBuffer = 22 * byteBuffer;
				} else if (M < 75) {
					byteBuffer = 30 * byteBuffer;
				} else {
					byteBuffer = 40 * byteBuffer;
				}
				byte[] bi = new byte[byteBuffer];
				int b = 0;
				int times = 0;
				while ((b = istream.read(bi)) != -1) {
					currentSize += b;
					output.write(bi, 0, b);
					output.flush();
					// 下载进行中
					if (times > 100 || times == 0) {
						notification.notificationing(notifyId, totalSize,
								currentSize, apkName);
						times = 0;
					}
					times++;
				}
				notification.notificationing(notifyId, totalSize, currentSize,
						apkName);
				downloadSuccess = true;
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (!downloadSuccess) {
					// 下载失败
					notification.notifyFailed(notifyId,
							R.drawable.stat_sysl_complete, fileName, url);
					urlList.remove(url);
				} else {
					Bundle args = new Bundle();
					args.putInt("notifyId", notifyId);
					args.putString("fileName", fileName);
					Message msg = handler.obtainMessage(DOWNLOAD_SUCCESS, args);
					handler.sendMessage(msg);
					urlList.remove(url);
				}
				conn.disconnect();
				try {
					if (istream != null) {
						istream.close();
					}
					if (output != null)
						output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
