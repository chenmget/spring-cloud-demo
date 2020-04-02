package com.chenm.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: spring-cloud-demo
 * @description:
 * @author: cm
 * @create: 2020-03-25 21:04
 **/
@RestController
public class HelloController {
    @Value("${neo.hello}")
    private String hello;

    @RequestMapping("/hello")
    public String from() {
        return this.hello;
    }

    public String getValue(){
        return hello;
    }
}
