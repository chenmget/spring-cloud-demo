package com.iwhalecloud.retail.workflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class WorkFlowServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkFlowServiceApplication.class, args);
	}
}
