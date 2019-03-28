package com.iwhalecloud.retail.order2b.dto.resquest.order;

import com.iwhalecloud.retail.order2b.consts.order.OrderPaymentType;
import com.iwhalecloud.retail.order2b.consts.order.OrderShipType;
import com.iwhalecloud.retail.order2b.annotation.EnumCheckValidate;
import com.iwhalecloud.retail.order2b.annotation.NullCheckValidate;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CreateOrderRequest extends PreCreateOrderReq implements Serializable {

    /**
     * 订单来源：sourceForm
     */
    @ApiModelProperty(value = "【必填】1:APP；2：微商城-普通单；3：微商城-商机单；4：其它")
    @NullCheckValidate(message = "typeCode 字段不能为空")
    private String typeCode;


    private String orderType;

    @ApiModelProperty(value = "买家留言")
    private String remark;

    /**
     * 支付信息
     */
    @ApiModelProperty(value = "【必填】在线支付  3线下付款")
    @EnumCheckValidate(enumClass = OrderPaymentType.class, message = "paymentType 类型不匹配,请参考文档")
    private String paymentType;
    @ApiModelProperty(value = "【必填】在线支付  3线下付款",hidden = true)
    private String paymentName;

    /**
     * 物流信息
     */
    @ApiModelProperty(value = "【必填】 配发方式")
    @EnumCheckValidate(enumClass = OrderShipType.class, message = "shipType 类型不匹配,请参考文档")
    private String shipType;
    @ApiModelProperty(value = "金额")
    private String shipAmount;
    @ApiModelProperty(value = "【必填】es_member_address")
    private String addressId;

    /**
     * 发票信息
     */
    @ApiModelProperty(value = "发票类型：1普通发票、2增值发票")
    private String invoiceType;
    @ApiModelProperty(value = "发票抬头")
    private String invoiceTitle;
    @ApiModelProperty(value = "纳税人识别号")
    private String taxPayerNo;
    @ApiModelProperty(value = "注册地址")
    private String registerAddress;
    @ApiModelProperty(value = "注册电话")
    private String registerPhone;
    @ApiModelProperty(value = "开户银行")
    private String registerBank;
    @ApiModelProperty(value = "银行卡号")
    private String registerBankAcct;

    /**
     * 店铺名称
     */
    @ApiModelProperty(value = "厅店id")
    private String shopId;
    @ApiModelProperty(value = "厅店名称")
    private String shopName;


}
