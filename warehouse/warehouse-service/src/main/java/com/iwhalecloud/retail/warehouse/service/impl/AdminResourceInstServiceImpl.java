package com.iwhalecloud.retail.warehouse.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.req.ProductGetByIdReq;
import com.iwhalecloud.retail.goods2b.dto.req.ProductResourceInstGetReq;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductResourceResp;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.dto.req.MerchantGetReq;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceBatchRecService;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.constant.Constant;
import com.iwhalecloud.retail.warehouse.dto.ExcelResourceReqDetailDTO;
import com.iwhalecloud.retail.warehouse.dto.ResourceInstDTO;
import com.iwhalecloud.retail.warehouse.dto.ResourceReqDetailPageDTO;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.dto.response.*;
import com.iwhalecloud.retail.warehouse.entity.ResourceRequest;
import com.iwhalecloud.retail.warehouse.manager.*;
import com.iwhalecloud.retail.warehouse.runable.RunableTask;
import com.iwhalecloud.retail.warehouse.service.AdminResourceInstService;
import com.iwhalecloud.retail.warehouse.service.MerchantResourceInstService;
import com.iwhalecloud.retail.warehouse.service.ResouceStoreService;
import com.iwhalecloud.retail.warehouse.service.SupplierResourceInstService;
import com.iwhalecloud.retail.warehouse.util.ZopClientUtil;
import com.iwhalecloud.retail.workflow.common.WorkFlowConst;
import com.iwhalecloud.retail.workflow.dto.RouteDTO;
import com.iwhalecloud.retail.workflow.dto.TaskDTO;
import com.iwhalecloud.retail.workflow.dto.req.RouteReq;
import com.iwhalecloud.retail.workflow.service.RouteService;
import com.iwhalecloud.retail.workflow.service.TaskItemService;
import com.iwhalecloud.retail.workflow.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminResourceInstServiceImpl implements AdminResourceInstService {

    @Autowired
    private ResourceInstService resourceInstService;

    @Reference
    private MerchantService merchantService;

    @Reference
    private SupplierResourceInstService supplierResourceInstService;

    @Reference
    private MerchantResourceInstService merchantResourceInstService;

    @Autowired
    private Constant constant;
    
    @Autowired
    private CallService callService;
    
    @Autowired
    private ResourceInstManager resourceInstManager;

    @Reference
    private ResouceStoreService resouceStoreService;

    @Autowired
    private RunableTask runableTask;

    @Autowired
    private ResourceUploadTempManager resourceUploadTempManager;

    @Autowired
    private ResourceBatchRecService resourceBatchRecService;

    @Autowired
    private ResourceReqDetailManager resourceReqDetailManager;

    @Autowired
    private ResourceRequestManager resourceRequestManager;

    @Reference
    private ProductService productService;

    @Autowired
    private ResourceReqDetailManager detailManager;

    @Reference
    private TaskService taskService;

    @Reference
    private TaskItemService taskItemService;

    @Reference
    private RouteService routeService;

    @Value("${zop.secret}")
    private String zopSecret;

    @Value("${zop.url}")
    private String zopUrl;
    @Override
    public ResultVO<Page<ResourceInstListPageResp>> getResourceInstList(ResourceInstListPageReq req) {
        log.info("AdminResourceInstServiceImpl.getResourceInstList req={}", JSON.toJSONString(req));
        return resourceInstService.getResourceInstList(req);
    }

    @Override
    public ResultVO<ResourceInstAddResp> addResourceInst(ResourceInstAddReq req) {
        log.info("AdminResourceInstServiceImpl.addResourceInst req={}", JSON.toJSONString(req));
        // 管理员只能给厂商和零售商增加串码
        MerchantGetReq merchantGetReq = new MerchantGetReq();
        merchantGetReq.setMerchantId(req.getMktResStoreId());
        ResultVO<MerchantDTO> merchantResultVO = resouceStoreService.getMerchantByStore(req.getMktResStoreId());
        log.info("AdminResourceInstServiceImpl.addResourceInst merchantService.getMerchantDetail req={},resp={}", JSON.toJSONString(merchantGetReq), JSON.toJSONString(merchantResultVO));
        if (!merchantResultVO.isSuccess() || null == merchantResultVO.getResultData()) {
            return ResultVO.error(constant.getCannotGetMerchantMsg());
        }
        String merchantType = merchantResultVO.getResultData().getMerchantType();
        // 管理员添加串码有传目标仓库Id
        req.setDestStoreId(req.getMktResStoreId());
        if (PartnerConst.MerchantTypeEnum.MANUFACTURER.getType().equals(merchantType)) {
            req.setMerchantId(merchantResultVO.getResultData().getMerchantId());
            return merchantResourceInstService.addResourceInstByAdmin(req);
        }else if(PartnerConst.MerchantTypeEnum.SUPPLIER_PROVINCE.getType().equals(merchantType) || PartnerConst.MerchantTypeEnum.SUPPLIER_GROUND.getType().equals(merchantType)) {
            req.setMerchantId(merchantResultVO.getResultData().getMerchantId());
            return supplierResourceInstService.addResourceInstByAdmin(req);
        }else {
            return ResultVO.error("用户类型不正确");
        }
    }

    @Override
    public ResultVO updateResourceInstByIds(AdminResourceInstDelReq req) {
        log.info("AdminResourceInstServiceImpl.updateResourceInstByIds req={}", JSON.toJSONString(req));
        return resourceInstService.updateResourceInstByIds(req);
    }

    @Override
	public ResultVO inventoryChange(InventoryChangeReq req) {
		log.info("AdminResourceInstOpenServiceImpl.inventoryChange req={}", JSON.toJSONString(req));
		
//		InventoryChangeResp inventoryChangeResp = new InventoryChangeResp();
		String result = "";
		List<ResourceInstDTO> resourceInstList = resourceInstManager.listInstsByNbr(req.getDeviceId());
		if(resourceInstList.size()<=0 || null == resourceInstList){
			return ResultVO.error("串码不在库中");
		}
        String b = "";
        Map request = new HashMap<>();
        request.put("deviceId",req.getDeviceId());
        request.put("userName",req.getUserName());
        request.put("code",req.getCode());
        request.put("params",req.getParams());
        String callUrl = "ord.operres.OrdInventoryChange";
		try {
            b = ZopClientUtil.zopService(callUrl, zopUrl, request, zopSecret);
            Map parseObject = JSON.parseObject(b, new TypeReference<HashMap>(){});
            String body = String.valueOf(parseObject.get("Body"));
            Map parseObject2 = JSON.parseObject(body, new TypeReference<HashMap>(){});
            String inventoryChangeResponse = String.valueOf(parseObject2.get("inventoryChangeResponse"));
            Map parseObject3 = JSON.parseObject(inventoryChangeResponse, new TypeReference<HashMap>(){});
            String inventoryChangeReturn = String.valueOf(parseObject3.get("inventoryChangeReturn"));

            if("0".equals(inventoryChangeReturn)){
                log.info("AdminResourceInstOpenServiceImpl.inventoryChange postWebServiceFailed inventoryChangeReturn={}", inventoryChangeReturn);
                return ResultVO.success("串码推送ITMS(添加)成功");
            }else if("1".equals(inventoryChangeReturn)){
                log.info("AdminResourceInstOpenServiceImpl.inventoryChange postWebServiceFailed inventoryChangeReturn={}", inventoryChangeReturn);
                return ResultVO.error("串码推送ITMS(添加)已经存在");
            }else{
                log.info("AdminResourceInstOpenServiceImpl.inventoryChange postWebServiceFailed inventoryChangeResponse={}", JSON.toJSONString(inventoryChangeResponse));
                return ResultVO.error("串码推送ITMS(添加)失败");
            }

//			result = callService.postInvenChangeToWebService(req);
//			inventoryChangeResp.setResult(result);
		} catch (Exception e) {
			log.error("AdminResourceInstOpenServiceImpl.inventoryChange postWebServiceFailed req={}", JSON.toJSONString(req));
			return ResultVO.error("AdminResourceInstOpenServiceImpl.inventoryChange postWebServiceFailed");
		}
//		return ResultVO.success(result);
	}

    @Override
    public ResultVO<Page<ResourceReqDetailPageResp>> listResourceUploadTemp(ResourceUploadTempListPageReq req) {
        // 多线程没跑完，返回空
        if (runableTask.validNbrHasDone()) {
            Page<ResourceUploadTempListResp> page=resourceUploadTempManager.listResourceUploadTemp(req);
            List<ResourceUploadTempListResp> uploadList=page.getRecords();
            //临时表中无数据
            if(CollectionUtils.isEmpty(uploadList)){
                return ResultVO.success(new Page<ResourceReqDetailPageResp>());
            }
            List<String> detailIds=uploadList.stream().map(ResourceUploadTempListResp::getMktResReqDetailId).collect(Collectors.toList());
            ResourceReqDetailQueryReq queryReq=new ResourceReqDetailQueryReq();
            queryReq.setPageNo(req.getPageNo());
            queryReq.setPageSize(req.getPageSize());
            queryReq.setMktResReqDetailIds(detailIds);
            queryReq.setReqType(ResourceConst.REQTYPE.PUTSTORAGE_APPLYFOR.getCode());
            Page<ResourceReqDetailPageDTO> detailPage = resourceReqDetailManager.listResourceRequestPage(queryReq);
            Page<ResourceReqDetailPageResp> respPage = new Page<ResourceReqDetailPageResp>();
            BeanUtils.copyProperties(page, respPage);
            //组装数据
            List<ResourceReqDetailPageDTO> detailList=detailPage.getRecords();
            List<ResourceReqDetailPageResp> resultList=new ArrayList<>();
            //产品集合
            Map<String,ProductResourceResp> productMap=new HashMap<>();
            for(ResourceUploadTempListResp temp : uploadList){
                ResourceReqDetailPageResp resp=new ResourceReqDetailPageResp();
                BeanUtils.copyProperties(temp, resp);
                Optional<ResourceReqDetailPageDTO> option =detailList.stream()
                        .filter(t->t.getMktResInstNbr().equals(temp.getMktResInstNbr())).findFirst();
                if(option.isPresent()){
                    ResourceReqDetailPageDTO dto=option.get();
                    BeanUtils.copyProperties(dto, resp);
                    String productId=dto.getMktResId();
                    //获取产品信息
                    ProductResourceResp prodResp = productMap.get(productId);
                    if(null == prodResp){
                        ProductResourceInstGetReq productQueryReq = new ProductResourceInstGetReq();
                        productQueryReq.setProductId(productId);
                        ResultVO<List<ProductResourceResp>> productResultVO = productService.getProductResource(productQueryReq);
                        log.info("AdminResourceInstServiceImpl.listResourceUploadTemp.getProductResource req={} resp={}",JSON.toJSON(productQueryReq ),JSON.toJSON(productResultVO));
                        List<ProductResourceResp> prodList = productResultVO.getResultData();
                        prodResp = prodList.size() > 0 ? prodList.get(0) : new ProductResourceResp();
                        productMap.put(productId,prodResp);
                    }
                    // 添加产品信息
                    BeanUtils.copyProperties(prodResp, resp);
                    //填充审核状态
                    resp.setStatusCdName(ResourceConst.DetailStatusCd.getNameByCode(temp.getStatusCd()));

                }
                resultList.add(resp);
            }
            respPage.setRecords(resultList);
            return ResultVO.success(respPage);
        } else{
            return ResultVO.success();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO<String> batchAuditNbr(ResourceInstCheckReq req) {
        //申请单明细主键集合
        List<String> mktResInstNbrs = req.getMktResInstNbrs();
        if (CollectionUtils.isEmpty(mktResInstNbrs)) {
            return ResultVO.error("请选择要审核的内容");
        }
        log.info("AdminResourceInstServiceImpl.batchAuditNbr mktResInstNbrSize={}", JSON.toJSONString(mktResInstNbrs.size()));
        //根据串码获取到待审核的申请详情
        Date now = new Date();
        ResourceReqDetailQueryReq resourceReqDetailQueryReq = new ResourceReqDetailQueryReq();
        resourceReqDetailQueryReq.setMktResInstNbrs(mktResInstNbrs);
        resourceReqDetailQueryReq.setPageNo(1);
        resourceReqDetailQueryReq.setPageSize(mktResInstNbrs.size()*2);
        resourceReqDetailQueryReq.setStatusCd(ResourceConst.DetailStatusCd.STATUS_CD_1009.getCode());
        resourceReqDetailQueryReq.setReqType(ResourceConst.REQTYPE.PUTSTORAGE_APPLYFOR.getCode());
        Page<ResourceReqDetailPageDTO> respPage = resourceReqDetailManager.listResourceRequestPage(resourceReqDetailQueryReq);
        List<ResourceReqDetailPageDTO> resDetailList = respPage.getRecords();
        if (CollectionUtils.isEmpty(resDetailList)) {
            return ResultVO.error("没有待审批的内容");
        }
        //申请单id,用于判断申请单下的明细是否都已被处理，处理了修改申请单状态
        List<String> reqIds = resDetailList.stream().map(ResourceReqDetailPageDTO::getMktResReqId).collect(Collectors.toList());
        //按照申请单号进行分组
        Map<String, List<ResourceReqDetailPageDTO>> map = resDetailList.stream().collect(Collectors.groupingBy(t -> t.getMktResReqId()));
        //Map<Date, List<ResourceReqDetailPageDTO>> map = resDetailList.stream().collect(Collectors.groupingBy(t -> t.getCreateDate()));
        final String batchId = UUID.randomUUID().toString();
        String statusCd=req.getCheckStatusCd();
        //审核不通过
        if(ResourceConst.DetailStatusCd.STATUS_CD_1004.getCode().equals(statusCd)){
            ResourceReqDetailUpdateReq detailUpdateReq = new ResourceReqDetailUpdateReq();
            detailUpdateReq.setUpdateStaff(req.getUpdateStaff());
            detailUpdateReq.setUpdateDate(now);
            detailUpdateReq.setStatusDate(now);
            detailUpdateReq.setStatusCd(statusCd);
            for (Map.Entry<String, List<ResourceReqDetailPageDTO>> entry : map.entrySet()) {
                List<String> mktResReqDetailIdList = entry.getValue().stream().map(ResourceReqDetailPageDTO::getMktResReqDetailId).collect(Collectors.toList());
                detailUpdateReq.setMktResReqDetailIds(mktResReqDetailIdList);

                detailUpdateReq.setCreateDate(entry.getValue().get(0).getCreateDate());
                //修改明细状态
                resourceReqDetailManager.updateDetailByNbrs(detailUpdateReq);
                //验证明细是否全部处理，修改申请单状态
                ResourceReqUpdateReq resourceReqUpdateReq = new ResourceReqUpdateReq();
                resourceReqUpdateReq.setMktResReqId(entry.getKey());
                resourceReqUpdateReq.setUpdateStaff(req.getUpdateStaff());
                resourceReqUpdateReq.setUpdateStaffName(req.getUpdateStaffName());
                checkResRequestFinish(resourceReqUpdateReq);
            }

        }else if(ResourceConst.DetailStatusCd.STATUS_CD_1005.getCode().equals(statusCd)){
            //审核通过
            //for (Map.Entry<Date, List<ResourceReqDetailPageDTO>> entry : map.entrySet()) {
                runableTask.auditPassResDetail(resDetailList,req.getUpdateStaff(),req.getUpdateStaffName());
            //}
        }
        return ResultVO.success("审核成功");
    }

    /**
     * 判断申请单下的申请明细是否都已完成，完成了修改申请单状态
     * @param
     */
    @Override
    public ResultVO<String> checkResRequestFinish(ResourceReqUpdateReq resourceReqUpdateReq) {
        String resReqId = resourceReqUpdateReq.getMktResReqId();
        //获取该申请单明细总数
        ResourceReqDetailReq req=new ResourceReqDetailReq();
        req.setMktResReqId(resReqId);
        int total=resourceReqDetailManager.resourceRequestCount(req);
        //获取该申请单审核中的明细总数
        req.setStatusCd(ResourceConst.DetailStatusCd.STATUS_CD_1009.getCode());
        int waitNum=resourceReqDetailManager.resourceRequestCount(req);
        //存在未审核的明细，暂不做处理
        if (waitNum > 0)
            return ResultVO.success();
        if (total > 0 && waitNum == 0){
            //申请单下的明细已全部审核过，判断是否全成功
            //获取该申请单审核成功的明细总数
            req.setStatusCd(ResourceConst.DetailStatusCd.STATUS_CD_1005.getCode());
            int valdNum=resourceReqDetailManager.resourceRequestCount(req);
            //获取申请单详情
            ResourceRequestItemQueryReq resourceRequestItemQueryReq=new ResourceRequestItemQueryReq();
            resourceRequestItemQueryReq.setMktResReqId(resReqId);
            ResourceRequest resourceRequest=resourceRequestManager.queryResourceRequest(resourceRequestItemQueryReq);
            if(resourceRequest==null){
                log.info("AdminResourceInstServiceImpl.dealResRequest 根据resReqId未找到对应申请单，resReqId={}",resReqId);
                return ResultVO.success();
            }
            ResourceRequestUpdateReq updateReq=new ResourceRequestUpdateReq();
            updateReq.setMktResReqId(resReqId);
            updateReq.setCreateDate(resourceRequest.getCreateDate());
            if (valdNum == total){
                //审核状态改为成功
                updateReq.setStatusCd(ResourceConst.MKTRESSTATE.REVIEWED.getCode());
            }else{
                //审核状态改为完成
                updateReq.setStatusCd(ResourceConst.MKTRESSTATE.DONE.getCode());
            }
            resourceRequestManager.updateResourceRequestStatus(updateReq);
            //将流程办结
            ResourceProcessUpdateReq resourceProcessUpdateReq = new ResourceProcessUpdateReq();
            BeanUtils.copyProperties(resourceReqUpdateReq, resourceProcessUpdateReq);
            resourceProcessUpdateReq.setFormId(resReqId);
            finishProcess(resourceProcessUpdateReq);
        }
        return ResultVO.success();
    }



    /**
     * 办结串码审核流程
     * @param
     */
    private void finishProcess(ResourceProcessUpdateReq resourceProcessUpdateReq) {
        //根据formId找到task
        String formId=resourceProcessUpdateReq.getFormId();
        ResultVO<List<TaskDTO>> taskResult=this.taskService.getTaskByFormId(formId);
        if(!taskResult.isSuccess()||CollectionUtils.isEmpty(taskResult.getResultData())||taskResult.getResultData().size()>1){
            log.info("AdminResourceInstServiceImpl.finishProcess.getTaskByFormId formId ={},taskList={}", formId, JSON.toJSONString(taskResult.getResultData()));
            return;
        }
        List<TaskDTO> taskList=taskResult.getResultData();
        TaskDTO task = taskList.get(0);
//        //找到taskItem
//        ResultVO<TaskItemDTO> taskItemDTOResultVO=taskItemService.queryTaskItemByTaskId(task.getTaskId());
//        if(!taskItemDTOResultVO.isSuccess()||taskItemDTOResultVO.getResultData()==null){
//            log.info("AdminResourceInstServiceImpl.finishProcess.queryTaskItemByTaskId taskId ={},taskList={}", task.getTaskId(), JSON.toJSONString(taskItemDTOResultVO.getResultData()));
//        }
//        TaskItemDTO taskItem=taskItemDTOResultVO.getResultData();
        //根据路由找到下一节点可选项
        RouteReq routeReq=new RouteReq();
        routeReq.setProcessId(task.getProcessId());
        routeReq.setCurNodeId(task.getCurNodeId());
        ResultVO<List<RouteDTO>> routeResult=routeService.listRoute(routeReq);
        log.info("AdminResourceInstServiceImpl.finishProcess.routeResult req={} resp={}",JSON.toJSONString(routeReq),JSON.toJSONString(routeResult));
        if(!routeResult.isSuccess()){
            return;
        }
        //判断下一节点是否有结束节点，如果有，执行该节点
        List<RouteDTO>  routeList=routeResult.getResultData();
        for(RouteDTO route : routeList) {
            if (WorkFlowConst.WF_NODE.NODE_END.getId().equals(route.getNextNodeId())) {
                // 执行流程下一步
                taskService.endProcess(resourceProcessUpdateReq.getUpdateStaff(), resourceProcessUpdateReq.getUpdateStaffName(), task.getTaskId(), route.getRouteId());
//                RouteNextReq routeNextReq =new RouteNextReq();
//                routeNextReq.setTaskItemId(taskItem.getTaskItemId());
//                routeNextReq.setNextNodeId(route.getNextNodeId());
//                routeNextReq.setRouteId(route.getRouteId());
//                routeNextReq.setTaskId(task.getTaskId());
//                routeNextReq.setHandlerUserId(resourceProcessUpdateReq.getUpdateStaff());
//                routeNextReq.setHandlerUserName(resourceProcessUpdateReq.getUpdateStaffName());
//                routeNextReq.setHandlerMsg("审核结束");
//                taskService.nextRoute(routeNextReq);
            }
        }

    }

    /**
     * 处理审核通过明细
     * @param
     */
   /* @Transactional(rollbackFor = Exception.class)
    public ResultVO<Object> auditPassResDetail(List<ResourceReqDetailPageDTO> resDetailList) {
        log.info("AdminResourceInstServiceImpl.auditPassResDetail.auditPassResDetail resDetailList={}", JSON.toJSONString(resDetailList));
        //根据申请单id进行分组,同一个申请单下，产品商家等信息一致，减少请求服务次数
        Map<String, List<ResourceReqDetailPageDTO>> map = resDetailList.stream().collect(Collectors.groupingBy(t -> t.getMktResReqId()));
        for (Map.Entry<String, List<ResourceReqDetailPageDTO>> entry :map.entrySet()){
            String mktResReqId=entry.getKey();
            List<ResourceReqDetailPageDTO> detailList=entry.getValue();
            //获取申请单下所有详情
//            ResourceReqDetailQueryReq detailQueryReq = new ResourceReqDetailQueryReq();
//            detailQueryReq.setMktResReqId(mktResReqId);
//            List<ResourceReqDetailDTO> reqDetailDTOS = detailManager.listDetail(detailQueryReq);
            List<String> mktResInstNbrs=detailList.stream().map(ResourceReqDetailPageDTO::getMktResInstNbr).collect(Collectors.toList());
            log.info("AdminResourceInstServiceImpl.auditPassResDetail mktResInstNbrs={}", JSON.toJSONString(mktResInstNbrs));
            Map<String, String> ctCodeMap = new HashMap<>();
            Map<String, String> snCodeMap = new HashMap<>();
            Map<String, String> macCodeMap = new HashMap<>();
            detailList.forEach(item->{
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
            //获取相关产品信息
            ResourceReqDetailPageDTO detailDTO = detailList.get(0);
            ProductGetByIdReq productGetByIdReq = new ProductGetByIdReq();
            productGetByIdReq.setProductId(detailDTO.getMktResId());
            ResultVO<ProductResp> producttVO = productService.getProduct(productGetByIdReq);
            log.info("AdminResourceInstServiceImpl.auditPassResDetail.getProduct mktResId={} resp={}", mktResReqId, JSON.toJSONString(producttVO));
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
            addReq.setMktResInstType(detailDTO.getMktResInstType());
            addReq.setDestStoreId(detailDTO.getDestStoreId());
            addReq.setMktResId(detailDTO.getMktResId());
            addReq.setCtCodeMap(ctCodeMap);
            addReq.setSnCodeMap(snCodeMap);
            addReq.setMacCodeMap(macCodeMap);
            addReq.setCreateStaff(detailDTO.getCreateStaff());
            addReq.setTypeId(typeId);
            ResultVO<MerchantDTO> resultVO = resouceStoreService.getMerchantByStore(detailDTO.getDestStoreId());
            String merchantId = null;
            if(null == resultVO || null == resultVO.getResultData()){
                log.warn("AdminResourceInstServiceImpl.auditPassResDetail resouceStoreService.getMerchantByStore resultVO is null");
                return ResultVO.error("AdminResourceInstServiceImpl.auditPassResDetail resouceStoreService.getMerchantByStore resultVO is null");
            } else {
                MerchantDTO merchantDTO = resultVO.getResultData();
                merchantId = merchantDTO.getMerchantId();
                addReq.setLanId(merchantDTO.getLanId());
                addReq.setRegionId(merchantDTO.getCity());
                addReq.setMerchantId(merchantId);
            }
            runableTask.exceutorAddNbr(addReq);
            log.info("AdminResourceInstServiceImpl.auditPassResDetail runableTask.exceutorAddNbr addReq={}", addReq);

            // step3 增加事件和批次
            Map<String, List<String>> mktResIdAndNbrMap = this.getMktResIdAndNbrMap(detailList);
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
            log.info("AdminResourceInstServiceImpl.auditPassResDetail resourceBatchRecService.saveEventAndBatch req={},resp={}", JSON.toJSONString(batchAndEventAddReq));
            runableTask.exceutorAddNbrTrack(addReq);

        }

        //runableTask.exceutorAddNbrTrack(addReq);
        return ResultVO.success();
    }*/

    /*private Map<String, List<String>> getMktResIdAndNbrMap(List<ResourceReqDetailPageDTO> instList){
        Map<String, List<String>> mktResIdAndNbrMap = new HashMap<>();
        List<ResourceReqDetailPageDTO> detailList = instList;
        for (ResourceReqDetailPageDTO resp : detailList){
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
    }*/

    @Override
    public ResultVO<String> uploadNbrDetail(List<ExcelResourceReqDetailDTO> data, String userId) {
        String batchId = runableTask.exceutorNbrValid(data,userId);
        if(batchId==null){
            return ResultVO.error("导入excel出错");
        }
        return ResultVO.success(batchId);
    }

    @Override
    public ResultVO<String> submitNbrAudit(ResourceUploadTempListPageReq req) {
        //查询审核成功的串码集合
        req.setResult(ResourceConst.CONSTANT_NO);
        req.setStatusCd(ResourceConst.DetailStatusCd.STATUS_CD_1005.getCode());
        req.setPageSize(100000);
        req.setPageNo(1);
        Page<ResourceUploadTempListResp> pagePass=resourceUploadTempManager.listResourceUploadTemp(req);
        List<ResourceUploadTempListResp> passList=pagePass.getRecords();
        //查询审核失败的串码集合
        req.setStatusCd(ResourceConst.DetailStatusCd.STATUS_CD_1004.getCode());
        Page<ResourceUploadTempListResp> pageFail=resourceUploadTempManager.listResourceUploadTemp(req);
        List<ResourceUploadTempListResp> failList=pageFail.getRecords();
        //审核通过的串码
        if(CollectionUtils.isNotEmpty(passList)){
            ResourceInstCheckReq checkPassReq=new ResourceInstCheckReq();
            checkPassReq.setMktResInstNbrs(passList.stream().map(ResourceUploadTempListResp::getMktResInstNbr).collect(Collectors.toList()));
            checkPassReq.setCheckStatusCd(ResourceConst.DetailStatusCd.STATUS_CD_1005.getCode());
            checkPassReq.setUpdateStaff(req.getUpdateStaff());
            batchAuditNbr(checkPassReq);
        }
        //审核失败的串码
        if(CollectionUtils.isNotEmpty(failList)){
            //根据审核说明进行分组
            Map<String, List<ResourceUploadTempListResp>> map = failList.stream().collect(Collectors.groupingBy(t -> t.getRemark()));
            ResourceInstCheckReq checkFailReq=new ResourceInstCheckReq();
            checkFailReq.setCheckStatusCd(ResourceConst.DetailStatusCd.STATUS_CD_1004.getCode());
            checkFailReq.setUpdateStaff(req.getUpdateStaff());
            for(Map.Entry<String,List<ResourceUploadTempListResp>> entry : map.entrySet()){
                checkFailReq.setMktResInstNbrs(entry.getValue().stream().map(ResourceUploadTempListResp::getMktResInstNbr).collect(Collectors.toList()));
                checkFailReq.setRemark(entry.getKey());
                batchAuditNbr(checkFailReq);
            }
        }
        //删除临时记录
        ResourceUploadTempDelReq resourceUploadTempDelReq = new ResourceUploadTempDelReq();
        resourceUploadTempDelReq.setMktResUploadBatch(req.getMktResUploadBatch());
        resourceUploadTempManager.delResourceUploadTemp(resourceUploadTempDelReq);
        return  ResultVO.success("批量审核串码成功");
    }

    @Override
    public ResultVO<ResourceUploadTempCountResp> countResourceUploadTemp(ResourceUploadTempDelReq req) {
        // 多线程没跑完，返回空
        if (runableTask.validNbrHasDone()) {
            //查询成功次数
            req.setResult(ResourceConst.CONSTANT_NO);
            int passNum=resourceUploadTempManager.countTotal(req);
            //查询失败次数
            req.setResult(ResourceConst.CONSTANT_YES);
            int failNum=resourceUploadTempManager.countTotal(req);
            ResourceUploadTempCountResp resp=new ResourceUploadTempCountResp();
            resp.setPassNum(passNum);
            resp.setFailNum(failNum);
            return ResultVO.success(resp);
        } else{
            return ResultVO.success();
        }



    }

    @Override
    public ResultVO<Boolean> validBatchAuditNbr() {
        if (runableTask.validBatchAuditNbr()) {
            return ResultVO.success(true);
        } else{
            return ResultVO.success(false);
        }
    }

}
