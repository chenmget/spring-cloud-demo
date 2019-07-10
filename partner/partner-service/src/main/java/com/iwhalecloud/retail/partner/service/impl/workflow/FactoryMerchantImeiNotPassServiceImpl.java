package com.iwhalecloud.retail.partner.service.impl.workflow;


import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.partner.dto.req.PermissionApplyItemUpdateReq;
import com.iwhalecloud.retail.partner.dto.req.PermissionApplyUpdateReq;
import com.iwhalecloud.retail.partner.manager.PermissionApplyItemManager;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.partner.service.PermissionApplyItemService;
import com.iwhalecloud.retail.partner.service.workflow.FactoryMerchantImeiNotPassService;
import com.iwhalecloud.retail.partner.service.PermissionApplyService;
import com.iwhalecloud.retail.workflow.config.InvokeRouteServiceRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.dubbo.config.annotation.Service;

@Slf4j
@Component
@Service
public class FactoryMerchantImeiNotPassServiceImpl implements FactoryMerchantImeiNotPassService {

    @Autowired
    private PermissionApplyService permissionApplyService;

    @Autowired
    private PermissionApplyItemManager permissionApplyItemManager;

    @Override
    public ResultVO run(InvokeRouteServiceRequest params) {
        log.info("FactoryMerchantImeiNotPassServiceImpl.run params={}", JSON.toJSONString(params));
        if (params == null) {
            return ResultVO.error(ResultCodeEnum.LACK_OF_PARAM);
        }
        String applyId = params.getBusinessId();
        //将权限申请表状态改为不通过
        PermissionApplyUpdateReq permissionApplyUpdateReq = new PermissionApplyUpdateReq();
        permissionApplyUpdateReq.setApplyId(applyId);
        permissionApplyUpdateReq.setStatusCd(PartnerConst.PermissionApplyStatusEnum.NOT_PASS.getCode());
        permissionApplyService.updatePermissionApply(permissionApplyUpdateReq);
        //将权限申请明细状态改为失效
        PermissionApplyItemUpdateReq updateReq = new PermissionApplyItemUpdateReq();
        updateReq.setApplyId(applyId);
        updateReq.setStatusCd(PartnerConst.TelecomCommonState.INVALID.getCode());
        permissionApplyItemManager.updateStatus(updateReq);
        return ResultVO.success();
    }
}
