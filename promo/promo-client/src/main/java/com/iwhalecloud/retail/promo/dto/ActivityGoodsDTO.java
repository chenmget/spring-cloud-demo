package com.iwhalecloud.retail.promo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * ActivityGoods
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型act_activity_goods, 对应实体ActivityGoods类")
public class ActivityGoodsDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * ID
  	 */
	@ApiModelProperty(value = "ID")
  	private java.lang.String id;
	
	/**
  	 * 营销活动code
  	 */
	@ApiModelProperty(value = "营销活动code")
  	private java.lang.String marketingActivityCode;
	
	/**
  	 * 商品id
  	 */
	@ApiModelProperty(value = "商品id")
  	private java.lang.String goodsId;
	
	/**
  	 * 商品名称
  	 */
	@ApiModelProperty(value = "商品名称")
  	private java.lang.String goodsName;
	
	/**
  	 * 是否自动上下架：0否、1是
  	 */
	@ApiModelProperty(value = "是否自动上下架：0否、1是")
  	private java.lang.Byte isAutoMarket;

	@ApiModelProperty(value = "创建人。")
	private java.lang.String creator;

	/**
	 * 记录首次创建的时间。
	 */
	@ApiModelProperty(value = "创建时间。")
	private java.util.Date gmt_create;
	/**
	 * 记录每次修改的员工标识。
	 */
	@ApiModelProperty(value = "修改人。")
	private java.lang.String modifier;

	/**
	 * 记录每次修改的时间。
	 */
	@ApiModelProperty(value = "修改时间。")
	private java.util.Date gmt_modified;
	/**
	 * 地市/区县ID--基本信息页面
	 */
	@ApiModelProperty(value = "地市/区县Id")
	private java.lang.String regionId;

	/**
	 * 地市/区县ID--基本信息页面反参用
	 */
	@ApiModelProperty(value = "地市/区县Id")
	private java.lang.String key;

	/**
	 * 地市/区县名称--基本信息页面反参用
	 */
	@ApiModelProperty(value = "地市/区县名称")
	private java.lang.String title;
	/**
	 * 是否删除：0未删、1删除。
	 */
	@ApiModelProperty(value = "是否删除：0未删、1删除。")
	private String isDeleted;
  	
}
