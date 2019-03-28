package com.iwhalecloud.retail.order.dbservice.impl;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.order.consts.OmsCommonConsts;
import com.iwhalecloud.retail.order.dbservice.BuilderOrderExpandService;
import com.iwhalecloud.retail.order.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order.dto.resquest.BuilderOrderRequest;
import com.iwhalecloud.retail.order.model.BuilderOrderDTO;
import com.iwhalecloud.retail.order.model.OrderEntity;
import com.iwhalecloud.retail.order.model.OrderItemEntity;
import com.iwhalecloud.retail.order.reference.CouponManagerReference;
import com.iwhalecloud.retail.order.reference.GoodsManagerReference;
import com.iwhalecloud.retail.order.util.CurrencyUtil;
import com.iwhalecloud.retail.rights.consts.RightsStatusConsts;
import com.iwhalecloud.retail.rights.dto.response.CheckRightsResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class BuilderOrderExpandServiceImpl implements BuilderOrderExpandService {

    @Autowired
    private CouponManagerReference couponManagerReference;

    @Autowired
    private GoodsManagerReference goodsManagerReference;

    @Override
    public CommonResultResp<List<BuilderOrderDTO>> useCouponByEquivalence(List<BuilderOrderDTO> initOrderList, BuilderOrderRequest requestDTO) {
        CommonResultResp resp = new CommonResultResp();
        //克隆一份数据，保证使用优惠券失败之后，数据的完整性
        List<BuilderOrderDTO> orderList = JSON.parseArray(JSON.toJSONString(initOrderList), BuilderOrderDTO.class);
        if (CollectionUtils.isEmpty(orderList)) {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg("未找到订单");
            return resp;
        }

        String memberID = orderList.get(0).getOrder().getMemberId();
        String couponCode = orderList.get(0).getOrder().getCouponCode();
        requestDTO.setCouponOrderId(orderList.get(0).getOrder().getOrderId());
        //不使用优惠券
        if (StringUtils.isEmpty(couponCode)) {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            return resp;
        }


        double orderAllAmount = 0;
        for (BuilderOrderDTO order : orderList) {
            orderAllAmount = CurrencyUtil.add(orderAllAmount, Double.parseDouble(order.getOrder().getGoodsAmount()));
        }
        //计算可优惠金额
        CheckRightsResponseDTO respDTO = couponManagerReference.checkRights(memberID, couponCode, orderAllAmount);
        if (respDTO == null) {
            resp.setResultMsg("无可用优惠券");
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            return resp;
        }

        resp = couponManagerReference.userights( requestDTO, RightsStatusConsts.RIGHTS_STATUS_OCCUPY);
        if (!resp.isSuccess()) {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg("预占失败");
            return resp;
        }

        double per = 0;

        for (int i = 0; i < orderList.size(); i++) {
            OrderEntity orderDTO = orderList.get(i).getOrder();
            //订单减免
            if (i == orderList.size() - 1) {
                per = CurrencyUtil.sub(respDTO.getDiscountPrice(), per);
                orderDTO.setOrderCoupon(per);
            } else {
                //优惠金额占总订单的百分比
                double coupon = CurrencyUtil.div(Double.parseDouble(orderDTO.getGoodsAmount()), orderAllAmount);
                coupon = CurrencyUtil.mul(coupon, respDTO.getDiscountPrice());
                per = CurrencyUtil.add(per, coupon);
                orderDTO.setOrderCoupon(coupon);
            }
            orderDTO.setOrderAmount(String.valueOf(CurrencyUtil.sub(Double.parseDouble(orderDTO.getGoodsAmount()), orderDTO.getOrderCoupon())));

            //商品减免
            List<OrderItemEntity> orderItem = orderList.get(i).getOrderItem();
            for (int j = 0; j < orderItem.size(); j++) {
                double itemPer = 0;
                OrderItemEntity item = orderItem.get(0);
                if (j == orderList.size() - 1) {
                    itemPer = CurrencyUtil.sub(orderDTO.getOrderCoupon(), itemPer);
                    item.setCouponPrice(itemPer);
                } else {
                    //优惠金额占总订单的百分比
                    double goodsAmount=CurrencyUtil.mul(item.getPrice(),item.getNum());
                    double coupon = CurrencyUtil.div(goodsAmount, Double.parseDouble(orderDTO.getGoodsAmount()));
                    coupon = CurrencyUtil.mul(coupon, orderDTO.getOrderCoupon());
                    itemPer = CurrencyUtil.add(itemPer, coupon);
                    item.setCouponPrice(coupon);
                }
            }
        }

        //更改优惠券状态，预占变为已使用
        resp = couponManagerReference.userights( requestDTO, RightsStatusConsts.PROVOBJ_TYPE_USER);
        resp.setResultData(orderList);
        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }

    @Override
    public CommonResultResp RollbackCouponByEquivalence(String orderiD, BuilderOrderRequest requestDTO) {
        return couponManagerReference.userights(requestDTO,RightsStatusConsts.RIGHTS_STATUS_UNUSED);
    }

    @Override
    public CommonResultResp updateGoodsNum(List<String> orders, BuilderOrderRequest requestDTO) {
        CommonResultResp resp=new CommonResultResp();
        if(CollectionUtils.isEmpty(orders)){
            resp.setResultMsg("未查询到订单子项");
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            return resp;
        }
        goodsManagerReference.updateBuyCountById(orders);

        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }


}
