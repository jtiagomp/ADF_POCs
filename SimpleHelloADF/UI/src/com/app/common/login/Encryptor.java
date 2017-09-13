package com.app.common.login;

import com.app.common.utils.LogUtils;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

//import org.apache.commons.codec.binary.Base64;

public class Encryptor {
    public static String encrypt(String key, String initVector, String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());


            return null;// Base64.encodeBase64String(encrypted);
        } catch (Exception ex) {
            LogUtils.writeLogError("ERROR while encrypt this value:  " + value, ex);
        }

        return null;
    }

    public static String decrypt(String key, String initVector, String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = null;// cipher.doFinal(Base64.decodeBase64(encrypted));

            return new String(original);
        } catch (Exception ex) {
            LogUtils.writeLogError("ERROR while decrypt this value:  " + encrypted, ex);
        }

        return null;
    }

    public static void main(String[] args) {
        String key = "LKJ29876MKJ97654"; // 128 bit key
        String initVector = "RandomInitVector"; // 16 bytes IV

        String value = "joao.tiago.";

        String encryptedString = encrypt(key, initVector, value);
        String decryptedString = decrypt(key, initVector, encryptedString);
        System.out.println("encryptedString " + encryptedString);
        System.out.println("decryptedString " + decryptedString);

    }
}
