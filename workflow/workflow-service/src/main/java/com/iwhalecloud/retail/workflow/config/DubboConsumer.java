package com.iwhalecloud.retail.workflow.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * dubbo动态消费者管理类
 * @author z
 */
@Component
@Slf4j
public class DubboConsumer {

    @Autowired
    ApplicationConfig applicationConfig;
    @Autowired
    RegistryConfig registryConfig;

    public static ConcurrentMap<String, ReferenceConfig> referenceConfig = new ConcurrentHashMap<>();

    private ReferenceConfig initConsumer( String className,String serviceGroup) throws ClassNotFoundException {
        ReferenceConfig reference = new ReferenceConfig<>();
        reference.setApplication(applicationConfig);
        reference.setRegistry(registryConfig);

        //如果有设置分组，则添加分组信息
        if (!StringUtils.isEmpty(serviceGroup)) {
            reference.setGroup(serviceGroup);
        }

        reference.setInterface(Class.forName(className));

        return reference;
    }

    /**
     * 根据类路径和分组获取消费者调用对象
     * @param className 类路径
     * @param serviceGroup 分组
     * @return
     */
    public ReferenceConfig getConsumer(String className,String serviceGroup) {
        final String key = className + "_" + serviceGroup;
        ReferenceConfig  reference= referenceConfig.get(key);
        if(reference==null){
            try {
            	log.info("------------------------------------------DubboConsumer.getConsumer-------className = -------------"+className);
                reference = initConsumer(className, serviceGroup);

                referenceConfig.put(key, reference);
            } catch (ClassNotFoundException e) {
                log.info("DubboConsumer.addConsumer  error ",e);
            }
        }
        return reference;
    }
}
