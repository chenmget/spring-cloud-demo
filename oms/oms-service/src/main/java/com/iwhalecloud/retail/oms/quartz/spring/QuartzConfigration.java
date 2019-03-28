//package com.iwhalecloud.retail.oms.quartz.spring;
//
//import org.quartz.Scheduler;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.config.PropertiesFactoryBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.scheduling.quartz.SchedulerFactoryBean;
//
//import javax.sql.DataSource;
//import java.io.IOException;
//import java.util.Properties;
//
//
//@Configuration
//public class QuartzConfigration {
//
//    @Autowired
//    private OmsJobFactory omsJobFactory;  //自定义的factory
//
//    @Autowired
//    private DataSource dataSource;
//
//    //获取工厂bean
//    @Bean
//    public SchedulerFactoryBean schedulerFactoryBean() {
//        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
//        try {
//            schedulerFactoryBean.setQuartzProperties(quartzProperties());
//            schedulerFactoryBean.setJobFactory(omsJobFactory);
//            //创建SchedulerFactoryBean
//            //使用数据源
//            schedulerFactoryBean.setDataSource(dataSource);
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return schedulerFactoryBean;
//    }
//    //指定quartz.properties
//    @Bean
//    public Properties quartzProperties() throws IOException {
//        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
//        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz/quartz.properties"));
//        propertiesFactoryBean.afterPropertiesSet();
//        return propertiesFactoryBean.getObject();
//    }
//
//    //创建schedule
//    @Bean(name = "scheduler")
//    public Scheduler scheduler() {
//        return schedulerFactoryBean().getScheduler();
//    }
//}
