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
 * Service
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("wf_service")
@ApiModel(value = "对应模型wf_service, 对应实体Service类")
@KeySequence(value = "seq_wf_serviceid",clazz = String.class)
public class Service implements Serializable {
    /**表名常量*/
    public static final String TNAME = "wf_service";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 服务ID 
  	 */
	@TableId
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

	/**
	 * 服务分组（订单：order，商品：goods，合作伙伴：partner）
	 */
	@ApiModelProperty(value = "分组 ")
	private String serviceGroup;

	@ApiModelProperty(value = "服务动态参数，直接读取数据库配置，这里只是透传过去")
	private String dynamicParam;

	@ApiModelProperty(value = "执行类型:1：java执行（当前只支持1） 2：sql执行")
	private String execType;
  	
  	//属性 end
  	
  	public static enum FieldNames{
        /** 服务ID  */
        serviceId,
        /** 服务名称  */
        serviceName,
        /** 服务描述  */
        serviceDesc,
        /** 服务类路径  */
        classPath,
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
