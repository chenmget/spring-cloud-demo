package com.iwhalecloud.retail.goods.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * ProdGoodsContract
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型prod_goods_contract, 对应实体ProdGoodsContract类")
@KeySequence(value="seq_prod_goods_contract_id",clazz = String.class)
public class ProdGoodsContract implements Serializable {
    /**表名常量*/
    public static final String TNAME = "prod_goods_contract";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 商品id
  	 */
	@ApiModelProperty(value = "商品id")
	@TableId
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
  	private Long purchasePrice;
  	
  	/**
  	 * 话费预存
  	 */
	@ApiModelProperty(value = "话费预存")
  	private Long deposit;
  	
  	/**
  	 * 首月返还
  	 */
	@ApiModelProperty(value = "首月返还")
  	private Long firstReturn;
  	
  	/**
  	 * 次月起返还
  	 */
	@ApiModelProperty(value = "次月起返还")
  	private Long nextReturn;
  	
  	
  	//属性 end
  	
  	public static enum FieldNames{
        /** 商品id */
        goodsId,
        /** 合约期 */
        contractPeriod,
        /** 购机款 */
        purchasePrice,
        /** 话费预存 */
        deposit,
        /** 首月返还 */
        firstReturn,
        /** 次月起返还 */
        nextReturn
    }

	

}
