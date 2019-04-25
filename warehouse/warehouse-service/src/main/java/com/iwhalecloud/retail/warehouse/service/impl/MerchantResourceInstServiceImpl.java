package com.iwhalecloud.retail.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.exception.RetailTipException;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.constant.Constant;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListPageResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceUploadTempListResp;
import com.iwhalecloud.retail.warehouse.manager.ResourceUploadTempManager;
import com.iwhalecloud.retail.warehouse.runable.RunableTask;
import com.iwhalecloud.retail.warehouse.service.MerchantResourceInstService;
import com.iwhalecloud.retail.warehouse.service.ResouceStoreService;
import com.iwhalecloud.retail.warehouse.service.ResourceRequestService;
import com.iwhalecloud.retail.workflow.common.WorkFlowConst;
import com.iwhalecloud.retail.workflow.dto.req.ProcessStartReq;
import com.iwhalecloud.retail.workflow.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

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

    @Override
    public ResultVO<Page<ResourceInstListPageResp>> getResourceInstList(ResourceInstListPageReq req) {
        log.info("MerchantResourceInstServiceImpl.getResourceInstList req={}", JSON.toJSONString(req));
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
    public ResultVO validNbr(ResourceInstAddReq req){
        // 集采前端会传入库仓库id,其他类型根据当前登陆用户去获取仓库
        String mktResStoreId = req.getDestStoreId();
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
        String batchId = runableTask.exceutorValid(resourceInstValidReq);
        return ResultVO.success(batchId);
    }

    @Override
    public ResultVO addResourceInst(ResourceInstAddReq req) {
        log.info("MerchantResourceInstServiceImpl.addResourceInst req={}", JSON.toJSONString(req));
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
        String mktResStoreId = req.getDestStoreId();
        if (!ResourceConst.MKTResInstType.NONTRANSACTION.getCode().equals(req.getMktResInstType())) {
            StoreGetStoreIdReq storeGetStoreIdReq = new StoreGetStoreIdReq();
            storeGetStoreIdReq.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
            storeGetStoreIdReq.setMerchantId(merchantId);
            mktResStoreId = resouceStoreService.getStoreId(storeGetStoreIdReq);
            log.info("MerchantResourceInstServiceImpl.addResourceInst resouceStoreService.getStoreId req={},resp={}", JSON.toJSONString(storeGetStoreIdReq), mktResStoreId);
            if (StringUtils.isBlank(mktResStoreId)) {
                return ResultVO.error(constant.getCannotGetStoreMsg());
            }
            req.setDestStoreId(mktResStoreId);
        }
        req.setMktResStoreId(ResourceConst.NULL_STORE_ID);
        List<String> mktResInstNbrs = req.getMktResInstNbrs();
        // step2 新增申请单
        ResourceRequestAddReq resourceRequestAddReq = new ResourceRequestAddReq();
        List<ResourceRequestAddReq.ResourceRequestInst> instDTOList = Lists.newLinkedList();
        for (String nbr : mktResInstNbrs) {
            ResourceRequestAddReq.ResourceRequestInst instDTO = new ResourceRequestAddReq.ResourceRequestInst();
            instDTO.setMktResInstNbr(nbr);
            instDTO.setMktResId(req.getMktResId());
            if (null != req.getCtCode()) {
                instDTO.setCtCode(req.getCtCode().get(nbr));
            }
            instDTOList.add(instDTO);
        }
        List<String> checkMktResInstNbrs = req.getCheckMktResInstNbrs();
        if (!CollectionUtils.isEmpty(checkMktResInstNbrs)) {
            checkMktResInstNbrs.retainAll(mktResInstNbrs);
            for (String nbr : checkMktResInstNbrs) {
                ResourceRequestAddReq.ResourceRequestInst instDTO = new ResourceRequestAddReq.ResourceRequestInst();
                instDTO.setMktResInstNbr(nbr);
                instDTO.setMktResId(req.getMktResId());
                instDTO.setIsInspection(ResourceConst.CONSTANT_YES);
                if (null != req.getCtCode()) {
                    instDTO.setCtCode(req.getCtCode().get(nbr));
                }
                instDTOList.add(instDTO);
            }
        }
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
        ResultVO<String> resultVO = requestService.insertResourceRequest(resourceRequestAddReq);
        log.info("MerchantResourceInstServiceImpl.addResourceInst requestService.insertResourceRequest req={}, resultVO={}", JSON.toJSONString(resourceRequestAddReq), JSON.toJSONString(resultVO));
        // step3 启动工作流
        ProcessStartReq processStartDTO = new ProcessStartReq();
        processStartDTO.setTitle("串码入库审批流程");
        processStartDTO.setApplyUserId(req.getCreateStaff());
        processStartDTO.setProcessId(ResourceConst.ADD_NBR_WORK_FLOW_INST);
        processStartDTO.setTaskSubType(WorkFlowConst.TASK_SUB_TYPE.TASK_SUB_TYPE_1020.getTaskSubType());
        processStartDTO.setApplyUserName(req.getApplyUserName());
        if (resultVO != null && resultVO.getResultData() != null) {
            processStartDTO.setFormId(resultVO.getResultData());
        }
        ResultVO startResultVO = taskService.startProcess(processStartDTO);
        log.info("MerchantResourceInstServiceImpl.addResourceInst taskService.startProcess req={}, resp={}", JSON.toJSONString(processStartDTO), JSON.toJSONString(startResultVO));
        if (null != startResultVO && !startResultVO.getResultCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
            throw new RetailTipException(ResultCodeEnum.ERROR.getCode(), "启动工作流失败");
        }
        ResourceUploadTempDelReq resourceUploadTempDelReq = new ResourceUploadTempDelReq();
        resourceUploadTempDelReq.setMktResInstNbrList(checkMktResInstNbrs);
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
            return ResultVO.success(new Page<ResourceUploadTempListResp>());
        }

    }
}
