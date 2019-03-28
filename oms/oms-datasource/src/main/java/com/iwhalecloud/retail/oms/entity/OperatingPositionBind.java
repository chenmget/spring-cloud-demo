package com.iwhalecloud.retail.oms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 运营位关联表
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("OPERATING_POSITION_BIND")
@ApiModel(value = "对应模型OPERATING_POSITION_BIND, 对应实体OperatingPositionBind类")
public class OperatingPositionBind implements Serializable {
    /**表名常量*/
    public static final String TNAME = "OPERATING_POSITION_BIND";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * ID
  	 */
	@TableId(type = IdType.ID_WORKER)
	@ApiModelProperty(value = "ID")
  	private java.lang.Long id;
  	
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
  	private java.lang.String creator;
  	
  	/**
  	 * 修改人	
  	 */
	@ApiModelProperty(value = "修改人	")
  	private java.lang.String modifier;
  	
  	/**
  	 * 是否删除：0未删、1删除	
  	 */
	@ApiModelProperty(value = "是否删除：0未删、1删除	")
  	private java.lang.Integer isDeleted;
  	
  	/**
  	 * 运营位ID	
  	 */
	@ApiModelProperty(value = "运营位ID	")
  	private java.lang.String operatingPositionId;
  	
  	/**
  	 * 商品编码	
  	 */
	@ApiModelProperty(value = "商品编码	")
  	private java.lang.String productNumber;
  	
  	/**
  	 * 内容编码	
  	 */
	@ApiModelProperty(value = "内容编码	")
  	private java.lang.String contentNumber;

	@ApiModelProperty(value = "所属厅店")
	private String adscriptionShopId; //所属厅店

  	//属性 end


	/** 字段名称枚举. */
	public enum FieldNames {
		/** ID. */
		id("id","ID"),

		/** 创建时间. */
		gmtCreate("gmtCreate","GMT_CREATE"),

		/** 修改时间. */
		gmtModified("gmtModified","GMT_MODIFIED"),

		/** 创建人. */
		creator("creator","CREATOR"),

		/** 修改人	. */
		modifier("modifier","MODIFIER"),

		/** 是否删除：0未删、1删除	. */
		isDeleted("isDeleted","IS_DELETED"),

		/** 运营位ID	. */
		operatingPositionId("operatingPositionId","OPERATING_POSITION_ID"),

		/** 商品编码	. */
		productNumber("productNumber","PRODUCT_NUMBER"),

		/** 内容编码	. */
		contentNumber("contentNumber","CONTENT_NUMBER"),

		/** 所属厅店. */
		adscriptionShopId("adscriptionShopId","ADSCRIPTION_SHOP_ID");

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
