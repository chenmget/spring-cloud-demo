package com.iwhalecloud.retail.goods2b.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * ProdKeywords
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型PROD_KEYWORDS, 对应实体ProdKeywords类")
public class ProdKeywordsDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * 关键字ID
  	 */
	@ApiModelProperty(value = "关键字ID")
  	private String keywordsId;
	
	/**
  	 * 记录搜索关键字
  	 */
	@ApiModelProperty(value = "记录搜索关键字")
  	private String keywords;
	
	/**
  	 * 记录搜索次数
  	 */
	@ApiModelProperty(value = "记录搜索次数")
  	private Long searchNum;
	
  	
}
