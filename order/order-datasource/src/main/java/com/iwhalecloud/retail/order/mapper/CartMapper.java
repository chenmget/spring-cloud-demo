package com.iwhalecloud.retail.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.order.dto.CartItemDTO;
import com.iwhalecloud.retail.order.dto.resquest.ListCartReqDTO;
import com.iwhalecloud.retail.order.dto.resquest.UpdateCartReqDTO;
import com.iwhalecloud.retail.order.entity.Cart;
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
    int updateCheckedFlag(@Param("req")UpdateCartReqDTO req);
    /**
     *列出购物车下面的商品列表
     * @param req
     * @return
     */
    List<CartItemDTO> listGoods(@Param("req") ListCartReqDTO req);

}
