package com.famous.zhifu.loan.famouscarloan.account;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.activity.ActivityInterface;
import com.famous.zhifu.loan.activity.Consts;
import com.famous.zhifu.loan.internet.ServiceClient;
import com.famous.zhifu.loan.util.Tools;

public class NameVerifyActivity extends Activity implements ActivityInterface,
		OnClickListener {
	private View mView;
	private TextView mTvTitle;
	private Button submit;
	private EditText editName, editIdcard;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		mView = getLayoutInflater()
				.inflate(R.layout.activity_name_verify, null);
		setContentView(mView);
		findView();
		initData();
		addAction();
	}

	@Override
	public void findView() {
		mTvTitle = (TextView) mView.findViewById(R.id.main_header_tv_text);

		submit = (Button) mView.findViewById(R.id.submit);
		editName = (EditText) mView.findViewById(R.id.editName);
		editIdcard = (EditText) mView.findViewById(R.id.editIdcard);

	}

	@Override
	public void initData() {
		mTvTitle.setText(getResources().getString(R.string.verify_name));
	}

	@Override
	public void addAction() {
		submit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.submit:
			submitNameData();
			break;

		default:
			break;
		}
	}

	public void submitNameData() {
		String userName = editName.getText().toString().trim();
		String idCard = editIdcard.getText().toString().trim();

		if (Tools.isEmpty(userName)) {
			Toast.makeText(this, "请收入您的姓名", 1).show();
		} else if (Tools.isEmpty(idCard)) {
			Toast.makeText(this, "请输入您的身份证号码", 1).show();
		}else if(!Tools.isIdCard(idCard)){
			Toast.makeText(this, "请输入正确的身份证号码", 1).show();
		} else {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("type", "realname");
			params.put("name", userName);
			params.put("certinumber", idCard);
			ServiceClient.call(Consts.NetWork.BINDSUBMIT, params, verifyback);
		}
	}

	ServiceClient.Callback verifyback = new ServiceClient.Callback() {
		@Override
		public void callback(JSONObject ret) {
			// System.out.println(" 实名验证  " + ret);
			try {
				if (ret.getInt("errorcode") == 0) {
					String result = ret.getString("dataresult");
					Toast.makeText(NameVerifyActivity.this, result, 1).show();
					setResult(1002);
					NameVerifyActivity.this.finish();
				}
			} catch (JSONException e) {
				try {
					String str = ret.getString("errormsg");
					Toast.makeText(NameVerifyActivity.this, str, 1).show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					Toast.makeText(NameVerifyActivity.this,
							Consts.UNKNOWN_FAULT, 1).show();
				}
			}
		}
	};
	
	
	public void backHandler(View v){
		finish();
	}
}
