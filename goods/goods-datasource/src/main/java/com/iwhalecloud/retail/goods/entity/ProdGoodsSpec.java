package com.iwhalecloud.retail.goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * ProdGoodsSpec
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型prod_goods_spec, 对应实体ProdGoodsSpec类")
public class ProdGoodsSpec implements Serializable {
    /**表名常量*/
    public static final String TNAME = "prod_goods_spec";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * relId
  	 */
	@TableId(type = IdType.ID_WORKER_STR)
	@ApiModelProperty(value = "relId")
  	private String relId;
  	
  	/**
  	 * 规格ID
  	 */
	@ApiModelProperty(value = "规格ID")
  	private String specId;
  	
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
  	 * 规格值ID
  	 */
	@ApiModelProperty(value = "规格值ID")
  	private String specValueId;
  	
  	
  	//属性 end
  	
  	public static enum FieldNames{
        /** relId */
        relId,
        /** 规格ID */
        specId,
        /** 商品ID */
        goodsId,
        /** 产品ID */
        productId,
        /** 规格值ID */
        specValueId
    }

	

}
