package com.iwhalecloud.retail.rights.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 记录优惠券实例属性信息。
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型COUPON_INST_ATTR, 对应实体CouponInstAttr类")
public class CouponInstAttrRespDTO implements Serializable {

	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	
  	/**
  	 * 优惠券实例ID的标识，主键
  	 */
	@ApiModelProperty(value = "优惠券实例ID的标识，主键")
  	private java.lang.String couponInstId;
  	
  	/**
  	 * 记录属性ID。
  	 */
	@ApiModelProperty(value = "记录属性ID。")
  	private java.lang.Long attrId;
  	
  	/**
  	 * 属性值标识。
  	 */
	@ApiModelProperty(value = "属性值标识。")
  	private java.lang.Long attrValueId;
  	
  	/**
  	 * 属性值。
  	 */
	@ApiModelProperty(value = "属性值。")
  	private java.lang.String attrValue;
  	
  	/**
  	 * 记录状态。LOVB=PUB-C-0001
  	 */
	@ApiModelProperty(value = "记录状态。LOVB=PUB-C-0001")
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
  	private java.lang.Long createStaff;
  	
  	/**
  	 * 记录首次创建的时间。
  	 */
	@ApiModelProperty(value = "记录首次创建的时间。")
  	private java.util.Date createDate;
  	
  	/**
  	 * 记录每次修改的员工标识。
  	 */
	@ApiModelProperty(value = "记录每次修改的员工标识。")
  	private java.lang.Long updateStaff;
  	
  	/**
  	 * 记录每次修改的时间。
  	 */
	@ApiModelProperty(value = "记录每次修改的时间。")
  	private java.util.Date updateDate;
  	
  	/**
  	 * 本地网
  	 */
	@ApiModelProperty(value = "本地网")
  	private java.lang.Long lanId;
  	
  	/**
  	 * 指向公共管理区域标识
  	 */
	@ApiModelProperty(value = "指向公共管理区域标识")
  	private java.lang.Long regionId;
  	
  	/**
  	 * 备注
  	 */
	@ApiModelProperty(value = "备注")
  	private java.lang.String remark;
  	
  	
  	//属性 end

}
