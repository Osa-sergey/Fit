package com.serg.fit.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.serg.fit.R;

public class SplashActivity extends AppCompatActivity {

    private static int ANIM_TIME = 4000;
    private static int TIME_OUT = 4200;
    private LottieAnimationView animView;
    private TextView welcomeText;
    private Animation fromBottomAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        animView  = (LottieAnimationView) findViewById(R.id.logoAnim);
        welcomeText = (TextView) findViewById(R.id.welcomeText);
        fromBottomAnim = AnimationUtils.loadAnimation(this,R.anim.from_bottom);

        startAnim();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent logInIntent = new Intent(SplashActivity.this, LogInActivity.class);
                startActivity(logInIntent);
            }
        },TIME_OUT);
   }

    private void startAnim(){
        ValueAnimator animator = ValueAnimator.ofFloat(0f,1f).setDuration(ANIM_TIME);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animView.setProgress((Float)animation.getAnimatedValue());
            }
        });
        animator.start();
        welcomeText.setAnimation(fromBottomAnim);
    }
}
