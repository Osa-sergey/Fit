package com.serg.fit.Activities;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.serg.fit.R;

public class SplashActivity extends AppCompatActivity {

    private static int ANIM_TIME = 4000;
    private static int TIME_OUT = 4200;
    private LottieAnimationView animView;
    private TextView welcomeText;
    private Animation fromBottomAnim;
    private SharedPreferences pref;
    private Boolean internet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        animView  = (LottieAnimationView) findViewById(R.id.logoAnim);
        welcomeText = (TextView) findViewById(R.id.welcomeText);
        fromBottomAnim = AnimationUtils.loadAnimation(this,R.anim.from_bottom);

        startAnim();
        pref = getApplicationContext().getSharedPreferences("Pref",MODE_PRIVATE);

        if(pref.getBoolean("FirstRun",true)){
            pref.edit().putBoolean("FirstRun",false).apply();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent signUpIntent = new Intent(SplashActivity.this, SignInActivity.class);
                    startActivity(signUpIntent);
                }
            },TIME_OUT);
        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent signInIntent = new Intent(SplashActivity.this, SignInActivity.class);
                    startActivity(signInIntent);
                }
            },TIME_OUT);
        }
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
