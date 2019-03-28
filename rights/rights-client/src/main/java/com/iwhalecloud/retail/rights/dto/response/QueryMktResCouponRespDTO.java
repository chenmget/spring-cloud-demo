package com.iwhalecloud.retail.rights.dto.response;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 定义优惠券相关的基本信息,如优惠方式、管理类型、使用系统等
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型MKT_RES_COUPON, 对应实体MktResCoupon类")
public class QueryMktResCouponRespDTO {
	 /**
     * 优惠券标识 主键
     */
    @ApiModelProperty(value = "优惠券标识 主键")
    private java.lang.String mktResId;

    /**
     * 记录营销资源名称。
     */
    @ApiModelProperty(value = "记录营销资源名称。")
    private java.lang.String mktResName;

    /**
     * 记录营销资源编码。
     */
    @ApiModelProperty(value = "记录营销资源编码。")
    private java.lang.String mktResNbr;

    /**
     * 营销资源类别标识
     */
    @ApiModelProperty(value = "营销资源类别标识")
    private java.lang.Long mktResTypeId;

    /**
     * 优惠方式LOVB=RES-C-0041

     定额、折扣、随机等
     */
    @ApiModelProperty(value = "优惠方式LOVB=RES-C-0041 定额、折扣、随机等")
    private java.lang.String discountType;

    /**
     * 管理类型LOVB=RES-C-0042
     内部、外部
     */
    @ApiModelProperty(value = "管理类型LOVB=RES-C-0042 内部、外部")
    private java.lang.String manageType;

    /**
     * 优惠券使用系统标识,翼支付、支付宝等
     */
    @ApiModelProperty(value = "优惠券使用系统标识,翼支付、支付宝等")
    private java.lang.Long useSysId;

    /**
     * 记录红包的展示面额信息。固定类展示为固定额；不固定类展示最大优惠额度或折扣率
     */
    @ApiModelProperty(value = "记录红包的展示面额信息。固定类展示为固定额；不固定类展示最大优惠额度或折扣率")
    private java.lang.Long showAmount;

    /**
     * 状态LOVB=PUB-C-0001
     */
    @ApiModelProperty(value = "状态LOVB=PUB-C-0001")
    private java.lang.String statusCd;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private java.lang.String remark;

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
     * partnerId
     */
    @ApiModelProperty(value = "partnerId")
    private java.lang.String partnerId;

    @ApiModelProperty(value = "优化券类: 1:平台优惠券  2:商家优惠券 3:产品优惠券")
    private java.lang.String couponType;

}
