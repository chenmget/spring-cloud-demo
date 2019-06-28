package cn.buildworld.elasticjob.job.partner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.elasticjob.lite.annotation.ElasticSimpleJob;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.common.ProductConst;
import com.iwhalecloud.retail.goods2b.dto.req.ProductListReq;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.dto.req.LSSAddControlReq;
import com.iwhalecloud.retail.partner.dto.req.MerchantListReq;
import com.iwhalecloud.retail.partner.dto.req.MerchantRulesDeleteReq;
import com.iwhalecloud.retail.partner.dto.req.MerchantUpdateReq;
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
@ElasticSimpleJob(cron = "0 0 20 * * ?",
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
                    //旧逻辑
//                    MerchantRulesDeleteReq merchantRulesDeleteReq = new MerchantRulesDeleteReq();
//                    merchantRulesDeleteReq.setMerchantId(merchantDTO.getMerchantId());
//                    merchantRulesDeleteReq.setRuleType("1");
//                    merchantRulesDeleteReq.setTargetType("2");
//                    merchantRulesService.deleteMerchantRules(merchantRulesDeleteReq);

//                    LSSAddControlReq req = new LSSAddControlReq();
//                    req.setMerchantId(merchantDTO.getMerchantId());
//                    req.setRuleType("1");
//                    req.setTargetType("2");
//                    List<String> targetIdList = getProductIdList();
//                    req.setTargetIdList(targetIdList);
//                    newParMerchAddProdService.addProd(req);

                    // 新逻辑  zhongwenlong2019.06.27
                    deleteMerchantRules(merchantDTO.getMerchantId());
                    newParMerchAddProdService.addRetailerDefaultRule(merchantDTO.getMerchantId());

                    MerchantUpdateReq merchantUpdateReq = new MerchantUpdateReq();
                    merchantUpdateReq.setMerchantId(merchantDTO.getMerchantId());
                    merchantUpdateReq.setAssignedFlg("8");
                    merchantService.updateMerchant(merchantUpdateReq);

                }
            }
        }
    }

    /**
     * 添加权限之前  清除需要清除的权限
     *
     * @param merchantId
     */
    private void deleteMerchantRules(String merchantId) {
        MerchantRulesDeleteReq merchantRulesDeleteReq = new MerchantRulesDeleteReq();
        merchantRulesDeleteReq.setMerchantId(merchantId);
        // 经营权限--机型权限
        merchantRulesDeleteReq.setRuleType(PartnerConst.MerchantRuleTypeEnum.BUSINESS.getType());
        merchantRulesDeleteReq.setTargetType(PartnerConst.MerchantBusinessTargetTypeEnum.MODEL.getType());
        merchantRulesService.deleteMerchantRules(merchantRulesDeleteReq);

        // 调拨权限--机型权限
        merchantRulesDeleteReq.setRuleType(PartnerConst.MerchantRuleTypeEnum.TRANSFER.getType());
        merchantRulesDeleteReq.setTargetType(PartnerConst.MerchantTransferTargetTypeEnum.MODEL.getType());
        merchantRulesService.deleteMerchantRules(merchantRulesDeleteReq);

        // 调拨权限--区域权限
        merchantRulesDeleteReq.setRuleType(PartnerConst.MerchantRuleTypeEnum.TRANSFER.getType());
        merchantRulesDeleteReq.setTargetType(PartnerConst.MerchantTransferTargetTypeEnum.REGION.getType());
        merchantRulesService.deleteMerchantRules(merchantRulesDeleteReq);

    }

    /**
     * 获取一定条件下的产品ID集合
     *
     * @return
     */
    private List<String> getProductIdList() {
        ProductListReq productListReq = new ProductListReq();
        productListReq.setIsDeleted(ProductConst.IsDelete.NO.getCode());
        productListReq.setStatus(ProductConst.StatusType.EFFECTIVE.getCode());
        return productService.listProductId(productListReq).getResultData();
    }

}
