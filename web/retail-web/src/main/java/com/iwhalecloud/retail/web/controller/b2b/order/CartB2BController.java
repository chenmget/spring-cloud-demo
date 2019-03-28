package com.iwhalecloud.retail.web.controller.b2b.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.model.cart.CartItemDTO;
import com.iwhalecloud.retail.order2b.dto.response.CartListResp;
import com.iwhalecloud.retail.order2b.dto.resquest.cart.*;
import com.iwhalecloud.retail.order2b.service.CartService;
import com.iwhalecloud.retail.rights.common.ResultCodeEnum;
import com.iwhalecloud.retail.web.annotation.GoodsRankingsAnnotation;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.interceptor.MemberContext;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import com.iwhalecloud.retail.web.utils.FastDFSImgStrJoinUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author My
 * @Date 2018/12/21
 **/
@RestController
@RequestMapping("/api/b2b/cart")
@CrossOrigin
@Slf4j
public class CartB2BController {

    @Reference
    private CartService cartService;

    @Value("${fdfs.showUrl}")
    private String dfsShowIp;
    /**
     * 查询购物车
     * @return
     */
    @GetMapping(value="getCartList")
    @UserLoginToken
    public ResultVO getCartList(){
        ListCartReq req = new ListCartReq();
        //获取memberId
        String userId = UserContext.getUserId();
        if(StringUtils.isEmpty(userId)){
            return ResultVO.error();
        }
        if(StringUtils.isNotEmpty(userId)){
            req.setUserId(userId);
        }
        ResultVO<List<CartListResp>> resultVO= cartService.listCart(req);
        if(ResultCodeEnum.ERROR.getCode().equals(resultVO.getResultCode())){
            return ResultVO.success(Lists.newArrayList());
        }
        List<CartListResp> listResp = resultVO.getResultData();
        if(null == listResp){
            log.info("CartController listResp is null");
            return ResultVO.success(Lists.newArrayList());
        }
        for (CartListResp resp : listResp) {
            List<CartItemDTO> cartItems = resp.getGoodsItemList();
            for (CartItemDTO cartItem : cartItems) {
                if (StringUtils.isEmpty(cartItem.getFileUrl())) {
                    continue;
                }
                String newImageFile = FastDFSImgStrJoinUtil.fullImageUrl(cartItem.getFileUrl(), dfsShowIp, true);
                cartItem.setFileUrl(newImageFile);
            }
        }
        return ResultVO.success(listResp);
    }

    /**
     * 添加购物车
     * @param req
     * @return
     */
    @RequestMapping(value="/addCart",method = RequestMethod.POST)
    @UserLoginToken
    @GoodsRankingsAnnotation
    public ResultVO addCart(@RequestBody @Valid AddCartReq req, BindingResult results){
        log.info("CartController addCart req={} ",req);
        if(results.hasErrors()){
            return ResultVO.error(results.getFieldError().getDefaultMessage());
        }
        String userId = UserContext.getUserId();
        if(StringUtils.isEmpty(userId)){
            return ResultVO.error("userId must not be null");
        }
        req.setUserId(userId);
        ResultVO resultVO= cartService.addCart(req);
        return resultVO;
    }
    /**
     * 修改购物车数量
     * @param req
     * @return
     */
    @RequestMapping(value="/updateCartNum",method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO updateCartNum(@RequestBody UpdateCartReq req){
        log.info("CartController updateCartNum req={} ",req);
        if(StringUtils.isEmpty(req.getCartId())){
            return ResultVO.error("cartId must not be null");
        }
        String userId = UserContext.getUserId();;
        req.setUserId(userId);
        return cartService.updateCart(req);
    }

    /**
     * 删除购物车
     * req
     * @return
     */
    @RequestMapping(value="/deleteCart",method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO<Boolean> deleteCart(@RequestBody DeleteCartReq req){
        log.info("CartController deleteCart req={} ",req);
        String userId = UserContext.getUserId();
        req.setUserId(userId);
        return cartService.deleteCart(req);
    }

    @GetMapping(value="/cartReverseSelection")
    @UserLoginToken
    public ResultVO<Boolean> cartReverseSelection(@RequestParam String supplierId,@RequestParam String checkedFlag){
        CartReverseSelectionReq req = new CartReverseSelectionReq();
        String userId = UserContext.getUserId();
        if(StringUtils.isEmpty(userId)){
            return ResultVO.error("userId must not be null");
        }
        req.setUserId(userId);
        req.setCheckedFlag(checkedFlag);
        if(StringUtils.isEmpty(supplierId)){
            return ResultVO.error("partnerId must not be null");
        }
        req.setSupplierId(supplierId);
        int resp = cartService.cartReverseSelection(req);
        if (resp>0) {
            return ResultVO.success();
        } else {
            return ResultVO.error();
        }
    }

    /**
     * 批量添加购物车
     * @param req
     * @return
     */
    @RequestMapping(value="/batchAddCart",method = RequestMethod.POST)
    @UserLoginToken
    @GoodsRankingsAnnotation
    public ResultVO batchAddCart(@RequestBody @Valid CartBatchAddReq req, BindingResult results){
        log.info("CartController addCart req={} ",req);
        if(results.hasErrors()) {
            return ResultVO.error(results.getFieldError().getDefaultMessage());
        }
        String userId = MemberContext.getMemberId();
        if(StringUtils.isEmpty(userId)){
            return ResultVO.error("userId must not be null");
        }
        for (AddCartReq addCartReqDTO : req.getCartAddReqList()) {
            addCartReqDTO.setUserId(userId);
        }
        int resp= cartService.batchAddCart(req);
        if (org.springframework.util.StringUtils.isEmpty(resp)) {
            return ResultVO.error();
        }
        if (resp>0) {
            return ResultVO.success();
        } else {
            return ResultVO.error();
        }
    }

}
