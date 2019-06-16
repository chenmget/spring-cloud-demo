package com.iwhalecloud.retail.warehouse.dubbo.workflow;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceReqDetailUpdateReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceRequestUpdateReq;
import com.iwhalecloud.retail.warehouse.entity.ResourceReqItem;
import com.iwhalecloud.retail.warehouse.manager.ResourceReqDetailManager;
import com.iwhalecloud.retail.warehouse.manager.ResourceReqItemManager;
import com.iwhalecloud.retail.warehouse.service.AuditNotPassActionService;
import com.iwhalecloud.retail.warehouse.service.ResourceRequestService;
import com.iwhalecloud.retail.workflow.config.InvokeRouteServiceRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author my
 * @desc 绿色通道审核不通过处理
 */
@Slf4j
@Service
public class AuditNotPassActionServiceImpl implements AuditNotPassActionService {

    @Reference
    private ResourceRequestService requestService;

    @Autowired
    private ResourceReqDetailManager resourceReqDetailManager;

    @Autowired
    private ResourceReqItemManager resourceReqItemManager;

    @Override
    public ResultVO run(InvokeRouteServiceRequest params) {
        // 对应申请单ID
        String businessId = params.getBusinessId();
        // 申请单修改状态
        ResourceRequestUpdateReq updateReq = new ResourceRequestUpdateReq();
        updateReq.setMktResReqId(businessId);
        updateReq.setStatusCd(ResourceConst.MKTRESSTATE.CANCEL.getCode());
        ResultVO<Boolean> updatRequestVO = requestService.updateResourceRequestState(updateReq);
        log.info("AuditNotPassActionServiceImpl.run requestService.updateResourceRequestState updateReq={}, resp={}", JSON.toJSONString(updateReq), JSON.toJSONString(updatRequestVO));
        // step4 修改申请单详情状态为通过
        List<ResourceReqItem> itemList = resourceReqItemManager.getListResourceReqItem(businessId);
        log.info("AuditNotPassActionServiceImpl.run resourceReqItemManager.getListResourceReqItem req={}, resp={}", businessId, JSON.toJSONString(itemList));
        List<String> mktResReqItemId = itemList.stream().map(ResourceReqItem::getMktResReqItemId).collect(Collectors.toList());
        ResourceReqDetailUpdateReq detailUpdateReq = new ResourceReqDetailUpdateReq();
        detailUpdateReq.setMktResReqItemIdList(mktResReqItemId);
        detailUpdateReq.setStatusCd(ResourceConst.DetailStatusCd.STATUS_CD_1004.getCode());
        Integer detailNum = resourceReqDetailManager.updateResourceReqDetailStatusCd(detailUpdateReq);
        log.info("AuditNotPassActionServiceImpl.run resourceReqDetailManager.updateResourceReqDetailStatusCd detailUpdateReq={}, resp={}", JSON.toJSONString(detailUpdateReq), detailNum);
        // 申请单ID->明细
        return ResultVO.success();
    }

}
