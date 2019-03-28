package com.iwhalecloud.retail.order2b.busiservice.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.busiservice.SelectOrderService;
import com.iwhalecloud.retail.order2b.consts.OrderManagerConsts;
import com.iwhalecloud.retail.order2b.consts.order.OrderAllStatus;
import com.iwhalecloud.retail.order2b.dto.model.order.CouponInsDTO;
import com.iwhalecloud.retail.order2b.entity.*;
import com.iwhalecloud.retail.order2b.manager.AdvanceOrderManager;
import com.iwhalecloud.retail.order2b.manager.AfterSaleManager;
import com.iwhalecloud.retail.order2b.manager.OrderManager;
import com.iwhalecloud.retail.order2b.manager.PromotionManager;
import com.iwhalecloud.retail.order2b.model.*;
import com.iwhalecloud.retail.order2b.reference.MemberInfoReference;
import com.iwhalecloud.retail.order2b.util.CurrencyUtil;
import com.iwhalecloud.retail.order2b.util.Utils;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.service.MerchantService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class SelectOrderServiceImpl implements SelectOrderService {

    @Autowired
    private OrderManager orderManager;

    @Autowired
    private AfterSaleManager afterSaleManager;

    @Autowired
    private PromotionManager promotionManager;

    @Autowired
    private AdvanceOrderManager advanceOrderManager;


    @Value("${fdfs.show.url}")
    private String showUrl;

    @Autowired
    private MemberInfoReference memberInfoReference;

    @Reference
    private MerchantService merchantService;

    @Override
    public IPage<OrderInfoModel> selectOrderListByOrder(SelectOrderDetailModel req) {

        List<String> orderList = this.getOrderListByCondition(req);
        if (!CollectionUtils.isEmpty(orderList)) {
            req.setOrderList(new ArrayList<>(orderList));
        }

        /**
         * 默认查询普通订单
         */
        if (OrderManagerConsts.ORDER_CAT_1.equals(req.getOrderCat())) {
            req.setOrderCat(OrderManagerConsts.ORDER_CAT_1);
            if (!CollectionUtils.isEmpty(req.getStatusAll())) {
                req.getStatusAll().add(OrderAllStatus.ORDER_STATUS_13.getCode());
                req.getStatusAll().add(OrderAllStatus.ORDER_STATUS_14.getCode());
            }
        }
        IPage<OrderInfoModel> list = orderManager.selectOrderListByOrder(req);
        if (!CollectionUtils.isEmpty(list.getRecords())) {
            for (OrderInfoModel orderInfoModel : list.getRecords()) {
                ResultVO<MerchantDTO> resultVO = merchantService.getMerchantById(orderInfoModel.getMerchantId());
                if (resultVO.isSuccess() && null != resultVO.getResultData() && org.apache.commons.lang3.StringUtils.isNotEmpty(resultVO.getResultData().getMerchantCode())) {
                    MerchantDTO merchantDTO = resultVO.getResultData();
                    orderInfoModel.setMerchantCode(merchantDTO.getMerchantCode());
                }
            }
        }
        if (CollectionUtils.isEmpty(list.getRecords())) {
            return list;
        }

        //图片拼接:ip+port
        for (OrderInfoModel model : list.getRecords()) {
            /**
             * 营销活动
             */
            this.getPromotion(model);
        }
        return list;
    }

    @Override
    public List<OrderItemInfoModel> selectOrderItemInfoListById(OrderItem orderItem) {
        List<OrderItemInfoModel> itemInfoModels = orderManager.getOrderItemInfoListById(orderItem);
        return itemInfoModels;
    }

    @Override
    public IPage<AfterSalesModel> selectAfterSale(SelectAfterModel req) {

        List<String> itemList = new ArrayList<>();
        //商品查询
        List<String> goodsOrderLsit = new ArrayList<>();
        if (!StringUtils.isEmpty(req.getGoodsName())//商品名称
                || !StringUtils.isEmpty(req.getGoodsSn())//编码
                || !StringUtils.isEmpty(req.getBrandName())//类别
                ) {
            goodsOrderLsit.add("-1");
            OrderItem orderItem = new OrderItem();
            if (!StringUtils.isEmpty(req.getGoodsName())) {
                orderItem.setGoodsName("%" + req.getGoodsName() + "%");
            }
            if (!StringUtils.isEmpty(req.getGoodsSn())) {
                orderItem.setSn(req.getGoodsSn());
            }
            if (!StringUtils.isEmpty(req.getBrandName())) {
                orderItem.setBrandName("%" + req.getBrandName() + "%");
            }
            orderItem.setSourceFrom(req.getSourceFrom());
            List<OrderItem> gList = orderManager.selectOrderItem(orderItem);
            for (OrderItem i : gList) {
                goodsOrderLsit.add(i.getItemId());
            }
            itemList.addAll(goodsOrderLsit);
            itemList.retainAll(goodsOrderLsit);
        }

        //串码查询
        List<String> resBerLsit = new ArrayList<>();
        if (!StringUtils.isEmpty(req.getResNbr())) {
            resBerLsit.add("-1");
            OrderItemDetailModel orderItemDetail = new OrderItemDetailModel();
            orderItemDetail.setResNbr(req.getResNbr());
            List<OrderItemDetail> dList = orderManager.selectOrderItemDetail(orderItemDetail);
            for (OrderItemDetail orderItemDetail1 : dList) {
                resBerLsit.add(orderItemDetail1.getItemId());
            }
            itemList.addAll(resBerLsit);
            itemList.retainAll(resBerLsit);
        }
        if (!CollectionUtils.isEmpty(itemList)) {
            req.setItemList(new ArrayList<>(itemList));
        }

        //供应商查询
        if (!StringUtils.isEmpty(req.getSupplierName())) {
            req.setSupplierName("%" + req.getSupplierName() + "%");
        }

        IPage<AfterSalesModel> iPage = afterSaleManager.selectAfterSales(req);
        //图片拼接:ip+port
        for (AfterSalesModel model : iPage.getRecords()) {

            OrderItem orderItem = model.getOrderItems();
            if (!StringUtils.isEmpty(orderItem.getImage())) {
                orderItem.setImage(Utils.attacheUrlPrefix(showUrl, orderItem.getImage()));
            }

            if (!StringUtils.isEmpty(model.getUploadImgUrl())) {
                model.setUploadImgUrl(Utils.attacheUrlPrefix(showUrl, model.getUploadImgUrl()));
            }

            if (!StringUtils.isEmpty(model.getRefundImgUrl())) {
                model.setRefundImgUrl(Utils.attacheUrlPrefix(showUrl, model.getRefundImgUrl()));
            }
        }

        return iPage;
    }

    @Override
    public List<BuilderOrderModel> builderOrderInfoRollback(String orderId, CreateOrderLogModel logModel) {

        List<BuilderOrderModel> bList = new ArrayList<>();
        SelectOrderDetailModel orderReq = new SelectOrderDetailModel();
        orderReq.setOrderId(orderId);
        IPage<OrderInfoModel> list = orderManager.selectOrderListByOrder(orderReq);

        if (CollectionUtils.isEmpty(list.getRecords())) {
            return bList;
        }


        BuilderOrderModel builderOrderModel = new BuilderOrderModel();
        Order order = new Order();
        BeanUtils.copyProperties(list.getRecords().get(0), order);

        bList.add(builderOrderModel);
        builderOrderModel.setOrder(order);
        builderOrderModel.setOrderItem(list.getRecords().get(0).getOrderItems());

        logModel.setOrderCat(order.getOrderCat());
        logModel.setUserCode(order.getUserId());
        logModel.setUserId(order.getCreateUserId());
        logModel.setMerchantId(order.getMerchantId());
        logModel.setErrorMessage("订单取消");

        PromotionModel promotionModel = new PromotionModel();
        promotionModel.setOrderId(orderId);
        /**
         * 是否使用了优惠券
         */
        List<Promotion> promotions = promotionManager.selectPromotion(promotionModel);
        List<CouponInsDTO> couList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(promotions)) {

            logModel.setActivityPromotion(true);

            HashMap<String, CouponInsDTO> couHashMap = new HashMap<>();
            for (Promotion p : promotions) {
                CouponInsDTO couponInsDTO = couHashMap.get(p.getPromotionInstId());
                if (couponInsDTO == null) {
                    couponInsDTO = new CouponInsDTO();
                    couponInsDTO.setCouponCode(p.getPromotionInstId());
                    couponInsDTO.setCouponMKId(p.getPromotionId());
                    couList.add(couponInsDTO);
                    couHashMap.put(p.getPromotionInstId(), couponInsDTO);
                }
            }
        }
        if (!CollectionUtils.isEmpty(couList)) {
            logModel.setCouponInsList(couList);
            logModel.setCoupon(true);
        }
        //库存回滚
        logModel.setUpdateStock(true);
        //分货规则回滚
        logModel.setRuleGoods(true);
        logModel.setUpdateGoodsBuyCount(true);
        return bList;
    }

    @Override
    public IPage<AdvanceOrderInfoModel> queryadvanceOrderList(SelectOrderDetailModel req) {
        List<String> orderList = this.getOrderListByCondition(req);

        if (!CollectionUtils.isEmpty(orderList)) {
            req.setOrderList(new ArrayList<>(orderList));
        }
        // 定金支付时间、尾款支付时间查询
        if (!Objects.isNull(req.getAdvancePayTimeStart()) || !Objects.isNull(req.getRestPayTimeStart())) {
            orderList.add("-1");
            List<AdvanceOrder> advanceOrders = advanceOrderManager.queryAdvanceOrderByCondition(req);
            for (AdvanceOrder advanceOrder : advanceOrders) {
                orderList.add(advanceOrder.getOrderId());
            }
        }
        if (!CollectionUtils.isEmpty(orderList)) {
            req.setOrderList(new ArrayList<>(orderList));
        }

        IPage<AdvanceOrderInfoModel> list = advanceOrderManager.queryAdvanceOrderList(req);
        //图片拼接:ip+port
        for (AdvanceOrderInfoModel model : list.getRecords()) {
            // 营销活动
            this.getPromotion(model);
        }
        return list;
    }

    @Override
    public List<String> getOrderStatusByUser(String userType, String orderType) {
        List<String> statusList = new ArrayList<>();

        statusList.add(OrderAllStatus.ORDER_STATUS_12.getCode());
        statusList.add(OrderAllStatus.ORDER_STATUS_12_.getCode());
        statusList.add(OrderAllStatus.ORDER_STATUS_1_.getCode());
        statusList.add(OrderAllStatus.ORDER_STATUS_2_.getCode());

        statusList.add(OrderAllStatus.ORDER_STATUS_2.getCode());
        statusList.add(OrderAllStatus.ORDER_STATUS_4.getCode());
        statusList.add(OrderAllStatus.ORDER_STATUS_41.getCode());
        statusList.add(OrderAllStatus.ORDER_STATUS_5.getCode());
        statusList.add(OrderAllStatus.ORDER_STATUS_6.getCode());
        statusList.add(OrderAllStatus.ORDER_STATUS_10.getCode());
        statusList.add(OrderAllStatus.ORDER_STATUS_10_.getCode());
        statusList.add(OrderAllStatus.ORDER_STATUS_4_.getCode());
        statusList.add(OrderAllStatus.ORDER_STATUS_31.getCode());

        switch (userType) {
            case OrderManagerConsts.USER_EXPORT_TYPE_1:
                break;
            case OrderManagerConsts.USER_EXPORT_TYPE_2:
                break;
            case OrderManagerConsts.USER_EXPORT_TYPE_3:
                break;
        }

        switch (orderType) {
            case OrderManagerConsts.ORDER_CAT_1:
                statusList.add(OrderAllStatus.ORDER_STATUS_14.getCode());
                statusList.add(OrderAllStatus.ORDER_STATUS_13.getCode());
                break;
            default:
                break;
        }
        return statusList;
    }

    /**
     * 获取营销活动
     *
     * @param model
     */
    private void getPromotion(OrderInfoModel model) {
        PromotionModel promotionModel = new PromotionModel();
        promotionModel.setOrderId(model.getOrderId());
        List<Promotion> promotions = promotionManager.selectPromotion(promotionModel);
        model.setPromotionList(promotions);

        if (!CollectionUtils.isEmpty(model.getPromotionList())) {
            for (Promotion promotion : model.getPromotionList()) {

                if (promotion.getDiscount() == null) {
                    continue;
                }
                //直减
                if ("10".equals(promotion.getPromotionType())) {
                    model.setDirectReductionAmount(CurrencyUtil.add(Double.parseDouble(promotion.getDiscount()),
                            model.getDirectReductionAmount()));
                    //优惠券
                } else if ("20".equals(promotion.getPromotionType())) {
                    model.setAcCouponAmount(CurrencyUtil.add(Double.parseDouble(promotion.getDiscount()),
                            model.getAcCouponAmount()));
                }
            }
        }

        int receiveNum = 0;
        int deliveryNum = 0;
        for (OrderItem orderItem : model.getOrderItems()) {
            if (orderItem.getReceiveNum() != null) {
                receiveNum += orderItem.getReceiveNum();
            }
            if (orderItem.getDeliveryNum() != null) {
                deliveryNum += orderItem.getDeliveryNum();
            }
            if (!StringUtils.isEmpty(orderItem.getImage())) {
                orderItem.setImage(Utils.attacheUrlPrefix(showUrl, orderItem.getImage()));
            }
        }
        model.setReceiveNum(receiveNum);
        model.setDeliveryNum(deliveryNum);
    }

    /**
     * 根据查询条件获取订单id列表
     *
     * @param req
     * @return
     */
    private List<String> getOrderListByCondition(SelectOrderDetailModel req) {
        List<String> orderList = new ArrayList<>();

        if (!CollectionUtils.isEmpty(req.getReqOrderList())) {
            orderList.addAll(req.getReqOrderList());
        }

        //商品、品牌、商品编码查询
        List<String> goodsOrderLsit = new ArrayList<>();
        if (!StringUtils.isEmpty(req.getGoodsName()) || !StringUtils.isEmpty(req.getGoodsSn()) || !StringUtils.isEmpty(req.getBrandName())) {
            goodsOrderLsit.add("-1");
            OrderItem orderItem = new OrderItem();
            if (!StringUtils.isEmpty(req.getGoodsName())) {
                orderItem.setGoodsName("%" + req.getGoodsName() + "%");
            }
            if (!StringUtils.isEmpty(req.getGoodsSn())) {
                orderItem.setSn(req.getGoodsSn());
            }
            if (!StringUtils.isEmpty(req.getBrandName())) {
                orderItem.setBrandName("%" + req.getBrandName() + "%");
            }
            orderItem.setSourceFrom(req.getSourceFrom());
            List<OrderItem> gList = orderManager.selectOrderItem(orderItem);
            for (OrderItem i : gList) {
                goodsOrderLsit.add(i.getOrderId());
            }
            orderList.addAll(goodsOrderLsit);
            orderList.retainAll(goodsOrderLsit);
        }

        //串码查询
        List<String> resBerLsit = new ArrayList<>();
        if (!StringUtils.isEmpty(req.getResNbr())) {
            resBerLsit.add("-1");
            resBerLsit.addAll(orderManager.selectOrderIdByresNbr(req.getResNbr()));

            orderList.addAll(resBerLsit);
            orderList.retainAll(resBerLsit);
        }

        //营销活动查询
        List<String> promotionList = new ArrayList<>();
        if (!StringUtils.isEmpty(req.getActivityPromoName())) {
            promotionList.add("-1");
            PromotionModel promotionModel = new PromotionModel();
            promotionModel.setMktActName("%" + req.getActivityPromoName() + "%");
            List<Promotion> promotions = promotionManager.selectPromotion(promotionModel);
            for (Promotion promotion : promotions) {
                promotionList.add(promotion.getOrderId());
            }
            orderList.addAll(promotionList);
            orderList.retainAll(promotionList);
        }
        return orderList;
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("-1");
        list.retainAll(new ArrayList<>());
        System.out.println(list);
    }


}
