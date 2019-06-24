package com.iwhalecloud.retail.order2b.busiservice.impl;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.order2b.busiservice.MarketingActivitiesService;
import com.iwhalecloud.retail.order2b.consts.OmsCommonConsts;
import com.iwhalecloud.retail.order2b.consts.OrderManagerConsts;
import com.iwhalecloud.retail.order2b.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.PreCreateOrderReq;
import com.iwhalecloud.retail.order2b.entity.OrderItem;
import com.iwhalecloud.retail.order2b.entity.Promotion;
import com.iwhalecloud.retail.order2b.model.BuilderOrderModel;
import com.iwhalecloud.retail.order2b.model.CreateOrderLogModel;
import com.iwhalecloud.retail.order2b.reference.ActivityManagerReference;
import com.iwhalecloud.retail.order2b.reference.CouponManagerReference;
import com.iwhalecloud.retail.rights.consts.RightsStatusConsts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class MarketingActivitiesServiceImpl implements MarketingActivitiesService {

    @Autowired
    private ActivityManagerReference activityManagerReference;
    @Autowired
    private CouponManagerReference couponManagerReference;


    @Override
    public CommonResultResp orderActivityAmount(PreCreateOrderReq request, BuilderOrderModel model) {
        CommonResultResp resp = new CommonResultResp();

        /**
         *  压测开启
         */
//        if(true){
//            resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
//            return resp;
//        }

        /**
         * 优惠券
         */
        if (!CollectionUtils.isEmpty(request.getCouponInsList())) {
            CommonResultResp couponResp = couponManagerReference.orderCouponAmount(request, request.getCouponInsList(), model);
            if (couponResp.isFailure()) {
                resp.setResultCode(couponResp.getResultCode());
                resp.setResultMsg(couponResp.getResultMsg());
                return resp;
            }
            //组装营销活动数据
            activityManagerReference.builderOrderActivity(request.getCouponInsList(), model);
        }

        /**
         * 预售活动
         */
        Boolean advanceType = CollectionUtils.isEmpty(request.getOrderCatList()) && (request.getOrderCatList().contains(OrderManagerConsts.ORDER_CAT.ORDER_CAT_1.getCode()) || request.getOrderCatList().contains(OrderManagerConsts.ORDER_CAT.ORDER_CAT_3.getCode()));
        if (advanceType) {
            resp = activityManagerReference.selectAdvanceOrderInfo(request,model);
            if(resp.isFailure()){
                return resp;
            }
        }


        /**
         * 前置补贴活动
         */
        activityManagerReference.activityAndPromotions(request,model);


        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }

    @Override
    public CommonResultResp userCoupon(PreCreateOrderReq request, BuilderOrderModel model,
                                       CreateOrderLogModel logModel) {

        CommonResultResp resp = new CommonResultResp();
        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);

        /**
         * 计算优惠券
         */
        CommonResultResp couponResp = couponManagerReference.orderCouponAmount(request, request.getCouponInsList(), model);
        if (couponResp.isFailure()) {
            resp.setResultCode(couponResp.getResultCode());
            resp.setResultMsg(couponResp.getResultMsg());
            return resp;
        }
        /**
         * 组装营销活动数据
         */
        activityManagerReference.builderOrderActivity(request.getCouponInsList(), model);
        /**
         *   参与活动之后，优惠活动绑定订单项
         */
        if (!CollectionUtils.isEmpty(model.getPromotionList())) {
            for (OrderItem orderItem : model.getOrderItem()) {
                for (Promotion promotion : model.getPromotionList()) {

                    if (orderItem.getProductId().equals(promotion.getProductId())) {
                        orderItem.setCouponPrice(Double.parseDouble(promotion.getDiscount()));
                        promotion.setOrderId(orderItem.getOrderId());
                        promotion.setSourceFrom(orderItem.getSourceFrom());
                        promotion.setOrderItemId(orderItem.getItemId());
                    }
                }
            }
        }
        if (resp.isFailure()) {
            return resp;
        }
        logModel.setCoupon(true);

        /**
         * 使用优惠券
         */
        if (logModel.isCoupon()) {
            resp = couponManagerReference.userights(logModel, RightsStatusConsts.RIGHTS_STATUS_USED,
                    model.getOrder().getOrderId());
            if (resp.isFailure()) {
                resp.setResultCode(resp.getResultCode());
                resp.setResultMsg(resp.getResultMsg());
                logModel.setErrorMessage(resp.getResultMsg());
                return resp;
            }
        }
        return resp;
    }


    @Override
    public CommonResultResp marketingActivities(PreCreateOrderReq request, BuilderOrderModel model, CreateOrderLogModel logModel) {
        CommonResultResp resp = new CommonResultResp();

        /**
         *  压测开启
         */
//        if(true){
//            resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
//            return resp;
//        }

        /**
         * 优惠券活动
         */
        if (!CollectionUtils.isEmpty(request.getCouponInsList())) {
            resp = userCoupon(request, model, logModel);
            if (resp.isFailure()) {
                return resp;
            }
        }

        /**
         * 预售活动
         */
        Boolean advanceType = CollectionUtils.isEmpty(request.getOrderCatList()) && (request.getOrderCatList().contains(OrderManagerConsts.ORDER_CAT.ORDER_CAT_1.getCode()) || request.getOrderCatList().contains(OrderManagerConsts.ORDER_CAT.ORDER_CAT_3.getCode()));
        if (advanceType) {
            resp = activityManagerReference.selectAdvanceOrderInfo(request,model);
            if(resp.isFailure()){
                return resp;
            }
        }

        /**
         * 前置补贴活动
         */
        activityManagerReference.activityAndPromotions(request,model);

        /**
         * 活动记录
         */
        log.info("gs_marketingActivity,model{}", JSON.toJSONString(model));
        activityManagerReference.saveActivityPromotion(model,logModel);


        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }


}
