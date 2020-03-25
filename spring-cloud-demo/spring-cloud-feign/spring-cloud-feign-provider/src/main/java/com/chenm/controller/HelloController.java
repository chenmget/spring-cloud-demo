package com.chenm.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: spring-cloud-demo
 * @description:
 * @author: cm
 * @create: 2020-03-25 13:49
 **/
@RestController
@RequestMapping("/api/")
public class HelloController {
    @RequestMapping("/hello")
    public String index(@RequestParam String name) {
        return "hello "+name+"，this is first messge";
    }
}
