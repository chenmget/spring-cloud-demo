package com.iwhalecloud.retail.warehouse.busiservice.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.req.ProductGetByIdReq;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstCheckService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.ResourceInstDTO;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstAddReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstsGetReq;
import com.iwhalecloud.retail.warehouse.manager.ResourceInstManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ResourceInstCheckServiceImpl implements ResourceInstCheckService {

    @Autowired
    private ResourceInstManager resourceInstManager;

    @Reference
    private ProductService productService;


    @Override
    public List<String> qryEnableInsertNbr(ResourceInstAddReq req){
        List<String> existNbrs = new ArrayList<>();
        ProductGetByIdReq productGetByIdReq = new ProductGetByIdReq();
        productGetByIdReq.setProductId(req.getMktResId());
        ResultVO<ProductResp> producttVO = productService.getProduct(productGetByIdReq);
        log.info("ResourceInstCheckServiceImpl.qryEnableInsertNbr productService.getProduct mktResId={} resp={}", req.getMktResId(), JSON.toJSONString(producttVO));
        String typeId = "";
        if (producttVO.isSuccess() && null != producttVO.getResultData()) {
            typeId = producttVO.getResultData().getTypeId();
            req.setTypeId(typeId);
        }
        // 一去重：串码存在且状态可用不再导入
        ResourceInstsGetReq resourceInstsGetReq = new ResourceInstsGetReq();
        List<String> mktResInstNbrs = Lists.newArrayList(req.getMktResInstNbrs());
        resourceInstsGetReq.setMktResInstNbrs(mktResInstNbrs);
        List<String> merchantTypes = null;
        if (PartnerConst.MerchantTypeEnum.MANUFACTURER.getType().equals(req.getMerchantType())) {
            // 厂商增加：只校验厂商库
            merchantTypes = Lists.newArrayList(PartnerConst.MerchantTypeEnum.MANUFACTURER.getType());
        }else{
            // 非厂商增加：只校验非厂商库
            merchantTypes = Lists.newArrayList(PartnerConst.MerchantTypeEnum.SUPPLIER_GROUND.getType(),
                    PartnerConst.MerchantTypeEnum.SUPPLIER_PROVINCE.getType(),
                    PartnerConst.MerchantTypeEnum.PARTNER.getType());
        }
        resourceInstsGetReq.setMerchantTypes(merchantTypes);
        resourceInstsGetReq.setMktResStoreId(req.getDestStoreId());
        resourceInstsGetReq.setTypeId(typeId);
        List<ResourceInstDTO> inst = resourceInstManager.getResourceInsts(resourceInstsGetReq);
        log.info("ResourceInstCheckServiceImpl.addResourceInst resourceInstManager.getResourceInsts req={},resp={}", JSON.toJSONString(resourceInstsGetReq), JSON.toJSONString(inst));
        if (CollectionUtils.isNotEmpty(inst)) {
            // 作废的串码可以重现导入,把作废的串码去除
            inst = inst.stream().filter(t -> !ResourceConst.STATUSCD.DELETED.getCode().equals(t.getStatusCd())).collect(Collectors.toList());
            List<String> instNbrs = inst.stream().map(ResourceInstDTO::getMktResInstNbr).collect(Collectors.toList());
            existNbrs = mktResInstNbrs.stream().filter(t -> instNbrs.contains(t)).collect(Collectors.toList());
        }
        return existNbrs;
    }

}