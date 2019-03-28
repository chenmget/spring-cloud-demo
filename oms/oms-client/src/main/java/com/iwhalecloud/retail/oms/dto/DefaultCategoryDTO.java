package com.iwhalecloud.retail.oms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 默认运营位及内容
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型DEFAULT_CATEGORY, 对应实体DefaultCategory类")
public class DefaultCategoryDTO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;


	//属性 begin
	/**
	 * ID
	 */
	@ApiModelProperty(value = "ID")
	private java.lang.Long id;

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
	private java.lang.String creator;

	/**
	 * 修改人
	 */
	@ApiModelProperty(value = "修改人")
	private java.lang.String modifier;

	/**
	 * 是否删除：0未删、1删除
	 */
	@ApiModelProperty(value = "是否删除：0未删、1删除	")
	private java.lang.Long isDeleted;

	/**
	 * 默认货架类目名称
	 */
	@ApiModelProperty(value = "默认货架类目名称")
	private java.lang.String categoryName;

	/**
	 * 类目展示样式：推荐、九宫格、轮播
	 */
	@ApiModelProperty(value = "类目展示样式：推荐、九宫格、轮播")
	private java.lang.String categoryStyle;

	/**
	 * 关联的货架模板编号
	 */
	@ApiModelProperty(value = "关联的货架模板编号")
	private java.lang.String goodsCategoryId;

	/**
	 * 对应的商品类别名称
	 */
	@ApiModelProperty(value = "对应的商品类别名称")
	private java.lang.String goodsCategoryName;

	/**
	 * 关联的货架模板编号
	 */
	@ApiModelProperty(value = "关联的货架模板编号")
	private java.lang.String shelfTemplatesNumber;

	private java.lang.String action;

}
