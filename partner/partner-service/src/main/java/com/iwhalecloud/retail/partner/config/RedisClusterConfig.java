package com.iwhalecloud.retail.partner.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.MapPropertySource;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author z
 * @date 2019/3/19
 */
@Configuration
@EnableCaching
@ConfigurationProperties(prefix = "spring.redis")
public class RedisClusterConfig extends CachingConfigurerSupport {

    @Value("${spring.redis.cluster.nodes}")
    private String clusterNodes;
    @Value("${spring.redis.cluster.timeout}")
    private Long timeout;
    @Value("${spring.redis.cluster.max-redirects}")
    private String maxRedirects;
    private String password;

    /**
     * 初始化 RedisTemplate
     * Spring 使用 StringRedisTemplate 封装了 RedisTemplate 对象来进行对redis的各种操作，它支持所有的 redis 原生的 api。
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    @Bean(name = "redisTemplate")
    public RedisTemplate redisTemplate() {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(connectionFactory(getClusterConfiguration()));
        setSerializer(template);
        return template;
    }

    /**
     * Redis Cluster参数配置
     *
     * @return
     */
    public RedisClusterConfiguration getClusterConfiguration() {
        Map<String, Object> source = new HashMap<String, Object>();
        source.put("spring.redis.cluster.nodes", clusterNodes);
        source.put("spring.redis.cluster.timeout", timeout);
        source.put("spring.redis.cluster.max-redirects", maxRedirects);
        return new RedisClusterConfiguration(new MapPropertySource("RedisClusterConfiguration", source));
    }

    /**
     * 连接池设置
     *
     * @param configuration
     * @return
     */
    private RedisConnectionFactory connectionFactory(RedisClusterConfiguration configuration) {

        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(configuration);
        //如果需要设置密码
        if (!StringUtils.isEmpty(password)) {
            connectionFactory.setPassword(password);
        }
        connectionFactory.afterPropertiesSet();
        return connectionFactory;
    }

    /**
     * 序列化工具
     * 使用 Spring 提供的序列化工具替换 Java 原生的序列化工具，这样 ReportBean 不需要实现 Serializable 接口
     *
     * @param template
     */
    private void setSerializer(StringRedisTemplate template) {
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
    }

    /**
     * 生产key的策略
     *
     * @return
     */
    @Bean
    public KeyGenerator wiselyKeyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }


}
