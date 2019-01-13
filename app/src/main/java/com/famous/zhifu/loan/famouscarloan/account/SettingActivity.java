package com.famous.zhifu.loan.famouscarloan.account;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.activity.ActivityInterface;
import com.famous.zhifu.loan.activity.Consts;
import com.famous.zhifu.loan.activity.LoginActivity;
import com.famous.zhifu.loan.activity.MainActivity;
import com.famous.zhifu.loan.activity.MyApp;
import com.famous.zhifu.loan.activity.WebViewActivity;
import com.famous.zhifu.loan.famouscarloan.home.CalculateAc;
import com.famous.zhifu.loan.famouscarloan.home.LoanMoneyActivity;
import com.famous.zhifu.loan.famouscarloan.notice.NoticeActivity;
import com.famous.zhifu.loan.internet.ServiceClient;
import com.famous.zhifu.loan.util.NetUtil;
import com.famous.zhifu.loan.util.SharePrefUtil;
import com.famous.zhifu.loan.util.Tools;
import com.famous.zhifu.loan.util.UpDateCustomDialog;

public class SettingActivity extends Activity implements ActivityInterface {
	private View mView;
	private TextView mTvTitle;
	private ImageView mIvRight;
	private Button exit;

	private String updatemsg;
	private JSONObject json;
	private String version_currect;
	private String version_client;
	private String update_desc;
	private TextView tv_update_desc;
	private String updateUrl;
	private UpDateCustomDialog upDateCustomDialog;
	private RelativeLayout rl;
	private MainActivity parentActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		mView = getLayoutInflater().inflate(R.layout.activity_account_setting,
				null);
		setContentView(mView);
		findView();
		initData();
		addAction();
	}

	@Override
	public void findView() {
		mView.findViewById(R.id.main_header_iv_left).setVisibility(View.GONE);

		mTvTitle = (TextView) mView.findViewById(R.id.main_header_tv_text);
		mIvRight = (ImageView) mView.findViewById(R.id.main_header_iv_right);

		exit = (Button) mView.findViewById(R.id.exit);
		rl = (RelativeLayout) mView.findViewById(R.id.rl);
	}

	@Override
	public void initData() {
		parentActivity = (MainActivity)getParent();
		mTvTitle.setText("更多内容");
		mIvRight.setVisibility(View.GONE);

		tv_update_desc = (TextView) findViewById(R.id.tv_update_desc);
		updatemsg = SharePrefUtil.getString(SettingActivity.this, "update", "");
		try {
			json = new JSONObject(updatemsg);
			version_currect = json.getString("version_currect");
			version_client = json.getString("version_client");
			update_desc = json.getString("update_desc");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		if ((Integer.toString(Tools.getVersionCode(parentActivity))).equals(version_currect)) {
			tv_update_desc.setText("该版本是最新版本");
		} else {
			tv_update_desc.setText("当前版本号:" + Tools.getVersionCode(parentActivity));
		}
	}

	@Override
	public void addAction() {
		exit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				exit();
			}
		});

		rl.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getUpdateData();
			}
		});

	}

	public void bigIconClick(View v) {
		switch (v.getId()) {
		case R.id.more_border_news: // 新闻公告
			startActivity(new Intent(this, NoticeActivity.class));
			
			break;

		case R.id.more_border_quick: // 快速借款
			startActivity(new Intent(this, LoanMoneyActivity.class));
			
			break;

		case R.id.more_border_accurate: // 计算器

			startActivity(new Intent(this, CalculateAc.class));

			break;

		default:
			break;
		}
	}

	public void smallIconClick(View v) {
		switch (v.getId()) {
		case R.id.more_about: // 关于智富360
			String url = "http://service.zhifu360.com/about";

			Intent intent = new Intent(SettingActivity.this,
					WebViewActivity.class);
			intent.putExtra("apipath", url);

			startActivity(intent);

			break;

		case R.id.more_question: // 常见问题
			startActivity(new Intent(SettingActivity.this,
					UsuallyProblemActivity.class));

			break;

		case R.id.more_feedback: // 意见反馈
			startActivity(new Intent(SettingActivity.this,
					FeedBackActivity.class));

			break;

		case R.id.more_line: // 客服热线
			Intent intentkfrx = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:400-86-360-86"));

			startActivity(intentkfrx);

			break;

		case R.id.more_setting: // 设置
			if (!Tools.isEmpty(MyApp.token)) {
				startActivity(new Intent(this,AccountVerifyActivity.class));
			} else {
				Toast.makeText(this, "您尚未登录账户", 1).show();
				
				startActivityForResult(new Intent(this,LoginActivity.class), 1003);
			}

		default:
			break;
		}
	}

	private void getUpdateData() {
		try {
			if ((Integer.toString(Tools.getVersionCode(parentActivity))).equals(version_currect)) {
				Toast.makeText(SettingActivity.this, "已经是最新版本",
						Toast.LENGTH_SHORT).show();
			} else {
				 
					updateUrl = json.getJSONObject("lasted_version_url")
							.getString("ANDROID");

					String apkPath = Environment.getExternalStorageDirectory()
							.getPath() + "/mingchedai/apk";
					String apkName = "";

					if (!TextUtils.isEmpty(updateUrl))
						apkName = updateUrl.substring(updateUrl
								.lastIndexOf("/") + 1);
					if (!TextUtils.isEmpty(apkName)
							&& !apkName.contains(".apk")) {
						apkName += ".apk";
					}
					upDateCustomDialog = new UpDateCustomDialog(
							SettingActivity.this, apkPath, updateUrl, apkName);
					upDateCustomDialog.showDilog("版本升级提示", update_desc);
				
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void exit() {
		if (NetUtil.isNetworkConnected(this)) {
			ServiceClient.call(Consts.NetWork.EXIT, null, callback);
		} else {
			Toast.makeText(this, Consts.NONETWORK, 1).show();
		}
	}

	ServiceClient.Callback callback = new ServiceClient.Callback() {
		@Override
		public void callback(JSONObject data) {
			// System.out.println("退出登录  " + data);
			try {
				if (data.getInt("errorcode") == 0) {
					if (!TextUtils.isEmpty(data.get("dataresult").toString())) {
						Toast.makeText(SettingActivity.this,
								data.get("dataresult") + "", 1).show();

						SharePrefUtil.clearUserInfo(SettingActivity.this);
						MyApp.username = "";
						MyApp.token = "";
						finish();
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
				try {
					String str = data.getString("errormsg");
					Toast.makeText(SettingActivity.this, str, 1).show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					Toast.makeText(SettingActivity.this, Consts.UNKNOWN_FAULT,
							1).show();
				}
			}
		}
	};

}
