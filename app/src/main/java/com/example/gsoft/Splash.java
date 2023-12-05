package com.example.gsoft;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Splash extends Activity {

    protected int _splashTime = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        StartAnimations();
    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        RelativeLayout l=findViewById(R.id.RE_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();

        TextView tv=findViewById(R.id.splash);

        tv.clearAnimation();
        tv.startAnimation(anim);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                startActivity(new Intent(Splash.this, MainActivity.class));
                finish();
            }
        }, _splashTime);
    }
}