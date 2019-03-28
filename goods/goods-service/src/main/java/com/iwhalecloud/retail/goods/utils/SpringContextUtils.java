package com.iwhalecloud.retail.goods.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * @author Z
 * @date 2018/12/4
 */
@Service
public class SpringContextUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;


    /**
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtils.applicationContext = applicationContext;
    }


    /**
     * 获得spring上下文
     * @return ApplicationContext spring上下文
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }


    /**
     * 获取bean
     * @param name service注解方式name为小驼峰格式
     * @return  Object bean的实例对象
     */
    public static Object getBean(String name) throws BeansException {
        return applicationContext.getBean(name);
    }
}
