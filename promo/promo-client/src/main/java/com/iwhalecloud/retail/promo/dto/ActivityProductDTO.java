package com.iwhalecloud.retail.promo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * ActivityProduct
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型act_activity_product, 对应实体ActivityProduct类")
public class ActivityProductDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * ID
  	 */
	@ApiModelProperty(value = "ID")
  	private java.lang.String id;
	
	/**
  	 * 营销活动code
  	 */
	@ApiModelProperty(value = "营销活动Id")
  	private java.lang.String marketingActivityId;
	
	/**
  	 * 产品基础表
  	 */
	@ApiModelProperty(value = "产品基础表")
  	private java.lang.String productBaseId;
	
	/**
  	 * 产品ID
  	 */
	@ApiModelProperty(value = "产品ID")
  	private java.lang.String productId;
	
	/**
  	 * 价格
  	 */
	@ApiModelProperty(value = "价格")
  	private java.lang.Long price;
	
	/**
  	 * 预付价格
  	 */
	@ApiModelProperty(value = "预付价格")
  	private java.lang.Long prePrice;
	
	/**
  	 * 返利金额
  	 */
	@ApiModelProperty(value = "返利金额")
  	private java.lang.Long rebatePrice;
	
	/**
  	 * 优惠方式为直减时，保存减免的金额
  	 */
	@ApiModelProperty(value = "优惠方式为直减时，保存减免的金额")
  	private java.lang.Long discountAmount;
	
	/**
  	 * 数量
  	 */
	@ApiModelProperty(value = "数量")
  	private java.lang.Long num;
	
	/**
  	 * 达量
  	 */
	@ApiModelProperty(value = "达量")
  	private java.lang.Long reachAmount;
	
//	/**
//  	 * 支付方式
//  	 */
//	@ApiModelProperty(value = "支付方式")
//  	private java.lang.String payType;
	
	/**
  	 * 供应商编码
  	 */
	@ApiModelProperty(value = "供应商编码")
  	private java.lang.String supplierCode;

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
