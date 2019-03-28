package com.iwhalecloud.retail.web.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableWebMvc
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
	
	@Bean
	WxUserInterceptor wxUserInterceptor() {
		return new WxUserInterceptor();
	}
	
    @Bean
    AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }
    
    


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	
    	registry.addInterceptor(wxUserInterceptor())
        	.addPathPatterns("/**");
    	
        registry.addInterceptor(authenticationInterceptor())
            .addPathPatterns("/**");

    }

}
