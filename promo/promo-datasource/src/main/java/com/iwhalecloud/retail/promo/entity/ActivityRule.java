package com.iwhalecloud.retail.promo.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * ActivityRule
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("act_activity_rule")
@KeySequence(value = "seq_act_activity_rule_id", clazz = String.class)
@ApiModel(value = "对应模型act_activity_rule, 对应实体ActivityRule类")
public class ActivityRule implements Serializable {
    /**表名常量*/
    public static final String TNAME = "act_activity_rule";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * ID
  	 */
	@TableId
	@ApiModelProperty(value = "ID")
  	private java.lang.String id;
  	
  	/**
  	 * 营销活动code
  	 */
	@ApiModelProperty(value = "营销活动Id")
  	private java.lang.String marketingActivityId;
  	
  	/**
  	 * ruleId
  	 */
	@ApiModelProperty(value = "ruleId")
  	private java.lang.String ruleId;
  	
  	/**
  	 * ruleName
  	 */
	@ApiModelProperty(value = "ruleName")
  	private java.lang.String ruleName;
  	
  	/**
  	 * remark
  	 */
	@ApiModelProperty(value = "remark")
  	private java.lang.String remark;
  	
  	/**
  	 * settlementRule
  	 */
	@ApiModelProperty(value = "settlementRule")
  	private java.lang.String settlementRule;
  	
  	/**
  	 * useStartTime
  	 */
	@ApiModelProperty(value = "useStartTime")
  	private java.util.Date useStartTime;
  	
  	/**
  	 * useEndTime
  	 */
	@ApiModelProperty(value = "useEndTime")
  	private java.util.Date useEndTime;
  	
  	/**
  	 * deductionScale
  	 */
	@ApiModelProperty(value = "deductionScale")
  	private java.lang.String deductionScale;
  	
  	/**
  	 * calculationRule
  	 */
	@ApiModelProperty(value = "calculationRule")
  	private java.lang.String calculationRule;

	@ApiModelProperty(value = "创建人。")
	private java.lang.String creator;

	/**
	 * 记录首次创建的时间。
	 */
	@ApiModelProperty(value = "创建时间。")
	private java.util.Date gmtCreate;
	/**
	 * 记录每次修改的员工标识。
	 */
	@ApiModelProperty(value = "修改人。")
	private java.lang.String modifier;

	/**
	 * 记录每次修改的时间。
	 */
	@ApiModelProperty(value = "修改时间。")
	private java.util.Date gmtModified;
	/**
	 * 是否删除：0未删、1删除。
	 */
	@ApiModelProperty(value = "是否删除：0未删、1删除。")
	private String isDeleted;
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** ID. */
		id("id","ID"),
		
		/** 营销活动ID. */
		marketingActivityId("marketingActivityId","MARKETING_ACTIVITY_ID"),
		
		/** ruleId. */
		ruleId("ruleId","RULE_ID"),
		
		/** ruleName. */
		ruleName("ruleName","RULE_NAME"),
		
		/** remark. */
		remark("remark","REMARK"),
		
		/** settlementRule. */
		settlementRule("settlementRule","SETTLEMENT_RULE"),
		
		/** useStartTime. */
		useStartTime("useStartTime","USE_START_TIME"),
		
		/** useEndTime. */
		useEndTime("useEndTime","USE_END_TIME"),
		
		/** deductionScale. */
		deductionScale("deductionScale","DEDUCTION_SCALE"),
		
		/** calculationRule. */
		calculationRule("calculationRule","CALCULATION_RULE"),

		/** 是否删除：0未删、1删除。. */
		isDeleted("isDeleted","IS_DELETED"),

		/** 记录首次创建的员工标识。. */
		creator("creator","CREATOR"),

		/** 记录首次创建的时间。. */
		gmtCreate("gmtCreate","GMT_CREATE"),

		/** 记录每次修改的员工标识。. */
		modifier("modifier","MODIFIER"),

		/** 记录每次修改的时间。. */
		gmtModified("gmtModified","GMT_MODIFIED");

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
