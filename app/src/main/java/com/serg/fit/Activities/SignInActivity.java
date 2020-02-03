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

import Enums.ServerResponse;

public class SignInActivity extends AppCompatActivity {

    private String userEmail;
    private String userPassword;
    private Boolean userAuto;

    private CheckedTextView autoSave;
    private TextInputEditText email;
    private TextInputEditText password;

    private TextView signUpRef;
    private SharedPreferences pref;
    private MaterialButton btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        signUpRef = (TextView) findViewById(R.id.log_reg);
        autoSave = (CheckedTextView) findViewById(R.id.signIn_autoSave);
        email = (TextInputEditText) findViewById(R.id.signIn_email);
        password = (TextInputEditText) findViewById(R.id.signIn_password);
        btnSubmit = (MaterialButton)  findViewById(R.id.signIn_submit);

        //переход между логином и регистрацией
        signUpRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(SignInActivity.this,SignUpActivity.class);
                startActivity(signUpIntent);
            }
        });

        //переключение иконки чекбокса
        autoSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CheckedTextView) v).toggle();
            }
        });
        //проверка на наличие незакрытой сессии
        pref = getApplicationContext().getSharedPreferences("Pref",MODE_PRIVATE);
        userPassword = pref.getString("authorizedPassword", "");

        //если у нас есть авторизованный с флагом
        if(!userPassword.equals("")&& pref.getBoolean("authorizedAuto", false)) {
            userEmail = pref.getString("authorizedEmail","");
            userAuto = pref.getBoolean("authorizedAuto", false);
            //быстрое подключение для авторизованного пользователя
            auth(userEmail,userPassword,userAuto);
            //если запомненный пользователь не авторизовался, то вписываем его данные в форму и просим ввести пароль
            email.setText(userEmail);
            autoSave.setChecked(userAuto);
            password.setError(getResources().getString(R.string.err_wrongPassword));
        }
        //авторизованный без флага. Делаем возможность быстрого ввода пароля
        if(!userPassword.equals("")&& !pref.getBoolean("authorizedAuto", false)){
            userEmail = pref.getString("authorizedEmail","");
            email.setText(userEmail);
            autoSave.setChecked(false);
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    boolean flag = false;
                    //проверки на отсутствие ввода
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
                    //авторизация по кнопке
                    auth(userEmail,userPassword,userAuto);
                    //для удобства заранее заполняем
                    email.setText(userEmail);
                    autoSave.setChecked(userAuto);
            }
        });
    }

    /**
     * Функция проверки авторизации если проверки пройдены, то перекидывает в основное меню
     * @param email емаил для проверки
     * @param password пароль
     * @param autoAuth выставлена ли галочка о том, что пользователя нужно запомнить
     */
    void auth(String email,String password,boolean autoAuth){
        Intent mainIntent = new Intent(SignInActivity.this,MainActivity.class);

        //проверка на совпадение email авторизованного пользователя и того, что в строке email
        if(!pref.getString("authorizedPassword","").equals("") &&
            !pref.getString("authorizedEmail","").equals(email)){
            pref.edit().putString("authorizedPassword","").apply();
        }
        if(SupportUtils.checkInternetConnection()){
            if(pref.getString("authorizedPassword","").equals("")){
                //авторизованного пользователя нету
                if(!SupportUtils.checkEmail(email)){
                    this.email.setError(getResources().getString(R.string.err_notCheckedEmail));
                    return;
                }
                if(!SupportUtils.checkPassword(password)){
                    this.password.setError(getResources().getString(R.string.err_notCheckedPass));
                    return;
                }
                password = SupportUtils.md5(password);

                ServerResponse response = serverAuthorization(email,password);
                switch (response){
                    case SUCCESSFULLY:
                        pref.edit().putString("authorizedEmail",email).
                                putString("authorizedPassword",password).
                                putBoolean("authorizedAuto",autoAuth).apply();
                        Log.d("sp",email+" "+password+ " "+autoAuth);
                        //TODO проверка что есть БД под него
                        startActivity(mainIntent);
                        break;
                    case WRONG_PASS_OR_EMAIL:
                        this.password.setError(getResources().getString(R.string.err_authFail));
                        this.email.setError(getResources().getString(R.string.err_authFail));
                        break;
                    case NO_CONNECTION:
                        Toast.makeText(getApplicationContext(),getResources().getText(R.string.err_notConnection),Toast.LENGTH_LONG).show();
                }
            }else {
                if(!autoAuth) {
                    //проверка пароля, хеширование
                    if(!SupportUtils.checkPassword(password)){
                        this.password.setError(getResources().getString(R.string.err_notCheckedPass));
                        pref.edit().putString("authorizedPassword","").apply();
                        return;
                    }
                    password = SupportUtils.md5(password);
                }
                ServerResponse response = serverAuthorization(email,password);
                switch (response){
                    case SUCCESSFULLY:
                        pref.edit().putString("authorizedEmail",email).
                                putString("authorizedPassword",password).
                                putBoolean("authorizedAuto",autoAuth).apply();
                        Log.d("sp",email+" "+password+ " "+autoAuth);
                        startActivity(mainIntent);
                        break;
                    case WRONG_PASS_OR_EMAIL:
                        this.password.setError(getResources().getString(R.string.err_authFail));
                        this.email.setError(getResources().getString(R.string.err_authFail));
                        pref.edit().putString("authorizedPassword","").apply();
                        break;
                    case NO_CONNECTION:
                        Toast.makeText(getApplicationContext(),getResources().getText(R.string.err_notConnection),Toast.LENGTH_LONG).show();
                }
            }
        }else {
            if(!pref.getString("authorizedPassword","").equals("")){
                if(autoAuth){
                    //авторизованный пользователь есть, флажок есть
                    //переходим к приложению
                    startActivity(mainIntent);
                }else{
                    //авторизованный пользователь есть, но флажка нет
                    //проверка пароля, хеширование
                    if(!SupportUtils.checkPassword(password)){
                        this.password.setError(getResources().getString(R.string.err_notCheckedPass));
                        pref.edit().putString("authorizedPassword","").apply();
                        return;
                    }
                    password = SupportUtils.md5(password);
                    if(password.equals(pref.getString("authorizedPassword",""))){
                        startActivity(mainIntent);
                    }else{
                        this.password.setError(getResources().getString(R.string.err_wrongPassword));
                        pref.edit().putString("authorizedPassword","").apply();
                    }
                }
            }else{
                String DBPassword = getPasswordFromDB(email);
                if(DBPassword == null) {
                    this.password.setError(getResources().getString(R.string.err_authFail));
                    this.email.setError(getResources().getString(R.string.err_authFail));
                    return;
                }
                if(!SupportUtils.checkPassword(password)){
                    this.password.setError(getResources().getString(R.string.err_notCheckedPass));
                    return;
                }
                password = SupportUtils.md5(password);
                if(password.equals(DBPassword)){
                    pref.edit().putString("authorizedEmail",email).
                            putString("authorizedPassword",password).
                            putBoolean("authorizedAuto",autoAuth).apply();
                    Log.d("sp",email+" "+password+ " "+autoAuth);
                    startActivity(mainIntent);
                }else {
                    this.password.setError(getResources().getString(R.string.err_authFail));
                    this.email.setError(getResources().getString(R.string.err_authFail));
                }
            }
        }
    }

    /**
     * Делает завпрос к базе данных чтобы выяснить наличие данного пользователя в сохраненных
     * @param email email сохраненного пользователя
     * @return hash если пользователь есть в базе, null если нету
     */
    //TODO проверяем наличие записи по уникальному полю email
    private String getPasswordFromDB(String email) {
        return null;
    }

    /**
     * Отправка запроса авторизации на сервер
     * @param userEmail почта
     * @param userPassword хэш пароля
     * @return SUCCESSFULLY успешное выполнение авторизации,
     *     NO_CONNECTION нет соединения с сервером,
     *     WRONG_PASS_OR_EMAIL неверная комбинация email, password
     */
    //TODO посылка запроса на авторизацию пользователя
    private ServerResponse serverAuthorization(String userEmail, String userPassword) {
        return ServerResponse.SUCCESSFULLY;
    }
}

