package com.iwhalecloud.retail.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.exception.RetailTipException;
import com.iwhalecloud.retail.goods2b.dto.req.MerChantGetProductReq;
import com.iwhalecloud.retail.goods2b.dto.req.ProductResourceInstGetReq;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductResourceResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.system.service.CommonRegionService;
import com.iwhalecloud.retail.warehouse.busiservice.ResouceInstTrackService;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstCheckService;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstLogService;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.constant.Constant;
import com.iwhalecloud.retail.warehouse.dto.ResouceStoreDTO;
import com.iwhalecloud.retail.warehouse.dto.ResourceInstDTO;
import com.iwhalecloud.retail.warehouse.dto.ResourceReqDetailDTO;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstAddResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListPageResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceRequestResp;
import com.iwhalecloud.retail.warehouse.manager.*;
import com.iwhalecloud.retail.warehouse.runable.RunableTask;
import com.iwhalecloud.retail.warehouse.service.*;
import com.iwhalecloud.retail.workflow.common.WorkFlowConst;
import com.iwhalecloud.retail.workflow.dto.req.ProcessStartReq;
import com.iwhalecloud.retail.workflow.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Slf4j
@Service("supplierResourceInstService")
public class SupplierResourceInstServiceImpl implements SupplierResourceInstService {

    @Autowired
    private ResourceInstManager resourceInstManager;

    @Autowired
    private ResouceEventManager resouceEventManager;

    @Autowired
    private ResourceChngEvtDetailManager detailManager;

    @Autowired
    private ResourceBatchRecManager batchRecManager;

    @Autowired
    private ResourceInstStoreManager resourceInstStoreManager;

    @Autowired
    private ResouceStoreManager resouceStoreManager;

    @Reference
    private ProductService productService;

    @Reference
    private MerchantService merchantService;

    @Autowired
    private ResourceInstService resourceInstService;

    @Reference
    private TaskService taskService;

    @Reference
    private ResourceRequestService resourceRequestService;

    @Reference
    private ResouceStoreService resouceStoreService;

    @Reference
    private ResourceInstStoreService resourceInstStoreService;

    @Reference
    private MarketingResStoreService marketingResStoreService;

    @Reference
    private CommonRegionService commonRegionService;

    @Reference
    private ResourceRequestService requestService;

    @Autowired
    private ResourceReqDetailManager resourceReqDetailManager;

    @Autowired
    private ResouceInstTrackService resouceInstTrackService;

    @Autowired
    private Constant constant;

    @Autowired
    private ResourceInstLogService resourceInstLogService;

    @Autowired
    private ResourceInstCheckService resourceInstCheckService;

    @Autowired
    private RunableTask runableTask;

    @Autowired
    private ResourceUploadTempManager resourceUploadTempManager;


    @Override
    public ResultVO addResourceInst(ResourceInstAddReq req) {
        log.info("SupplierResourceInstServiceImpl.addResourceInst req={}", JSON.toJSONString(req));
        String merchantId = req.getMerchantId();
        ResultVO<MerchantDTO> merchantResultVO = merchantService.getMerchantById(req.getMerchantId());
        log.info("SupplierResourceInstServiceImpl.addResourceInst merchantService.getMerchantById merchantId={},resp={}", req.getMerchantId(), JSON.toJSONString(merchantResultVO));
        if (!merchantResultVO.isSuccess() || null == merchantResultVO.getResultData()) {
            return ResultVO.error(constant.getCannotGetMerchantMsg());
        }
        MerchantDTO merchantDTO = merchantResultVO.getResultData();
        ResultVO<MerchantDTO> sourceMerchantResultVO = merchantService.getMerchantById(req.getMerchantId());
        log.info("SupplierResourceInstServiceImpl.addResourceInst merchantService.getMerchantById merchantId={},resp={}", req.getSourcemerchantId(), JSON.toJSONString(sourceMerchantResultVO));
        if (!sourceMerchantResultVO.isSuccess() || null == sourceMerchantResultVO.getResultData()) {
            return ResultVO.error(constant.getCannotGetMerchantMsg());
        }
        MerchantDTO sourceMerchantDTO = sourceMerchantResultVO.getResultData();
        // 获取仓库
        StoreGetStoreIdReq storeGetStoreIdReq = new StoreGetStoreIdReq();
        storeGetStoreIdReq.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
        storeGetStoreIdReq.setMerchantId(merchantId);
        String mktResStoreId = null;
        try{
            mktResStoreId = resouceStoreService.getStoreId(storeGetStoreIdReq);
            log.info("SupplierResourceInstServiceImpl.addResourceInst resouceStoreService.getStoreId req={},resp={}", JSON.toJSONString(storeGetStoreIdReq), mktResStoreId);
            if (StringUtils.isBlank(mktResStoreId)) {
                return ResultVO.error(constant.getCannotGetStoreMsg());
            }
        }catch (Exception e){
            return ResultVO.error(constant.getGetRepeatStoreMsg());
        }
        req.setMerchantType(merchantDTO.getMerchantType());
        req.setDestStoreId(mktResStoreId);
        req.setSourceType(sourceMerchantDTO.getMerchantType());
        req.setLanId(merchantDTO.getLanId());
        req.setRegionId(merchantDTO.getCity());
        req.setStatusCd(ResourceConst.STATUSCD.AVAILABLE.getCode());
        req.setStorageType(ResourceConst.STORAGETYPE.TRANSACTION_WAREHOUSING.getCode());
        req.setCreateStaff(merchantId);
        Boolean addNum = resourceInstService.addResourceInst(req);
        if (!addNum) {
            return ResultVO.error("串码入库失败");
        }
        ResourceInstUpdateReq resourceInstUpdateReq = new ResourceInstUpdateReq();
        resourceInstUpdateReq.setDestStoreId(req.getMktResStoreId());
        resourceInstUpdateReq.setMktResInstNbrs(req.getMktResInstNbrs());
        resourceInstUpdateReq.setMktResStoreId(ResourceConst.NULL_STORE_ID);
        resourceInstUpdateReq.setEventType(ResourceConst.EVENTTYPE.SALE_TO_ORDER.getCode());
        resourceInstUpdateReq.setCheckStatusCd(Lists.newArrayList(
                ResourceConst.STATUSCD.AUDITING.getCode(),
                ResourceConst.STATUSCD.ALLOCATIONED.getCode(),
                ResourceConst.STATUSCD.ALLOCATIONING.getCode(),
                ResourceConst.STATUSCD.RESTORAGEING.getCode(),
                ResourceConst.STATUSCD.RESTORAGED.getCode(),
                ResourceConst.STATUSCD.DELETED.getCode()));
        resourceInstUpdateReq.setStatusCd(ResourceConst.STATUSCD.SALED.getCode());
        resourceInstUpdateReq.setTypeId(req.getTypeId());
        resourceInstUpdateReq.setMerchantId(req.getSourcemerchantId());
        ResultVO updateResourceresultVO = resourceInstService.updateResourceInst(resourceInstUpdateReq);
        if (!updateResourceresultVO.isSuccess()) {
            throw new RetailTipException(ResultCodeEnum.ERROR.getCode(), updateResourceresultVO.getResultMsg());
        }
        return ResultVO.success();
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResultVO delResourceInst(AdminResourceInstDelReq req) {
        AdminResourceInstDelReq delReq = new AdminResourceInstDelReq();
        BeanUtils.copyProperties(req, delReq);
        // 获取仓库
        StoreGetStoreIdReq storeGetStoreIdReq = new StoreGetStoreIdReq();
        storeGetStoreIdReq.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
        storeGetStoreIdReq.setMerchantId(req.getMerchantId());
        String mktResStoreId = resouceStoreService.getStoreId(storeGetStoreIdReq);
        log.info("SupplierResourceInstServiceImpl.delResourceInst resouceStoreService.getStoreId req={},resp={}", JSON.toJSONString(storeGetStoreIdReq), mktResStoreId);
        req.setMktResStoreId(ResourceConst.NULL_STORE_ID);
        req.setDestStoreId(mktResStoreId);
        return resourceInstService.updateResourceInstByIds(req);
    }


    @Override
    public ResultVO<Page<ResourceInstListPageResp>> getResourceInstList(ResourceInstListPageReq req) {
        log.info("SupplierResourceInstServiceImpl.getResourceInstList req={}", JSON.toJSONString(req));
        return this.resourceInstService.getResourceInstList(req);
    }

    /**
     * 判断是否需要审核环节：
     * 1.本地市内可调拨，地市管理员审核
     * 2.跨地市调拨，调出和调入双方管理员审核"
     */
    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResultVO allocateResourceInst(SupplierResourceInstAllocateReq req) {
        //根据仓库查使用对象
        ResultVO<MerchantDTO> sourceMerchantVO = resouceStoreService.getMerchantByStore(req.getMktResStoreId());
        log.info("SupplierResourceInstServiceImpl.allocateResourceInst resouceStoreService.getMerchantByStore req={},resp={}", req.getMktResStoreId(), JSON.toJSONString(sourceMerchantVO));
        MerchantDTO sourceMerchantDTO = sourceMerchantVO.getResultData();
        if (null == sourceMerchantDTO) {
            return ResultVO.error("商家获取失败");
        }
        ResultVO<MerchantDTO> destMerchantVO = resouceStoreService.getMerchantByStore(req.getMktResStoreId());
        log.info("SupplierResourceInstServiceImpl.allocateResourceInst resouceStoreService.getMerchantByStore req={},resp={}", req.getDestStoreId(), JSON.toJSONString(destMerchantVO));
        MerchantDTO destMerchantDTO = destMerchantVO.getResultData();
        if (null == destMerchantDTO) {
            return ResultVO.error("商家获取失败");
        }
        ResourceInstsGetByIdListAndStoreIdReq selectReq = new ResourceInstsGetByIdListAndStoreIdReq();
        selectReq.setMktResInstIdList(req.getMktResInstIds());
        selectReq.setMktResStoreId(req.getDestStoreId());
        List<ResourceInstDTO> resourceInstDTOList = resourceInstService.selectByIds(selectReq);

        Boolean sameLanId = sourceMerchantDTO.getLanId() != null && destMerchantDTO.getLanId() != null && sourceMerchantDTO.getLanId().equals(destMerchantDTO.getLanId());
        // step1 如果跨地市不允许调拨(地包调货不考虑串码类型)
        String successMessage = ResourceConst.ALLOCATE_SUCESS_MSG;
        String reqCode = resourceInstManager.getPrimaryKey();

        String processId = ResourceConst.ALLOCATE_WORK_FLOW_INST;
        String taskSubType = WorkFlowConst.TASK_SUB_TYPE.TASK_SUB_TYPE_1010.getTaskSubType();
        if (!sameLanId) {
            processId = ResourceConst.ALLOCATE_WORK_FLOW_INST_2;
            taskSubType = WorkFlowConst.TASK_SUB_TYPE.TASK_SUB_TYPE_2040.getTaskSubType() ;
        }
        // step2 新增申请单
        List<ResourceRequestAddReq.ResourceRequestInst> resourceRequestInsts = new ArrayList<>();
        for (ResourceInstDTO resourceInstDTO : resourceInstDTOList) {
            ResourceRequestAddReq.ResourceRequestInst resourceRequestInst = new ResourceRequestAddReq.ResourceRequestInst();
            BeanUtils.copyProperties(resourceInstDTO, resourceRequestInst);
            resourceRequestInsts.add(resourceRequestInst);
        }
        ResourceRequestAddReq resourceRequestAddReq = new ResourceRequestAddReq();
        resourceRequestAddReq.setReqName("调拨申请单");
        resourceRequestAddReq.setReqType(ResourceConst.REQTYPE.ALLOCATE_APPLYFOR.getCode());
        resourceRequestAddReq.setMktResStoreId(req.getMktResStoreId());
        resourceRequestAddReq.setCreateStaff(req.getCreateStaff());
        resourceRequestAddReq.setDestStoreId(req.getDestStoreId());
        resourceRequestAddReq.setStatusCd(ResourceConst.MKTRESSTATE.PROCESSING.getCode());
        resourceRequestAddReq.setChngType(ResourceConst.PUT_IN_STOAGE);
        resourceRequestAddReq.setInstList(resourceRequestInsts);
        resourceRequestAddReq.setLanId(sourceMerchantDTO.getLanId());
        resourceRequestAddReq.setRegionId(sourceMerchantDTO.getCity());
        resourceRequestAddReq.setReqCode(reqCode);
        ResultVO<String> resultVOInsertResReq = resourceRequestService.insertResourceRequest(resourceRequestAddReq);
        log.info("SupplierResourceInstServiceImpl.allocateResourceInst resourceRequestService.insertResourceRequest req={},resp={}", JSON.toJSONString(resourceRequestAddReq), JSON.toJSONString(resultVOInsertResReq));
        if (resultVOInsertResReq == null || resultVOInsertResReq.getResultData() == null) {
            throw new RetailTipException(ResultCodeEnum.ERROR.getCode(), "添加申请单失败");
        }

        // step3 发起流程到目标商家，由目标商家决定是否接受
        String createStaff = req.getCreateStaff();
        ProcessStartReq processStartDTO = new ProcessStartReq();
        processStartDTO.setTitle("调拨审批流程");
        processStartDTO.setApplyUserId(createStaff);
        processStartDTO.setApplyUserName(sourceMerchantDTO.getMerchantName());
        processStartDTO.setProcessId(processId);
        processStartDTO.setFormId(resultVOInsertResReq.getResultData());
        processStartDTO.setTaskSubType(taskSubType);
        processStartDTO.setExtends1(sourceMerchantDTO.getCityName());
        ResultVO startResultVO = taskService.startProcess(processStartDTO);
        log.info("SupplierResourceInstServiceImpl.allocateResourceInst taskService.startProcess req={}, resp={}", JSON.toJSONString(processStartDTO), JSON.toJSONString(startResultVO));
        if (null != startResultVO && startResultVO.getResultCode().equals(ResultCodeEnum.ERROR.getCode())) {
            throw new RetailTipException(ResultCodeEnum.ERROR.getCode(), "启动工作流失败");
        }
        // step4 修改源仓库串码为调拨中
        AdminResourceInstDelReq updateReq = new AdminResourceInstDelReq();
        BeanUtils.copyProperties(req, updateReq);
        // 设置状态校验条件
        List<String> checkStatusCd = Lists.newArrayList(
                ResourceConst.STATUSCD.DELETED.getCode(),
                ResourceConst.STATUSCD.AUDITING.getCode(),
                ResourceConst.STATUSCD.ALLOCATIONED.getCode(),
                ResourceConst.STATUSCD.ALLOCATIONING.getCode(),
                ResourceConst.STATUSCD.RESTORAGEING.getCode(),
                ResourceConst.STATUSCD.RESTORAGED.getCode(),
                ResourceConst.STATUSCD.SALED.getCode());
        updateReq.setCheckStatusCd(checkStatusCd);
        updateReq.setStatusCd(ResourceConst.STATUSCD.ALLOCATIONING.getCode());
        updateReq.setUpdateStaff(createStaff);
        updateReq.setEventType(ResourceConst.EVENTTYPE.ALLOT.getCode());
        updateReq.setObjType(ResourceConst.EVENT_OBJTYPE.PUT_STORAGE.getCode());
        updateReq.setObjId(resultVOInsertResReq.getResultData());
        updateReq.setMktResStoreId(req.getMktResStoreId());
        updateReq.setDestStoreId(req.getDestStoreId());
        ResultVO updateResultVO = resourceInstService.updateResourceInstByIds(updateReq);
        log.info("SupplierResourceInstServiceImpl.allocateResourceInst resourceInstService.updateResourceInstByIds req={},resp={}", JSON.toJSONString(updateReq), JSON.toJSONString(updateResultVO));
        if (!updateResultVO.isSuccess()) {
            throw new RetailTipException(ResultCodeEnum.ERROR.getCode(), "更新串码失败");
        }
        return ResultVO.success(successMessage+reqCode);
    }


    @Override
    public ResultVO deliveryOutResourceInst(DeliveryResourceInstReq req) {
        StoreGetStoreIdReq storePageReq = new StoreGetStoreIdReq();
        storePageReq.setMerchantId(req.getSellerMerchantId());
        storePageReq.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
        String storeId = null;
        String destStoreId = null;
        try{
            storeId = resouceStoreService.getStoreId(storePageReq);
            if (StringUtils.isEmpty(storeId)) {
                return ResultVO.error(constant.getCannotGetStoreMsg());
            }
            log.info("SupplierResourceInstServiceImpl.deliveryOutResourceInst resouceStoreService.getStoreId req={},storeId={}", JSON.toJSONString(storePageReq), JSON.toJSONString(storeId));
            storePageReq.setMerchantId(req.getBuyerMerchantId());
            destStoreId = resouceStoreService.getStoreId(storePageReq);
            if (StringUtils.isEmpty(destStoreId)) {
                return ResultVO.error(constant.getCannotGetStoreMsg());
            }
            log.info("SupplierResourceInstServiceImpl.deliveryOutResourceInst resouceStoreService.getStoreId req={},destStoreId={}", JSON.toJSONString(storePageReq), JSON.toJSONString(destStoreId));
        }catch (Exception e){
            return ResultVO.error(constant.getGetRepeatStoreMsg());
        }
        ResultVO<MerchantDTO> merchantVO = merchantService.getMerchantById(req.getBuyerMerchantId());
        if (!merchantVO.isSuccess() || null == merchantVO.getResultData()) {
            return ResultVO.error(constant.getCannotGetMerchantMsg());
        }
        MerchantDTO merchantDTO = merchantVO.getResultData();
        log.info("SupplierResourceInstServiceImpl.deliveryOutResourceInst resouceStoreService.getMerchantById req={},destStoreId={}", req.getBuyerMerchantId(), JSON.toJSONString(merchantVO));
        for (DeliveryResourceInstItem item : req.getDeliveryResourceInstItemList()) {
            ResourceInstUpdateReq resourceInstUpdateReq = new ResourceInstUpdateReq();
            resourceInstUpdateReq.setMktResInstNbrs(item.getMktResInstNbrs());
            resourceInstUpdateReq.setCheckStatusCd(Lists.newArrayList(ResourceConst.STATUSCD.DELETED.getCode()));
            resourceInstUpdateReq.setMktResStoreId(storeId);
            resourceInstUpdateReq.setDestStoreId(destStoreId);
            resourceInstUpdateReq.setStatusCd(ResourceConst.STATUSCD.SALED.getCode());
            resourceInstUpdateReq.setEventType(ResourceConst.EVENTTYPE.SALE_TO_ORDER.getCode());
            resourceInstUpdateReq.setObjType(ResourceConst.EVENT_OBJTYPE.ALLOT.getCode());
            resourceInstUpdateReq.setObjId(req.getOrderId());
            resourceInstUpdateReq.setMerchantId(req.getBuyerMerchantId());
            if (PartnerConst.MerchantTypeEnum.PARTNER.getType().equals(merchantDTO.getMerchantType())) {
                resourceInstUpdateReq.setMerchantName(merchantDTO.getMerchantName());
                resourceInstUpdateReq.setMerchantCode(merchantDTO.getMerchantCode());
            }
            resourceInstUpdateReq.setMktResId(item.getProductId());
            resourceInstUpdateReq.setMerchantId(merchantDTO.getMerchantId());
            resourceInstUpdateReq.setOrderId(req.getOrderId());
            resourceInstUpdateReq.setCreateTime(new Date());
            ResultVO delRS = resourceInstService.updateResourceInstForTransaction(resourceInstUpdateReq);
            log.info("SupplierResourceInstServiceImpl.deliveryOutResourceInst resourceInstService.delResourceInst req={},resp={}", JSON.toJSONString(resourceInstUpdateReq), JSON.toJSONString(delRS));
        }
        // 下单是增加了在途数量，发货时减去
        if (null != req.getUpdateStockReq()) {
            resourceInstStoreService.updateStock(req.getUpdateStockReq());
        }
        return ResultVO.success(true);
    }

    @Override
    public ResultVO deliveryInResourceInst(DeliveryResourceInstReq req) {
        ResultVO<MerchantDTO> merchantResultVO = merchantService.getMerchantById(req.getSellerMerchantId());
        if (!merchantResultVO.isSuccess() || null == merchantResultVO.getResultData()) {
            return ResultVO.error(constant.getCannotGetMerchantMsg());
        }
        MerchantDTO merchantDTO = merchantResultVO.getResultData();
        log.info("SupplierResourceInstServiceImpl.deliveryInResourceInst merchantService.getMerchantById req={},resp={}", JSON.toJSONString(req), JSON.toJSONString(merchantResultVO));

        ResultVO<MerchantDTO> buyerResultVO = merchantService.getMerchantById(req.getBuyerMerchantId());
        if (!buyerResultVO.isSuccess() || null == buyerResultVO.getResultData()) {
            return ResultVO.error(constant.getCannotGetMerchantMsg());
        }
        MerchantDTO buyer = buyerResultVO.getResultData();
        log.info("SupplierResourceInstServiceImpl.deliveryInResourceInst merchantService.getMerchantById req={},resp={}", JSON.toJSONString(req), JSON.toJSONString(merchantResultVO));
        // 源仓库
        StoreGetStoreIdReq storeGetStoreIdReq = new StoreGetStoreIdReq();
        storeGetStoreIdReq.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
        storeGetStoreIdReq.setMerchantId(req.getSellerMerchantId());
        String mktResStoreId = resouceStoreService.getStoreId(storeGetStoreIdReq);
        log.info("SupplierResourceInstServiceImpl.deliveryInResourceInst resouceStoreService.getStoreId req={},resp={}", JSON.toJSONString(storeGetStoreIdReq), mktResStoreId);
        // 目标仓库
        storeGetStoreIdReq.setMerchantId(req.getBuyerMerchantId());
        String destStoreId = resouceStoreService.getStoreId(storeGetStoreIdReq);
        log.info("SupplierResourceInstServiceImpl.deliveryInResourceInst resouceStoreService.getStoreId req={},resp={}", JSON.toJSONString(storeGetStoreIdReq), destStoreId);

        for (DeliveryResourceInstItem item : req.getDeliveryResourceInstItemList()) {
            if (StringUtils.isEmpty(item.getProductId())) {
                return ResultVO.error(constant.getNoProductIdMsg());
            }
            ResourceInstAddReq resourceInstAddReq = new ResourceInstAddReq();
            resourceInstAddReq.setMktResInstNbrs(item.getMktResInstNbrs());
            resourceInstAddReq.setCreateStaff(req.getBuyerMerchantId());
            resourceInstAddReq.setStatusCd(ResourceConst.STATUSCD.AVAILABLE.getCode());
            resourceInstAddReq.setEventType(ResourceConst.EVENTTYPE.PUT_STORAGE.getCode());
            resourceInstAddReq.setSourceType(ResourceConst.SOURCE_TYPE.SUPPLIER.getCode());
            resourceInstAddReq.setStorageType(ResourceConst.STORAGETYPE.TRANSACTION_WAREHOUSING.getCode());
            resourceInstAddReq.setMktResInstType(ResourceConst.MKTResInstType.TRANSACTION.getCode());
            resourceInstAddReq.setObjType(ResourceConst.EVENT_OBJTYPE.ALLOT.getCode());
            resourceInstAddReq.setObjId(item.getOrderItemId());
            resourceInstAddReq.setMktResId(item.getProductId());
            resourceInstAddReq.setOrderId(req.getOrderId());
            resourceInstAddReq.setCreateDate(new Date());
            resourceInstAddReq.setMktResStoreId(mktResStoreId);
            resourceInstAddReq.setDestStoreId(destStoreId);
            resourceInstAddReq.setSupplierName(merchantDTO.getMerchantName());
            resourceInstAddReq.setSupplierCode(merchantDTO.getMerchantCode());
            resourceInstAddReq.setMerchantId(req.getBuyerMerchantId());
            resourceInstAddReq.setLanId(buyer.getLanId());
            resourceInstAddReq.setRegionId(buyer.getCity());
            ResultVO addRS = this.resourceInstService.addResourceInstForTransaction(resourceInstAddReq);
            log.info("SupplierResourceInstServiceImpl.deliveryInResourceInst resourceInstService.addResourceInst req={},resp={}", JSON.toJSONString(resourceInstAddReq), JSON.toJSONString(addRS));
            if (addRS == null || !addRS.isSuccess()) {
                return ResultVO.error(addRS.getResultMsg());
            }
        }
        return ResultVO.success(true);
    }

    @Override
    public ResultVO backDeliveryOutResourceInst(DeliveryResourceInstReq req) {
        ResultVO<MerchantDTO> merchantResultVO = merchantService.getMerchantById(req.getBuyerMerchantId());
        log.info("SupplierResourceInstServiceImpl.backDeliveryOutResourceInst merchantService.getMerchantById req={},resp={}", JSON.toJSONString(req), JSON.toJSONString(merchantResultVO));
        if (null == merchantResultVO || !merchantResultVO.isSuccess() || null == merchantResultVO.getResultData()) {
            return ResultVO.error("查无该商家");
        }
        // 源仓库
        StoreGetStoreIdReq storePageReq = new StoreGetStoreIdReq();
        storePageReq.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
        storePageReq.setMerchantId(req.getSellerMerchantId());
        String mktResStoreId = resouceStoreService.getStoreId(storePageReq);
        log.info("SupplierResourceInstServiceImpl.deliveryInResourceInst resouceStoreService.getStoreId req={},resp={}", JSON.toJSONString(storePageReq), mktResStoreId);
        // 目标仓库
        storePageReq.setMerchantId(req.getBuyerMerchantId());
        String destStoreId = resouceStoreService.getStoreId(storePageReq);
        log.info("SupplierResourceInstServiceImpl.backDeliveryOutResourceInst resourceInstService  destStoreId={}",destStoreId);
        for (DeliveryResourceInstItem item : req.getDeliveryResourceInstItemList()) {

            ResourceInstUpdateReq resourceInstUpdateReq = new ResourceInstUpdateReq();
            resourceInstUpdateReq.setMktResInstNbrs(item.getMktResInstNbrs());
            // 设置状态校验条件
            List<String> checkStatusCd = Lists.newArrayList(ResourceConst.STATUSCD.DELETED.getCode());
            resourceInstUpdateReq.setCheckStatusCd(checkStatusCd);
            resourceInstUpdateReq.setStatusCd(ResourceConst.STATUSCD.DELETED.getCode());
            resourceInstUpdateReq.setEventType(ResourceConst.EVENTTYPE.BUY_BACK.getCode());
            resourceInstUpdateReq.setObjType(ResourceConst.EVENT_OBJTYPE.ALLOT.getCode());
            resourceInstUpdateReq.setDestStoreId(destStoreId);
            resourceInstUpdateReq.setMktResStoreId(mktResStoreId);
            resourceInstUpdateReq.setUpdateStaff(merchantResultVO.getResultData().getUserId());
            resourceInstUpdateReq.setMerchantId(req.getBuyerMerchantId());
            ResultVO updateResultVO = resourceInstService.updateResourceInstForTransaction(resourceInstUpdateReq);
            log.info("SupplierResourceInstServiceImpl.backDeliveryOutResourceInst resourceInstService.updateResourceInstForTransaction req={},resp={}", JSON.toJSONString(resourceInstUpdateReq), JSON.toJSONString(updateResultVO));
            if (updateResultVO == null || !updateResultVO.isSuccess()) {
                return ResultVO.error(updateResultVO.getResultMsg());
            }
        }
        return ResultVO.success(true);
    }

    @Override
    public ResultVO backDeliveryInResourceInst(DeliveryResourceInstReq req) {
        ResultVO<MerchantDTO> merchantResultVO = merchantService.getMerchantById(req.getSellerMerchantId());
        log.info("SupplierResourceInstServiceImpl.backDeliveryInResourceInst merchantService.getMerchantById req={},resp={}", JSON.toJSONString(req), JSON.toJSONString(merchantResultVO));
        if (null == merchantResultVO || !merchantResultVO.isSuccess() || null == merchantResultVO.getResultData()) {
            return ResultVO.error("查无该商家");
        }
        // 仓库
        StoreGetStoreIdReq storeGetStoreIdReq = new StoreGetStoreIdReq();
        storeGetStoreIdReq.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
        storeGetStoreIdReq.setMerchantId(req.getSellerMerchantId());
        String destStoreId = resouceStoreService.getStoreId(storeGetStoreIdReq);
        log.info("SupplierResourceInstServiceImpl.backDeliveryOutResourceInst resourceInstService req={} destStoreId={}", JSON.toJSONString(storeGetStoreIdReq), destStoreId);
        storeGetStoreIdReq.setMerchantId(req.getBuyerMerchantId());
        String sourceStoreId = resouceStoreService.getStoreId(storeGetStoreIdReq);
        log.info("SupplierResourceInstServiceImpl.backDeliveryOutResourceInst resourceInstService req={} storeId={}",JSON.toJSONString(storeGetStoreIdReq), sourceStoreId);
        for (DeliveryResourceInstItem item : req.getDeliveryResourceInstItemList()) {
            ResourceInstAddReq resourceInstAddReq = new ResourceInstAddReq();
            resourceInstAddReq.setMktResInstNbrs(item.getMktResInstNbrs());
            resourceInstAddReq.setCreateStaff(req.getBuyerMerchantId());
            resourceInstAddReq.setStatusCd(ResourceConst.STATUSCD.AVAILABLE.getCode());
            resourceInstAddReq.setEventType(ResourceConst.EVENTTYPE.PUT_STORAGE.getCode());
            resourceInstAddReq.setSourceType(ResourceConst.SOURCE_TYPE.SUPPLIER.getCode());
            resourceInstAddReq.setStorageType(ResourceConst.STORAGETYPE.TRANSACTION_WAREHOUSING.getCode());
            resourceInstAddReq.setMktResInstType(ResourceConst.MKTResInstType.TRANSACTION.getCode());
            resourceInstAddReq.setObjType(ResourceConst.EVENT_OBJTYPE.ALLOT.getCode());
            resourceInstAddReq.setObjId(item.getOrderItemId());
            resourceInstAddReq.setMktResId(item.getProductId());
            resourceInstAddReq.setOrderId(req.getOrderId());
            resourceInstAddReq.setCreateDate(new Date());
            resourceInstAddReq.setMktResStoreId(sourceStoreId);
            resourceInstAddReq.setDestStoreId(destStoreId);
            resourceInstAddReq.setMerchantId(req.getSellerMerchantId());
            ResultVO addRS = this.resourceInstService.addResourceInstForTransaction(resourceInstAddReq);
            log.info("SupplierResourceInstServiceImpl.deliveryInResourceInst resourceInstService.addResourceInstForTransaction req={},resp={}", JSON.toJSONString(resourceInstAddReq), JSON.toJSONString(addRS));
            if (addRS == null || !addRS.isSuccess()) {
                return ResultVO.error(addRS.getResultMsg());
            }
        }
        return ResultVO.success(true);
    }

    @Override
    public ResultVO<List<ResourceInstListPageResp>> getBatch(ResourceInstBatchReq req) {
        List<ResourceInstListPageResp> list = resourceInstManager.getBatch(req);
        log.info("SupplierResourceInstServiceImpl.getBatch req={}", JSON.toJSONString(req));
        // 添加产品信息
        for (ResourceInstListPageResp resp : list) {
            String productId = resp.getMktResId();
            ProductResourceInstGetReq queryReq = new ProductResourceInstGetReq();
            queryReq.setProductId(productId);
            ResultVO<List<ProductResourceResp>> resultVO = productService.getProductResource(queryReq);
            log.info("SupplierResourceInstServiceImpl.getBatch productService.getProductResource req={},resp={}", JSON.toJSONString(queryReq), JSON.toJSONString(resultVO));
            List<ProductResourceResp> prodList = resultVO.getResultData();
            if (null != prodList && !prodList.isEmpty()) {
                ProductResourceResp prodResp = prodList.get(0);
                BeanUtils.copyProperties(prodResp, resp);
            }
        }
        return ResultVO.success(list);
    }

    @Override
    public ResultVO<Boolean> updateInstState(ResourceInstUpdateReq req) {
        log.info("SupplierResourceInstServiceImpl.updateInstState req={}", JSON.toJSONString(req));
        req.setCheckStatusCd(Lists.newArrayList(
                ResourceConst.STATUSCD.DELETED.getCode(),
                ResourceConst.STATUSCD.AUDITING.getCode(),
                ResourceConst.STATUSCD.ALLOCATIONED.getCode(),
                ResourceConst.STATUSCD.ALLOCATIONING.getCode(),
                ResourceConst.STATUSCD.DELETED.getCode()));
        return this.resourceInstService.updateInstState(req);
    }

    @Override
    public ResultVO confirmReciveNbr(ConfirmReciveNbrReq req) {
        // step1 申请单状态改为完成
        String resReqId = req.getResReqId();
        ResourceRequestItemQueryReq resourceRequestReq = new ResourceRequestItemQueryReq();
        resourceRequestReq.setMktResReqId(resReqId);
        ResultVO<ResourceRequestResp> resourceRequestRespVO = requestService.queryResourceRequest(resourceRequestReq);
        String statusCdReviewed = ResourceConst.MKTRESSTATE.REVIEWED.getCode();
        Boolean statusNotRight = !resourceRequestRespVO.isSuccess() || resourceRequestRespVO.getResultData() == null || !statusCdReviewed.equals(resourceRequestRespVO.getResultData().getStatusCd());
        if (statusNotRight) {
            return ResultVO.error(ResultCodeEnum.ERROR.getCode(), "申请单状态不正确");
        }
        ResourceRequestResp resourceRequestResp = resourceRequestRespVO.getResultData();
        ResourceRequestUpdateReq reqUpdate = new ResourceRequestUpdateReq();
        reqUpdate.setMktResReqId(resReqId);
        reqUpdate.setStatusCd(ResourceConst.MKTRESSTATE.DONE.getCode());
        ResultVO<Boolean> updateResourceRequestStateVO = requestService.updateResourceRequestState(reqUpdate);
        log.info("RetailerResourceInstMarketServiceImpl.confirmRefuseNbr requestService.updateResourceRequestState req={},resp={}", JSON.toJSONString(reqUpdate),JSON.toJSONString(updateResourceRequestStateVO));

        // step1 找到串码明细
        ResourceReqDetailQueryReq queryReq = new ResourceReqDetailQueryReq();
        queryReq.setMktResReqId(req.getResReqId());
        List<ResourceReqDetailDTO> list = resourceReqDetailManager.listDetail(queryReq);
        log.info("RetailerResourceInstMarketServiceImpl.confirmRefuseNbr resourceReqDetailManager.listDetail req={},resp={}", JSON.toJSONString(queryReq),JSON.toJSONString(list));
        if (null == list || list.isEmpty()) {
            return ResultVO.error("没有查找到申请单");
        }
        List<String> mktResInstIds = list.stream().map(ResourceReqDetailDTO::getMktResInstId).collect(Collectors.toList());

        // step2 把状态改为已调拨
        AdminResourceInstDelReq updateReq = new AdminResourceInstDelReq();
        List<String> checkStatusCd = Lists.newArrayList(
                ResourceConst.STATUSCD.AUDITING.getCode(),
                ResourceConst.STATUSCD.AVAILABLE.getCode(),
                ResourceConst.STATUSCD.RESTORAGEING.getCode(),
                ResourceConst.STATUSCD.RESTORAGED.getCode(),
                ResourceConst.STATUSCD.SALED.getCode(),
                ResourceConst.STATUSCD.DELETED.getCode());
        updateReq.setMktResInstIds(mktResInstIds);
        updateReq.setCheckStatusCd(checkStatusCd);
        updateReq.setStatusCd(ResourceConst.STATUSCD.ALLOCATIONED.getCode());
        updateReq.setEventType(ResourceConst.EVENTTYPE.CANCEL.getCode());
        updateReq.setObjType(ResourceConst.EVENT_OBJTYPE.PUT_STORAGE.getCode());
        updateReq.setObjId(resReqId);
        updateReq.setUpdateStaff(req.getUpdateStaff());
        updateReq.setDestStoreId(resourceRequestResp.getDestStoreId());
        updateReq.setMktResStoreId(resourceRequestResp.getMktResStoreId());
        updateReq.setMerchantId(resourceRequestResp.getMerchantId());
        ResultVO<List<String>> updateVO = resourceInstService.updateResourceInstByIds(updateReq);
        log.info("SupplierResourceInstServiceImpl.confirmReciveNbr resourceInstService.updateResourceInstByIds req={}, resp={}", JSON.toJSONString(updateReq), JSON.toJSONString(updateVO));

        // step3 领用方入库
        ResourceInstsGetByIdListAndStoreIdReq selectReq = new ResourceInstsGetByIdListAndStoreIdReq();
        selectReq.setMktResInstIdList(mktResInstIds);
        selectReq.setMktResStoreId(resourceRequestResp.getDestStoreId());
        List<ResourceInstDTO> insts = resourceInstManager.selectByIds(selectReq);
        // 按产品维度组装数据
        Map<String, List<ResourceInstDTO>> map = insts.stream().collect(Collectors.groupingBy(t -> t.getMktResId()));
        ResourceInstPutInReq instPutInReq = new ResourceInstPutInReq();
        instPutInReq.setInsts(map);
        instPutInReq.setCreateStaff(req.getUpdateStaff());
        instPutInReq.setStorageType(ResourceConst.STORAGETYPE.ALLOCATION_AND_WAREHOUSING.getCode());
        instPutInReq.setSourceType(ResourceConst.SOURCE_TYPE.SUPPLIER.getCode());
        instPutInReq.setEventType(ResourceConst.EVENTTYPE.ALLOT.getCode());
        instPutInReq.setMktResStoreId(resourceRequestResp.getMktResStoreId());
        instPutInReq.setDestStoreId(resourceRequestResp.getDestStoreId());
        instPutInReq.setMerchantId(req.getMerchantId());
        ResultVO resultResourceInstPutIn = resourceInstService.resourceInstPutIn(instPutInReq);
        log.info("SupplierResourceInstServiceImpl.confirmReciveNbr resourceInstService.resourceInstPutIn req={}, resp={}", JSON.toJSONString(instPutInReq), JSON.toJSONString(resultResourceInstPutIn));
        return ResultVO.success(instPutInReq.getUnUse());
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResultVO confirmRefuseNbr(ConfirmReciveNbrReq req) {
        log.info("SupplierResourceInstServiceImpl.confirmRefuseNbr req={}, resp={}", JSON.toJSONString(req));
        // step1 申请单修改状态
        ResourceRequestUpdateReq reqUpdate = new ResourceRequestUpdateReq();
        reqUpdate.setMktResReqId(req.getResReqId());
        reqUpdate.setStatusCd(ResourceConst.MKTRESSTATE.CANCEL.getCode());
        requestService.updateResourceRequestState(reqUpdate);
        //step2 根据申请单表保存的源仓库和申请单明细找到对应的串码
        ResourceReqDetailQueryReq queryReq = new ResourceReqDetailQueryReq();
        queryReq.setMktResReqId(req.getResReqId());
        List<ResourceReqDetailDTO> list = resourceReqDetailManager.listDetail(queryReq);
        log.info("SupplierResourceInstServiceImpl.confirmRefuseNbr resourceReqDetailManager.listDetail req={}, resp={}", JSON.toJSONString(queryReq), JSON.toJSONString(list));
        if (CollectionUtils.isEmpty(list)) {
            return ResultVO.error("没有查找到申请单");
        }
        ResultVO<MerchantDTO> merchantResultVO = resouceStoreService.getMerchantByStore(list.get(0).getMktResStoreId());
        if (!merchantResultVO.isSuccess() || null == merchantResultVO.getResultData()) {
            return ResultVO.error(constant.getCannotGetMerchantMsg());
        }
        List<String> mktResInstIds = list.stream().map(ResourceReqDetailDTO::getMktResInstId).collect(Collectors.toList());
        // step3 修改源仓库串码为可用
        AdminResourceInstDelReq updateReq = new AdminResourceInstDelReq();
        List<String> checkStatusCd = new ArrayList<String>(7);
        checkStatusCd.add(ResourceConst.STATUSCD.AUDITING.getCode());
        checkStatusCd.add(ResourceConst.STATUSCD.AVAILABLE.getCode());
        checkStatusCd.add(ResourceConst.STATUSCD.ALLOCATIONED.getCode());
        checkStatusCd.add(ResourceConst.STATUSCD.RESTORAGEING.getCode());
        checkStatusCd.add(ResourceConst.STATUSCD.RESTORAGED.getCode());
        checkStatusCd.add(ResourceConst.STATUSCD.SALED.getCode());
        checkStatusCd.add(ResourceConst.STATUSCD.DELETED.getCode());
        updateReq.setCheckStatusCd(checkStatusCd);
        updateReq.setStatusCd(ResourceConst.STATUSCD.AVAILABLE.getCode());
        updateReq.setEventType(ResourceConst.EVENTTYPE.RECYCLE.getCode());
        updateReq.setMktResInstIds(mktResInstIds);
        updateReq.setEventType(ResourceConst.EVENTTYPE.CANCEL.getCode());
        updateReq.setObjType(ResourceConst.EVENT_OBJTYPE.PUT_STORAGE.getCode());
        updateReq.setObjId(req.getResReqId());
        updateReq.setMerchantId(merchantResultVO.getResultData().getMerchantId());
        updateReq.setMktResStoreId(list.get(0).getDestStoreId());
        updateReq.setDestStoreId(list.get(0).getMktResStoreId());
        ResultVO resp = resourceInstService.updateResourceInstByIds(updateReq);
        log.info("SupplierResourceInstServiceImpl.confirmRefuseNbr resourceInstService.resourceInstPutIn req={}, resp={}", JSON.toJSONString(updateReq), JSON.toJSONString(resp));
        return ResultVO.success();
    }

    @Override
    public ResultVO validResourceInst(DeliveryValidResourceInstReq req) {
        ResouceStoreDTO storeDTO = resouceStoreManager.getStore(req.getMerchantId(), ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
        log.info("SupplierResourceInstServiceImpl.validResourceInst resouceStoreManager.getStore req={}, resp={}", JSON.toJSONString(req), JSON.toJSONString(storeDTO));
        if (null == storeDTO) {
            return ResultVO.error(constant.getCannotGetStoreMsg());
        }
        req.setMktResStoreId(storeDTO.getMktResStoreId());
        return ResultVO.success(resourceInstManager.validResourceInst(req));
    }

    @Override
    public ResultVO validNbr(ResourceInstValidReq req){
        // 集采前端会传入库仓库id,其他类型根据当前登陆用户去获取仓库
        String mktResStoreId = req.getMktResStoreId();
        if (!ResourceConst.MKTResInstType.NONTRANSACTION.getCode().equals(req.getMktResInstType())) {
            StoreGetStoreIdReq storeGetStoreIdReq = new StoreGetStoreIdReq();
            storeGetStoreIdReq.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
            storeGetStoreIdReq.setMerchantId(req.getMerchantId());
            mktResStoreId = resouceStoreService.getStoreId(storeGetStoreIdReq);
            log.info("MerchantResourceInstServiceImpl.validNbr resouceStoreService.getStoreId req={},resp={}", JSON.toJSONString(storeGetStoreIdReq), mktResStoreId);
            if (StringUtils.isBlank(mktResStoreId)) {
                return ResultVO.error(constant.getCannotGetStoreMsg());
            }
        }
        ResourceInstValidReq resourceInstValidReq = new ResourceInstValidReq();
        BeanUtils.copyProperties(req, resourceInstValidReq);
        resourceInstValidReq.setMktResStoreId(mktResStoreId);
        String batchId = runableTask.exceutorValidForSupplier(resourceInstValidReq);
        return ResultVO.success(batchId);
    }

    @Override
    public ResultVO<Page<ResourceInstListPageResp>> getResourceInstListForTask(ResourceInstListPageReq req) {
        log.info("SupplierResourceInstServiceImpl.getResourceInstList req={}", JSON.toJSONString(req));
        // 多线程没跑完，返回空
        if (runableTask.addNbrForSupplierHasDone()) {
            return this.resourceInstService.getResourceInstList(req);
        } else{
            return ResultVO.success();
        }
    }

    @Override
    public ResultVO listResourceUploadTemp(ResourceUploadTempListPageReq req) {
        // 多线程没跑完，返回空
        if (runableTask.validForSupplierHasDone()) {
            return ResultVO.success(resourceUploadTempManager.listResourceUploadTemp(req));
        } else{
            return ResultVO.success();
        }

    }

    @Override
    public ResultVO exceutorAddNbrForSupplier(ResourceInstAddReq req) {
        runableTask.exceutorAddNbrForSupplier(req);
        return ResultVO.success();
    }

    @Override
    public ResultVO addResourceInstByAdmin(ResourceInstAddReq req) {
        log.info("SupplierResourceInstServiceImpl.addResourceInstByAdmin req={}", JSON.toJSONString(req));
        // 获取产品归属厂商
        MerChantGetProductReq merChantGetProductReq = new MerChantGetProductReq();
        merChantGetProductReq.setProductId(req.getMktResId());
        ResultVO<String> productRespResultVO = this.productService.getMerchantByProduct(merChantGetProductReq);
        log.info("SupplierResourceInstServiceImpl.addResourceInstByAdmin productService.getMerchantByProduct req={} resp={}", JSON.toJSONString(merChantGetProductReq), JSON.toJSONString(productRespResultVO));
        if (!productRespResultVO.isSuccess() || StringUtils.isEmpty(productRespResultVO.getResultData())) {
            return ResultVO.error(constant.getCannotGetMuanfacturerMsg());
        }
        // 获取厂商源仓库
        String sourceStoreMerchantId = productRespResultVO.getResultData();
        StoreGetStoreIdReq storeManuGetStoreIdReq = new StoreGetStoreIdReq();
        storeManuGetStoreIdReq.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
        storeManuGetStoreIdReq.setMerchantId(sourceStoreMerchantId);
        String manuResStoreId = resouceStoreService.getStoreId(storeManuGetStoreIdReq);
        log.info("SupplierResourceInstServiceImpl.addResourceInstByAdmin resouceStoreService.getStoreId req={} resp={}", JSON.toJSONString(storeManuGetStoreIdReq), JSON.toJSONString(manuResStoreId));
        if (StringUtils.isEmpty(manuResStoreId)) {
            return ResultVO.error(constant.getCannotGetStoreMsg());
        }
        ResultVO<MerchantDTO> merchantResultVO = resouceStoreService.getMerchantByStore(req.getMktResStoreId());
        log.info("SupplierResourceInstServiceImpl.addResourceInstByAdmin resouceStoreService.getMerchantByStore req={} resp={}", req.getMktResStoreId(), JSON.toJSONString(merchantResultVO));
        if (!merchantResultVO.isSuccess() || null == merchantResultVO.getResultData()) {
            return ResultVO.error(constant.getCannotGetMerchantMsg());
        }
        ResultVO<MerchantDTO> sourceMerchantResultVO = merchantService.getMerchantById(sourceStoreMerchantId);
        log.info("SupplierResourceInstServiceImpl.addResourceInstByAdmin merchantService.getMerchantById req={} resp={}", sourceStoreMerchantId, JSON.toJSONString(sourceMerchantResultVO));
        if (!sourceMerchantResultVO.isSuccess() || null == sourceMerchantResultVO.getResultData()) {
            return ResultVO.error(constant.getCannotGetMerchantMsg());
        }
        MerchantDTO merchantDTO = merchantResultVO.getResultData();
        MerchantDTO sourceMerchantDTO = sourceMerchantResultVO.getResultData();

        ResourceInstAddResp resourceInstAddResp = new ResourceInstAddResp();
        ResourceInstValidReq resourceInstValidReq = new ResourceInstValidReq();
        req.setDestStoreId(req.getMktResStoreId());
        BeanUtils.copyProperties(req, resourceInstValidReq);
        CopyOnWriteArrayList<String> newList = new CopyOnWriteArrayList(req.getMktResInstNbrs());
        List<String> existNbrs = resourceInstCheckService.vaildOwnStore(resourceInstValidReq, newList);
        List<String> mktResInstNbrs = req.getMktResInstNbrs();
        resourceInstAddResp.setExistNbrs(existNbrs);
        mktResInstNbrs.removeAll(existNbrs);
        if(CollectionUtils.isEmpty(mktResInstNbrs)){
            return ResultVO.error("该产品串码已在库，请不要重复录入！");
        }
        List<String> merchantNbrList = resourceInstCheckService.validMerchantStore(resourceInstValidReq);
        if(CollectionUtils.isEmpty(merchantNbrList)){
            return ResultVO.error("厂商库该机型串码不存在！");
        }
        req.setMktResInstNbrs(merchantNbrList);
        req.setDestStoreId(req.getMktResStoreId());
        req.setMktResStoreId(manuResStoreId);
        req.setSourceType(sourceMerchantDTO.getMerchantType());
        req.setLanId(merchantDTO.getLanId());
        req.setRegionId(merchantDTO.getCity());
        req.setMerchantType(merchantDTO.getMerchantType());
        req.setCreateStaff(merchantDTO.getMerchantId());
        mktResInstNbrs.removeAll(merchantNbrList);
        resourceInstAddResp.setPutInFailNbrs(mktResInstNbrs);
        Boolean addNum = resourceInstService.addResourceInst(req);
        if (!addNum) {
            return ResultVO.error("串码入库失败");
        }
        ResourceInstUpdateReq resourceInstUpdateReq = new ResourceInstUpdateReq();
        resourceInstUpdateReq.setDestStoreId(manuResStoreId);
        resourceInstUpdateReq.setMktResInstNbrs(merchantNbrList);
        resourceInstUpdateReq.setMktResStoreId(ResourceConst.NULL_STORE_ID);
        resourceInstUpdateReq.setMerchantId(sourceStoreMerchantId);
        resourceInstUpdateReq.setEventType(ResourceConst.EVENTTYPE.SALE_TO_ORDER.getCode());
        resourceInstUpdateReq.setCheckStatusCd(Lists.newArrayList(
                ResourceConst.STATUSCD.AUDITING.getCode(),
                ResourceConst.STATUSCD.ALLOCATIONED.getCode(),
                ResourceConst.STATUSCD.ALLOCATIONING.getCode(),
                ResourceConst.STATUSCD.RESTORAGEING.getCode(),
                ResourceConst.STATUSCD.RESTORAGED.getCode(),
                ResourceConst.STATUSCD.DELETED.getCode()));
        resourceInstUpdateReq.setStatusCd(ResourceConst.STATUSCD.SALED.getCode());
        resourceInstUpdateReq.setTypeId(req.getTypeId());
        ResultVO updateResourceresultVO = resourceInstService.updateResourceInst(resourceInstUpdateReq);
        if (!updateResourceresultVO.isSuccess()) {
            throw new RetailTipException(ResultCodeEnum.ERROR.getCode(), updateResourceresultVO.getResultMsg());
        }
        return ResultVO.success("串码入库完成", resourceInstAddResp);
    }
}