package com.chenm.service;

import com.chenm.controller.HelloController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @program: spring-cloud-demo
 * @description:
 * @author: cm
 * @create: 2020-03-25 21:09
 **/
@Component
public class ApplicationRunnerImpl implements ApplicationRunner {
    @Autowired
    private HelloController helloController;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("通过实现ApplicationRunner接口，在spring boot项目启动后打印参数");
        System.out.println(helloController.getValue());
    }


}
