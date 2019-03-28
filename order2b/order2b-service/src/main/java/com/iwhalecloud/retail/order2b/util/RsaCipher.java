package com.iwhalecloud.retail.order2b.util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.X509Certificate;

public abstract class RsaCipher
{
    /**
     * 算法/工作模式/填充方式.
     */
    public static final String RSA_ALGORITHM = "RSA/ECB/PKCS1Padding";
    /**
     * 签名算法.
     */
    public static final String SIGNATURE_ALGORITHM = "SHA1withRSA";
    public static final Object LOCK = new Object();
    private static BouncyCastleProvider bouncyCastleProvider;

    /**
     * 用私钥对信息生成数字签名.
     *
     * @param data     待签名信
     * @param privateK 私钥
     * @return 数字签名
     * @throws GeneralSecurityException 加密异常
     */
    public static byte[] sign(byte[] data, PrivateKey privateK)
            throws GeneralSecurityException {
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data);

        return signature.sign();
    }


    public static byte[] enDecryptByRsa(byte[] data, Key key, int mode)
            throws GeneralSecurityException
    {
        byte[] var21;
        BouncyCastleProvider provider = getInstanceProvider();
        ByteArrayOutputStream outputStream = null;
        try
        {
            outputStream = new ByteArrayOutputStream();
            Cipher cp = Cipher.getInstance("RSA/ECB/PKCS1Padding", provider);
            cp.init(mode, key);
            int blockSize = cp.getBlockSize();
            int blocksNum = (int)Math.ceil((double) data.length / (double)blockSize);
            int calcSize = blockSize;
            Object buffer = null;

            for (int i = 0; i < blocksNum; ++i) {
                if (i == blocksNum - 1) {
                    calcSize = data.length - i * blockSize;
                }

                byte[] var22 = cp.doFinal(data, i * blockSize, calcSize);
                try
                {
                    outputStream.write(var22);
                } catch (IOException var19) {
                    throw new GeneralSecurityException("RSA加/解密时出现异常", var19);
                }
            }

            var21 = outputStream.toByteArray();
        } finally {
            if (outputStream != null)
                try {
                    outputStream.close();
                }
                catch (IOException var18)
                {
                }

        }

        return var21;
    }

    public static boolean verify(byte[] data, String sBase64Cert, byte[] sign)
            throws GeneralSecurityException {
        // 将cer从base64转换为对象
        X509Certificate cerObj = CertUtils.base64StrToCert(sBase64Cert);

        PublicKey publicKey = cerObj.getPublicKey();
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicKey);
        signature.update(data);
        return signature.verify(sign);
    }

    public static boolean verify(String data, String sBase64Cert, String sign)
            throws GeneralSecurityException {
        return verify(data.getBytes(), sBase64Cert, Base64Utils.decode(sign));
    }
    /*

    public static RSAPublicKey restoreKeyByModules(String modulesHex) throws GeneralSecurityException {
        BigInteger modules = new BigInteger(modulesHex, 16);
        BigInteger publicExponent = new BigInteger("65537");
        RSAPublicKeyImpl rsaPublicKey = new RSAPublicKeyImpl(modules, publicExponent);
        return rsaPublicKey;
    }
    */

    public static byte[] enDecryptByRsa(byte[] data, Key key, EncryptMode mode) throws GeneralSecurityException {
        return enDecryptByRsa(data, key, 1 + mode.ordinal());
    }

    private static BouncyCastleProvider getInstanceProvider() {
        if (bouncyCastleProvider == null) {
            Object var0 = LOCK;
            synchronized (LOCK) {
                if (bouncyCastleProvider == null)
                    bouncyCastleProvider = new BouncyCastleProvider();
            }

        }

        return bouncyCastleProvider; }

    public static enum EncryptMode {
        ENCRYPT, DECRYPT;
    }
}