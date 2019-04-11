package com.iwhalecloud.retail.order2b.reference;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.common.FileConst;
import com.iwhalecloud.retail.goods2b.dto.*;
import com.iwhalecloud.retail.goods2b.dto.req.*;
import com.iwhalecloud.retail.goods2b.dto.resp.ExchangeObjectGetResp;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.*;
import com.iwhalecloud.retail.order2b.consts.OmsCommonConsts;
import com.iwhalecloud.retail.order2b.consts.OrderManagerConsts;
import com.iwhalecloud.retail.order2b.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderGoodsItemDTO;
import com.iwhalecloud.retail.order2b.dto.resquest.order.PreCreateOrderReq;
import com.iwhalecloud.retail.order2b.entity.OrderItem;
import com.iwhalecloud.retail.order2b.model.CartItemModel;
import com.iwhalecloud.retail.order2b.model.CreateOrderLogModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class GoodsManagerReference {

    @Reference
    private GoodsRulesService goodsRulesService;

    @Reference
    private ProductService productService;

    @Reference
    private GoodsProductRelService goodsProductRelService;

    @Reference
    private FileService fileService;

    @Reference
    private GoodsService goodsService;

    @Reference
    private ProductBaseService productBaseService;


    public CommonResultResp checkGoodsProdRule(String userCode, String tagerType, List<CartItemModel> cartItemModels) {
        CommonResultResp resp = new CommonResultResp();
        List<GoodsRulesOperateDTO> conditionList = new ArrayList<>();
        for (CartItemModel cat : cartItemModels) {
            GoodsRulesOperateDTO goodsRulesDTO = new GoodsRulesOperateDTO();
            goodsRulesDTO.setGoodsId(cat.getGoodsId());
            goodsRulesDTO.setProductId(cat.getProductId());
            goodsRulesDTO.setMerchantType(tagerType); //商家类型
            goodsRulesDTO.setMerchantId(userCode);
            goodsRulesDTO.setDrawNum((long) cat.getNum());
            conditionList.add(goodsRulesDTO);
        }
        ProdGoodsRuleEditReq prodGoodsRuleEditReq = new ProdGoodsRuleEditReq();
        prodGoodsRuleEditReq.setGoodsRulesOperateDTOList(conditionList);
        ResultVO<Boolean> booleanResultVO = goodsRulesService.checkGoodsProdRule(prodGoodsRuleEditReq);
        log.info("gs_10010_checkGoodsProdRule req{},resp{}", JSON.toJSONString(conditionList), JSON.toJSONString(booleanResultVO));
        if (booleanResultVO.getResultData()) {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            return resp;
        }

        resp.setResultMsg("您的分货数量已用完，无法再购买!");
        resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
        return resp;

    }

    public CartItemModel builderCart(PreCreateOrderReq request, OrderGoodsItemDTO req) {
        //查询产品信息
        ProductGetByIdReq prodectReq=new ProductGetByIdReq();
        prodectReq.setProductId(req.getProductId());
        prodectReq.setSourceFrom(request.getSourceFrom());
        ResultVO<ProductResp> proVo = productService.getProduct(prodectReq);
        ProductResp product = proVo.getResultData();
        if (null == product) {
            return null;
        }

        /**
         * 商品产品关系
         */
        GoodsProductRelEditReq goodsProductRelEditReq = new GoodsProductRelEditReq();
        goodsProductRelEditReq.setProductId(req.getProductId());
        goodsProductRelEditReq.setGoodsId(req.getGoodsId());
        ResultVO<GoodsDetailDTO> resultVO = goodsProductRelService.qryGoodsByProductIdAndGoodsId(goodsProductRelEditReq);
        if (resultVO.getResultData() == null) {
            return null;
        }
        GoodsDetailDTO detail = resultVO.getResultData();

        if(StringUtils.isEmpty(request.getActivityId())){
            request.setOrderCat(OrderManagerConsts.ORDER_CAT_0);
        }else{
            if(StringUtils.isEmpty(detail.getIsAdvanceSale())){
                request.setOrderCat(OrderManagerConsts.ORDER_CAT_0);
            }else{
                request.setOrderCat(detail.getIsAdvanceSale());
            }
        }

        CartItemModel cartItemModel = new CartItemModel();

        cartItemModel.setGoodsId(detail.getGoodsId());
        cartItemModel.setSupplierId(request.getMerchantId());

        cartItemModel.setProductId(product.getProductId());
        cartItemModel.setUnitName(product.getUnitName());

        cartItemModel.setName(detail.getGoodsName());
        cartItemModel.setPrice(detail.getDeliveryPrice());
        cartItemModel.setSpecs(detail.getSpecName());

        cartItemModel.setSn(product.getSn());
        cartItemModel.setBrandName(detail.getBrandName());
        cartItemModel.setNum(req.getNum());


        // 查询默认图片
        FileGetReq fileGetReq = new FileGetReq();
        fileGetReq.setTargetId(req.getGoodsId());
        fileGetReq.setTargetType(FileConst.TargetType.GOODS_TARGET.getType());
        fileGetReq.setSubType(FileConst.SubType.THUMBNAILS_SUB.getType());
        ResultVO<List<ProdFileDTO>> files = fileService.getFile(fileGetReq);
        if (files != null) {
            List<ProdFileDTO> fileList = files.getResultData();
            if (null != fileList && !fileList.isEmpty()) {
                cartItemModel.setFileUrl(fileList.get(0).getFileUrl());
            }
        }

        return cartItemModel;
    }

    /**
     * 购买数量，校验
     */
    public boolean checkBuyCount(List<CartItemModel> cartItemModelList,PreCreateOrderReq request) {
        List<BuyCountCheckDTO> buyCountCheckDTOS = new ArrayList<>();
        for (CartItemModel cartItemModel : cartItemModelList) {
            BuyCountCheckDTO buyCountCheckDTO = new BuyCountCheckDTO();
            buyCountCheckDTO.setBuyCount((long) cartItemModel.getNum());
            buyCountCheckDTO.setProductId(cartItemModel.getProductId());
            buyCountCheckDTO.setGoodsId(cartItemModel.getGoodsId());
            buyCountCheckDTOS.add(buyCountCheckDTO);
        }
        GoodsProductRelEditReq goodsProductRelEditReq = new GoodsProductRelEditReq();
        goodsProductRelEditReq.setBuyCountCheckDTOList(buyCountCheckDTOS);
        goodsProductRelEditReq.setUserId(request.getUserId());
        goodsProductRelEditReq.setMerchantId(request.getUserCode());
        goodsProductRelEditReq.setIsAdvanceSale(request.getOrderCat());
        goodsProductRelEditReq.setMarketingActivityId(request.getActivityId());
        ResultVO<Boolean> resultVO = goodsProductRelService.checkBuyCount(goodsProductRelEditReq);
        log.info("gs_10010_qryMinAndMaxNum req{},resp{}", JSON.toJSONString(goodsProductRelEditReq), JSON.toJSONString(resultVO));
        if (resultVO.isSuccess() && resultVO.getResultData()) {
            return true;
        }
        return false;
    }

    /**
     * 是否需要商家确认
     */
    public boolean isMerchantConfirm(List<CartItemModel> list) {
        GoodsIdListReq req=new GoodsIdListReq();
        List<String> goodsIds = new ArrayList<>();
        for (CartItemModel cartItemModel : list) {
            goodsIds.add(cartItemModel.getGoodsId());
        }
        req.setGoodsIdList(goodsIds);
        List<GoodsDTO> resultVO = goodsService.listGoods(req).getResultData();
        if (CollectionUtils.isEmpty(resultVO)) {
            return false;
        }
        for (GoodsDTO g : resultVO) {
            //只有有商品需要商家确认，则这个单需要商家确认
            if (OrderManagerConsts.IS_MERCHANT_CONFIRM.equals(String.valueOf(g.getIsMerchantConfirm()))) {
                return true;
            }
        }
        return false;
    }


    /**
     * 更新分货规则
     * state(1:更新，-1回滚)
     */
    public ResultVO updateGoodsRule(CreateOrderLogModel request, List<OrderItem> list, int state) {

        List<GoodsRulePurchaseDTO> goodsRulePurchaseDTOs = new ArrayList<>();
        for (OrderItem orderItem : list) {
            GoodsRulePurchaseDTO goodsRulePurchaseDTO = new GoodsRulePurchaseDTO();
            goodsRulePurchaseDTO.setGoodsId(orderItem.getGoodsId());
            goodsRulePurchaseDTO.setMerchantId(request.getMerchantId());
            goodsRulePurchaseDTO.setProductId(orderItem.getProductId());
            goodsRulePurchaseDTO.setPurchasedNum((long) orderItem.getNum() * state);
            goodsRulePurchaseDTOs.add(goodsRulePurchaseDTO);
        }
        ProdGoodsRuleEditReq prodGoodsRuleEditReq = new ProdGoodsRuleEditReq();
        prodGoodsRuleEditReq.setGoodsRulePurchaseDTOs(goodsRulePurchaseDTOs);
        ResultVO resultVO = goodsRulesService.raiseMarketNumDiffer(prodGoodsRuleEditReq);
        log.info("gs_10010_updateGoodsRule,req{},resp{}", JSON.toJSONString(goodsRulePurchaseDTOs),
                JSON.toJSONString(resultVO));
        return resultVO;
    }

    /**
     * 更新商品购买量
     */
    public ResultVO updateBuyCountById(List<OrderItem> list,int state) {
        List<UpdateBuyCountByIdReq> countByIdReqs = new ArrayList<>();
        for (OrderItem orderItemModel : list) {
            UpdateBuyCountByIdReq goods = new UpdateBuyCountByIdReq();
            goods.setProductId(orderItemModel.getProductId());
            goods.setGoodsId(orderItemModel.getGoodsId());
            goods.setBuyCount(orderItemModel.getNum()*state);
            countByIdReqs.add(goods);
        }
        GoodsBuyCountByIdReq req=new GoodsBuyCountByIdReq();
        req.setUpdateBuyCountByIdReqList(countByIdReqs);
        ResultVO resultVO = goodsService.updateBuyCountById(req);
        log.info("gs_10010_updateBuyCountById,req{}.resp{}", JSON.toJSONString(req), JSON.toJSONString(resultVO));
        return resultVO;
    }

    /**
     * 换货对象获取
     */
    public CommonResultResp<String> getHHObject(String productID,String handlerId){
        CommonResultResp<String> resp=new CommonResultResp<>();
//        1是厂商，2是供应商
        ProductExchangeObjectGetReq req=new ProductExchangeObjectGetReq();
        req.setProductId(productID);
        ResultVO<ExchangeObjectGetResp> resultVO=productBaseService.getExchangeObject(req);
        log.info("gs_10010_getHHObject,req{},resp{}",JSON.toJSONString(req),JSON.toJSONString(resultVO));
        if(resultVO.getResultData()!=null){
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            if("1".equals(resultVO.getResultData().getExchangeObject())){
                if(StringUtils.isEmpty(resultVO.getResultData().getManufacturerId())){
                    resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                    resp.setResultMsg("获取换货对象为空");
                    return resp;
                }
                resp.setResultData(resultVO.getResultData().getManufacturerId());
            }else{
                resp.setResultData(handlerId);
            }
        }else{
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg("获取换货对象失败");
        }
        return resp;
    }


}
