package com.iwhalecloud.retail.warehouse.busiservice.impl;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceBatchRecService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.ResouceEventDTO;
import com.iwhalecloud.retail.warehouse.dto.ResourceBatchRecDTO;
import com.iwhalecloud.retail.warehouse.dto.ResourceChngEvtDetailDTO;
import com.iwhalecloud.retail.warehouse.dto.request.BatchAndEventAddReq;
import com.iwhalecloud.retail.warehouse.manager.ResouceEventManager;
import com.iwhalecloud.retail.warehouse.manager.ResourceBatchRecManager;
import com.iwhalecloud.retail.warehouse.manager.ResourceChngEvtDetailManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class ResourceBatchRecServiceImpl implements ResourceBatchRecService {

    @Autowired
    private ResourceBatchRecManager resourceBatchRecManager;

    @Autowired
    private ResouceEventManager resouceEventManager;

    @Autowired
    private ResourceChngEvtDetailManager resourceChngEvtDetailManager;

    @Override
    public ResultVO<Boolean> insertResourceBatchRec(ResourceBatchRecDTO batchRecDTO) {
        String batchRecId = resourceBatchRecManager.insertResourceBatchRec(batchRecDTO);
        if(StringUtils.isEmpty(batchRecId)){
            return ResultVO.error();
        }
        return ResultVO.success(true);
    }


    @Override
    @Async
    public ResultVO saveEventAndBatch(BatchAndEventAddReq req) {
        Map<String, List<String>> mktResIdAndNbrMap = req.getMktResIdAndNbrMap();
        for (Map.Entry<String, List<String>> entry : mktResIdAndNbrMap.entrySet()) {

            // step1增加批次事件并把ID返回
            ResourceBatchRecDTO batchRecDTO = new ResourceBatchRecDTO();
            BeanUtils.copyProperties(req, batchRecDTO);
            batchRecDTO.setQuantity(1L);
            resourceBatchRecManager.insertResourceBatchRec(batchRecDTO);
            log.info("ResourceInstServiceImpl.addResourceInst batchRecManager.insertResourceBatchRec req={}", JSON.toJSONString(batchRecDTO));
            // 增加事件
            ResouceEventDTO eventDTO = new ResouceEventDTO();
            BeanUtils.copyProperties(req, eventDTO);
            eventDTO.setMktResId(entry.getKey());
            String eventId = resouceEventManager.insertResouceEvent(eventDTO);
            log.info("ResourceInstServiceImpl.addResourceInst resouceEventManager.insertResouceEvent req={},eventId={}", JSON.toJSONString(eventDTO), JSON.toJSONString(eventId));

            // 串码目标仓库也加一条明细,如果没传，像调拨，在调拨的时候就插入了，收货的时候不需要在插入
            String mktResStoreId = req.getMktResStoreId();
            if (!StringUtils.isEmpty(mktResStoreId) && !ResourceConst.NULL_STORE_ID.equals(mktResStoreId)) {
                for(String nbr : entry.getValue()){
                    // 增加事件明细
                    ResourceChngEvtDetailDTO detailDTO = new ResourceChngEvtDetailDTO();
                    BeanUtils.copyProperties(req, detailDTO);
                    detailDTO.setMktResEventId(eventId);
                    detailDTO.setChngType(ResourceConst.PUT_IN_STOAGE);
                    detailDTO.setStatusCd(ResourceConst.StatusCdEnum.STATUS_CD_VALD.getCode());
                    detailDTO.setMktResInstNbr(nbr);
                    int addChngEvtDetailCnt = resourceChngEvtDetailManager.insertChngEvtDetail(detailDTO);
                    log.info("ResourceInstServiceImpl.addResourceInst detailManager.insertChngEvtDetail req={} addChngEvtDetailCnt={}", JSON.toJSONString(detailDTO), addChngEvtDetailCnt);
                }
            }

            String destStroeId = req.getDestStoreId();
            if (!StringUtils.isEmpty(destStroeId) && !ResourceConst.NULL_STORE_ID.equals(destStroeId)) {
                for(String nbr : entry.getValue()){
                    ResourceChngEvtDetailDTO detailDTO = new ResourceChngEvtDetailDTO();
                    BeanUtils.copyProperties(req, detailDTO);
                    detailDTO.setMktResEventId(eventId);
                    detailDTO.setChngType(ResourceConst.OUT_PUT_STOAGE);
                    detailDTO.setStatusCd(ResourceConst.StatusCdEnum.STATUS_CD_INVALD.getCode());
                    detailDTO.setMktResInstNbr(nbr);
                    detailDTO.setMktResStoreId(req.getDestStoreId());
                    int addChngEvtDetailCnt = resourceChngEvtDetailManager.insertChngEvtDetail(detailDTO);
                    log.info("ResourceInstServiceImpl.addResourceInst detailManager.insertChngEvtDetail req={} addChngEvtDetailCnt={}", JSON.toJSONString(detailDTO), addChngEvtDetailCnt);
                }
            }
        }

        return ResultVO.success();
    }


}