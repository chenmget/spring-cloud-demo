package com.iwhalecloud.retail.workflow.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * RouteService
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型wf_route_service, 对应实体RouteService类")
public class RouteServiceDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * id 
  	 */
	@ApiModelProperty(value = "id ")
  	private String id;
	
	/**
  	 * 路由ID 
  	 */
	@ApiModelProperty(value = "路由ID ")
  	private String routeId;
	
	/**
  	 * 流程ID 
  	 */
	@ApiModelProperty(value = "流程ID ")
  	private String processId;
	
	/**
  	 * 服务ID 
  	 */
	@ApiModelProperty(value = "服务ID ")
  	private String serviceId;
	
	/**
  	 * 排序 
  	 */
	@ApiModelProperty(value = "排序 ")
  	private Long sort;
	
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
