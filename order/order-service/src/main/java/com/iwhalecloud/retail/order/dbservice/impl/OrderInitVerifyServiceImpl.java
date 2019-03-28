package com.iwhalecloud.retail.order.dbservice.impl;


import com.iwhalecloud.retail.order.consts.OmsCommonConsts;
import com.iwhalecloud.retail.order.consts.order.ActionFlowType;
import com.iwhalecloud.retail.order.consts.order.OrderAllStatus;
import com.iwhalecloud.retail.order.consts.order.OrderPayType;
import com.iwhalecloud.retail.order.consts.order.OrderShipType;
import com.iwhalecloud.retail.order.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order.dto.model.OrderModel;
import com.iwhalecloud.retail.order.dto.resquest.BuilderOrderRequest;
import com.iwhalecloud.retail.order.dto.resquest.OrderInfoEntryRequest;
import com.iwhalecloud.retail.order.manager.OrderCoreManager;
import com.iwhalecloud.retail.order.manager.OrderManager;
import com.iwhalecloud.retail.order.manager.OrderZFlowManager;
import com.iwhalecloud.retail.order.model.OrderFlowEntity;
import com.iwhalecloud.retail.order.model.OrderUpdateAttrEntity;
import com.iwhalecloud.retail.order.reference.GoodsManagerReference;
import com.iwhalecloud.retail.order.dbservice.OrderInitVerifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Slf4j
@Service
public class OrderInitVerifyServiceImpl implements OrderInitVerifyService {

    @Autowired
    private OrderCoreManager orderCoreManager;


    @Autowired
    private OrderZFlowManager orderZFlowManager;

    @Autowired
    private GoodsManagerReference goodsManagerReference;

    @Autowired
    private OrderManager orderManager;


    @Override
    @Transactional
    public CommonResultResp initOrderInfo(BuilderOrderRequest request, OrderUpdateAttrEntity order) {
        CommonResultResp commonResultResp = new CommonResultResp();

        OrderModel orderModel=orderManager.selectOrder(order.getOrderId());
        if(orderModel==null){
            commonResultResp.setResultMsg("下单失败");
            commonResultResp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            return commonResultResp;
        }

        /**
         * 添加供货商信息
         */
        OrderFlowEntity coreDTO = goodsManagerReference.selectGoodsInfoByOrder(order.getOrderId());
        if (!CollectionUtils.isEmpty(coreDTO.getItem()) && !StringUtils.isEmpty(coreDTO.getItem().get(0).getShipUserId())) {
            String supperId = coreDTO.getItem().get(0).getShipUserId() + ",";
            order.setShipUserId(supperId.substring(0, supperId.indexOf(",")));
        }

        /**
         * 订单状态初始化
         */
        OrderPayType orderShipType=OrderPayType.matchOpCode(order.getPayType());
        switch (orderShipType) {
            case ORDER_PAY_CODE_2: //货到付款
                order.setPayStatus("0");
                order.setStatus(OrderAllStatus.ORDER_STATUS_4.getCode());
                break;
            case ORDER_PAY_CODE_3: //线下支付
                order.setPayStatus("1");
                order.setStatus(OrderAllStatus.ORDER_STATUS_4.getCode());
                order.setPaymoney(Double.valueOf(orderModel.getOrderAmount()));
                break;
            default: //默认待付款
                order.setPayStatus("0");
                order.setStatus(OrderAllStatus.ORDER_STATUS_2.getCode());
                break;
        }
        //如果是配送方式是客户自提，需要录入手机串码
        OrderShipType shipType=OrderShipType.matchOpCode(request.getShipType());
        switch (shipType) {
            case ORDER_SHIP_TYPE_2:
                order.setGetGoodsCode((int)((Math.random()*9+1)*100000)+"");
                break;
            default:
                break;
        }
        orderCoreManager.initOrderStatus(order);


        //初始化订单流程
        orderZFlowManager.insertFlowList(request, order.getOrderId());

        commonResultResp.setResultMsg("成功");
        commonResultResp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return commonResultResp;
    }

    @Override
    public CommonResultResp checkOrderStatus(OrderUpdateAttrEntity dto) {
        CommonResultResp resp = new CommonResultResp();
        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        if (StringUtils.isEmpty(dto.getFlowType()) || StringUtils.isEmpty(dto.getFlowType())) {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg("订单号和flowType不能为空");
            return resp;
        }
        OrderUpdateAttrEntity order = orderCoreManager.selectOrderStatus(dto);
        if (order == null) {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg("未查询到订单");
            return resp;
        }
        ActionFlowType actionFlowType=ActionFlowType.matchOpCode(dto.getFlowType());
        switch (actionFlowType) {
            case ORDER_HANDLER_FH: //发货
                if (OrderAllStatus.ORDER_STATUS_4.getCode().equals(order.getStatus())) {
                    resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
                } else {
                    resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                    resp.setResultMsg("只有待发货状态才能发货");
                }

                break;
            case ORDER_HANDLER_PJ: //评价
                break;
            case ORDER_HANDLER_QX: //取消
                boolean boo1 = OrderAllStatus.ORDER_STATUS_2.getCode().equals(order.getStatus());//待支付
                boolean boo2 = OrderAllStatus.ORDER_STATUS_4.getCode().equals(order.getStatus())  //待发货
                        && OrderPayType.ORDER_PAY_CODE_2.getCode().equals(order.getPayType())  //货到付款
                        && "0".equals(order.getPayStatus());//待支付
                if (boo1 || boo2) {
                    resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
                } else {
                    resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                    resp.setResultMsg("只有未付款，货到付款待发货的订单能取消");
                }
                break;
            case ORDER_HANDLER_ZF: //支付
                break;
            case ORDER_HANDLER_SH: //收货
                if (OrderAllStatus.ORDER_STATUS_5.getCode().equals(order.getStatus())) {
                    resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
                } else {
                    resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                    resp.setResultMsg("只有待收货状态才能确认收货");
                }
                break;
            case ORDER_HANDLER_SC: //删除
                break;
            default:
                resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                resp.setResultMsg("操作类型flowType有误,请检查");
                break;
        }

        return resp;
    }


    @Override
    public CommonResultResp checkMobileString(OrderInfoEntryRequest request) {
        CommonResultResp resp=new CommonResultResp();
        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }

}
