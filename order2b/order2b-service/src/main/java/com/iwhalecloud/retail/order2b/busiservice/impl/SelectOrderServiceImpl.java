package com.iwhalecloud.retail.order2b.busiservice.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.member.dto.response.MemberAddressRespDTO;
import com.iwhalecloud.retail.member.service.MemberAddressService;
import com.iwhalecloud.retail.order2b.busiservice.SelectOrderService;
import com.iwhalecloud.retail.order2b.config.Order2bContext;
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
import com.iwhalecloud.retail.partner.dto.req.MerchantGetReq;
import com.iwhalecloud.retail.partner.dto.req.MerchantLigthReq;
import com.iwhalecloud.retail.partner.dto.resp.MerchantLigthResp;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private MemberInfoReference memberInfoReference;


    @Value("${fdfs.show.url}")
    private String showUrl;

    @Reference
    private MemberAddressService memberAddressService;

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
        if (CollectionUtils.isEmpty(list.getRecords())) {
            return list;
        }

        if (!CollectionUtils.isEmpty(list.getRecords())) {
            for(OrderInfoModel orderInfoModel:list.getRecords()){
                String receiveAddrId = orderInfoModel.getReceiveAddrId();
                if(!StringUtils.isEmpty(receiveAddrId)){
                    MemberAddressRespDTO memberAddressRespDTO = memberAddressService.queryAddress(receiveAddrId);
                    if(null!=memberAddressRespDTO){
                        orderInfoModel.setReceiveAddr(memberAddressRespDTO.getProvince()+memberAddressRespDTO.getCity()+
                                memberAddressRespDTO.getRegion()+memberAddressRespDTO.getAddr());
                    }
                }
            }
        }
        /**
         * 营销活动
         */
        this.getPromotion(list.getRecords());
        return list;
    }

    @Override
    public List<OrderItemInfoModel> selectOrderItemInfoListById(OrderItem orderItem) {
        List<OrderItemInfoModel> itemInfoModels = orderManager.getOrderItemInfoListById(orderItem);
        return itemInfoModels;
    }

    @Override
    public IPage<AfterSalesModel> selectAfterSale(SelectAfterModel req) {

        List<List<String>> itemList = new ArrayList<>();
        //商品查询

        if (!StringUtils.isEmpty(req.getGoodsName())//商品名称
                || !StringUtils.isEmpty(req.getGoodsSn())//编码
                || !StringUtils.isEmpty(req.getBrandName())//类别
                ) {
            List<String> goodsOrderLsit = new ArrayList<>();
            OrderItemModel orderItem = new OrderItemModel();
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
            orderItem.setLanIdList(req.getLanIdList());
            List<OrderItem> gList = orderManager.selectOrderItem(orderItem);
            for (OrderItem i : gList) {
                goodsOrderLsit.add(i.getItemId());
            }
            itemList.add(goodsOrderLsit);
        }

        if (!StringUtils.isEmpty(req.getMerchantCode()) || !StringUtils.isEmpty(req.getMerchantName()) || !StringUtils.isEmpty(req.getBusinessEntityName())) {
            MerchantLigthReq merchantLigthReq = new MerchantLigthReq();
            BeanUtils.copyProperties(req, merchantLigthReq);
            List<String> merchantIdList = memberInfoReference.listMerchantIdList(merchantLigthReq);
            req.setApplicantIdList(merchantIdList);
        }

        //串码查询
        if (!StringUtils.isEmpty(req.getResNbr())) {
            List<String> resBerLsit = new ArrayList<>();
            OrderItemDetailModel orderItemDetail = new OrderItemDetailModel();
            orderItemDetail.setResNbr(req.getResNbr());
            orderItemDetail.setLanIdList(req.getLanIdList());
            List<OrderItemDetail> dList = orderManager.selectOrderItemDetail(orderItemDetail);
            for (OrderItemDetail orderItemDetail1 : dList) {
                resBerLsit.add(orderItemDetail1.getItemId());
            }
            itemList.add(resBerLsit);
        }

        /**
         * 查询出来的itemId,求交集
         */
        if (!CollectionUtils.isEmpty(itemList)) {
            List<String> orderItemList=new ArrayList<>();
            orderItemList.addAll(itemList.get(0));
            for (int i=1;i<itemList.size();i++){
                orderItemList.retainAll(itemList.get(i));
            }
            req.setItemList(orderItemList);
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

            // 组装申请人信息
            if (!StringUtils.isEmpty(model.getApplicantId())) {
                MerchantGetReq merchantGetReq = new MerchantGetReq();
                merchantGetReq.setMerchantId(model.getApplicantId());
                MerchantLigthResp merchantLigthResp = memberInfoReference.getMerchantForOrder(merchantGetReq);
                if (null != merchantGetReq) {
                    BeanUtils.copyProperties(merchantLigthResp, model);
                }
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

        IPage<OrderInfoModel> list = orderManager.selectOrderListByOrder(req);
        if (CollectionUtils.isEmpty(list.getRecords())) {
            return new Page<>();
        }

        //营销活动
        this.getPromotion(list.getRecords());
        Page<AdvanceOrderInfoModel> respPage= new Page<>();
        BeanUtils.copyProperties(list,respPage);
        respPage.setRecords(JSON.parseArray(JSON.toJSONString(list.getRecords()),AdvanceOrderInfoModel.class));
        for (AdvanceOrderInfoModel model : respPage.getRecords()) {
            // 预售订单
            model.setAdvanceOrder(advanceOrderManager.getAdvanceOrderByOrderId(model.getOrderId()));
        }
        return respPage;
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
     * 获取参与的营销活动
     * param orderList
     */
    private void getPromotion(List<OrderInfoModel> modelList) {
        PromotionModel promotionModel = new PromotionModel();

        /**
         * 组装订单id
         */
        promotionModel.setOrderIdList(new ArrayList<>(modelList.size()));
        promotionModel.setLanIdList(new ArrayList<>(modelList.size()));
        for (OrderInfoModel orderInfoModel:modelList){
            promotionModel.getOrderIdList().add(orderInfoModel.getOrderId());
            promotionModel.getLanIdList().add(orderInfoModel.getLanId());
//            if(StringUtils.isEmpty(Order2bContext.getDubboRequest().getLanId())){
//                List<String> lanIds=new ArrayList<>(1);
//                lanIds.add(orderInfoModel.getLanId());
//                promotionModel.setLanIdList(lanIds);
//            }
        }

        List<Promotion> promotions = promotionManager.selectPromotion(promotionModel);
        if(CollectionUtils.isEmpty(promotions)){
            return;
        }
        Map<String, List<Promotion>> orderItemMaps = promotions.stream().collect(Collectors.groupingBy(Promotion::getOrderId));

        for (OrderInfoModel model:modelList){
            model.setPromotionList(orderItemMaps.get(model.getOrderId()));
            if (CollectionUtils.isEmpty(model.getPromotionList())) {
                continue;
            }
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
    }

    /**
     * 根据查询条件获取订单id列表
     *
     * @param req
     * @return
     */
    private List<String> getOrderListByCondition(SelectOrderDetailModel req) {
        List<String> orderList = new ArrayList<>();
        List<List<String>> selectList=new ArrayList<>();

        if (!CollectionUtils.isEmpty(req.getReqOrderList())) {
            selectList.add(req.getReqOrderList());
        }

        //商品、品牌、商品编码查询
        if (!StringUtils.isEmpty(req.getGoodsName()) || !StringUtils.isEmpty(req.getGoodsSn()) || !StringUtils.isEmpty(req.getBrandName())) {
            List<String> goodsOrderLsit = new ArrayList<>();
            OrderItemModel orderItem = new OrderItemModel();
            if (!StringUtils.isEmpty(req.getGoodsName())) {
                orderItem.setGoodsName("%" + req.getGoodsName() + "%");
            }
            if (!StringUtils.isEmpty(req.getGoodsSn())) {
                orderItem.setSn(req.getGoodsSn());
            }
            if (!StringUtils.isEmpty(req.getBrandName())) {
                orderItem.setBrandName("%" + req.getBrandName() + "%");
            }
            orderItem.setLanIdList(req.getLanIdList());
            List<OrderItem> gList = orderManager.selectOrderItem(orderItem);
            for (OrderItem i : gList) {
                goodsOrderLsit.add(i.getOrderId());
            }
            selectList.add(goodsOrderLsit);
        }

        //串码查询
        if (!StringUtils.isEmpty(req.getResNbr())) {
            List<String> resBerLsit = new ArrayList<>();
            OrderItemDetailModel model=new OrderItemDetailModel();
            model.setLanIdList(req.getLanIdList());
            model.setResNbr(req.getResNbr());
            resBerLsit.addAll(orderManager.selectOrderIdByresNbr(model));
            selectList.add(resBerLsit);
        }

        //营销活动查询
        if (!StringUtils.isEmpty(req.getActivityPromoName())) {
            List<String> promotionList = new ArrayList<>();
            PromotionModel promotionModel = new PromotionModel();
            promotionModel.setMktActName("%" + req.getActivityPromoName() + "%");
            promotionModel.setLanIdList(req.getLanIdList());
            List<Promotion> promotions = promotionManager.selectPromotion(promotionModel);
            for (Promotion promotion : promotions) {
                promotionList.add(promotion.getOrderId());
            }
            selectList.add(promotionList);
        }
        if(CollectionUtils.isEmpty(selectList)){
            return orderList;
        }
        orderList.addAll(selectList.get(0));
        for (int i=1;i<selectList.size();i++){
            orderList.retainAll(selectList.get(i));
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
