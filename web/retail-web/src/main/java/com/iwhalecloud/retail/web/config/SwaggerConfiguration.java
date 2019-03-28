package com.iwhalecloud.retail.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableWebMvc
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //修正Byte转string的Bug
                .directModelSubstitute(Byte.class, Integer.class)
                .select()
                //为当前包路径
                .apis(RequestHandlerSelectors.basePackage("com.iwhalecloud.retail"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("retail RESTful API")
                //描述
                .description("API 描述")
                //创建人
                .contact(new Contact("retail", "http://www.baidu.com", ""))
                //版本号
                .version("1.0")
                .build();
    }


}