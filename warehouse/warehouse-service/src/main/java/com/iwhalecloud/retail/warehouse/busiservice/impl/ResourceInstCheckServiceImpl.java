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
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstValidReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstsGetReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceRequestAddReq;
import com.iwhalecloud.retail.warehouse.manager.ResourceInstManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(isolation= Isolation.SERIALIZABLE,propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    public List<String> vaildOwnStore(ResourceInstValidReq req){
        List<String> existNbrs = new ArrayList<>();
        ProductGetByIdReq productGetByIdReq = new ProductGetByIdReq();
        productGetByIdReq.setProductId(req.getMktResId());
        ResultVO<ProductResp> producttVO = productService.getProduct(productGetByIdReq);
        log.info("ResourceInstServiceImpl.vaildOwnStore productService.getProduct mktResId={} resp={}", req.getMktResId(), JSON.toJSONString(producttVO));
        String typeId = "";
        if (producttVO.isSuccess() && null != producttVO.getResultData()) {
            typeId = producttVO.getResultData().getTypeId();
            req.setTypeId(typeId);
        }
        // 一去重：串码存在不再导入
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
        resourceInstsGetReq.setMktResStoreId(req.getMktResStoreId());
        resourceInstsGetReq.setTypeId(typeId);
        List<ResourceInstDTO> inst = resourceInstManager.getResourceInsts(resourceInstsGetReq);
        log.info("ResourceInstCheckServiceImpl.vaildOwnStore resourceInstManager.getResourceInsts req={},resp={}", JSON.toJSONString(resourceInstsGetReq), JSON.toJSONString(inst));
        if (CollectionUtils.isNotEmpty(inst)) {
            List<String> instNbrs = inst.stream().map(ResourceInstDTO::getMktResInstNbr).collect(Collectors.toList());
            existNbrs = mktResInstNbrs.stream().filter(t -> instNbrs.contains(t)).collect(Collectors.toList());
        }
        return existNbrs;
    }


    @Override
    public List<ResourceInstDTO> validMerchantStore(ResourceInstValidReq req){
        List<String> validNbrList = req.getMktResInstNbrs();
        ResourceInstsGetReq manufacturerResourceInstsGetReq = new ResourceInstsGetReq();
        manufacturerResourceInstsGetReq.setMktResInstNbrs(validNbrList);
        manufacturerResourceInstsGetReq.setMktResId(req.getMktResId());
        List<String> manufacturerTypes = new ArrayList<>();
        manufacturerTypes.add(PartnerConst.MerchantTypeEnum.MANUFACTURER.getType());
        manufacturerResourceInstsGetReq.setMerchantTypes(manufacturerTypes);
        manufacturerResourceInstsGetReq.setMktResStoreId(req.getMktResStoreId());
        List<ResourceInstDTO> merchantInst = resourceInstManager.getResourceInsts(manufacturerResourceInstsGetReq);
        log.info("ResourceInstCheckServiceImpl.validMerchantStore resourceInstManager.getResourceInsts req={},resp={}", JSON.toJSONString(manufacturerResourceInstsGetReq), JSON.toJSONString(merchantInst));
        return  merchantInst;
    }

    @Override
    public List<ResourceRequestAddReq.ResourceRequestInst> getReqInst(ResourceInstAddReq req){
        List<ResourceRequestAddReq.ResourceRequestInst> instDTOList = Lists.newLinkedList();
        List<String> mktResInstNbrs = req.getMktResInstNbrs();
        for (String nbr : mktResInstNbrs) {
            ResourceRequestAddReq.ResourceRequestInst instDTO = new ResourceRequestAddReq.ResourceRequestInst();
            instDTO.setMktResInstNbr(nbr);
            instDTO.setMktResId(req.getMktResId());
            if (null != req.getCtCode()) {
                instDTO.setCtCode(req.getCtCode().get(nbr));
            }
            instDTOList.add(instDTO);
        }
        List<String> checkMktResInstNbrs = req.getCheckMktResInstNbrs();
        if (!CollectionUtils.isEmpty(checkMktResInstNbrs)) {
            checkMktResInstNbrs.retainAll(mktResInstNbrs);
            for (String nbr : checkMktResInstNbrs) {
                ResourceRequestAddReq.ResourceRequestInst instDTO = new ResourceRequestAddReq.ResourceRequestInst();
                instDTO.setMktResInstNbr(nbr);
                instDTO.setMktResId(req.getMktResId());
                instDTO.setIsInspection(ResourceConst.CONSTANT_YES);
                if (null != req.getCtCode()) {
                    instDTO.setCtCode(req.getCtCode().get(nbr));
                }
                instDTOList.add(instDTO);
            }
        }
        log.info("ResourceInstCheckServiceImpl.getReqInst  req={},resp={}", JSON.toJSONString(req), JSON.toJSONString(instDTOList));
        return instDTOList;
    }
}