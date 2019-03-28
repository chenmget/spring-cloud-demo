package com.iwhalecloud.retail.workflow.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * Route
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型wf_route, 对应实体Route类")
public class RouteDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * 路由ID 
  	 */
	@ApiModelProperty(value = "路由ID ")
  	private String routeId;
	
	/**
  	 * 路由名称 
  	 */
	@ApiModelProperty(value = "路由名称 ")
  	private String routeName;
	
	/**
  	 * 流程ID 
  	 */
	@ApiModelProperty(value = "流程ID ")
  	private String processId;
	
	/**
  	 * 当前环节ID 
  	 */
	@ApiModelProperty(value = "当前环节ID ")
  	private String curNodeId;
	
	/**
  	 * 当前环节名称 
  	 */
	@ApiModelProperty(value = "当前环节名称 ")
  	private String curNodeName;
	
	/**
  	 * 下一环节ID 
  	 */
	@ApiModelProperty(value = "下一环节ID ")
  	private String nextNodeId;
	
	/**
  	 * 下一环节名称 
  	 */
	@ApiModelProperty(value = "下一环节名称 ")
  	private String nextNodeName;
	
	/**
  	 * 下环节是否自动执行 
  	 */
	@ApiModelProperty(value = "下环节是否自动执行 ")
  	private String booAutoExe;
	
	/**
  	 * 顺序 
  	 */
	@ApiModelProperty(value = "顺序 ")
  	private Long sort;
	
	/**
  	 * 描述 
  	 */
	@ApiModelProperty(value = "描述 ")
  	private String routerDesc;
	
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
