package com.iwhalecloud.retail.order.dto.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ContractPInfoModel implements Serializable {

    @ApiModelProperty(value = "id")
    private String contractId;
    @ApiModelProperty(value = "订单号")
    private String orderId;
    @ApiModelProperty(value = "机主姓名")
    private String contractName;
    @ApiModelProperty(value = "联系号码")
    private String contractPhone;
    @ApiModelProperty(value = "身份证号")
    private String icNum;
    @ApiModelProperty(value = "实名认证")
    private String authentication;
    @ApiModelProperty(value = "身份类型：身份证")
    private String icType="身份证";
    @ApiModelProperty(value = "合约机号码")
    private String phone;
    @ApiModelProperty(value = "合约机号码状态")
    private String phoneStatus;


}
