package com.iwhalecloud.retail.web.controller.b2b.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.response.CreateOrderResp;
import com.iwhalecloud.retail.order2b.dto.response.PreCheckOrderResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.CalcOrderAmountReq;
import com.iwhalecloud.retail.order2b.dto.resquest.order.CreateOrderRequest;
import com.iwhalecloud.retail.order2b.dto.resquest.order.PreCreateOrderReq;
import com.iwhalecloud.retail.order2b.service.OrderCreateOpenService;
import com.iwhalecloud.retail.rights.common.ResultCodeEnum;
import com.iwhalecloud.retail.rights.dto.SelectedCouponDTO;
import com.iwhalecloud.retail.rights.dto.request.CouponOrderItemDTO;
import com.iwhalecloud.retail.rights.dto.request.OrderCouponListReq;
import com.iwhalecloud.retail.rights.dto.response.OrderCouponListResp;
import com.iwhalecloud.retail.rights.service.CouponInstService;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.controller.b2b.order.dto.response.CalcAmountResp;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/b2b/order/create")
@Slf4j
public class OrderCreateB2BController {

    /**
     * 订单创建
     */

    @Reference
    private OrderCreateOpenService orderCreateOpenService;

    @Reference
    private CouponInstService couponInstService;

    /**
     * 下单预校验
     */
    @RequestMapping(value = "/preCheckOrderItem", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO<PreCheckOrderResp> preCheckOrderItem(@RequestBody PreCreateOrderReq request) {
        request.setMemberId(UserContext.getUserId());
        request.setUserId(UserContext.getUserId());
        request.setUserCode(UserContext.getUser().getRelCode());
        request.setLanId(UserContext.getUser().getLanId());
        return orderCreateOpenService.preCheckOrderItem(request);
    }

    /**
     * 算费
     */
    @RequestMapping(value = "/calcOrderAmount", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO<CalcAmountResp> calcOrderAmount(@RequestBody CalcOrderAmountReq request) {
        request.setMemberId(UserContext.getUserId());
        request.setUserId(UserContext.getUserId());
        request.setUserCode(UserContext.getUser().getRelCode());
        request.setLanId(UserContext.getUser().getLanId());
        //拼装优惠券校验接口参数
        OrderCouponListReq checkCouponReq = new OrderCouponListReq();
        checkCouponReq.setUserId(UserContext.getUserId());
        checkCouponReq.setMerchantId(request.getMerchantId());
        checkCouponReq.setUserMerchantId(request.getUserCode()); // 设置买家的商家ID zhongwenlong
        checkCouponReq.setOrderItemList(JSON.parseArray(JSON.toJSONString(request.getGoodsItem()), CouponOrderItemDTO.class));
        checkCouponReq.setSelectedCouponList(getSelectedCouponDTOs(request.getSelectedCouponList()));
//        调用优惠券校验服务
        ResultVO<OrderCouponListResp> checkCouponResp = couponInstService.orderCouponList(checkCouponReq);
        if (checkCouponResp.getResultData() == null) {
            checkCouponResp.setResultData(new OrderCouponListResp());
        }
        //拼装订单算费接口参数
        PreCreateOrderReq preCreateOrderReq = JSON.parseObject(JSON.toJSONString(request), PreCreateOrderReq.class);
        //调用算费服务
        ResultVO<PreCheckOrderResp> calcAmount = orderCreateOpenService.preCheckOrderItem(preCreateOrderReq);
        if (ResultCodeEnum.ERROR.getCode().equals(calcAmount.getResultCode())) {
            return ResultVO.error(calcAmount.getResultMsg());
        }
        CalcAmountResp calcAmountResp = new CalcAmountResp();
        calcAmountResp.setOrderAmount(calcAmount.getResultData().getOrderAmount());
        calcAmountResp.setPromotionList(calcAmount.getResultData().getPromotionList());
        calcAmountResp.setCouponList(checkCouponResp.getResultData().getCouponList());
        calcAmountResp.setOrderItemList(checkCouponResp.getResultData().getOrderItemList());
        return ResultVO.success(calcAmountResp);
    }


    private List<SelectedCouponDTO> getSelectedCouponDTOs(List<com.iwhalecloud.retail.order2b.dto.model.order.SelectedCouponDTO> selectedCoupon) {
        List<SelectedCouponDTO> selectedCouponDTOS = new ArrayList<>();
        if (CollectionUtils.isEmpty(selectedCoupon)) {
            return selectedCouponDTOS;
        }
        for (com.iwhalecloud.retail.order2b.dto.model.order.SelectedCouponDTO selectedCouponDTO : selectedCoupon) {
            SelectedCouponDTO selectedCouponDTO1 = new SelectedCouponDTO();
            BeanUtils.copyProperties(selectedCouponDTO, selectedCouponDTO1);
            selectedCouponDTOS.add(selectedCouponDTO1);
        }
        return selectedCouponDTOS;
    }

    /**
     * 创建订单
     */
    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO<CreateOrderResp> createOrder(@RequestBody CreateOrderRequest request) {
        request.setMemberId(UserContext.getUserId());
        request.setUserId(UserContext.getUserId());
        request.setUserCode(UserContext.getUser().getRelCode());
        request.setLanId(UserContext.getUser().getLanId());
        return orderCreateOpenService.createOrder(request);
    }

}
