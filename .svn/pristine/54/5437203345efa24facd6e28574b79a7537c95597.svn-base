package com.famous.zhifu.loan.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.RemoteViews;

import com.famous.zhifu.loan.R;

public class ElvesNotification {
	Context mContext;
	Notification notification;
	Notification notify;
	NotificationManager notifyM;

	public ElvesNotification(Context context) {
		mContext = context;
		notifyM = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
	}

	public void notificationing(int notifyId, int totalSize, int progress,
			String apkName) {
		if (notification == null) {
			notification = new Notification(R.drawable.stat_sys_download,
					"正在下载" + apkName, System.currentTimeMillis());

			notification.icon = R.drawable.stat_sys_download;
			notification.flags = Notification.FLAG_AUTO_CANCEL;
			notification.when = System.currentTimeMillis();
			notification.contentView = new RemoteViews(
					mContext.getPackageName(), R.layout.notifying);
			if (TextUtils.isEmpty(apkName)) {
				notification.contentView.setTextViewText(R.id.notifyId, "正在下载"
						+ mContext.getText(R.string.app_name));
			} else {
				notification.contentView.setTextViewText(R.id.notifyId, "正在下载"
						+ apkName);
			}
			notification.contentIntent = PendingIntent.getActivity(mContext,
					notifyId, new Intent(), 0);
		}
		notification.contentView.setProgressBar(R.id.notifyBar, totalSize,
				progress, totalSize <= 0);
		notifyM.notify(notifyId, notification);
	}

	public void notifyFailed(int notifyId, int icon, String fileName, String url) {
		String apkName = fileName.substring(fileName.lastIndexOf("/") + 1);
		String time = new SimpleDateFormat("hh:MM:ss").format(new Date());
		Notification notification = new Notification(
				R.drawable.stat_sysl_complete, "下载失败",
				System.currentTimeMillis());
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		notification.contentView = new RemoteViews(mContext.getPackageName(),
				R.layout.notifyed);
		notification.contentView.setImageViewResource(R.id.notifyLog, icon);
		notification.contentView.setTextViewText(R.id.notifyMessage,
				"下载失败，点击重新下载");
		notification.contentView.setTextViewText(R.id.notifyTitle, apkName);
		notification.contentView.setTextViewText(R.id.notifyTime, time);
		// notification.contentIntent = PendingIntent.getActivity(mContext,
		// notifyId, new Intent(), 0);
		Intent intent = new Intent(SisterReceiver.ACTION_DOWNLOAD_FAILED);
		intent.putExtra("url", url);
		intent.putExtra("notifyId", notifyId);
		notification.contentIntent = PendingIntent.getBroadcast(mContext,
				notifyId, intent, 0);
		notifyM.notify(notifyId, notification);
	}

	public void cancelNotify(int notifyId) {
		notifyM.cancel(notifyId);
	}

	public void notifySuccessed(int notifyId, int icon, String fileName) {
		String apkName = fileName.substring(fileName.lastIndexOf("/") + 1);
		String time = new SimpleDateFormat("hh:MM:ss").format(new Date());
		Notification notification = new Notification(
				R.drawable.stat_sysl_complete, "下载成功",
				System.currentTimeMillis());
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		notification.contentView = new RemoteViews(mContext.getPackageName(),
				R.layout.notifyed);
		notification.contentView.setImageViewResource(R.id.notifyLog, icon);
		notification.contentView.setTextViewText(R.id.notifyMessage,
				"下载成功，点击安装");
		notification.contentView.setTextViewText(R.id.notifyTitle, apkName);
		notification.contentView.setTextViewText(R.id.notifyTime, time);
		Intent intent = new Intent(SisterReceiver.ACTION_DOWNLOAD_SUCCESSED);
		intent.putExtra("fileName", fileName);
		intent.putExtra("notifyId", notifyId);
		notification.contentIntent = PendingIntent.getBroadcast(mContext,
				notifyId, intent, 0);
		notifyM.notify(notifyId, notification);
	}

}
