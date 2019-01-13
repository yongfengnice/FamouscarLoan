package com.famous.zhifu.loan.famouscarloan.account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.activity.ActivityInterface;
import com.famous.zhifu.loan.activity.Consts;
import com.famous.zhifu.loan.adapter.ProblemAdapter;
import com.famous.zhifu.loan.entity.ProblemInfo;
import com.famous.zhifu.loan.internet.ServiceClient;
import com.famous.zhifu.loan.util.NetUtil;

public class UsuallyProblemActivity extends Activity implements
		ActivityInterface {
	private View mView;
	private TextView mTvTitle;
	private ListView mLv;
	private ProblemAdapter adpater;
	private ArrayList<ProblemInfo> lists = new ArrayList<ProblemInfo>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		mView = getLayoutInflater().inflate(R.layout.activity_usually_problem,null);
		setContentView(mView);
		findView();
		initData();
		addAction();
		requestData();
	}

	@Override
	public void findView() {
		mTvTitle = (TextView) mView.findViewById(R.id.main_header_tv_text);
		mLv = (ListView) mView.findViewById(R.id.usually_problem_lv);
	}

	@Override
	public void initData() {
		mTvTitle.setText(getResources().getString(R.string.usually_problem));

		adpater = new ProblemAdapter(this, lists);
		mLv.setAdapter(adpater);
	}

	@Override
	public void addAction() {
		
		mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ProblemInfo problemInfo = lists.get(position);
				
				Intent intent = new Intent(UsuallyProblemActivity.this,
						UsuallyProblemDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("problem", problemInfo);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}

	public void requestData() {
		if (NetUtil.isNetworkConnected(this)) {
			Map<String, Object> params = new HashMap<String, Object>();
			ServiceClient.call(Consts.NetWork.PROBLEM, params, callback);
		} else {
			Toast.makeText(this, Consts.NONETWORK, 1).show();
		}
	}

	ServiceClient.Callback callback = new ServiceClient.Callback() {
		@Override
		public void callback(JSONObject data) {
			// System.out.println("常见问题  " + data);
			try {
				if (data.getInt("errorcode") == 0) {
					JSONArray array = (JSONArray) data.get("dataresult");
					for (int i = 0, j = array.length(); i < j; i++) {
						ProblemInfo info = new ProblemInfo();
						JSONObject obj = array.getJSONObject(i);
						info.setContent(obj.getString("content"));
						info.setTitle(obj.getString("title"));
						lists.add(info);
					}
					adpater.notifyDataSetChanged();
				}
			} catch (JSONException e) {
				e.printStackTrace();
				try {
					String str = data.getString("errormsg");
					Toast.makeText(UsuallyProblemActivity.this, str, 1).show();
				} catch (JSONException ee) {
					ee.printStackTrace();
					Toast.makeText(UsuallyProblemActivity.this,
							Consts.UNKNOWN_FAULT, 1).show();
				}
			}
		}
	};
	
	public void backHandler(View v){
		finish();
	}

}
