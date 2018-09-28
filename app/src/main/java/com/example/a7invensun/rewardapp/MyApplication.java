package com.example.a7invensun.rewardapp;

import android.app.Application;
import android.content.Context;

import com.example.a7invensun.rewardapp.util.SharedPreferencesUtil;

/**
 * Created by 7invensun on 2018/9/11.
 */

public class MyApplication extends Application {
    private static Context mContext;

    public static Context getInstance(){
        return mContext;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        SharedPreferencesUtil.getInstance().initSp(mContext);
    }
}
