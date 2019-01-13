package com.famous.zhifu.loan.famouscarloan.investlist;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.tsz.afinal.FinalBitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.activity.ActivityInterface;
import com.famous.zhifu.loan.activity.Consts;
import com.famous.zhifu.loan.activity.LoginActivity;
import com.famous.zhifu.loan.activity.MyApp;
import com.famous.zhifu.loan.adapter.DetailPayAdapter;
import com.famous.zhifu.loan.adapter.DetailTzjlAdapter;
import com.famous.zhifu.loan.adapter.GirdAdapter;
import com.famous.zhifu.loan.entity.CarPictureInfo;
import com.famous.zhifu.loan.entity.DetailPayTypeInfo;
import com.famous.zhifu.loan.entity.DetailTzjlInfo;
import com.famous.zhifu.loan.entity.PictureInfo;
import com.famous.zhifu.loan.entity.TemporaryInfo;
import com.famous.zhifu.loan.intef.ILinster;
import com.famous.zhifu.loan.internet.ServiceClient;
import com.famous.zhifu.loan.util.NetUtil;
import com.famous.zhifu.loan.view.View_Loading;
import com.famous.zhifu.loan.view.dialog.DetailJisuanPopup;
import com.famous.zhifu.loan.view.mygridview.ExpandableHeightGridView;
import com.famous.zhifu.loan.view.mylistview.MyListView;

public class InvestDetailActivity extends Activity implements ActivityInterface, OnClickListener, ILinster {
	private View mView;
	private ImageView userImg;
	private String sn;
	private TextView tvType, mTvTitle, title, tvSn, tvLoanmoney, tvLoanmonth,tvRepayment, tvYearrate,jindu, submit;
	private TextView stateView,timeView,remainJine,has_jine,zuidi_jine;
	
	private ProgressBar bar;
	private String remark;
	private int radio, i;
	public static InvestDetailActivity act;

	private String mintendermoney;// 最小投资金额
	private String yearrate;// 年收益率
	private String loanmonth;// 标借款时间
	private String repayment; // 还款方式
	private float ketoujine;
	private String loanmoney;// 借款金额
	private String tendermoney; // 已投标金额
	
	private RadioGroup radioGroup ;
	LinearLayout borderLayout;
	private LayoutInflater inflater;
	
	private JSONObject cacheObj = null;
	
	private DetailTzjlAdapter detailTzjlAdapter = null;
	private DetailPayAdapter payAdapter = null;
	private GirdAdapter carAdapter = null;
	
	private ArrayList<TemporaryInfo> temporaryLists = new ArrayList<TemporaryInfo>();
	
	private DetailJisuanPopup popup = null;
	
	private View_Loading loading;

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			i = i + 1;
			if (i <= radio) {
				//circle.setProgress(i);

				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
					}
				}, 10);
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		mView = getLayoutInflater().inflate(R.layout.invest_detail, null);
		setContentView(mView);
		act = this;
		findView();
		initData();
		addAction();
		
		loading.setVisibility(View.VISIBLE);
		
		getInvestDetail();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if (TextUtils.isEmpty(MyApp.token)) {
			userImg.setVisibility(View.VISIBLE);
		} else {
			userImg.setVisibility(View.GONE);
		}
	}

	@Override
	public void findView() {
		inflater = LayoutInflater.from(this);
		
		mTvTitle = (TextView) mView.findViewById(R.id.main_header_tv_text);
		
		loading = (View_Loading) findViewById(R.id.loading);
		
		userImg = (ImageView)mView.findViewById(R.id.main_header_iv_right);
		
		tvType = (TextView) mView.findViewById(R.id.detail_type);
		title = (TextView) mView.findViewById(R.id.title);
		tvSn = (TextView) mView.findViewById(R.id.sn);
		
		tvLoanmoney = (TextView) mView.findViewById(R.id.loanmoney);
		tvLoanmonth = (TextView) mView.findViewById(R.id.loanmonth);
		tvRepayment = (TextView) mView.findViewById(R.id.repayment);
		tvYearrate = (TextView) mView.findViewById(R.id.yearrate);
		
		bar = (ProgressBar) mView.findViewById(R.id.invest_detail_progressbar);
		jindu = (TextView) mView.findViewById(R.id.invest_detail_jindu); 
		
		stateView = (TextView) mView.findViewById(R.id.invest_detail_remain_state);
		timeView = (TextView) mView.findViewById(R.id.invest_detail_remain_time);
		remainJine = (TextView) mView.findViewById(R.id.invest_detail_remain_jine);
		has_jine = (TextView) mView.findViewById(R.id.invest_detail_has_jine);
		zuidi_jine = (TextView) mView.findViewById(R.id.invest_detail_zuidi_jine);
		
		radioGroup = (RadioGroup) mView.findViewById(R.id.detail_radio_button_group);
		borderLayout = (LinearLayout) mView.findViewById(R.id.invest_detail_tab_border);

		submit = (TextView) mView.findViewById(R.id.submit);
	}

	@Override
	public void initData() {
		mTvTitle.setText(getResources().getString(R.string.invest_detail));

		sn = getIntent().getStringExtra("sn");
	}

	@Override
	public void addAction() {
		
		submit.setOnClickListener(this);
		
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup arg0, int checkedId) {
				borderLayout.removeAllViews();
				
				switch (checkedId) {
				case R.id.invest_detail_tab_loan:	//借款信息
					
					borderLayout.addView(getUserInfo());
					
					break;
					
				case R.id.invest_detail_tab_car:	//车辆概况
					borderLayout.addView(getCarView());
					
					break;

				case R.id.invest_detail_tab_record:	//投资记录
					borderLayout.addView(getTouziView());
					
					break;
					
				default:
					borderLayout.addView(getPayType());
					
					break;
				}
			}
		});
	}
	
	//借款信息
	private View getUserInfo(){
		View view = inflater.inflate(R.layout.item_invest_detail_personal, null);
		
		try {
			JSONObject userInfo = cacheObj.getJSONObject("userinfo");
			
			TextView yongtuView = (TextView) view.findViewById(R.id.item_invest_detail_personal_yongtu) ;
			yongtuView.setText(userInfo.getString("jiekuanyongtu"));
			
			TextView laiyuanView = (TextView) view.findViewById(R.id.item_invest_detail_personal_laiyuan);
			laiyuanView.setText(userInfo.getString("huankuanlaiyuan"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return view;
	}
	//车辆概况
	private View getCarView(){
		View view = inflater.inflate(R.layout.item_invest_detail_car, null);
		
		try {
			setCarBasicInfo(cacheObj.getJSONObject("userinfo"),view);
			
			JSONObject obj = cacheObj.getJSONObject("loan_info");		//为了不改代码设此无用代码 
			
			ExpandableHeightGridView gridView = (ExpandableHeightGridView) view.findViewById(R.id.item_invest_detail_car_girdview);
			
			gridView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
					
					TemporaryInfo temporaryInfo = temporaryLists.get(position);
					
					Intent intent = new Intent(InvestDetailActivity.this, BigImageAc.class);
					
					intent.putExtra("url", temporaryInfo.getUrl());
					intent.putExtra("name", temporaryInfo.getName());
					
					startActivity(intent);
				}
			});
			
			if (null==carAdapter) {
				setGridViewAdapter();
			}
			
			gridView.setAdapter(carAdapter);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		return view;
	}
	//填充车辆基本信息
	private void setCarBasicInfo(JSONObject obj,View view){
		TextView brandView = (TextView) view.findViewById(R.id.item_invest_detail_car_brand);
		TextView nameView = (TextView) view.findViewById(R.id.item_invest_detail_car_model);
		TextView courseView = (TextView) view.findViewById(R.id.item_invest_detail_car_licheng);
		TextView yearView = (TextView) view.findViewById(R.id.item_invest_detail_car_nianxian);
		TextView priceView = (TextView) view.findViewById(R.id.item_invest_detail_car_jiage);
		TextView newcar_priceView = (TextView) view.findViewById(R.id.item_invest_detail_car_xin);
		TextView changeView = (TextView) view.findViewById(R.id.item_invest_detail_car_guohu);
		TextView fixView = (TextView) view.findViewById(R.id.item_invest_detail_car_jilu);
		TextView accidentView = (TextView) view.findViewById(R.id.item_invest_detail_car_shigu);
		
		try {
			brandView.setText(obj.getString("cheliangpinpai"));
			nameView.setText(obj.getString("cheliangxinghao"));
			courseView.setText(obj.getString("xingshilicheng")+"公里");
			yearView.setText(obj.getString("cheliangnianxian")+"年");
			priceView.setText(obj.getString("pinggujiage")+"万元");
			newcar_priceView.setText(obj.getString("xinchejiage")+"万元");
			changeView.setText(obj.getString("guohucishu")+"次");
			fixView.setText(obj.getString("daxiujilu"));
			accidentView.setText(obj.getString("jiaotongshigu"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void setGridViewAdapter(){
		List<CarPictureInfo> pirLists = new ArrayList<CarPictureInfo>();
		
		try {
			JSONArray array = cacheObj.getJSONArray("material");
			
			for (int i = 0, j = array.length(); i < j; i++) {
				ArrayList<PictureInfo> lists = new ArrayList<PictureInfo>();
				CarPictureInfo info = new CarPictureInfo();
				JSONObject jb = array.getJSONObject(i);
				info.setName(jb.getString("code_name"));
				info.setCode(jb.getString("code"));
				JSONArray list = jb.getJSONArray("list");
				for (int k = 0, l = list.length(); k < l; k++) {
					PictureInfo pInfo = new PictureInfo();
					JSONObject jobj = list.getJSONObject(k);
					pInfo.setPath(jobj.getString("path"));
					pInfo.setSmallpath(jobj.getString("smallpath"));
					pInfo.setTitle(jobj.getString("title"));
					lists.add(pInfo);
				}
				info.setPicLists(lists);
				
				pirLists.add(info);
			}
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		
		for (int i = 0, j = pirLists.size(); i < j; i++) {
			CarPictureInfo carPictureInfo = pirLists.get(i);
			ArrayList<PictureInfo> picList = carPictureInfo.getPicLists();
			for (int n = 0, m = picList.size(); n < m; n++) {
				TemporaryInfo info = new TemporaryInfo();
				info.setUrl(picList.get(n).getPath());
				info.setSmallUrl(picList.get(n).getSmallpath());
				info.setName(carPictureInfo.getName());
				temporaryLists.add(info);
			}
		}
		
		FinalBitmap fb = FinalBitmap.create(this);
		
		carAdapter = new GirdAdapter(this, temporaryLists, fb);
	}
	
	//投资记录
	private View getTouziView(){
		View view = inflater.inflate(R.layout.item_invest_detail_record, null);
		MyListView listView = (MyListView) view.findViewById(R.id.item_invest_detail_record_lv);
		
		View headView = inflater.inflate(R.layout.item_item_invest_detail_record_header, null);
		listView.addHeaderView(headView);
		
		if (null!=detailTzjlAdapter) {
			listView.setAdapter(detailTzjlAdapter);
			
			return view;
		}
		
		try {
			JSONArray tender = cacheObj.getJSONArray("tender");
			
			List<DetailTzjlInfo> tzjlList = new ArrayList<DetailTzjlInfo>();
			
			for (int i = 0,j=tender.length(); i <j; i++) {
				JSONObject item = tender.getJSONObject(i);
				
				DetailTzjlInfo info = new DetailTzjlInfo();
				
				info.setMoney(item.getString("money"));
				info.setUsername(item.getString("username"));
				info.setType(item.getString("type"));
				info.setTimeadd(item.getString("timeadd"));
				info.setIs_auto(item.getString("is_auto"));
				info.setIntegral(item.getString("integral"));
				
				tzjlList.add(info);
			}
			
			listView.setAdapter(detailTzjlAdapter = new DetailTzjlAdapter(this, tzjlList));
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		return view;
	}
	
	//还款方式
	private View getPayType(){
		View view = inflater.inflate(R.layout.item_invest_detail_record, null);
		MyListView listView = (MyListView) view.findViewById(R.id.item_invest_detail_record_lv);
		
		View headView = inflater.inflate(R.layout.item_item_invest_detail_repay_header, null);
		listView.addHeaderView(headView);
		
		if (null!=payAdapter) {
			listView.setAdapter(payAdapter);
			
			return view;
		}
		
		List<DetailPayTypeInfo> payList = new ArrayList<DetailPayTypeInfo>();
		
		try {
			JSONArray issue = cacheObj.getJSONArray("issue");
			
			for (int i = 0,j=issue.length(); i < j; i++) {
				JSONObject item = issue.getJSONObject(i);
				
				DetailPayTypeInfo info = new DetailPayTypeInfo();
				info.setMoney(item.getString("money"));
				info.setEndtime(item.getString("endtime"));
				info.setIsrepayment(item.getInt("isrepayment"));
				info.setResiduemoney(item.getString("residuemoney"));
				
				payList.add(info);
			}
			
			listView.setAdapter(payAdapter = new DetailPayAdapter(this, payList));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		return view;
	}
	
	
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.info:	//借款信息
			Intent infoIntent = new Intent(this, InfoDetailAc.class);
			infoIntent.putExtra("remark", remark);
			startActivity(infoIntent);
			break;	
		case R.id.car:		//车辆概况
			Intent carIntent = new Intent(this, CarDetailAc.class);
			carIntent.putExtra("sn", sn);
			startActivity(carIntent);
			break;
		case R.id.record:	//投资记录
			Intent recordIntent = new Intent(this, RecordDetailAc.class);
			recordIntent.putExtra("sn", sn);
			startActivity(recordIntent);
			break;
		case R.id.repay:	//还款方式
			Intent repayIntent = new Intent(this, RepayAc.class);
			repayIntent.putExtra("sn", sn);
			startActivity(repayIntent);
			break;
			
		case R.id.submit:
			if (TextUtils.isEmpty(MyApp.token)) {
				startActivity(new Intent(InvestDetailActivity.this,LoginActivity.class));
			} else {
				
				ketoujine = Float.parseFloat(loanmoney.replace(",", ""))- Float.parseFloat(tendermoney.replace(",", ""));
				
				Intent intentqddtz = new Intent(InvestDetailActivity.this,InvestNumAc.class);
				
				Bundle bundleqddtz = new Bundle();
				bundleqddtz.putString("sn", sn);
				bundleqddtz.putString("mintendermoney", mintendermoney);
				bundleqddtz.putString("yearrate", yearrate);
				bundleqddtz.putString("loanmonth", loanmonth);
				bundleqddtz.putString("repayment", repayment);
				bundleqddtz.putFloat("ketoujine", ketoujine);
				
				intentqddtz.putExtras(bundleqddtz);
				
				startActivity(intentqddtz);
			}
			break;

		default:
			break;
		}
	}

	private void getInvestDetail() {
		if (NetUtil.isNetworkConnected(this)) {
			Map<String, Object> params = new HashMap<String, Object>();
			
			params.put("sn", sn);
			params.put("page_type", "");
			
			ServiceClient.call(Consts.NetWork.LOANDETAIL, params, qian_detail);
		} else {
			Toast.makeText(this, Consts.NONETWORK, 1).show();
		}
	}

	ServiceClient.Callback qian_detail = new ServiceClient.Callback() {

		@Override
		public void callback(JSONObject data) {
			// System.out.println("投资列表详情   :" + data);
			
			try {
				if (data.getInt("errorcode") == 0) {
					cacheObj = data.getJSONObject("dataresult");
					
					JSONObject jsonObject = data.getJSONObject("dataresult");
					JSONObject obj = jsonObject.getJSONObject("loan_info");
					remark = obj.getString("remark");

					mintendermoney = obj.getString("mintendermoney");
					yearrate = obj.getString("yearrate");
					loanmonth = obj.getString("loanmonth");
					repayment = obj.getString("repayment");
					loanmoney = obj.getString("loanmoney");
					tendermoney = obj.getString("tendermoney");

					String labelStr = obj.getString("title");
					int index = labelStr.indexOf("】");
					
					tvType.setText(labelStr.substring(0, index+1));
					
					title.setText(labelStr.substring(index+1, labelStr.length()));
					tvSn.setText("（编号：" + obj.getString("loansn")+"）");
					
					tvLoanmonth.setText(loanmonth + "个月");
					tvLoanmoney.setText("￥ " + loanmoney);
					tvRepayment.setText(obj.getString("repayment"));
					tvYearrate.setText(yearrate + "％");
					
					radio = Integer.parseInt(obj.getString("ratio"));
					
					if (radio==100) {
						bar.setProgressDrawable(getResources().getDrawable(R.drawable.self_define_progress_complete));
					}else {
						bar.setProgressDrawable(getResources().getDrawable(R.drawable.self_define_progress_uncomplete));
					}
					bar.setProgress(radio);
					jindu.setText("完成"+radio+"%");
					
					//timeView,remainJine,has_jine,zuidi_jine;
					setTime(obj.getString("loanstatus"),obj.getString("lssuingtime"));
					
					remainJine.setText(tendermoney+"元");
					has_jine.setText(obj.getString("need_amount")+"元");
					zuidi_jine.setText(mintendermoney);
					
					
					radioGroup.check(R.id.invest_detail_tab_loan);
				}
			} catch (Exception e) {
				e.printStackTrace();
				try {
					String str = data.getString("errormsg");
					Toast.makeText(InvestDetailActivity.this, str, 1).show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					
					Toast.makeText(InvestDetailActivity.this,Consts.UNKNOWN_FAULT, 1).show();
				}
			}
			
			loading.setVisibility(View.GONE);
		}
	};
	
	
	static Handler timeHandler = new Handler();  
	
	static Runnable timeRunnable = null;
	
	//设置剩余时间
	private void setTime(String loanstatus,String time){
		if (loanstatus.equals("1")) {
			stateView.setText("剩余时间");
			
			final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			try {
				final Date date1 = df.parse(time);
				
				timeRunnable=new Runnable() {
					
					@Override
					public void run() {
						
						try {
							Date date2 = df.parse(df.format(new Date()));
							
							long cha = date1.getTime() - date2.getTime();
							
							timeView.setText(parseTime(cha));  
						} catch (ParseException e) {
							e.printStackTrace();
						}
				            
						timeHandler.postDelayed(timeRunnable, 1000);  
					}
				};
				
				timeHandler.post(timeRunnable); 
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
		}else {
			String temp = "";
			
			stateView.setText("当前状态");
			
			if (loanstatus.equals("2")) {
				temp = "已流标";
			}else if (loanstatus.equals("3")) {
				temp = "已满标";
			}else if (loanstatus.equals("4")) {
				temp = "还款中";
			}else {
				temp = "还款完成";
			}
			
			timeView.setText(temp);
		}
	}
	
	/** 根据时间差计算时间 */
	private String parseTime(long cha){
		
		String tempString = "";
		
		if (cha<0) {
			cha = 0-cha;
		}
		
		if(cha==0){
			tempString = "0小时0分0秒";
		}else {
			long day=cha/(24*60*60*1000) >0? cha/(24*60*60*1000):0;   
			
			long hour=(cha/(60*60*1000)-day*24)>0?(cha/(60*60*1000)-day*24):0;   
			
			long min=((cha/(60*1000))-day*24*60-hour*60);  
			
			long s=(cha/1000-day*24*60*60-hour*60*60-min*60); 
			
			if (day>0) {
				tempString = ""+day+"天"+hour+"小时"+min+"分"+s+"秒";
			}else {
				tempString = hour+"小时"+min+"分"+s+"秒";
			}
			
			// Log.i("时间差2", ""+day+"天"+hour+"小时"+min+"分"+s+"秒");
		}
		
		return tempString;
	}

	@Override
	public void linster(Intent in) {
		// System.out.println("你好");
		sn = in.getExtras().getString("sn");
		dosomething();
	}

	private void dosomething() {
		if (NetUtil.isNetworkConnected(this)) {
			getInvestDetail();
		} else {
			Toast.makeText(InvestDetailActivity.this, Consts.NONETWORK, 1).show();
		}
	}
	
	//计算按钮触发
	public void calculateHandler(View v){
		String type = repayment.equals("付息还本")? "2":"1";
		
		if (null==popup) {
			popup = new DetailJisuanPopup(this,type,yearrate);
		}
		
		popup.showAtLocation(mView, Gravity.CENTER, 0, 0);
	}

	public void userHandler(View v) {
		startActivity(new Intent(this,LoginActivity.class));
	}
	
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		// Log.i("onDestroy", "onDestroy");
		
		timeHandler.removeCallbacks(timeRunnable);
	}

	public void backHandler(View v) {
		finish();
	}

}
