package com.example.project3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

//앱 실행시 나오는 초기 로딩화면
public class LoadingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        ImageView imageView = (ImageView)findViewById(R.id.imageview); //로딩화면에 나오는 이미지
        imageView.setImageResource(R.drawable.booty);
        startLoading();
    }

    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), login.class); //로딩화면 후 로그인 화면으로 넘어간다
                startActivity(intent);
                finish();
            }
        }, 2000);
    }

}