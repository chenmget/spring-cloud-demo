//package com.iwhalecloud.retail.oms.quartz.spring;
//
//
//import org.quartz.spi.TriggerFiredBundle;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
//import org.springframework.scheduling.quartz.AdaptableJobFactory;
//import org.springframework.stereotype.Component;
//
//@Component
//public class OmsJobFactory extends AdaptableJobFactory {
//
//    @Autowired
//    private AutowireCapableBeanFactory capableBeanFactory;
//
//    @Override
//    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
//        Object jobInstance = super.createJobInstance(bundle);
//        capableBeanFactory.autowireBean(jobInstance); //这一步解决不能spring注入bean的问题
//        return jobInstance;
//    }
//
//}
