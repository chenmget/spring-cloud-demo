package com.iwhalecloud.retail.warehouse.busiservice.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.busiservice.ResouceInstTrackService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.ResouceInstTrackDTO;
import com.iwhalecloud.retail.warehouse.dto.ResouceInstTrackDetailDTO;
import com.iwhalecloud.retail.warehouse.dto.ResourceInstDTO;
import com.iwhalecloud.retail.warehouse.dto.ResourceReqDetailDTO;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstAddResp;
import com.iwhalecloud.retail.warehouse.manager.ResouceInstTrackDetailManager;
import com.iwhalecloud.retail.warehouse.manager.ResouceInstTrackManager;
import com.iwhalecloud.retail.warehouse.manager.ResourceInstManager;
import com.iwhalecloud.retail.warehouse.manager.ResourceReqDetailManager;
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
        List<ResourceInstDTO> insts = resourceInstManager.selectByIds(distinctList);
        log.info("ResouceInstTrackServiceImpl.asynUpdateTrackForAddmin resourceInstManager.selectByIds req={}, resp={}", JSON.toJSONString(distinctList), JSON.toJSONString(insts));
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
        if (CollectionUtils.isEmpty(distinctList)) {
            return;
        }
        resourceInstsGetReq.setMktResInstNbrs(distinctList);
        resourceInstsGetReq.setStatusCd("");
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
        req.setMktResInstNbrs(distinctList);
        req.setStatusCd("");
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
        resourceInstsGetReq.setStatusCd("");
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
        resourceInstsGetReq.setStatusCd("");
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
        resourceInstsGetReq.setStatusCd("");
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
        List<ResourceInstDTO> insts = resourceInstManager.selectByIds(distinctList);
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
        resourceInstsGetReq.setStatusCd("");
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
        resourceInstsGetReq.setStatusCd("");
        List<ResourceInstDTO> insts = resourceInstManager.getResourceInsts(resourceInstsGetReq);
        log.info("ResouceInstTrackServiceImpl.asynAcceptTrackForSupplier resourceInstManager.getResourceInsts req={}, resp={}", JSON.toJSONString(distinctList), JSON.toJSONString(insts));
        int count = 0;
        for (int i = 0; i < insts.size(); i++) {
            ResourceInstDTO resourceInstDTO = insts.get(i);
            ResouceInstTrackDTO resouceInstTrackDTO = new ResouceInstTrackDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDTO);
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
        resourceInstsGetReq.setStatusCd("");
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
        resourceInstsGetReq.setStatusCd("");
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
        List<String> mktResInstNbrs = req.getMktResInstNbrs();
        List<String> distinctList = mktResInstNbrs.stream().distinct().collect(Collectors.toList());
        ResourceInstsGetReq resourceInstsGetReq = new ResourceInstsGetReq();
        BeanUtils.copyProperties(req, resourceInstsGetReq);
        req.setMktResInstNbrs(distinctList);
        if (CollectionUtils.isEmpty(distinctList)) {
            return;
        }
        resourceInstsGetReq.setStatusCd("");
        List<ResourceInstDTO> insts = resourceInstManager.getResourceInsts(resourceInstsGetReq);
        log.info("ResouceInstTrackServiceImpl.asynGreenChannelForRetail resourceInstManager.getResourceInsts req={}, resp={}", JSON.toJSONString(distinctList), JSON.toJSONString(insts));
        int count = 0;
        for (int i = 0; i < insts.size(); i++) {
            ResourceInstDTO resourceInstDTO = insts.get(i);
            ResouceInstTrackDTO resouceInstTrackDTO = new ResouceInstTrackDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDTO);
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
        resourceInstsGetReq.setStatusCd("");
        if (CollectionUtils.isEmpty(distinctList)) {
            return;
        }
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
        resourceInstsGetReq.setStatusCd("");
        if (CollectionUtils.isEmpty(distinctList)) {
            return;
        }
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
        List<ResourceInstDTO> insts = resourceInstManager.selectByIds(distinctList);
        log.info("ResouceInstTrackServiceImpl.allocateResourceInstForRetail resourceInstManager.selectByIds req={}, resp={}", JSON.toJSONString(distinctList), JSON.toJSONString(insts));
        int count = 0;
        for (int i = 0; i < insts.size(); i++) {
            ResourceInstDTO resourceInstDTO = insts.get(i);
            ResouceInstTrackDTO resouceInstTrackDTO = new ResouceInstTrackDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDTO);
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

}
