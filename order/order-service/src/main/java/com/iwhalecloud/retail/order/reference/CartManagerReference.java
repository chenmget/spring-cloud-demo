package com.iwhalecloud.retail.order.reference;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.order.dto.model.OrderGoodsItemModel;
import com.iwhalecloud.retail.order.dto.response.CartListResp;
import com.iwhalecloud.retail.order.dto.resquest.*;
import com.iwhalecloud.retail.order.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CartManagerReference {

    @Reference
    private CartService cartService;

    public CartListResp selectCart(String memberId, String Partner_id) {
        ListCartReqDTO req = new ListCartReqDTO();

        req.setMemberId(memberId);
        req.setPartnerId(Partner_id);
        CartListResp resp = cartService.listCart(req);
        if(resp==null){
            return new CartListResp();
        }
        log.info("gs_10010_selectCart memberId={},Partner_id={},resp={}", memberId, Partner_id, JSON.toJSONString(resp.getGoodsItemList()));
        return resp;
    }

    public int deleteCartByid(String memberID) {

        DeleteCartReqDTO cartDeleteReq = new DeleteCartReqDTO();
        cartDeleteReq.setMemberId(memberID);
        cartDeleteReq.setClean(true);
        int count = cartService.deleteCart(cartDeleteReq);
        log.info("gs_10010_deleteCartByid req={},resp={}", JSON.toJSONString(cartDeleteReq), count);
        return count;
    }

    public void cleanCheckCart(String memberId,String partnerId) {
        CartReverseSelectionReq selectionReq = new CartReverseSelectionReq();
        selectionReq.setMemberId(memberId);
        selectionReq.setPartnerId(partnerId);
        int count = cartService.cartReverseSelection(selectionReq);
        log.info("gs_10010_cartReverseSelection selectionReq{},cartReverseSelection{}",
                JSON.toJSONString(selectionReq), count);
    }

    public int addGoodsToCart(OrderGoodsItemModel goods, BuilderOrderRequest request) {
        AddCartReqDTO cartAddReq = new AddCartReqDTO();
        cartAddReq.setProductId(goods.getProductId());
        cartAddReq.setCartUseType("1");
        cartAddReq.setPartnerId(request.getUserId());
        cartAddReq.setNum(Long.valueOf(goods.getGoodsNum()));
        cartAddReq.setMemberId(request.getMemberId());
        cartAddReq.setMemberLvId(request.getLvId());
        int count = cartService.addCart(cartAddReq);
        log.info("gs_10010_builderOrder selectionReq{},selectionResp{}",
                JSON.toJSONString(cartAddReq), count);
        return count;
    }
}
