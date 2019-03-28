package com.iwhalecloud.retail.goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * ProdSpecValues
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型prod_spec_values, 对应实体ProdSpecValues类")
public class ProdSpecValues implements Serializable {
    /**表名常量*/
    public static final String TNAME = "prod_spec_values";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 值ID
  	 */
	@TableId(type = IdType.ID_WORKER_STR)
	@ApiModelProperty(value = "值ID")
  	private String specValueId;
  	
  	/**
  	 * 规格ID
  	 */
	@ApiModelProperty(value = "规格ID")
  	private String specId;
  	
  	/**
  	 * 值名称
  	 */
	@ApiModelProperty(value = "值名称")
  	private String specValue;
  	
  	/**
  	 * 值类型
  	 */
	@ApiModelProperty(value = "值类型")
  	private Long specType;
  	
  	
  	//属性 end
  	
  	public static enum FieldNames{
        /** 值ID */
        specValueId,
        /** 规格ID */
        specId,
        /** 值名称 */
        specValue,
        /** 值类型 */
        specType
    }

	

}
