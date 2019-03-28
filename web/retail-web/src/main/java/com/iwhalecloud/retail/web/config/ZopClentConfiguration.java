package com.iwhalecloud.retail.web.config;

import com.iwhalecloud.retail.oms.service.zop.ZopClientService;
import com.iwhalecloud.retail.oms.service.zop.ZopConfigStorage;
import com.iwhalecloud.retail.web.zop.ZopClientServiceImpl;
import com.iwhalecloud.retail.web.zop.ZopConfigStorageImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(ZopClientService.class)
@Slf4j
public class ZopClentConfiguration {

    @Value("${zop.appId}")
    private String appId;
    @Value("${zop.appSecret}")
    private String appSecret;
    @Value("${zop.url}")
    private String url;
    @Value("${zop.timeout}")
    private String timeout;

    @Bean
    @ConditionalOnMissingBean
    public ZopConfigStorage zopConfigStorage() {
        ZopConfigStorage configStorage = new ZopConfigStorageImpl();

        log.info("能开参数==============Start");
        log.info("appId={},appSecret={},url={},timeout={}",appId,appSecret,url,timeout);
        log.info("能开参数==============End");

        configStorage.setAppId(this.appId);
        configStorage.setAppSecret(this.appSecret);
        configStorage.setUrl(this.url);
        configStorage.setTimeout(Integer.parseInt(this.timeout));

        return configStorage;
    }

    @Bean
    @ConditionalOnMissingBean
    public ZopClientService zopClientService(ZopConfigStorage configStorage) {
        ZopClientService zopClientService = new ZopClientServiceImpl();
        zopClientService.setZopConfigStorage(configStorage);
        return zopClientService;
    }
}
