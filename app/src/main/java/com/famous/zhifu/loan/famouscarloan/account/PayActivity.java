package com.famous.zhifu.loan.famouscarloan.account;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baofoo.sdk.vip.BaofooPayActivity;
import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.activity.ActivityInterface;
import com.famous.zhifu.loan.activity.Consts;
import com.famous.zhifu.loan.activity.LoginActivity;
import com.famous.zhifu.loan.internet.ServiceClient;
import com.famous.zhifu.loan.pay.OrderService;
import com.famous.zhifu.loan.util.NetUtil;

public class PayActivity extends Activity implements ActivityInterface {
	
	private EditText accountText = null;
	private Button submitBtn = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_pay);
		
		findView();
		addAction();
		initData();
	}

	@Override
	public void findView() {
		TextView titleView = (TextView) findViewById(R.id.main_header_tv_text);
		titleView.setText("确认投资");
		
		accountText = (EditText) findViewById(R.id.pay_account);
		submitBtn = (Button) findViewById(R.id.pay_submit);
	}


	@Override
	public void initData() {
		
	}


	@Override
	public void addAction() {
		submitBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getOrderNo();
			}
		});
	}
	
	private void getOrderNo() {
		String temp = accountText.getText().toString().trim();
		
		if (temp.length()==0) {
			Toast.makeText(this, "请输入充值金额", 1).show();
			
			return;
		}
		
		if (NetUtil.isNetworkConnected(this)) {
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("amount",temp);
			
			ServiceClient.call(Consts.NetWork.PAY, params, callback);
		} else {
			Toast.makeText(this, Consts.NONETWORK, 1).show();
		}
	}
	
	ServiceClient.Callback callback = new ServiceClient.Callback() {
		@Override
		public void callback(JSONObject data) {
			 System.out.println("交易码： " + data);
			 
			try {
				if (data.getInt("errorcode") == 0) {
					if (!TextUtils.isEmpty(data.get("dataresult").toString())) {
						JSONObject json = data.getJSONObject("dataresult");
						
						goPay(json.getString("order_id"),json.getBoolean("testMode"));
					}
				}
				
			} catch (JSONException e) {
				e.printStackTrace();
				
				try {
					String str = data.getString("errormsg");
					Toast.makeText(PayActivity.this, str, 1).show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					Toast.makeText(PayActivity.this, Consts.UNKNOWN_FAULT, 1).show();
				}
			}
		}
	};
	
	private void goPay(String orderNo,boolean testMode){
		OrderService orderService = new OrderService(this,orderNo,testMode);
		
		orderService.execute();
	}
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == OrderService.REQUEST_CODE_BAOFOO_SDK) {
			String result = "";
			String msg = "";
			if (data == null || data.getExtras() == null) {
				msg = "支付已被取消";
				
			} else {
				result = data.getExtras().getString(BaofooPayActivity.PAY_RESULT);//-1:失败  0:取消  1:成功  10:处理中 
				msg = data.getExtras().getString(BaofooPayActivity.PAY_MESSAGE);
			}
			
			Toast.makeText(this, msg, 1).show();
			
			finish();
			
			/*AlertDialog dialog = new AlertDialog(this) {};
			dialog.setMessage(msg);
			dialog.show();*/
		}
	}
	
	public void backHandler(View v){
		finish();
	}

}
