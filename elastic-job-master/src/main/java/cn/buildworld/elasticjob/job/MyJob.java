package cn.buildworld.elasticjob.job;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.elasticjob.lite.annotation.ElasticSimpleJob;
import com.iwhalecloud.retail.pay.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@ElasticSimpleJob(cron = "0/2 * * * * ?",
        jobName = "firstJob",
        shardingTotalCount = 1,
        jobParameter = "测试参数",
        shardingItemParameters = "0=A,1=B",
        dataSource = "datasource")
@Component
@Slf4j
public class MyJob implements SimpleJob {

    @Reference
    JobService jobService;

    @Override
    public void execute(ShardingContext shardingContext) {
    /*    if (jobService == null) {
            System.out.println("321");
            return ;
        }
        jobService.myJob();*/
        log.info("MyJob demo run...");
    }
}