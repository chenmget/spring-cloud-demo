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
 * Node
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("wf_node")
@ApiModel(value = "对应模型wf_node, 对应实体Node类")
@KeySequence(value = "seq_wf_node_id",clazz = String.class)
public class Node implements Serializable {
    /**表名常量*/
    public static final String TNAME = "wf_node";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 环节ID 
  	 */
	@TableId
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
	@ApiModelProperty(value = "所属类型 ")
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

	@ApiModelProperty(value = "是否指定用户:0-否，1-是")
	private String booAppointDealUser;
  	
  	
  	//属性 end
  	
  	public static enum FieldNames{
        /** 环节ID  */
        nodeId,
        /** 环节名称  */
        nodeName,
        /** 所属类型  */
        type,
        /** 环节编码  */
        nodeCode,
        /** 环节描述  */
        nodeDesc,
        /** 是否可编辑  */
        booEdit,
        /** 是否自动执行  */
        booAutoExe,
        /** 创建时间  */
        createTime,
        /** 创建用户ID  */
        createUserId,
        /** 修改时间  */
        updateTime,
        /** 修改用户ID  */
        updateUserId,
		/** 修改用户ID  */
		booAppointDealUser
    }

	

}
