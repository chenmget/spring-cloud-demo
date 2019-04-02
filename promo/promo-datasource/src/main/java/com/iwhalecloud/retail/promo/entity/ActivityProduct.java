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
 * ActivityProduct
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("act_activity_product")
@KeySequence(value = "seq_act_activity_product_id",clazz = String.class)
@ApiModel(value = "对应模型act_activity_product, 对应实体ActivityProduct类")
public class ActivityProduct implements Serializable {
    /**表名常量*/
    public static final String TNAME = "act_activity_product";
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
  	 * 产品基础表
  	 */
	@ApiModelProperty(value = "产品基础表")
  	private java.lang.String productBaseId;
  	
  	/**
  	 * 产品ID
  	 */
	@ApiModelProperty(value = "产品ID")
  	private java.lang.String productId;
  	
  	/**
  	 * 价格
  	 */
	@ApiModelProperty(value = "价格")
  	private java.lang.Long price;
  	
  	/**
  	 * 预付价格
  	 */
	@ApiModelProperty(value = "预付价格")
  	private java.lang.Long prePrice;
  	
  	/**
  	 * 返利金额
  	 */
	@ApiModelProperty(value = "返利金额")
  	private java.lang.Long rebatePrice;
  	
  	/**
  	 * 优惠方式为直减时，保存减免的金额
  	 */
	@ApiModelProperty(value = "优惠方式为直减时，保存减免的金额")
  	private java.lang.Long discountAmount;
  	
  	/**
  	 * 数量
  	 */
	@ApiModelProperty(value = "数量")
  	private java.lang.Long num;
  	
  	/**
  	 * 达量
  	 */
	@ApiModelProperty(value = "达量")
  	private java.lang.Long reachAmount;

  	
  	/**
  	 * 供应商编码
  	 */
	@ApiModelProperty(value = "供应商编码")
  	private java.lang.String supplierCode;

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

	/**
	 * 记录数据来源
	 */
	@ApiModelProperty(value = "记录数据来源。")
	private java.lang.String sourceFrom;

	/**
	 * 是否限制产品参与总数量
	 1.限制
	 0.不限制
	 */
	@ApiModelProperty(value = "产品参与总数量限制标识")
	private String numLimitFlg;
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** ID. */
		id("id","ID"),
		
		/** 营销活动code. */
		marketingActivityId("marketingActivityId","MARKETING_ACTIVITY_ID"),
		
		/** 产品基础表. */
		productBaseId("productBaseId","PRODUCT_BASE_ID"),
		
		/** 产品ID. */
		productId("productId","PRODUCT_ID"),
		
		/** 价格. */
		price("price","PRICE"),
		
		/** 预付价格. */
		prePrice("prePrice","PRE_PRICE"),
		
		/** 返利金额. */
		rebatePrice("rebatePrice","REBATE_PRICE"),
		
		/** 优惠方式为直减时，保存减免的金额. */
		discountAmount("discountAmount","DISCOUNT_AMOUNT"),
		
		/** 数量. */
		num("num","NUM"),
		
		/** 达量. */
		reachAmount("reachAmount","REACH_AMOUNT"),
		

		
		/** 供应商编码. */
		supplierCode("supplierCode","SUPPLIER_CODE"),

		/** 是否删除：0未删、1删除。. */
		isDeleted("isDeleted","IS_DELETED"),

		/** 记录首次创建的员工标识。. */
		creator("creator","CREATOR"),

		/** 记录首次创建的时间。. */
		gmtCreate("gmtCreate","GMT_CREATE"),

		/** 记录每次修改的员工标识。. */
		modifier("modifier","MODIFIER"),

		/** 记录每次修改的时间。. */
		gmtModified("gmtModified","GMT_MODIFIED"),

		/** 记录数据来源 */
		sourceFrom("sourceFrom","SOURCE_FROM"),

		/**是否限制产品参与总数量
		 1.限制
		 0.不限制*/
		numLimitFlg("numLimitFlg","NUM_LIMIT_FLG");

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
