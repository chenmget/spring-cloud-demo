package com.iwhalecloud.retail.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.config.ConsumerConfig;

@Configuration
public class DubboConfiguration {

	/**
	 * 消费者配置不主动监督zookeeper服务
	 *
	 * @return
	 */
	@Bean
	public ConsumerConfig consumerConfig() {
	   ConsumerConfig consumerConfig = new ConsumerConfig();
	   consumerConfig.setCheck(false);
	   consumerConfig.setTimeout(15000);
	   consumerConfig.setRetries(0); 	//不重发请求
	   consumerConfig.setFilter("sourceFromfilter");
	   return consumerConfig;
	}
}
