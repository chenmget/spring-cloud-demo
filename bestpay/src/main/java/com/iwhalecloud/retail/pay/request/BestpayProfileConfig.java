package com.iwhalecloud.retail.pay.request;

import com.iwhalecloud.retail.pay.util.CertUtils;
import com.iwhalecloud.retail.pay.util.PropertiesUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

@Configuration
public class BestpayProfileConfig
{
    @Value("${pay.jksPath}")
    private String jksPath;

    @Value("${pay.jksToken}")
    private String jksToken;

    @Value("${pay.jksAlias}")
    private String jksAlias;

    @Value("${pay.BestpayPublicKey}")
    private String BestpayPublicKey;

    @Bean
    @Profile({"bestpay"})
    public BestpayCertificate stockCertificate()
    {
        PrivateKey merchantPrivateKey = null;
        PublicKey merchantPublicKey = null;
        PublicKey bestpayPublicKey = null;
        try {
            KeyStore ks = CertUtils.getKeyStore(jksPath, jksToken);
            Certificate certificate = ks.getCertificate(jksAlias);
            merchantPrivateKey = (PrivateKey)ks.getKey(jksAlias, jksToken.toCharArray());
            merchantPublicKey = certificate.getPublicKey();
            X509Certificate cert = CertUtils.base64StrToCert(BestpayPublicKey);
            bestpayPublicKey = cert.getPublicKey();
        }
        catch (GeneralSecurityException e) {
            e.printStackTrace();
            return null;
        }
        return new BestpayCertificate(merchantPrivateKey, merchantPublicKey, bestpayPublicKey);
    }
}