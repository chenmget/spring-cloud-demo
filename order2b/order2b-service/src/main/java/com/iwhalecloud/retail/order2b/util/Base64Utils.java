package com.iwhalecloud.retail.order2b.util;

import org.bouncycastle.util.encoders.Base64;


public final class Base64Utils {
    private Base64Utils() {
    }

    /**
     * <p>
     * Base 64编码
     * </p>
     *
     * @param base64
     * @return
     * @throws Exception
     */
    public static byte[] decode(String base64) {
        return Base64.decode(base64.getBytes());
    }

    /**
     * <p>
     * Base 64解码
     * </p>
     *
     * @param bytes
     * @return
     * @throws Exception
     */
    public static String encode(byte[] bytes) {
        return new String(Base64.encode(bytes));
    }
}