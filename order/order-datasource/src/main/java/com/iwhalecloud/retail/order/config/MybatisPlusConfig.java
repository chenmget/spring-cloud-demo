package com.iwhalecloud.retail.order.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@MapperScan("com.iwhalecloud.retail.order.mapper.*")
public class MybatisPlusConfig {

//    @Autowired
//    private DataSource dataSource;
//
//    @Value("${spring.mybatis.mappers:classpath:order/mapper/*.xml}")
//    private String mappers;

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Bean(value = "mysqlKeyGenerator")
    public WhaleCloudKeyGenerator mysqlKeyGenerator() {
        WhaleCloudKeyGenerator keyGenerator=new WhaleCloudKeyGenerator();
        return keyGenerator;
    }

//    @Bean(value = "sqlSessionFactory")
//    public SqlSessionFactoryBean sqlSessionFactory() throws IOException {
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        // 设置数据源
//        sqlSessionFactoryBean.setDataSource(dataSource);
//        Resource[] resources = new Resource[]{};
//
//        for (String item : mappers.split(",")) {
//            Resource[] itemResource = new PathMatchingResourcePatternResolver().getResources(item);
//            resources = ArrayUtils.addAll(resources, itemResource);
//        }
//
//        sqlSessionFactoryBean.setMapperLocations(resources);
//        return sqlSessionFactoryBean;
//    }


}

