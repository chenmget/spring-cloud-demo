package com.iwhalecloud.retail.order2b.busiservice.impl;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.busiservice.AdvanceSaleOrderBuilderService;
import com.iwhalecloud.retail.order2b.busiservice.MarketingActivitiesService;
import com.iwhalecloud.retail.order2b.consts.OmsCommonConsts;
import com.iwhalecloud.retail.order2b.consts.OrderManagerConsts;
import com.iwhalecloud.retail.order2b.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.CreateOrderRequest;
import com.iwhalecloud.retail.order2b.dto.resquest.order.PreCreateOrderReq;
import com.iwhalecloud.retail.order2b.entity.OrderItem;
import com.iwhalecloud.retail.order2b.model.BuilderOrderModel;
import com.iwhalecloud.retail.order2b.model.CartItemModel;
import com.iwhalecloud.retail.order2b.model.CreateOrderLogModel;
import com.iwhalecloud.retail.order2b.model.SupplierModel;
import com.iwhalecloud.retail.order2b.reference.ActivityManagerReference;
import com.iwhalecloud.retail.order2b.reference.CouponManagerReference;
import com.iwhalecloud.retail.order2b.reference.GoodsManagerReference;
import com.iwhalecloud.retail.order2b.reference.MemberInfoReference;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.rights.consts.RightsStatusConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdvanceSaleOrderBuilderServiceImpl implements AdvanceSaleOrderBuilderService {

    @Autowired
    private ActivityManagerReference activityManagerReference;

    @Autowired
    private GoodsManagerReference goodsManagerReference;

    @Autowired
    private MarketingActivitiesService marketingActivitiesService;


    @Autowired
    private MemberInfoReference memberInfoReference;

    @Autowired
    private CouponManagerReference couponManagerReference;

    @Override
    public CommonResultResp checkRule(PreCreateOrderReq request, List<CartItemModel> list) {
        CommonResultResp resp = new CommonResultResp();
        SupplierModel supplierModel = memberInfoReference.selectSuperById(request.getUserCode());
        /**
         * 用户身份校验（省包/厂商，不能购买）
         */
        if (supplierModel != null) {
            if (PartnerConst.MerchantTypeEnum.SUPPLIER_PROVINCE.getType().equals(supplierModel.getMerchantType())//省包
                    || PartnerConst.MerchantTypeEnum.MANUFACTURER.getType().equals(supplierModel.getMerchantType())//厂商
                    ) {
                resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                resp.setResultMsg("省包供应商和厂商不能购买");
                return resp;
            }

        }

        /**
         * 地包不能购买地包的商品
         */
        if (supplierModel != null) {
            if (PartnerConst.MerchantTypeEnum.SUPPLIER_GROUND.getType().equals(supplierModel.getMerchantType())) { //地包
                SupplierModel merchanModel= memberInfoReference.selectSuperById(request.getMerchantId());
                if(merchanModel!=null && PartnerConst.MerchantTypeEnum.SUPPLIER_GROUND.getType().equals(merchanModel.getMerchantType())){
                    resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                    resp.setResultMsg("地包不能购买地包的商品");
                    return resp;
                }
            }

        }

        /**
         * 购买数量校验（最大，最小）
         */
        ResultVO b = goodsManagerReference.checkBuyCount(list,request);
        if (!b.isSuccess()) {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg(b.getResultMsg());
            return resp;
        }

        /**
         * 是否需要商家确认
         */
        boolean isConfirm = goodsManagerReference.isMerchantConfirm(list);
        if (isConfirm) {
            request.setIsMerchantConfirm(OrderManagerConsts.IS_MERCHANT_CONFIRM);
        }

        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }

    @Override
    public CommonResultResp<CreateOrderLogModel> orderOtherHandler(CreateOrderRequest request, List<BuilderOrderModel> list) {

        CommonResultResp<CreateOrderLogModel> resp = new CommonResultResp<>();
        CreateOrderLogModel logModel = new CreateOrderLogModel();
        logModel.setUserId(request.getUserId());
        logModel.setUserCode(request.getUserCode());
        logModel.setMerchantId(request.getMerchantId());
        logModel.setOrderCat(request.getOrderCat());
        logModel.setCouponInsList(request.getCouponInsList());
        resp.setResultData(logModel);


        List<OrderItem> orderItems = new ArrayList<>();
        for (BuilderOrderModel builderOrderModel : list) {
            orderItems.addAll(builderOrderModel.getOrderItem());
        }

        /**
         *参与活动
         */
        CommonResultResp  couponResp = marketingActivitiesService.marketingActivities(request, list.get(0), logModel);
            if (couponResp.isFailure()) {
                resp.setResultCode(couponResp.getResultCode());
                resp.setResultMsg(couponResp.getResultMsg());
                logModel.setErrorMessage(resp.getResultMsg());
                return resp;
            }


        /**
         * 购买数量+++
         */
        ResultVO resultVO=goodsManagerReference.updateBuyCountById(orderItems,OrderManagerConsts.UPDATE_PUSH);
        logModel.setUpdateGoodsBuyCount(true);
        if (!resultVO.isSuccess()) {
            resp.setResultCode(resultVO.getResultCode());
            resp.setResultMsg("更新购买量失败，"+resultVO.getResultMsg());
            logModel.setErrorMessage(resp.getResultMsg());
            return resp;
        }

        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }

    @Override
    public CommonResultResp rollback(List<BuilderOrderModel> list, CreateOrderLogModel log) {
        CommonResultResp resp = new CommonResultResp<>();

        List<OrderItem> orderItems = new ArrayList<>();
        for (BuilderOrderModel builderOrderModel : list) {
            orderItems.addAll(builderOrderModel.getOrderItem());
        }

        /**
         * 优惠券使用回滚
         */
        if (log.isCoupon()) {
            couponManagerReference.userights(log, RightsStatusConsts.RIGHTS_STATUS_UNUSED,
                    list.get(0).getOrder().getOrderId());
        }

        /**
         * 商品购买量回滚
         */
        if(log.isUpdateGoodsBuyCount()){
            goodsManagerReference.updateBuyCountById(orderItems,OrderManagerConsts.UPDATE_ROLLBACK);
        }

        /**
         * 参与活动回滚
         */
        if(log.isActivityPromotion()){
            activityManagerReference.rollActivityPromotion(list.get(0));
        }

        resp.setResultMsg(log.getErrorMessage());
        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }

}
