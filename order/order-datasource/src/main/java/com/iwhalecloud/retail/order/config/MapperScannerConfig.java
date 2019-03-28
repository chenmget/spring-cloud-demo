//package com.iwhalecloud.retail.order.config;
//
//import org.mybatis.spring.mapper.MapperScannerConfigurer;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.AutoConfigureAfter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@AutoConfigureAfter(MybatisPlusConfig.class)
//public class MapperScannerConfig {
//
//    @Value("${spring.mybatis.scan.package:com.iwhalecloud.retail.order.mapper}")
//    private String basePackages;
//
//    @Bean
//    public MapperScannerConfigurer mapperScannerConfigurer() {
//        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
//        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
//        mapperScannerConfigurer.setBasePackage("com.iwhalecloud.retail.order.mapper;com.iwhalecloud.retail.cart.mapper");
//        return mapperScannerConfigurer;
//    }
//
//
//}
