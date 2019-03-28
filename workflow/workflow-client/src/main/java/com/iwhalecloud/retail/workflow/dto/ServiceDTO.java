package com.iwhalecloud.retail.workflow.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * Service
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型wf_service, 对应实体Service类")
public class ServiceDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * 服务ID 
  	 */
	@ApiModelProperty(value = "服务ID ")
  	private String serviceId;
	
	/**
  	 * 服务名称 
  	 */
	@ApiModelProperty(value = "服务名称 ")
  	private String serviceName;
	
	/**
  	 * 服务描述 
  	 */
	@ApiModelProperty(value = "服务描述 ")
  	private String serviceDesc;
	
	/**
  	 * 服务类路径 
  	 */
	@ApiModelProperty(value = "服务类路径 ")
  	private String classPath;
	
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
