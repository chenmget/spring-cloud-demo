package com.iwhalecloud.retail.order.dbservice.impl;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.goods.dto.ProdGoodsDTO;
import com.iwhalecloud.retail.member.dto.response.MemberResp;
import com.iwhalecloud.retail.order.config.WhaleCloudKeyGenerator;
import com.iwhalecloud.retail.order.consts.OmsCommonConsts;
import com.iwhalecloud.retail.order.consts.OrderManagerConsts;
import com.iwhalecloud.retail.order.dbservice.BuilderOrderService;
import com.iwhalecloud.retail.order.dto.CartItemDTO;
import com.iwhalecloud.retail.order.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order.dto.model.OrderGoodsItemModel;
import com.iwhalecloud.retail.order.dto.response.CartListResp;
import com.iwhalecloud.retail.order.dto.resquest.BuilderOrderRequest;
import com.iwhalecloud.retail.order.handler.BuilderOrderHandler;
import com.iwhalecloud.retail.order.manager.DeliveryManager;
import com.iwhalecloud.retail.order.manager.OrderManager;
import com.iwhalecloud.retail.order.model.*;
import com.iwhalecloud.retail.order.util.CurrencyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class BuilderOrderServiceImpl implements BuilderOrderService {

    @Autowired
    private BuilderOrderHandler builderOrderHandler;

    @Autowired
    private DeliveryManager deliveryManager;

    @Autowired
    private OrderManager orderManager;

    @Autowired
    private WhaleCloudKeyGenerator whaleCloudKeyGenerator;


    @Override
    public CommonResultResp<OrderEntity> builderOrder(BuilderOrderRequest request) {
        CommonResultResp<OrderEntity> resp = new CommonResultResp();
        //会员查询
        MemberResp member = builderOrderHandler.selectMember(request.getMemberId());
        if (member == null) {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg("未查询到会员");
            return resp;
        }
        // 表里面没这个字段啦（钟文龙2019.3.6）
//        request.setLvId(member.getLvId());

        OrderEntity initOrder = new OrderEntity();
        initOrder.setMemberId(member.getMemberId());
        initOrder.setShippingType(request.getShipType());
        //初始化订单数据(前端传入)
        BeanUtils.copyProperties(request, initOrder);

        MemberAddrDTO deliverEntityDTO = builderOrderHandler.selectAddrByID(request.getAddressId(),request.getShipType());
        if (deliverEntityDTO == null) {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg("未查询到收货地址或者厅店地址");
            return resp;
        }
        //收货地址
        BeanUtils.copyProperties(deliverEntityDTO, initOrder);

        resp.setResultData(initOrder);
        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }

    @Override
    public CommonResultResp<OrderEntity> addCartLJGM(BuilderOrderRequest request) {
        CommonResultResp resultVO = new CommonResultResp();
        switch (request.getSourceType()) {
            case OrderManagerConsts.ORDER_SOURCE_TYPE_LJGM:

                if (!StringUtils.isEmpty(request.getMemberId())) {
                    //购物车反选
                    builderOrderHandler.cleanCheckCart(request.getMemberId(),request.getUserId());
                }

                for (OrderGoodsItemModel goods : request.getGoodsItem()) {
                    //加入购物车
                    int count = builderOrderHandler.addGoodsToCart(goods, request);
                }
                break;
            case OrderManagerConsts.ORDER_SOURCE_TYPE_GWC:
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
    public CommonResultResp<List<BuilderOrderDTO>> builderOrderItem(BuilderOrderRequest request) {

        List<BuilderOrderDTO> list = new ArrayList<>();
        CommonResultResp resp = new CommonResultResp();
        //购物车查询
        CartListResp cartListResp = builderOrderHandler.selectCart(request.getMemberId(), request.getUserId());
        log.info("gs_10010_builderOrderItem request={},goodsItem={}",
                JSON.toJSONString(request), JSON.toJSONString(cartListResp.getGoodsItemList()));
        if (CollectionUtils.isEmpty(cartListResp.getGoodsItemList())) {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg("购物车为空");
            return resp;
        }

        //商品查询
        HashMap<String, List<CartItemDTO>> map = new HashMap<>();
        List<ProdGoodsDTO> goodsList = builderOrderHandler.selectGoods(cartListResp.getGoodsItemList(), map);
        if (CollectionUtils.isEmpty(goodsList)) {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg("未查询到商品");
            return resp;
        }

        //拆单
        List<BuilderOrderDTO> orderList = builderOrderHandler.demolitionOrder(goodsList, map);
        resp.setResultData(orderList);
        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }

    @Override
    public CommonResultResp<DeliverEntity> builderDelivery(BuilderOrderRequest request) {
        CommonResultResp resp = new CommonResultResp();
        DeliverEntity deliverEntityDTO = new DeliverEntity();
        MemberAddrDTO addrEntityDTO = builderOrderHandler.selectAddrByID(request.getAddressId(),request.getShipType());
        BeanUtils.copyProperties(addrEntityDTO, deliverEntityDTO);
        deliverEntityDTO.setShipType(request.getShipType());
        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        resp.setResultData(deliverEntityDTO);
        return resp;
    }

    @Override
    public CommonResultResp crateOrderData(List<BuilderOrderDTO> list) {

        CommonResultResp resp = new CommonResultResp();

        //生成batchid
        String batch_id = UUID.randomUUID().toString().replace("-", "");

        for (BuilderOrderDTO builder : list) {
            String orderId = whaleCloudKeyGenerator.mysqlKeySeq("");
            OrderEntity order = builder.getOrder();
            order.setOrderId(orderId);
            order.setOrderCoupon(0.0);
            order.setBatchId(batch_id);
            Integer goodsNum = 0;
            double orderAmount = 0;
            double goodsAmount = 0;
            int i = 0;
            for (OrderItemEntity item : builder.getOrderItem()) {
                goodsNum += item.getNum();

                double goodsSum = CurrencyUtil.mul(item.getPrice(), item.getNum()); //商品总价
                goodsAmount = CurrencyUtil.add(goodsAmount, goodsSum);
                orderAmount = CurrencyUtil.add(orderAmount, goodsSum);
                item.setOrderId(orderId);
                item.setLvId(order.getLvId());
                item.setCouponPrice(0.0);
            }
            order.setGoodsAmount(String.valueOf(goodsAmount));
            order.setOrderAmount(String.valueOf(orderAmount));
            order.setGoodsNum(String.valueOf(goodsNum));

            DeliverEntity delivery = builder.getDeliver();
            delivery.setMemberId(order.getMemberId());
            delivery.setOrderId(orderId);
            delivery.setType("0");
            delivery.setShipStatus("0");
        }

        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }


    @Override
    @Transactional
    public CommonResultResp saveOrder(List<BuilderOrderDTO> list) {
        CommonResultResp resp = new CommonResultResp();
        for (BuilderOrderDTO item : list) {
            orderManager.saveOrder(item.getOrder());
            orderManager.saveOrderItem(item.getOrderItem());
            deliveryManager.saveDelivery(item.getDeliver());
        }
        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }


    @Override
    public CommonResultResp builderFinish(BuilderOrderRequest request, List<BuilderOrderDTO> list) {
        CommonResultResp resp = new CommonResultResp();
        builderOrderHandler.builderFinish(request.getMemberId());

        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }

}
