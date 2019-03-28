package com.iwhalecloud.retail.order.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.order.consts.ResultCode;
import com.iwhalecloud.retail.order.dto.CartItemDTO;
import com.iwhalecloud.retail.order.dto.resquest.AddCartReqDTO;
import com.iwhalecloud.retail.order.dto.resquest.DeleteCartReqDTO;
import com.iwhalecloud.retail.order.dto.resquest.ListCartReqDTO;
import com.iwhalecloud.retail.order.dto.resquest.UpdateCartReqDTO;
import com.iwhalecloud.retail.order.entity.Cart;
import com.iwhalecloud.retail.order.mapper.CartMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${fdfs.show.url}")
    private String dfsShowIp;

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public int add(AddCartReqDTO req) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("product_id", req.getProductId());
        queryWrapper.eq("member_id",req.getMemberId());
        queryWrapper.eq("partner_id",req.getPartnerId());
        List<Cart> list = cartMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(list)) {
           for (Cart cart:list){
               QueryWrapper query = new QueryWrapper();
               query.eq("product_id",req.getProductId());
               query.eq("partner_id",cart.getPartnerId());
               if(!cart.getItemtype().equals(req.getItemType())){
                   cart.setItemtype(req.getItemType());
               }
               cart.setNum(cart.getNum()+req.getNum());
               cart.setIsCheck("1");
               return cartMapper.update(cart,query);
           }
        } else {
            Cart cart = new Cart();
            BeanUtils.copyProperties(req, cart);
            cart.setItemtype(req.getItemType());
            return cartMapper.insert(cart);
        }
        return ResultCode.RESULE_CODE_SUCCESS;
    }

    /**
     * 立即购买
     * @param req
     */
    public int addLjgm(AddCartReqDTO req){
        Cart cart = new Cart();
        BeanUtils.copyProperties(req, cart);
        cart.setItemtype(req.getItemType());
        return cartMapper.insert(cart);
    }
    /**
     * 更新购物车
     * @param req
     */
    public int updateNum(UpdateCartReqDTO req){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("partner_id", req.getPartnerId());
        queryWrapper.eq("cart_id",req.getCartId());
        Cart cart = new Cart();
        cart.setNum(Long.valueOf(req.getNum()));
        return cartMapper.update(cart,queryWrapper);
    }

    /**
     * 修改勾选状态
     * @param req
     */
    public int updateCheckedFlag(UpdateCartReqDTO req) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("cart_id", req.getCartId());
        Cart cart = new Cart();
        cart.setIsChecked(req.getCheckedFlag());
        return cartMapper.update(cart, queryWrapper);

    }
    /**
     * 清空用户购物车（可登陆，可离线）
     */
    public int cleanByMemberOrSession(DeleteCartReqDTO req){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("member_id",req.getMemberId());
        //清空已选购物车的条件
        if(StringUtils.isEmpty(req.getIsClean())){
            queryWrapper.eq("is_checked",1);
            queryWrapper.or(true);
            queryWrapper.isNull("is_checked");
        }
        return cartMapper.delete(queryWrapper);
    }

    /**
     * 删除购物车商品
     * @param req
     */
    public int delete(DeleteCartReqDTO req){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in("cart_id",req.getCartIds());
        return cartMapper.delete(queryWrapper);
    }

    /**
     * 获取购物车列表所有商品信息（调用商品能力封装订单项数据）
     * @param req
     * @return
     */
    public List<CartItemDTO> listAllGoods(ListCartReqDTO req){
        List<CartItemDTO> cartItemDTOList = cartMapper.listGoods(req);
        //pkgGoodsInf(cartItemDTOList);
        return cartItemDTOList;
    }
    /**
     *列出购物车下面的商品列表
     * @param req
     * @return
     */
    public List<CartItemDTO> listGoods(ListCartReqDTO req){
        return cartMapper.listGoods(req);
    }

    /**
     * 购物车反选
     * @param checkedFlag
     * @param memberId
     * @param partnerId
     */
    public int cartReverseSelection(Integer checkedFlag, String memberId,String partnerId){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("member_id",memberId);
        queryWrapper.eq("partner_id",partnerId);
        Cart cart = new Cart();
        cart.setIsChecked(checkedFlag);
        return cartMapper.update(cart,queryWrapper);
    }

    /**
     * 统一替换附件的前缀，多个图片用逗号，隔开。比如http://xxx.com/group1/xx.jpg替换后为group1/xx.jpg
     * @param  originalUrls 需要替换前缀的url
     * @return 替换后的字符串
     */
    public String replaceUrlPrefix(String originalUrls) {
        if (org.springframework.util.StringUtils.isEmpty(originalUrls)) {
            return "";
        }

        return originalUrls.replaceAll(dfsShowIp,"");
    }

}
