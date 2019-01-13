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
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.activity.ActivityInterface;
import com.famous.zhifu.loan.activity.Consts;
import com.famous.zhifu.loan.famouscarloan.home.FindPasswordActivity;
import com.famous.zhifu.loan.internet.ServiceClient;
import com.famous.zhifu.loan.util.NetUtil;
import com.famous.zhifu.loan.util.Tools;

public class ChangeHeadVerifyActivity extends Activity implements
		ActivityInterface, OnClickListener {
	private View mView;
	private TextView mTvTitle, findPwd;
	private Button submit;
	private TextView originalPwd, editPassword, editAgainPwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		mView = getLayoutInflater().inflate(
				R.layout.activity_change_head_verify, null);
		setContentView(mView);
		findView();
		initData();
		addAction();
	}

	@Override
	public void findView() {
		mTvTitle = (TextView) mView.findViewById(R.id.main_header_tv_text);

		findPwd = (TextView) mView.findViewById(R.id.findPwd);
		submit = (Button) mView.findViewById(R.id.submit);

		originalPwd = (TextView) findViewById(R.id.originalPwd);
		editPassword = (TextView) findViewById(R.id.editPassword);
		editAgainPwd = (TextView) findViewById(R.id.editAgainPwd);
	}

	@Override
	public void initData() {
		mTvTitle.setText(getResources().getString(R.string.verify_password));
	}

	@Override
	public void addAction() {
		submit.setOnClickListener(this);
		findPwd.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.submit:
			submit();
			break;
		case R.id.findPwd:
			startActivity(new Intent(this, FindPasswordActivity.class));
			break;

		default:
			break;
		}
	}

	public void submit() {
		String oPwd = originalPwd.getText().toString().trim();
		String pwd = editPassword.getText().toString().trim();
		String aPwd = editAgainPwd.getText().toString().trim();
		if (Tools.isEmpty(oPwd)) {
			Toast.makeText(this, "请输入原始密码", 1).show();
		} else if (Tools.isEmpty(pwd)) {
			Toast.makeText(this, "请输入新密码", 1).show();
		} else if (pwd.length() < 6 || pwd.length() > 16) {
			Toast.makeText(this, "新密码应在6-16位之间", 1).show();
		} else if (Tools.isEmpty(aPwd)) {
			Toast.makeText(this, "请再次输入新密码", 1).show();
		} else if (!Tools.isEquals(pwd, aPwd)) {
			Toast.makeText(this, "两次密码不一致", 1).show();
		} else {
			if (NetUtil.isNetworkConnected(this)) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("oldpassword", oPwd);
				params.put("newpassword", pwd);
				params.put("repassword", aPwd);
				ServiceClient.call(Consts.NetWork.UPDATE, params, callback);
			} else {
				Toast.makeText(this, Consts.NONETWORK, 1).show();
			}
		}
	}

	ServiceClient.Callback callback = new ServiceClient.Callback() {
		@Override
		public void callback(JSONObject data) {
			// System.out.println("修改密码  " + data);
			try {
				if (data.getInt("errorcode") == 0) {
					if (!TextUtils.isEmpty(data.get("dataresult").toString())) {
						JSONObject obj = data.getJSONObject("dataresult");
						Toast.makeText(ChangeHeadVerifyActivity.this,
								obj.getString("msg"), 1).show();
						finish();
					}

				}
			} catch (JSONException e) {
				e.printStackTrace();
				try {
					String str = data.getString("errormsg");
					Toast.makeText(ChangeHeadVerifyActivity.this, str, 1)
							.show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					Toast.makeText(ChangeHeadVerifyActivity.this,
							Consts.UNKNOWN_FAULT, 1).show();
				}
			}
		}
	};
	
	public void backHandler(View v){
		finish();
	}

}
