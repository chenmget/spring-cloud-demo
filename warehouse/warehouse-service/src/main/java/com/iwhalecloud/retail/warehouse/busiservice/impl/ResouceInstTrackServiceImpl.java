package com.iwhalecloud.retail.warehouse.busiservice.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.system.dto.response.OrganizationRegionResp;
import com.iwhalecloud.retail.system.service.OrganizationService;
import com.iwhalecloud.retail.warehouse.busiservice.ResouceInstTrackService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.constant.Constant;
import com.iwhalecloud.retail.warehouse.dto.ResouceInstTrackDTO;
import com.iwhalecloud.retail.warehouse.dto.ResouceInstTrackDetailDTO;
import com.iwhalecloud.retail.warehouse.dto.ResourceInstDTO;
import com.iwhalecloud.retail.warehouse.dto.ResourceReqDetailDTO;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.manager.ResouceInstTrackDetailManager;
import com.iwhalecloud.retail.warehouse.manager.ResouceInstTrackManager;
import com.iwhalecloud.retail.warehouse.manager.ResourceInstManager;
import com.iwhalecloud.retail.warehouse.manager.ResourceReqDetailManager;
import com.iwhalecloud.retail.warehouse.service.ResouceStoreService;
import com.iwhalecloud.retail.warehouse.service.RetailerResourceInstService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
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

    @Reference
    private OrganizationService organizationService;


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
        selectReq.setMktResStoreId(req.getDestStoreId());
        List<ResourceInstDTO> insts = resourceInstManager.selectByIds(selectReq);
        log.info("ResouceInstTrackServiceImpl.asynUpdateTrackForAddmin resourceInstManager.selectByIds req={}, resp={}", JSON.toJSONString(selectReq), JSON.toJSONString(insts));
        Integer countTrack = 0;
        int countTrackDetail = 0;
        for (int i = 0; i < insts.size(); i++) {
            ResourceInstDTO resourceInstDTO = insts.get(i);
            if (unavailbaleNbrs.contains(resourceInstDTO.getMktResInstNbr())) {
                log.info("delete nbr not sucess nbr={}", resourceInstDTO.getMktResInstNbr());
                continue;
            }
            ResouceInstTrackDTO resouceInstTrackDTO = new ResouceInstTrackDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDTO);
            resouceInstTrackDTO.setStatusCd(ResourceConst.STATUSCD.DELETED.getCode());
            countTrack += resouceInstTrackManager.saveResouceInstTrack(resouceInstTrackDTO);
            log.info("ResouceInstTrackServiceImpl.asynUpdateTrackForAddmin resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), countTrack);
            ResouceInstTrackDetailDTO resouceInstTrackDetailDTO = new ResouceInstTrackDetailDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDetailDTO);
            resouceInstTrackDetailDTO.setStorageType(ResourceConst.STORAGETYPE.ADMINISTRATOR_MODIFICATION.getCode());
            countTrackDetail += resouceInstTrackDetailManager.saveResouceInstTrackDetail(resouceInstTrackDetailDTO);
            log.info("ResouceInstTrackServiceImpl.asynUpdateTrackForAddmin resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), countTrackDetail);
        }
    }

    @Async
    @Override
    public void asynSaveTrackForMerchant(ResourceInstAddReq req, ResultVO resp, CopyOnWriteArrayList<String> newlist) {
        log.info("ResouceInstTrackServiceImpl.asynSaveTrackForMerchant req={}", JSON.toJSONString(req));
        if (!resp.isSuccess()) {
            return;
        }
        Map<String, String> ctCodeMap = req.getCtCode();
        int count = 0;
        for (int i = 0; i < newlist.size(); i++) {
            String mktResInstNbr = newlist.get(i);
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
            resouceInstTrackDetailDTO.setSourceLanId(req.getLanId());
            resouceInstTrackDetailDTO.setSourceRegionId(req.getRegionId());
            resouceInstTrackDetailDTO.setInTime(new Date());
            count += resouceInstTrackDetailManager.saveResouceInstTrackDetail(resouceInstTrackDetailDTO);
            log.info("ResouceInstTrackServiceImpl.asynSaveTrackForMerchant resouceInstTrackManager.saveResouceInstTrackDetail req={}, resp={}", JSON.toJSONString(resouceInstTrackDetailDTO), count);
        }
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
        resourceInstsGetReq.setMktResStoreId(req.getDestStoreId());
        List<ResourceInstDTO> insts = resourceInstManager.getResourceInsts(resourceInstsGetReq);
        log.info("ResouceInstTrackServiceImpl.asynUpdateTrackForMerchant resourceInstManager.getResourceInsts req={}, resp={}", JSON.toJSONString(distinctList), JSON.toJSONString(insts));
        int countTrack = 0;
        int countTrackDetail = 0;
        for (int i = 0; i < insts.size(); i++) {
            ResourceInstDTO resourceInstDTO = insts.get(i);
            ResouceInstTrackDTO resouceInstTrackDTO = new ResouceInstTrackDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDTO);
            resouceInstTrackDTO.setMktResStoreId(req.getDestStoreId());
            countTrack += resouceInstTrackManager.saveResouceInstTrack(resouceInstTrackDTO);
            log.info("ResouceInstTrackServiceImpl.asynUpdateTrackForMerchant resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), countTrack);
            ResouceInstTrackDetailDTO resouceInstTrackDetailDTO = new ResouceInstTrackDetailDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDetailDTO);
            resouceInstTrackDetailDTO.setStorageType(ResourceConst.STORAGETYPE.VENDOR_MODIFICATION.getCode());
            resouceInstTrackDetailDTO.setSourceStoreId(req.getDestStoreId());
            resouceInstTrackDetailDTO.setSourceMerchantId(req.getMerchantId());
            resouceInstTrackDetailDTO.setOutTime(new Date());
            countTrackDetail += resouceInstTrackDetailManager.saveResouceInstTrackDetail(resouceInstTrackDetailDTO);
            log.info("ResouceInstTrackServiceImpl.asynUpdateTrackForMerchant resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), countTrackDetail);
        }
    }

    @Async
    @Override
    public void asynSaveTrackForSupplier(ResourceInstAddReq req, ResultVO resp) {
        log.info("ResouceInstTrackServiceImpl.asynSaveTrackForSupplier req={}", JSON.toJSONString(req));
        if (!resp.isSuccess()) {
            return;
        }

        ResultVO<MerchantDTO> resultVO = merchantService.getMerchantById(req.getMerchantId());
        log.info("ResouceInstTrackServiceImpl.asynSaveTrackForSupplier merchantService.getMerchantById storeId={}, resp={}", req.getMerchantId(), JSON.toJSONString(resultVO));
        MerchantDTO merchantDTO = null;
        if (resultVO.isSuccess()) {
            merchantDTO = resultVO.getResultData();
        }
        int countTrack = 0;
        int countTrackDetail = 0;
        for (String mktResInstNbr : req.getMktResInstNbrs()) {
            ResourceInstsTrackGetReq getReq = new ResourceInstsTrackGetReq();
            getReq.setTypeId(req.getTypeId());
            getReq.setMktResInstNbrList(Lists.newArrayList(mktResInstNbr));
            List<ResouceInstTrackDTO> trackInst = resouceInstTrackManager.listResourceInstsTrack(getReq);
            log.info("ResouceInstTrackServiceImpl.asynSaveTrackForSupplier resouceInstTrackManager.listResourceInstsTrack storeId={}, resp={}", JSON.toJSONString(getReq), JSON.toJSONString(trackInst));
            if (CollectionUtils.isNotEmpty(trackInst)) {
                continue;
            }
            ResouceInstTrackDTO resouceInstTrackDTO = trackInst.get(0);
            ResouceInstTrackDetailDTO resouceInstTrackDetailDTO = new ResouceInstTrackDetailDTO();
            resouceInstTrackDetailDTO.setStorageType(ResourceConst.STORAGETYPE.SUPPLIER_INPUT.getCode());
            resouceInstTrackDetailDTO.setTargetLanId(merchantDTO.getLanId());
            resouceInstTrackDetailDTO.setTargetMerchantId(merchantDTO.getMerchantId());
            resouceInstTrackDetailDTO.setTargetRegionId(merchantDTO.getCity());
            resouceInstTrackDetailDTO.setTargetStoreId(req.getDestStoreId());
            resouceInstTrackDetailDTO.setInTime(new Date());
            resouceInstTrackDetailDTO.setSourceRegionId(resouceInstTrackDTO.getRegionId());
            resouceInstTrackDetailDTO.setSourceLanId(resouceInstTrackDTO.getLanId());
            resouceInstTrackDetailDTO.setSourceMerchantId(resouceInstTrackDTO.getMerchantId());
            resouceInstTrackDetailDTO.setTargetStoreId(resouceInstTrackDTO.getMktResStoreId());
            countTrackDetail += resouceInstTrackDetailManager.saveResouceInstTrackDetail(resouceInstTrackDetailDTO);
            log.info("ResouceInstTrackServiceImpl.asynSaveTrackForSupplier resouceInstTrackDetailManager.saveResouceInstTrackDetail req={}, resp={}", JSON.toJSONString(resouceInstTrackDetailDTO), countTrackDetail);

            resouceInstTrackDTO.setMktResStoreId(req.getDestStoreId());
            resouceInstTrackDTO.setMerchantId(req.getMerchantId());
            resouceInstTrackDTO.setLanId(merchantDTO.getLanId());
            resouceInstTrackDTO.setRegionId(merchantDTO.getCity());
            resouceInstTrackDTO.setSourceType(merchantDTO.getMerchantType());
            resouceInstTrackDTO.setMktResInstType(ResourceConst.MKTResInstType.TRANSACTION.getCode());
            countTrack += resouceInstTrackManager.saveResouceInstTrack(resouceInstTrackDTO);
            log.info("ResouceInstTrackServiceImpl.asynSaveTrackForSupplier resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), countTrack);
        }
    }

    @Async
    @Override
    public void asynDeleteTrackForSupplier(AdminResourceInstDelReq req, ResultVO resp) {
        log.info("ResouceInstTrackServiceImpl.asynDeleteTrackForSupplier req={}", JSON.toJSONString(req));
        if (!resp.isSuccess()) {
            return;
        }
        ResourceInstsGetByIdListAndStoreIdReq resourceInstsGetReq = new ResourceInstsGetByIdListAndStoreIdReq();
        BeanUtils.copyProperties(req, resourceInstsGetReq);
        resourceInstsGetReq.setMktResInstIdList(req.getMktResInstIds());
        resourceInstsGetReq.setMktResStoreId(req.getDestStoreId());
        List<ResourceInstDTO> insts = resourceInstManager.selectByIds(resourceInstsGetReq);
        log.info("ResouceInstTrackServiceImpl.asynDeleteTrackForSupplier resourceInstManager.getResourceInsts req={}, resp={}", JSON.toJSONString(resourceInstsGetReq), JSON.toJSONString(insts));
        int count = 0;
        for (int i = 0; i < insts.size(); i++) {
            ResourceInstDTO resourceInstDTO = insts.get(i);
            ResouceInstTrackDTO resouceInstTrackDTO = new ResouceInstTrackDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDTO);
            resourceInstsGetReq.setMktResStoreId(req.getDestStoreId());
            resouceInstTrackDTO.setStatusCd(ResourceConst.STATUSCD.DELETED.getCode());
            count += resouceInstTrackManager.saveResouceInstTrack(resouceInstTrackDTO);
            log.info("ResouceInstTrackServiceImpl.asynDeleteTrackForSupplier resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
            ResouceInstTrackDetailDTO resouceInstTrackDetailDTO = new ResouceInstTrackDetailDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDetailDTO);
            resouceInstTrackDetailDTO.setStorageType(ResourceConst.STORAGETYPE.SUPPLIER_DELETION.getCode());
            count += resouceInstTrackDetailManager.saveResouceInstTrackDetail(resouceInstTrackDetailDTO);
            log.info("ResouceInstTrackServiceImpl.asynDeleteTrackForSupplier resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
        }
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
        resourceInstsGetReq.setMktResStoreId(req.getMktResStoreId());
        List<ResourceInstDTO> insts = resourceInstManager.getResourceInsts(resourceInstsGetReq);
        log.info("ResouceInstTrackServiceImpl.asynUpdateTrackForSupplier resourceInstManager.getResourceInsts req={}, resp={}", JSON.toJSONString(distinctList), JSON.toJSONString(insts));
        int count = 0;
        for (int i = 0; i < insts.size(); i++) {
            ResourceInstDTO resourceInstDTO = insts.get(i);
            ResouceInstTrackDTO resouceInstTrackDTO = new ResouceInstTrackDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDTO);
            resouceInstTrackDTO.setStatusCd(ResourceConst.STATUSCD.RESTORAGED.getCode());
            count += resouceInstTrackManager.saveResouceInstTrack(resouceInstTrackDTO);
            log.info("ResouceInstTrackServiceImpl.asynUpdateTrackForSupplier resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
            ResouceInstTrackDetailDTO resouceInstTrackDetailDTO = new ResouceInstTrackDetailDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDetailDTO);
            resouceInstTrackDetailDTO.setStorageType(storageType);
            count += resouceInstTrackDetailManager.saveResouceInstTrackDetail(resouceInstTrackDetailDTO);
            log.info("ResouceInstTrackServiceImpl.asynUpdateTrackForSupplier resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
        }
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
        log.info("ResouceInstTrackServiceImpl.asynAllocateTrackForSupplier resourceInstManager.selectByIds req={}, resp={}", JSON.toJSONString(selectReq), JSON.toJSONString(insts));
        int count = 0;
        for (int i = 0; i < insts.size(); i++) {
            ResourceInstDTO resourceInstDTO = insts.get(i);
            ResouceInstTrackDTO resouceInstTrackDTO = new ResouceInstTrackDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDTO);
            resouceInstTrackDTO.setStatusCd(ResourceConst.STATUSCD.ALLOCATIONING.getCode());
            count += resouceInstTrackManager.saveResouceInstTrack(resouceInstTrackDTO);
            log.info("ResouceInstTrackServiceImpl.asynAllocateTrackForSupplier resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
            ResouceInstTrackDetailDTO resouceInstTrackDetailDTO = new ResouceInstTrackDetailDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDetailDTO);
            resouceInstTrackDetailDTO.setStorageType(ResourceConst.STORAGETYPE.WAREHOUSING.getCode());
            count += resouceInstTrackDetailManager.saveResouceInstTrackDetail(resouceInstTrackDetailDTO);
            log.info("ResouceInstTrackServiceImpl.asynAllocateTrackForSupplier resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
        }
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
        ResultVO<MerchantDTO> merchantResultVO = resouceStoreService.getMerchantByStore(storeId);
        List<DeliveryResourceInstItem> deliveryResourceInstItemList = req.getDeliveryResourceInstItemList();
        for (DeliveryResourceInstItem deliveryResourceInstItem : deliveryResourceInstItemList) {
            List<String> mktResInstNbrList = deliveryResourceInstItem.getMktResInstNbrs();
            ResourceInstsGetReq resourceInstsGetReq = new ResourceInstsGetReq();
            resourceInstsGetReq.setMktResInstNbrs(mktResInstNbrList);
            resourceInstsGetReq.setMktResStoreId(storeId);
            resourceInstsGetReq.setMktResId(deliveryResourceInstItem.getProductId());
            List<ResourceInstDTO> insts = resourceInstManager.getResourceInsts(resourceInstsGetReq);
            log.info("ResouceInstTrackServiceImpl.asynShipTrackForSupplier resourceInstManager.getResourceInsts req={}, resp={}", JSON.toJSONString(resourceInstsGetReq), JSON.toJSONString(insts));
            int countTrack = 0;
            int countTrackDetail = 0;
            for (int i = 0; i < insts.size(); i++) {
                ResourceInstDTO resourceInstDTO = insts.get(i);
                ResouceInstTrackDTO resouceInstTrackDTO = new ResouceInstTrackDTO();
                BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDTO);
                resouceInstTrackDTO.setStatusCd(ResourceConst.STATUSCD.SALED.getCode());
                countTrack += resouceInstTrackManager.saveResouceInstTrack(resouceInstTrackDTO);
                log.info("ResouceInstTrackServiceImpl.asynShipTrackForSupplier resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), countTrack);
                ResouceInstTrackDetailDTO resouceInstTrackDetailDTO = new ResouceInstTrackDetailDTO();
                BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDetailDTO);
                resouceInstTrackDetailDTO.setOrderId(req.getOrderId());
                // 发货出库
                resouceInstTrackDetailDTO.setOutTime(new Date());
                resouceInstTrackDetailDTO.setOrderId(req.getOrderId());
                resouceInstTrackDetailDTO.setStorageType(ResourceConst.STORAGETYPE.SUPPLIER_DELIVERY.getCode());
                resouceInstTrackDetailDTO.setSourceLanId(resourceInstDTO.getLanId());
                resouceInstTrackDetailDTO.setSourceRegionId(resourceInstDTO.getLanId());
                resouceInstTrackDetailDTO.setSourceMerchantId(resourceInstDTO.getMerchantId());
                resouceInstTrackDetailDTO.setSourceStoreId(resourceInstDTO.getMktResStoreId());
                resouceInstTrackDetailDTO.setTargetStoreId(storeId);
                if (merchantResultVO.isSuccess() && null != merchantResultVO.getResultData()) {
                    MerchantDTO merchantDTO = merchantResultVO.getResultData();
                    resouceInstTrackDetailDTO.setTargetMerchantId(merchantDTO.getMerchantId());
                    resouceInstTrackDetailDTO.setTargetLanId(merchantDTO.getLanId());
                    resouceInstTrackDetailDTO.setTargetRegionId(merchantDTO.getCity());
                }
                countTrackDetail += resouceInstTrackDetailManager.saveResouceInstTrackDetail(resouceInstTrackDetailDTO);
                log.info("ResouceInstTrackServiceImpl.asynShipTrackForSupplier resouceInstTrackDetailManager.saveResouceInstTrackDetail req={}, resp={}", JSON.toJSONString(resouceInstTrackDetailDTO), countTrackDetail);
            }
        }
    }

    @Async
    @Override
    public void asynAcceptTrackForSupplier(DeliveryResourceInstReq req, ResultVO resp) {
        log.info("ResouceInstTrackServiceImpl.asynAcceptTrackForSupplier req={}", JSON.toJSONString(req));
        if (!resp.isSuccess()) {
            return;
        }
        String buyerMerchantId = req.getBuyerMerchantId();
        StoreGetStoreIdReq storePageReq = new StoreGetStoreIdReq();
        storePageReq.setMerchantId(buyerMerchantId);
        storePageReq.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
        String buyerStoreId = resouceStoreService.getStoreId(storePageReq);
        log.info("ResouceInstTrackServiceImpl.asynAcceptTrackForSupplier resouceStoreService.getStoreId req={},buyerStoreId={}", JSON.toJSONString(storePageReq), buyerStoreId);

        storePageReq.setMerchantId(req.getSellerMerchantId());
        String sellerStoreId = resouceStoreService.getStoreId(storePageReq);
        log.info("ResouceInstTrackServiceImpl.asynAcceptTrackForSupplier resouceStoreService.getStoreId req={},sellerStoreId={}", JSON.toJSONString(storePageReq), sellerStoreId);
        ResultVO<MerchantDTO> sellerMerchantResultVO = merchantService.getMerchantById(req.getSellerMerchantId());
        log.info("ResouceInstTrackServiceImpl.asynAcceptTrackForSupplier merchantService.getMerchantById req={},sellerStoreId={}", req.getSellerMerchantId(), JSON.toJSONString(sellerMerchantResultVO));
        ResultVO<MerchantDTO> buyerMerchantResultVO = merchantService.getMerchantById(buyerMerchantId);
        log.info("ResouceInstTrackServiceImpl.asynAcceptTrackForSupplier merchantService.getMerchantById req={},buyerMerchantId={}", buyerMerchantId, JSON.toJSONString(buyerMerchantResultVO));

        // 是否省内直供：在零售商收货确认时更新，是省包商交易到零售商的情况下
        String ifDirectSuppLy = ResourceConst.CONSTANT_NO;
        // 是否地包供货：地包商交易给零售商时填是
        String ifGroundSupply = ResourceConst.CONSTANT_NO;
        List<DeliveryResourceInstItem> instItems = req.getDeliveryResourceInstItemList();
        for (DeliveryResourceInstItem item : instItems) {
            List<String> mktResInstNbrList = item.getMktResInstNbrs();
            ResourceInstsGetReq getReq = new ResourceInstsGetReq();
            getReq.setMktResStoreId(sellerStoreId);
            getReq.setMktResInstNbrs(mktResInstNbrList);
            getReq.setMktResId(item.getProductId());
            List<ResourceInstDTO> insts = resourceInstManager.getResourceInsts(getReq);
            log.info("ResouceInstTrackServiceImpl.asynAcceptTrackForSupplier resourceInstManager.getResourceInsts req={}, resp={}", JSON.toJSONString(getReq), JSON.toJSONString(insts));
            int countTrack = 0;
            int countTrackDetail = 0;
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
                resouceInstTrackDTO.setMktResStoreId(buyerStoreId);
                if (buyerMerchantResultVO.isSuccess() && null != buyerMerchantResultVO.getResultData()) {
                    MerchantDTO merchantDTO = buyerMerchantResultVO.getResultData();
                    resouceInstTrackDTO.setLanId(merchantDTO.getLanId());
                    resouceInstTrackDTO.setRegionId(merchantDTO.getCity());
                }
                if (sellerMerchantResultVO.isSuccess() && null != sellerMerchantResultVO.getResultData()) {
                    MerchantDTO merchantDTO = buyerMerchantResultVO.getResultData();
                    resouceInstTrackDTO.setSourceType(merchantDTO.getMerchantType());
                }
                resouceInstTrackDTO.setMktResInstType(ResourceConst.MKTResInstType.TRANSACTION.getCode());
                resouceInstTrackDTO.setStatusCd(ResourceConst.STATUSCD.AVAILABLE.getCode());
                countTrack += resouceInstTrackManager.saveResouceInstTrack(resouceInstTrackDTO);
                log.info("ResouceInstTrackServiceImpl.asynAcceptTrackForSupplier resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), countTrack);
                ResouceInstTrackDetailDTO resouceInstTrackDetailDTO = new ResouceInstTrackDetailDTO();
                BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDetailDTO);
                // 收货入库
                resouceInstTrackDetailDTO.setInTime(new Date());
                resouceInstTrackDetailDTO.setOrderId(req.getOrderId());
                resouceInstTrackDetailDTO.setStorageType(ResourceConst.STORAGETYPE.TRANSACTION_WAREHOUSING.getCode());
                resouceInstTrackDetailDTO.setSourceLanId(resourceInstDTO.getLanId());
                resouceInstTrackDetailDTO.setSourceRegionId(resourceInstDTO.getLanId());
                resouceInstTrackDetailDTO.setSourceMerchantId(resourceInstDTO.getMerchantId());
                resouceInstTrackDetailDTO.setSourceStoreId(resourceInstDTO.getMktResStoreId());
                resouceInstTrackDetailDTO.setTargetStoreId(buyerStoreId);
                if (buyerMerchantResultVO.isSuccess() && null != buyerMerchantResultVO.getResultData()) {
                    MerchantDTO merchantDTO = buyerMerchantResultVO.getResultData();
                    resouceInstTrackDetailDTO.setTargetMerchantId(merchantDTO.getMerchantId());
                    resouceInstTrackDetailDTO.setTargetLanId(merchantDTO.getLanId());
                    resouceInstTrackDetailDTO.setTargetRegionId(merchantDTO.getCity());
                }
                countTrackDetail += resouceInstTrackDetailManager.saveResouceInstTrackDetail(resouceInstTrackDetailDTO);
                log.info("ResouceInstTrackServiceImpl.asynAcceptTrackForSupplier resouceInstTrackManager.saveResouceInstTrackDetail req={}, resp={}", JSON.toJSONString(resouceInstTrackDetailDTO), countTrackDetail);
            }
        }
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
        String buyerStoreId = resouceStoreService.getStoreId(storePageReq);
        log.info("ResouceInstTrackServiceImpl.asynBackShipTrackForSupplier resouceStoreService.getStoreId req={},storeId={}", JSON.toJSONString(storePageReq), buyerStoreId);

        storePageReq.setMerchantId(req.getSellerMerchantId());
        String sellerStoreId = resouceStoreService.getStoreId(storePageReq);
        log.info("ResouceInstTrackServiceImpl.asynAcceptTrackForSupplier resouceStoreService.getStoreId req={},sellerStoreId={}", JSON.toJSONString(storePageReq), sellerStoreId);

        ResultVO<MerchantDTO> merchantResultVO = merchantService.getMerchantById(req.getSellerMerchantId());
        log.info("ResouceInstTrackServiceImpl.asynAcceptTrackForSupplier merchantService.getMerchantById req={},sellerStoreId={}", req.getSellerMerchantId(), JSON.toJSONString(merchantResultVO));

        List<DeliveryResourceInstItem> deliveryResourceInstItemList = req.getDeliveryResourceInstItemList();
        for (DeliveryResourceInstItem deliveryResourceInstItem : deliveryResourceInstItemList) {
            ResourceInstsGetReq resourceInstsGetReq = new ResourceInstsGetReq();
            resourceInstsGetReq.setMktResInstNbrs(deliveryResourceInstItem.getMktResInstNbrs());
            resourceInstsGetReq.setMktResStoreId(buyerStoreId);
            resourceInstsGetReq.setMktResId(deliveryResourceInstItem.getProductId());
            List<ResourceInstDTO> insts = resourceInstManager.getResourceInsts(resourceInstsGetReq);
            int countTrack = 0;
            int countTrackDetail = 0;
            for (int i = 0; i < insts.size(); i++) {
                ResourceInstDTO resourceInstDTO = insts.get(i);
                ResouceInstTrackDTO resouceInstTrackDTO = new ResouceInstTrackDTO();
                BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDTO);
                resouceInstTrackDTO.setStatusCd(ResourceConst.STATUSCD.DELETED.getCode());
                countTrack += resouceInstTrackManager.saveResouceInstTrack(resouceInstTrackDTO);
                log.info("ResouceInstTrackServiceImpl.asynBackShipTrackForSupplier resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), countTrack);
                ResouceInstTrackDetailDTO resouceInstTrackDetailDTO = new ResouceInstTrackDetailDTO();
                BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDetailDTO);
                resouceInstTrackDetailDTO.setOrderId(req.getOrderId());
                resouceInstTrackDetailDTO.setStorageType(ResourceConst.STORAGETYPE.SUPPLIER_RETURN_DELIVERY.getCode());
                resouceInstTrackDetailDTO.setOutTime(new Date());
                resouceInstTrackDetailDTO.setOrderId(req.getOrderId());
                resouceInstTrackDetailDTO.setSourceLanId(resourceInstDTO.getLanId());
                resouceInstTrackDetailDTO.setSourceRegionId(resourceInstDTO.getLanId());
                resouceInstTrackDetailDTO.setSourceMerchantId(resourceInstDTO.getMerchantId());
                resouceInstTrackDetailDTO.setSourceStoreId(buyerStoreId);
                resouceInstTrackDetailDTO.setTargetStoreId(sellerStoreId);
                if (merchantResultVO.isSuccess() && null != merchantResultVO.getResultData()) {
                    MerchantDTO merchantDTO = merchantResultVO.getResultData();
                    resouceInstTrackDetailDTO.setTargetMerchantId(merchantDTO.getMerchantId());
                    resouceInstTrackDetailDTO.setTargetLanId(merchantDTO.getLanId());
                    resouceInstTrackDetailDTO.setTargetRegionId(merchantDTO.getCity());
                }
                countTrackDetail += resouceInstTrackDetailManager.saveResouceInstTrackDetail(resouceInstTrackDetailDTO);
                log.info("ResouceInstTrackServiceImpl.asynBackShipTrackForSupplier resouceInstTrackDetailManager.saveResouceInstTrackDetail req={}, resp={}", JSON.toJSONString(resouceInstTrackDetailDTO), countTrackDetail);
            }
        }
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
        String buyerStoreId = resouceStoreService.getStoreId(storePageReq);
        log.info("ResouceInstTrackServiceImpl.asynBackAcceptTrackForSupplier resouceStoreService.getStoreId req={},storeId={}", JSON.toJSONString(storePageReq), buyerStoreId);

        storePageReq.setMerchantId(req.getSellerMerchantId());
        String sellerStoreId = resouceStoreService.getStoreId(storePageReq);
        log.info("ResouceInstTrackServiceImpl.asynAcceptTrackForSupplier resouceStoreService.getStoreId req={},sellerStoreId={}", JSON.toJSONString(storePageReq), sellerStoreId);

        ResultVO<MerchantDTO> sellerMerchantResultVO = merchantService.getMerchantById(req.getSellerMerchantId());
        log.info("ResouceInstTrackServiceImpl.asynAcceptTrackForSupplier merchantService.getMerchantById req={},merchantId={}", req.getSellerMerchantId(), JSON.toJSONString(sellerMerchantResultVO));
        ResultVO<MerchantDTO> buyerMerchantResultVO = merchantService.getMerchantById(req.getBuyerMerchantId());
        log.info("ResouceInstTrackServiceImpl.asynAcceptTrackForSupplier merchantService.getMerchantById req={},merchantId={}", req.getBuyerMerchantId(), JSON.toJSONString(buyerMerchantResultVO));

        List<DeliveryResourceInstItem> deliveryResourceInstItemList = req.getDeliveryResourceInstItemList();
        for (DeliveryResourceInstItem deliveryResourceInstItem : deliveryResourceInstItemList) {
            ResourceInstsGetReq resourceInstsGetReq = new ResourceInstsGetReq();
            resourceInstsGetReq.setMktResInstNbrs(deliveryResourceInstItem.getMktResInstNbrs());
            resourceInstsGetReq.setMktResStoreId(buyerStoreId);
            resourceInstsGetReq.setMktResId(deliveryResourceInstItem.getProductId());
            List<ResourceInstDTO> insts = resourceInstManager.getResourceInsts(resourceInstsGetReq);
            log.info("ResouceInstTrackServiceImpl.asynBackAcceptTrackForSupplier resourceInstManager.getResourceInsts req={}, resp={}", JSON.toJSONString(resourceInstsGetReq), JSON.toJSONString(insts));
            int countTrack = 0;
            int countTrackDetail = 0;
            for (int i = 0; i < insts.size(); i++) {
                ResourceInstDTO resourceInstDTO = insts.get(i);
                ResouceInstTrackDTO resouceInstTrackDTO = new ResouceInstTrackDTO();
                BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDTO);
                resouceInstTrackDTO.setStatusCd(ResourceConst.STATUSCD.AVAILABLE.getCode());
                resouceInstTrackDTO.setMerchantId(req.getSellerMerchantId());
                resouceInstTrackDTO.setMktResStoreId(sellerStoreId);
                if (sellerMerchantResultVO.isSuccess() && null != sellerMerchantResultVO.getResultData()) {
                    MerchantDTO merchantDTO = sellerMerchantResultVO.getResultData();
                    resouceInstTrackDTO.setLanId(merchantDTO.getLanId());
                    resouceInstTrackDTO.setRegionId(merchantDTO.getCity());
                }
                if (buyerMerchantResultVO.isSuccess() && null != buyerMerchantResultVO.getResultData()) {
                    MerchantDTO merchantDTO = buyerMerchantResultVO.getResultData();
                    resouceInstTrackDTO.setSourceType(merchantDTO.getMerchantType());
                }
                countTrack += resouceInstTrackManager.saveResouceInstTrack(resouceInstTrackDTO);
                log.info("ResouceInstTrackServiceImpl.asynBackAcceptTrackForSupplier resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), countTrack);
                ResouceInstTrackDetailDTO resouceInstTrackDetailDTO = new ResouceInstTrackDetailDTO();
                BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDetailDTO);
                resouceInstTrackDetailDTO.setOrderId(req.getOrderId());
                resouceInstTrackDetailDTO.setStorageType(ResourceConst.STORAGETYPE.SUPPLIER_RETURN_AND_RECEIVE.getCode());
                resouceInstTrackDetailDTO.setInTime(new Date());
                resouceInstTrackDetailDTO.setOrderId(req.getOrderId());
                resouceInstTrackDetailDTO.setSourceLanId(resourceInstDTO.getLanId());
                resouceInstTrackDetailDTO.setSourceRegionId(resourceInstDTO.getLanId());
                resouceInstTrackDetailDTO.setSourceMerchantId(resourceInstDTO.getMerchantId());
                resouceInstTrackDetailDTO.setSourceStoreId(sellerStoreId);
                resouceInstTrackDetailDTO.setTargetStoreId(buyerStoreId);
                if (sellerMerchantResultVO.isSuccess() && null != sellerMerchantResultVO.getResultData()) {
                    MerchantDTO merchantDTO = sellerMerchantResultVO.getResultData();
                    resouceInstTrackDetailDTO.setTargetMerchantId(merchantDTO.getMerchantId());
                    resouceInstTrackDetailDTO.setTargetLanId(merchantDTO.getLanId());
                    resouceInstTrackDetailDTO.setTargetRegionId(merchantDTO.getCity());
                }
                countTrackDetail += resouceInstTrackDetailManager.saveResouceInstTrackDetail(resouceInstTrackDetailDTO);
                log.info("ResouceInstTrackServiceImpl.asynBackAcceptTrackForSupplier resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), countTrackDetail);
            }
        }
    }

    @Async
    @Override
    public void asynGreenChannelForRetail(ResourceInstAddReq req, ResultVO resp) {
        log.info("ResouceInstTrackServiceImpl.asynGreenChannelForRetail req={}", JSON.toJSONString(req));
        if (!resp.isSuccess()) {
            return;
        }
        String merchantId = req.getMerchantId();
        ResultVO<MerchantDTO> merchantDTOResultVO = merchantService.getMerchantById(merchantId);
        log.info("ResouceInstTrackServiceImpl.asynGreenChannelForRetail merchantService.getMerchantById req={},resp={}", merchantId, JSON.toJSONString(merchantDTOResultVO));
        if (!merchantDTOResultVO.isSuccess() || null == merchantDTOResultVO.getResultData()) {
            return;
        }
        MerchantDTO merchantDTO = merchantDTOResultVO.getResultData();
        // 获取仓库
        StoreGetStoreIdReq storeGetStoreIdReq = new StoreGetStoreIdReq();
        storeGetStoreIdReq.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
        storeGetStoreIdReq.setMerchantId(merchantId);
        String mktResStoreId = resouceStoreService.getStoreId(storeGetStoreIdReq);
        log.info("ResouceInstTrackServiceImpl.asynGreenChannelForRetail resouceStoreService.getStoreId req={},resp={}", JSON.toJSONString(storeGetStoreIdReq), mktResStoreId);

        List<String> mktResInstNbrList = req.getMktResInstNbrs();
        int countTrack = 0;
        int countTrackDetail = 0;
        for (String mktResInstNbr : mktResInstNbrList) {
            ResouceInstTrackDTO resouceInstTrackDTO = new ResouceInstTrackDTO();
            resouceInstTrackDTO.setTypeId(req.getTypeId());
            resouceInstTrackDTO.setStatusCd(ResourceConst.STATUSCD.AVAILABLE.getCode());
            resouceInstTrackDTO.setMktResStoreId(mktResStoreId);
            resouceInstTrackDTO.setIfGreenChannel(ResourceConst.CONSTANT_YES);
            resouceInstTrackDTO.setMerchantId(req.getMerchantId());
            resouceInstTrackDTO.setLanId(merchantDTO.getLanId());
            resouceInstTrackDTO.setSourceType(merchantDTO.getMerchantType());
            resouceInstTrackDTO.setMktResId(req.getMktResId());
            resouceInstTrackDTO.setMktResInstNbr(mktResInstNbr);
            resouceInstTrackDTO.setRegionId(merchantDTO.getCity());
            resouceInstTrackDTO.setMktResInstType(ResourceConst.MKTResInstType.NONTRANSACTION.getCode());
            countTrack += resouceInstTrackManager.saveResouceInstTrack(resouceInstTrackDTO);
            log.info("ResouceInstTrackServiceImpl.asynGreenChannelForRetail resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), countTrack);
            ResouceInstTrackDetailDTO resouceInstTrackDetailDTO = new ResouceInstTrackDetailDTO();
            resouceInstTrackDetailDTO.setInTime(new Date());
            resouceInstTrackDetailDTO.setTargetLanId(merchantDTO.getLanId());
            resouceInstTrackDetailDTO.setTargetRegionId(merchantDTO.getCity());
            resouceInstTrackDetailDTO.setTargetMerchantId(merchantDTO.getMerchantId());
            resouceInstTrackDetailDTO.setTargetStoreId(mktResStoreId);
            resouceInstTrackDetailDTO.setStorageType(ResourceConst.STORAGETYPE.GREEN_CHANNEL.getCode());
            countTrackDetail += resouceInstTrackDetailManager.saveResouceInstTrackDetail(resouceInstTrackDetailDTO);
            log.info("ResouceInstTrackServiceImpl.asynGreenChannelForRetail resouceInstTrackManager.saveResouceInstTrackDetail req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), countTrackDetail);
        }
    }

    @Async
    @Override
    public void asynDeleteInstForRetail(ResourceInstUpdateReq req, ResultVO resp) {
        log.info("ResouceInstTrackServiceImpl.asynDeleteInstForRetail req={}", JSON.toJSONString(req));
        if (!resp.isSuccess()) {
            return;
        }
        List<String> mktResInstNbrs = req.getMktResInstNbrs();
        Map<String, String> nbrAndTypeId = req.getNbrAndTypeId();
        ResourceInstsGetReq resourceInstsGetReq = new ResourceInstsGetReq();
        resourceInstsGetReq.setMktResStoreId(req.getMktResStoreId());
        resourceInstsGetReq.setMktResInstNbrs(mktResInstNbrs);
        List<ResourceInstDTO> insts = resourceInstManager.getResourceInsts(resourceInstsGetReq);
        log.info("ResouceInstTrackServiceImpl.asynDeleteInstForRetail resourceInstManager.getResourceInsts req={}, resp={}", JSON.toJSONString(resourceInstsGetReq), JSON.toJSONString(insts));
        int count = 0;
        for (int i = 0; i < insts.size(); i++) {
            ResourceInstDTO resourceInstDTO = insts.get(i);
            // 相同串码但是不同产品类型，只更新传进来那个产品对应的串码
            if (StringUtils.isNotBlank(nbrAndTypeId.get(resourceInstDTO.getMktResInstNbr()))) {
                continue;
            }
            ResouceInstTrackDTO resouceInstTrackDTO = new ResouceInstTrackDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDTO);
            resouceInstTrackDTO.setStatusCd(ResourceConst.STATUSCD.DELETED.getCode());
            count += resouceInstTrackManager.saveResouceInstTrack(resouceInstTrackDTO);
            log.info("ResouceInstTrackServiceImpl.asynGreenChannelForRetail resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
            ResouceInstTrackDetailDTO resouceInstTrackDetailDTO = new ResouceInstTrackDetailDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDetailDTO);
            resouceInstTrackDetailDTO.setStorageType(ResourceConst.STORAGETYPE.RETAILER_DELETION.getCode());
            count += resouceInstTrackDetailManager.saveResouceInstTrackDetail(resouceInstTrackDetailDTO);
            log.info("ResouceInstTrackServiceImpl.asynGreenChannelForRetail resouceInstTrackManager.saveResouceInstTrackDetail req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
        }
    }

    @Async
    @Override
    public void pickResourceInstForRetail(ResourceInstPickupReq req, ResultVO resp) {
        log.info("ResouceInstTrackServiceImpl.pickResourceInstForRetail req={}", JSON.toJSONString(req));
        if (!resp.isSuccess()) {
            return;
        }
        // 先检查零售商所属十四个地市中的一个是否存在
        ResultVO<List<OrganizationRegionResp>> organizationIdVO = organizationService.queryRegionOrganizationId();
        log.info("ResouceInstTrackServiceImpl.pickResourceInstForRetail organizationService.getStoreId queryRegionOrganizationId={}", JSON.toJSONString(organizationIdVO));
        if (!organizationIdVO.isSuccess() || CollectionUtils.isEmpty(organizationIdVO.getResultData())) {
            return;
        }
        String lanId = req.getLanId();
        List<OrganizationRegionResp> sameLanIdOrganization = organizationIdVO.getResultData().stream().filter(t -> lanId.equals(t.getLanId())).collect(Collectors.toList());
        StoreGetStoreIdReq storeGetStoreIdReq = new StoreGetStoreIdReq();
        storeGetStoreIdReq.setMerchantId(sameLanIdOrganization.get(0).getOrgId());
        storeGetStoreIdReq.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
        String sourceStoreId = resouceStoreService.getStoreId(storeGetStoreIdReq);
        log.info("ResouceInstTrackServiceImpl.pickResourceInstForRetail resouceStoreService.getStoreId req={}, sourceStoreId={}", sameLanIdOrganization.get(0).getOrgId(),sourceStoreId);

        storeGetStoreIdReq.setMerchantId(req.getMerchantId());
        String targetStoreId = resouceStoreService.getStoreId(storeGetStoreIdReq);
        log.info("ResouceInstTrackServiceImpl.pickResourceInstForRetail resouceStoreService.getStoreId req={}, targetStoreId={}", req.getMerchantId(), targetStoreId);

        ResultVO<MerchantDTO> sourceMerchantResultVO = merchantService.getMerchantById(sameLanIdOrganization.get(0).getOrgId());
        log.info("ResouceInstTrackServiceImpl.pickResourceInstForRetail merchantService.getMerchantById req={},sellerStoreId={}", sameLanIdOrganization.get(0).getOrgId(), JSON.toJSONString(sourceMerchantResultVO));
        ResultVO<MerchantDTO> targetMerchantResultVO = merchantService.getMerchantById(req.getMerchantId());
        log.info("ResouceInstTrackServiceImpl.pickResourceInstForRetail merchantService.getMerchantById req={},buyerMerchantId={}", req.getMerchantId(), JSON.toJSONString(targetMerchantResultVO));

        ResourceInstsGetReq resourceInstsGetReq = new ResourceInstsGetReq();
        resourceInstsGetReq.setMktResInstNbrs(req.getMktResInstNbrs());
        resourceInstsGetReq.setMktResStoreId(sourceStoreId);
        List<ResourceInstDTO> insts = resourceInstManager.getResourceInsts(resourceInstsGetReq);
        log.info("ResouceInstTrackServiceImpl.pickResourceInsForRetail resourceInstManager.getResourceInsts req={}, resp={}", JSON.toJSONString(resourceInstsGetReq), JSON.toJSONString(insts));
        int count = 0;
        for (int i = 0; i < insts.size(); i++) {
            ResourceInstDTO resourceInstDTO = insts.get(i);
            ResouceInstTrackDTO resouceInstTrackDTO = new ResouceInstTrackDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDTO);
            resouceInstTrackDTO.setMktResStoreId(targetStoreId);
            if (targetMerchantResultVO.isSuccess() && null != targetMerchantResultVO.getResultData()) {
                MerchantDTO targetMerchant = targetMerchantResultVO.getResultData();
                resouceInstTrackDTO.setMerchantId(targetMerchant.getMerchantId());
                resouceInstTrackDTO.setLanId(targetMerchant.getLanId());
                resouceInstTrackDTO.setRegionId(targetMerchant.getCity());
            }
            if (sourceMerchantResultVO.isSuccess() && null != sourceMerchantResultVO.getResultData()) {
                MerchantDTO sourceMerchant = sourceMerchantResultVO.getResultData();
                resouceInstTrackDTO.setSourceType(sourceMerchant.getMerchantType());
            }
            count += resouceInstTrackManager.saveResouceInstTrack(resouceInstTrackDTO);
            log.info("ResouceInstTrackServiceImpl.asynGreenChannelForRetail resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
            ResouceInstTrackDetailDTO resouceInstTrackDetailDTO = new ResouceInstTrackDetailDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDetailDTO);
            resouceInstTrackDetailDTO.setStorageType(ResourceConst.STORAGETYPE.RETAILER_APPOINTMENT.getCode());
            resouceInstTrackDetailDTO.setSourceStoreId(sourceStoreId);
            resouceInstTrackDetailDTO.setTargetStoreId(targetStoreId);
            if (targetMerchantResultVO.isSuccess() && null != targetMerchantResultVO.getResultData()) {
                MerchantDTO merchantDTO = targetMerchantResultVO.getResultData();
                resouceInstTrackDetailDTO.setSourceLanId(merchantDTO.getLanId());
                resouceInstTrackDetailDTO.setSourceRegionId(merchantDTO.getLanId());
                resouceInstTrackDetailDTO.setSourceMerchantId(merchantDTO.getMerchantId());
            }
            if (targetMerchantResultVO.isSuccess() && null != targetMerchantResultVO.getResultData()) {
                MerchantDTO merchantDTO = targetMerchantResultVO.getResultData();
                resouceInstTrackDetailDTO.setTargetMerchantId(merchantDTO.getMerchantId());
                resouceInstTrackDetailDTO.setTargetLanId(merchantDTO.getLanId());
                resouceInstTrackDetailDTO.setTargetRegionId(merchantDTO.getCity());
            }
            count += resouceInstTrackDetailManager.saveResouceInstTrackDetail(resouceInstTrackDetailDTO);
            log.info("ResouceInstTrackServiceImpl.asynGreenChannelForRetail resouceInstTrackManager.saveResouceInstTrackDetail req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
        }
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
            resouceInstTrackDTO.setStatusCd(ResourceConst.STATUSCD.ALLOCATIONING.getCode());
            count += resouceInstTrackManager.saveResouceInstTrack(resouceInstTrackDTO);
            log.info("ResouceInstTrackServiceImpl.asynGreenChannelForRetail resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
            ResouceInstTrackDetailDTO resouceInstTrackDetailDTO = new ResouceInstTrackDetailDTO();
            BeanUtils.copyProperties(resourceInstDTO, resouceInstTrackDetailDTO);
            resouceInstTrackDetailDTO.setStorageType(ResourceConst.STORAGETYPE.RETAILER_ALLOCATION.getCode());
            count += resouceInstTrackDetailManager.saveResouceInstTrackDetail(resouceInstTrackDetailDTO);
            log.info("ResouceInstTrackServiceImpl.asynGreenChannelForRetail resouceInstTrackManager.saveResouceInstTrackDetail req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), count);
        }
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
        ResourceReqDetailDTO detailDTO = list.get(0);
        ResultVO<MerchantDTO> sourceMerchantResultVO = resouceStoreService.getMerchantByStore(detailDTO.getMktResStoreId());
        log.info("ResouceInstTrackServiceImpl.asynAcceptTrackForSupplier merchantService.getMerchantById req={},merchantId={}", detailDTO.getMktResStoreId(), JSON.toJSONString(sourceMerchantResultVO));
        ResultVO<MerchantDTO> targetMerchantResultVO = resouceStoreService.getMerchantByStore(detailDTO.getDestStoreId());
        log.info("ResouceInstTrackServiceImpl.asynAcceptTrackForSupplier merchantService.getMerchantById req={},merchantId={}", detailDTO.getDestStoreId(), JSON.toJSONString(targetMerchantResultVO));

        int countTrack = 0;
        int countTrackDetail = 0;
        for (ResourceReqDetailDTO resourceReqDetailDTO : list) {
            ResourceInstsTrackGetReq getReq = new ResourceInstsTrackGetReq();
            getReq.setMktResInstNbrList(Lists.newArrayList(resourceReqDetailDTO.getMktResInstNbr()));
            getReq.setMktResId(resourceReqDetailDTO.getMktResId());
            List<ResouceInstTrackDTO> resouceInstTrackDTOList = resouceInstTrackManager.listResourceInstsTrack(getReq);
            if (CollectionUtils.isNotEmpty(resouceInstTrackDTOList)) {
                ResouceInstTrackDTO resouceInstTrackDTO = resouceInstTrackDTOList.get(0);
                resouceInstTrackDTO.setMktResId(resourceReqDetailDTO.getMktResId());
                resouceInstTrackDTO.setMktResInstType(resourceReqDetailDTO.getMktResInstType());
                if (targetMerchantResultVO.isSuccess() && null != targetMerchantResultVO.getResultData()) {
                    MerchantDTO targetMerchant = targetMerchantResultVO.getResultData();
                    resouceInstTrackDTO.setMerchantId(targetMerchant.getMerchantId());
                    resouceInstTrackDTO.setLanId(targetMerchant.getLanId());
                    resouceInstTrackDTO.setRegionId(targetMerchant.getCity());
                }
                if (sourceMerchantResultVO.isSuccess() && null != sourceMerchantResultVO.getResultData()) {
                    MerchantDTO sourceMerchant = sourceMerchantResultVO.getResultData();
                    resouceInstTrackDTO.setSourceType(sourceMerchant.getMerchantType());
                }
                countTrack += resouceInstTrackManager.saveResouceInstTrack(resouceInstTrackDTO);
                log.info("ResouceInstTrackServiceImpl.asynGreenChannelForRetail resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), countTrack);
                ResouceInstTrackDetailDTO resouceInstTrackDetailDTO = new ResouceInstTrackDetailDTO();
                BeanUtils.copyProperties(resourceReqDetailDTO, resouceInstTrackDetailDTO);
                resouceInstTrackDetailDTO.setStorageType(ResourceConst.STORAGETYPE.ALLOCATION_AND_WAREHOUSING.getCode());
                resouceInstTrackDetailDTO.setInTime(new Date());
                resouceInstTrackDetailDTO.setSourceStoreId(detailDTO.getMktResStoreId());
                resouceInstTrackDetailDTO.setTargetStoreId(detailDTO.getDestStoreId());
                if (sourceMerchantResultVO.isSuccess() && null != sourceMerchantResultVO.getResultData()) {
                    MerchantDTO merchantDTO = sourceMerchantResultVO.getResultData();
                    resouceInstTrackDetailDTO.setSourceLanId(merchantDTO.getLanId());
                    resouceInstTrackDetailDTO.setSourceRegionId(merchantDTO.getLanId());
                    resouceInstTrackDetailDTO.setSourceMerchantId(merchantDTO.getMerchantId());
                }
                if (targetMerchantResultVO.isSuccess() && null != targetMerchantResultVO.getResultData()) {
                    MerchantDTO merchantDTO = targetMerchantResultVO.getResultData();
                    resouceInstTrackDetailDTO.setTargetMerchantId(merchantDTO.getMerchantId());
                    resouceInstTrackDetailDTO.setTargetLanId(merchantDTO.getLanId());
                    resouceInstTrackDetailDTO.setTargetRegionId(merchantDTO.getCity());
                }
                countTrackDetail += resouceInstTrackDetailManager.saveResouceInstTrackDetail(resouceInstTrackDetailDTO);
                log.info("ResouceInstTrackServiceImpl.asynGreenChannelForRetail resouceInstTrackManager.saveResouceInstTrackDetail req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), countTrackDetail);
            }
        }
    }

    @Async
    @Override
    public void allocateResourceIntsWarehousingCancelForRetail(ConfirmReciveNbrReq req, ResultVO resp) {
        // 找到串码明细
        log.info("ResouceInstTrackServiceImpl.allocateResourceIntsWarehousingForRetail req={}", JSON.toJSONString(req));
        ResourceReqDetailQueryReq queryReq = new ResourceReqDetailQueryReq();
        queryReq.setMktResReqId(req.getResReqId());
        List<ResourceReqDetailDTO> list = resourceReqDetailManager.listDetail(queryReq);
        log.info("ResouceInstTrackServiceImpl.allocateResourceIntsWarehousingForRetail resourceReqDetailManager.listDetail req={}", JSON.toJSONString(queryReq));
        ResourceReqDetailDTO detailDTO = list.get(0);
        ResultVO<MerchantDTO> sourceMerchantResultVO = resouceStoreService.getMerchantByStore(detailDTO.getMktResStoreId());
        log.info("ResouceInstTrackServiceImpl.asynAcceptTrackForSupplier merchantService.getMerchantById req={},merchantId={}", detailDTO.getMktResStoreId(), JSON.toJSONString(sourceMerchantResultVO));

        int countTrack = 0;
        int countTrackDetail = 0;
        for (ResourceReqDetailDTO resourceReqDetailDTO : list) {
            ResourceInstsTrackGetReq getReq = new ResourceInstsTrackGetReq();
            getReq.setMktResInstNbrList(Lists.newArrayList(resourceReqDetailDTO.getMktResInstNbr()));
            getReq.setMktResId(resourceReqDetailDTO.getMktResId());
            List<ResouceInstTrackDTO> resouceInstTrackDTOList = resouceInstTrackManager.listResourceInstsTrack(getReq);
            if (CollectionUtils.isNotEmpty(resouceInstTrackDTOList)) {
                ResouceInstTrackDTO resouceInstTrackDTO = resouceInstTrackDTOList.get(0);
                resouceInstTrackDTO.setStatusCd(ResourceConst.STATUSCD.AVAILABLE.getCode());
                countTrack += resouceInstTrackManager.saveResouceInstTrack(resouceInstTrackDTO);
                log.info("ResouceInstTrackServiceImpl.asynGreenChannelForRetail resouceInstTrackManager.saveResouceInstTrack req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), countTrack);
                ResouceInstTrackDetailDTO resouceInstTrackDetailDTO = new ResouceInstTrackDetailDTO();
                BeanUtils.copyProperties(resourceReqDetailDTO, resouceInstTrackDetailDTO);
                resouceInstTrackDetailDTO.setStorageType(ResourceConst.STORAGETYPE.ALLOCATION_AND_WAREHOUSING.getCode());
                resouceInstTrackDetailDTO.setInTime(new Date());
                resouceInstTrackDetailDTO.setSourceStoreId(detailDTO.getMktResStoreId());
                resouceInstTrackDetailDTO.setTargetStoreId(detailDTO.getDestStoreId());
                if (sourceMerchantResultVO.isSuccess() && null != sourceMerchantResultVO.getResultData()) {
                    MerchantDTO merchantDTO = sourceMerchantResultVO.getResultData();
                    resouceInstTrackDetailDTO.setSourceLanId(merchantDTO.getLanId());
                    resouceInstTrackDetailDTO.setSourceRegionId(merchantDTO.getLanId());
                    resouceInstTrackDetailDTO.setSourceMerchantId(merchantDTO.getMerchantId());
                    resouceInstTrackDetailDTO.setTargetMerchantId(merchantDTO.getMerchantId());
                    resouceInstTrackDetailDTO.setTargetLanId(merchantDTO.getLanId());
                    resouceInstTrackDetailDTO.setTargetRegionId(merchantDTO.getCity());
                }
                countTrackDetail += resouceInstTrackDetailManager.saveResouceInstTrackDetail(resouceInstTrackDetailDTO);
                log.info("ResouceInstTrackServiceImpl.asynGreenChannelForRetail resouceInstTrackManager.saveResouceInstTrackDetail req={}, resp={}", JSON.toJSONString(resouceInstTrackDTO), countTrackDetail);
            }
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


    @Override
    public ResultVO<List<ResouceInstTrackDTO>> listResourceInstsTrack(ResourceInstsTrackGetReq req, CopyOnWriteArrayList<String> mktResInstNbrList){
        req.setMktResInstNbrList(mktResInstNbrList);
        return ResultVO.success(resouceInstTrackManager.listResourceInstsTrack(req));
    }
}
