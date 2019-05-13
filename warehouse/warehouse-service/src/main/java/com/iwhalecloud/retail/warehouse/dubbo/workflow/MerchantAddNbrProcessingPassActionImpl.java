package com.iwhalecloud.retail.warehouse.dubbo.workflow;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.service.MerchantLimitService;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceBatchRecService;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.ResourceReqDetailDTO;
import com.iwhalecloud.retail.warehouse.dto.request.BatchAndEventAddReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstAddReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceReqDetailQueryReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceRequestUpdateReq;
import com.iwhalecloud.retail.warehouse.manager.ResourceReqDetailManager;
import com.iwhalecloud.retail.warehouse.runable.RunableTask;
import com.iwhalecloud.retail.warehouse.service.MerchantAddNbrProcessingPassActionService;
import com.iwhalecloud.retail.warehouse.service.ResouceStoreService;
import com.iwhalecloud.retail.warehouse.service.ResourceRequestService;
import com.iwhalecloud.retail.workflow.config.InvokeRouteServiceRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author my
 * @desc 绿色通道审核通过处理
 */
@Slf4j
@Service
public class MerchantAddNbrProcessingPassActionImpl implements MerchantAddNbrProcessingPassActionService {

    @Autowired
    private ResourceInstService resourceInstService;

    @Reference
    private MerchantLimitService merchantLimitService;

    @Autowired
    private ResourceReqDetailManager detailManager;

    @Reference
    private ResouceStoreService resouceStoreService;

    @Reference
    private ResourceRequestService requestService;

    @Autowired
    private ResourceBatchRecService resourceBatchRecService;

    @Autowired
    private RunableTask runableTask;
    
    @Override
    public ResultVO run(InvokeRouteServiceRequest params) {
        String businessId = params.getBusinessId();
        // step1 根据申请单表保存的源仓库和申请单明细找到对应的串码
        ResourceReqDetailQueryReq detailQueryReq = new ResourceReqDetailQueryReq();
        detailQueryReq.setMktResReqId(businessId);
        List<ResourceReqDetailDTO> reqDetailDTOS = detailManager.listDetail(detailQueryReq);
        log.info("MerchantAddNbrProcessingPassActionImpl.run detailManager.listDetail detailQueryReq={}, respSize={}", JSON.toJSONString(detailQueryReq), null == reqDetailDTOS ? 0 : reqDetailDTOS.size());
        List<String> mktResInstNbrs = reqDetailDTOS.stream().map(ResourceReqDetailDTO::getMktResInstNbr).collect(Collectors.toList());
        Map<String, String> ctCodeMap = new HashMap<>();
        reqDetailDTOS.forEach(item->{
            ctCodeMap.put(item.getMktResInstNbr(), item.getCtCode());
        });
        ResourceReqDetailDTO detailDTO = reqDetailDTOS.get(0);

        // step2 根据申请单表保存的目标仓库和申请单明细找到对应的串码及商家信息
        ResourceInstAddReq addReq = new ResourceInstAddReq();
        addReq.setMktResInstNbrs(mktResInstNbrs);
        addReq.setStatusCd(ResourceConst.STATUSCD.AVAILABLE.getCode());
        addReq.setSourceType(ResourceConst.SOURCE_TYPE.MERCHANT.getCode());
        addReq.setStorageType(ResourceConst.STORAGETYPE.VENDOR_INPUT.getCode());
        addReq.setEventType(ResourceConst.EVENTTYPE.PUT_STORAGE.getCode());
        addReq.setMktResStoreId(ResourceConst.NULL_STORE_ID);
        addReq.setMktResInstType(detailDTO.getMktResInstType());
        addReq.setDestStoreId(detailDTO.getDestStoreId());
        addReq.setMktResId(detailDTO.getMktResId());
        addReq.setCtCode(ctCodeMap);
        addReq.setCreateStaff(detailDTO.getCreateStaff());

        ResultVO<MerchantDTO> resultVO = resouceStoreService.getMerchantByStore(detailDTO.getDestStoreId());
        String merchantId = null;
        if(null == resultVO || null == resultVO.getResultData()){
            log.warn("MerchantAddNbrProcessingPassActionImpl.run resouceStoreService.getMerchantByStore resultVO is null");
            return ResultVO.error("MerchantAddNbrProcessingPassActionImpl.run resouceStoreService.getMerchantByStore resultVO is null");
        } else {
            MerchantDTO merchantDTO = resultVO.getResultData();
            merchantId = merchantDTO.getMerchantId();
            addReq.setMerchantId(merchantDTO.getMerchantId());
            addReq.setMerchantType(merchantDTO.getMerchantType());
            addReq.setMerchantName(merchantDTO.getMerchantName());
            addReq.setMerchantCode(merchantDTO.getMerchantCode());
            addReq.setLanId(merchantDTO.getLanId());
            addReq.setRegionId(merchantDTO.getCity());
        }
        runableTask.exceutorAddNbr(addReq);
        log.info("MerchantAddNbrProcessingPassActionImpl.run resourceInstService.addResourceInst addReq={}", addReq);

        // step3 修改申请单状态变为审核通过
        ResourceRequestUpdateReq reqUpdate = new ResourceRequestUpdateReq();
        reqUpdate.setMktResReqId(businessId);
        reqUpdate.setStatusCd(ResourceConst.MKTRESSTATE.REVIEWED.getCode());
        ResultVO<Boolean> updatRequestVO = requestService.updateResourceRequestState(reqUpdate);
        log.info("MerchantAddNbrProcessingPassActionImpl.run requestService.updateResourceRequestState reqUpdate={}, resp={}", JSON.toJSONString(reqUpdate), JSON.toJSONString(updatRequestVO));
        // step4 增加事件和批次
        Map<String, List<String>> mktResIdAndNbrMap = this.getMktResIdAndNbrMap(reqDetailDTOS);
        BatchAndEventAddReq batchAndEventAddReq = new BatchAndEventAddReq();
        batchAndEventAddReq.setEventType(ResourceConst.EVENTTYPE.PUT_STORAGE.getCode());
        batchAndEventAddReq.setLanId(detailDTO.getLanId());
        batchAndEventAddReq.setMktResIdAndNbrMap(mktResIdAndNbrMap);
        batchAndEventAddReq.setRegionId(detailDTO.getRegionId());
        batchAndEventAddReq.setDestStoreId(detailDTO.getMktResStoreId());
        batchAndEventAddReq.setMktResStoreId(ResourceConst.NULL_STORE_ID);
        batchAndEventAddReq.setMerchantId(merchantId);
        batchAndEventAddReq.setCreateStaff(merchantId);
        resourceBatchRecService.saveEventAndBatch(batchAndEventAddReq);
        log.info("MerchantAddNbrProcessingPassActionImpl.run resourceBatchRecService.saveEventAndBatch req={},resp={}", JSON.toJSONString(batchAndEventAddReq));

        return ResultVO.success();
    }


    private Map<String, List<String>> getMktResIdAndNbrMap(List<ResourceReqDetailDTO> instList){
        Map<String, List<String>> mktResIdAndNbrMap = new HashMap<>();
        List<ResourceReqDetailDTO> detailList = instList;
        for (ResourceReqDetailDTO resp : detailList){
            if(mktResIdAndNbrMap.containsKey(resp.getMktResId())){
                List<String> mktResIdList = mktResIdAndNbrMap.get(resp.getMktResId());
                mktResIdList.add(resp.getMktResInstNbr());
            }else{
                List<String> mktResIdList = new ArrayList<>();
                mktResIdList.add(resp.getMktResInstNbr());
                mktResIdAndNbrMap.put(resp.getMktResId(), mktResIdList);
            }
        }
        return mktResIdAndNbrMap;
    }
}
