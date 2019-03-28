package com.iwhalecloud.retail.order.reference;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.goods.dto.ProdGoodsDTO;
import com.iwhalecloud.retail.goods.dto.ProdProductDTO;
import com.iwhalecloud.retail.goods.dto.ResultVO;
import com.iwhalecloud.retail.goods.dto.req.ProdGoodsListReq;
import com.iwhalecloud.retail.goods.dto.req.UpdateBuyCountByIdReq;
import com.iwhalecloud.retail.goods.service.dubbo.ProdGoodsService;
import com.iwhalecloud.retail.goods.service.dubbo.ProdProductService;
import com.iwhalecloud.retail.order.dto.model.OrderItemModel;
import com.iwhalecloud.retail.order.manager.OrderManager;
import com.iwhalecloud.retail.order.model.OrderFlowEntity;
import com.iwhalecloud.retail.order.model.OrderFlowItemEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class GoodsManagerReference {

    @Reference
    private ProdProductService prodProductService;

    @Reference
    private ProdGoodsService prodGoodsService;

    public List<ProdGoodsDTO> selectGoodsByIds(List<String> goods) {
        ProdGoodsListReq pageByIdsReq = new ProdGoodsListReq();
        pageByIdsReq.setIds(goods);

        ResultVO<List<ProdGoodsDTO>> goodsList = prodGoodsService.listGoods(pageByIdsReq);
        List<ProdGoodsDTO> goodsDTOS = goodsList.getResultData();
        return goodsDTOS;
    }

    public List<ProdProductDTO> selectProductByGoodsId(String goodsId) {
        ResultVO<List<ProdProductDTO>> resultVO = prodProductService.queryProductByGoodsId(goodsId);

        return resultVO.getResultData();
    }

    @Autowired
    private OrderManager orderManager;

    /**
     * 查询订单中商品
     */
    public OrderFlowEntity selectGoodsInfoByOrder(String orderId) {
        OrderFlowEntity coreDTO = new OrderFlowEntity();
        coreDTO.setOrderId(orderId);
        List<OrderItemModel> orderItemsList = orderManager.selectOrderItemsList(orderId);

        List<String> goods = new ArrayList<>();
        for (OrderItemModel orderi : orderItemsList) {
            goods.add(orderi.getGoodsId());
        }
        if (!CollectionUtils.isEmpty(goods)) {
            List<ProdGoodsDTO> goodsList = selectGoodsByIds(goods);
            List<OrderFlowItemEntity> itemDTOS = new ArrayList<>();
            for (ProdGoodsDTO g : goodsList) {
                OrderFlowItemEntity i = new OrderFlowItemEntity();
                i.setGoodsId(g.getGoodsId());
                i.setShipUserId(g.getSupperId());
                itemDTOS.add(i);
            }
            coreDTO.setItem(itemDTOS);

        }

        return coreDTO;

    }

    /**
     * 更新商品库存
     */
    public Object updateBuyCountById(List<String> orders) {
        List<OrderItemModel> list = orderManager.selectOrderItemsList(orders);
        List<UpdateBuyCountByIdReq> countByIdReqs = new ArrayList<>();
        for (OrderItemModel orderItemModel : list) {
            UpdateBuyCountByIdReq goods = new UpdateBuyCountByIdReq();
            goods.setGoodsId(orderItemModel.getGoodsId());
            goods.setBuyCount(orderItemModel.getNum());
            countByIdReqs.add(goods);
        }
        ResultVO resultVO = prodGoodsService.updateBuyCountById(countByIdReqs);
        return resultVO;
    }
}
