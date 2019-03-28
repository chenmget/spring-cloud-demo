package com.iwhalecloud.retail.web.controller.system.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;


/**
 * SysMenu
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型sys_role_menu, 对应实体RoleMen类")
public class SaveRoleMenuReq implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	//属性 begin
	/**
	 * id
	 */
//	@ApiModelProperty(value = "id")
//	private java.lang.String id;

	/**
	 * menuId
	 */
	@ApiModelProperty(value = "menuId")
	@NotEmpty(message = "菜单ID不能为空")
	private java.lang.String menuId;

	/**
	 * menuName
	 */
	@ApiModelProperty(value = "menuName")
	@NotEmpty(message = "菜单名称不能为空")
	private java.lang.String menuName;

	/**
	 * roleId
	 */
	@ApiModelProperty(value = "roleId")
	@NotEmpty(message = "角色ID不能为空")
	private java.lang.String roleId;

	/**
	 * roleName
	 */
	@NotEmpty(message = "角色名称不能为空")
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
//	@ApiModelProperty(value = "记录首次创建的时间。")
//	private java.util.Date createDate;
}
