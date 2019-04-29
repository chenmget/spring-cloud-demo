package cn.buildworld.elasticjob.job.partner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.elasticjob.lite.annotation.ElasticSimpleJob;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.common.ProductConst;
import com.iwhalecloud.retail.goods2b.dto.ProductDTO;
import com.iwhalecloud.retail.goods2b.dto.req.ProductAuditStateUpdateReq;
import com.iwhalecloud.retail.goods2b.dto.req.ProductGetReq;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.dto.req.MerchantListReq;
import com.iwhalecloud.retail.partner.dto.req.MerchantRulesSaveReq;
import com.iwhalecloud.retail.partner.service.MerchantRulesService;
import com.iwhalecloud.retail.partner.service.MerchantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/4/29.
 */
@ElasticSimpleJob(cron = "0 0/10 * * * ?",
        jobName = "AutoAddMerchantRulesJob",
        shardingTotalCount = 1,
        jobParameter = "测试参数",
        shardingItemParameters = "0=A,1=B",
        dataSource = "datasource")
@Component
@Slf4j
public class AutoAddMerchantRulesJob implements SimpleJob {
    @Reference(timeout = 10000)
    MerchantRulesService merchantRulesService;

    @Reference(timeout = 10000)
    ProductService productService;

    @Reference(timeout = 10000)
    MerchantService merchantService;

    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("------>> AutoAddMerchantRulesJob start......");
        if (productService == null) {
            return;
        }
        if (merchantRulesService == null) {
            return;
        }
        if (merchantService == null) {
            return;
        }
        try {
            ProductGetReq req = new ProductGetReq();
            req.setAttrValue10(ProductConst.attrValue10.EFFECTIVE.getCode());
            ResultVO<Page<ProductDTO>> resultVO = productService.selectProduct(req);
            log.info("AutoAddMerchantRulesJob selectProduct={}", resultVO.getResultData().getRecords().size());
            List<String> productIds  = new ArrayList<>();
            if(resultVO.isSuccess() && null != resultVO.getResultData()){
                List<ProductDTO> productDTOs = resultVO.getResultData().getRecords();
                if(!CollectionUtils.isEmpty(productDTOs)){
                   for(ProductDTO productDTO:productDTOs) {
                       productIds.add(productDTO.getProductId());
                   }
                }
            }
            MerchantListReq merchantListReq = new MerchantListReq();
            merchantListReq.setMerchantType(PartnerConst.MerchantTypeEnum.PARTNER.getType());
            ResultVO<List<MerchantDTO>>  merchantResultVo = merchantService.listMerchant(merchantListReq);
            log.info("AutoAddMerchantRulesJob listMerchant={}", merchantResultVo.getResultData().size());
            List<MerchantDTO> merchantDTOs = new ArrayList<>();
            if(merchantResultVo.isSuccess()&& null!=merchantResultVo){
                merchantDTOs = merchantResultVo.getResultData();
            }
            if(!CollectionUtils.isEmpty(merchantDTOs) && !CollectionUtils.isEmpty(productIds)){
                for(MerchantDTO merchantDTO: merchantDTOs){
                    MerchantRulesSaveReq merchantRulesSaveReq = new MerchantRulesSaveReq();
                    merchantRulesSaveReq.setMerchantId(merchantDTO.getMerchantId());
                    merchantRulesSaveReq.setRuleType("1");
                    merchantRulesSaveReq.setTargetType("2");
                    int listSize=productIds.size();
                    int toIndex=100;
                    for(int i=0;i<listSize;i+=100){
                        if(i+100>listSize){
                            toIndex=listSize-i;
                        }
                        List newlist = productIds.subList(i,i+toIndex);
                        merchantRulesSaveReq.setTargetIdList(newlist);
                        merchantRulesService.saveMerchantRules(merchantRulesSaveReq);
                        ProductAuditStateUpdateReq productAuditStateUpdateReq = new ProductAuditStateUpdateReq();
                        productAuditStateUpdateReq.setProductIds(newlist);
                        productAuditStateUpdateReq.setAttrValue10(ProductConst.attrValue10.SUCCESS.getCode());
                        productService.updateAttrValue10(productAuditStateUpdateReq);
                        log.info("AutoAddMerchantRulesJob updateAttrValue10={}", productAuditStateUpdateReq);
                    }
                }
            }

        } catch (Exception e) {
            log.error("AutoAddMerchantRulesJob exceute exception", e);
        }
        log.info("------>> AutoAddMerchantRulesJob end......");
    }
}
