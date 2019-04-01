package com.iwhalecloud.retail.web.controller.b2b.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.response.ReBateOrderInDetailResp;
import com.iwhalecloud.retail.order2b.dto.resquest.promo.ReBateOrderInDetailReq;
import com.iwhalecloud.retail.order2b.service.OrderItemDetailForReBateService;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lhr 2019-04-01 14:36:30
 */
@Slf4j
@RestController
@RequestMapping("/api/b2b/order/accountBalanceDetail")
public class AccountBalanceOrderDetailController {

    @Reference
    private OrderItemDetailForReBateService orderItemDetailForReBateService;

    @ApiOperation(value = "返利活动收入订单明细", notes = "条件分页查询")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/queryAccountBalanceOrderDetailPage")
    @UserLoginToken
    public ResultVO<Page<ReBateOrderInDetailResp>> queryAccountBalanceOrderDetailPage(@RequestBody ReBateOrderInDetailReq req){
        log.info("AccountBalanceDetailController queryAccountBalanceOrderDetailPage req={} ", JSON.toJSON(req));
        if (StringUtils.isEmpty(req.getOrderId())){
            return ResultVO.error("订单Id为空");
        }
        return orderItemDetailForReBateService.queryOrderItemDetailDtoByOrderId(req);
    }
}
