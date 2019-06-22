package cn.buildworld.elasticjob.job.warehouse;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.elasticjob.lite.annotation.ElasticSimpleJob;
import com.iwhalecloud.retail.warehouse.service.ResouceStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 渠道视图经营主体信息同步定时器
 *
 * @author xuqinyuan
 */
@ElasticSimpleJob(cron = "0 0 2 * * ?",
    jobName = "syncStoreJob",
    shardingTotalCount = 1,
    jobParameter = "测试参数",
    shardingItemParameters = "0=A,1=B",
    dataSource = "datasource")
@Component
@Slf4j
public class SyncStoreJob implements SimpleJob {

    @Reference(timeout = 10000)
    ResouceStoreService resouceStoreService;

    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("SyncStoreJob.execute");
        if (resouceStoreService == null) {
            return;
        }
        log.info("SyncStoreJob.execute begin");
        try {
            resouceStoreService.initStoredata();
        } catch (Exception e) {
            log.error("SyncStoreJob exceute exception", e);
        }
        log.info("SyncStoreJob.execute end");
    }
}