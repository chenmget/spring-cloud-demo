package com.iwhalecloud.retail.partner.service.impl;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.common.ParInvoiceConst;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.partner.dto.req.InvoiceAddReq;
import com.iwhalecloud.retail.partner.dto.req.InvoicePageReq;
import com.iwhalecloud.retail.partner.dto.resp.InvoiceAddResp;
import com.iwhalecloud.retail.partner.dto.resp.InvoicePageResp;
import com.iwhalecloud.retail.partner.entity.Invoice;
import com.iwhalecloud.retail.partner.manager.InvoiceManager;
import com.iwhalecloud.retail.partner.service.InvoiceService;
import com.iwhalecloud.retail.system.dto.CommonRegionDTO;
import com.iwhalecloud.retail.system.service.CommonRegionService;
import com.iwhalecloud.retail.workflow.common.WorkFlowConst;
import com.iwhalecloud.retail.workflow.dto.req.NextRouteAndReceiveTaskReq;
import com.iwhalecloud.retail.workflow.dto.req.ProcessStartReq;
import com.iwhalecloud.retail.workflow.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Component("invoiceService")
@Slf4j
@Service
public class InvoiceServiceImpl implements InvoiceService {
    
    @Autowired
    private InvoiceManager invoiceManager;

    @Reference
    private TaskService taskService;

    @Reference
    private CommonRegionService commonRegionService;
    
    @Override
    public ResultVO<Page<InvoicePageResp>> pageInvoiceByMerchantId(InvoicePageReq req){
        log.info("InvoiceServiceImpl.pageInvoiceByMerchantId req={}", JSON.toJSONString(req));
        if (Objects.isNull(req.getMerchantId())) {
            return ResultVO.error(PartnerConst.MERCHANT_IS_NULL);
        }
        return ResultVO.success(invoiceManager.pageInvoiceByMerchantId(req));
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO<InvoiceAddResp> createParInvoice(InvoiceAddReq req){
        log.info("InvoiceServiceImpl.createParInvoice req={}", JSON.toJSONString(req));
        InvoiceAddResp invoiceAddResp = new InvoiceAddResp();
        Invoice invoice = new Invoice();
        BeanUtils.copyProperties(req, invoice);
        invoiceManager.createParInvoice(invoice);
        String invoiceId = invoice.getInvoiceId();
        invoiceAddResp.setInvoiceId(invoiceId);
        //如果userId为空，则表明是修改操作
        if(StringUtils.isEmpty(req.getUserId())) {
            return ResultVO.success(invoiceAddResp); 
        }
        
        if(StringUtils.isEmpty(req.getInvoiceId())) {
            try {
                ResultVO workFlowResultVO = audInvoice(req.getUserId(), req.getUserName(), req.getLanId(), req.getRegionId(), invoiceId);
            } catch (Exception e){
                e.printStackTrace();
                throw new RuntimeException("启动流程失败");
            } finally {
            }
        }else {

            ResultVO<InvoicePageResp> respResultVOInvoice = queryParInvoiceInfo(req.getInvoiceId());
            InvoicePageResp invoicePageResp = respResultVOInvoice.getResultData();
            if (ParInvoiceConst.VatInvoiceStatus.AUDITED.getCode().equals(invoicePageResp.getVatInvoiceStatus())) {
                try {
                    ResultVO workFlowResultVO = audInvoice(req.getUserId(), req.getUserName(), req.getLanId(), req.getRegionId(), invoiceId);
                } catch (Exception e){
                    ResultVO.error();
                    throw new RuntimeException("启动流程失败");
                } finally {
                }
                
            } else {
                try {
                    ResultVO workFlowResultVO = turnAutInvoice(req.getUserId(), req.getUserName(), invoiceId);
                } catch (Exception e){
                    ResultVO.error();
                    throw new RuntimeException("驳回流程失败");
                } finally {
                }
            }
        }
        return ResultVO.success(invoiceAddResp);
    }

    @Override
    public ResultVO<InvoicePageResp> queryParInvoiceInfo(String invoiceId){
        log.info("InvoiceServiceImpl.pageInvoiceByMerchantId req={}", invoiceId);
        return ResultVO.success(invoiceManager.queryParInvoiceInfo(invoiceId));
    }
    
    public ResultVO audInvoice(String userId,String userName,String lanId,String regionId,String invoiceId){
        log.info("InvoiceServiceImpl.audInvoice    req:{},{},{},{}",userId,userName,lanId,regionId);
        updateInvoiceStatus(invoiceId,ParInvoiceConst.VatInvoiceStatus.AUDITING.getCode());
        ResultVO<CommonRegionDTO> resultVOLan = ResultVO.error();
        if(lanId != null){
            resultVOLan = commonRegionService.getCommonRegionById(lanId);
        }
        ResultVO<CommonRegionDTO> resultVORegion = ResultVO.error();
        if(regionId != null){
            resultVORegion = commonRegionService.getCommonRegionById(regionId);
        }
        String lanName = "";
        String regionName = "";
        if(resultVOLan.isSuccess() && resultVOLan.getResultData() != null){
            lanName = resultVOLan.getResultData().getRegionName();
        }
        if(resultVORegion.isSuccess() && resultVORegion.getResultData() != null){
            regionName = resultVORegion.getResultData().getRegionName();
        }
        log.info("InvoiceServiceImpl.audInvoice.regionName    req:{},{}",lanName,regionName);
        String extends1 = lanName+""+regionName;
        log.info("InvoiceServiceImpl.audInvoice.extends1    req:{}",extends1);
        //2 .调用外部接口 发起审核流程 返回业务ID marketingActivityId
        // 发起流程到零售，由零售商决定是否接受
        // 流程ID先传个1，业务ID你传个唯一的值就好
        ProcessStartReq processStartDTO = new ProcessStartReq();
        processStartDTO.setTitle("专票信息审核流程");
        //创建流程者， 参数需要提供
        processStartDTO.setApplyUserId(userId);
        processStartDTO.setApplyUserName(userName);
        processStartDTO.setProcessId("6");
        processStartDTO.setFormId(invoiceId);
        processStartDTO.setExtends1(extends1);
        //TASK_SUB_TYPE_2020 专票信息流程
        processStartDTO.setTaskSubType(WorkFlowConst.TASK_SUB_TYPE.TASK_SUB_TYPE_2020.getTaskSubType());
        // 指定下一环节处理人
//                HandlerUser user = new HandlerUser();
//                user.setHandlerUserId(merchantDTO.getUserId());
//                user.setHandlerUserName(merchantDTO.getMerchantName());
//                List<HandlerUser> uerList = new ArrayList<HandlerUser>(1);
//                processStartDTO.setNextHandlerUser(uerList);
        ResultVO taskServiceRV = new ResultVO();
        try{
            //开启流程
            taskServiceRV = taskService.startProcess(processStartDTO);
            return ResultVO.success();
        }catch (Exception ex){
            return ResultVO.error();
        }finally {
            log.info("InvoiceServiceImpl.audInvoice req={},resp={}", 
                    JSON.toJSONString(processStartDTO), JSON.toJSONString(taskServiceRV));
        }
    }
    
    
    public ResultVO turnAutInvoice(String userId,String userName,String invoiceId){
        updateInvoiceStatus(invoiceId,ParInvoiceConst.VatInvoiceStatus.AUDITING.getCode());
        
        NextRouteAndReceiveTaskReq nextRouteAndReceiveTaskReq = new NextRouteAndReceiveTaskReq();
        nextRouteAndReceiveTaskReq.setFormId(invoiceId);
        nextRouteAndReceiveTaskReq.setHandlerUserId(userId);
        nextRouteAndReceiveTaskReq.setHandlerMsg("发票驳回审核");
        
        return taskService.nextRouteAndReceiveTask(nextRouteAndReceiveTaskReq);
    }
    
    public int updateInvoiceStatus(String invoiceId,String vatInvoiceStatus){
        return invoiceManager.updateInvoiceStatus(invoiceId,vatInvoiceStatus);
    }
}
