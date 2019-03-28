package com.iwhalecloud.retail.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * SysMenu
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型sys_menu, 对应实体SysMenu类")
public class MenuDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * menuId
  	 */
	@ApiModelProperty(value = "menuId")
  	private java.lang.String menuId;
	
	/**
  	 * parentMenuId
  	 */
	@ApiModelProperty(value = "parentMenuId")
  	private java.lang.String parentMenuId;
	
	/**
  	 * menuName
  	 */
	@ApiModelProperty(value = "menuName")
  	private java.lang.String menuName;
	
	/**
  	 * menuDesc
  	 */
	@ApiModelProperty(value = "menuDesc")
  	private java.lang.String menuDesc;
	
	/**
  	 * menuUrl
  	 */
	@ApiModelProperty(value = "menuUrl")
  	private java.lang.String menuUrl;
	
	/**
  	 * 1：菜单
            2：按钮
  	 */
	@ApiModelProperty(value = "1：菜单 2：按钮")
  	private java.lang.String menuType;
	
	/**
  	 * 记录状态。LOVB=PUB-C-0001。
  	 */
	@ApiModelProperty(value = "记录状态。LOVB=PUB-C-0001。")
  	private java.lang.String statusCd;
	
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

	@ApiModelProperty(value = "平台:  1 交易平台   2管理平台")
	private String platform; // 平台:  1 交易平台   2管理平台


}
