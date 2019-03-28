package com.iwhalecloud.retail.order2b.util;

import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

@Slf4j
public class CertUtils {

    private static File getCertFile(String path) throws GeneralSecurityException {
        //File file = new File("/NRetail/server.jks");
        File file = new File(CertUtils.class.getClassLoader().getResource(path).getFile());
        if (!file.exists()) {
            throw new GeneralSecurityException("证书文件不存在：" + path);
        }
        return file;
    }

    public static KeyStore getKeyStore(String keyStorePath, String keyStorePwd) throws GeneralSecurityException {
        log.info("CertUtils.CertUtils keyStorePath{} ", keyStorePath);
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        InputStream fs = null;
        try {
            //fs = new FileInputStream(file);
            fs = CertUtils.class.getClassLoader().getResourceAsStream(keyStorePath);
            ks.load(fs, keyStorePwd.toCharArray());
        } catch (IOException ex) {
            log.error("CertUtils.CertUtils error is {}", ex);
            throw new GeneralSecurityException("获取证书异常");
        } finally {
            try {
                if (fs != null) {
                    fs.close();
                }
            } catch (Exception ex) {
                //ignore..
                log.error("CertUtils.getKeyStore Exception ex {}", ex);
            }
        }
        return ks;
    }

    public static X509Certificate getCertFromCer(String cerFilePath) throws GeneralSecurityException {
        CertificateFactory f = CertificateFactory.getInstance("X.509");
        File file = getCertFile(cerFilePath);
        InputStream fs = null;
        try {
            fs = new FileInputStream(file);
            X509Certificate certificate = (X509Certificate) f
                    .generateCertificate(fs);
            return certificate;
        } catch (IOException ex) {
            throw new GeneralSecurityException("获取证书异常");
        } finally {
            try {
                if (fs != null) {
                    fs.close();
                }
            } catch (Exception ex) {
                //ignore..
            }
        }
    }

    public static X509Certificate base64StrToCert(String base64Cert)
            throws GeneralSecurityException {
        try {
            CertificateFactory factory = CertificateFactory.getInstance("X.509");
            ByteArrayInputStream streamCert = new ByteArrayInputStream(
                    new BASE64Decoder().decodeBuffer(base64Cert));

            X509Certificate cert = (X509Certificate) factory.generateCertificate(streamCert);
            if (cert == null) {
                throw new GeneralSecurityException("将cer从base64转换为对象失败");
            }
            return cert;
        } catch (IOException ex) {
            throw new GeneralSecurityException("将cer从base64转换为对象失败", ex);
        }
    }
}
