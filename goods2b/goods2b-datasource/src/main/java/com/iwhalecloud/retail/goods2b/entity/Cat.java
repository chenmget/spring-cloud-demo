package com.iwhalecloud.retail.goods2b.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Cat
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型prod_cat, 对应实体ProdCat类")
@KeySequence(value="seq_prod_cat_cat_id",clazz = String.class)
@TableName("prod_cat")
public class Cat implements Serializable {
    /**表名常量*/
    public static final String TNAME = "prod_cat";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * catId
  	 */
	@ApiModelProperty(value = "catId")
	@TableId
  	private String catId;

	@ApiModelProperty(value = "类别名称")
	private String catName;

	@ApiModelProperty(value = "上级类别ID")
	private String parentCatId;

	@ApiModelProperty(value = "类别路径")
	private String catPath;

	@ApiModelProperty(value = "排序")
	private Integer catOrder;

	@ApiModelProperty(value = "类型ID")
	private String typeId;

	@ApiModelProperty(value = "是否删除")
	private String isDeleted;

	@ApiModelProperty(value = "创建人")
	private String createStaff;

	@ApiModelProperty(value = "创建时间")
	private Date createDate;

	@ApiModelProperty(value = "修改人")
	private String updateStaff;

	@ApiModelProperty(value = "修改时间")
	private Date updateDate;

	@ApiModelProperty(value = "数据来源")
	private String sourceFrom;
  	
  	
  	//属性 end
  	
  	public static enum FieldNames{
        /** catId */
        catId,
        /** 类别名称 */
		catName,
        /** 上级类别ID */
		parentCatId,
        /** 类别路径 */
		catPath,
        /** 排序 */
        catOrder,
		/** 类型ID */
		typeId,
		/** 是否删除 */
		isDeleted,
		/** 创建人 */
		createStaff,
		/** 创建时间 */
		createDate,
		/** 修改人 */
		updateStaff,
		/** 修改时间 */
		updateDate,
		/** 数据来源 */
		sourceFrom
    }

	

}
