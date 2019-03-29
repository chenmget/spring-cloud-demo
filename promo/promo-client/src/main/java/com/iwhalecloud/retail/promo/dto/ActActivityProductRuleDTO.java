package com.iwhalecloud.retail.promo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * ActActivityProductRule
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型ACT_ACTIVITY_PRODUCT_RULE, 对应实体ActActivityProductRule类")
public class ActActivityProductRuleDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * ID
  	 */
	@ApiModelProperty(value = "ID")
  	private String id;
	
	/**
  	 * 参与活动产品关联ID
  	 */
	@ApiModelProperty(value = "参与活动产品关联ID")
  	private String actProdRelId;
	
	/**
  	 * 产品ID
  	 */
	@ApiModelProperty(value = "产品ID")
  	private String productId;
	
	/**
  	 * 表达式
  	 */
	@ApiModelProperty(value = "表达式")
  	private String expression;
	
	/**
  	 * 规则条件
  	 */
	@ApiModelProperty(value = "规则条件")
  	private String ruleAmount;
	
	/**
  	 * 优惠金额
  	 */
	@ApiModelProperty(value = "优惠金额")
  	private String price;
	
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
  	private String creator;
	
	/**
  	 * 修改人
  	 */
	@ApiModelProperty(value = "修改人")
  	private String modifier;
	
	/**
  	 * sourceFrom
  	 */
	@ApiModelProperty(value = "sourceFrom")
  	private String sourceFrom;
	
	/**
  	 * 是否删除：0未删、1删除
  	 */
	@ApiModelProperty(value = "是否删除：0未删、1删除")
  	private String isDeleted;
	
  	
}
