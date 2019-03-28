package com.iwhalecloud.retail.goods.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;


/**
 * ProdGoods
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型prod_goods, 对应实体ProdGoods类")
public class ProdGoodsDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * 商品id
  	 */
	@ApiModelProperty(value = "商品id")
  	private String goodsId;
	
	/**
  	 * 商品名称
  	 */
	@ApiModelProperty(value = "商品名称")
  	private String name;
	
	/**
  	 * 商品编码
  	 */
	@ApiModelProperty(value = "商品编码")
  	private String sn;
	
	/**
  	 * 品牌id
  	 */
	@ApiModelProperty(value = "品牌id")
  	private String brandId;
	
	/**
  	 * 商品目录
  	 */
	@ApiModelProperty(value = "商品目录")
  	private String catId;

	/**
	 * 类型ID
	 */
	@ApiModelProperty(value = "类型ID")
	private String typeId;
	
	/**
  	 * 重量
  	 */
	@ApiModelProperty(value = "重量")
  	private Double weight;
	
	/**
  	 * 状态 1上架 0下架
  	 */
	@ApiModelProperty(value = "状态 1上架 0下架")
  	private Integer marketEnable;
	
	/**
  	 * 介绍
  	 */
	@ApiModelProperty(value = "介绍")
  	private String intro;
	
	/**
  	 * 销售价
  	 */
	@ApiModelProperty(value = "销售价")
  	private Double price;
	
	/**
  	 * 成本价
  	 */
	@ApiModelProperty(value = "成本价")
  	private Double cost;
	
	/**
  	 * 市场价
  	 */
	@ApiModelProperty(value = "市场价")
  	private Double mktprice;
	
	/**
  	 * 创建时间
  	 */
	@ApiModelProperty(value = "创建时间")
  	private java.util.Date createTime;
	
	/**
  	 * 更新时间
  	 */
	@ApiModelProperty(value = "更新时间")
  	private java.util.Date lastModify;
	
	/**
  	 * 查看次数
  	 */
	@ApiModelProperty(value = "查看次数")
  	private Long viewCount;
	
	/**
  	 * 购买数
  	 */
	@ApiModelProperty(value = "购买数")
  	private Long buyCount;
	
	/**
  	 * sord
  	 */
	@ApiModelProperty(value = "sord")
  	private Long sord;
	
	/**
  	 * 商品发布人
  	 */
	@ApiModelProperty(value = "商品发布人")
  	private String creatorUser;
	
	/**
  	 * 供货商id
  	 */
	@ApiModelProperty(value = "供货商id")
  	private String supperId;
	
	/**
  	 * 审批状态
  	 */
	@ApiModelProperty(value = "审批状态")
  	private String auditState;
	
	/**
  	 * 产品简称
  	 */
	@ApiModelProperty(value = "产品简称")
  	private String simpleName;
	
	/**
  	 * 最小量
  	 */
	@ApiModelProperty(value = "最小量")
  	private Long minNim;
	
	/**
  	 * 单位
  	 */
	@ApiModelProperty(value = "单位")
  	private String unit;
	
	/**
  	 * _商家_分类_品牌_商品名称_ search_key like %%
  	 */
	@ApiModelProperty(value = "_商家_分类_品牌_商品名称_ search_key like %%")
  	private String searchKey;
	
	/**
  	 * 卖点
  	 */
	@ApiModelProperty(value = "卖点")
  	private String sellingPoint;
	
	/**
  	 * 地市编码
  	 */
	@ApiModelProperty(value = "地市编码")
  	private String regionId;
	
	/**
  	 * 地市名称
  	 */
	@ApiModelProperty(value = "地市名称")
  	private String regionName;

	/**
	 * 默认图片
	 */
	@ApiModelProperty(value = "默认图片")
	private String defaultImage;

    /**
     * 供应商名称
     */
    @ApiModelProperty(value = "供应商名称")
    private String supperName;

	/**
	 * 好评率
	 */
	@ApiModelProperty(value = "好评率")
	private BigDecimal goodsCommentsRate;
	/**
	 * 评论数
	 */
	@ApiModelProperty(value = "评论数")
	private Integer goodsCommentsNum;

	/**
	 * 是否串码 1串码 0非串码
	 */
	@ApiModelProperty(value = "是否串号 1串码 0非串码")
	private Integer isSerialNo;
}
