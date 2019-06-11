package com.iwhalecloud.retail.warehouse.busiservice.impl;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstLogService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.ResouceEventDTO;
import com.iwhalecloud.retail.warehouse.dto.ResourceBatchRecDTO;
import com.iwhalecloud.retail.warehouse.dto.ResourceChngEvtDetailDTO;
import com.iwhalecloud.retail.warehouse.dto.ResourceInstDTO;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListPageResp;
import com.iwhalecloud.retail.warehouse.entity.ResourceInst;
import com.iwhalecloud.retail.warehouse.manager.ResouceEventManager;
import com.iwhalecloud.retail.warehouse.manager.ResourceBatchRecManager;
import com.iwhalecloud.retail.warehouse.manager.ResourceChngEvtDetailManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("resourceInstLogService")
@Slf4j
public class ResourceInstLogServiceImpl implements ResourceInstLogService {

    @Autowired
    private ResouceEventManager resouceEventManager;

    @Autowired
    private ResourceChngEvtDetailManager detailManager;

    @Autowired
    private ResourceBatchRecManager batchRecManager;

    @Async
    @Override
    public void addResourceInstLog(ResourceInstAddReq req, List<ResourceInst> resourceInsts, String batchId) {
        log.info("ResourceInstLogServiceImpl.addResourceInstLog req={}", JSON.toJSONString(req));
        if (ResourceConst.EVENTTYPE.NO_RECORD.getCode().equals(req.getEventType())) {
            log.info("ResourceInstLogServiceImpl.addResourceInstLog no record");
            return;
        }
        // 增加批次事件并把ID返回
        String merchantId = req.getMerchantId();
        ResourceBatchRecDTO batchRecDTO = new ResourceBatchRecDTO();
        BeanUtils.copyProperties(req, batchRecDTO);
        if (null != req.getSalesPrice()) {
            batchRecDTO.setCostPrice(req.getSalesPrice());
        }
        batchRecDTO.setQuantity((long) resourceInsts.size());
        batchRecDTO.setMktResBatchId(batchId);
        batchRecManager.insertResourceBatchRec(batchRecDTO);
        log.info("ResourceInstLogServiceImpl.addResourceInstLog batchRecManager.insertResourceBatchRec req={},batchId={}", JSON.toJSONString(batchRecDTO), JSON.toJSONString(batchId));
        // 增加事件
        ResouceEventDTO eventDTO = new ResouceEventDTO();
        BeanUtils.copyProperties(req, eventDTO);
        eventDTO.setCreateStaff(merchantId);
        eventDTO.setStatusCd(req.getEventStatusCd());
        String eventId = resouceEventManager.insertResouceEvent(eventDTO);
        log.info("ResourceInstLogServiceImpl.addResourceInstLog resouceEventManager.insertResouceEvent req={},eventId={}", JSON.toJSONString(eventDTO), JSON.toJSONString(eventId));
        for (ResourceInst resourceInst : resourceInsts) {
            // 增加事件明细
            ResourceChngEvtDetailDTO detailDTO = new ResourceChngEvtDetailDTO();
            BeanUtils.copyProperties(req, detailDTO);
            detailDTO.setMktResEventId(eventId);
            detailDTO.setMktResInstId(resourceInst.getMktResInstId());
            detailDTO.setChngType(ResourceConst.PUT_IN_STOAGE);
            detailDTO.setStatusCd(ResourceConst.StatusCdEnum.STATUS_CD_VALD.getCode());
            detailDTO.setCreateStaff(merchantId);
            detailDTO.setMktResInstNbr(resourceInst.getMktResInstNbr());
            detailDTO.setMktResInstId(resourceInst.getMktResInstId());
            detailDTO.setMktResStoreId(req.getDestStoreId());
            int addChngEvtDetailCnt = detailManager.insertChngEvtDetail(detailDTO);
            log.info("ResourceInstLogServiceImpl.addResourceInstLog detailManager.insertChngEvtDetail req={} addChngEvtDetailCnt={}", JSON.toJSONString(detailDTO), addChngEvtDetailCnt);
        }
    }

    @Async
    @Override
    public void updateResourceInstLog(ResourceInstUpdateReq req, List<ResourceInstListPageResp> resourceInsts) {
        log.info("ResourceInstLogServiceImpl.updateResourceInstLog req={}, resourceInsts={}", JSON.toJSONString(req), JSON.toJSONString(resourceInsts));
        if (ResourceConst.EVENTTYPE.NO_RECORD.getCode().equals(req.getEventType())) {
            log.info("ResourceInstLogServiceImpl.updateResourceInstLog no need record");
            return;
        }
        ResourceInstListPageResp inst = resourceInsts.get(0);
        // step2 记录事件(根据产品维度)
        ResouceEventDTO eventDTO = new ResouceEventDTO();
        BeanUtils.copyProperties(inst, eventDTO);
        BeanUtils.copyProperties(req, eventDTO);
        String mktResStoreId = req.getMktResStoreId();
        String destStoreId = req.getDestStoreId();
        eventDTO.setEventType(req.getEventType());
        eventDTO.setStatusCd(req.getEventStatusCd());
        eventDTO.setDestStoreId(mktResStoreId);
        eventDTO.setMktResStoreId(destStoreId);
        String eventId = resouceEventManager.insertResouceEvent(eventDTO);
        log.info("ResourceInstLogServiceImpl.updateResourceInstLog resourceInstManager.insertResouceEvent req={},resp={}", JSON.toJSONString(eventDTO), JSON.toJSONString(eventId));
        for (ResourceInstListPageResp resourceInst : resourceInsts) {
            String chngType = ResourceConst.STATUSCD.AVAILABLE.getCode().equals(req.getStatusCd()) ? ResourceConst.PUT_IN_STOAGE : ResourceConst.OUT_PUT_STOAGE;
            String eventStatusCd = ResourceConst.STATUSCD.AVAILABLE.getCode().equals(req.getStatusCd()) ? ResourceConst.StatusCdEnum.STATUS_CD_VALD.getCode() : ResourceConst.StatusCdEnum.STATUS_CD_INVALD.getCode();
            // 增加事件明细
            ResourceChngEvtDetailDTO detailDTO = new ResourceChngEvtDetailDTO();
            BeanUtils.copyProperties(req, detailDTO);
            BeanUtils.copyProperties(resourceInst, detailDTO);
            detailDTO.setMktResEventId(eventId);
            detailDTO.setChngType(chngType);
            detailDTO.setStatusCd(eventStatusCd);
            if (StringUtils.isEmpty(req.getUpdateStaff())) {
                detailDTO.setCreateStaff("1");
            } else {
                detailDTO.setCreateStaff(req.getUpdateStaff());
            }
            detailDTO.setMktResStoreId(resourceInst.getMktResStoreId());
            detailDTO.setMktResInstNbr(resourceInst.getMktResInstNbr());
            detailDTO.setCreateDate(new Date());
            detailDTO.setMktResStoreId(req.getDestStoreId());
            int addChngEvtDetailCnt = detailManager.insertChngEvtDetail(detailDTO);
            log.info("ResourceInstLogServiceImpl.updateResourceInstLog detailManager.insertChngEvtDetail req={} addChngEvtDetailCnt={}", JSON.toJSONString(detailDTO), addChngEvtDetailCnt);
        }
    }

    @Async
    @Override
    public void updateResourceInstByIdLog(AdminResourceInstDelReq req, List<ResourceInstDTO> resourceInsts) {
        log.info("ResourceInstLogServiceImpl.updateResourceInstByIdLog req={}, resourceInsts={}", JSON.toJSONString(req), JSON.toJSONString(resourceInsts));
        if (ResourceConst.EVENTTYPE.NO_RECORD.getCode().equals(req.getEventType())) {
            log.info("ResourceInstLogServiceImpl.updateResourceInstByIdLog no need record");
            return;
        }
        ResourceInstDTO inst = resourceInsts.get(0);
        // step2 记录事件(根据产品维度)
        ResouceEventDTO eventDTO = new ResouceEventDTO();
        BeanUtils.copyProperties(inst, eventDTO);
        BeanUtils.copyProperties(req, eventDTO);
        eventDTO.setEventType(req.getEventType());
        eventDTO.setStatusCd(req.getEventStatusCd());
        String eventId = resouceEventManager.insertResouceEvent(eventDTO);
        log.info("ResourceInstLogServiceImpl.updateResourceInstByIdLog resourceInstManager.insertResouceEvent req={},resp={}", JSON.toJSONString(eventDTO), JSON.toJSONString(eventId));
        for (ResourceInstDTO resourceInst : resourceInsts) {
            String chngType = ResourceConst.STATUSCD.AVAILABLE.getCode().equals(req.getStatusCd()) ? ResourceConst.PUT_IN_STOAGE : ResourceConst.OUT_PUT_STOAGE;
            String eventStatusCd = ResourceConst.STATUSCD.AVAILABLE.getCode().equals(req.getStatusCd()) ? ResourceConst.StatusCdEnum.STATUS_CD_VALD.getCode() : ResourceConst.StatusCdEnum.STATUS_CD_INVALD.getCode();
            // 增加事件明细
            ResourceChngEvtDetailDTO detailDTO = new ResourceChngEvtDetailDTO();
            BeanUtils.copyProperties(req, detailDTO);
            BeanUtils.copyProperties(resourceInst, detailDTO);
            detailDTO.setMktResEventId(eventId);
            detailDTO.setChngType(chngType);
            detailDTO.setStatusCd(eventStatusCd);
            if (StringUtils.isEmpty(req.getUpdateStaff())) {
                detailDTO.setCreateStaff("1");
            } else {
                detailDTO.setCreateStaff(req.getUpdateStaff());
            }
            detailDTO.setMktResStoreId(resourceInst.getMktResStoreId());
            detailDTO.setMktResInstNbr(resourceInst.getMktResInstNbr());
            detailDTO.setCreateDate(new Date());
            detailDTO.setMktResStoreId(req.getDestStoreId());
            int addChngEvtDetailCnt = detailManager.insertChngEvtDetail(detailDTO);
            log.info("ResourceInstLogServiceImpl.updateResourceInstByIdLog detailManager.insertChngEvtDetail req={} addChngEvtDetailCnt={}", JSON.toJSONString(detailDTO), addChngEvtDetailCnt);
        }
    }

    @Async
    @Override
    public void pickupResourceInstLog(ResourceInstPutInReq req, List<ResourceInstDTO> resourceList, String batchId) {
        log.info("ResourceInstLogServiceImpl.pickupResourceInstLog req={}, resourceInsts={}", JSON.toJSONString(req), JSON.toJSONString(resourceList));
        if (ResourceConst.EVENTTYPE.NO_RECORD.getCode().equals(req.getEventType())) {
            log.info("ResourceInstLogServiceImpl.pickupResourceInstLog no need record");
            return;
        }
        ResourceInstDTO inst = resourceList.get(0);
        Double salePrice = inst.getSalesPrice() == null ? 0D : inst.getSalesPrice();
        // 增加事件
        ResouceEventDTO eventDTO = new ResouceEventDTO();
        BeanUtils.copyProperties(inst, eventDTO);
        BeanUtils.copyProperties(req, eventDTO);
        eventDTO.setEventType(req.getEventType());
        eventDTO.setStatusCd(req.getEventStatusCd());
        String mktResStoreId = req.getMktResStoreId();
        String destStoreId = req.getDestStoreId();
        eventDTO.setDestStoreId(mktResStoreId);
        eventDTO.setMktResStoreId(destStoreId);
        String eventId = resouceEventManager.insertResouceEvent(eventDTO);
        log.info("ResourceInstLogServiceImpl.pickupResourceInstLog resouceStoreManager.insertResouceEvent req={},resp={}", JSON.toJSONString(eventDTO), JSON.toJSONString(eventId));
        // 增加批次
        ResourceBatchRecDTO batchRecDTO = new ResourceBatchRecDTO();
        BeanUtils.copyProperties(inst, batchRecDTO);
        batchRecDTO.setCostPrice(salePrice);
        Long size = Long.valueOf(resourceList.size());
        batchRecDTO.setQuantity(size);
        batchRecDTO.setMktResBatchId(batchId);
        batchRecManager.insertResourceBatchRec(batchRecDTO);
        log.info("ResourceInstLogServiceImpl.pickupResourceInstLog insertResourceBatchRec.insertResouceEvent req={},resp={}", JSON.toJSONString(batchRecDTO), JSON.toJSONString(batchId));
        for (ResourceInstDTO resourceInst : resourceList) {
            // 增加事件明细
            ResourceChngEvtDetailDTO detailDTO = new ResourceChngEvtDetailDTO();
            BeanUtils.copyProperties(req, detailDTO);
            detailDTO.setMktResEventId(eventId);
            detailDTO.setMktResInstId(resourceInst.getMktResInstId());
            detailDTO.setChngType(ResourceConst.PUT_IN_STOAGE);
            detailDTO.setStatusCd(ResourceConst.StatusCdEnum.STATUS_CD_VALD.getCode());
            detailDTO.setMktResInstNbr(resourceInst.getMktResInstNbr());
            detailDTO.setMktResInstId(resourceInst.getMktResInstId());
            detailDTO.setMktResStoreId(req.getDestStoreId());
            int addChngEvtDetailCnt = detailManager.insertChngEvtDetail(detailDTO);
            log.info("ResourceInstLogServiceImpl.pickupResourceInstLog detailManager.insertChngEvtDetail req={} addChngEvtDetailCnt={}", JSON.toJSONString(detailDTO), addChngEvtDetailCnt);
        }
    }

    @Async
    @Override
    public void delResourceInstLog(AdminResourceInstDelReq req, List<ResourceInstDTO> resourceInsts) {
        log.info("ResourceInstLogServiceImpl.delResourceInstLog req={}, resourceInsts={}", JSON.toJSONString(req), JSON.toJSONString(resourceInsts));
        if (ResourceConst.EVENTTYPE.NO_RECORD.getCode().equals(req.getEventType())) {
            log.info("ResourceInstLogServiceImpl.delResourceInstLog no need record");
            return;
        }
        List<String> productList = new ArrayList<>();
        for (ResourceInstDTO resourceInst : resourceInsts) {
            String productId = resourceInst.getMktResId();
            String eventId = "";
            if (!productList.contains(productId)) {
                // step2 记录事件(根据产品维度)
                ResouceEventDTO eventDTO = new ResouceEventDTO();
                BeanUtils.copyProperties(resourceInst, eventDTO);
                BeanUtils.copyProperties(req, eventDTO);
                eventDTO.setEventType(req.getEventType());
                eventDTO.setStatusCd(req.getEventStatusCd());
                eventId = resouceEventManager.insertResouceEvent(eventDTO);
                log.info("ResourceInstLogServiceImpl.delResourceInstLog resourceInstManager.insertResouceEvent req={},resp={}", JSON.toJSONString(eventDTO), JSON.toJSONString(eventId));
                productList.add(productId);
            }

            String chngType = ResourceConst.STATUSCD.AVAILABLE.getCode().equals(req.getStatusCd()) ? ResourceConst.PUT_IN_STOAGE : ResourceConst.OUT_PUT_STOAGE;
            String eventStatusCd = ResourceConst.STATUSCD.AVAILABLE.getCode().equals(req.getStatusCd()) ? ResourceConst.StatusCdEnum.STATUS_CD_VALD.getCode() : ResourceConst.StatusCdEnum.STATUS_CD_INVALD.getCode();
            // 增加事件明细
            ResourceChngEvtDetailDTO detailDTO = new ResourceChngEvtDetailDTO();
            BeanUtils.copyProperties(resourceInst, detailDTO);
            BeanUtils.copyProperties(req, detailDTO);
            detailDTO.setMktResEventId(eventId);
            detailDTO.setChngType(chngType);
            detailDTO.setStatusCd(eventStatusCd);
            if (StringUtils.isEmpty(req.getUpdateStaff())) {
                detailDTO.setCreateStaff("1");
            } else {
                detailDTO.setCreateStaff(req.getUpdateStaff());
            }
            detailDTO.setMktResStoreId(resourceInst.getMktResStoreId());
            detailDTO.setMktResInstNbr(resourceInst.getMktResInstNbr());
            detailDTO.setMktResStoreId(req.getDestStoreId());
            detailDTO.setCreateDate(new Date());
            int addChngEvtDetailCnt = detailManager.insertChngEvtDetail(detailDTO);
            log.info("ResourceInstLogServiceImpl.delResourceInstLog detailManager.insertChngEvtDetail req={} addChngEvtDetailCnt={}", JSON.toJSONString(detailDTO), addChngEvtDetailCnt);
        }
    }

    @Async
    @Override
    public void supplierDeliveryOutResourceInstLog(ResourceInstUpdateReq req, List<ResourceInstDTO> resourceInsts) {
        log.info("ResourceInstLogServiceImpl.delResourceInstLog req={}, resourceInsts={}", JSON.toJSONString(req), JSON.toJSONString(resourceInsts));
        if (ResourceConst.EVENTTYPE.NO_RECORD.getCode().equals(req.getEventType())) {
            log.info("ResourceInstLogServiceImpl.supplierDeliveryOutResourceInstLog no need record");
            return;
        }
        List<String> productList = new ArrayList<>();
        for (ResourceInstDTO resourceInst : resourceInsts) {
            String productId = resourceInst.getMktResId();
            String eventId = "";
            if (!productList.contains(productId)) {
                // step2 记录事件(根据产品维度)
                ResouceEventDTO eventDTO = new ResouceEventDTO();
                BeanUtils.copyProperties(resourceInst, eventDTO);
                BeanUtils.copyProperties(req, eventDTO);
                eventDTO.setEventType(req.getEventType());
                eventDTO.setStatusCd(req.getEventStatusCd());
                eventId = resouceEventManager.insertResouceEvent(eventDTO);
                log.info("ResourceInstLogServiceImpl.delResourceInstLog resourceInstManager.insertResouceEvent req={},resp={}", JSON.toJSONString(eventDTO), JSON.toJSONString(eventId));
                productList.add(productId);
            }

            String chngType = ResourceConst.STATUSCD.AVAILABLE.getCode().equals(req.getStatusCd()) ? ResourceConst.PUT_IN_STOAGE : ResourceConst.OUT_PUT_STOAGE;
            String eventStatusCd = ResourceConst.STATUSCD.AVAILABLE.getCode().equals(req.getStatusCd()) ? ResourceConst.StatusCdEnum.STATUS_CD_VALD.getCode() : ResourceConst.StatusCdEnum.STATUS_CD_INVALD.getCode();
            // 增加事件明细
            ResourceChngEvtDetailDTO detailDTO = new ResourceChngEvtDetailDTO();
            BeanUtils.copyProperties(req, detailDTO);
            BeanUtils.copyProperties(resourceInst, detailDTO);
            detailDTO.setMktResEventId(eventId);
            detailDTO.setChngType(chngType);
            detailDTO.setStatusCd(eventStatusCd);
            if (StringUtils.isEmpty(req.getUpdateStaff())) {
                detailDTO.setCreateStaff("1");
            } else {
                detailDTO.setCreateStaff(req.getUpdateStaff());
            }
            detailDTO.setMktResStoreId(resourceInst.getMktResStoreId());
            detailDTO.setMktResInstNbr(resourceInst.getMktResInstNbr());
            detailDTO.setCreateDate(new Date());
            detailDTO.setMktResStoreId(req.getDestStoreId());
            int addChngEvtDetailCnt = detailManager.insertChngEvtDetail(detailDTO);
            log.info("ResourceInstLogServiceImpl.delResourceInstLog detailManager.insertChngEvtDetail req={} addChngEvtDetailCnt={}", JSON.toJSONString(detailDTO), addChngEvtDetailCnt);
        }
    }
}
