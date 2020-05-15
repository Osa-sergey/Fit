package com.serg.fit.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.serg.fit.R;

import Enums.SigningUpTypes;

public class TypeSignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private MaterialButton btnPhone, btnMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_sign_up);

        btnMail = findViewById(R.id.btnViaMail);
        btnPhone = findViewById(R.id.btnViaPhone);

        btnPhone.setOnClickListener(this);
        btnMail.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext(), ConfirmationActivity.class);
        if (view == btnPhone) {
            intent.putExtra("type", SigningUpTypes.PHONE);
        } else if (view == btnMail) {
            intent.putExtra("type", SigningUpTypes.EMAIL);

        }

        startActivity(intent);
    }
}
