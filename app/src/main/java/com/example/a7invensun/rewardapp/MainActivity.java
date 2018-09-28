package com.example.a7invensun.rewardapp;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.a7invensun.rewardapp.util.SharedPreferencesUtil;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private EditText wordEdit;
    private ClipboardManager manager;
    private static final String PACKANG_NAME = "com.eg.android.AlipayGphone";
    private RadioGroup mSexRg;
    private RadioButton xieRb,heRb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        wordEdit = findViewById(R.id.word_edit);
        mSexRg = findViewById(R.id.sex_rg);
        xieRb =  this.findViewById(R.id.xie_rb);
        heRb =  this.findViewById(R.id.he_rb);
        //获取剪贴板管理器：
        manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        String word = SharedPreferencesUtil.getInstance().getOtherMessage("word");
        String clickId = SharedPreferencesUtil.getInstance().getOtherMessage("clickId");
        if (word != null){
            wordEdit.setText(word);
        }else {
            Toast.makeText(this, "未找到口令,请输入", Toast.LENGTH_SHORT).show();
        }

        if (clickId.equals("1")){
            xieRb.setChecked(true);
        }else if (clickId.equals("2")){
            heRb.setChecked(true);
        }
    }

    public void startAlipayClick(View view) {
        startAlipay();
    }

    private void startAlipay() {
        String word = wordEdit.getText().toString();
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("", word);
        manager.setPrimaryClip(mClipData);

        boolean isInstall = isAppInstall(this);
        Log.e(TAG, "startAlipay: " + isInstall);
        if (isInstall) {
            openApp(PACKANG_NAME);
        } else {
            Toast.makeText(this, "请安装支付宝", Toast.LENGTH_SHORT).show();
        }
    }

    private void openApp(String packageName) {
        try {
            PackageInfo pi = getPackageManager().getPackageInfo(packageName, 0);
            Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
            resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            resolveIntent.setPackage(pi.packageName);

            PackageManager pm = this.getPackageManager();
            List<ResolveInfo> apps = pm.queryIntentActivities(resolveIntent, 0);
            ResolveInfo ri = apps.iterator().next();
            if (ri != null) {
                String newPackageName = ri.activityInfo.packageName;
                String className = ri.activityInfo.name;
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);//重点是加这个
                Log.e(TAG, "openApp=== newPackageName: " + newPackageName + "----className: " + className);
                ComponentName cn = new ComponentName(newPackageName, className);
                intent.setComponent(cn);
                startActivity(intent);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean isAppInstall(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals(PACKANG_NAME)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void saveWordClick(View view) {
        String word = wordEdit.getText().toString();
        SharedPreferencesUtil.getInstance().saveOtherMessage("word",word);
    }

    public void resetClick(View view) {
        SharedPreferencesUtil.getInstance().saveOtherMessage("word",this.getString(R.string.word_xie));
        SharedPreferencesUtil.getInstance().saveOtherMessage("clickId","1");
        wordEdit.setText(R.string.word_xie);
        xieRb.setChecked(true);
    }

    public void confirmClick(View view) {
        int sex = mSexRg.getCheckedRadioButtonId();
        if (sex == R.id.xie_rb){
            SharedPreferencesUtil.getInstance().saveOtherMessage("word",this.getString(R.string.word_xie));
            SharedPreferencesUtil.getInstance().saveOtherMessage("clickId","1");
            wordEdit.setText(R.string.word_xie);

            Toast.makeText(MainActivity.this,"谢@",Toast.LENGTH_SHORT).show();
        }
        if (sex == R.id.he_rb){
            SharedPreferencesUtil.getInstance().saveOtherMessage("word",this.getString(R.string.word_he));
            SharedPreferencesUtil.getInstance().saveOtherMessage("clickId","2");
            wordEdit.setText(R.string.word_he);
            Toast.makeText(MainActivity.this,"已选贺瑶",Toast.LENGTH_SHORT).show();
        }
    }

}
