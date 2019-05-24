package com.iwhalecloud.retail.workflow.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * Node
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型wf_node, 对应实体Node类")
public class NodeDTO implements java.io.Serializable {

	private static final long serialVersionUID = 790148216182856210L;

	//属性 begin
	/**
  	 * 环节ID 
  	 */
	@ApiModelProperty(value = "环节ID ")
  	private String nodeId;
	
	/**
  	 * 环节名称 
  	 */
	@ApiModelProperty(value = "环节名称 ")
  	private String nodeName;
	
	/**
  	 * 所属类型 
  	 */
	@ApiModelProperty(value = "所属类型:1. 处理节点 2. 判断节点 ")
  	private String type;
	
	/**
  	 * 环节编码 
  	 */
	@ApiModelProperty(value = "环节编码 ")
  	private String nodeCode;
	
	/**
  	 * 环节描述 
  	 */
	@ApiModelProperty(value = "环节描述 ")
  	private String nodeDesc;
	
	/**
  	 * 是否可编辑 
  	 */
	@ApiModelProperty(value = "是否可编辑 ")
  	private String booEdit;
	
	/**
  	 * 是否自动执行 
  	 */
	@ApiModelProperty(value = "是否自动执行 ")
  	private String booAutoExe;
	
	/**
  	 * 创建时间 
  	 */
	@ApiModelProperty(value = "创建时间 ")
  	private java.util.Date createTime;
	
	/**
  	 * 创建用户ID 
  	 */
	@ApiModelProperty(value = "创建用户ID ")
  	private String createUserId;
	
	/**
  	 * 修改时间 
  	 */
	@ApiModelProperty(value = "修改时间 ")
  	private java.util.Date updateTime;
	
	/**
  	 * 修改用户ID 
  	 */
	@ApiModelProperty(value = "修改用户ID ")
  	private String updateUserId;
	
  	
}
