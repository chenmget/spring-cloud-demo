package cn.buildworld.elasticjob.job.warehouse;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.elasticjob.lite.annotation.ElasticSimpleJob;
import com.iwhalecloud.retail.promo.service.ProdFileBingToService;

import lombok.extern.slf4j.Slf4j;
/**
 * 生效的活动配置活动图片
 * @author liweisong
 *
 */

@ElasticSimpleJob(cron = "0 0 1 * * ?",
jobName = "firstJob",
shardingTotalCount = 1,
jobParameter = "测试参数",
shardingItemParameters = "0=A,1=B",
dataSource = "datasource")
@Component
@Slf4j
public class ProdFileBingToMktActivityJob implements SimpleJob  {

	@Reference
	private ProdFileBingToService prodFileBingToService;
	
	@Override
	public void execute(ShardingContext shardingContext) {
		if (prodFileBingToService == null) {
            return ;
        }
        long startTime = System.currentTimeMillis();
        log.info("ProdFileBingToMktActivityJob start-->startTime={}", startTime);
        // 绑定在途活动
        prodFileBingToService.goodsBingProdFile();
        // 解绑失效活动
        prodFileBingToService.goodsUnBundlingProdFile();
        long endTime = System.currentTimeMillis();
        log.info("ProdFileBingToMktActivityJob end-->endTime={}",endTime);
		
	}

}
