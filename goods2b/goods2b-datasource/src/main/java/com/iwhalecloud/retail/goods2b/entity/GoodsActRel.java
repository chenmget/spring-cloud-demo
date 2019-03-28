package com.iwhalecloud.retail.goods2b.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * GoodsActRel
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("prod_goods_act_rel")
@KeySequence(value="seq_prod_goods_act_rel_id",clazz = String.class)
@ApiModel(value = "对应模型prod_goods_act_rel, 对应实体GoodsActRel类")
public class GoodsActRel implements Serializable {
    /**表名常量*/
    public static final String TNAME = "prod_goods_act_rel";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 关联ID
  	 */
	@TableId
	@ApiModelProperty(value = "关联ID")
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
	@ApiModelProperty(value = "是否删除")
  	private String isDeleted;
  	
  	
  	//属性 end
  	
  	public static enum FieldNames{
        /** 关联ID */
        relId,
        /** 商品ID */
        goodsId,
        /** 营销活动ID */
        actId,
        /** 营销活动名称 */
        actName,
        /** 是否删除 */
        isDeleted
    }

	

}
