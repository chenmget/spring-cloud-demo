package com.iwhalecloud.retail.goods2b.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * ProdProduct
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("prod_product")
@KeySequence(value="seq_prod_product_id",clazz = String.class)
@ApiModel(value = "对应模型prod_product, 对应实体ProdProduct类")
public class Product implements Serializable {
    /**表名常量*/
    public static final String TNAME = "prod_product";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * productId
  	 */
	@TableId
	@ApiModelProperty(value = "productId")
  	private String productId;
  	
  	/**
  	 * 产品基本信息ID
  	 */
	@ApiModelProperty(value = "产品基本信息ID")
  	private String productBaseId;
  	
  	/**
  	 * 地包供货价下限
  	 */
	@ApiModelProperty(value = "地包供货价下限")
  	private Long localSupplyFeeLower;
  	
  	/**
  	 * 地包供货价上限
  	 */
	@ApiModelProperty(value = "地包供货价上限")
  	private Long localSupplyFeeUpper;
  	
  	/**
  	 * 非地包供货价下限
  	 */
	@ApiModelProperty(value = "非地包供货价下限")
  	private Long supplyFeeLower;
  	
  	/**
  	 * 非地包供货价上限
  	 */
	@ApiModelProperty(value = "非地包供货价上限")
  	private Long supplyFeeUpper;
  	
  	/**
  	 * 产品编码
  	 */
	@ApiModelProperty(value = "产品编码")
  	private String sn;

  	/**
  	 * 产品名称
  	 */
	@ApiModelProperty(value = "产品名称")
  	private String unitName;

  	/**
  	 * 销售价
  	 */
	@ApiModelProperty(value = "销售价")
  	private Double cost;
  	
  	/**
  	 * sku
  	 */
	@ApiModelProperty(value = "sku")
  	private String sku;
  	
  	/**
  	 * 颜色
  	 */
	@ApiModelProperty(value = "颜色")
  	private String color;
  	
  	/**
  	 * 容量
  	 */
	@ApiModelProperty(value = "容量")
  	private String memory;
  	
  	/**
  	 * 屏幕尺寸
  	 */
	@ApiModelProperty(value = "屏幕尺寸")
  	private String inch;
  	
  	/**
  	 * 规格1
  	 */
	@ApiModelProperty(value = "规格1")
  	private String attrValue1;
  	
  	/**
  	 * 规格2
  	 */
	@ApiModelProperty(value = "规格2")
  	private String attrValue2;
  	
  	/**
  	 * 规格3
  	 */
	@ApiModelProperty(value = "规格3")
  	private String attrValue3;
  	
  	/**
  	 * 规格4
  	 */
	@ApiModelProperty(value = "规格4")
  	private String attrValue4;
  	
  	/**
  	 * 规格5
  	 */
	@ApiModelProperty(value = "规格5")
  	private String attrValue5;
  	
  	/**
  	 * 规格6
  	 */
	@ApiModelProperty(value = "规格6")
  	private String attrValue6;
  	
  	/**
  	 * 规格7
  	 */
	@ApiModelProperty(value = "规格7")
  	private String attrValue7;
  	
  	/**
  	 * 规格8
  	 */
	@ApiModelProperty(value = "规格8")
  	private String attrValue8;
  	
  	/**
  	 * 规格9
  	 */
	@ApiModelProperty(value = "规格9")
  	private String attrValue9;
  	
  	/**
  	 * 规格10
  	 */
	@ApiModelProperty(value = "规格10")
  	private String attrValue10;
  	
  	/**
  	 * 状态:01 待提交，02审核中，03 已挂网，04 已退市
  	 */
	@ApiModelProperty(value = "状态:01 待提交，02审核中，03 已挂网，04 已退市")
  	private String status;
  	
  	/**
  	 * isDeleted
  	 */
	@ApiModelProperty(value = "isDeleted")
  	private String isDeleted;
  	
  	/**
  	 * 归属厂家
  	 */
	@ApiModelProperty(value = "归属厂家")
  	private String manufacturerId;
  	
  	/**
  	 * 创建人
  	 */
	@ApiModelProperty(value = "创建人")
  	private String createStaff;
  	
  	/**
  	 * createDate
  	 */
	@ApiModelProperty(value = "createDate")
  	private java.util.Date createDate;
  	
  	/**
  	 * 修改人
  	 */
	@ApiModelProperty(value = "修改人")
  	private String updateStaff;
  	
  	/**
  	 * updateDate
  	 */
	@ApiModelProperty(value = "updateDate")
  	private java.util.Date updateDate;

	/**
	 * sourceFrom
	 */
	@ApiModelProperty(value = "sourceFrom")
	private String sourceFrom;

	/**
	 * auditState
	 */
	@ApiModelProperty(value = "auditState")
	private String auditState;

	@ApiModelProperty(value = "采购类型")
	private String purchaseType;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** productId. */
		productId("productId","PRODUCT_ID"),
		
		/** 产品基本信息ID. */
		productBaseId("productBaseId","PRODUCT_BASE_ID"),
		
		/** 地包供货价下限. */
		localSupplyFeeLower("localSupplyFeeLower","local_supply_fee_lower"),
		
		/** 地包供货价上限. */
		localSupplyFeeUpper("localSupplyFeeUpper","local_supply_fee_upper"),
		
		/** 非地包供货价下限. */
		supplyFeeLower("supplyFeeLower","supply_fee_lower"),
		
		/** 非地包供货价上限. */
		supplyFeeUpper("supplyFeeUpper","supply_fee_upper"),
		
		/** 产品编码. */
		sn("sn","SN"),
		
		/** 销售价. */
		cost("cost","COST"),
		
		/** sku. */
		sku("sku","SKU"),
		
		/** 颜色. */
		color("color","COLOR"),
		
		/** 容量. */
		memory("memory","MEMORY"),
		
		/** 屏幕尺寸. */
		inch("inch","INCH"),
		
		/** 规格1. */
		attrValue1("attrValue1","ATTR_VALUE1"),
		
		/** 规格2. */
		attrValue2("attrValue2","ATTR_VALUE2"),
		
		/** 规格3. */
		attrValue3("attrValue3","ATTR_VALUE3"),
		
		/** 规格4. */
		attrValue4("attrValue4","ATTR_VALUE4"),
		
		/** 规格5. */
		attrValue5("attrValue5","ATTR_VALUE5"),
		
		/** 规格6. */
		attrValue6("attrValue6","ATTR_VALUE6"),
		
		/** 规格7. */
		attrValue7("attrValue7","ATTR_VALUE7"),
		
		/** 规格8. */
		attrValue8("attrValue8","ATTR_VALUE8"),
		
		/** 规格9. */
		attrValue9("attrValue9","ATTR_VALUE9"),
		
		/** 规格10. */
		attrValue10("attrValue10","ATTR_VALUE10"),
		
		/** 状态:01 待提交，02审核中，03 已挂网，04 已退市. */
		status("status","STATUS"),
		
		/** isDeleted. */
		isDeleted("isDeleted","is_deleted"),
		
		/** 归属厂家. */
		manufacturerId("manufacturerId","manufacturer_id"),
		
		/** 创建人. */
		createStaff("createStaff","create_staff"),
		
		/** createDate. */
		createDate("createDate","create_date"),
		
		/** 修改人. */
		updateStaff("updateStaff","update_staff"),
		
		/** updateDate. */
		updateDate("updateDate","update_date"),

		/** sourceFrom. */
		sourceFrom("sourceFrom","source_from"),

		/** purchaseType. */
		purchaseType("purchaseType","purchase_type"),

		/** auditState. */
		auditState("auditState","audit_state");

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
