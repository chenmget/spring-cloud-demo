package com.iwhalecloud.retail.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * SysRole
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型sys_role, 对应实体SysRole类")
public class RoleDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * roleId
  	 */
	@ApiModelProperty(value = "roleId")
  	private java.lang.String roleId;
	
	/**
  	 * roleName
  	 */
	@ApiModelProperty(value = "roleName")
  	private java.lang.String roleName;
	
	/**
  	 * roleDesc
  	 */
	@ApiModelProperty(value = "roleDesc")
  	private java.lang.String roleDesc;
	
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
	
  	
}
