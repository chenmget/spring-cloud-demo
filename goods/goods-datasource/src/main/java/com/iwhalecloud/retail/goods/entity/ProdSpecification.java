package com.iwhalecloud.retail.goods.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * ProdSpecification
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型prod_specification, 对应实体ProdSpecification类")
public class ProdSpecification implements Serializable {
    /**表名常量*/
    public static final String TNAME = "prod_specification";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 规格ID
  	 */
  	@TableId(type = IdType.ID_WORKER_STR)
	@ApiModelProperty(value = "规格ID")
  	private String specId;
  	
  	/**
  	 * 规格名称
  	 */
	@ApiModelProperty(value = "规格名称")
  	private String specName;
  	
  	/**
  	 * 规格类型
  	 */
	@ApiModelProperty(value = "规格类型")
  	private Long specType;
  	
  	/**
  	 * 规格描述
  	 */
	@ApiModelProperty(value = "规格描述")
  	private String specMemo;
  	
  	/**
  	 * 状态
  	 */
	@ApiModelProperty(value = "状态")
  	private Long disabled;
  	
  	
  	//属性 end
  	
  	public static enum FieldNames{
        /** 规格ID */
        specId,
        /** 规格名称 */
        specName,
        /** 规格类型 */
        specType,
        /** 规格描述 */
        specMemo,
        /** 状态 */
        disabled
    }

	

}
