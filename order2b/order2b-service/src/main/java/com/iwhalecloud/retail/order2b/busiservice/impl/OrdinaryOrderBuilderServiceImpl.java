package com.iwhalecloud.retail.order2b.busiservice.impl;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.busiservice.BuilderCartService;
import com.iwhalecloud.retail.order2b.busiservice.MarketingActivitiesService;
import com.iwhalecloud.retail.order2b.busiservice.OrdinaryOrderBuilderService;
import com.iwhalecloud.retail.order2b.consts.OmsCommonConsts;
import com.iwhalecloud.retail.order2b.consts.OrderManagerConsts;
import com.iwhalecloud.retail.order2b.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order2b.dto.resquest.cart.DeleteCartReq;
import com.iwhalecloud.retail.order2b.dto.resquest.order.CreateOrderRequest;
import com.iwhalecloud.retail.order2b.dto.resquest.order.PreCreateOrderReq;
import com.iwhalecloud.retail.order2b.entity.OrderItem;
import com.iwhalecloud.retail.order2b.model.BuilderOrderModel;
import com.iwhalecloud.retail.order2b.model.CartItemModel;
import com.iwhalecloud.retail.order2b.model.CreateOrderLogModel;
import com.iwhalecloud.retail.order2b.model.SupplierModel;
import com.iwhalecloud.retail.order2b.reference.*;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.rights.consts.RightsStatusConsts;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrdinaryOrderBuilderServiceImpl implements OrdinaryOrderBuilderService {

    @Autowired
    private ResNbrManagerReference resNbrManagerReference;

    @Autowired
    private ActivityManagerReference activityManagerReference;

    @Autowired
    private GoodsManagerReference goodsManagerReference;

    @Autowired
    private MarketingActivitiesService marketingActivitiesService;

    @Autowired
    private BuilderCartService builderCartService;

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
        ResultVO b = goodsManagerReference.checkBuyCount(list, request);
        if (!b.isSuccess()) {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg(b.getResultMsg());
            return resp;
        }

        /**
         * 库存校验
         */
        ResultVO<Boolean> checkRes = resNbrManagerReference.getProductQuantityByMerchant(request, list);
        if (!checkRes.isSuccess()) {
            resp.setResultMsg("库存校验失败:" + checkRes.getResultMsg());
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            return resp;
        }


        /**
         * 是否需要商家确认
         */
        boolean isConfirm = goodsManagerReference.isMerchantConfirm(list);
        if (isConfirm) {
            request.setIsMerchantConfirm(OrderManagerConsts.IS_MERCHANT_CONFIRM);
        }

        /**
         * 分货规则校验
         */

        if (supplierModel != null) {
            resp = goodsManagerReference.checkGoodsProdRule(request.getUserCode(),
                    supplierModel.getMerchantType(), list);
            if (resp.isFailure()) {
                return resp;
            }
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
        if (CollectionUtils.isNotEmpty(request.getOrderCatList())) {
            logModel.setOrderCat(request.getOrderCatList().get(0));
        }
        logModel.setCouponInsList(request.getCouponInsList());
        resp.setResultData(logModel);


        List<OrderItem> orderItems = new ArrayList<>();
        for (BuilderOrderModel builderOrderModel : list) {
            orderItems.addAll(builderOrderModel.getOrderItem());
        }
        /**
         * 更新库存
         */
        ResultVO resultVO = resNbrManagerReference.updateStockReq(logModel, orderItems, OrderManagerConsts.UPDATE_PUSH);
        logModel.setUpdateStock(true);
        if (!resultVO.isSuccess()) {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg("更新库存失败，" + resultVO.getResultMsg());
            logModel.setErrorMessage(resp.getResultMsg());
            return resp;
        }

        /**
         * 更新分货规则
         */
        resultVO = goodsManagerReference.updateGoodsRule(logModel, orderItems, OrderManagerConsts.UPDATE_PUSH);
        logModel.setRuleGoods(true);
        if (!resultVO.isSuccess()) {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg("更新分货规则失败，" + resultVO.getResultMsg());
            logModel.setErrorMessage(resp.getResultMsg());
            return resp;
        }

        /**
         * 活动
         */
        CommonResultResp couponResp = marketingActivitiesService.marketingActivities(request, list.get(0), logModel);
        if (couponResp.isFailure()) {
            resp.setResultCode(couponResp.getResultCode());
            resp.setResultMsg(couponResp.getResultMsg());
            logModel.setErrorMessage(resp.getResultMsg());
            return resp;
        }


        /**
         * 购买数量+++
         */
        resultVO = goodsManagerReference.updateBuyCountById(orderItems, OrderManagerConsts.UPDATE_PUSH);
        logModel.setUpdateGoodsBuyCount(true);
        if (!resultVO.isSuccess()) {
            resp.setResultCode(resultVO.getResultCode());
            resp.setResultMsg("更新购买量失败，" + resultVO.getResultMsg());
            logModel.setErrorMessage(resp.getResultMsg());
            return resp;
        }

        /**
         * 清空购物车
         */
        if (OrderManagerConsts.ORDER_SOURCE_TYPE_GWC.equals(request.getSourceType())) {
            DeleteCartReq cartDeleteReq = new DeleteCartReq();
            cartDeleteReq.setUserId(request.getUserId());
            cartDeleteReq.setClean(true);
            builderCartService.deleteCart(cartDeleteReq);
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
         * 分货规则回滚
         */
        if (log.isRuleGoods()) {
            goodsManagerReference.updateGoodsRule(log, orderItems, OrderManagerConsts.UPDATE_ROLLBACK);
        }

        /**
         * 库存回滚
         */
        if (log.isUpdateStock()) {
            resNbrManagerReference.updateStockReq(log, orderItems, OrderManagerConsts.UPDATE_ROLLBACK);
        }

        /**
         * 商品购买量回滚
         */
        if (log.isUpdateGoodsBuyCount()) {
            goodsManagerReference.updateBuyCountById(orderItems, OrderManagerConsts.UPDATE_ROLLBACK);
        }

        /**
         * 参与活动回滚
         */
        if (log.isActivityPromotion()) {
            activityManagerReference.rollActivityPromotion(list.get(0));
        }

        resp.setResultMsg(log.getErrorMessage());
        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }
}
