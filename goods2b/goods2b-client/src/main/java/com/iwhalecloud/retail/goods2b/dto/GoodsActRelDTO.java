package com.iwhalecloud.retail.goods2b.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * GoodsActRel
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型prod_goods_act_rel, 对应实体GoodsActRel类")
public class GoodsActRelDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * 关联ID
  	 */
	@ApiModelProperty(value = "关联ID",hidden = true)
  	private String relId;
	
	/**
  	 * 商品ID
  	 */
	@ApiModelProperty(value = "商品ID")
  	private String goodsId;
	
	/**
  	 * 营销活动ID
  	 */
	@ApiModelProperty(value = "营销活动ID")
  	private String actId;
	
	/**
  	 * 营销活动名称
  	 */
	@ApiModelProperty(value = "营销活动名称")
  	private String actName;

	/**
	 * 活动类型
	 */
	@ApiModelProperty(value = "活动类型\n" +
			"1001-预售\n" +
			"1002-前置补贴\n" +
			"1003-返利")
	private java.lang.String actType;
	
	/**
  	 * 是否删除
  	 */
	@ApiModelProperty(value = "是否删除" , hidden = true)
  	private String isDeleted;
	
  	
}
