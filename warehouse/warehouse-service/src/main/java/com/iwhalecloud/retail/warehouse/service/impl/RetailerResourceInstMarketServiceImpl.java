package com.iwhalecloud.retail.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.exception.RetailTipException;
import com.iwhalecloud.retail.goods2b.dto.req.ProductGetByIdReq;
import com.iwhalecloud.retail.goods2b.dto.req.ProductResourceInstGetReq;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductForResourceResp;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductResourceResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.dto.MerchantLimitDTO;
import com.iwhalecloud.retail.partner.dto.req.MerchantLimitUpdateReq;
import com.iwhalecloud.retail.partner.service.MerchantLimitService;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.system.dto.response.OrganizationRegionResp;
import com.iwhalecloud.retail.system.service.OrganizationService;
import com.iwhalecloud.retail.warehouse.busiservice.ResouceEventService;
import com.iwhalecloud.retail.warehouse.busiservice.ResouceInstTrackService;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceBatchRecService;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.constant.Constant;
import com.iwhalecloud.retail.warehouse.dto.ResouceInstTrackDTO;
import com.iwhalecloud.retail.warehouse.dto.ResourceInstStoreDTO;
import com.iwhalecloud.retail.warehouse.dto.ResourceReqDetailDTO;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.dto.request.markresswap.*;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstAddResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListPageResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceRequestResp;
import com.iwhalecloud.retail.warehouse.dto.response.markresswap.QryMktInstInfoByConditionItemSwapResp;
import com.iwhalecloud.retail.warehouse.dto.response.markresswap.base.QueryMarkResQueryResultsSwapResp;
import com.iwhalecloud.retail.warehouse.manager.ResourceBatchRecManager;
import com.iwhalecloud.retail.warehouse.manager.ResourceInstManager;
import com.iwhalecloud.retail.warehouse.manager.ResourceInstStoreManager;
import com.iwhalecloud.retail.warehouse.manager.ResourceReqDetailManager;
import com.iwhalecloud.retail.warehouse.model.MerchantInfByNbrModel;
import com.iwhalecloud.retail.warehouse.service.MarketingResStoreService;
import com.iwhalecloud.retail.warehouse.service.ResouceStoreService;
import com.iwhalecloud.retail.warehouse.service.ResourceRequestService;
import com.iwhalecloud.retail.warehouse.service.RetailerResourceInstService;
import com.iwhalecloud.retail.workflow.common.WorkFlowConst;
import com.iwhalecloud.retail.workflow.dto.req.HandlerUser;
import com.iwhalecloud.retail.workflow.dto.req.ProcessStartReq;
import com.iwhalecloud.retail.workflow.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RetailerResourceInstMarketServiceImpl implements RetailerResourceInstService {

    @Autowired
    private ResourceInstManager resourceInstManager;
    @Autowired
    private ResourceBatchRecManager batchRecManager;
    @Reference
    private ProductService productService;
    @Autowired
    private ResourceInstService resourceInstService;
    @Reference
    private TaskService taskService;
    @Reference
    private MerchantService merchantService;
    @Reference
    private MerchantLimitService merchantLimitService;
    @Reference
    private ResouceStoreService resouceStoreService;
    @Autowired
    private ResourceReqDetailManager resourceReqDetailManager;
    @Reference
    private ResourceRequestService requestService;
    @Reference
    private MarketingResStoreService marketingResStoreService;
    @Autowired
    private ResourceBatchRecService resourceBatchRecService;
    @Autowired
    private ResourceInstStoreManager resourceInstStoreManager;
    @Autowired
    private ResouceEventService resouceEventService;
    @Autowired
    private ResouceInstTrackService resouceInstTrackService;
    @Autowired
    private Constant constant;
    @Reference
    private OrganizationService organizationService;

    private final String CLASS_TYPE_1 = "1";
    private final String CLASS_TYPE_2 = "2";
    private final String CLASS_TYPE_3 = "3";

    /**
     * 退库
     */
    private final String serviceCode = "Bandout";

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResultVO addResourceInstByGreenChannel(ResourceInstAddReq req) {
        // step1 绿色通道额度校验
        String merchantId = req.getMerchantId();
        Long limitNum = Long.valueOf(req.getMktResInstNbrs().size());
        ResultVO<MerchantDTO> merchantDTOResultVO = merchantService.getMerchantById(merchantId);
        if (!merchantDTOResultVO.isSuccess() || null == merchantDTOResultVO.getResultData()) {
            return ResultVO.error("商家获取失败");
        }
        ResultVO<MerchantLimitDTO> merchantLimitDTO = merchantLimitService.getMerchantLimit(merchantId);
        log.info("RetailerResourceInstMarketServiceImpl.addResourceInstByGreenChannel merchantLimitService.getMerchantLimit merchantId={}, resp={}", merchantId, JSON.toJSONString(merchantLimitDTO));
        if (!merchantLimitDTO.isSuccess() || merchantLimitDTO.getResultData() == null) {
            return ResultVO.error("获取额度失败");
        }
        // 获取仓库
        StoreGetStoreIdReq storeGetStoreIdReq = new StoreGetStoreIdReq();
        storeGetStoreIdReq.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
        storeGetStoreIdReq.setMerchantId(merchantId);
        String mktResStoreId = resouceStoreService.getStoreId(storeGetStoreIdReq);
        log.info("RetailerResourceInstMarketServiceImpl.addResourceInstByGreenChannel resouceStoreService.getStoreId req={},resp={}", JSON.toJSONString(storeGetStoreIdReq), mktResStoreId);

        // 没有配置限额或者配置了并且超额
        Boolean aboveLimit = merchantLimitDTO.getResultData().getMaxSerialNum() < (merchantLimitDTO.getResultData().getSerialNumUsed() + limitNum);
        if (aboveLimit) {
            // step2 新增申请单
            ResourceRequestAddReq resourceRequestAddReq = new ResourceRequestAddReq();
            List<ResourceRequestAddReq.ResourceRequestInst> instDTOList = Lists.newLinkedList();
            for (String str : req.getMktResInstNbrs()) {
                ResourceRequestAddReq.ResourceRequestInst instDTO = new ResourceRequestAddReq.ResourceRequestInst();
                instDTO.setMktResInstNbr(str);
                instDTO.setMktResId(req.getMktResId());
                instDTOList.add(instDTO);
            }
            String reqCode = resourceInstManager.getPrimaryKey();
            resourceRequestAddReq.setReqType(ResourceConst.REQTYPE.PUTSTORAGE_APPLYFOR.getCode());
            BeanUtils.copyProperties(req, resourceRequestAddReq);
            resourceRequestAddReq.setInstList(instDTOList);
            resourceRequestAddReq.setReqCode(reqCode);
            resourceRequestAddReq.setReqName(createReqName(req.getProductName(), resourceRequestAddReq.getReqType()));
            resourceRequestAddReq.setChngType(ResourceConst.PUT_IN_STOAGE);
            resourceRequestAddReq.setLanId(merchantDTOResultVO.getResultData().getLanId());
            resourceRequestAddReq.setRegionId(merchantDTOResultVO.getResultData().getCity());

            resourceRequestAddReq.setMktResStoreId(mktResStoreId);
            ResultVO<String> resultVO = requestService.insertResourceRequest(resourceRequestAddReq);
            log.info("RetailerResourceInstMarketServiceImpl.addResourceInstByGreenChannel() resourceRequestService.insertResourceRequest req={}, resultVO={}", JSON.toJSONString(resourceRequestAddReq), JSON.toJSONString(resultVO));
            // step3 启动工作流
            ProcessStartReq processStartDTO = new ProcessStartReq();
            processStartDTO.setTitle("绿色通道超过限额审批流程");
            processStartDTO.setApplyUserId(req.getCreateStaff());
            processStartDTO.setProcessId(ResourceConst.GREEN_CHANNEL_WORK_FLOW_INST);
            processStartDTO.setTaskSubType(WorkFlowConst.TASK_SUB_TYPE.TASK_SUB_TYPE_1090.getTaskSubType());
            processStartDTO.setApplyUserName(req.getApplyUserName());
            if (resultVO != null && resultVO.getResultData() != null) {
                processStartDTO.setFormId(resultVO.getResultData());
            }
            ResultVO startResultVO = taskService.startProcess(processStartDTO);
            log.info("RetailerResourceInstMarketServiceImpl.addResourceInstByGreenChannel taskService.startProcess req={}, resp={}", JSON.toJSONString(processStartDTO), JSON.toJSONString(startResultVO));
            if (null != startResultVO && startResultVO.getResultCode().equals(ResultCodeEnum.ERROR.getCode())) {
                throw new RetailTipException(ResultCodeEnum.ERROR.getCode(), "启动工作流失败");
            }
            return ResultVO.error(ResourceConst.SUCESS_MSG + reqCode);
        }else{
            req.setMktResStoreId(mktResStoreId);
            ResultVO syncTerminalResultVO = resourceInstService.syncTerminal(req);
            if (syncTerminalResultVO.isSuccess()) {
                // step4 修改绿色通道免审核额度
                Long usedInstNbrsNum = merchantLimitDTO.getResultData().getSerialNumUsed() + limitNum;
                MerchantLimitUpdateReq merchantLimitUpdateReq = new MerchantLimitUpdateReq();
                merchantLimitUpdateReq.setMerchantId(merchantId);
                merchantLimitUpdateReq.setSerialNumUsed(usedInstNbrsNum);
                ResultVO updateGreenChannelUseLimit = merchantLimitService.updateMerchantLimit(merchantLimitUpdateReq);
                log.info("RetailerResourceInstServiceImpl.addResourceInstByGreenChannel merchantLimitService.updateMerchantLimit merchantLimitUpdateReq={}, resp={}", JSON.toJSONString(merchantLimitUpdateReq), JSON.toJSONString(updateGreenChannelUseLimit));

                Map<String, List<String>> mktResIdAndNbrMap = new HashMap<>(req.getMktResInstNbrs().size());
                mktResIdAndNbrMap.put(req.getMktResId(), req.getMktResInstNbrs());
                // step3 增加事件和批次
                BatchAndEventAddReq batchAndEventAddReq = new BatchAndEventAddReq();
                batchAndEventAddReq.setEventType(ResourceConst.EVENTTYPE.PUT_STORAGE.getCode());
                batchAndEventAddReq.setLanId(merchantDTOResultVO.getResultData().getLanId());
                batchAndEventAddReq.setRegionId(merchantDTOResultVO.getResultData().getCity());
                batchAndEventAddReq.setMktResIdAndNbrMap(mktResIdAndNbrMap);
                batchAndEventAddReq.setMerchantId(merchantId);
                batchAndEventAddReq.setDestStoreId(mktResStoreId);
                batchAndEventAddReq.setMktResStoreId(ResourceConst.NULL_STORE_ID);
                batchAndEventAddReq.setEventType(ResourceConst.EVENTTYPE.PUT_STORAGE.getCode());
                batchAndEventAddReq.setMktResInstNbrs(req.getMktResInstNbrs());
                batchAndEventAddReq.setCreateStaff(req.getCreateStaff());
                resourceBatchRecService.saveEventAndBatch(batchAndEventAddReq);
                log.info("RetailerResourceInstMarketServiceImpl.syncTerminal resourceBatchRecService.saveEventAndBatch req={},resp={}", JSON.toJSONString(batchAndEventAddReq));

                ResourceInstStoreDTO resourceInstStoreDTO = new ResourceInstStoreDTO();
                resourceInstStoreDTO.setMktResId(req.getMktResId());
                resourceInstStoreDTO.setMktResStoreId(mktResStoreId);
                resourceInstStoreDTO.setMerchantId(req.getMerchantId());
                resourceInstStoreDTO.setLanId(merchantDTOResultVO.getResultData().getLanId());
                // 出库类型，库存增加
                resourceInstStoreDTO.setQuantityAddFlag(true);
                resourceInstStoreDTO.setQuantity(Long.valueOf(req.getMktResInstNbrs().size()));
                int updateResInstStore = resourceInstStoreManager.updateResourceInstStore(resourceInstStoreDTO);
                if (updateResInstStore < 1) {
                    throw new RetailTipException(ResultCodeEnum.ERROR.getCode(), "库存没更新成功");
                }
                log.info("RetailerResourceInstMarketServiceImpl.confirmReciveNbr resourceInstStoreManager.updateResourceInstStore req={},resp={}", JSON.toJSONString(resourceInstStoreDTO), JSON.toJSONString(updateResInstStore));
            }

            return syncTerminalResultVO;
        }

    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResultVO delResourceInst(ResourceInstUpdateReq req) {
        // 获取仓库
        StoreGetStoreIdReq storeGetStoreIdReq = new StoreGetStoreIdReq();
        storeGetStoreIdReq.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
        storeGetStoreIdReq.setMerchantId(req.getMerchantId());
        String mktResStoreId = resouceStoreService.getStoreId(storeGetStoreIdReq);
        log.info("RetailerResourceInstServiceImpl.delResourceInst resouceStoreService.getStoreId req={},resp={}", JSON.toJSONString(storeGetStoreIdReq), mktResStoreId);

        req.setMktResStoreId(mktResStoreId);
        List<String> nbrList = req.getMktResInstNbrs();
        SynMktInstStatusSwapReq synMktInstStatusReq = new SynMktInstStatusSwapReq();
        ResultVO<MerchantDTO> merchantDTOResultVO = merchantService.getMerchantById(req.getMerchantId());
        if (!merchantDTOResultVO.isSuccess() || null == merchantDTOResultVO.getResultData()) {
            return ResultVO.error("商家获取失败");
        }

        // 先获取串码，用来记录库存及事件
        ResourceInstBatchReq resourceInstBatchReq = new ResourceInstBatchReq();
        resourceInstBatchReq.setMktResStoreId(mktResStoreId);
        resourceInstBatchReq.setMktResInstNbrs(nbrList);
        ResultVO<List<ResourceInstListPageResp>> instListVO = getBatch(resourceInstBatchReq);
        List<ResourceInstListPageResp> instList = instListVO.getResultData();
        Map<String, List<String>> mktResIdAndNbrMap = this.getMktResIdAndNbrMap(instList, CLASS_TYPE_1);

        synMktInstStatusReq.setLanId(merchantDTOResultVO.getResultData().getLanId());
        synMktInstStatusReq.setBarCode(String.join(",", nbrList));
        synMktInstStatusReq.setServiceCode(serviceCode);
        ResultVO synMktInstStatusVO = marketingResStoreService.synMktInstStatus(synMktInstStatusReq);
        if (!synMktInstStatusVO.isSuccess()) {
            return synMktInstStatusVO;
        }

        // 增加事件和批次
        EventAndDetailReq eventAndDetailReq = new EventAndDetailReq();
        BeanUtils.copyProperties(req, eventAndDetailReq);
        eventAndDetailReq.setRegionId(merchantDTOResultVO.getResultData().getCity());
        eventAndDetailReq.setLanId(merchantDTOResultVO.getResultData().getLanId());
        eventAndDetailReq.setCreateStaff(req.getUpdateStaff());
        eventAndDetailReq.setMktResStoreId(ResourceConst.NULL_STORE_ID);
        eventAndDetailReq.setDestStoreId(mktResStoreId);
        eventAndDetailReq.setMktResIdAndNbrMap(mktResIdAndNbrMap);
        ResultVO saveEventAndBatchVO = resouceEventService.saveResouceEventAndDetail(eventAndDetailReq);
        log.info("RetailerResourceInstMarketServiceImpl.delResourceInst resouceEventService.saveResouceEventAndDetail req={},resp={}", JSON.toJSONString(eventAndDetailReq), JSON.toJSONString(saveEventAndBatchVO));

        // step 4:修改库存(出库)
        for (Map.Entry<String, List<String>> entry : mktResIdAndNbrMap.entrySet()) {
            ResourceInstStoreDTO resourceInstStoreDTO = new ResourceInstStoreDTO();
            resourceInstStoreDTO.setMktResId(entry.getKey());
            resourceInstStoreDTO.setMktResStoreId(mktResStoreId);
            resourceInstStoreDTO.setMerchantId(req.getMerchantId());
            resourceInstStoreDTO.setLanId(instList.get(0).getLanId());
            // 出库类型，库存减少
            resourceInstStoreDTO.setQuantityAddFlag(false);
            resourceInstStoreDTO.setQuantity(Long.valueOf(entry.getValue().size()));
            int updateResInstStore = resourceInstStoreManager.updateResourceInstStore(resourceInstStoreDTO);
            if (updateResInstStore < 1) {
                throw new RetailTipException(ResultCodeEnum.ERROR.getCode(), "库存没更新成功");
            }
            log.info("RetailerResourceInstMarketServiceImpl.confirmReciveNbr resourceInstStoreManager.updateResourceInstStore req={},resp={}", JSON.toJSONString(resourceInstStoreDTO), JSON.toJSONString(updateResInstStore));
        }

        return synMktInstStatusVO;
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResultVO confirmReciveNbr(ConfirmReciveNbrReq req) {
        AdminResourceInstDelReq delReq = new AdminResourceInstDelReq();

        // step1 申请单状态改为完成
        String resReqId = req.getResReqId();
        ResourceRequestItemQueryReq resourceRequestReq = new ResourceRequestItemQueryReq();
        resourceRequestReq.setMktResReqId(resReqId);
        ResultVO<ResourceRequestResp> resourceRequestRespVO = requestService.queryResourceRequest(resourceRequestReq);
        String statusCdReviewed = ResourceConst.MKTRESSTATE.REVIEWED.getCode();
        log.info("RetailerResourceInstMarketServiceImpl.confirmRefuseNbr requestService.queryResourceRequest req={},resp={}", JSON.toJSONString(resourceRequestReq),JSON.toJSONString(resourceRequestRespVO));
        Boolean statusNotRight = !resourceRequestRespVO.isSuccess() || resourceRequestRespVO.getResultData() == null || !statusCdReviewed.equals(resourceRequestRespVO.getResultData().getStatusCd());
        if (statusNotRight) {
            return ResultVO.error(ResultCodeEnum.ERROR.getCode(), "申请单状态不正确");
        }

        ResourceRequestResp resourceRequestResp = resourceRequestRespVO.getResultData();
        ResourceRequestUpdateReq reqUpdate = new ResourceRequestUpdateReq();
        reqUpdate.setMktResReqId(resReqId);
        reqUpdate.setStatusCd(ResourceConst.MKTRESSTATE.DONE.getCode());
        ResultVO<Boolean> updateResourceRequestStateVO =  requestService.updateResourceRequestState(reqUpdate);
        log.info("RetailerResourceInstMarketServiceImpl.confirmRefuseNbr requestService.queryResourceRequest req={},resp={}", JSON.toJSONString(reqUpdate),JSON.toJSONString(updateResourceRequestStateVO));

        // step1 找到串码明细
        ResourceReqDetailQueryReq queryReq = new ResourceReqDetailQueryReq();
        queryReq.setMktResReqId(req.getResReqId());
        List<ResourceReqDetailDTO> list = resourceReqDetailManager.listDetail(queryReq);
        log.info("RetailerResourceInstMarketServiceImpl.confirmRefuseNbr resourceReqDetailManager.listDetail req={},resp={}", JSON.toJSONString(queryReq),JSON.toJSONString(list));
        if (null == list || list.isEmpty()) {
            return ResultVO.error("没有查找到申请单");
        }

        // step3 领用方入库
        ResourceInstAddReq addReq = new ResourceInstAddReq();
        List<String> mktResInstNbrs = list.stream().map(ResourceReqDetailDTO::getMktResInstNbr).collect(Collectors.toList());
        ResourceReqDetailDTO detailDTO = list.get(0);

        //根据申请单表保存的目标仓库和申请单明细找到对应的串码及商家信息
        addReq.setMktResInstNbrs(mktResInstNbrs);
        addReq.setStatusCd(ResourceConst.STATUSCD.AVAILABLE.getCode());
        addReq.setSourceType(ResourceConst.SOURCE_TYPE.RETAILER.getCode());
        addReq.setStorageType(ResourceConst.STORAGETYPE.ALLOCATION_AND_WAREHOUSING.getCode());
        addReq.setMerchantType(PartnerConst.MerchantTypeEnum.MANUFACTURER.getType());
        addReq.setCreateStaff(req.getUpdateStaff());
        String merchantId = req.getMerchantId();
        addReq.setMerchantId(merchantId);
        addReq.setLanId(detailDTO.getLanId());
        addReq.setMktResId(detailDTO.getMktResId());
        addReq.setRegionId(detailDTO.getRegionId());
        addReq.setMktResStoreId(resourceRequestResp.getMktResStoreId());
        ResultVO syncTerminalVO = resourceInstService.syncTerminal(addReq);
        log.info("RetailerResourceInstMarketServiceImpl.confirmRefuseNbr resourceInstService.syncTerminal req={},resp={}", JSON.toJSONString(addReq),JSON.toJSONString(syncTerminalVO));
        if (syncTerminalVO.isSuccess()) {
            Map<String, List<String>> mktResIdAndNbrMap = this.getMktResIdAndNbrMap(list, CLASS_TYPE_3);
            // 增加事件和批次
            BatchAndEventAddReq batchAndEventAddReq = new BatchAndEventAddReq();
            BeanUtils.copyProperties(req, batchAndEventAddReq);
            batchAndEventAddReq.setCreateStaff(req.getUpdateStaff());
            batchAndEventAddReq.setMktResInstNbrs(mktResInstNbrs);
            batchAndEventAddReq.setLanId(list.get(0).getLanId());
            batchAndEventAddReq.setMktResIdAndNbrMap(mktResIdAndNbrMap);
            batchAndEventAddReq.setRegionId(list.get(0).getRegionId());
            batchAndEventAddReq.setEventType(ResourceConst.EVENTTYPE.ALLOT.getCode());
            batchAndEventAddReq.setObjType(ResourceConst.EVENT_OBJTYPE.PUT_STORAGE.getCode());
            batchAndEventAddReq.setObjId(req.getResReqId());
            batchAndEventAddReq.setMktResStoreId(detailDTO.getMktResStoreId());
            batchAndEventAddReq.setDestStoreId(detailDTO.getDestStoreId());
            resourceBatchRecService.saveEventAndBatch(batchAndEventAddReq);
            log.info("RetailerResourceInstMarketServiceImpl.confirmRefuseNbr resourceBatchRecService.saveEventAndBatch req={}", JSON.toJSONString(batchAndEventAddReq));

            // step 4:修改库存(出库)
            for (Map.Entry<String, List<String>> entry : mktResIdAndNbrMap.entrySet()) {
                ResourceInstStoreDTO resourceInstStoreDTO = new ResourceInstStoreDTO();
                resourceInstStoreDTO.setMktResId(entry.getKey());
                resourceInstStoreDTO.setMktResStoreId(resourceRequestResp.getDestStoreId());
                resourceInstStoreDTO.setMerchantId(merchantId);
                resourceInstStoreDTO.setLanId(detailDTO.getLanId());
                // 出库类型，库存增加
                resourceInstStoreDTO.setQuantityAddFlag(true);
                resourceInstStoreDTO.setQuantity(Long.valueOf(entry.getValue().size()));
                int updateResInstStore = resourceInstStoreManager.updateResourceInstStore(resourceInstStoreDTO);
                if (updateResInstStore < 1) {
                    throw new RetailTipException(ResultCodeEnum.ERROR.getCode(), "库存没更新成功");
                }
                log.info("RetailerResourceInstMarketServiceImpl.confirmReciveNbr resourceInstStoreManager.updateResourceInstStore req={},resp={}", JSON.toJSONString(resourceInstStoreDTO), JSON.toJSONString(updateResInstStore));
            }
        }

        return syncTerminalVO;
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResultVO confirmRefuseNbr(ConfirmReciveNbrReq req) {
        // step1 申请单修改状态
        ResourceRequestItemQueryReq resourceRequestReq = new ResourceRequestItemQueryReq();
        resourceRequestReq.setMktResReqId(req.getResReqId());
        ResultVO<ResourceRequestResp> resourceRequestRespVO = requestService.queryResourceRequest(resourceRequestReq);
        String statusCdReviewed = ResourceConst.MKTRESSTATE.REVIEWED.getCode();
        Boolean statusNotRight = !resourceRequestRespVO.isSuccess() || resourceRequestRespVO.getResultData() == null || !statusCdReviewed.equals(resourceRequestRespVO.getResultData().getStatusCd());
        if (statusNotRight) {
            return ResultVO.error(ResultCodeEnum.ERROR.getCode(), "申请单状态不正确");
        }
        ResourceRequestResp resourceRequestResp = resourceRequestRespVO.getResultData();
        ResourceRequestUpdateReq reqUpdate = new ResourceRequestUpdateReq();
        reqUpdate.setMktResReqId(req.getResReqId());
        reqUpdate.setStatusCd(ResourceConst.MKTRESSTATE.CANCEL.getCode());
        ResultVO<Boolean> updateResourceRequestStateVO = requestService.updateResourceRequestState(reqUpdate);
        log.info("RetailerResourceInstMarketServiceImpl.confirmRefuseNbr requestService.updateResourceRequestState req={},resp={}", JSON.toJSONString(reqUpdate), JSON.toJSONString(updateResourceRequestStateVO));

        //step2 根据申请单表保存的源仓库和申请单明细找到对应的串码
        ResourceReqDetailQueryReq queryReq = new ResourceReqDetailQueryReq();
        queryReq.setMktResReqId(req.getResReqId());
        List<ResourceReqDetailDTO> list = resourceReqDetailManager.listDetail(queryReq);
        log.info("RetailerResourceInstMarketServiceImpl.confirmRefuseNbr resourceReqDetailManager.listDetail req={},resp={}", JSON.toJSONString(queryReq), JSON.toJSONString(list));

        if (null == list || list.isEmpty()) {
            return ResultVO.error("没有查找到申请单");
        }

        // step3 源仓库串码入库
        List<SyncTerminalItemSwapReq> syncTerminalItemSwapReqList = new ArrayList<SyncTerminalItemSwapReq>(list.size());
        List<String> mktResInstNbrs = new ArrayList<String>(list.size());
        for (ResourceReqDetailDTO dto : list) {
            ProductGetByIdReq productReq = new ProductGetByIdReq();
            productReq.setProductId(dto.getMktResId());
            ResultVO<ProductForResourceResp> productRespResultVO = productService.getProductForResource(productReq);
            String sn = "";
            if (productRespResultVO.isSuccess() && productRespResultVO.getResultData() != null) {
                sn = productRespResultVO.getResultData().getSn();
            }
            SyncTerminalItemSwapReq syncTerminalItemSwapReq = new SyncTerminalItemSwapReq();
            syncTerminalItemSwapReq.setBarCode(dto.getMktResInstNbr());
            syncTerminalItemSwapReq.setStoreId(resourceRequestResp.getDestStoreId());
            syncTerminalItemSwapReq.setLanId(dto.getLanId());
            syncTerminalItemSwapReq.setDirectPrice("");
            syncTerminalItemSwapReq.setPurchaseType(ResourceConst.PURCHASE_TYPE.PURCHASE_TYPE_12.getCode());
            syncTerminalItemSwapReq.setProductCode(sn);
            MerchantInfByNbrModel merchantInfByNbrModel = resourceInstService.qryMerchantInfoByNbr(dto.getMktResInstNbr());
            syncTerminalItemSwapReq.setProvSupplyId(merchantInfByNbrModel.getProvSupplyId());
            syncTerminalItemSwapReq.setProvSupplyName(merchantInfByNbrModel.getProvSupplyName());
            syncTerminalItemSwapReq.setCitySupplyId(merchantInfByNbrModel.getCitySupplyId());
            syncTerminalItemSwapReq.setCitySupplyName(merchantInfByNbrModel.getCitySupplyName());
            mktResInstNbrs.add(dto.getMktResInstNbr());
            syncTerminalItemSwapReqList.add(syncTerminalItemSwapReq);
        }
        SyncTerminalSwapReq syncTerminalSwapReq = new SyncTerminalSwapReq();
        syncTerminalSwapReq.setMktResList(syncTerminalItemSwapReqList);
        ResultVO syncTerminalVO = marketingResStoreService.syncTerminal(syncTerminalSwapReq);
        log.info("RetailerResourceInstMarketServiceImpl.confirmRefuseNbr marketingResStoreService.syncTerminal req={},resp={}", JSON.toJSONString(syncTerminalSwapReq), JSON.toJSONString(syncTerminalVO));
        if (syncTerminalVO.isSuccess()) {
            Map<String, List<String>> mktResIdAndNbrMap = this.getMktResIdAndNbrMap(list, CLASS_TYPE_3);
            // 增加事件和批次
            BatchAndEventAddReq batchAndEventAddReq = new BatchAndEventAddReq();
            BeanUtils.copyProperties(req, batchAndEventAddReq);
            batchAndEventAddReq.setCreateStaff(req.getUpdateStaff());
            batchAndEventAddReq.setMktResInstNbrs(mktResInstNbrs);
            batchAndEventAddReq.setLanId(list.get(0).getLanId());
            batchAndEventAddReq.setMktResIdAndNbrMap(mktResIdAndNbrMap);
            batchAndEventAddReq.setRegionId(list.get(0).getRegionId());
            batchAndEventAddReq.setEventType(ResourceConst.EVENTTYPE.ALLOT.getCode());
            batchAndEventAddReq.setObjType(ResourceConst.EVENT_OBJTYPE.PUT_STORAGE.getCode());
            batchAndEventAddReq.setObjId(req.getResReqId());
            resourceBatchRecService.saveEventAndBatch(batchAndEventAddReq);
            log.info("RetailerResourceInstMarketServiceImpl.confirmRefuseNbr resourceBatchRecService.saveEventAndBatch req={}", JSON.toJSONString(batchAndEventAddReq));
        }

        return syncTerminalVO;
    }

    @Override
    public ResultVO<Page<ResourceInstListPageResp>> listResourceInst(ResourceInstListPageReq req) {
        Page<ResourceInstListPageResp> page = new Page<ResourceInstListPageResp>();
        List<QryMktInstInfoByConditionItemSwapResp> qryMktInstInfoList = new ArrayList<>();
        // 组装请求参数,参数为空传空字符串(接口要求)
        QryMktInstInfoByConditionSwapReq qryMktInstInfoByConditionReq = new QryMktInstInfoByConditionSwapReq();
        qryMktInstInfoByConditionReq.setInstoreBeginTime(req.getCreateTimeStart());
        qryMktInstInfoByConditionReq.setInstoreEndTime(req.getCreateTimeEnd());
        String mktResId = CollectionUtils.isEmpty(req.getMktResIds()) ? null : req.getMktResIds().get(0);
        qryMktInstInfoByConditionReq.setMktResId(mktResId);
        qryMktInstInfoByConditionReq.setMktResName(req.getProductName());
        qryMktInstInfoByConditionReq.setMktResNbr(req.getSn());
        String mktResStoreId = CollectionUtils.isEmpty(req.getMktResStoreIds()) ? null : req.getMktResStoreIds().get(0);
        qryMktInstInfoByConditionReq.setStoreId(mktResStoreId);
        qryMktInstInfoByConditionReq.setStatusCd(req.getStatusCd());
        Integer pageNo = req.getPageNo() == null ? 1 : req.getPageNo();
        qryMktInstInfoByConditionReq.setPageIndex(String.valueOf(pageNo));
        Integer pageSize = req.getPageSize() == null ? 5 : req.getPageSize();
        qryMktInstInfoByConditionReq.setPageSize(String.valueOf(pageSize));
        qryMktInstInfoByConditionReq.setPageIndex(String.valueOf(req.getPageNo()));
        qryMktInstInfoByConditionReq.setPageSize(String.valueOf(req.getPageSize()));
        // 不支持多串码查询，只能多次查询组装返回
        if (!CollectionUtils.isEmpty(req.getMktResInstNbrs())) {
            for (String nbr : req.getMktResInstNbrs()) {
                qryMktInstInfoByConditionReq.setBarCode(nbr);
                ResultVO<QueryMarkResQueryResultsSwapResp<QryMktInstInfoByConditionItemSwapResp>> queryMarkResQueryResultsRespResultVO = marketingResStoreService.qryMktInstInfoByCondition(qryMktInstInfoByConditionReq);
                log.info("RetailerResourceInstMarketServiceImpl.listResourceInst marketingResStoreService.qryMktInstInfoByCondition req={}, resp={}", JSON.toJSONString(qryMktInstInfoByConditionReq), JSON.toJSONString(queryMarkResQueryResultsRespResultVO));
                if (!queryMarkResQueryResultsRespResultVO.isSuccess() || queryMarkResQueryResultsRespResultVO.getResultData() == null || CollectionUtils.isEmpty(queryMarkResQueryResultsRespResultVO.getResultData().getQueryInfo())) {
                    return ResultVO.success(new Page<ResourceInstListPageResp>());
                }
                qryMktInstInfoList.addAll(queryMarkResQueryResultsRespResultVO.getResultData().getQueryInfo());
            }
        }else{
            ResultVO<QueryMarkResQueryResultsSwapResp<QryMktInstInfoByConditionItemSwapResp>> queryMarkResQueryResultsRespResultVO = marketingResStoreService.qryMktInstInfoByCondition(qryMktInstInfoByConditionReq);
            log.info("RetailerResourceInstMarketServiceImpl.listResourceInst marketingResStoreService.qryMktInstInfoByCondition req={}, resp={}", JSON.toJSONString(qryMktInstInfoByConditionReq), JSON.toJSONString(queryMarkResQueryResultsRespResultVO));
            if (!queryMarkResQueryResultsRespResultVO.isSuccess() || queryMarkResQueryResultsRespResultVO.getResultData() == null || CollectionUtils.isEmpty(queryMarkResQueryResultsRespResultVO.getResultData().getQueryInfo())) {
                return ResultVO.success(new Page<ResourceInstListPageResp>());
            }
            qryMktInstInfoList = queryMarkResQueryResultsRespResultVO.getResultData().getQueryInfo();
        }


        // 组装返回数据
        List<ResourceInstListPageResp> resourceInstListRespListPage = translateNbrInst(qryMktInstInfoList);
        page.setRecords(resourceInstListRespListPage);
        return ResultVO.success(page);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResultVO retreatStorageResourceInst(RetreatStorageReq req) {
        return null;
    }

    @Override
    public ResultVO<List<ResourceInstListPageResp>> getBatch(ResourceInstBatchReq req) {
        List<ResourceInstListPageResp> resourceInstListRespListPage = new ArrayList<ResourceInstListPageResp>(5);
        List<String> nbrs = req.getMktResInstNbrs();
        ResourceInstListPageReq resourceInstListPageReq = new ResourceInstListPageReq();
        List<String> mktResStoreIds = Lists.newArrayList(req.getMktResStoreId());
        resourceInstListPageReq.setMktResStoreIds(mktResStoreIds);
        resourceInstListPageReq.setMktResInstNbrs(nbrs);
        ResultVO<Page<ResourceInstListPageResp>> listRespVO = this.listResourceInst(resourceInstListPageReq);
        log.info("RetailerResourceInstMarketServiceImpl.getBatch listResourceInst req={}, resp={}", JSON.toJSONString(resourceInstListPageReq), JSON.toJSONString(listRespVO.getResultData().getRecords()));

        if (!listRespVO.isSuccess() || listRespVO.getResultData() == null || CollectionUtils.isEmpty(listRespVO.getResultData().getRecords())){
            return ResultVO.success(new ArrayList<ResourceInstListPageResp>());
        }
        List<ResourceInstListPageResp> listResp = listRespVO.getResultData().getRecords();
        for(ResourceInstListPageResp resp : listResp){
            ResourceInstListPageResp dto = new ResourceInstListPageResp();
            BeanUtils.copyProperties(resp, dto);
            ProductResourceInstGetReq productResourceInstGetReq = new ProductResourceInstGetReq();
            String sn = StringUtils.isBlank(resp.getSn()) ? "" : resp.getSn();
            productResourceInstGetReq.setSn(sn);
            // BSS3.0没有返回产品id，只返回了sn，根据sn查出productId用于权限校验
            String productId = "";
            ResultVO<List<ProductResourceResp>> getProductResourceVO = productService.getProductResource(productResourceInstGetReq);
            log.info("RetailerResourceInstMarketServiceImpl.allocateResourceInst productService.getBatch sn={},resp={}", sn, JSON.toJSONString(getProductResourceVO));
            if (getProductResourceVO.isSuccess() && !CollectionUtils.isEmpty(getProductResourceVO.getResultData())) {
                productId = getProductResourceVO.getResultData().get(0).getProductId();
            }
            dto.setMktResId(productId);
            resourceInstListRespListPage.add(dto);
        }
        return ResultVO.success(resourceInstListRespListPage);
    }

    /**
     * 生申请单名称
     *
     * @param productName
     * @return
     */
    private String createReqName(String productName, String reqType) {
        StringBuilder b = new StringBuilder();
        b.append(ResourceConst.REQTYPE.getReqTypeName(reqType));
        b.append("-");
        b.append(productName);
        return b.toString();
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResultVO allocateResourceInst(RetailerResourceInstAllocateReq req) {

        //step 1: 校验是否需要审核单
        //根据仓库查使用对象
        ResultVO<MerchantDTO> destMerchantVO = resouceStoreService.getMerchantByStore(req.getDestStoreId());
        log.info("RetailerResourceInstMarketServiceImpl.allocateResourceInst resouceStoreService.getMerchantByStore req={},resp={}", req.getDestStoreId(), JSON.toJSONString(destMerchantVO));
        MerchantDTO destMerchantDTO = destMerchantVO.getResultData();
        if (null == destMerchantDTO) {
            return ResultVO.error("商家获取失败");
        }
        ResultVO<MerchantDTO> sorctMerchantVO = resouceStoreService.getMerchantByStore(req.getMktResStoreId());
        log.info("RetailerResourceInstMarketServiceImpl.allocateResourceInst resouceStoreService.getMerchantByStore req={},resp={}", req.getMktResStoreId(), JSON.toJSONString(sorctMerchantVO));
        MerchantDTO sourceMerchantDTO = sorctMerchantVO.getResultData();
        if (null == sourceMerchantDTO) {
            return ResultVO.error("商家获取失败");
        }
        String requestStatusCd = ResourceConst.MKTRESSTATE.REVIEWED.getCode();
        String successMessage = ResourceConst.ALLOCATE_SUCESS_MSG;
        List<String> mktResInstNbrs = req.getMktResInstNbrs();
        String auditType = validAllocateNbr(sourceMerchantDTO, destMerchantDTO, mktResInstNbrs);
        String reqCode = resourceInstManager.getPrimaryKey();
        if (ResourceConst.ALLOCATE_AUDIT_TYPE.ALLOCATE_AUDIT_TYPE_0.getCode().equals(auditType)) {
            return ResultVO.error("不能调拨，请检查调拨串码和目标仓库");
        }else if(ResourceConst.ALLOCATE_AUDIT_TYPE.ALLOCATE_AUDIT_TYPE_2.getCode().equals(auditType)){
            requestStatusCd = ResourceConst.MKTRESSTATE.PROCESSING.getCode();
            successMessage = ResourceConst.ALLOCATE_AUDITING_MSG + reqCode;
        }
        // 此处应该调用BSS3.0接口查
        ResourceInstBatchReq resourceInstBatchReq = new ResourceInstBatchReq();
        resourceInstBatchReq.setMktResInstNbrs(req.getMktResInstNbrs());
        String mktResStoreId = req.getMktResStoreId();
        String destStoreId = req.getDestStoreId();
        resourceInstBatchReq.setMktResStoreId(mktResStoreId);
        ResultVO<List<ResourceInstListPageResp>> resourceInstListRespVO = this.getBatch(resourceInstBatchReq);
        if (!resourceInstListRespVO.isSuccess() || CollectionUtils.isEmpty(resourceInstListRespVO.getResultData())) {
            return resourceInstListRespVO;
        }
        List<ResourceInstListPageResp> resourceInstDTOList = resourceInstListRespVO.getResultData();
        List<ResourceRequestAddReq.ResourceRequestInst> resourceRequestInsts = new ArrayList<>(resourceInstDTOList.size());
        for (ResourceInstListPageResp resourceInstDTO : resourceInstDTOList) {
            ResourceRequestAddReq.ResourceRequestInst resourceRequestInst = new ResourceRequestAddReq.ResourceRequestInst();
            BeanUtils.copyProperties(resourceInstDTO, resourceRequestInst);
            resourceRequestInsts.add(resourceRequestInst);
        }
        //新增申请单
        ResourceRequestAddReq resourceRequestAddReq = new ResourceRequestAddReq();
        resourceRequestAddReq.setReqName("调拨申请单");
        resourceRequestAddReq.setReqType(ResourceConst.REQTYPE.ALLOCATE_APPLYFOR.getCode());
        resourceRequestAddReq.setMktResStoreId(mktResStoreId);
        resourceRequestAddReq.setDestStoreId(destStoreId);
        resourceRequestAddReq.setCreateStaff(req.getCreateStaff());
        resourceRequestAddReq.setStatusCd(requestStatusCd);
        resourceRequestAddReq.setChngType(ResourceConst.PUT_IN_STOAGE);
        resourceRequestAddReq.setInstList(resourceRequestInsts);
        resourceRequestAddReq.setLanId(destMerchantDTO.getLanId());
        resourceRequestAddReq.setRegionId(destMerchantDTO.getCity());
        resourceRequestAddReq.setReqCode(reqCode);
        ResultVO<String> resultVOInsertResReq = requestService.insertResourceRequest(resourceRequestAddReq);
        log.info("RetailerResourceInstMarketServiceImpl.allocateResourceInst resourceRequestService.insertResourceRequest req={},resp={}", JSON.toJSONString(resourceRequestAddReq), JSON.toJSONString(resultVOInsertResReq));
        if (!resultVOInsertResReq.isSuccess()) {
            return resultVOInsertResReq;
        }
        // step4 如果不需要审核则发起流程目标仓库处理人，由目标仓库处理人决定是否接受
        String uuid = resourceInstManager.getPrimaryKey();
        ProcessStartReq processStartDTO = new ProcessStartReq();
        processStartDTO.setTitle("调拨审批流程");
        processStartDTO.setApplyUserId(req.getCreateStaff());
        processStartDTO.setProcessId(ResourceConst.ALLOCATE_WORK_FLOW_INST_2);
        processStartDTO.setFormId(resultVOInsertResReq.getResultData());
        processStartDTO.setTaskSubType(WorkFlowConst.TASK_SUB_TYPE.TASK_SUB_TYPE_2040.getTaskSubType());
        // 指定下一环节处理人
        HandlerUser user = new HandlerUser();
        user.setHandlerUserId(destMerchantDTO.getUserId());
        user.setHandlerUserName(destMerchantDTO.getMerchantName());
        List<HandlerUser> uerList = new ArrayList<HandlerUser>(1);
        processStartDTO.setNextHandlerUser(uerList);
        ResultVO taskServiceRV = taskService.startProcess(processStartDTO);
        log.info("RetailerResourceInstMarketServiceImpl.allocateResourceInst taskService.startProcess req={},resp={}", JSON.toJSONString(processStartDTO), JSON.toJSONString(taskServiceRV));
        if (!taskServiceRV.getResultCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
            throw new RetailTipException(ResultCodeEnum.ERROR.getCode(), "启动工作流失败");
        }
        // 删除源串码
        String nbrs = StringUtils.join(mktResInstNbrs, ",");
        SynMktInstStatusSwapReq synMktInstStatusSwapReq = new SynMktInstStatusSwapReq();
        synMktInstStatusSwapReq.setLanId(req.getLanId());
        synMktInstStatusSwapReq.setBarCode(nbrs);
        synMktInstStatusSwapReq.setServiceCode(serviceCode);
        ResultVO synMktInstStatusVO = marketingResStoreService.synMktInstStatus(synMktInstStatusSwapReq);
        log.info("RetailerResourceInstMarketServiceImpl.allocateResourceInst marketingResStoreService.synMktInstStatus req={},resp={}", JSON.toJSONString(synMktInstStatusSwapReq), JSON.toJSONString(synMktInstStatusVO));
        if (!synMktInstStatusVO.isSuccess()) {
            return synMktInstStatusVO;
        }
        // 增加事件
        Map<String, List<String>> mktResIdAndNbrMap = this.getMktResIdAndNbrMap(resourceInstDTOList, CLASS_TYPE_1);
        EventAndDetailReq eventAndDetailReq = new EventAndDetailReq();
        BeanUtils.copyProperties(req, eventAndDetailReq);
        eventAndDetailReq.setRegionId(sourceMerchantDTO.getCity());
        eventAndDetailReq.setMerchantId(sourceMerchantDTO.getMerchantId());
        eventAndDetailReq.setMktResStoreId(req.getMktResStoreId());
        eventAndDetailReq.setDestStoreId(req.getDestStoreId());
        eventAndDetailReq.setMktResIdAndNbrMap(mktResIdAndNbrMap);
        eventAndDetailReq.setObjId(resultVOInsertResReq.getResultData());
        eventAndDetailReq.setObjType(ResourceConst.EVENT_OBJTYPE.PUT_STORAGE.getCode());
        eventAndDetailReq.setEventType(ResourceConst.EVENTTYPE.ALLOT.getCode());
        ResultVO saveEventAndBatchVO =  resouceEventService.saveResouceEventAndDetail(eventAndDetailReq);
        log.info("RetailerResourceInstMarketServiceImpl.syncTerminal resouceEventService.saveResouceEventAndDetail req={},resp={}", JSON.toJSONString(eventAndDetailReq), JSON.toJSONString(saveEventAndBatchVO));
        // step 4:修改库存(出库)
        for (Map.Entry<String, List<String>> entry : mktResIdAndNbrMap.entrySet()) {
            ResourceInstStoreDTO resourceInstStoreDTO = new ResourceInstStoreDTO();
            resourceInstStoreDTO.setMktResId(entry.getKey());
            resourceInstStoreDTO.setMktResStoreId(mktResStoreId);
            resourceInstStoreDTO.setMerchantId(sourceMerchantDTO.getMerchantId());
            resourceInstStoreDTO.setLanId(sourceMerchantDTO.getLanId());
            resourceInstStoreDTO.setRegionId(sourceMerchantDTO.getCity());
            // 出库类型，库存减少
            resourceInstStoreDTO.setQuantityAddFlag(false);
            resourceInstStoreDTO.setQuantity(Long.valueOf(entry.getValue().size()));
            int updateResInstStore = resourceInstStoreManager.updateResourceInstStore(resourceInstStoreDTO);
            log.info("RetailerResourceInstMarketServiceImpl.allocateResourceInst resourceInstStoreManager.updateResourceInstStore req={},resp={}", JSON.toJSONString(resourceInstStoreDTO), JSON.toJSONString(updateResInstStore));
            if (updateResInstStore < 1) {
                throw new RetailTipException(ResultCodeEnum.ERROR.getCode(), "库存没更新成功");
            }
        }
        return ResultVO.success(successMessage);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResultVO pickResourceInst(ResourceInstPickupReq req) {
        ResourceInstAddResp resourceInstAddResp = new ResourceInstAddResp();
        // 先检查零售商所属十四个地市中的一个是否存在
        ResultVO<List<OrganizationRegionResp>> organizationIdVO = organizationService.queryRegionOrganizationId();
        log.info("RetailerResourceInstMarketServiceImpl.pickResourceInst organizationService.getStoreId queryRegionOrganizationId={}", JSON.toJSONString(organizationIdVO));
        if (!organizationIdVO.isSuccess() || CollectionUtils.isEmpty(organizationIdVO.getResultData())) {
            return organizationIdVO;
        }
        String lanId = req.getLanId();
        List<OrganizationRegionResp> sameLanIdOrganization = organizationIdVO.getResultData().stream().filter(t -> lanId.equals(t.getLanId())).collect(Collectors.toList());
        StoreGetStoreIdReq storeGetStoreIdReq = new StoreGetStoreIdReq();
        storeGetStoreIdReq.setMerchantId(sameLanIdOrganization.get(0).getOrgId());
        storeGetStoreIdReq.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
        String merchantStoreId = resouceStoreService.getStoreId(storeGetStoreIdReq);
        log.info("RetailerResourceInstMarketServiceImpl.pickResourceInst resouceStoreService.getStoreId merchantStoreId={}", merchantStoreId);

        ResourceInstListReq resourceInstListReq = new ResourceInstListReq();
        resourceInstListReq.setMktResStoreId(merchantStoreId);
        resourceInstListReq.setMktResInstNbrs(req.getMktResInstNbrs());
        resourceInstListReq.setStatusCd(ResourceConst.STATUSCD.AVAILABLE.getCode());
        ResultVO<List<ResourceInstListResp>> merchantNbrInstVO = resourceInstService.listResourceInst(resourceInstListReq);
        if (!merchantNbrInstVO.isSuccess() || CollectionUtils.isEmpty(merchantNbrInstVO.getResultData())) {
            resourceInstAddResp.setPutInFailNbrs(req.getMktResInstNbrs());
            return ResultVO.success("源仓库中不存在", resourceInstAddResp);
        }
        StoreGetStoreIdReq storePageReq = new StoreGetStoreIdReq();
        storePageReq.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
        storePageReq.setMerchantId(req.getMerchantId());
        String storeId = resouceStoreService.getStoreId(storePageReq);
        log.info("RetailerResourceInstMarketServiceImpl.syncTerminal resouceStoreService.getStoreId merchantId={},storeId={}", req.getMerchantId(), storeId);
        if (StringUtils.isBlank(storeId)) {
            return ResultVO.error(constant.getNoStoreMsg());
        }

        List<ResourceInstListResp> merchantNbrInst = merchantNbrInstVO.getResultData();
        Map<String, List<ResourceInstListResp>> map = merchantNbrInst.stream().collect(Collectors.groupingBy(t -> t.getMktResId()));
        List<String> mktResInstNbrList = Lists.newArrayList();
        Map<String, List<String>> mktResIdAndNbrMap = this.getMktResIdAndNbrMap(merchantNbrInst, CLASS_TYPE_2);
        for (Map.Entry<String, List<String>> entry : mktResIdAndNbrMap.entrySet()) {
            ProductGetByIdReq productReq = new ProductGetByIdReq();
            productReq.setProductId(entry.getKey());
            ResultVO<ProductForResourceResp> productRespResultVO = productService.getProductForResource(productReq);
            log.info("RetailerResourceInstMarketServiceImpl.syncTerminal productService.getProductForResource req={},resp={}", JSON.toJSONString(productReq), JSON.toJSONString(productRespResultVO));
            String isFixedLine = "";
            if (productRespResultVO.isSuccess() && productRespResultVO.getResultData() != null) {
                isFixedLine = productRespResultVO.getResultData().getIsFixedLine();
            }
            if (!ResourceConst.CONSTANT_YES.equals(isFixedLine)) {
                ResourceInstAddReq resourceInstAddReq = new ResourceInstAddReq();
                resourceInstAddReq.setMerchantId(req.getMerchantId());
                resourceInstAddReq.setLanId(lanId);
                resourceInstAddReq.setMktResInstNbrs(entry.getValue());
                resourceInstAddReq.setMktResId(entry.getKey());
                resourceInstAddReq.setCreateStaff(req.getUpdateStaff());
                resourceInstAddReq.setMktResStoreId(storeId);
                ResultVO ebuyTerminalVO = resourceInstService.syncTerminal(resourceInstAddReq);
                if (!ebuyTerminalVO.isSuccess()) {
                    return ebuyTerminalVO;
                }
            } else {
                mktResInstNbrList.addAll(entry.getValue());
            }
        }
        if (!CollectionUtils.isEmpty(mktResInstNbrList)) {
            ResultVO resultVO = saveEebuyTerminal(map, mktResInstNbrList, lanId, storeId);
            if (!resultVO.isSuccess()) {
                return resultVO;
            }
        }
        BatchAndEventAddReq batchAndEventAddReq = new BatchAndEventAddReq();
        batchAndEventAddReq.setEventType(ResourceConst.EVENTTYPE.PUT_STORAGE.getCode());
        batchAndEventAddReq.setLanId(lanId);
        batchAndEventAddReq.setMktResIdAndNbrMap(mktResIdAndNbrMap);
        batchAndEventAddReq.setMerchantId(req.getMerchantId());
        batchAndEventAddReq.setDestStoreId(storeId);
        batchAndEventAddReq.setMktResStoreId(ResourceConst.NULL_STORE_ID);
        batchAndEventAddReq.setEventType(ResourceConst.EVENTTYPE.PUT_STORAGE.getCode());
        batchAndEventAddReq.setMktResInstNbrs(req.getMktResInstNbrs());
        batchAndEventAddReq.setCreateStaff(req.getUpdateStaff());
        resourceBatchRecService.saveEventAndBatch(batchAndEventAddReq);
        log.info("RetailerResourceInstMarketServiceImpl.syncTerminal resourceBatchRecService.saveEventAndBatch req={},resp={}", JSON.toJSONString(batchAndEventAddReq));
        return ResultVO.success();
    }

    private List<ResourceInstListPageResp> translateNbrInst(List<QryMktInstInfoByConditionItemSwapResp> qryMktInstInfoList){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<ResourceInstListPageResp> resourceInstListRespListPage = new ArrayList<>(qryMktInstInfoList.size());
        for (QryMktInstInfoByConditionItemSwapResp resp : qryMktInstInfoList) {
            ResourceInstListPageResp dto = new ResourceInstListPageResp();
            dto.setMktResInstNbr(resp.getBarCode());
            dto.setUnitName(resp.getMktResName());
            dto.setMktResStoreId(resp.getStoreId());
            dto.setMktResStoreName(resp.getStoreName());
            dto.setMktResInstType(resp.getCurrState());
            Double salesPrice = StringUtils.isNotBlank(resp.getPrice()) ? Double.valueOf(resp.getPrice()) : 0D;
            dto.setSalesPrice(salesPrice);
            try {
                String zeroDate = "0000-00-00 00:00:00";
                String statusDateStr = StringUtils.isBlank(resp.getStatusDate()) ? zeroDate : resp.getStatusDate();
                String createDateStr = StringUtils.isBlank(resp.getStatusDate()) ? zeroDate : resp.getStatusDate();
                Date statusDate = format.parse(statusDateStr);
                Date createDate = format.parse(createDateStr);

                dto.setStatusDate(statusDate);
                dto.setCreateDate(createDate);
            }catch (ParseException e){
                log.error(e.toString(), "时间转换错误");
            }
            dto.setStatusCd(resp.getState());
            dto.setMerchantCode(resp.getProviderCode());
            dto.setMerchantName(resp.getProviderName());
            dto.setSupplierCode(resp.getProvSupplyId());
            dto.setSupplierName(resp.getProvSupplyName());
            dto.setStorageType(resp.getPurchaseType());
            ProductResourceInstGetReq productResourceInstGetReq = new ProductResourceInstGetReq();
            String sn = StringUtils.isBlank(resp.getMktResNbr()) ? "" : resp.getMktResNbr();
            productResourceInstGetReq.setSn(sn);
            ResultVO<List<ProductResourceResp>> ProductResourceRespVO = productService.getProductResource(productResourceInstGetReq);
            log.info("RetailerResourceInstMarketServiceImpl.translateNbrInst productService.getProductResource req={}, resp={}", JSON.toJSONString(productResourceInstGetReq), JSON.toJSONString(ProductResourceRespVO));
            if (ProductResourceRespVO.isSuccess() && !CollectionUtils.isEmpty(ProductResourceRespVO.getResultData())) {
                List<ProductResourceResp> list = ProductResourceRespVO.getResultData();
                dto.setBrandName(list.get(0).getBrandName());
                dto.setTypeName(list.get(0).getTypeName());
            }
            dto.setSn(sn);
            BeanUtils.copyProperties(resp, dto);
            resourceInstListRespListPage.add(dto);
        }

        return resourceInstListRespListPage;
    }

    private Map<String, List<String>> getMktResIdAndNbrMap(List instList, String classType){
        Map<String, List<String>> mktResIdAndNbrMap = new HashMap<>();
        if (CLASS_TYPE_1.equals(classType)) {
            List<ResourceInstListPageResp> resourceInstList = instList;
            for (ResourceInstListPageResp resp : resourceInstList){
                if(mktResIdAndNbrMap.containsKey(resp.getMktResId())){
                    List<String> mktResIdList = mktResIdAndNbrMap.get(resp.getMktResId());
                    mktResIdList.add(resp.getMktResInstNbr());
                }else{
                    List<String> mktResIdList = new ArrayList<>();
                    mktResIdList.add(resp.getMktResInstNbr());
                    mktResIdAndNbrMap.put(resp.getMktResId(), mktResIdList);
                }
            }
        }else if (CLASS_TYPE_2.equals(classType)) {
            List<ResourceInstListResp> resourceInstList = instList;
            for (ResourceInstListResp resp : resourceInstList){
                if(mktResIdAndNbrMap.containsKey(resp.getMktResId())){
                    List<String> mktResIdList = mktResIdAndNbrMap.get(resp.getMktResId());
                    mktResIdList.add(resp.getMktResInstNbr());
                }else{
                    List<String> mktResIdList = new ArrayList<>();
                    mktResIdList.add(resp.getMktResInstNbr());
                    mktResIdAndNbrMap.put(resp.getMktResId(), mktResIdList);
                }
            }
        }else {
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
        }
        return mktResIdAndNbrMap;
    }

    /**
     * 判断是否需要审核环节：
     * 零售商门店，省包供货、绿色通道录入的串码，只能选择同个经营主体下的其他门店仓库进行调拨，同地市时不审核，跨地市时需要审核（调出和调入双方审核）
     * 地包供货的串码，只能选择同地市的零售商仓库，不需要审核
     * @param sourceMerchant
     * @param destMerchant
     * @param nbrList
     * @return
     */
    private String validAllocateNbr(MerchantDTO sourceMerchant, MerchantDTO destMerchant, List<String> nbrList){
        String sourceBusinessEntityCode = sourceMerchant.getBusinessEntityCode();
        String destBusinessEntityCode = destMerchant.getBusinessEntityCode();
        String sourceLanId = sourceMerchant.getLanId();
        String destLanId = destMerchant.getLanId();
        Boolean sameMerchant = StringUtils.isNotBlank(sourceBusinessEntityCode) && StringUtils.isNotBlank(destBusinessEntityCode) && sourceBusinessEntityCode.equals(destBusinessEntityCode);
        Boolean sameLanId = StringUtils.isNotBlank(sourceLanId) && StringUtils.isNotBlank(destLanId) && sourceLanId.equals(destLanId);

        Boolean hasDirectSuppLy = false;
        Boolean hasGroundSupply = false;
        for (String nbr : nbrList) {
            ResultVO<ResouceInstTrackDTO> resouceInstTrackDTOVO = resouceInstTrackService.getResourceInstTrackByNbrAndMerchantId(nbr, sourceMerchant.getMerchantId());
            log.info("RetailerResourceInstMarketServiceImpl.validAllocateNbr resouceInstTrackService.getResourceInstTrackByNbrAndMerchantId nbr={}, resp={}", nbr, JSON.toJSONString(resouceInstTrackDTOVO));
            if (!resouceInstTrackDTOVO.isSuccess() || null == resouceInstTrackDTOVO.getResultData()) {
                return ResourceConst.ALLOCATE_AUDIT_TYPE.ALLOCATE_AUDIT_TYPE_0.getCode();
            }
            ResouceInstTrackDTO resouceInstTrackDTO = resouceInstTrackDTOVO.getResultData();
            if (ResourceConst.CONSTANT_YES.equals(resouceInstTrackDTO.getIfGreenChannel()) ||ResourceConst.CONSTANT_YES.equals(resouceInstTrackDTO.getIfDirectSupply())) {
                hasDirectSuppLy = true;
            }
            if (ResourceConst.CONSTANT_YES.equals(resouceInstTrackDTO.getIfGroundSupply())) {
                hasGroundSupply = true;
            }
        }
        // 全是绿色通道和省直供串码且跨商 不让调拨
        Boolean directSuppLyAndNotSameMerchant = hasDirectSuppLy && !hasGroundSupply && !sameMerchant;
        // 全是绿色通道和省直供串码且不跨商，且不跨地市  不需审核
        Boolean directSuppLyAndSameMerchantAndSameLanId = hasDirectSuppLy && !hasGroundSupply && sameMerchant && sameLanId;
        // 全是绿色通道和省直供串码不跨商，跨地市  需要调出方和调入方审核
        Boolean directSuppLyAndSameMerchantAndNotSameLanId = hasDirectSuppLy && !hasGroundSupply && sameMerchant && !sameLanId;
        // 全是地堡供应串码，且不能跨地市（跨地市不让调拨，跨不跨商家都不需要审核）不需要审核
        Boolean directSuppLyAndSameLanId = !hasDirectSuppLy && hasGroundSupply && sameLanId;

        if (directSuppLyAndSameMerchantAndNotSameLanId) {
            return ResourceConst.ALLOCATE_AUDIT_TYPE.ALLOCATE_AUDIT_TYPE_2.getCode();
        } else if(directSuppLyAndSameMerchantAndSameLanId || directSuppLyAndSameLanId) {
            return ResourceConst.ALLOCATE_AUDIT_TYPE.ALLOCATE_AUDIT_TYPE_1.getCode();
        } else{
            return ResourceConst.ALLOCATE_AUDIT_TYPE.ALLOCATE_AUDIT_TYPE_0.getCode();
        }
    }

    private ResultVO saveEebuyTerminal(Map<String, List<ResourceInstListResp>> map, List<String> mktResInstNbrList, String lanId, String mktResStoreId){
        for (Map.Entry<String, List<ResourceInstListResp>> entry : map.entrySet()) {
            List<ResourceInstListResp> instList = entry.getValue();
            ProductGetByIdReq productReq = new ProductGetByIdReq();
            productReq.setProductId(entry.getKey());
            ResultVO<ProductForResourceResp> productRespResultVO = productService.getProductForResource(productReq);
            log.info("RetailerResourceInstMarketServiceImpl.syncTerminal productService.getProductForResource req={},resp={}", JSON.toJSONString(productReq), JSON.toJSONString(productRespResultVO));
            String sn = "";
            if (productRespResultVO.isSuccess() && productRespResultVO.getResultData() != null) {
                sn = productRespResultVO.getResultData().getSn();
            }
            for(ResourceInstListResp dto : instList) {
                if (!mktResInstNbrList.contains(dto.getMktResInstNbr())) {
                    continue;
                }
                ResultVO<MerchantDTO> merchantDTOResultVO = resouceStoreService.getMerchantByStore(dto.getMktResStoreId());
                if (!merchantDTOResultVO.isSuccess() || null == merchantDTOResultVO) {
                    return ResultVO.error(constant.getCannotGetMerchantMsg());
                }
                MerchantDTO merchantDTO = merchantDTOResultVO.getResultData();
                List<EBuyTerminalItemSwapReq> eBuyTerminalItemReqs = Lists.newArrayList();
                EBuyTerminalItemSwapReq eBuyTerminalItemSwapReq = new EBuyTerminalItemSwapReq();
                eBuyTerminalItemSwapReq.setMktId(sn);
                eBuyTerminalItemSwapReq.setPurchaseType(ResourceConst.PURCHASE_TYPE.PURCHASE_TYPE_13.getCode());
                String salePrice = dto.getSalesPrice() == null ? "" : String.valueOf(dto.getSalesPrice());
                eBuyTerminalItemSwapReq.setSalesPrice(salePrice);
                eBuyTerminalItemSwapReq.setBarCode(dto.getMktResInstNbr());
                eBuyTerminalItemSwapReq.setStoreId(mktResStoreId);
                eBuyTerminalItemSwapReq.setLanId(lanId);
                eBuyTerminalItemSwapReq.setSupplyName(merchantDTO.getMerchantName());
                eBuyTerminalItemSwapReq.setSupplyCode(merchantDTO.getMerchantCode());
                eBuyTerminalItemReqs.add(eBuyTerminalItemSwapReq);
                EBuyTerminalSwapReq eBuyTerminalSwapReq = new EBuyTerminalSwapReq();
                eBuyTerminalSwapReq.setMktResList(eBuyTerminalItemReqs);
                ResultVO eBuyTerminalResultVO = marketingResStoreService.ebuyTerminal(eBuyTerminalSwapReq);
                log.info("RetailerResourceInstMarketServiceImpl.syncTerminal marketingResStoreService.ebuyTerminal req={}", JSON.toJSONString(eBuyTerminalSwapReq), JSON.toJSONString(eBuyTerminalResultVO));
                if (!eBuyTerminalResultVO.isSuccess()) {
                    return eBuyTerminalResultVO;
                }
            }
        }
        return ResultVO.success();
    }

}