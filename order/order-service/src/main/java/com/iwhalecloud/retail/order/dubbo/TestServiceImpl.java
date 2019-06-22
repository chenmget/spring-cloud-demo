package com.iwhalecloud.retail.order.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.order.dto.TestUserDTO;
import com.iwhalecloud.retail.order.entity.UserCopyTest;
import com.iwhalecloud.retail.order.manager.TestUserManger;
import com.iwhalecloud.retail.order.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@Service
public class TestServiceImpl implements TestService {

    public String sayHello(String msg) {
        return "hello " + msg;
    }

    @Autowired
    private TestUserManger testUserManger;


    @Override
    public String testMabatis() {
        List<TestUserDTO> list = testUserManger.testFindUser();
        log.info("service list{}", JSON.toJSONString(list));
        return JSON.toJSONString(list);
    }

    @Override
    public String testH2Mapper() {
        List<UserCopyTest> list = testUserManger.testSelect();
        log.info("service list{}", JSON.toJSONString(list));
        return JSON.toJSONString(list);
    }
}
