package cn.buildworld.elasticjob.job.partner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.elasticjob.lite.annotation.ElasticSimpleJob;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.dto.req.MerchantListReq;
import com.iwhalecloud.retail.partner.dto.req.MerchantUpdateReq;
import com.iwhalecloud.retail.partner.service.MerchantRulesService;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.partner.service.NewParMerchAddProdService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2019/6/18.
 */
@ElasticSimpleJob(cron = "0 0 23 * * ?",
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

    @Reference(timeout = 10000)
    private ProductService productService;

    @Override
    public void execute(ShardingContext shardingContext) {
        MerchantListReq merchantListReq = new MerchantListReq();
        merchantListReq.setMerchantType("4");
        merchantListReq.setAssignedFlg("9");
        ResultVO<List<MerchantDTO>> resultVO = merchantService.listMerchant(merchantListReq);
        if (resultVO.isSuccess() && null != resultVO.getResultData()) {
            List<MerchantDTO> merchantDTOList = resultVO.getResultData();
            if (CollectionUtils.isNotEmpty(merchantDTOList)) {
                for (MerchantDTO merchantDTO : merchantDTOList) {

                    // 新逻辑  zhongwenlong2019.06.27
                    newParMerchAddProdService.addRetailerDefaultRule(merchantDTO.getMerchantId());

                    MerchantUpdateReq merchantUpdateReq = new MerchantUpdateReq();
                    merchantUpdateReq.setMerchantId(merchantDTO.getMerchantId());
                    merchantUpdateReq.setAssignedFlg("8");
                    merchantService.updateMerchant(merchantUpdateReq);

                }
            }
        }
    }

}
