package com.iwhalecloud.retail.order2b.service.impl.workflow;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.entity.PurApply;
import com.iwhalecloud.retail.order2b.manager.PurApplyManager;
import com.iwhalecloud.retail.partner.dto.MerchantDetailDTO;
import com.iwhalecloud.retail.partner.dto.req.MerchantGetReq;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.system.dto.request.UserGetReq;
import com.iwhalecloud.retail.system.service.UserService;
import com.iwhalecloud.retail.workflow.dto.req.HandlerUser;
import com.iwhalecloud.retail.workflow.extservice.WFServiceExecutor;
import com.iwhalecloud.retail.workflow.extservice.params.ServiceParamContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @auther chen bin784
 * @date 2019/6/19 15:32
 * @description 获取供应商的UserId 作为处理人
 */

@Slf4j
@Service(group = "order")
public class PurApplyGetHandlerUserIdServiceImpl implements WFServiceExecutor {

    @Reference
    private UserService userService;
    @Autowired
    private PurApplyManager purApplyManager;
    @Override
    public ResultVO<List<HandlerUser>> execute(ServiceParamContext context) {
        log.info("PurApplyGetHandlerUserIdServiceImpl.execute params={}", JSON.toJSONString(context));
        if (context == null || StringUtils.isEmpty(context.getBusinessId())) {
            return ResultVO.error(ResultCodeEnum.LACK_OF_PARAM);
        }
        PurApply purApply = purApplyManager.getPurApplyByAppId(context.getBusinessId());
        String merchantId = purApply.getMerchantId();
         List<HandlerUser> handlerUsers = Lists.newArrayList();
         UserGetReq userGetReq = new UserGetReq();
         userGetReq.setRelCode(merchantId);
        UserDTO userDTO = userService.getUser(userGetReq);
        System.out.println("PurApplyGetHandlerUserIdServiceImpl.execute userDTO={}"+ JSON.toJSONString(userDTO));

        HandlerUser handlerUser = new HandlerUser();

        handlerUser.setHandlerUserId(userDTO.getUserId());
        handlerUser.setHandlerUserName(userDTO.getUserName());
        handlerUsers.add(handlerUser);

        //以下实现获取处理人的逻辑
        return ResultVO.success(handlerUsers);

    }
}

