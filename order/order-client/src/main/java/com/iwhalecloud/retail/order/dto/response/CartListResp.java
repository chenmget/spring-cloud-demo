package com.iwhalecloud.retail.order.dto.response;

import com.iwhalecloud.retail.order.dto.CartItemDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author My
 * @Date 2018/11/29
 **/
@Data
public class CartListResp implements Serializable {
    /**
     * 商品列表
     */
    private List<CartItemDTO> goodsItemList;
}
