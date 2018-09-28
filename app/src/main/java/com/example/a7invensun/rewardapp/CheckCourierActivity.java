package com.example.a7invensun.rewardapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.a7invensun.rewardapp.util.CompanyCodeUtil;

import butterknife.BindView;

public class CheckCourierActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "CheckCourierActivity";
    @BindView(R.id.snr_company_checkcourier)
    Spinner snrCompanyCheckcourier;
    @BindView(R.id.et_couriernumber_checkcourier)
    EditText etCouriernumberCheckcourierl;
    @Override
    public int getLayout() {
        return R.layout.activity_check_courier;
    }

    @Override
    protected void OnActCreate(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        snrCompanyCheckcourier.setOnItemSelectedListener(this);
    }

    @Override
    protected int switchoverAnimationIn() {
        return RIGHT;
    }

    @Override
    protected int switchoverAnimationOut() {
        return LEFT;
    }

    public void findCourierClick(View view) {

        String trim = etCouriernumberCheckcourierl.getText().toString().trim();
        if (!trim.isEmpty()&&cardNumber!=null){
            String deliveryCompanyNo = CompanyCodeUtil.getDeliveryCompanyNo(cardNumber);
            Log.e(TAG, "onItemClick: "+deliveryCompanyNo );


        }
    }



    String cardNumber;
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        cardNumber = getResources().getStringArray(R.array.delivery_company)[position];

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
