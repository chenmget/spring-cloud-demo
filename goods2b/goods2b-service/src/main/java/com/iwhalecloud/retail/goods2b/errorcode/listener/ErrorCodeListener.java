package com.iwhalecloud.retail.goods2b.errorcode.listener;

import com.iwhalecloud.retail.annotation.ErrorCodeAnnotation;
import com.iwhalecloud.retail.goods2b.errorcode.ErrorCodeInitHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Description: 异常编码初始化监听器
 * @author: Z
 * @date: 2019/7/1 15:26
 */
@Component
@Slf4j
public class ErrorCodeListener implements ApplicationListener<ContextRefreshedEvent> {

    @Resource
    private ErrorCodeInitHelper errorCodeInitHelper;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info(">>>>>>>>>>>>>>>>>>ErrorCodeListener init start>>>>>>>>>>>>>>>>>>");
        // 根容器为Spring容器
        if (event.getApplicationContext().getParent() == null) {
            Map<String, Object> beans = event.getApplicationContext().getBeansWithAnnotation(ErrorCodeAnnotation.class);

            errorCodeInitHelper.initErrorCodes(beans.values());
        }
        log.info(">>>>>>>>>>>>>>>>>>ErrorCodeListener init end>>>>>>>>>>>>>>>>>>");
    }
}
