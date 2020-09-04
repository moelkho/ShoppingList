package com.example.grocerylistapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.grocerylistapp.R;

public class SplashActivity extends AppCompatActivity {

    private ImageView logo;
    private static int splashTimeOut = 3000;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        logo = findViewById(R.id.logo);

        Animation animation = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.splashanimation);
        logo.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },splashTimeOut);



    }
}
