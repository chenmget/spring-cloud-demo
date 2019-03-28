package com.iwhalecloud.retail.workflow.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * TaskPool
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型wf_task_pool, 对应实体TaskPool类")
public class TaskPoolDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * id  
  	 */
	@ApiModelProperty(value = "id  ")
  	private java.lang.String poolId;
	
	/**
  	 * 路由实例表  
  	 */
	@ApiModelProperty(value = "路由实例表  ")
  	private java.lang.String taskItemId;
	
	/**
  	 * 任务ID  
  	 */
	@ApiModelProperty(value = "任务ID  ")
  	private java.lang.String taskId;
	
	/**
  	 * 用户id  
  	 */
	@ApiModelProperty(value = "用户id  ")
  	private java.lang.String userId;
	
	/**
  	 * 用户名称  
  	 */
	@ApiModelProperty(value = "用户名称  ")
  	private java.lang.String userName;
	
  	
}
