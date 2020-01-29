package com.serg.fit.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.serg.fit.R;

public class SignInActivity extends AppCompatActivity {

    private TextView signUpRef;
    private CheckedTextView autoSave;
    private SharedPreferences pref;
    private String userEmail;
    private String userPassword;
    private Boolean userAuto;
    private TextInputEditText email;
    private TextInputEditText password;
    private MaterialButton btnSubmit;
    private Boolean internet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Bundle bundle = getIntent().getExtras();
        internet = bundle.getBoolean("internet");

        signUpRef = (TextView) findViewById(R.id.reg_log);
        autoSave = (CheckedTextView) findViewById(R.id.reg_autoSave);
        email = (TextInputEditText) findViewById(R.id.signin_email);
        password = (TextInputEditText) findViewById(R.id.signin_password);
        btnSubmit = (MaterialButton)  findViewById(R.id.signin_submit);

        signUpRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(SignInActivity.this,SignUpActivity.class);
                startActivity(signUpIntent);
            }
        });

        autoSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CheckedTextView) v).toggle();
            }
        });

        pref = getApplicationContext().getSharedPreferences("Pref",MODE_PRIVATE);
        userEmail = pref.getString("authorizedEmail","");
        if(!userEmail.equals("")) {
            userPassword = pref.getString("authorizedPassword", "");
            userAuto = pref.getBoolean("authorizedAuto", false);
            auth(userEmail,userPassword,userAuto);
            //если запомненный пользователь не авторизовался, то внисываем его данные в форму и просим ввести пароль
            email.setText(userEmail);
            autoSave.setChecked(userAuto);
            password.setError(getResources().getString(R.string.err_wrongPassword));
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    boolean flag = false;
                    if(email.getText().length()==0){
                        email.setError(getResources().getString(R.string.err_emptyString));
                        flag = true;
                    }
                    if(password.getText().length()==0){
                        password.setError(getResources().getString(R.string.err_emptyString));
                        flag = true;
                    }
                    if(flag) return;
                    userEmail = email.getText().toString();
                    userPassword = password.getText().toString();
                    userAuto = autoSave.isChecked();
                    auth(userEmail,userPassword,userAuto);
            }
        });

    }

    /**
     * Функция проверки авторизации если проверки пройдены, то перекидывает в основное меню
     * @param email емаил для проверки
     * @param passwword пароль
     * @param autoAuth выставлена ли галочка о том, что пользователя нужно запомнить
     */
    void auth(String email,String passwword,boolean autoAuth){

    }
}
