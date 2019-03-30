package com.iwhalecloud.retail.goods2b;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class Goods2BServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(Goods2BServiceApplication.class, args);
	}
}
