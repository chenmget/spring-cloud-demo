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
import com.iwhalecloud.retail.warehouse.service.GreenChannelProcessingPassActionService;
import com.iwhalecloud.retail.warehouse.service.ResouceStoreService;
import com.iwhalecloud.retail.warehouse.service.ResourceRequestService;
import com.iwhalecloud.retail.warehouse.util.ProfileUtil;
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
public class GreenChannelProcessingPassActionImpl implements GreenChannelProcessingPassActionService {

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
    private ProfileUtil profileUtil;

    @Autowired
    private ResourceBatchRecService resourceBatchRecService;
    
    @Override
    public ResultVO run(InvokeRouteServiceRequest params) {
        //对应申请单ID
        String businessId = params.getBusinessId();
        //根据申请单表保存的源仓库和申请单明细找到对应的串码
        ResourceReqDetailQueryReq detailQueryReq = new ResourceReqDetailQueryReq();
        detailQueryReq.setMktResReqId(businessId);
        List<ResourceReqDetailDTO> reqDetailDTOS = detailManager.listDetail(detailQueryReq);
        log.info("GreenChannelProcessingPassActionImpl.run detailManager.listDetail detailQueryReq={}, resp={}", JSON.toJSONString(detailQueryReq), JSON.toJSONString(reqDetailDTOS));
        List<String> mktResInstNbrs = reqDetailDTOS.stream().map(ResourceReqDetailDTO::getMktResInstNbr).collect(Collectors.toList());
        ResourceReqDetailDTO detailDTO = reqDetailDTOS.get(0);
        //根据申请单表保存的目标仓库和申请单明细找到对应的串码及商家信息
        ResourceInstAddReq addReq = new ResourceInstAddReq();
        addReq.setMktResInstNbrs(mktResInstNbrs);
        addReq.setStatusCd(ResourceConst.STATUSCD.AVAILABLE.getCode());
        addReq.setSourceType(ResourceConst.SOURCE_TYPE.RETAILER.getCode());
        addReq.setStorageType(ResourceConst.STORAGETYPE.GREEN_CHANNEL.getCode());
        addReq.setEventType(ResourceConst.EVENTTYPE.PUT_STORAGE.getCode());
        addReq.setMktResStoreId(ResourceConst.NULL_STORE_ID);
        addReq.setDestStoreId(detailDTO.getMktResStoreId());
        addReq.setMktResId(detailDTO.getMktResId());

        ResultVO<MerchantDTO> resultVO = resouceStoreService.getMerchantByStore(detailDTO.getMktResStoreId());
        String merchantId = null;
        if(null == resultVO || null == resultVO.getResultData()){
            log.warn("GreenChannelProcessingPassActionImpl.run resouceStoreService.getMerchantByStore resultVO is null");
            return ResultVO.error("GreenChannelProcessingPassActionImpl.run resouceStoreService.getMerchantByStore resultVO is null");
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
        ResultVO addRespResultVO = null;
        if (profileUtil.isLocal()) {
            addRespResultVO = resourceInstService.syncTerminal(addReq);
            log.info("GreenChannelProcessingPassActionImpl.run resourceInstService.syncTerminal addReq={}, resp={}", JSON.toJSONString(addReq), JSON.toJSONString(addRespResultVO));
        } else {
            addRespResultVO = ResultVO.success();
        }
        if (addRespResultVO.isSuccess()) {
            //修改申请单状态变为审核通过
            ResourceRequestUpdateReq reqUpdate = new ResourceRequestUpdateReq();
            reqUpdate.setMktResReqId(businessId);
            reqUpdate.setStatusCd(ResourceConst.MKTRESSTATE.REVIEWED.getCode());
            ResultVO<Boolean> updatRequestVO = requestService.updateResourceRequestState(reqUpdate);
            log.info("GreenChannelProcessingPassActionImpl.run requestService.updateResourceRequestState reqUpdate={}, resp={}", JSON.toJSONString(reqUpdate), JSON.toJSONString(updatRequestVO));

            // step3 增加事件和批次
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
            log.info("GreenChannelProcessingPassActionImpl.run resourceBatchRecService.saveEventAndBatch req={},resp={}", JSON.toJSONString(batchAndEventAddReq));
        }
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
