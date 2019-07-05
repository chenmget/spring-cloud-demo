package cn.buildworld.elasticjob.job.goods;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.elasticjob.lite.annotation.ElasticSimpleJob;
import com.iwhalecloud.retail.promo.service.GoodsBindingActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author: wang.jiaxin
 * @date: 2019年03月26日
 * @description: 更新商品参与活动标志
 **/
@ElasticSimpleJob(cron = "0 0/10 * * * ?",
        jobName = "firstJob",
        shardingTotalCount = 1,
        jobParameter = "测试参数",
        shardingItemParameters = "0=A,1=B",
        dataSource = "datasource")
@Component
@Slf4j
public class GoodsBingActivityJob implements SimpleJob {

    @Reference
    private GoodsBindingActivityService goodsBindingActivityService;

    @Override
    public void execute(ShardingContext shardingContext) {
        if (goodsBindingActivityService == null) {
            return ;
        }
        long startTime = System.currentTimeMillis();
        log.info("GoodsBingActivityJob start-->startTime={}", startTime);
        // 解绑失效活动
        goodsBindingActivityService.goodsUnBundlingActivity();
        // 绑定在途活动
        goodsBindingActivityService.goodsBingActivity();
        long endTime = System.currentTimeMillis();
        log.info("GoodsBingActivityJob end-->endTime={}",endTime);
    }
}