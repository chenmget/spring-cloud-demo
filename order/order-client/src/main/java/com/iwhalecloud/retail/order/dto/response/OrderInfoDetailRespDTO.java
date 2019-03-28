package com.iwhalecloud.retail.order.dto.response;

import com.iwhalecloud.retail.order.dto.model.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderInfoDetailRespDTO implements Serializable {

    //单号
    private String orderId;
    //订单信息
    private OrderModel order;

    //订单子项
    private List<OrderItemModel> orderItems;

    //物流信息
    private List<DeliveryModel> deliveryList;

    //门店信息
    private Object partnerShop;

    //合约信息
    private ContractPInfoModel contractInfo;

    //订单过程流
    private List<OrderZFlowModel> flowList;

    //提货码
    private String getGoodsCode;

    //当前要处理的过程
    private String currentFlowType;

    //当前时间
    private String currTime;

    //结束时间
    private String flowEndTime;

    private UserMemberModel memberInfo;


}
