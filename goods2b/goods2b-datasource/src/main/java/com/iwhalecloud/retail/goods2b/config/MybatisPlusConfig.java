package com.iwhalecloud.retail.goods2b.config;

import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.extension.incrementer.OracleKeyGenerator;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.iwhalecloud.retail.goods2b.interceptor.MysqlKeyGenerator;
import com.iwhalecloud.retail.goods2b.interceptor.TelDbKeyGenerator;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@MapperScan("com.baomidou.cloud.service.*.mapper*")
@MapperScan("com.iwhalecloud.retail.goods2b.mapper.*")
public class MybatisPlusConfig {

    @Value("${db-type}")
    private String dbType;

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Bean
    public IKeyGenerator getKeyGenerator(){
        if (DB_TYPE_TELDB.equalsIgnoreCase(dbType)) {
            return new TelDbKeyGenerator();
        } else if (DB_TYPE_Oracle.equalsIgnoreCase(dbType)) {
            return new OracleKeyGenerator();
        }

        return new MysqlKeyGenerator();
    }

    public static final String DB_TYPE_MYSQL = "MySql";

    public static final String DB_TYPE_TELDB = "TelDB";

    public static final String DB_TYPE_Oracle = "Oracle";

//    @Bean
//    public OracleKeyGenerator oracleKeyGenerator(){
//        return new OracleKeyGenerator();
//    }
//
//    @Bean
//    public SourceFromInterceptor mybatisSqlInterceptor() {
//        return new SourceFromInterceptor();
//    }
}
