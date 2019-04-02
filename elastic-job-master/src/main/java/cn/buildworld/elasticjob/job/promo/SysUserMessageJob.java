package cn.buildworld.elasticjob.job.promo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.elasticjob.lite.annotation.ElasticSimpleJob;
import com.iwhalecloud.retail.promo.service.MarketingActivityService;
import com.iwhalecloud.retail.system.service.SysUserMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author li.yulong
 * @date 2019/4/2 21:33
 */
@ElasticSimpleJob(cron = "0 0 1 * * ? ",
        jobName = "sysUserMessageUpdateJob",
        shardingTotalCount = 1,
        jobParameter = "测试参数",
        shardingItemParameters = "0=A,1=B",
        dataSource = "datasource")
@Component
@Slf4j
public class SysUserMessageJob implements SimpleJob {

    @Reference
    private SysUserMessageService sysUserMessageService;

    @Reference
    private MarketingActivityService marketingActivityService;

    @Override
    public void execute(ShardingContext shardingContext) {
        if (sysUserMessageService == null) {
            return ;
        }

        long startTime = System.currentTimeMillis();
        log.info("SysUserMessageJob start-->startTime={}", startTime);
        sysUserMessageService.updateSysUserMessage();
        marketingActivityService.notifyMerchantActivityOrderDelivery();
        long endTime = System.currentTimeMillis();
        log.info("SysUserMessageJob end-->endTime={}",endTime);
    }
}
