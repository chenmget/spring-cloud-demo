package com.iwhalecloud.retail.workflow.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * RuleDef
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("wf_rule_def")
@ApiModel(value = "对应模型wf_rule_def, 对应实体RuleDef类")
public class RuleDef implements Serializable {
    /**表名常量*/
    public static final String TNAME = "wf_rule_def";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * ruleId
  	 */
	@TableId(type = IdType.ID_WORKER)
	@ApiModelProperty(value = "ruleId")
  	private String ruleId;
  	
  	/**
  	 * attrId
  	 */
	@ApiModelProperty(value = "attrId")
  	private String attrId;
  	
  	/**
  	 * 规则表达式中的运算符号 1. 等于 2. 不等于 3. 大于 4. 大于等于 5. 小于 6. 小于等于
  	 */
	@ApiModelProperty(value = "规则表达式中的运算符号 1. 等于 2. 不等于 3. 大于 4. 大于等于 5. 小于 6. 小于等于")
  	private Long operSign;
  	
  	/**
  	 * attrValueId
  	 */
	@ApiModelProperty(value = "attrValueId")
  	private String attrValueId;
  	
  	/**
  	 * attrValue
  	 */
	@ApiModelProperty(value = "attrValue")
  	private String attrValue;
  	
  	/**
  	 * ruleExpression
  	 */
	@ApiModelProperty(value = "ruleExpression")
  	private String ruleExpression;
  	
  	/**
  	 * sort
  	 */
	@ApiModelProperty(value = "sort")
  	private Long sort;
  	
  	/**
  	 * createTime
  	 */
	@ApiModelProperty(value = "createTime")
  	private java.util.Date createTime;
  	
  	/**
  	 * createUserId
  	 */
	@ApiModelProperty(value = "createUserId")
  	private String createUserId;
  	
  	/**
  	 * updateTime
  	 */
	@ApiModelProperty(value = "updateTime")
  	private java.util.Date updateTime;
  	
  	/**
  	 * updateUserId
  	 */
	@ApiModelProperty(value = "updateUserId")
  	private String updateUserId;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** ruleId. */
		ruleId("ruleId","rule_id"),
		
		/** attRid. */
		attRid("attrId","attr_id"),
		
		/** 规则表达式中的运算符号 1. 等于 2. 不等于 3. 大于 4. 大于等于 5. 小于 6. 小于等于. */
		operSign("operSign","oper_sign"),
		
		/** attrValueId. */
		attrValueId("attrValueId","attr_value_id"),
		
		/** attrValue. */
		attrValue("attrValue","attr_value"),
		
		/** ruleExpression. */
		ruleExpression("ruleExpression","rule_expression"),
		
		/** sort. */
		sort("sort","sort"),
		
		/** createTime. */
		createTime("createTime","create_time"),
		
		/** createUserId. */
		createUserId("createUserId","create_user_id"),
		
		/** updateTime. */
		updateTime("updateTime","update_time"),
		
		/** updateUserId. */
		updateUserId("updateUserId","update_user_id");

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
