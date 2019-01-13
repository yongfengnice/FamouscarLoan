package com.famous.zhifu.loan.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.Process;

import com.famous.zhifu.loan.R;

public class UpdateUtils {
	ElvesNotification notification;
	Context context;

	public UpdateUtils(Context context) {
		this.context = context;
		notification = new ElvesNotification(context);
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			int what = msg.what;
			if (what == 999999) {
				String result = (String) msg.obj;
				String fileName = result.split("&")[0];
				int notifyId = Integer.parseInt(result.split("&")[1]);
				notification.notifySuccessed(notifyId,
						R.drawable.stat_sysl_complete, fileName);
			} else if (what == 888888) {
				String result = (String) msg.obj;
				String fileName = result.split("&")[0];
				int notifyId = Integer.parseInt(result.split("&")[1]);
				String url=result.split("&")[2];
				notification.notifyFailed(notifyId,
						R.drawable.stat_sysl_complete, fileName,url);
			} else if (what == 777777) {
				String result = (String) msg.obj;
				int total = Integer.parseInt(result.split("&")[1]);
				int progress = Integer.parseInt(result.split("&")[0]);
				String filename = result.split("&")[2];
				int notifyId = Integer.parseInt(result.split("&")[3]);
				notification.notificationing(notifyId, total, progress,
						filename);
			}
		}
	};



	/**
	 * 
	 * @param apkPath
	 *            apk安装路径
	 * @param url
	 *            apk下载地址
	 * @param handler
	 *            下载成功后会send 一个带有路径的apk名称
	 */
	public void downloadApk(final String apkPath, final String url) {
		new Thread() {
			@Override
			public void run() {
				int notifyId = Math.abs((int) System.currentTimeMillis());
				android.os.Process
						.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
				boolean downloadSuccess = false;
				String fileName = apkPath + "/";
				String apkName = "";
				File file = new File(apkPath);
				if (!file.exists()) {
					file.mkdirs();
				}
				URL urls;
				HttpURLConnection conn = null;
				FileOutputStream output = null;
				InputStream istream = null;
				try {
					urls = new URL(url);
					conn = (HttpURLConnection) urls.openConnection();
					conn.setConnectTimeout(30 * 1000);
					conn.connect();
					istream = conn.getInputStream();
					int totalSize = conn.getContentLength();
					int currentSize = 0;
					apkName = url.substring(url.lastIndexOf("/") + 1);
					fileName += apkName;
					output = new FileOutputStream(new File(fileName));

					byte[] bi = new byte[1024];
					int b = 0;
					int times = 0;
					while ((b = istream.read(bi)) != -1) {
						currentSize += b;
						output.write(bi, 0, b);
						output.flush();
						// 下载进行中
						times++;
						if (times > 50) {
							Message msg = handler.obtainMessage(777777,
									currentSize + "&" + totalSize + "&"
											+ apkName + "&" + notifyId);
							handler.sendMessage(msg);
							times = 0;
						}
//						// Log.i("--------", "当前："+currentSize+"|"+totalSize);
					}
					downloadSuccess = true;
				} catch (MalformedURLException e) {
				} catch (IOException e) {
				} catch (Exception e) {
							
				} finally {
					if (!downloadSuccess) {
						// 下载失败
						Message msg = handler.obtainMessage(888888, fileName
								+ "&" + notifyId+"&"+url);
						handler.sendMessage(msg);
					} else {
						// 下载成功
						Message msg = handler.obtainMessage(999999, fileName
								+ "&" + notifyId);
						handler.sendMessage(msg);

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
					handler.sendEmptyMessage(0);
				}
			}

		}.start();
	}

	/**
	 * 
	 * @param context
	 *            安装apk
	 * @param fileName
	 *            带有路径的apk名称
	 */
	public static void installApk(Context context, String fileName) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(Uri.parse("file://" + fileName),
				"application/vnd.android.package-archive");
		context.startActivity(intent);
	}






}
