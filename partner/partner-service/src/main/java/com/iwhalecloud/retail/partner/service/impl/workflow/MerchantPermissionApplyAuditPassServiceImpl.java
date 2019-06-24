package com.iwhalecloud.retail.partner.service.impl.workflow;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.partner.dto.req.PermissionApplyAuditReq;
import com.iwhalecloud.retail.partner.service.PermissionApplyService;
import com.iwhalecloud.retail.partner.service.workflow.MerchantPermissionApplyAuditPassService;
import com.iwhalecloud.retail.workflow.config.InvokeRouteServiceRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wenlong.zhong
 * @date 2019/4/1
 */
@Slf4j
public class MerchantPermissionApplyAuditPassServiceImpl implements MerchantPermissionApplyAuditPassService {

    @Autowired
    private PermissionApplyService permissionApplyService;

    @Override
    public ResultVO run(InvokeRouteServiceRequest params) {
        log.info("MerchantPermissionApplyAuditPassServiceImpl.run params={}", JSON.toJSONString(params));
        if (params == null) {
            log.info("MerchantPermissionApplyAuditPassServiceImpl.run LACK_OF_PARAM params={}", JSON.toJSONString(params));
            return ResultVO.error(ResultCodeEnum.LACK_OF_PARAM);
        }
        String applyId = params.getBusinessId();
        PermissionApplyAuditReq req = new PermissionApplyAuditReq();
        req.setApplyId(applyId);
        req.setUpdateStaff(params.getHandlerUserId());
        req.setStatusCd(PartnerConst.PermissionApplyStatusEnum.PASS.getCode());
        ResultVO resultVO;
        try {
            resultVO = permissionApplyService.auditPermissionApply(req);
        } catch (Exception e) {
            resultVO = ResultVO.error("审核失败");
        }
        return resultVO;

    }
}
