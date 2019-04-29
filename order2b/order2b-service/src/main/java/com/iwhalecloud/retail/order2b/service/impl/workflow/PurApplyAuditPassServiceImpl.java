package com.iwhalecloud.retail.order2b.service.impl.workflow;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.consts.PurApplyConsts;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.PurApplyReq;
import com.iwhalecloud.retail.order2b.service.PurchaseApplyService;
import com.iwhalecloud.retail.order2b.service.workflow.PurApplyAuditNotPassService;
import com.iwhalecloud.retail.workflow.config.InvokeRouteServiceRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @auther lin.wenhui@iwhalecloud.com
 * @date 2019/4/26 15:32
 * @description 采购申请单审核通过服务
 */

@Slf4j
@Service
public class PurApplyAuditPassServiceImpl implements PurApplyAuditNotPassService {

    @Reference
    private PurchaseApplyService purchaseApplyService;

    @Override
    public ResultVO run(InvokeRouteServiceRequest params) {
        log.info("PurApplyAuditPassServiceImpl.run params={}", JSON.toJSONString(params));
        if (params == null || StringUtils.isEmpty(params.getBusinessId())) {
            return ResultVO.error(ResultCodeEnum.LACK_OF_PARAM);
        }
        PurApplyReq req = new PurApplyReq();
        req.setApplyId(params.getBusinessId());
        //审核通过
        req.setStatusCd(PurApplyConsts.PUR_APPLY_STATUS_PASS);
        return purchaseApplyService.updatePurApplyStatus(req);
    }
}

