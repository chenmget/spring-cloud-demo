package com.iwhalecloud.retail.workflow.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * NodeRights
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("wf_node_rights")
@ApiModel(value = "对应模型wf_node_rights, 对应实体NodeRights类")
@KeySequence(value = "seq_wf_node_rights_id",clazz = String.class)
public class NodeRights implements Serializable {
    /**表名常量*/
    public static final String TNAME = "wf_node_rights";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * id 
  	 */
	@TableId
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
	 * serviceId
	 */
	@ApiModelProperty(value = "服务ID ")
	private String serviceId;
  	
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
  	
  	/**
  	 * 记录环节路由的条件ID，引用到条件规则表
  	 */
	@ApiModelProperty(value = "记录环节路由的条件ID，引用到条件规则表 ")
  	private String 	routeCondition;


  	//属性 end
  	
  	public static enum FieldNames{
        /** id  */
        rightsId,
        /** 权限类型 类型：0：申请用户,1:角色，2：指定用户 */
        rightsType,
        /** 环节ID  */
        nodeId,
        /** 用户ID  */
        userId,
        /** 角色ID  */
        roleId,
        /** 部门ID  */
        deptId,
        /** 创建时间  */
        createTime,
        /** 创建用户ID  */
        createUserId,
        /** 修改时间  */
        updateTime,
        /** 修改用户ID  */
        updateUserId
    }

	

}
