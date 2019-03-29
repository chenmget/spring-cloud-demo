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
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.system.dto.CommonRegionDTO;
import com.iwhalecloud.retail.system.service.CommonRegionService;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.constant.Constant;
import com.iwhalecloud.retail.warehouse.dto.*;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceRequestResp;
import com.iwhalecloud.retail.warehouse.manager.*;
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
import java.util.List;
import java.util.Map;
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
    private Constant constant;


    @Override
    //todo 事务先去掉，影响主流程 200_539
//    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResultVO addResourceInst(ResourceInstAddReq req) {
        log.info("SupplierResourceInstServiceImpl.addResourceInst req={}", JSON.toJSONString(req));
        // 通过串码找厂商仓库，找到跳出
        for (String nbr : req.getMktResInstNbrs()) {
            String storeId = resourceInstService.getMerchantStoreIdByNbr(nbr);
            log.info("SupplierResourceInstServiceImpl.addResourceInst resourceInstService.getMerchantStoreIdByNbr storeId={}", storeId);
            if (StringUtils.isNotBlank(storeId)) {
                req.setMktResStoreId(storeId);
                break;
            }
        }
        String merchantId = req.getMerchantId();
        ResultVO<MerchantDTO> merchantDTOResultVO = merchantService.getMerchantById(req.getMerchantId());
        if (!merchantDTOResultVO.isSuccess() || null == merchantDTOResultVO.getResultData()) {
            return ResultVO.error(constant.getCannotGetMerchantMsg());
        } else {
            MerchantDTO merchantDTO = merchantDTOResultVO.getResultData();
            req.setMerchantType(merchantDTO.getMerchantType());
            req.setMerchantName(merchantDTO.getMerchantName());
            req.setMerchantCode(merchantDTO.getMerchantCode());
            req.setLanId(merchantDTO.getLanId());
            req.setRegionId(merchantDTO.getCity());
        }
        // 获取仓库
        StoreGetStoreIdReq storeGetStoreIdReq = new StoreGetStoreIdReq();
        storeGetStoreIdReq.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
        storeGetStoreIdReq.setMerchantId(merchantId);
        String mktResStoreId = resouceStoreService.getStoreId(storeGetStoreIdReq);
        log.info("SupplierResourceInstServiceImpl.addResourceInst resouceStoreService.getStoreId req={},resp={}", JSON.toJSONString(storeGetStoreIdReq), mktResStoreId);
        if (StringUtils.isBlank(mktResStoreId)) {
            return ResultVO.error(constant.getCannotGetStoreMsg());
        }
        req.setDestStoreId(mktResStoreId);
        return resourceInstService.addResourceInst(req);
    }

    @Override
    //todo 事务先去掉，影响主流程 200_539
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResultVO delResourceInst(ResourceInstUpdateReq req) {
        List<String> nbrs = Lists.newArrayList(req.getMktResInstNbrs().stream().distinct().collect(Collectors.toList()));
        List<String> productList = new ArrayList<>();

        // 获取仓库
        StoreGetStoreIdReq storeGetStoreIdReq = new StoreGetStoreIdReq();
        storeGetStoreIdReq.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
        storeGetStoreIdReq.setMerchantId(req.getMerchantId());
        String mktResStoreId = resouceStoreService.getStoreId(storeGetStoreIdReq);
        log.info("SupplierResourceInstServiceImpl.delResourceInst resouceStoreService.getStoreId req={},resp={}", JSON.toJSONString(storeGetStoreIdReq), mktResStoreId);

        // 串码实列状态更新为删除,现在支持导入删除，前端传不了主键
        Integer num = 0;
        if (null != nbrs && !nbrs.isEmpty()) {
            req.setMktResStoreId(mktResStoreId);
            num = resourceInstManager.updateResourceInst(req);
        }

        for (String nbr : nbrs) {
            String changeStatusCd = req.getStatusCd();
            ResourceInstGetReq queryReq = new ResourceInstGetReq();
            queryReq.setMktResInstNbr(nbr);
            queryReq.setMerchantId(req.getMerchantId());
            ResourceInstDTO inst = resourceInstManager.getResourceInst(queryReq);
            log.info("SupplierResourceInstServiceImpl.delResourceInst resourceInstManager.getResourceInst req={},resp={}", JSON.toJSONString(queryReq), JSON.toJSONString(inst));
            String statusCd = inst.getStatusCd();
            // 没删除成功的，不加日志及更新库存
            if (null == inst || !changeStatusCd.equals(inst.getStatusCd())) {
                continue;
            }
            String productId = inst.getMktResId();
            Double salePrice = inst.getSalesPrice() == null ? 0D : inst.getSalesPrice();
            String eventId = "";

            if (!productList.contains(productId)) {
                // 增加事件
                ResouceEventDTO eventDTO = new ResouceEventDTO();
                BeanUtils.copyProperties(inst, eventDTO);
                eventDTO.setEventType(ResourceConst.EVENTTYPE.PUT_STORAGE.getCode());
                eventDTO.setDestStoreId(mktResStoreId);
                eventDTO.setMktResStoreId(ResourceConst.NULL_STORE_ID);
                eventId = resouceEventManager.insertResouceEvent(eventDTO);
                log.info("SupplierResourceInstServiceImpl.delResourceInst resouceEventManager.insertResouceEvent req={},resp={}", JSON.toJSONString(eventDTO), JSON.toJSONString(eventId));
                // DOWN 增加批次，不同的产品Id不同的事件、批次
                ResourceBatchRecDTO batchRecDTO = new ResourceBatchRecDTO();
                BeanUtils.copyProperties(inst, batchRecDTO);
                batchRecDTO.setCostPrice(salePrice);
                String size = String.valueOf(nbrs.size());
                batchRecDTO.setQuantity(Long.parseLong(size));
                String batchId = batchRecManager.insertResourceBatchRec(batchRecDTO);
                log.info("SupplierResourceInstServiceImpl.delResourceInst batchRecManager.insertResourceBatchRec req={},resp={}", JSON.toJSONString(batchRecDTO), JSON.toJSONString(batchId));
                productList.add(productId);
            }
            // 增加日志
            ResourceChngEvtDetailDTO detailDTO = new ResourceChngEvtDetailDTO();
            BeanUtils.copyProperties(inst, detailDTO);
            detailDTO.setChngType(ResourceConst.OUT_PUT_STOAGE);
            detailDTO.setMktResInstId(inst.getMktResInstId());
            detailDTO.setMktResInstNbr(nbr);
            detailDTO.setMktResEventId(eventId);
            int chngEvtDetailCnt = detailManager.insertChngEvtDetail(detailDTO);
            log.info("SupplierResourceInstServiceImpl.delResourceInst  detailManager.insertChngEvtDetail req={},resp={}", JSON.toJSONString(detailDTO), JSON.toJSONString(chngEvtDetailCnt));
            // 更新库存 可能是不同仓库的串码,所以一个串码更新一次
            ResourceInstStoreDTO resourceInstStoreDTO = new ResourceInstStoreDTO();
            BeanUtils.copyProperties(inst, resourceInstStoreDTO);
            resourceInstStoreDTO.setQuantity(1L);
            resourceInstStoreDTO.setQuantityAddFlag(false);
            resourceInstStoreDTO.setStatusCd(ResourceConst.STATUSCD.AVAILABLE.getCode());
            int restInstStoreCnt = resourceInstStoreManager.updateResourceInstStore(resourceInstStoreDTO);
            log.info("SupplierResourceInstServiceImpl.delResourceInst resourceInstStoreManager.updateResourceInstStore req={},resp={}", JSON.toJSONString(resourceInstStoreDTO), JSON.toJSONString(restInstStoreCnt));
        }
        return ResultVO.success(num);
    }

    @Override
    public ResultVO resetResourceInst(ResourceInstUpdateReq req) {
        List<String> nbrs = Lists.newArrayList(req.getMktResInstNbrs().stream().distinct().collect(Collectors.toList()));
        List<String> unavailbaleNbrs = new ArrayList<>();
        List<String> productList = new ArrayList<>();

        // 串码实列状态更新为删除
        ResouceStoreDTO store = resouceStoreManager.getStore(req.getMerchantId(), ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
        log.info("ResourceInstServiceImpl.resetResourceInst resouceStoreManager.getStore merchantId={},resp={}", req.getMerchantId(), JSON.toJSONString(store));
        if (null == store) {
            return ResultVO.error("获取仓库失败");
        }
        String mktResStroeId = store.getMktResStoreId();
        req.setMktResStoreId(mktResStroeId);
        Integer num = resourceInstManager.updateResourceInst(req);
        log.info("ResourceInstServiceImpl.resetResourceInst resourceInstManager.updateResourceInst merchantId={},resp={}", JSON.toJSONString(store), num);

        for (String nbr : nbrs) {
            ResourceInstGetReq queryReq = new ResourceInstGetReq();
            queryReq.setMktResInstNbr(nbr);
            queryReq.setMerchantId(req.getMerchantId());
            ResourceInstDTO inst = resourceInstManager.getResourceInst(queryReq);
            log.info("SupplierResourceInstServiceImpl.resetResourceInst resourceInstManager.getResourceInst req={},resp={}", JSON.toJSONString(queryReq), JSON.toJSONString(inst));
            String statusCd = inst.getStatusCd();
            String productId = inst.getMktResId();
            Double salePrice = inst.getSalesPrice() == null ? 0D : inst.getSalesPrice();
            String eventId = "";
            if (!req.getStatusCd().equals(statusCd)) {
                unavailbaleNbrs.add(nbr);
                continue;
            }
            if (!productList.contains(productId)) {
                // 增加事件
                ResouceEventDTO eventDTO = new ResouceEventDTO();
                BeanUtils.copyProperties(inst, eventDTO);
                eventDTO.setEventType(ResourceConst.EVENTTYPE.PUT_STORAGE.getCode());
                eventDTO.setDestStoreId(mktResStroeId);
                eventDTO.setMktResStoreId(ResourceConst.NULL_STORE_ID);
                eventId = resouceEventManager.insertResouceEvent(eventDTO);
                log.info("SupplierResourceInstServiceImpl.resetResourceInst resouceEventManager.insertResouceEvent req={},resp={}", JSON.toJSONString(eventDTO), JSON.toJSONString(eventId));
                // DOWN 增加批次，不同的产品Id不同的事件、批次
                ResourceBatchRecDTO batchRecDTO = new ResourceBatchRecDTO();
                BeanUtils.copyProperties(inst, batchRecDTO);
                batchRecDTO.setCostPrice(salePrice);
                String size = String.valueOf(nbrs.size());
                batchRecDTO.setQuantity(Long.parseLong(size));
                String batchId = batchRecManager.insertResourceBatchRec(batchRecDTO);
                log.info("SupplierResourceInstServiceImpl.resetResourceInst batchRecManager.insertResourceBatchRec req={},resp={}", JSON.toJSONString(batchRecDTO), JSON.toJSONString(batchId));
                productList.add(productId);
            }
            // 增加日志
            ResourceChngEvtDetailDTO detailDTO = new ResourceChngEvtDetailDTO();
            BeanUtils.copyProperties(inst, detailDTO);
            detailDTO.setChngType(ResourceConst.OUT_PUT_STOAGE);
            detailDTO.setMktResInstId(inst.getMktResInstId());
            detailDTO.setMktResInstNbr(nbr);
            detailDTO.setMktResEventId(eventId);
            int chngEvtDetailCnt = detailManager.insertChngEvtDetail(detailDTO);
            log.info("SupplierResourceInstServiceImpl.resetResourceInst  detailManager.insertChngEvtDetail req={},resp={}", JSON.toJSONString(detailDTO), JSON.toJSONString(chngEvtDetailCnt));
            // 更新库存 可能是不同仓库的串码,所以一个串码更新一次
            ResourceInstStoreDTO resourceInstStoreDTO = new ResourceInstStoreDTO();
            BeanUtils.copyProperties(inst, resourceInstStoreDTO);
            resourceInstStoreDTO.setQuantity(1L);
            resourceInstStoreDTO.setMktResStoreId(mktResStroeId);
            Boolean quantityAddFlag = false;
            if (ResourceConst.STATUSCD.AVAILABLE.equals(req.getStatusCd())) {
                quantityAddFlag = true;
            }
            resourceInstStoreDTO.setQuantityAddFlag(quantityAddFlag);
            int restInstStoreCnt = resourceInstStoreManager.updateResourceInstStore(resourceInstStoreDTO);
            log.info("SupplierResourceInstServiceImpl.resetResourceInst resourceInstStoreManager.updateResourceInstStore req={},resp={}", JSON.toJSONString(resourceInstStoreDTO), JSON.toJSONString(restInstStoreCnt));
        }
        // 去除库中不存在串码及状态不可用串码
        nbrs.removeAll(unavailbaleNbrs);
        // 不能删除的串码返回
        if (!unavailbaleNbrs.isEmpty()) {
            return ResultVO.success(unavailbaleNbrs);
        }
        return ResultVO.success();
    }

    @Override
    public ResultVO<Page<ResourceInstListResp>> getResourceInstList(ResourceInstListReq req) {
        log.info("SupplierResourceInstServiceImpl.getResourceInstList req={}", JSON.toJSONString(req));
        return this.resourceInstService.getResourceInstList(req);
    }

    @Override
    public ResultVO<Page<ResourceInstListResp>> listResourceInst(ResourceInstListReq req) {
        if (StringUtils.isNotBlank(req.getUnitTypeName()) || StringUtils.isNotBlank(req.getBrandId()) || StringUtils.isNotBlank(req.getSn())) {
            ProductResourceInstGetReq queryReq = new ProductResourceInstGetReq();
            BeanUtils.copyProperties(req, queryReq);
            ResultVO<List<ProductResourceResp>> resultVO = productService.getProductResource(queryReq);
            log.info("SupplierResourceInstServiceImpl.listResourceInst productService.getProductResource req={},resp={}", JSON.toJSONString(queryReq), JSON.toJSONString(resultVO));
            List<ProductResourceResp> list = resultVO.getResultData();
            if (CollectionUtils.isNotEmpty(list)) {
                req.setMktResIds(list.stream().map(ProductResourceResp::getProductId).collect(Collectors.toList()));
            }
        }

        Page<ResourceInstListResp> page = resourceInstManager.getResourceInstList(req);
        log.info("SupplierResourceInstServiceImpl.listResourceInst resourceInstManager.getResourceInstList req={},resp={}", JSON.toJSONString(req), JSON.toJSONString(page));
        List<ResourceInstListResp> list = page.getRecords();
        if (CollectionUtils.isEmpty(list)) {
            return ResultVO.success(page);
        }

        // 添加产品信息
        for (ResourceInstListResp resp : list) {
            String productId = resp.getMktResId();
            ProductResourceInstGetReq queryReq = new ProductResourceInstGetReq();
            queryReq.setProductId(productId);
            ResultVO<List<ProductResourceResp>> resultVO = productService.getProductResource(queryReq);
            log.info("SupplierResourceInstServiceImpl.listResourceInst productService.getProductResource req={},resp={}", JSON.toJSONString(queryReq), JSON.toJSONString(resultVO));
            List<ProductResourceResp> prodList = resultVO.getResultData();
            if (CollectionUtils.isNotEmpty(prodList)) {
                ProductResourceResp prodResp = prodList.get(0);
                resp.setUnitTypeName(prodResp.getUnitTypeName());
                resp.setUnitType(prodResp.getUnitType());
                resp.setProductName(prodResp.getProductName());
                resp.setCatName(prodResp.getCatName());
                resp.setBrandName(prodResp.getBrandName());
                resp.setTypeName(prodResp.getTypeName());
            }
        }
        return ResultVO.success(page);
    }

    /**
     * 判断是否需要审核环节：
     * 1）判断是否跨地市，即判断源仓库与目标仓库本地网标识是否一致，一致则不需要审核，申请单流转到目标仓库处理人进行确认；不一致则进行下一个判断
     * 2）判断是否有补贴，根据订单编号查询是否参与了补贴活动，目前补贴活动未设计，若有参与补贴，则需要审核环节；
     * 3、需要审核环节的，申请单流转到配置的运营人员进行审核，审核通过的，申请单流转到目标仓库处理人；不通过则打回到申请单发起人；
     * 4、目标仓库处理人进行调拨收货确认，确认后串码进行入库操作；
     */
    @Override
    //todo 事务先去掉，影响主流程 200_539
//    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResultVO allocateResourceInst(SupplierResourceInstAllocateReq req) {
        //根据仓库查使用对象
        ResultVO<MerchantDTO> sorctMerchantVO = resouceStoreService.getMerchantByStore(req.getMktResStoreId());
        log.info("SupplierResourceInstServiceImpl.allocateResourceInst resouceStoreService.getMerchantByStore req={},resp={}", req.getMktResStoreId(), JSON.toJSONString(sorctMerchantVO));
        MerchantDTO sourceMerchantDTO = sorctMerchantVO.getResultData();
        if (null == sourceMerchantDTO) {
            return ResultVO.error("商家获取失败");
        }

        String requestStatusCd = ResourceConst.MKTRESSTATE.REVIEWED.getCode();
        //step 1: 校验是否需要审核单
        ResultVO<ResouceStoreDTO> destStoreVO = resouceStoreService.getResouceStore(req.getDestStoreId());
        log.info("SupplierResourceInstServiceImpl.allocateResourceInst resouceStoreService.getResouceStore1 req={},resp={}", req.getDestStoreId(), JSON.toJSONString(destStoreVO));
        ResultVO<ResouceStoreDTO> sourceStoreVO = resouceStoreService.getResouceStore(req.getMktResStoreId());
        log.info("SupplierResourceInstServiceImpl.allocateResourceInst resouceStoreService.getResouceStore2 req={},resp={}", req.getMktResStoreId(), JSON.toJSONString(sourceStoreVO));
        // 是否跨地市
        Boolean isTransRegional = false;
        Boolean stroeIsNotNull = destStoreVO.isSuccess() && destStoreVO.getResultData() != null && sourceStoreVO.isSuccess() && sourceStoreVO.getResultData() != null;
        if (stroeIsNotNull) {
            isTransRegional = !destStoreVO.getResultData().getLanId().equals(sourceStoreVO.getResultData().getLanId());
        }

        // 是否有前置补贴
        ResultVO<Boolean> nbrHasActivityVO = resourceInstService.nbrHasActivity(req.getNbrAndProductId());
        log.info("SupplierResourceInstServiceImpl.allocateResourceInst resourceInstService.nbrHasActivity req={},resp={}", req.getNbrAndProductId(), JSON.toJSONString(nbrHasActivityVO));
        Boolean nbrHasActivity = false;
        if (nbrHasActivityVO.isSuccess()) {
            nbrHasActivity = nbrHasActivityVO.getResultData();
        }

        // step3 如果跨地市或有前置补贴需要审核，申请单状态为处理中
        String successMessage = ResourceConst.ALLOCATE_SUCESS_MSG;
        if (isTransRegional || nbrHasActivity) {
            requestStatusCd = ResourceConst.MKTRESSTATE.PROCESSING.getCode();
            successMessage = ResourceConst.ALLOCATE_AUDITING_MSG;
        }

        //新增申请单
        List<ResourceInstDTO> resourceInstDTOList = resourceInstService.selectByIds(req.getMktResInstIds());
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
        resourceRequestAddReq.setLanId(sourceMerchantDTO.getLanId());
        resourceRequestAddReq.setRegionId(sourceMerchantDTO.getCity());
        ResultVO<String> resultVOInsertResReq = resourceRequestService.insertResourceRequest(resourceRequestAddReq);
        if (resultVOInsertResReq == null || resultVOInsertResReq.getResultData() == null) {
            return ResultVO.error("添加申请单失败");
        }
        log.info("SupplierResourceInstServiceImpl.allocateResourceInst resourceRequestService.insertResourceRequest req={},resp={}", JSON.toJSONString(resourceRequestAddReq), JSON.toJSONString(resultVOInsertResReq));

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
        updateReq.setMktResStoreId(sourceStoreVO.getResultData().getMktResStoreId());
        updateReq.setDestStoreId(destStoreVO.getResultData().getMktResStoreId());
        ResultVO updateResultVO = resourceInstService.updateResourceInstByIds(updateReq);
        log.info("SupplierResourceInstServiceImpl.allocateResourceInst resourceInstService.updateResourceInstByIds req={},resp={}", JSON.toJSONString(updateReq), JSON.toJSONString(updateResultVO));
        List<String> unUse = (List<String>) updateResultVO.getResultData();
        if (null != unUse && unUse.size() == req.getMktResInstIds().size()) {
            return ResultVO.error("串码id不正确");
        }

        //根据仓库查使用对象
        ResultVO<MerchantDTO> resultVO = resouceStoreService.getMerchantByStore(req.getDestStoreId());
        log.info("SupplierResourceInstServiceImpl.allocateResourceInst resouceStoreService.getMerchantByStore req={},resp={}", JSON.toJSONString(req.getDestStoreId()), JSON.toJSONString(resultVO));
        MerchantDTO merchantDTO = resultVO.getResultData();
        if (null == merchantDTO) {
            return ResultVO.error("商家获取失败");
        }

        // step4 发起流程到目标商家，由目标商家决定是否接受
        // 流程ID先传个1，业务ID传个唯一的值就好
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
            log.error("供应商串码调拨发起工作流异常", ex);
        } finally {
            log.info("SupplierResourceInstServiceImpl.allocateResourceInst taskService.startProcess req={},resp={}", JSON.toJSONString(processStartDTO), JSON.toJSONString(taskServiceRV));
        }
        if (!CollectionUtils.isEmpty(unUse)) {
            return ResultVO.success("失败串码", unUse);
        }
        log.info("RetailerResourceInstMarketServiceImpl successMessage={}", successMessage);
        return ResultVO.success(successMessage);
    }

    @Override
    public ResultVO validResourceInst(ValidResourceInstReq req) {
        ResultVO<MerchantDTO> merchantResultVO = merchantService.getMerchantById(req.getMerchantId());
        log.info("ResourceInstServiceImpl.validResourceInst merchantService.getMerchantById req={},resp={}", JSON.toJSONString(req), JSON.toJSONString(merchantResultVO));
        if (merchantResultVO != null && merchantResultVO.isSuccess() && merchantResultVO.getResultData() != null && merchantResultVO.getResultData().getMerchantType() != null) {
            req.setMerchantType(merchantResultVO.getResultData().getMerchantType());
            return resourceInstManager.validResourceInst(req);
        } else {
            return ResultVO.error("商家不存在");
        }
    }

    @Override
    public ResultVO deliveryOutResourceInst(DeliveryResourceInstReq req) {
        StoreGetStoreIdReq storePageReq = new StoreGetStoreIdReq();
        storePageReq.setMerchantId(req.getSellerMerchantId());
        storePageReq.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
        String storeId = resouceStoreService.getStoreId(storePageReq);
        log.info("ResourceInstServiceImpl.deliveryOutResourceInst resouceStoreService.getStoreId req={},storeId={}", JSON.toJSONString(storePageReq), JSON.toJSONString(storeId));

        storePageReq.setMerchantId(req.getBuyerMerchantId());
        String destStoreId = resouceStoreService.getStoreId(storePageReq);
        log.info("ResourceInstServiceImpl.deliveryOutResourceInst resouceStoreService.getStoreId req={},destStoreId={}", JSON.toJSONString(storePageReq), JSON.toJSONString(destStoreId));

        List<String> mktResInstNbrs = new ArrayList<>();
        for (DeliveryResourceInstItem item : req.getDeliveryResourceInstItemList()) {
            mktResInstNbrs.addAll(item.getMktResInstNbrs());
        }
        ResourceInstUpdateReq resourceInstUpdateReq = new ResourceInstUpdateReq();
        resourceInstUpdateReq.setMktResInstNbrs(mktResInstNbrs);
        resourceInstUpdateReq.setCheckStatusCd(Lists.newArrayList(ResourceConst.STATUSCD.DELETED.getCode()));
        resourceInstUpdateReq.setMktResStoreId(storeId);
        resourceInstUpdateReq.setDestStoreId(destStoreId);
        resourceInstUpdateReq.setStatusCd(ResourceConst.STATUSCD.SALED.getCode());
        resourceInstUpdateReq.setEventType(ResourceConst.EVENTTYPE.SALE_TO_ORDER.getCode());
        resourceInstUpdateReq.setObjType(ResourceConst.EVENT_OBJTYPE.ALLOT.getCode());
        resourceInstUpdateReq.setObjId(req.getOrderId());
        resourceInstUpdateReq.setMerchantId(req.getSellerMerchantId());
        ResultVO delRS = resourceInstService.updateResourceInstForTransaction(resourceInstUpdateReq);
        log.info("ResourceInstServiceImpl.deliveryOutResourceInst resourceInstService.delResourceInst req={},resp={}", JSON.toJSONString(resourceInstUpdateReq), JSON.toJSONString(delRS));

        // 下单是增加了在途数量，发货时减去
        if (null != req.getUpdateStockReq()) {
            resourceInstStoreService.updateStock(req.getUpdateStockReq());
        }

        if (delRS == null || !delRS.isSuccess()) {
            return ResultVO.success(false);
        } else {
            return ResultVO.success(true);
        }
    }

    @Override
    public ResultVO deliveryInResourceInst(DeliveryResourceInstReq req) {
        ResultVO<MerchantDTO> merchantResultVO = merchantService.getMerchantById(req.getBuyerMerchantId());
        if (!merchantResultVO.isSuccess() || null == merchantResultVO.getResultData()) {
            return ResultVO.error(constant.getCannotGetMerchantMsg());
        }
        MerchantDTO merchantDTO = merchantResultVO.getResultData();
        log.info("ResourceInstServiceImpl.deliveryInResourceInst merchantService.getMerchantById req={},resp={}", JSON.toJSONString(req), JSON.toJSONString(merchantResultVO));
        List<String> mktResInstNbrs = new ArrayList<>();
        for (DeliveryResourceInstItem item : req.getDeliveryResourceInstItemList()) {
            if (StringUtils.isEmpty(item.getProductId())) {
                return ResultVO.error(constant.getNoProductIdMsg());
            }
            mktResInstNbrs.addAll(item.getMktResInstNbrs());
            ResourceInstAddReq resourceInstAddReq = new ResourceInstAddReq();
            resourceInstAddReq.setMktResInstNbrs(mktResInstNbrs);
            resourceInstAddReq.setCreateStaff(req.getBuyerMerchantId());
            resourceInstAddReq.setMerchantId(req.getBuyerMerchantId());
            resourceInstAddReq.setMerchantName(merchantDTO.getMerchantName());
            resourceInstAddReq.setMerchantCode(merchantDTO.getMerchantCode());
            resourceInstAddReq.setMerchantType(merchantDTO.getMerchantType());
            resourceInstAddReq.setLanId(merchantDTO.getLanId());
            resourceInstAddReq.setRegionId(merchantDTO.getCity());
            resourceInstAddReq.setStatusCd(ResourceConst.STATUSCD.AVAILABLE.getCode());
            resourceInstAddReq.setEventType(ResourceConst.EVENTTYPE.PUT_STORAGE.getCode());
            resourceInstAddReq.setSourceType(ResourceConst.SOURCE_TYPE.SUPPLIER.getCode());
            resourceInstAddReq.setStorageType(ResourceConst.STORAGETYPE.TRANSACTION_WAREHOUSING.getCode());
            resourceInstAddReq.setMktResInstType(ResourceConst.MKTResInstType.TRANSACTION.getCode());
            resourceInstAddReq.setObjType(ResourceConst.EVENT_OBJTYPE.ALLOT.getCode());
            resourceInstAddReq.setObjId(item.getOrderItemId());
            resourceInstAddReq.setMktResId(item.getProductId());
            resourceInstAddReq.setOrderId(req.getOrderId());
            // 源仓库
            StoreGetStoreIdReq storeGetStoreIdReq = new StoreGetStoreIdReq();
            storeGetStoreIdReq.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
            storeGetStoreIdReq.setMerchantId(req.getSellerMerchantId());
            String mktResStoreId = resouceStoreService.getStoreId(storeGetStoreIdReq);
            log.info("ResourceInstServiceImpl.deliveryInResourceInst resouceStoreService.getStoreId req={},resp={}", JSON.toJSONString(storeGetStoreIdReq), mktResStoreId);
            resourceInstAddReq.setMktResStoreId(mktResStoreId);
            // 目标仓库
            storeGetStoreIdReq.setMerchantId(req.getBuyerMerchantId());
            String destStoreId = resouceStoreService.getStoreId(storeGetStoreIdReq);
            log.info("ResourceInstServiceImpl.deliveryInResourceInst resouceStoreService.getStoreId req={},resp={}", JSON.toJSONString(storeGetStoreIdReq), destStoreId);
            resourceInstAddReq.setDestStoreId(destStoreId);
            ResultVO addRS = this.resourceInstService.addResourceInst(resourceInstAddReq);
            log.info("ResourceInstServiceImpl.deliveryInResourceInst resourceInstService.addResourceInst req={},resp={}", JSON.toJSONString(resourceInstAddReq), JSON.toJSONString(addRS));
            if (addRS == null || !addRS.isSuccess()) {
                return ResultVO.error(addRS.getResultMsg());
            }
        }

        return ResultVO.success(true);
    }

    @Override
    public ResultVO backDeliveryOutResourceInst(DeliveryResourceInstReq req) {
        ResultVO<MerchantDTO> merchantResultVO = merchantService.getMerchantById(req.getBuyerMerchantId());
        log.info("ResourceInstServiceImpl.backDeliveryOutResourceInst merchantService.getMerchantById req={},resp={}", JSON.toJSONString(req), JSON.toJSONString(merchantResultVO));
        if (null == merchantResultVO || !merchantResultVO.isSuccess() || null == merchantResultVO.getResultData()) {
            return ResultVO.error("查无该商家");
        }
        // 源仓库
        StoreGetStoreIdReq storePageReq = new StoreGetStoreIdReq();
        storePageReq.setMerchantId(req.getBuyerMerchantId());
        String mktResStoreId = resouceStoreService.getStoreId(storePageReq);
        log.info("ResourceInstServiceImpl.deliveryInResourceInst resouceStoreService.getStoreId req={},resp={}", JSON.toJSONString(storePageReq), mktResStoreId);
        // 目标仓库
        StoreGetStoreIdReq storeGetStoreIdReq = new StoreGetStoreIdReq();
        storeGetStoreIdReq.setMerchantId(req.getSellerMerchantId());
        storeGetStoreIdReq.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
        String destStoreId = resouceStoreService.getStoreId(storeGetStoreIdReq);
        log.info("ResourceInstServiceImpl.backDeliveryOutResourceInst resourceInstService  destStoreId{}",destStoreId);
        for (DeliveryResourceInstItem item : req.getDeliveryResourceInstItemList()) {
            ResourceInstsGetReq resourceInstsGetReq = new ResourceInstsGetReq();
            resourceInstsGetReq.setMktResInstNbrs(item.getMktResInstNbrs());
            resourceInstsGetReq.setMktResStoreId(mktResStoreId);
            List<ResourceInstDTO> resourceInstDTOList = resourceInstManager.getResourceInsts(resourceInstsGetReq);
            if (CollectionUtils.isEmpty(resourceInstDTOList)) {
                continue;
            }
            AdminResourceInstDelReq adminResourceInstDelReq = new AdminResourceInstDelReq();
            adminResourceInstDelReq.setMktResInstIds(resourceInstDTOList.stream().map(ResourceInstDTO::getMktResInstId).collect(Collectors.toList()));
            // 设置状态校验条件
            List<String> checkStatusCd = Lists.newArrayList(
                    ResourceConst.STATUSCD.AUDITING.getCode(),
                    ResourceConst.STATUSCD.ALLOCATIONED.getCode(),
                    ResourceConst.STATUSCD.ALLOCATIONING.getCode());
            //退库中的不过滤
            //       ResourceConst.STATUSCD.RESTORAGEING.getCode()

            adminResourceInstDelReq.setCheckStatusCd(checkStatusCd);
            adminResourceInstDelReq.setStatusCd(ResourceConst.STATUSCD.DELETED.getCode());
            adminResourceInstDelReq.setEventType(ResourceConst.EVENTTYPE.BUY_BACK.getCode());
            adminResourceInstDelReq.setObjType(ResourceConst.EVENT_OBJTYPE.ALLOT.getCode());
            adminResourceInstDelReq.setObjId(req.getOrderId());
            adminResourceInstDelReq.setDestStoreId(destStoreId);
            adminResourceInstDelReq.setMktResStoreId(mktResStoreId);
            adminResourceInstDelReq.setUpdateStaff(merchantResultVO.getResultData().getUserId());
            ResultVO updateResultVO = resourceInstService.updateResourceInstByIdsForTransaction(adminResourceInstDelReq);
            log.info("ResourceInstServiceImpl.backDeliveryOutResourceInst resourceInstService.updateResultVO req={},resp={}", JSON.toJSONString(adminResourceInstDelReq), JSON.toJSONString(updateResultVO));
            if (updateResultVO == null || !updateResultVO.isSuccess()) {
                return ResultVO.error(updateResultVO.getResultMsg());
            }
        }
        return ResultVO.success(true);
    }

    @Override
    public ResultVO backDeliveryInResourceInst(DeliveryResourceInstReq req) {
        ResultVO<MerchantDTO> merchantResultVO = merchantService.getMerchantById(req.getSellerMerchantId());
        log.info("ResourceInstServiceImpl.backDeliveryInResourceInst merchantService.getMerchantById req={},resp={}", JSON.toJSONString(req), JSON.toJSONString(merchantResultVO));
        if (null == merchantResultVO || !merchantResultVO.isSuccess() || null == merchantResultVO.getResultData()) {
            return ResultVO.error("查无该商家");
        }
        // 源仓库
        StoreGetStoreIdReq storeGetStoreIdReq = new StoreGetStoreIdReq();
        storeGetStoreIdReq.setMerchantId(req.getBuyerMerchantId());
        storeGetStoreIdReq.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
        String mktResStoreId = resouceStoreService.getStoreId(storeGetStoreIdReq);
        log.info("ResourceInstServiceImpl.backDeliveryOutResourceInst resourceInstService req={} mktResStoreId={}",JSON.toJSONString(storeGetStoreIdReq), mktResStoreId);
        // 目标仓库
        storeGetStoreIdReq.setMerchantId(req.getSellerMerchantId());
        String destStoreId = resouceStoreService.getStoreId(storeGetStoreIdReq);
        log.info("ResourceInstServiceImpl.backDeliveryOutResourceInst resourceInstService req={} destStoreId={}",JSON.toJSONString(storeGetStoreIdReq), destStoreId);
        for (DeliveryResourceInstItem item : req.getDeliveryResourceInstItemList()) {
            ResourceInstsGetReq resourceInstsGetReq = new ResourceInstsGetReq();
            resourceInstsGetReq.setMktResInstNbrs(item.getMktResInstNbrs());
            resourceInstsGetReq.setMktResStoreId(mktResStoreId);
            List<ResourceInstDTO> resourceInstDTOList = resourceInstManager.getResourceInsts(resourceInstsGetReq);
            if (CollectionUtils.isEmpty(resourceInstDTOList)) {
                continue;
            }
            AdminResourceInstDelReq adminResourceInstDelReq = new AdminResourceInstDelReq();
            adminResourceInstDelReq.setMktResInstIds(resourceInstDTOList.stream().map(ResourceInstDTO::getMktResInstId).collect(Collectors.toList()));
            // 设置状态校验条件
            List<String> checkStatusCd = Lists.newArrayList(
                    ResourceConst.STATUSCD.DELETED.getCode(),
                    ResourceConst.STATUSCD.AUDITING.getCode(),
                    ResourceConst.STATUSCD.ALLOCATIONED.getCode(),
                    ResourceConst.STATUSCD.ALLOCATIONING.getCode());
            // 不用校验1306-已退库条件 1305-退库中
            //ResourceConst.STATUSCD.RESTORAGEING.getCode()
            //ResourceConst.STATUSCD.RESTORAGED.getCode()

            adminResourceInstDelReq.setCheckStatusCd(checkStatusCd);
            adminResourceInstDelReq.setStatusCd(ResourceConst.STATUSCD.RESTORAGED.getCode());
            adminResourceInstDelReq.setEventType(ResourceConst.EVENTTYPE.BUY_BACK.getCode());
            adminResourceInstDelReq.setObjType(ResourceConst.EVENT_OBJTYPE.ALLOT.getCode());
            adminResourceInstDelReq.setObjId(req.getOrderId());
            //目标仓库
            adminResourceInstDelReq.setDestStoreId(destStoreId);
            adminResourceInstDelReq.setMktResStoreId(mktResStoreId);
            ResultVO updateResultVO = resourceInstService.updateResourceInstByIdsForTransaction(adminResourceInstDelReq);
            log.info("ResourceInstServiceImpl.backDeliveryInResourceInst resourceInstService.updateResultVO req={},resp={}", JSON.toJSONString(adminResourceInstDelReq), JSON.toJSONString(updateResultVO));
            if (updateResultVO == null || !updateResultVO.isSuccess()) {
                return ResultVO.error(updateResultVO.getResultMsg());
            }
        }
        return ResultVO.success(true);
    }

    @Override
    public ResultVO<List<ResourceInstListResp>> getBatch(ResourceInstBatchReq req) {
        List<ResourceInstListResp> list = resourceInstManager.getBatch(req);
        log.info("ResourceInstServiceImpl.getBatch req={}", JSON.toJSONString(req));
        // 添加产品信息
        for (ResourceInstListResp resp : list) {
            String productId = resp.getMktResId();
            ProductResourceInstGetReq queryReq = new ProductResourceInstGetReq();
            queryReq.setProductId(productId);
            ResultVO<List<ProductResourceResp>> resultVO = productService.getProductResource(queryReq);
            log.info("ResourceInstServiceImpl.getBatch productService.getProductResource req={},resp={}", JSON.toJSONString(queryReq), JSON.toJSONString(resultVO));
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
    //todo 事务先去掉，影响主流程 200_539
//    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
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
                /** 已调拨状态不过滤 ResourceConst.STATUSCD.ALLOCATIONED.getCode(),**/
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

        ResultVO<List<String>> updateVO = resourceInstService.updateResourceInstByIds(updateReq);

        // step3 领用方入库
        // 找出串码实列
        List<ResourceInstDTO> insts = resourceInstManager.selectByIds(mktResInstIds);
        // 按产品维度组装数据
        Map<String, List<ResourceInstDTO>> map = insts.stream().collect(Collectors.groupingBy(t -> t.getMktResId()));
        ResourceInstPutInReq instPutInReq = new ResourceInstPutInReq();
        instPutInReq.setMerchantId(req.getMerchantId());
        instPutInReq.setInsts(map);
        instPutInReq.setCreateStaff(req.getUpdateStaff());
        instPutInReq.setStorageType(ResourceConst.STORAGETYPE.ALLOCATION_AND_WAREHOUSING.getCode());
        instPutInReq.setSourceType(ResourceConst.SOURCE_TYPE.SUPPLIER.getCode());
        instPutInReq.setEventType(ResourceConst.EVENTTYPE.ALLOT.getCode());
        instPutInReq.setMktResStoreId(resourceRequestResp.getMktResStoreId());
        instPutInReq.setDestStoreId(resourceRequestResp.getDestStoreId());
        ResultVO resultResourceInstPutIn = resourceInstService.resourceInstPutIn(instPutInReq);
        log.info("SupplierResourceInstServiceImpl.confirmReciveNbr resourceInstService.resourceInstPutIn req={}, resp={}", JSON.toJSONString(instPutInReq), JSON.toJSONString(resultResourceInstPutIn));
        return ResultVO.success(instPutInReq.getUnUse());
    }

    @Override
    //todo 事务先去掉，影响主流程 200_539
//    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
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
        updateReq.setMktResStoreId(list.get(0).getMktResStoreId());
        ResultVO resp = resourceInstService.updateResourceInstByIds(updateReq);
        log.info("SupplierResourceInstServiceImpl.confirmRefuseNbr resourceInstService.resourceInstPutIn req={}, resp={}", JSON.toJSONString(updateReq), JSON.toJSONString(resp));
        return ResultVO.success();
    }
}