package com.famous.zhifu.loan.famouscarloan.account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.activity.ActivityInterface;
import com.famous.zhifu.loan.activity.Consts;
import com.famous.zhifu.loan.adapter.BankAdapter;
import com.famous.zhifu.loan.internet.ServiceClient;

public class BankSelectActivity extends Activity implements ActivityInterface,
		OnClickListener {
	private View mView;
	private TextView mTvTitle;
	private ImageView mIvRight, mIvLeft;
	private ArrayList<String> arrayLists = new ArrayList<String>();
	private ListView lv_bank;
	private BankAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		mView = getLayoutInflater().inflate(R.layout.bank_select, null);
		setContentView(mView);
		findView();
		initData();
		addAction();
		getBankData();
	}

	@Override
	public void findView() {
		mTvTitle = (TextView) mView.findViewById(R.id.main_header_tv_text);
		mIvRight = (ImageView) mView.findViewById(R.id.main_header_iv_right);
		mIvLeft = (ImageView) mView.findViewById(R.id.main_header_iv_left);
		
		lv_bank = (ListView) findViewById(R.id.lv_bank);
		adapter = new BankAdapter(this, arrayLists);
		lv_bank.setAdapter(adapter);
	}

	@Override
	public void initData() {
		mTvTitle.setText("请选择开户银行");
		mIvRight.setVisibility(View.GONE);
	}

	@Override
	public void addAction() {
		mIvLeft.setOnClickListener(this);
		lv_bank.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String name = arrayLists.get(arg2);
				Intent intent = new Intent();
				intent.putExtra("name", name);
				setResult(1002, intent);
				finish();
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_header_iv_left:
			finish();
			break;

		default:
			break;
		}
	}

	public void getBankData() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", "bank");
		params.put("act", "get_banklist");
		ServiceClient.call(Consts.NetWork.BINDSUBMIT, params, verifyback);
	}

	ServiceClient.Callback verifyback = new ServiceClient.Callback() {
		@Override
		public void callback(JSONObject ret) {
			// System.out.println(" 银行列表  " + ret);
			try {
				if (ret.getInt("errorcode") == 0) {
					JSONArray array = ret.getJSONArray("dataresult");
					for (int i = 0, j = array.length(); i < j; i++) {
						arrayLists.add(array.getString(i));
					}
					adapter.notifyDataSetChanged();
				}
			} catch (JSONException e) {
				try {
					String str = ret.getString("errormsg");
					Toast.makeText(BankSelectActivity.this, str, 1).show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					Toast.makeText(BankSelectActivity.this,
							Consts.UNKNOWN_FAULT, 1).show();
				}
			}
		}
	};

}
