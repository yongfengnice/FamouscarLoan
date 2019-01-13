package com.famous.zhifu.loan.famouscarloan.investlist;

import java.util.ArrayList;
import java.util.HashMap;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.activity.Consts;
import com.famous.zhifu.loan.adapter.GirdAdapter;
import com.famous.zhifu.loan.entity.CarPictureInfo;
import com.famous.zhifu.loan.entity.PictureInfo;
import com.famous.zhifu.loan.entity.TemporaryInfo;
import com.famous.zhifu.loan.internet.ServiceClient;
import com.famous.zhifu.loan.util.NetUtil;

public class CarDetailAc extends Activity implements OnClickListener {
	private String sn;
	private TextView car1, car2, car3, car4, car5, car6, car7, car8, car9;
	private ArrayList<CarPictureInfo> pirLists = new ArrayList<CarPictureInfo>();
	private ArrayList<TemporaryInfo> temporaryLists = new ArrayList<TemporaryInfo>();
	private GridView gridview;
	private GirdAdapter adapter;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
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
			adapter.notifyDataSetChanged();
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.car_detail);
		initView();
		getCarDetail();
	}

	public void initView() {
		sn = getIntent().getStringExtra("sn");

		TextView title = (TextView) findViewById(R.id.main_header_tv_text);
		title.setText("车辆信息");
		ImageView ivLeft = (ImageView) findViewById(R.id.main_header_iv_left);
		ivLeft.setOnClickListener(this);
		ImageView ivRight = (ImageView) findViewById(R.id.main_header_iv_right);
		ivRight.setVisibility(View.GONE);
		car1 = (TextView) findViewById(R.id.car1);
		car2 = (TextView) findViewById(R.id.car2);
		car3 = (TextView) findViewById(R.id.car3);
		car4 = (TextView) findViewById(R.id.car4);
		car5 = (TextView) findViewById(R.id.car5);
		car6 = (TextView) findViewById(R.id.car6);
		car7 = (TextView) findViewById(R.id.car7);
		car8 = (TextView) findViewById(R.id.car8);
		car9 = (TextView) findViewById(R.id.car9);

		FinalBitmap fb = FinalBitmap.create(this);
		gridview = (GridView) findViewById(R.id.girdview);
		adapter = new GirdAdapter(this, temporaryLists, fb);
		gridview.setAdapter(adapter);
		
		gridview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				TemporaryInfo temporaryInfo = temporaryLists.get(position);
				Intent intent = new Intent(CarDetailAc.this, BigImageAc.class);
				intent.putExtra("url", temporaryInfo.getUrl());
				intent.putExtra("name", temporaryInfo.getName());
				startActivity(intent);
			}
		});
	}

	private void getCarDetail() {
		if (NetUtil.isNetworkConnected(this)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("sn", sn);
			params.put("page_type", "carinfo");
			ServiceClient
					.call(Consts.NetWork.LOANDETAIL, params, qian_detail);
		} else {
			Toast.makeText(this, Consts.NONETWORK, 1).show();
		}
	}

	ServiceClient.Callback qian_detail = new ServiceClient.Callback() {

		@Override
		public void callback(JSONObject data) {
			// System.out.println("车辆详情   :" + data);
			try {
				if (data.getInt("errorcode") == 0) {
					JSONObject jsonObject = data.getJSONObject("dataresult");
					JSONObject obj = jsonObject.getJSONObject("carinfo");
					car1.setText(obj.getString("brand"));
					car2.setText(obj.getString("name"));
					car3.setText(obj.getString("course"));
					car4.setText(obj.getString("year"));
					car5.setText(obj.getString("price"));
					car6.setText(obj.getString("newcar_price"));
					car7.setText(obj.getString("change"));
					car8.setText(obj.getString("fix"));
					car9.setText(obj.getString("accident"));

					JSONArray array = jsonObject.getJSONArray("material");
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
					handler.sendEmptyMessage(0);
				}
			} catch (Exception e) {
				e.printStackTrace();
				try {
					String str = data.getString("errormsg");
					Toast.makeText(CarDetailAc.this, str, 1).show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					Toast.makeText(CarDetailAc.this, Consts.UNKNOWN_FAULT, 1)
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
