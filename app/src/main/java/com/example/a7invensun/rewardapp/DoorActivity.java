package com.example.a7invensun.rewardapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;

public class DoorActivity extends BaseActivity {
//    @BindView(R.id.tv_left_header)
//    TextView tvLeftHeader;
//    @BindView(R.id.tv_center_header)
//    TextView tvCenterHeader;
//    @BindView(R.id.tv_right_header)
//    TextView tvRightHeader;

    @Override
    public int getLayout() {
        return R.layout.activity_door;
    }

    @Override
    protected void OnActCreate(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        tvCenterHeader.setText("导航页");
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
