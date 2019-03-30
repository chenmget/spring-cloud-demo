package com.iwhalecloud.retail.order2b.config;

import com.iwhalecloud.retail.order2b.model.BestpayConfigModel;
import com.iwhalecloud.retail.order2b.util.CertUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Properties;

@Slf4j
@Configuration
public class BestpayLoadConfig {

    @Value("${pay.certificateFile}")
    private String certificateFile;

    @Bean
    public BestpayConfigModel orderCertificate() {
        try {
            File file = ResourceUtils.getFile(certificateFile);
            InputStream in  = new FileInputStream(file);
            Properties props = new Properties();
            InputStreamReader inputStream = new InputStreamReader(in, "UTF-8");
            props.load(inputStream);
            String keyStorePath = (String)props.get("jksPath");
            String keyStorePwd = (String)props.get("jksToken");
            String alias = (String)props.get("jksAlias");
            String base64Cert = (String)props.get("BestpayPublicKey");
            KeyStore keyStore = CertUtils.getKeyStore(keyStorePath, keyStorePwd);
            java.security.cert.Certificate certificate = keyStore.getCertificate(alias);
            PrivateKey merchantPrivateKey = (PrivateKey) keyStore.getKey(alias, keyStorePwd.toCharArray());
            PublicKey merchantPublicKey = certificate.getPublicKey();
            X509Certificate cert = CertUtils.base64StrToCert(base64Cert);
            PublicKey bestpayPublicKey = cert.getPublicKey();
            BestpayConfigModel bestpayConfigModel = new BestpayConfigModel();
            bestpayConfigModel.setAsynNoticeUrl((String)props.getProperty("ASYNNOTICEURL"));
            bestpayConfigModel.setBestpayPublicKey(bestpayPublicKey);
            bestpayConfigModel.setMerchantPrivateKey(merchantPrivateKey);
            bestpayConfigModel.setOrgLoginCode((String)props.getProperty("ORGLOGINCODE"));
            bestpayConfigModel.setPlatCode((String)props.getProperty("PLATCODE"));
            bestpayConfigModel.setSynNoticeUrl((String)props.getProperty("SYNNOTICEURL"));
            bestpayConfigModel.setUrl((String)props.getProperty("URL"));
            bestpayConfigModel.setBestpayPublicKey(merchantPublicKey);
            return bestpayConfigModel;
        } catch (Exception e) {
            log.error("BestpayProfileConfig.orderCertificate is error ", e);
            return null;
        }
    }
}
