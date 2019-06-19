package com.iwhalecloud.retail.order2b.service.impl.workflow;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.ProcureApplyReq;
import com.iwhalecloud.retail.order2b.manager.PurApplyManager;
import com.iwhalecloud.retail.order2b.service.workflow.PurApplyTypeAuditPassService;
import com.iwhalecloud.retail.workflow.config.InvokeRouteServiceRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @auther lin.wenhui@iwhalecloud.com
 * @date 2019/4/26 15:32
 * @description 采购申请单审核通过服务
 */

@Slf4j
@Service
public class PurApplyTypeAuditPassServiceImpl implements PurApplyTypeAuditPassService {

    @Autowired
    private PurApplyManager purApplyManager;

    @Override
    public ResultVO run(InvokeRouteServiceRequest params) {
        log.info("PurApplyAuditPassServiceImpl.run params={}", JSON.toJSONString(params));
        if (params == null || StringUtils.isEmpty(params.getBusinessId())) {
            return ResultVO.error(ResultCodeEnum.LACK_OF_PARAM);
        }
        ProcureApplyReq req = new ProcureApplyReq();
        req.setApplyId(params.getBusinessId());
        //审核通过
//        req.setStatusCd(PurApplyConsts.PUR_APPLY_STATUS_PASS);
        purApplyManager.updatePurApplyStatusCd(req);
        return ResultVO.success();
    }
}

