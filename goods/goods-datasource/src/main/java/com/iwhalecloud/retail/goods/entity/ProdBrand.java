package com.iwhalecloud.retail.goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * ProdBrand
 * @author he.sw
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型prod_brand, 对应实体ProdBrand类")
public class ProdBrand implements Serializable {
    /**表名常量*/
    public static final String TNAME = "prod_brand";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 品牌ID
  	 */
	@ApiModelProperty(value = "品牌ID")
	@TableId(type = IdType.ID_WORKER_STR)
  	private String brandId;
  	
  	/**
  	 * 品牌名称
  	 */
	@ApiModelProperty(value = "品牌名称")
  	private String name;
  	
  	/**
  	 * 品牌网站
  	 */
	@ApiModelProperty(value = "品牌网站")
  	private String url;
  	
  	/**
  	 * 品牌编码
  	 */
	@ApiModelProperty(value = "品牌编码")
  	private String brandCode;
  	
  	/**
  	 * 品牌描述
  	 */
	@ApiModelProperty(value = "品牌描述")
  	private String brief;
  	
  	
  	//属性 end
  	
  	public static enum FieldNames{
        /** 品牌ID */
        brandId,
        /** 品牌名称 */
        name,
        /** 品牌网站 */
        url,
        /** 品牌编码 */
        brandCode,
        /** 品牌描述 */
        brief
    }

	

}
