package com.iwhalecloud.retail.pay.util;

import org.springframework.util.ResourceUtils;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class CertUtils
{
    public static KeyStore getKeyStore(String keyStorePath, String keyStorePwd) throws GeneralSecurityException
    {
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        InputStream fs = null;
        try {
            File file = ResourceUtils.getFile(keyStorePath);
            fs = new FileInputStream(file);
            ks.load(fs, keyStorePwd.toCharArray());
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new GeneralSecurityException("获取证书异常");
        } finally {
            try {
                if (fs != null) {
                    fs.close();
                }
            }
            catch (Exception localException1)
            {
            }
        }
        return ks;
    }

    public static X509Certificate getCertFromCer(String cerFilePath) throws GeneralSecurityException {
        CertificateFactory f = CertificateFactory.getInstance("X.509");
        InputStream fs = null;
        try {
            File file = ResourceUtils.getFile(cerFilePath);
            fs = new FileInputStream(file);

            X509Certificate certificate = (X509Certificate)f
                    .generateCertificate(fs);

            X509Certificate localX509Certificate1 = certificate;
            return localX509Certificate1;
        }
        catch (IOException ex)
        {
            throw new GeneralSecurityException("获取证书异常");
        } finally {
            try {
                if (fs != null) {
                    fs.close();
                }
            } catch (Exception localException1) {
            }
        }
    }

    public static X509Certificate base64StrToCert(String base64Cert) throws GeneralSecurityException{
        try
        {
            CertificateFactory factory = CertificateFactory.getInstance("X.509");
            ByteArrayInputStream streamCert = new ByteArrayInputStream(new BASE64Decoder().decodeBuffer(base64Cert));
            X509Certificate cert = (X509Certificate)factory.generateCertificate(streamCert);
            if (cert == null) {
                throw new GeneralSecurityException("将cer从base64转换为对象失败");
            }
            return cert;
        }
        catch (IOException ex) {
        }
        return null;
    }
}
