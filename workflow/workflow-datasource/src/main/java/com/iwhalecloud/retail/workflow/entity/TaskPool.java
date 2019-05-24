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
 * TaskPool
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("wf_task_pool")
@ApiModel(value = "对应模型wf_task_pool, 对应实体TaskPool类")
@KeySequence(value = "seq_wf_task_pool_id",clazz = String.class)
public class TaskPool implements Serializable {
    /**表名常量*/
    public static final String TNAME = "wf_task_pool";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * id  
  	 */
	@TableId
	@ApiModelProperty(value = "id  ")
  	private String poolId;
  	
  	/**
  	 * 路由实例表  
  	 */
	@ApiModelProperty(value = "路由实例表  ")
  	private String taskItemId;
  	
  	/**
  	 * 任务ID  
  	 */
	@ApiModelProperty(value = "任务ID  ")
  	private String taskId;
  	
  	/**
  	 * 用户id  
  	 */
	@ApiModelProperty(value = "用户id  ")
  	private String userId;
  	
  	/**
  	 * 用户名称  
  	 */
	@ApiModelProperty(value = "用户名称  ")
  	private String userName;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** id  . */
		poolId("poolId","pool_id"),
		
		/** 路由实例表  . */
		taskItemId("taskItemId","task_item_id"),
		
		/** 任务ID  . */
		taskId("taskId","task_id"),
		
		/** 用户id  . */
		userId("userId","user_id"),
		
		/** 用户名称  . */
		userName("userName","user_name");

		private String fieldName;
		private String tableFieldName;
		FieldNames(String fieldName, String tableFieldName){
			this.fieldName = fieldName;
			this.tableFieldName = tableFieldName;
		}

		public String getFieldName() {
			return fieldName;
		}

		public String getTableFieldName() {
			return tableFieldName;
		}
	}

}
