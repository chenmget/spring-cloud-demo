package com.iwhalecloud.retail.goods.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * ProdGoodsRel
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型prod_goods_rel, 对应实体ProdGoodsRel类")
@KeySequence(value="seq_prod_goods_rel_id",clazz = String.class)
public class ProdGoodsRel implements Serializable {
    /**表名常量*/
    public static final String TNAME = "prod_goods_rel";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 关联ID
  	 */
	@TableId
	@ApiModelProperty(value = "关联ID")
  	private String relId;
  	
  	/**
  	 * A端商品id
  	 */
	@ApiModelProperty(value = "A端商品id")
  	private String aGoodsId;
  	
  	/**
  	 * Z端商品id
  	 */
	@ApiModelProperty(value = "Z端商品id")
  	private String zGoodsId;
  	
  	/**
  	 * 合约关联套餐CONTRACT_OFFER、终端关联购买计划TERMINAL_PLAN
  	 */
	@ApiModelProperty(value = "合约关联套餐CONTRACT_OFFER、终端关联购买计划TERMINAL_PLAN")
  	private String relType;
  	
  	
  	//属性 end
  	
  	public static enum FieldNames{
        /** 关联ID */
        relId,
        /** A端商品id */
        agoodsId,
        /** Z端商品id */
        zgoodsId,
        /** 合约关联套餐CONTRACT_OFFER、终端关联购买计划TERMINAL_PLAN */
        relType
    }

	

}
