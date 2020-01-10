package com.lc.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
    public static String PasswordMD5Func(String pwd) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hash = md.digest(pwd.getBytes(StandardCharsets.UTF_8));

        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : hash){
            stringBuilder.append(String.format("%02x",b));
        }
        return stringBuilder.toString();
    }
}
