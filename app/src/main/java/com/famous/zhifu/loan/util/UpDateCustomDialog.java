package com.famous.zhifu.loan.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.intef.IUpdate;
import com.famous.zhifu.loan.service.DownloadServer;

public class UpDateCustomDialog implements OnClickListener {
	private Context context;
	private Dialog dialog = null;
	private RelativeLayout layout;
	private TextView message;
	private TextView dialogTitle;
	private Button btnSure;
	private Button btnCancel;

	private int width = 0;
	private int height = 0;

	private IUpdate iupDate;
	private View v;
	private View verDev;
	private ViewTreeObserver vto;
	LinearLayout.LayoutParams lp;
	private String apkPath;
	private String updateUrl;
	private String apkName;

	public UpDateCustomDialog(Context context, String apkPath,
			String updateUrl, String apkName) {
		this.context = context;
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);

		width = wm.getDefaultDisplay().getWidth();// 屏幕宽度
		height = wm.getDefaultDisplay().getHeight();// 屏幕宽度
		this.apkPath = apkPath;
		this.updateUrl = updateUrl;
		this.apkName = apkName;

	}

	private void setDialogView() {
		v = LayoutInflater.from(context).inflate(R.layout.update_dialog, null);
		// 显示样式
		v.setScrollBarStyle(R.style.CustomExitDialog);
		message = (TextView) v.findViewById(R.id.dialog_message);
		dialogTitle = (TextView) v.findViewById(R.id.exit_dialog);
		layout = (RelativeLayout) v.findViewById(R.id.dialog_layout);
		btnSure = (Button) v.findViewById(R.id.btn_exit_dialog_sure);
		btnSure.setText("马上升级");
		btnCancel = (Button) v.findViewById(R.id.btn_exit_dialog_cancel);
		btnCancel.setText("下次再说");
		verDev = v.findViewById(R.id.verDev);
		vto = (ViewTreeObserver) layout.getViewTreeObserver();
		lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
	}

	public void showDilog(String titleMessage, String message_str) {
		dialog = new Dialog(context);
		dialog = new AlertDialog.Builder(context).create();
		dialog.show();
		setDialogView();
		// 实例化弹出框里面的控件
		dialog.getWindow().setContentView(v);

		dialog.getWindow().setLayout(width / 6 * 5, height / 2 * 1);

		btnSure.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		message.setText(Html.fromHtml(message_str));

		if (titleMessage != null) {
			dialogTitle.setText(titleMessage);
		} else {
			dialogTitle.setText("");
		}

	}

	public void setUpdate(IUpdate iupDate) {
		this.iupDate = iupDate;
	}

	@Override
	public void onClick(View arg0) {
		if (arg0.getId() == R.id.btn_exit_dialog_sure) {
			Intent intent = new Intent();
//			intent.putExtra("apkPath", apkPath);
//			intent.putExtra("url", updateUrl);
//			intent.putExtra("apkName", apkName);
//			intent.setClass(context, DownloadServer.class);
//			context.startService(intent);
//			Intent intent = new Intent();        
		    intent.setAction("android.intent.action.VIEW");    
		    updateUrl = "http://static.zhifu360.com/Apk/Android/FamouscarLoan.apk";
		    Uri content_url = Uri.parse(updateUrl);   
		    intent.setData(content_url);  
		    context.startActivity(intent);

			dialog.cancel();
			layout.setVisibility(View.GONE);
		} else if (arg0.getId() == R.id.btn_exit_dialog_cancel) {
			dialog.cancel();
			layout.setVisibility(View.GONE);
		}
	}

	public void close() {
		dialog.cancel();
	}

}
