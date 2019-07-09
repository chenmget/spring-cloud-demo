package com.iwhalecloud.retail.order2b.reference;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.config.DBTableSequence;
import com.iwhalecloud.retail.order2b.config.WhaleCloudKeyGenerator;
import com.iwhalecloud.retail.order2b.consts.OmsCommonConsts;
import com.iwhalecloud.retail.order2b.consts.OrderManagerConsts;
import com.iwhalecloud.retail.order2b.consts.order.TypeStatus;
import com.iwhalecloud.retail.order2b.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order2b.dto.model.order.CouponInsDTO;
import com.iwhalecloud.retail.order2b.dto.resquest.order.PreCreateOrderReq;
import com.iwhalecloud.retail.order2b.entity.AdvanceOrder;
import com.iwhalecloud.retail.order2b.entity.Order;
import com.iwhalecloud.retail.order2b.entity.OrderItem;
import com.iwhalecloud.retail.order2b.entity.Promotion;
import com.iwhalecloud.retail.order2b.manager.PromotionManager;
import com.iwhalecloud.retail.order2b.model.BuilderOrderModel;
import com.iwhalecloud.retail.order2b.model.CreateOrderLogModel;
import com.iwhalecloud.retail.order2b.model.PromotionModel;
import com.iwhalecloud.retail.order2b.util.CurrencyUtil;
import com.iwhalecloud.retail.promo.dto.PromotionWithMarketingActivityDTO;
import com.iwhalecloud.retail.promo.dto.req.ActHistoryPurChaseAddReq;
import com.iwhalecloud.retail.promo.dto.req.ActHistoryPurChaseUpReq;
import com.iwhalecloud.retail.promo.dto.req.AdvanceActivityProductInfoReq;
import com.iwhalecloud.retail.promo.dto.req.MarketingActivityQueryByGoodsReq;
import com.iwhalecloud.retail.promo.dto.resp.AdvanceActivityProductInfoResp;
import com.iwhalecloud.retail.promo.dto.resp.MarketingActivityDetailResp;
import com.iwhalecloud.retail.promo.dto.resp.MarketingActivityInfoResp;
import com.iwhalecloud.retail.promo.dto.resp.MarketingAndPromotionResp;
import com.iwhalecloud.retail.promo.service.HistoryPurchaseService;
import com.iwhalecloud.retail.promo.service.MarketingActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ActivityManagerReference {

    @Reference
    private MarketingActivityService marketingActivityService;

    @Reference
    private HistoryPurchaseService historyPurchaseService;

    @Autowired
    private PromotionManager promotionManager;


    @Autowired
    private WhaleCloudKeyGenerator whaleCloudKeyGenerator;

    public void builderOrderActivity(List<CouponInsDTO> couponList, BuilderOrderModel model) {

        List<String> resIds = new ArrayList<>(couponList.size());
        for (CouponInsDTO couponInsDTO : couponList) {
            resIds.add(couponInsDTO.getCouponMKId());
        }
        ResultVO<List<PromotionWithMarketingActivityDTO>> resultVO = marketingActivityService.getMarketingActivitiesByMktResIds(resIds);

        if (CollectionUtils.isEmpty(resultVO.getResultData())) {
            return;
        }
        /**
         * 营销活动信息
         */
        for (PromotionWithMarketingActivityDTO mac : resultVO.getResultData()) {
            for (Promotion promotion : model.getPromotionList()) {
                if (promotion.getPromotionId().equals(mac.getMktResId())) {
                    promotion.setMktActId(mac.getMarketingActivityId());
                    promotion.setPromotionType(String.valueOf(mac.getPromotionType()));
                    promotion.setMktActName(mac.getName());
                    promotion.setMktActType(mac.getActivityType());
                }
            }
        }

    }

    /**
     * 预售活动
     */
    public CommonResultResp selectAdvanceOrderInfo(PreCreateOrderReq req, BuilderOrderModel builderOrderModel) {
        CommonResultResp resp = new CommonResultResp();
        OrderItem orderItem = builderOrderModel.getOrderItem().get(0);
        AdvanceActivityProductInfoReq advanceActivityProductInfoReq = new AdvanceActivityProductInfoReq();
        advanceActivityProductInfoReq.setSourceFrom(req.getSourceFrom());
        advanceActivityProductInfoReq.setMarketingActivityId(req.getActivityId());
        advanceActivityProductInfoReq.setProductId(orderItem.getProductId());

        ResultVO<AdvanceActivityProductInfoResp> respResultVO =
                marketingActivityService.getAdvanceActivityProductInfo(advanceActivityProductInfoReq);
        log.info("gs_10010_selectAdvanceOrderInfo,req{},resp{}", JSON.toJSONString(advanceActivityProductInfoReq),
                JSON.toJSONString(respResultVO));
        AdvanceActivityProductInfoResp infoResp = respResultVO.getResultData();
        if (infoResp == null) {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg("未查询到活动，activityId=" + req.getActivityId());
            return resp;
        }
        AdvanceOrder advanceOrder = new AdvanceOrder();
        advanceOrder.setActivityId(infoResp.getId());
//                "1. 支付定金" +
//                "2. 全款支付"
        double goodsSum = CurrencyUtil.mul(orderItem.getPrice(), orderItem.getNum()); //商品总价
        if (OrderManagerConsts.ADVANCE_ORDER_PAY_1.equals(infoResp.getPayType())) {
            builderOrderModel.setAdvancePayType(OrderManagerConsts.ADVANCE_ORDER_PAY_1);
            advanceOrder.setAdvanceAmount(CurrencyUtil.mul(Double.parseDouble(String.valueOf(infoResp.getPrePrice())), orderItem.getNum()));
            advanceOrder.setRestAmount(CurrencyUtil.sub(goodsSum, advanceOrder.getAdvanceAmount()));
        } else {
            builderOrderModel.setAdvancePayType(OrderManagerConsts.ADVANCE_ORDER_PAY_2);
            advanceOrder.setAdvanceAmount(goodsSum);
            advanceOrder.setRestAmount(0.0);
        }
        advanceOrder.setAdvancePayBegin(String.valueOf(new Timestamp(infoResp.getPreStartTime().getTime())));
        advanceOrder.setAdvancePayEnd(String.valueOf(new Timestamp(infoResp.getPreEndTime().getTime())));
        advanceOrder.setAdvancePayStatus(TypeStatus.TYPE_11.getCode());

        advanceOrder.setRestPayBegin(String.valueOf(new Timestamp(infoResp.getTailPayStartTime().getTime())));
        advanceOrder.setRestPayEnd(String.valueOf(new Timestamp(infoResp.getTailPayEndTime().getTime())));
        advanceOrder.setRestPayStatus(TypeStatus.TYPE_11.getCode());

        advanceOrder.setOrderId(builderOrderModel.getOrderId());
        advanceOrder.setId(whaleCloudKeyGenerator.tableKeySeq(DBTableSequence.ORD_ADVANCE_ORDER.getCode()));

        advanceOrder.setCreateUserId(req.getUserId());
        advanceOrder.setCreateTime(String.valueOf(new Timestamp(System.currentTimeMillis())));

        advanceOrder.setSourceFrom(req.getSourceFrom());

        builderOrderModel.setAdvanceOrder(advanceOrder);
        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }

    /**
     * 前置补贴活动
     */
    public void activityAndPromotions(PreCreateOrderReq req, BuilderOrderModel model) {
        MarketingActivityQueryByGoodsReq activityRrq = new MarketingActivityQueryByGoodsReq();
        activityRrq.setSupplierCode(req.getMerchantId());
        activityRrq.setMerchantCode(req.getUserCode());
        activityRrq.setActivityType("1002");
        List<Promotion> promotionList = model.getPromotionList();
        if (CollectionUtils.isEmpty(promotionList)) {
            promotionList = new ArrayList<>();
        }
        model.setPromotionList(promotionList);
        for (OrderItem orderiMT : model.getOrderItem()) {
            activityRrq.setProductId(orderiMT.getProductId());
            List<MarketingAndPromotionResp> iteList = marketingActivityService.listMarketingActivityAndPromotions(activityRrq).getResultData();
            log.info("gs_10010_activityAndPromotions req{},resp{}", JSON.toJSONString(activityRrq), JSON.toJSONString(iteList));
            if (CollectionUtils.isEmpty(iteList)) {
                continue;
            }

            boolean isContinueUse = true;
            for (MarketingAndPromotionResp promotionResp : iteList) {
//                活动类型 1001--预售  1002--前置补贴 1003--返利
//                优惠类型 10--减免 20--券 30--返利 40--赠送
                double goodAmount = CurrencyUtil.mul(orderiMT.getNum(), orderiMT.getPrice());
                double discuontTotal = 0.0;
                if (OrderManagerConsts.ACTIVITY_TYPE_1002.equals(promotionResp.getActivityType()) && "10".equals(promotionResp.getPromotionTypeCode())) {
                    Promotion promotion = new Promotion();
                    promotion.setOrderItemId(orderiMT.getItemId());
                    promotion.setSourceFrom(orderiMT.getSourceFrom());
                    promotion.setOrderId(orderiMT.getOrderId());
                    promotion.setMktActName(promotionResp.getName());
                    promotion.setMktActId(promotionResp.getId());
                    promotion.setPromotionType(promotionResp.getPromotionTypeCode());
                    promotion.setProductId(promotionResp.getProductId());
                    promotion.setGoodsId(orderiMT.getGoodsId());
                    promotion.setPromotionName(promotionResp.getMktResName());
                    promotion.setMktActType(promotionResp.getActivityType());
                    if (promotionResp.getPromotionPrice() != null) {

                        double disTotal=Double.parseDouble(promotionResp.getPromotionPrice());
                        //orderiMT.getNum()  下单的商品的数量
                        long limitNum = promotionResp.getNum();
                        if(limitNum >= orderiMT.getNum() || limitNum==-1){// 活动限制的数量 > 下单的数量
                        	disTotal =CurrencyUtil.mul(disTotal,orderiMT.getNum());
                        }else{// 活动数量  < 下单数
                        	disTotal = CurrencyUtil.mul(disTotal,limitNum);
                        }
                        
                        log.info("gs_10010_discuontTotal,disTotal",disTotal);
                        if(disTotal>=goodAmount){
                            isContinueUse = false;
                            promotion.setDiscount(String.valueOf(goodAmount));
                            promotionList.add(promotion);
                        }else{
                            if (discuontTotal >= goodAmount) {
                                isContinueUse = false;
                            } else {
                                promotion.setDiscount(String.valueOf(disTotal));
                                promotionList.add(promotion);
                            }
                        }
                        discuontTotal = CurrencyUtil.add(discuontTotal,disTotal);
                        log.info("gs_10010_discuontTotal,discuontTotal",discuontTotal);
                    }
                    if (!isContinueUse) {
                        break;
                    }

                }
            }
        }

    }

    /**
     * 记录订单参与的活动
     */
    public ResultVO saveActivityPromotion(BuilderOrderModel builderOrderModel, CreateOrderLogModel logModel) {
        ResultVO resultVO = new ResultVO();
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        if (CollectionUtils.isEmpty(builderOrderModel.getPromotionList())) {
            return resultVO;
        }

        Order order = builderOrderModel.getOrder();
        List<ActHistoryPurChaseAddReq> reqList = new ArrayList<>();
        for (Promotion promotion : builderOrderModel.getPromotionList()) {
            for (OrderItem orderItem : builderOrderModel.getOrderItem()) {
                if (promotion.getOrderItemId().equals(orderItem.getItemId())) {
                    ActHistoryPurChaseAddReq req = new ActHistoryPurChaseAddReq();
                    //订单信息
                    req.setOrderType(order.getOrderType());
                    req.setOrderId(order.getOrderId());
                    //订单项信息
                    req.setNum(orderItem.getNum());
                    req.setPrice(String.valueOf(orderItem.getPrice()));
                    req.setProductId(orderItem.getProductId());
                    req.setGoodsId(orderItem.getGoodsId());
                    req.setOrderItemId(orderItem.getItemId());
                    //优惠信息
                    req.setCouponInstId(promotion.getPromotionInstId());
                    req.setCreator(order.getCreateUserId());
                    req.setMarketingActivityId(promotion.getMktActId());
                    if (order.getSupplierCode() == null) {
                        req.setSupplierCode(order.getMerchantCode());
                    } else {
                        req.setSupplierCode(order.getSupplierCode());
                    }

                    req.setMerchantCode(order.getMerchantCode());
                    req.setPromotionPrice(promotion.getDiscount());
                    req.setActivityType(promotion.getMktActType());
                    reqList.add(req);
                    break;
                }
            }
        }

        resultVO = historyPurchaseService.addActHistroyPurchase(reqList);
        log.info("gs_10010_saveActivityPromotion,req{},resp{}", JSON.toJSONString(reqList), JSON.toJSONString(resultVO));
        logModel.setActivityPromotion(true);
        return resultVO;
    }

    /**
     * 回滚
     */
    public ResultVO rollActivityPromotion(BuilderOrderModel builderOrderModel) {
        ResultVO resultVO = new ResultVO();
        ActHistoryPurChaseUpReq req = new ActHistoryPurChaseUpReq();
        req.setOrderId(builderOrderModel.getOrder().getOrderId());
        historyPurchaseService.updateHistroyPurchase(req);
        log.info("gs_10010_rollActivityPromotion,req{},resp{}", JSON.toJSONString(req), JSON.toJSONString(resultVO));
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resultVO;
    }

    /**
     * 订单关联的前置补贴活动是否超过要求的发货截至时间
     */
    public String validActivityByOrderItemId(String orderItemId) {
        PromotionModel promotionModel = new PromotionModel();
        promotionModel.setOrderItemId(orderItemId);
        List<Promotion> promotions = promotionManager.selectPromotion(promotionModel);
        log.info("ActivityManagerReference.valieNbr promotionManager.selectPromotion promotionModel={}, resp={}", JSON.toJSONString(promotionModel) , JSON.toJSONString(promotions));
        if (!CollectionUtils.isEmpty(promotions)) {
            List<String> activityIdList = promotions.stream().map(Promotion::getMktActId).collect(Collectors.toList());
            for (String activityId : activityIdList) {
                ResultVO<MarketingActivityInfoResp> resultVO = marketingActivityService.queryMarketingActivityInfor(activityId);
                if (!resultVO.isSuccess() || null == resultVO.getResultData()) {
                    return null;
                }
                MarketingActivityDetailResp resp = resultVO.getResultData().getMarketingActivityDetailResp();
                Date endTime = resp.getDeliverEndTime();
                Date startTime = resp.getDeliverStartTime();
                Date now = new Date();
                // 前置补贴活动要求的发货截至时间小于当前时间
                if (endTime.before(now)) {
                    return "该订单参加的\""+resp.getName()+"\"营销活动已结束，无法进行串码上传，未发货的商品请线下协调退款重新下单发货";
                }
                // 前置补贴活动要求的发货开始时间大于当前时间
                if (startTime.after(now)) {
                    return "该订单参加的\""+resp.getName()+"\"营销活动发货时间还没到，请到发货时间重新下单发货";
                }
            }
        }
        return null;
    }
}
