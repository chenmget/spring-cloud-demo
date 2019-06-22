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
            List<String> detailIds=uploadList.stream().map(ResourceUploadTempListResp::getMktResReqDetailId).collect(Collectors.toList());
            ResourceReqDetailQueryReq queryReq=new ResourceReqDetailQueryReq();
            queryReq.setPageNo(1);
            queryReq.setPageSize(req.getPageSize());
            queryReq.setMktResReqDetailIds(detailIds);
            //queryReq.setStatusCd(ResourceConst.DetailStatusCd.STATUS_CD_WAIT.getCode());
            Page<ResourceReqDetailPageDTO> detailPage = resourceReqDetailManager.listResourceRequestPage(queryReq);
            Page<ResourceReqDetailPageResp> respPage = new Page<ResourceReqDetailPageResp>();
            BeanUtils.copyProperties(page, respPage);
            //组装数据
            List<ResourceReqDetailPageDTO> detailList=detailPage.getRecords();
            List<ResourceReqDetailPageResp> resultList=new ArrayList<>();
            for(ResourceUploadTempListResp temp : uploadList){
                ResourceReqDetailPageResp resp=new ResourceReqDetailPageResp();
                Optional<ResourceReqDetailPageDTO> option =detailList.stream().filter(t->t.getMktResInstNbr().equals(temp.getMktResInstNbr())).findFirst();
                if(option.isPresent()){
                    ResourceReqDetailPageDTO dto=option.get();
                    BeanUtils.copyProperties(dto, resp);
                    resp.setResultDesc(temp.getResultDesc());
                    resp.setRemark(temp.getRemark());
                    //获取产品信息
                    ProductResourceInstGetReq productQueryReq = new ProductResourceInstGetReq();
                    productQueryReq.setProductId(dto.getMktResId());
                    ResultVO<List<ProductResourceResp>> resultVO = productService.getProductResource(productQueryReq);
                    List<ProductResourceResp> prodList = resultVO.getResultData();
                    if (CollectionUtils.isEmpty(prodList)) {
                        continue;
                    }
                    ProductResourceResp prodResp = prodList.get(0);
                    //填充审核状态
                    resp.setStatusCdName(ResourceConst.DetailStatusCd.getNameByCode(temp.getStatusCd()));
                    // 添加产品信息
                    BeanUtils.copyProperties(prodResp, resp);
                    resultList.add(resp);
                    //删除该信息，否则串码重复情况下，取到的详情永远是第一个
                    detailList.remove(dto);
                }

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
        //TODO 验证当前申请单是否该审批人审批
        //申请单明细主键集合
        List<String> mktResReqDetailIds=req.getMktResReqDetailIds();
        log.info("AdminResourceInstServiceImpl.batchAuditNbr mktResReqDetailIds={}", JSON.toJSONString(mktResReqDetailIds));
        if(CollectionUtils.isEmpty(mktResReqDetailIds)){
            return ResultVO.error("请选择要审核的内容");
        }
        //根据mktResReqDetailIds获取到待审核的申请详情等
        Date now = new Date();
        ResourceReqDetailQueryReq resourceReqDetailQueryReq=new ResourceReqDetailQueryReq();
        resourceReqDetailQueryReq.setMktResReqDetailIds(mktResReqDetailIds);
        resourceReqDetailQueryReq.setPageNo(1);
        resourceReqDetailQueryReq.setPageSize(mktResReqDetailIds.size());
        resourceReqDetailQueryReq.setStatusCd(ResourceConst.DetailStatusCd.STATUS_CD_1009.getCode());//状态为待审核
        Page<ResourceReqDetailPageDTO> respPage = resourceReqDetailManager.listResourceRequestPage(resourceReqDetailQueryReq);
        List<ResourceReqDetailPageDTO> resDetailList=respPage.getRecords();

        //符合修改状态的申请单
        List<String> reqIds = resDetailList.stream().map(ResourceReqDetailPageDTO::getMktResReqId).collect(Collectors.toList());
        List<String> detailIds = resDetailList.stream().map(ResourceReqDetailPageDTO::getMktResReqDetailId).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(detailIds)){
            return ResultVO.error("没有待审批的内容");
        }

        //修改申请单需要带上申请时间，所以根据申请时间进行分组
        Map<Date, List<ResourceReqDetailPageDTO>> map = resDetailList.stream().collect(Collectors.groupingBy(t -> t.getCreateDate()));
        for (Map.Entry<Date,List<ResourceReqDetailPageDTO>> entry:map.entrySet()){
            ResourceReqDetailUpdateReq detailUpdateReq = new ResourceReqDetailUpdateReq();
            detailUpdateReq.setMktResReqDetailIdList(entry.getValue().stream().map(ResourceReqDetailPageDTO::getMktResReqDetailId).collect(Collectors.toList()));
            detailUpdateReq.setUpdateStaff(req.getUpdateStaff());
            detailUpdateReq.setUpdateDate(now);
            detailUpdateReq.setStatusDate(now);
            detailUpdateReq.setCreateDate(entry.getKey());
            detailUpdateReq.setRemark(req.getRemark());
            //审核不通过
            if (req.getCheckStatusCd().equals(ResourceConst.DetailStatusCd.STATUS_CD_1004.getCode())) {
                detailUpdateReq.setStatusCd(ResourceConst.DetailStatusCd.STATUS_CD_1004.getCode());
                resourceReqDetailManager.updateDetailByIds(detailUpdateReq);
            }
            //审核通过
            if (req.getCheckStatusCd().equals(ResourceConst.DetailStatusCd.STATUS_CD_1005.getCode())) {
                //执行串码审核通过流程
                auditPassResDetail(resDetailList,reqIds);
                detailUpdateReq.setStatusCd(ResourceConst.DetailStatusCd.STATUS_CD_1005.getCode());
                resourceReqDetailManager.updateDetailByIds(detailUpdateReq);
            }
        }
        //验证明细是否已经全都完成
        ResourceReqUpdateReq resourceReqUpdateReq=new ResourceReqUpdateReq();
        resourceReqUpdateReq.setMktResReqIdList(reqIds);
        resourceReqUpdateReq.setUpdateStaff(req.getUpdateStaff());
        resourceReqUpdateReq.setUpdateStaffName(req.getUpdateStaffName());
        dealResRequest(resourceReqUpdateReq);
        return ResultVO.success("审核成功");
    }

    /**
     * 判断申请单下的申请明细是否都已完成，完成了修改申请单状态
     * @param
     */
    public ResultVO<String> dealResRequest(ResourceReqUpdateReq resourceReqUpdateReq) {
        List<String> reqIds = resourceReqUpdateReq.getMktResReqIdList();
        for(String resReqId : reqIds){
            //获取该申请单明细总数
            ResourceReqDetailReq req=new ResourceReqDetailReq();
            req.setMktResReqId(resReqId);
            int total=resourceReqDetailManager.resourceRequestCount(req);
            //获取该申请单审核中的明细总数
            req.setStatusCd(ResourceConst.DetailStatusCd.STATUS_CD_1009.getCode());
            int waitNum=resourceReqDetailManager.resourceRequestCount(req);
            //存在未审核的明细，暂不做处理
            if (waitNum > 0)
                break ;
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
                    continue;
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
    @Transactional(rollbackFor = Exception.class)
    public ResultVO<Object> auditPassResDetail(List<ResourceReqDetailPageDTO> resDetailList, List<String> reqIds) {
        log.info("AdminResourceInstServiceImpl.auditPassResDetail.auditPassResDetail resDetailList={} reqIds={}", JSON.toJSONString(resDetailList), reqIds);
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
    }

    private Map<String, List<String>> getMktResIdAndNbrMap(List<ResourceReqDetailPageDTO> instList){
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
    }

    @Override
    public ResultVO<String> uploadNbrDetail(List<ExcelResourceReqDetailDTO> data, String createStaff) {
        String batchId = runableTask.exceutorNbrValid(data,createStaff);
        if(batchId==null){
            return ResultVO.error("导入excel出错");
        }
        return ResultVO.success(batchId);
    }

    @Override
    public ResultVO<String> submitNbrAudit(ResourceUploadTempListPageReq req) {
        //获取批次id
        String mktResUploadBatch=req.getMktResUploadBatch();
        //查询审核成功的串码集合
        req.setResult(ResourceConst.CONSTANT_NO);
        req.setStatusCd(ResourceConst.DetailStatusCd.STATUS_CD_1005.getCode());
        List<ResourceUploadTempListResp> passList=resourceUploadTempManager.listResourceUpload(req);
        //查询审核失败的串码集合
        req.setStatusCd(ResourceConst.DetailStatusCd.STATUS_CD_1004.getCode());
        List<ResourceUploadTempListResp> failList=resourceUploadTempManager.listResourceUpload(req);
        //审核通过的串码
        if(CollectionUtils.isNotEmpty(passList)){
            ResourceInstCheckReq checkPassReq=new ResourceInstCheckReq();
            checkPassReq.setMktResReqDetailIds(passList.stream().map(ResourceUploadTempListResp::getMktResReqDetailId).collect(Collectors.toList()));
            checkPassReq.setCheckStatusCd(ResourceConst.DetailStatusCd.STATUS_CD_1005.getCode());
            checkPassReq.setUpdateStaff(req.getUpdateStaff());
            batchAuditNbr( checkPassReq);
        }
        //审核失败的串码
        if(CollectionUtils.isNotEmpty(failList)){
            ResourceInstCheckReq checkFailReq=new ResourceInstCheckReq();
            checkFailReq.setMktResReqDetailIds(failList.stream().map(ResourceUploadTempListResp::getMktResReqDetailId).collect(Collectors.toList()));
            checkFailReq.setCheckStatusCd(ResourceConst.DetailStatusCd.STATUS_CD_1004.getCode());
            checkFailReq.setUpdateStaff(req.getUpdateStaff());
            batchAuditNbr( checkFailReq);
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

}
