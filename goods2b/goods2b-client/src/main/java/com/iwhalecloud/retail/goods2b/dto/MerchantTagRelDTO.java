package com.iwhalecloud.retail.goods2b.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * PartnerTagRel
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型prod_merchant_tag_rel, 对应实体MerchantTagRel类")
public class MerchantTagRelDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;


	/**
	 * 标签名称 从 prod_tags表冗余
	 */
	@ApiModelProperty(value = "标签名称")
	private String tagName;
  	
  	//属性 begin
	/**
  	 * 关联ID
  	 */
	@ApiModelProperty(value = "关联ID")
  	private String relId;
	
	/**
  	 * 标签ID
  	 */
	@ApiModelProperty(value = "标签ID")
  	private String tagId;

	/**
	 * 店中商(分销商) ID
	 */
	@ApiModelProperty(value = "商家 ID")
	private String merchantId;



}
