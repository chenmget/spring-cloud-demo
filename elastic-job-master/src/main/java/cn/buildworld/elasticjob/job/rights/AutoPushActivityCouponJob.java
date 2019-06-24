package cn.buildworld.elasticjob.job.rights;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.elasticjob.lite.annotation.ElasticSimpleJob;
import com.iwhalecloud.retail.rights.service.RightsJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author zhou.zc
 * @date 2019年03月29日
 * @Description:推送优惠券，生成优惠券实例和领取记录
 */
@ElasticSimpleJob(cron = "0 */10 * * * ?",
        jobName = "autoPushActivityCouponJob",
        shardingTotalCount = 1,
        jobParameter = "测试参数",
        shardingItemParameters = "0=A,1=B",
        dataSource = "datasource")
@Component
@Slf4j
public class AutoPushActivityCouponJob implements SimpleJob {

    @Reference
    private RightsJobService rightsJobService;

    @Override
    public void execute(ShardingContext shardingContext) {
        if (rightsJobService == null) {
            return ;
        }
        long startTime = System.currentTimeMillis();
        log.info("AutoPushActivityCouponJob start-->startTime={}", startTime);
        rightsJobService.autoReceiveCoupon();
        long endTime = System.currentTimeMillis();
        log.info("AutoPushActivityCouponJob end-->endTime={}",endTime);
    }
}
