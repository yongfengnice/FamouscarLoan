package com.famous.zhifu.loan.famouscarloan.home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.tsz.afinal.FinalBitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.activity.ActivityInterface;
import com.famous.zhifu.loan.activity.Consts;
import com.famous.zhifu.loan.activity.LoginActivity;
import com.famous.zhifu.loan.activity.MainActivity;
import com.famous.zhifu.loan.activity.MyApp;
import com.famous.zhifu.loan.activity.RegsiterActivity;
import com.famous.zhifu.loan.adapter.HomeAdapter;
import com.famous.zhifu.loan.adapter.HomeAdapter.IHomeClickListener;
import com.famous.zhifu.loan.entity.CarInfo;
import com.famous.zhifu.loan.entity.HomeInfo;
import com.famous.zhifu.loan.famouscarloan.account.SettingActivity;
import com.famous.zhifu.loan.famouscarloan.investlist.InvestDetailActivity;
import com.famous.zhifu.loan.internet.ServiceClient;
import com.famous.zhifu.loan.util.DensityUtils;
import com.famous.zhifu.loan.util.NetUtil;
import com.famous.zhifu.loan.util.SharePrefUtil;
import com.famous.zhifu.loan.util.Tools;
import com.famous.zhifu.loan.util.UpDateCustomDialog;
import com.famous.zhifu.loan.view.abviewpager.AbSlidingPlayView;
import com.famous.zhifu.loan.view.xlistview.XListView;
import com.famous.zhifu.loan.view.xlistview.XListView.IXListViewListener;

public class HomeActivity extends Activity implements ActivityInterface,IXListViewListener, IHomeClickListener {
	private View mView;
	private ArrayList<CarInfo> carlists = new ArrayList<CarInfo>();
	private ArrayList<HomeInfo> homelists = new ArrayList<HomeInfo>();
	private boolean isclearList = true; // 是否清除集合(用于下拉刷新)
	private XListView xlistview;
	private HomeAdapter adapter;
	private AbSlidingPlayView slidingPlayView;
	private FinalBitmap fb;
	
	private TextView totalLend = null;
	
	private TextView loginView = null;
	
	private ImageView regImg = null;
	private TextView guidView = null;
	
	private MainActivity parentActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mView = getLayoutInflater().inflate(R.layout.activity_home, null);
		setContentView(mView);
		MyApp.getInstance().addActivity(this);
		findView();
		initData();
		addAction();
		initHome();
	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if (TextUtils.isEmpty(MyApp.token)) {
			loginView.setVisibility(View.VISIBLE);
			
			regImg.setVisibility(View.VISIBLE);
			guidView.setText("快速注册");
			
		} else {
			loginView.setVisibility(View.GONE);
			
			regImg.setVisibility(View.GONE);
			guidView.setText("进入帐户中心");
		}
	}


	@Override
	public void findView() {
		loginView = (TextView)findViewById(R.id.home_login);
		
		fb = FinalBitmap.create(getApplicationContext());
		HomeInfo home = new HomeInfo();
		homelists.add(home);
		View home_headview = getLayoutInflater().from(this).inflate(R.layout.home_headview, null);
		
		totalLend = (TextView)home_headview.findViewById(R.id.home_headview_touzi);
		
		LinearLayout reg = (LinearLayout) home_headview.findViewById(R.id.home_headview_reg);
		
		regImg = (ImageView) home_headview.findViewById(R.id.home_headview_reg_img);
		guidView = (TextView) home_headview.findViewById(R.id.home_headview_guid);
		
		
		reg.setOnClickListener(new MyClickListener());
		
		slidingPlayView = (AbSlidingPlayView) home_headview.findViewById(R.id.slidingPlayView);
		
		slidingPlayView.setNavHorizontalGravity(Gravity.CENTER_HORIZONTAL);
		
		int scale = 1;
		
		if (Tools.getScreenWidth(this)<=800) {
			scale = 1;
		}else {
			scale = Tools.getScreenWidth(this)/800;
		}
		
		home_headview.setMinimumHeight(DensityUtils.dp2px(this, 200) * scale);

		xlistview = (XListView) mView.findViewById(R.id.xlistview);
		xlistview.setXListViewListener(this);
		xlistview.setPullLoadEnable(false);// 开启加载更多
		xlistview.setPullRefreshEnable(true);// 开启下拉刷新
		long lastRefTime = SharePrefUtil.getLastUpdateTime(getApplicationContext(), Consts.SharePref.HOME);
		xlistview.setLastRefreshTime(lastRefTime);
		xlistview.addHeaderView(home_headview);
		
		adapter = new HomeAdapter(this, homelists);
		adapter.setListener(this);
		xlistview.setAdapter(adapter);
	}

	@Override
	public void initData() {
		parentActivity = (MainActivity)getParent();
		
	}

	@Override
	public void addAction() {

	}

	private class MyClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.home_headview_reg:	//快速注册
				if (TextUtils.isEmpty(MyApp.token)) {
					startActivity(new Intent(HomeActivity.this,RegsiterActivity.class));
				}else {
					parentActivity.setClicKHandler(2);
				}
				
				break;
				
			/*case R.id.home_lly_join_us:// 加盟我们~
				startActivity(new Intent(HomeActivity.this,JoinUsActivity.class));
				break;*/
			}
		}
	}

	private void initHome() {
		if (NetUtil.isNetworkConnected(this)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("version", Consts.CONFIG_VERSION);
			params.put("device_os", android.os.Build.VERSION.RELEASE);
			params.put("device_width", MyApp.screenWidth + "");
			params.put("device_height", MyApp.screenHeight + "");
			ServiceClient.call(Consts.NetWork.HOMEINIT, params, initback);
		} else {
			Toast.makeText(HomeActivity.this, Consts.NONETWORK, 1).show();
			xlistview.stopRefresh();
		}

	}

	final ServiceClient.Callback initback = new ServiceClient.Callback() {

		@Override
		public void callback(JSONObject ret) {

			xlistview.stopRefresh();
			// 记录最后刷新时间
			long lastTime = System.currentTimeMillis();
			xlistview.setLastRefreshTime(lastTime);
			SharePrefUtil.setLastUpdateTime(getApplicationContext(),Consts.SharePref.HOME);
			// 如果是下拉刷新则清除集合
			if (isclearList) {
				homelists.clear();
				carlists.clear();
				isclearList = false;
				slidingPlayView.stopPlay();
				slidingPlayView.removeAllViews();
			}

			try {
				if (ret.getString("errorcode").equals("0")) {
					HomeInfo info = new HomeInfo();
					JSONObject json = ret.getJSONObject("dataresult");
					SharePrefUtil.saveString(HomeActivity.this, "update", json+ "");
					
					JSONArray carArray = json.getJSONArray("going_loan");
					// 解析车信息
					parseCarArray(carArray);
					
					JSONArray adArray = json.getJSONArray("banners");
					// 解析广告信息
					initViewPager(adArray);

					info.setCarlists(carlists);
					
					info.setTotal_lend(json.getString("total_lend"));
					totalLend.setText(info.getTotal_lend());
					info.setVersion_client(json.getString("version_client"));
					info.setVersion_currect(json.getString("version_currect"));
					info.setUpdate_desc(json.getString("update_desc"));
					info.setUpdate_force(json.getString("update_force"));
					info.setTotal_rate(json.getString("total_rate"));
					info.setAndroidUrl(json.getJSONObject("lasted_version_url").getString("ANDROID"));
					homelists.add(info);

					adapter.notifyDataSetChanged();
					if( !(Integer.toString(Tools.getVersionCode(parentActivity))).equals(info.getVersion_client())){
						String update_desc = json.getString("update_desc");
						String updateUrl = json.getJSONObject("lasted_version_url")
								.getString("ANDROID");
						String apkPath = Environment.getExternalStorageDirectory()
								.getPath() + "/zhifu360/apk";
						String apkName = "";

						if (!TextUtils.isEmpty(updateUrl))
							apkName = updateUrl.substring(updateUrl
									.lastIndexOf("/") + 1);
						if (!TextUtils.isEmpty(apkName)
								&& !apkName.contains(".apk")) {
							apkName += ".apk";
						}
						UpDateCustomDialog upDateCustomDialog = new UpDateCustomDialog(
								HomeActivity.this, apkPath, updateUrl, apkName);
						upDateCustomDialog.showDilog("版本升级提示", update_desc);
					}
				}
			} catch (JSONException e) {
				try {
					String str = ret.getString("errormsg");
					Toast.makeText(HomeActivity.this, str, 1).show();
				} catch (JSONException e1) {
					e1.printStackTrace();
					Toast.makeText(HomeActivity.this, "获取签到信息失败", 1).show();
				}
			}
		}
	};

	public void parseCarArray(JSONArray carArray) {
		try {
			for (int i = 0, j = carArray.length(); i < j; i++) {
				CarInfo info = new CarInfo();
				JSONObject obj = carArray.getJSONObject(i);
				info.setLoan_type(obj.getString("loan_type"));
				info.setCateid(obj.getString("cateid"));
				info.setLoanday(obj.getString("loanday"));
				info.setTendermoney(obj.getString("tendermoney"));
				info.setLoansn(obj.getString("loansn"));
				info.setYearrate(obj.getString("yearrate"));
				info.setCate_logo(obj.getString("cate_logo"));
				info.setRepayment(obj.getString("repayment"));
				info.setLoanstatus(obj.getString("loanstatus"));
				info.setLoanmoney(obj.getString("loanmoney"));
				info.setLoanmonth(obj.getString("loanmonth"));
				info.setTitle(obj.getString("title"));
				info.setRatio(obj.getString("ratio"));
				info.setPath(obj.getString("slt_path"));
				info.setId(obj.getString("id"));
				info.setType(obj.getString("type"));
				
				carlists.add(info);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void initViewPager(JSONArray adArray) {
		LayoutInflater inflater = LayoutInflater.from(this);
		
		try {
			for (int i = 0, j = adArray.length(); i < j; i++) {
				JSONObject jo = adArray.getJSONObject(i);
				String url = jo.getString("image");
				
				ImageView image = (ImageView) inflater.inflate(R.layout.item_home_img, null);
				

//				image.setLayoutParams(new Gallery.LayoutParams(Tools.getScreenWidth(this), 400));
				
//				ImageView image = new ImageView(HomeActivity.this);
				
				fb.display(image, url);
//				image.setScaleType(ScaleType.FIT_CENTER);
				slidingPlayView.addView(image);
			}
			// 开始图片自动切换
			slidingPlayView.startPlay();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onRefresh() {
		isclearList = true;
		
		initHome();
	}

	@Override
	public void onLoadMore() {

	}

	@Override
	public void homeCallback(int flag) {
		switch (flag) {
		case 1:
			startActivity(new Intent(HomeActivity.this, JoinUsActivity.class));
			break;
		case 2:
			startActivity(new Intent(HomeActivity.this, LoanMoneyActivity.class));
			break;
		case 3:
			startActivity(new Intent(HomeActivity.this, RankActivity.class));
			break;
		case 4:
			startActivity(new Intent(HomeActivity.this, CalculateAc.class));
			break;
			
		case 5:
			CarInfo carInfo1 = carlists.get(0);
			Intent intent1 = new Intent(HomeActivity.this,InvestDetailActivity.class);
			intent1.putExtra("sn", carInfo1.getLoansn());
			startActivity(intent1);
			break;
		case 6:
			CarInfo carInfo2 = carlists.get(1);
			Intent intent2 = new Intent(HomeActivity.this,InvestDetailActivity.class);
			intent2.putExtra("sn", carInfo2.getLoansn());
			startActivity(intent2);
			break;
		case 7:
			CarInfo carInfo3 = carlists.get(2);
			Intent intent3 = new Intent(HomeActivity.this,InvestDetailActivity.class);
			intent3.putExtra("sn", carInfo3.getLoansn());
			startActivity(intent3);
			break;

		default:
			break;
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		MyApp.getInstance().remove(this);
	}
	
	public void loginHandler(View v){
		startActivity(new Intent(this,LoginActivity.class));
	}
}

