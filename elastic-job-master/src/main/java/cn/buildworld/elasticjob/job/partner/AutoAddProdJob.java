package cn.buildworld.elasticjob.job.partner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.elasticjob.lite.annotation.ElasticSimpleJob;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.dto.req.LSSAddControlReq;
import com.iwhalecloud.retail.partner.dto.req.MerchantListReq;
import com.iwhalecloud.retail.partner.dto.req.MerchantRulesDeleteReq;
import com.iwhalecloud.retail.partner.service.MerchantRulesService;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.partner.service.NewParMerchAddProdService;
import com.iwhalecloud.retail.system.dto.request.UserListReq;
import com.iwhalecloud.retail.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2019/6/18.
 */
@ElasticSimpleJob(cron = "0 0 21 * * ?",
        jobName = "AutoAddProdJob",
        shardingTotalCount = 1,
        jobParameter = "测试参数",
        shardingItemParameters = "0=A,1=B",
        dataSource = "datasource")
@Component
@Slf4j
public class AutoAddProdJob implements SimpleJob {
    @Reference(timeout = 10000)
    private NewParMerchAddProdService newParMerchAddProdService;

    @Reference(timeout = 10000)
    private MerchantService merchantService;

    @Reference(timeout = 10000)
    private MerchantRulesService merchantRulesService;

    @Override
    public void execute(ShardingContext shardingContext) {
        MerchantListReq merchantListReq = new MerchantListReq();
        merchantListReq.setMerchantType("4");
        ResultVO<List<MerchantDTO>> resultVO =  merchantService.listMerchant(merchantListReq);
        if(resultVO.isSuccess() && null!=resultVO.getResultData()){
            List<MerchantDTO> merchantDTOList = resultVO.getResultData();
            if(CollectionUtils.isNotEmpty(merchantDTOList)){
                for(MerchantDTO merchantDTO:merchantDTOList){
                    MerchantRulesDeleteReq merchantRulesDeleteReq = new MerchantRulesDeleteReq();
                    merchantRulesDeleteReq.setMerchantId(merchantDTO.getMerchantId());
                    merchantRulesDeleteReq.setRuleType("1");
                    merchantRulesDeleteReq.setTargetType("2");
                    merchantRulesService.deleteMerchantRules(merchantRulesDeleteReq);

                    LSSAddControlReq req = new LSSAddControlReq();
                    req.setMerchantId(merchantDTO.getMerchantId());
                    req.setRuleType("1");
                    req.setTargetType("2");
                    List<String> targetIdList = newParMerchAddProdService.selectProductIdList();
                    req.setTargetIdList(targetIdList);
                    newParMerchAddProdService.addProd(req);
                }
            }
        }
    }
}
