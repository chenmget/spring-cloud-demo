package com.iwhalecloud.retail.order2b.service.impl.workflow;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.MerchantDetailDTO;
import com.iwhalecloud.retail.partner.dto.req.MerchantGetReq;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.workflow.config.InvokeRouteServiceRequest;
import com.iwhalecloud.retail.workflow.dto.req.HandlerUser;
import com.iwhalecloud.retail.workflow.extservice.WFServiceExecutor;
import com.iwhalecloud.retail.workflow.extservice.params.ServiceParamContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @auther chen bin784
 * @date 2019/6/19 15:32
 * @description 获取供应商的UserId 作为处理人
 */

@Slf4j
@Service
public class PurApplyGetHandlerUserIdServiceImpl implements WFServiceExecutor {

    @Reference
    private MerchantService merchantService;

    @Override
    public ResultVO<List<HandlerUser>> execute(ServiceParamContext context) {
        log.info("PurApplyGetHandlerUserIdServiceImpl.run params={}", JSON.toJSONString(context));
        if (context == null || StringUtils.isEmpty(context.getParamsValue())) {
            return ResultVO.error(ResultCodeEnum.LACK_OF_PARAM);
        }

        String merchantId = context.getParamsValue();
         List<HandlerUser> handlerUsers = Lists.newArrayList();
        HandlerUser handlerUser = new HandlerUser();
        MerchantGetReq merchantGetReq = new MerchantGetReq();
        merchantGetReq.setMerchantId(merchantId);
        ResultVO<MerchantDetailDTO> merchantInfo =merchantService.getMerchantDetail(merchantGetReq);
        log.info("PurApplyGetHandlerUserIdServiceImpl.run merchantInfo={}", JSON.toJSONString(merchantInfo));
        MerchantDetailDTO m = merchantInfo.getResultData();
        String userId = m.getUserId();
        String supplierName = m.getSupplierName();
        handlerUser.setHandlerUserId(userId);
        handlerUser.setHandlerUserName(supplierName);
        handlerUsers.add(handlerUser);

        //以下实现获取处理人的逻辑
        return ResultVO.success(handlerUsers);

    }
}

