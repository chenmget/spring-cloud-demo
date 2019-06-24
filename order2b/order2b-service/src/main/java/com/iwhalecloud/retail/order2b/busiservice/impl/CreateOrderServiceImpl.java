package com.iwhalecloud.retail.order2b.busiservice.impl;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.order2b.busiservice.*;
import com.iwhalecloud.retail.order2b.consts.OmsCommonConsts;
import com.iwhalecloud.retail.order2b.consts.OrderManagerConsts;
import com.iwhalecloud.retail.order2b.consts.order.OrderAllStatus;
import com.iwhalecloud.retail.order2b.consts.order.OrderBuilderType;
import com.iwhalecloud.retail.order2b.consts.order.OrderShipType;
import com.iwhalecloud.retail.order2b.consts.order.TypeStatus;
import com.iwhalecloud.retail.order2b.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order2b.dto.model.cart.CartOrderAmountDTO;
import com.iwhalecloud.retail.order2b.dto.model.order.CreateOrderDTO;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderItemDTO;
import com.iwhalecloud.retail.order2b.dto.model.order.PromotionDTO;
import com.iwhalecloud.retail.order2b.dto.model.order.UserMemberDTO;
import com.iwhalecloud.retail.order2b.dto.response.CreateOrderResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.CreateOrderRequest;
import com.iwhalecloud.retail.order2b.dto.resquest.order.PreCreateOrderReq;
import com.iwhalecloud.retail.order2b.entity.AdvanceOrder;
import com.iwhalecloud.retail.order2b.entity.Order;
import com.iwhalecloud.retail.order2b.entity.OrderItem;
import com.iwhalecloud.retail.order2b.manager.AdvanceOrderManager;
import com.iwhalecloud.retail.order2b.manager.OrderManager;
import com.iwhalecloud.retail.order2b.manager.OrderZFlowManager;
import com.iwhalecloud.retail.order2b.manager.PromotionManager;
import com.iwhalecloud.retail.order2b.model.*;
import com.iwhalecloud.retail.order2b.reference.MemberInfoReference;
import com.iwhalecloud.retail.order2b.reference.TaskManagerReference;
import com.iwhalecloud.retail.order2b.util.Utils;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.system.common.SystemConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.iwhalecloud.retail.order2b.consts.order.OrderBuilderType.*;

@Service
@Slf4j
public class CreateOrderServiceImpl implements CreateOrderService {

    @Autowired
    private MemberInfoReference memberInfoReference;

    @Autowired
    private SelectCartGoodsService selectCartGoodsService;


    @Autowired
    private OrderManager orderManager;

    @Autowired
    private PromotionManager promotionManager;

    @Autowired
    private AdvanceOrderManager advanceOrderManager;

    @Autowired
    private OrderZFlowManager orderZFlowManager;

    @Autowired
    private TaskManagerReference taskManagerReference;

    @Autowired
    private BuilderCartService builderCartService;

    @Autowired
    private OrdinaryOrderBuilderService ordinaryOrderBuilderService;

    @Autowired
    private AdvanceSaleOrderBuilderService advanceSaleOrderBuilderService;


    @Override
    public CommonResultResp<List<CartItemModel>> selectCartGoods(CreateOrderRequest request) {
        CommonResultResp<List<CartItemModel>> resultVO = new CommonResultResp<>();
        switch (request.getSourceType()) {
            case OrderManagerConsts.ORDER_SOURCE_TYPE_LJGM:
                resultVO = selectCartGoodsService.builderCartData(request);
                if (resultVO.isFailure()) {
                    return resultVO;
                }
                break;
            case OrderManagerConsts.ORDER_SOURCE_TYPE_GWC:

                //购物车查询
                List<CartItemModel> cartList = builderCartService.selectCart(request);
                if (CollectionUtils.isEmpty(cartList)) {
                    resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                    resultVO.setResultMsg("购物车为空");
                    return resultVO;
                }
                resultVO.setResultData(cartList);
                break;
            default:
                resultVO.setResultMsg("sourceType不匹配");
                resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                return resultVO;
        }
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resultVO;
    }

    @Override
    public CommonResultResp checkRule(PreCreateOrderReq request, List<CartItemModel> list) {
        CommonResultResp resp;
        /**
         * 预售订单
         */
        Boolean advanceType = CollectionUtils.isEmpty(request.getOrderCatList()) && (request.getOrderCatList().contains(OrderManagerConsts.ORDER_CAT.ORDER_CAT_1.getCode()) || request.getOrderCatList().contains(OrderManagerConsts.ORDER_CAT.ORDER_CAT_3.getCode()));
        if (advanceType) {
            resp = advanceSaleOrderBuilderService.checkRule(request, list);
        } else { //普通订单
            resp = ordinaryOrderBuilderService.checkRule(request, list);
        }
        return resp;
    }

    @Override
    public CommonResultResp<Order> initRequestToOrder(CreateOrderRequest request) {
        /**
         * 参数校验
         */
        CommonResultResp resp = Utils.validatorCheck(request);
        if (!resp.isSuccess()) {
            resp.setResultCode(resp.getResultCode());
            resp.setResultMsg(resp.getResultMsg());
            return resp;
        }

        Order order = new Order();
        /**
         * 用户信息检验
         */
        UserInfoModel userInfoModel = memberInfoReference.selectUserInfo(request.getMemberId());
        if (userInfoModel == null) {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg("用户信息，验证失败");
            return resp;
        }

        SupplierModel userModel = memberInfoReference.selectSuperById(request.getUserCode());
        if (userModel != null) {
            order.setBuyerCode(userModel.getMerchantCode());
        }

        /**
         * 商家校验
         */
        SupplierModel supplierModel = memberInfoReference.selectSuperById(request.getMerchantId());
        if (supplierModel == null) {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg("商家信息，验证失败");
            return resp;
        }
        /**
         * 收货地址
         */
        MemberAddrModel deliverEntityDTO = null;
        OrderShipType shipType = OrderShipType.matchOpCode(request.getShipType());
        switch (shipType) {
            case ORDER_SHIP_TYPE_2:
                deliverEntityDTO = new MemberAddrModel();
                break;
            case ORDER_SHIP_TYPE_1:
                deliverEntityDTO = memberInfoReference.selectReceiveAddrById(request.getAddressId());
                break;
            default:
                break;
        }
        if (deliverEntityDTO == null) {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg("未查询到收货地址");
            return resp;
        }
        //收货地址
        BeanUtils.copyProperties(request, order);  //复制前端传入的数据
        BeanUtils.copyProperties(deliverEntityDTO, order); //保存收货信息
        order.setReceiveAddrId(request.getAddressId());

        /**
         * 通过商家和会员的身份 匹配对应的订单类型
         */
        OrderBuilderType builderType = getOrderType(userInfoModel.getUserFounder(), supplierModel.getMerchantType());
        order.setOrderType(builderType.getCode());

        order.setCreateTime(String.valueOf(new Timestamp(System.currentTimeMillis())));
        order.setCreateUserId(request.getMemberId());
        order.setSourceFrom(request.getSourceFrom());
        order.setUserId(request.getUserCode());
        order.setLanId(userInfoModel.getLanId());
        order.setUserName(userInfoModel.getUserName());
        order.setSupplierName(supplierModel.getMerchantName());
        order.setSupplierId(request.getMerchantId());
        order.setMerchantCode(supplierModel.getMerchantCode());
        order.setSupplierCode(supplierModel.getMerchantCode());

        resp.setResultData(order);
        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }

    @Override
    public CommonResultResp<List<BuilderOrderModel>> AssembleOrderItem(List<CartItemModel> cartList, Order order) {
        CommonResultResp resp = new CommonResultResp();
        //构建订单数据
        List<BuilderOrderModel> orderList = new ArrayList<>();

        BuilderOrderModel builderOrderModel = selectCartGoodsService.builderOrderItems(cartList);

        Order orderi = new Order();
        BeanUtils.copyProperties(order, orderi);
        builderOrderModel.setSourceFrom(order.getSourceFrom());
        orderi.setOrderId(builderOrderModel.getOrderId());
        builderOrderModel.setOrder(orderi);
        builderOrderModel.setSupperName(orderi.getSupplierName());
        builderOrderModel.setSupperId(orderi.getSupplierId());

        for (OrderItem item : builderOrderModel.getOrderItem()) {
            item.setSourceFrom(builderOrderModel.getSourceFrom());
        }

        orderList.add(builderOrderModel);
        resp.setResultData(orderList);
        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }

    @Override
    public CommonResultResp<CreateOrderLogModel> orderOtherHandler(CreateOrderRequest request, List<BuilderOrderModel> list) {

        CommonResultResp<CreateOrderLogModel> resp;
        /**
         * 预售订单
         */
        Boolean advanceType = CollectionUtils.isEmpty(request.getOrderCatList()) && (request.getOrderCatList().contains(OrderManagerConsts.ORDER_CAT.ORDER_CAT_1.getCode()) || request.getOrderCatList().contains(OrderManagerConsts.ORDER_CAT.ORDER_CAT_3.getCode()));
        if (advanceType) {
            resp = advanceSaleOrderBuilderService.orderOtherHandler(request, list);
        } else { //普通订单
            resp = ordinaryOrderBuilderService.orderOtherHandler(request, list);
        }

        //订单金额计算
        CommonResultResp couponResp = orderTotalAmount(list);
        if (couponResp.isFailure()) {
            resp.setResultCode(couponResp.getResultCode());
            resp.setResultMsg(couponResp.getResultMsg());
            resp.getResultData().setErrorMessage(resp.getResultMsg());
            return resp;
        }
        return resp;
    }

    @Override
    public CommonResultResp orderTotalAmount(List<BuilderOrderModel> list) {
        CommonResultResp resultResp = new CommonResultResp();
        //生成batchid
        String batch_id = UUID.randomUUID().toString().replace("-", "");
        for (BuilderOrderModel builder : list) {

            /**
             * 计算订单金额
             */
            CommonResultResp resp = selectCartGoodsService.selectOrderAmount(builder);
            if (resp.isFailure()) {
                return resp;
            }
            CartOrderAmountDTO catOrder = (CartOrderAmountDTO) resp.getResultData();
            Order order = builder.getOrder();
            order.setBatchId(batch_id);
            order.setCouponAmount(catOrder.getCouponAmount());
            order.setGoodsAmount(catOrder.getGoodsAmount());
            order.setOrderAmount(catOrder.getOrderAmount());
            order.setGoodsNum(catOrder.getGoodsNum());
        }
        resultResp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resultResp;
    }

    @Override
    @Transactional
    public CommonResultResp builderOrder(CreateOrderRequest request, List<BuilderOrderModel> list) {
        log.info("CreateOrderServiceImpl.builderOrder request={},list={}", JSON.toJSONString(request), JSON.toJSONString(list));
        CommonResultResp resp = new CommonResultResp();

        for (BuilderOrderModel builderOrderModel : list) {

            Order order = builderOrderModel.getOrder();
            order.setPayStatus(TypeStatus.TYPE_11.getCode());

            order.setOrderCat(request.getOrderCatList().get(0));
            if (CollectionUtils.isEmpty(request.getOrderCatList())) {
                order.setOrderCat(OrderManagerConsts.ORDER_CAT.ORDER_CAT_0.getCode());
            }else {
                order.setOrderCat(request.getOrderCatList().get(0));
            }
            builderOrderModel.setOrderCat(order.getOrderCat());

            // 预售类型
            Boolean advanceType = CollectionUtils.isEmpty(request.getOrderCatList()) && (request.getOrderCatList().contains(OrderManagerConsts.ORDER_CAT.ORDER_CAT_1.getCode()) || request.getOrderCatList().contains(OrderManagerConsts.ORDER_CAT.ORDER_CAT_3.getCode()));
            if (advanceType) {
                order.setStatus(OrderAllStatus.ORDER_STATUS_13.getCode());
            } else {
                //普通订单
                order.setStatus(OrderAllStatus.ORDER_STATUS_2.getCode());
            }

            //如果是配送方式是客户自提，需要录入手机串码
            OrderShipType shipType=OrderShipType.matchOpCode(request.getShipType());
            switch (shipType) {
                case ORDER_SHIP_TYPE_2:
                    order.setGetGoodsCode((int)((Math.random()*9+1)*100000)+"");
                    break;
                default:
                    break;
            }

            /**
             * 商家确认
             */
            if (OrderManagerConsts.IS_MERCHANT_CONFIRM.equals(request.getIsMerchantConfirm())) { //需要商家确认
                order.setStatus(OrderAllStatus.ORDER_STATUS_12.getCode());
            }

            orderManager.saveOrder(builderOrderModel.getOrder());
            log.info("CreateOrderServiceImpl.builderOrder orderManager.saveOrder order={}", JSON.toJSONString(builderOrderModel.getOrder()));
            orderManager.saveOrderItem(builderOrderModel.getOrderItem());
            if (!CollectionUtils.isEmpty(builderOrderModel.getPromotionList())) {
                promotionManager.insertPromotionList(builderOrderModel.getPromotionList());
            }
            if (builderOrderModel.getAdvanceOrder() != null) {
                advanceOrderManager.insertInto(builderOrderModel.getAdvanceOrder());
            }
            //记录工作流数据
            orderZFlowManager.insertFlowList(request, builderOrderModel);

        }
        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }

    @Override
    public CommonResultResp<CreateOrderResp> builderFinishOnSuccess(CreateOrderRequest request, List<BuilderOrderModel> orderModelList) {

        CommonResultResp<CreateOrderResp> resp = new CommonResultResp<>();
        /**
         * 组装数据，返回给前端
         */
        List<CreateOrderDTO> orderDTOS = new ArrayList<>(orderModelList.size());
        for (BuilderOrderModel orderModel : orderModelList) {
            CreateOrderDTO orderDTO = new CreateOrderDTO();
            BeanUtils.copyProperties(orderModel.getOrder(), orderDTO);
            orderDTO.setSupperName(orderModel.getSupperName());
            orderDTO.setPayType(request.getPaymentType());
            orderDTO.setIsMerchantConfirm(request.getIsMerchantConfirm());
            orderDTO.setUserName(orderDTO.getUserName());
            orderDTO.setOrderItem(JSON.parseArray(JSON.toJSONString(orderModel.getOrderItem()), OrderItemDTO.class));
            orderDTO.setPromotionList(JSON.parseArray(JSON.toJSONString(orderModel.getPromotionList()), PromotionDTO.class));

            UserMemberDTO userMemberDTO = new UserMemberDTO();
            BeanUtils.copyProperties(orderModel.getOrder(), userMemberDTO);

            if (orderModel.getAdvanceOrder() != null) {
                AdvanceOrder adOr = orderModel.getAdvanceOrder();
                orderDTO.setAdvanceAmount(adOr.getAdvanceAmount());
                orderDTO.setRestAmount(adOr.getRestAmount());
                orderDTO.setAdvancePayType(orderModel.getAdvancePayType());
            }

            orderDTOS.add(orderDTO);
            //我的代办
            if (OrderManagerConsts.IS_MERCHANT_CONFIRM.equals(orderDTO.getIsMerchantConfirm())) {
                taskManagerReference.addTaskByHandleList("待卖家确认", orderModel.getOrder().getOrderId(),
                        orderModel.getOrder().getCreateUserId(), orderModel.getOrder().getMerchantId());
            } else {
                String messge = "";
                Boolean advanceType = CollectionUtils.isEmpty(request.getOrderCatList()) && (request.getOrderCatList().contains(OrderManagerConsts.ORDER_CAT.ORDER_CAT_1.getCode()) || request.getOrderCatList().contains(OrderManagerConsts.ORDER_CAT.ORDER_CAT_3.getCode()));
                if (advanceType) {
                    messge = "待付款";
                } else {
                    messge = "待付定金";
                }

                taskManagerReference.addTaskByHandlerOne(messge, orderModel.getOrder().getOrderId(),
                        orderModel.getOrder().getCreateUserId(), orderModel.getOrder().getCreateUserId());
            }

        }
        CreateOrderResp createOrderResp = new CreateOrderResp();
        createOrderResp.setOrderList(orderDTOS);

        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        resp.setResultData(createOrderResp);

        return resp;
    }

    @Override
    public CommonResultResp builderFinishOnFailure(List<BuilderOrderModel> list, CreateOrderLogModel log) {
        CommonResultResp resp;
        /**
         * 预售订单
         */
        if (OrderManagerConsts.ORDER_CAT.ORDER_CAT_1.getCode().equals(log.getOrderCat()) || OrderManagerConsts.ORDER_CAT.ORDER_CAT_3.getCode().equals(log.getOrderCat())) {
            resp = advanceSaleOrderBuilderService.rollback(list, log);
        } else { //普通订单
            resp = ordinaryOrderBuilderService.rollback(list, log);
        }
        return resp;
    }

    public static OrderBuilderType getOrderType(Integer userType, String merchantIType) {
        if (StringUtils.isEmpty(userType) || StringUtils.isEmpty(merchantIType)) {
            return ORDER_TYPE_2;
        }

        if (merchantIType.equals(PartnerConst.MerchantTypeEnum.SUPPLIER_PROVINCE.getType())) {//省包
            switch (userType) {
                case SystemConst.USER_FOUNDER_5://地包：
                    return ORDER_TYPE_11;
                case SystemConst.USER_FOUNDER_3://零售商
                    return ORDER_TYPE_12;
                default:
                    return ORDER_TYPE_2;
            }
        } else if (merchantIType.equals(PartnerConst.MerchantTypeEnum.SUPPLIER_GROUND.getType())) {//地包
            switch (userType) {
                case SystemConst.USER_FOUNDER_3://零售商
                    return ORDER_TYPE_13;
                default:
                    return ORDER_TYPE_2;
            }
        }
        return ORDER_TYPE_2;
    }


}
