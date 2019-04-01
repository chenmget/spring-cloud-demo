package com.iwhalecloud.retail.partner.service.impl.workflow;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.req.PermissionApplyPassReq;
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
        PermissionApplyPassReq req = new PermissionApplyPassReq();
        req.setApplyId(applyId);
        return permissionApplyService.passPermissionApply(req);
    }
}
