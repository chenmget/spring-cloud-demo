package com.iwhalecloud.retail.order2b.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.order2b.config.Order2bContext;
import com.iwhalecloud.retail.order2b.dto.base.OrderRequest;
import com.iwhalecloud.retail.order2b.dto.model.cart.CartItemDTO;
import com.iwhalecloud.retail.order2b.dto.resquest.cart.*;
import com.iwhalecloud.retail.order2b.entity.Cart;
import com.iwhalecloud.retail.order2b.mapper.CartMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author My
 * @Date 2018/11/26
 **/
@Component
public class CartManager {
    @Resource
    private CartMapper cartMapper;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int add(AddCartReq req) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("product_id", req.getProductId());
        queryWrapper.eq("user_id", req.getUserId());
        queryWrapper.eq("goods_id", req.getGoodsId());
        queryWrapper.eq("supplier_id", req.getSupplierId());
        OrderRequest sourcefrom=Order2bContext.getDubboRequest();
        if (!StringUtils.isEmpty(sourcefrom.getSourceFrom())) {
            queryWrapper.eq("source_from", sourcefrom.getSourceFrom());
        }
        List<Cart> list = cartMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(list)) {
            Cart cart=new Cart();
            cart.setNum(list.get(0).getNum() + req.getNum());
            cart.setIsCheck("1");
            cart.setUserId(null);
            return cartMapper.update(cart, queryWrapper);
        } else {
            Cart cart = new Cart();
            BeanUtils.copyProperties(req, cart);
            return cartMapper.insert(cart);
        }
    }


    /**
     * 更新购物车
     *
     * @param req
     */
    public int updateNum(UpdateCartReq req) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("cart_id", req.getCartId());
        Cart cart = new Cart();
        cart.setNum(Long.valueOf(req.getNum()));
        return cartMapper.update(cart, queryWrapper);
    }

    public Cart getCartId(UpdateCartReq req){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", req.getUserId());
        queryWrapper.eq("cart_id", req.getCartId());
        return cartMapper.selectOne(queryWrapper);
    }

    /**
     * 修改勾选状态
     *
     * @param req
     */
    public int updateCheckedFlag(UpdateCartReq req) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("cart_id", req.getCartId());
        queryWrapper.eq("user_id", req.getUserId());
        Cart cart = new Cart();
        cart.setIsCheck(String.valueOf(req.getCheckedFlag()));
//        cart.setSupplierId(req.getSupplierId());
//        cart.setSessionId(req.getSessionId());
//        cart.setUserId(req.getUserId());
//        cart.setNum(Long.valueOf(req.getNum()));
//        cartMapper.updateCheckedFlag(req);
        Integer num = cartMapper.update(cart, queryWrapper);
        return num;

    }

    /**
     * 清空用户购物车（可登陆，可离线）
     */
    public int cleanByMemberOrSession(DeleteCartReq req) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", req.getUserId());
        OrderRequest sourcefrom=Order2bContext.getDubboRequest();
        if (!StringUtils.isEmpty(sourcefrom.getSourceFrom())) {
            queryWrapper.eq("source_from", sourcefrom.getSourceFrom());
        }


        //清空已选购物车的条件
        if (StringUtils.isEmpty(req.getIsClean())) {
            queryWrapper.eq("is_check", "1");
            queryWrapper.or(true);
            queryWrapper.isNull("is_check");
        }
        return cartMapper.delete(queryWrapper);
    }

    /**
     * 删除购物车商品
     *
     * @param cartIds
     */
    public int delete(List<String> cartIds,String userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in("cart_id", cartIds);
        queryWrapper.in("user_id", userId);
        return cartMapper.delete(queryWrapper);
    }

    /**
     * 获取购物车列表所有商品信息（调用商品能力封装订单项数据）
     *
     * @param req
     * @return
     */
    public List<CartItemDTO> listAllGoods(ListCartReq req) {
        List<CartItemDTO> cartItemDTOList = cartMapper.listGoods(req);
        return cartItemDTOList;
    }


    /**
     * 购物车反选
     *
     */
    public int cartReverseSelection(CartReverseSelectionReq req) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", req.getUserId());
        queryWrapper.eq("supplier_id", req.getSupplierId());
        OrderRequest sourcefrom=Order2bContext.getDubboRequest();
        if (!StringUtils.isEmpty(sourcefrom.getSourceFrom())) {
            queryWrapper.eq("source_from", sourcefrom.getSourceFrom());
        }
        Cart cart = new Cart();
        cart.setIsCheck(String.valueOf(req.getCheckedStatus()));
        return cartMapper.update(cart, queryWrapper);
    }

}
