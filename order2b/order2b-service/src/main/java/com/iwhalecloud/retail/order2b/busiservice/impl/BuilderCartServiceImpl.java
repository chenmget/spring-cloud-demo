package com.iwhalecloud.retail.order2b.busiservice.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.common.FileConst;
import com.iwhalecloud.retail.goods2b.dto.GoodsDetailDTO;
import com.iwhalecloud.retail.goods2b.dto.ProdFileDTO;
import com.iwhalecloud.retail.goods2b.dto.req.FileGetReq;
import com.iwhalecloud.retail.goods2b.dto.req.GoodsProductRelEditReq;
import com.iwhalecloud.retail.goods2b.service.dubbo.FileService;
import com.iwhalecloud.retail.goods2b.service.dubbo.GoodsProductRelService;
import com.iwhalecloud.retail.order2b.busiservice.BuilderCartService;
import com.iwhalecloud.retail.order2b.busiservice.CreateOrderService;
import com.iwhalecloud.retail.order2b.consts.OmsCommonConsts;
import com.iwhalecloud.retail.order2b.consts.OrderManagerConsts;
import com.iwhalecloud.retail.order2b.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order2b.dto.model.cart.CartItemDTO;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderGoodsItemDTO;
import com.iwhalecloud.retail.order2b.dto.response.CartListResp;
import com.iwhalecloud.retail.order2b.dto.resquest.cart.AddCartReq;
import com.iwhalecloud.retail.order2b.dto.resquest.cart.DeleteCartReq;
import com.iwhalecloud.retail.order2b.dto.resquest.cart.ListCartReq;
import com.iwhalecloud.retail.order2b.dto.resquest.order.CreateOrderRequest;
import com.iwhalecloud.retail.order2b.entity.Cart;
import com.iwhalecloud.retail.order2b.manager.CartManager;
import com.iwhalecloud.retail.order2b.mapper.CartMapper;
import com.iwhalecloud.retail.order2b.model.CartItemModel;
import com.iwhalecloud.retail.order2b.model.UserInfoModel;
import com.iwhalecloud.retail.order2b.reference.GoodsManagerReference;
import com.iwhalecloud.retail.order2b.reference.MemberInfoReference;
import com.iwhalecloud.retail.order2b.util.Utils;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.dto.req.SupplierGetReq;
import com.iwhalecloud.retail.partner.service.MerchantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BuilderCartServiceImpl implements BuilderCartService {

    @Autowired
    private CreateOrderService createOrderService;

    @Autowired
    private MemberInfoReference memberInfoReference;

    @Autowired
    private GoodsManagerReference goodsManagerReference;

    @Reference
    private GoodsProductRelService goodsProductRelService;

    @Reference
    private MerchantService merchantService;

    @Reference
    private FileService fileService;

    @Value("${fdfs.show.url}")
    private String dfsShowIp;

    @Autowired
    private CartManager cartManager;


    @Autowired
    private CartMapper cartMapper;

    public CommonResultResp goodsRulesCheck(AddCartReq req) {
        CommonResultResp resp = new CommonResultResp();

        UserInfoModel userInfoModel = memberInfoReference.selectUserInfo(req.getUserId());
        if (userInfoModel == null) {
            resp.setResultMsg("用户信息验证错误");
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            return resp;
        }

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("product_id", req.getProductId());
        queryWrapper.eq("user_id", req.getUserId());
        queryWrapper.eq("goods_id", req.getGoodsId());
        queryWrapper.eq("supplier_id", req.getSupplierId());
        List<Cart> list = cartMapper.selectList(queryWrapper);

        /**
         * 分货规则校验
         */

        List<CartItemModel> cartItemModels = new ArrayList<>();
        CartItemModel cartItemModel = new CartItemModel();
        cartItemModels.add(cartItemModel);
        cartItemModel.setProductId(req.getProductId());
        cartItemModel.setGoodsId(req.getGoodsId());
        if (!CollectionUtils.isEmpty(list)) {
            int num = 0;
            for (Cart cat : list) {
                if (cat.getNum() == null) {
                    cat.setNum(0L);
                }
                num = (int) (num + cat.getNum());
            }
            cartItemModel.setNum(num);
        }
        cartItemModel.setNum((int) (cartItemModel.getNum() + req.getNum()));

        /**
         * 规则校验
         */
        CreateOrderRequest createOrderRequest=new CreateOrderRequest();
        createOrderRequest.setUserId(req.getUserId());
        createOrderRequest.setUserCode(userInfoModel.getRelCode());
        createOrderRequest.setMerchantId(req.getSupplierId());
        createOrderRequest.setOrderCat(OrderManagerConsts.ORDER_CAT_0);
        resp= createOrderService.checkRule(createOrderRequest,cartItemModels);
        if (resp.isFailure()) {
            return resp;
        }

        return resp;

    }

    /**
     * 调用商品能力封装订单项数据
     *
     * @param itemList
     * @return
     */
    public List<CartListResp> pkgGoodsInf(List<CartItemDTO> itemList) {
        Map<String, List<CartItemDTO>> data = new HashMap();
        List<CartListResp> cartListResps = Lists.newLinkedList();
        data = itemList.stream().collect(Collectors.groupingBy(CartItemDTO::getSupplierId));
        for (Map.Entry<String, List<CartItemDTO>> entry : data.entrySet()) {
            CartListResp listResp = new CartListResp();
            //查询供应商名称
            SupplierGetReq supplierGetReq = new SupplierGetReq();
            supplierGetReq.setSupplierId(entry.getKey());
            String merchantId = entry.getKey();
            ResultVO<MerchantDTO> merResultVO = merchantService.getMerchantById(merchantId);
            if (null != merResultVO && merResultVO.getResultData() != null) {
                log.error("CartServiceImpl pkgGoodsInf supplier={}", JSON.toJSONString(merResultVO));
                MerchantDTO merchantDTO = merResultVO.getResultData();
                listResp.setSupplierName(merchantDTO.getMerchantName());
            }
            listResp.setSupplierId(entry.getKey());
            for (CartItemDTO item : entry.getValue()) {
                GoodsProductRelEditReq goodsProductRelEditReq = new GoodsProductRelEditReq();
                goodsProductRelEditReq.setProductId(item.getProductId());
                goodsProductRelEditReq.setGoodsId(item.getGoodsId());
                ResultVO<GoodsDetailDTO> resultVO = goodsProductRelService.qryGoodsByProductIdAndGoodsId(goodsProductRelEditReq);

                // 查询默认图片
//                String targetType = FileConst.TargetType.GOODS_TARGET.getType();
//                String defaultType = FileConst.SubType.THUMBNAILS_SUB.getType();
//                FileGetReq fileGetReq = new FileGetReq();
//                fileGetReq.setTargetId(item.getGoodsId());
//                fileGetReq.setTargetType(targetType);
//                fileGetReq.setSubType(defaultType);
                FileGetReq fileGetReq = new FileGetReq();
                fileGetReq.setTargetId(item.getGoodsId());
                fileGetReq.setTargetType(FileConst.TargetType.GOODS_TARGET.getType());
                fileGetReq.setSubType(FileConst.SubType.THUMBNAILS_SUB.getType());
                ResultVO<List<ProdFileDTO>> files = fileService.getFile(fileGetReq);

                if (null == resultVO) {
                    log.error("CartServiceImpl pkgGoodsInf prodGoods is null");
                    return null;
                }
                GoodsDetailDTO detailDTO = resultVO.getResultData();
                if (null == detailDTO) {
                    log.error("CartServiceImpl pkgGoodsInf prodGoods is null");
                    return null;
                }
                if (StringUtils.isEmpty(detailDTO.getGoodsId())) {
                    log.error("CartServiceImpl pkgGoodsInf goodsId is null");
                    return null;
                }
                item.setMaxNum(detailDTO.getMaxNum());
                item.setMinNum(detailDTO.getMinNum());
                item.setGoodsId(detailDTO.getGoodsId());
                item.setName(detailDTO.getGoodsName() == null ? "" : detailDTO.getGoodsName());
                item.setSpecs(detailDTO.getSpecName() == null ? "" : detailDTO.getSpecName());
                if (detailDTO.getMktprice() != null) {
                    item.setMktprice(detailDTO.getMktprice());
                }
                String image_default = "";
                if (files != null) {
                    List<ProdFileDTO> fileList = files.getResultData();
                    if (null != fileList && !fileList.isEmpty()) {
                        image_default = fileList.get(0).getFileUrl();
                    }
                }
                image_default = Utils.replaceUrlPrefix(dfsShowIp,image_default);
                item.setFileUrl(image_default);
            }
            listResp.setGoodsItemList(entry.getValue());
            cartListResps.add(listResp);

        }

        return cartListResps;
    }


    public List<CartItemModel> selectCart(CreateOrderRequest request) {
        ListCartReq reqDTO=new ListCartReq();
        reqDTO.setUserId(request.getUserId());
        reqDTO.setSupplierId(request.getMerchantId());
        reqDTO.setSourceFrom(request.getSourceFrom());
        List<CartItemDTO> list = cartManager.listAllGoods(reqDTO);
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        log.info("gs_10010_selectCart req={},resp={}", JSON.toJSONString(reqDTO), JSON.toJSONString(list));
        List<CartItemModel> cartItemDTOS=new ArrayList<>();
        for (CartItemDTO c: list) {
            if("1".equals(c.getIsCheck())){
                OrderGoodsItemDTO goodsItemDTO=new OrderGoodsItemDTO();
                goodsItemDTO.setProductId(c.getProductId());
                goodsItemDTO.setGoodsId(c.getGoodsId());
                goodsItemDTO.setNum(c.getNum());
                CartItemModel cartItemModel= goodsManagerReference.builderCart(request,goodsItemDTO);
                cartItemDTOS.add(cartItemModel);
            }
        }
        return cartItemDTOS;
    }

    @Override
    public ResultVO<Boolean> deleteCart(DeleteCartReq req) {
        if (req.isClean()) {
            //通过设值来判断是否拼接SQL,清空已选购物车
            req.setIsClean(null);
            int num = cartManager.cleanByMemberOrSession(req);
            if (num < 1) {
                return ResultVO.error("清空已选购物车失败");
            }
        } else if (req.isCleanAll()) {
            //通过设值来判断是否拼接SQL，清空购物车
            req.setIsClean("Y");
            int num = cartManager.cleanByMemberOrSession(req);
            if (num < 1) {
                return ResultVO.error("清空购物车失败");
            }
        } else {
            if (org.apache.commons.collections.CollectionUtils.isEmpty(req.getCartIds())) {
                return ResultVO.error("cartId must not be null");
            }
            int num = cartManager.delete(req.getCartIds(),req.getUserId());
            if (num < 1) {
                return ResultVO.error("删除购物车失败");
            }
        }
        return ResultVO.success();
    }


}
