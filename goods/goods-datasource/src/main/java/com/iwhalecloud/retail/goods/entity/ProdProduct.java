package com.iwhalecloud.retail.goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel(value = "对应模型prod_product, 对应实体ProdProduct类")
public class ProdProduct implements Serializable {
    /**表名常量*/
    public static final String TNAME = "prod_product";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 产品ID
  	 */
	@TableId(type = IdType.ID_WORKER_STR)
	@ApiModelProperty(value = "产品ID")
  	private String productId;
  	
  	/**
  	 * 商品ID
  	 */
	@ApiModelProperty(value = "商品ID")
  	private String goodsId;
  	
  	/**
  	 * 产品名称
  	 */
	@ApiModelProperty(value = "产品名称")
  	private String name;
  	
  	/**
  	 * 产品编码
  	 */
	@ApiModelProperty(value = "产品编码")
  	private String sn;
  	
  	/**
  	 * 产品库存
  	 */
	@ApiModelProperty(value = "产品库存")
  	private Long store;
  	
  	/**
  	 * 市场价
  	 */
	@ApiModelProperty(value = "市场价")
  	private Double price;
  	
  	/**
  	 * 销售价
  	 */
	@ApiModelProperty(value = "销售价")
  	private Double cost;
  	
  	/**
  	 * 重量
  	 */
	@ApiModelProperty(value = "重量")
  	private Double weight;
  	
  	/**
  	 * specs
  	 */
	@ApiModelProperty(value = "specs")
  	private String specs;
  	
  	/**
  	 * 颜色
  	 */
	@ApiModelProperty(value = "颜色")
  	private String color;
  	
  	/**
  	 * sku
  	 */
	@ApiModelProperty(value = "sku")
  	private String sku;

	/**
	 * 放入回收站时为1，正常为0
	 */
	@ApiModelProperty(value = "放入回收站时为1，正常为0")
	private Long disabled;
  	
  	
  	//属性 end
  	
  	public static enum FieldNames{
        /** 产品ID */
        productId,
        /** 商品ID */
        goodsId,
        /** 产品名称 */
        name,
        /** 产品编码 */
        sn,
        /** 产品库存 */
        store,
        /** 市场价 */
        price,
        /** 销售价 */
        cost,
        /** 重量 */
        weight,
        /** specs */
        specs,
        /** 颜色 */
        color,
        /** sku */
        sku,
		/** disabled */
		disabled
    }

	

}
