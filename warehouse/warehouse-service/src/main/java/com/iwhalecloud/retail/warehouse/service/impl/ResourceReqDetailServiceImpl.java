package com.iwhalecloud.retail.warehouse.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.req.ProductRebateReq;
import com.iwhalecloud.retail.goods2b.dto.req.ProductResourceInstGetReq;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductResourceResp;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.dto.req.MerchantListReq;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.ResourceReqDetailPageDTO;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceReqDetailPageReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceReqDetailQueryReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceReqDetailPageResp;
import com.iwhalecloud.retail.warehouse.manager.ResourceReqDetailManager;
import com.iwhalecloud.retail.warehouse.manager.ResourceRequestManager;
import com.iwhalecloud.retail.warehouse.runable.RunableTask;
import com.iwhalecloud.retail.warehouse.service.ResouceStoreService;
import com.iwhalecloud.retail.warehouse.service.ResourceReqDetailService;
import com.iwhalecloud.retail.workflow.common.WorkFlowConst;
import com.iwhalecloud.retail.workflow.dto.RouteDTO;
import com.iwhalecloud.retail.workflow.dto.req.RouteReq;
import com.iwhalecloud.retail.workflow.dto.req.TaskPageReq;
import com.iwhalecloud.retail.workflow.dto.resp.TaskPageResp;
import com.iwhalecloud.retail.workflow.service.RouteService;
import com.iwhalecloud.retail.workflow.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
public class ResourceReqDetailServiceImpl implements ResourceReqDetailService {

    @Autowired
    private ResourceReqDetailManager resourceReqDetailManager;

    @Reference
    private ProductService productService;

    @Autowired
    private RunableTask runableTask;

    @Reference
    private MerchantService merchantService;

    @Autowired
    private ResourceRequestManager resourceRequestManager;

    @Reference
    private ResouceStoreService resouceStoreService;

    @Reference
    private TaskService taskService;

    @Reference
    private RouteService routeService;



    @Override
    public ResultVO<Page<ResourceReqDetailPageResp>> resourceRequestPage(ResourceReqDetailPageReq req){
        Page<ResourceReqDetailPageResp> respPage = resourceReqDetailManager.resourceRequestPage(req);
        if (null == respPage || CollectionUtils.isEmpty(respPage.getRecords())) {
            return ResultVO.success(respPage);
        }
        List<ResourceReqDetailPageResp> list = respPage.getRecords();
        ProductResourceInstGetReq productReq = new ProductResourceInstGetReq();
        productReq.setProductId(list.get(0).getMktResId());
        ResultVO<List<ProductResourceResp>> productRespVO = productService.getProductResource(productReq);
        log.info("ResourceReqDetailServiceImpl.resourceRequestPage productService.getProductResource req={}, resp={}", JSON.toJSONString(productReq), JSON.toJSONString(productRespVO));
        if (!productRespVO.isSuccess() || CollectionUtils.isEmpty(productRespVO.getResultData())) {
            return ResultVO.success(respPage);
        }
        // 这个接口供厂商新增串码审核页展示用，新增的串码选择的是同一个产品，他们的产品信息都一样
        ProductResourceResp productResourceResp = productRespVO.getResultData().get(0);
        for (ResourceReqDetailPageResp dto : list) {
            BeanUtils.copyProperties(productResourceResp, dto);
        }
        return ResultVO.success(respPage);
    }


    @Override
    public ResultVO<List<ResourceReqDetailPageResp>> resourceRequestList(ResourceReqDetailPageReq req){
        List<ResourceReqDetailPageResp> list = runableTask.exceutorQueryReqDetail(req);

        if (CollectionUtils.isEmpty(list)) {
            return ResultVO.success(list);
        }
        ProductResourceInstGetReq productReq = new ProductResourceInstGetReq();
        productReq.setProductId(list.get(0).getMktResId());
        ResultVO<List<ProductResourceResp>> productRespVO = productService.getProductResource(productReq);
        log.info("ResourceReqDetailServiceImpl.resourceRequestList productService.getProductResource req={}, resp={}", JSON.toJSONString(productReq), JSON.toJSONString(productRespVO));
        if (!productRespVO.isSuccess() || CollectionUtils.isEmpty(productRespVO.getResultData())) {
            return ResultVO.success(list);
        }
        // 这个接口供厂商新增串码审核页展示用，新增的串码选择的是同一个产品，他们的产品信息都一样
        ProductResourceResp productResourceResp = productRespVO.getResultData().get(0);
        for (ResourceReqDetailPageResp dto : list) {
            BeanUtils.copyProperties(productResourceResp, dto);
        }
        return ResultVO.success(list);
    }

    @Override
    public ResultVO<Page<ResourceReqDetailPageResp>> listResourceRequestDetailPage(ResourceReqDetailQueryReq req) {
        //根据搜索条件组装参数
        packageResourceReqDetailQueryReq(req);
        Page<ResourceReqDetailPageDTO> respPage=new Page();
        if(ResourceConst.DetailStatusCd.STATUS_CD_1004.getCode().equals(req.getStatusCd())||ResourceConst.DetailStatusCd.STATUS_CD_1005.getCode().equals(req.getStatusCd())){
            //查询状态为审核通过或审核不通过，查询用户审核过的明细
            req.setUpdateStaff(req.getUserId());
            respPage = resourceReqDetailManager.listResourceRequestPage(req);
        }else if(ResourceConst.DetailStatusCd.STATUS_CD_1009.getCode().equals(req.getStatusCd())){
            //查询状态为待审核
            //如果审核时间查询条件不为空，直接返回空数据
            if(StringUtils.isNotEmpty(req.getStatusEndDate())||StringUtils.isNotEmpty(req.getStatusStartDate())){
                return ResultVO.success(new Page());
            }
            //获取用户当前待处理的流程表单id，即串码申请单id
            List<String> formIdList=getUserHandleFormId(req.getUserId());
            if(CollectionUtils.isEmpty(formIdList)){
                return ResultVO.success(new Page());
            }
            req.setMktResReqIdList(formIdList);
            //查询当前处理人待审核的串码明细
            respPage=resourceReqDetailManager.listResourceRequestPage(req);
        }else{
            //查询状态为全部
            //获取用户当前待处理的流程表单id，即串码申请单id
            List<String> formIdList=getUserHandleFormId(req.getUserId());
            //获取用户处理过的申请单id
            req.setUpdateStaff(req.getUserId());
            List<ResourceReqDetailPageDTO> resourceReqDetailList=resourceReqDetailManager.listDistinctResourceRequestByUser(req);
            List<String> reqId=resourceReqDetailList.stream().map(ResourceReqDetailPageDTO ::getMktResReqId).collect(Collectors.toList());
            formIdList.addAll(reqId);
            req.setMktResReqIdList(formIdList);
            req.setUpdateStaff(null);
            respPage=resourceReqDetailManager.listResourceRequestPage(req);
        }
        //组装返回值
        Page<ResourceReqDetailPageResp> result=fillResourceRequestDetailPage(respPage);
        return ResultVO.success(result);
    }

    @Override
    public ResultVO<Page<ResourceReqDetailPageResp>> listMerchantResourceRequestDetailPage(ResourceReqDetailQueryReq req) {
        //根据搜索条件组装参数
        packageResourceReqDetailQueryReq(req);
        Page<ResourceReqDetailPageDTO> respPage= resourceReqDetailManager.listResourceRequestPage(req);
        Page<ResourceReqDetailPageResp> result=fillResourceRequestDetailPage(respPage);
        return ResultVO.success(result);
    }

    /**
     * 组装串码明细相关产品，商家信息
     * @param respPage
     * @return
     */
    private Page<ResourceReqDetailPageResp> fillResourceRequestDetailPage (Page<ResourceReqDetailPageDTO> respPage){
        List<ResourceReqDetailPageDTO> list = respPage.getRecords();
        Page<ResourceReqDetailPageResp> result=new Page<ResourceReqDetailPageResp>();
        BeanUtils.copyProperties(respPage, result);
        if (CollectionUtils.isEmpty(list)) {
            return result;
        }
        //根据mktResId分组，减少调用服务次数，组装品牌名，产品名，商家名
        Map<String, List<ResourceReqDetailPageDTO>> map = list.stream().collect(Collectors.groupingBy(t -> t.getMktResId()));
        //商家id与商家名map，如果厂商id相同，直接从map中拿
        Map<String,String> merchantMap=new HashMap<>();
        for (Map.Entry<String, List<ResourceReqDetailPageDTO>> entry : map.entrySet()) {
            String mktResId = entry.getKey();
            ProductResourceInstGetReq queryReq = new ProductResourceInstGetReq();
            queryReq.setProductId(mktResId);
            ResultVO<List<ProductResourceResp>> prodectResultVO = productService.getProductResource(queryReq);
            log.info("ResourceReqDetailServiceImpl.fillResourceRequestDetailPage.getProductResource req={},resp={}", JSON.toJSONString(queryReq), JSON.toJSONString(prodectResultVO));
            if (!prodectResultVO.isSuccess() || CollectionUtils.isEmpty(prodectResultVO.getResultData())) {
                continue;
            }
            List<ProductResourceResp> prodList = prodectResultVO.getResultData();
            ProductResourceResp prodResp = prodList.get(0);
            List<ResourceReqDetailPageDTO> instList =entry.getValue();
            for (ResourceReqDetailPageDTO resp : instList) {
                //填充审核状态
                resp.setStatusCdName(ResourceConst.DetailStatusCd.getNameByCode(resp.getStatusCd()));
                // 添加产品信息
                BeanUtils.copyProperties(prodResp, resp);
                // 添加厂商信息
                String merchantId=resp.getMerchantId();
                if (StringUtils.isNotBlank(merchantId)) {
                    if(StringUtils.isNotBlank(merchantMap.get(merchantId))){
                        resp.setMerchantName(merchantMap.get(merchantId));
                        continue;
                    }
                    //通过厂家id获取厂家信息
                    ResultVO<MerchantDTO> merchantResultVO = merchantService.getMerchantById(merchantId);
                    log.info("ResourceReqDetailServiceImpl.fillResourceRequestDetailPage.getMerchantById req={},resp={}", JSON.toJSONString(queryReq), JSON.toJSONString(merchantResultVO));
                    if (merchantResultVO.isSuccess() && null != merchantResultVO.getResultData()) {
                        MerchantDTO merchantDTO = merchantResultVO.getResultData();
                        resp.setMerchantName(merchantDTO.getMerchantName());
                        merchantMap.put(merchantId, merchantDTO.getMerchantName());
                    }
                }

            }
        }
        List<ResourceReqDetailPageResp> resultList = getResourceReqDetailList(list);
        result.setRecords(resultList);
        return result;
    }

    //时间戳转时间字符串
    private String getDateStr(Date date) {
        if(date==null){
            return null;
        }
        //填充日期
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        try {
            String StringDate = format.format(date);
            return StringDate;
        }catch (Exception e){
            log.error("时间解析错误",e);
        }
        return null;
    }

    /**
     * 获取用户当前待处理的流程表单id
     * @param
     * @return
     */
    @Override
    public List<String> getUserHandleFormId(String userId) {
        log.info("ResourceReqDetailServiceImpl.getUserHandleFormId,userId={}",userId);
        //申请单id集合
        List<String> formIdList=new ArrayList<>();
        //查询串码审核工作流的待办流程，找出对应申请单id
        TaskPageReq taskPageReq = new TaskPageReq();
        taskPageReq.setHandlerUserId(userId);
        taskPageReq.setPageSize(100);  //最多查询100条记录
        StringBuffer taskSubType = new StringBuffer();
        taskSubType.append(WorkFlowConst.TASK_SUB_TYPE.TASK_SUB_TYPE_3010.getTaskSubType()+",")
                .append(WorkFlowConst.TASK_SUB_TYPE.TASK_SUB_TYPE_1020.getTaskSubType());
        taskPageReq.setTaskSubTypeList(Arrays.asList(taskSubType.toString().split(",")));
        ResultVO<Page<TaskPageResp>> pageResultVO = taskService.queryTaskPage(taskPageReq);
        log.info("ResourceReqDetailServiceImpl.getUserHandleFormId.queryTaskPage req={} resp={}",JSON.toJSONString(taskPageReq),JSON.toJSONString(pageResultVO));
        if(!pageResultVO.isSuccess()){
            return formIdList;
        }
        if(pageResultVO.getResultData()!=null){
            Page<TaskPageResp> taskPageResp=pageResultVO.getResultData();
            List<TaskPageResp> taskList=taskPageResp.getRecords();
            //判断这些流程是否处于最后一个审批节点
            for(TaskPageResp task : taskList){
                RouteReq routeReq=new RouteReq();
                routeReq.setProcessId(task.getProcessId());
                routeReq.setCurNodeId(task.getCurNodeId());
                ResultVO<List<RouteDTO>> routeResult=routeService.listRoute(routeReq);
                log.info("ResourceReqDetailServiceImpl.getUserHandleFormId.routeResult req={} resp={}",JSON.toJSONString(routeReq),JSON.toJSONString(routeResult));
                if(!routeResult.isSuccess()){
                   continue;
                }
                //判断下一节点是否有结束节点，如果有，则说明当前节点是最后一个审批节点
                List<RouteDTO>  routeList=routeResult.getResultData();
                for(RouteDTO route : routeList){
                    if(WorkFlowConst.WF_NODE.NODE_END.getId().equals(route.getNextNodeId())){
                        formIdList.add(task.getFormId());
                    }
                }
            }
        }
        return formIdList;
    }


    private List<ResourceReqDetailPageResp> getResourceReqDetailList(List<ResourceReqDetailPageDTO> list) {
        List<ResourceReqDetailPageResp> resultList=new ArrayList<>();
        for(ResourceReqDetailPageDTO dto: list){
            ResourceReqDetailPageResp resp=new ResourceReqDetailPageResp();
            BeanUtils.copyProperties(dto,resp);
            resp.setCreateDateStr(getDateStr(resp.getCreateDate()));
            String statusCd=resp.getStatusCd();
            //非审核不通过状态，状态说明置空
            if(!ResourceConst.DetailStatusCd.STATUS_CD_1004.getCode().equals(statusCd)){
                resp.setRemark(null);
            }
            //待审核状态，审核时间置空
            if(ResourceConst.DetailStatusCd.STATUS_CD_1009.getCode().equals(statusCd)){
                resp.setStatusDate(null);
            }
            resp.setStatusDateStr(getDateStr(resp.getStatusDate()));
            resultList.add(resp);
        }
        return resultList;
    }

    /**
     * 组装请求参数
     * @param req
     */
    private void packageResourceReqDetailQueryReq(ResourceReqDetailQueryReq req) {
        //如果商家名不为空，取回商家id
        if(req.getMerchantName()!=null){
            MerchantListReq merchantGetReq=new MerchantListReq();
            merchantGetReq.setMerchantName(req.getMerchantName());
            merchantGetReq.setMerchantType(PartnerConst.MerchantTypeEnum.MANUFACTURER.getType());//默认厂商
            ResultVO<List<MerchantDTO>> merchantResult = merchantService.listMerchant(merchantGetReq);
            log.info("ResourceReqDetailServiceImpl.packageResourceReqDetailQueryReq.listMerchant req={} resp={}",JSON.toJSONString(merchantGetReq),JSON.toJSONString(merchantResult));
            if(merchantResult.isSuccess()&&merchantResult.getResultData()!=null){
                List<MerchantDTO> merchantList=merchantResult.getResultData();
                List<String> merchantIds=merchantList.stream().distinct().map(MerchantDTO::getMerchantId).collect(Collectors.toList());
                req.setMerchantId(merchantIds.size() > 0 ? merchantIds : Lists.newArrayList("0"));
            }
        }
        //如果产品名称或品牌名称不为空，取回产品id
        if(req.getProductName()!=null||req.getBrandId()!=null||req.getTypeId()!=null){
            ProductRebateReq productRebateReq=new ProductRebateReq();
            productRebateReq.setProductName(req.getProductName());
            productRebateReq.setBrandId(req.getBrandId());
            productRebateReq.setTypeId(req.getTypeId());
            ResultVO<List<ProductResp>> prodctResult=productService.getProductForRebate(productRebateReq);
            log.info("ResourceReqDetailServiceImpl.packageResourceReqDetailQueryReq.getProductForRebate req={} resp={}",JSON.toJSONString(productRebateReq),JSON.toJSONString(prodctResult));
            if(prodctResult.isSuccess()&&prodctResult.getResultData()!=null){
                List<ProductResp> productList=prodctResult.getResultData();
                List<String> productIds=productList.stream().distinct().map(ProductResp::getProductId).collect(Collectors.toList());
                req.setProductId(productIds.size() > 0 ? productIds : Lists.newArrayList("0"));
            }
        }
    }
}