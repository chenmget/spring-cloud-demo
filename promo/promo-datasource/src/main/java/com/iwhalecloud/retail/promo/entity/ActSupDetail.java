package com.iwhalecloud.retail.promo.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * ActSupDetail
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("ACT_SUPPLEMENTARY_DETAIL")
@KeySequence(value = "seq_act_supplementary_detail_id", clazz = String.class)
@ApiModel(value = "对应模型ACT_SUPPLEMENTARY_DETAIL, 对应实体ActSupDetail类")
public class ActSupDetail implements Serializable {
    /**表名常量*/
    public static final String TNAME = "ACT_SUPPLEMENTARY_DETAIL";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 主键
  	 */
	@TableId
	@ApiModelProperty(value = "主键")
  	private String id;
  	
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
  	 * 补录记录ID
  	 */
	@ApiModelProperty(value = "补录记录ID")
  	private String recordId;
  	
  	/**
  	 * 商家类型，同商家表中商家类型
  	 */
	@ApiModelProperty(value = "商家类型，同商家表中商家类型")
  	private String merchantType;
  	
  	/**
  	 * 商家编码
  	 */
	@ApiModelProperty(value = "商家编码")
  	private String merchantCode;
  	/**
  	 * 商家名称
  	 */
	@ApiModelProperty(value = "商家名称")
  	private String merchantName;
  	
  	/**
  	 * 订单的创建时间
  	 */
	@ApiModelProperty(value = "订单的创建时间")
  	private java.util.Date orderTime;
  	
  	/**
  	 * 订单ID
  	 */
	@ApiModelProperty(value = "订单ID")
  	private String orderId;
  	
  	/**
  	 * 订单项ID
  	 */
	@ApiModelProperty(value = "订单项ID")
  	private String orderItemId;
  	
  	/**
  	 * 商品ID
  	 */
	@ApiModelProperty(value = "商品ID")
  	private String goodsId;
  	
  	/**
  	 * 产品ID
  	 */
	@ApiModelProperty(value = "产品ID")
  	private String productId;
  	
  	/**
  	 * 价格
  	 */
	@ApiModelProperty(value = "价格")
  	private Long price;
  	
  	/**
  	 * 优惠额或补贴额
  	 */
	@ApiModelProperty(value = "优惠额或补贴额")
  	private Long discount;
  	
  	/**
  	 * 订单中包含的串码
  	 */
	@ApiModelProperty(value = "订单中包含的串码")
  	private String resNbr;
  	
  	/**
  	 * 串码的发货时间
  	 */
	@ApiModelProperty(value = "串码的发货时间")
  	private java.util.Date shipTime;
  	
  	/**
  	 * 供应商编码
  	 */
	@ApiModelProperty(value = "供应商编码")
  	private String supplierCode;
  	
  	/**
  	 * 供应商名称
  	 */
	@ApiModelProperty(value = "供应商名称")
  	private String supplierName;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 主键. */
		id("id","ID"),
		
		/** 创建时间. */
		gmtCreate("gmtCreate","GMT_CREATE"),
		
		/** 修改时间. */
		gmtModified("gmtModified","GMT_MODIFIED"),
		
		/** 创建人. */
		creator("creator","CREATOR"),
		
		/** 修改人. */
		modifier("modifier","MODIFIER"),
		
		/** 补录记录ID. */
		recordId("recordId","RECORD_ID"),
		
		/** 商家类型，同商家表中商家类型. */
		merchantType("merchantType","MERCHANT_TYPE"),
		
		/** 商家编码. */
		merchantCode("merchantCode","MERCHANT_CODE"),
		
		/** 商家名称. */
		merchantName("merchantName","MERCHANT_NAME"),
		
		/** 订单的创建时间. */
		orderTime("orderTime","ORDER_TIME"),
		
		/** 订单ID. */
		orderId("orderId","ORDER_ID"),
		
		/** 订单项ID. */
		orderItemId("orderItemId","ORDER_ITEM_ID"),
		
		/** 商品ID. */
		goodsId("goodsId","GOODS_ID"),
		
		/** 产品ID. */
		productId("productId","PRODUCT_ID"),
		
		/** 价格. */
		price("price","PRICE"),
		
		/** 优惠额或补贴额. */
		discount("discount","DISCOUNT"),
		
		/** 订单中包含的串码. */
		resNbr("resNbr","RES_NBR"),
		
		/** 串码的发货时间. */
		shipTime("shipTime","SHIP_TIME"),
		
		/** 供应商编码. */
		supplierCode("supplierCode","SUPPLIER_CODE"),
		
		/** 供应商名称. */
		supplierName("supplierName","SUPPLIER_NAME");

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
