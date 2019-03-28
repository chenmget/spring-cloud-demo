package com.iwhalecloud.retail.workflow.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * NodeRights
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型wf_node_rights, 对应实体NodeRights类")
public class NodeRightsDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * id 
  	 */
	@ApiModelProperty(value = "id ")
  	private String rightsId;
	
	/**
  	 * 权限类型 类型：0：申请用户,1:角色，2：指定用户
  	 */
	@ApiModelProperty(value = "权限类型 类型：0：申请用户,1:角色，2：指定用户")
  	private String rightsType;
	
	/**
  	 * 环节ID 
  	 */
	@ApiModelProperty(value = "环节ID ")
  	private String nodeId;
	
	/**
  	 * 用户ID 
  	 */
	@ApiModelProperty(value = "用户ID ")
  	private String userId;
	
	/**
  	 * 角色ID 
  	 */
	@ApiModelProperty(value = "角色ID ")
  	private String roleId;
	
	/**
  	 * 部门ID 
  	 */
	@ApiModelProperty(value = "部门ID ")
  	private String deptId;
	
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
