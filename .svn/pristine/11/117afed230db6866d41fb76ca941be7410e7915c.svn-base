package com.famous.zhifu.loan.famouscarloan.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.GridView;
import android.widget.TextView;

import com.famous.zhifu.loan.R;
import com.famous.zhifu.loan.activity.ActivityInterface;
import com.famous.zhifu.loan.adapter.LoanGridAdapter;

public class LoanMoneyConfirmActivity extends Activity implements ActivityInterface{
    private View mView;
    private TextView mTvTitle;
    private GridView mGV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        mView = getLayoutInflater().inflate(R.layout.loan_money_confirm,null);
        setContentView(mView);
        findView();
        initData();
        addAction();
    }

    @Override
    public void findView() {
        mTvTitle = (TextView)mView.findViewById(R.id.main_header_tv_text);
        mGV = (GridView)mView.findViewById(R.id.loan_money_confirm_gv);
    }

    @Override
    public void initData() {
        mTvTitle.setText(getResources().getString(R.string.loan));
        String[] strings = new String[]{getString(R.string.id_card),getString(R.string.car_card),getString(R.string.driver_card),getString(R.string.car_list),getString(R.string.credit_report),getString(R.string.income_point)};
        int[] drawables = new int[]{R.drawable.material_idcard,R.drawable.material_cardocument,R.drawable.material_drivingpermit,R.drawable.material_insurance,R.drawable.material_creditreport,
                R.drawable.material_income};
        mGV.setAdapter(new LoanGridAdapter(getApplication(),strings,drawables));
    }

    @Override
    public void addAction() {
    	
    }
    
	public void backHandler(View v) {
		finish();
	}
}
