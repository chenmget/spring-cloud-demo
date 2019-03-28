package com.iwhalecloud.retail.order2b.service;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.response.CartListResp;
import com.iwhalecloud.retail.order2b.dto.resquest.cart.*;

import java.util.List;

/**
 * @Author My
 * @Date 2018/11/26
 **/
public interface CartService {
    /**
     * 添加购物车
     * @param req
     */
    ResultVO<Boolean> addCart(AddCartReq req);

    /**
     * 修改购物车
     * @param req
     */
    ResultVO updateCart(UpdateCartReq req);

    /**
     * 删除购物车
     * @param req
     * @return
     */
    ResultVO<Boolean> deleteCart(DeleteCartReq req);

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
    ResultVO<List<CartListResp>> listCart(ListCartReq req);

    /**
     * 批量新增
     * @return
     */
    int batchAddCart(CartBatchAddReq req);


}
