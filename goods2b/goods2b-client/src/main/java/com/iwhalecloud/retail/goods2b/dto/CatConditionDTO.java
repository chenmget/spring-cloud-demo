package com.iwhalecloud.retail.goods2b.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * CatCondition
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型prod_cat_condition, 对应实体CatCondition类")
public class CatConditionDTO implements java.io.Serializable {

  	//属性 begin
	/**
  	 * 主键ID
  	 */
	@ApiModelProperty(value = "主键ID")
  	private String id;
	
	/**
  	 * 产品类别ID
  	 */
	@ApiModelProperty(value = "产品类别ID(prod_cat表主键)")
	private String catId;
	
	/**
  	 * 商品类型关联的筛选条件类型 1. 产品类型 2. 产品属性 3. 品牌 4. 营销活动类型 5. 产品标签
  	 */
	@ApiModelProperty(value = "商品类型关联的筛选条件类型 1. 产品类型 2. 产品属性 3. 品牌 4. 营销活动类型 5. 产品标签")
  	private String relType;
	
	/**
  	 *   商品类型关联类型为以下类型时，记录关联的对象ID 1. 产品类型 时 记录产品类型ID 2. 产品属性 时 记录产品属性ID 3. 品牌 时 记录品牌ID 4. 营销活动类型 时 记录营销活动类型ID 5. 产品标签 时 记录产品标签ID
  	 */
	@ApiModelProperty(value = "  商品类型关联类型为以下类型时，记录关联的对象ID 1. 产品类型 时 记录产品类型ID 2. 产品属性 时 记录产品属性ID 3. 品牌 时 记录品牌ID 4. 营销活动类型 时 记录营销活动类型ID 5. 产品标签 时 记录产品标签ID")
  	private String relObjId;
	
	/**
  	 * 商品类型关联类型 产品属性时记录属性的值
  	 */
	@ApiModelProperty(value = "商品类型关联类型 产品属性时记录属性的值")
  	private String relObjValue;
	
	/**
  	 * 排序
  	 */
	@ApiModelProperty(value = "排序")
  	private Long order;
	
	/**
  	 * 创建人
  	 */
	@ApiModelProperty(value = "创建人")
  	private String createStaff;
	
	/**
  	 * 创建时间
  	 */
	@ApiModelProperty(value = "创建时间")
  	private java.util.Date createDate;
	
	/**
  	 * 修改人
  	 */
	@ApiModelProperty(value = "修改人")
  	private String updateStaff;
	
	/**
  	 * 修改时间
  	 */
	@ApiModelProperty(value = "修改时间")
  	private java.util.Date updateDate;
	
  	
}
