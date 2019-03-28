package com.iwhalecloud.retail.rights.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lin.wenhui@iwhalecloud.com
 * @date 2019/2/26 10:08
 * @description
 */

@Data
@ApiModel(value = "查询全部商家已使用的优惠券--响应参数")
public class CouponUsedResp implements Serializable {

    private static final long serialVersionUID = -8366962546830255377L;

    @ApiModelProperty(value = "优惠券来源")
    private String couponResource;

    @ApiModelProperty(value = "优惠券编号")
    private String couponNo;

    @ApiModelProperty(value = "优惠券名称")
    private String couponName;

    @ApiModelProperty(value = "优惠券类型")
    private String couponType;

    @ApiModelProperty(value = "有效期")
    private Date expiryDate;

    @ApiModelProperty(value = "面额")
    private Double amount;

    @ApiModelProperty(value = "使用供应商")
    private String supplier;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "订单状态")
    private String orderState;

    @ApiModelProperty(value = "交易时间")
    private String dealTime;

    @ApiModelProperty(value = "合作伙伴标识")
    private String partnerId;
}

