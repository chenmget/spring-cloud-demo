package com.iwhalecloud.retail.pay;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@Slf4j
public class PayApplication {

    public static void main(String[] args) {
        ApplicationContext context=SpringApplication.run(PayApplication.class, args);
        String[] actives=context.getEnvironment().getActiveProfiles();
        for(String active:actives){
            log.info("spring.profiles.active:"+active);
        }
    }

}
