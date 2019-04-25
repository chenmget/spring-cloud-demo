package cn.buildworld.elasticjob.job.order2b;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.elasticjob.lite.annotation.ElasticSimpleJob;
import com.iwhalecloud.retail.order2b.service.AdvanceOrderOpenService;
import com.iwhalecloud.retail.pay.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 定时取消未按时支付的预售单（超时未付定金和超时未付尾款的）
 * 每5分钟执行一次
 * @author z
 */
@ElasticSimpleJob(cron = "0 0/5 * * * ? ",
        jobName = "firstJob",
        shardingTotalCount = 1,
        jobParameter = "测试参数",
        shardingItemParameters = "0=A,1=B",
        dataSource = "datasource")
@Component
@Slf4j
public class AdvanceOrderCancelJob implements SimpleJob {

    @Reference
    AdvanceOrderOpenService advanceOrderOpenService;

    @Override
    public void execute(ShardingContext shardingContext) {
        if (advanceOrderOpenService == null) {
            return ;
        }

        long startTime = System.currentTimeMillis();
        log.info("AdvanceOrderCancelJob start-->startTime={}", startTime);
        try {
            advanceOrderOpenService.cancelOverTimePayOrder();
        } catch (Exception e) {
            log.error("AdvanceOrderCancelJob exceute exception", e);
        }
        long endTime = System.currentTimeMillis();
        log.info("AdvanceOrderCancelJob end-->endTime={}",endTime);
    }
}