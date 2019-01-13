package com.famous.zhifu.loan.famouscarloan.account;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.entity.City;
import com.famous.zhifu.loan.entity.MyRegion;
import com.famous.zhifu.loan.util.CityUtils;

/**
 * 省市二级联动选择页
 * 
 */
public class CitySelectAc extends Activity implements OnClickListener {
	private static int PROVINCE = 0x00;
	private static int CITY = 0x01;

	private City city;
	private TextView[] tvs = new TextView[2];
	private int[] ids = { R.id.rb_province, R.id.rb_city };
	private int last, current;
	private TextView back;
	private ListView lv_city;
	private ArrayList<MyRegion> regions;
	private CityAdapter adapter;
	private CityUtils util;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.city_select);
		initView();
	}

	public void initView() {
		city = new City();
		Intent in = getIntent();
		city = in.getParcelableExtra("city");

		for (int i = 0; i < tvs.length; i++) {
			tvs[i] = (TextView) findViewById(ids[i]);
			tvs[i].setOnClickListener(this);
		}

		if (city == null) {
			city = new City();
			city.setProvince("");
			city.setCity("");
		} else {
			if (!TextUtils.isEmpty(city.getProvince())) {
				tvs[0].setText(city.getProvince());
			}
			if (!TextUtils.isEmpty(city.getCity())) {
				tvs[1].setText(city.getCity());
			}
		}

		back = (TextView) findViewById(R.id.back);
		back.setOnClickListener(this);

		util = new CityUtils(this, hand);
		util.initProvince();
		tvs[current].setBackgroundColor(0xff999999);
		lv_city = (ListView) findViewById(R.id.lv_city);

		regions = new ArrayList<MyRegion>();
		adapter = new CityAdapter(this);
		lv_city.setAdapter(adapter);
		lv_city.setOnItemClickListener(onItemClickListener);
	}

	OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (current == PROVINCE) {
				String newProvince = regions.get(position).getName();
				if (!newProvince.equals(city.getProvince())) {
					city.setProvince(newProvince);
					tvs[0].setText(regions.get(position).getName());
					city.setRegionId(regions.get(position).getId());
					city.setProvinceCode(regions.get(position).getId());
					city.setCityCode("");
					tvs[1].setText("市");
				}
				current = 1;
				// 点击省份列表中的省份就初始化城市列表
				util.initCities(city.getProvinceCode());

				tvs[last].setBackgroundColor(Color.WHITE);
				tvs[current].setBackgroundColor(Color.GRAY);
				last = current;

			} else if (current == CITY) {
				String newCity = regions.get(position).getName();
				if (!newCity.equals(city.getCity())) {
					city.setCity(newCity);
					tvs[1].setText(regions.get(position).getName());
					city.setRegionId(regions.get(position).getId());
					city.setCityCode(regions.get(position).getId());
				}

				Intent in = new Intent();
				in.putExtra("city", city);
				setResult(1009, in);
				finish();
			}
		}

	};

	Handler hand = new Handler() {
		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case 1:
				regions = (ArrayList<MyRegion>) msg.obj;
				adapter.clear();
				adapter.addAll(regions);
				adapter.update();
				break;

			case 2:
				regions = (ArrayList<MyRegion>) msg.obj;
				adapter.clear();
				adapter.addAll(regions);
				adapter.update();
				break;

			default:
				break;
			}
		};
	};

	/**
	 * 适配器
	 * 
	 * @author yusy
	 */
	class CityAdapter extends ArrayAdapter<MyRegion> {

		LayoutInflater inflater;

		public CityAdapter(Context con) {
			super(con, 0);
			inflater = LayoutInflater.from(CitySelectAc.this);
		}

		@Override
		public View getView(int arg0, View v, ViewGroup arg2) {
			v = inflater.inflate(R.layout.city_item, null);
			TextView tv_city = (TextView) v.findViewById(R.id.tv_city);
			tv_city.setText(getItem(arg0).getName());
			return v;
		}

		public void update() {
			this.notifyDataSetChanged();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.rb_province:
			current = 0;
			util.initProvince();
			tvs[last].setBackgroundColor(Color.WHITE);
			tvs[current].setBackgroundColor(Color.GRAY);
			last = current;
			break;
		case R.id.rb_city:
			if (TextUtils.isEmpty(city.getProvinceCode())) {
				current = 0;
				Toast.makeText(CitySelectAc.this, "您还没有选择省份",
						Toast.LENGTH_SHORT).show();
				return;
			}
			util.initCities(city.getProvinceCode());
			current = 1;
			tvs[last].setBackgroundColor(Color.WHITE);
			tvs[current].setBackgroundColor(Color.GRAY);
			last = current;
			break;
		default:
			break;
		}
	}
}
