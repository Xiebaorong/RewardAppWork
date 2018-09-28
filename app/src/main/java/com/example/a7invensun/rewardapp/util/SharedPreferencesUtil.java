package com.example.a7invensun.rewardapp.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.a7invensun.rewardapp.R;

/**
 * Created by 7invensun on 2018/9/11.
 */

public class SharedPreferencesUtil {
    private static SharedPreferencesUtil instance = null;
    private Context mContext;
    private SharedPreferences sp;
    private SharedPreferences.Editor edit;

    public static SharedPreferencesUtil getInstance() {
        if (instance == null) {
            synchronized (SharedPreferencesUtil.class) {
                if (instance == null) {
                    instance = new SharedPreferencesUtil();

                }
            }
        }
        return instance;
    }

    public void initSp(Context context) {
        mContext = context;
        sp = mContext.getSharedPreferences("SaveWord", Context.MODE_PRIVATE);
        boolean isFirstRun = sp.getBoolean("isF", true);
        if (isFirstRun) {
            SharedPreferences.Editor edit1 = sp.edit();
            edit1 = sp.edit();
            edit1.putString("word", context.getString(R.string.word_xie));
            edit1.putString("clickId", "1");
            edit1.putBoolean("isF", false);
            edit1.commit();
        }
    }

    public void saveOtherMessage( String key,String value) {
        edit = sp.edit();
        this.edit.putString(key, value);
        this.edit.commit();
    }

    public String getOtherMessage(String key) {
        return sp.getString(key, "");
    }

}
