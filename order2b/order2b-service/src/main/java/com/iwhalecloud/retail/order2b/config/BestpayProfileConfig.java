package com.iwhalecloud.retail.order2b.config;

import com.iwhalecloud.retail.order2b.util.CertUtils;
import com.iwhalecloud.retail.order2b.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;


@Slf4j
@Configuration
public class BestpayProfileConfig {
    @Bean
    @Profile("local")
    public BestpayCertificate localCertificate(){
        PropertiesUtil propertiesUtil=new PropertiesUtil("properties/local/bestpay.properties");
        String keyStorePath=propertiesUtil.get("jksPath");
        String keyStorePwd=propertiesUtil.get("jksToken");
        String alias=propertiesUtil.get("jksAlias");
        String sBase64Cert=propertiesUtil.get("BestpayPublicKey");
        PrivateKey merchantPrivateKey=null;
        PublicKey merchantPublicKey=null;
        PublicKey bestpayPublicKey=null;
        try {
            KeyStore ks = CertUtils.getKeyStore(keyStorePath, keyStorePwd);
            java.security.cert.Certificate certificate= ks.getCertificate(alias);
            merchantPrivateKey=(PrivateKey)ks.getKey(alias,keyStorePwd.toCharArray());
            merchantPublicKey=certificate.getPublicKey();
            X509Certificate cert=CertUtils.base64StrToCert(sBase64Cert);
            bestpayPublicKey=cert.getPublicKey();

        }catch (GeneralSecurityException e) {
            e.printStackTrace();
            return null;
        }
        return new BestpayCertificate(merchantPrivateKey,merchantPublicKey,bestpayPublicKey);
    }

    @Bean
    @Profile("test")
    public BestpayCertificate testCertificate(){
        PropertiesUtil propertiesUtil=new PropertiesUtil("properties/test/bestpay.properties");
        String keyStorePath=propertiesUtil.get("jksPath");
        String keyStorePwd=propertiesUtil.get("jksToken");
        String alias=propertiesUtil.get("jksAlias");
        String sBase64Cert=propertiesUtil.get("BestpayPublicKey");
        PrivateKey merchantPrivateKey=null;
        PublicKey merchantPublicKey=null;
        PublicKey bestpayPublicKey=null;
        try {
            KeyStore ks = CertUtils.getKeyStore(keyStorePath, keyStorePwd);
            java.security.cert.Certificate certificate= ks.getCertificate(alias);
            merchantPrivateKey=(PrivateKey)ks.getKey(alias,keyStorePwd.toCharArray());
            merchantPublicKey=certificate.getPublicKey();
            X509Certificate cert=CertUtils.base64StrToCert(sBase64Cert);
            bestpayPublicKey=cert.getPublicKey();

        }catch (GeneralSecurityException e) {
            e.printStackTrace();
            return null;
        }
        return new BestpayCertificate(merchantPrivateKey,merchantPublicKey,bestpayPublicKey);
    }

    @Bean
    @Profile("dev")
    public BestpayCertificate devCertificate(){
        PropertiesUtil propertiesUtil=new PropertiesUtil("properties/dev/bestpay.properties");
        String keyStorePath=propertiesUtil.get("jksPath");
        String keyStorePwd=propertiesUtil.get("jksToken");
        String alias=propertiesUtil.get("jksAlias");
        String sBase64Cert=propertiesUtil.get("BestpayPublicKey");
        PrivateKey merchantPrivateKey=null;
        PublicKey merchantPublicKey=null;
        PublicKey bestpayPublicKey=null;
        try {
            KeyStore ks = CertUtils.getKeyStore(keyStorePath, keyStorePwd);
            java.security.cert.Certificate certificate= ks.getCertificate(alias);
            merchantPrivateKey=(PrivateKey)ks.getKey(alias,keyStorePwd.toCharArray());
            merchantPublicKey=certificate.getPublicKey();
            X509Certificate cert=CertUtils.base64StrToCert(sBase64Cert);
            bestpayPublicKey=cert.getPublicKey();

        }catch (GeneralSecurityException e) {
            e.printStackTrace();
            return null;
        }
        return new BestpayCertificate(merchantPrivateKey,merchantPublicKey,bestpayPublicKey);
    }

    @Bean
    @Profile("prod")
    public BestpayCertificate prodCertificate(){
        PropertiesUtil propertiesUtil=new PropertiesUtil("properties/prod/bestpay.properties");
        String keyStorePath=propertiesUtil.get("jksPath");
        String keyStorePwd=propertiesUtil.get("jksToken");
        String alias=propertiesUtil.get("jksAlias");
        String sBase64Cert=propertiesUtil.get("BestpayPublicKey");
        PrivateKey merchantPrivateKey=null;
        PublicKey merchantPublicKey=null;
        PublicKey bestpayPublicKey=null;
        try {
            KeyStore ks = CertUtils.getKeyStore(keyStorePath, keyStorePwd);
            java.security.cert.Certificate certificate= ks.getCertificate(alias);
            merchantPrivateKey=(PrivateKey)ks.getKey(alias,keyStorePwd.toCharArray());
            merchantPublicKey=certificate.getPublicKey();
            X509Certificate cert=CertUtils.base64StrToCert(sBase64Cert);
            bestpayPublicKey=cert.getPublicKey();
        }catch (GeneralSecurityException e) {
            e.printStackTrace();
            return null;
        }
        return new BestpayCertificate(merchantPrivateKey,merchantPublicKey,bestpayPublicKey);
    }

    @Bean
    @Profile("order")
    public BestpayCertificate orderCertificate(){
        PropertiesUtil propertiesUtil=new PropertiesUtil("properties/order/bestpay.properties");
        String keyStorePath=propertiesUtil.get("jksPath");
        String keyStorePwd=propertiesUtil.get("jksToken");
        String alias=propertiesUtil.get("jksAlias");
        String sBase64Cert=propertiesUtil.get("BestpayPublicKey");
        PrivateKey merchantPrivateKey=null;
        PublicKey merchantPublicKey=null;
        PublicKey bestpayPublicKey=null;
        try {
            KeyStore ks = CertUtils.getKeyStore(keyStorePath, keyStorePwd);
            java.security.cert.Certificate certificate= ks.getCertificate(alias);
            merchantPrivateKey=(PrivateKey)ks.getKey(alias,keyStorePwd.toCharArray());
            merchantPublicKey=certificate.getPublicKey();
            X509Certificate cert=CertUtils.base64StrToCert(sBase64Cert);
            bestpayPublicKey=cert.getPublicKey();
        }catch (GeneralSecurityException e) {
            log.error("BestpayProfileConfig.orderCertificate is error  ", e);
            e.printStackTrace();
            return null;
        } finally {
            log.info("BestpayProfileConfig.orderCertificate bestpayPublicKey={}", bestpayPublicKey);
        }
        return new BestpayCertificate(merchantPrivateKey,merchantPublicKey,bestpayPublicKey);
    }
}
