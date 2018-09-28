package com.example.a7invensun.rewardapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DoorActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door);
    }

    @Override
    public int getLayout() {
        return 0;
    }

    @Override
    protected void OnActCreate(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int switchoverAnimationIn() {
        return RIGHT;
    }

    @Override
    protected int switchoverAnimationOut() {
        return LEFT;
    }

    public void toMainClick(View view) {
        startActivity(new Intent(this,MainActivity.class));
    }

    public void toCheckCourierClick(View view) {
        startActivity(new Intent(this,CheckCourierActivity.class));
    }
}
