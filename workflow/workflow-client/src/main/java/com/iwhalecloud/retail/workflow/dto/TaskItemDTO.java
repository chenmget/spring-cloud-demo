package com.iwhalecloud.retail.workflow.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * TaskItem
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型wf_task_item, 对应实体TaskItem类")
public class TaskItemDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "任务项ID  ")
	private java.lang.String taskItemId;

	@ApiModelProperty(value = "处理结果")
	private String routeName;

	@ApiModelProperty(value = "处理时间")
	private java.util.Date handlerTime;

	@ApiModelProperty(value = "任务处理人")
	private String handlerUserName;

	@ApiModelProperty(value = "处理意见  ")
	private String handlerMsg;

	@ApiModelProperty(value = "上一节点名称")
	private String preNodeName;

	/**
	 * 状态  1：待领取
	 * 2：待处理
	 * 3：已处理
	 */
	@ApiModelProperty(value = "状态  1：待领取\n" +
			"2：待处理\n" +
			"3：已处理")
	private java.lang.String itemStatus;
	
  	
}
