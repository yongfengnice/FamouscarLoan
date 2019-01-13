package com.famous.zhifu.loan.famouscarloan.account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.activity.ActivityInterface;
import com.famous.zhifu.loan.activity.Consts;
import com.famous.zhifu.loan.adapter.HeAdapter;
import com.famous.zhifu.loan.entity.HeInfo;
import com.famous.zhifu.loan.entity.TzjlInfo;
import com.famous.zhifu.loan.internet.ServiceClient;
import com.famous.zhifu.loan.util.NetUtil;
import com.famous.zhifu.loan.view.View_Loading;

public class TzjlDetailAc extends Activity implements ActivityInterface {
	private View mView;
	private TextView mTvTitle, mTvNumber, mTvDetailTitle, mTvMoney,
			mTvHadMoney, mTvYear, mTvTime, mTvStyle, mTvInvestTime;
	private TzjlInfo tzjlInfo;
	private ListView listview;
	private ArrayList<HeInfo> arrays = new ArrayList<HeInfo>();
	private HeAdapter adapter;
	private View_Loading loading;
	private Button memberSend;
	private EditText memberNo;
	private LinearLayout member_layout;
	private String enableStaff;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		mView = getLayoutInflater().inflate(R.layout.tzjl_detail, null);
		setContentView(mView);
		findView();
		initData();
		addAction();

	}

	@Override
	public void findView() {
		mTvTitle = (TextView) mView.findViewById(R.id.main_header_tv_text);
		mTvNumber = (TextView) mView
				.findViewById(R.id.tzjl_detail_tv_project_number);
		mTvDetailTitle = (TextView) mView
				.findViewById(R.id.tzjl_detail_tv_title);
		mTvMoney = (TextView) mView.findViewById(R.id.tzjl_detail_tv_money);
		mTvHadMoney = (TextView) mView
				.findViewById(R.id.tzjl_detail_tv_had_money);
		mTvYear = (TextView) mView.findViewById(R.id.tzjl_detail_tv_year);
		mTvTime = (TextView) mView.findViewById(R.id.tzjl_detail_tv_time);
		mTvStyle = (TextView) mView.findViewById(R.id.tzjl_detail_tv_style);
		mTvInvestTime = (TextView) mView
				.findViewById(R.id.tzjl_detail_tv_invest_time);

		loading = (View_Loading) findViewById(R.id.loading);
		
		listview = (ListView) findViewById(R.id.listview);
		adapter = new HeAdapter(this, arrays);
		listview.setAdapter(adapter);
		memberNo = (EditText)findViewById(R.id.tzjl_detail_tv_member_no);
		memberSend = (Button)findViewById(R.id.tzjl_detail_tv_member_send);
		member_layout = (LinearLayout)findViewById(R.id.tzjl_detail_tv_member_Layout);
	}

	@Override
	public void initData() {
		mTvTitle.setText("投资记录详情");
		tzjlInfo = (TzjlInfo) getIntent().getSerializableExtra("tzjlInfo");
		if (null != tzjlInfo) {
			mTvNumber.setText("项目编号:" + tzjlInfo.getLoansn());
			mTvDetailTitle.setText(tzjlInfo.getTitle());
			mTvMoney.setText(tzjlInfo.getMoney() + "");
			mTvYear.setText(tzjlInfo.getYearrate() + "%");
			mTvInvestTime.setText(tzjlInfo.getTimeadd());
			mTvTime.setText(tzjlInfo.getLoanmonth() + "个月");
			getData();
			if("0".equals(enableStaff)){
				member_layout.setVisibility(View.GONE);
			}else if(memberNo.getText()!=null){
				memberSend.setEnabled(false);
				memberNo.setEnabled(false);
				memberSend.setBackgroundColor(Color.GRAY);
			}
		}
		memberSend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (NetUtil.isNetworkConnected(TzjlDetailAc.this)) {
					loading.setVisibility(View.VISIBLE);
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("type", "staff");
					params.put("loansn", tzjlInfo.getLoansn() + "");
					params.put("tendersn", tzjlInfo.getTendersn() + "");
					params.put("staffno", memberNo.getText()+"");
					ServiceClient.call(Consts.NetWork.INVESTSTAFFNO,params, staffNoCallBack);
				} else {
					Toast.makeText(TzjlDetailAc.this, Consts.NONETWORK, 1).show();
				}
			}
		});
	}
	
final ServiceClient.Callback staffNoCallBack = new ServiceClient.Callback() {
		
		@Override
		public void callback(JSONObject data) {
			loading.setVisibility(View.GONE);
			try {
				String result = data.optString("errormsg");
				if("".equals(result)){
					Toast.makeText(TzjlDetailAc.this,"发送成功", 1).show();
					memberSend.setEnabled(false);
					memberNo.setEnabled(false);
					memberSend.setBackgroundColor(Color.GRAY);
				}else{
					Toast.makeText(TzjlDetailAc.this,result, 1).show();
				}
			} catch (Exception e) {
				e.printStackTrace();
				try {
					String str = data.getString("errormsg");
					Toast.makeText(TzjlDetailAc.this, str, 1).show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					Toast.makeText(TzjlDetailAc.this, Consts.UNKNOWN_FAULT, 1)
							.show();
				}
			}
		}
	};

	@Override
	public void addAction() {
	}

	public void getData() {
		if (NetUtil.isNetworkConnected(this)) {
			loading.setVisibility(View.VISIBLE);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("loansn", tzjlInfo.getLoansn() + "");
			params.put("tendersn", tzjlInfo.getTendersn() + "");
			ServiceClient.call(Consts.NetWork.INVESTDETAIL, params, verifyback);
		} else {
			Toast.makeText(this, Consts.NONETWORK, 1).show();
		}
	}

	ServiceClient.Callback verifyback = new ServiceClient.Callback() {
		@Override
		public void callback(JSONObject data) {
			loading.setVisibility(View.GONE);
			try {
				if (data.getInt("errorcode") == 0) {
					JSONObject json = data.getJSONObject("dataresult");
					JSONObject obj1 = json.getJSONObject("tender");
					mTvStyle.setText(obj1.getString("payment"));
					mTvHadMoney.setText(obj1.getString("reciverate"));
					memberNo.setText(obj1.optString("staffno"));
					enableStaff = obj1.optString("enableinputstaff");
					JSONArray array = json.getJSONArray("allot");
					for (int i = 0, j = array.length(); i < j; i++) {
						HeInfo info = new HeInfo();
						JSONObject obj = array.getJSONObject(i);
						info.setLasttime(obj.getString("lasttime"));
						info.setId(obj.getString("id"));
						info.setMoney(obj.getString("money"));
						info.setStatus(obj.getString("status"));
						info.setRatemoney(obj.getString("ratemoney"));
						info.setResiduemoney(obj.getString("residuemoney"));
						arrays.add(info);
					}
					adapter.notifyDataSetChanged();
				}
			} catch (JSONException e) {
				e.printStackTrace();
				try {
					String str = data.getString("errormsg");
					Toast.makeText(TzjlDetailAc.this, str, 1).show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					Toast.makeText(TzjlDetailAc.this, Consts.UNKNOWN_FAULT, 1)
							.show();
				}
			}
		}
	};
	
	public void backHandler(View v) {
		finish();
	}
	
}
