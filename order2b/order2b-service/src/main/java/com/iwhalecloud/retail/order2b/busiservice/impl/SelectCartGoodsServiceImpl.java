package com.iwhalecloud.retail.order2b.busiservice.impl;

import com.iwhalecloud.retail.order2b.busiservice.SelectCartGoodsService;
import com.iwhalecloud.retail.order2b.config.DBTableSequence;
import com.iwhalecloud.retail.order2b.config.WhaleCloudKeyGenerator;
import com.iwhalecloud.retail.order2b.consts.OmsCommonConsts;
import com.iwhalecloud.retail.order2b.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order2b.dto.model.cart.CartOrderAmountDTO;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderGoodsItemDTO;
import com.iwhalecloud.retail.order2b.dto.resquest.order.PreCreateOrderReq;
import com.iwhalecloud.retail.order2b.entity.OrderItem;
import com.iwhalecloud.retail.order2b.entity.Promotion;
import com.iwhalecloud.retail.order2b.model.BuilderOrderModel;
import com.iwhalecloud.retail.order2b.model.CartItemModel;
import com.iwhalecloud.retail.order2b.reference.GoodsManagerReference;
import com.iwhalecloud.retail.order2b.util.CurrencyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SelectCartGoodsServiceImpl implements SelectCartGoodsService {

    @Autowired
    private GoodsManagerReference goodsManagerReference;

    @Autowired
    private WhaleCloudKeyGenerator whaleCloudKeyGenerator;

    /**
     * 根据供应商拆单
     */
    @Override
    public List<BuilderOrderModel> demolitionOrderBySupperId(List<CartItemModel> cartGoodsModel) {
        List<BuilderOrderModel> list = new ArrayList<>();
        Map<String, List<CartItemModel>> skuIdMap = new HashMap<>();
        for (CartItemModel skuVo : cartGoodsModel) {
            if (StringUtils.isEmpty(skuVo.getSupplierId())) {
                skuVo.setSupplierId("-99");
            }
            String supperId = skuVo.getSupplierId() + ",";  //多个供应商，取第一个
            supperId = supperId.substring(0, supperId.indexOf(","));

            List<CartItemModel> tempList = skuIdMap.get(supperId);
            if (tempList == null) {
                tempList = new ArrayList<>();
                tempList.add(skuVo);
                skuIdMap.put(supperId, tempList);
            } else {
                tempList.add(skuVo);
            }
        }

        for (String supperyId : skuIdMap.keySet()) {
            BuilderOrderModel builderOrderDTO = builderOrderItems(skuIdMap.get(supperyId));
            list.add(builderOrderDTO);
        }
        return list;
    }

    @Override
    public BuilderOrderModel builderOrderItems(List<CartItemModel> productIds) {
        BuilderOrderModel builderOrderDTO = new BuilderOrderModel();
        builderOrderDTO.setOrderId(whaleCloudKeyGenerator.tableKeySeq(DBTableSequence.ORD_ORDER.getCode()));
        List<OrderItem> orderItem = new ArrayList<>();
        for (CartItemModel cartItem : productIds) {
            OrderItem itemEntityDTO = new OrderItem();
            itemEntityDTO.setItemId(whaleCloudKeyGenerator.tableKeySeq(DBTableSequence.ORD_ORDER_ITEMS.getCode()));
            itemEntityDTO.setOrderId(builderOrderDTO.getOrderId());
            itemEntityDTO.setPrice(cartItem.getPrice());
            itemEntityDTO.setGoodsName(cartItem.getName());
            itemEntityDTO.setNum(cartItem.getNum());
            itemEntityDTO.setCouponPrice(0.0);
            itemEntityDTO.setSpecId(cartItem.getProductId());
            //商品类型（1：手机）
            itemEntityDTO.setGoodsId(cartItem.getGoodsId());
            itemEntityDTO.setProductId(cartItem.getProductId());
            itemEntityDTO.setSpecDesc(cartItem.getSpecs());
            itemEntityDTO.setImage(cartItem.getFileUrl());
            itemEntityDTO.setProductName(cartItem.getUnitName());

            itemEntityDTO.setSn(cartItem.getSn());
            itemEntityDTO.setBrandName(cartItem.getBrandName());

            orderItem.add(itemEntityDTO);

            builderOrderDTO.setSupperId(cartItem.getSupplierId());
        }
        builderOrderDTO.setOrderItem(orderItem);
        return builderOrderDTO;
    }

    @Override
    public CommonResultResp<CartOrderAmountDTO> selectOrderAmount(BuilderOrderModel builder) {
        CommonResultResp<CartOrderAmountDTO> resp = new CommonResultResp<>();
        if (CollectionUtils.isEmpty(builder.getOrderItem())) {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg("购物车商品不能为空");
            return resp;
        }

        //订单项减免
        if (!CollectionUtils.isEmpty(builder.getPromotionList())) {
            for (OrderItem orderItem : builder.getOrderItem()) {
                double couponAmount = 0.0;
                for (Promotion promotion : builder.getPromotionList()) {
                    if (promotion.getProductId().equals(orderItem.getProductId())) {
                        if (promotion.getDiscount() != null) {
                            couponAmount = CurrencyUtil.add(couponAmount, Double.parseDouble(promotion.getDiscount()));
                        }
                    }
                    orderItem.setCouponPrice(couponAmount);
                }
            }
        }

        CartOrderAmountDTO order = new CartOrderAmountDTO();
        Integer goodsNum = 0;
        double orderAmount = 0;
        double goodsAmount = 0;
        double couponAmount = 0;
        for (OrderItem item : builder.getOrderItem()) {
            goodsNum += item.getNum();

            double goodsSum = CurrencyUtil.mul(item.getPrice(), item.getNum()); //商品总价
            goodsAmount = CurrencyUtil.add(goodsAmount, goodsSum);
            orderAmount = CurrencyUtil.add(orderAmount, goodsSum);
            couponAmount = CurrencyUtil.add(couponAmount, item.getCouponPrice());
        }
        order.setGoodsAmount(goodsAmount);
        order.setOrderAmount(CurrencyUtil.sub(orderAmount, couponAmount));
        order.setCouponAmount(couponAmount);
        order.setGoodsNum(goodsNum);

        if(order.getOrderAmount()<0){
            order.setOrderAmount(0.0);
        }

        if (builder.getAdvanceOrder() != null) {
            order.setAdvanceAmount(builder.getAdvanceOrder().getAdvanceAmount());
            order.setRestAmount(builder.getAdvanceOrder().getRestAmount());
        }

        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        resp.setResultData(order);
        return resp;
    }

    @Override
    public CommonResultResp<List<CartItemModel>> builderCartData(PreCreateOrderReq request) {
        CommonResultResp resp = new CommonResultResp();

        List<CartItemModel> list = new ArrayList<>();

        for (OrderGoodsItemDTO goodsItemDTO : request.getGoodsItem()) {
            CartItemModel cartItemModel = goodsManagerReference.builderCart(request, goodsItemDTO);

            if (cartItemModel == null) {
                resp.setResultMsg("未查询到商品或者产品:");
                resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                return resp;
            }

            list.add(cartItemModel);
        }
        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        resp.setResultData(list);

        return resp;
    }

}
