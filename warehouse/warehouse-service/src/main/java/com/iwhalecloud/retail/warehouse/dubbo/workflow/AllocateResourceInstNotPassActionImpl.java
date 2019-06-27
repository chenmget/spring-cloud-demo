package com.iwhalecloud.retail.warehouse.dubbo.workflow;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.warehouse.busiservice.ResouceInstTrackService;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.constant.Constant;
import com.iwhalecloud.retail.warehouse.dto.ResourceReqDetailDTO;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.manager.ResourceReqDetailManager;
import com.iwhalecloud.retail.warehouse.manager.ResourceReqItemManager;
import com.iwhalecloud.retail.warehouse.service.AllocateResourceInstNotPassActionService;
import com.iwhalecloud.retail.warehouse.service.ResouceStoreService;
import com.iwhalecloud.retail.warehouse.service.ResourceRequestService;
import com.iwhalecloud.retail.workflow.config.InvokeRouteServiceRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author my
 * @desc 调拨审核不通过处理
 */
@Slf4j
@Service
public class AllocateResourceInstNotPassActionImpl implements AllocateResourceInstNotPassActionService {

    @Reference
    private ResourceRequestService requestService;

    @Autowired
    private ResourceReqDetailManager resourceReqDetailManager;

    @Autowired
    private ResourceReqItemManager resourceReqItemManager;

    @Reference
    private ResouceStoreService resouceStoreService;

    @Autowired
    private ResourceInstService resourceInstService;

    @Autowired
    private ResouceInstTrackService resouceInstTrackService;

    @Autowired
    private Constant constant;

    @Override
    public ResultVO run(InvokeRouteServiceRequest params) {
        // 对应申请单ID
        String businessId = params.getBusinessId();
        // step1 申请单修改状态
        ResourceRequestUpdateReq updateReq = new ResourceRequestUpdateReq();
        updateReq.setMktResReqId(businessId);
        updateReq.setStatusCd(ResourceConst.MKTRESSTATE.CANCEL.getCode());
        ResultVO<Boolean> updatRequestVO = requestService.updateResourceRequestState(updateReq);
        log.info("AllocateResourceInstNotPassActionImpl.run requestService.updateResourceRequestState delReq={}, resp={}", JSON.toJSONString(updateReq), JSON.toJSONString(updatRequestVO));
        //step2 根据申请单表保存的源仓库和申请单明细找到对应的串码
        ResourceReqDetailQueryReq queryReq = new ResourceReqDetailQueryReq();
        queryReq.setMktResReqId(businessId);
        List<ResourceReqDetailDTO> list = resourceReqDetailManager.listDetail(queryReq);
        log.info("AllocateResourceInstNotPassActionImpl.run resourceReqDetailManager.listDetail req={}, resp={}", JSON.toJSONString(queryReq), JSON.toJSONString(list));
        if (CollectionUtils.isEmpty(list)) {
            return ResultVO.error(constant.getCannotGetRequestItemMsg());
        }
        ResourceReqDetailDTO detailDTO = list.get(0);
        ResourceReqDetailUpdateReq detailUpdateReq = new ResourceReqDetailUpdateReq();
        detailUpdateReq.setMktResReqItemIdList(Lists.newArrayList(detailDTO.getMktResReqItemId()));
        detailUpdateReq.setStatusCd(ResourceConst.DetailStatusCd.STATUS_CD_1004.getCode());
        detailUpdateReq.setUpdateStaff(params.getHandlerUserId());
        Integer detailNum = resourceReqDetailManager.updateResourceReqDetailStatusCd(detailUpdateReq);
        log.info("AllocateResourceInstNotPassActionImpl.run resourceReqDetailManager.updateResourceReqDetailStatusCd detailUpdateReq={}, resp={}", JSON.toJSONString(detailUpdateReq), detailNum);

        ResultVO<MerchantDTO> merchantResultVO = resouceStoreService.getMerchantByStore(detailDTO.getMktResStoreId());
        log.info("AllocateResourceInstNotPassActionImpl.run resouceStoreService.getMerchantByStore req={}, resp={}", JSON.toJSONString(queryReq), JSON.toJSONString(list));
        if (!merchantResultVO.isSuccess() || null == merchantResultVO.getResultData()) {
            return ResultVO.error(constant.getCannotGetMerchantMsg());
        }
        List<String> mktResInstIds = list.stream().map(ResourceReqDetailDTO::getMktResInstId).collect(Collectors.toList());
        // step3 修改源仓库串码为可用
        AdminResourceInstDelReq delReq = new AdminResourceInstDelReq();
        List<String> checkStatusCd = new ArrayList<String>(7);
        checkStatusCd.add(ResourceConst.STATUSCD.AUDITING.getCode());
        checkStatusCd.add(ResourceConst.STATUSCD.AVAILABLE.getCode());
        checkStatusCd.add(ResourceConst.STATUSCD.ALLOCATIONED.getCode());
        checkStatusCd.add(ResourceConst.STATUSCD.RESTORAGEING.getCode());
        checkStatusCd.add(ResourceConst.STATUSCD.RESTORAGED.getCode());
        checkStatusCd.add(ResourceConst.STATUSCD.SALED.getCode());
        checkStatusCd.add(ResourceConst.STATUSCD.DELETED.getCode());
        delReq.setCheckStatusCd(checkStatusCd);
        delReq.setStatusCd(ResourceConst.STATUSCD.AVAILABLE.getCode());
        delReq.setMktResInstIdList(mktResInstIds);
        delReq.setEventType(ResourceConst.EVENTTYPE.ALLOT.getCode());
        delReq.setObjType(ResourceConst.EVENT_OBJTYPE.PUT_STORAGE.getCode());
        delReq.setObjId(businessId);
        delReq.setMerchantId(merchantResultVO.getResultData().getMerchantId());
        delReq.setMktResStoreId(detailDTO.getDestStoreId());
        delReq.setDestStoreId(detailDTO.getMktResStoreId());
        delReq.setEventStatusCd(ResourceConst.EVENTSTATE.CANCEL.getCode());
        ResultVO resp = resourceInstService.updateResourceInstByIds(delReq);
        log.info("AllocateResourceInstNotPassActionImpl.run resourceInstService.updateResourceInstByIds req={}, resp={}", JSON.toJSONString(delReq), JSON.toJSONString(resp));
        ConfirmReciveNbrReq req = new ConfirmReciveNbrReq();
        req.setResReqId(businessId);
        resouceInstTrackService.allocateResourceIntsWarehousingCancelForRetail(req, resp);
        return ResultVO.success();
    }

}
