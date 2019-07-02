package com.iwhalecloud.retail.partner.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.common.ProductConst;
import com.iwhalecloud.retail.goods2b.dto.req.ProductListReq;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.partner.dto.req.LSSAddControlReq;
import com.iwhalecloud.retail.partner.dto.req.MerchantRulesDeleteReq;
import com.iwhalecloud.retail.partner.dto.req.MerchantRulesSaveReq;
import com.iwhalecloud.retail.partner.entity.MerchantRules;
import com.iwhalecloud.retail.partner.manager.MerchantRulesManager;
import com.iwhalecloud.retail.partner.service.MerchantRulesService;
import com.iwhalecloud.retail.partner.service.NewParMerchAddProdService;
import com.iwhalecloud.retail.system.dto.CommonRegionDTO;
import com.iwhalecloud.retail.system.service.CommonRegionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NewParMerchAddProdServiceImpl implements NewParMerchAddProdService {

    @Autowired
    private MerchantRulesManager merchantRulesManager;

    @Reference
    private MerchantRulesService merchantRulesService;

    @Reference
    private ProductService productService;

    @Reference
    private CommonRegionService commonRegionService;

    /**
     * 编辑选择零售商时设置默认赋权：a、所有机型经营权限； b、所有地市和所有机型的调拨权限
     * @param merchantId
     * @return
     */
    @Override
    public ResultVO<Integer> addRetailerDefaultRule(String merchantId) {
        log.info("NewParMerchAddProdServiceImpl.addMerchantDefaultRule(), 入参merchantId={} ", merchantId);

        // 先清除权限
        deleteRetailerDefaultRule(merchantId);

        // 所有有效的机型ID集合
        List<String> productIdList = getProductIdList();
        List<String> lanIdList = getAllLanIDList();
        log.info("NewParMerchAddProdServiceImpl.addProd(), 所有的机型ID集合={} ", JSON.toJSONString(productIdList));
        log.info("NewParMerchAddProdServiceImpl.addProd(), 所有的地市ID集合={} ", JSON.toJSONString(lanIdList));

        // a、所有机型经营权限
        if (!addBusinessModeRules(merchantId, productIdList)) {
            return ResultVO.error("新增的零售商 所有机型经营权限 赋权失败");
        }

        // b、所有地市和所有机型的调拨权限
        if (!addTransferModeRules(merchantId, productIdList)) {
            return ResultVO.error("新增的零售商 所有机型的调拨权限 赋权失败");
        }
        if (!addTransferRegionRules(merchantId, lanIdList)) {
            return ResultVO.error("新增的零售商 所有地市的调拨权限 赋权失败");
        }

        log.info("NewParMerchAddProdServiceImpl.addMerchantDefaultRule(), 零售商时设置默认赋权：a、所有机型经营权限； b、所有地市和所有机型的调拨权限  成功 ");
        return ResultVO.success(1);

    }

    /**
     * 添加权限之前  清除需要清除的权限
     * 清除商家的 默认权权限：a、所有机型经营权限； b、所有地市和所有机型的调拨权限
     * @param merchantId
     */
    private void deleteRetailerDefaultRule(String merchantId) {
        log.info("NewParMerchAddProdServiceImpl.deleteRetailerDefaultRule(), 清除商家：{} 的默认权权限 ", merchantId);

        MerchantRulesDeleteReq merchantRulesDeleteReq = new MerchantRulesDeleteReq();
        merchantRulesDeleteReq.setMerchantId(merchantId);
        // 经营权限--机型权限
        merchantRulesDeleteReq.setRuleType(PartnerConst.MerchantRuleTypeEnum.BUSINESS.getType());
        merchantRulesDeleteReq.setTargetType(PartnerConst.MerchantBusinessTargetTypeEnum.MODEL.getType());
        merchantRulesManager.deleteMerchantRules(merchantRulesDeleteReq);

        // 调拨权限--机型权限
        merchantRulesDeleteReq.setRuleType(PartnerConst.MerchantRuleTypeEnum.TRANSFER.getType());
        merchantRulesDeleteReq.setTargetType(PartnerConst.MerchantTransferTargetTypeEnum.MODEL.getType());
        merchantRulesManager.deleteMerchantRules(merchantRulesDeleteReq);

        // 调拨权限--区域权限
        merchantRulesDeleteReq.setRuleType(PartnerConst.MerchantRuleTypeEnum.TRANSFER.getType());
        merchantRulesDeleteReq.setTargetType(PartnerConst.MerchantTransferTargetTypeEnum.REGION.getType());
        merchantRulesManager.deleteMerchantRules(merchantRulesDeleteReq);
    }

    /**
     * 给商家设置权限 经营权限-机型权限
     * @param merchantId 要设置的商家id集合
     * @param productIds 机型ID集合
     */
    private boolean addBusinessModeRules(String merchantId, List<String> productIds) {
        if (!StringUtils.isEmpty(merchantId) && !CollectionUtils.isEmpty(productIds)) {
            MerchantRulesSaveReq merchantRulesSaveReq = new MerchantRulesSaveReq();
            merchantRulesSaveReq.setMerchantId(merchantId);
            merchantRulesSaveReq.setRuleType(PartnerConst.MerchantRuleTypeEnum.BUSINESS.getType());
            merchantRulesSaveReq.setTargetType(PartnerConst.MerchantBusinessTargetTypeEnum.MODEL.getType());
            merchantRulesSaveReq.setTargetIdList(productIds);
            return merchantRulesService.saveMerchantRules(merchantRulesSaveReq).isSuccess();
        }
        return false;
    }

    /**
     * 给商家设置权限 调拨权限-机型权限
     * @param merchantId 要设置的商家id集合
     * @param productIds 机型ID集合
     */
    private boolean addTransferModeRules(String merchantId, List<String> productIds) {
        if (!StringUtils.isEmpty(merchantId) && !CollectionUtils.isEmpty(productIds)) {
            MerchantRulesSaveReq merchantRulesSaveReq = new MerchantRulesSaveReq();
            merchantRulesSaveReq.setMerchantId(merchantId);
            merchantRulesSaveReq.setRuleType(PartnerConst.MerchantRuleTypeEnum.TRANSFER.getType());
            merchantRulesSaveReq.setTargetType(PartnerConst.MerchantTransferTargetTypeEnum.MODEL.getType());
            merchantRulesSaveReq.setTargetIdList(productIds);
            return merchantRulesService.saveMerchantRules(merchantRulesSaveReq).isSuccess();
        }
        return false;
    }

    /**
     * 给商家设置权限 调拨权限-区域权限
     * @param merchantId 要设置的商家id集合
     * @param regionIdList 区域ID集合
     */
    private boolean addTransferRegionRules(String merchantId, List<String> regionIdList) {
        if (!StringUtils.isEmpty(merchantId) && !CollectionUtils.isEmpty(regionIdList)) {
            MerchantRulesSaveReq merchantRulesSaveReq = new MerchantRulesSaveReq();
            merchantRulesSaveReq.setMerchantId(merchantId);
            merchantRulesSaveReq.setRuleType(PartnerConst.MerchantRuleTypeEnum.TRANSFER.getType());
            merchantRulesSaveReq.setTargetType(PartnerConst.MerchantTransferTargetTypeEnum.REGION.getType());
            merchantRulesSaveReq.setTargetIdList(regionIdList);
            return merchantRulesService.saveMerchantRules(merchantRulesSaveReq).isSuccess();
        }
        return false;
    }

    /**
     * 获取一定条件下的产品ID集合
     * @return
     */
    private List<String> getProductIdList() {
        ProductListReq productListReq = new ProductListReq();
        productListReq.setIsDeleted(ProductConst.IsDelete.NO.getCode());
        productListReq.setStatus(ProductConst.StatusType.EFFECTIVE.getCode());
        return productService.listProductId(productListReq).getResultData();
    }

    /**
     * 获取所有地市ID集合
     * @return
     */
    private List<String> getAllLanIDList() {
        List<CommonRegionDTO> dtoList = commonRegionService.listLan().getResultData();
        List<String> lanIdList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(dtoList)) {
            lanIdList = dtoList.stream().map(CommonRegionDTO::getRegionId).collect(Collectors.toList());
        }
        return lanIdList;
    }

}
