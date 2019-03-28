package com.iwhalecloud.retail.warehouse.dubbo.workflow;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceRequestUpdateReq;
import com.iwhalecloud.retail.warehouse.manager.ResourceReqDetailManager;
import com.iwhalecloud.retail.warehouse.service.AllocateResourceInstNotPassActionService;
import com.iwhalecloud.retail.warehouse.service.ResourceRequestService;
import com.iwhalecloud.retail.workflow.config.InvokeRouteServiceRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author my
 * @desc 调拨审核不通过处理
 */
@Slf4j
@Service
public class AllocateResourceInstNotPassActionImpl implements AllocateResourceInstNotPassActionService {

    @Autowired
    private ResourceInstService resourceInstService;

    @Reference
    private ResourceRequestService resourceRequestService;

    @Autowired
    private ResourceReqDetailManager resourceReqDetailManager;

    @Reference
    private ResourceRequestService requestService;

    @Override
    public ResultVO run(InvokeRouteServiceRequest params) {
        //对应申请单ID
        String businessId = params.getBusinessId();
        // step1 申请单修改状态
        ResourceRequestUpdateReq reqUpdate = new ResourceRequestUpdateReq();
        reqUpdate.setMktResReqId(businessId);
        reqUpdate.setStatusCd(ResourceConst.MKTRESSTATE.CANCEL.getCode());
        ResultVO<Boolean> updatRequestVO = requestService.updateResourceRequestState(reqUpdate);
        log.info("AllocateResourceInstNotPassActionImpl.run requestService.updateResourceRequestState reqUpdate={}, resp={}", JSON.toJSONString(reqUpdate), JSON.toJSONString(updatRequestVO));

        return ResultVO.success();
    }

}
