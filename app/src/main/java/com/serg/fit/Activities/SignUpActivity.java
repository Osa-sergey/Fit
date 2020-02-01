package com.serg.fit.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.serg.fit.R;


public class SignUpActivity extends AppCompatActivity {

    private TextView signInRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signInRef = (TextView) findViewById(R.id.reg_log);
        signInRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = new Intent(SignUpActivity.this,SignInActivity.class);
                startActivity(signInIntent);
            }
        });


    }
}
