package com.example.a7invensun.rewardapp.callback;

import android.content.Context;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public abstract class LoadCallBack<T> extends BaseCallBack<T> {
    private Context mContext;

    public LoadCallBack(Context context) {
        this.mContext = context;
    }

    @Override
    public void OnRequestBefore(Request request) {

    }

    @Override
    public void onFailure(Call call,IOException e) {

    }

    @Override
    public void onResponse(Response response) {

    }

    @Override
    public void inProgress(int progress, long total, int id) {

    }
}
