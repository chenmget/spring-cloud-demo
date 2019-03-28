package com.iwhalecloud.retail.web.controller.order;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.oms.dto.resquest.TempCartReq;
import com.iwhalecloud.retail.oms.service.TempCartService;
import com.iwhalecloud.retail.web.annotation.GoodsRankingsAnnotation;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.controller.BaseController;
import com.iwhalecloud.retail.web.interceptor.MemberContext;
import com.iwhalecloud.retail.order.dto.CartItemDTO;
import com.iwhalecloud.retail.order.dto.response.CartListResp;
import com.iwhalecloud.retail.order.dto.resquest.*;
import com.iwhalecloud.retail.order.service.CartService;
import com.iwhalecloud.retail.web.utils.FastDFSImgStrJoinUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author pjq
 * @date 2018/10/8
 */
@RestController
@RequestMapping("/api/cart")
@CrossOrigin
@Slf4j
public class CartController extends BaseController {

    @Reference
    private CartService cartService;
    @Reference
    private TempCartService tempCartService;

    @Value("${fdfs.showUrl}")
    private String dfsShowIp;

    /**
     * 查询购物车
     * @return
     */
    @GetMapping(value="getCartList")
    @UserLoginToken
    public ResultVO getCartList(@RequestParam String partnerId){
        ListCartReqDTO req = new ListCartReqDTO();
        //获取memberId
        String memberId = MemberContext.getMemberId();
        if(StringUtils.isEmpty(memberId)){
            return failResultVO();
        }
        if(StringUtils.isEmpty(partnerId)){
            return failResultVO("partnerId must not be null");
        }
        if(StringUtils.isNotEmpty(memberId)){
            req.setMemberId(memberId);
        }
        req.setPartnerId(partnerId);
        CartListResp resp = cartService.listCart(req);
        if(null == resp){
            log.error("CartController getCartList is fail");
            return ResultVO.error(" query cart is fail");
        }
        List<CartItemDTO> cartItems = resp.getGoodsItemList();
        for(CartItemDTO cartItem:cartItems){
            if(StringUtils.isEmpty(cartItem.getDefaultImage())){
                continue;
            }
            String newImageFile = FastDFSImgStrJoinUtil.fullImageUrl(cartItem.getDefaultImage(), dfsShowIp, true);
            cartItem.setDefaultImage(newImageFile);
        }
        return ResultVO.success(resp);
    }

    /**
     * 添加购物车
     * @param req
     * @return
     */
    @RequestMapping(value="/addCart",method = RequestMethod.POST)
    @UserLoginToken
    @GoodsRankingsAnnotation
    public ResultVO addCart(@RequestBody AddCartReqDTO req){
        log.info("CartController addCart req={} ",req);
        if(null == req){
            return failResultVO();
        }
        if(StringUtils.isEmpty(req.getPartnerId())){
            return failResultVO("partnerId must not be null");
        }
        String memeberId = MemberContext.getMemberId();
        req.setMemberId(memeberId);
        if(StringUtils.isEmpty(memeberId)){
            return failResultVO("memeberId must not be null");
        }
        int resp= cartService.addCart(req);
        if (org.springframework.util.StringUtils.isEmpty(resp)) {
            return ResultVO.error();
        }
        if (resp>0) {
            return ResultVO.success();
        } else {
            return ResultVO.error();
        }
    }
    @RequestMapping(value="/batchAddCart",method = RequestMethod.POST)
    @UserLoginToken
    @GoodsRankingsAnnotation
    public ResultVO batchAddCart(@RequestBody CartBatchAddReq req){
        log.info("CartController batchAddCart req={} ",req);
        if(null == req){
            return failResultVO();
        }
        if(CollectionUtils.isEmpty(req.getCartAddReqList())){
            return failResultVO();
        }
        List<AddCartReqDTO> cartAddReqList = req.getCartAddReqList();
        for(AddCartReqDTO cartAddReq : cartAddReqList){
            String memeberId = MemberContext.getMemberId();
            cartAddReq.setMemberId(memeberId);
        }
        int resp = cartService.batchAddCart(req);
        if (resp>0) {
            return ResultVO.success();
        } else {
            return ResultVO.error();
        }
    }
    /**
     * 修改购物车数量
     * @param req
     * @return
     */
    @RequestMapping(value="/updateCartNum",method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO<Boolean> updateCartNum(@RequestBody UpdateCartReqDTO req){
        log.info("CartController updateCartNum req={} ",req);
        if(StringUtils.isEmpty(req.getPartnerId())){
            return failResultVO("partnerId must not be null");
        }
        if(StringUtils.isEmpty(req.getCartId())){
            return failResultVO("cartId must not be null");
        }
        int resp = cartService.updateCart(req);
        if (resp>0) {
            return ResultVO.success();
        } else {
            return ResultVO.error();
        }
    }

    /**
     * 删除购物车
     * req
     * @return
     */
    @RequestMapping(value="/deleteCart",method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO<Boolean> deleteCart(@RequestBody DeleteCartReqDTO req){
        log.info("CartController deleteCart req={} ",req);
        if(CollectionUtils.isEmpty(req.getCartIds())){
            return ResultVO.error("cartIds must not be null");
        }
        String memeberId = MemberContext.getMemberId();
        req.setMemberId(memeberId);
        int resp = cartService.deleteCart(req);
        if (resp>0) {
            return ResultVO.success();
        } else {
            return ResultVO.error();
        }
    }

    @GetMapping(value="/cartReverseSelection")
    @UserLoginToken
    public ResultVO<Boolean> cartReverseSelection(@RequestParam String partnerId,@RequestParam String checkedFlag){
        CartReverseSelectionReq req = new CartReverseSelectionReq();
        String memberId = MemberContext.getMemberId();
        req.setMemberId(memberId);
        req.setCheckedFlag(checkedFlag);
        if(StringUtils.isEmpty(partnerId)){
            return failResultVO("partnerId must not be null");
        }
        req.setPartnerId(partnerId);
        int resp = cartService.cartReverseSelection(req);
        if (resp>0) {
            return ResultVO.success();
        } else {
            return ResultVO.error();
        }
    }
    @RequestMapping(value="/tempAddCart",method = RequestMethod.POST)
    public ResultVO<Boolean> tempAddCart(@RequestBody TempCartReq req){
        if(null == req){
            return ResultVO.error("req  is null");
        }
        if(StringUtils.isEmpty(req.getKey())){
            return ResultVO.error("key is null");
        }
        if(StringUtils.isEmpty(req.getValue())){
            return ResultVO.error("value is null");
        }
        tempCartService.tempAddCart(req.getKey(),req.getValue());
        return ResultVO.success(true);
    }

    @GetMapping(value="/getTempCart")
    public ResultVO<String> getTempCart(@RequestParam String key){
        if(StringUtils.isEmpty(key)){
            return ResultVO.error("key is null");
        }
        Object result = tempCartService.getTempCart(key);
        if(null == result){
            return ResultVO.error("result is null");
        }
        return ResultVO.success(result.toString());
    }

    @GetMapping(value="/selectTempCart")
    public ResultVO<Boolean> selectTempCart(@RequestParam String key){
        if(StringUtils.isEmpty(key)){
            return ResultVO.error("key is null");
        }
        Boolean result = tempCartService.selectTempCart(key);
        return ResultVO.success(result);
    }

}
