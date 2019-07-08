package com.iwhalecloud.retail.web.controller.b2b.system.request;

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
@ApiModel(value = "对应模型sys_menu, 对应实体SysMenu类")
public class SaveMenuReq implements java.io.Serializable {

	private static final long serialVersionUID = 1L;


	//属性 begin
	/**
	 * menuId
	 */
//	@ApiModelProperty(value = "menuId")
//  	private String menuId;

	/**
	 * parentMenuId
	 */
	@ApiModelProperty(value = "parentMenuId")
	private String parentMenuId;

	/**
	 * menuName
	 */
	@NotEmpty(message = "菜单名称不能为空")
	@ApiModelProperty(value = "menuName")
	private String menuName;

	/**
	 * menuDesc
	 */
	@ApiModelProperty(value = "menuDesc")
	private String menuDesc;

	/**
	 * menuUrl
	 */
	@NotEmpty(message = "菜单路径URL不能为空")
	@ApiModelProperty(value = "menuUrl")
	private String menuUrl;

	/**
	 * 1:页面菜单  (默认)    2:接口菜单
	 */
	@ApiModelProperty(value = "1:页面菜单  (默认)    2:接口菜单 ")
	private String menuType;

	/**
	 * 记录状态。LOVB=PUB-C-0001。   1:有效   0:无效
	 */
	@ApiModelProperty(value = "记录状态。LOVB=PUB-C-0001。   1:有效   0:无效")
	private String statusCd;

	/**
	 * 记录首次创建的员工标识。
	 */
	@ApiModelProperty(value = "记录首次创建的员工标识。")
	private String createStaff;

	/**
	 * 记录首次创建的时间。
	 */
	@ApiModelProperty(value = "记录首次创建的时间。")
	private java.util.Date createDate;

	/**
	 * 记录每次修改的员工标识。
	 */
	@ApiModelProperty(value = "记录每次修改的员工标识。")
	private String updateStaff;

	/**
	 * 记录每次修改的时间。
	 */
	@ApiModelProperty(value = "记录每次修改的时间。")
	private java.util.Date updateDate;

	@NotEmpty(message = "平台类型不能为空，1交易平台，2管理平台")
	@ApiModelProperty(value = "平台类型:  1 交易平台   2管理平台")
	private String platform; // 平台:  1 交易平台   2管理平台

}
