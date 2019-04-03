package com.iwhalecloud.retail.order2b.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

@Slf4j
public class CertUtils {

    public static KeyStore getKeyStore(String keyStorePath, String keyStorePwd) throws GeneralSecurityException {
        log.info("CertUtils.KeyStore keyStorePath={} ", keyStorePath);
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        InputStream inputStream = null;
        try {
            File file = ResourceUtils.getFile(keyStorePath);
            inputStream = new FileInputStream(file);
            keyStore.load(inputStream, keyStorePwd.toCharArray());
        } catch (IOException ex) {
            log.error("CertUtils.CertUtils error is ", ex);
            throw new GeneralSecurityException("获取证书异常");
        } finally {
            try {
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (Exception ex) {
                log.error("CertUtils.getKeyStore Exception ex ", ex);
            }
        }
        return keyStore;
    }

    public static X509Certificate getCertFromCer(String cerFilePath) throws GeneralSecurityException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        InputStream inputStream = null;
        try {
            File file = ResourceUtils.getFile(cerFilePath);
            inputStream = new FileInputStream(file);
            X509Certificate certificate = (X509Certificate) certificateFactory.generateCertificate(inputStream);
            return certificate;
        } catch (IOException ex) {
            throw new GeneralSecurityException("获取证书异常");
        } finally {
            try {
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (Exception ex) {
            }
        }
    }

    public static X509Certificate base64StrToCert(String base64Cert) throws GeneralSecurityException {
        try {
            CertificateFactory factory = CertificateFactory.getInstance("X.509");
            ByteArrayInputStream streamCert = new ByteArrayInputStream(new BASE64Decoder().decodeBuffer(base64Cert));
            X509Certificate cert = (X509Certificate) factory.generateCertificate(streamCert);
            if (null == cert) {
                throw new GeneralSecurityException("将cer从base64转换为对象失败");
            }
            return cert;
        } catch (IOException ex) {
            throw new GeneralSecurityException("将cer从base64转换为对象失败", ex);
        }
    }
}
