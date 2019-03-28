package com.iwhalecloud.retail.promo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.common.PromoConst;
import com.iwhalecloud.retail.promo.service.ActSupRecordAuditPassService;
import com.iwhalecloud.retail.promo.service.ActSupRecordService;
import com.iwhalecloud.retail.workflow.config.InvokeRouteServiceRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author li.yulong
 * @date 2019.03.09
 */
@Slf4j
@Component
@Service

public class ActSupRecordAuditPassServiceImpl implements ActSupRecordAuditPassService {

    @Autowired
    private ActSupRecordService actSupRecordService;

    @Override
    public ResultVO run(InvokeRouteServiceRequest params) {
        log.info("ActSupRecordAuditPassServiceImpl.run params={}", JSON.toJSONString(params));
        if (params == null) {
            log.info("ActSupRecordAuditPassServiceImpl.run LACK_OF_PARAM params={}", JSON.toJSONString(params));
            return ResultVO.error(ResultCodeEnum.LACK_OF_PARAM);

        }
        String recordId = params.getBusinessId();
        String status =  PromoConst.ActivitySupStatus.ACTIVITY_SUP_STATUS_SUCCESS.getCode();
        return actSupRecordService.updateActSupRecordStatus(recordId, status);

    }
}
