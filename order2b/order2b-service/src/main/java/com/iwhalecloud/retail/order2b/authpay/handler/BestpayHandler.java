/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2018 All Rights Reserved.
 */
package com.iwhalecloud.retail.order2b.authpay.handler;

import org.bouncycastle.util.encoders.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;

/**
 * @author yangbo2018
 * @version Id: BestpayHandler.java, v 0.1 2018/3/2 9:54 yangbo2018 Exp $$
 */
public class BestpayHandler {
    /**
     * 字符集编码采用UTF-8
     */
    private static final String ENCODING = "UTF-8";
    /**
     * 生成AES加密所需Key的算法
     */
    private static final String KEY_ALGORITHM = "AES";
    /**
     * 加解密算法/工作模式/填充方式,Java6.0支持PKCS5Padding填充方式,BouncyCastle支持PKCS7Padding填充方式
     */
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";

    private static final String RSA_ALGORITHM = "RSA/ECB/PKCS1Padding";

    public static final String SIGNATURE_ALGORITHM_SHA1 = "SHA1withRSA";
    public static final String SIGNATURE_ALGORITHM_SHA256 = "SHA256withRSA";

    private TradeCertificate tradeCertificate;

    public TradeCertificate getTradeCertificate() {
        return tradeCertificate;
    }

    public void setTradeCertificate(TradeCertificate tradeCertificate) {
        this.tradeCertificate = tradeCertificate;
    }

    public BestpayHandler(TradeCertificate tradeCertificate) {
        this.tradeCertificate = tradeCertificate;
    }

    /**
     * 函数功能：敏感信息加密,AES算法为AES/CBC/PKCS5Padding
     * 参数：key，16位随机字符串
     * 参数：tobeEncoded，待加密的字符串，如银行卡号，V5版中的数字信封明文
     * 参数：iv,AES加密用到的IV值，V3，V4版本接口为固定值1234567892546398，V5版本接口随商户证书一起提供
     * *
     */

    public String encode(String key, String tobeEncoded, String iv) throws Exception {
        //1.AES加密
        Key k = new SecretKeySpec(key.getBytes(ENCODING), KEY_ALGORITHM);
        IvParameterSpec ivs = new IvParameterSpec(iv.getBytes());
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, k, ivs);
        byte[] bytes = cipher.doFinal(tobeEncoded.getBytes(ENCODING));
        //2.BASE64编码
        return new String(Base64.encode(bytes));
    }

    /**
     * 敏感信息解密，如银行卡等信息
     */

    public String decode(String key, String tobeDecode, String iv) throws Exception {
        //BASE64解码
        byte[] base64Bytes = Base64.decode(tobeDecode);
        Key k = new SecretKeySpec(key.getBytes(ENCODING), KEY_ALGORITHM);
        IvParameterSpec ivs = new IvParameterSpec(iv.getBytes());
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, k, ivs);
        byte[] bytes = cipher.doFinal(base64Bytes);
        return new String(bytes);

    }

    /**
     * 函数功能 :RSA非对称加密，用于对16位的AES秘钥进行加密,算法为RSA/ECB/PKCS1Padding
     * 参数：TobeEncryptMsg待加密的字符串，如16位AES秘钥
     */

    public String rsaEncode(String TobeEncryptMsg) {
        //获取公钥
        Cipher instance;
        try {
            instance = Cipher.getInstance(RSA_ALGORITHM);
            instance.init(Cipher.ENCRYPT_MODE, tradeCertificate.getBestpayPublicKey());
            byte[] bytes = instance.doFinal(TobeEncryptMsg.getBytes());
            return new String(Base64.encode(bytes));
        } catch (Exception e) {
            throw new RuntimeException("RSA加密失败", e);
        }

    }

    /**
     * 函数功能：使用商户私钥加签
     * 参数：tobeSigned，待加签的字符串，V3，版为json字符串，V4，V5版为用&连接起来的key=value键值对
     * 参数：algorithm,加签算法，V3，V4版本为SHA1withRSA，V5版本为SHA256withRSA
     */

    public String sign(String tobeSigned, String algorithm) throws GeneralSecurityException, UnsupportedEncodingException {
        PrivateKey privateKey = tradeCertificate.getMerchantPrivateKey();
        Signature signature = Signature.getInstance(algorithm);
        signature.initSign(privateKey);
        signature.update(tobeSigned.getBytes("UTF-8"));
        byte[] signBytes = signature.sign();
        return new String(Base64.encode(signBytes));
    }

    /**
     * 函数功能：使用翼支付公钥对翼支付响应报文验签
     * 参数：plainText，待验签的字符串，V3为翼支付响应的data对应json传，V4，V5版为将翼支付响应报文中除sign以外的值用&连接起来的key=value键值对
     * 参数：sign：翼支付响应的签文sign
     * 参数：algorithm,加签算法，V3，V4版本为SHA1withRSA，V5版本为SHA256withRSA
     */

    public boolean verify(String plainText, String sign, String algorithm) {

        try {
            PublicKey publicKey = tradeCertificate.getBestpayPublicKey();
            Signature verify = Signature.getInstance(algorithm);
            verify.initVerify(publicKey);
            verify.update(plainText.getBytes(ENCODING));
            return verify.verify(Base64.decode(sign));
        } catch (Exception e) {
            throw new RuntimeException("验签失败", e);
        }
    }

}
