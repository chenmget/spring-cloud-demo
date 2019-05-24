package com.iwhalecloud.retail.workflow.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * Process
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型wf_process, 对应实体Process类")
public class ProcessDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * 流程ID 
  	 */
	@ApiModelProperty(value = "流程ID ")
  	private String processId;
	
	/**
  	 * 流程名称 
  	 */
	@ApiModelProperty(value = "流程名称 ")
  	private String processName;
	
	/**
  	 * 流程类型 
  	 */
	@ApiModelProperty(value = "流程类型 ")
  	private String type;
	
	/**
  	 * 状态 0：无效
            1：有效
  	 */
	@ApiModelProperty(value = "状态 0：无效 1：有效")
  	private String processStatus;
	
	/**
  	 * 版本号 
  	 */
	@ApiModelProperty(value = "版本号 ")
  	private Long version;
	
	/**
  	 * 描述 
  	 */
	@ApiModelProperty(value = "描述 ")
  	private String processDesc;
	
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
