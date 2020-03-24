package com.example.calander;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class main_splash extends AppCompatActivity {
    private static int SPLASH_SCREEN_TIME_OUT=1400;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_splash);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(main_splash.this,
                        MainActivity.class);
                startActivity(i);
                finish();
                }
        }, SPLASH_SCREEN_TIME_OUT);


    }

}
