package com.serg.fit.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.serg.fit.R;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.ExecutionException;

public class SplashActivity extends AppCompatActivity {

    private static int ANIM_TIME = 4000;
    private static int TIME_OUT = 4200;
    private LottieAnimationView animView;
    private TextView welcomeText;
    private Animation fromBottomAnim;
    private SharedPreferences pref;
    private SharedPreferences.Editor prefEdit;
    private Boolean internet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        animView  = (LottieAnimationView) findViewById(R.id.logoAnim);
        welcomeText = (TextView) findViewById(R.id.welcomeText);
        fromBottomAnim = AnimationUtils.loadAnimation(this,R.anim.from_bottom);

        startAnim();
        AsyncTask<Void,Void,Boolean> task = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    int timeoutMs = 1500;
                    Socket sock = new Socket();
                    SocketAddress sockaddr = new InetSocketAddress("8.8.8.8", 53);

                    sock.connect(sockaddr, timeoutMs);
                    sock.close();

                    return true;
                } catch (IOException e) { return false; }
            }
        };
        task.execute();
        try{
          internet = task.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("InternetConnection",e.toString());
        }
        pref = getApplicationContext().getSharedPreferences("Pref",MODE_PRIVATE);

        if(pref.getBoolean("FirstRun",true)){
            prefEdit = pref.edit();
            prefEdit.putBoolean("FirstRun",false).apply();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent signUpIntent = new Intent(SplashActivity.this, SignUpActivity.class);
                    signUpIntent.putExtra("internet",internet);
                    startActivity(signUpIntent);
                }
            },TIME_OUT);
        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent signInIntent = new Intent(SplashActivity.this, SignInActivity.class);
                    signInIntent.putExtra("internet",internet);
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
