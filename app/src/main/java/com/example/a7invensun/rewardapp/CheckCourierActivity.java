package com.example.a7invensun.rewardapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.a7invensun.rewardapp.adapter.ShowMsgAdapter;
import com.example.a7invensun.rewardapp.callback.LoadCallBack;
import com.example.a7invensun.rewardapp.fragment.ScanFragment;
import com.example.a7invensun.rewardapp.model.DeliveryMessages;
import com.example.a7invensun.rewardapp.util.AppUrl;
import com.example.a7invensun.rewardapp.util.CompanyCodeUtil;
import com.example.a7invensun.rewardapp.util.GsonUtil;
import com.example.a7invensun.rewardapp.util.OkhttpUtil;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

import static com.example.a7invensun.rewardapp.util.NetUtil.NETWORK_NONE;

public class CheckCourierActivity extends BaseActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, View.OnFocusChangeListener {
    private static final String TAG = "CheckCourierActivity";
    @BindView(R.id.snr_company_checkcourier)
    Spinner snrCompanyCheckcourier;
    @BindView(R.id.et_couriernumber_checkcourier)
    EditText etCouriernumberCheckcourierl;
    @BindView(R.id.lv_showmessage_checkcourier)
    ListView lvShowmessageCheckcourier;
    @BindView(R.id.tv_hint_prompts)
    TextView tvHintPrompts;
    @BindView(R.id.rl_shownumber_checkcourier)
    RelativeLayout rlShownumberCheckcourier;
    @BindView(R.id.fl_getfragment_checkcourier)
    FrameLayout flGetfragmentCheckcourier;
    private List<DeliveryMessages.Message> dataList;
    private int netState;
    private boolean isScan;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private Fragment scanFragment;

    @Override
    protected void onNetChanged(int netWorkState) {
        if (netWorkState == NETWORK_NONE) {
            tvHintPrompts.setVisibility(View.VISIBLE);
        } else {
            tvHintPrompts.setVisibility(View.GONE);
        }
    }

    @Override
    public int getLayout() {
        return R.layout.activity_check_courier;
    }

    @Override
    protected void OnActCreate(Bundle savedInstanceState) {
    }

    @Override
    protected void initView() {
        tvLeftHeader.setText("返回");
        tvCenterHeader.setText("快递查询");
        tvRightHeader.setText("使用扫码");
        tvLeftHeader.setOnClickListener(this);
        tvRightHeader.setOnClickListener(this);
        snrCompanyCheckcourier.setOnItemSelectedListener(this);
        etCouriernumberCheckcourierl.setOnFocusChangeListener(this);

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
        String num = etCouriernumberCheckcourierl.getText().toString().trim();
        if (!num.isEmpty() && cardNumber != null) {
            if (dataList != null) {
                dataList.clear();
            }
            String deliveryCompanyNo = CompanyCodeUtil.getDeliveryCompanyNo(cardNumber);
            Log.e(TAG, "onItemClick: " + deliveryCompanyNo);
            showProgressDialog("正在查询中。。。");
            if (netState != NETWORK_NONE) {
                Map<String, String> params = new HashMap<>();
                params.put("type", deliveryCompanyNo);
                params.put("postid", num);
                OkhttpUtil.getInstance().newRequest(AppUrl.FINDURL, OkhttpUtil.HttpMethodType.POST, params, new LoadCallBack<String>(CheckCourierActivity.this) {
                    @Override
                    public void onSuccess(Call call, Response response, String s) {
                        Log.e(TAG, "onSuccess: " + response.code() + "------" + s);
                        if (response.code() == 200) {
                            disMissDialog();
                            Type type = new TypeToken<DeliveryMessages>() {
                            }.getType();
                            DeliveryMessages deliveryMessages = (DeliveryMessages) GsonUtil.jsontoBean(s, type);
                            if (!("ok").equals(deliveryMessages.getMessage())) {
                                sendToast(deliveryMessages.getMessage());
                            } else {
                                dataList = deliveryMessages.getData();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ShowMsgAdapter adapter = new ShowMsgAdapter(CheckCourierActivity.this, dataList);
                                        lvShowmessageCheckcourier.setAdapter(adapter);

                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onError(Call call, int statusCode, Exception e) {
                        Log.e(TAG, "onError: " + e.getMessage());
                    }
                });

            }
        } else {
            sendToast("请输入快递单号");
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left_header:
                finish();
                break;
            case R.id.tv_right_header:
                manager = getSupportFragmentManager();
                transaction = manager.beginTransaction();
                hideFragment(transaction);
                if (isScan) {
                    isScan = false;
                    tvRightHeader.setText("使用扫码 ");
                    rlShownumberCheckcourier.setVisibility(View.VISIBLE);
                    flGetfragmentCheckcourier.setVisibility(View.GONE);
                } else {
                    isScan = true;
                    tvRightHeader.setText("输入单号");
                    rlShownumberCheckcourier.setVisibility(View.GONE);
                    flGetfragmentCheckcourier.setVisibility(View.VISIBLE);
                    scanFragment = new ScanFragment();
                    transaction.replace(R.id.fl_getfragment_checkcourier, scanFragment);
                    transaction.commit();
                }
                break;
        }
    }

    /**
     * 移除Fragment
     *
     * @param transaction
     */
    private void hideFragment(FragmentTransaction transaction) {
        if (scanFragment != null) {
            //transaction.hide(f2);
            transaction.remove(scanFragment);
            scanFragment = null;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        Log.e(TAG, "onFocusChange: " + hasFocus);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(netStateReceiver);
    }
}
