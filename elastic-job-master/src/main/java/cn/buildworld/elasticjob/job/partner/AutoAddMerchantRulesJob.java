package cn.buildworld.elasticjob.job.partner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.elasticjob.lite.annotation.ElasticSimpleJob;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.goods2b.common.ProductConst;
import com.iwhalecloud.retail.goods2b.dto.req.ProductAuditStateUpdateReq;
import com.iwhalecloud.retail.goods2b.dto.req.ProductListReq;
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

import java.util.List;
import java.util.stream.Collectors;

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
            // 新逻辑 zhongwenlong 2019.06.26

            // 取要添加的机型ID 集合
            ProductListReq req = new ProductListReq();
            req.setAttrValue10(ProductConst.attrValue10.EFFECTIVE.getCode());
            List<String> productIds = productService.listProductId(req).getResultData();

            log.info("AutoAddMerchantRulesJob 状态是：审核成功待添加权限 的产品ID集合：{} ", JSON.toJSONString(productIds));
            if (CollectionUtils.isEmpty(productIds)) {
                // 没有  审核成功待添加权限  状态的机型ID集合 直接返回
                return;
            }

            // 取零售商id集合
            List<String> retailerIdList = getMerchantIdListByType(PartnerConst.MerchantTypeEnum.PARTNER.getType());
            // 取地包商id集合
            List<String> supplierGroundIdList = getMerchantIdListByType(PartnerConst.MerchantTypeEnum.SUPPLIER_GROUND.getType());
            log.info("AutoAddMerchantRulesJob merchantService.listMerchant() 待添加权限的零售商数量：{} ", retailerIdList.size());
            log.info("AutoAddMerchantRulesJob merchantService.listMerchant() 待添加权限的地包商数量：{} ", supplierGroundIdList.size());

            /**
             * 添加权限操作逻辑：
             * a、给所有零售商默认添加： 1、经营权限--机型权限； 2、调拨权限--机型权限；
             * b、给所有地包商默认添加： 1、调拨权限--机型权限；
             */
            // 限制每次最多插入200条记录
            int listSize = productIds.size();
            int toIndex = 200;
            for (int i = 0; i < listSize; i += 200) {
                if (i + 200 > listSize) {
                    toIndex = listSize - i;
                }
                List newlist = productIds.subList(i, i + toIndex);

                // 逻辑  给所有零售商默认添加： 经营权限--机型权限；
                addBusinessModeRules(retailerIdList, productIds);

                // 逻辑 给所有零售商、地包商默认添加： 调拨权限--机型权限
                // 合并 零售商、地包商 ID集合
                List<String> allMerchantIdList = Lists.newArrayList(retailerIdList);
                allMerchantIdList.addAll(supplierGroundIdList);
                addTransferModeRules(allMerchantIdList, productIds);

                // 更改产品审核的状态 为： 添加权限成功
                ProductAuditStateUpdateReq productAuditStateUpdateReq = new ProductAuditStateUpdateReq();
                productAuditStateUpdateReq.setProductIds(newlist);
                productAuditStateUpdateReq.setAttrValue10(ProductConst.attrValue10.SUCCESS.getCode());
                productService.updateAttrValue10(productAuditStateUpdateReq);
                log.info("AutoAddMerchantRulesJob updateAttrValue10={}", productAuditStateUpdateReq);
            }

        } catch (Exception e) {
            log.error("AutoAddMerchantRulesJob exceute exception", e);
        }
        log.info("------>> AutoAddMerchantRulesJob end......");
    }

    /**
     * 根据类型获取商家ID 集合
     * @param merchantType
     * @return
     */
    private List<String> getMerchantIdListByType(String merchantType) {
        List<String> merchantIdList = Lists.newArrayList();
        MerchantListReq merchantListReq = new MerchantListReq();
        merchantListReq.setMerchantType(merchantType);
        List<MerchantDTO> merchantDTOList = merchantService.listMerchant(merchantListReq).getResultData();
        if (!CollectionUtils.isEmpty(merchantDTOList)) {
            merchantIdList = merchantDTOList.stream().map(MerchantDTO::getMerchantId).collect(Collectors.toList());
        }
        return merchantIdList;
    }

    /**
     * 给商家设置权限 经营权限-机型权限
     * @param merchantIds 要设置的商家id集合
     * @param productIds 机型ID集合
     */
    private void addBusinessModeRules(List<String> merchantIds, List<String> productIds) {
        if (!CollectionUtils.isEmpty(merchantIds) && !CollectionUtils.isEmpty(productIds)) {
            for (String merchantId : merchantIds) {
                MerchantRulesSaveReq merchantRulesSaveReq = new MerchantRulesSaveReq();
                merchantRulesSaveReq.setMerchantId(merchantId);
                merchantRulesSaveReq.setRuleType(PartnerConst.MerchantRuleTypeEnum.BUSINESS.getType());
                merchantRulesSaveReq.setTargetType(PartnerConst.MerchantBusinessTargetTypeEnum.MODEL.getType());
                merchantRulesSaveReq.setTargetIdList(productIds);
                merchantRulesService.saveMerchantRules(merchantRulesSaveReq);
            }
        }
    }

    /**
     * 给商家设置权限 调拨权限-机型权限
     * @param merchantIds 要设置的商家id集合
     * @param productIds 机型ID集合
     */
    private void addTransferModeRules(List<String> merchantIds, List<String> productIds) {
        if (!CollectionUtils.isEmpty(merchantIds) && !CollectionUtils.isEmpty(productIds)) {
            for (String merchantId : merchantIds) {
                MerchantRulesSaveReq merchantRulesSaveReq = new MerchantRulesSaveReq();
                merchantRulesSaveReq.setMerchantId(merchantId);
                merchantRulesSaveReq.setRuleType(PartnerConst.MerchantRuleTypeEnum.TRANSFER.getType());
                merchantRulesSaveReq.setTargetType(PartnerConst.MerchantTransferTargetTypeEnum.MODEL.getType());
                merchantRulesSaveReq.setTargetIdList(productIds);
                merchantRulesService.saveMerchantRules(merchantRulesSaveReq);
            }
        }
    }

}
