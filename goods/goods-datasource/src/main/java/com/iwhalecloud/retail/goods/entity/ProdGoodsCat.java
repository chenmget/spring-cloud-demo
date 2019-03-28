package com.iwhalecloud.retail.goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * ProdGoodsCat
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型prod_goods_cat, 对应实体ProdGoodsCat类")
public class ProdGoodsCat implements Serializable {
    /**表名常量*/
    public static final String TNAME = "prod_goods_cat";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * catId
  	 */
	@ApiModelProperty(value = "catId")
	@TableId(type = IdType.ID_WORKER_STR)
  	private String catId;
  	
  	/**
  	 * 类别名称
  	 */
	@ApiModelProperty(value = "类别名称")
  	private String name;
  	
  	/**
  	 * 上级类别ID
  	 */
	@ApiModelProperty(value = "上级类别ID")
  	private Long parentId;
  	
  	/**
  	 * 类别路径
  	 */
	@ApiModelProperty(value = "类别路径")
  	private String catPath;
  	
  	/**
  	 * 排序
  	 */
	@ApiModelProperty(value = "排序")
  	private Long catOrder;
  	
  	
  	//属性 end
  	
  	public static enum FieldNames{
        /** catId */
        catId,
        /** 类别名称 */
        name,
        /** 上级类别ID */
        parentId,
        /** 类别路径 */
        catPath,
        /** 排序 */
        catOrder
    }

	

}
