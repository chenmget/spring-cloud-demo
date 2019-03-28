package com.iwhalecloud.retail.goods2b.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * GoodsContract
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型ES_GOODS_CONTRACT, 对应实体GoodsContract类")
public class GoodsContractDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * 商品id
  	 */
	@ApiModelProperty(value = "商品id")
  	private String goodsId;
	
	/**
  	 * 合约期
  	 */
	@ApiModelProperty(value = "合约期")
  	private Long contractPeriod;
	
	/**
  	 * 购机款
  	 */
	@ApiModelProperty(value = "购机款")
  	private Double purchasePrice;
	
	/**
  	 * 话费预存
  	 */
	@ApiModelProperty(value = "话费预存")
  	private Double deposit;
	
	/**
  	 * 首月返还
  	 */
	@ApiModelProperty(value = "首月返还")
  	private Double firstReturn;
	
	/**
  	 * 次月起返还
  	 */
	@ApiModelProperty(value = "次月起返还")
  	private Double nextReturn;
	
	/**
  	 * 平台
  	 */
	@ApiModelProperty(value = "平台")
  	private String sourceFrom;
	
  	
}
