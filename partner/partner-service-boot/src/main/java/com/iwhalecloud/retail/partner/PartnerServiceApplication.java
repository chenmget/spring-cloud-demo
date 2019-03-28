package com.iwhalecloud.retail.partner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class PartnerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PartnerServiceApplication.class, args);
	}
}
