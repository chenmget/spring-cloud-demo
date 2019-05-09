package cn.buildworld.elasticjob.job.partner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.elasticjob.lite.annotation.ElasticSimpleJob;
import com.iwhalecloud.retail.partner.service.PartnerJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 渠道视图经营主体信息同步定时器
 *
 * @author xuqinyuan
 */
@ElasticSimpleJob(cron = "0 30 11 * * ?",
    jobName = "syncBusinessEntityJob",
    shardingTotalCount = 1,
    jobParameter = "测试参数",
    shardingItemParameters = "0=A,1=B",
    dataSource = "datasource")
@Component
@Slf4j
public class SyncBusinessEntityJob implements SimpleJob {

    @Reference(timeout = 10000)
    PartnerJobService partnerJobService;

    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("------>> SyncBusinessEntityJob start......");
        if (partnerJobService == null) {
            return;
        }
        try {
            partnerJobService.syncBusinessEntity();
        } catch (Exception e) {
            log.error("SyncBusinessEntityJob exceute exception", e);
        }
        log.info("------>> SyncBusinessEntityJob end......");
    }
}