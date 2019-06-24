package com.iwhalecloud.retail.warehouse.dubbo.workflow;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.req.ProductGetByIdReq;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.service.MerchantLimitService;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceBatchRecService;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.ResouceStoreDTO;
import com.iwhalecloud.retail.warehouse.dto.ResourceReqDetailDTO;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.manager.ResourceReqDetailManager;
import com.iwhalecloud.retail.warehouse.runable.RunableTask;
import com.iwhalecloud.retail.warehouse.service.MerchantAddNbrProcessingPassActionService;
import com.iwhalecloud.retail.warehouse.service.ResouceStoreService;
import com.iwhalecloud.retail.warehouse.service.ResourceRequestService;
import com.iwhalecloud.retail.workflow.config.InvokeRouteServiceRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    @Reference
    private ProductService productService;

    @Autowired
    private ResourceReqDetailManager resourceReqDetailManager;
    
    @Override
    public ResultVO run(InvokeRouteServiceRequest params) {
        String businessId = params.getBusinessId();
        // step1 根据申请单表保存的源仓库和申请单明细找到对应的串码
        ResourceReqDetailQueryReq detailQueryReq = new ResourceReqDetailQueryReq();
        detailQueryReq.setMktResReqId(businessId);
        List<ResourceReqDetailDTO> reqDetailDTOS = detailManager.listDetail(detailQueryReq);
        List<String> mktResInstNbrs = reqDetailDTOS.stream().map(ResourceReqDetailDTO::getMktResInstNbr).collect(Collectors.toList());
        log.info("MerchantAddNbrProcessingPassActionImpl.run detailManager.listDetail mktResInstNbrs={}", JSON.toJSONString(mktResInstNbrs));
        Map<String, String> ctCodeMap = new HashMap<>();
        Map<String, String> snCodeMap = new HashMap<>();
        Map<String, String> macCodeMap = new HashMap<>();
        reqDetailDTOS.forEach(item->{
            if(StringUtils.isNotBlank(item.getCtCode())){
                ctCodeMap.put(item.getMktResInstNbr(), item.getCtCode());
            }
            if(StringUtils.isNotBlank(item.getSnCode())){
                snCodeMap.put(item.getMktResInstNbr(), item.getSnCode());
            }
            if(StringUtils.isNotBlank(item.getMacCode())){
                macCodeMap.put(item.getMktResInstNbr(), item.getMacCode());
            }

        });
        ResourceReqDetailDTO detailDTO = reqDetailDTOS.get(0);

        ProductGetByIdReq productGetByIdReq = new ProductGetByIdReq();
        productGetByIdReq.setProductId(detailDTO.getMktResId());
        ResultVO<ProductResp> producttVO = productService.getProduct(productGetByIdReq);
        log.info("MerchantAddNbrProcessingPassActionImpl.run productService.getProduct mktResId={} resp={}", detailDTO.getMktResId(), JSON.toJSONString(producttVO));
        String typeId = "";
        if (producttVO.isSuccess() && null != producttVO.getResultData()) {
            typeId = producttVO.getResultData().getTypeId();
        }

        // step2 根据申请单表保存的目标仓库和申请单明细找到对应的串码及商家信息
        ResourceInstAddReq addReq = new ResourceInstAddReq();
        addReq.setMktResInstNbrs(mktResInstNbrs);
        addReq.setStatusCd(ResourceConst.STATUSCD.AVAILABLE.getCode());
        addReq.setSourceType(ResourceConst.SOURCE_TYPE.MERCHANT.getCode());
        addReq.setStorageType(ResourceConst.STORAGETYPE.VENDOR_INPUT.getCode());
        addReq.setEventType(ResourceConst.EVENTTYPE.PUT_STORAGE.getCode());
        addReq.setMktResStoreId(ResourceConst.NULL_STORE_ID);
        addReq.setEventStatusCd(ResourceConst.EVENTSTATE.DONE.getCode());
        addReq.setMktResInstType(detailDTO.getMktResInstType());
        addReq.setDestStoreId(detailDTO.getDestStoreId());
        addReq.setMktResId(detailDTO.getMktResId());
        addReq.setCtCodeMap(ctCodeMap);
        addReq.setSnCodeMap(snCodeMap);
        addReq.setMacCodeMap(macCodeMap);
        addReq.setCreateStaff(detailDTO.getCreateStaff());
        addReq.setTypeId(typeId);

        if (ResourceConst.MKTResInstType.NONTRANSACTION.getCode().equals(detailDTO.getMktResInstType())) {
            StorePageReq storePageReq = new StorePageReq();
            storePageReq.setMktResStoreId(detailDTO.getDestStoreId());
            Page<ResouceStoreDTO> page = resouceStoreService.pageStore(storePageReq);
            log.info("MerchantAddNbrProcessingPassActionImpl.run resouceStoreService.pageStore storePageReq={}", JSON.toJSONString(page.getRecords()));
            ResouceStoreDTO storeDTO = page.getRecords().get(0);
            addReq.setLanId(storeDTO.getLanId());
            addReq.setRegionId(storeDTO.getRegionId());
            addReq.setMerchantId(storeDTO.getMerchantId());
        } else {
            ResultVO<MerchantDTO> resultVO = resouceStoreService.getMerchantByStore(detailDTO.getDestStoreId());
            String merchantId = null;
            if(null == resultVO || null == resultVO.getResultData()){
                log.warn("MerchantAddNbrProcessingPassActionImpl.run resouceStoreService.getMerchantByStore resultVO is null");
                return ResultVO.error("MerchantAddNbrProcessingPassActionImpl.run resouceStoreService.getMerchantByStore resultVO is null");
            } else {
                MerchantDTO merchantDTO = resultVO.getResultData();
                merchantId = merchantDTO.getMerchantId();
                addReq.setLanId(merchantDTO.getLanId());
                addReq.setRegionId(merchantDTO.getCity());
                addReq.setMerchantId(merchantId);
            }
        }
        runableTask.exceutorAddNbr(addReq);
        log.info("MerchantAddNbrProcessingPassActionImpl.run resourceInstService.addResourceInst addReq={}", addReq);

        // step3 修改申请单状态变为审核通过
        ResourceRequestUpdateReq reqUpdate = new ResourceRequestUpdateReq();
        reqUpdate.setMktResReqId(businessId);
        reqUpdate.setStatusCd(ResourceConst.MKTRESSTATE.DONE.getCode());
        ResultVO<Boolean> updatRequestVO = requestService.updateResourceRequestState(reqUpdate);
        log.info("MerchantAddNbrProcessingPassActionImpl.run requestService.updateResourceRequestState reqUpdate={}, resp={}", JSON.toJSONString(reqUpdate), JSON.toJSONString(updatRequestVO));
        // step4 修改申请单详情状态为通过
        ResourceReqDetailUpdateReq detailUpdateReq = new ResourceReqDetailUpdateReq();
        detailUpdateReq.setMktResReqItemIdList(Lists.newArrayList(detailDTO.getMktResReqItemId()));
        detailUpdateReq.setStatusCd(ResourceConst.DetailStatusCd.STATUS_CD_1005.getCode());
        detailUpdateReq.setUpdateStaff(params.getHandlerUserId());
        Integer detailNum = resourceReqDetailManager.updateResourceReqDetailStatusCd(detailUpdateReq);
        log.info("MerchantAddNbrProcessingPassActionImpl.run resourceReqDetailManager.updateResourceReqDetailStatusCd detailUpdateReq={}, resp={}", JSON.toJSONString(detailUpdateReq), detailNum);
        runableTask.exceutorAddNbrTrack(addReq);
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
