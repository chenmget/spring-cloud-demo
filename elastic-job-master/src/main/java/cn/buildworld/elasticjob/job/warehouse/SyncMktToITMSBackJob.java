package cn.buildworld.elasticjob.job.warehouse;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.elasticjob.lite.annotation.ElasticSimpleJob;
import com.iwhalecloud.retail.warehouse.service.MktResItmsReturnRecService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 串码入库，ITMS集成回执
 * <p>
 * Created by  on 2019/4/24.
 */
@ElasticSimpleJob(cron = "0 0/30 * * * ?",
        jobName = "SyncMktToITMSBackJob",
        shardingTotalCount = 1,
        jobParameter = "任务参数",
        shardingItemParameters = "0=A,1=B",
        dataSource = "datasource")
@Component
@Slf4j
public class SyncMktToITMSBackJob implements SimpleJob {

    @Reference(timeout = 10000)
    MktResItmsReturnRecService mktResItmsReturnRecService;

    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("SyncMktToITMSBackJob start.....");
        if (mktResItmsReturnRecService == null) {
            log.info("SyncMktToITMSBackJob error mktResItmsReturnRecService is null");
            return;
        }
        try {
            mktResItmsReturnRecService.syncMktToITMSBack();
        } catch (RuntimeException e) {
            log.error("串码入库，ITMS集成回执", e);
        } catch (Exception e) {
            log.error("串码入库，ITMS集成回执", e);
        }

        log.info("SyncMktToITMSBackJob end.....");
    }
}
