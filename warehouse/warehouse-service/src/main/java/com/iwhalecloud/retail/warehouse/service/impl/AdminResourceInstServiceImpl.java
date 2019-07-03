package com.iwhalecloud.retail.warehouse.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.req.ProductResourceInstGetReq;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductResourceResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.dto.req.MerchantGetReq;
import com.iwhalecloud.retail.partner.dto.req.MerchantListReq;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.warehouse.busiservice.ResouceInstTrackService;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.constant.Constant;
import com.iwhalecloud.retail.warehouse.dto.ExcelResourceReqDetailDTO;
import com.iwhalecloud.retail.warehouse.dto.ResouceInstTrackDTO;
import com.iwhalecloud.retail.warehouse.dto.ResourceInstDTO;
import com.iwhalecloud.retail.warehouse.dto.ResourceReqDetailPageDTO;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.dto.response.*;
import com.iwhalecloud.retail.warehouse.entity.ResouceUploadTemp;
import com.iwhalecloud.retail.warehouse.entity.ResourceRequest;
import com.iwhalecloud.retail.warehouse.manager.*;
import com.iwhalecloud.retail.warehouse.runable.RunableTask;
import com.iwhalecloud.retail.warehouse.service.AdminResourceInstService;
import com.iwhalecloud.retail.warehouse.service.MerchantResourceInstService;
import com.iwhalecloud.retail.warehouse.service.ResouceStoreService;
import com.iwhalecloud.retail.warehouse.service.SupplierResourceInstService;
import com.iwhalecloud.retail.warehouse.util.WarehouseCacheUtils;
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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminResourceInstServiceImpl implements AdminResourceInstService {

    @Autowired
    private ResourceInstService resourceInstService;

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

    @Autowired
    private ResouceInstTrackManager resouceInstTrackManager;

    @Reference
    private ResouceStoreService resouceStoreService;

    @Autowired
    private RunableTask runableTask;

    @Autowired
    private ResourceUploadTempManager resourceUploadTempManager;

    @Autowired
    private ResourceReqDetailManager resourceReqDetailManager;

    @Autowired
    private ResourceRequestManager resourceRequestManager;

    @Reference
    private ProductService productService;

    @Reference
    private TaskService taskService;

    @Reference
    private TaskItemService taskItemService;

    @Reference
    private RouteService routeService;

    @Autowired
    private WarehouseCacheUtils warehouseCacheUtils;

    @Value("${zop.secret}")
    private String zopSecret;

    @Value("${zop.url}")
    private String zopUrl;

    @Autowired
    private ResouceInstTrackDetailManager resouceInstTrackDetailManager;

    @Autowired
    private ResouceInstTrackService resouceInstTrackService;

    @Reference
    private MerchantService merchantService;


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

    /**
     * 管理员根据batchId删除串码
     * @param batchId 上一步导入时的批次号
     * @param userId  操作人的id
     * @return
     */
    @Override
    public ResultVO delResourceInstByBatchId(String batchId,String userId){
        log.info("ResourceInstServiceImpl.delResourceInstByBatchId batchId={}", batchId);
        // 1.根据batchId 查询临时表中有效数据信息
        ResourceUploadTempListPageReq resourceInstTempListReq = new ResourceUploadTempListPageReq();
        resourceInstTempListReq.setMktResUploadBatch(batchId);
        resourceInstTempListReq.setResult(ResourceConst.CONSTANT_NO);
        List<ResourceUploadTempListResp> resourceInstTempList = resourceUploadTempManager.listResourceUpload(resourceInstTempListReq);
        // 2.过滤出有效数据串码id信息
        List<String> mktResInstNbrs=resourceInstTempList.stream().map(ResourceUploadTempListResp::getMktResInstNbr).collect(Collectors.toList());
        // 3.根据串码ids信息查询营销资源轨迹表，获取完整数据
        ResourceInstsTrackGetReq instsTrackGetReq = new ResourceInstsTrackGetReq();
        instsTrackGetReq.setMktResInstNbrList(new CopyOnWriteArrayList(mktResInstNbrs));
        List<ResouceInstTrackDTO> instTrackList = resouceInstTrackManager.listResourceInstsTrack(instsTrackGetReq);
        // 4.将处理好的完整数据，根据仓库StoreId进行分组
        Map<String, List<ResouceInstTrackDTO>> groups = instTrackList.stream().collect(Collectors.groupingBy(t -> t.getMktResStoreId()));
        // 5.根据分组结果，调用原有的批量删除方法()进行批量删除处理
        List<String> unDeleteNbrs = Lists.newArrayList();
        for (Map.Entry<String,List<ResouceInstTrackDTO>> group:groups.entrySet()){
            String storeId = group.getKey();
            AdminResourceInstDelReq resourceInstDelReq = new AdminResourceInstDelReq();
            resourceInstDelReq.setUpdateStaff(userId);
            resourceInstDelReq.setStatusCd(ResourceConst.STATUSCD.DELETED.getCode());
            resourceInstDelReq.setEventType(ResourceConst.EVENTTYPE.CANCEL.getCode());
            // 只有可用状态的串码才能删除
            List<String> checkStatusCd = Lists.newArrayList(ResourceConst.STATUSCD.DELETED.getCode(),
                    ResourceConst.STATUSCD.AUDITING.getCode(),ResourceConst.STATUSCD.ALLOCATIONING.getCode(),
                    ResourceConst.STATUSCD.RESTORAGEING.getCode(),ResourceConst.STATUSCD.RESTORAGED.getCode(),
                    ResourceConst.STATUSCD.SALED.getCode());
            resourceInstDelReq.setCheckStatusCd(checkStatusCd);

            resourceInstDelReq.setMktResStoreId(group.getKey());
            List<ResouceInstTrackDTO> resourceInstDTOList = group.getValue();
            List<String> resInstNbrs=resourceInstDTOList.stream().map(ResouceInstTrackDTO::getMktResInstNbr).collect(Collectors.toList());
            //根据串码和仓库获取串码实例
            ResourceInstsGetReq instsGetReq = new ResourceInstsGetReq();
            instsGetReq.setMktResInstNbrs(resInstNbrs);
            instsGetReq.setMktResStoreId(storeId);
            List<ResourceInstDTO> instList = resourceInstManager.getResourceInsts(instsGetReq);
            List<String> resInstId = instList.stream().map(ResourceInstDTO::getMktResInstId).collect(Collectors.toList());
            resourceInstDelReq.setMktResInstIdList(resInstId);
            resourceInstDelReq.setDestStoreId(storeId);
            ResultVO resultVO = updateResourceInstByIds(resourceInstDelReq);
            List<String> unavailbaleNbrs = (List<String>)resultVO.getResultData();
            unDeleteNbrs.addAll(unavailbaleNbrs);
            //删除轨迹表数据
            AdminResourceInstDelReq adminResourceInstDelReq = new AdminResourceInstDelReq();
            adminResourceInstDelReq.setMktResStoreId(storeId);
            adminResourceInstDelReq.setMktResInstIdList(resInstId);
            adminResourceInstDelReq.setDestStoreId(storeId);
            resouceInstTrackService.asynUpdateTrackForAddmin(adminResourceInstDelReq, ResultVO.success(unavailbaleNbrs));
        }
        return ResultVO.success(unDeleteNbrs);
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


    /**
     * 判断申请单下的申请明细是否都已完成，完成了修改申请单状态
     * @param
     */
    @Override
    public ResultVO<String> checkResRequestFinish(ResourceReqUpdateReq resourceReqUpdateReq) {
        String resReqId = resourceReqUpdateReq.getMktResReqId();
        //获取该申请单明细总数
        ResourceReqDetailReq req = new ResourceReqDetailReq();
        req.setMktResReqId(resReqId);

        //获取该申请单审核中的明细总数
        req.setStatusCd(ResourceConst.DetailStatusCd.STATUS_CD_1009.getCode());
        int waitNum = resourceReqDetailManager.resourceRequestCount(req);
        //存在未审核的明细，暂不做处理
        if (waitNum > 0) {
            return ResultVO.success();
        }

        if (waitNum == 0) {
            //申请单下的明细已全部审核过，判断是否全成功
            //获取该申请单审核成功的明细总数
            req.setStatusCd(ResourceConst.DetailStatusCd.STATUS_CD_1005.getCode());

            //获取申请单详情
            ResourceRequestItemQueryReq resourceRequestItemQueryReq = new ResourceRequestItemQueryReq();
            resourceRequestItemQueryReq.setMktResReqId(resReqId);
            ResourceRequest resourceRequest = resourceRequestManager.queryResourceRequest(resourceRequestItemQueryReq);
            if (resourceRequest == null) {
                log.info("AdminResourceInstServiceImpl.dealResRequest 根据resReqId未找到对应申请单，resReqId={}", resReqId);
                return ResultVO.success();
            }
            ResourceRequestUpdateReq updateReq = new ResourceRequestUpdateReq();
            updateReq.setMktResReqId(resReqId);
            updateReq.setCreateDate(resourceRequest.getCreateDate());
            //审核状态改为完成
            updateReq.setStatusCd(ResourceConst.MKTRESSTATE.DONE.getCode());

            resourceRequestManager.updateResourceRequestStatus(updateReq);

            //将流程办结
            ResourceProcessUpdateReq resourceProcessUpdateReq = new ResourceProcessUpdateReq();
            BeanUtils.copyProperties(resourceReqUpdateReq, resourceProcessUpdateReq);
            resourceProcessUpdateReq.setFormId(resReqId);
            finishProcess(resourceProcessUpdateReq);
        }
        return ResultVO.success();
    }


    @Override
    public ResultVO<String> batchAuditNbr(ResourceInstCheckReq req) {
        //申请单明细主键集合
        List<String> mktResInstNbrs = req.getMktResInstNbrs();
        if (CollectionUtils.isEmpty(mktResInstNbrs)) {
            return ResultVO.error("请选择要审核的内容");
        }
        log.info("AdminResourceInstServiceImpl.batchAuditNbr mktResInstNbrSize={}", JSON.toJSONString(mktResInstNbrs.size()));

        //根据串码获取到待审核的申请详情
        List<ResourceReqDetailPageDTO> resDetailList = getResourceReqDetailPageDTOS(mktResInstNbrs);
        if (CollectionUtils.isEmpty(resDetailList)) {
            return ResultVO.error("没有待审批的内容");
        }

        String statusCd = req.getCheckStatusCd();

        //审核不通过
        if (ResourceConst.DetailStatusCd.STATUS_CD_1004.getCode().equals(statusCd)) {
            doAuditResNbrUnPass(req, resDetailList);
        } else if (ResourceConst.DetailStatusCd.STATUS_CD_1005.getCode().equals(statusCd)) {
            warehouseCacheUtils.put(ResourceConst.ADD_NBR_INST , ResourceConst.ADD_NBR_INST );
            runableTask.auditPassResDetail(resDetailList, req.getUpdateStaff(), req.getUpdateStaffName());
        }
        return ResultVO.success("审核成功");
    }

    /**
     * 审核串码不通过
     * @param req
     * @param resDetailList
     */
    private void doAuditResNbrUnPass(ResourceInstCheckReq req, List<ResourceReqDetailPageDTO> resDetailList) {
        final Date now = new Date();
        //按照申请单号进行分组
        Map<String, List<ResourceReqDetailPageDTO>> mktResReqMap = resDetailList.stream().collect(Collectors.groupingBy(t -> t.getMktResReqId()));
        ResourceReqDetailUpdateReq detailUpdateReq = new ResourceReqDetailUpdateReq();
        detailUpdateReq.setUpdateStaff(req.getUpdateStaff());
        detailUpdateReq.setUpdateDate(now);
        detailUpdateReq.setStatusDate(now);
        detailUpdateReq.setStatusCd(ResourceConst.DetailStatusCd.STATUS_CD_1004.getCode());

        long statTime = System.currentTimeMillis();
        log.info("AdminResourceInstServiceImpl.doAuditResNbrUnPass startTime={}", statTime);
        for (Map.Entry<String, List<ResourceReqDetailPageDTO>> entry : mktResReqMap.entrySet()) {
            long updateDetailByDetailIdsStatTime = System.currentTimeMillis();
            log.info("AdminResourceInstServiceImpl.doAuditResNbrUnPass updateDetailByDetailIds reqId={} startTime={}", entry.getKey(), updateDetailByDetailIdsStatTime);
            //获取申请单中的详情ID集合
            List<String> mktResReqDetailIdList = entry.getValue().stream().map(ResourceReqDetailPageDTO::getMktResReqDetailId).collect(Collectors.toList());
            detailUpdateReq.setMktResReqDetailIds(mktResReqDetailIdList);
            //修改明细状态
            resourceReqDetailManager.updateDetailByNbrs(detailUpdateReq);
            long updateDetailByDetailIdsEndTime = System.currentTimeMillis();
            long updateDetailByDetailIdsConsuming = updateDetailByDetailIdsEndTime - updateDetailByDetailIdsStatTime;
            log.info("AdminResourceInstServiceImpl.doAuditResNbrUnPass updateDetailByDetailIds reqId={} endTime={} consumeTime={}", entry.getKey(), statTime, updateDetailByDetailIdsConsuming);

            //验证明细是否全部处理，修改申请单状态
            long checkResRequestFinishStatTime = System.currentTimeMillis();
            log.info("AdminResourceInstServiceImpl.doAuditResNbrUnPass checkResRequestFinish reqId={} startTime={}", entry.getKey(), checkResRequestFinishStatTime);
            ResourceReqUpdateReq resourceReqUpdateReq = new ResourceReqUpdateReq();
            resourceReqUpdateReq.setMktResReqId(entry.getKey());
            resourceReqUpdateReq.setUpdateStaff(req.getUpdateStaff());
            resourceReqUpdateReq.setUpdateStaffName(req.getUpdateStaffName());
            checkResRequestFinish(resourceReqUpdateReq);
            long checkResRequestFinishEndTime = System.currentTimeMillis();
            long checkResRequestFinishConsuming = updateDetailByDetailIdsEndTime - updateDetailByDetailIdsStatTime;
            log.info("AdminResourceInstServiceImpl.doAuditResNbrUnPass checkResRequestFinish reqId={} endTime={} consumeTime={}", entry.getKey(), checkResRequestFinishEndTime, checkResRequestFinishConsuming);
        }
        long endTime = System.currentTimeMillis();
        long timeConsuming = endTime - statTime;
        log.info("AdminResourceInstServiceImpl.doAuditResNbrUnPass endTime={},timeConsuming={}", endTime, timeConsuming);
    }

    /**
     * 根据串码获取到待审核的申请详情
     * @param mktResInstNbrs 串码列表
     * @return
     */
    private List<ResourceReqDetailPageDTO> getResourceReqDetailPageDTOS(List<String> mktResInstNbrs) {
        ResourceReqDetailQueryReq resourceReqDetailQueryReq = new ResourceReqDetailQueryReq();
        resourceReqDetailQueryReq.setMktResInstNbrs(mktResInstNbrs);
        resourceReqDetailQueryReq.setPageNo(1);
        resourceReqDetailQueryReq.setPageSize(mktResInstNbrs.size() * 2);
        resourceReqDetailQueryReq.setStatusCd(ResourceConst.DetailStatusCd.STATUS_CD_1009.getCode());
        resourceReqDetailQueryReq.setReqType(ResourceConst.REQTYPE.PUTSTORAGE_APPLYFOR.getCode());
        resourceReqDetailQueryReq.setSearchCount(false);
        Page<ResourceReqDetailPageDTO> respPage = resourceReqDetailManager.listResourceRequestPage(resourceReqDetailQueryReq);
        return respPage.getRecords();
    }

    /**
     * 办结串码审核流程
     * @param
     */
    private void finishProcess(ResourceProcessUpdateReq resourceProcessUpdateReq) {
        //根据formId找到task
        String formId = resourceProcessUpdateReq.getFormId();
        ResultVO<List<TaskDTO>> taskResult = this.taskService.getTaskByFormId(formId);
        if (!taskResult.isSuccess() || CollectionUtils.isEmpty(taskResult.getResultData()) || taskResult.getResultData().size() > 1) {
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

            }
        }

    }

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
        //判断是否存在正在执行的任务
        if(null != warehouseCacheUtils.get(ResourceConst.ADD_NBR_INST)){
            return ResultVO.error("存在正在执行的串码入库操作，请稍后再试");
        }
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
        }else{
            //不存在审核通过的串码，清空执行标识
            warehouseCacheUtils.evict(ResourceConst.ADD_NBR_INST);
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
        return ResultVO.success(runableTask.validBatchAuditNbr());
    }

    @Override
    public ResultVO<ResourceUploadTempCountResp> uploadDelResourceInst(List<ExcelResourceReqDetailDTO> data, String userId) {
        if(CollectionUtils.isEmpty(data)){
            return  ResultVO.error("上传内容为空");
        }
        String batchId = resourceInstService.getPrimaryKey();
        //获取串码集合
        List<String> mktResInstNbrList = data.stream().map(t -> t.getMktResInstNbr().trim()).distinct().collect(Collectors.toList());
        //根据串码获取串码轨迹数据
        ResourceInstsTrackGetReq req = new ResourceInstsTrackGetReq();
        req.setMktResInstNbrList(new CopyOnWriteArrayList(mktResInstNbrList));
        List<ResouceInstTrackDTO> instTrackList = resouceInstTrackManager.listResourceInstsTrack(req);
        //临时表集合
        List<ResouceUploadTemp> tempList = new ArrayList<ResouceUploadTemp>(mktResInstNbrList.size());
        Date now = new Date();
        //获取厂家id集合
        List<String> merchantIdList = instTrackList.stream().map(t -> t.getMerchantId()).distinct().collect(Collectors.toList());
        //获取厂家id集合
        List<MerchantDTO> merchantDTOList = getMerchantByMerchantIdList(merchantIdList);

        //厂家列表转换为Map<merchantId,merchantType>
        Map<String,String> merchantMap = merchantDTOList.stream().collect(Collectors.toMap(MerchantDTO::getMerchantId, MerchantDTO::getMerchantType));

        //遍历判断串码是否符合厂商或供应商在库可用串码
        for(String mktResInstNbr : mktResInstNbrList){
            Optional<ResouceInstTrackDTO> optional = instTrackList.stream().filter(t->t.getMktResInstNbr().equals(mktResInstNbr)).findFirst();
            ResouceUploadTemp uploadTemp = getResourceTempByInstOptional(optional,merchantMap);
            uploadTemp.setMktResInstNbr(mktResInstNbr);
            uploadTemp.setMktResUploadBatch(batchId);
            uploadTemp.setUploadDate(now);
            uploadTemp.setCreateDate(now);
            uploadTemp.setCreateStaff(userId);
            tempList.add(uploadTemp);
        }
        //结果写入临时表
        resourceUploadTempManager.saveBatch(tempList);
        //返回结果
        ResourceUploadTempCountResp resp = new ResourceUploadTempCountResp();
        resp.setMktResUploadBatch(batchId);
        Long passNum = tempList.stream().filter(t->t.getResult().equals(ResourceConst.CONSTANT_NO)).count();
        resp.setPassNum(passNum.intValue());
        resp.setFailNum(tempList.size()-passNum.intValue());
        return ResultVO.success(resp);
    }

    private ResouceUploadTemp getResourceTempByInstOptional(Optional<ResouceInstTrackDTO> optional , Map<String,String> merchantMap) {
        ResouceUploadTemp uploadTemp = new ResouceUploadTemp();
        //默认有异常
        uploadTemp.setResult(ResourceConst.CONSTANT_YES);
        String resultDesc="";
        if(optional.isPresent()){
            //实例表存在数据
            ResouceInstTrackDTO resouceInstTrackDTO = optional.get();
            //串码实例状态
            String statusCd = resouceInstTrackDTO.getStatusCd();
            //厂商类型
            String merchantType = "";
            if(merchantMap != null && !merchantMap.isEmpty()){
                merchantType = merchantMap.get(resouceInstTrackDTO.getMerchantId());
            }
            if(!ResourceConst.STATUSCD.AVAILABLE.getCode().equals(statusCd)){
                //串码非在库可用
                resultDesc = constant.getPesInstInvalid();
            }else if(!PartnerConst.MerchantTypeEnum.MANUFACTURER.getType().equals(merchantType) && !PartnerConst.MerchantTypeEnum.SUPPLIER_GROUND.getType().equals(merchantType) && !PartnerConst.MerchantTypeEnum.SUPPLIER_PROVINCE.getType().equals(merchantType)){
                //串码厂商类型非法
                resultDesc = "串码必须归属厂商或者供应商";
            }else{
                //合法串码
                uploadTemp.setResult(ResourceConst.CONSTANT_NO);
            }
            uploadTemp.setResultDesc(resultDesc);
        }else{
            //串码不存在
            uploadTemp.setResultDesc(constant.getNoResInst());
        }
        return uploadTemp;
    }

    @Override
    public ResultVO<Page<ResourceInstListPageResp>> listDelResourceInstTemp(ResourceUploadTempListPageReq req) {
        //获取临时表记录
        Page<ResourceUploadTempListResp> uploadTempPage = resourceUploadTempManager.listResourceUploadTemp(req);
        List<ResourceUploadTempListResp> uploadTempList = uploadTempPage.getRecords();
        List<String> mktResInstNbrList = uploadTempList.stream().map(t -> t.getMktResInstNbr()).collect(Collectors.toList());
        //根据串码获取串码轨迹数据
        ResourceInstsTrackGetReq instsTrackGetReq = new ResourceInstsTrackGetReq();
        instsTrackGetReq.setMktResInstNbrList(new CopyOnWriteArrayList(mktResInstNbrList));
        List<ResouceInstTrackDTO> instTrackList = resouceInstTrackManager.listResourceInstsTrack(instsTrackGetReq);

        //获取厂家id集合
        List<String> merchantIdList = instTrackList.stream().map(t -> t.getMerchantId()).distinct().collect(Collectors.toList());
        List<MerchantDTO> merchantDTOList = getMerchantByMerchantIdList(merchantIdList);
        //厂家列表转换为Map<merchantId,merchantType>
        Map<String,String> merchantMap = merchantDTOList.stream().collect(Collectors.toMap(MerchantDTO::getMerchantId, MerchantDTO::getMerchantType));

        List<ResourceInstListPageResp> instRespList = new ArrayList<>(instTrackList.size());
        for(ResouceInstTrackDTO dto : instTrackList){
            ResourceInstListPageResp resp = new ResourceInstListPageResp();
            BeanUtils.copyProperties(dto, resp);
            instRespList.add(resp);
        }
        //组装产品信息
        resourceInstService.fillResourceInst(instRespList);
        //组装审核结果
        List<ResourceInstListPageResp> resultList = new ArrayList<>(uploadTempList.size());
        for(ResourceUploadTempListResp tempListResp : uploadTempList){
            ResourceInstListPageResp resp = new ResourceInstListPageResp();
            BeanUtils.copyProperties(tempListResp, resp);
            for(ResourceInstListPageResp instResp : instRespList){
                if(instResp.getMktResInstNbr().equals(tempListResp.getMktResInstNbr())){
                    BeanUtils.copyProperties(instResp, resp);
                    //组装厂商信息
                    if(merchantMap !=null && !merchantMap.isEmpty()){
                        resp.setMerchantTypeName(PartnerConst.MerchantTypeEnum.getNameByType(merchantMap.get(instResp.getMerchantId())));
                    }
                    //组装在库状态
                    resp.setStatusCdName(ResourceConst.STATUSCD.getName(instResp.getStatusCd()));
                    resp.setResultName(ResourceConst.CONSTANT_YES.equals(instResp.getResult()) ? "失败" : "成功");
                }
            }
            resultList.add(resp);
        }

        Page<ResourceInstListPageResp> instListPageRespPage = new Page<ResourceInstListPageResp>();
        BeanUtils.copyProperties(uploadTempPage, instListPageRespPage);
        instListPageRespPage.setRecords(resultList);
        return ResultVO.success(instListPageRespPage);
    }

    private List<MerchantDTO> getMerchantByMerchantIdList(List<String> merchantIdList) {
        //获取厂家信息
        MerchantListReq merchantListReq = new MerchantListReq();
        merchantListReq.setMerchantIdList(merchantIdList);
        ResultVO<List<MerchantDTO>> merchantResult = this.merchantService.listMerchant(merchantListReq);
        List<MerchantDTO> merchantDTOList =new ArrayList<>();
        if(merchantResult.isSuccess()){
            merchantDTOList = merchantResult.getResultData();
        }
        return merchantDTOList;
    }


    @Override
    public ResultVO resetResourceInst(AdminResourceInstDelReq req) {
        log.info("AdminResourceInstServiceImpl.resetResourceInst req={}", JSON.toJSONString(req));
        ResourceInstsGetByIdListAndStoreIdReq queryReq = new ResourceInstsGetByIdListAndStoreIdReq();
        queryReq.setMktResInstIdList(req.getMktResInstIdList());
        queryReq.setMktResStoreId(req.getDestStoreId());
        List<ResourceInstDTO> instListResps = resourceInstManager.selectByIds(queryReq);
        log.info("AdminResourceInstServiceImpl.resetResourceInst resourceInstManager.selectByIds req={}", JSON.toJSONString(req), JSON.toJSONString(instListResps));
        if (CollectionUtils.isEmpty(instListResps)) {
            return ResultVO.error(constant.getNoResInst());
        }
        for (ResourceInstDTO resp : instListResps) {
            if (StringUtils.isNotEmpty(resp.getOrderId())) {
                return ResultVO.error(resp.getMktResInstNbr()+constant.getTradeNbrCanNotReset());
            }
        }

        String mktResStoreId = resouceInstTrackDetailManager.getMerchantStoreId(instListResps.get(0).getMktResInstNbr());
        log.info("AdminResourceInstServiceImpl.resetResourceInst resouceInstTrackDetailManager.getMerchantStoreId req={}", instListResps.get(0).getMktResInstNbr(), mktResStoreId);
        if (StringUtils.isEmpty(mktResStoreId)) {
            return ResultVO.error(constant.getCannotGetStoreMsg());
        }

        req.setMktResStoreId(mktResStoreId);
        ResultVO resultVO = resourceInstService.updateResourceInstByIds(req);
        if (!resultVO.isSuccess()) {
            return resultVO;
        }
        // 更新厂家对应的串码
        AdminResourceInstDelReq delReq = new AdminResourceInstDelReq();
        delReq.setUpdateStaff(req.getUpdateStaff());
        delReq.setMktResInstIdList(req.getMktResInstIdList());
        req.setStatusCd(ResourceConst.STATUSCD.AVAILABLE.getCode());
        delReq.setEventType(ResourceConst.EVENTTYPE.NO_RECORD.getCode());
        delReq.setMktResStoreId(req.getDestStoreId());
        delReq.setDestStoreId(mktResStoreId);
        List<String> checkStatusCd = Lists.newArrayList(ResourceConst.STATUSCD.DELETED.getCode());
        delReq.setCheckStatusCd(checkStatusCd);

        return resourceInstService.updateResourceInstByIds(delReq);
    }
}
