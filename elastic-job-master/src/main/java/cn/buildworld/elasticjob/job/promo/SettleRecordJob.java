package cn.buildworld.elasticjob.job.promo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.elasticjob.lite.annotation.ElasticSimpleJob;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.dto.SettleRecordDTO;
import com.iwhalecloud.retail.promo.service.SettleRecordService;
import com.iwhalecloud.retail.system.dto.CommonRegionDTO;
import com.iwhalecloud.retail.system.dto.request.CommonRegionListReq;
import com.iwhalecloud.retail.system.service.CommonRegionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
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
    @Reference(timeout = 60000)
    private SettleRecordService settleRecordService;
    @Reference
    private CommonRegionService commonRegionService;

    @Override
    public void execute(ShardingContext shardingContext) {
        Date startDate = new Date();
        log.info("SettleRecordJob run start.....{}", startDate);

        try {
            CommonRegionListReq req = new CommonRegionListReq();
            List<CommonRegionDTO> commonRegionDTOs = new ArrayList<>();
            ResultVO<List<CommonRegionDTO>> resultVO = commonRegionService.listCommonRegion(req);
            if(resultVO.isSuccess() && null!=resultVO.getResultData()){
                commonRegionDTOs = resultVO.getResultData();
            }

            if(!CollectionUtils.isEmpty(commonRegionDTOs)){
                for(CommonRegionDTO commonRegionDTO:commonRegionDTOs){
                    String lanId = commonRegionDTO.getRegionId();
                    log.info("SettleRecordJob lanId .....{}", lanId);
                    List<SettleRecordDTO> settleRecordDTOs = settleRecordService.getSettleRecord(lanId);
                    if(!CollectionUtils.isEmpty(settleRecordDTOs)){
                        log.info("SettleRecordJob batchAddSettleRecord .....{}", settleRecordDTOs.size());
                        settleRecordService.batchAddSettleRecord(settleRecordDTOs);
                    }
                }
            }
        }catch (Exception ex) {
            log.error("SettleRecordJob getSettleRecord throw exception ex={}", ex);
        }

        Date endDate = new Date();
        log.info("SettleRecordJob run end.....{}", endDate);
    }
}
