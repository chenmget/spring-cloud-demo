package com.iwhalecloud.retail.order.ropservice.impl;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.order.consts.OmsCommonConsts;
import com.iwhalecloud.retail.order.consts.OrderManagerConsts;
import com.iwhalecloud.retail.order.consts.order.OrderAllStatus;
import com.iwhalecloud.retail.order.dbservice.ContractOrderService;
import com.iwhalecloud.retail.order.dbservice.OrderInitVerifyService;
import com.iwhalecloud.retail.order.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order.dto.model.OrderCollectionModel;
import com.iwhalecloud.retail.order.dto.resquest.BuilderOrderRequest;
import com.iwhalecloud.retail.order.manager.OrderCoreManager;
import com.iwhalecloud.retail.order.model.BuilderOrderDTO;
import com.iwhalecloud.retail.order.model.DeliverEntity;
import com.iwhalecloud.retail.order.model.OrderEntity;
import com.iwhalecloud.retail.order.model.OrderUpdateAttrEntity;
import com.iwhalecloud.retail.order.ropservice.BuilderOrderManagerService;
import com.iwhalecloud.retail.order.dbservice.BuilderOrderExpandService;
import com.iwhalecloud.retail.order.dbservice.BuilderOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class BuilderOrderManagerServiceImpl implements BuilderOrderManagerService {

    @Autowired
    private OrderCoreManager orderCoreManager;

    @Autowired
    private BuilderOrderService builderOrderService;
    @Autowired
    private BuilderOrderExpandService builderOrderExpandService;


    @Autowired
    private OrderInitVerifyService orderInitVerifyService;


    @Autowired
    private ContractOrderService contractOrderService;


    @Override
    public CommonResultResp builderOrderLocal(BuilderOrderRequest request) {
        CommonResultResp<List<BuilderOrderDTO>> couponResp = new CommonResultResp();
        //初始化订单
        CommonResultResp resp = builderOrderService.builderOrder(request);
        if (!resp.isSuccess()) {
            return resp;
        }
        OrderEntity orderEntityDTO = new OrderEntity();
        BeanUtils.copyProperties(resp.getResultData(), orderEntityDTO);
        log.info("gs_10010_initOrder={}", JSON.toJSONString(orderEntityDTO));

        //加入购物车（立即购买）
        resp = builderOrderService.addCartLJGM(request);
        if (!resp.isSuccess()) {
            return resp;
        }

        //构建子订单项
        resp = builderOrderService.builderOrderItem(request);
        if (!resp.isSuccess()) {
            return resp;
        }
        List<BuilderOrderDTO> orderItem = JSON.parseArray(JSON.toJSONString(resp.getResultData()), BuilderOrderDTO.class);
        log.info("::" + orderItem);
        //初始化物流信息
        resp = builderOrderService.builderDelivery(request);
        if (!resp.isSuccess()) {
            return resp;
        }
        DeliverEntity deliverEntityDTO = new DeliverEntity();
        BeanUtils.copyProperties(resp.getResultData(), deliverEntityDTO);

        for (BuilderOrderDTO order : orderItem) {
            OrderEntity ord = new OrderEntity();
            BeanUtils.copyProperties(orderEntityDTO, ord);
            order.setOrder(ord);
            DeliverEntity deli = new DeliverEntity();
            BeanUtils.copyProperties(deliverEntityDTO, deli);
            order.setDeliver(deli);
        }

        builderOrderService.crateOrderData(orderItem);


        /**
         * 使用优惠券
         */
        couponResp = builderOrderExpandService.useCouponByEquivalence(orderItem, request);
        if (couponResp.isSuccess()) {
            request.setIsUseCoupon(1);
            orderItem = couponResp.getResultData();
        }

        try {
            resp = builderOrderService.saveOrder(orderItem);
            builderOrderService.builderFinish(request, orderItem);
            if (resp.isSuccess()) {

                List<OrderCollectionModel> respList = new ArrayList<>();
                for (BuilderOrderDTO b : orderItem) {
                    OrderCollectionModel r = new OrderCollectionModel();
                    BeanUtils.copyProperties(b.getOrder(), r);
                    r.setOrder_id(b.getOrder().getOrderId());
                    respList.add(r);
                }
                resp.setResultData(respList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg(e.getMessage());
            log.info("gs_builder_error:e{}", e.getMessage());
            if (OrderManagerConsts.ORDER_SOURCE_TYPE_LJGM.equals(request.getSourceType())) {
                builderOrderService.builderFinish(request, orderItem);
            }
        }
        return resp;
    }

    @Override
    public CommonResultResp orderInitInfo(List<OrderUpdateAttrEntity> orderStatusList,BuilderOrderRequest request) {

        CommonResultResp resp=new CommonResultResp();

        //订单初始化
        for (OrderUpdateAttrEntity dto : orderStatusList) {
            orderInitVerifyService.initOrderInfo(request, dto);
        }
        //合约机信息录入
        if (request.getBindType().equals(OrderManagerConsts.ORDER_BIND_TYPE_1)) {
            contractOrderService.insertContractInfo(request.getContractInfo(), orderStatusList.get(0));
        }

        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }

    @Override
    public void orderException(List<String> orderList, BuilderOrderRequest request) {
        for (String orderID : orderList) {
            OrderUpdateAttrEntity dto = new OrderUpdateAttrEntity();
            dto.setOrderId(orderID);
            dto.setStatus(OrderAllStatus.ORDER_STATUS_99.getCode());
            orderCoreManager.updateOrderStatus(dto);
        }

        //回滚优惠券
        if (request.getIsUseCoupon() != null && request.getIsUseCoupon() == 1) {
            builderOrderExpandService.RollbackCouponByEquivalence(request.getCouponOrderId(), request);
        }

    }

    @Override
    public CommonResultResp orderSuccess(List<String> order, BuilderOrderRequest request) {
        /**
         * 更新库存
         */
        CommonResultResp resp = builderOrderExpandService.updateGoodsNum(order, request);

        return resp;
    }


}
