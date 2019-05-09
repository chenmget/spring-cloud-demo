package cn.buildworld.elasticjob.job.partner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.elasticjob.lite.annotation.ElasticSimpleJob;
import com.iwhalecloud.retail.partner.service.PartnerJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 渠道信息商家信息同步定时器
 *
 * @author xuqinyuan
 */
@ElasticSimpleJob(cron = "0 50 16 * * ?",
    jobName = "syncMerchantJob",
    shardingTotalCount = 1,
    jobParameter = "测试参数",
    shardingItemParameters = "0=A,1=B",
    dataSource = "datasource")
@Component
@Slf4j
public class SyncMerchantJob implements SimpleJob {

    @Reference(timeout = 10000)
    PartnerJobService partnerJobService;

    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("----->>  SyncMerchantJob start ..... ");
        if (partnerJobService == null) {
            return;
        }
        try {
            partnerJobService.syncMerchant();
        } catch (Exception e) {
            log.error("SyncMerchantJob exceute exception", e);
        }
        log.info("----->>  SyncMerchantJob end ..... ");
    }
}