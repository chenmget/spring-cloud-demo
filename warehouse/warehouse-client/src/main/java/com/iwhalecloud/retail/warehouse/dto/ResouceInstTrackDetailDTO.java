package com.iwhalecloud.retail.warehouse.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * ResouceInstTrackDetail
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型mkt_res_inst_track_detail, 对应实体ResouceInstTrackDetail类")
public class ResouceInstTrackDetailDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * 营销资源实例的标识，主键
  	 */
	@ApiModelProperty(value = "营销资源实例的标识，主键")
  	private java.lang.String trackDetailId;
	
	/**
  	 * 入库类型：交易入库、调拨入库、领用入库、零售商绿色通道、供应商自有资源、厂商录入
  	 */
	@ApiModelProperty(value = "入库类型：交易入库、调拨入库、领用入库、零售商绿色通道、供应商自有资源、厂商录入")
  	private java.lang.String storageType;
	
	/**
  	 * 记录营销资源实例编码。
  	 */
	@ApiModelProperty(value = "记录营销资源实例编码。")
  	private java.lang.String mktResInstNbr;
	
	/**
  	 * 源商家ID，没源商家时该字段为空
  	 */
	@ApiModelProperty(value = "源商家ID，没源商家时该字段为空")
  	private java.lang.String sourceMerchantId;
	
	/**
  	 * 目标商家ID
  	 */
	@ApiModelProperty(value = "目标商家ID")
  	private java.lang.String targetMerchantId;
	
	/**
  	 * 营销资源仓库标识
  	 */
	@ApiModelProperty(value = "营销资源仓库标识")
  	private java.lang.String sourceStoreId;
	
	/**
  	 * 营销资源仓库标识
  	 */
	@ApiModelProperty(value = "营销资源仓库标识")
  	private java.lang.String targetStoreId;
	
	/**
  	 * 记录出库时间
  	 */
	@ApiModelProperty(value = "记录出库时间")
  	private java.util.Date outTime;
	
	/**
  	 * 记录入库时间
  	 */
	@ApiModelProperty(value = "记录入库时间")
  	private java.util.Date inTime;
	
	/**
  	 * 记录源本地网标识。
  	 */
	@ApiModelProperty(value = "记录源本地网标识。")
  	private java.lang.String sourceLanId;
	
	/**
  	 * 记录目标本地网标识。
  	 */
	@ApiModelProperty(value = "记录目标本地网标识。")
  	private java.lang.String targetLanId;
	
	/**
  	 * 记录源本地网标识。
  	 */
	@ApiModelProperty(value = "记录源本地网标识。")
  	private java.lang.String sourceRegionId;
	
	/**
  	 * 记录目标本地网标识。
  	 */
	@ApiModelProperty(value = "记录目标本地网标识。")
  	private java.lang.String targetRegionId;
	
	/**
  	 * orderId
  	 */
	@ApiModelProperty(value = "orderId")
  	private java.lang.String orderId;
	
	/**
  	 * orderTime
  	 */
	@ApiModelProperty(value = "orderTime")
  	private java.util.Date orderTime;
	
  	
}
