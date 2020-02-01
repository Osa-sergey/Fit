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

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private Boolean internet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        signUpRef = (TextView) findViewById(R.id.reg_log);
        autoSave = (CheckedTextView) findViewById(R.id.reg_autoSave);
        email = (TextInputEditText) findViewById(R.id.signin_email);
        password = (TextInputEditText) findViewById(R.id.signin_password);
        btnSubmit = (MaterialButton)  findViewById(R.id.signin_submit);

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
        //TODO проверять наличие подключения но не сигнала
        internet = checkInternetConnection();
        if(internet){
            if(pref.getString("authorizedPassword","").equals("")){
                //авторизованного пользователя нету
                if(!checkEmail(email)){
                    this.email.setError(getResources().getString(R.string.err_notCheckedEmail));
                    return;
                }
                if(!checkPassword(password)){
                    this.password.setError(getResources().getString(R.string.err_notCheckedPass));
                    return;
                }
                password = md5(password);

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
                    case WRONGPASSOREMAIL:
                        this.password.setError(getResources().getString(R.string.err_authFail));
                        this.email.setError(getResources().getString(R.string.err_authFail));
                        break;
                    case NOCONNECTION:
                        Toast.makeText(getApplicationContext(),getResources().getText(R.string.err_notConnection),Toast.LENGTH_LONG).show();
                }
            }else {
                if(!autoAuth) {
                    //проверка пароля, хеширование
                    if(!checkPassword(password)){
                        this.password.setError(getResources().getString(R.string.err_notCheckedPass));
                        pref.edit().putString("authorizedPassword","").apply();
                        return;
                    }
                    password = md5(password);
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
                    case WRONGPASSOREMAIL:
                        this.password.setError(getResources().getString(R.string.err_authFail));
                        this.email.setError(getResources().getString(R.string.err_authFail));
                        pref.edit().putString("authorizedPassword","").apply();
                        break;
                    case NOCONNECTION:
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
                    if(!checkPassword(password)){
                        this.password.setError(getResources().getString(R.string.err_notCheckedPass));
                        pref.edit().putString("authorizedPassword","").apply();
                        return;
                    }
                    password = md5(password);
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
                if(!checkPassword(password)){
                    this.password.setError(getResources().getString(R.string.err_notCheckedPass));
                    return;
                }
                password = md5(password);
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
     * Проверка на подключение либо к мобильной либо к стационароной сети
     * @return true если есть подключение
     */
    private Boolean checkInternetConnection() {
        return true;
    }

    /**
     * Делает завпрос к базе данных чтобы выяснить наличие данного пользователя в сохраненных
     * @param email email сохраненного пользователя
     * @return hash если пользователь есть в базе, null если нету
     */
    private String getPasswordFromDB(String email) {
        return null;
    }

    /**
     * Функция для проверки правильности формата почты
     * @param email проверяемая почта
     * @return true если подходит по формату, иначе false
     */
    private boolean checkEmail(String email) {
        Pattern p = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * Функция для проверки правильности формата пароля
     * @param password проверяем пароль
     * @return true если подходит по формату, иначе false
     */
    private boolean checkPassword(String password) {
        return password.length() >= 6 && password.length() <= 20;
    }

    /**
     * Отправка запроса на сервер
     * @param userEmail почта
     * @param userPassword хэш пароля
     * @return true если на сервере есть такое сочитание, иначе false
     */
    private ServerResponse serverAuthorization(String userEmail, String userPassword) {
        return ServerResponse.SUCCESSFULLY;
    }

    /**
     * кастомная функция хэширования md5
     * @param st хэшируемая строка
     * @return хэш
     */
    private String md5(String st) {
        MessageDigest messageDigest;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(st.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        BigInteger bigInt = new BigInteger(1, digest);
        String md5Hex = bigInt.toString(16);

        while( md5Hex.length() < 32 ){
            md5Hex = "0" + md5Hex;
        }
        return md5Hex;
    }
}

enum ServerResponse{
    SUCCESSFULLY,
    NOCONNECTION,
    WRONGPASSOREMAIL
}
