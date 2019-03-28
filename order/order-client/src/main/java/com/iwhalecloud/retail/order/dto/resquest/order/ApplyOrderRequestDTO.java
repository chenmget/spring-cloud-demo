package com.iwhalecloud.retail.order.dto.resquest.order;

import com.iwhalecloud.retail.order.dto.base.ModifyBaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ApplyOrderRequestDTO extends ModifyBaseRequest implements Serializable {

    @ApiModelProperty(value = "4退货，3换货，2退款")
    private String serviceType; 
    @ApiModelProperty(value = "订单号")
    private String orderId; 
    @ApiModelProperty(value = "1")
    private String shippingId;
    @ApiModelProperty(value = " 新增:new ,编辑:edit")
    private String action;
    @ApiModelProperty(value = " 问题描述")
    private String questionDesc;
    @ApiModelProperty(value = " 退款银行")
    private String bankInfo;
    @ApiModelProperty(value = " 开户名")
    private String accountHolderName;
    @ApiModelProperty(value = "联系人")
    private String linkman; 
    @ApiModelProperty(value = "账号")
    private String bankAccount;
    @ApiModelProperty(value = "手机号码")
    private String phoneNum;
    @ApiModelProperty(value = "折旧金额")
    private String depreciationPrice;
    @ApiModelProperty(value = "物流费用")
    private String shipPrice;
    @ApiModelProperty(value = "订单项详情")
    private List<OrderDetailDTO> ois;
    @ApiModelProperty(value = "申请单号")
    private String applyId; 

}
