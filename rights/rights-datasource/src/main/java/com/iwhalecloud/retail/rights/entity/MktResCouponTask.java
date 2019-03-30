package com.iwhalecloud.retail.rights.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * MktResCouponTask
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("mkt_res_coupon_task")
@KeySequence(value = "seq_mkt_res_coupon_task",clazz = String.class)
@ApiModel(value = "对应模型mkt_res_coupon_task, 对应实体MktResCouponTask类")
public class MktResCouponTask implements Serializable {
    /**表名常量*/
    public static final String TNAME = "mkt_res_coupon_task";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 优惠券实例编码
  	 */
	@TableId
	@ApiModelProperty(value = "优惠券实例编码")
  	private String taskId;
  	
  	/**
  	 * 营销活动标识
  	 */
	@ApiModelProperty(value = "营销活动标识")
  	private String marketingActivityId;
  	
  	/**
  	 * 营销资源标识id
  	 */
	@ApiModelProperty(value = "营销资源标识id")
  	private String mktResId;
  	
  	/**
  	 * 优惠券赠送的客户统一账号
  	 */
	@ApiModelProperty(value = "优惠券赠送的客户统一账号")
  	private String custAcctId;
  	
  	/**
  	 * 发放记录标识
  	 */
	@ApiModelProperty(value = "发放记录标识")
  	private String provRecId;
  	
  	/**
  	 * 备注
  	 */
	@ApiModelProperty(value = "备注")
  	private String remark;
  	
  	/**
  	 * 记录状态变更的时间。
  	 */
	@ApiModelProperty(value = "记录状态变更的时间。")
  	private java.util.Date statusDate;
  	
  	/**
  	 * 记录首次创建的员工标识。
  	 */
	@ApiModelProperty(value = "记录首次创建的员工标识。")
  	private String createStaff;
  	
  	/**
  	 * 记录首次创建的时间。
  	 */
	@ApiModelProperty(value = "记录首次创建的时间。")
  	private java.util.Date createDate;
  	
  	/**
  	 * 记录每次修改的员工标识。
  	 */
	@ApiModelProperty(value = "记录每次修改的员工标识。")
  	private String updateStaff;
  	
  	/**
  	 * 记录每次修改的时间。
  	 */
	@ApiModelProperty(value = "记录每次修改的时间。")
  	private java.util.Date updateDate;
  	
  	/**
  	 * 记录状态
  	 */
	@ApiModelProperty(value = "记录状态")
  	private String statusCd;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 优惠券实例编码. */
		taskId("taskId","TASK_ID"),
		
		/** 营销活动标识. */
		marketingActivityId("marketingActivityId","MARKETING_ACTIVITY_ID"),
		
		/** 营销资源标识id. */
		mktResId("mktResId","MKT_RES_ID"),
		
		/** 优惠券赠送的客户统一账号. */
		custAcctId("custAcctId","CUST_ACCT_ID"),
		
		/** 发放记录标识. */
		provRecId("provRecId","PROV_REC_ID"),
		
		/** 备注. */
		remark("remark","REMARK"),
		
		/** 记录状态变更的时间。. */
		statusDate("statusDate","STATUS_DATE"),
		
		/** 记录首次创建的员工标识。. */
		createStaff("createStaff","CREATE_STAFF"),
		
		/** 记录首次创建的时间。. */
		createDate("createDate","CREATE_DATE"),
		
		/** 记录每次修改的员工标识。. */
		updateStaff("updateStaff","UPDATE_STAFF"),
		
		/** 记录每次修改的时间。. */
		updateDate("updateDate","UPDATE_DATE"),
		
		/** 记录状态. */
		statusCd("statusCd","STATUS_CD");

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
