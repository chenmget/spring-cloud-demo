package com.iwhalecloud.retail.warehouse.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.exception.RetailTipException;
import com.iwhalecloud.retail.goods2b.dto.req.MerChantGetProductReq;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.system.dto.OrganizationDTO;
import com.iwhalecloud.retail.system.service.OrganizationService;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceBatchRecService;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstCheckService;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.constant.Constant;
import com.iwhalecloud.retail.warehouse.dto.ResouceStoreDTO;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstAddResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListPageResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceUploadTempListResp;
import com.iwhalecloud.retail.warehouse.dto.response.SelectProcessResp;
import com.iwhalecloud.retail.warehouse.manager.ResouceInstTrackManager;
import com.iwhalecloud.retail.warehouse.manager.ResouceStoreManager;
import com.iwhalecloud.retail.warehouse.manager.ResourceUploadTempManager;
import com.iwhalecloud.retail.warehouse.runable.QueryResourceInstRunableTask;
import com.iwhalecloud.retail.warehouse.runable.RunableTask;
import com.iwhalecloud.retail.warehouse.service.MerchantResourceInstService;
import com.iwhalecloud.retail.warehouse.service.ResouceStoreService;
import com.iwhalecloud.retail.warehouse.service.ResourceRequestService;
import com.iwhalecloud.retail.warehouse.util.MarketingZopClientUtil;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Slf4j
public class MerchantResourceInstServiceImpl implements MerchantResourceInstService {

    @Autowired
    private ResourceInstService resourceInstService;
    @Reference
    private MerchantService merchantService;
    @Reference
    private ResouceStoreService resouceStoreService;
    @Reference
    private ResourceRequestService requestService;
    @Reference
    private TaskService taskService;
    @Autowired
    private Constant constant;
    @Autowired
    private RunableTask runableTask;
    @Autowired
    private ResourceUploadTempManager resourceUploadTempManager;
    @Autowired
    private ResourceInstCheckService resourceInstCheckService;
    @Autowired
    private ResouceInstTrackManager resouceInstTrackManager;
    @Reference
    private ProductService productService;
    @Autowired
    private QueryResourceInstRunableTask queryResourceInstRunableTask;
    @Autowired
    private MarketingZopClientUtil zopClientUtil;
    @Autowired
    private ResouceStoreManager resouceStoreManager;
    @Autowired
    private ResourceBatchRecService resourceBatchRecService;
    @Reference
    private OrganizationService organizationService;

    @Override
    public ResultVO<Page<ResourceInstListPageResp>> getResourceInstList(ResourceInstListPageReq req) {
        log.info("MerchantResourceInstServiceImpl.getResourceInstList req={}", JSON.toJSONString(req));
        if (CollectionUtils.isEmpty(req.getMktResStoreIds())) {
            String mktResInstNbr = req.getMktResInstNbr();
            String mktResStoreId = resouceInstTrackManager.getStoreIdByNbr(mktResInstNbr);
            log.info("MerchantResourceInstServiceImpl.getResourceInstList resouceInstTrackManager.getStoreIdByNbr mktResInstNbr={}, mktResStoreId={}", mktResInstNbr, mktResStoreId);
            req.setMktResStoreIds(Lists.newArrayList(mktResStoreId));
        }
        return resourceInstService.getResourceInstList(req);
    }

    @Override
    public ResultVO delResourceInst(ResourceInstUpdateReq req) {
        log.info("MerchantResourceInstServiceImpl.delResourceInst req={}", JSON.toJSONString(req));
        // 获取仓库
        StoreGetStoreIdReq storeGetStoreIdReq = new StoreGetStoreIdReq();
        storeGetStoreIdReq.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
        storeGetStoreIdReq.setMerchantId(req.getMerchantId());
        String mktResStoreId = resouceStoreService.getStoreId(storeGetStoreIdReq);
        log.info("MerchantResourceInstServiceImpl.delResourceInst resouceStoreService.getStoreId req={},resp={}", JSON.toJSONString(storeGetStoreIdReq), mktResStoreId);
        if (StringUtils.isBlank(mktResStoreId)) {
            return ResultVO.error(constant.getCannotGetStoreMsg());
        }
        req.setDestStoreId(mktResStoreId);
        req.setMktResStoreId(ResourceConst.NULL_STORE_ID);
        return resourceInstService.updateResourceInst(req);
    }

    @Override
    public ResultVO validNbr(ResourceInstValidReq req){
        // 集采前端会传入库仓库id,其他类型根据当前登陆用户去获取仓库
        String mktResStoreId = req.getMktResStoreId();
        if (ResourceConst.MKTResInstType.TRANSACTION.getCode().equals(req.getMktResInstType())) {
            StoreGetStoreIdReq storeGetStoreIdReq = new StoreGetStoreIdReq();
            storeGetStoreIdReq.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
            storeGetStoreIdReq.setMerchantId(req.getMerchantId());
            mktResStoreId = resouceStoreService.getStoreId(storeGetStoreIdReq);
            log.info("MerchantResourceInstServiceImpl.validNbr resouceStoreService.getStoreId req={},resp={}", JSON.toJSONString(storeGetStoreIdReq), mktResStoreId);
            if (StringUtils.isBlank(mktResStoreId)) {
                return ResultVO.error(constant.getCannotGetStoreMsg());
            }
        } else if (ResourceConst.MKTResInstType.TEST_FIX_LINE.getCode().equals(req.getMktResInstType())) {
            StorePageReq storePageReq = new StorePageReq();
            storePageReq.setStoreType(ResourceConst.STORE_TYPE.PROVINCE.getCode());
            storePageReq.setStoreGrade(ResourceConst.STORE_GRADE.PROVINCE.getCode());
            Page<ResouceStoreDTO> pageVO = resouceStoreManager.pageStore(storePageReq);
            log.info("AdminResourceInstServiceImpl.addResourceInst resouceStoreManager.pageStore req={},resp={}", JSON.toJSONString(storePageReq), JSON.toJSONString(pageVO.getRecords()));
            if (null == pageVO || CollectionUtils.isEmpty(pageVO.getRecords())) {
                return ResultVO.error(constant.getCannotGetStoreMsg());
            }
            mktResStoreId = pageVO.getRecords().get(0).getMktResStoreId();
        }
        ResourceInstValidReq resourceInstValidReq = new ResourceInstValidReq();
        BeanUtils.copyProperties(req, resourceInstValidReq);
        resourceInstValidReq.setMktResStoreId(mktResStoreId);
        String batchId = runableTask.exceutorValid(resourceInstValidReq);
        return ResultVO.success(batchId);
    }


    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResultVO addResourceInst(ResourceInstAddReq req) {
        log.info("MerchantResourceInstServiceImpl.addResourceInst req={}, nbrSize={}", JSON.toJSONString(req), req.getMktResInstNbrs().size());
        String merchantId = req.getMerchantId();
        ResultVO<MerchantDTO> merchantDTOResultVO = merchantService.getMerchantById(merchantId);
        if (!merchantDTOResultVO.isSuccess() || null == merchantDTOResultVO.getResultData()) {
            return ResultVO.error(constant.getCannotGetMerchantMsg());
        }
        MerchantDTO merchantDTO = merchantDTOResultVO.getResultData();
        req.setMerchantId(merchantDTO.getMerchantId());
        req.setMerchantType(merchantDTO.getMerchantType());
        req.setMerchantName(merchantDTO.getMerchantName());
        req.setMerchantCode(merchantDTO.getMerchantCode());
        req.setLanId(merchantDTO.getLanId());
        req.setRegionId(merchantDTO.getCity());

        // 集采前端会传入库仓库id,其他类型根据当前登陆用户去获取仓库
        StoreGetStoreIdReq storeGetStoreIdReq = new StoreGetStoreIdReq();
        storeGetStoreIdReq.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
        storeGetStoreIdReq.setMerchantId(merchantId);
        String mktResStoreId = resouceStoreService.getStoreId(storeGetStoreIdReq);
        log.info("MerchantResourceInstServiceImpl.addResourceInst resouceStoreService.getStoreId req={},resp={}", JSON.toJSONString(storeGetStoreIdReq), mktResStoreId);
        if (StringUtils.isBlank(mktResStoreId)) {
            return ResultVO.error(constant.getCannotGetStoreMsg());
        }
        req.setDestStoreId(mktResStoreId);
        if (CollectionUtils.isNotEmpty(req.getThreeCheckMktResInstNbrs())) {
            resourceInstCheckService.noticeITMS(req.getThreeCheckMktResInstNbrs(), merchantDTO.getMerchantName(), mktResStoreId, merchantDTO.getLanId());
        }
        req.setMktResStoreId(ResourceConst.NULL_STORE_ID);
        ResultVO selectProcessRespVO = resourceInstCheckService.selectProcess(req);
        if (!selectProcessRespVO.isSuccess() && null ==selectProcessRespVO.getResultData()) {
            return selectProcessRespVO;
        }
        SelectProcessResp selectProcessResp = (SelectProcessResp)selectProcessRespVO.getResultData();
        // step2 新增申请单
        ResourceRequestAddReq resourceRequestAddReq = new ResourceRequestAddReq();
        List<ResourceRequestAddReq.ResourceRequestInst> instDTOList = resourceInstCheckService.getReqInst(req);
        String reqCode = resourceInstService.getPrimaryKey();
        resourceRequestAddReq.setReqType(ResourceConst.REQTYPE.PUTSTORAGE_APPLYFOR.getCode());
        BeanUtils.copyProperties(req, resourceRequestAddReq);
        resourceRequestAddReq.setInstList(instDTOList);
        resourceRequestAddReq.setReqCode(reqCode);
        resourceRequestAddReq.setReqName("串码入库申请单");
        resourceRequestAddReq.setChngType(ResourceConst.PUT_IN_STOAGE);
        resourceRequestAddReq.setLanId(merchantDTOResultVO.getResultData().getLanId());
        resourceRequestAddReq.setRegionId(merchantDTOResultVO.getResultData().getCity());
        resourceRequestAddReq.setMktResStoreId(ResourceConst.NULL_STORE_ID);
        resourceRequestAddReq.setDestStoreId(mktResStoreId);
        resourceRequestAddReq.setStatusCd(selectProcessResp.getRequestStatusCd());
        resourceRequestAddReq.setMktResInstType(req.getMktResInstType());
        String mktResReqId = runableTask.excuetorAddReq(resourceRequestAddReq);
        log.info("MerchantResourceInstServiceImpl.addResourceInst requestService.insertResourceRequest req={}, resp={}", JSON.toJSONString(resourceRequestAddReq), mktResReqId);
        // step3 启动工作流
        ProcessStartReq processStartDTO = new ProcessStartReq();
        processStartDTO.setTitle("串码入库审批流程");
        processStartDTO.setApplyUserId(req.getCreateStaff());
        processStartDTO.setProcessId(selectProcessResp.getProcessId());
        processStartDTO.setTaskSubType(selectProcessResp.getTaskSubType());
        processStartDTO.setApplyUserName(merchantDTO.getMerchantName());
        processStartDTO.setFormId(mktResReqId);
        ResultVO startResultVO = taskService.startProcess(processStartDTO);
        log.info("MerchantResourceInstServiceImpl.addResourceInst taskService.startProcess req={}, resp={}", JSON.toJSONString(processStartDTO), JSON.toJSONString(startResultVO));
        if (null != startResultVO && !startResultVO.getResultCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
            throw new RetailTipException(ResultCodeEnum.ERROR.getCode(), "启动工作流失败");
        }
        ResourceUploadTempDelReq resourceUploadTempDelReq = new ResourceUploadTempDelReq();
        resourceUploadTempDelReq.setMktResUploadBatch(req.getMktResUploadBatch());
        runableTask.exceutorDelNbr(resourceUploadTempDelReq);
        return ResultVO.success("串码入库提交申请单");
    }

    @Override
    public ResultVO selectProduct(PageProductReq req) {
        log.info("MerchantResourceInstServiceImpl.selectProduct req={}", JSON.toJSONString(req));
        return resourceInstService.selectProduct(req);
    }


    @Override
    public ResultVO<Page<ResourceUploadTempListResp>> listResourceUploadTemp(ResourceUploadTempListPageReq req) {
        // 多线程没跑完，返回空
        if (runableTask.validHasDone()) {
            return ResultVO.success(resourceUploadTempManager.listResourceUploadTemp(req));
        } else{
            return ResultVO.success();
        }

    }

    @Override
    public ResultVO exceutorDelNbr(ResourceUploadTempDelReq req) {
        return ResultVO.success(resourceUploadTempManager.delResourceUploadTemp(req));
    }

    @Override
    public List<ResourceUploadTempListResp> exceutorQueryTempNbr(ResourceUploadTempDelReq req) {
        // 多线程没跑完，返回空
        return runableTask.exceutorQueryTempNbr(req);
    }


    @Override
    public ResultVO addResourceInstByAdmin(ResourceInstAddReq req) {
        log.info("MerchantResourceInstServiceImpl.addResourceInstByAdmin req={}", JSON.toJSONString(req));
        // 获取产品归属厂商
        MerChantGetProductReq merChantGetProductReq = new MerChantGetProductReq();
        merChantGetProductReq.setProductId(req.getMktResId());
        ResultVO<String> productRespResultVO = this.productService.getMerchantByProduct(merChantGetProductReq);
        log.info("MerchantResourceInstServiceImpl.addResourceInstByAdmin productService.getMerchantByProduct req={} resp={}", JSON.toJSONString(merChantGetProductReq), JSON.toJSONString(productRespResultVO));
        if (!productRespResultVO.isSuccess() || StringUtils.isEmpty(productRespResultVO.getResultData())) {
            return ResultVO.error(constant.getCannotGetMuanfacturerMsg());
        }
        // 厂商只能录入自己产品的串码
        String sourceStoreMerchantId = productRespResultVO.getResultData();
        if (!req.getMerchantId().equals(sourceStoreMerchantId)) {
            return ResultVO.error(constant.getNotMatchMerchant());
        }
        req.setDestStoreId(req.getMktResStoreId());
        ResultVO<MerchantDTO> merchantDTOResultVO = merchantService.getMerchantById(req.getMerchantId());
        if (!merchantDTOResultVO.isSuccess() || null == merchantDTOResultVO.getResultData()) {
            return ResultVO.error(constant.getCannotGetMerchantMsg());
        }
        MerchantDTO merchantDTO = merchantDTOResultVO.getResultData();
        req.setMerchantType(merchantDTO.getMerchantType());
        req.setMerchantName(merchantDTO.getMerchantName());
        req.setMerchantCode(merchantDTO.getMerchantCode());
        req.setLanId(merchantDTO.getLanId());
        req.setRegionId(merchantDTO.getCity());
        ResourceInstAddResp resourceInstAddResp = new ResourceInstAddResp();
        ResourceInstValidReq resourceInstValidReq = new ResourceInstValidReq();
        BeanUtils.copyProperties(req, resourceInstValidReq);
        CopyOnWriteArrayList<String> newList = new CopyOnWriteArrayList(req.getMktResInstNbrs());
        List<String> existNbrs = resourceInstCheckService.vaildOwnStore(resourceInstValidReq, newList);
        List<String> mktResInstNbrs = req.getMktResInstNbrs();
        resourceInstAddResp.setExistNbrs(existNbrs);
        mktResInstNbrs.removeAll(existNbrs);
        if(CollectionUtils.isEmpty(mktResInstNbrs)){
            return ResultVO.error("该产品串码已在库，请不要重复录入！");
        }

        req.setSourceType(merchantDTOResultVO.getResultData().getMerchantType());
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<String>(mktResInstNbrs);
        Boolean addNum = resourceInstService.addResourceInstByMerchant(req, list);
        if (!addNum) {
            return ResultVO.error("串码入库失败");
        }
        return ResultVO.success("串码入库完成", resourceInstAddResp);
    }

    @Override
    public ResultVO<List<ResourceInstListPageResp>> queryForExport(ResourceInstListPageReq req){
        return ResultVO.success(queryResourceInstRunableTask.exceutorQueryResourceInst(req));
    }

    @Override
    public ResultVO addResourceInstForProvinceStore(ResourceInstAddReq req) {
        log.info("MerchantResourceInstServiceImpl.addResourceInstForProvinceStore req={}", JSON.toJSONString(req));
        String provinceStoreLanId = "731";
        String provinceStoreRegionId = "731";
        // 获取产品归属厂商
        MerChantGetProductReq merChantGetProductReq = new MerChantGetProductReq();
        merChantGetProductReq.setProductId(req.getMktResId());
        ResultVO<String> productRespResultVO = this.productService.getMerchantByProduct(merChantGetProductReq);
        log.info("MerchantResourceInstServiceImpl.addResourceInstForProvinceStore productService.getMerchantByProduct req={} resp={}", JSON.toJSONString(merChantGetProductReq), JSON.toJSONString(productRespResultVO));
        if (!productRespResultVO.isSuccess() || StringUtils.isEmpty(productRespResultVO.getResultData())) {
            return ResultVO.error(constant.getCannotGetMuanfacturerMsg());
        }
        String muanfacturerId = productRespResultVO.getResultData();
        ResouceStoreDTO muanfactureStoreDTO = this.resouceStoreManager.getStore(muanfacturerId, ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
        log.info("MerchantResourceInstServiceImpl.addResourceInstForProvinceStore resouceStoreManager.getStore req={} resp={}", JSON.toJSONString(muanfacturerId), JSON.toJSONString(muanfactureStoreDTO));
        if (null == muanfactureStoreDTO) {
            return ResultVO.error(constant.getCannotGetStoreMsg());
        }
        // 获取厂商
        ResultVO<MerchantDTO> merchantDTOResultVO = merchantService.getMerchantById(req.getMerchantId());
        log.info("MerchantResourceInstServiceImpl.addResourceInstForProvinceStore merchantService.getMerchantById req={} resp={}", req.getMerchantId(), JSON.toJSONString(merchantDTOResultVO));
        if (!merchantDTOResultVO.isSuccess() || null == merchantDTOResultVO.getResultData()) {
            return ResultVO.error(constant.getCannotGetMerchantMsg());
        }
        MerchantDTO merchantDTO = merchantDTOResultVO.getResultData();
        StorePageReq storePageReq = new StorePageReq();
        storePageReq.setStoreGrade(ResourceConst.STORE_GRADE.PROVINCE.getCode());
        storePageReq.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
        storePageReq.setStoreType(ResourceConst.STORE_TYPE.PROVINCE.getCode());
        if (ResourceConst.MKTResInstType.NONTRANSACTION.getCode().equals(req.getMktResInstType())) {
            storePageReq.setStoreGrade(ResourceConst.STORE_GRADE.CITY.getCode());
            storePageReq.setStoreType(ResourceConst.STORE_TYPE.CITY.getCode());
            storePageReq.setMktResStoreId(req.getMktResStoreId());
        }
        Page<ResouceStoreDTO> pageStore = resouceStoreManager.pageStore(storePageReq);
        log.info("MerchantResourceInstServiceImpl.addResourceInstForProvinceStore resouceStoreManager.pageStore storePageReq={}", JSON.toJSONString(storePageReq), JSON.toJSONString(pageStore.getRecords()));
        if (null == pageStore || CollectionUtils.isEmpty(pageStore.getRecords())) {
            return ResultVO.error(constant.getCannotGetStoreMsg());
        }
        ResouceStoreDTO storeDTO = pageStore.getRecords().get(0);
        String destStoreId = storeDTO.getMktResStoreId();
        if (ResourceConst.MKTResInstType.NONTRANSACTION.getCode().equals(req.getMktResInstType())) {
            ResultVO<OrganizationDTO> organizationtVO = organizationService.getOrganization(storeDTO.getMerchantId());
            if (organizationtVO.isSuccess() && null != organizationtVO.getResultData()) {
                OrganizationDTO organizationDTO = organizationtVO.getResultData();
                provinceStoreLanId = organizationDTO.getLanId();
                provinceStoreRegionId = organizationDTO.getRegionId();
            }
        }
        req.setMktResStoreId(muanfactureStoreDTO.getMktResStoreId());
        req.setMerchantType(PartnerConst.MerchantTypeEnum.SUPPLIER_PROVINCE.getType());
        req.setLanId(provinceStoreLanId);
        req.setRegionId(provinceStoreRegionId);
        req.setSourceType(PartnerConst.MerchantTypeEnum.MANUFACTURER.getType());
        req.setStatusCd(ResourceConst.STATUSCD.AVAILABLE.getCode());
        req.setSourceType(ResourceConst.SOURCE_TYPE.MERCHANT.getCode());
        req.setStorageType(ResourceConst.STORAGETYPE.VENDOR_INPUT.getCode());
        req.setEventType(ResourceConst.EVENTTYPE.PUT_STORAGE.getCode());
        req.setMktResStoreId(ResourceConst.NULL_STORE_ID);
        req.setDestStoreId(destStoreId);
        req.setMerchantId(storeDTO.getMerchantId());
        req.setSupplierCode(merchantDTO.getMerchantCode());
        req.setSupplierName(merchantDTO.getMerchantName());
        runableTask.exceutorAddNbr(req);
        log.info("MerchantResourceInstServiceImpl.addResourceInstForProvinceStore resourceInstService.addResourceInst addReq={}", req);
        // step4 增加事件和批次
        Map<String, List<String>> mktResIdAndNbrMap = new HashMap<>();
        mktResIdAndNbrMap.put(req.getMktResId(), req.getMktResInstNbrs());
        BatchAndEventAddReq batchAndEventAddReq = new BatchAndEventAddReq();
        batchAndEventAddReq.setEventType(ResourceConst.EVENTTYPE.PUT_STORAGE.getCode());
        batchAndEventAddReq.setLanId(provinceStoreLanId);
        batchAndEventAddReq.setMktResIdAndNbrMap(mktResIdAndNbrMap);
        batchAndEventAddReq.setRegionId(provinceStoreRegionId);
        batchAndEventAddReq.setDestStoreId(destStoreId);
        batchAndEventAddReq.setMktResStoreId(ResourceConst.NULL_STORE_ID);
        batchAndEventAddReq.setMerchantId(storeDTO.getMerchantId());
        batchAndEventAddReq.setCreateStaff(req.getCreateStaff());
        resourceBatchRecService.saveEventAndBatch(batchAndEventAddReq);
        log.info("MerchantResourceInstServiceImpl.addResourceInstForProvinceStore resourceBatchRecService.saveEventAndBatch req={},resp={}", JSON.toJSONString(batchAndEventAddReq));
        // 更新轨迹
        runableTask.exceutorAddNbrTrack(req);
        return ResultVO.success();
    }
}
