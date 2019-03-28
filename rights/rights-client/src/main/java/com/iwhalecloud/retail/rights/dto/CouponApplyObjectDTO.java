package com.iwhalecloud.retail.rights.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 记录优惠券与能优惠的对象，如商品、销售品、产品、积分等。
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型COUPON_APPLY_OBJECT, 对应实体CouponApplyObject类")
public class CouponApplyObjectDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    //属性 begin
    /**
     * 优惠券适用对象标识
     */
    @ApiModelProperty(value = "优惠券适用对象标识")
    private java.lang.String applyObjectId;

    /**
     * 优惠券标识
     */
    @ApiModelProperty(value = "优惠券标识")
    private java.lang.String mktResId;

    /**
     * 适用对象类型LOVB=RES-C-0044
     商品、销售品、积分等
     */
    @ApiModelProperty(value = "适用对象类型LOVB=RES-C-0044商品、销售品、积分等")
    private java.lang.String objType;

    /**
     * 适用对象标识
     兑换关系：记录A端营销资源能够兑换的Z端营销资源，如优惠券适用兑换的商品。
     */
    @ApiModelProperty(value = "适用对象标识兑换关系：记录A端营销资源能够兑换的Z端营销资源，如优惠券适用兑换的商品。")
    private java.lang.String objId;

    /**
     * 记录状态。LOVB=PUB-C-0001。
     */
    @ApiModelProperty(value = "记录状态。LOVB=PUB-C-0001。")
    private java.lang.String statusCd;

    /**
     * 记录状态变更的时间。
     */
    @ApiModelProperty(value = "记录状态变更的时间。")
    private java.util.Date statusDate;

    /**
     * 记录首次创建的员工标识。
     */
    @ApiModelProperty(value = "记录首次创建的员工标识。")
    private java.lang.String createStaff;

    /**
     * 记录首次创建的时间。
     */
    @ApiModelProperty(value = "记录首次创建的时间。")
    private java.util.Date createDate;

    /**
     * 记录每次修改的员工标识。
     */
    @ApiModelProperty(value = "记录每次修改的员工标识。")
    private java.lang.String updateStaff;

    /**
     * 记录每次修改的时间。
     */
    @ApiModelProperty(value = "记录每次修改的时间。")
    private java.util.Date updateDate;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private java.lang.String remark;

}