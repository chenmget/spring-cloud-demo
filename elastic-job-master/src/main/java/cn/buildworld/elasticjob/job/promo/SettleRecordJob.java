package cn.buildworld.elasticjob.job.promo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.elasticjob.lite.annotation.ElasticSimpleJob;
import com.iwhalecloud.retail.promo.dto.SettleRecordDTO;
import com.iwhalecloud.retail.promo.service.SettleRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * 生成前置补贴的结算数据
 *
 * @author pengxiang
 */
//每个月1号2点启动执行定时任务
@ElasticSimpleJob(cron = "0 0 2 1 * ?",
        jobName = "SettleRecordJob",
        shardingTotalCount = 1,
        jobParameter = "测试参数",
        shardingItemParameters = "0=A,1=B",
        dataSource = "datasource")
@Component
@Slf4j
public class SettleRecordJob implements SimpleJob {
    @Reference(timeout = 10000)
    private SettleRecordService settleRecordService;

    @Override
    public void execute(ShardingContext shardingContext) {
        Date startDate = new Date();
        log.info("SettleRecordJob run start.....{}", startDate);

        try {
            List<SettleRecordDTO> settleRecordDTOs = settleRecordService.getSettleRecord();
            if(!CollectionUtils.isEmpty(settleRecordDTOs)){
                settleRecordService.batchAddSettleRecord(settleRecordDTOs);
            }
        }catch (Exception ex) {
            log.error("SettleRecordJob getSettleRecord throw exception ex={}", ex);
        }

        Date endDate = new Date();
        log.info("SettleRecordJob run end.....{}", endDate);
    }
}
