package com.iwhalecloud.retail.order2b.authpay.util;

import com.iwhalecloud.retail.order2b.authpay.handler.TradeCertificate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

@Component
public class CertificateUtil {

    private static String merchantCertificatePath;
    private static String merchantCertificatePwd;
    private static String bestpayCertificatePath;
    private static String iv;

    @Value("${pay.merchantCertificatePath}")
    public void setMerchantCertificatePath(String merchantCertificatePath) {
        CertificateUtil.merchantCertificatePath = merchantCertificatePath;
    }

    @Value("${pay.merchantCertificatePwd}")
    public void setMerchantCertificatePwd(String merchantCertificatePwd) {
        CertificateUtil.merchantCertificatePwd = merchantCertificatePwd;
    }

    @Value("${pay.bestpayCertificatePath}")
    public void setBestpayCertificatePath(String bestpayCertificatePath) {
        CertificateUtil.bestpayCertificatePath = bestpayCertificatePath;
    }

    @Value("${pay.iv}")
    public void setIv(String iv) {
        CertificateUtil.iv = iv;
    }

    public static final String KEYSTORETYPE_JKS = "JKS";
    public static final String KEYSTORETYPE_PKCS12 = "PKCS12";

    public static TradeCertificate getTradeCertificate(String keyStroeType) {

        PrivateKey merchantPrivateKey = null;
        PublicKey bestpayPublicKey = null;

        ClassLoader cl = CertificateUtil.class.getClassLoader();
        InputStream fiKeyFile = cl.getResourceAsStream(merchantCertificatePath);
        merchantPrivateKey = getMerchantPirvateKey(fiKeyFile, merchantCertificatePwd, keyStroeType);
        InputStream certfile = cl.getResourceAsStream(bestpayCertificatePath);
        bestpayPublicKey = getPublicKey(certfile);
        return new TradeCertificate(merchantPrivateKey, bestpayPublicKey, iv);
    }

    /**
     * 证书类型为“存量数据"类型证书类型，flag为true,当flag为false时同 TradeCertificate getTradeCertificate(String properties, String keyStroeType)
     */

    public static TradeCertificate getTradeCertificate(String keyStroeType, boolean flag) {
        if (!flag) {
            return getTradeCertificate(keyStroeType);
        }

        PrivateKey merchantPrivateKey = null;
        PublicKey bestpayPublicKey = null;

        ClassLoader cl = CertificateUtil.class.getClassLoader();
        InputStream fiKeyFile = cl.getResourceAsStream(merchantCertificatePath);
        merchantPrivateKey = getMerchantPirvateKey(fiKeyFile, merchantCertificatePwd, keyStroeType);
        bestpayPublicKey = base64StrToCert(bestpayCertificatePath);
        return new TradeCertificate(merchantPrivateKey, bestpayPublicKey, iv);
    }

    /**
     * 获取公钥
     */
    private static PublicKey getPublicKey(InputStream pubKey) {
        X509Certificate x509cert = null;
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            x509cert = (X509Certificate) cf.generateCertificate(pubKey);
        } catch (CertificateException e) {
            if (pubKey != null) {
                try {
                    pubKey.close();
                } catch (IOException e1) {
                    throw new RuntimeException("文件流关闭异常", e1);
                }
            }
            throw new RuntimeException("初始化公钥异常", e);
        }
        return x509cert.getPublicKey();
    }

    /**
     * 获取商户私钥
     */
    private static PrivateKey getMerchantPirvateKey(InputStream priKey, String keyPassword, String keyStoreType) {
        String keyAlias;
        PrivateKey privateKey = null;
        try {
            KeyStore ks = KeyStore.getInstance(keyStoreType);
            ks.load(priKey, keyPassword.toCharArray());
            Enumeration<?> myEnum = ks.aliases();
            while (myEnum.hasMoreElements()) {
                keyAlias = (String) myEnum.nextElement();
                if (ks.isKeyEntry(keyAlias)) {
                    privateKey = (PrivateKey) ks.getKey(keyAlias, keyPassword.toCharArray());
                    break;
                }
            }
        } catch (Exception e) {
            if (priKey != null) {
                try {
                    priKey.close();
                } catch (IOException e1) {
                    throw new RuntimeException("流关闭异常", e1);
                }
            }
            throw new RuntimeException("加载私钥失败", e);
        }

        if (privateKey == null) {
            throw new RuntimeException("私钥不存在");
        }

        return privateKey;
    }

    public static PublicKey base64StrToCert(String base64Cert) {
        try {
            CertificateFactory factory = CertificateFactory.getInstance("X.509");
            ByteArrayInputStream streamCert = new ByteArrayInputStream(
                    new BASE64Decoder().decodeBuffer(base64Cert));

            X509Certificate cert = (X509Certificate) factory.generateCertificate(streamCert);
            if (cert == null) {
                throw new GeneralSecurityException("将cer从base64转换为对象失败");
            }
            return cert.getPublicKey();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String certToBase64(PublicKey publicKey) {
        byte[] keyBytes = publicKey.getEncoded();
        String s = (new BASE64Encoder()).encode(keyBytes);
        return s;
    }

}
