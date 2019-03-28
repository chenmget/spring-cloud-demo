package com.iwhalecloud.retail.oms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 云货架绑定人员关系表
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型R_CLOUD_SHELF_BIND_USER, 对应实体CloudShelfBindUser类")
public class CloudShelfBindUserDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * 自增ID
  	 */
	@ApiModelProperty(value = "自增ID")
  	private java.lang.Long id;
	
	/**
  	 * 创建时间
  	 */
	@ApiModelProperty(value = "创建时间")
  	private java.util.Date gmtCreate;
	
	/**
  	 * 修改时间
  	 */
	@ApiModelProperty(value = "修改时间")
  	private java.util.Date gmtModified;
	
	/**
  	 * 创建人
  	 */
	@ApiModelProperty(value = "创建人")
  	private java.lang.String creator;
	
	/**
  	 * 修改人
  	 */
	@ApiModelProperty(value = "修改人")
  	private java.lang.String modifier;
	
	/**
  	 * 是否删除：0未删、1删除  
  	 */
	@ApiModelProperty(value = "是否删除：0未删、1删除  ")
  	private java.lang.Integer isDeleted;
	
	/**
  	 * 云货架编号
  	 */
	@ApiModelProperty(value = "云货架编号")
  	private java.lang.String cloudShelfNumber;
	
	/**
  	 * 员工工号
  	 */
	@ApiModelProperty(value = "员工工号")
  	private java.lang.String userId;

	/**
	 * 员工名称
	 */
	@ApiModelProperty(value = "员工名称")
	private java.lang.String userName;
	
	/**
  	 * 员工身份：1管理员、2普通员工
  	 */
	@ApiModelProperty(value = "员工身份：1管理员、2普通员工")
  	private java.lang.Long userIdentity;
	
  	
}
