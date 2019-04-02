package com.iwhalecloud.retail.order2b.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.busiservice.BuilderCartService;
import com.iwhalecloud.retail.order2b.consts.CartStatus;
import com.iwhalecloud.retail.order2b.consts.ResultCode;
import com.iwhalecloud.retail.order2b.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order2b.dto.model.cart.CartItemDTO;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderGoodsItemDTO;
import com.iwhalecloud.retail.order2b.dto.response.CartListResp;
import com.iwhalecloud.retail.order2b.dto.resquest.cart.*;
import com.iwhalecloud.retail.order2b.dto.resquest.order.PreCreateOrderReq;
import com.iwhalecloud.retail.order2b.entity.Cart;
import com.iwhalecloud.retail.order2b.manager.CartManager;
import com.iwhalecloud.retail.order2b.model.CartItemModel;
import com.iwhalecloud.retail.order2b.reference.GoodsManagerReference;
import com.iwhalecloud.retail.order2b.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author My
 * @Date 2018/11/26
 **/
@Service
@Slf4j
public class CartServiceImpl implements CartService {
    @Autowired
    private CartManager cartManager;

    @Autowired
    private BuilderCartService builderCartService;

    @Autowired
    private GoodsManagerReference goodsManagerReference;


    @Override
    public ResultVO<Boolean> addCart(AddCartReq req) {

        if (req.getNum() == null) {
            return ResultVO.error("数量不能为空");
        }

        PreCreateOrderReq createOrderReq = new PreCreateOrderReq();
        createOrderReq.setUserId(req.getUserId());
        OrderGoodsItemDTO dto = new OrderGoodsItemDTO();
        dto.setGoodsId(req.getGoodsId());
        dto.setProductId(req.getProductId());
        dto.setNum(Integer.parseInt(req.getNum() + ""));
        CartItemModel cartItemModel = goodsManagerReference.builderCart(createOrderReq, dto);

        if (cartItemModel == null) {
            return ResultVO.error("查询不到商品或产品为空");
        }
        req.setPrice(cartItemModel.getPrice());
        req.setName(cartItemModel.getName());

        /**
         * 规则校验
         */
        CommonResultResp resultResp = builderCartService.goodsRulesCheck(req);
        if (resultResp.isFailure()) {
            return ResultVO.error(resultResp.getResultMsg());
        }
        int num = cartManager.add(req);
        if (num < 1) {
            return ResultVO.error("新增失败");
        }
        return ResultVO.success(true);
    }

    @Override
    public ResultVO updateCart(UpdateCartReq req) {

        Cart cart = cartManager.getCartId(req);
        if (cart == null) {
            return ResultVO.error("未查询到购物车");
        }
        AddCartReq checkReq = new AddCartReq();
        BeanUtils.copyProperties(cart, checkReq);
        checkReq.setNum(checkReq.getNum()+req.getNum());

        if (UpdateCartReq.ACTION_NUM.equals(req.getAction())) {

            /**
             * 规则校验
             */
            CommonResultResp resultResp = builderCartService.goodsRulesCheck(checkReq);
            if (resultResp.isFailure()) {
                return ResultVO.error(resultResp.getResultMsg());
            }

            cartManager.updateNum(req);
        } else if (UpdateCartReq.ACTION_CHECKED_FLAG.equals(req.getAction())) {
            cartManager.updateCheckedFlag(req);
        }

        return ResultVO.success();
    }

    @Override
    public ResultVO<Boolean> deleteCart(DeleteCartReq req) {
        return builderCartService.deleteCart(req);
    }

    @Override
    public int cartReverseSelection(CartReverseSelectionReq req) {
        Integer checkedFlag = 0;
        if (CartStatus.CHECKED_FLAG_1.equals(req.getCheckedFlag())) {
            checkedFlag = 1;
        }
        try {
            req.setCheckedStatus(checkedFlag);
            return cartManager.cartReverseSelection(req);
        } catch (Exception e) {
            log.error("CartServiceImpl cartReverseSelection Exception={}", e);
            return ResultCode.RESULE_CODE_FAIL;
        }
    }

    @Override
    public ResultVO<List<CartListResp>> listCart(ListCartReq req) {
        CartListResp resp = new CartListResp();
        List<CartListResp> cartListResps = Lists.newArrayList();
        try {
            List<CartItemDTO> cartList = cartManager.listAllGoods(req);
            log.info("CartServiceImpl listCart listAllGoods--cartList={} ", JSON.toJSONString(cartList));
            if (CollectionUtils.isEmpty(cartList)) {
                return ResultVO.success();
            }
            resp.setGoodsItemList(cartList);
            cartListResps = builderCartService.pkgGoodsInf(cartList);
            if (CollectionUtils.isEmpty(cartListResps)) {
                return ResultVO.error("查询产品为空");
            }
        } catch (Exception e) {
            log.error("CartServiceImpl listCart Exception={}", e);
            return ResultVO.error("运行时异常");
        }
        return ResultVO.success(cartListResps);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int batchAddCart(CartBatchAddReq req) {
        List<AddCartReq> reqList = req.getCartAddReqList();
        int num = 1;
        for (AddCartReq addCartReqDTO : reqList) {
            ResultVO<Boolean> resultVO = addCart(addCartReqDTO);
            if (null == resultVO || ResultCodeEnum.ERROR.getCode().equals(resultVO.getResultCode())) {
                return ResultCode.RESULE_CODE_FAIL;
            }
        }
        return num;
    }

}
