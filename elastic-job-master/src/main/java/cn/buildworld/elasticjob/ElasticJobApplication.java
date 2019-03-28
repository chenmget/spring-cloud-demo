package cn.buildworld.elasticjob;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@Slf4j
@EnableJpaAuditing
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class ElasticJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElasticJobApplication.class, args);
    }
}
