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
 * RouteService
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("wf_route_service")
@ApiModel(value = "对应模型wf_route_service, 对应实体RouteService类")
@KeySequence(value = "seq_wf_route_service_id",clazz = String.class)
public class RouteService implements Serializable {
    /**表名常量*/
    public static final String TNAME = "wf_route_service";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * id 
  	 */
	@TableId
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
  	
  	
  	//属性 end
  	
  	public static enum FieldNames{
        /** id  */
        id,
        /** 路由ID  */
        routeId,
        /** 流程ID  */
        processId,
        /** 服务ID  */
        serviceId,
        /** 排序  */
        sort,
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
