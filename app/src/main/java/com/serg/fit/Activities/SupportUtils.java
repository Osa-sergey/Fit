package com.serg.fit.Activities;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class SupportUtils {

    /**
     * Проверка на подключение либо к мобильной либо к стационароной сети
     * @return true если есть подключение
     */
    //TODO проверять наличие подключения но не сигнала
    static Boolean checkInternetConnection() {
        return true;
    }

    /**
     * Функция для проверки правильности формата почты
     * @param email проверяемая почта
     * @return true если подходит по формату, иначе false
     */
    static boolean checkEmail(String email) {
        Pattern p = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher m = p.matcher(email.trim());
        return m.matches();
    }

    /**
     * Функция для проверки правильности формата пароля
     * @param password проверяем пароль
     * @return true если подходит по формату, иначе false
     */
    static boolean checkPassword(String password) {
        return password.length() >= 6 && password.length() <= 20;
    }

    /**
     * Проверка на имя
     * @param name проверяемая строка
     * @return true, если содержатся только буквы
     */
    static boolean checkName(String name) {
        Pattern p = Pattern.compile("[А-ЯЁ][а-яё]{1,19}|[A-Z][a-z]{1,19}");
        Matcher m = p.matcher(name.trim());
        return m.matches();
    }

    /**
     * Кастомная функция хэширования md5
     * @param st хэшируемая строка
     * @return хэш
     */
    static String md5(String st) {
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
