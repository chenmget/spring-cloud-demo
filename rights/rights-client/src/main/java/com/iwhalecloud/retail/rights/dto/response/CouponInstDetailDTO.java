package com.iwhalecloud.retail.rights.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("优惠券实例详情，带出一些mkt_res_coupon，coupon_discount_rule表的字段(优惠券名称、描述、抵扣规则等等）")
public class CouponInstDetailDTO implements Serializable {
    private static final long serialVersionUID = -6605199895491866908L;

    @ApiModelProperty(value = "优惠券实例标识")
    private java.lang.String couponInstId;

    @ApiModelProperty(value = "优惠券实例编码")
    private java.lang.String couponInstNbr;

    @ApiModelProperty(value = "优惠券标识")
    private java.lang.String mktResId;

    @ApiModelProperty(value = "记录优惠券使用后最终的优惠额度信息")
    private java.lang.Long couponAmount;

    @ApiModelProperty(value = "优惠券赠送的客户统一账号")
    private java.lang.String custAcctId;

    @ApiModelProperty(value = "生效时间")
    private java.util.Date effDate;

    @ApiModelProperty(value = "失效时间")
    private java.util.Date expDate;

    @ApiModelProperty(value = "状态LOVB=RES-C-0046未使用、已使用、已过期")
    private java.lang.String statusCd;

    /**** 以下是 mkt_res_coupon 表 字段*/

    @ApiModelProperty(value = "优惠券实名称")
    private java.lang.String mktResName;

    @ApiModelProperty(value = "优惠方式LOVB=RES-C-0041定额、折扣、随机等")
    private java.lang.String discountType;

    @ApiModelProperty(value = "优化券类: 1:平台优惠券  2:商家优惠券 3:产品优惠券")
    private java.lang.String couponType;


    /**** 以下是 coupon_discount_rule 表 字段*/

    @ApiModelProperty(value = "抵扣规则标识")
    private String discountRuleId;

    @ApiModelProperty(value = "记录抵扣的固定金额或折扣")
    private Double discountValue;

    @ApiModelProperty(value = "记录抵扣的上限")
    private Double maxValue;

    @ApiModelProperty(value = "记录抵扣的下限")
    private Double minValue;

    @ApiModelProperty(value = "记录使用优惠券需要满足的条件")
    private Double ruleAmount;

    @ApiModelProperty(value = "记录优惠券的使用类型LOVB=RES-C-0045商品、商铺内、总额")
    private String useType;

    @ApiModelProperty(value = "标志同一个优惠券的多个优惠券实例是否可叠加使用。LOVB=PUB-C-0006")
    private String reuseFlag;

    @ApiModelProperty(value = "标志不同优惠券的多个实例是否可混合使用。LOVB=PUB-C-0006")
    private String mixUseFlag;

    @ApiModelProperty(value = "标志不同优惠券的多个实例是否可循环使用。LOVB=PUB-C-0006")
    private String recycleFlag;

    @ApiModelProperty(value = "抵扣规则描述")
    private String discountRuleDesc;


}
