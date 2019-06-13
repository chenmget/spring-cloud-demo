package com.iwhalecloud.retail.warehouse.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.req.ProductGetByIdReq;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.constant.Constant;
import com.iwhalecloud.retail.warehouse.dto.ResouceStoreDTO;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.manager.ResouceStoreManager;
import com.iwhalecloud.retail.warehouse.manager.ResourceInstManager;
import com.iwhalecloud.retail.warehouse.service.ResouceStoreService;
import com.iwhalecloud.retail.warehouse.service.ResourceInstStoreService;
import com.iwhalecloud.retail.warehouse.service.TradeResourceInstService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service("tradeResourceInstService")
public class TradeResourceInstServiceImpl implements TradeResourceInstService {

    @Autowired
    private ResourceInstManager resourceInstManager;

    @Autowired
    private ResouceStoreManager resouceStoreManager;

    @Reference
    private ProductService productService;

    @Reference
    private MerchantService merchantService;

    @Autowired
    private ResourceInstService resourceInstService;

    @Reference
    private ResouceStoreService resouceStoreService;

    @Reference
    private ResourceInstStoreService resourceInstStoreService;

    @Autowired
    private Constant constant;


    @Override
    public ResultVO tradeOutResourceInst(TradeResourceInstReq req) {
        StoreGetStoreIdReq storeGetStoreIdReq = new StoreGetStoreIdReq();
        storeGetStoreIdReq.setMerchantId(req.getSellerMerchantId());
        storeGetStoreIdReq.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
        String destStoreId = null;
        try{
            destStoreId = resouceStoreService.getStoreId(storeGetStoreIdReq);
            if (StringUtils.isEmpty(destStoreId)) {
                return ResultVO.error(constant.getCannotGetStoreMsg());
            }
            log.info("TradeResourceInstServiceImpl.tradeOutResourceInst resouceStoreService.getStoreId req={},storeId={}", JSON.toJSONString(storeGetStoreIdReq), destStoreId);
        }catch (Exception e){
            return ResultVO.error(constant.getGetRepeatStoreMsg());
        }

        StorePageReq storePageReq = new StorePageReq();
        storePageReq.setStoreGrade(ResourceConst.STORE_GRADE.CITY.getCode());
        storePageReq.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
        storePageReq.setStoreType(ResourceConst.STORE_TYPE.CITY.getCode());
        storePageReq.setLanIdList(Lists.newArrayList(req.getLanId()));
        Page<ResouceStoreDTO> storeDTOPage = resouceStoreManager.pageStore(storePageReq);
        log.info("TradeResourceInstServiceImpl.tradeOutResourceInst resouceStoreManager.pageStore merchantStoreId={}", JSON.toJSONString(storePageReq), JSON.toJSONString(storeDTOPage.getRecords()));
        if (null == storeDTOPage || CollectionUtils.isEmpty(storeDTOPage.getRecords())) {
            return ResultVO.error(constant.getCannotGetStoreMsg());
        }
        String sourceStoreId = storeDTOPage.getRecords().get(0).getMktResStoreId();
        for (TradeResourceInstItem item : req.getTradeResourceInstItemList()) {
            ResourceInstUpdateReq resourceInstUpdateReq = new ResourceInstUpdateReq();
            resourceInstUpdateReq.setMktResInstNbrs(item.getMktResInstNbrs());
            resourceInstUpdateReq.setCheckStatusCd(Lists.newArrayList(ResourceConst.STATUSCD.DELETED.getCode()));
            resourceInstUpdateReq.setMktResStoreId(sourceStoreId);
            resourceInstUpdateReq.setDestStoreId(destStoreId);
            resourceInstUpdateReq.setStatusCd(ResourceConst.STATUSCD.SALED.getCode());
            resourceInstUpdateReq.setEventType(ResourceConst.EVENTTYPE.RECEIVE.getCode());
            resourceInstUpdateReq.setObjType(ResourceConst.EVENT_OBJTYPE.PUT_STORAGE.getCode());
            resourceInstUpdateReq.setObjId(req.getOrderId());
            resourceInstUpdateReq.setMerchantId(req.getSellerMerchantId());
            resourceInstUpdateReq.setMktResId(item.getProductId());
            resourceInstUpdateReq.setOrderId(req.getOrderId());
            resourceInstUpdateReq.setCreateTime(new Date());
            ResultVO delRS = resourceInstService.updateResourceInstForTransaction(resourceInstUpdateReq);
            log.info("TradeResourceInstServiceImpl.tradeOutResourceInst resourceInstService.delResourceInst req={},resp={}", JSON.toJSONString(resourceInstUpdateReq), JSON.toJSONString(delRS));
            if (delRS == null || !delRS.isSuccess()) {
                return ResultVO.error(delRS.getResultMsg());
            }
        }
        return ResultVO.success(true);
    }

    @Override
    public ResultVO tradeInResourceInst(TradeResourceInstReq req) {
        ResultVO<MerchantDTO> merchantResultVO = merchantService.getMerchantById(req.getSellerMerchantId());
        if (!merchantResultVO.isSuccess() || null == merchantResultVO.getResultData()) {
            return ResultVO.error(constant.getCannotGetMerchantMsg());
        }
        MerchantDTO merchantDTO = merchantResultVO.getResultData();
        log.info("TradeResourceInstServiceImpl.tradeInResourceInst merchantService.getMerchantById req={},resp={}", JSON.toJSONString(req), JSON.toJSONString(merchantResultVO));

        // 源仓库
        StoreGetStoreIdReq storeGetStoreIdReq = new StoreGetStoreIdReq();
        storeGetStoreIdReq.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
        storeGetStoreIdReq.setMerchantId(req.getSellerMerchantId());
        String mktResStoreId = resouceStoreService.getStoreId(storeGetStoreIdReq);
        log.info("TradeResourceInstServiceImpl.tradeInResourceInst resouceStoreService.getStoreId req={},resp={}", JSON.toJSONString(storeGetStoreIdReq), mktResStoreId);
        // 目标仓库
        StorePageReq storePageReq = new StorePageReq();
        storePageReq.setStoreGrade(ResourceConst.STORE_GRADE.CITY.getCode());
        storePageReq.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
        storePageReq.setStoreType(ResourceConst.STORE_TYPE.CITY.getCode());
        storePageReq.setLanIdList(Lists.newArrayList(req.getLanId()));
        Page<ResouceStoreDTO> storeDTOPage = resouceStoreManager.pageStore(storePageReq);
        log.info("TradeResourceInstServiceImpl.tradeInResourceInst resouceStoreManager.pageStore merchantStoreId={}", JSON.toJSONString(storeGetStoreIdReq), JSON.toJSONString(storeDTOPage.getRecords()));
        if (null == storeDTOPage || CollectionUtils.isEmpty(storeDTOPage.getRecords())) {
            return ResultVO.error(constant.getCannotGetStoreMsg());
        }
        ResouceStoreDTO storeDTO = storeDTOPage.getRecords().get(0);
        String destStoreId = storeDTO.getMktResStoreId();

        for (TradeResourceInstItem item : req.getTradeResourceInstItemList()) {
            if (StringUtils.isEmpty(item.getProductId())) {
                return ResultVO.error(constant.getNoProductIdMsg());
            }
            ResourceInstAddReq resourceInstAddReq = new ResourceInstAddReq();
            resourceInstAddReq.setMktResInstNbrs(item.getMktResInstNbrs());
            resourceInstAddReq.setCreateStaff(req.getSellerMerchantId());
            resourceInstAddReq.setStatusCd(ResourceConst.STATUSCD.AVAILABLE.getCode());
            resourceInstAddReq.setEventType(ResourceConst.EVENTTYPE.RECEIVE.getCode());
            resourceInstAddReq.setSourceType(ResourceConst.SOURCE_TYPE.SUPPLIER.getCode());
            resourceInstAddReq.setStorageType(ResourceConst.STORAGETYPE.LEADING_INTO_STORAGE.getCode());
            resourceInstAddReq.setMktResInstType(ResourceConst.MKTResInstType.NONTRANSACTION.getCode());
            resourceInstAddReq.setObjType(ResourceConst.EVENT_OBJTYPE.PUT_STORAGE.getCode());
            resourceInstAddReq.setObjId(req.getOrderId());
            resourceInstAddReq.setMktResId(item.getProductId());
            resourceInstAddReq.setOrderId(req.getOrderId());
            resourceInstAddReq.setCreateTime(new Date());
            resourceInstAddReq.setMktResStoreId(mktResStoreId);
            resourceInstAddReq.setDestStoreId(destStoreId);
            resourceInstAddReq.setSupplierName(merchantDTO.getMerchantName());
            resourceInstAddReq.setSupplierCode(merchantDTO.getMerchantCode());
            resourceInstAddReq.setMerchantId(storeDTO.getMerchantId());
            resourceInstAddReq.setLanId(req.getLanId());
            resourceInstAddReq.setRegionId(storeDTO.getRegionId());
            resourceInstAddReq.setEventStatusCd(ResourceConst.EVENTSTATE.DONE.getCode());
            resourceInstAddReq.setMerchantType(storeDTO.getMerchantType());
            ProductGetByIdReq productReq = new ProductGetByIdReq();
            productReq.setProductId(item.getProductId());
            ResultVO<ProductResp> productRespResultVO = productService.getProductInfo(productReq);
            log.info("TradeResourceInstServiceImpl.tradeInResourceInst productService.getProductInfo productId={},resp={}", item.getProductId(), JSON.toJSONString(productRespResultVO));
            if (productRespResultVO.isSuccess() && null != productRespResultVO.getResultData()) {
                resourceInstAddReq.setTypeId(productRespResultVO.getResultData().getTypeId());
            }
            ResultVO addRS = this.resourceInstService.addResourceInstForTransaction(resourceInstAddReq);
            log.info("TradeResourceInstServiceImpl.tradeInResourceInst resourceInstService.addResourceInst req={},resp={}", JSON.toJSONString(resourceInstAddReq), JSON.toJSONString(addRS));
            if (addRS == null || !addRS.isSuccess()) {
                return ResultVO.error(addRS.getResultMsg());
            }
        }
        return ResultVO.success(true);
    }

}