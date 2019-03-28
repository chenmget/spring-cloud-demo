package com.iwhalecloud.retail.order.service;

import com.iwhalecloud.retail.order.dto.response.CartListResp;
import com.iwhalecloud.retail.order.dto.resquest.*;

/**
 * @Author My
 * @Date 2018/11/26
 **/
public interface CartService {
    /**
     * 添加购物车
     * @param req
     */
    int addCart(AddCartReqDTO req);

    /**
     * 修改购物车
     * @param req
     */
    int updateCart(UpdateCartReqDTO req);

    /**
     * 删除购物车
     * @param req
     * @return
     */
    int deleteCart(DeleteCartReqDTO req);

    /**
     * 购物车反选
     * @param req
     * @return
     */
    int cartReverseSelection(CartReverseSelectionReq req);

    /**
     *查询购物车列表
     * @param req
     * @return
     */
    CartListResp listCart(ListCartReqDTO req);

    /**
     * 批量新增
     * @return
     */
    int batchAddCart(CartBatchAddReq req);
}
