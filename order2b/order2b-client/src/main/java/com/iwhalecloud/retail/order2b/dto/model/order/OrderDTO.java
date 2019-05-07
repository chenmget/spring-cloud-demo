package com.iwhalecloud.retail.order2b.dto.model.order;

import com.iwhalecloud.retail.order2b.consts.order.*;
import com.iwhalecloud.retail.order2b.dto.base.SelectModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

import static com.iwhalecloud.retail.order2b.consts.order.TypeStatus.PAY;

@Data
public class OrderDTO extends SelectModel implements Serializable {

    @ApiModelProperty("订单号")
    private String orderId;
    private String sn;
    @ApiModelProperty("订单类型标识")
    private String orderType;
    @ApiModelProperty("订单类型名称")
    private String orderTypeName;
    @ApiModelProperty("供应商id")
    private String supplierId;
    @ApiModelProperty("买家id")
    private String userId;
    @ApiModelProperty("买家名称")
    private String userName;
    @ApiModelProperty("商家id")
    private String merchantId;
    @ApiModelProperty("订单状态")
    private String status;
    @ApiModelProperty("订单状态名称")
    private String statusName;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("供应商名称")
    private String supplierName;
    @ApiModelProperty("创建用户id")
    private String createUserId;
    @ApiModelProperty("付款类型")
    private String paymentId;
    @ApiModelProperty("付款名称")
    private String paymentName;
    @ApiModelProperty("支付方式")
    private String paymentType;
    @ApiModelProperty("支付状态")
    private String payStatus;
    @ApiModelProperty("支付状态名称")
    private String payStatusName;
    @ApiModelProperty("支付方式名称")
    private String payName;
    @ApiModelProperty("支付方式")
    private String payType;
    @ApiModelProperty("支付流水号")
    private String payCode;
    @ApiModelProperty("支付金额")
    private Double payMoney;
    @ApiModelProperty("支付时间")
    private String payTime;
    @ApiModelProperty("发货状态")
    private String shipStatus;
    @ApiModelProperty("发货类型")
    private String shipType;
    @ApiModelProperty("发货类型名称")
    private String shipTypeName;
    private String shipTime;
    @ApiModelProperty("物流费用")
    private Double shipAmount;
    @ApiModelProperty("收货时间")
    private String receiveName;
    @ApiModelProperty("收货地址")
    private String receiveAddr;
    @ApiModelProperty("收货zip")
    private String receiveZip;
    @ApiModelProperty("收货email")
    private String receiveEmail;
    @ApiModelProperty("收货电话")
    private String receiveMobile;
    @ApiModelProperty("收货时间")
    private String receiveTime;
    @ApiModelProperty("商品金额")
    private Double goodsAmount;
    @ApiModelProperty("订单金额")
    private Double orderAmount;
    private Integer goodsNum;
    private Integer receiveNum;
    private Integer deliveryNum;
    private Double couponAmount;
    private String remark;
    private String sourceFrom;
    private String typeCode;
    private String batchId;
    private String invoiceType;
    private String invoiceTitle;
    private String taxPayerNo;
    private String registerAddress;
    private String registerPhone;
    private String registerBank;
    private String registerBankAcct;
    private Integer isNeedShip;
    private String couponCode;
    private String couponDesc;
    private String workId;
    private Integer isDelete;
    private String getGoodsCode;
    @ApiModelProperty("订单类别，0.普通分销\n" +
            "1.预售")
    private String orderCat;

    private String supplierCode;
    private String merchantCode;
    private String buyerCode;

    @ApiModelProperty("买家名称")
    private String buyerName;
    @ApiModelProperty("买家类型")
    private String buyerType;

    @ApiModelProperty("直减金额")
    private double directReductionAmount;
    @ApiModelProperty("优惠金额")
    private double acCouponAmount;

    public String getPayTime() {
        return timeFormat(payTime);
    }

    public String getShipTime() {
        return timeFormat(shipTime);
    }

    public String getCreateTime() {
        return timeFormat(createTime);
    }

    public String getReceiveTime() {
        return timeFormat(receiveTime);
    }

    public String getOrderTypeName() {
        return OrderBuilderType.matchOpCode(orderType).getName();
    }

    public String getStatusName() {
        return OrderAllStatus.matchOpCode(status).getName();
    }

    public String getPayName() {
        return OrderPayType.matchOpCode(payType).getName();
    }

    public String getPayStatusName() {
        return TypeStatus.matchOpCode(payStatus,PAY).getName();
    }
    public String getShipTypeName() {
        return OrderShipType.matchOpCode(shipType).getName();
    }

    public Double getShipAmount100(){
        if(shipAmount==null){
            return shipAmount;
        }
        return shipAmount/100.0;
    }

    public Double getCouponAmount100() {
        if(couponAmount==null){
            return couponAmount;
        }
        return couponAmount/100.0;
    }

    public Double getOrderAmount100() {
        if(orderAmount==null){
            return orderAmount;
        }
        return orderAmount/100.0;
    }

    public Double getGoodsAmount100() {
        if(goodsAmount==null){
            return goodsAmount;
        }
        return goodsAmount/100.0;
    }
}
