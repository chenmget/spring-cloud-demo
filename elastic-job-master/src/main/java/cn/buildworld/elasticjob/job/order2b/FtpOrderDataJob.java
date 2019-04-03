package cn.buildworld.elasticjob.job.order2b;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.elasticjob.lite.annotation.ElasticSimpleJob;
import com.iwhalecloud.retail.order2b.service.AdvanceOrderOpenService;
import com.iwhalecloud.retail.order2b.service.FtpOrderDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author 吴良勇
 * @date 2019/4/1 20:02
 * 将以完成的订单数据上传到FTP上，每个月1号，15号，凌晨2点运行
 */
@ElasticSimpleJob(cron = "0 0 2 1,15 * ? ",
        jobName = "ftpOrderDataJob",
        shardingTotalCount = 1,
        jobParameter = "测试参数",
        shardingItemParameters = "0=A,1=B",
        dataSource = "datasource")
@Component
@Slf4j
public class FtpOrderDataJob implements SimpleJob {
    @Reference
    FtpOrderDataService ftpOrderDataService;

    @Override
    public void execute(ShardingContext shardingContext) {
        if (ftpOrderDataService == null) {
            return ;
        }

        long startTime = System.currentTimeMillis();
        log.info("FtpOrderDataJob start-->startTime={}", startTime);
        ftpOrderDataService.uploadFtpForTask();
        long endTime = System.currentTimeMillis();
        log.info("FtpOrderDataJob end-->endTime={}",endTime);
    }
}
