package com.iwhalecloud.retail.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * SysUserRole
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型sys_user_role, 对应实体SysUserRole类")
public class UserRoleDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * id
  	 */
	@ApiModelProperty(value = "id")
  	private java.lang.String id;
	
	/**
  	 * userId
  	 */
	@ApiModelProperty(value = "userId")
  	private java.lang.String userId;

	/**
	 * userName
	 */
	@ApiModelProperty(value = "userName")
	private java.lang.String userName;

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
  	 * 记录首次创建的员工标识。
  	 */
	@ApiModelProperty(value = "记录首次创建的员工标识。")
  	private java.lang.String createStaff;
	
	/**
  	 * 记录首次创建的时间。
  	 */
	@ApiModelProperty(value = "记录首次创建的时间。")
  	private java.util.Date createDate;
	
  	
}
