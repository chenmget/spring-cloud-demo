package com.iwhalecloud.retail.goods2b.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * ProdProductBase
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("prod_product_base")
@KeySequence(value="seq_prod_product_base_id",clazz = String.class)
@ApiModel(value = "对应模型prod_product_base, 对应实体ProdProductBase类")
public class ProductBase implements Serializable {
    /**表名常量*/
    public static final String TNAME = "prod_product_base";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * productBaseId
  	 */
	@TableId
	@ApiModelProperty(value = "productBaseId")
  	private String productBaseId;
  	
  	/**
	 * 产品类别
	 */
	@ApiModelProperty(value = "产品类别")
	private String catId;

	/**
	 * 产品分类
	 */
	@ApiModelProperty(value = "产品类型")
	private String typeId;
  	
  	/**
  	 * 品牌
  	 */
	@ApiModelProperty(value = "品牌")
  	private String brandId;
  	
  	/**
  	 * 产品名称
  	 */
	@ApiModelProperty(value = "产品名称")
  	private String productName;
  	
  	/**
  	 * 产品型号
  	 */
	@ApiModelProperty(value = "产品型号")
  	private String unitType;
  	
  	/**
  	 * 型号名称
  	 */
	@ApiModelProperty(value = "型号名称")
  	private String unitTypeName;
  	
  	/**
  	 * effDate
  	 */
	@ApiModelProperty(value = "effDate")
  	private java.util.Date effDate;
  	
  	/**
  	 * expDate
  	 */
	@ApiModelProperty(value = "expDate")
  	private java.util.Date expDate;
  	
  	/**
  	 * 是否有串码
  	 */
	@ApiModelProperty(value = "是否有串码")
  	private String isImei;
  	
  	/**
  	 * 是否推送ITMS
  	 */
	@ApiModelProperty(value = "是否推送ITMS")
  	private String isItms;
  	
  	/**
  	 * 是否需要CT码
  	 */
	@ApiModelProperty(value = "是否需要CT码")
  	private String isCtCode;
	
  	/**
  	 * 是否需要抽检
  	 */
	@ApiModelProperty(value = "是否需要抽检")
  	private String isInspection;

	/**
	 * 是否需要抽检
	 */
	@ApiModelProperty(value = "是否固网产品")
	private String isFixedLine;

  	/**
  	 * 产品编码
  	 */
	@ApiModelProperty(value = "产品编码")
  	private String productCode;
  	
  	/**
  	 * 单位
  	 */
	@ApiModelProperty(value = "单位")
  	private String unit;

	/**
	 * 换货对象
	 */
	@ApiModelProperty(value = "换货对象")
	private String exchangeObject;

//	/**
//	 * 采购类型
//	 */
//	@ApiModelProperty(value = "采购类型")
//	private String purchaseType;
  	
  	/**
  	 * 参数1
  	 */
	@ApiModelProperty(value = "参数1")
  	private String param1;
  	
  	/**
  	 * 参数2
  	 */
	@ApiModelProperty(value = "参数2")
  	private String param2;
  	
  	/**
  	 * 参数3
  	 */
	@ApiModelProperty(value = "参数3")
  	private String param3;
  	
  	/**
  	 * 参数4
  	 */
	@ApiModelProperty(value = "参数4")
  	private String param4;
  	
  	/**
  	 * 参数5
  	 */
	@ApiModelProperty(value = "参数5")
  	private String param5;
  	
  	/**
  	 * 参数6
  	 */
	@ApiModelProperty(value = "参数6")
  	private String param6;
  	
  	/**
  	 * 参数7
  	 */
	@ApiModelProperty(value = "参数7")
  	private String param7;
  	
  	/**
  	 * 参数8
  	 */
	@ApiModelProperty(value = "参数8")
  	private String param8;
  	
  	/**
  	 * 参数9
  	 */
	@ApiModelProperty(value = "参数9")
  	private String param9;
  	
  	/**
  	 * 参数10
  	 */
	@ApiModelProperty(value = "参数10")
  	private String param10;
  	
  	/**
  	 * 参数11
  	 */
	@ApiModelProperty(value = "参数11")
  	private String param11;
  	
  	/**
  	 * 参数12
  	 */
	@ApiModelProperty(value = "参数12")
  	private String param12;
  	
  	/**
  	 * 参数13
  	 */
	@ApiModelProperty(value = "参数13")
  	private String param13;
  	
  	/**
  	 * 参数14
  	 */
	@ApiModelProperty(value = "参数14")
  	private String param14;
  	
  	/**
  	 * 参数15
  	 */
	@ApiModelProperty(value = "参数15")
  	private String param15;
  	
  	/**
  	 * 参数16
  	 */
	@ApiModelProperty(value = "参数16")
  	private String param16;
  	
  	/**
  	 * 参数17
  	 */
	@ApiModelProperty(value = "参数17")
  	private String param17;
  	
  	/**
  	 * 参数18
  	 */
	@ApiModelProperty(value = "参数18")
  	private String param18;
  	
  	/**
  	 * 参数19
  	 */
	@ApiModelProperty(value = "参数19")
  	private String param19;
  	
  	/**
  	 * 参数20
  	 */
	@ApiModelProperty(value = "参数20")
  	private String param20;
  	
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
	 * 平均供货价
	 */
	@ApiModelProperty(value = "平均供货价")
	private Double avgSupplyPrice;

	/**
	 * 价格档位
	 */
	@ApiModelProperty(value = "价格档位")
	private String priceLevel;

  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** productBaseId. */
		productBaseId("productBaseId","PRODUCT_BASE_ID"),
		
		/** 产品分类. */
		catId("catId","CAT_ID"),
		
		/** 品牌. */
		brandId("brandId","BRAND_ID"),
		
		/** 产品名称. */
		productName("productName","PRODUCT_NAME"),
		
		/** 产品型号. */
		unitType("unitType","UNIT_TYPE"),
		
		/** 型号名称. */
		unitTypeName("unitTypeName","UNIT_TYPE_NAME"),
		
		/** effDate. */
		effDate("effDate","eff_date"),
		
		/** expDate. */
		expDate("expDate","exp_date"),
		
		/** 是否有串码. */
		isImei("isImei","IS_IMEI"),
		
		/** 是否推送ITMS. */
		isItms("isItms","IS_ITMS"),
		
		/** 是否需要CT码. */
		isCtCode("isCtCode","IS_CT_CODE"),

		/** 是否需要抽检. */
		isInspection("isInspection","IS_INSPECTION"),

		/** 是否固网产品. */
		isFixedLine("isFixedLine","IS_FIXED_LINE"),
		
		/** 产品编码. */
		productCode("productCode","PRODUCT_CODE"),
		
		/** 单位. */
		unit("unit","UNIT"),

		/** 换货对象. */
		exchangeObject ("exchangeObject ","EXCHANGE_OBJECT"),

		/** 采购类型. */
		purchaseType("purchaseType","PURCHASE_TYPE"),
		
		/** 参数1. */
		param1("param1","PARAM1"),
		
		/** 参数2. */
		param2("param2","PARAM2"),
		
		/** 参数3. */
		param3("param3","PARAM3"),
		
		/** 参数4. */
		param4("param4","PARAM4"),
		
		/** 参数5. */
		param5("param5","PARAM5"),
		
		/** 参数6. */
		param6("param6","PARAM6"),
		
		/** 参数7. */
		param7("param7","PARAM7"),
		
		/** 参数8. */
		param8("param8","PARAM8"),
		
		/** 参数9. */
		param9("param9","PARAM9"),
		
		/** 参数10. */
		param10("param10","PARAM10"),
		
		/** 参数11. */
		param11("param11","PARAM11"),
		
		/** 参数12. */
		param12("param12","PARAM12"),
		
		/** 参数13. */
		param13("param13","PARAM13"),
		
		/** 参数14. */
		param14("param14","PARAM14"),
		
		/** 参数15. */
		param15("param15","PARAM15"),
		
		/** 参数16. */
		param16("param16","PARAM16"),
		
		/** 参数17. */
		param17("param17","PARAM17"),
		
		/** 参数18. */
		param18("param18","PARAM18"),
		
		/** 参数19. */
		param19("param19","PARAM19"),
		
		/** 参数20. */
		param20("param20","PARAM20"),
		
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

		/** priceLevel. */
		priceLevel("priceLevel","price_level"),

		/**平均供货价*/
		avgSupplyPrice("avgSupplyPrice","AVG_SUPPLY_PRICE");

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
