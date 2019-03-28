package com.iwhalecloud.retail.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * PublicDict
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型sys_public_dict, 对应实体PublicDict类")
public class PublicDictDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * 模块数据    例如商品模块:goods    系统模块: system
  	 */
	@ApiModelProperty(value = "模块数据    例如商品模块:goods    系统模块: system")
  	private String module;
	
	/**
  	 * 数据类型     如 : 用户类型: user_type
  	 */
	@ApiModelProperty(value = "数据类型     如 : 用户类型: user_type")
  	private String type;
	
	/**
  	 * 键值
  	 */
	@ApiModelProperty(value = "键值")
  	private String pkey;
	
	/**
  	 * 键名
  	 */
	@ApiModelProperty(value = "键名")
  	private String pname;
	
	/**
  	 * 描述
  	 */
	@ApiModelProperty(value = "描述")
  	private String comments;
	
	/**
  	 * 扩展字段a
  	 */
	@ApiModelProperty(value = "扩展字段a")
  	private Integer codea;
	
	/**
  	 * 扩展字段
  	 */
	@ApiModelProperty(value = "扩展字段")
  	private String codeb;
	
	/**
  	 * 扩展字段
  	 */
	@ApiModelProperty(value = "扩展字段")
  	private String codec;
	
	/**
  	 * 扩展字段
  	 */
	@ApiModelProperty(value = "扩展字段")
  	private String coded;
	
	/**
  	 * sort
  	 */
	@ApiModelProperty(value = "排序字段")
  	private Integer sort;
	
  	
}
