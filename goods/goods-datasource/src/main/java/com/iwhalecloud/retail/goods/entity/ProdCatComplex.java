package com.iwhalecloud.retail.goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * ProdCatComplex
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型prod_cat_complex, 对应实体ProdCatComplex类")
public class ProdCatComplex implements Serializable {
    /**表名常量*/
    public static final String TNAME = "prod_cat_complex";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * id
  	 */
	@ApiModelProperty(value = "id")
	@TableId(type = IdType.ID_WORKER_STR)
  	private String id;
  	
  	/**
  	 * catId
  	 */
	@ApiModelProperty(value = "catId")
  	private String catId;
  	
  	/**
  	 * goodsId
  	 */
	@ApiModelProperty(value = "goodsId")
  	private String goodsId;
  	
  	/**
  	 * 商品名称
  	 */
	@ApiModelProperty(value = "商品名称")
  	private String goodsName;
  	
  	/**
  	 * createTime
  	 */
	@ApiModelProperty(value = "createTime")
  	private java.util.Date createTime;
  	
  	
  	//属性 end
  	
  	public static enum FieldNames{
        /** id */
        id,
        /** catId */
        catId,
        /** goodsId */
        goodsId,
        /** 商品名称 */
        goodsName,
        /** createTime */
        createTime
    }

	

}
