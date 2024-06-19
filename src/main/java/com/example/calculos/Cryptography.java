package com.example.calculos;

import android.util.Base64;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;




public class Cryptography {
    private static final String KEY = "1234567890123456";
    private static SecretKey getFixedKey() {
        return new SecretKeySpec(KEY.getBytes(), "AES");
    }
    public static String encrypt(String data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
        SecureRandom secureRandom = new SecureRandom();

        byte[] iv = new byte[12];
        secureRandom.nextBytes(iv);

        SecretKey key = getFixedKey();

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);

        byte[] encryptedData = cipher.doFinal(data.getBytes());

        byte[] encryptedMessage = new byte[iv.length + encryptedData.length];
        System.arraycopy(iv, 0, encryptedMessage, 0, iv.length);
        System.arraycopy(encryptedData, 0, encryptedMessage, iv.length, encryptedData.length);

        return Base64.encodeToString(encryptedMessage, Base64.DEFAULT);
    }
    public static String decrypt(String encryptedData) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException {
        byte[] decodedData = Base64.decode(encryptedData, Base64.DEFAULT);

        byte[] iv = new byte[12];
        System.arraycopy(decodedData, 0, iv, 0, iv.length);

        byte[] cipherText = new byte[decodedData.length - iv.length];
        System.arraycopy(decodedData, iv.length, cipherText, 0, cipherText.length);

        SecretKey key = getFixedKey();

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.DECRYPT_MODE, key, parameterSpec);

        byte[] originalData = cipher.doFinal(cipherText);

        return new String(originalData);
    }
}