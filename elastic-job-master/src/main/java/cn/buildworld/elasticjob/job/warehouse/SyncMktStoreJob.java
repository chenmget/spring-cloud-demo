package cn.buildworld.elasticjob.job.warehouse;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.elasticjob.lite.annotation.ElasticSimpleJob;
import com.iwhalecloud.retail.warehouse.service.MktResStoreTempService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 仓库信息同步定时器:凌晨4点同步
 *
 * @author wuliangyong
 */
@ElasticSimpleJob(cron = "0 0 4 * * ?",
    jobName = "syncMktStoreJob",
    shardingTotalCount = 1,
    jobParameter = "测试参数",
    shardingItemParameters = "0=A,1=B",
    dataSource = "datasource")
@Component
@Slf4j
public class SyncMktStoreJob implements SimpleJob {

    @Reference(timeout = 10000)
    MktResStoreTempService mktResStoreTempService;

    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("SyncMktStoreJob start.....");
        if(mktResStoreTempService==null){
            log.info("SyncMktStoreJob error mktResStoreTempService is null");
            return;
        }
        try{
            mktResStoreTempService.synTempToMktResStore();
        }catch (RuntimeException e){
            log.error("同步营销资源库存",e);
        }catch (Exception e){
            log.error("同步营销资源库存",e);
        }

        log.info("SyncMktStoreJob end.....");
    }
}