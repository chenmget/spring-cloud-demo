package com.iwhalecloud.retail.order.dubbo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.goods.dto.ProdProductDTO;
import com.iwhalecloud.retail.goods.dto.ResultVO;
import com.iwhalecloud.retail.goods.dto.resp.QryGoodsByProductIdResp;
import com.iwhalecloud.retail.goods.service.dubbo.ProdGoodsService;
import com.iwhalecloud.retail.goods.service.dubbo.ProdGoodsSpecService;
import com.iwhalecloud.retail.goods.service.dubbo.ProdProductService;
import com.iwhalecloud.retail.order.consts.CartStatus;
import com.iwhalecloud.retail.order.consts.OmsCommonConsts;
import com.iwhalecloud.retail.order.consts.ResultCode;
import com.iwhalecloud.retail.order.dto.CartItemDTO;
import com.iwhalecloud.retail.order.dto.response.CartListResp;
import com.iwhalecloud.retail.order.dto.resquest.*;
import com.iwhalecloud.retail.order.manager.CartManager;
import com.iwhalecloud.retail.order.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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
    @Reference
    private ProdProductService prodProductService;
    @Reference
    private ProdGoodsService prodGoodsService;
    @Reference
    private ProdGoodsSpecService prodGoodsSpecService;
    @Override
    public int addCart(AddCartReqDTO req) {
        try {

            //查询产品信息
            ProdProductDTO product = prodProductService.getProduct(req.getProductId());
            if (null == product) {
                log.error("CartServiceImpl addCart productid is null");
                return ResultCode.RESULE_CODE_FAIL;
            }

            ResultVO<QryGoodsByProductIdResp> resultVO = prodGoodsService.qryGoodsByProductId(req.getProductId());
            if(null == resultVO){
                log.error("CartServiceImpl addCart 查询商品类型出错");
                return ResultCode.RESULE_CODE_FAIL;
            }
            QryGoodsByProductIdResp resp = resultVO.getResultData();
            if(null == resp){
                log.error("CartServiceImpl addCart 查询商品类型出错");
                return ResultCode.RESULE_CODE_FAIL;
            }

            if(StringUtils.isEmpty(resp.getTypeId())){
                req.setItemType(0);
            }else {
                req.setItemType(Integer.valueOf(resp.getTypeId()));
            }
            req.setPrice(product.getPrice().floatValue());
            req.setName(product.getName());
            if (null != product.getWeight()) {
                req.setWeight(product.getWeight().floatValue());
            }
            if (!StringUtils.isEmpty(req.getCartUseType()) && "1".equals(req.getCartUseType())) {
                return cartManager.addLjgm(req);
            } else {
                return cartManager.add(req);
            }
        } catch (Exception e) {
            log.error("CartServiceImpl addCart Exception={} ", e);
            return ResultCode.RESULE_CODE_FAIL;
        }
    }

    @Override
    public int updateCart(UpdateCartReqDTO req) {
        try {
            if (UpdateCartReqDTO.ACTION_NUM.equals(req.getAction())) {
                return cartManager.updateNum(req);
            } else if (UpdateCartReqDTO.ACTION_CHECKED_FLAG.equals(req.getAction())) {
                return cartManager.updateCheckedFlag(req);
            }
        } catch (Exception e) {
            log.error("CartServiceImpl updateCart Exception={} ", e);
            return ResultCode.RESULE_CODE_FAIL;
        }
        return ResultCode.RESULE_CODE_SUCCESS;
    }

    @Override
    public int deleteCart(DeleteCartReqDTO req) {
        if (null == req) {
            log.error("CartServiceImpl deleteCart cartIds={} ", req.getCartIds());
            return ResultCode.RESULE_CODE_FAIL;
        }
        try {
            if (req.isClean()) {
                //通过设值来判断是否拼接SQL,清空已选购物车
                req.setIsClean(null);
                return cartManager.cleanByMemberOrSession(req);
            } else if (req.isCleanAll()) {
                //通过设值来判断是否拼接SQL，清空购物车
                req.setIsClean("Y");
                return cartManager.cleanByMemberOrSession(req);
            } else {
                return cartManager.delete(req);
            }
        } catch (Exception e) {
            log.error("CartServiceImpl deleteCart Exception={} ", e);
            return ResultCode.RESULE_CODE_FAIL;
        }
    }

    @Override
    public int cartReverseSelection(CartReverseSelectionReq req) {
        Integer checkedFlag = 0;
        if(CartStatus.CHECKED_FLAG_1.equals(req.getCheckedFlag())){
            checkedFlag=1;
        }
        try {
            return cartManager.cartReverseSelection(checkedFlag, req.getMemberId(), req.getPartnerId());
        } catch (Exception e) {
            log.error("CartServiceImpl cartReverseSelection Exception={}", e);
            return ResultCode.RESULE_CODE_FAIL;
        }
    }

    @Override
    public CartListResp listCart(ListCartReqDTO req) {
        CartListResp resp = new CartListResp();
        try {
            //req.setItemType(CartStatus.ITEM_TYPE_0);
            List<CartItemDTO> cartList = cartManager.listAllGoods(req);
            //调用商品能力封装订单项数据
            cartList=pkgGoodsInf(cartList);
            if (CollectionUtils.isEmpty(cartList)) {
                resp.setGoodsItemList(Lists.newArrayList());
                return resp;
            }
            resp.setGoodsItemList(cartList);
        } catch (Exception e) {
            log.error("CartServiceImpl listCart Exception={}", e);
            return null;
        }
        return resp;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int batchAddCart(CartBatchAddReq req) {
        List<AddCartReqDTO> reqList = req.getCartAddReqList();
        int num = 0;
        for (AddCartReqDTO addCartReqDTO : reqList) {
            num = addCart(addCartReqDTO);
            if (num <= 0) {
                return ResultCode.RESULE_CODE_FAIL;
            }
        }
        return num;
    }

    /**
     * 调用商品能力封装订单项数据
     * @param itemList
     * @return
     */
    private List<CartItemDTO> pkgGoodsInf(List<CartItemDTO> itemList) {
        for (CartItemDTO item : itemList) {
            ResultVO<QryGoodsByProductIdResp> resultVO =prodGoodsService.qryGoodsByProductId(item.getProductId());
            if(OmsCommonConsts.RESULE_CODE_FAIL.equals(resultVO.getResultCode())){
                log.error("CartServiceImpl pkgGoodsInf errorMsg={} ",resultVO.getResultMsg());
                return null;
            }
            if(null == resultVO.getResultCode()){
                log.error("CartServiceImpl pkgGoodsInf prodGoods is null");
                return null;
            }
            QryGoodsByProductIdResp resp = resultVO.getResultData();
            if(null == resp){
                log.error("CartServiceImpl pkgGoodsInf prodGoods is null");
                return null;
            }
            if(StringUtils.isEmpty(resp.getGoodsId())){
                log.error("CartServiceImpl pkgGoodsInf goodsId is null");
                return null;
            }
            item.setGoodsId(resp.getGoodsId());
            item.setName(resp.getName() == null ? "" : resp.getName());
            item.setSpecs(resp.getSpecs() == null ? "" : resp.getSpecs());
            if (resp.getMktprice() != null) {
                item.setMktprice(resp.getMktprice());
            }
           /* if (resp.getPoint() != null) {
                item.setPoint(resp.getPoint());
            }
            item.setSn(resp.getSn() == null ? "" : resp.getSn());
            if (resp.getCtn() != null) {
                item.setCtn(resp.getCtn());
            }*/
            String image_default = "";
            image_default = resp.getDefaultImage();
            if (org.apache.commons.lang.StringUtils.isBlank(image_default)) {
                image_default = resp.getRollImage();
            }
            image_default = cartManager.replaceUrlPrefix(image_default);
            item.setDefaultImage(image_default);
            item.setWeight(resp.getWeight());
        }
        return itemList;
    }
}
