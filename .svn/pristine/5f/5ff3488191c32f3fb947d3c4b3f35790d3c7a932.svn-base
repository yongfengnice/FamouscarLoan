package com.famous.zhifu.loan.famouscarloan.investlist;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.activity.Consts;
import com.famous.zhifu.loan.internet.ServiceClient;
import com.famous.zhifu.loan.util.Tools;

public class InfoDetailAc extends Activity implements OnClickListener {
	private TextView title, remarkTv;
	private ImageView ivLeft, ivRight;
	private String remark;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info_detail);
		initView();
		// getCarDetail();
	}

	public void initView() {
		remark = getIntent().getStringExtra("remark");

		title = (TextView) findViewById(R.id.main_header_tv_text);
		title.setText("借款人信息");
		ivLeft = (ImageView) findViewById(R.id.main_header_iv_left);
		ivLeft.setOnClickListener(this);
		ivRight = (ImageView) findViewById(R.id.main_header_iv_right);
		ivRight.setVisibility(View.GONE);

		remarkTv = (TextView) findViewById(R.id.remarkTv);
		if(!Tools.isEmpty(remark)){
			remarkTv.setText(Html.fromHtml(remark));
		}
	}

	private void getCarDetail() {
		// if (NetUtil.isNetworkConnected(this)) {
		// Map<String, Object> params = new HashMap<String, Object>();
		// params.put("sn", sn);
		// params.put("page_type", "userinfo");
		// ServiceClient
		// .call(Consts.NetWork.INVESTDETAIL, params, qian_detail);
		// } else {
		// Toast.makeText(this, Consts.NONETWORK, 1).show();
		// }
	}

	ServiceClient.Callback qian_detail = new ServiceClient.Callback() {

		@Override
		public void callback(JSONObject data) {
			// System.out.println("借款人信息   :" + data);

			try {
				if (data.getInt("errorcode") == 0) {
					JSONObject jsonObject = data.getJSONObject("dataresult");
				}
			} catch (Exception e) {
				e.printStackTrace();
				try {
					String str = data.getString("errormsg");
					Toast.makeText(InfoDetailAc.this, str, 1).show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					Toast.makeText(InfoDetailAc.this, Consts.UNKNOWN_FAULT, 1)
							.show();
				}
			}
		}
	};

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
}
