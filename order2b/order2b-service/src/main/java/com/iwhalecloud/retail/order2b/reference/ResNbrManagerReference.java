package com.iwhalecloud.retail.order2b.reference;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.consts.OmsCommonConsts;
import com.iwhalecloud.retail.order2b.consts.order.TypeStatus;
import com.iwhalecloud.retail.order2b.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order2b.dto.model.order.SendGoodsItemDTO;
import com.iwhalecloud.retail.order2b.dto.resquest.order.PreCreateOrderReq;
import com.iwhalecloud.retail.order2b.dto.resquest.order.ReceiveGoodsReq;
import com.iwhalecloud.retail.order2b.dto.resquest.order.SendGoodsRequest;
import com.iwhalecloud.retail.order2b.entity.Order;
import com.iwhalecloud.retail.order2b.entity.OrderApply;
import com.iwhalecloud.retail.order2b.entity.OrderItem;
import com.iwhalecloud.retail.order2b.model.CartItemModel;
import com.iwhalecloud.retail.order2b.model.CreateOrderLogModel;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.dto.response.GetProductQuantityByMerchantResp;
import com.iwhalecloud.retail.warehouse.service.ResourceInstStoreService;
import com.iwhalecloud.retail.warehouse.service.SupplierResourceInstService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class ResNbrManagerReference {

    @Reference
    private SupplierResourceInstService supplierResourceInstService;
    @Reference
    private ResourceInstStoreService resourceInstStoreService;

    @Reference
    private SupplierResourceInstService resourceInstService;

    /**
     * 出库
     */
    public ResultVO<Boolean> nbrOutResource(Order order, String recCode, SendGoodsRequest request) {
        DeliveryResourceInstReq validResourceInstReq = new DeliveryResourceInstReq();
        validResourceInstReq.setSellerMerchantId(recCode); //发货对象
        validResourceInstReq.setBuyerMerchantId(order.getUserId()); //收货对象
        List<DeliveryResourceInstItem> list = new ArrayList<>();

        List<ProductQuantityItem> itemList=new ArrayList<>();
        for (SendGoodsItemDTO goodsItemDTO : request.getGoodsItemDTOList()) {
            DeliveryResourceInstItem instItem = new DeliveryResourceInstItem();
            instItem.setOrderItemId(goodsItemDTO.getItemId());
            instItem.setProductId(goodsItemDTO.getProductId());
            instItem.setMktResInstNbrs(goodsItemDTO.getResNbrList());
            list.add(instItem);

            ProductQuantityItem productQuantityItem=new ProductQuantityItem();
            productQuantityItem.setNum((long) goodsItemDTO.getResNbrList().size()*(-1));
            productQuantityItem.setProductId(goodsItemDTO.getProductId());
            itemList.add(productQuantityItem);
        }
        validResourceInstReq.setDeliveryResourceInstItemList(list);

        if(TypeStatus.TYPE_34.getCode().equals(request.getType())){

        }else{
            //修改数量
            UpdateStockReq updateStockReq=new UpdateStockReq();
            updateStockReq.setMerchantId(order.getMerchantId());
            updateStockReq.setItemList(itemList);
            validResourceInstReq.setUpdateStockReq(updateStockReq);
            validResourceInstReq.setOrderId(order.getOrderId());
        }

        ResultVO<Boolean> resultVO = supplierResourceInstService.deliveryOutResourceInst(validResourceInstReq);
        log.info("gs_10010_nbrOutResource req{},resp{}", JSON.toJSONString(validResourceInstReq), JSON.toJSONString(resultVO));
        return resultVO;
    }

    /**
     * 入库
     */
    public ResultVO<Boolean> nbrInResource(String handlerId,ReceiveGoodsReq req, List<SendGoodsItemDTO> nbrLsit) {
        DeliveryResourceInstReq validResourceInstReq = new DeliveryResourceInstReq();
        validResourceInstReq.setBuyerMerchantId(req.getUserCode()); //收货对象
        validResourceInstReq.setSellerMerchantId(handlerId); //发货对象
        List<DeliveryResourceInstItem> list = new ArrayList<>();
        for (SendGoodsItemDTO goodsItemDTO : nbrLsit) {
            DeliveryResourceInstItem instItem = new DeliveryResourceInstItem();
            instItem.setOrderItemId(goodsItemDTO.getItemId());
            instItem.setProductId(goodsItemDTO.getProductId());
            instItem.setMktResInstNbrs(goodsItemDTO.getResNbrList());
            list.add(instItem);
        }
        validResourceInstReq.setOrderId(req.getOrderId());
        validResourceInstReq.setDeliveryResourceInstItemList(list);
        ResultVO<Boolean> resultVO = supplierResourceInstService.deliveryInResourceInst(validResourceInstReq);
        log.info("gs_10010_nbrInResource req{},resp{}", JSON.toJSONString(validResourceInstReq), JSON.toJSONString(resultVO));
        return resultVO;
    }

    /**
     * 库存校验
     */
    public ResultVO<Boolean> getProductQuantityByMerchant(PreCreateOrderReq request, List<CartItemModel> list) {

        ResultVO<Boolean> booleanResultVO = new ResultVO<>();
        GetProductQuantityByMerchantReq req = new GetProductQuantityByMerchantReq();
        req.setMerchantId(request.getMerchantId());
        List<ProductQuantityItem> itemLsit = new ArrayList<>();

        for (CartItemModel cartItemModel : list) {
            ProductQuantityItem item = new ProductQuantityItem();
            item.setNum((long) cartItemModel.getNum());
            item.setProductId(cartItemModel.getProductId());
            itemLsit.add(item);
        }
        req.setItemList(itemLsit);

        ResultVO<GetProductQuantityByMerchantResp> resultVO = resourceInstStoreService.getProductQuantityByMerchant(req);

        if (resultVO.isSuccess() && resultVO.getResultData() != null && resultVO.getResultData().getInStock()) {
            booleanResultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            booleanResultVO.setResultData(true);
        } else {
            booleanResultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            booleanResultVO.setResultData(false);
        }

        booleanResultVO.setResultMsg(resultVO.getResultMsg());
        log.info("gs_10010_getProductQuantityByMerchant req{},resp{}", JSON.toJSONString(req), JSON.toJSONString(resultVO));
        return booleanResultVO;
    }

    /**
     * 更新库存
     *  state(1:更新，-1回滚)
     */
    public ResultVO updateStockReq(CreateOrderLogModel request, List<OrderItem> orderItems, int state) {
        UpdateStockReq updateStockReq = new UpdateStockReq();
        updateStockReq.setMerchantId(request.getMerchantId());
        List<ProductQuantityItem> itemLsit = new ArrayList<>();
        for (OrderItem cartItemModel : orderItems) {
            ProductQuantityItem item = new ProductQuantityItem();
            item.setNum((long) cartItemModel.getNum()*state);
            item.setProductId(cartItemModel.getProductId());
            itemLsit.add(item);
        }
        updateStockReq.setItemList(itemLsit);
        ResultVO resultVO = resourceInstStoreService.updateStock(updateStockReq);
        log.info("gs_10010_updateStockReq req{},resp{}", JSON.toJSONString(updateStockReq), JSON.toJSONString(resultVO));
        return resultVO;
    }

    /**
     * 冻结/解冻
     */

    public CommonResultResp resNbrDJ(String applyuserId, String statusCd, List<String> list,String productId) {
        CommonResultResp resp = new CommonResultResp();
        ResourceInstUpdateReq req = new ResourceInstUpdateReq();
        req.setMerchantId(applyuserId);
        req.setStatusCd(statusCd);
        req.setMktResId(productId);
        req.setMktResInstNbrs(list);
        ResultVO<Boolean> resultVO = resourceInstService.updateInstState(req);
        log.info("gs_10010_resNbrDJ req{},resp{}",JSON.toJSONString(req),JSON.toJSONString(resultVO));
        if (resultVO.isSuccess() && resultVO.getResultData()) {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        } else {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg("更新串码状态错误："+resultVO.getResultMsg());
        }
        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }

    /**
     * 退货 出库
     */
    public CommonResultResp returnGoodsOut(OrderApply apply, SendGoodsItemDTO goodsItemDTO) {
        CommonResultResp resp = new CommonResultResp();
        DeliveryResourceInstReq validResourceInstReq = new DeliveryResourceInstReq();
        validResourceInstReq.setBuyerMerchantId(apply.getApplicantId());
        List<DeliveryResourceInstItem> list = new ArrayList<>();
        DeliveryResourceInstItem instItem = new DeliveryResourceInstItem();
        instItem.setOrderItemId(goodsItemDTO.getItemId());
        instItem.setProductId(goodsItemDTO.getProductId());
        instItem.setMktResInstNbrs(goodsItemDTO.getResNbrList());
        list.add(instItem);

        validResourceInstReq.setDeliveryResourceInstItemList(list);
        validResourceInstReq.setOrderId(apply.getOrderId());
        ResultVO<Boolean> resultVO = supplierResourceInstService.backDeliveryOutResourceInst(validResourceInstReq);
        log.info("gs_10010_backDeliveryInResourceInst req{},resp{}", JSON.toJSONString(validResourceInstReq), JSON.toJSONString(resultVO));

        if (resultVO.isSuccess() && resultVO.getResultData()) {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        } else {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg("退货调用库存失败：" + resultVO.getResultMsg());
        }
        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }

    /**
     * 退货 入库
     */
    public CommonResultResp returnGoodsIn(OrderApply apply, SendGoodsItemDTO goodsItemDTO) {
        CommonResultResp resp = new CommonResultResp();
        DeliveryResourceInstReq validResourceInstReq = new DeliveryResourceInstReq();
        validResourceInstReq.setSellerMerchantId(apply.getHandlerId());
        List<DeliveryResourceInstItem> list = new ArrayList<>();
        DeliveryResourceInstItem instItem = new DeliveryResourceInstItem();
        instItem.setOrderItemId(goodsItemDTO.getItemId());
        instItem.setProductId(goodsItemDTO.getProductId());
        instItem.setMktResInstNbrs(goodsItemDTO.getResNbrList());
        list.add(instItem);

        validResourceInstReq.setBuyerMerchantId(apply.getApplicantId());
        validResourceInstReq.setDeliveryResourceInstItemList(list);
        validResourceInstReq.setOrderId(apply.getOrderId());
        ResultVO<Boolean> resultVO = supplierResourceInstService.backDeliveryInResourceInst(validResourceInstReq);
        log.info("gs_10010_backDeliveryOutResourceInst req{},resp{}", JSON.toJSONString(validResourceInstReq), JSON.toJSONString(resultVO));

        if (resultVO.isSuccess() && resultVO.getResultData()) {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        } else {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg("退货调用库存失败：" + resultVO.getResultMsg());
        }
        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }

    /**
     * 串码校验
     */
    public ResultVO resNbrValidity(DeliveryValidResourceInstReq req) {
        ResultVO resultVO = supplierResourceInstService.validResourceInst(req);
        log.info("ResNbrManagerReference.resNbrValidity req={},resp={}", JSON.toJSONString(req), JSON.toJSONString(resultVO));
        return resultVO;
    }
}
