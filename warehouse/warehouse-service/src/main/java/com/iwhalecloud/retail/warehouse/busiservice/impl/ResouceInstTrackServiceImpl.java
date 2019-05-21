package com.iwhalecloud.retail.warehouse.busiservice.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.warehouse.busiservice.ResouceInstTrackService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.constant.Constant;
import com.iwhalecloud.retail.warehouse.dto.ResouceInstTrackDTO;
import com.iwhalecloud.retail.warehouse.dto.ResouceInstTrackDetailDTO;
import com.iwhalecloud.retail.warehouse.dto.ResourceInstDTO;
import com.iwhalecloud.retail.warehouse.dto.ResourceReqDetailDTO;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstAddResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListPageResp;
import com.iwhalecloud.retail.warehouse.manager.ResouceInstTrackDetailManager;
import com.iwhalecloud.retail.warehouse.manager.ResouceInstTrackManager;
import com.iwhalecloud.retail.warehouse.manager.ResourceInstManager;
import com.iwhalecloud.retail.warehouse.manager.ResourceReqDetailManager;
import com.iwhalecloud.retail.warehouse.service.ResouceStoreService;
import com.iwhalecloud.retail.warehouse.service.RetailerResourceInstService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("asyncTaskService")
@Slf4j
public class ResouceInstTrackServiceImpl implements ResouceInstTrackService {

    @Autowired
    private ResourceInstManager resourceInstManager;

    @Autowired
    private ResouceInstTrackManager resouceInstTrackManager;

    @Autowired
    private ResouceInstTrackDetailManager resouceInstTrackDetailManager;

    @Autowired
    private ResourceReqDetailManager resourceReqDetailManager;

    @Reference
    private MerchantService merchantService;

    @Autowired
    private Constant constant;

    @Reference
    private ResouceStoreService resouceStoreService;

    @Reference
    private RetailerResourceInstService retailerResourceInstService;

    @Async
    @Override
    public void asynSaveTrackForAddmin(ResourceInstAddReq req, ResultVO<ResourceInstAddResp> resp) {
        log.info("ResouceInstTrackServiceImpl.asynSaveTrackForAddmin req={}", JSON.toJSONString(req));
        if (!resp.isSuccess()) {
            return;
        }
        Map<String, String> ctCodeMap = req.getCtCode();
        int count = 0;
        for (int i = 0; i < req.getMktResInstNbrs().size(); i++) {
            String mktResInstNbr = req.getMktResInstNbrs().get(i);
            ResouceInstTrackDTO resouceInstTrackDTO = new ResouceInstTrackDTO();
            if (null != ctCodeMap) {
                resouceInstTrackDTO.setCtCode(ctCodeMap.get(mktResInstNbr));
            }
            BeanUtils.copyProperties(req, resouceInstTrackDTO);
            resouceInstTrackDTO.setMktResInstNbr(mktResInstNbr);
            count += resouceInstTrackManager.saveResouceInstTrack(resouceInstTrackDTO);
            log.info("ResouceInstTrackServiceImpl.asynSaveTrackForAddmin resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
            ResouceInstTrackDetailDTO resouceInstTrackDetailDTO = new ResouceInstTrackDetailDTO();
            BeanUtils.copyProperties(req, resouceInstTrackDetailDTO);
            resouceInstTrackDetailDTO.setMktResInstNbr(mktResInstNbr);
            resouceInstTrackDetailDTO.setSourceMerchantId(req.getMerchantId());
            resouceInstTrackDetailDTO.setSourceStoreId(req.getMktResStoreId());
            resouceInstTrackDetailDTO.setSourceLanId(req.getLanId());
            resouceInstTrackDetailDTO.setStorageType(ResourceConst.STORAGETYPE.ADMINISTRATOR_INPUT.getCode());
            count += resouceInstTrackDetailManager.saveResouceInstTrackDetail(resouceInstTrackDetailDTO);
            log.info("ResouceInstTrackServiceImpl.asynSaveTrackForAddmin resouceInstTrackManager.saveResouceInstTrackDetail req={}, resp={}", JSON.toJSONString(resouceInstTrackDetailDTO), count);
        }
        return;
    }

    @Async
    @Override
    public void asynUpdateTrackForAddmin(AdminResourceInstDelReq req, ResultVO resp) {
        log.info("ResouceInstTrackServiceImpl.asynUpdateTrackForAddmin req={}", JSON.toJSONString(req));
        if (!resp.isSuccess()) {
            return;
        }
        List<String> unavailbaleNbrs = (List<String>) resp.getResultData();
        List<String> mktResInstIds = req.getMktResInstIds();
        List<String> distinctList = mktResInstIds.stream().distinct().collect(Collectors.toList());
        if (CollectionUtils.isEmpty(distinctList)) {
            return;
        }
        ResourceInstsGetByIdListAndStoreIdReq selectReq = new ResourceInstsGetByIdListAndStoreIdReq();
        selectReq.setMktResInstIdList(distinctList);
        selectReq.setMktResStoreId(req.getMktResStoreId());
        List<ResourceInstDTO> insts = resourceInstManager.selectByIds(selectReq);
        log.info("ResouceInstTrackServiceImpl.asynUpdateTrackForAddmin resourceInstManager.selectByIds req={}, resp={}", JSON.toJSONString(selectReq), JSON.toJSONString(insts));
        insts.removeAll(unavailbaleNbrs);
        int count = 0;
        for (int i = 0; i < insts.size(); i++) {
            ResourceInstDTO resourceInstDTO = insts.get(i);
            ResouceInstTrackDTO resouceInstTrackDTO = new ResouceInstTrackDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDTO);
            count += resouceInstTrackManager.saveResouceInstTrack(resouceInstTrackDTO);
            log.info("ResouceInstTrackServiceImpl.asynUpdateTrackForAddmin resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
            ResouceInstTrackDetailDTO resouceInstTrackDetailDTO = new ResouceInstTrackDetailDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDetailDTO);
            resouceInstTrackDetailDTO.setStorageType(ResourceConst.STORAGETYPE.ADMINISTRATOR_MODIFICATION.getCode());
            count += resouceInstTrackDetailManager.saveResouceInstTrackDetail(resouceInstTrackDetailDTO);
            log.info("ResouceInstTrackServiceImpl.asynUpdateTrackForAddmin resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
        }
        return;
    }

    @Async
    @Override
    public void asynSaveTrackForMerchant(ResourceInstAddReq req, ResultVO resp) {
        log.info("ResouceInstTrackServiceImpl.asynSaveTrackForMerchant req={}", JSON.toJSONString(req));
        if (!resp.isSuccess()) {
            return;
        }
        Map<String, String> ctCodeMap = req.getCtCode();
        int count = 0;
        for (int i = 0; i < req.getMktResInstNbrs().size(); i++) {
            String mktResInstNbr = req.getMktResInstNbrs().get(i);
            ResouceInstTrackDTO resouceInstTrackDTO = new ResouceInstTrackDTO();
            if (null != ctCodeMap) {
                resouceInstTrackDTO.setCtCode(ctCodeMap.get(mktResInstNbr));
            }
            BeanUtils.copyProperties(req, resouceInstTrackDTO);
            resouceInstTrackDTO.setMktResInstNbr(mktResInstNbr);
            resouceInstTrackDTO.setMktResStoreId(req.getDestStoreId());
            count += resouceInstTrackManager.saveResouceInstTrack(resouceInstTrackDTO);
            log.info("ResouceInstTrackServiceImpl.asynSaveTrackForMerchant resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
            ResouceInstTrackDetailDTO resouceInstTrackDetailDTO = new ResouceInstTrackDetailDTO();
            BeanUtils.copyProperties(req, resouceInstTrackDetailDTO);
            resouceInstTrackDetailDTO.setMktResInstNbr(mktResInstNbr);
            resouceInstTrackDetailDTO.setSourceStoreId(req.getMktResStoreId());
            resouceInstTrackDetailDTO.setSourceMerchantId(req.getMerchantId());
            resouceInstTrackDetailDTO.setStorageType(ResourceConst.STORAGETYPE.VENDOR_INPUT.getCode());
            count += resouceInstTrackDetailManager.saveResouceInstTrackDetail(resouceInstTrackDetailDTO);
            log.info("ResouceInstTrackServiceImpl.asynSaveTrackForMerchant resouceInstTrackManager.saveResouceInstTrackDetail req={}, resp={}", JSON.toJSONString(resouceInstTrackDetailDTO), count);
        }
        return;
    }

    @Async
    @Override
    public void asynUpdateTrackForMerchant(ResourceInstUpdateReq req, ResultVO resp) {
        log.info("ResouceInstTrackServiceImpl.asynUpdateTrackForMerchant req={}", JSON.toJSONString(req));
        if (!resp.isSuccess()) {
            return;
        }
        List<String> mktResInstNbrs = req.getMktResInstNbrs();
        List<String> distinctList = mktResInstNbrs.stream().distinct().collect(Collectors.toList());
        ResourceInstsGetReq resourceInstsGetReq = new ResourceInstsGetReq();
        BeanUtils.copyProperties(req, resourceInstsGetReq);
        resourceInstsGetReq.setMktResStoreId(req.getDestStoreId());
        if (CollectionUtils.isEmpty(distinctList)) {
            return;
        }
        resourceInstsGetReq.setMktResInstNbrs(distinctList);
        resourceInstsGetReq.setMktResStoreId(req.getDestStoreId());
        List<ResourceInstDTO> insts = resourceInstManager.getResourceInsts(resourceInstsGetReq);
        log.info("ResouceInstTrackServiceImpl.asynUpdateTrackForMerchant resourceInstManager.getResourceInsts req={}, resp={}", JSON.toJSONString(distinctList), JSON.toJSONString(insts));
        int count = 0;
        for (int i = 0; i < insts.size(); i++) {
            ResourceInstDTO resourceInstDTO = insts.get(i);
            ResouceInstTrackDTO resouceInstTrackDTO = new ResouceInstTrackDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDTO);
            count += resouceInstTrackManager.saveResouceInstTrack(resouceInstTrackDTO);
            log.info("ResouceInstTrackServiceImpl.asynUpdateTrackForMerchant resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
            ResouceInstTrackDetailDTO resouceInstTrackDetailDTO = new ResouceInstTrackDetailDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDetailDTO);
            resouceInstTrackDetailDTO.setStorageType(ResourceConst.STORAGETYPE.VENDOR_MODIFICATION.getCode());
            count += resouceInstTrackDetailManager.saveResouceInstTrackDetail(resouceInstTrackDetailDTO);
            log.info("ResouceInstTrackServiceImpl.asynUpdateTrackForMerchant resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
        }
        return;
    }

    @Async
    @Override
    public void asynSaveTrackForSupplier(ResourceInstAddReq req, ResultVO resp) {
        log.info("ResouceInstTrackServiceImpl.asynSaveTrackForSupplier req={}", JSON.toJSONString(req));
        if (!resp.isSuccess()) {
            return;
        }
        List<String> mktResInstNbrs = req.getMktResInstNbrs();
        List<String> distinctList = mktResInstNbrs.stream().distinct().collect(Collectors.toList());
        ResourceInstsGetReq resourceInstsGetReq = new ResourceInstsGetReq();
        BeanUtils.copyProperties(req, resourceInstsGetReq);
        if (CollectionUtils.isEmpty(distinctList)) {
            return;
        }
        resourceInstsGetReq.setMktResInstNbrs(distinctList);
        resourceInstsGetReq.setMktResStoreId(req.getDestStoreId());
        List<ResourceInstDTO> insts = resourceInstManager.getResourceInsts(resourceInstsGetReq);
        log.info("ResouceInstTrackServiceImpl.asynSaveTrackForSupplier resourceInstManager.getResourceInsts req={}, resp={}", JSON.toJSONString(distinctList), JSON.toJSONString(insts));
        int count = 0;
        for (int i = 0; i < insts.size(); i++) {
            ResourceInstDTO resourceInstDTO = insts.get(i);
            ResouceInstTrackDTO resouceInstTrackDTO = new ResouceInstTrackDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDTO);
            count += resouceInstTrackManager.saveResouceInstTrack(resouceInstTrackDTO);
            log.info("ResouceInstTrackServiceImpl.asynSaveTrackForSupplier resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
            ResouceInstTrackDetailDTO resouceInstTrackDetailDTO = new ResouceInstTrackDetailDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDetailDTO);
            resouceInstTrackDetailDTO.setStorageType(ResourceConst.STORAGETYPE.SUPPLIER_INPUT.getCode());
            count += resouceInstTrackDetailManager.saveResouceInstTrackDetail(resouceInstTrackDetailDTO);
            log.info("ResouceInstTrackServiceImpl.asynSaveTrackForSupplier resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
        }
        return;
    }

    @Async
    @Override
    public void asynDeleteTrackForSupplier(ResourceInstUpdateReq req, ResultVO resp) {
        log.info("ResouceInstTrackServiceImpl.asynDeleteTrackForSupplier req={}", JSON.toJSONString(req));
        if (!resp.isSuccess()) {
            return;
        }
        List<String> mktResInstNbrs = req.getMktResInstNbrs();
        List<String> distinctList = mktResInstNbrs.stream().distinct().collect(Collectors.toList());
        ResourceInstsGetReq resourceInstsGetReq = new ResourceInstsGetReq();
        BeanUtils.copyProperties(req, resourceInstsGetReq);
        resourceInstsGetReq.setMktResInstNbrs(distinctList);
        resourceInstsGetReq.setMktResStoreId(req.getMktResStoreId());
        if (CollectionUtils.isEmpty(distinctList)) {
            return;
        }
        List<ResourceInstDTO> insts = resourceInstManager.getResourceInsts(resourceInstsGetReq);
        log.info("ResouceInstTrackServiceImpl.asynDeleteTrackForSupplier resourceInstManager.getResourceInsts req={}, resp={}", JSON.toJSONString(distinctList), JSON.toJSONString(insts));
        int count = 0;
        for (int i = 0; i < insts.size(); i++) {
            ResourceInstDTO resourceInstDTO = insts.get(i);
            ResouceInstTrackDTO resouceInstTrackDTO = new ResouceInstTrackDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDTO);
            count += resouceInstTrackManager.saveResouceInstTrack(resouceInstTrackDTO);
            log.info("ResouceInstTrackServiceImpl.asynDeleteTrackForSupplier resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
            ResouceInstTrackDetailDTO resouceInstTrackDetailDTO = new ResouceInstTrackDetailDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDetailDTO);
            resouceInstTrackDetailDTO.setStorageType(ResourceConst.STORAGETYPE.SUPPLIER_DELETION.getCode());
            count += resouceInstTrackDetailManager.saveResouceInstTrackDetail(resouceInstTrackDetailDTO);
            log.info("ResouceInstTrackServiceImpl.asynDeleteTrackForSupplier resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
        }
        return;
    }

    @Override
    public void asynResetTrackForSupplier(ResourceInstUpdateReq req, ResultVO resp) {
        log.info("ResouceInstTrackServiceImpl.asynDeleteTrackForSupplier req={}", JSON.toJSONString(req));
        if (!resp.isSuccess()) {
            return;
        }
        List<String> mktResInstNbrs = req.getMktResInstNbrs();
        List<String> distinctList = mktResInstNbrs.stream().distinct().collect(Collectors.toList());
        ResourceInstsGetReq resourceInstsGetReq = new ResourceInstsGetReq();
        BeanUtils.copyProperties(req, resourceInstsGetReq);
        resourceInstsGetReq.setMktResInstNbrs(distinctList);
        resourceInstsGetReq.setMktResStoreId(req.getMktResStoreId());
        if (CollectionUtils.isEmpty(distinctList)) {
            return;
        }
        List<ResourceInstDTO> insts = resourceInstManager.getResourceInsts(resourceInstsGetReq);
        log.info("ResouceInstTrackServiceImpl.asynDeleteTrackForSupplier resourceInstManager.getResourceInsts req={}, resp={}", JSON.toJSONString(distinctList), JSON.toJSONString(insts));
        int count = 0;
        for (int i = 0; i < insts.size(); i++) {
            ResourceInstDTO resourceInstDTO = insts.get(i);
            ResouceInstTrackDTO resouceInstTrackDTO = new ResouceInstTrackDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDTO);
            count += resouceInstTrackManager.saveResouceInstTrack(resouceInstTrackDTO);
            log.info("ResouceInstTrackServiceImpl.asynDeleteTrackForSupplier resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
            ResouceInstTrackDetailDTO resouceInstTrackDetailDTO = new ResouceInstTrackDetailDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDetailDTO);
            resouceInstTrackDetailDTO.setStorageType(ResourceConst.STORAGETYPE.SUPPLIER_RESET.getCode());
            count += resouceInstTrackDetailManager.saveResouceInstTrackDetail(resouceInstTrackDetailDTO);
            log.info("ResouceInstTrackServiceImpl.asynDeleteTrackForSupplier resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
        }
        return;
    }

    @Async
    @Override
    public void asynUpdateTrackForSupplier(ResourceInstUpdateReq req, ResultVO resp) {
        log.info("ResouceInstTrackServiceImpl.asynUpdateTrackForSupplier req={}", JSON.toJSONString(req));
        if (!resp.isSuccess()) {
            return;
        }
        List<String> mktResInstNbrs = req.getMktResInstNbrs();
        List<String> distinctList = mktResInstNbrs.stream().distinct().collect(Collectors.toList());
        ResourceInstsGetReq resourceInstsGetReq = new ResourceInstsGetReq();
        BeanUtils.copyProperties(req, resourceInstsGetReq);
        resourceInstsGetReq.setMktResInstNbrs(distinctList);
        if (CollectionUtils.isEmpty(distinctList)) {
            return;
        }
        String storageType = "";
        if (req.getStatusCd().equals(ResourceConst.STATUSCD.RESTORAGEING.getCode())) {
            storageType = ResourceConst.STORAGETYPE.RETURNS.getCode();
        } else if (req.getStatusCd().equals(ResourceConst.STATUSCD.EXCHANGEING.getCode())) {
            storageType = ResourceConst.STORAGETYPE.WAREHOUSING.getCode();
        }
        List<ResourceInstDTO> insts = resourceInstManager.getResourceInsts(resourceInstsGetReq);
        log.info("ResouceInstTrackServiceImpl.asynUpdateTrackForSupplier resourceInstManager.getResourceInsts req={}, resp={}", JSON.toJSONString(distinctList), JSON.toJSONString(insts));
        int count = 0;
        for (int i = 0; i < insts.size(); i++) {
            ResourceInstDTO resourceInstDTO = insts.get(i);
            ResouceInstTrackDTO resouceInstTrackDTO = new ResouceInstTrackDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDTO);
            count += resouceInstTrackManager.saveResouceInstTrack(resouceInstTrackDTO);
            log.info("ResouceInstTrackServiceImpl.asynUpdateTrackForSupplier resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
            ResouceInstTrackDetailDTO resouceInstTrackDetailDTO = new ResouceInstTrackDetailDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDetailDTO);
            resouceInstTrackDetailDTO.setStorageType(storageType);
            count += resouceInstTrackDetailManager.saveResouceInstTrackDetail(resouceInstTrackDetailDTO);
            log.info("ResouceInstTrackServiceImpl.asynUpdateTrackForSupplier resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
        }
        return;
    }

    @Async
    @Override
    public void asynAllocateTrackForSupplier(SupplierResourceInstAllocateReq req, ResultVO resp) {
        log.info("ResouceInstTrackServiceImpl.asynAllocateTrackForSupplier req={}", JSON.toJSONString(req));
        if (!resp.isSuccess()) {
            return;
        }
        if (CollectionUtils.isEmpty(req.getMktResInstIds())) {
            return;
        }
        List<String> distinctList = req.getMktResInstIds().stream().distinct().collect(Collectors.toList());
        ResourceInstsGetByIdListAndStoreIdReq selectReq = new ResourceInstsGetByIdListAndStoreIdReq();
        selectReq.setMktResInstIdList(distinctList);
        selectReq.setMktResStoreId(req.getMktResStoreId());
        List<ResourceInstDTO> insts = resourceInstManager.selectByIds(selectReq);
        log.info("ResouceInstTrackServiceImpl.asynAllocateTrackForSupplier resourceInstManager.selectByIds req={}, resp={}", JSON.toJSONString(distinctList), JSON.toJSONString(insts));
        int count = 0;
        for (int i = 0; i < insts.size(); i++) {
            ResourceInstDTO resourceInstDTO = insts.get(i);
            ResouceInstTrackDTO resouceInstTrackDTO = new ResouceInstTrackDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDTO);
            count += resouceInstTrackManager.saveResouceInstTrack(resouceInstTrackDTO);
            log.info("ResouceInstTrackServiceImpl.asynAllocateTrackForSupplier resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
            ResouceInstTrackDetailDTO resouceInstTrackDetailDTO = new ResouceInstTrackDetailDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDetailDTO);
            resouceInstTrackDetailDTO.setStorageType(ResourceConst.STORAGETYPE.WAREHOUSING.getCode());
            count += resouceInstTrackDetailManager.saveResouceInstTrackDetail(resouceInstTrackDetailDTO);
            log.info("ResouceInstTrackServiceImpl.asynAllocateTrackForSupplier resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
        }
        return;
    }

    @Async
    @Override
    public void asynShipTrackForSupplier(DeliveryResourceInstReq req, ResultVO resp) {
        log.info("ResouceInstTrackServiceImpl.asynShipTrackForSupplier req={}", JSON.toJSONString(req));
        if (!resp.isSuccess()) {
            return;
        }
        StoreGetStoreIdReq storePageReq = new StoreGetStoreIdReq();
        storePageReq.setMerchantId(req.getSellerMerchantId());
        storePageReq.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
        String storeId = resouceStoreService.getStoreId(storePageReq);
        List<String> distinctList = Lists.newArrayList();
        List<DeliveryResourceInstItem> deliveryResourceInstItemList = req.getDeliveryResourceInstItemList();
        for (DeliveryResourceInstItem deliveryResourceInstItem : deliveryResourceInstItemList) {
            distinctList.addAll(deliveryResourceInstItem.getMktResInstNbrs());
        }
        ResourceInstsGetReq resourceInstsGetReq = new ResourceInstsGetReq();
        resourceInstsGetReq.setMktResInstNbrs(distinctList);
        if (CollectionUtils.isEmpty(distinctList)) {
            return;
        }
        resourceInstsGetReq.setMktResStoreId(storeId);
        List<ResourceInstDTO> insts = resourceInstManager.getResourceInsts(resourceInstsGetReq);
        log.info("ResouceInstTrackServiceImpl.asynShipTrackForSupplier resourceInstManager.getResourceInsts req={}, resp={}", JSON.toJSONString(distinctList), JSON.toJSONString(insts));
        int count = 0;
        for (int i = 0; i < insts.size(); i++) {
            ResourceInstDTO resourceInstDTO = insts.get(i);
            ResouceInstTrackDTO resouceInstTrackDTO = new ResouceInstTrackDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDTO);
            count += resouceInstTrackManager.saveResouceInstTrack(resouceInstTrackDTO);
            log.info("ResouceInstTrackServiceImpl.asynShipTrackForSupplier resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
            ResouceInstTrackDetailDTO resouceInstTrackDetailDTO = new ResouceInstTrackDetailDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDetailDTO);
            resouceInstTrackDetailDTO.setOrderId(req.getOrderId());
            // 发货出库
            resouceInstTrackDetailDTO.setOutTime(new Date());
            resouceInstTrackDetailDTO.setOrderId(req.getOrderId());
            resouceInstTrackDetailDTO.setStorageType(ResourceConst.STORAGETYPE.TRANSACTION_WAREHOUSING.getCode());
            count += resouceInstTrackDetailManager.saveResouceInstTrackDetail(resouceInstTrackDetailDTO);
            log.info("ResouceInstTrackServiceImpl.asynShipTrackForSupplier resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
        }
        return;
    }

    @Async
    @Override
    public void asynAcceptTrackForSupplier(DeliveryResourceInstReq req, ResultVO resp) {
        log.info("ResouceInstTrackServiceImpl.asynAcceptTrackForSupplier req={}", JSON.toJSONString(req));
        if (!resp.isSuccess()) {
            return;
        }
        StoreGetStoreIdReq storePageReq = new StoreGetStoreIdReq();
        storePageReq.setMerchantId(req.getBuyerMerchantId());
        storePageReq.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
        String storeId = resouceStoreService.getStoreId(storePageReq);
        log.info("ResouceInstTrackServiceImpl.asynAcceptTrackForSupplier resouceStoreService.getStoreId req={},storeId={}", JSON.toJSONString(storePageReq), JSON.toJSONString(storeId));

        List<String> distinctList = Lists.newArrayList();
        List<DeliveryResourceInstItem> deliveryResourceInstItemList = req.getDeliveryResourceInstItemList();
        for (DeliveryResourceInstItem deliveryResourceInstItem : deliveryResourceInstItemList) {
            distinctList.addAll(deliveryResourceInstItem.getMktResInstNbrs());
        }
        ResourceInstsGetReq resourceInstsGetReq = new ResourceInstsGetReq();
        resourceInstsGetReq.setMktResInstNbrs(distinctList);
        if (CollectionUtils.isEmpty(distinctList)) {
            return;
        }
        // 是否省内直供：在零售商收货确认时更新，是省包商交易到零售商的情况下
        String ifDirectSuppLy = ResourceConst.CONSTANT_NO;
        // 是否地包供货：地包商交易给零售商时填是
        String ifGroundSupply = ResourceConst.CONSTANT_NO;
        String buyerMerchantId = req.getBuyerMerchantId();
        resourceInstsGetReq.setMktResStoreId(storeId);
        List<ResourceInstDTO> insts = resourceInstManager.getResourceInsts(resourceInstsGetReq);
        log.info("ResouceInstTrackServiceImpl.asynAcceptTrackForSupplier resourceInstManager.getResourceInsts req={}, resp={}", JSON.toJSONString(distinctList), JSON.toJSONString(insts));
        int count = 0;
        for (int i = 0; i < insts.size(); i++) {
            ResourceInstDTO resourceInstDTO = insts.get(i);
            ResouceInstTrackDTO resouceInstTrackDTO = new ResouceInstTrackDTO();
            if (PartnerConst.MerchantTypeEnum.SUPPLIER_PROVINCE.equals(resourceInstDTO.getMerchantType())) {
                ifDirectSuppLy = ResourceConst.CONSTANT_YES;
            }
            if (PartnerConst.MerchantTypeEnum.SUPPLIER_GROUND.equals(resourceInstDTO.getMerchantType())) {
                ifGroundSupply = ResourceConst.CONSTANT_YES;
            }
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDTO);
            resouceInstTrackDTO.setIfDirectSupply(ifDirectSuppLy);
            resouceInstTrackDTO.setIfGroundSupply(ifGroundSupply);
            resouceInstTrackDTO.setMerchantId(buyerMerchantId);
            count += resouceInstTrackManager.saveResouceInstTrack(resouceInstTrackDTO);
            log.info("ResouceInstTrackServiceImpl.asynAcceptTrackForSupplier resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
            ResouceInstTrackDetailDTO resouceInstTrackDetailDTO = new ResouceInstTrackDetailDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDetailDTO);
            // 收货入库
            resouceInstTrackDetailDTO.setInTime(new Date());
            resouceInstTrackDetailDTO.setOrderId(req.getOrderId());
            resouceInstTrackDetailDTO.setStorageType(ResourceConst.STORAGETYPE.TRANSACTION_WAREHOUSING.getCode());
            count += resouceInstTrackDetailManager.saveResouceInstTrackDetail(resouceInstTrackDetailDTO);
            log.info("ResouceInstTrackServiceImpl.asynAcceptTrackForSupplier resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
        }
        return;
    }

    @Async
    @Override
    public void asynBackShipTrackForSupplier(DeliveryResourceInstReq req, ResultVO resp) {
        log.info("ResouceInstTrackServiceImpl.asynBackShipTrackForSupplier req={}", JSON.toJSONString(req));
        if (!resp.isSuccess()) {
            return;
        }
        StoreGetStoreIdReq storePageReq = new StoreGetStoreIdReq();
        storePageReq.setMerchantId(req.getBuyerMerchantId());
        storePageReq.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
        String storeId = resouceStoreService.getStoreId(storePageReq);
        log.info("ResouceInstTrackServiceImpl.asynBackShipTrackForSupplier resouceStoreService.getStoreId req={},storeId={}", JSON.toJSONString(storePageReq), JSON.toJSONString(storeId));

        List<String> distinctList = Lists.newArrayList();
        List<DeliveryResourceInstItem> deliveryResourceInstItemList = req.getDeliveryResourceInstItemList();
        for (DeliveryResourceInstItem deliveryResourceInstItem : deliveryResourceInstItemList) {
            distinctList.addAll(deliveryResourceInstItem.getMktResInstNbrs());
        }
        ResourceInstsGetReq resourceInstsGetReq = new ResourceInstsGetReq();
        resourceInstsGetReq.setMktResInstNbrs(distinctList);
        if (CollectionUtils.isEmpty(distinctList)) {
            return;
        }
        resourceInstsGetReq.setMktResStoreId(storeId);
        List<ResourceInstDTO> insts = resourceInstManager.getResourceInsts(resourceInstsGetReq);
        int count = 0;
        for (int i = 0; i < insts.size(); i++) {
            ResourceInstDTO resourceInstDTO = insts.get(i);
            ResouceInstTrackDTO resouceInstTrackDTO = new ResouceInstTrackDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDTO);
            count += resouceInstTrackManager.saveResouceInstTrack(resouceInstTrackDTO);
            log.info("ResouceInstTrackServiceImpl.asynBackShipTrackForSupplier resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
            ResouceInstTrackDetailDTO resouceInstTrackDetailDTO = new ResouceInstTrackDetailDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDetailDTO);
            resouceInstTrackDetailDTO.setOrderId(req.getOrderId());
            resouceInstTrackDetailDTO.setStorageType(ResourceConst.STORAGETYPE.SUPPLIER_RETURN_DELIVERY.getCode());
            count += resouceInstTrackDetailManager.saveResouceInstTrackDetail(resouceInstTrackDetailDTO);
            log.info("ResouceInstTrackServiceImpl.asynBackShipTrackForSupplier resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
        }
        return;
    }

    @Async
    @Override
    public void asynBackAcceptTrackForSupplier(DeliveryResourceInstReq req, ResultVO resp) {
        log.info("ResouceInstTrackServiceImpl.asynBackAcceptTrackForSupplier req={}", JSON.toJSONString(req));
        if (!resp.isSuccess()) {
            return;
        }
        StoreGetStoreIdReq storePageReq = new StoreGetStoreIdReq();
        storePageReq.setMerchantId(req.getSellerMerchantId());
        storePageReq.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
        String storeId = resouceStoreService.getStoreId(storePageReq);
        log.info("ResouceInstTrackServiceImpl.asynBackAcceptTrackForSupplier resouceStoreService.getStoreId req={},storeId={}", JSON.toJSONString(storePageReq), JSON.toJSONString(storeId));

        List<String> distinctList = Lists.newArrayList();
        List<DeliveryResourceInstItem> deliveryResourceInstItemList = req.getDeliveryResourceInstItemList();
        for (DeliveryResourceInstItem deliveryResourceInstItem : deliveryResourceInstItemList) {
            distinctList.addAll(deliveryResourceInstItem.getMktResInstNbrs());
        }
        ResourceInstsGetReq resourceInstsGetReq = new ResourceInstsGetReq();
        resourceInstsGetReq.setMktResInstNbrs(distinctList);
        if (CollectionUtils.isEmpty(distinctList)) {
            return;
        }
        resourceInstsGetReq.setMktResStoreId(storeId);
        List<ResourceInstDTO> insts = resourceInstManager.getResourceInsts(resourceInstsGetReq);
        log.info("ResouceInstTrackServiceImpl.asynBackAcceptTrackForSupplier resourceInstManager.getResourceInsts req={}, resp={}", JSON.toJSONString(distinctList), JSON.toJSONString(insts));
        int count = 0;
        for (int i = 0; i < insts.size(); i++) {
            ResourceInstDTO resourceInstDTO = insts.get(i);
            ResouceInstTrackDTO resouceInstTrackDTO = new ResouceInstTrackDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDTO);
            count += resouceInstTrackManager.saveResouceInstTrack(resouceInstTrackDTO);
            log.info("ResouceInstTrackServiceImpl.asynBackAcceptTrackForSupplier resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
            ResouceInstTrackDetailDTO resouceInstTrackDetailDTO = new ResouceInstTrackDetailDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDetailDTO);
            resouceInstTrackDetailDTO.setOrderId(req.getOrderId());
            resouceInstTrackDetailDTO.setStorageType(ResourceConst.STORAGETYPE.SUPPLIER_RETURN_AND_RECEIVE.getCode());
            count += resouceInstTrackDetailManager.saveResouceInstTrackDetail(resouceInstTrackDetailDTO);
            log.info("ResouceInstTrackServiceImpl.asynBackAcceptTrackForSupplier resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
        }
        return;
    }

    @Async
    @Override
    public void asynGreenChannelForRetail(ResourceInstAddReq req, ResultVO resp) {
        log.info("ResouceInstTrackServiceImpl.asynGreenChannelForRetail req={}", JSON.toJSONString(req));
        if (!resp.isSuccess()) {
            return;
        }

        // 获取仓库
        StoreGetStoreIdReq storeGetStoreIdReq = new StoreGetStoreIdReq();
        storeGetStoreIdReq.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
        storeGetStoreIdReq.setMerchantId(req.getMerchantId());
        String mktResStoreId = resouceStoreService.getStoreId(storeGetStoreIdReq);
        log.info("ResouceInstTrackServiceImpl.asynGreenChannelForRetail resouceStoreService.getStoreId req={},resp={}", JSON.toJSONString(storeGetStoreIdReq), mktResStoreId);

        ResourceInstBatchReq resourceInstsGetReq = new ResourceInstBatchReq();
        BeanUtils.copyProperties(req, resourceInstsGetReq);
        resourceInstsGetReq.setMktResStoreId(mktResStoreId);

        ResultVO<List<ResourceInstListPageResp>> listResultVO = retailerResourceInstService.getBatch(resourceInstsGetReq);
        log.info("ResouceInstTrackServiceImpl.asynGreenChannelForRetail retailerResourceInstService.getBatch req={}, resp={}", JSON.toJSONString(resourceInstsGetReq), JSON.toJSONString(listResultVO));
        List<ResourceInstListPageResp> insts = listResultVO.getResultData();
        int count = 0;
        for (int i = 0; i < insts.size(); i++) {
            ResourceInstListPageResp resourceInstDTO = insts.get(i);
            ResouceInstTrackDTO resouceInstTrackDTO = new ResouceInstTrackDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDTO);
            resouceInstTrackDTO.setIfGreenChannel(ResourceConst.CONSTANT_YES);
            resouceInstTrackDTO.setMerchantId(req.getMerchantId());
            count += resouceInstTrackManager.saveResouceInstTrack(resouceInstTrackDTO);
            log.info("ResouceInstTrackServiceImpl.asynGreenChannelForRetail resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
            ResouceInstTrackDetailDTO resouceInstTrackDetailDTO = new ResouceInstTrackDetailDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDetailDTO);
            resouceInstTrackDetailDTO.setStorageType(ResourceConst.STORAGETYPE.GREEN_CHANNEL.getCode());
            count += resouceInstTrackDetailManager.saveResouceInstTrackDetail(resouceInstTrackDetailDTO);
            log.info("ResouceInstTrackServiceImpl.asynGreenChannelForRetail resouceInstTrackManager.saveResouceInstTrackDetail req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
        }
        return;
    }

    @Async
    @Override
    public void asynDeleteInstForRetail(ResourceInstUpdateReq req, ResultVO resp) {
        log.info("ResouceInstTrackServiceImpl.asynDeleteInstForRetail req={}", JSON.toJSONString(req));
        if (!resp.isSuccess()) {
            return;
        }
        List<String> mktResInstNbrs = req.getMktResInstNbrs();
        List<String> distinctList = mktResInstNbrs.stream().distinct().collect(Collectors.toList());
        ResourceInstsGetReq resourceInstsGetReq = new ResourceInstsGetReq();
        BeanUtils.copyProperties(req, resourceInstsGetReq);
        resourceInstsGetReq.setMktResInstNbrs(distinctList);
        if (CollectionUtils.isEmpty(distinctList)) {
            return;
        }
        resourceInstsGetReq.setMktResStoreId(req.getDestStoreId());
        List<ResourceInstDTO> insts = resourceInstManager.getResourceInsts(resourceInstsGetReq);
        log.info("ResouceInstTrackServiceImpl.asynDeleteInstForRetail resourceInstManager.getResourceInsts req={}, resp={}", JSON.toJSONString(distinctList), JSON.toJSONString(insts));
        int count = 0;
        for (int i = 0; i < insts.size(); i++) {
            ResourceInstDTO resourceInstDTO = insts.get(i);
            ResouceInstTrackDTO resouceInstTrackDTO = new ResouceInstTrackDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDTO);
            count += resouceInstTrackManager.saveResouceInstTrack(resouceInstTrackDTO);
            log.info("ResouceInstTrackServiceImpl.asynGreenChannelForRetail resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
            ResouceInstTrackDetailDTO resouceInstTrackDetailDTO = new ResouceInstTrackDetailDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDetailDTO);
            resouceInstTrackDetailDTO.setStorageType(ResourceConst.STORAGETYPE.RETAILER_DELETION.getCode());
            count += resouceInstTrackDetailManager.saveResouceInstTrackDetail(resouceInstTrackDetailDTO);
            log.info("ResouceInstTrackServiceImpl.asynGreenChannelForRetail resouceInstTrackManager.saveResouceInstTrackDetail req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
        }
        return;
    }

    @Async
    @Override
    public void pickResourceInstForRetail(ResourceInstPickupReq req, ResultVO resp) {
        log.info("ResouceInstTrackServiceImpl.pickResourceInst req={}", JSON.toJSONString(req));
        if (!resp.isSuccess()) {
            return;
        }
        List<String> mktResInstNbrs = req.getMktResInstNbrs();
        List<String> distinctList = mktResInstNbrs.stream().distinct().collect(Collectors.toList());
        ResourceInstsGetReq resourceInstsGetReq = new ResourceInstsGetReq();
        BeanUtils.copyProperties(req, resourceInstsGetReq);
        resourceInstsGetReq.setMktResInstNbrs(distinctList);
        if (CollectionUtils.isEmpty(distinctList)) {
            return;
        }
        resourceInstsGetReq.setMktResStoreId(req.getMktResStoreId());
        List<ResourceInstDTO> insts = resourceInstManager.getResourceInsts(resourceInstsGetReq);
        log.info("ResouceInstTrackServiceImpl.pickResourceInsForRetail resourceInstManager.getResourceInsts req={}, resp={}", JSON.toJSONString(distinctList), JSON.toJSONString(insts));
        int count = 0;
        for (int i = 0; i < insts.size(); i++) {
            ResourceInstDTO resourceInstDTO = insts.get(i);
            ResouceInstTrackDTO resouceInstTrackDTO = new ResouceInstTrackDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDTO);
            count += resouceInstTrackManager.saveResouceInstTrack(resouceInstTrackDTO);
            log.info("ResouceInstTrackServiceImpl.asynGreenChannelForRetail resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
            ResouceInstTrackDetailDTO resouceInstTrackDetailDTO = new ResouceInstTrackDetailDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDetailDTO);
            resouceInstTrackDetailDTO.setStorageType(ResourceConst.STORAGETYPE.RETAILER_APPOINTMENT.getCode());
            count += resouceInstTrackDetailManager.saveResouceInstTrackDetail(resouceInstTrackDetailDTO);
            log.info("ResouceInstTrackServiceImpl.asynGreenChannelForRetail resouceInstTrackManager.saveResouceInstTrackDetail req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
        }
        return;
    }

    @Async
    @Override
    public void allocateResourceInstForRetail(RetailerResourceInstAllocateReq req, ResultVO resp) {
        log.info("ResouceInstTrackServiceImpl.allocateResourceInstForRetail req={}", JSON.toJSONString(req));
        if (!resp.isSuccess()) {
            return;
        }
        if (CollectionUtils.isEmpty(req.getMktResInstIds())) {
            return;
        }
        List<String> distinctList = req.getMktResInstIds().stream().distinct().collect(Collectors.toList());
        ResourceInstsGetByIdListAndStoreIdReq selectReq = new ResourceInstsGetByIdListAndStoreIdReq();
        selectReq.setMktResInstIdList(distinctList);
        selectReq.setMktResStoreId(req.getMktResStoreId());
        List<ResourceInstDTO> insts = resourceInstManager.selectByIds(selectReq);
        log.info("ResouceInstTrackServiceImpl.allocateResourceInstForRetail resourceInstManager.selectByIds req={}, resp={}", JSON.toJSONString(distinctList), JSON.toJSONString(insts));
        int count = 0;
        for (int i = 0; i < insts.size(); i++) {
            ResourceInstDTO resourceInstDTO = insts.get(i);
            ResouceInstTrackDTO resouceInstTrackDTO = new ResouceInstTrackDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDTO);
            resouceInstTrackDTO.setIfGreenChannel(ResourceConst.CONSTANT_YES);
            count += resouceInstTrackManager.saveResouceInstTrack(resouceInstTrackDTO);
            log.info("ResouceInstTrackServiceImpl.asynGreenChannelForRetail resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
            ResouceInstTrackDetailDTO resouceInstTrackDetailDTO = new ResouceInstTrackDetailDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDetailDTO);
            resouceInstTrackDetailDTO.setStorageType(ResourceConst.STORAGETYPE.RETAILER_ALLOCATION.getCode());
            count += resouceInstTrackDetailManager.saveResouceInstTrackDetail(resouceInstTrackDetailDTO);
            log.info("ResouceInstTrackServiceImpl.asynGreenChannelForRetail resouceInstTrackManager.saveResouceInstTrackDetail req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
        }
        return;
    }

    @Async
    @Override
    public void allocateResourceIntsWarehousingForRetail(ConfirmReciveNbrReq req, ResultVO resp) {
        // 找到串码明细
        log.info("ResouceInstTrackServiceImpl.allocateResourceIntsWarehousingForRetail req={}", JSON.toJSONString(req));
        ResourceReqDetailQueryReq queryReq = new ResourceReqDetailQueryReq();
        queryReq.setMktResReqId(req.getResReqId());
        List<ResourceReqDetailDTO> list = resourceReqDetailManager.listDetail(queryReq);
        log.info("ResouceInstTrackServiceImpl.allocateResourceIntsWarehousingForRetail resourceReqDetailManager.listDetail req={}", JSON.toJSONString(queryReq));
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        int count = 0;
        for (ResourceReqDetailDTO resourceReqDetailDTO : list) {
            ResouceInstTrackDTO resouceInstTrackDTO = new ResouceInstTrackDTO();
            BeanUtils.copyProperties(resourceReqDetailDTO, resouceInstTrackDTO);
            resouceInstTrackDTO.setIfGreenChannel(ResourceConst.CONSTANT_YES);
            count += resouceInstTrackManager.saveResouceInstTrack(resouceInstTrackDTO);
            log.info("ResouceInstTrackServiceImpl.asynGreenChannelForRetail resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
            ResouceInstTrackDetailDTO resouceInstTrackDetailDTO = new ResouceInstTrackDetailDTO();
            BeanUtils.copyProperties(resourceReqDetailDTO, resouceInstTrackDetailDTO);
            resouceInstTrackDetailDTO.setStorageType(ResourceConst.STORAGETYPE.ALLOCATION_AND_WAREHOUSING.getCode());
            count += resouceInstTrackDetailManager.saveResouceInstTrackDetail(resouceInstTrackDetailDTO);
            log.info("ResouceInstTrackServiceImpl.asynGreenChannelForRetail resouceInstTrackManager.saveResouceInstTrackDetail req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
        }
    }

    @Async
    @Override
    public void allocateResourceIntsWarehousingCancelForRetail(ConfirmReciveNbrReq req, ResultVO resp) {
        // 找到串码明细
        log.info("ResouceInstTrackServiceImpl.allocateResourceIntsWarehousingCancelForRetail req={}", JSON.toJSONString(req));
        ResourceReqDetailQueryReq queryReq = new ResourceReqDetailQueryReq();
        queryReq.setMktResReqId(req.getResReqId());
        List<ResourceReqDetailDTO> list = resourceReqDetailManager.listDetail(queryReq);
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        int count = 0;
        for (ResourceReqDetailDTO resourceReqDetailDTO : list) {
            ResouceInstTrackDTO resouceInstTrackDTO = new ResouceInstTrackDTO();
            BeanUtils.copyProperties(resourceReqDetailDTO, resouceInstTrackDTO);
            count += resouceInstTrackManager.saveResouceInstTrack(resouceInstTrackDTO);
            log.info("ResouceInstTrackServiceImpl.asynGreenChannelForRetail resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
            ResouceInstTrackDetailDTO resouceInstTrackDetailDTO = new ResouceInstTrackDetailDTO();
            BeanUtils.copyProperties(resourceReqDetailDTO, resouceInstTrackDetailDTO);
            resouceInstTrackDetailDTO.setStorageType(ResourceConst.STORAGETYPE.ALLOCATION_AND_WAREHOUSING_CANCEL.getCode());
            count += resouceInstTrackDetailManager.saveResouceInstTrackDetail(resouceInstTrackDetailDTO);
            log.info("ResouceInstTrackServiceImpl.asynGreenChannelForRetail resouceInstTrackManager.saveResouceInstTrackDetail req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
        }
    }

    @Override
    public String qryOrderIdByNbr(String nbr) {
        return resouceInstTrackManager.qryOrderIdByNbr(nbr, ResourceConst.STORAGETYPE.SUPPLIER_DELIVERY.getCode());
    }

    @Override
    public ResultVO<ResouceInstTrackDTO> getResourceInstTrackByNbrAndMerchantId(String nbr, String merchantId){
        return ResultVO.success(resouceInstTrackManager.getResourceInstTrackByNbrAndMerchantId(nbr, merchantId));
    }
}
