package cn.buildworld.elasticjob.job.warehouse;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.elasticjob.lite.annotation.ElasticSimpleJob;
import com.iwhalecloud.retail.warehouse.service.ResourceInstStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 串码入库，与ITMS集成
 * <p>
 * Created by jiyou on 2019/4/24.
 */
@ElasticSimpleJob(cron = "0 0/30 * * * ?",
        jobName = "SyncMktToITMSJob",
        shardingTotalCount = 1,
        jobParameter = "任务参数",
        shardingItemParameters = "0=A,1=B",
        dataSource = "datasource")
@Component
@Slf4j
public class SyncMktToITMSJob implements SimpleJob {

    @Reference(timeout = 10000)
    ResourceInstStoreService resourceInstStoreService;

    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("SyncMktToITMSJob start.....");
        if (resourceInstStoreService == null) {
            log.info("SyncMktToITMSJob error mktResStoreTempService is null");
            return;
        }
        try {
//            resourceInstStoreService.syncMktToITMS();
        } catch (RuntimeException e) {
            log.error("串码入库，与ITMS集成", e);
        } catch (Exception e) {
            log.error("串码入库，与ITMS集成", e);
        }

        log.info("SyncMktToITMSJob end.....");
    }
}