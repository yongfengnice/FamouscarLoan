package com.famous.zhifu.loan.view.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.activity.MyApp;

public class ExitDialog {
	private Context context;
	private Dialog dialog = null;
	private WindowManager wm;
	private int width, height;
	private Button btnSure, btnCancel;
	private TextView messageTextView;
	private TextView title;
	private String type = "exit";

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ExitDialog(Context context) {
		this.context = context;
		this.wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		;
		width = wm.getDefaultDisplay().getWidth();// 屏幕宽度
		height = wm.getDefaultDisplay().getHeight();// 屏幕宽度
	}

	public void disDialog() {
		if (dialog != null) {
			dialog.cancel();
		}
	}

	public void showDilog() {
		dialog = new Dialog(context);
		dialog = new AlertDialog.Builder(context).create();
		dialog.show();
		View v = LayoutInflater.from(context).inflate(R.layout.exit_dialog,
				null);
		// 显示样式
		v.setScrollBarStyle(R.style.CustomExitDialog);
		// 实例化弹出框里面的控件

		final RelativeLayout layout = (RelativeLayout) v
				.findViewById(R.id.dialog_layout);
		btnSure = (Button) v.findViewById(R.id.btn_exit_dialog_sure);
		btnCancel = (Button) v.findViewById(R.id.btn_exit_dialog_cancel);
		btnSure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MyApp.getInstance().removeAll();
			}
		});
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.cancel();
				layout.setVisibility(View.GONE);
			}
		});

		// 显示弹出框

		dialog.getWindow().setContentView(v);
		dialog.getWindow().setLayout(width * 9 / 10, height * 2 / 7);
	}

	public void showDilog(String tile_str, String message) {
		dialog = new Dialog(context);
		dialog = new AlertDialog.Builder(context).create();
		dialog.show();
		View v = LayoutInflater.from(context).inflate(R.layout.update_dialog,
				null);
		messageTextView = (TextView) v.findViewById(R.id.dialog_message);
		title = (TextView) v.findViewById(R.id.exit_dialog);
		messageTextView.setText(message);
		title.setText(tile_str);
		// 显示样式
		v.setScrollBarStyle(R.style.CustomExitDialog);
		// 实例化弹出框里面的控件

		final RelativeLayout layout = (RelativeLayout) v
				.findViewById(R.id.dialog_layout);
		btnSure = (Button) v.findViewById(R.id.btn_exit_dialog_sure);
		btnCancel = (Button) v.findViewById(R.id.btn_exit_dialog_cancel);
		btnSure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((Activity) context).finish();
			}
		});
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.cancel();
				layout.setVisibility(View.GONE);
			}
		});

		// 显示弹出框

		dialog.getWindow().setContentView(v);
		dialog.getWindow().setLayout(width * 9 / 10, height * 2 / 7);
	}

	public void showDilog(String tile_str, String message, final Handler handler) {
		dialog = new Dialog(context);
		dialog = new AlertDialog.Builder(context).create();
		dialog.show();
		View v = LayoutInflater.from(context).inflate(R.layout.update_dialog,
				null);
		messageTextView = (TextView) v.findViewById(R.id.dialog_message);
		title = (TextView) v.findViewById(R.id.exit_dialog);
		messageTextView.setText(message);
		title.setText(tile_str);
		// 显示样式
		v.setScrollBarStyle(R.style.CustomExitDialog);
		// 实例化弹出框里面的控件

		final RelativeLayout layout = (RelativeLayout) v
				.findViewById(R.id.dialog_layout);
		btnSure = (Button) v.findViewById(R.id.btn_exit_dialog_sure);
		btnCancel = (Button) v.findViewById(R.id.btn_exit_dialog_cancel);
		btnSure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (type.equals("exit")) {
					handler.sendEmptyMessage(5001);
				} 
			}
		});
		
		btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.cancel();
				layout.setVisibility(View.GONE);
			}
		});
		
		// 显示弹出框
		dialog.getWindow().setContentView(v);
		dialog.getWindow().setLayout(width * 9 / 10, height * 2 / 7);
	}
}
