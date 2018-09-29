package com.example.a7invensun.rewardapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a7invensun.rewardapp.callback.INetEvent;
import com.example.a7invensun.rewardapp.receiver.NetStateReceiver;
import com.example.a7invensun.rewardapp.util.StatusBarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.a7invensun.rewardapp.util.NetUtil.NETWORK_NONE;

public abstract class BaseActivity extends AppCompatActivity implements INetEvent{

    private static final int MSG_WHAT_SHOWTOAST = 1;
    public Context mContext;
    public static INetEvent iNetEvent;
    @BindView(R.id.tv_left_header)
    TextView tvLeftHeader;
    @BindView(R.id.tv_center_header)
    TextView tvCenterHeader;
    @BindView(R.id.tv_right_header)
    TextView tvRightHeader;

    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int TOP = 3;
    public static final int BOTTOM = 4;
    public static final int SCALE = 5;
    public static final int FADE = 6;
    private ProgressDialog progressDialog;
    private NetStateReceiver netStateReceiver;
    private Toast toast;
    private Unbinder bind;
    private Handler baseHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_WHAT_SHOWTOAST:
                    String content = msg.getData().getString("content");
                    showToast(content);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        if (getLayout() != 0) {
            setContentView(getLayout());
        }
        bind = ButterKnife.bind(this);
        iNetEvent = this;
        netStateReceiver = new NetStateReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(netStateReceiver,filter);

        OnActCreate(savedInstanceState);
        initView();

        //状态栏
        StatusBarUtils.setWindowStatusBarColor(this,R.color.colorBlack);
        if (switchoverAnimationIn() != 0) {
            activityIn(switchoverAnimationIn());
        }
    }

    @Override
    public void onNetChange(int netWorkState) {
        onNetChanged(netWorkState);
    }

    protected abstract void onNetChanged(int netWorkState);

    private void activityOut(int state) {
        switch (state) {
            case LEFT:
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                break;
            case RIGHT:
                overridePendingTransition(R.anim.right_in, R.anim.right_out);
                break;
        }
    }

    private void activityIn(int state) {
        switch (state) {
            case LEFT:
                //输入，退出  的界面
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                break;
            case RIGHT:
                overridePendingTransition(R.anim.right_in, R.anim.right_out);
                break;
        }
    }


    public abstract int getLayout();

    protected abstract void OnActCreate(Bundle savedInstanceState);

    protected abstract void initView();

    protected abstract int switchoverAnimationIn();

    protected abstract int switchoverAnimationOut();


    public void sendToast(String content) {
        Message msg = baseHandler.obtainMessage();
        msg.what = MSG_WHAT_SHOWTOAST;
        Bundle bundle = new Bundle();
        bundle.putString("content", content);
        msg.setData(bundle);
        baseHandler.sendMessage(msg);
    }

    private void showToast(String content) {
        if (toast == null) {
            toast = Toast.makeText(mContext, content, toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
        baseHandler.removeCallbacksAndMessages(MSG_WHAT_SHOWTOAST);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void showProgressDialog(String msg) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage(msg);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(true);//indeterminateDrawable="@drawable/anim"
        progressDialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.net_progress_animation,null));//R.drawable.net_progress_animation
        try {
            progressDialog.show();
        } catch (WindowManager.BadTokenException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * 取消对话框显示
     */
    public void disMissDialog() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        bind.unbind();
        unregisterReceiver(netStateReceiver);
        super.onDestroy();

    }

    @Override
    public void finish() {
        super.finish();
        if (switchoverAnimationOut()!=0){
            activityOut(switchoverAnimationOut());
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            unregisterReceiver(netStateReceiver);
           baseHandler.removeCallbacksAndMessages("");
        }
        return super.onKeyDown(keyCode, event);
    }
}
