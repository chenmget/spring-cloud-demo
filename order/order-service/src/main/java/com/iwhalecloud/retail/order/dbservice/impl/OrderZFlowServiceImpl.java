package com.iwhalecloud.retail.order.dbservice.impl;


import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.order.consts.OmsCommonConsts;
import com.iwhalecloud.retail.order.consts.OrderManagerConsts;
import com.iwhalecloud.retail.order.consts.order.ActionFlowType;
import com.iwhalecloud.retail.order.consts.order.OrderAllStatus;
import com.iwhalecloud.retail.order.consts.order.OrderShipType;
import com.iwhalecloud.retail.order.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order.dto.model.OrderItemModel;
import com.iwhalecloud.retail.order.dto.model.OrderModel;
import com.iwhalecloud.retail.order.dto.model.OrderZFlowModel;
import com.iwhalecloud.retail.order.dto.model.SendGoodsItemModel;
import com.iwhalecloud.retail.order.dto.resquest.OrderInfoEntryRequest;
import com.iwhalecloud.retail.order.dto.resquest.PayOrderRequest;
import com.iwhalecloud.retail.order.dto.resquest.SendGoodsRequest;
import com.iwhalecloud.retail.order.dto.resquest.UpdateOrderStatusRequest;
import com.iwhalecloud.retail.order.manager.*;
import com.iwhalecloud.retail.order.model.OrderFlowEntity;
import com.iwhalecloud.retail.order.model.OrderFlowItemEntity;
import com.iwhalecloud.retail.order.model.OrderUpdateAttrEntity;
import com.iwhalecloud.retail.order.dbservice.OrderZFlowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OrderZFlowServiceImpl implements OrderZFlowService {

    @Autowired
    private OrderCoreManager orderCoreManager;
    @Autowired
    private OrderZFlowManager orderZFlowManager;

    @Autowired
    private ContractOrderManager contractOrderManager;

    @Autowired
    private DeliveryManager deliveryManager;

    @Autowired
    private OrderManager orderManager;

    @Override
    @Transactional
    public CommonResultResp pay(PayOrderRequest request) {
        CommonResultResp resultVO = new CommonResultResp();
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);

        OrderZFlowModel zFlowDTO = new OrderZFlowModel();
        zFlowDTO.setOrderId(request.getOrderId());
        zFlowDTO.setFlowType(request.getFlowType());

        OrderUpdateAttrEntity attrEntity=new OrderUpdateAttrEntity();
        attrEntity.setOrderId(request.getOrderId());
        attrEntity.setPayCode(request.getPayCode());
        attrEntity.setPaymoney(request.getPaymoney());
        orderCoreManager.payByZXZF(attrEntity);
        orderZFlowManager.updateFlowList(zFlowDTO);

        return resultVO;
    }

    @Override
    @Transactional
    public CommonResultResp sendGoods(SendGoodsRequest request) {

        CommonResultResp resultVO = new CommonResultResp();
        OrderModel orderModel = orderManager.selectOrder(request.getOrderId());
        if (orderModel == null) {
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg("未找到订单");
            return resultVO;
        }

        OrderShipType orderShipType = OrderShipType.matchOpCode(orderModel.getShippingType());
        switch (orderShipType) {
            case NULL:
                resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                resultVO.setResultMsg("数据有误,发货类型不匹配，请联系管理员");
                return resultVO;
            case ORDER_SHIP_TYPE_2: //自提
                if (!orderModel.getGetGoodsCode().equals(request.getGetCode())) {
                    resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                    resultVO.setResultMsg("提货码错误，请重新输入");
                    return resultVO;
                }
                break;
            case ORDER_SHIP_TYPE_1: //快递发货
                deliveryManager.updateDeliveryInfo(request);
                break;
        }

        OrderUpdateAttrEntity dto = new OrderUpdateAttrEntity();
        dto.setOrderId(request.getOrderId());
        orderCoreManager.deliverGoods(dto);

        OrderZFlowModel zFlowDTO = new OrderZFlowModel();
        zFlowDTO.setOrderId(request.getOrderId());
        zFlowDTO.setFlowType(ActionFlowType.ORDER_HANDLER_FH.getCode());
        orderZFlowManager.updateFlowList(zFlowDTO);
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resultVO;
    }

    @Override
    @Transactional
    public CommonResultResp addCMRR(OrderInfoEntryRequest request) {
        CommonResultResp resultResp = new CommonResultResp();

        OrderFlowEntity coreDTO = new OrderFlowEntity();
        coreDTO.setOrderId(request.getOrderId());
        List<OrderFlowItemEntity> list = new ArrayList<>();
        coreDTO.setItem(list);
        for (SendGoodsItemModel i : request.getItem()) {
            OrderFlowItemEntity item = new OrderFlowItemEntity();
            if (StringUtils.isEmpty(i.getMobileString())) {
                continue;
            }
            item.setProductId(i.getProductId());
            item.setGoodsId(i.getGoodsId());
            item.setImei(i.getMobileString());
            list.add(item);
        }

        int result = orderCoreManager.updateOrderItemsByOrderId(coreDTO);

        log.info("gs_10010_updateOrderItemsByOrderId list={}", JSON.toJSONString(list));
        resultResp.setResultMsg("成功");
        resultResp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);

       List<OrderItemModel> orderItemModels=  orderManager.selectOrderItemsList(coreDTO.getOrderId());

       //商品所有手机串号录入之后，修改状态
        String imeis="";
        int imeisNum=0;
        for (OrderItemModel orderItem:orderItemModels) {
            if(OrderManagerConsts.CHRR_IS_1.equals(orderItem.getIsChrr())){

                //未输入串码
                if(StringUtils.isEmpty(orderItem.getImei())){
                    return resultResp;
                }
                imeisNum+=orderItem.getNum();
                if(StringUtils.isEmpty(imeis)){
                    imeis=orderItem.getImei();
                }else{
                    imeis=","+orderItem.getImei();
                }
            }
        }

        //输入的串号不完整
        if(imeis.split(",").length<imeisNum){
            return resultResp;
        }

        //        串码录入 更新
        OrderZFlowModel zFlowDTO = new OrderZFlowModel();
        zFlowDTO.setFlowType(ActionFlowType.ORDER_HANDLER_CHRR.getCode());
        zFlowDTO.setOrderId(request.getOrderId());
        orderZFlowManager.updateFlowList(zFlowDTO);
        return resultResp;
    }

    @Override
    public CommonResultResp addLZRR(OrderInfoEntryRequest request) {
        CommonResultResp resultResp = new CommonResultResp();
        OrderZFlowModel zFlowDTO = new OrderZFlowModel();
        zFlowDTO.setFlowType(ActionFlowType.ORDER_HANDLER_LZRR.getCode());
        zFlowDTO.setOrderId(request.getOrderId());
        resultResp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resultResp;
    }

    @Override
    public CommonResultResp addDDBR(OrderInfoEntryRequest dto) {
        CommonResultResp resp = new CommonResultResp();
        if (StringUtils.isEmpty(dto.getAfterOrder())) {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg("订单补录 afterOrder 不能为空");
            return resp;
        }
        contractOrderManager.updateHYJOrderId(dto);

        OrderZFlowModel zFlowDTO = new OrderZFlowModel();
        zFlowDTO.setOrderId(dto.getOrderId());
        zFlowDTO.setFlowType(dto.getFlowType());
        orderZFlowManager.updateFlowList(zFlowDTO);

        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }

    @Override
    public CommonResultResp sureReciveGoods(UpdateOrderStatusRequest request) {
        CommonResultResp resp = new CommonResultResp();
        OrderUpdateAttrEntity dto = new OrderUpdateAttrEntity();
        dto.setOrderId(request.getOrderId());
        OrderZFlowModel zFlowDTO = new OrderZFlowModel();
        zFlowDTO.setOrderId(request.getOrderId());
        zFlowDTO.setFlowType(request.getFlowType());

        orderCoreManager.collectGoods(dto);
        orderZFlowManager.updateFlowList(zFlowDTO);

        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }

    @Override
    public CommonResultResp evaluate(UpdateOrderStatusRequest request) {
        CommonResultResp resp = new CommonResultResp();
        OrderZFlowModel zFlowDTO = new OrderZFlowModel();
        zFlowDTO.setOrderId(request.getOrderId());
        zFlowDTO.setFlowType(request.getFlowType());
        OrderUpdateAttrEntity dto = new OrderUpdateAttrEntity();
        dto.setOrderId(request.getOrderId());
        dto.setStatus(OrderAllStatus.ORDER_STATUS_6.getCode());
        orderCoreManager.updateOrderStatus(dto);
        orderZFlowManager.updateFlowList(zFlowDTO);
        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }

    @Override
    public CommonResultResp cancelOrder(UpdateOrderStatusRequest request) {
        CommonResultResp resp = new CommonResultResp();
        OrderUpdateAttrEntity dto = new OrderUpdateAttrEntity();
        dto.setOrderId(request.getOrderId());
        dto.setStatus(OrderAllStatus.ORDER_STATUS_10_.getCode());
        orderCoreManager.updateOrderStatus(dto);
        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }

    @Override
    public CommonResultResp deleteOrder(UpdateOrderStatusRequest request) {
        log.info("gs_10010_deleteOrder request{}", JSON.toJSONString(request));
        OrderUpdateAttrEntity dto = new OrderUpdateAttrEntity();
        dto.setOrderId(request.getOrderId());
        orderCoreManager.deleteOrder(dto);
        CommonResultResp resultVO = new CommonResultResp();
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resultVO;
    }

    public static void main(String[] args) {
        String a="1,1";
        System.out.println(a.split(",").length);
    }
}
