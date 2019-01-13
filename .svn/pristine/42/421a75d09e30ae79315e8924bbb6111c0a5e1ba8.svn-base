package com.famous.zhifu.loan.famouscarloan.account;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.activity.ActivityInterface;
import com.famous.zhifu.loan.activity.Consts;
import com.famous.zhifu.loan.internet.ServiceClient;
import com.famous.zhifu.loan.util.NetUtil;
import com.famous.zhifu.loan.util.Tools;

public class FeedBackActivity extends Activity implements ActivityInterface {
	private View mView;
	private TextView mTvTitle, record;
	private EditText editContent, editPhone;
	private Button submit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		mView = getLayoutInflater().inflate(R.layout.activity_feedback, null);
		setContentView(mView);
		findView();
		initData();
		addAction();
	}

	@Override
	public void findView() {
		mTvTitle = (TextView) mView.findViewById(R.id.main_header_tv_text);

		editContent = (EditText) mView.findViewById(R.id.editContent);
		editPhone = (EditText) mView.findViewById(R.id.editPhone);
		submit = (Button) mView.findViewById(R.id.submit);
		record = (TextView) mView.findViewById(R.id.record);
	}

	@Override
	public void initData() {
		mTvTitle.setText(getResources().getString(R.string.feedback));
	}

	@Override
	public void addAction() {
		editContent.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				int length = editContent.getText().toString().length();
				if (length <= 500) {
					record.setText("(" + length + "/" + "500)");
					editContent.setEnabled(true);
				} else {
					editContent.setEnabled(false);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String phone = editPhone.getText().toString().trim();
				String content = editContent.getText().toString().trim();
				if (content.length() < 10) {
					Toast.makeText(FeedBackActivity.this, "回复内容必须大于10个字符", 1)
							.show();
				} else if (Tools.isEmpty(phone)) {
					Toast.makeText(FeedBackActivity.this, "请输入手机号码", 1).show();
				} else {
					submit(content, phone);
				}
			}
		});
	}

	public void submit(String content, String phone) {
		if (NetUtil.isNetworkConnected(this)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("feedback", content);
			params.put("mobile", phone);
			ServiceClient.call(Consts.NetWork.FEEDBACK, params, callback);
		} else {
			Toast.makeText(this, Consts.NONETWORK, 1).show();
		}
	}

	ServiceClient.Callback callback = new ServiceClient.Callback() {
		@Override
		public void callback(JSONObject data) {
			// System.out.println("意见反馈  " + data);
			try {
				if (data.getInt("errorcode") == 0) {
					String obj = data.getString("dataresult");
					if (!TextUtils.isEmpty(obj)) {
						Toast.makeText(FeedBackActivity.this, obj, 1).show();
						FeedBackActivity.this.finish();
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
				try {
					String str = data.getString("errormsg");
					Toast.makeText(FeedBackActivity.this, str, 1).show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					Toast.makeText(FeedBackActivity.this, Consts.UNKNOWN_FAULT,
							1).show();
				}
			}
		}
	};
	
	public void backHandler(View v){
		finish();
	}

}
