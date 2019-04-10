package com.iwhalecloud.retail.promo.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * AccountBalanceRule
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("ACCOUNT_BALANCE_RULE")
@ApiModel(value = "对应模型ACCOUNT_BALANCE_RULE, 对应实体AccountBalanceRule类")
@KeySequence(value="seq_rule_id",clazz = String.class)
public class AccountBalanceRule implements Serializable {
    /**表名常量*/
    public static final String TNAME = "ACCOUNT_BALANCE_RULE";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 规则标识
  	 */
	@TableId
	@ApiModelProperty(value = "规则标识")
  	private java.lang.String ruleId;
  	
  	/**
  	 * 余额账本所属的余额类型。ACC-C-0009
  	 */
	@ApiModelProperty(value = "余额账本所属的余额类型。ACC-C-0009")
  	private String balanceTypeId;
  	
  	/**
  	 * 规则类型，10 返利使用规则
  	 */
	@ApiModelProperty(value = "规则类型，10 返利使用规则")
  	private String ruleType;
  	
  	/**
  	 * 余额对象类型，10 商家、20 产品
  	 */
	@ApiModelProperty(value = "余额对象类型，10 商家、20 产品")
  	private String objType;
  	
  	/**
  	 * 余额对象标识
  	 */
	@ApiModelProperty(value = "余额对象标识")
  	private String objId;
  	
  	/**
  	 * 生效时间
  	 */
	@ApiModelProperty(value = "生效时间")
  	private java.util.Date effDate;
  	
  	/**
  	 * 失效时间
  	 */
	@ApiModelProperty(value = "失效时间")
  	private java.util.Date expDate;
  	
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
	@ApiModelProperty(value = "记录创建的员工。")
  	private String createStaff;
  	
  	/**
  	 * 记录创建的时间。
  	 */
	@ApiModelProperty(value = "记录创建的时间。")
  	private java.util.Date createDate;
  	
  	/**
  	 * 记录修改的员工。
  	 */
	@ApiModelProperty(value = "记录修改的员工。")
  	private String updateStaff;
  	
  	/**
  	 * 记录修改的时间。
  	 */
	@ApiModelProperty(value = "记录修改的时间。")
  	private java.util.Date updateDate;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 规则标识. */
		ruleId("ruleId","RULE_ID"),
		
		/** 余额账本所属的余额类型。ACC-C-0009. */
		balanceTypeId("balanceTypeId","BALANCE_TYPE_ID"),
		
		/** 规则类型，10 返利使用规则. */
		ruleType("ruleType","RULE_TYPE"),
		
		/** 余额对象类型，10 商家、20 产品. */
		objType("objType","OBJ_TYPE"),
		
		/** 余额对象标识. */
		objId("objId","OBJ_ID"),
		
		/** 生效时间. */
		effDate("effDate","EFF_DATE"),
		
		/** 失效时间. */
		expDate("expDate","EXP_DATE"),
		
		/** 状态; 1000 有效，1100 失效. */
		statusCd("statusCd","STATUS_CD"),
		
		/** 状态变更的时间。. */
		statusDate("statusDate","STATUS_DATE"),
		
		/** 记录创建的员工。. */
		createStaff("createStaff","CREATE_STAFF"),
		
		/** 记录创建的时间。. */
		createDate("createDate","CREATE_DATE"),
		
		/** 记录修改的员工。. */
		updateStaff("updateStaff","UPDATE_STAFF"),
		
		/** 记录修改的时间。. */
		updateDate("updateDate","UPDATE_DATE");

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
