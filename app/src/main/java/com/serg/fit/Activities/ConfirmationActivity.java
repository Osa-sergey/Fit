package com.serg.fit.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.transition.TransitionManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.serg.fit.R;
import com.transitionseverywhere.ChangeText;

import net.rimoto.intlphoneinput.IntlPhoneInput;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import Enums.SigningUpTypes;
import Utils.SupportUtils;

public class ConfirmationActivity extends AppCompatActivity {

    private ViewGroup transitionsContainer;

    private IntlPhoneInput phoneInputView;
    private SigningUpTypes signingUpType;
    private Button btnNext;
    private TextInputEditText tietCode;
    private TextInputEditText tietEmail;
    private TextInputLayout tilEmail;
    private TextInputLayout tilCode;
    private TextView tvNoCode;
    private TextView tvTitle;
    private String verificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    private boolean hasGotTheCode = false;

    private FirebaseAuth mAuth;

    //    задержка для отправки СМС
    int phoneDelay = 60;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        mAuth = FirebaseAuth.getInstance();


        try {

            signingUpType = (SigningUpTypes) getIntent().getSerializableExtra("type");
        } catch (Exception e) {
            Log.w("confirmationActivity", "onCreate: " + e.toString());
        }

        tvTitle = findViewById(R.id.tvTypeYourData);
        tvNoCode = findViewById(R.id.tvHaveNotReceivedTheCode);

        phoneInputView = findViewById(R.id.my_phone_input);
        tietEmail = findViewById(R.id.tietMail);

        phoneInputView.setVisibility(View.GONE);
        tietEmail.setVisibility(View.GONE);


        btnNext = findViewById(R.id.btnNext);
        tietCode = findViewById(R.id.tietCode);
        tilEmail = findViewById(R.id.tilMail);
        tilCode = findViewById(R.id.tilCode);

        tilCode.setVisibility(View.GONE);
        tvNoCode.setVisibility(View.GONE);

        transitionsContainer = findViewById(R.id.transition_container);

        chooseType(signingUpType);

        tvNoCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TransitionManager.beginDelayedTransition(transitionsContainer,
                        new ChangeText().setChangeBehavior(ChangeText.CHANGE_BEHAVIOR_OUT_IN));

                tvNoCode.setTextColor(Color.GRAY);
                if (signingUpType.equals(SigningUpTypes.PHONE)) {
                    tvNoCode.setText(getString(R.string.codeSent));
                    sendVerificationCode();
                } else if (signingUpType.equals(SigningUpTypes.EMAIL)) {
                    tvNoCode.setText(getString(R.string.mailSent));
                }


            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (signingUpType == SigningUpTypes.PHONE) {
                    if (phoneInputView.isValid()) {
                        next();
                    } else {
                        Toast.makeText(ConfirmationActivity.this, getString(R.string.incorrect_number), Toast.LENGTH_SHORT).show();
                    }
                } else if (signingUpType == SigningUpTypes.EMAIL) {


                    if (tietEmail.getText().toString().isEmpty()) {

                        tilEmail.setError(getString(R.string.err_emptyString));
                        tilEmail.setEnabled(true);
                    } else if (!SupportUtils.checkEmail(tietEmail.getText().toString())) {
                        tilEmail.setError(getString(R.string.err_notCheckedEmail));
                        tilEmail.setEnabled(true);
                    } else {
                        tilEmail.setErrorEnabled(false);
                        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                        intent.putExtra("type", SigningUpTypes.EMAIL);
                        if (signingUpType.equals(SigningUpTypes.EMAIL))
                            intent.putExtra("email", tilEmail.getEditText().getText().toString());
                        startActivity(intent);
                    }
                }


            }

        });


    }

    private void next() {
        if (!hasGotTheCode) {

            sendVerificationCode();

            tilCode.setVisibility(View.VISIBLE);
            tvNoCode.setVisibility(View.VISIBLE);
            Animation alphaTransition = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha_transition);
            alphaTransition.setDuration(700);


            tvNoCode.setAnimation(alphaTransition);
            tilCode.setAnimation(alphaTransition);

            tvNoCode.animate();
            tilCode.animate();
            alphaTransition.start();

            hasGotTheCode = true;

        } else {

            verifyCode();

        }
    }

    //    Отправка SMS Code
    private void sendVerificationCode() {


//       Ставим язык приложения для SMS кода
        mAuth.useAppLanguage();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneInputView.getNumber(),        // Phone number to verify
                phoneDelay,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallBack);        // OnVerificationStateChangedCallbacks
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            verificationId = s;
            mResendToken = forceResendingToken;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                tietCode.setText(code);
                verifyCode();
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                // ...
                Toast.makeText(ConfirmationActivity.this, "Ошибка запроса: " + e.toString(), Toast.LENGTH_SHORT).show();
            } else if (e instanceof FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                // ...
                Toast.makeText(ConfirmationActivity.this, "Ошибка запроса: ", Toast.LENGTH_SHORT).show();

            }

        }
    };

    private void verifyCode() {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, Objects.requireNonNull(tietCode.getText()).toString());
        signInWithCredential(credential);
    }


    private void chooseType(SigningUpTypes signingUpType) {
        //      Открыть клавиатуру при открытии страницы
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        if (signingUpType == SigningUpTypes.PHONE) {


            tvTitle.setText(R.string.type_your_phone);

            phoneInputView.setVisibility(View.VISIBLE);
            phoneInputView.setEnabled(true);
//        Ставит стандартную страну по информации с симки/системуму языку
            phoneInputView.setDefault();

            tvNoCode.setText(R.string.HaveNotReceivedTheCode);

        } else if (signingUpType == SigningUpTypes.EMAIL) {

            tvTitle.setText(R.string.type_your_email);


            tietEmail.setVisibility(View.VISIBLE);
            tvNoCode.setText(R.string.HaveNotReceivedTheMessage);

        }
    }

    private void signInWithCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(ConfirmationActivity.this, "Success", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            startActivity(intent);

                        } else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                });
    }
}
