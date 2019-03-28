package com.iwhalecloud.retail.order2b.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * OrderLog
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("ord_order_log")
@ApiModel(value = "对应模型ord_order_log, 对应实体OrderLog类")
public class OrderLog implements Serializable {
    /**表名常量*/
    public static final String TNAME = "ord_order_log";
  	private static final long serialVersionUID = 1L;

	private String lanId;
  
  	
  	//属性 begin
  	/**
  	 * 变更流水
  	 */
	@TableId(type = IdType.ID_WORKER)
	@ApiModelProperty(value = "变更流水")
  	private java.lang.String logId;
  	
  	/**
  	 * 订单ID
  	 */
	@ApiModelProperty(value = "订单ID")
  	private java.lang.String orderId;
  	
  	/**
  	 * 变更动作
  	 */
	@ApiModelProperty(value = "变更动作")
  	private java.lang.String changeAction;
  	
  	/**
  	 * 变更原因
  	 */
	@ApiModelProperty(value = "变更原因")
  	private java.lang.String changeReson;
  	
  	/**
  	 * 变更描述
  	 */
	@ApiModelProperty(value = "变更描述")
  	private java.lang.String desc;
  	
  	/**
  	 * 变更前状态
  	 */
	@ApiModelProperty(value = "变更前状态")
  	private java.lang.String preStatus;
  	
  	/**
  	 * 变更后状态
  	 */
	@ApiModelProperty(value = "变更后状态")
  	private java.lang.String postStatus;
  	
  	/**
  	 * 用户ID
  	 */
	@ApiModelProperty(value = "用户ID")
  	private java.lang.String userId;
  	
  	/**
  	 * 创建时间
  	 */
	@ApiModelProperty(value = "创建时间")
  	private java.util.Date createTime;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 变更流水. */
		logId("logId","LOG_ID"),
		
		/** 订单ID. */
		orderId("orderId","ORDER_ID"),
		
		/** 变更动作. */
		changeAction("changeAction","CHANGE_ACTION"),
		
		/** 变更原因. */
		changeReson("changeReson","CHANGE_RESON"),
		
		/** 变更描述. */
		desc("desc","DESC"),
		
		/** 变更前状态. */
		preStatus("preStatus","PRE_STATUS"),
		
		/** 变更后状态. */
		postStatus("postStatus","POST_STATUS"),
		
		/** 用户ID. */
		userId("userId","USER_ID"),
		
		/** 创建时间. */
		createTime("createTime","CREATE_TIME");

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
