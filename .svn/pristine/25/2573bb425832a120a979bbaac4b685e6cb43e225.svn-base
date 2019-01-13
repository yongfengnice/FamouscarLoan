package com.famous.zhifu.loan.pay;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.baofoo.sdk.vip.BaofooPayActivity;
import com.famous.zhifu.loan.famouscarloan.account.PayActivity;

@SuppressLint("SimpleDateFormat")
public class OrderService extends AsyncTask<Integer, Void, Boolean> {

	private PayActivity payActivity; // 附属界面
	public final static int REQUEST_CODE_BAOFOO_SDK = 100;
	private AlertDialog dialog; // 全局对话框
	private String orderNo = null; // 保留请求序列号，此序列号与商户自身订单号无关，请注意
	private String msg = "";
	private final static String URL_BAOFOO_GATEWAY_TEST = "http://tgw.baofoo.com/paysdk/index";
	private final static String URL_OK = "0000";
	
	private boolean testMode = false;

	public OrderService(PayActivity payActivity,String orderNo,boolean testMode) {
		this.payActivity = payActivity;
		this.orderNo = orderNo;
		this.testMode = testMode;
		
		Log.i("orderNo", orderNo);
	}

	@Override
	protected void onPreExecute() {
		Log.i("orderService", "onPreExecute");
		// 处理前先弹出一个处理等待对话框
		dialog = new AlertDialog(payActivity) {
		};
		dialog.setMessage("正在创建宝付支付订单");
		dialog.setCancelable(false);
		dialog.show();
	}

	@Override
	protected Boolean doInBackground(Integer... params) {
		Log.i("orderService", "doInBackground");
		try {
			if (orderNo == null || orderNo.equals("")) {
				return false;
			}
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
		return true;
	}

	@Override
	protected void onPostExecute(Boolean isOk) {
		Log.i("orderService", "onPostExecute");
		// 处理结束后处理
		// 关闭全局对话框
		dialog.dismiss();
		// 判断处理是否出错
		if (isOk) {
			Intent payintent = new Intent(payActivity, BaofooPayActivity.class);
			payintent.putExtra(BaofooPayActivity.PAY_TOKEN, orderNo);
			payintent.putExtra(BaofooPayActivity.PAY_BUSINESS, !testMode);
			payActivity.startActivityForResult(payintent, REQUEST_CODE_BAOFOO_SDK);
		} else {
			AlertDialog msgdialog = new AlertDialog(payActivity) {};
			msgdialog.setMessage("创建订单失败 "+msg);
			msgdialog.show();
		}
	}
}
