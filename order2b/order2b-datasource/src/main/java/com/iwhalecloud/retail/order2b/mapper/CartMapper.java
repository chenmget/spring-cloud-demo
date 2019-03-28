package com.iwhalecloud.retail.order2b.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.order2b.dto.model.cart.CartItemDTO;
import com.iwhalecloud.retail.order2b.dto.resquest.cart.ListCartReq;
import com.iwhalecloud.retail.order2b.dto.resquest.cart.UpdateCartReq;
import com.iwhalecloud.retail.order2b.entity.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author My
 * @Date 2018/11/26
 **/
@Mapper
public interface CartMapper extends BaseMapper<Cart> {

    /**
     * 更新购物车的状态
     * @param req
     */
    int updateCheckedFlag(@Param("req")UpdateCartReq req);
    /**
     *列出购物车下面的商品列表
     * @param req
     * @return
     */
    List<CartItemDTO> listGoods(@Param("req") ListCartReq req);

}
