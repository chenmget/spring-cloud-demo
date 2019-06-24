package com.iwhalecloud.retail.warehouse.dubbo.workflow;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceReqDetailUpdateReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceRequestUpdateReq;
import com.iwhalecloud.retail.warehouse.entity.ResourceReqItem;
import com.iwhalecloud.retail.warehouse.manager.ResourceReqDetailManager;
import com.iwhalecloud.retail.warehouse.manager.ResourceReqItemManager;
import com.iwhalecloud.retail.warehouse.service.AuditPassActionService;
import com.iwhalecloud.retail.warehouse.service.ResouceStoreService;
import com.iwhalecloud.retail.warehouse.service.ResourceRequestService;
import com.iwhalecloud.retail.workflow.config.InvokeRouteServiceRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author my
 * @desc 调拨审核通过处理
 */
@Slf4j
@Service
public class AuditPassActionImpl implements AuditPassActionService {

    @Autowired
    private ResourceInstService resourceInstService;

    @Autowired
    private ResourceReqDetailManager resourceReqDetailManager;

    @Reference
    private ResouceStoreService resouceStoreService;

    @Reference
    private ResourceRequestService requestService;

    @Autowired
    private ResourceReqItemManager resourceReqItemManager;


    @Override
    public ResultVO run(InvokeRouteServiceRequest params) {
        //对应申请单ID
        String businessId = params.getBusinessId();
        // step1 申请单修改状态
        ResourceRequestUpdateReq reqUpdate = new ResourceRequestUpdateReq();
        reqUpdate.setMktResReqId(businessId);
        reqUpdate.setStatusCd(ResourceConst.MKTRESSTATE.REVIEWED.getCode());
        ResultVO<Boolean> updatRequestVO = requestService.updateResourceRequestState(reqUpdate);
        log.info("AuditPassActionServiceImpl.run requestService.updateResourceRequestState reqUpdate={}, resp={}", JSON.toJSONString(reqUpdate), JSON.toJSONString(updatRequestVO.getResultData()));

        // step4 修改申请单详情状态为通过
        List<ResourceReqItem> itemList = resourceReqItemManager.getListResourceReqItem(businessId);
        log.info("AuditPassActionServiceImpl.run resourceReqItemManager.getListResourceReqItem req={}, resp={}", businessId, JSON.toJSONString(itemList));
        List<String> mktResReqItemId = itemList.stream().map(ResourceReqItem::getMktResReqItemId).collect(Collectors.toList());
        ResourceReqDetailUpdateReq detailUpdateReq = new ResourceReqDetailUpdateReq();
        detailUpdateReq.setMktResReqItemIdList(mktResReqItemId);
        detailUpdateReq.setStatusCd(ResourceConst.DetailStatusCd.STATUS_CD_1005.getCode());
        detailUpdateReq.setUpdateStaff(params.getHandlerUserId());
        Integer detailNum = resourceReqDetailManager.updateResourceReqDetailStatusCd(detailUpdateReq);
        log.info("AuditPassActionServiceImpl.run resourceReqDetailManager.updateResourceReqDetailStatusCd detailUpdateReq={}, resp={}", JSON.toJSONString(detailUpdateReq), detailNum);

        return ResultVO.success();

    }

}
