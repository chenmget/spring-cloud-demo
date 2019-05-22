package com.iwhalecloud.retail.promo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * ActivityScope
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型act_activity_scope, 对应实体ActivityScope类")
public class ActivityScopeDTO implements java.io.Serializable {
    
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
  	 * 地市
  	 */
	@ApiModelProperty(value = "地市")
  	private java.lang.String lanId;
	/**
	 * 地市名称
	 */
	@ApiModelProperty(value = "地市名称")
	private java.lang.String lanName;
	
	/**
  	 * 市县
  	 */
	@ApiModelProperty(value = "市县")
  	private java.lang.String city;

	/**
	 * 市县名称
	 */
	@ApiModelProperty(value = "市县名称")
	private java.lang.String cityName;
	/**
  	 * 供应商名称
  	 */
	@ApiModelProperty(value = "供应商名称")
  	private java.lang.String supplierName;
	
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
	 * 供应商名称--基本信息页面
	 */
	@ApiModelProperty(value = "供应商名称")
	private java.lang.String merchantName;

	/**
	 * 供应商编码--基本信息页面
	 */
	@ApiModelProperty(value = "供应商编码")
	private java.lang.String merchantCode;

	/**
	 * 供应商Id--基本信息页面
	 */
	@ApiModelProperty(value = "供应商Id")
	private java.lang.String merchantId;

	/**
	 * 地市/区县ID--基本信息页面
	 */
	@ApiModelProperty(value = "地市/区县Id")
	private java.lang.String regionId;

	/**
	 * 地市/区县ID--基本信息页面反参用
	 */
	@ApiModelProperty(value = "地市/区县Id")
	private java.lang.String key;

	/**
	 * 地市/区县名称--基本信息页面反参用
	 */
	@ApiModelProperty(value = "地市/区县名称")
	private java.lang.String title;
	/**
	 * 是否删除：0未删、1删除。
	 */
	@ApiModelProperty(value = "是否删除：0未删、1删除。")
	private String isDeleted;

	/**
	 * 状态,0：待审核,1：有效,-1：审核不通过
	 */
	@ApiModelProperty(value = "状态,0：待审核,1：有效,-1：审核不通过")
	private java.lang.String status;
  	
}
