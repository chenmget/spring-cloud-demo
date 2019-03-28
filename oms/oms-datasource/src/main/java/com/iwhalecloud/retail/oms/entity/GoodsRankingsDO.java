package com.iwhalecloud.retail.oms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 商品加购历史
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型R_GOODS_RANKINGS")
@TableName("R_GOODS_RANKINGS")
public class GoodsRankingsDO implements Serializable {
    /**表名常量*/
    public static final String TNAME = "R_ORDER_CART";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 加购id
  	 */
	@ApiModelProperty(value = "加购id")
	@TableId(type = IdType.ID_WORKER)
  	private Long id;

	/**
	 * 产品编号
	 */
	@ApiModelProperty(value = "业务ID")
	private java.lang.String objId;

	/**
	 * 产品编号
	 */
	@ApiModelProperty(value = "业务编码")
	private java.lang.String objType;
  	
  	/**
  	 * 产品编号
  	 */
	@ApiModelProperty(value = "产品编号")
  	private java.lang.String productId;
  	
  	/**
  	 * 商品编号
  	 */
	@ApiModelProperty(value = "商品编号")
  	private java.lang.String goodsId;
  	
  	/**
  	 * 订单编号
  	 */
	@ApiModelProperty(value = "订单编号")
  	private java.lang.String orderId;
  	
  	/**
  	 * 数量
  	 */
	@ApiModelProperty(value = "数量")
  	private java.lang.Long num;
  	
  	/**
  	 * 回话id
  	 */
	@ApiModelProperty(value = "回话id")
  	private java.lang.String sessionId;
  	
  	/**
  	 * 商品名称
  	 */
	@ApiModelProperty(value = "商品名称")
  	private java.lang.String goodsName;
  	/**
  	 * 会员id
  	 */
	@ApiModelProperty(value = "会员id")
  	private java.lang.String memberId;

	/**
	 * 会员id
	 */
	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	/**
	 * 会员id
	 */
	@ApiModelProperty(value = "修改时间")
	private Date lastModify;
  	
  	
  	//属性 end
  	
  	public static enum FieldNames{
        /** 加购id */
        cartId,
        /** 产品编号 */
        productId,
        /** 商品编号 */
        goodsId,
        /** 订单编号 */
        orderId,
        /** 数量 */
        num,
        /** 回话id */
        sessionId,
        /** 商品名称 */
        goodsName,
        /** 价格 */
        price,
        /** 会员等级 */
        memberLvId,
        /** 规格id */
        specId,
        /** 会员id */
        memberId
    }

	

}
