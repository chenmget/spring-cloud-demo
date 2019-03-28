package com.iwhalecloud.retail.order.handler;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.goods.dto.ProdGoodsDTO;
import com.iwhalecloud.retail.goods.dto.ProdProductDTO;
import com.iwhalecloud.retail.member.dto.response.MemberResp;
import com.iwhalecloud.retail.order.consts.order.OrderShipType;
import com.iwhalecloud.retail.order.dto.CartItemDTO;
import com.iwhalecloud.retail.order.dto.model.OrderGoodsItemModel;
import com.iwhalecloud.retail.order.dto.response.CartListResp;
import com.iwhalecloud.retail.order.dto.resquest.BuilderOrderRequest;
import com.iwhalecloud.retail.order.model.BuilderOrderDTO;
import com.iwhalecloud.retail.order.model.MemberAddrDTO;
import com.iwhalecloud.retail.order.model.OrderItemEntity;
import com.iwhalecloud.retail.order.reference.CartManagerReference;
import com.iwhalecloud.retail.order.reference.GoodsManagerReference;
import com.iwhalecloud.retail.order.reference.MemberInfoReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class BuilderOrderHandler {

    @Autowired
    private MemberInfoReference memberInfoReference;

    @Autowired
    private GoodsManagerReference goodsManagerReference;

    @Autowired
    private CartManagerReference cartManagerReference;

    /**
     * 会员查询
     */
    public MemberResp selectMember(String memberId) {
        return memberInfoReference.selectMember(memberId);
    }


    /**
     * 查询购物车
     */
    public CartListResp selectCart(String memberId, String Partner_id) {
        return cartManagerReference.selectCart(memberId, Partner_id);
    }

    public void cleanCheckCart(String memberId,String partnerId) {
        cartManagerReference.cleanCheckCart(memberId,partnerId);
    }

    public int addGoodsToCart(OrderGoodsItemModel goods, BuilderOrderRequest request) {
        return cartManagerReference.addGoodsToCart(goods, request);
    }

    /**
     * 查询购物车商品
     */
    public List<ProdGoodsDTO> selectGoods(List<CartItemDTO> cartItems, Map<String, List<CartItemDTO>> map) {
        List<String> goods = new ArrayList<>();
        for (CartItemDTO cartItem : cartItems) {

            //找出选中的购物车
            if ("1".equals(cartItem.getIsChecked())) {
                List<CartItemDTO> productIds = map.get(cartItem.getGoodsId());
                if (CollectionUtils.isEmpty(productIds)) {
                    productIds = new ArrayList<>();
                }
                productIds.add(cartItem);
                map.put(cartItem.getGoodsId(), productIds);
                goods.add(cartItem.getGoodsId());
            }
        }
        log.info("gs_10010_selectGoods goods={}", goods);
        if (CollectionUtils.isEmpty(goods)) {
            return null;
        }

        List<ProdGoodsDTO> goodsList = goodsManagerReference.selectGoodsByIds(goods);
        log.info("gs_10010_selectGoods goodsList{} ", JSON.toJSONString(goodsList));
        return goodsList;
    }

    /**
     * 根据供应商拆单
     */
    public List<BuilderOrderDTO> demolitionOrder(List<ProdGoodsDTO> goodsList, HashMap<String, List<CartItemDTO>> cartItems) {
        List<BuilderOrderDTO> list = new ArrayList<>();
        Map<String, List<ProdGoodsDTO>> skuIdMap = new HashMap<>();
        for (ProdGoodsDTO skuVo : goodsList) {

//            if (StringUtils.isEmpty(skuVo.getSupperId())) {
//                skuVo.setSupperId("-99");
//            }
//            String supperId = skuVo.getSupperId() + ",";  //多个供应商，取第一个
//            supperId = supperId.substring(0, supperId.indexOf(","));
            String supperId = "-99";
            List<ProdGoodsDTO> tempList = skuIdMap.get(supperId);
            if (tempList == null) {
                tempList = new ArrayList<>();
                tempList.add(skuVo);
                skuIdMap.put(supperId, tempList);
            } else {
                tempList.add(skuVo);
            }
        }

        for (String supperyId : skuIdMap.keySet()) {
            List<ProdGoodsDTO> goodsL = skuIdMap.get(supperyId);
            BuilderOrderDTO builderOrderDTO = new BuilderOrderDTO();
            List<OrderItemEntity> orderItem = new ArrayList<>();
            for (ProdGoodsDTO goods : goodsL) {
                orderItem.addAll(goodsToOrder(goods, cartItems.get(goods.getGoodsId())));
            }
            builderOrderDTO.setOrderItem(orderItem);
            list.add(builderOrderDTO);

        }
        return list;
    }

    private List<OrderItemEntity> goodsToOrder(ProdGoodsDTO goods, List<CartItemDTO> productIds) {
        List<OrderItemEntity> orderItemList = new ArrayList<>();
        List<ProdProductDTO> list = goodsManagerReference.selectProductByGoodsId(goods.getGoodsId());
        for (CartItemDTO cartItem : productIds) {
            for (ProdProductDTO p : list) {
                if (p.getProductId().equals(cartItem.getProductId())) {
                    OrderItemEntity itemEntityDTO = new OrderItemEntity();
                    itemEntityDTO.setPrice(cartItem.getPrice());
                    itemEntityDTO.setName(p.getName());
                    itemEntityDTO.setNum(cartItem.getNum());
                    itemEntityDTO.setCouponPrice(cartItem.getCoupPrice());
                    itemEntityDTO.setSpecId(p.getProductId());
                    //商品类型（1：手机）
                    itemEntityDTO.setItemType(String.valueOf(cartItem.getItemtype()));
                    itemEntityDTO.setGoodsId(p.getGoodsId());
                    itemEntityDTO.setProductId(p.getProductId());
                    itemEntityDTO.setSpecDesc(p.getSpecs());
                    itemEntityDTO.setImage(cartItem.getDefaultImage());
                    //手机串码
                    itemEntityDTO.setIsChrr(goods.getIsSerialNo());
                    orderItemList.add(itemEntityDTO);
                }
            }
        }
        return orderItemList;
    }

    /**
     * 下单完成
     */
    public int builderFinish(String memberId) {
        return cartManagerReference.deleteCartByid(memberId);
    }

    public MemberAddrDTO selectAddrByID(String addr,String shipType) {
        MemberAddrDTO memberAddrDTO=null;
        OrderShipType shipType1= OrderShipType.matchOpCode(shipType);
        switch (shipType1){
            case ORDER_SHIP_TYPE_1: //快递发货
                memberAddrDTO= memberInfoReference.selectAddrByID(addr);
                break;
            case ORDER_SHIP_TYPE_2: //门店自取
                memberAddrDTO=memberInfoReference.selectAddrByShipId(addr);
                break;
            case NULL:
                break;
        }

        return memberAddrDTO;
    }

}
