package com.iwhalecloud.retail.member.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * MemberLevel
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("mem_member_level")
@KeySequence(value = "seq_mem_member_level_id", clazz = String.class)
@ApiModel(value = "对应模型mem_member_level, 对应实体MemberLevel类")
public class MemberLevel implements Serializable {
    /**表名常量*/
    public static final String TNAME = "mem_member_level";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 等级ID
  	 */
	@TableId
	@ApiModelProperty(value = "等级ID")
  	private String lvId;
  	
  	/**
  	 * 等级名称
  	 */
	@ApiModelProperty(value = "等级名称")
  	private String lvName;
  	
  	/**
  	 * 商家ID
  	 */
	@ApiModelProperty(value = "商家ID")
  	private String merchantId;
  	
  	/**
  	 * 折扣  如 80%   存的是80
  	 */
	@ApiModelProperty(value = "折扣  如 80%   存的是80")
  	private java.math.BigDecimal discount;
  	
  	/**
  	 * 起始积分
  	 */
	@ApiModelProperty(value = "起始积分")
  	private Long pointStart;
  	
  	/**
  	 * 结束积分
  	 */
	@ApiModelProperty(value = "结束积分")
  	private Long pointEnd;
  	
  	/**
  	 * 展示名称
  	 */
	@ApiModelProperty(value = "展示名称")
  	private String showName;
  	
  	/**
  	 * 是否默认等级 1 是   0否    每个商户只能有一个默认等级
  	 */
	@ApiModelProperty(value = "是否默认等级 1 是   0否    每个商户只能有一个默认等级")
  	private String isDefault;
  	
  	/**
  	 * 平台类型
  	 */
	@ApiModelProperty(value = "平台类型")
  	private String sourceFrom;
  	
  	/**
  	 * 状态  1有效  0无效
  	 */
	@ApiModelProperty(value = "状态  1有效  0无效")
  	private String status;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 等级ID. */
		lvId("lvId","LV_ID"),
		
		/** 等级名称. */
		lvName("lvName","LV_NAME"),
		
		/** 商家ID. */
		merchantId("merchantId","MERCHANT_ID"),
		
		/** 折扣  如 80%   存的是80. */
		discount("discount","DISCOUNT"),
		
		/** 起始积分. */
		pointStart("pointStart","POINT_START"),
		
		/** 结束积分. */
		pointEnd("pointEnd","POINT_END"),
		
		/** 展示名称. */
		showName("showName","SHOW_NAME"),
		
		/** 是否默认等级 1 是   0否    每个商户只能有一个默认等级. */
		isDefault("isDefault","IS_DEFAULT"),
		
		/** 平台类型. */
		sourceFrom("sourceFrom","SOURCE_FROM"),
		
		/** 状态  1有效  0无效. */
		status("status","STATUS");

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
