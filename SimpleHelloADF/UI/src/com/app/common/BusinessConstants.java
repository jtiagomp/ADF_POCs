package com.app.common;



public class BusinessConstants {

    private static final String encryptorkey = "LKJ29876MKJ97654"; // 128 bit key
    private static final String encryptorinitVector = "RandomInitVector"; // 16 bytes IV

    public BusinessConstants() {
        super();
    }
    
    
    public static String getEncryptorkey(){
        return encryptorkey;
    }
    
    public static String getEncryptorinitVector(){
        return encryptorinitVector;
    }
}