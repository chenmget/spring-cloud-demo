package com.iwhalecloud.retail.rights.dto.request;

import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author lin.wenhui@iwhalecloud.com
 * @date 2019/2/26 10:07
 * @description
 */
@Data
@ApiModel(value = "查询全部商家已使用的优惠券--请求参数")
public class CouponUsedReq extends PageVO {

    @ApiModelProperty(value = "商家id")
    private String businessId;

    @ApiModelProperty(value = "优惠券编号")
    private String couponNo;

    @ApiModelProperty(value = "优惠券名称")
    private String couponName;

    @ApiModelProperty(value = "优惠券类型")
    private String couponType;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "使用供应商")
    private String supplier;

    @ApiModelProperty(value = "交易开始时间")
    private String dealStartDate;

    @ApiModelProperty(value = "交易结束时间")
    private String dealEndDate;

    @ApiModelProperty(value = "产品名称")
    private String productName;
}

