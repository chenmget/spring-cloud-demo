package cn.buildworld.elasticjob.job.report;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.elasticjob.lite.annotation.ElasticSimpleJob;
import com.iwhalecloud.retail.report.service.RptSupplierOperatingDayService;

import lombok.extern.slf4j.Slf4j;

//每天凌晨两点执行定时任务0 0 2 * * ?	每天上午的10点15分执行  0 15 10 ? * *
@ElasticSimpleJob(cron = "0 0 1 * * ?",
      jobName = "firstJob",
      shardingTotalCount = 1,
      jobParameter = "测试参数",
      shardingItemParameters = "0=A,1=B",
      dataSource = "datasource")
@Component
@Slf4j
public class RptSupplierOperatingDayJob implements SimpleJob  {
	
	@Reference
    RptSupplierOperatingDayService reportJob;
	
	@Override
    public void execute(ShardingContext shardingContext) {
    	if(reportJob == null){
    		return ;
    	}
		try {
			reportJob.hqRptSupplierOperatingDayData();
		} catch (Exception e) {
			log.error("ReportDataForSuDayJob exceute exception", e);
		}
    }
}
