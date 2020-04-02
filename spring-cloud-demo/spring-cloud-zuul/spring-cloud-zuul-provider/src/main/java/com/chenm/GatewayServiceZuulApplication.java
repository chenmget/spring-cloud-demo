package com.chenm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @program: spring-cloud-demo
 * @description:
 * @author: cm
 * @create: 2020-03-25 22:37
 **/
@SpringBootApplication
@EnableZuulProxy
public class GatewayServiceZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceZuulApplication.class, args);
    }
}
