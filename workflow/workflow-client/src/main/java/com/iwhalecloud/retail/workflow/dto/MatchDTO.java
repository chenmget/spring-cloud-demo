package com.iwhalecloud.retail.workflow.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * Match
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型wf_match, 对应实体Match类")
public class MatchDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * ID 
  	 */
	@ApiModelProperty(value = "ID ")
  	private String matchId;
	
	/**
  	 * 匹配脚本 
  	 */
	@ApiModelProperty(value = "匹配脚本 ")
  	private String matchScript;
	
	/**
  	 * 流程ID 
  	 */
	@ApiModelProperty(value = "流程ID ")
  	private String wfId;
	
  	
}
