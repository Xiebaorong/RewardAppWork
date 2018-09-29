package com.example.a7invensun.rewardapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

import com.example.a7invensun.rewardapp.BaseActivity;
import com.example.a7invensun.rewardapp.callback.INetEvent;
import com.example.a7invensun.rewardapp.util.NetUtil;

/**
 * Created by 7invensun on 2018/9/29.
 */

public class NetStateReceiver extends BroadcastReceiver {
    private static final String TAG = "NetStateReceiver";
    private INetEvent mINetEvent= BaseActivity.iNetEvent;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
           if (mINetEvent!=null){
               int networkConnected = NetUtil.isNetworkConnected(context);
               mINetEvent.onNetChange(networkConnected);
           }

        }
    }
}
