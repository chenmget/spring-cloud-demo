package com.iwhalecloud.retail.promo.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * AccountBalanceType
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("ACCOUNT_BALANCE_TYPE")
@ApiModel(value = "对应模型ACCOUNT_BALANCE_TYPE, 对应实体AccountBalanceType类")
@KeySequence(value="seq_balance_type_id",clazz = String.class)

public class AccountBalanceType implements Serializable {
    /**表名常量*/
    public static final String TNAME = "ACCOUNT_BALANCE_TYPE";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 余额账本所属的余额类型。ACC-C-0009
  	 */
	@TableId
	@ApiModelProperty(value = "余额账本所属的余额类型。ACC-C-0009")
  	private java.lang.String balanceTypeId;
  	
  	/**
  	 * 余额类型的名称，活动名称+返利，活动名称+价保
  	 */
	@ApiModelProperty(value = "余额类型的名称，活动名称+返利，活动名称+价保")
  	private String balanceTypeName;
  	
  	/**
  	 * 活动ID
  	 */
	@ApiModelProperty(value = "活动ID")
  	private String actId;
  	
  	/**
  	 * 状态; 1000 有效，1100 失效
  	 */
	@ApiModelProperty(value = "状态; 1000 有效，1100 失效")
  	private String statusCd;
  	
  	/**
  	 * 状态变更的时间。
  	 */
	@ApiModelProperty(value = "状态变更的时间。")
  	private java.util.Date statusDate;
  	
  	/**
  	 * 记录创建的员工。 
  	 */
	@ApiModelProperty(value = "记录创建的员工。 ")
  	private String createStaff;
  	
  	/**
  	 * 记录创建的时间。
  	 */
	@ApiModelProperty(value = "记录创建的时间。")
  	private java.util.Date createDate;
  	
  	/**
  	 * 记录修改的员工。 
  	 */
	@ApiModelProperty(value = "记录修改的员工。 ")
  	private String updateStaff;
  	
  	/**
  	 * 记录修改的时间。
  	 */
	@ApiModelProperty(value = "记录修改的时间。")
  	private java.util.Date updateDate;
  	
  	/**
  	 * 记录备注信息。
  	 */
	@ApiModelProperty(value = "记录备注信息。")
  	private String remark;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 余额账本所属的余额类型。ACC-C-0009. */
        balanceTypeId("balanceTypeId","BALANCE_TYPE_ID"),
		
		/** 余额类型的名称，活动名称+返利，活动名称+价保. */
		balanceTypeName("balanceTypeName","BALANCE_TYPE_NAME"),
		
		/** 活动ID. */
		actId("actId","ACT_ID"),
		
		/** 状态; 1000 有效，1100 失效. */
		statusCd("statusCd","STATUS_CD"),
		
		/** 状态变更的时间。. */
		statusDate("statusDate","STATUS_DATE"),
		
		/** 记录创建的员工。 . */
		createStaff("createStaff","CREATE_STAFF"),
		
		/** 记录创建的时间。. */
		createDate("createDate","CREATE_DATE"),
		
		/** 记录修改的员工。 . */
		updateStaff("updateStaff","UPDATE_STAFF"),
		
		/** 记录修改的时间。. */
		updateDate("updateDate","UPDATE_DATE"),
		
		/** 记录备注信息。. */
		remark("remark","REMARK");

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
