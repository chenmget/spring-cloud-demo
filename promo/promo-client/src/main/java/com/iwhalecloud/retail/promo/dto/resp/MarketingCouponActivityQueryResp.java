package com.iwhalecloud.retail.promo.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: wang.jiaxin
 * @Date: 2019年02月20日
 * @Description: 查询B2B商品适用卡券响应参数
 **/
@Data
@ApiModel(value = "查询B2B商品适用卡券响应参数")
public class MarketingCouponActivityQueryResp implements Serializable {
    //属性 begin
    /**
     * 优惠券id
     */
    @ApiModelProperty(value = "优惠券id")
    private java.lang.String mktResId;

    /**
     * 优惠名称
     */
    @ApiModelProperty(value = "优惠名称")
    private java.lang.String mktResName;

    /**
     * 营销资源类别名称
     */
    @ApiModelProperty(value = "营销资源类别名称")
    private java.lang.String mktResTypeName;

    /**
     * 记录抵扣的固定金额或折扣
     */
    @ApiModelProperty(value = "记录抵扣的固定金额或折扣")
    private Double discountValue;

    /**
     * 记录使用优惠券需要满足的条件
     */
    @ApiModelProperty(value = "记录使用优惠券需要满足的条件")
    private Double ruleAmount;

    /**
     * 优惠券生效时间
     */
    @ApiModelProperty(value = "优惠券生效时间")
    private java.util.Date promotionEffectTime;

    /**
     * 优惠券过期时间
     */
    @ApiModelProperty(value = "优惠券过期时间")
    private java.util.Date promotionOverdueTime;
}
