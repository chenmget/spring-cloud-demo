package com.iwhalecloud.retail.promo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * Promotion
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型act_promotion, 对应实体Promotion类")
public class PromotionDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * ID
  	 */
	@ApiModelProperty(value = "ID")
  	private java.lang.String id;
	
	/**
  	 * 营销活动编号
  	 */
	@ApiModelProperty(value = "营销活动Id")
  	private java.lang.String marketingActivityId;
	
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
  	 * 优惠价格
  	 */
	@ApiModelProperty(value = "优惠价格")
  	private java.lang.String promotionPrice;
	
	/**
  	 * 优惠类型：10减免20券30返利40赠送50红包
  	 */
	@ApiModelProperty(value = "优惠类型：10减免20券30返利40赠送50红包")
  	private java.lang.String promotionType;
	
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
	
	/**
  	 * 备注
  	 */
	@ApiModelProperty(value = "备注")
  	private java.lang.String remark;
	
//	/**
//  	 * 记录首次创建的员工标识。
//  	 */
//	@ApiModelProperty(value = "记录首次创建的员工标识。")
//  	private java.lang.String createStaff;
//
//	/**
//  	 * 记录首次创建的时间。
//  	 */
//	@ApiModelProperty(value = "记录首次创建的时间。")
//  	private java.util.Date createDate;
//
//	/**
//  	 * 记录每次修改的员工标识。
//  	 */
//	@ApiModelProperty(value = "记录每次修改的员工标识。")
//  	private java.lang.String updateStaff;
//
//	/**
//  	 * 记录每次修改的时间。
//  	 */
//	@ApiModelProperty(value = "记录每次修改的时间。")
//  	private java.util.Date updateDate;
	/**
	 * 记录首次创建的员工标识。
	 */
	@ApiModelProperty(value = "创建人。")
	private java.lang.String creator;

	/**
	 * 记录首次创建的时间。
	 */
	@ApiModelProperty(value = "创建时间。")
	private java.util.Date gmtCreate;

	/**
	 * 记录每次修改的员工标识。
	 */
	@ApiModelProperty(value = "修改人。")
	private java.lang.String modifier;

	/**
	 * 记录每次修改的时间。
	 */
	@ApiModelProperty(value = "修改时间。")
	private java.util.Date gmtModified;
	/**
	 * 是否删除：0未删、1删除。
	 */
	@ApiModelProperty(value = "是否删除：0未删、1删除。")
	private String isDeleted;
	
  	
}
