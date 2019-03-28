package com.iwhalecloud.retail.oms.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.redis")
@Data
public class RedisProperties {
    private int    expireSeconds;
    @Value("${spring.redis.cluster.nodes}")
    private String  clusterNodes;
    private int    commandTimeout;
    @Value("${spring.redis.password}")
    private String  password;
}
