package com.iwhalecloud.retail.pay.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil
{
    public static final String AES_CBC_PKCS5 = "AES/CBC/PKCS5Padding";
    public static final String IV = "1234567892546398";

    public static Cipher initDecryptCipher(String key, String algorithm, int mode, String iv)
            throws Exception
    {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("utf-8"), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(mode, secretKeySpec, ivSpec);
        return cipher;
    }

    public static byte[] encrypt(String content, Cipher cipher) throws Exception {
        return cipher.doFinal(content.getBytes("utf-8"));
    }

    public static byte[] decrypt(byte[] content, Cipher cipher) throws Exception {
        return cipher.doFinal(content);
    }
}