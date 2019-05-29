package com.iwhalecloud.retail.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.req.ProductResourceInstGetReq;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductResourceResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.dto.MerchantLimitDTO;
import com.iwhalecloud.retail.partner.service.MerchantLimitService;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.system.dto.CommonRegionDTO;
import com.iwhalecloud.retail.system.service.CommonRegionService;
import com.iwhalecloud.retail.warehouse.busiservice.ResouceEventService;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceChngEvtDetailService;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.ResouceStoreDTO;
import com.iwhalecloud.retail.warehouse.dto.ResourceInstDTO;
import com.iwhalecloud.retail.warehouse.dto.ResourceReqDetailDTO;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListPageResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceRequestResp;
import com.iwhalecloud.retail.warehouse.manager.ResourceBatchRecManager;
import com.iwhalecloud.retail.warehouse.manager.ResourceInstManager;
import com.iwhalecloud.retail.warehouse.manager.ResourceReqDetailManager;
import com.iwhalecloud.retail.warehouse.service.ResouceStoreService;
import com.iwhalecloud.retail.warehouse.service.ResourceRequestService;
import com.iwhalecloud.retail.warehouse.service.RetailerResourceInstService;
import com.iwhalecloud.retail.workflow.common.WorkFlowConst;
import com.iwhalecloud.retail.workflow.dto.req.ProcessStartReq;
import com.iwhalecloud.retail.workflow.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RetailerResourceInstServiceImpl implements RetailerResourceInstService {

    @Autowired
    private ResourceInstManager resourceInstManager;

    @Autowired
    private ResouceEventService resouceEventService;

    @Autowired
    private ResourceChngEvtDetailService detailService;

    @Autowired
    private ResourceBatchRecManager batchRecManager;

    @Reference
    private ProductService productService;

    @Autowired
    private ResourceInstService resourceInstService;

    @Reference
    private TaskService taskService;

    @Reference
    private ResourceRequestService resourceRequestService;

    @Reference
    private MerchantService merchantService;

    @Autowired
    private ResourceReqDetailManager detailManager;

    @Reference
    private MerchantLimitService merchantLimitService;

    @Reference
    private ResouceStoreService resouceStoreService;

    @Autowired
    private ResourceReqDetailManager resourceReqDetailManager;

    @Reference
    private ResourceRequestService requestService;

    @Reference
    private CommonRegionService commonRegionService;

    @Override
    @Deprecated
    public ResultVO addResourceInstByGreenChannel(ResourceInstAddReq req) {
        // step1 绿色通道额度校验
        String merchantId = req.getMerchantId();
        String targetId = req.getMktResId();
        Long limitNum = Long.valueOf(req.getMktResInstNbrs().size());
        ResultVO<MerchantLimitDTO> merchantLimitDTO = merchantLimitService.getMerchantLimit(merchantId);
        log.info("RetailerResourceInstServiceImpl.addResourceInstByGreenChannel merchantLimitService.getMerchantLimit merchantId={}, resp={}", merchantId, JSON.toJSONString(merchantLimitDTO));
        if (!merchantLimitDTO.isSuccess() || merchantLimitDTO.getResultData() == null) {
            return ResultVO.error("获取额度失败");
        }
        // 没有配置限额或者配置了并且超额
        Boolean aboveLimit = merchantLimitDTO.getResultData().getMaxSerialNum() < (merchantLimitDTO.getResultData().getSerialNumUsed() + limitNum);

        // 获取仓库
        StoreGetStoreIdReq storeGetStoreIdReq = new StoreGetStoreIdReq();
        storeGetStoreIdReq.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
        storeGetStoreIdReq.setMerchantId(merchantId);
        String mktResStoreId = resouceStoreService.getStoreId(storeGetStoreIdReq);
        log.info("RetailerResourceInstServiceImpl.addResourceInstByGreenChannel resouceStoreService.getStoreId req={},resp={}", JSON.toJSONString(storeGetStoreIdReq), mktResStoreId);
        ResultVO<MerchantDTO> merchantDTOResultVO = merchantService.getMerchantById(merchantId);
        log.info("RetailerResourceInstServiceImpl.addResourceInstByGreenChannel merchantService.getMerchantById req={},resp={}", merchantId, JSON.toJSONString(merchantDTOResultVO));
        if (!merchantDTOResultVO.isSuccess() || null == merchantDTOResultVO.getResultData()) {
            return ResultVO.error("商家获取失败");
        }
        MerchantDTO merchantDTO = merchantDTOResultVO.getResultData();

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
            resourceRequestAddReq.setReqType(ResourceConst.REQTYPE.PUTSTORAGE_APPLYFOR.getCode());
            BeanUtils.copyProperties(req, resourceRequestAddReq);
            resourceRequestAddReq.setInstList(instDTOList);
            resourceRequestAddReq.setReqName(createReqName(req.getProductName(), resourceRequestAddReq.getReqType()));
            resourceRequestAddReq.setChngType(ResourceConst.PUT_IN_STOAGE);
            resourceRequestAddReq.setLanId(merchantDTO.getLanId());
            resourceRequestAddReq.setRegionId(merchantDTO.getCity());

            resourceRequestAddReq.setMktResStoreId(mktResStoreId);
            ResultVO<String> resultVO = resourceRequestService.insertResourceRequest(resourceRequestAddReq);
            log.info("RetailerResourceInstServiceImpl.addResourceInstByGreenChannel() resourceRequestService.insertResourceRequest --> resultVO={}", JSON.toJSONString(resultVO));
            // step3 启动工作流
            ProcessStartReq processStartDTO = new ProcessStartReq();
            processStartDTO.setTitle("绿色通道超过限额审批流程");
            processStartDTO.setApplyUserId(req.getCreateStaff());
            processStartDTO.setApplyUserName(merchantDTO.getMerchantName());
            processStartDTO.setProcessId(ResourceConst.GREEN_CHANNEL_WORK_FLOW_INST);
            processStartDTO.setTaskSubType(WorkFlowConst.TASK_SUB_TYPE.TASK_SUB_TYPE_1090.getTaskSubType());
            processStartDTO.setApplyUserName(req.getApplyUserName());
            if (resultVO != null && resultVO.getResultData() != null) {
                processStartDTO.setFormId(resultVO.getResultData());
            }
            ResultVO<CommonRegionDTO> resultVOLan = commonRegionService.getCommonRegionById(merchantDTO.getLanId());
            ResultVO<CommonRegionDTO> resultVORegion = commonRegionService.getCommonRegionById(merchantDTO.getCity());
            String lanName = "";
            String regionName = "";
            if (resultVOLan.isSuccess() && resultVOLan.getResultData() != null) {
                lanName = resultVOLan.getResultData().getRegionName();
            }
            if (resultVORegion.isSuccess() && resultVORegion.getResultData() != null) {
                regionName = resultVORegion.getResultData().getRegionName();
            }
            String extends1 = lanName + regionName;
            processStartDTO.setExtends1(extends1);
            ResultVO startResultVO = taskService.startProcess(processStartDTO);
            log.info("RetailerResourceInstServiceImpl.addResourceInstByGreenChannel taskService.startProcess req={}, resp={}", JSON.toJSONString(processStartDTO), JSON.toJSONString(startResultVO));
            if (null != startResultVO && startResultVO.getResultCode().equals(ResultCodeEnum.ERROR.getCode())) {
                ResultVO.error();
            }
            ResourceRequestItemQueryReq queryReq = new ResourceRequestItemQueryReq();
            queryReq.setMktResReqId(resultVO.getResultData());
            ResultVO<ResourceRequestResp> respResultVO = resourceRequestService.queryResourceRequest(queryReq);
            return ResultVO.error(ResourceConst.SUCESS_MSG);
        } else {
            return ResultVO.success();
        }
    }

    @Override
    @Deprecated
    public ResultVO delResourceInst(ResourceInstUpdateReq req) {
        log.info("RetailerResourceInstServiceImpl.delResourceInst req={}", JSON.toJSONString(req));
        // 获取仓库
        StoreGetStoreIdReq storeGetStoreIdReq = new StoreGetStoreIdReq();
        storeGetStoreIdReq.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
        storeGetStoreIdReq.setMerchantId(req.getMerchantId());
        String mktResStoreId = resouceStoreService.getStoreId(storeGetStoreIdReq);
        log.info("RetailerResourceInstServiceImpl.delResourceInst resouceStoreService.getStoreId req={},resp={}", JSON.toJSONString(storeGetStoreIdReq), mktResStoreId);
        req.setDestStoreId(mktResStoreId);
        req.setMktResStoreId(ResourceConst.NULL_STORE_ID);
        return resourceInstService.updateResourceInst(req);
    }

    @Override
    @Deprecated
    public ResultVO confirmReciveNbr(ConfirmReciveNbrReq req) {
        // step1 申请单状态改为完成
        String resReqId = req.getResReqId();
        ResourceRequestItemQueryReq resourceRequestReq = new ResourceRequestItemQueryReq();
        resourceRequestReq.setMktResReqId(resReqId);
        ResultVO<ResourceRequestResp> resourceRequestRespVO = requestService.queryResourceRequest(resourceRequestReq);
        String statusCdReviewed = ResourceConst.MKTRESSTATE.REVIEWED.getCode();
        Boolean statusNotRight = !resourceRequestRespVO.isSuccess() || resourceRequestRespVO.getResultData() == null || !statusCdReviewed.equals(resourceRequestRespVO.getResultData().getStatusCd());
        log.info("RetailerResourceInstServiceImpl.confirmRefuseNbr requestService.queryResourceRequest req={},resp={}", JSON.toJSONString(resourceRequestReq),JSON.toJSONString(resourceRequestRespVO));
        if (statusNotRight) {
            return ResultVO.error(ResultCodeEnum.ERROR.getCode(), "申请单状态不正确");
        }

        ResourceRequestResp resourceRequestResp = resourceRequestRespVO.getResultData();
        ResourceRequestUpdateReq reqUpdate = new ResourceRequestUpdateReq();
        reqUpdate.setMktResReqId(resReqId);
        reqUpdate.setStatusCd(ResourceConst.MKTRESSTATE.DONE.getCode());
        ResultVO updateResourceRequestStateVO = requestService.updateResourceRequestState(reqUpdate);
        log.info("RetailerResourceInstServiceImpl.confirmRefuseNbr requestService.updateResourceRequestState req={},resp={}", JSON.toJSONString(reqUpdate),JSON.toJSONString(updateResourceRequestStateVO));

        // step1 找到串码明细
        ResourceReqDetailQueryReq queryReq = new ResourceReqDetailQueryReq();
        queryReq.setMktResReqId(req.getResReqId());
        List<ResourceReqDetailDTO> list = resourceReqDetailManager.listDetail(queryReq);
        log.info("RetailerResourceInstServiceImpl.confirmRefuseNbr resourceReqDetailManager.listDetail req={},resp={}", JSON.toJSONString(queryReq),JSON.toJSONString(list));
        if (null == list || list.isEmpty()) {
            return ResultVO.error("没有查找到申请单");
        }
        List<String> mktResInstIds = list.stream().map(ResourceReqDetailDTO::getMktResInstId).collect(Collectors.toList());
        String mktResStroeId = list.get(0).getMktResStoreId();

        // step2 把状态改为已调拨
        AdminResourceInstDelReq updateReq = new AdminResourceInstDelReq();
        List<String> checkStatusCd = Lists.newArrayList(
                ResourceConst.STATUSCD.AUDITING.getCode(),
                ResourceConst.STATUSCD.AVAILABLE.getCode(),
                ResourceConst.STATUSCD.ALLOCATIONED.getCode(),
                ResourceConst.STATUSCD.RESTORAGEING.getCode(),
                ResourceConst.STATUSCD.RESTORAGED.getCode(),
                ResourceConst.STATUSCD.SALED.getCode(),
                ResourceConst.STATUSCD.DELETED.getCode());
        updateReq.setMktResInstIdList(mktResInstIds);
        updateReq.setCheckStatusCd(checkStatusCd);
        updateReq.setStatusCd(ResourceConst.STATUSCD.ALLOCATIONED.getCode());
        updateReq.setEventType(ResourceConst.EVENTTYPE.ALLOT.getCode());
        updateReq.setObjType(ResourceConst.EVENT_OBJTYPE.PUT_STORAGE.getCode());
        updateReq.setObjId(resReqId);
        updateReq.setDestStoreId(resourceRequestResp.getDestStoreId());
        updateReq.setMktResStoreId(resourceRequestResp.getMktResStoreId());
        ResultVO<List<String>> updateVO = resourceInstService.updateResourceInstByIds(updateReq);
        log.info("RetailerResourceInstServiceImpl.pickResourceInst resourceInstService.updateResourceInstByIds req={}, resp={}", JSON.toJSONString(updateReq), JSON.toJSONString(updateVO));

        // step3 领用方入库
        ResourceInstsGetByIdListAndStoreIdReq selectReq = new ResourceInstsGetByIdListAndStoreIdReq();
        selectReq.setMktResInstIdList(mktResInstIds);
        selectReq.setMktResStoreId(resourceRequestResp.getDestStoreId());
        List<ResourceInstDTO> insts = resourceInstManager.selectByIds(selectReq);
        // 筛选出状态不正确的串码实列,只有调拨中的可用
        String allocationedStatuscd = ResourceConst.STATUSCD.ALLOCATIONED.getCode();
        List<ResourceInstDTO> allocationedInsts = insts.stream().filter(t -> allocationedStatuscd.equals(t.getStatusCd())).collect(Collectors.toList());
        // 按产品维度组装数据
        Map<String, List<ResourceInstDTO>> map = allocationedInsts.stream().collect(Collectors.groupingBy(t -> t.getMktResId()));
        ResourceInstPutInReq instPutInReq = new ResourceInstPutInReq();
        instPutInReq.setMerchantId(req.getMerchantId());
        instPutInReq.setInsts(map);
        instPutInReq.setCreateStaff(req.getUpdateStaff());
        instPutInReq.setStorageType(ResourceConst.STORAGETYPE.ALLOCATION_AND_WAREHOUSING.getCode());
        instPutInReq.setEventType(ResourceConst.EVENTTYPE.SALE_TO_ORDER.getCode());
        instPutInReq.setDestStoreId(resourceRequestResp.getDestStoreId());
        instPutInReq.setMktResStoreId(resourceRequestResp.getMktResStoreId());
        ResultVO resultResourceInstPutIn = resourceInstService.resourceInstPutIn(instPutInReq);
        log.info("RetailerResourceInstServiceImpl.pickResourceInst resourceInstService.resourceInstPutIn req={}, resp={}", JSON.toJSONString(instPutInReq), JSON.toJSONString(resultResourceInstPutIn));
        return ResultVO.success(instPutInReq.getUnUse());
    }

    @Override
    @Deprecated
    public ResultVO confirmRefuseNbr(ConfirmReciveNbrReq req) {
        // step1 申请单修改状态
        ResourceRequestUpdateReq reqUpdate = new ResourceRequestUpdateReq();
        reqUpdate.setMktResReqId(req.getResReqId());
        reqUpdate.setStatusCd(ResourceConst.MKTRESSTATE.CANCEL.getCode());
        requestService.updateResourceRequestState(reqUpdate);

        //step2 根据申请单表保存的源仓库和申请单明细找到对应的串码
        ResourceReqDetailQueryReq queryReq = new ResourceReqDetailQueryReq();
        queryReq.setMktResReqId(req.getResReqId());
        List<ResourceReqDetailDTO> list = resourceReqDetailManager.listDetail(queryReq);
        if (null == list || list.isEmpty()) {
            return ResultVO.error("没有查找到申请单");
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
        updateReq.setMktResInstIdList(mktResInstIds);
        updateReq.setEventType(ResourceConst.EVENTTYPE.CANCEL.getCode());
        updateReq.setObjType(ResourceConst.EVENT_OBJTYPE.PUT_STORAGE.getCode());
        updateReq.setObjId(req.getResReqId());
        resourceInstService.updateResourceInstByIds(updateReq);
        return ResultVO.success();
    }

    @Override
    public ResultVO<Page<ResourceInstListPageResp>> listResourceInst(ResourceInstListPageReq req) {
        return resourceInstService.getResourceInstList(req);
    }

    @Override
    @Deprecated
    public ResultVO retreatStorageResourceInst(RetreatStorageReq req) {
        //step1: 申请单id->串码
        //step2: 零售商串码已退库
        //step3: 供应商还原串码的状态为可用
        //获取营销资源申请单明细，并获取实例的串码
        ResourceReqDetailQueryReq detailQueryReq = new ResourceReqDetailQueryReq();
        detailQueryReq.setMktResReqId(req.getMktResReqId());
        List<ResourceReqDetailDTO> reqDetailDTOS = detailManager.listDetail(detailQueryReq);
        log.info("RetailerResourceInstServiceImpl.retreatStorageResourceInst detailManager.listDetail req={}, resp={}", JSON.toJSONString(detailQueryReq), JSON.toJSONString(reqDetailDTOS));
        List<String> mktResInstNbrs = reqDetailDTOS.stream().map(ResourceReqDetailDTO::getMktResInstNbr).collect(Collectors.toList());
        String mktResStoreId = reqDetailDTOS.get(0).getMktResStoreId();
        ResourceInstUpdateReq retailerUpdateReq = new ResourceInstUpdateReq();
        retailerUpdateReq.setMktResInstNbrs(mktResInstNbrs);
        retailerUpdateReq.setMktResStoreId(mktResStoreId);
        retailerUpdateReq.setStatusCd(ResourceConst.REQTYPE.BACKSTORAGE_APPLYFOR.getCode());
        Integer num = resourceInstManager.updateResourceInst(retailerUpdateReq);
        log.info("RetailerResourceInstServiceImpl.retreatStorageResourceInst resourceInstManager.updateResourceInst req={}, resp={}", JSON.toJSONString(retailerUpdateReq), JSON.toJSONString(num));
        ResourceInstUpdateReq supplierUpdateReq = new ResourceInstUpdateReq();
        supplierUpdateReq.setMktResInstNbrs(mktResInstNbrs);
        supplierUpdateReq.setMktResStoreId(mktResStoreId);
        num = resourceInstManager.updateResourceInst(supplierUpdateReq);
        log.info("RetailerResourceInstServiceImpl.retreatStorageResourceInst resourceInstManager.updateResourceInst req={}, resp={}", JSON.toJSONString(supplierUpdateReq), JSON.toJSONString(num));
        return ResultVO.success();
    }

    @Override
    @Deprecated
    public ResultVO<List<ResourceInstListPageResp>> getBatch(ResourceInstBatchReq req) {
        List<ResourceInstListPageResp> list = resourceInstManager.getBatch(req);
        // 添加产品信息
        for (ResourceInstListPageResp resp : list) {
            String productId = resp.getMktResId();
            ProductResourceInstGetReq queryReq = new ProductResourceInstGetReq();
            queryReq.setProductId(productId);
            ResultVO<List<ProductResourceResp>> resultVO = productService.getProductResource(queryReq);
            log.info("RetailerResourceInstServiceImpl.getBatch productService.getProductResource req={},resp={}", JSON.toJSONString(queryReq), JSON.toJSONString(resultVO));
            List<ProductResourceResp> prodList = resultVO.getResultData();
            if (null != prodList && !prodList.isEmpty()) {
                ProductResourceResp prodResp = prodList.get(0);
                BeanUtils.copyProperties(prodResp, resp);
            }
        }
        return ResultVO.success(list);
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

    /**
     * 判断是否需要审核环节：
     * 1）判断是否跨零售商，即判断源仓库与目标仓库的商家是否同一经营主体，一致则进行下一个判断；不一致，则申请单流转到调出方地市管理员进行审核；
     * 2）判断是否跨地市，即判断源仓库与目标仓库本地网标识是否一致，一致则不需要审核，申请单流转到目标仓库处理人进行确认；不一致则进行下一个判断
     * 3、需要审核环节的，申请单流转到配置的运营人员进行审核，审核通过的，申请单流转到目标仓库处理人；不通过则打回到申请单发起人；
     * 4、目标仓库处理人进行调拨收货确认，确认后串码进行入库操作；
     */
    @Override
    @Deprecated
    public ResultVO allocateResourceInst(RetailerResourceInstAllocateReq req) {
        //step 1: 校验是否需要审核单
        ResultVO<ResouceStoreDTO> destStoreVO = resouceStoreService.getResouceStore(req.getDestStoreId());
        log.info("RetailerResourceInstServiceImpl.allocateResourceInst resouceStoreService.getResouceStore1 req={},resp={}", req.getDestStoreId(), JSON.toJSONString(destStoreVO));
        ResultVO<ResouceStoreDTO> sourceStoreVO = resouceStoreService.getResouceStore(req.getMktResStoreId());
        log.info("RetailerResourceInstServiceImpl.allocateResourceInst resouceStoreService.getResouceStore2 req={},resp={}", req.getMktResStoreId(), JSON.toJSONString(sourceStoreVO));
        // 是否跨地市
        Boolean isTransRegional = false;
        Boolean stroeIsNotNull = destStoreVO.isSuccess() && destStoreVO.getResultData() != null && sourceStoreVO.isSuccess() && sourceStoreVO.getResultData() != null;
        if (stroeIsNotNull) {
            isTransRegional = !destStoreVO.getResultData().getLanId().equals(sourceStoreVO.getResultData().getLanId());
        }

        //根据仓库查使用对象
        ResultVO<MerchantDTO> destMerchantVO = resouceStoreService.getMerchantByStore(req.getDestStoreId());
        log.info("RetailerResourceInstServiceImpl.allocateResourceInst resouceStoreService.getMerchantByStore req={},resp={}", req.getDestStoreId(), JSON.toJSONString(destMerchantVO));
        MerchantDTO destMerchantDTO = destMerchantVO.getResultData();
        if (null == destMerchantDTO) {
            return ResultVO.error("商家获取失败");
        }
        ResultVO<MerchantDTO> sorctMerchantVO = resouceStoreService.getMerchantByStore(req.getMktResStoreId());
        log.info("RetailerResourceInstServiceImpl.allocateResourceInst resouceStoreService.getMerchantByStore req={},resp={}", req.getMktResStoreId(), JSON.toJSONString(sorctMerchantVO));
        MerchantDTO sourceMerchantDTO = sorctMerchantVO.getResultData();
        if (null == sourceMerchantDTO) {
            return ResultVO.error("商家获取失败");
        }
        // 是否商家是否同一经营主体
        Boolean isNotSameMerchant = !destMerchantDTO.getBusinessEntityCode().equals(sourceMerchantDTO.getBusinessEntityCode());
        // step3 如果跨地市或不属于同一个商家实体需要审核，申请单状态为处理中
        String requestStatusCd = ResourceConst.MKTRESSTATE.REVIEWED.getCode();
        String successMessage = ResourceConst.ALLOCATE_SUCESS_MSG;
        if (isTransRegional || isNotSameMerchant) {
            requestStatusCd = ResourceConst.MKTRESSTATE.PROCESSING.getCode();
            successMessage = ResourceConst.ALLOCATE_AUDITING_MSG;
        }
        //新增申请单
        ResourceInstsGetByIdListAndStoreIdReq selectReq = new ResourceInstsGetByIdListAndStoreIdReq();
        selectReq.setMktResInstIdList(req.getMktResInstIds());
        selectReq.setMktResStoreId(req.getMktResStoreId());
        List<ResourceInstDTO> resourceInstDTOList = resourceInstManager.selectByIds(selectReq);
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
        resourceRequestAddReq.setStatusCd(requestStatusCd);
        resourceRequestAddReq.setChngType(ResourceConst.PUT_IN_STOAGE);
        resourceRequestAddReq.setInstList(resourceRequestInsts);
        resourceRequestAddReq.setLanId(destMerchantDTO.getLanId());
        resourceRequestAddReq.setRegionId(destMerchantDTO.getCity());
        ResultVO<String> resultVOInsertResReq = resourceRequestService.insertResourceRequest(resourceRequestAddReq);
        log.info("RetailerResourceInstServiceImpl.allocateResourceInst resourceRequestService.insertResourceRequest req={},resp={}", JSON.toJSONString(resourceRequestAddReq), JSON.toJSONString(resultVOInsertResReq));
        if (resultVOInsertResReq == null || resultVOInsertResReq.getResultData() == null) {
            return ResultVO.error("添加申请单失败");
        }

        // step2 修改源仓库串码为调拨中
        String createStaff = req.getCreateStaff();
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
        updateReq.setUpdateStaff(req.getCreateStaff());
        updateReq.setEventType(ResourceConst.EVENTTYPE.ALLOT.getCode());
        updateReq.setObjType(ResourceConst.EVENT_OBJTYPE.PUT_STORAGE.getCode());
        updateReq.setObjId(resultVOInsertResReq.getResultData());
        updateReq.setDestStoreId(destStoreVO.getResultData().getMktResStoreId());
        updateReq.setMktResStoreId(sourceStoreVO.getResultData().getMktResStoreId());
        ResultVO updateResultVO = resourceInstService.updateResourceInstByIds(updateReq);
        log.info("RetailerResourceInstServiceImpl.allocateResourceInst resourceInstService.updateResourceInstByIds req={},resp={}", JSON.toJSONString(updateReq), JSON.toJSONString(updateResultVO));
        List<String> unUse = (List<String>) updateResultVO.getResultData();
        if (null != unUse && unUse.size() == req.getMktResInstIds().size()) {
            return ResultVO.error("串码id不正确");
        }

        // step4 如果不需要审核则发起流程目标仓库处理人，由目标仓库处理人决定是否接受
        // 流程ID先传个1，业务ID传个唯一的值就好
        String uuid = resourceInstManager.getPrimaryKey();
        ProcessStartReq processStartDTO = new ProcessStartReq();
        processStartDTO.setTitle("调拨审批流程");
        processStartDTO.setApplyUserId(createStaff);
        processStartDTO.setApplyUserName(sourceMerchantDTO.getMerchantName());
        processStartDTO.setProcessId(ResourceConst.ALLOCATE_WORK_FLOW_INST);
        processStartDTO.setFormId(resultVOInsertResReq.getResultData());
        processStartDTO.setTaskSubType(WorkFlowConst.TASK_SUB_TYPE.TASK_SUB_TYPE_1010.getTaskSubType());

        ResultVO<CommonRegionDTO> resultVOLan = commonRegionService.getCommonRegionById(sourceMerchantDTO.getLanId());
        ResultVO<CommonRegionDTO> resultVORegion = commonRegionService.getCommonRegionById(sourceMerchantDTO.getCity());
        StringBuilder extends1 = new StringBuilder();
        if (resultVOLan.isSuccess() && resultVOLan.getResultData() != null) {
            extends1.append(resultVOLan.getResultData().getRegionName());
        }
        if (resultVORegion.isSuccess() && resultVORegion.getResultData() != null) {
            extends1.append(resultVORegion.getResultData().getRegionName());
        }
        processStartDTO.setExtends1(extends1.toString());
        ResultVO taskServiceRV = new ResultVO();
        try {
            taskServiceRV = taskService.startProcess(processStartDTO);
        } catch (Exception ex) {
            log.error("发起工作流异常", ex);
        } finally {
            log.info("RetailerResourceInstServiceImpl.allocateResourceInst taskService.startProcess req={},resp={}", JSON.toJSONString(processStartDTO), JSON.toJSONString(taskServiceRV));
        }
        if (!CollectionUtils.isEmpty(unUse)) {
            return ResultVO.success("失败串码", unUse);
        }
        return ResultVO.success(successMessage);
    }

    @Override
    @Deprecated
    public ResultVO pickResourceInst(ResourceInstPickupReq req) {
        return ResultVO.success();
    }

}