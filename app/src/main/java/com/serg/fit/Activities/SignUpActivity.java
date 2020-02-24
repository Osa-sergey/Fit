package com.serg.fit.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.serg.fit.R;

import Enums.ServerResponse;
import Utils.SupportUtils;


public class SignUpActivity extends AppCompatActivity {

    private TextView signInRef;

    private TextInputEditText name;
    private TextInputEditText secondName;
    private TextInputEditText email;
    private TextInputEditText password;
    private TextInputEditText secondPassword;

    private MaterialButton btnSubmit;

    private Dialog dialogOccup;
    private MaterialButton btnDialogOccup;
    private Dialog dialogSuccess;
    private MaterialButton btnDialogSuccess;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signInRef = (TextView) findViewById(R.id.reg_log);

        name = (TextInputEditText) findViewById(R.id.reg_name);
        secondName = (TextInputEditText) findViewById(R.id.reg_secondName);
        email = (TextInputEditText) findViewById(R.id.reg_email);
        password = (TextInputEditText) findViewById(R.id.reg_password);
        secondPassword = (TextInputEditText) findViewById(R.id.reg_secondPassword);

        btnSubmit = (MaterialButton) findViewById(R.id.reg_submit);

        //переход между логином и регистрацией
        signInRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = new Intent(SignUpActivity.this,SignInActivity.class);
                startActivity(signInIntent);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singUp();
                password.setText("");
                secondPassword.setText("");
            }
        });
    }

    private void singUp() {
        if(!SupportUtils.checkInternetConnection()){
            Toast.makeText(getApplicationContext(),getResources().getText(R.string.err_notInternet),Toast.LENGTH_LONG).show();
            return;
        }
        boolean flag = false;
        if(name.getText().length() == 0){
            name.setError(getResources().getString(R.string.err_emptyString));
            flag = true;
        }if(secondName.getText().length() == 0){
            secondName.setError(getResources().getString(R.string.err_emptyString));
            flag = true;
        }if(email.getText().length() == 0){
            email.setError(getResources().getString(R.string.err_emptyString));
            flag = true;
        }if(password.getText().length() == 0){
            password.setError(getResources().getString(R.string.err_emptyString));
            flag = true;
        }if(secondPassword.getText().length() == 0){
            secondPassword.setError(getResources().getString(R.string.err_emptyString));
            flag = true;
        }
        if(flag) return;
        if(!SupportUtils.checkName(name.getText().toString())){
            name.setError(getResources().getString(R.string.err_badName));
        }if(!SupportUtils.checkName(secondName.getText().toString())){
            secondName.setError(getResources().getString(R.string.err_badName));
        }if(!SupportUtils.checkEmail(email.getText().toString())){
            email.setError(getResources().getString(R.string.err_notCheckedEmail));
        }if(!SupportUtils.checkPassword(password.getText().toString())){
            password.setError(getResources().getString(R.string.err_notCheckedPass));
            return;
        }if(!password.getText().toString().equals(secondPassword.getText().toString())){
            password.setError(getResources().getString(R.string.err_differentPass));
            secondPassword.setError(getResources().getString(R.string.err_differentPass));
            return;
        }
        String accountName = name.getText().toString();
        String accountSecondName = secondName.getText().toString();
        final String accountEmail = email.getText().toString();
        final String accountPasswordMd5 = SupportUtils.md5(password.getText().toString());
        ServerResponse response = serverCreationAccount(accountName,accountSecondName,accountEmail,accountPasswordMd5);
        switch (response){
            case SUCCESSFULLY:
                dialogSuccess = new Dialog(this);
                dialogSuccess.setCancelable(false);
                dialogSuccess.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogSuccess.setContentView(R.layout.dialog_success);
                dialogSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                textView = (TextView) dialogSuccess.findViewById(R.id.text);
                btnDialogSuccess = (MaterialButton) dialogSuccess.findViewById(R.id.btn_submit);
                btnDialogSuccess.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ServerResponse serverResponse = checkEmailVerification(accountEmail);
                        switch (serverResponse){
                            case SUCCESSFULLY:
                                //TODO вносить данные в DB
                                SharedPreferences pref = getApplicationContext().getSharedPreferences("Pref",MODE_PRIVATE);
                                pref.edit().putString("authorizedEmail",accountEmail).
                                        putString("authorizedPassword",accountPasswordMd5).
                                        putBoolean("authorizedAuto",false).apply();
                                Log.d("sp",email+" "+password+ " "+false);
                                startActivity(new Intent(SignUpActivity.this,MainActivity.class));
                                break;
                            case NOT_CONFIRMED:
                                textView.setText(getResources().getString(R.string.hint_rerun));
                                break;
                            case NO_CONNECTION:
                                Toast.makeText(getApplicationContext(),getResources().getText(R.string.err_notConnection),Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                });
                dialogSuccess.show();
                break;
            case NO_CONNECTION:
                Toast.makeText(getApplicationContext(),R.string.err_notConnection,Toast.LENGTH_LONG).show();
                break;
            case THIS_EMAIL_IS_OCCUPIED:
                dialogOccup = new Dialog(this);
                dialogOccup.setCancelable(false);
                dialogOccup.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogOccup.setContentView(R.layout.dialog_occupied);
                dialogOccup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                btnDialogOccup = (MaterialButton) dialogOccup.findViewById(R.id.btn_submit);
                btnDialogOccup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        email.setText("");
                        dialogOccup.dismiss();
                    }
                });
                dialogOccup.show();
        }
    }

    /**
     * Проверка на подьверждение почты для только что зарегистрированного пользователя
     * @param accountEmail проверяемая почта
     * @return NOT_CONFIRMED почта не подтверждена,
     *     SUCCESSFULLY все успешно,
     *     NO_CONNECTION нет соединения
     */
    //TODO проверить что подтвердили почту
    private ServerResponse checkEmailVerification(String accountEmail) {
        return ServerResponse.SUCCESSFULLY;
    }

    /**
     * Отправка запроса на создание пользователя
     * @param Name имя пользователя
     * @param SecondName фамилия пользователя
     * @param Email пользователя
     * @param PasswordMd5 хэш пароля
     * @return SUCCESSFULLY успешное создание записи,
     *     NO_CONNECTION нет соединения с сервером,
     *     THIS_EMAIL_IS_OCCUPIED занятый email
     */
    private ServerResponse serverCreationAccount(String Name, String SecondName, String Email, String PasswordMd5) {
        //TODO посылка запроса на создание пользователя
        return ServerResponse.SUCCESSFULLY;
    }

}
