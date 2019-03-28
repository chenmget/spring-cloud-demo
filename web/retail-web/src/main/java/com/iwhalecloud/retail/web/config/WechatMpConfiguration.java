package com.iwhalecloud.retail.web.config;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * wechat mp configuration
 *
 * @author Binary Wang(https://github.com/binarywang)
 */
//@ConditionalOnClass 标注在类上面，表示该类下面的所有@Bean都会启用配置，也可以标注在方法上面，只是对该方法启用配置
@Configuration
@ConditionalOnClass(WxMpService.class)
@EnableConfigurationProperties(WechatMpProperties.class)
public class WechatMpConfiguration {
    @Autowired
    private WechatMpProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public WxMpConfigStorage configStorage() {
        WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();
        
        String appId = this.properties.getAppId();
        String secret = this.properties.getSecret();
        String token = this.properties.getToken();
        String aesKey = this.properties.getAesKey();
        
        System.out.println("微信配置参数==============Start");
        System.out.println("appId=>" + appId + ", secret=>" + secret + ", token=>" + token + ", aesKey=>" + aesKey);
        System.out.println("微信配置参数==============End");
        
        configStorage.setAppId(appId);
        configStorage.setSecret(secret);
        configStorage.setToken(token);
        configStorage.setAesKey(aesKey);
        
        return configStorage;
    }

    @Bean
    @ConditionalOnMissingBean
    public WxMpService wxMpService(WxMpConfigStorage configStorage) {
        WxMpService wxMpService = new me.chanjar.weixin.mp.api.impl.WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(configStorage);
        return wxMpService;
    }
}
