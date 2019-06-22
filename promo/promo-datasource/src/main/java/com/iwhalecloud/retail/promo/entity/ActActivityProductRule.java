package com.iwhalecloud.retail.promo.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * ActActivityProductRule
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("ACT_ACTIVITY_PRODUCT_RULE")
@ApiModel(value = "对应模型ACT_ACTIVITY_PRODUCT_RULE, 对应实体ActActivityProductRule类")
@KeySequence(value="seq_retail_all_tables",clazz = String.class)

public class ActActivityProductRule implements Serializable {
    /**表名常量*/
    public static final String TNAME = "ACT_ACTIVITY_PRODUCT_RULE";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * ID
  	 */
	@TableId
	@ApiModelProperty(value = "ID")
  	private String id;
  	
  	/**
  	 * 参与活动产品关联ID
  	 */
	@ApiModelProperty(value = "参与活动产品关联ID")
  	private String actProdRelId;
  	
  	/**
  	 * 产品ID
  	 */
	@ApiModelProperty(value = "产品ID")
  	private String productId;
  	
  	/**
  	 * 表达式
  	 */
	@ApiModelProperty(value = "表达式")
  	private String expression;
  	
  	/**
  	 * 规则条件
  	 */
	@ApiModelProperty(value = "规则条件")
  	private String ruleAmount;
  	
  	/**
  	 * 优惠金额
  	 */
	@ApiModelProperty(value = "优惠金额")
  	private String price;
  	
  	/**
  	 * 创建时间
  	 */
	@ApiModelProperty(value = "创建时间")
  	private java.util.Date gmtCreate;
  	
  	/**
  	 * 修改时间
  	 */
	@ApiModelProperty(value = "修改时间")
  	private java.util.Date gmtModified;
  	
  	/**
  	 * 创建人
  	 */
	@ApiModelProperty(value = "创建人")
  	private String creator;
  	
  	/**
  	 * 修改人
  	 */
	@ApiModelProperty(value = "修改人")
  	private String modifier;
  	
  	/**
  	 * sourceFrom
  	 */
	@ApiModelProperty(value = "sourceFrom")
  	private String sourceFrom;
  	
  	/**
  	 * 是否删除：0未删、1删除
  	 */
	@ApiModelProperty(value = "是否删除：0未删、1删除")
  	private String isDeleted;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** ID. */
		id("id","ID"),
		
		/** 参与活动产品关联ID. */
		actProdRelId("actProdRelId","ACT_PROD_REL_ID"),
		
		/** 产品ID. */
		productId("productId","PRODUCT_ID"),
		
		/** 表达式. */
		expression("expression","EXPRESSION"),
		
		/** 规则条件. */
		ruleAmount("ruleAmount","RULE_AMOUNT"),
		
		/** 优惠金额. */
		price("price","PRICE"),
		
		/** 创建时间. */
		gmtCreate("gmtCreate","GMT_CREATE"),
		
		/** 修改时间. */
		gmtModified("gmtModified","GMT_MODIFIED"),
		
		/** 创建人. */
		creator("creator","CREATOR"),
		
		/** 修改人. */
		modifier("modifier","MODIFIER"),
		
		/** sourceFrom. */
		sourceFrom("sourceFrom","SOURCE_FROM"),
		
		/** 是否删除：0未删、1删除. */
		isDeleted("isDeleted","IS_DELETED");

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
