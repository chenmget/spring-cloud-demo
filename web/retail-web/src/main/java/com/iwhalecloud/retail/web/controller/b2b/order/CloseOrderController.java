package com.iwhalecloud.retail.web.controller.b2b.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.resquest.order.CloseOrderReq;
import com.iwhalecloud.retail.order2b.dto.resquest.order.OrderApplyGetReq;
import com.iwhalecloud.retail.order2b.service.CloseOrderOpenService;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * 关闭订单入口
 *
 * @author xu.qinyuan@ztesoft.com
 * @date 2019年03月26日
 */
@RestController
@RequestMapping("/api/b2b/order/close")
@CrossOrigin
@Slf4j
public class CloseOrderController {
    @Reference
    private CloseOrderOpenService closeOrderOpenService;

    /**
     * 卖家申请关闭订单
     * @param request
     * @return
     */
    @RequestMapping(value = "/applyClose", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO applyClose(@RequestBody CloseOrderReq request) {
        UserDTO user = UserContext.getUser();
        if (Objects.isNull(user)) {
            return ResultVO.error("您尚未登陆，请登录");
        }
        request.setUserId(user.getUserId());
        return closeOrderOpenService.applyCloseOrder(request);
    }

    /**
     * 查询关闭订单申请的信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryCloseOrderApply", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO queryCloseOrderApply(@RequestBody OrderApplyGetReq request) {
        return closeOrderOpenService.queryCloseOrderApply(request);
    }

    /**
     * 买家同意关闭订单
     * @param request
     * @return
     */
    @RequestMapping(value = "/agreeClose", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO agreeClose(@RequestBody CloseOrderReq request) {
        UserDTO user = UserContext.getUser();
        if (Objects.isNull(user)) {
            return ResultVO.error("您尚未登陆，请登录");
        }
        request.setUserId(user.getUserId());
        return closeOrderOpenService.agreeClose(request);
    }

    /**
     * 买家不同意关闭订单
     * @param request
     * @return
     */
    @RequestMapping(value = "/disagreeClose", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO disagreeClose(@RequestBody CloseOrderReq request) {
        UserDTO user = UserContext.getUser();
        if (Objects.isNull(user)) {
            return ResultVO.error("您尚未登陆，请登录");
        }
        request.setUserId(user.getUserId());
        return closeOrderOpenService.disagreeClose(request);
    }
}
