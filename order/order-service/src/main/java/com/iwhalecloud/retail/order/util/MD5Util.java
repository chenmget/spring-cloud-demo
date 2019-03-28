package com.iwhalecloud.retail.order.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    public MD5Util() {
    }

    public static String compute(String inStr) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] md5Bytes = md5.digest(inStr.getBytes());
        StringBuffer hexValue = new StringBuffer();

        for(int i = 0; i < md5Bytes.length; ++i) {
            int val = md5Bytes[i] & 255;
            if (val < 16) {
                hexValue.append("0");
            }

            hexValue.append(Integer.toHexString(val));
        }

        return hexValue.toString();
    }

    public static final String MD5(String s) throws NoSuchAlgorithmException {
        char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        byte[] strTemp = s.getBytes();
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(strTemp);
        byte[] md = md5.digest();
        int j = md.length;
        char[] str = new char[j * 2];
        int k = 0;

        for(int i = 0; i < j; ++i) {
            byte byte0 = md[i];
            str[k++] = hexDigits[byte0 >>> 4 & 15];
            str[k++] = hexDigits[byte0 & 15];
        }

        return new String(str);
    }

    public static final String md5Str(String plainText) {
        String md5Str = null;

        try {
            StringBuffer buf = new StringBuffer();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte[] b = md.digest();

            for(int offset = 0; offset < b.length; ++offset) {
                int i = b[offset];
                if (i < 0) {
                    i += 256;
                }

                if (i < 16) {
                    buf.append("0");
                }

                buf.append(Integer.toHexString(i));
            }

            md5Str = buf.toString().substring(8, 24);
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        return md5Str;
    }
}

