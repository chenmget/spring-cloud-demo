package cn.buildworld.elasticjob.config;

import com.alibaba.dubbo.config.ConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
	   return consumerConfig;
	}
}
