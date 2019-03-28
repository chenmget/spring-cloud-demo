package com.iwhalecloud.retail.partner.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 供应商供货产品信息
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("PAR_SUPPLY_PRODUCT_REL")
@KeySequence(value="seq_par_supply_product_rel_id",clazz = String.class)
@ApiModel(value = "对应模型PAR_SUPPLY_PRODUCT_REL, 对应实体SupplyProduct类")
public class SupplyProductRel implements Serializable {
    /**表名常量*/
    public static final String TNAME = "ES_SUPPLY_PRODUCT_REL";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * REL_ID
  	 */
	@TableId
	@ApiModelProperty(value = "REL_ID")
  	private java.lang.String relId;
  	
  	/**
  	 * SUPPLIER_ID
  	 */
	@ApiModelProperty(value = "SUPPLIER_ID")
  	private java.lang.String supplierId;
  	
  	/**
  	 * PRODUCT_ID
  	 */
	@ApiModelProperty(value = "PRODUCT_ID")
  	private java.lang.String productId;
  	
  	/**
  	 * SUPPLIER_PRICE
  	 */
	@ApiModelProperty(value = "SUPPLIER_PRICE")
  	private java.lang.String supplierPrice;

  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** REL_ID. */
		relId("relId","REL_ID"),
		
		/** SUPPLIER_ID. */
		supplierId("supplierId","SUPPLIER_ID"),
		
		/** PRODUCT_ID. */
		productId("productId","PRODUCT_ID"),
		
		/** SUPPLIER_PRICE. */
		supplierPrice("supplierPrice","SUPPLIER_PRICE");
		
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
